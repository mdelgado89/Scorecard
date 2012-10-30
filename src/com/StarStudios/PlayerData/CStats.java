package com.StarStudios.PlayerData;

import java.io.Serializable;

 
@SuppressWarnings("serial")
public class CStats implements Serializable
{
	
    static 
    {
        System.loadLibrary("scorecard");
    }
	
	private int atbats;
	private int hits;

	/*
	 * Stats in the database
	 */

	private int singles;
	private int doubles;
	private int triples;
	private int homeruns;
	private int strikeouts;
	private int groundouts;
	private int flyouts;
	private int sacflys;
	private int sacbunts;
	private int rbis;
	private int walks;

	private float[] Averages;
	
	
	public float[] getInArray()
	{
		return new float[]{getAvg(), getOBP(), getSlugging(), getOPS(), getHits(), getHomeruns(), getRbis()};
	}
	
	public int getSingles() 
	{
		return singles;
	}
	public void setSingles(int singles)
	{
		this.singles = singles;
	}
	public int getDoubles()
	{
		return doubles;
	}
	public void setDoubles(int doubles) 
	{
		this.doubles = doubles;
	}
	public int getTriples() 
	{
		return triples;
	}
	public void setTriples(int triples) 
	{
		this.triples = triples;
	}
	public int getHomeruns() 
	{
		return homeruns;
	}
	public void setHomeruns(int homeruns)
	{
		this.homeruns = homeruns;
	}
	public int getStrikeouts() 
	{
		return strikeouts;
	}
	public void setStrikeouts(int strikeouts)
	{
		this.strikeouts = strikeouts;
	}
	public int getGroundouts() 
	{
		return groundouts;
	}
	public void setGroundouts(int groundouts) 
	{
		this.groundouts = groundouts;
	}
	public int getFlyouts() 
	{
		return flyouts;
	}
	public void setFlyouts(int flyouts) 
	{
		this.flyouts = flyouts;
	}
	public int getSacflys() 
	{
		return sacflys;
	}
	public void setSacflys(int sacflys)
	{
		this.sacflys = sacflys;
	}
	public int getSacBunts() 
	{
		return sacbunts;
	}
	public void setSacBunts(int sacbunts)
	{
		this.sacbunts = sacbunts;
	}
	public int getRbis() 
	{
		return rbis;
	}
	public void setRbis(int rbis) 
	{
		this.rbis = rbis;
	}
	public int getWalks() 
	{
		return walks;
	}
	public void setWalks(int walks)
	{
		this.walks = walks;
	}
	public int getAtbats() 
	{
		return atbats;
	}
	public void setAtbats(int atbats) 
	{
		this.atbats = atbats;
	}
	public int getHits() 
	{
		return hits;
	}
	public void setHits(int hits) 
	{
		this.hits = hits;
	}
	public float getAvg()
	{
		if(Averages != null && Averages.length > 0)
			return Averages[0];
		else return 0;
	}
	public float getOBP()
	{
		if(Averages != null && Averages.length > 0)
			return Averages[1];
		else return 0;
	}
	public float getOPS()
	{
		if(Averages != null && Averages.length > 0)
			return Averages[3];
		else return 0;
	}
	public float getSlugging()
	{
		if(Averages != null && Averages.length > 0)
			return Averages[2];
		else return 0;
	}
	public float getTotalBases()
	{
		if(Averages != null && Averages.length > 0)
			return Averages[4];
		else return 0;
	}
	public void calculateStats()
	{
		Averages = Calculate(new int[]{atbats, hits, singles, doubles, triples, homeruns, walks, sacflys, sacbunts});
	}
	private native float[] Calculate(int[] stats);
}
