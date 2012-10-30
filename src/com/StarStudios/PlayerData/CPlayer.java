package com.StarStudios.PlayerData;

import java.util.ArrayList;
import java.util.List;

import com.StarStudios.TeamData.CTeam;

@SuppressWarnings("serial")
public class CPlayer implements IPerson
{
	private CPerson person;
	private List<EPositions> positions;
	private CStats stats;
	private CTeam team;
	
	public CPlayer(String firstname, String lastname)
	{
		person = new CPerson();
		positions = new ArrayList<EPositions>();
		person.setFirstName(firstname);
		person.setLastName(lastname);
		stats = new CStats();
	}
	public CPlayer(String firstname, String lastname, CStats stats)
	{
		person = new CPerson();
		positions = new ArrayList<EPositions>();
		person.setFirstName(firstname);
		person.setLastName(lastname);
		this.stats = stats;
		//stats = new CStats();
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CPlayer)
		{
			boolean bReturn = ((CPlayer) o).getFirstName().contentEquals(getFirstName());
			if(!bReturn)
				return bReturn;
			bReturn = ((CPlayer) o).getLastName().contentEquals(getLastName());
			if(!bReturn)
				return bReturn;
			else
				return bReturn;
		}
		
		return super.equals(o);
	}
	@Override
	public String getFirstName() 
	{
		return person.getFirstName();
	}
	@Override
	public String getLastName()
	{
		return person.getLastName();
	}

	public EPositions getMainPosition()
	{
		return positions.get(0);                                                                   
	}
	public List<EPositions> getAllPositions()
	{
		return positions;
	}
	/*
	 * Add position
	 * parameter position
	 * Add main position first
	 */
	public void addPosition(EPositions position)
	{
		//Add the main position first
		this.positions.add(position);
	}

	@Override
	public String toString()
	{
		return person.getFullName();
	}
	
	@Override
	public void setFirstName(String firstname) 
	{
		person.setFirstName(firstname);
	}
	@Override
	public void setLastName(String lastname)
	{
		person.setLastName(lastname);
	}
	public void setStats(CStats stats)
	{
		this.stats = stats;
	}
	public CStats getStats()
	{		
		return stats;
	}
	public CTeam getTeam()
	{
		return team;
	}
	public void setTeam(CTeam team)
	{
		this.team = team;
	}
	@Override
	public String getFullName()
	{
		// TODO Auto-generated method stub
		return person.getFullName();
	}
}
