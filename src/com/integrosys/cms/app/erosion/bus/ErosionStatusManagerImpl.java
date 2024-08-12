package com.integrosys.cms.app.erosion.bus;

import java.util.List;

public class ErosionStatusManagerImpl implements IErosionStatusManager {

	IErosionStatusDao erosionStatusDao;

	public IErosionStatusDao getErosionStatusDao() {
		return erosionStatusDao;
	}

	public void setErosionStatusDao(IErosionStatusDao erosionStatusDao) {
		this.erosionStatusDao = erosionStatusDao;
	}

	public List findErosionActivities(boolean isCreate) throws ErosionStatusException {
		return erosionStatusDao.findErosionActivities(isCreate);
	}

	public List findErosionActivitiesByStatus(String status) throws ErosionStatusException {
		return erosionStatusDao.findErosionActivitiesByStatus(status);
	}

	public List updateAllErosionActivites(List erosionStatusList) throws ErosionStatusException {
		return erosionStatusDao.updateAllErosionActivites(erosionStatusList);
	}

	public IErosionStatus updateErosionActivity(IErosionStatus erosionStatus)	throws ErosionStatusException {
		return erosionStatusDao.updateErosionActivity(erosionStatus);
	}

}
