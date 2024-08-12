package com.integrosys.cms.app.function.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

public class TeamFunctionGrpBusManagerImpl extends AbstractFunctionMappingBusManager {

	public String getFunctionGroupMappingEntityName() {
		return ITeamFunctionGrpDao.ACTUAL_TEAM_FUNCTION_GRP;
	}

	public List updateToWorkingCopy(List workingCopy, List imageCopy) throws TeamFunctionGrpException {
		// TODO
		List listTeamFunctionGrp = (List) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy,
				imageCopy, new String[] { "teamId", "functionGrpId", "groupId" }, new String[] { "teamFunctionGrpID" });
		List listActualTeamFunctionGrp = new ArrayList();

		for (int i = 0; i < listTeamFunctionGrp.size(); i++) {
			ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) listTeamFunctionGrp.get(i);
			if (getTeamFunctionGrp(teamFunctionGrp.getTeamFunctionGrpID()) != null) {
				teamFunctionGrp = updateTeamFunctionGrp(teamFunctionGrp);
			}
			else {
				teamFunctionGrp = createTeamFunctionGrp(teamFunctionGrp);
			}
			listActualTeamFunctionGrp.add(teamFunctionGrp);
		}
		List listTeamFunctionGrpToRemove = (List) EntityAssociationUtils.retrieveRemovedObjectsCollection(workingCopy,
				imageCopy, new String[] { "teamId", "functionGrpId", "groupId" });

		for (int i = 0; i < listTeamFunctionGrpToRemove.size(); i++) {
			ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) listTeamFunctionGrpToRemove.get(i);
			deleteTeamFunctionGrp(teamFunctionGrp);
		}
		return listActualTeamFunctionGrp;
	}
}
