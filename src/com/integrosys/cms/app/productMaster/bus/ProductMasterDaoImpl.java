package com.integrosys.cms.app.productMaster.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.geography.state.bus.IStateDAO;

public class ProductMasterDaoImpl extends HibernateDaoSupport implements IProductMasterDao{

	
	/**
	  * @return Particular ProductMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IProductMaster getProductMaster(String entityName, Serializable key)throws ProductMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (IProductMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new ProductMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return ProductMaster Object
	 * @param Entity Name
	 * @param ProductMaster Object  
	 * This method Creates ProductMaster Object
	 */
	public IProductMaster createProductMaster(String entityName,
			IProductMaster productMaster)
			throws ProductMasterException {
		if(!(entityName==null|| productMaster==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, productMaster);
			productMaster.setId(key.longValue());
			return productMaster;
			}else{
				throw new ProductMasterException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return ProductMaster Object
	 * @param Entity Name
	 * @param ProductMaster Object  
	 * This method Updates ProductMaster Object
	 */
	
	public IProductMaster updateProductMaster(String entityName, IProductMaster item)throws ProductMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IProductMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ProductMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public boolean isProductCodeUnique(String productCode) {
			String actualQuery = "SELECT FROM "+IProductMasterDao.ACTUAL_PRODUCT_MASTER_NAME+" WHERE DEPRECATED='N' and PRODUCT_CODE like '"+productCode+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
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
	
	public List getProductMasterList(){
		List productMasterList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IProductMasterDao.ACTUAL_PRODUCT_MASTER_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			productMasterList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return productMasterList;
	}
}
