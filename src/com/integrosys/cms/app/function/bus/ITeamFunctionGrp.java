package com.integrosys.cms.app.function.bus;

public interface ITeamFunctionGrp extends java.io.Serializable {
	public long getTeamFunctionGrpID();
	public void setTeamFunctionGrpID(long teamFunctionGrpID);
	public long getVersionTime();
	public void setVersionTime(long versionTime);
	public long getTeamId();
	public void setTeamId(long teamId);
	public long getTeamTypeId();
	public void setTeamTypeId(long teamTypeId);
	public long getFunctionGrpId();
	public void setFunctionGrpId(long functionGrpId);
	public long getGroupId();
	public void setGroupId(long groupId);
	public String getStatus();
	public void setStatus(String status);
}
