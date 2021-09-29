package com.mclich.epamproject.exception;

/** Connection Is Not Available exception */

@SuppressWarnings("serial")
public class CNAException extends RuntimeException
{
	public CNAException(String msg)
	{
		super(msg);
	}
}