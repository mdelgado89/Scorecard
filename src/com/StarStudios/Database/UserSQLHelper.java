package com.StarStudios.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.StarStudios.UserData.CUser;

public class UserSQLHelper extends SQLiteOpenHelper
{
	static final String TAG = "UserData";

	public static final String DB_NAME = "userdata.db";
	private UserDataTable udt;
	
//	public static final String T_NAME = "user_data";
//
//	public static final String C_ID = "_id";
//	public static final String C_FIRSTNAME = "first_name";
//	public static final String C_LASTNAME = "last_name";
//	public static final String C_EMAIL = "email";
//	public static final String C_PASSWORD = "password";

	public static final int DB_VERSION = 12;

	SQLiteDatabase db;

	public UserSQLHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
		udt = new UserDataTable(context, this);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
//		String DATABASE_CREATE = String
//				.format("create table %s "
//						+ "(%s integer primary key autoincrement, %s text not null, %s text not null, %s text unique not null, %s text not null)",
//						T_NAME, C_ID, C_FIRSTNAME, C_LASTNAME, C_EMAIL,
//						C_PASSWORD);

		//Log.d(TAG, "onCreate with SQL: " + UserDataTable.DATABASE_CREATE);

		// TODO Auto-generated method stub
		db.execSQL(UserDataTable.DATABASE_CREATE);
		db.execSQL(TeamDataTable.DATABASE_CREATE);
		db.execSQL(PlayerDataTable.DATABASE_CREATE);
		db.execSQL(StatsDataTable.DATABASE_CREATE);
	}

	/*
	 * this is going to be deprecated
	 */
	public void insert(CUser user)
	{
		udt.insert(user);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Usually alter table statement
		db.execSQL("drop table if exists " + UserDataTable.T_NAME);
		db.execSQL("drop table if exists " + TeamDataTable.T_NAME);
		db.execSQL("drop table if exists " + PlayerDataTable.T_NAME);
		db.execSQL("drop table if exists " + StatsDataTable.T_NAME);

		onCreate(db);
	}

}
