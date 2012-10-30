package com.StarStudios.Scorecard;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

import com.StarStudios.Database.UserSQLHelper;

public class ScorecardApp extends Application implements OnSharedPreferenceChangeListener
{
	private static final String TAG = "Application";
	private UserSQLHelper userSql = null;
	SharedPreferences sharedPrefs;
	String username, password;
	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPrefs.registerOnSharedPreferenceChangeListener(this);
		
	}
	
	public UserSQLHelper getUserSQL()
	{
		if(userSql == null)
		{
			userSql = new UserSQLHelper(this);
			username = sharedPrefs.getString("username", "");
			password = sharedPrefs.getString("password", "");
		
			Log.d(TAG, "Username = " + username);
			Log.d(TAG, "Password = " + password);
		}
		return userSql;
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key)
	{
		// TODO Auto-generated method stub
		if(key.contentEquals("username"))
		{
			username = sharedPrefs.getString(key, "");
		}		
		else if(key.contentEquals("password"))
		{
			password = sharedPrefs.getString(key, "");
		}
	}
}
