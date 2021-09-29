package com.mclich.epamproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException;
import com.mclich.epamproject.exception.TransactionException.GetException;
import com.mclich.epamproject.filter.LogFilter;

public class DAOFactory
{
	public enum Type
	{
		MY_SQL; /*ORALCE, CLOUDSCAPE, SYBASE;*/
	}
	
	private static final Map<Type, DAOFactory> INSTANCES=new HashMap<>();
	private static final Map<Type, List<String>> ARGUMENTS=new HashMap<>();
	
	static
	{
		DAOFactory.INSTANCES.put(Type.MY_SQL, new DAOFactory(Type.MY_SQL));
		DAOFactory.ARGUMENTS.put(Type.MY_SQL, Arrays.asList("jdbc:mysql://localhost:3306/internet_store", "root", "!n3F4HvKuGLaRGxtK"));
	}
	
	private Type type;
	
	private DAOFactory(Type type)
	{
		this.type=type;
	}
	
	public static synchronized DAOFactory getInstance(Type type)
	{
		if(type==null) throw new NullPointerException("Type cannot be null");
		return DAOFactory.INSTANCES.get(type);
	}
	
	public <E extends TransactionException> void rollback(Connection con, E exc) throws E
	{
		try
		{
			con.rollback();
		}
		catch(SQLException e)
		{
			LogFilter.getLogger().error("Could not rollback connection", e);
			throw exc;
		}
	}
	
	public <E extends TransactionException> void close(Connection con, E exc) throws E
	{
		try
		{
			if(con!=null&&!con.isClosed()) con.close();
		}
		catch(SQLException e)
		{
			LogFilter.getLogger().error("Could not close connection", e);
			throw exc;
		}
	}
	
	public <E extends TransactionException> void close(PreparedStatement ps, Connection con, E exc) throws E
	{
		try
		{
			if(ps!=null&&!ps.isClosed()) ps.close();
			this.close(con, exc);
		}
		catch(SQLException e)
		{
			LogFilter.getLogger().error("Could not close statement or connection", e);
			throw exc;
		}
	}
	
	public void close(ResultSet rs, PreparedStatement ps, Connection con, GetException exc) throws GetException
	{
		try
		{
			if(rs!=null&&!rs.isClosed()) rs.close();
			this.close(ps, con, exc);
		}
		catch(SQLException e)
		{
			LogFilter.getLogger().error("Could not close result set, statement or connection", e);
			throw exc;
		}
	}
	
	public Connection getConnection() throws CNAException
	{
		List<String> args=DAOFactory.ARGUMENTS.get(this.type);
		Connection con=null;
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con=DriverManager.getConnection(args.get(0), args.get(1), args.get(2));
			con.setAutoCommit(false);
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Connection is not available", exc);
			throw new CNAException("Connection is not available: "+exc.getMessage());
		}
		return con;
	}
}