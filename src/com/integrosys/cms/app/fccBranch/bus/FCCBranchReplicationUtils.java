package com.integrosys.cms.app.fccBranch.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class FCCBranchReplicationUtils {

	/**
	 * <p>
	 * Replicate CaseBranch which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IFCCBranch replicateFCCBranchForCreateStagingCopy(IFCCBranch fccBranch) {

        IFCCBranch replicatedIdx = (IFCCBranch) ReplicateUtils.replicateObject(fccBranch,
				new String[] { "id"});

        return replicatedIdx;
	}
}