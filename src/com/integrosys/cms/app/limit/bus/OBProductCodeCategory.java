package com.integrosys.cms.app.limit.bus;

public class OBProductCodeCategory implements IProductCodeStage{

	private long id;
	private String productCodeId;
	private long versionTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductCodeId() {
		return productCodeId;
	}
	public void setProductCodeId(String productCodeId) {
		this.productCodeId = productCodeId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
}
