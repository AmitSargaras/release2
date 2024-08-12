package com.integrosys.cms.app.erosion.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.erosion.schedular.ErosionStatusComparator;

public class ErosionStatusDaoImpl extends HibernateDaoSupport implements IErosionStatusDao {

	public List findErosionActivities(boolean isCreate) throws ErosionStatusException {
		List erosionStatusList = (List) getHibernateTemplate().loadAll(OBErosionStatus.class);
		Collections.sort(erosionStatusList, new ErosionStatusComparator());
		if (erosionStatusList == null) {
			erosionStatusList = new ArrayList();
		}
		return erosionStatusList;
	}

	public List findErosionActivitiesByStatus(String status) throws ErosionStatusException {
		if (status == null) {
			throw new ErosionStatusException("ErosionStatusDaoImpl.findErosionActivitiesByStatus(String): Status cannot be null.");
		}
		String query = "FROM com.integrosys.cms.app.erosion.bus.OBErosionStatus erosionstatus where status="+status;
	    List erosionStatusList = (ArrayList) getHibernateTemplate().find(query);
	    return erosionStatusList;
	}

	public List updateAllErosionActivites(List erosionStatusList) throws ErosionStatusException { 
		List erosionActivityList = null;
		if (erosionStatusList == null || erosionStatusList.isEmpty() ) {
			erosionActivityList = new ArrayList();
		}
		else {
			Iterator erosionStatusListIterator = erosionStatusList.iterator();
			IErosionStatus erosionStatus;
			while (erosionStatusListIterator.hasNext()) {
				erosionStatus = (IErosionStatus)erosionStatusListIterator.next();
				erosionActivityList.add(this.updateErosionActivity(erosionStatus));				
			}
		}
		return erosionActivityList;
	}

	public IErosionStatus updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException {
		if (erosionStatus == null) {
			throw new ErosionStatusException("ErosionStatusDaoImpl.updateErosionActivity(IErosionStatus): IErosionStatus cannot be null.");
		}
		DefaultLogger.debug(this,"before updating status pending "+erosionStatus.getStatus());
		getHibernateTemplate().update(erosionStatus);
		DefaultLogger.debug(this,"after updating status pending "+erosionStatus.getStatus());
		return  (IErosionStatus) getHibernateTemplate().load(OBErosionStatus.class, new Long(erosionStatus.getId()));
	}
	
}
