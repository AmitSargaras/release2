package com.integrosys.cms.app.erosion.proxy;

import java.util.List;
import com.integrosys.cms.app.erosion.bus.ErosionStatusException;
import com.integrosys.cms.app.erosion.bus.IErosionStatus;

public interface IErosionStatusProxy {

	public List updateAllErosionActivites(List erosionStatusList) throws ErosionStatusException;
	public IErosionStatus updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException;
	public List findErosionActivities(boolean isCreate) throws ErosionStatusException;	
	public List findErosionActivitiesByStatus(String status) throws ErosionStatusException;

}
