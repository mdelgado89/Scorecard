package com.StarStudios.Scorecard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.StarStudios.Database.PlayerDataTable;
import com.StarStudios.Database.TeamDataTable;
import com.StarStudios.Database.UserDataTable;
import com.StarStudios.Database.UserSQLHelper;
import com.StarStudios.TeamData.CTeam;
import com.StarStudios.UserData.CUser;


public class ScorecardActivity extends Activity implements OnClickListener
{

	CUser user;
	EditText txtUsername, txtPassword;
	Button btnNewUser, btnSignIn;
	UserSQLHelper sqlHelper;
	UserDataTable udt;
	TeamDataTable tdt;
	PlayerDataTable pdt;
	CheckBox cbRememberMe;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setupActivity();
		sqlHelper = ((ScorecardApp)getApplication()).getUserSQL();
		udt = new UserDataTable(this, sqlHelper);
		tdt = new TeamDataTable(this, sqlHelper);
		pdt = new PlayerDataTable(this, sqlHelper);

		ScorecardApp app = (ScorecardApp)getApplication();
		
		if(!app.username.isEmpty() && !app.password.isEmpty())
		{
			signInHelper(app.username, app.password);
		}
		
	}
	
	public void setupActivity()
	{
		txtUsername = (EditText)findViewById(R.id.sign_in_activity_email);
		txtPassword = (EditText)findViewById(R.id.sign_in_activity_password);
		
		
		btnSignIn =  (Button)findViewById(R.id.sign_in_activity_signInButton);
		btnNewUser = (Button)findViewById(R.id.sign_in_activity_new_user);
		
		btnNewUser.setOnClickListener(this);
		btnSignIn.setOnClickListener(this);
		
		cbRememberMe = (CheckBox)findViewById(R.id.sia_cb_remember_me);
	}
	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if(v == (View)btnNewUser)
		{
			//new intent for new activity
			
			//start new intent with result back
			//the user created goes to database
			//user creates new team in Main team menu

			Intent userIntent = new Intent(this, CreateProfileActivity.class);
			//userIntent.putExtra("user", user);
			startActivity(userIntent);
			this.finish();
		}
		else if(v == (View)btnSignIn)
		{
			//new intent for new activity
			//check database for username and password correct
			//then start activity
			if(cbRememberMe.isChecked())
			{
				SharedPreferences.Editor editor = ((ScorecardApp)getApplication()).sharedPrefs.edit();
				editor.putString("username", txtUsername.getText().toString());
				editor.putString("password", txtPassword.getText().toString());
				editor.commit();
			}
			signInHelper(txtUsername.getText().toString(), txtPassword.getText().toString());
		}
	}
	
	private void signInHelper(String username, String password)
	{
		CUser currentUser = udt.findUser(username);
		tdt.query(currentUser);
		addPlayersToTeams(currentUser);
		if(currentUser != null)
		{
			if(password.contentEquals(currentUser.getPassword()))
			{
				Intent intent = new Intent(this, ChooseTeamActivity.class);
				intent.putExtra("user", currentUser);
				startActivity(intent);
				finish();
			}
		}
		else
		{
			Toast toast = Toast.makeText(this, "Username or Password not found", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	private void addPlayersToTeams(CUser user)
	{
		for(CTeam team : user.getTeams())
		{
			pdt.query(team);
		}
	}
}
