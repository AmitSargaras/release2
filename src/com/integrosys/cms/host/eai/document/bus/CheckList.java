package com.integrosys.cms.host.eai.document.bus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CheckList implements java.io.Serializable {

	private String AANumber;

	private String applicationType;

	private CCChecklist ccChecklist;

	private char changeIndicator;

	private long checkListID = ICMSConstant.LONG_INVALID_VALUE;

	private Vector checkListItem;

	private Set checkListItemSet;

	private String checklistType;

	private Date lastModifiedDate;

	private long limitProfileId = ICMSConstant.LONG_INVALID_VALUE;

	private Long customerId;

	private Long pledgorId;

	private String origCountry;

	private String origOrganisation;

	private SCChecklist scChecklist;

	private long templateID;

	// private long collateralID;

	private char updateStatusIndicator;

	private long versionTime;

	private String status;

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public Date getJDOLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getLastModifiedDate() {
		return MessageDate.getInstance().getString(this.lastModifiedDate);
	}

	public void setJDOLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = MessageDate.getInstance().getDate(lastModifiedDate);
	}

	public char getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(char updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public char getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(char changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public CCChecklist getCcChecklist() {
		return ccChecklist;
	}

	public void setCcChecklist(CCChecklist ccChecklist) {
		this.ccChecklist = ccChecklist;
	}

	public String getChecklistType() {
		return checklistType;
	}

	public void setChecklistType(String checklistType) {
		this.checklistType = checklistType;
	}

	public String getOrigCountry() {
		return origCountry;
	}

	public void setOrigCountry(String origCountry) {
		this.origCountry = origCountry;
	}

	public String getOrigOrganisation() {
		return origOrganisation;
	}

	public void setOrigOrganisation(String origOrganisation) {
		this.origOrganisation = origOrganisation;
	}

	public SCChecklist getScChecklist() {
		return scChecklist;
	}

	public void setScChecklist(SCChecklist scChecklist) {
		this.scChecklist = scChecklist;
	}

	public long getTemplateID() {
		return templateID;
	}

	public void setTemplateID(long templateID) {
		this.templateID = templateID;
	}

	/*
	 * public long getCollateralID() { return collateralID; }
	 * 
	 * public void setCollateralID(long collateralID) { this.collateralID =
	 * collateralID; }
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getAANumber() {
		return AANumber;
	}

	public void setAANumber(String number) {
		AANumber = number;
	}

	public long getCheckListID() {
		return checkListID;
	}

	public void setCheckListID(long checkListID) {
		this.checkListID = checkListID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public Long getPledgorId() {
		return pledgorId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setPledgorId(Long pledgorId) {
		this.pledgorId = pledgorId;
	}

	public Vector getCheckListItem() {
		return checkListItem;
	}

	public void setCheckListItem(Vector checkListItem) {
		this.checkListItem = checkListItem;

		if (checkListItemSet == null) {
			checkListItemSet = new HashSet();
		}
		else {
			checkListItemSet.clear();
		}

		checkListItemSet.addAll(checkListItem);
	}

	public Set getCheckListItemSet() {
		return checkListItemSet;
	}

	public void setCheckListItemSet(Set checkListItemSet) {
		this.checkListItemSet = checkListItemSet;

		if (checkListItem == null) {
			checkListItem = new Vector();
		}
		else {
			checkListItem.clear();
		}

		checkListItem.addAll(checkListItemSet);
	}

	public boolean isUpdateChecklist() {
		if ((IEaiConstant.CHANGEINDICATOR == getChangeIndicator())
				&& (IEaiConstant.UPDATEINDICATOR == getUpdateStatusIndicator())) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isInsertChecklist() {
		if ((IEaiConstant.CHANGEINDICATOR == getChangeIndicator())
				&& (IEaiConstant.CREATEINDICATOR == getUpdateStatusIndicator())) {
			return true;
		}
		else {
			return false;
		}
	}

}
