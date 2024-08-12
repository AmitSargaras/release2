package com.integrosys.cms.app.goodsMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class GoodsMasterReplicationUtils {

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
	public static IGoodsMaster replicateGoodsMasterForCreateStagingCopy(IGoodsMaster goodsMaster) {

		IGoodsMaster replicatedIdx = (IGoodsMaster) ReplicateUtils.replicateObject(goodsMaster,
				new String[] { "id"});

        return replicatedIdx;
	}
}
