package com.StarStudios.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.StarStudios.TeamData.CRecord;
import com.StarStudios.TeamData.CTeam;
import com.StarStudios.UserData.CUser;

public class TeamDataTable
{
	public static final String T_NAME = "team_data";
	
	public static final String C_ID = "team_id";
	public static final String C_TEAMNAME = "team_name";
	public static final String C_WINS = "wins";
	public static final String C_LOSSES = "losses";
	public static final String C_NODECISION = "no_decision";
	public static final String C_FORIEGN_ID = "user_id";

	public static final String DATABASE_CREATE = String
			.format("create table %s "
					+ "(%s integer primary key autoincrement, %s text unique not null, %s integer, %s integer, %s integer, %s integer, "
					+ "FOREIGN KEY (%s) REFERENCES %s (%s));", T_NAME, C_ID,
					C_TEAMNAME, C_WINS, C_LOSSES, C_NODECISION, C_FORIEGN_ID,
					C_FORIEGN_ID, UserDataTable.T_NAME,
					UserDataTable.C_ID);

	UserSQLHelper dbHelper;
	Context context;
	CUser user;
	
	public TeamDataTable(Context context, UserSQLHelper dbHelper)
	{
		this.context = context;
		this.dbHelper = dbHelper;
	}
	public void insert(CTeam team)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(C_TEAMNAME, team.getTeamName());
		values.put(C_WINS, team.getRecord().getWins());
		values.put(C_LOSSES, team.getRecord().getLosses());
		values.put(C_NODECISION, team.getRecord().getNoDecisions());
		values.put(C_FORIEGN_ID, findUserID(team.getUser().getEmail()));
		
		db.insertWithOnConflict(T_NAME, null, values, SQLiteDatabase.CONFLICT_ABORT);
	}
	
	public List<CTeam> query(CUser user)
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		List<CTeam> list = new ArrayList<CTeam>();
		
		int userID = findUserID(user.getEmail());
		String WHERE = C_FORIEGN_ID+"='" + userID + "'";
		Cursor cursor = db.query(T_NAME, null, WHERE, null, null, null, null);

		while(cursor.moveToNext())
		{
			CTeam team = fromCursor(cursor);
			team.setUser(user);
			user.addTeam(team);
				
			list.add(team);
		}
		
		return list;
	}
	
	private CTeam fromCursor(Cursor C)
	{
		CTeam team = null;
		CRecord record = new CRecord();
		team = new CTeam(C.getString(C.getColumnIndex(C_TEAMNAME)));
		record.setWins(C.getInt(C.getColumnIndex(C_WINS)));
		record.setLosses(C.getInt(C.getColumnIndex(C_LOSSES)));
		record.setNoDecisions(C.getInt(C.getColumnIndex(C_NODECISION)));
		team.setRecord(record);
		
		return team;
	}
	
	public int findUserID(String email)
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		//String[] columns = {C_EMAIL};
		String WHERE = UserDataTable.C_EMAIL+"='" + email + "'";
		
		Cursor cursor = db.query(UserDataTable.T_NAME, null, WHERE, null, null, null, null);

		if(cursor.moveToFirst())
		{
			return cursor.getInt(cursor.getColumnIndex(UserDataTable.C_ID));
		}

		return -1;
	}
}
