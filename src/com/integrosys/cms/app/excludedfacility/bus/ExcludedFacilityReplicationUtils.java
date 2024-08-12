package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class ExcludedFacilityReplicationUtils {

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
	public static IExcludedFacility replicateExcludedFacilityForCreateStagingCopy(IExcludedFacility excludedFacility) {

		IExcludedFacility replicatedIdx = (IExcludedFacility) ReplicateUtils.replicateObject(excludedFacility,
				new String[] { "id"});

        return replicatedIdx;
	}
}
