package com.integrosys.cms.app.goodsMaster.bus;

import java.util.Set;
import java.util.Date;

public class OBGoodsMaster implements IGoodsMaster,Cloneable{


	
	private long id;
	private long versionTime;

	
	private String goodsCode;
	private String goodsName;
	private String goodsParentCode;
	private Set goodsParentCodeList;
	
	private String operationName;
	private String status;
	
	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String cpsId;
	private String restrictionType;
	
	public Set getGoodsParentCodeList() {
		return goodsParentCodeList;
	}
	
	public void setGoodsParentCodeList(Set goodsParentCodeList) {
		this.goodsParentCodeList = goodsParentCodeList;
	}
	
	public String getGoodsParentCode() {
		return goodsParentCode;
	}
	public void setGoodsParentCode(String goodsParentCode) {
		this.goodsParentCode = goodsParentCode;
	}
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getRestrictionType() {
		return restrictionType;
	}
	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	
}
