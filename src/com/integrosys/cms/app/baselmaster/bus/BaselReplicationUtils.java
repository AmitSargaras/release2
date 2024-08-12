package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.component.bus.IComponent;

public abstract class BaselReplicationUtils {

	public static IBaselMaster replicateBaselForCreateStagingCopy(IBaselMaster basel) {

		IBaselMaster replicatedIdx = (IBaselMaster) ReplicateUtils.replicateObject(basel,
				new String[] { "id"});

        return replicatedIdx;
	}
}
