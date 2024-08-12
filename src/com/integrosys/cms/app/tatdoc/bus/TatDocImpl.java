package com.integrosys.cms.app.tatdoc.bus;

import java.util.*;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.tatduration.bus.ITatParam;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 1, 2008 Time: 5:27:45 PM To
 * change this template use File | Settings | File Templates.
 */
public class TatDocImpl extends HibernateDaoSupport implements ITatDocDAO {

	// **************** Implementation Methods ******************
	public ITatDoc create(String entityName, ITatDoc tatDoc) {
		Long key = (Long) getHibernateTemplate().save(entityName, tatDoc);
		return getTatDocByID(entityName, key);
	}

	public ITatDoc update(String entityName, ITatDoc tatDoc) {
		getHibernateTemplate().update(entityName, tatDoc);
		return getTatDocByID(entityName, tatDoc.getTatDocID());
	}

	public ITatDoc getTatDocByID(String entityName, long tatDocID) {
		return getTatDocByID(entityName, new Long(tatDocID));
	}

	public ITatDoc getTatDocByID(String entityName, Long tatDocID) {
		return (ITatDoc) getHibernateTemplate().get(entityName, tatDocID);
	}

	public ITatDoc getTatDocByLimitProfileID(String entityName, long limitProfileID) {
		Map parametersMap = new HashMap();
		parametersMap.put("limitProfileID", new Long(limitProfileID));
		parametersMap.put("status", "ACTIVE");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap))
				.addOrder(Order.desc("tatDocID"));
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (ITatDoc) resultList.get(0);
	}
	
	public ITatParam getTatParamByAppType(String appType)
	{
		Map parametersMap = new HashMap();
		parametersMap.put("applicationType", appType);
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualTatParam").add(Restrictions.allEq(parametersMap));
		List result = getHibernateTemplate().findByCriteria(criteria);
		
		ITatParam tatParam = null;
		if(!result.isEmpty())
			tatParam = (ITatParam) result.get(0);
		return tatParam;
	}

    public void commitTatTrackingList(OBTatLimitTrack trackOB) {
        if (trackOB != null) {            
//            Map parametersMap = new HashMap();
//            parametersMap.put("limitProfileId", new Long(trackOB.getLimitProfileId()));
//
//            DetachedCriteria criteria = DetachedCriteria.forEntityName("tatLimitTrack").add(Restrictions.allEq(parametersMap));
//            List resultList = getHibernateTemplate().findByCriteria(criteria);
//
//            if (!resultList.isEmpty()) {
//                //update
//                getHibernateTemplate().update("tatLimitTrack", trackOB);
//            } else {
//                //create
//                getHibernateTemplate().save("tatLimitTrack", trackOB);
//            }
            getHibernateTemplate().saveOrUpdate("tatLimitTrack", trackOB);
        }
    }

}
