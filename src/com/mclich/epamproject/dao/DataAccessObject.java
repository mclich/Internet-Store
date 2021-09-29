package com.mclich.epamproject.dao;

import java.util.List;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.*;

public interface DataAccessObject<T>
{
	void create(T t) throws CreateException, CNAException;
	
	void delete(T t) throws DeleteException, CNAException;
	
	void update(int id, T toUpdate) throws UpdateException, CNAException;
	
	T get(int id) throws GetException, CNAException;
	
	int getId(T t) throws GetException, CNAException;
	
	List<T> getAll() throws GetException, CNAException;
}