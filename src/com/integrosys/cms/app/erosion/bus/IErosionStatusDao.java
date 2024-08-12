package com.integrosys.cms.app.erosion.bus;

import java.util.List;

public interface IErosionStatusDao {
	
	public List updateAllErosionActivites(List erosionStatusList) throws ErosionStatusException;
	public IErosionStatus updateErosionActivity(IErosionStatus udf) throws ErosionStatusException;
	public List findErosionActivities(boolean isCreate) throws ErosionStatusException;	
	public List findErosionActivitiesByStatus(String status) throws ErosionStatusException;

}
