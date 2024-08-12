package com.integrosys.cms.app.eod.sync.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.udf.bus.UdfException;

public class EODSyncStatusDaoImpl extends HibernateDaoSupport implements IEODSyncStatusDao {
	
	public IEODSyncStatus findEODSyncActivityByProcessingKey(String processKey) throws EODSyncStatusException {
			if (processKey == null) {
				throw new EODSyncStatusException("EODSyncStatusDaoImpl.findEODSyncActivitiesBySyncDirection(String): processingKey cannot be null.");
			}
			
			DetachedCriteria criteria = DetachedCriteria.forEntityName("eodSyncStatus").add(Restrictions.eq("processKey", processKey));
			/*updating the code to support hibernate 4 jars
			List<OBEODSyncStatus> eodStatusList =  getHibernateTemplate().findByCriteria(criteria);*/
		    List<OBEODSyncStatus> eodStatusList =  (List<OBEODSyncStatus>) getHibernateTemplate().findByCriteria(criteria);

		    if(eodStatusList!=null && ! eodStatusList.isEmpty()){
		    	return eodStatusList.get(0);
		    }else{
		    	return null;
		    }
	}
	

	public List<OBEODSyncStatus> findEODSyncActivitiesBySyncDirection(
			String syncDirection) throws EODSyncStatusException {
			if (syncDirection == null) {
				throw new EODSyncStatusException("EODSyncStatusDaoImpl.findEODSyncActivitiesBySyncDirection(String): syncDirection cannot be null.");
			}
			DetachedCriteria criteria = DetachedCriteria.forEntityName("eodSyncStatus").add(Restrictions.eq("syncDirection", syncDirection));
			/*updating the code to support hibernate 4 jars
			List<OBEODSyncStatus> eodStatusList =  getHibernateTemplate().findByCriteria(criteria);*/
		    List<OBEODSyncStatus> eodStatusList =  (List<OBEODSyncStatus>) getHibernateTemplate().findByCriteria(criteria);
		    return eodStatusList;
		}
	
	public List<IEODSyncStatus> updateAllEODSyncActivites(List eodSyncStatusList) throws EODSyncStatusException { 
		List<IEODSyncStatus> eodSyncActivityList = null;
		if (eodSyncStatusList == null || eodSyncStatusList.isEmpty() ) {
			eodSyncActivityList = new ArrayList<IEODSyncStatus>();
		}
		else {
			Iterator<OBEODSyncStatus> eodStatusListIterator = eodSyncStatusList.iterator();
			IEODSyncStatus eodStatus;
			while (eodStatusListIterator.hasNext()) {
				eodStatus = (IEODSyncStatus)eodStatusListIterator.next();
				eodSyncActivityList.add(this.updateEODSyncActivity(eodStatus));				
			}
		}
		return eodSyncActivityList;
	}
	public IEODSyncStatus updateEODSyncActivity(IEODSyncStatus eodSyncStatus) throws EODSyncStatusException {
		if (eodSyncStatus == null) {
			throw new UdfException("EODSyncStatusDaoImpl.updateEODSyncActivity(IEODSyncStatus): IEODStatus cannot be null.");
		}
		DefaultLogger.debug(this,"before updating status pending "+eodSyncStatus.getProcessStatus());
		System.out.println("before updating status pending "+eodSyncStatus.getProcessStatus());
		getHibernateTemplate().update("eodSyncStatus",eodSyncStatus);
		DefaultLogger.debug(this,"after updating status pending "+eodSyncStatus.getProcessStatus());
		System.out.println("after updating status pending "+eodSyncStatus.getProcessStatus());
		return  (IEODSyncStatus) getHibernateTemplate().load("eodSyncStatus", new Long(eodSyncStatus.getId()));
	}
	
	public List<IEODSyncStatus> findEODSyncActivities() throws EODSyncStatusException {
		List<IEODSyncStatus> eodSyncStatusList = (List<IEODSyncStatus>) getHibernateTemplate().find(" FROM eodSyncStatus");
		if (eodSyncStatusList == null) {
			eodSyncStatusList = new ArrayList<IEODSyncStatus>();
		}else{
			Collections.sort(eodSyncStatusList, new Comparator<IEODSyncStatus>() {
				public int compare(IEODSyncStatus o1,
						IEODSyncStatus o2) {
					return (o1.getId()).compareTo(o2.getId());
				}
			});
		}
		return eodSyncStatusList;
	}
	public void logEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		OBEODSyncStatusLog syncStatusLog;
		try {
			
		List<OBEODSyncStatus> eodSyncActivityList = findEODSyncActivitiesBySyncDirection(syncDirection);
		DefaultLogger.info(this, "Number of items logging for EOD sync status: "+eodSyncActivityList.size());
		for (IEODSyncStatus syncStatus : eodSyncActivityList) {
			syncStatusLog= new OBEODSyncStatusLog();
			syncStatusLog.setApplicationDate(syncStatus.getApplicationDate());
			syncStatusLog.setCurrentDate(syncStatus.getCurrentDate());
			syncStatusLog.setSyncDirection(syncStatus.getSyncDirection());
			syncStatusLog.setProcessKey(syncStatus.getProcessKey());
			syncStatusLog.setProcessDesc(syncStatus.getProcessDesc());
			syncStatusLog.setProcessStartTime(syncStatus.getProcessStartTime());
			syncStatusLog.setProcessEndTime(syncStatus.getProcessEndTime());
			syncStatusLog.setProcessStatus(syncStatus.getProcessStatus());
			syncStatusLog.setProcessException(syncStatus.getProcessException());
			syncStatusLog.setTotalCount(syncStatus.getTotalCount());
			syncStatusLog.setSuccessCount(syncStatus.getSuccessCount());
			syncStatusLog.setFailedCount(syncStatus.getFailedCount());
			getHibernateTemplate().save("eodSyncStatusLog",syncStatusLog);
		}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error while logging sync status.", e);
			throw new EODSyncStatusException("Error while logging sync status.");
		}
	}
	public void resetEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		try {
			List<OBEODSyncStatus> eodSyncActivityList = findEODSyncActivitiesBySyncDirection(syncDirection);
			
			for (IEODSyncStatus syncStatus : eodSyncActivityList) {
				syncStatus.setProcessStartTime(null);
				syncStatus.setProcessEndTime(null);
				syncStatus.setProcessStatus(EODSyncProcessStatus.PENDING_EXECUTION.name());
				syncStatus.setProcessException("");
				syncStatus.setTotalCount(0);
				syncStatus.setSuccessCount(0);
				syncStatus.setFailedCount(0);
				getHibernateTemplate().update("eodSyncStatus",syncStatus);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error while reseting sync status ", e);
			throw new EODSyncStatusException("Error while reseting sync status.");
		}
	}
}
