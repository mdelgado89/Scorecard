package com.StarStudios.TeamData;

import java.io.Serializable;
import java.util.List;

import com.StarStudios.PlayerData.CPlayer;

public interface ITeam extends Serializable
{
	List<CPlayer> getPlayers();
	void addPlayer(CPlayer player);

	boolean getIsBaseballTeam();
	void setIsBaseballTeam(boolean isBaseballTeam);
	CRecord getRecord();
}
