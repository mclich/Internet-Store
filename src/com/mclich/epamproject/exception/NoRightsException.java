package com.mclich.epamproject.exception;

@SuppressWarnings("serial")
public class NoRightsException extends RuntimeException
{
	public NoRightsException()
	{
		this("You have no rights to make this action");
	}
	
	public NoRightsException(String msg)
	{
		super(msg);
	}
}