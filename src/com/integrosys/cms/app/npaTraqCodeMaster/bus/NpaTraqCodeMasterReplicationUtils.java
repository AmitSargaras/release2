package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;

public abstract class NpaTraqCodeMasterReplicationUtils {

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
	public static INpaTraqCodeMaster replicateNpaTraqCodeMasterForCreateStagingCopy(INpaTraqCodeMaster npaTraqCodeMaster) {

		INpaTraqCodeMaster replicatedIdx = (INpaTraqCodeMaster) ReplicateUtils.replicateObject(npaTraqCodeMaster,
				new String[] { "id"});

        return replicatedIdx;
	}
}
