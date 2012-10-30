package com.StarStudios.PlayerData;

@SuppressWarnings("serial")
public class CPerson implements IPerson
{
	private String firstname;
	private String lastname;
	

	@Override
	public String getFullName()
	{
		return firstname + " " + lastname;
	}
	
	@Override
	public String getFirstName()
	{
		// TODO Auto-generated method stub
		return firstname;
	}

	@Override
	public String getLastName() 
	{
		// TODO Auto-generated method stub
		return lastname;
	}

	@Override
	public void setFirstName(String firstname)
	{
		// TODO Auto-generated method stub
		this.firstname = firstname;
	}

	@Override
	public void setLastName(String lastname) 
	{
		// TODO Auto-generated method stub
		this.lastname = lastname;
	}


}
