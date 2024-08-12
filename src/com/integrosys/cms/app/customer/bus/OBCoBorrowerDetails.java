package com.integrosys.cms.app.customer.bus;

public class OBCoBorrowerDetails implements ICoBorrowerDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private long mainProfileId;
	private String coBorrowerLiabId;
	private String coBorrowerName;
	private String isInterfaced;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getMainProfileId() {
		return mainProfileId;
	}
	public void setMainProfileId(long mainProfileId) {
		this.mainProfileId = mainProfileId;
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

	public String getIsInterfaced() {
		return isInterfaced;
	}
	public void setIsInterfaced(String isInterfaced) {
		this.isInterfaced = isInterfaced;
	}
	
	
}
