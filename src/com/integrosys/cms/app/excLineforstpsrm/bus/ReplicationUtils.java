package com.integrosys.cms.app.excLineforstpsrm.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class ReplicationUtils {

	public static IExcLineForSTPSRM replicateForCreateStagingCopy(IExcLineForSTPSRM item) {
		IExcLineForSTPSRM replicatedIdx = (IExcLineForSTPSRM) ReplicateUtils.replicateObject(item,
				new String[] { "id"});

        return replicatedIdx;
	}
}