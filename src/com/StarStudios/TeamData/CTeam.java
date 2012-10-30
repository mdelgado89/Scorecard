package com.StarStudios.TeamData;

import java.util.ArrayList;
import java.util.List;

import com.StarStudios.PlayerData.CPlayer;
import com.StarStudios.UserData.CUser;

@SuppressWarnings("serial")
public class CTeam implements ITeam
{
	private String teamName;
	private boolean isBaseballTeam = true;
	private List<CPlayer> players;
	private CRecord record;
	private CLineup lineup;
	private CUser user;
	public CTeam()
	{
		teamName = "Unnamed team";
		players = new ArrayList<CPlayer>();
		record = new CRecord();
		lineup = new CLineup();
		
	}
	public CTeam(String teamName)
	{
		this.teamName = teamName;
		players = new ArrayList<CPlayer>();
		record = new CRecord();
		lineup = new CLineup();
		
	}
	public CUser getUser()
	{
		return user;
	}
	public void setUser(CUser user)
	{
		this.user = user;
	}
	public String getTeamName()
	{
		return teamName;
	}
	
	@Override
	public List<CPlayer> getPlayers() 
	{
		// TODO Auto-generated method stub
		return players;
	}

	@Override
	public void addPlayer(CPlayer player)
	{
		// TODO Auto-generated method stub
		player.setTeam(this);
		
		players.add(player);
		lineup.addPlayerToBench(player);
	}
	public void setPlayer(List<CPlayer> players)
	{
		this.players = players;
	}
	@Override
	public CRecord getRecord() 
	{
		// TODO Auto-generated method stub
		return record;
	}
	public void setRecord(CRecord record)
	{
		this.record = record;
	}
	public CLineup getLineup()
	{
		return lineup;
	}
	@Override
	public String toString()
	{
		return teamName;
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CTeam)
		{
			return ((CTeam) o).getTeamName().equals(teamName);
		}
		return false;
		
	}
	public boolean getIsBaseballTeam()
	{
		return isBaseballTeam;
	}
	public void setIsBaseballTeam(boolean isBaseballTeam)
	{
		this.isBaseballTeam = isBaseballTeam;
	}
	public void removePlayer(CPlayer player)
	{
		players.remove(player);
	}
}
