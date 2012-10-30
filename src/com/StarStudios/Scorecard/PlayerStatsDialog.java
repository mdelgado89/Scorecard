package com.StarStudios.Scorecard;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.PlayerData.CStats;

public class PlayerStatsDialog extends Dialog
{
	
	CPlayer player = null;
	CStats stats = null;
	ListView listview = null;
	Context context;
	public PlayerStatsDialog(Context context)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.player_stats_dialog);
		listview = (ListView)findViewById(R.id.psd_lv_StatsList);
	}
	
	public PlayerStatsDialog(Context context, CPlayer player)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.player_stats_dialog);
		this.player = player;
		this.stats = player.getStats();
		
		listview = (ListView)findViewById(R.id.psd_lv_StatsList);
		
		setupStats();
	}

	public PlayerStatsDialog(Context context, CStats stats)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.player_stats_dialog);
		this.stats = stats;
		listview = (ListView)findViewById(R.id.psd_lv_StatsList);
	}
	
	public void setupStats()
	{
		if(stats != null)
		{			
			List<String> statsList = new ArrayList<String>();
			for(float f : stats.getInArray())
			{
				statsList.add(Float.toString(f));
			}
			
			ListAdapter adapter = new ArrayAdapter<String>(context, R.layout.layout_content_left, R.id.team_list_content_left, statsList);
			listview.setAdapter(adapter);
		}
	}
}
