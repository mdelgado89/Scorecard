package com.StarStudios.TeamData;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CRecord implements Serializable
{
	private int wins;
	private int losses;
	private int noDecisions;
	
	
	public CRecord()
	{
		wins = 0;
		losses = 0;
		noDecisions = 0;
	}
	public int getWins()
	{
		return wins;
	}
	public void setWins(int wins)
	{
		this.wins = wins;
	}
	public void setLosses(int losses)
	{
		this.losses = losses;
	}
	public void setNoDecisions(int noDecisions)
	{
		this.noDecisions = noDecisions;
	}
	public int getLosses()
	{
		return losses;
	}
	public int getNoDecisions()
	{
		return noDecisions;
	}
	
	public void addWin()
	{
		wins++;
	}
	public void addLoss()
	{
		losses++;
	}
	public void addNoDecision()
	{
		noDecisions++;
	}
}
