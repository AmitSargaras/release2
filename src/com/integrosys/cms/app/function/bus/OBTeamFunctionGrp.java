package com.integrosys.cms.app.function.bus;

public class OBTeamFunctionGrp implements ITeamFunctionGrp {

	private long teamFunctionGrpID;

	private long versionTime;

	private long teamId;

	private long teamTypeId;

	private long functionGrpId;

	private long groupId;

	private String status;

	public long getTeamFunctionGrpID() {
		return teamFunctionGrpID;
	}

	public void setTeamFunctionGrpID(long teamFunctionGrpID) {
		this.teamFunctionGrpID = teamFunctionGrpID;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public long getTeamTypeId() {
		return teamTypeId;
	}

	public void setTeamTypeId(long teamTypeId) {
		this.teamTypeId = teamTypeId;
	}

	public long getFunctionGrpId() {
		return functionGrpId;
	}

	public void setFunctionGrpId(long functionGrpId) {
		this.functionGrpId = functionGrpId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "\nteamFunctionGrpID = " + teamFunctionGrpID + "\nversionTime = " + versionTime + "\nteamId = " + teamId
				+ "\nteamTypeId = " + teamTypeId + "\nfunctionGrpId = " + functionGrpId + "\ngroupId = " + groupId
				+ "\nstatus = " + status;
	}
}
