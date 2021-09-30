package com.mclich.epamproject.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.DAOFactory;
import com.mclich.epamproject.dao.DataAccessObject;
import com.mclich.epamproject.dao.DAOFactory.Type;
import com.mclich.epamproject.entity.Order;
import com.mclich.epamproject.entity.Product;
import com.mclich.epamproject.entity.Order.Status;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.*;

public class OrderDAO implements DataAccessObject<Order>
{
	private static final OrderDAO INSTANCE=new OrderDAO();
	
	private DAOFactory factory;
	
	private OrderDAO()
	{
		this.factory=DAOFactory.getInstance(Type.MY_SQL);
	}
	
	public static OrderDAO getInstance()
	{
		return OrderDAO.INSTANCE;
	}
	
	@Override
	public void create(Order order) throws CreateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			boolean flag=false;
			con=this.factory.getConnection();
			if(order.getId()==-1) ps=con.prepareStatement("INSERT INTO internet_store.order (status, user_id) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
			else if(order.getId()<=this.getAutoIncrement()) throw new IllegalStateException("Order ID value is not unique");
			else
			{
				flag=true;
				ps=con.prepareStatement("INSERT INTO internet_store.order (id, status, user_id) VALUES (?, ?, ?);");
			}
			int k=1;
			if(flag) ps.setInt(k++, order.getId());
			ps.setInt(k++, order.getStatus().ordinal());
			ps.setInt(k++, UserDAO.getInstance().getId(order.getOrderer()));
			ps.executeUpdate();
			int orderId=order.getId();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) orderId=rs.getInt(1);
			ps.close();
			for(Product product:order.getProducts().keySet())
			{
				ps=con.prepareStatement("INSERT INTO internet_store.order_products (order_id, product_id, count) VALUES (?, ?, ?);");
				ps.setInt(1, orderId);
				ps.setInt(2, product.getId());
				ps.setInt(3, order.getProducts().get(product));
				ps.executeUpdate();
				ps.close();
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			Constants.LOGGER.error("Could not create a new order", exc);
			this.factory.rollback(con, new CreateException());
			throw new CreateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new CreateException());
		}
	}

	@Override
	public void delete(Order order) throws DeleteException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			int orderId=this.getId(order);
			ps=con.prepareStatement("DELETE FROM internet_store.order WHERE id=?;");
			ps.setInt(1, orderId);
			ps.executeUpdate();
			ps.close();
			ps=con.prepareStatement("DELETE FROM internet_store.order_products WHERE order_id=?;");
			ps.setInt(1, orderId);
			ps.executeUpdate();
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not delete an order", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		catch(GetException exc)
		{
			Constants.LOGGER.error("Could not get an order ID", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new DeleteException());
		}
	}
	
	@Override
	public void update(int id, Order toUpdate) throws UpdateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("UPDATE internet_store.order SET status=?, user_id=? WHERE id=?;");
			ps.setInt(1, toUpdate.getStatus().ordinal());
			ps.setInt(2, UserDAO.getInstance().getId(toUpdate.getOrderer()));
			ps.setInt(3, id);
			ps.executeUpdate();
			con.commit();
			this.factory.close(ps, con, new UpdateException());
			con=this.factory.getConnection();
			for(Product product:toUpdate.getProducts().keySet())
			{
				ps=con.prepareStatement("UPDATE internet_store.order_products SET product_id=? WHERE order_id=?;");
				ps.setInt(1, ProductDAO.getInstance().getId(product));
				ps.setInt(2, id);
				ps.executeUpdate();
				ps.close();
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			Constants.LOGGER.error("Could not update an order", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new UpdateException());
		}
	}

	@Override
	public Order get(int id) throws GetException, CNAException
	{
		Order result=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id, status, user_id FROM internet_store.order WHERE id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.first())
			{
				result=new Order(UserDAO.getInstance().get(rs.getInt(3)), new HashMap<>());
				result.setId(rs.getInt(1));
				result.setStatus(Status.byInteger(rs.getInt(2)));
				PreparedStatement ps2=con.prepareStatement("SELECT product_id, count FROM internet_store.order_products WHERE order_id=?;");
				ps2.setInt(1, id);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{
					result.addProducts(ProductDAO.getInstance().get(rs2.getInt(1)), rs2.getInt(2));
				}
				rs2.close();
				ps2.close();
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			Constants.LOGGER.error("Could not get a product", exc);
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
	public int getId(Order order) throws GetException, CNAException
	{
		if(order.getId()!=-1) return order.getId();
		int result=-1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.order WHERE status=? AND user_id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, order.getStatus().ordinal());
			ps.setInt(2, UserDAO.getInstance().getId(order.getOrderer()));
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
		catch(GetException exc)
		{
			Constants.LOGGER.error("Could not get user from order", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		if(result==-1) throw new IllegalArgumentException("No orders were found in DB by order characteristics");
		return result;
	}
	
	public int getAutoIncrement() throws GetException, CNAException
	{
		int result=1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE TABLE_SCHEMA=? AND TABLE_NAME=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "internet_store");
			ps.setString(2, "order");
			rs=ps.executeQuery();
			if(rs.first()) result=rs.getInt(1);
			con.commit();
		}
		catch(SQLException exc)
		{
			Constants.LOGGER.error("Could not get last inserted ID value", exc);
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
	public List<Order> getAll() throws GetException, CNAException
	{
		List<Order> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.order;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(this.get(rs.getInt(1)));
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
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