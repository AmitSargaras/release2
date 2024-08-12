/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/OBDiaryItem.java,v 1.7 2005/09/30 11:32:23 jtan Exp $
 */
package com.integrosys.cms.app.partygroup.bus;

//app

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * 
 @author $Author: Bharat Waghela $
 */
public class OBPartyGroup implements IPartyGroup {

	/**
	 * constructor
	 */
	public OBPartyGroup() {
	}

	private long id;
	private long versionTime;

	private String partyCode;
	private String partyName;
	private Amount groupExpLimit;

	private String operationName;
	

	public Amount getGroupExpLimit() {
		return groupExpLimit;
	}

	public void setGroupExpLimit(Amount groupExpLimit) {
		this.groupExpLimit = groupExpLimit;
	}

	private String status;
	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String cpsId;

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	
}