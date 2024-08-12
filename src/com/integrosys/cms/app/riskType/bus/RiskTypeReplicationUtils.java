package com.integrosys.cms.app.riskType.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class RiskTypeReplicationUtils {

	/**
	 * <p>
	 * Replicate ExcludedFacility which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IRiskType replicateRiskTypeForCreateStagingCopy(IRiskType riskType) {

		IRiskType replicatedIdx = (IRiskType) ReplicateUtils.replicateObject(riskType,
				new String[] { "id"});

        return replicatedIdx;
	}
}
