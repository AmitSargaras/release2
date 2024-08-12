/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntries.java
 *
 * Created on February 5, 2007, 6:02 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.item;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainCCEntryForm extends TrxContextForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String entryName;

	private String entryCode;

	private String country;

	private String refCategoryCode;
	//Added by Anil for EOD syncMigration
	private String entryId = null;
	private String syncRemark = null;
	private String syncStatus = null;
	private String syncAction = null;
	private String activeStatus;
	
	
	private String categoryCode;

	private String cpsId;
	
	private String operationName;
	
	private String event;

	/*
	 * private String entrySource private String entryId private String
	 * categoryCode private String categoryCodeId private String groupId
	 */

	public String[][] getMapper() {
		return new String[][] { { "aCommonCodeEntryObj", "com.integrosys.cms.ui.commoncodeentry.item.CCEntryMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRefCategoryCode(String refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
	}

	public String getRefCategoryCode() {
		return refCategoryCode;
	}

	/**
	 * @return the entryId
	 */
	public String getEntryId() {
		return entryId;
	}

	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	/**
	 * @return the syncRemark
	 */
	public String getSyncRemark() {
		return syncRemark;
	}

	/**
	 * @param syncRemark the syncRemark to set
	 */
	public void setSyncRemark(String syncRemark) {
		this.syncRemark = syncRemark;
	}

	/**
	 * @return the syncStatus
	 */
	public String getSyncStatus() {
		return syncStatus;
	}

	/**
	 * @param syncStatus the syncStatus to set
	 */
	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	/**
	 * @return the syncAction
	 */
	public String getSyncAction() {
		return syncAction;
	}

	/**
	 * @param syncAction the syncAction to set
	 */
	public void setSyncAction(String syncAction) {
		this.syncAction = syncAction;
	}

	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
		public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
