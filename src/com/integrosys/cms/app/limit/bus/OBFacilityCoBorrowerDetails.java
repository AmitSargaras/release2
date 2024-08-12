package com.integrosys.cms.app.limit.bus;

import java.util.Date;

public class OBFacilityCoBorrowerDetails implements IFacilityCoBorrowerDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String createBy;
	private Date creationDate;
	private Long mainProfileId;
	private long limitId;
	private String coBorrowerLiabId;
	private String coBorrowerName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getMainProfileId() {
		return mainProfileId;
	}
	public void setMainProfileId(Long mainProfileId) {
		this.mainProfileId = mainProfileId;
	}
	public long getLimitId() {
		return limitId;
	}
	public void setLimitId(long limitId) {
		this.limitId = limitId;
	}
	public String getCoBorrowerLiabId() {
		return coBorrowerLiabId;
	}
	public void setCoBorrowerLiabId(String coBorrowerLiabId) {
		this.coBorrowerLiabId = coBorrowerLiabId;
	}
	public String getCoBorrowerName() {
		return coBorrowerName;
	}
	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
	
}
