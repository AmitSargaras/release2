package com.integrosys.cms.host.eai.document.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.support.PersistentDaoSupport;

/**
 * ORM Based DAO using hibernate for documentation / checklist module
 * 
 * @author Chong Jun Yong
 * @since 18.08.2008
 * 
 */
public class DocumentDaoImpl extends PersistentDaoSupport implements IDocumentDao {

	public CheckList retrieveBorrowerCheckListByCmsLimitProfileIdAndCmsCustomerId(long cmsLimitProfileId,
			long cmsCustomerId, String subCategory) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCheckListEntity");

		Map parameters = new HashMap();
		parameters.put("limitProfileId", new Long(cmsLimitProfileId));
		parameters.put("customerId", new Long(cmsCustomerId));
		parameters.put("ccChecklist.customerType", subCategory);
		parameters.put("checklistType", "CC");

		criteria.add(Restrictions.allEq(parameters));
		criteria.add(Restrictions.ne("status", ICMSConstant.STATE_CHECKLIST_OBSOLETE));

		List result = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (result != null && !result.isEmpty()) {
			return (CheckList) result.get(0);
		}

		return null;
	}

	public CheckList retrieveCollateralCheckListByCmsLimitProfileIdAndCmsCollateralId(long cmsLimitProfileId,
			long cmsCollateralId) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCheckListEntity");

		Map parameters = new HashMap();
		parameters.put("limitProfileId", new Long(cmsLimitProfileId));
		parameters.put("scChecklist.cmsCollateralID", new Long(cmsCollateralId));
		parameters.put("checklistType", "S");

		criteria.add(Restrictions.allEq(parameters));
		criteria.add(Restrictions.ne("status", ICMSConstant.STATE_CHECKLIST_OBSOLETE));

		List result = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (result != null && !result.isEmpty()) {
			return (CheckList) result.get(0);
		}

		return null;
	}

	public CheckList retrievePledgorCheckListByCmsLimitProfileIdAndCmsPledgorId(long cmsLimitProfileId,
			long cmsPledgorId) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCheckListEntity");

		Map parameters = new HashMap();
		parameters.put("limitProfileId", new Long(cmsLimitProfileId));
		parameters.put("pledgorId", new Long(cmsPledgorId));
		parameters.put("ccChecklist.customerType", "PLEDGOR");
		parameters.put("checklistType", "CC");

		criteria.add(Restrictions.allEq(parameters));
		criteria.add(Restrictions.ne("status", ICMSConstant.STATE_CHECKLIST_OBSOLETE));

		List result = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (result != null && !result.isEmpty()) {
			return (CheckList) result.get(0);
		}

		return null;
	}

	public CheckListItem[] retrieveCheckListItemsByCmsCheckListId(long cmsCheckListId) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCheckListItemEntity");

		criteria.add(Restrictions.eq("checklistID", new Long(cmsCheckListId)));

		List result = getHibernateTemplate().findByCriteria(criteria);

		if (result != null && !result.isEmpty()) {
			return (CheckListItem[]) result.toArray(new CheckListItem[0]);
		}

		return null;
	}

}
