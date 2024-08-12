package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class LimitsOfAuthorityMasterDaoImpl extends HibernateDaoSupport implements ILimitsOfAuthorityMasterDao{

	public ILimitsOfAuthorityMaster get(String entityName, Serializable key) throws LimitsOfAuthorityMasterException {
		if(!(entityName==null|| key==null)){
			return (ILimitsOfAuthorityMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new LimitsOfAuthorityMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public ILimitsOfAuthorityMaster create(String entityName, 
			ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException {
		if(!(entityName==null|| obj==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, obj);
			obj.setId(key.longValue());
			return obj;
		}else{
				throw new LimitsOfAuthorityMasterException("ERROR- Entity name or key is null ");
		}
	}
	
	public ILimitsOfAuthorityMaster update(String entityName, ILimitsOfAuthorityMaster item) throws LimitsOfAuthorityMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ILimitsOfAuthorityMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new LimitsOfAuthorityMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public ILimitsOfAuthorityMaster delete(String entityName, ILimitsOfAuthorityMaster item) throws LimitsOfAuthorityMasterException{
		
		ILimitsOfAuthorityMaster returnObj = new OBLimitsOfAuthorityMaster();
		try{
			item.setStatus("INACTIVE");
			item.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(entityName, item);
			returnObj = (ILimitsOfAuthorityMaster) getHibernateTemplate().load(entityName,new Long(item.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in delete Banking arrangement Facility exclusion ",obe);
			obe.printStackTrace();
			throw new LimitsOfAuthorityMasterException("Unable to delete Banking arrangement Facility exclusion with id ["+item.getId()+"]");
		}
		return returnObj;
	}
	
	public List getList(){
		List resultList = new ArrayList();
		try{
			
			String query = "SELECT FROM " +ILimitsOfAuthorityMasterDao.ACTUAL_NAME +" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			resultList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
}