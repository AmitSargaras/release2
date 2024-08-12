/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/OBDiaryItem.java,v 1.7 2005/09/30 11:32:23 jtan Exp $
 */
package com.integrosys.cms.app.diary.bus;

//app

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Value object for diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public class OBDiaryItem implements IDiaryItemUI {
	private long itemID;

	private ITeam team;

	private long teamTypeID;

	private String customerReference;

	private String customerName;

	private String description;

	private String narration;

	private Date dueDate;

	private Date expiryDate;

	private String lastUpdatedBy;

	private Date lastUpdatedTime;

	private boolean isObsolete;

	private String FAM;

	private String customerSegment;

	private long versionTime;

	private String allowedCountry;

	private long teamId;
	
	private long diaryNumber;
	
	private String region; 
	
	private String facilityBoardCategory;
	
	private String facilityLineNo;
	
	private String facilitySerialNo;
	
	private String dropLineOD;
	
	private FormFile odScheduleUploadFile;
	
	private String activity;
	
	private String makerId;
	
	private Date makerDateTime;
	
	private String status;
	
	private String action;
	
	private String closeBy;
	
	private String closeDate;
	
	private Date nextTargetDate;
	
	private String linkEvent;
	
	private String uploadFileError;
	
	private String isDelete;
	
	private String month;
	
	private String closingAmount;
	
	private String customerSegmentName;
	
	

	/**
	 * constructor
	 */
	public OBDiaryItem() {
		this.itemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	public String getAllowedCountry() {
		return allowedCountry;
	}

	/**
	 * retrieves the customer name
	 * @return String - name
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * retrieves the LE ID of the customer
	 * @return long - customer LE ID
	 */
	public String getCustomerReference() {
		return customerReference;
	}

	/**
	 * retrieves the customer segment of this diary item
	 * 
	 * @return String - customer segment
	 */
	public String getCustomerSegment() {
		return customerSegment;
	}

	/**
	 * retrieves the description of the diary item
	 * @return String - item description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * retrieves the due date of diary item
	 * @return Date - diary item due date
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * retrieves the expiry date of diary item
	 * @return date - diary item expiry date
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * retrieves the FAM of diary item
	 * 
	 * @return String - FAM name
	 */
	public String getFAM() {
		return FAM;
	}

	/**
	 * retrieves the item id of diary item
	 * @return long - item id
	 */
	public long getItemID() {
		return itemID;
	}

	/**
	 * retrieves the person who last updated the item
	 * @return string - person who last updated item
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * retrieves the last updated time
	 * @return Date - time of update
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * retrieves the narrative text of the diary item
	 * @return String - narrative text
	 */
	public String getNarration() {
		return narration;
	}

	/**
	 * tests whether a diary item is obsolete
	 * @return boolean - diary item status
	 */
	public boolean getObsoleteInd() {
		return isObsolete;
	}

	/**
	 * retrieves the team who owns this diary item
	 * @return ITeam - team value object
	 */
	public ITeam getTeam() {
		return team;
	}

	public long getTeamId() {
		return teamId;
	}

	/**
	 * the team type id of this diary item
	 * @return team type id - long
	 */
	public long getTeamTypeID() {
		return teamTypeID;
	}

	/**
	 * retrieves the timestamp of last modification to object
	 * @return long - timestamp of change
	 */
	public long getVersionTime() {
		return versionTime;
	}

	public boolean isBold() {

		return  !getObsoleteInd();

	}

	/**
	 * tests whether item has expired
	 * @return boolean - diary expiry status
	 */
	private boolean isExpired() {

		Date now = DateUtil.initializeStartDate(DateUtil.getDate());

		return getExpiryDate().getTime() < now.getTime();

	}

	/**
	 * sets the country allowed for viewing
	 * @param country - allowed country
	 */
	public void setAllowedCountry(String country) {
		allowedCountry = country;
	}

	/**
	 * sets the customer name in diary item
	 * @param name - a customer
	 */
	public void setCustomerName(String name) {
		this.customerName = name;
	}

	/**
	 * sets the LE ID of the customer
	 * @param customerReference - customer LE ID
	 */
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	/**
	 * sets the customer segment for diary item
	 * 
	 * @param seg - customer segment
	 */
	public void setCustomerSegment(String seg) {
		this.customerSegment = seg;
	}

	/**
	 * sets the description for the diary item
	 * @param desc - item description
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}

	/**
	 * sets the due date of diary item
	 * @param dueDate - diary item due date
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * sets the expiry date of diary item
	 * @param expiryDate - diary item expiry date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * sets the FAM for this diary item
	 * 
	 * @param fam - fam name
	 */
	public void setFAM(String fam) {
		this.FAM = fam;
	}

	/**
	 * sets the id for the diary item
	 * @param itemID - long
	 */
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	/**
	 * sets the person identity who updates the item
	 * @param who - person updating item
	 */
	public void setLastUpdatedBy(String who) {
		this.lastUpdatedBy = who;
	}

	/**
	 * sets the time of updating
	 * @param when - update timestamp
	 */
	public void setLastUpdatedTime(Date when) {
		this.lastUpdatedTime = when;
	}

	/**
	 * sets the narrative text of the diary item
	 * @param narration
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}

	/**
	 * sets the obselete status for a diary item
	 * @param obsolete
	 */
	public void setObsoleteInd(boolean obsolete) {
		this.isObsolete = obsolete;
	}

	/**
	 * sets the team owner of this diary item
	 * @param team - team value object
	 */
	public void setTeam(ITeam team) {
		this.team = team;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public void setTeamTypeID(long teamTypeID) {
		this.teamTypeID = teamTypeID;
	}		

	public long getDiaryNumber() {
		return diaryNumber;
	}

	public void setDiaryNumber(long diaryNumber) {
		this.diaryNumber = diaryNumber;
	}
	
	
	public String getRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	
	public void setRegion(String region) {
		this.region = region;
		
	}

	public String getFacilityBoardCategory() {
		return facilityBoardCategory;
	}

	public void setFacilityBoardCategory(String facilityBoardCategory) {
		this.facilityBoardCategory = facilityBoardCategory;
	}
	

	public String getFacilityLineNo() {
		return facilityLineNo;
	}

	public void setFacilityLineNo(String facilityLineNo) {
		this.facilityLineNo = facilityLineNo;
	}
	
	

	public String getFacilitySerialNo() {
		return facilitySerialNo;
	}

	public void setFacilitySerialNo(String facilitySerialNo) {
		this.facilitySerialNo = facilitySerialNo;
	}	

	public String getDropLineOD() {
		return dropLineOD;
	}

	public void setDropLineOD(String dropLineOD) {
		this.dropLineOD = dropLineOD;
	}
		
	
	public FormFile getOdScheduleUploadFile() {
		return odScheduleUploadFile;
	}

	public void setOdScheduleUploadFile(FormFile odScheduleUploadFile) {
		this.odScheduleUploadFile = odScheduleUploadFile;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}	

	public Date getMakerDateTime() {
		return makerDateTime;
	}

	public void setMakerDateTime(Date makerDateTime) {
		this.makerDateTime = makerDateTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCloseBy() {
		return closeBy;
	}

	public void setCloseBy(String closeBy) {
		this.closeBy = closeBy;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	
	

	public Date getNextTargetDate() {
		return nextTargetDate;
	}

	public void setNextTargetDate(Date nextTargetDate) {
		this.nextTargetDate = nextTargetDate;
	}
	
	

	public String getLinkEvent() {
		return linkEvent;
	}

	public void setLinkEvent(String linkEvent) {
		this.linkEvent = linkEvent;
	}
	
	

	public String getUploadFileError() {
		return uploadFileError;
	}

	public void setUploadFileError(String uploadFileError) {
		this.uploadFileError = uploadFileError;
	}

	
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getClosingAmount() {
		return closingAmount;
	}

	public void setClosingAmount(String closingAmount) {
		this.closingAmount = closingAmount;
	}

	/**
	 * sets the timestamp of object change
	 * @param l - timestamp
	 */
	public void setVersionTime(long l) {
		this.versionTime = l;
	}

	
	
	public String getCustomerSegmentName() {
		return customerSegmentName;
	}

	public void setCustomerSegmentName(String customerSegmentName) {
		this.customerSegmentName = customerSegmentName;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(System.identityHashCode(this));

		buf.append(" CIF [").append(customerReference).append("], ");
		buf.append("Customer Name [").append(customerName).append("], ");
		buf.append("Short Description [").append(description).append("], ");
		buf.append("Expiry Date [").append(expiryDate).append("], ");
		buf.append("Reminder Date [").append(dueDate).append("], ");
		buf.append("Access Country [").append(allowedCountry).append("]");

		return buf.toString();
	}

	
}