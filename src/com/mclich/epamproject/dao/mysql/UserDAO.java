package com.mclich.epamproject.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import com.mclich.epamproject.dao.DAOFactory;
import com.mclich.epamproject.dao.DataAccessObject;
import com.mclich.epamproject.dao.DAOFactory.Type;
import com.mclich.epamproject.entity.Order;
import com.mclich.epamproject.entity.User;
import com.mclich.epamproject.entity.Order.Status;
import com.mclich.epamproject.entity.User.Role;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.CreateException;
import com.mclich.epamproject.exception.TransactionException.DeleteException;
import com.mclich.epamproject.exception.TransactionException.GetException;
import com.mclich.epamproject.exception.TransactionException.UpdateException;
import com.mclich.epamproject.filter.LogFilter;

public class UserDAO implements DataAccessObject<User>
{
	private static final UserDAO INSTANCE=new UserDAO();
	
	private DAOFactory factory;
	
	private UserDAO()
	{
		this.factory=DAOFactory.getInstance(Type.MY_SQL);
	}
	
	public static UserDAO getInstance()
	{
		return UserDAO.INSTANCE;
	}
	
	@Override
	public void create(User user) throws CreateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("INSERT INTO internet_store.user (login, password, email, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			int k=1;
			ps.setString(k++, user.getLogin());
			ps.setString(k++, user.getPassword());
			ps.setString(k++, user.getEmail());
			ps.setString(k++, user.getFirstName());
			ps.setString(k++, user.getLastName());
			ps.setBoolean(k++, user.getGender());
			ps.executeUpdate();
			int userId=-1;
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) userId=rs.getInt(1);
			ps.close();
			for(Role role:user.getRoles())
			{
				ps=con.prepareStatement("INSERT INTO internet_store.user_roles (user_id, user_role_id) VALUES (?, ?);");
				ps.setInt(1, userId);
				ps.setInt(2, role.ordinal()+1);
				ps.executeUpdate();
				ps.close();
			}
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not create new user", exc);
			this.factory.rollback(con, new CreateException());
			throw new CreateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new CreateException());
		}
	}

	@Override
	public void delete(User user) throws DeleteException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			int userId=this.getId(user);
			ps=con.prepareStatement("DELETE FROM internet_store.user_roles WHERE user_id=?;");
			ps.setInt(1, userId);
			ps.executeUpdate();
			ps.close();
			ps=con.prepareStatement("DELETE FROM internet_store.user WHERE id=?;");
			ps.setInt(1, userId);
			ps.executeUpdate();
			con.commit();
			OrderDAO oDAO=OrderDAO.getInstance();
			for(Order order:user.getOrders())
			{
				oDAO.delete(order);
			}
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not delete user", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get user ID", exc);
			this.factory.rollback(con, new DeleteException());
			throw new DeleteException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new DeleteException());
		}
	}

	@Override
	public void update(int id, User toUpdate) throws UpdateException, CNAException
	{
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("UPDATE internet_store.user SET login=?, password=?, email=?, first_name=?, last_name=?, gender=? WHERE id=?;");
			int k=1;
			ps.setString(k++, toUpdate.getLogin());
			ps.setString(k++, toUpdate.getPassword());
			ps.setString(k++, toUpdate.getEmail());
			ps.setString(k++, toUpdate.getFirstName());
			ps.setString(k++, toUpdate.getLastName());
			ps.setBoolean(k++, toUpdate.getGender());
			ps.setInt(k++, id);
			ps.executeUpdate();
			con.commit();
			this.factory.close(ps, con, new UpdateException());
			con=this.factory.getConnection();
			for(Role role:toUpdate.getRoles())
			{
				ps=con.prepareStatement("UPDATE internet_store.user_roles SET user_role_id=? WHERE user_id=?;");
				ps.setInt(1, role.ordinal()+1);
				ps.setInt(2, id);
				ps.executeUpdate();
				ps.close();
			}
			con.commit();
			OrderDAO oDAO=OrderDAO.getInstance();
			for(Order order:toUpdate.getOrders())
			{
				oDAO.update(oDAO.getId(order), order);
			}
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not update user", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		catch(GetException exc)
		{
			LogFilter.getLogger().error("Could not get order ID from user orders", exc);
			this.factory.rollback(con, new UpdateException());
			throw new UpdateException(exc.getMessage());
		}
		finally
		{
			this.factory.close(ps, con, new UpdateException());
		}
	}

	@Override
	public User get(int id) throws GetException, CNAException
	{
		User result=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT login, password, email, first_name, last_name, gender FROM internet_store.user WHERE id=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.first())
			{
				result=new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
				result.getRoles().clear();
				PreparedStatement ps2=con.prepareStatement("SELECT user_role_id FROM internet_store.user_roles WHERE user_id=?;");
				ps2.setInt(1, id);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{
					result.addRole(Role.byInteger(rs2.getInt(1)));
				}
				rs2.close();
				ps2.close();
				PreparedStatement ps3=con.prepareStatement("SELECT id, status FROM internet_store.order WHERE user_id=?;");
				ps3.setInt(1, id);
				ResultSet rs3=ps3.executeQuery();
				ProductDAO pDAO=ProductDAO.getInstance();
				while(rs3.next())
				{
					Order order=new Order(result, new HashMap<>());
					order.setId(rs3.getInt(1));
					order.setStatus(Status.byInteger(rs3.getInt(2)));
					PreparedStatement ps4=con.prepareStatement("SELECT product_id, count FROM internet_store.order_products WHERE order_id=?;");
					ps4.setInt(1, order.getId());
					ResultSet rs4=ps4.executeQuery();
					while(rs4.next())
					{
						order.addProducts(pDAO.get(rs4.getInt(1)), rs4.getInt(2));
					}
					rs4.close();
					ps4.close();
					result.addOrder(order);
				}
				rs3.close();
				ps3.close();
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not get user", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		return result;
	}
	
	public User get(String login) throws GetException, CNAException
	{
		User result=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.user WHERE login=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, login);
			rs=ps.executeQuery();
			if(rs.first()) result=this.get(rs.getInt(1));
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not get user", exc);
			this.factory.rollback(con, new GetException());
			throw new GetException(exc.getMessage());
		}
		finally
		{
			this.factory.close(rs, ps, con, new GetException());
		}
		return result;
	}
	
	public User get(String login, String password) throws GetException, CNAException
	{
		User result=null;
		result=this.get(login);
		if(result==null) throw new GetException("Such user does not exist");
		else if(!result.getPassword().equals(DigestUtils.sha256Hex(password))) throw new GetException("Wrong password, try again");
		return result;
	}
	
	@Override
	public int getId(User user) throws GetException, CNAException
	{
		int result=-1;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.user WHERE login=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, user.getLogin());
			rs=ps.executeQuery();
			if(rs.first()) result=rs.getInt(1);
			con.commit();
		}
		catch(SQLException exc)
		{
			LogFilter.getLogger().error("Could not get user ID", exc);
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
	public List<User> getAll() throws GetException, CNAException
	{
		List<User> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT id FROM internet_store.user;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(this.get(rs.getInt(1)));
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not get a list of users", exc);
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
	
	public List<String> getAllLogins()
	{
		List<String> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT login FROM internet_store.user;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(rs.getString(1));
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not get a list of user logins", exc);
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
	
	public List<String> getAllEmails()
	{
		List<String> result=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			con=this.factory.getConnection();
			ps=con.prepareStatement("SELECT email FROM internet_store.user;");
			rs=ps.executeQuery();
			while(rs.next())
			{
				result.add(rs.getString(1));
			}
			con.commit();
		}
		catch(SQLException | GetException exc)
		{
			LogFilter.getLogger().error("Could not get a list of user emails", exc);
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