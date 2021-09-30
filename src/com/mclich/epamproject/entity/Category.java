package com.mclich.epamproject.entity;

import java.io.Serializable;
import com.mclich.epamproject.Constants;

@SuppressWarnings("serial")
public class Category implements Serializable
{
	private String name;
	
	public Category(String name)
	{
		this.name=name;
		Constants.LOGGER.info("Entity created: "+this.toString());
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	@Override
	public String toString()
	{
		return "Category [name="+this.name+"]";
	}
}