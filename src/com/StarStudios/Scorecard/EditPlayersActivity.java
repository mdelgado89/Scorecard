package com.StarStudios.Scorecard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.StarStudios.Database.PlayerDataTable;
import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.TeamData.CTeam;

public class EditPlayersActivity extends Activity implements OnDismissListener, OnItemClickListener, OnClickListener
{

	private final int EDIT = 0;
	private final int REMOVE = 1;
	
	ListView lstPlayers;
	CTeam team;
	ActionBar actionBar;
	CPlayer player = null;
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		setContentView(R.layout.edit_players);
		lstPlayers = (ListView)findViewById(R.id.ep_lst_players);
		lstPlayers.setOnItemClickListener(this);
		team = (CTeam)getIntent().getSerializableExtra("Team");
		
		addAllPlayers();
		
		
		setupActionBar();
		
		
	}
	@Override
	public void onResume()
	{
		super.onResume();
		
		addAllPlayers();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// TODO Auto-generated method stub
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.edit_player_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	private void finishActivity()
	{
		Intent resultIntent = new Intent();
		resultIntent.putExtra("newteam", team);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}
	@Override
	public void onBackPressed() 
	{
		finishActivity();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			finishActivity();
			break;
		case R.id.add_player:
			Dialog d =  new AddPlayerDialog(this, team, getApplication());
			d.show();
			d.setOnDismissListener(this);
			break;
		}
		return false;
	}
	
	private void setupActionBar()
	{
		actionBar = getActionBar();
		
		actionBar.setTitle("Edit Players");
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	public boolean addAllPlayers()
	{
		///query the database for players on current team
		ArrayAdapter<CPlayer> adapter = new ArrayAdapter<CPlayer>(this, R.layout.layout_content, R.id.team_list_content, team.getPlayers());
		lstPlayers.setAdapter(adapter);
		return true;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog)
	{
		// TODO Auto-generated method stub
		if(dialog instanceof AddPlayerDialog)
		{
			addAllPlayers();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub
		if(arg0.getItemAtPosition(arg2) instanceof CPlayer)
		{
			player = ((CPlayer)arg0.getItemAtPosition(arg2));
			Builder ad = new AlertDialog.Builder(this);
			ad.setTitle(player.getFullName());
			ad.setItems(new String[]{"Edit", "Remove"}, this);
			
			ad.show();
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		// TODO Auto-generated method stub
		if(which == EDIT)
		{
			AddPlayerDialog d =  new AddPlayerDialog(this, team, getApplication());

			d.editPlayer(player);
			d.show();
			d.setOnDismissListener(this);
		}
		else if (which == REMOVE)
		{
			PlayerDataTable pdt = new PlayerDataTable(this, ((ScorecardApp)getApplication()).getUserSQL());
			pdt.remove(player);
			//((ScorecardApp)getApplication()).getUserSQL().;
			
			team.removePlayer(player);
			Toast.makeText(this, "Player Removed", Toast.LENGTH_LONG).show();
			
			
		}
		
		addAllPlayers();
	}
}