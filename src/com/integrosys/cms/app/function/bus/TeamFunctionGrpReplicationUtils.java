package com.integrosys.cms.app.function.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public class TeamFunctionGrpReplicationUtils {
	public static ITeamFunctionGrp replicateTeamFunctionGrpForCreateStagingCopy(ITeamFunctionGrp teamFunctionGrp) {
		
		ITeamFunctionGrp replicatedTeamFunctionGrp = (ITeamFunctionGrp) ReplicateUtils.replicateObject(teamFunctionGrp,
				new String[] { "teamFunctionGrpID" });

		return replicatedTeamFunctionGrp;
	}
}
