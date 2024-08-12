package com.integrosys.cms.app.cersaiMapping.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;

public class CersaiMappingJdbcImpl extends JdbcTemplateAdapter implements
ICersaiMappingJdbc{

	private static final String SELECT_CERSAI_MAPPING_TRX = "SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE ='";
	
	@Override
	public List getMasterList() {
		String sql = "select entry_code,entry_name from common_code_category_entry where category_code ='Master_Name'";
				
				
	    List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
			String[] stringArray = new String[2];
            stringArray[0] = rs.getString("entry_code");
            stringArray[1] = rs.getString("entry_name");
            return stringArray;
		
		}
	});
	 return resultList;
}

	@Override
	public ICersaiMapping[] getMasterValueList(String masterName) {
		
		
		
		String sql1 = "select count(*) from CMS_CERSAI_MAPPING where MASTER_NAME = '"+masterName+"'";
		long mappingData = getJdbcTemplate().queryForLong(sql1);
		
		String sql2 = "";
		DBUtil dbUtil = null;
		if(masterName.equals("PROPERTY_TYPE")) {
			sql2 = "select entry_name from common_code_category_entry where category_code = '"+masterName+"' OR category_code = 'COLLATERAL_TYPE'";
		}else {
			sql2 = "select entry_name from common_code_category_entry where category_code ='"+masterName+"'";
		}
		DBUtil dbUtil3 = null;
		
		

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql2);
			// DefaultLogger.debug(this, sql);
			ResultSet rs = null;
			rs = dbUtil.executeQuery();
			
			dbUtil3 = new DBUtil();
			ResultSet rs1 = null;
			String sql3="";
			
			ICersaiMapping cersaiMapping= null;  
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				cersaiMapping = new OBCersaiMapping();
				cersaiMapping.setClimsValue(rs.getString("entry_name"));
				
//				sql3 = "SELECT CERSAI_VALUE FROM CMS_SUB_STAGE_CERSAI_MAPPING where ID=(SELECT MAX(ID) FROM CMS_SUB_STAGE_CERSAI_MAPPING " + 
//						"WHERE CLIMS_VALUE='"+rs.getString("entry_name")+"') AND CLIMS_VALUE='"+rs.getString("entry_name")+"' AND ROWNUM=1";
				
				sql3 = "SELECT CERSAI_VALUE FROM CMS_SUB_CERSAI_MAPPING where ID=(SELECT MAX(ID) FROM CMS_SUB_CERSAI_MAPPING " + 
						"WHERE CLIMS_VALUE='"+rs.getString("entry_name")+"') AND CLIMS_VALUE='"+rs.getString("entry_name")+"' AND ROWNUM=1";
				dbUtil3.setSQL(sql3);
				rs1 = dbUtil3.executeQuery();
				if(rs1.next()) {
					cersaiMapping.setCersaiValue(rs1.getString("CERSAI_VALUE"));
				}else {
					cersaiMapping.setCersaiValue("");
				}
				resultList.add(cersaiMapping);
			}
			rs.close();
			return (ICersaiMapping[]) resultList.toArray(new ICersaiMapping[resultList.size()]);
			
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException getMasterValueList ", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception getMasterValueList ", ex);
		}
		finally {
			try {
				dbUtil.close();
				dbUtil3.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException getGeneralParamEntry ", ex);
			}
		}
}
	@Override
	public void insertDataIntoCersaiStaging(StringBuffer sqlInsertQuery) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL(sqlInsertQuery.toString());
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "File data is inserted into Table: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertDataToTable_CMS_SUB_STAGE_CERSAI_MAPPING:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertDataToTable_CMS_SUB_STAGE_CERSAI_MAPPING:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	
	public String getNameOfMaster(String climsName) throws Exception{
		String masterName="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT CATEGORY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME='"+climsName+"'");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					masterName=rs.getString("CATEGORY_CODE");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getNameOfMaster: "+e.getMessage());
        	e.printStackTrace();
		}
		return masterName;
	}
	
	
	
	public void insertDataIntoCersaiActual(StringBuffer sqlInsertQuery) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL(sqlInsertQuery.toString());
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "File data is inserted into Table: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertDataToTable_CMS_SUB_STAGE_CERSAI_MAPPING:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertDataToTable_CMS_SUB_STAGE_CERSAI_MAPPING:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	
	public String getMasterName(String id) throws Exception{
		String masterName="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select distinct master_name from  CMS_SUB_STAGE_CERSAI_MAPPING where ID='"+id+"'");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					masterName=rs.getString("master_name");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getMasterName: "+e.getMessage());
        	e.printStackTrace();
		}
		return masterName;
	}
	
