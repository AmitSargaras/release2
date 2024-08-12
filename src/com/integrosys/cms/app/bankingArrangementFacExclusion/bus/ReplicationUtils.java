package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class ReplicationUtils {

	public static IBankingArrangementFacExclusion replicateForCreateStagingCopy(IBankingArrangementFacExclusion item) {
		IBankingArrangementFacExclusion replicatedIdx = (IBankingArrangementFacExclusion) ReplicateUtils.replicateObject(item,
				new String[] { "id"});

        return replicatedIdx;
	}
}