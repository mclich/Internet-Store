package com.mclich.epamproject.entity;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class Order implements Serializable
{
	public enum Status
	{
		PROCESSED, PAID, CANCELLED;

		public static Status byInteger(int value)
		{
			if(value==Status.PROCESSED.ordinal()) return Status.PROCESSED;
			else if(value==Status.PAID.ordinal()) return Status.PAID;
			else if(value==Status.CANCELLED.ordinal()) return Status.CANCELLED;
			else throw new IllegalArgumentException("No such order status enum");
		}
		
		@Override
		public String toString()
		{
			return Character.toUpperCase(this.name().charAt(0))+this.name().substring(1).toLowerCase();
		}
	}
	
	private int id;
	private User orderer;
	private Map<Product, Integer> products;
	private Status status;
	
	public Order(User orderer, Map<Product, Integer> products)
	{
		if(products.values().stream().anyMatch(count->count<=0)) throw new IllegalArgumentException("Cannot add less that 1 product to order");
		this.id=-1;
		this.orderer=orderer;
		this.products=products;
		this.status=Status.PROCESSED;
	}
	
	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		if(this.id!=-1) throw new IllegalStateException("Order ID has been already set");
		this.id=id;
	}

	public User getOrderer()
	{
		return this.orderer;
	}

	public void setOrderer(User orderer)
	{
		if(orderer==null) throw new NullPointerException("Orderer cannot be null");
		this.orderer=orderer;
	}

	public Map<Product, Integer> getProducts()
	{
		return this.products;
	}

	public void setProducts(Map<Product, Integer> products)
	{
		if(products.values().stream().anyMatch(count->count<=0)) throw new IllegalArgumentException("Cannot set less that 1 product in order");
		this.products=products;
	}

	public void addProducts(Product product, int count)
	{
		if(product==null) throw new NullPointerException("Product cannot be null");
		if(count<=0) throw new IllegalArgumentException("Cannot add less that 1 product to order");
		this.products.put(product, count);
	}
	
	public Status getStatus()
	{
		return this.status;
	}

	public void setStatus(Status status)
	{
		this.status=status;
	}
}