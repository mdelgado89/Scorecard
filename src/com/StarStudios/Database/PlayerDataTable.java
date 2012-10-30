package com.StarStudios.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.PlayerData.CStats;
import com.StarStudios.PlayerData.EPositions;
import com.StarStudios.TeamData.CTeam;

public class PlayerDataTable
{
	public static final String T_NAME = "player_data";
	
	public static final String C_ID = "player_id";
	public static final String C_FIRSTNAME = "first_name";
	public static final String C_LASTNAME = "last_name";
	public static final String C_POSITION = "position";
	//public static final String C_STATS_ID = "stats_id";
	
	public static final String DATABASE_CREATE = String
			.format("create table %s "
					+ "(%s integer primary key autoincrement, %s text not null, %s text not null, %s text not null, %s integer, "
					+ "FOREIGN KEY (%s) REFERENCES %s (%s))", T_NAME, C_ID,
					C_FIRSTNAME, C_LASTNAME, C_POSITION, TeamDataTable.C_ID, TeamDataTable.C_ID,
					TeamDataTable.T_NAME, TeamDataTable.C_ID);
	
	Context context;
	UserSQLHelper dbHelper;
	StatsDataTable statsTable;
	public PlayerDataTable(Context context, UserSQLHelper dbHelper)
	{
		this.context = context;
		this.dbHelper = dbHelper;

		statsTable = new StatsDataTable(this.context, this.dbHelper);
	}
	public void remove(CPlayer player)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String WHERE = C_FIRSTNAME + "=? AND " + C_LASTNAME + " =?";
		
		db.delete(T_NAME, WHERE, new String[]{player.getFirstName(), player.getLastName()});
	}
	public void insert(CPlayer player)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(C_FIRSTNAME, player.getFirstName());
		values.put(C_LASTNAME, player.getLastName());
		values.put(C_POSITION, player.getMainPosition().toString());
		values.put(TeamDataTable.C_ID, findTeamID(player.getTeam().getTeamName()));
		db.insert(T_NAME, null, values);

		query(player.getTeam());
		int playerID = findPlayerID(player);
		statsTable.insert(player.getStats(), playerID);
	}
	public int findTeamID(String teamName)
	{		
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// String[] columns = {C_EMAIL};
		String WHERE = TeamDataTable.C_TEAMNAME + "='" + teamName + "'";

		Cursor cursor = db.query(TeamDataTable.T_NAME, null, WHERE, null, null,
				null, null);

		if (cursor.moveToFirst())
		{
			return cursor.getInt(cursor.getColumnIndex(TeamDataTable.C_ID));
		}

		return -1;
	}
	public List<CPlayer> query(CTeam team)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		List<CPlayer> list = new ArrayList<CPlayer>();
		
		int teamID = findTeamID(team.getTeamName());
		
		String WHERE = TeamDataTable.C_ID + "='" + teamID + "'";
		
		Cursor cursor = db.query(T_NAME, null, WHERE, null, null, null, null);
		
		
		while(cursor.moveToNext())
		{
			CPlayer player = fromCursor(cursor);
			player.setTeam(team);
			
			
			//team.addPlayer(player);
				
			list.add(player);
		
		}
		team.setPlayer(list);
		
		return list;
	}
	
	public CPlayer fromCursor(Cursor c)
	{
		int nPlayerID = c.getInt(c.getColumnIndex(C_ID));
		
		CStats stats = statsTable.query(nPlayerID);
		
		CPlayer player = new CPlayer(c.getString(c.getColumnIndex(C_FIRSTNAME)),
									c.getString(c.getColumnIndex(C_LASTNAME)),
									stats);
		
		EPositions pos = EPositions.getPosition(c.getString(c.getColumnIndex(C_POSITION)));
		player.addPosition(pos);
		return player;
	}
	public int findPlayerID(CPlayer player)
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int teamID = findTeamID(player.getTeam().getTeamName());
		
		String WHERE = TeamDataTable.C_ID + "='" + teamID + "' AND "
				+ C_FIRSTNAME + "='" + player.getFirstName() + "' AND "
				+ C_LASTNAME + "='" +player.getLastName() + "'";

		Cursor cursor = db.query(T_NAME, null, WHERE, null, null, null, null);
		
		if(cursor.moveToFirst())
		{
			return cursor.getInt(cursor.getColumnIndex(C_ID));
		}
		return -1;
		
	}
}
