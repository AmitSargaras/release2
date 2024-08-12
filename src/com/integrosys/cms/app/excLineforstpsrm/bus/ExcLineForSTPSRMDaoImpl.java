package com.integrosys.cms.app.excLineforstpsrm.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class ExcLineForSTPSRMDaoImpl extends HibernateDaoSupport implements IExcLineForSTPSRMDao{

	public IExcLineForSTPSRM get(String entityName, Serializable key) throws ExcLineForSTPSRMException {
		if(!(entityName==null|| key==null)){
			return (IExcLineForSTPSRM) getHibernateTemplate().get(entityName, key);
		}else{
			throw new ExcLineForSTPSRMException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IExcLineForSTPSRM create(String entityName, 
			IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException {
		if(!(entityName==null|| obj==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, obj);
			obj.setId(key.longValue());
			return obj;
		}else{
				throw new ExcLineForSTPSRMException("ERROR- Entity name or key is null ");
		}
	}
	
	public IExcLineForSTPSRM update(String entityName, IExcLineForSTPSRM item) throws ExcLineForSTPSRMException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IExcLineForSTPSRM) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ExcLineForSTPSRMException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IExcLineForSTPSRM delete(String entityName, IExcLineForSTPSRM item) throws ExcLineForSTPSRMException{
		
		IExcLineForSTPSRM returnObj = new OBExcLineForSTPSRM();
		try{
			item.setStatus("INACTIVE");
			item.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(entityName, item);
			returnObj = (IExcLineForSTPSRM) getHibernateTemplate().load(entityName,new Long(item.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in delete Exclusion Line for STP for SRM ",obe);
			obe.printStackTrace();
			throw new ExcLineForSTPSRMException("Unable to delete Exclusion Line for STP for SRM with id ["+item.getId()+"]");
		}
		return returnObj;
	}
	
	public List getList(){
		List resultList = new ArrayList();
		try{
			
			String query = "SELECT FROM " +IExcLineForSTPSRMDao.ACTUAL_NAME +" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			resultList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
}