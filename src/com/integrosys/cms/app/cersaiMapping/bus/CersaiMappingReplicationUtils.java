package com.integrosys.cms.app.cersaiMapping.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;

public class CersaiMappingReplicationUtils {

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
	public static ICersaiMapping replicateCersaiMappingForCreateStagingCopy(ICersaiMapping cersaiMapping) {

		ICersaiMapping replicatedIdx = (ICersaiMapping) ReplicateUtils.replicateObject(cersaiMapping,
				new String[] { "id"});

        return replicatedIdx;
	}
}
