package com.integrosys.cms.app.caseCreationUpdate.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * 
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author abhijit.rudrakshawar
 * 
 */
public abstract class CaseCreationReplicationUtils {

	/**
	 * <p>
	 * Replicate CaseCreation which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ICaseCreation replicateCaseCreationForCreateStagingCopy(ICaseCreation caseCreationUpdate) {

        ICaseCreation replicatedIdx = (ICaseCreation) ReplicateUtils.replicateObject(caseCreationUpdate,
				new String[] { "id"});

        return replicatedIdx;
	}
}