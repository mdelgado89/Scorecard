package com.StarStudios.Scorecard;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.StarStudios.Database.UserSQLHelper;
import com.StarStudios.UserData.CUser;

public class CreateProfileActivity extends Activity implements OnClickListener
{
	Button btnAddFirstTeam;
	Button btnContinue;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_profile);
		
		EditText Emailtxt = (EditText)findViewById(R.id.create_profile_email);
		
		Emailtxt.setText(getUsername());
		
		btnAddFirstTeam = (Button)findViewById(R.id.btnAddFirstTeam);
		btnAddFirstTeam.setOnClickListener(this);
		
		btnContinue = (Button)findViewById(R.id.create_profile_btn_continue);
		btnContinue.setOnClickListener(this);
	}
	
	public String getUsername()
	{
		AccountManager manager = AccountManager.get(this);
		Account[] accounts = manager.getAccountsByType("com.google");
		List<String> possibleEmails = new ArrayList<String>();

		for (Account account : accounts)
		{
			// TODO: Check possibleEmail against an email regex or treat
			// account.name as an email address only for certain account.type
			// values.
			possibleEmails.add(account.name);
		}

		if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null)
		{
			String email = possibleEmails.get(0);
			
			return email;
		}
		else
			return null;
	}

	private CUser moveToNextIntent()
	{
		EditText etFirstName = (EditText)findViewById(R.id.cp_et_first_name);
		EditText etLastName = (EditText)findViewById(R.id.cp_et_last_name);
		
		EditText emailTxt = (EditText)findViewById(R.id.create_profile_email);
		EditText passwordTxt = (EditText)findViewById(R.id.create_profile_password);
		
		CUser user = new CUser(etFirstName.getText().toString(), etLastName.getText().toString(), emailTxt.getText().toString(), passwordTxt.getText().toString());
		
		UserSQLHelper userSQL = ((ScorecardApp)getApplication()).getUserSQL();
		userSQL.insert(user);
		
		return user;
	}
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if(v == btnContinue)
		{
			CUser user = moveToNextIntent();
			
			Intent intent = new Intent(this, ChooseTeamActivity.class);
			intent.putExtra("user", user);
			intent.putExtra("AddTeam", false);
			
			startActivity(intent);
			finish();
		}
		else if(v == btnAddFirstTeam)
		{
			CUser user = moveToNextIntent();
			
			Intent intent = new Intent(this, ChooseTeamActivity.class);
			intent.putExtra("user", user);
			intent.putExtra("AddTeam", true);
			startActivity(intent);
			
			finish();
		}
	}
}
