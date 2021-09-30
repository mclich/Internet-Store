package com.mclich.epamproject.entity;

import java.io.Serializable;
import java.time.LocalDate;
import com.mclich.epamproject.Constants;

@SuppressWarnings("serial")
public class Product implements Serializable
{
	private int id;
	private String name;
	private float price;
	private LocalDate additionDate;
	private Category category;
	
	public Product(String name, float price, LocalDate additionDate, Category category)
	{
		if(price<=0F) throw new IllegalArgumentException("Cannot create free product");
		this.id=-1;
		this.name=name;
		this.price=price;
		this.additionDate=additionDate;
		this.category=category;
		Constants.LOGGER.info("Entity created: "+this.toString());
	}
	
	public Product(String name, float price, Category category)
	{
		this(name, price, LocalDate.now(), category);
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		if(this.id!=-1) throw new IllegalStateException("Product ID has been already set");
		this.id=id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public float getPrice()
	{
		return this.price;
	}

	public void setPrice(float price)
	{
		if(price<=0F) throw new IllegalArgumentException("Cannot set free price");
		this.price=price;
	}

	public LocalDate getAdditionDate()
	{
		return this.additionDate;
	}

	public void setAdditionDate(LocalDate adddDate) throws IllegalArgumentException
	{
		this.additionDate=adddDate;
	}

	public Category getCategory()
	{
		return this.category;
	}

	public void setCategory(Category category)
	{
		this.category=category;
	}

	@Override
	public String toString()
	{
		return "Product [name="+this.name+", price="+this.price+", additionDate="+this.additionDate.format(Constants.DATE_FORMAT)+", category="+this.category.getName()+"]";
	}
}