/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBCustodianDocSearchResult.java,v 1.14 2006/08/30 12:09:22 jzhai Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Implementation class for the ICustodianDocSearchResult interface
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/08/30 12:09:22 $ Tag: $Name: $
 */
public class OBCustodianDocSearchResult implements ICustodianDocSearchResult {
	private long custodianDocID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListStatus = null;

	private String trxId = null;

	private String trxStatus = null;

	private Date trxDate = null;

	private String category = null;

	private String subCategory = null;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE; 

	// private ICustodianDocItemSearchResult[] custodianDocItems = null;
	private List custodianDocItems = null;

	private String reversalRemarks = null;

	private String reversalRmkUpdatedUserInfo = null;

	private String legalID = String.valueOf(ICMSConstant.LONG_INVALID_VALUE);

	private String legalName = null;

	private ArrayList customerList;

	/**
	 * Default Constructor
	 */
	public OBCustodianDocSearchResult() {
	}

	/**
	 * Getter methods
	 */
	public long getCustodianDocID() {
		return custodianDocID;
	}

	public long getCheckListID() {
		return checkListID;
	}

	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	public String getTrxId() {
		return trxId;
	}

	public String getTrxStatus() {
		return this.trxStatus;
	}

	public Date getTrxDate() {
		return this.trxDate;
	}

	public String getCategory() {
		return category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getSubProfileID() {
		return this.subProfileID;
	}

	public long getCollateralID() {
		return this.collateralID;
	}

	public long getPledgorID() {
		return this.pledgorID;
	}

	public ICustodianDocItemSearchResult[] getCustodianDocItems() {
		return (this.custodianDocItems == null) ? new ICustodianDocItemSearchResult[0]
				: (ICustodianDocItemSearchResult[]) this.custodianDocItems
						.toArray(new ICustodianDocItemSearchResult[custodianDocItems.size()]);
	}

	/**
	 * Setter methods
	 */
	public void setCustodianDocID(long custodianDocID) {
		this.custodianDocID = custodianDocID;
	}

	public void setCheckListID(long checkListID) {
		this.checkListID = checkListID;
	}

	public void setCheckListStatus(String checkListStatus) {
		this.checkListStatus = checkListStatus;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	public void setTrxDate(Date aTrxDate) {
		this.trxDate = aTrxDate;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public void setSubProfileID(long subProfileID) {
		this.subProfileID = subProfileID;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public void setPledgorID(long pledgorID) {
		this.pledgorID = pledgorID;
	}

	public void setCustodianDocItems(ICustodianDocItemSearchResult[] items) {
		if ((items != null) && (items.length > 0)) {
			this.custodianDocItems = Arrays.asList(items);
		}
	}

	public void addCustodianDocItem(ICustodianDocItemSearchResult item) {
		if (item == null) {
			return;
		}
		if (custodianDocItems == null) {
			custodianDocItems = new ArrayList();
		}
		custodianDocItems.add(item);
	}

	/**
	 * @return Returns the reversalRemarks.
	 */
	public String getReversalRemarks() {
		return reversalRemarks;
	}

	/**
	 * @param reversalRemarks The reversalRemarks to set.
	 */
	public void setReversalRemarks(String reversalRemarks) {
		this.reversalRemarks = reversalRemarks;
	}

	/**
	 * @return Returns the reversalRmkUpdatedUserInfo.
	 */
	public String getReversalRmkUpdatedUserInfo() {
		return reversalRmkUpdatedUserInfo;
	}

	/**
	 * @param reversalRmkUpdatedUserInfo The reversalRmkUpdatedUserInfo to set.
	 */
	public void setReversalRmkUpdatedUserInfo(String reversalRmkUpdatedUserInfo) {
		this.reversalRmkUpdatedUserInfo = reversalRmkUpdatedUserInfo;
	}

	public String getLegalID() {
		return legalID;
	}  //edit

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}   //edit

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public ArrayList getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}