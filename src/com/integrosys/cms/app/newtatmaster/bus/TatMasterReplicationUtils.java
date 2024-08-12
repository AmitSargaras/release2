package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.component.bus.IComponent;

public abstract class TatMasterReplicationUtils {
	


	public static INewTatMaster replicateTatMasterForCreateStagingCopy(INewTatMaster tatMaster) {

		INewTatMaster replicatedIdx = (INewTatMaster) ReplicateUtils.replicateObject(tatMaster,
				new String[] { "id"});

        return replicatedIdx;
	}


}
