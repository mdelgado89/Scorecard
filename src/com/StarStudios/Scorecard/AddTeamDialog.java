package com.StarStudios.Scorecard;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.StarStudios.Database.TeamDataTable;
import com.StarStudios.Database.UserSQLHelper;
import com.StarStudios.TeamData.CTeam;
import com.StarStudios.UserData.CUser;

public class AddTeamDialog extends Dialog implements OnClickListener
{

	Button cancel;
	Button addTeam;
	EditText teamName;
	RadioButton rbSoftball;
	RadioButton rbBaseball;
	CheckBox cbDefault;
	CUser user;
	Context context;
	UserSQLHelper dbHelper;
	public AddTeamDialog(Context context, CUser user)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.add_team_dialog);
		this.setCanceledOnTouchOutside(true);
		
		setTitle(R.string.add_team);
		this.user = user;
		
		setupButtons();
		
	}
	
	public AddTeamDialog(Context context, CUser user, Application app)
	{

		super(context);
		this.context = context;
		setContentView(R.layout.add_team_dialog);
		this.setCanceledOnTouchOutside(true);
		
		setTitle(R.string.add_team);
		this.user = user;
		
		setupButtons();
		
		dbHelper = ((ScorecardApp)app).getUserSQL();
	}
	
	private void setupButtons()
	{
		addTeam = (Button)findViewById(R.id.at_btn_add);
		cancel = (Button)findViewById(R.id.at_btn_cancel);
		teamName = (EditText)findViewById(R.id.at_et_team_name);
		//RadioGroup rg = (RadioGroup)findViewById(R.id.at_rg_choose_buttons);
		//rg.setShowDividers(RadioGroup.SHOW_DIVIDER_MIDDLE);
		rbSoftball = (RadioButton)findViewById(R.id.at_rb_softball);
		rbBaseball = (RadioButton)findViewById(R.id.at_rb_baseball);
		cbDefault = (CheckBox)findViewById(R.id.at_cb_default_team);
		addTeam.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if(v == cancel)
		{
			dismiss();
		}
		else if(v == addTeam)
		{
			//add team to along with users stuff and database
			CTeam team = new CTeam(teamName.getText().toString());
			team.setUser(user);
			if(user.addTeam(team))
			{
				TeamDataTable tdt = new TeamDataTable(context, dbHelper);
				tdt.insert(team);
				dismiss();
			}
			else
			{
				Toast toast = Toast.makeText(context, "Team already exist", Toast.LENGTH_LONG);
				toast.show();
			}
			
			
		}
	}

}
