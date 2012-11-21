package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.ListOfValue;
import org.springframework.dao.DuplicateKeyException;

public interface ListOfValueDao {
	// Retrieve all LOVs in the system. The results will be sorted by LOVType and then localString
	public List<ListOfValue> getAllListOfValues();
	
	// Retrieve all LOVs for a given LOVType. 
	// The results will be sorted by localString and then the given sorting instruction: key or displayorder
	public List<ListOfValue> getListOfValuesByType(String type, String sortBy);
	
	public int addListOfValue(ListOfValue lov) throws DuplicateKeyException, Exception;
	public int saveListOfValue(ListOfValue lov) throws Exception;
	public int deleteListOfValue(ListOfValue lov) throws Exception;
	public int deleteListOfValuesByType(String type) throws Exception;
}
