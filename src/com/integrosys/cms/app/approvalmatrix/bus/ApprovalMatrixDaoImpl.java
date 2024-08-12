package com.integrosys.cms.app.approvalmatrix.bus;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Implementation of {@link IApprovalMatrixDao} using Hibernate
 * 
 * @author Chong Jun Yong
 * @since 1.1
 */
public class ApprovalMatrixDaoImpl extends HibernateDaoSupport implements IApprovalMatrixDao {

	public IApprovalMatrixEntry createApprovalMatrixEntry(String entityName, IApprovalMatrixEntry approvalMatrixEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, approvalMatrixEntry);

		return (IApprovalMatrixEntry) getHibernateTemplate().get(entityName, key);
	}

	public IApprovalMatrixGroup createApprovalMatrixGroup(String entityName, IApprovalMatrixGroup approvalMatrixGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, approvalMatrixGroup);

		return (IApprovalMatrixGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteApprovalMatrixEntry(String entityName, IApprovalMatrixEntry approvalMatrixEntry) {
		getHibernateTemplate().delete(entityName, approvalMatrixEntry);
	}

	public void deleteApprovalMatrixGroup(String entityName, IApprovalMatrixGroup approvalMatrixGroup) {
		getHibernateTemplate().delete(entityName, approvalMatrixGroup);
	}

	public IApprovalMatrixEntry getApprovalMatrixEntryByPrimaryKey(String entityName, Serializable key) {
		return (IApprovalMatrixEntry) getHibernateTemplate().get(entityName, key);
	}

	public IApprovalMatrixGroup getApprovalMatrixGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IApprovalMatrixGroup) resultList.get(0);
	}

	public IApprovalMatrixEntry getApprovalMatrixEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IApprovalMatrixEntry) resultList.get(0);
	}

	public IApprovalMatrixGroup getApprovalMatrixGroupByPrimaryKey(String entityName, Serializable key) {
		return (IApprovalMatrixGroup) getHibernateTemplate().get(entityName, key);
	}

	public IApprovalMatrixEntry updateApprovalMatrixEntry(String entityName, IApprovalMatrixEntry approvalMatrixEntry) {
		getHibernateTemplate().update(entityName, approvalMatrixEntry);

		return (IApprovalMatrixEntry) getHibernateTemplate().get(entityName, new Long(approvalMatrixEntry.getApprovalMatrixEntryID()));
	}

	public IApprovalMatrixGroup updateApprovalMatrixGroup(String entityName, IApprovalMatrixGroup approvalMatrixGroup) {
		getHibernateTemplate().update(entityName, approvalMatrixGroup);

		return (IApprovalMatrixGroup) getHibernateTemplate().get(entityName, new Long(approvalMatrixGroup.getApprovalMatrixGroupID()));
	}
}
