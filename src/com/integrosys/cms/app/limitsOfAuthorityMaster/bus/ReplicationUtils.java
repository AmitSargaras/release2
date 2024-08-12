package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class ReplicationUtils {

	public static ILimitsOfAuthorityMaster replicateForCreateStagingCopy(ILimitsOfAuthorityMaster item) {
		ILimitsOfAuthorityMaster replicatedIdx = (ILimitsOfAuthorityMaster) ReplicateUtils.replicateObject(item,
				new String[] { "id"});

        return replicatedIdx;
	}
}