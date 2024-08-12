package com.integrosys.cms.app.holiday.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class HolidayReplicationUtils {

	/**
	 * <p>
	 * Replicate Holiday which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IHoliday replicateHolidayForCreateStagingCopy(IHoliday holiday) {

        IHoliday replicatedIdx = (IHoliday) ReplicateUtils.replicateObject(holiday,
				new String[] { "id"});

        return replicatedIdx;
	}
}