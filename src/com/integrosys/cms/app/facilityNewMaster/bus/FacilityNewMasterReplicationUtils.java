package com.integrosys.cms.app.facilityNewMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class FacilityNewMasterReplicationUtils {

	/**
	 * <p>
	 * Replicate FacilityNewMaster which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IFacilityNewMaster replicateFacilityNewMasterForCreateStagingCopy(IFacilityNewMaster facilityNewMaster) {

        IFacilityNewMaster replicatedIdx = (IFacilityNewMaster) ReplicateUtils.replicateObject(facilityNewMaster,
				new String[] { "id"});

        return replicatedIdx;
	}
}