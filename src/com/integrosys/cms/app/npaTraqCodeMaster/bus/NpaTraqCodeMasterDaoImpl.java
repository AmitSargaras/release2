package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.productMaster.bus.*;

public class NpaTraqCodeMasterDaoImpl extends HibernateDaoSupport implements INpaTraqCodeMasterDao{

	
	/**
	  * @return Particular NpaTraqCodeMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public INpaTraqCodeMaster getNpaTraqCodeMaster(String entityName, Serializable key)throws NpaTraqCodeMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (INpaTraqCodeMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new NpaTraqCodeMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return NpaTraqCodeMaster Object
	 * @param Entity Name
	 * @param NpaTraqCodeMaster Object  
	 * This method Creates NpaTraqCodeMaster Object
	 */
	public INpaTraqCodeMaster createNpaTraqCodeMaster(String entityName,
			INpaTraqCodeMaster npaTraqCodeMaster)
			throws NpaTraqCodeMasterException {
		if(!(entityName==null|| npaTraqCodeMaster==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, npaTraqCodeMaster);
			npaTraqCodeMaster.setId(key.longValue());
			return npaTraqCodeMaster;
			}else{
				throw new NpaTraqCodeMasterException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return NpaTraqCodeMaster Object
	 * @param Entity Name
	 * @param NpaTraqCodeMaster Object  
	 * This method Updates NpaTraqCodeMaster Object
	 */
	
	public INpaTraqCodeMaster updateNpaTraqCodeMaster(String entityName, INpaTraqCodeMaster item)throws NpaTraqCodeMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (INpaTraqCodeMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new NpaTraqCodeMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public boolean isNpaTraqCodeUnique(String npaTraqCode, String securityType, String securitySubType, String propertyTypeDesc) {
		
		String query = "SELECT FROM "+INpaTraqCodeMasterDao.ACTUAL_NPA_TRAQ_CODE_MASTER_NAME+" "
				+ "WHERE STATUS='ACTIVE' AND NPA_TRAQ_CODE LIKE '"+npaTraqCode+"' AND SECURITY_TYPE LIKE '"+securityType+"' AND SECURITY_SUB_TYPE LIKE '"+securitySubType+"' ";
		
		if("PT".equals(securityType)) {
			query="SELECT FROM "+INpaTraqCodeMasterDao.ACTUAL_NPA_TRAQ_CODE_MASTER_NAME+" "
					+ "WHERE STATUS='ACTIVE' AND PROPERTY_TYPE_CODE_DESC LIKE '"+propertyTypeDesc+"' ";
		}
			
		ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(query);
		if(actualResultList.size()>0)
			return true;
		else
			return false;
	}
	
	/*public SearchResult getSearchedProductMaster(String type,String text) {
		//DefaultLogger.debug(this, "Method call started :: getSearchedExcludedFacility() ");
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + IProductMasterDao.ACTUAL_EXCLUDED_FACILITY_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(EXCLUDED_FACILITY_CATEGORY)";

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
	
	public List getNpaTraqCodeMasterList(){
		List npaTraqCodeMasterList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+INpaTraqCodeMasterDao.ACTUAL_NPA_TRAQ_CODE_MASTER_NAME+" WHERE STATUS!='INACTIVE' ";
			npaTraqCodeMasterList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return npaTraqCodeMasterList;
	}
}
