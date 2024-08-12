package com.integrosys.cms.app.function.bus;

import java.util.List;

public interface ITeamFunctionGrpBusManager {

	public ITeamFunctionGrp getTeamFunctionGrp(long key) throws TeamFunctionGrpException;

	public ITeamFunctionGrp createTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException;

	public ITeamFunctionGrp updateTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException;

	public ITeamFunctionGrp deleteTeamFunctionGrp(ITeamFunctionGrp func) throws TeamFunctionGrpException;

	public ITeamFunctionGrp getTeamFunctionGrpByTeamID(long teamKey) throws TeamFunctionGrpException;

	public List updateToWorkingCopy(List workingCopy, List imageCopy)
			throws TeamFunctionGrpException;

	public List getTeamFunctionGrpByTeamType(long teamTypeKey) throws TeamFunctionGrpException;

	public List getActiveTeamFunctionGrp(long teamTypeKey) throws TeamFunctionGrpException;

	public List getTeamFunctionGrpByTeamId(long teamKey) throws TeamFunctionGrpException;

	public List getTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException;
}
