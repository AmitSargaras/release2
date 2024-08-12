package com.integrosys.cms.app.riskType.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.hibernate.Query;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;


public class RiskTypeDaoImpl extends HibernateDaoSupport implements IRiskTypeDao{

	
	/**
	  * @return Particular RiskType according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IRiskType getRiskType(String entityName, Serializable key)throws RiskTypeException {
		
		if(!(entityName==null|| key==null)){
		
		return (IRiskType) getHibernateTemplate().get(entityName, key);
		}else{
			throw new RiskTypeException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return RiskType Object
	 * @param Entity Name
	 * @param RiskType Object  
	 * This method Creates RiskType Object
	 */
	public IRiskType createRiskType(String entityName,
			IRiskType riskType)
			throws RiskTypeException {
		if(!(null == entityName || null == riskType)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, riskType);
			riskType.setId(key.longValue());
			//String risktypecode=getRiskTypeCode();
			//riskType.setRiskTypeCode(risktypecode);
			return riskType;
			}else{
				throw new RiskTypeException("ERROR- Entity name or key is null ");
			}
	}
	
	
	public String getRiskTypeCode() throws DBConnectionException{

        ResultSet rs;
        String risktypecode = null;
        NumberFormat numberFormat;
        DBUtil dbUtil = null;
        try {
            String query = "select RISK_TYPE_SEQ.NEXTVAL from dual";
            
            dbUtil = new DBUtil();
            dbUtil.setSQL(query);
            rs = dbUtil.executeQuery();
//            if(rs.getFetchSize()>0){
                while(rs.next()){
                	risktypecode = rs.getString(1);
                }
//            }
            rs.close();
        }
        catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getRiskTypeCode", ex);
        }
        catch (Exception ex) {
            throw new SearchDAOException("Exception in getRiskTypeCode", ex);
        }
        finally {
            try {
                dbUtil.close();
            }
            catch (SQLException ex) {
                throw new SearchDAOException("SQLException in getDocumentDescByDocCode", ex);
            }
        }
        numberFormat = new DecimalFormat("00");
        risktypecode = numberFormat.format(Long.parseLong(risktypecode));
		risktypecode = "RT" + risktypecode;
        return risktypecode;
    }
	/**
	 * @return RiskType Object
	 * @param Entity Name
	 * @param RiskType Object  
	 * This method Updates RiskType Object
	 */
	
	public IRiskType updateRiskType(String entityName, IRiskType item)throws RiskTypeException{
		if(!(null == entityName || null == item)){
			getHibernateTemplate().update(entityName, item);
			return  (IRiskType) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new RiskTypeException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IRiskType deleteRiskType(String entityName, IRiskType item)throws RiskTypeException{

		
		IRiskType returnObj = new OBRiskType();
		try{
			item.setStatus("INACTIVE");
			item.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(entityName, item);
			returnObj = (IRiskType) getHibernateTemplate().load(entityName,new Long(item.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteRiskType ",obe);
			obe.printStackTrace();
			throw new RiskTypeException("Unable to delete other bank with id ["+item.getId()+"]");
		}
		return returnObj;
	}
	
/*	public boolean isProductCodeUnique(String productCode) {
			String actualQuery = "SELECT FROM "+IRiskTypeDao.ACTUAL_RISK_TYPE_NAME+" WHERE DEPRECATED='N' and PRODUCT_CODE like '"+productCode+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
	}*/
	
	/*public SearchResult getSearchedRiskType(String type,String text) {
		//DefaultLogger.debug(this, "Method call started :: getSearchedExcludedFacility() ");
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + IRiskTypeDao.ACTUAL_EXCLUDED_FACILITY_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(EXCLUDED_FACILITY_CATEGORY)";

			} else {
				 if ( type.equalsIgnoreCase("excludedFacilityDescription") ) 	

//					query = "FROM " + IExcludedFacilityDao.ACTUAL_EXCLUDED_FACILITY_NAME + " WHERE UPPER(EXCLUDED_FACILITY_CATEGORY) LIKE '" + text.toUpperCase() + "%' and deprecated='N' and status = 'ACTIVE' order by UPPER(EXCLUDED_FACILITY_CATEGORY)";
					query = "FROM " + IExcludedFacilityDao.ACTUAL_EXCLUDED_FACILITY_NAME + " WHERE EXCLUDED_FACILITY_CATEGORY in (select cce.entryCode from "+ICommonCodeDao.ACTUAL_COMMON_CODE_ENTRY+" cce where cce.categoryCode = 'FACILITY_CATEGORY' and cce.entryName like '" + text + "%') and deprecated='N' and status = 'ACTIVE' order by UPPER(EXCLUDED_FACILITY_CATEGORY)";				

			}
			
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(),resultList);

		} catch (Exception e) {
			throw new ExcludedFacilityException("ERROR-- While retriving ExcludedFacility");
		}
		
		}*/
	
	public List getRiskTypeList(){
		List riskTypeList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IRiskTypeDao.ACTUAL_RISK_TYPE_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			riskTypeList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return riskTypeList;
	}
	
	public Set<String> getRiskGrade(){
		Set<String> riskGrade=new HashSet<String>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select entry_name from common_code_category_entry where category_code='RISK_GRADE' and active_status='1' order by entry_name asc");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					riskGrade.add(rs.getString("entry_name"));
				}
			}
			rs.close();
			dbUtil.close();
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getRiskGrade: "+e.getMessage());
        	e.printStackTrace();
		}
		return riskGrade;
		
	}
	
	public int getCountOfRiskTypeRecord(String riskTypeCode){
		
			System.out.println( "Inside getCountOfRiskTypeRecord Method");
			int count = 0;
		//	String sql="select count(*) as COUNT from CMS_RISK_TYPE where  STATUS='ACTIVE' and RISK_TYPE_NAME = '"+riskTypeName+"' AND RISK_TYPE_CODE = '"+riskTypeCode+"'  ";
			String sql="select count(*) as COUNT from CMS_RISK_TYPE where  STATUS='ACTIVE' and RISK_TYPE_CODE = '"+riskTypeCode+"'  ";

			DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					count=rs.getInt("COUNT");
				}
			}
			rs.close();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
}
	
	public int getCountOfRiskTypenNameRecord(String riskTypeName){
		
		System.out.println( "Inside getCountOfRiskTypenNameRecord Method");
		int count = 0;
	//	String sql="select count(*) as COUNT from CMS_RISK_TYPE where  STATUS='ACTIVE' and RISK_TYPE_NAME = '"+riskTypeName+"' AND RISK_TYPE_CODE = '"+riskTypeCode+"'  ";
		String sql="select count(*) as COUNT from CMS_RISK_TYPE where  STATUS='ACTIVE' and RISK_TYPE_NAME = '"+riskTypeName+"'";

		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				count=rs.getInt("COUNT");
			}
		}
		rs.close();
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return count;
}
	
	
}