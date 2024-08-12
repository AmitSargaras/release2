package com.integrosys.cms.app.tatduration.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 1, 2008 Time: 5:27:45 PM To
 * change this template use File | Settings | File Templates.
 */
public class TatParamDaoImpl extends HibernateDaoSupport implements ITatParamDAO 
{
	// **************** Implementation Methods ******************
	public ITatParam create(String entityName, ITatParam tatParam) 
	{
		Long key = (Long) getHibernateTemplate().save(entityName, tatParam);
		return getTatParam(entityName, key);
	}

	public ITatParam update(String entityName, ITatParam tatParam) {
		getHibernateTemplate().update(entityName, tatParam);
		return getTatParam(entityName, new Long(tatParam.getTatParamId()));
	}

	/*public ITatParamStage getTatParamByID(String entityName, long tatParamID) {
		return getTatParamStageByID(entityName, new Long(tatParamID));
	}*/

	public ITatParam getTatParam(String entityName, Long key) throws HibernateException
	{
		return (ITatParam) getHibernateTemplate().get(entityName, key);
	}
	
	public ITatParamItem getStageTatParamItem(Long tatParamItemId) throws HibernateException
	{
		return (ITatParamItem) getHibernateTemplate().get("stageTatParamItem", tatParamItemId);
	}
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws HibernateException
	{
		return (ITatParamItem) getHibernateTemplate().get("actualTatParamItem", tatParamItemId);
	}
	
	public ITatParam getTatParam(Long tatParamId) throws HibernateException
	{
		return (ITatParam) getHibernateTemplate().get("actualTatParam", tatParamId);
	}
	
	public ITatParam getTatParamByApplicationType(String appType) throws HibernateException
	{
		Map parametersMap = new HashMap();
		parametersMap.put("applicationType", appType);
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualTatParam").add(Restrictions.allEq(parametersMap));
		return (ITatParam) getHibernateTemplate().findByCriteria(criteria);	
//		return null;	
	}

}
