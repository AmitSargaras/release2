package com.integrosys.cms.app.goodsMaster.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.limit.bus.BFLTATParameterSummary;
import com.integrosys.cms.app.limit.bus.IBFLTATParameterSummary;
import com.integrosys.cms.app.limit.bus.LimitException;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;

public class GoodsMasterDaoImpl extends HibernateDaoSupport implements IGoodsMasterDao{

	
	/**
	  * @return Particular GoodsMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IGoodsMaster getGoodsMaster(String entityName, Serializable key)throws GoodsMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (IGoodsMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new GoodsMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return GoodsMaster Object
	 * @param Entity Name
	 * @param GoodsMaster Object  
	 * This method Creates GoodsMaster Object
	 */
	public IGoodsMaster createGoodsMaster(String entityName,
			IGoodsMaster goodsMaster)
			throws GoodsMasterException {
		if(!(entityName==null|| goodsMaster==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, goodsMaster);
			goodsMaster.setId(key.longValue());
			return goodsMaster;
			}else{
				throw new GoodsMasterException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return GoodsMaster Object
	 * @param Entity Name
	 * @param GoodsMaster Object  
	 * This method Updates GoodsMaster Object
	 */
	
	public IGoodsMaster updateGoodsMaster(String entityName, IGoodsMaster item)throws GoodsMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IGoodsMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new GoodsMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public boolean isGoodsCodeUnique(String goodsCode) {
			String actualQuery = "SELECT FROM "+IGoodsMasterDao.ACTUAL_GOODS_MASTER_NAME+" WHERE DEPRECATED='N' and GOODS_CODE like '"+goodsCode+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
	}
	
	/*public SearchResult getSearchedGoodsMaster(String type,String text) {
		//DefaultLogger.debug(this, "Method call started :: getSearchedExcludedFacility() ");
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + IGoodsMasterDao.ACTUAL_EXCLUDED_FACILITY_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(EXCLUDED_FACILITY_CATEGORY)";

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
	
	/*public List getGoodsMasterList(){
		List goodsMasterList = new ArrayList();
		try{
			
			
			String query = "SELECT FROM "+IGoodsMasterDao.ACTUAL_GOODS_MASTER_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			DefaultLogger.debug(this, " getGoodsParentCodeList() started."+query);
			goodsMasterList = (List) getHibernateTemplate().find(query);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return goodsMasterList;
	}*/
	
	public List getGoodsMasterList(){
		String theSQL = "SELECT H.GOODS_CODE,H.GOODS_NAME,H.GOODS_PARENT_CODE,H.RESTRICTION_TYPE "
			+ "FROM CMS_GOODS_MASTER H";

		return ((JdbcOperations) getHibernateTemplate()).query(theSQL, new Object[]{}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBGoodsMaster summary = new OBGoodsMaster();
				summary.setGoodsParentCode(rs.getString("GOODS_CODE"));
				summary.setGoodsCode(rs.getString("GOODS_NAME"));
				summary.setGoodsName(rs.getString("GOODS_PARENT_CODE"));
				summary.setRestrictionType(rs.getString("RESTRICTION_TYPE"));
				DefaultLogger.debug(rs.getString("GOODS_PARENT_CODE"), " getGoodsParentCodeList() started.");
				return summary;
			}
		});
	}
	
	
	public ArrayList getGoodsParentCodeList() {
		 ArrayList xrefIdList = new ArrayList();
		 DBUtil dbUtil = null;
		try{
			DefaultLogger.debug(this, " getGoodsParentCodeList() started.");
			dbUtil = new DBUtil();			   
			
			String query1="SELECT GOODS_CODE " + 
					" FROM CMS_GOODS_MASTER D where LENGTH(GOODS_CODE)>=4 and LENGTH(GOODS_CODE)<=6 " + 
					" ORDER BY D.GOODS_CODE " ;
			System.out.println("xrefIdList()=> sql query=>"+query1);
			ResultSet rs=null;
			 
			dbUtil.setSQL(query1);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
				String xrefId = rs.getString("GOODS_CODE");
				xrefIdList.add(xrefId);
				}
			}
			rs.close();
		}
		catch(SQLException e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, " getXrefIdList() completed.");
		return xrefIdList;
	}
	
}
