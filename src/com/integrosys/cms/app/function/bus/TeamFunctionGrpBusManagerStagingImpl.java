package com.integrosys.cms.app.function.bus;

import java.util.List;

public class TeamFunctionGrpBusManagerStagingImpl extends AbstractFunctionMappingBusManager{

	public String getFunctionGroupMappingEntityName() {
		return ITeamFunctionGrpDao.STAGING_TEAM_FUNCTION_GRP;
	}

	public List updateToWorkingCopy(List workingCopy, List imageCopy)
			throws TeamFunctionGrpException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented staging bus manager");
	}
}
