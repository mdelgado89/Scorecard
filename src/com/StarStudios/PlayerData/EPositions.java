package com.StarStudios.PlayerData;

import java.io.Serializable;

public enum EPositions implements Serializable
{

	PITCHER("P", 1),
	CATCHER("C", 2),
	FIRST_BASE("1B", 3),
	SECOND_BASE("2B", 4),
	THIRD_BASE("3B", 5),
	SHORT_STOP("SS", 6),
	LEFT_FIELD("LF", 7),
	CENTER_FIELD("CF", 8),
	SHORT_FIELD("SF", 10),
	RIGHT_FIELD("RF", 9);
	
	String positionName;
	int positionNumber;
	
	EPositions(String positionName, int positionNumber)
	{
		this.positionName = positionName;
		this.positionNumber = positionNumber;
	}
	public static EPositions getPosition(String position)
	{
		EPositions pos = null;
		
		for(EPositions pos1 : EPositions.values())
		{
			if(position.contentEquals(pos1.toString()))
			{
				pos = pos1;
			}
		}
		
		return pos;
	}
	public String getPositionName()
	{
		return positionName;
	}
	
	public int getPositionNumber()
	{
		return positionNumber;
	}
	
	@Override
	public String toString()
	{
		
		return positionName;
	}
}
