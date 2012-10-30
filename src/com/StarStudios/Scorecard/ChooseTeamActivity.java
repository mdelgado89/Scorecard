package com.StarStudios.Scorecard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.StarStudios.TeamData.CTeam;
import com.StarStudios.UserData.CUser;

public class ChooseTeamActivity extends Activity implements OnNavigationListener, OnDismissListener, OnItemClickListener
{
	private static final String TAG = "Choose Team Activity";
	private ActionBar actionBar = null;
	private CUser user;
	private ListView lstOptions;
	private List<String> mainOptions;
	private final int NEW_TEAM_RESULT = 1;
	private CTeam currentTeam;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_team);
		
		
		mainOptions = new ArrayList<String>();
		
		mainOptions.add("Score Game");
		mainOptions.add("Set Lineup");
		mainOptions.add("Statistics");
		mainOptions.add("Edit Players");

		lstOptions = (ListView)findViewById(R.id.ct_lst_team_options);
		
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.layout_content, R.id.team_list_content, mainOptions);
		
		lstOptions.setAdapter(adapter);
		lstOptions.setOnItemClickListener(this);
		
		actionBar = getActionBar();
		
		Intent intent = getIntent();
		boolean addTeam = false;
		addTeam = intent.getBooleanExtra("AddTeam", addTeam);
		
		setTeamActionBar();
		
		if(addTeam)
		{
			setTheme(android.R.style.Theme_Holo_Light_Dialog);
			Dialog d = new AddTeamDialog(this, user, getApplication());
			d.setOnDismissListener(this);
			d.show();
		}
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
		//Log.d(TAG, "class has started again");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		Log.d(TAG, "On Resume Called");
	}
	
	public void setTeamActionBar()
	{
		Intent intents = getIntent();
		
		user = (CUser)intents.getSerializableExtra("user");
		
		refreshTeamList();
		
		Log.d(TAG, "Action Bar Inited");
	}
	private void refreshTeamList()
	{
		Set<CTeam> steams = user.getTeams();
		CTeam[] teamArray = new CTeam[steams.size()];
		steams.toArray(teamArray);
		SpinnerAdapter teamArrayAdapter = new ArrayAdapter<CTeam>(this, R.layout.action_layout_content, R.id.action_list_content, teamArray);
		
		actionBar.setDisplayShowTitleEnabled(false);
		//actBar.setTitle("");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		actionBar.setListNavigationCallbacks(teamArrayAdapter, this);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// TODO Auto-generated method stub
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.user_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.menu_user_options:
			Log.d(TAG, "Enter User Options");
			break;
		case R.id.menu_team_options:
			Log.d(TAG, "Enter Team Options");
			break;
		case R.id.add_team:
			Log.d(TAG, "Add Team");
			setTheme(android.R.style.Theme_Holo_Light_Dialog);
			Dialog d = new AddTeamDialog(this, user, getApplication());
			d.setOnDismissListener(this);
			d.show();
			break;
		case R.id.app_settings:
			signOut();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	private void signOut()
	{
		SharedPreferences.Editor edit = ((ScorecardApp)getApplication()).sharedPrefs.edit();
		edit.putString("username", "");
		edit.putString("password", "");
		edit.commit();
		
		Intent intent = new Intent(this, ScorecardActivity.class);
		startActivity(intent);
		
		finish();
	}
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		// TODO Auto-generated method stub
		currentTeam = (CTeam) user.getTeams().toArray()[itemPosition];
		return true;
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		// TODO Auto-generated method stub
		if(dialog instanceof AddTeamDialog)
		{			
			setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
			refreshTeamList();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case NEW_TEAM_RESULT:
			if(resultCode == Activity.RESULT_OK)
			{
				CTeam team = (CTeam)data.getSerializableExtra("newteam");
				user.getTeams().remove(currentTeam);
				user.addTeam(team);
				refreshTeamList();
				
				Log.d(TAG, team.getTeamName());
			}
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		if(arg0.getItemAtPosition(arg2).toString() == "Edit Players")
		{
			Intent intent = new Intent(this, EditPlayersActivity.class);
			
			intent.putExtra("Team", currentTeam);
			startActivityForResult(intent, NEW_TEAM_RESULT);
		}
		else if(arg0.getItemAtPosition(arg2).toString() == "Statistics")
		{
			Intent intent = new Intent(this, StatsActivity.class);
			
			intent.putExtra("Team", currentTeam);
			startActivity(intent);
		}
	}
}
