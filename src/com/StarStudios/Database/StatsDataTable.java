package com.StarStudios.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.StarStudios.PlayerData.CStats;

public class StatsDataTable
{
	public static final String T_NAME = "stats_table";
	
	public static final String C_ID = "stats_id";
	
	public static final String C_AT_BATS = "at_bats";
	public static final String C_HITS = "hits";
	public static final String C_SINGLES = "singles";
	public static final String C_DOUBLES = "doubles";
	public static final String C_TRIPLES = "triples";
	public static final String C_HOMERUNS = "home_runs";
	public static final String C_STRIKEOUTS = "strike_outs";
	public static final String C_GROUNDOUTS = "ground_outs";
	public static final String C_FLYOUTS = "fly_outs";
	public static final String C_SAC_FLYS = "sac_flys";
	public static final String C_SAC_BUNTS = "sac_bunts";
	public static final String C_RBIS = "rbi";
	public static final String C_WALKS = "walks";

	public static final String DATABASE_CREATE = String
			.format("create table %s "
					+ "(%s integer primary key autoincrement, " +
					"%s int, %s int, %s int, %s int, " +
					"%s int, %s int, %s int, %s int, %s int, %s int, " +
					"%s int, %s int, %s int, %s integer unique not null, " +
					"FOREIGN KEY (%s) REFERENCES %s (%s))", 
					T_NAME, C_ID, C_AT_BATS, C_HITS, C_SINGLES, C_DOUBLES, C_TRIPLES, 
					C_HOMERUNS, C_STRIKEOUTS, C_GROUNDOUTS, C_FLYOUTS, C_SAC_FLYS, 
					C_SAC_BUNTS, C_RBIS, C_WALKS, PlayerDataTable.C_ID, PlayerDataTable.C_ID, 
					PlayerDataTable.T_NAME,	PlayerDataTable.C_ID);
	
	Context context;
	UserSQLHelper dbHelper;
	
	public StatsDataTable(Context context, UserSQLHelper dbHelper)
	{
		this.context = context;
		this.dbHelper = dbHelper;
	}
	public CStats query(int playerID)
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		CStats stats = null;
		String WHERE = PlayerDataTable.C_ID + "='" + playerID + "'";
		Cursor cursor = db.query(T_NAME, null, WHERE, null, null, null, null, null);
		
		if(cursor.moveToNext())
		{
			stats = fromCursor(cursor);
		}
		
		return stats;
	}
	public CStats fromCursor(Cursor c)
	{
		CStats stats = new CStats();
		
		stats.setAtbats(c.getInt(c.getColumnIndex(C_AT_BATS)));
		stats.setHits(c.getInt(c.getColumnIndex(C_HITS)));
		stats.setSingles(c.getInt(c.getColumnIndex(C_SINGLES)));
		stats.setDoubles(c.getInt(c.getColumnIndex(C_DOUBLES)));
		stats.setTriples(c.getInt(c.getColumnIndex(C_TRIPLES)));
		stats.setHomeruns(c.getInt(c.getColumnIndex(C_HOMERUNS)));
		stats.setStrikeouts(c.getInt(c.getColumnIndex(C_STRIKEOUTS)));
		stats.setFlyouts(c.getInt(c.getColumnIndex(C_FLYOUTS)));
		stats.setGroundouts(c.getInt(c.getColumnIndex(C_GROUNDOUTS)));
		stats.setSacflys(c.getInt(c.getColumnIndex(C_SAC_FLYS)));
		stats.setSacBunts(c.getInt(c.getColumnIndex(C_SAC_BUNTS)));
		stats.setRbis(c.getInt(c.getColumnIndex(C_RBIS)));
		stats.setWalks(c.getInt(c.getColumnIndex(C_WALKS)));
		
		stats.calculateStats();
		
		return stats;
	}
	public void insert(CStats stats, int playerID)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(C_AT_BATS, stats.getAtbats());
		values.put(C_HITS, stats.getHits());
		values.put(C_SINGLES, stats.getHits());
		values.put(C_DOUBLES, stats.getDoubles());
		values.put(C_TRIPLES, stats.getTriples());
		values.put(C_HOMERUNS, stats.getHomeruns());
		values.put(C_STRIKEOUTS, stats.getStrikeouts());
		values.put(C_FLYOUTS, stats.getFlyouts());
		values.put(C_GROUNDOUTS, stats.getGroundouts());
		values.put(C_SAC_FLYS, stats.getSacflys());
		values.put(C_SAC_BUNTS, stats.getSacBunts());
		values.put(C_RBIS, stats.getRbis());
		values.put(C_WALKS, stats.getWalks());
		values.put(PlayerDataTable.C_ID, playerID);
		
		db.insert(T_NAME, null, values);
	}
}
