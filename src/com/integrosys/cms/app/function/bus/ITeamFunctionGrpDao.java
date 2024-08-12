package com.integrosys.cms.app.function.bus;

import java.io.Serializable;
import java.util.List;

public interface ITeamFunctionGrpDao {

	public static final String ACTUAL_TEAM_FUNCTION_GRP = "actualTeamFunctionGrp";
	public static final String STAGING_TEAM_FUNCTION_GRP = "stagingTeamFunctionGrp";
	
	public ITeamFunctionGrp createTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp);
	public ITeamFunctionGrp updateTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp);
	public void deleteTeamFunctionGrp(String entityName, ITeamFunctionGrp teamFunctionGrp);
	public ITeamFunctionGrp getTeamFunctionGrpByPrimaryKey(String entityName, Serializable key);
	public ITeamFunctionGrp getTeamFunctionGrpByTeamID(String entityName, Serializable teamKey);
	public List getTeamFunctionGrpByTeamType(String entityName, Serializable teamTypeKey);
	public List getActiveTeamFunctionGrp(String entityName, Serializable teamTypeKey);
	public List getTeamFunctionGrpByTeamId(String entityName, Serializable teamKey);
	public List getTeamFunctionGrpByGroupId(String entityName, Serializable groupKey);
}
