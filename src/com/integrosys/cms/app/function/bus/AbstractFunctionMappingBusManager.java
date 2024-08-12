package com.integrosys.cms.app.function.bus;

import java.util.List;

public abstract class AbstractFunctionMappingBusManager implements ITeamFunctionGrpBusManager {
	private ITeamFunctionGrpDao teamFunctionGrpDao;

	public ITeamFunctionGrpDao getTeamFunctionGrpDao() {
		return teamFunctionGrpDao;
	}

	public void setTeamFunctionGrpDao(ITeamFunctionGrpDao teamFunctionGrpDao) {
		this.teamFunctionGrpDao = teamFunctionGrpDao;
	}

	public ITeamFunctionGrp getTeamFunctionGrp(long key) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getTeamFunctionGrpByPrimaryKey(getFunctionGroupMappingEntityName(),
				new Long(key));
	}

	public ITeamFunctionGrp createTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().createTeamFunctionGrp(getFunctionGroupMappingEntityName(), func);
	}

	public ITeamFunctionGrp updateTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().updateTeamFunctionGrp(getFunctionGroupMappingEntityName(), func);
	}

	public ITeamFunctionGrp deleteTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException {
		getTeamFunctionGrpDao().deleteTeamFunctionGrp(getFunctionGroupMappingEntityName(), func);
		return func;
	}

	public ITeamFunctionGrp getTeamFunctionGrpByTeamID(long teamKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getTeamFunctionGrpByTeamID(getFunctionGroupMappingEntityName(),
				new Long(teamKey));
	}

	public List getTeamFunctionGrpByTeamType(long teamTypeKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getTeamFunctionGrpByTeamType(getFunctionGroupMappingEntityName(),
				new Long(teamTypeKey));
	}

	public List getActiveTeamFunctionGrp(long teamTypeKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getActiveTeamFunctionGrp(getFunctionGroupMappingEntityName(),
				new Long(teamTypeKey));
	}
	
	public List getTeamFunctionGrpByTeamId(long teamKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getTeamFunctionGrpByTeamId(getFunctionGroupMappingEntityName(), new Long(teamKey));
	}
	
	public List getTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpDao().getTeamFunctionGrpByGroupId(getFunctionGroupMappingEntityName(), new Long(groupKey));
	}

	public abstract String getFunctionGroupMappingEntityName();
}
