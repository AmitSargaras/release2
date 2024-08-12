/*
 * Created on Mar 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitListSummaryItemBase implements Serializable {
	private String cmsLimitId;

	private String limitId;

	private String prodTypeCode;

	private String limitLoc;

	private IBookingLocation limitBookingLoc;

	private String outerLimitId;

	private String approvedAmount;

	private Amount authorizedAmount;

	private String drawingAmount;

	private Amount outstandingAmount;

	private String actualSecCoverage;

	private int innerLimitCount;

	private int linkSecCount;

	private String facilityTypeCode;
	
	private String lineNo;

	private List secItemList;
	
	private List tranchList;
	
	private String currencyCode;
	
	private String isAdhoc;
	
	private String isAdhocToSum;
	
	private String adhocAmount;
	
	private String isSubLimit;
	
	private String partyID;
	private String partyName;
	private String limitProfileId;
	private String customerID;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public String getPartyID() {
		return partyID;
	}

	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public LimitListSummaryItemBase() {
		secItemList = new ArrayList();
	}

	/**
	 * @return Returns the actualSecCoverage.
	 */
	public String getActualSecCoverage() {
		return actualSecCoverage;
	}

	/**
	 * @param actualSecCoverage The actualSecCoverage to set.
	 */
	public void setActualSecCoverage(String actualSecCoverage) {
		this.actualSecCoverage = actualSecCoverage;
	}

	/**
	 * @return Returns the approvedAmount.
	 */
	public String getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @param approvedAmount The approvedAmount to set.
	 */
	public void setApprovedAmount(String approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	/**
	 * @return Returns the authorizedAmount.
	 */
	public Amount getAuthorizedAmount() {
		return authorizedAmount;
	}

	/**
	 * @param authorizedAmount The authorizedAmount to set.
	 */
	public void setAuthorizedAmount(Amount authorizedAmount) {
		this.authorizedAmount = authorizedAmount;
	}

	/**
	 * @return Returns the cmsLimitId.
	 */
	public String getCmsLimitId() {
		return cmsLimitId;
	}

	/**
	 * @param cmsLimitId The cmsLimitId to set.
	 */
	public void setCmsLimitId(String cmsLimitId) {
		this.cmsLimitId = cmsLimitId;
	}

	/**
	 * @return Returns the drawingAmount.
	 */
	public String getDrawingAmount() {
		return drawingAmount;
	}

	/**
	 * @param drawingAmount The drawingAmount to set.
	 */
	public void setDrawingAmount(String drawingAmount) {
		this.drawingAmount = drawingAmount;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitId() {
		return limitId;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	/**
	 * @return Returns the limitLoc.
	 */
	public String getLimitLoc() {
		return limitLoc;
	}

	/**
	 * @param limitLoc The limitLoc to set.
	 */
	public void setLimitLoc(String limitLoc) {
		this.limitLoc = limitLoc;
	}

	/**
	 * @return Returns the limitBookingLoc.
	 */
	public IBookingLocation getLimitBookingLoc() {
		return this.limitBookingLoc;
	}

	/**
	 * @param limitBookingLoc The IBookingLocation to set.
	 */
	public void setLimitBookingLoc(IBookingLocation limitBookingLoc) {
		this.limitBookingLoc = limitBookingLoc;
	}

	/**
	 * @return Returns the outerLimitId.
	 */
	public String getOuterLimitId() {
		return outerLimitId;
	}

	/**
	 * @param outerLimitId The outerLimitId to set.
	 */
	public void setOuterLimitId(String outerLimitId) {
		this.outerLimitId = outerLimitId;
	}

	/**
	 * @return Returns the outstandingAmount.
	 */
	public Amount getOutstandingAmount() {
		return outstandingAmount;
	}

	/**
	 * @param outstandingAmount The outstandingAmount to set.
	 */
	public void setOutstandingAmount(Amount outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	/**
	 * @return Returns the prodTypeCode.
	 */
	public String getProdTypeCode() {
		return prodTypeCode;
	}

	/**
	 * @param prodTypeCode The prodTypeCode to set.
	 */
	public void setProdTypeCode(String prodTypeCode) {
		this.prodTypeCode = prodTypeCode;
	}

	/**
	 * @return Returns the facilityTypeCode.
	 */
	public String getFacilityTypeCode() {
		return facilityTypeCode;
	}

	/**
	 * @param prodTypeCode The facilityTypeCode to set.
	 */
	public void setFacilityTypeCode(String facilityTypeCode) {
		this.facilityTypeCode = facilityTypeCode;
	}

	public int getInnerLimitCount() {
		return innerLimitCount;
	}

	public void setInnerLimitCount(int innerLimitCount) {
		this.innerLimitCount = innerLimitCount;
	}

	public int getLinkSecCount() {
		return linkSecCount;
	}

	public void setLinkSecCount(int linkSecCount) {
		this.linkSecCount = linkSecCount;
	}

	/**
	 * @return Returns the secItemList.
	 */
	public List getSecItemList() {
		return secItemList;
	}

	public void setSecItemList(List secItemList) {
		this.secItemList = secItemList;
	}

	/**
	 * @param secItem
	 */
	public void addSecItem(LimitListSecItemBase secItem) {
		// TODO Auto-generated method stub
		secItemList.add(secItem);
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public List getTranchList() {
		return tranchList;
	}

	public void setTranchList(List tranchList) {
		this.tranchList = tranchList;
	}

	public String getIsAdhoc() {
		return isAdhoc;
	}

	public void setIsAdhoc(String isAdhoc) {
		this.isAdhoc = isAdhoc;
	}

	public String getAdhocAmount() {
		return adhocAmount;
	}

	public void setAdhocAmount(String adhocAmount) {
		this.adhocAmount = adhocAmount;
	}

	public String getIsAdhocToSum() {
		return isAdhocToSum;
	}

	public void setIsAdhocToSum(String isAdhocToSum) {
		this.isAdhocToSum = isAdhocToSum;
	}

	public String getIsSubLimit() {
		return isSubLimit;
	}

	public void setIsSubLimit(String isSubLimit) {
		this.isSubLimit = isSubLimit;
	}
	
	
}
