package com.mclich.epamproject.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class User implements Serializable
{
	public enum Role
	{
		CLIENT, ADMIN;
		
		public static Role byInteger(int value)
		{
			if(value==Role.CLIENT.ordinal()+1) return Role.CLIENT;
			else if(value==Role.ADMIN.ordinal()+1) return Role.ADMIN;
			else throw new IllegalArgumentException("No such user role enum");
		}

		@Override
		public String toString()
		{
			return Character.toUpperCase(this.name().charAt(0))+this.name().substring(1).toLowerCase();
		}
	}
	
	private String login;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private boolean gender;
	private List<Order> orders;
	private List<Role> roles;
	
	public User(String login, String password, String email, String firstName, String lastName, boolean gender) throws IllegalArgumentException
	{
		this.login=login;
		this.password=password;
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		this.gender=gender;
		this.orders=new ArrayList<>();
		this.roles=new ArrayList<>();
		this.roles.add(Role.CLIENT);
	}
	
	public String getLogin()
	{
		return this.login;
	}

	public void setLogin(String login) throws IllegalArgumentException
	{
		this.login=login;
	}
	
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}

	public boolean getGender()
	{
		return this.gender;
	}

	public void setGender(boolean gender)
	{
		this.gender=gender;
	}

	public List<Order> getOrders()
	{
		return this.orders;
	}

	public void addOrder(Order order)
	{
		if(order==null) throw new NullPointerException("Order cannot be null");
		this.orders.add(order);
	}
	
	public List<Role> getRoles()
	{
		return this.roles;
	}
	
	public String getRolesString()
	{
		return this.roles.stream().map(r->r.toString()).collect(Collectors.joining(", "));
	}
	
	public void addRole(Role role)
	{
		if(role==null) throw new NullPointerException("Role cannot be null");
		this.roles.add(role);
	}
	
	public void deleteRole(Role role)
	{
		if(role==null) throw new NullPointerException("Role cannot be null");
		if(!this.roles.remove(role)) throw new NoSuchElementException("This user does not contain given role");
	}
}