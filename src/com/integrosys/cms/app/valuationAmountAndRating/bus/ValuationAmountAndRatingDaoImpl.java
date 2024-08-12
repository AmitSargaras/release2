package com.integrosys.cms.app.valuationAmountAndRating.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class ValuationAmountAndRatingDaoImpl extends HibernateDaoSupport implements IValuationAmountAndRatingDao{

	
	/**
	  * @return Particular ValuationAmountAndRating according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IValuationAmountAndRating getValuationAmountAndRating(String entityName, Serializable key)throws ValuationAmountAndRatingException {
		
		if(!(entityName==null|| key==null)){
		
		return (IValuationAmountAndRating) getHibernateTemplate().get(entityName, key);
		}else{
			throw new ValuationAmountAndRatingException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return ValuationAmountAndRating Object
	 * @param Entity Name
	 * @param ValuationAmountAndRating Object  
	 * This method Creates ValuationAmountAndRating Object
	 */
	public IValuationAmountAndRating createValuationAmountAndRating(String entityName,
			IValuationAmountAndRating valuationAmountAndRating)
			throws ValuationAmountAndRatingException {
		if(!(entityName==null|| valuationAmountAndRating==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, valuationAmountAndRating);
			valuationAmountAndRating.setId(key.longValue());
			return valuationAmountAndRating;
			}else{
				throw new ValuationAmountAndRatingException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return ValuationAmountAndRating Object
	 * @param Entity Name
	 * @param ValuationAmountAndRating Object  
	 * This method Updates ValuationAmountAndRating Object
	 */
	
	public IValuationAmountAndRating updateValuationAmountAndRating(String entityName, IValuationAmountAndRating item)throws ValuationAmountAndRatingException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IValuationAmountAndRating) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ValuationAmountAndRatingException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IValuationAmountAndRating deleteValuationAmountAndRating(String entityName, IValuationAmountAndRating item)throws ValuationAmountAndRatingException{
//		if(!(entityName==null|| item==null)){
//			getHibernateTemplate().delete(entityName, item);
//			return  (IValuationAmountAndRating) getHibernateTemplate().load(entityName, new Long(item.getId()));
//		}else{
//			throw new ValuationAmountAndRatingException("ERROR-- Entity Name Or Key is null");
//		}
		
		IValuationAmountAndRating returnObj = new OBValuationAmountAndRating();
		try{
			item.setStatus("INACTIVE");
			item.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(entityName, item);
			returnObj = (IValuationAmountAndRating) getHibernateTemplate().load(entityName,new Long(item.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteValuationAmountAndRating ",obe);
			obe.printStackTrace();
			throw new ValuationAmountAndRatingException("Unable to delete other bank with id ["+item.getId()+"]");
		}
		return returnObj;
	}
	
	public boolean isProductCodeUnique(String productCode) {
			String actualQuery = "SELECT FROM "+IValuationAmountAndRatingDao.ACTUAL_VALUATION_AMOUNT_AND_RATING_NAME+" WHERE DEPRECATED='N' and PRODUCT_CODE like '"+productCode+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
	}
	
	/*public SearchResult getSearchedValuationAmountAndRating(String type,String text) {
		//DefaultLogger.debug(this, "Method call started :: getSearchedExcludedFacility() ");
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + IValuationAmountAndRatingDao.ACTUAL_EXCLUDED_FACILITY_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(EXCLUDED_FACILITY_CATEGORY)";

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
	
	public List getValuationAmountAndRatingList(){
		List valuationAmountAndRatingList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IValuationAmountAndRatingDao.ACTUAL_VALUATION_AMOUNT_AND_RATING_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			valuationAmountAndRatingList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return valuationAmountAndRatingList;
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
	
	public int getCountOfValuationamountMasterRecord(String criteria,String amount,String ramrating){
		
			System.out.println( "Inside getCountOfValuationamountMasterRecord Method");
			int count = 0;
			String sql="select count(*) as COUNT from CMS_VALUATION_AMT_RATING where  STATUS='ACTIVE' and criteria = '"+criteria+"' AND VALUATION_AMOUNT = '"+amount+"' AND RAM_RATING= '"+ramrating+"' ";
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