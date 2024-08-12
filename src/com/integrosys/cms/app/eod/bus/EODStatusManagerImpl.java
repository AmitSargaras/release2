package com.integrosys.cms.app.eod.bus;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public class EODStatusManagerImpl implements IEODStatusManager {

	IEODStatusDao eodStatusDao;

	public IEODStatusDao getEodStatusDao() {
		return eodStatusDao;
	}

	public void setEodStatusDao(IEODStatusDao eodStatusDao) {
		this.eodStatusDao = eodStatusDao;
	}

	public List findEODActivities() throws EODStatusException {
		return eodStatusDao.findEODActivities();
	}

	public List findEODActivitiesByStatus(String status) throws EODStatusException {
		return eodStatusDao.findEODActivitiesByStatus(status);
	}

	public List updateAllEODActivites(List eodStatusList) throws EODStatusException {
		return eodStatusDao.updateAllEODActivites(eodStatusList);
	}

	public IEODStatus updateEODActivity(IEODStatus eodStatus)	throws EODStatusException {
		return eodStatusDao.updateEODActivity(eodStatus);
	}
	public List findAdfRbiActivities() throws EODStatusException {
		return eodStatusDao.findAdfRbiActivities();
	}
	public IAdfRbiStatus updateAdfRbiActivity(IAdfRbiStatus eodStatus)	throws EODStatusException {
		return eodStatusDao.updateAdfRbiActivity(eodStatus);
	}
}
