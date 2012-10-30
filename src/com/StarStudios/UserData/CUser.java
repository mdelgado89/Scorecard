package com.StarStudios.UserData;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.StarStudios.PlayerData.CPerson;
import com.StarStudios.PlayerData.IPerson;
import com.StarStudios.TeamData.CTeam;

@SuppressWarnings("serial")
public class CUser implements IPerson, Serializable
{
	private CPerson person;

	private String email;
	private transient String password = new String();

	private HashSet<CTeam> teams;

	public CUser()
	{
		person = new CPerson();
		teams = new HashSet<CTeam>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean add(CTeam object)
			{
				// TODO Auto-generated method stub
				if (contains(object))
				{
					return false;
				}
				return super.add(object);
			}

			@Override
			public boolean contains(Object object)
			{
				if (object instanceof CTeam)
				{
					for (CTeam team : teams)
					{
						if (team.getTeamName().contentEquals(
								((CTeam) object).getTeamName()))
						{
							return true;
						}
					}
				}
				// TODO Auto-generated method stub
				return false;
			}
		};
		email = "Anonymous@anonymous.com";
		password = "123456";
	}

	public CUser(String firstname, String lastname, String email,
			String password)
	{
		person = new CPerson();
		teams = new HashSet<CTeam>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean add(CTeam object)
			{
				// TODO Auto-generated method stub
				if (contains(object))
				{
					return false;
				}
				return super.add(object);
			}

			@Override
			public boolean contains(Object object)
			{
				if (object instanceof CTeam)
				{
					for (CTeam team : teams)
					{
						if (team.getTeamName().contentEquals(((CTeam) object).getTeamName()))
						{
							return true;
						}
					}
				}
				// TODO Auto-generated method stub
				return false;
			}

		};

		person.setFirstName(firstname);
		person.setLastName(lastname);
		this.email = email;
		this.password = password;
	}

	public boolean addTeam(CTeam team)
	{
		if (!teams.add(team))
		{
			return false;
		}
		else
		{
			team.setUser(this);
		}
		
		return true;
	}

	public Set<CTeam> getTeams()
	{
		return teams;
	}

	@Override
	public String getFirstName()
	{
		// TODO Auto-generated method stub
		return person.getFirstName();
	}

	@Override
	public String getLastName()
	{
		// TODO Auto-generated method stub
		return person.getLastName();
	}

	@Override
	public void setFirstName(String firstname)
	{
		// TODO Auto-generated method stub
		person.setFirstName(firstname);

	}

	@Override
	public void setLastName(String lastname)
	{
		// TODO Auto-generated method stub
		person.setLastName(lastname);
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String getFullName()
	{
		// TODO Auto-generated method stub
		return person.getFullName();
	}
}
