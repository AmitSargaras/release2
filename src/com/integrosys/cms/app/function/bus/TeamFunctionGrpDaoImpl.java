package com.integrosys.cms.app.function.bus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class TeamFunctionGrpDaoImpl extends HibernateDaoSupport implements ITeamFunctionGrpDao {

	public ITeamFunctionGrp createTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp) {
		teamFunctionGrp.setStatus("A");
		Long key = (Long) getHibernateTemplate().save(entityName, teamFunctionGrp);
		teamFunctionGrp.setTeamFunctionGrpID(key.longValue());
		return teamFunctionGrp;
	}

	public ITeamFunctionGrp updateTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp) {
		getHibernateTemplate().update(entityName, teamFunctionGrp);
		return (ITeamFunctionGrp) getHibernateTemplate().get(entityName,
				new Long(teamFunctionGrp.getTeamFunctionGrpID()));
	}

	public void deleteTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp) {
		getHibernateTemplate().delete(entityName, teamFunctionGrp);
	}

	public ITeamFunctionGrp getTeamFunctionGrpByPrimaryKey(String entityName, Serializable key) {
		return (ITeamFunctionGrp) getHibernateTemplate().get(entityName, key);
	}

	public ITeamFunctionGrp getTeamFunctionGrpByTeamID(String entityName, Serializable teamKey) {
		Map parametersMap = new HashMap();
		parametersMap.put("teamId", teamKey);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (ITeamFunctionGrp) resultList.get(0);
	}

	public List getTeamFunctionGrpByTeamType(String entityName, Serializable teamTypeKey) {
		Map parametersMap = new HashMap();
		parametersMap.put("teamTypeId", teamTypeKey);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	public List getActiveTeamFunctionGrp(String entityName, Serializable teamTypeKey) {
		Map parametersMap = new HashMap();
		parametersMap.put("teamTypeId", teamTypeKey);
		parametersMap.put("status", "A");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	public List getTeamFunctionGrpByTeamId(String entityName, Serializable teamKey) {
		Map parametersMap = new HashMap();
		parametersMap.put("teamId", teamKey);
		parametersMap.put("status", "A");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	public List getTeamFunctionGrpByGroupId(String entityName, Serializable groupKey) {

		Map parametersMap = new HashMap();
		parametersMap.put("groupId", groupKey);
		parametersMap.put("status", "A");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}
}
