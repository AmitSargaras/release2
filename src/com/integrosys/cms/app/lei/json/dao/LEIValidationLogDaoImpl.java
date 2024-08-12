package com.integrosys.cms.app.lei.json.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;
import com.integrosys.cms.app.lei.json.dao.OBLEIValidationLog;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class LEIValidationLogDaoImpl extends HibernateDaoSupport implements ILEIValidationLogDao {

public String getEntityName() {
return ILEIValidationLogDao.ACTUAL_INTERFACE_LOG_NAME;
}

public ILEIValidationLog insertInterfaceLog(ILEIValidationLog log) throws Exception {

	ILEIValidationLog returnObj = null;	

	try {
			DefaultLogger.debug(this,"############# Inside insert log table method: "+log);
			getHibernateTemplate().save(getEntityName(), log);			
			returnObj = (ILEIValidationLog) getHibernateTemplate().load(
				getEntityName(), new Long(log.getId()));
	} catch (Exception obe) {
		DefaultLogger.error(this,"############# error in insertInterfaceLog ", obe);
		obe.printStackTrace();
		throw obe;
	}
	return returnObj;

}

@Override
public ILEIValidationLog createOrUpdateInterfaceLog(ILEIValidationLog log) throws Exception {
	DefaultLogger.debug(this,"############# Inside createOrUpdateInterfaceLog method "+ log);
	DefaultLogger.debug(this,"############# Inside createOrUpdateInterfaceLog method logID "+ log.getId());
	ILEIValidationLog returnObj = null;

	try {
		if(log.getId() > 0) {
			DefaultLogger.debug(this,"############# Inside if condition as its an update"+ log.getId());
			getHibernateTemplate().update(getEntityName(), log);
		}else {
			DefaultLogger.debug(this,"############# Inside else condition as its an insert"+log);
			getHibernateTemplate().save(getEntityName(), log);
		}
		returnObj = (ILEIValidationLog) getHibernateTemplate().load(
				getEntityName(), new Long(log.getId()));
	} catch (Exception obe) {
		DefaultLogger.error(this,"############# error in createOrUpdateInterfaceLog ", obe);
		obe.printStackTrace();
		throw obe;
	}
	return returnObj;

}

@Override
public Boolean checkLEIAlreadyValidated (String entityName, String leiCode,String partyId) {
	
	Boolean flag = false;
	try{
		Map parameters = new HashMap();
		parameters.put("leiCode", leiCode);
		parameters.put("status", "Success");
		parameters.put("isLEICodeValidated", 'Y');
		if(partyId!=null && !partyId.isEmpty()){
			parameters.put("partyId", partyId);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters))
		.addOrder(Order.desc("id"));
		
		List list = getHibernateTemplate().findByCriteria(criteria);
		
		if (list != null && !list.isEmpty() && list.size()>0) {
			flag = true;
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}
@Override
public Date fetchLastValidatedDate (String entityName, String leiCode,String partyId) {
	
	Date lastValidatedDate = null;
	try{
		Map parameters = new HashMap();
		parameters.put("leiCode", leiCode);
		parameters.put("status", "Success");
		parameters.put("isLEICodeValidated", 'Y');
		if(partyId!=null && !partyId.isEmpty()){
			parameters.put("partyId", partyId);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters))
		.addOrder(Order.desc("id"));
		
		List list = getHibernateTemplate().findByCriteria(criteria);
		
		if (list != null && !list.isEmpty() && list.size()>0) {
			lastValidatedDate = ((OBLEIValidationLog)list.get(0)).getLastValidatedDate();
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return lastValidatedDate;
}

@Override
public ILEIValidationLog getInterfaceLog(long id) throws Exception {
	return (ILEIValidationLog) getHibernateTemplate().get(getEntityName(), id);
}

}

