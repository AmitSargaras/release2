package com.integrosys.cms.app.eod.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.eod.AdfRbiStatusComparator;
import com.integrosys.cms.batch.eod.EODStatusComparator;

public class EODStatusDaoImpl extends HibernateDaoSupport implements IEODStatusDao {

	public List findEODActivities() throws EODStatusException {
		List eodStatusList = (List) getHibernateTemplate().loadAll(OBEODStatus.class);
		Collections.sort(eodStatusList, new EODStatusComparator());
		if (eodStatusList == null) {
			eodStatusList = new ArrayList();
		}
		return eodStatusList;
	}

	public List findEODActivitiesByStatus(String status) throws EODStatusException {
		if (status == null) {
			throw new EODStatusException("EODStatusDaoImpl.findEODActivitiesByStatus(String): Status cannot be null.");
		}
		String query = "FROM com.integrosys.cms.app.eod.bus.OBEODStatus eodstatus where status="+status;
	    List eodStatusList = (ArrayList) getHibernateTemplate().find(query);
	    return eodStatusList;
	}

	public List updateAllEODActivites(List eodStatusList) throws EODStatusException { 
		List eodActivityList = null;
		if (eodStatusList == null || eodStatusList.isEmpty() ) {
			eodActivityList = new ArrayList();
		}
		else {
			Iterator eodStatusListIterator = eodStatusList.iterator();
			IEODStatus eodStatus;
			while (eodStatusListIterator.hasNext()) {
				eodStatus = (IEODStatus)eodStatusListIterator.next();
				eodActivityList.add(this.updateEODActivity(eodStatus));				
			}
		}
		return eodActivityList;
	}

	public IEODStatus updateEODActivity(IEODStatus eodStatus) throws EODStatusException {
		if (eodStatus == null) {
			throw new EODStatusException("EODStatusDaoImpl.updateEODActivity(IEODStatus): IEODStatus cannot be null.");
		}
		DefaultLogger.debug(this,"before updating status pending "+eodStatus.getStatus());
		System.out.println("before updating status pending "+eodStatus.getStatus());
		getHibernateTemplate().update(eodStatus);
		DefaultLogger.debug(this,"after updating status pending "+eodStatus.getStatus());
		System.out.println("after updating status pending "+eodStatus.getStatus());
		return  (IEODStatus) getHibernateTemplate().load(OBEODStatus.class, new Long(eodStatus.getId()));
	}
	
	public List findAdfRbiActivities() throws EODStatusException {
		List eodStatusList = (List) getHibernateTemplate().loadAll(OBAdfRbiStatus.class);
		Collections.sort(eodStatusList, new AdfRbiStatusComparator());
		if (eodStatusList == null) {
			eodStatusList = new ArrayList();
		}
		return eodStatusList;
	}
	
	public IAdfRbiStatus updateAdfRbiActivity(IAdfRbiStatus eodStatus) throws EODStatusException {
		if (eodStatus == null) {
			throw new EODStatusException("EODStatusDaoImpl.updateEODActivity(IEODStatus): IEODStatus cannot be null.");
		}
		getHibernateTemplate().update(eodStatus);
		return  (IAdfRbiStatus) getHibernateTemplate().load(OBAdfRbiStatus.class, new Long(eodStatus.getId()));
	}

	
	
		
	
}
