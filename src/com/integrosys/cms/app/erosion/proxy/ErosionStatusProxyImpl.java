package com.integrosys.cms.app.erosion.proxy;

import java.util.List;

import com.integrosys.cms.app.erosion.bus.ErosionStatusException;
import com.integrosys.cms.app.erosion.bus.IErosionStatus;
import com.integrosys.cms.app.erosion.bus.IErosionStatusManager;

public class ErosionStatusProxyImpl implements IErosionStatusProxy {

	IErosionStatusManager erosionStatusManager;
	
	public IErosionStatusManager getErosionStatusManager() {
		return erosionStatusManager;
	}

	public void setErosionStatusManager(IErosionStatusManager erosionStatusManager) {
		this.erosionStatusManager = erosionStatusManager;
	}

	public List findErosionActivities(boolean isCreate) throws ErosionStatusException {
		return erosionStatusManager.findErosionActivities(isCreate);
	}

	public List findErosionActivitiesByStatus(String status) throws ErosionStatusException {
		return erosionStatusManager.findErosionActivitiesByStatus(status);
	}

	public List updateAllErosionActivites(List erosionStatusList) throws ErosionStatusException {
		return erosionStatusManager.updateAllErosionActivites(erosionStatusList);
	}

	public IErosionStatus updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException {
		return erosionStatusManager.updateErosionActivity(erosionStatus);
	}

}
