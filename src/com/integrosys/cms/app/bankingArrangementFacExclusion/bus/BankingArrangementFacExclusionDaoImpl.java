package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class BankingArrangementFacExclusionDaoImpl extends HibernateDaoSupport implements IBankingArrangementFacExclusionDao{

	public IBankingArrangementFacExclusion get(String entityName, Serializable key) throws BankingArrangementFacExclusionException {
		if(!(entityName==null|| key==null)){
			return (IBankingArrangementFacExclusion) getHibernateTemplate().get(entityName, key);
		}else{
			throw new BankingArrangementFacExclusionException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IBankingArrangementFacExclusion create(String entityName, 
			IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException {
		if(!(entityName==null|| obj==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, obj);
			obj.setId(key.longValue());
			return obj;
		}else{
				throw new BankingArrangementFacExclusionException("ERROR- Entity name or key is null ");
		}
	}
	
	public IBankingArrangementFacExclusion update(String entityName, IBankingArrangementFacExclusion item) throws BankingArrangementFacExclusionException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IBankingArrangementFacExclusion) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new BankingArrangementFacExclusionException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IBankingArrangementFacExclusion delete(String entityName, IBankingArrangementFacExclusion item) throws BankingArrangementFacExclusionException{
		
		IBankingArrangementFacExclusion returnObj = new OBBankingArrangementFacExclusion();
		try{
			item.setStatus("INACTIVE");
			item.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(entityName, item);
			returnObj = (IBankingArrangementFacExclusion) getHibernateTemplate().load(entityName,new Long(item.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in delete Banking arrangement Facility exclusion ",obe);
			obe.printStackTrace();
			throw new BankingArrangementFacExclusionException("Unable to delete Banking arrangement Facility exclusion with id ["+item.getId()+"]");
		}
		return returnObj;
	}
	
	public List getList(){
		List resultList = new ArrayList();
		try{
			
			String query = "SELECT FROM " +IBankingArrangementFacExclusionDao.ACTUAL_NAME +" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			resultList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
}