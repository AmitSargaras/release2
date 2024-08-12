/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemForm.java,v 1.6 2005/10/14 09:18:23 lini Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Action Form Bean for Diary Item Use Case
 * 
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/14 09:18:23 $ Tag: $Name: $
 */

public class DiaryItemForm extends TrxContextForm implements Serializable {

	private String itemId;

	private String leID;

	private String customerName;

	private String description;

	private String narration;

	private String itemDueDate;

	private String itemExpiryDate;

	private String lastUpdatedBy;

	private String lastUpdatedDate;

	private String isObsolete;

	private String customerIndex;

	private String allowedCountry;

	private String customerSegment;

	private String FAM;

	private String countryFilter;

	private String startExpiryDate;

	private String endExpiryDate;

	private String searchCustomerName;

	private String searchLeID;
	
	private String diaryNumber;
	
	private String region;
	
	private String facilityBoardCategory;
	
	private String facilityLineNo;
	
	private String facilitySerialNo;
	
	private String dropLineOD;
	
	private FormFile odScheduleUploadFile;
	
	private String activity;
	
	private String makerId;
	
	private String makerDateTime;
	
	private String status;
	
	private String action;
	
	private String closeBy;
	
	private String closeDate;
	
	private String nextTargetDate;
	
	private String linkEvent;
	
	private String uploadFileError;
	
	private String isDelete;
	
	private String month;
	
	private String closingAmount;

	private String genericCount;
	
	private String odCount;
	
	private String totalCount;
	
	private String segment;
	
	private String segmentName;
	
	private String overDueCount;
	
	private String frompage;
	
	private String customerSegmentName;

	public String getFacilityLineNo() {
		return facilityLineNo;
	}

	public void setFacilityLineNo(String facilityLineNo) {
		this.facilityLineNo = facilityLineNo;
	}

	public String getFacilityBoardCategory() {
		return facilityBoardCategory;
	}

	public void setFacilityBoardCategory(String facilityBoardCategory) {
		this.facilityBoardCategory = facilityBoardCategory;
	}

	public String getStartExpiryDate() {
		return startExpiryDate;
	}

	public void setStartExpiryDate(String startExpiryDate) {
		this.startExpiryDate = startExpiryDate;
	}

	public String getEndExpiryDate() {
		return endExpiryDate;
	}

	public void setEndExpiryDate(String endExpiryDate) {
		this.endExpiryDate = endExpiryDate;
	}

	public String getCountryFilter() {
		return countryFilter;
	}

	public void setCountryFilter(String countryFilter) {
		this.countryFilter = countryFilter;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getFAM() {
		return FAM;
	}

	public void setFAM(String FAM) {
		this.FAM = FAM;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLeID() {
		return leID;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getItemDueDate() {
		return itemDueDate;
	}

	public void setItemDueDate(String itemDueDate) {
		this.itemDueDate = itemDueDate;
	}

	public String getItemExpiryDate() {
		return itemExpiryDate;
	}

	public void setItemExpiryDate(String itemExpiryDate) {
		this.itemExpiryDate = itemExpiryDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getObsolete() {
		return isObsolete;
	}

	public void setObsolete(String obsolete) {
		isObsolete = obsolete;
	}

	/**
	 * customer index for searching
	 * @return String
	 */
	public String getCustomerIndex() {
		return customerIndex;
	}

	/**
	 * sets the customer index for searching
	 * @param customerIndex
	 */
	public void setCustomerIndex(String customerIndex) {
		this.customerIndex = customerIndex;
	}

	/**
	 * retrieves the country allowed for viewing
	 * @return country code
	 */
	public String getAllowedCountry() {
		return allowedCountry;
	}

	/**
	 * sets the country allowed for viewing
	 * @param allowedCountry
	 */
	public void setAllowedCountry(String allowedCountry) {
		this.allowedCountry = allowedCountry;
	}

	public String getSearchCustomerName() {
		return searchCustomerName;
	}

	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}

	public String getSearchLeID() {
		return searchLeID;
	}

	public void setSearchLeID(String searchLeID) {
		this.searchLeID = searchLeID;
	}
	
	

	public String getDiaryNumber() {
		return diaryNumber;
	}

	public void setDiaryNumber(String diaryNumber) {
		this.diaryNumber = diaryNumber;
	}
	
	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	
	public String getMakerDateTime() {
		return makerDateTime;
	}

	public void setMakerDateTime(String makerDateTime) {
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
	
	

	public String getNextTargetDate() {
		return nextTargetDate;
	}

	public void setNextTargetDate(String nextTargetDate) {
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

	
	public String getGenericCount() {
		return genericCount;
	}

	public void setGenericCount(String genericCount) {
		this.genericCount = genericCount;
	}

	public String getOdCount() {
		return odCount;
	}

	public void setOdCount(String odCount) {
		this.odCount = odCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}
		

	public String getOverDueCount() {
		return overDueCount;
	}

	public void setOverDueCount(String overDueCount) {
		this.overDueCount = overDueCount;
	}


	public String getFrompage() {
		return frompage;
	}

	public void setFrompage(String frompage) {
		this.frompage = frompage;
	}
	
	

	public FormFile getOdScheduleUploadFile() {
		return odScheduleUploadFile;
	}

	public void setOdScheduleUploadFile(FormFile odScheduleUploadFile) {
		this.odScheduleUploadFile = odScheduleUploadFile;
	}
	
	

	public String getCustomerSegmentName() {
		return customerSegmentName;
	}

	public void setCustomerSegmentName(String customerSegmentName) {
		this.customerSegmentName = customerSegmentName;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {

		String[][] input = { { "OBTrxContext", TRX_MAPPER }, { "diaryItemObj", DIARY_ITEM_MAPPER },
				{ "DiaryItemSearchCriteria", DIARY_LIST_MAPPER }, };
		return input;
	}

	public static final String DIARY_ITEM_MAPPER = "com.integrosys.cms.ui.diaryitem.DiaryItemMapper";

	public static final String DIARY_LIST_MAPPER = "com.integrosys.cms.ui.diaryitem.DiaryItemListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

}
