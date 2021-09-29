package com.mclich.epamproject.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable
{
	private String name;
	
	public Category(String name)
	{
		this.name=name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name=name;
	}
}