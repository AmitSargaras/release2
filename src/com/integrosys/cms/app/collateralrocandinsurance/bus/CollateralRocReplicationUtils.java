package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public class CollateralRocReplicationUtils {

	/**
	 * <p>
	 * Replicate CollateralRoc which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ICollateralRoc replicateCollateralRocForCreateStagingCopy(ICollateralRoc collateralRoc) {

		ICollateralRoc replicatedIdx = (ICollateralRoc) ReplicateUtils.replicateObject(collateralRoc,
				new String[] { "id"});

        return replicatedIdx;
	}
}
