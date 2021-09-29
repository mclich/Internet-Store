package com.mclich.epamproject.exception;

@SuppressWarnings("serial")
public class TransactionException extends RuntimeException
{
	public static class CreateException extends TransactionException
	{
		public CreateException()
		{
			super();
		}

		public CreateException(String msg)
		{
			super(msg);
		}
	}
	
	public static class DeleteException extends TransactionException
	{
		public DeleteException()
		{
			super();
		}
		
		public DeleteException(String msg)
		{
			super(msg);
		}
	}
	
	public static class UpdateException extends TransactionException
	{
		public UpdateException()
		{
			super();
		}
		
		public UpdateException(String msg)
		{
			super(msg);
		}
	}
	
	public static class GetException extends TransactionException
	{
		public GetException()
		{
			super();
		}
		
		public GetException(String msg)
		{
			super(msg);
		}
	}
	
	protected TransactionException(String msg)
	{
		super(msg);
	}

	protected TransactionException()
	{
		super();
	}
}