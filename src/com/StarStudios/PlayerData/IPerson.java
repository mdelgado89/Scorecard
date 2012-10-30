package com.StarStudios.PlayerData;

import java.io.Serializable;

public interface IPerson extends Serializable
{
	String getFirstName();
	String getLastName();
	String getFullName();
	void setFirstName(String firstname);
	void setLastName(String lastname);
}