public ICersaiMapping[] fetchValueList(String id) {
		
		//String sql1 = "SELECT CLIMS_VALUE,CERSAI_VALUE FROM CMS_SUB_STAGE_CERSAI_MAPPING WHERE ID= '"+id+"'";
		//long mappingData = getJdbcTemplate().queryForLong(sql1);
		

		DBUtil dbUtil = null;
		String sql = "SELECT CLIMS_VALUE,CERSAI_VALUE FROM CMS_SUB_STAGE_CERSAI_MAPPING WHERE ID='"+id+"'";
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ResultSet rs = null;
			rs = dbUtil.executeQuery();
			
			ICersaiMapping cersaiMapping= null;  
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				cersaiMapping = new OBCersaiMapping();
				cersaiMapping.setClimsValue(rs.getString("CLIMS_VALUE"));
				cersaiMapping.setCersaiValue(rs.getString("CERSAI_VALUE"));
				resultList.add(cersaiMapping);
			}
			rs.close();
			return (ICersaiMapping[]) resultList.toArray(new ICersaiMapping[resultList.size()]);
			
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException fetchValueList ", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception fetchValueList ", ex);
		}
		finally {
			try {
				dbUtil.close();
				
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException getGeneralParamEntry ", ex);
			}
		}
}

public class CersaiMappingRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OBCersaiMapping result = new OBCersaiMapping();
		
		//result.setCersaiValue(rs.getString("CERSAI_VALUE"));
		result.setClimsValue(rs.getString("ENTRY_NAME"));
		//result.setStatus(rs.getString("STATUS"));
		
		//result.setId(rs.getLong("id"));
		return result;
	}
}

public class CersaiMappingRowMapper1 implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OBCersaiMapping result = new OBCersaiMapping();
		
		//result.setCersaiValue(rs.getString("CERSAI_VALUE"));
		result.setClimsValue(rs.getString("NEW_COLLATERAL_DESCRIPTION"));
		//result.setStatus(rs.getString("STATUS"));
		
		//result.setId(rs.getLong("id"));
		return result;
	}
}

public SearchResult getAllCersaiMapping(String mastername) {
	List resultList = null;
	try {
		String SQL = "";
		
			SQL=SELECT_CERSAI_MAPPING_TRX + mastername+"'";
			
			resultList = getJdbcTemplate().query(SQL,
					new CersaiMappingRowMapper());
		
//		ICersaiMapping ic=new OBCersaiMapping();
//		String mastername=ic.getMasterName();
		
//		resultList = getJdbcTemplate().query(SQL,
//				new CersaiMappingRowMapper());

	} catch (Exception e) {
		throw new CersaiMappingException("ERROR-- While retriving Cersai Mapping.");
	}
	SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
	return searchresult;
	}




public ICersaiMapping[] getMasterListOfValues(String masterName) {
	
	String sql1 = "select count(*) from CMS_CERSAI_MAPPING where MASTER_NAME = '"+masterName+"'";
	long mappingData = getJdbcTemplate().queryForLong(sql1);
	

	DBUtil dbUtil = null;
	String sql2 = "";
	
	if(masterName.equals("PROPERTY_TYPE")) {
		sql2 = "select entry_name from common_code_category_entry where category_code = '"+masterName+"' OR category_code = 'COLLATERAL_TYPE'";
	}else {
		sql2 = "select entry_name from common_code_category_entry where category_code ='"+masterName+"'";
	}
	
	DBUtil dbUtil3 = null;
	
	
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql2);
		// DefaultLogger.debug(this, sql);
		ResultSet rs = null;
		rs = dbUtil.executeQuery();
		
		dbUtil3 = new DBUtil();
		ResultSet rs1 = null;
		String sql3="";
		
		ICersaiMapping cersaiMapping= null;  
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			cersaiMapping = new OBCersaiMapping();
			cersaiMapping.setClimsValue(rs.getString("entry_name"));
			
