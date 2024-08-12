package com.integrosys.cms.app.collateralrocandinsurance.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterDao;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeDao;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacilityDao;


public class CollateralRocDaoImpl extends HibernateDaoSupport implements ICollateralRocDao{

	/**
	  * @return Particular Collateral Roc and Insurance Mapping according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICollateralRoc getCollateralRoc(String entityName, Serializable key)throws CollateralRocException {
		
		if(!(entityName==null|| key==null)){
		
		return (ICollateralRoc) getHibernateTemplate().get(entityName, key);
		}else{
			throw new CollateralRocException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return Collateral Roc Object
	 * @param Entity Name
	 * @param Collateral Roc Object  
	 * This method Updates Collateral Roc Object
	 */
	
	public ICollateralRoc updateCollateralRoc(String entityName, ICollateralRoc item)throws CollateralRocException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICollateralRoc) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CollateralRocException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return Collateral Roc Object
	 * @param Entity Name
	 * @param Collateral Roc Object  
	 * This method Creates Collateral Roc Object
	 */
	public ICollateralRoc createCollateralRoc(String entityName,
			ICollateralRoc collateralRoc)
			throws CollateralRocException {
		if(!(entityName==null|| collateralRoc==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, collateralRoc);
			collateralRoc.setId(key.longValue());
			return collateralRoc;
			}else{
				throw new CollateralRocException("ERROR- Entity name or key is null ");
			}
	}
	
	public List getCollateralCodeList()throws ComponentException{
		
		 ArrayList resultList = new ArrayList();
			try{
				String sql = "SELECT ob.newCollateralCode , ob.newCollateralDescription FROM "+ICollateralNewMasterDao.ACTUAL_COLLATERAL_NEW_MASTER_NAME+" ob where DEPRECATED='N' and STATUS='ACTIVE'";
				
				resultList = (ArrayList) getHibernateTemplate().find(sql);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getCollateralCodeList ",ex);
			}
	     return resultList;
	
	}
	
	public List getComponentCodeList()throws ComponentException{
		
		 ArrayList resultList = new ArrayList();
			try{
				String sql = "SELECT ob.componentCode , ob.componentName FROM "+IComponentDao.ACTUAL_COMPONENT_NAME+" ob where DEPRECATED='N' and STATUS='ACTIVE'";
				
				resultList = (ArrayList) getHibernateTemplate().find(sql);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getComponentCodeList ",ex);
			}
	     return resultList;
	
	}
	
	public List getPropertyTypeList()throws ComponentException{
		
		 ArrayList resultList = new ArrayList();
			try{
				String sql = "SELECT ob.entryCode , ob.entryName FROM "+ICommonCodeDao.ACTUAL_COMMON_CODE_ENTRY+" ob where ob.categoryCode = 'PROPERTY_TYPE' and ACTIVE_STATUS='1' ";
				
				resultList = (ArrayList) getHibernateTemplate().find(sql);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getPropertyTypeList ",ex);
			}
	     return resultList;
	
	}
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode) {
	
			String actualQuery = "SELECT FROM "+ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME+" WHERE DEPRECATED='N' and COLLATERAL_CATEGORY like '"+collateralCategory+"' and COLLATERAL_ROC_DESCRIPTION like '"+collateralRocCode+"'";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
			 
	}
	
	public SearchResult getSearchedCollateralRoc(String type,String text) {
		try {
			String query = "";

			if ( text == null || text == "" ) {
				query = "FROM " + ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME + "  where deprecated='N' and status = 'ACTIVE' ORDER BY UPPER(COLLATERAL_ROC_CODE)";

			} else if ( type.equalsIgnoreCase("collateralRocCode") ) 	
				{
					query = "FROM " + ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME  + " WHERE UPPER(COLLATERAL_ROC_CODE) LIKE '" + text.toUpperCase() + "%' and deprecated='N' and status = 'ACTIVE' order by UPPER(COLLATERAL_ROC_CODE)";
				} else if(type.equalsIgnoreCase("collateralRocDescription"))
					{
						query = "FROM " + ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME  + " WHERE UPPER(COLLATERAL_ROC_DESCRIPTION) LIKE '" + text.toUpperCase() + "%' and deprecated='N' and status = 'ACTIVE' order by UPPER(COLLATERAL_ROC_CODE)";
					}else if(type.equalsIgnoreCase("irbCategory"))
						{
							query = "FROM " + ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME  + " WHERE UPPER(IRB_CATEGORY) LIKE '" + text.toUpperCase() + "%' and deprecated='N' and status = 'ACTIVE' order by UPPER(COLLATERAL_ROC_CODE)";
						}else if(type.equalsIgnoreCase("insuranceApplicable"))
							{
								query = "FROM " + ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME  + " WHERE UPPER(INSURANCE_APPLICABLE) LIKE '" + text.toUpperCase() + "%' and deprecated='N' and status = 'ACTIVE' order by UPPER(COLLATERAL_ROC_CODE)";
							}
			
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(),resultList);

		} catch (Exception e) {
			throw new CollateralRocException("ERROR-- While retriving CollateralRoc");
		}
		
		}
}
