package com.mclich.epamproject.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.DAOFactory;
import com.mclich.epamproject.dao.DAOFactory.Type;
import com.mclich.epamproject.dao.DataAccessObject;
import com.mclich.epamproject.entity.Category;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.*;

public class CategoryDAO implements DataAccessObject<Category>
{
	private static final CategoryDAO INSTANCE=new CategoryDAO();
	
	private DAOFactory factory;
	
	private CategoryDAO()
	{
		this.factory=DAOFactory.getInstance(Type.MY_SQL);
	}
	
	public static CategoryDAO getInstance()
	{
		return CategoryDAO.INSTANCE;
	}

	@Override
	public void create(Category category) throws CreateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("INSERT INTO internet_store.product_category (name) VALUES (?);");
			ps.setString(1, category.getName());
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not create a new category", exc);
			this.factory.rollback(con, new CreateException());
			throw new CreateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new CreateException());
		}
	}

	@Override
	public void delete(Category category) throws DeleteException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("DELETE FROM internet_store.product_category WHERE id=?;");
			ps.setInt(1, this.getId(category));
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not delete a category", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		catch(GetException exc)
		{
			Constants.LOGGER.error("Could not get category ID", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new DeleteException());
		}
	}

	@Override
	public void update(int id, Category toUpdate) throws UpdateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("UPDATE internet_store.product_category SET name=? WHERE id=?;");
			ps.setString(1, toUpdate.getName());
			ps.setInt(2, id);
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not update a category", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new UpdateException());
		}
	}

	@Override
	public Category get(int id) throws GetException, CNAException
	{
		Category result=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT name FROM internet_store.product_category WHERE id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.first()) result=new Category(rs.getString(1));
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not get a category", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		return result;
	}

	@Override
	public int getId(Category category) throws GetException, CNAException
	{
		int result=-1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.product_category WHERE name=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, category.getName());
			rs=ps.executeQuery();
			if(rs.first()) result=rs.getInt(1);
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not get a category ID", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		return result;
	}
	
	@Override
	public List<Category> getAll() throws GetException, CNAException
	{
		List<Category> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT name FROM internet_store.product_category;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(new Category(rs.getString(1)));
			}
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not get a list of categories", exc);
			result=new ArrayList<>();
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		return result;
	}
}