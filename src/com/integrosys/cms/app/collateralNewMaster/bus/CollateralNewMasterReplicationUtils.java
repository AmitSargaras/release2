package com.integrosys.cms.app.collateralNewMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class CollateralNewMasterReplicationUtils {

	/**
	 * <p>
	 * Replicate CollateralNewMaster which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ICollateralNewMaster replicateCollateralNewMasterForCreateStagingCopy(ICollateralNewMaster collateralNewMaster) {

        ICollateralNewMaster replicatedIdx = (ICollateralNewMaster) ReplicateUtils.replicateObject(collateralNewMaster,
				new String[] { "id"});

        return replicatedIdx;
	}
}