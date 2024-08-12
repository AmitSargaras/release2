package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * <p>
 * Limit ORM based DAO implementation using Hibernate persistence framework.
 * 
 * @author Chong Jun Yong
 * @since 02.09.2008
 */
public class FacilityDaoImpl extends HibernateDaoSupport implements IFacilityDao {

	public Serializable createFacilityMaster(String entityName, IFacilityMaster facilityMaster) {
		return getHibernateTemplate().save(entityName, facilityMaster);
	}

	public void deleteFacilityMaster(String entityName, IFacilityMaster facilityMaster) {
		IFacilityMaster loadedFacilityMaster = (IFacilityMaster) getHibernateTemplate().load(entityName,
				new Long(facilityMaster.getId()));

		getHibernateTemplate().delete(entityName, loadedFacilityMaster);
	}

	public IFacilityMaster findFacilityMasterByCmsLimitId(String entityName, long cmsLimitId) {
		Map parameters = new HashMap();
		parameters.put("limit.limitID", new Long(cmsLimitId));

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters))
				.addOrder(Order.desc("id"));

		List resultList = getHibernateTemplate().findByCriteria(criteria);
		if (resultList.isEmpty()) {
			return null;
		}

		return (IFacilityMaster) resultList.get(0);
	}

	public IFacilityMaster findFacilityMasterByLimitRef(String entityName, String limitRef) {

		StringBuffer hqlBuf = new StringBuffer();
		hqlBuf.append("select lmt.limitID from actualLimit lmt where lmt.limitRef = ?");

		List cmsLimitIdList = getHibernateTemplate().find(hqlBuf.toString(), limitRef);

		if (cmsLimitIdList == null || cmsLimitIdList.isEmpty()) {
			return null;
		}

		Long cmsLimitId = (Long) cmsLimitIdList.get(0);

		return findFacilityMasterByCmsLimitId(entityName, cmsLimitId.longValue());
	}

	public IFacilityMaster updateFacilityMaster(String entityName, IFacilityMaster facilityMaster) {
		getHibernateTemplate().update(entityName, facilityMaster);

		return (IFacilityMaster) getHibernateTemplate().load(entityName, new Long(facilityMaster.getId()));
	}

	public List findFacilityMasterByCmsLimitIdList(String entityName, List cmsLimitIdList) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(
				Restrictions.in("limit.limitID", cmsLimitIdList));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List findFacilityMasterByLimitRefList(String entityName, List limitRefList) {
		if (limitRefList == null || limitRefList.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List facilityMasterList = new ArrayList();
		for (Iterator itr = limitRefList.iterator(); itr.hasNext();) {
			String limitRef = (String) itr.next();
			IFacilityMaster facilityMaster = findFacilityMasterByLimitRef(entityName, limitRef);
			if (facilityMaster != null) {
				facilityMasterList.add(facilityMaster);
			}
		}

		return facilityMasterList;
	}

	public IFacilityMaster retrieveFacilityMasterByPrimaryKey(String entityName, Serializable key) {
		return (IFacilityMaster) getHibernateTemplate().get(entityName, key);
	}
}
