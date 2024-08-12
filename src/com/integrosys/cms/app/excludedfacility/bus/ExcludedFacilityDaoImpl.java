package com.integrosys.cms.app.excludedfacility.bus;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeDao;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityJdbcImpl.ExcludedFacilityRowMapper;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterDao;



public class ExcludedFacilityDaoImpl extends HibernateDaoSupport implements IExcludedFacilityDao{

	/**
	  * @return Particular Excluded Facility according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IExcludedFacility getExcludedFacility(String entityName, Serializable key)throws ExcludedFacilityException {
		
		if(!(entityName==null|| key==null)){
		
		return (IExcludedFacility) getHibernateTemplate().get(entityName, key);
		}else{
			throw new ExcludedFacilityException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return Excluded Facility Object
	 * @param Entity Name
	 * @param Excluded Facility Object  
	 * This method Creates Excluded Facility Object
	 */
	public IExcludedFacility createExcludedFacility(String entityName,
			IExcludedFacility excludedFacility)
			throws ExcludedFacilityException {
		if(!(entityName==null|| excludedFacility==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, excludedFacility);
			excludedFacility.setId(key.longValue());
			return excludedFacility;
			}else{
				throw new ExcludedFacilityException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Excluded Facility Object
	 * @param Entity Name
	 * @param Excluded Facility Object  
	 * This method Updates Excluded Facility Object
	 */
	
	public IExcludedFacility updateExcludedFacility(String entityName, IExcludedFacility item)throws ExcludedFacilityException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IExcludedFacility) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ExcludedFacilityException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public boolean isExcludedFacilityDescriptionUnique(String excludedFacilityDescription) {
//		String stagingQuery = "SELECT FROM "+IExcludedFacilityDao.STAGE_EXCLUDED_FACILITY_NAME+" WHERE DEPRECATED='N' and EXCLUDED_FACILITY_CATEGORY like '"+excludedFacilityDescription+"' ";
//		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
//		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+IExcludedFacilityDao.ACTUAL_EXCLUDED_FACILITY_NAME+" WHERE DEPRECATED='N' and EXCLUDED_FACILITY_CATEGORY like '"+excludedFacilityDescription+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
//		}	
//		else 
//			return false;		 
	}
	
	public SearchResult getSearchedExcludedFacility(String type,String text) {
		//DefaultLogger.debug(this, "Method call started :: getSearchedExcludedFacility() ");
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + IExcludedFacilityDao.ACTUAL_EXCLUDED_FACILITY_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(EXCLUDED_FACILITY_CATEGORY)";

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
		
		}
}
