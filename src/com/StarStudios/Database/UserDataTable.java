package com.StarStudios.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.StarStudios.UserData.CUser;

public class UserDataTable
{
	public static final String T_NAME = "user_data";
	
	public static final String C_ID = "_id";
	public static final String C_FIRSTNAME = "first_name";
	public static final String C_LASTNAME = "last_name";
	public static final String C_EMAIL = "email";
	public static final String C_PASSWORD = "password";
	
	public static final String DATABASE_CREATE = String
			.format("create table %s "
					+ "(%s integer primary key autoincrement, %s text not null, %s text not null, %s text unique not null, %s text not null)",
					T_NAME, C_ID, C_FIRSTNAME, C_LASTNAME, C_EMAIL,
					C_PASSWORD);

	private UserSQLHelper dbHelper;
	Context context;
	public UserDataTable(Context context, UserSQLHelper dbHelper)
	{
		this.context = context;
		this.dbHelper = dbHelper;
		
	}
	public void insert(CUser user)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(C_FIRSTNAME, user.getFirstName());
		values.put(C_LASTNAME, user.getLastName());
		values.put(C_EMAIL, user.getEmail());
		values.put(C_PASSWORD, user.getPassword());

		db.insertWithOnConflict(T_NAME, null, values, SQLiteDatabase.CONFLICT_ABORT);
	}
	public List<CUser> query()
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		List<CUser> list = new ArrayList<CUser>();
		//private String[] allColumns = { C_ID, C_EMAIL };
		Cursor cursor = db.query(T_NAME, null, null, null, null, null, null); // Select * from database
		
		while(cursor.moveToNext())
		{
			list.add(fromCursor(cursor));
		}
		
		return list;
	}
	public CUser findUser(String email)
	{
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		//String[] columns = {C_EMAIL};
		String WHERE = C_EMAIL+"='" + email + "'";
		
		Cursor cursor = db.query(T_NAME, null, WHERE, null, null, null, null);

		CUser user = null;
		if(cursor.moveToFirst())
		{
			user = fromCursor(cursor);
		}

		return user;
	}
	private CUser fromCursor(Cursor C)
	{
		CUser user = null;
		
		user = new CUser(C.getString(C.getColumnIndex(C_FIRSTNAME)),
							C.getString(C.getColumnIndex(C_LASTNAME)),
							C.getString(C.getColumnIndex(C_EMAIL)),
							C.getString(C.getColumnIndex(C_PASSWORD)));
		
		return user;
	}
}
