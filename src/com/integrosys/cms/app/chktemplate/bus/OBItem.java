/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBItem.java,v 1.4 2003/10/28 08:56:24 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

/**
 * This is the implementation class for the IItem
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 08:56:24 $ Tag: $Name: $
 */
public class OBItem implements IItem, Comparable {

	private static final long serialVersionUID = -4074729955589230866L;

	private long itemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String itemCode = null;

	private String itemDesc = null;

	private String itemType = null;

	private Date expiryDate = null;

	private String monitorType = null;

	private String documentVersion = null;

	private boolean isForBorrower;

	private boolean isForPledgor;

	private boolean isPreApprove;

	private String loanApplicationType = null;

	private IDynamicProperty[] propertyList = null;
	
	private List loanAppTypes;
	
	private int tenureCount=0;
	
	private String tenureType="";
	
	private String deprecated="";
	
	private String status="";

	private String statementType="";
	
	private String isRecurrent="";
	
	private String rating="";
	
	private String segment="";
	
	private String totalSancAmt="";
	
	private String classification="";
	
	private String guarantor="";
	
	private String isApplicableForCersaiInd;
	
	private String skipImgTag ="";


	public String getIsRecurrent() {
		return isRecurrent;
	}

	public void setIsRecurrent(String isRecurrent) {
		this.isRecurrent = isRecurrent;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getTotalSancAmt() {
		return totalSancAmt;
	}

	public void setTotalSancAmt(String totalSancAmt) {
		this.totalSancAmt = totalSancAmt;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
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

	public int getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(int tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public String getSkipImgTag() {
		return skipImgTag;
	}

	public void setSkipImgTag(String skipImgTag) {
		this.skipImgTag = skipImgTag;
	}

	
	/**
	 * Get the item ID
	 * @return long - the item ID
	 */
	public long getItemID() {
		return this.itemID;
	}

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getItemDesc() {
		return this.itemDesc;
	}

	/**
	 * Get the item type
	 * @return String - the item type
	 */
	public String getItemType() {
		return this.itemType;
	}

	/**
	 * Get the expiry date
	 * @return Date - the expiry date
	 */
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Get the monitor type
	 * @return String - the monitor type
	 */
	public String getMonitorType() {
		return this.monitorType;
	}

	/**
	 * Get the user-input document version.
	 * @return String - document version
	 */
	public String getDocumentVersion() {
		return documentVersion;
	}

	/**
	 * Get the flag to determine if item is applicable for borrower
	 * @return boolean - flag for borrower
	 */
	public boolean getIsForBorrower() {
		return isForBorrower;
	}

	/**
	 * Get the flag to determine if item is applicable for pledgor
	 * @return boolean - flag for pledgor
	 */
	public boolean getIsForPledgor() {
		return isForPledgor;
	}

	/**
	 * Get the flag to determine if item is pre-approve (by LOS)
	 * @return boolean - pre-approval indicator
	 */
	public boolean getIsPreApprove() {
		return isPreApprove;
	}

	/**
	 * Get the applicable loan application type. E.g. of loan application type =
	 * Hire Purchase, Credit Card, Corporate, All
	 * @return String - loan application type
	 */
	public String getLoanApplicationType() {
		return loanApplicationType;
	}

	/**
	 * Get the list of dynamic properties
	 * @return list of dynamic properties
	 */
	public IDynamicProperty[] getPropertyList() {
		return propertyList;
	}

	/**
	 * Set the item ID
	 * @param anItemID - long
	 */
	public void setItemID(long anItemID) {
		this.itemID = anItemID;
	}

	/**
	 * Set the item code
	 * @param anItemCode - String
	 */
	public void setItemCode(String anItemCode) {
		this.itemCode = anItemCode;
	}

	/**
	 * Set the item description
	 * @param anItemDesc - String
	 */
	public void setItemDesc(String anItemDesc) {
		this.itemDesc = anItemDesc;
	}

	/**
	 * Set the item type
	 * @param anItemType - String
	 */
	public void setItemType(String anItemType) {
		this.itemType = anItemType;
	}

	/**
	 * Set the expiry date
	 * @param anExpiryDate - Date
	 */
	public void setExpiryDate(Date anExpiryDate) {
		this.expiryDate = anExpiryDate;
	}

	/**
	 * Set the monitor type
	 * @param aMonitorType of String type
	 */
	public void setMonitorType(String aMonitorType) {
		this.monitorType = aMonitorType;
	}

	public void setDocumentVersion(String docVersion) {
		documentVersion = docVersion;
	}

	/**
	 * Set the flag to determine if item is applicable to borrower.
	 * @param flag - borrower indicator
	 */
	public void setIsForBorrower(boolean flag) {
		isForBorrower = flag;
	}

	/**
	 * Set the flag to determine if item is applicable to pledgor.
	 * @param flag - pledgor indicator
	 */
	public void setIsForPledgor(boolean flag) {
		isForPledgor = flag;
	}

	/**
	 * Set the flag to determine if item is pre-approve (in LOS).
	 * @param flag - pre-approval indicator
	 */
	public void setIsPreApprove(boolean flag) {
		isPreApprove = flag;
	}

	/**
	 * Set the loan application type that is applicable E.g. of loan application
	 * type = Hire Purchase, Credit Card, Corporate, All
	 * @param type - loan application type
	 */
	public void setLoanApplicationType(String type) {
		loanApplicationType = type;
	}

	/**
	 * Set the list of dynamic properties
	 * @param propertyList - list of dyanmic properties
	 */
	public void setPropertyList(IDynamicProperty[] propertyList) {
		this.propertyList = propertyList;
	}
	
	public void loadLoanAppTypes()
	{
		String[] appList = getLoanApplicationType().split("-");
		List docAppTypeItemListing = new ArrayList();
		for (int i =0; i <appList.length; i++)
		{
			OBDocumentAppTypeItem obDocAppTypeItem = new OBDocumentAppTypeItem();
			obDocAppTypeItem.setAppType(appList[i]);
			docAppTypeItemListing.add(obDocAppTypeItem);
		}
		
		setCMRDocAppItemList(docAppTypeItemListing);
		return;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBItem [");
		buf.append("itemCode=");
		buf.append(itemCode);
		buf.append(", itemDesc=");
		buf.append(itemDesc);
		buf.append(", itemID=");
		buf.append(itemID);
		buf.append(", documentVersion=");
		buf.append(documentVersion);
		buf.append(", loanApplicationType=");
		buf.append(loanApplicationType);
		buf.append(", expiryDate=");
		buf.append(expiryDate);
		buf.append(", monitorType=");
		buf.append(monitorType);
		buf.append(", itemType=");
		buf.append(itemType);
		buf.append(", isForBorrower=");
		buf.append(isForBorrower);
		buf.append(", isForPledgor=");
		buf.append(isForPledgor);
		buf.append(", isPreApprove=");
		buf.append(isPreApprove);
		buf.append(", propertyList=");
		buf.append(propertyList != null ? Arrays.asList(propertyList) : null);
		buf.append("]");
		return buf.toString();
	}

	public int compareTo(Object other) {
		String otherItemCode = (other == null) ? null : ((OBItem) other).itemCode;

		if (this.itemCode == null) {
			return (otherItemCode == null) ? 0 : -1;
		}

		return (otherItemCode == null) ? 1 : this.itemCode.compareTo(otherItemCode);
	}

	public Collection getCMRDocAppItemList() {
		return loanAppTypes;
	}

	public void setCMRDocAppItemList(Collection docAppItemList) {
		loanAppTypes = (List)docAppItemList;
		
	}

	
	private String oldItemCode = null;

	public String getOldItemCode() {
		return oldItemCode;
	}

	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}
	


	public String getIsApplicableForCersaiInd() {
		return isApplicableForCersaiInd;
	}

	public void setIsApplicableForCersaiInd(String isApplicableForCersaiInd) {
		this.isApplicableForCersaiInd = isApplicableForCersaiInd;
	}

}
