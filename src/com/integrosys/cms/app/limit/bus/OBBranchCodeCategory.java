package com.integrosys.cms.app.limit.bus;

public class OBBranchCodeCategory implements IBranchCodeStage{

	private long id;
	private String branchCodeId;
	private long versionTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBranchCodeId() {
		return branchCodeId;
	}
	public void setBranchCodeId(String branchCodeId) {
		this.branchCodeId = branchCodeId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
}
