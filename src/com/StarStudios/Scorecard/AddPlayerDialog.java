package com.StarStudios.Scorecard;

import java.util.Arrays;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.StarStudios.Database.PlayerDataTable;
import com.StarStudios.Database.UserSQLHelper;
import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.PlayerData.EPositions;
import com.StarStudios.TeamData.CTeam;

public class AddPlayerDialog extends Dialog implements OnClickListener
{
	Context context;
	Button cancel, add;
	EditText firstName, lastName;
	Spinner lstPositions;
	UserSQLHelper dbHelper;
	CTeam team;
	
	
	private AddPlayerDialog(Context context)
	{
		super(context);
		setContentView(R.layout.add_player_dialog);
		this.context = context;
		this.setCanceledOnTouchOutside(true);
		
		setTitle(this.context.getString(R.string.add_player));
		setupButtons();
	}
	public AddPlayerDialog(Context context, CTeam team, Application app)
	{
		super(context);
		setContentView(R.layout.add_player_dialog);
		this.context = context;
		this.setCanceledOnTouchOutside(true);
		
		setTitle(this.context.getString(R.string.add_player));
		setupButtons();
		this.team = team;
		dbHelper = ((ScorecardApp)app).getUserSQL();
		
	}
	
	public void setupButtons()
	{
		add = (Button)findViewById(R.id.ap_btn_add);
		cancel = (Button)findViewById(R.id.ap_btn_cancel);
		
		firstName = (EditText)findViewById(R.id.ap_et_player_first_name);
		lastName = (EditText)findViewById(R.id.ap_et_player_last_name);
		
		lstPositions = (Spinner)findViewById(R.id.ap_sp_player_position);
		setupSpinner();
		
		add.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	public void setupSpinner()
	{
		ArrayAdapter<EPositions> adapter = new ArrayAdapter<EPositions>(
				context, android.R.layout.simple_spinner_item);
		adapter.addAll(Arrays.asList(EPositions.values()));
		lstPositions.setAdapter(adapter);
	}

	@SuppressWarnings("unchecked")
	public void editPlayer(CPlayer player)
	{
		firstName.setText(player.getFirstName());
		lastName.setText(player.getLastName());
		add.setText("Apply");
		int posSelected = ((ArrayAdapter<EPositions>)lstPositions.getAdapter()).getPosition(player.getAllPositions().get(0));
		lstPositions.setSelection(posSelected);
	}
	@Override
	public void onClick(View arg0)
	{
		if(arg0 == add)
		{
			if(add.getText() == "Apply")
			{
				
			}
			else
			{
				//Add player and new set of stats to database
				CPlayer player = new CPlayer(firstName.getText().toString().trim(), lastName.getText().toString().trim());
				player.addPosition((EPositions)lstPositions.getSelectedItem());

				team.addPlayer(player);
				
				PlayerDataTable pdt = new PlayerDataTable(context, dbHelper);
				pdt.insert(player);
				
				Toast.makeText(context, "Player Added", Toast.LENGTH_LONG).show();
				
				dismiss();
			}

		}
		else if(arg0 == cancel)
		{
			dismiss();
		}		
	}

}
