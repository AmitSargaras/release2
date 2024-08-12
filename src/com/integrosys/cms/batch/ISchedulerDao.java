package com.integrosys.cms.batch;

public interface ISchedulerDao {
	
	public OBSchedulerLog createSchedulerLog(OBSchedulerLog log);

	public OBSchedulerLog updateSchedulerLog(OBSchedulerLog log);
}
