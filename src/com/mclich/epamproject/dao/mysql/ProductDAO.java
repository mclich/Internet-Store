package com.mclich.epamproject.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mclich.epamproject.dao.DAOFactory;
import com.mclich.epamproject.dao.DataAccessObject;
import com.mclich.epamproject.dao.DAOFactory.Type;
import com.mclich.epamproject.entity.Product;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.*;
import com.mclich.epamproject.filter.LogFilter;

public class ProductDAO implements DataAccessObject<Product>
{
	private static final ProductDAO INSTANCE=new ProductDAO();
	
	private DAOFactory factory;
	
	private ProductDAO()
	{
		this.factory=DAOFactory.getInstance(Type.MY_SQL);
	}
	
	public static ProductDAO getInstance()
	{
		return ProductDAO.INSTANCE;
	}
	
	@Override
	public void create(Product product) throws CreateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			boolean flag=false;
			con=this.factory.getConnection();
			if(product.getId()==-1) ps=con.prepareStatement("INSERT INTO internet_store.product (name, price, addition_date, product_category_id) VALUES (?, ?, ?, ?);");
			else if(product.getId()<=this.getLastInsertId()) throw new IllegalStateException("Product ID value is not unique");
			else
			{
				flag=true;
				ps=con.prepareStatement("INSERT INTO internet_store.product (id, name, price, addition_date, product_category_id) VALUES (?, ?, ?, ?, ?);");
			}
			int k=1;
			if(flag) ps.setInt(k++, product.getId());
			ps.setString(k++, product.getName());
			ps.setFloat(k++, product.getPrice());
			ps.setDate(k++, Date.valueOf(product.getAdditionDate()));
			ps.setInt(k++, CategoryDAO.getInstance().getId(product.getCategory()));
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not create a new product", exc);
			this.factory.rollback(con, new CreateException());
			throw new CreateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new CreateException());
		}
	}

	@Override
	public void delete(Product product) throws DeleteException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("DELETE FROM internet_store.product WHERE id=?;");
			ps.setInt(1, this.getId(product));
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not delete a product", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get a product ID", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new DeleteException());
		}
	}

	@Override
	public void update(int id, Product toUpdate) throws UpdateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("UPDATE internet_store.product SET name=?, price=?, product_category_id=? WHERE id=?;");
			ps.setString(1, toUpdate.getName());
			ps.setFloat(2, toUpdate.getPrice());
			//ps.setDate(3, Date.valueOf(toUpdate.getAdditionDate()));
			ps.setInt(3, CategoryDAO.getInstance().getId(toUpdate.getCategory()));
			ps.setInt(4, id);
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not update a product", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get a category from product", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new UpdateException());
		}
	}

	@Override
	public Product get(int id) throws GetException, CNAException
	{
		Product result=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id, name, price, addition_date, product_category_id FROM internet_store.product WHERE id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.first())
			{
				result=new Product(rs.getString(2), rs.getFloat(3), rs.getDate(4).toLocalDate(), CategoryDAO.getInstance().get(rs.getInt(5)));
				result.setId(rs.getInt(1));
			}
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not get a product", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get a category from product", exc);
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
	public int getId(Product product) throws GetException, CNAException
	{
		if(product.getId()!=-1) return product.getId();
		int result=-1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.product WHERE name=? AND price=? AND addition_date=? AND product_category_id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, product.getName());
			ps.setFloat(2, product.getPrice());
			ps.setDate(3, Date.valueOf(product.getAdditionDate()));
			ps.setInt(4, CategoryDAO.getInstance().getId(product.getCategory()));
			rs=ps.executeQuery();
			if(rs.first()) result=rs.getInt(1);
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not get a product ID", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get a category from product", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		if(result==-1) throw new IllegalArgumentException("No products were found in DB by product characteristics");
		return result;
	}
	
	public int getLastInsertId() throws GetException, CNAException
	{
		int result=-1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT LAST_INSERT_ID() FROM internet_store.product;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			if(rs.first()) result=rs.getInt(1);
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not get last inserted ID value", exc);
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
	public List<Product> getAll() throws GetException, CNAException
	{
		List<Product> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.product;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(this.get(rs.getInt(1)));
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not get a list of products", exc);
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