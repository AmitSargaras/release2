package com.integrosys.cms.app.caseBranch.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class CaseBranchReplicationUtils {

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
	public static ICaseBranch replicateCaseBranchForCreateStagingCopy(ICaseBranch caseBranch) {

        ICaseBranch replicatedIdx = (ICaseBranch) ReplicateUtils.replicateObject(caseBranch,
				new String[] { "id"});

        return replicatedIdx;
	}
}