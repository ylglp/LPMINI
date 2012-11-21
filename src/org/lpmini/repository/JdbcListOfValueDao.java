package org.lpmini.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpmini.domain.ListOfValue;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * It is the ListOfValueDao JDBC implementation. 
 * 
 * Creation date: Nov. 14, 2012
 * Last modify date: Nov. 20, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
@Repository
public class JdbcListOfValueDao implements ListOfValueDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertLOV;
	
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertLOV = new SimpleJdbcInsert(dataSource).withTableName("ListOfValue");
	}
	
	// o: the main object: this ListOfValue; 
	protected final static String fieldSelectionForRead =
			"o.LOVType,o.LocaleString,o.Key,o.StringValue,o.IntValue,o.DecimalValue,o.DisplayOrder,o.Notes";

	protected final static String fieldSetForUpdate = 
			"StringValue=:StringValue,IntValue=:IntValue,DecimalValue=:DecimalValue,DisplayOrder=:DisplayOrder,Notes=:Notes";
	
	private static class ListOfValueMapper implements RowMapper<ListOfValue> {
		
		public ListOfValue mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ListOfValue lov = new ListOfValue();
			
			lov.setLOVType(rs.getString("LOVType"));
			lov.setLocaleString(rs.getString("LocaleString"));
			lov.setKey(rs.getString("Key"));
			lov.setStringValue(rs.getString("StringValue"));
			lov.setIntValue(rs.getInt("IntValue"));
			lov.setDecimalValue(rs.getDouble("DecimalValue"));
			lov.setDisplayOrder(rs.getInt("DisplayOrder"));
			lov.setNotes(rs.getString("Notes"));
			
			return lov;
		}
	}
	
	@Override
	/**
	 * Retrieve all LOVs in the system. The results will be sorted by LOVType and then localString
	 */
	public List<ListOfValue> getAllListOfValues() {
		
		try {
			String strQuery = "select " + fieldSelectionForRead + " from ListOfValue as o order by o.LOVType, o.LocaleString";
			List<ListOfValue> lovs = namedParameterJdbcTemplate.getJdbcOperations().query(
					strQuery,
					new ListOfValueMapper());
			/*
			String strQuery = "select * from ListOfValue where LOVType=:LOVType";
			List<ListOfValue> lovs = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("LOVType", "TestLOVType1"),
					new ListOfValueMapper());
				*/
			return lovs;
		}
		catch (Exception e) {
			System.out.println("JdbcListOfValueDao.getAllListOfValues Exception: " + e.getMessage());
			return null;
		}
	} 

	@Override
	/**
	 * Retrieve all LOVs for a given LOVType. 
	 * The results will be sorted by localString and then the given sorting instruction: key or displayorder
	 * 
	 * @param type: LOVType
	 * @param sortBy: "key": it will be sorted by the field Key; "displayorder": it will be sorted by the field DisplayOrder 
	 * @return list of LOVs
	 */
	public List<ListOfValue> getListOfValuesByType(String type, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Add a ListOfValue
	 * @param lov: a LOV to add
	 * @return number of record added
	 * @throw if the triple <LOVType, LocalString, Key> already exists, it will throw the DuplicateKeyException
	 */
	public int addListOfValue(ListOfValue lov) throws DuplicateKeyException, Exception {
		return 0;
	}
	
	@Override
	/**
	 * Save the change of a ListOfValue. It uses <LOVType, LocalString, Key> to locate the record to update.
	 * @param lov: a LOV to save
	 * @return number of record updated
	 */
	public int saveListOfValue(ListOfValue lov) throws Exception {
		return 0;
	}
	
	@Override
	/**
	 * Delete a ListOfValue. It uses <LOVType, LocalString, Key> to locate the record to delete.
	 * @param lov: a LOV to delete
	 * @return number of record deleted
	 */
	public int deleteListOfValue(ListOfValue lov) throws Exception {
		return 0;
	}
	
	@Override
	/**
	 * Delete all the ListOfValue for the given LOVType.
	 * @param type: LOVType
	 * @return number of record deleted
	 */
	public int deleteListOfValuesByType(String type) throws Exception {
		return 0;
	}
	
}
