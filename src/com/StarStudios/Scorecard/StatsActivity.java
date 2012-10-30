package com.StarStudios.Scorecard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.TeamData.CTeam;

public class StatsActivity extends Activity implements OnItemClickListener
{ 
	CTeam team;
	ListView lstPlayers;
	ActionBar actionBar;
	@Override
	public void onCreate(Bundle onSavedInstance)
	{
		super.onCreate(onSavedInstance);
		setContentView(R.layout.stats_activity);
		setupActionBar();
		lstPlayers = (ListView)findViewById(R.id.sa_lv_PlayerNames);
		lstPlayers.setOnItemClickListener(this);
		Intent intent = getIntent();
		
		team = (CTeam)intent.getSerializableExtra("Team");
		
		populateList();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			break;
		}
		return false;
	}
	private void setupActionBar()
	{
		actionBar = getActionBar();
		
		actionBar.setTitle("Statistics");
		//actBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actBar.set
		
	}
	public void populateList()
	{
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		for (CPlayer player : team.getPlayers())
		{
			Map<String, Object> playerInfo = new HashMap<String, Object>(2);
			playerInfo.put("Player",
					player);
			playerInfo.put("Average", "Avg: " + 
					Float.toString(player.getStats().getAvg()));

			data.add(playerInfo);
		}
		// /query the database for players on current team
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.layout_content_left, new String[] { "Player",
						"Average" }, new int[] { R.id.team_list_content_left,
						R.id.sub_item_content });
		/*
		 * ArrayAdapter<CPlayer> adapter = new ArrayAdapter<CPlayer>(this,
		 * R.layout.layout_content, R.id.team_list_content, team.getPlayers());
		 */
		lstPlayers.setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		CPlayer player = null;
		Object o = arg0.getItemAtPosition(arg2);
		if(o instanceof Map<?, ?>)
		{
			Object mapObject = ((Map<?, ?>) o).get("Player");
			if(mapObject instanceof CPlayer)
			{
				player = (CPlayer) mapObject;
				//CStats stats = player.getStats();
				
				Dialog dialog = new PlayerStatsDialog(this, player);
				dialog.setTitle(player.getFullName());
				dialog.show();
				
			}
		}
	}
}