//			sql3 = "SELECT CERSAI_VALUE FROM CMS_SUB_STAGE_CERSAI_MAPPING where ID=(SELECT MAX(ID) FROM CMS_SUB_STAGE_CERSAI_MAPPING " + 
//					"WHERE CLIMS_VALUE='"+rs.getString("entry_name")+"') AND CLIMS_VALUE='"+rs.getString("entry_name")+"' AND ROWNUM=1";
			
			sql3 = "SELECT CERSAI_VALUE FROM CMS_SUB_CERSAI_MAPPING where ID=(SELECT MAX(ID) FROM CMS_SUB_CERSAI_MAPPING " + 
					"WHERE CLIMS_VALUE='"+rs.getString("entry_name")+"') AND CLIMS_VALUE='"+rs.getString("entry_name")+"' AND ROWNUM=1";
			dbUtil3.setSQL(sql3);
			rs1 = dbUtil3.executeQuery();
			if(rs1.next()) {
				cersaiMapping.setCersaiValue(rs1.getString("CERSAI_VALUE"));
			}else {
				cersaiMapping.setCersaiValue("");
			}
			resultList.add(cersaiMapping);
		}
		rs.close();
		return (ICersaiMapping[]) resultList.toArray(new ICersaiMapping[resultList.size()]);
		
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException getMasterValueList ", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception getMasterValueList ", ex);
	}
	finally {
		try {
			dbUtil.close();
			dbUtil3.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException getGeneralParamEntry ", ex);
		}
	}
}

public String getCersaiValForValidationIssueCheck(String climsVal,String mastername) throws Exception{
	String cersaiVal="";
	DBUtil dbUtil = null;
	ResultSet rs=null;
	try{
		dbUtil=new DBUtil();
		dbUtil.setSQL("SELECT CERSAI_VALUE from CMS_SUB_CERSAI_MAPPING " + 
				"WHERE ID=(select MAX(ID) " + 
				"from CMS_SUB_CERSAI_MAPPING where MASTER_NAME='"+mastername+"' AND CLIMS_VALUE='"+climsVal+"') AND CLIMS_VALUE='"+climsVal+"'");
		rs = dbUtil.executeQuery();
		if(null!=rs)
		{
			while(rs.next())
			{
				cersaiVal=rs.getString("CERSAI_VALUE");
			}
		}
		finalize(dbUtil,rs);
	}catch (Exception e) {
		DefaultLogger.debug(this, " exception in getCersaiValForValidationIssueCheck: "+e.getMessage());
    	e.printStackTrace();
	}
	return cersaiVal;
}


public String getClimsValForValidationIssueCheck(String climsVal,String mastername) throws Exception{
	String climVal="";
	DBUtil dbUtil = null;
	ResultSet rs=null;
	try{
		dbUtil=new DBUtil();
		dbUtil.setSQL("select entry_name from common_code_category_entry where category_code ='"+mastername+"' AND ENTRY_NAME='"+climsVal+"'");
		rs = dbUtil.executeQuery();
		if(null!=rs)
		{
			while(rs.next())
			{
				climVal=rs.getString("entry_name");
			}
		}
		finalize(dbUtil,rs);
	}catch (Exception e) {
		DefaultLogger.debug(this, " exception in getClimsValForValidationIssueCheck: "+e.getMessage());
    	e.printStackTrace();
	}
	return climVal;
}
	
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@Override
	public SearchResult getAllCersaiMapping() throws CersaiMappingException {
		return null;
	}

}
