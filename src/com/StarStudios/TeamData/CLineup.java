package com.StarStudios.TeamData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.util.Log;

import com.StarStudios.PlayerData.CPlayer;

@SuppressWarnings("serial")
public class CLineup implements Serializable
{	
	static final String TAG = "LINEUP";
	private SortedMap<Integer, CPlayer> lineup;
	private List<CPlayer> benchPlayers;
	
	CLineup()
	{
		lineup = new TreeMap<Integer, CPlayer>();
		benchPlayers = new ArrayList<CPlayer>();
	}
	CLineup(List<CPlayer> benchPlayers)
	{
		lineup = new TreeMap<Integer, CPlayer>();
		this.benchPlayers = benchPlayers;
	}
	public void addPlayerToLineup(Integer batpos, CPlayer player)
	{
		//check to see if player is on bench
		int nPlayer = findBenchPlayer(player);
		
		benchPlayers.remove(nPlayer);
		
		lineup.put(batpos, player);
	}
	public void addPlayerToBench(CPlayer player)
	{
		benchPlayers.add(player);
	}
	public void setLineup(SortedMap<Integer, CPlayer> lineup)
	{
		this.lineup = lineup;
	}
	public void subPlayer(CPlayer player, CPlayer benchPlayer)
	{
		if(lineup.containsValue(player))
		{
			//KeyPair kp = lineup.
			// add player to bench
			int nPlayer = findBenchPlayer(benchPlayer);
			int nPlayerToBench = findLineupPlayer(player);
			
			if(nPlayer >= 0 || nPlayerToBench < 0)
				return;
						
			lineup.remove(nPlayerToBench);
			lineup.put(nPlayerToBench, benchPlayer);
			
			benchPlayers.remove(nPlayer);
			benchPlayers.add(player);
			// take bench player and put him on line-up
			
			
		}
	}
	public void subPlayer(Integer batpos, CPlayer benchPlayer)
	{
		if(lineup.containsKey(batpos))
		{
			CPlayer player = lineup.get(batpos);

			// add player to bench
			benchPlayers.add(player);
			
			lineup.remove(batpos);
			lineup.put(batpos, benchPlayer);

			// remove the bench player
			benchPlayers.remove(player);
			//sortLineup();
		}
		else
		{
			Log.d(TAG, "Lineup player does not exist");
		}
	}
	public void setBench(List<CPlayer> players)
	{
		this.benchPlayers = players;
	}
	private int findBenchPlayer(CPlayer player)
	{
		int index = 0;
		for(CPlayer p : benchPlayers)
		{
			if(p.equals(player))
			{
				return index;
			}

			index++;
		}
		
		return -1;
	}
	private int findLineupPlayer(CPlayer player)
	{
		for (SortedMap.Entry<Integer, CPlayer> entry : lineup.entrySet())
		{
			if(entry.getValue().equals(player))
			{
				return entry.getKey();
			}
		}
		
		return -1;
	}
}
