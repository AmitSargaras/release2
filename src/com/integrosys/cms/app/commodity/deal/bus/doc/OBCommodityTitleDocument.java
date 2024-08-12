/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/OBCommodityTitleDocument.java,v 1.4 2004/07/07 12:15:50 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity Title Document entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/07 12:15:50 $ Tag: $Name: $
 */
public class OBCommodityTitleDocument implements ICommodityTitleDocument {
	private long titleDocID;

	private String processStageCode;

	private double advEligibilityPct;

	private String isSecured;

	private ITitleDocument titleDocType;

	private IWarehouseReceipt[] warehouseReceipts;

	private String bLNo;

	private String bLShippingCompany;

	private Date bLDate;

	private String bLRemarks;

	private String tRNo;

	private String tRBillLadingNo;

	private Amount tRAmount;

	private Date tRDate;

	private Date tRMaturityDate;

	private String tRRemarks;

	private String otherDocNo;

	private String otherDocDesc;

	private Date otherDocDate;

	private Date otherDocDueDate;

	private String otherDocRemarks;

	private Date transactionDate;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default Constructor.
	 */
	public OBCommodityTitleDocument() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommodityTitleDocument
	 */
	public OBCommodityTitleDocument(ICommodityTitleDocument obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get commodity title document id.
	 * 
	 * @return long
	 */
	public long getTitleDocID() {
		return titleDocID;
	}

	/**
	 * Set commodity title document id.
	 * 
	 * @param titleDocID of type long
	 */
	public void setTitleDocID(long titleDocID) {
		this.titleDocID = titleDocID;
	}

	/**
	 * Get process stage code.
	 * 
	 * @return String
	 */
	public String getProcessStageCode() {
		return processStageCode;
	}

	/**
	 * Set process stage code.
	 * 
	 * @param processStageCode of type String
	 */
	public void setProcessStageCode(String processStageCode) {
		this.processStageCode = processStageCode;
	}

	/**
	 * Get eligibility of advance percentage.
	 * 
	 * @return double
	 */
	public double getAdvEligibilityPct() {
		return advEligibilityPct;
	}

	/**
	 * Set eligibility of advance percentage.
	 * 
	 * @param advEligibilityPct of type double
	 */
	public void setAdvEligibilityPct(double advEligibilityPct) {
		this.advEligibilityPct = advEligibilityPct;
	}

	/**
	 * Get indicator to check if the title doc is secured or not.
	 * 
	 * @return String
	 */
	public String getIsSecured() {
		return isSecured;
	}

	/**
	 * Set indicator to check if the title doc is secured or not.
	 * 
	 * @param isSecured of type String
	 */
	public void setIsSecured(String isSecured) {
		this.isSecured = isSecured;
	}

	/**
	 * Get title document type.
	 * 
	 * @return ITitleDocument
	 */
	public ITitleDocument getTitleDocType() {
		return titleDocType;
	}

	/**
	 * Set title document type.
	 * 
	 * @param titleDocType of type ITitleDocument
	 */
	public void setTitleDocType(ITitleDocument titleDocType) {
		this.titleDocType = titleDocType;
	}

	/**
	 * Get warehouse details.
	 * 
	 * @return IWarehouseReceipt[]
	 */
	public IWarehouseReceipt[] getWarehouseReceipts() {
		return warehouseReceipts;
	}

	/**
	 * Set warehouse details.
	 * 
	 * @param warehouseReceipts of type IWarehouseReceipt[]
	 */
	public void setWarehouseReceipts(IWarehouseReceipt[] warehouseReceipts) {
		this.warehouseReceipts = warehouseReceipts;
	}

	/**
	 * Get bill of lading number.
	 * 
	 * @return String
	 */
	public String getBLNo() {
		return bLNo;
	}

	/**
	 * Set bill of lading number.
	 * 
	 * @param bLNo of type String
	 */
	public void setBLNo(String bLNo) {
		this.bLNo = bLNo;
	}

	/**
	 * Get shipping company name.
	 * 
	 * @return String
	 */
	public String getBLShippingCompany() {
		return bLShippingCompany;
	}

	/**
	 * Set shipping company name.
	 * 
	 * @param bLShippingCompany of type String
	 */
	public void setBLShippingCompany(String bLShippingCompany) {
		this.bLShippingCompany = bLShippingCompany;
	}

	/**
	 * Get bill of lading date.
	 * 
	 * @return Date
	 */
	public Date getBLDate() {
		return bLDate;
	}

	/**
	 * Set bill of lading date.
	 * 
	 * @param bLDate of type Date
	 */
	public void setBLDate(Date bLDate) {
		this.bLDate = bLDate;
	}

	/**
	 * Get bill of lading remarks.
	 * 
	 * @return String
	 */
	public String getBLRemarks() {
		return bLRemarks;
	}

	/**
	 * Set bill of lading remarks.
	 * 
	 * @param bLRemarks of type String
	 */
	public void setBLRemarks(String bLRemarks) {
		this.bLRemarks = bLRemarks;
	}

	/**
	 * Get trust receipt number.
	 * 
	 * @return String
	 */
	public String getTRNo() {
		return tRNo;
	}

	/**
	 * Set trust receipt number.
	 * 
	 * @param tRNo of type String
	 */
	public void setTRNo(String tRNo) {
		this.tRNo = tRNo;
	}

	/**
	 * Get trust receipt's bill of lading reference number.
	 * 
	 * @return String
	 */
	public String getTRBillLadingNo() {
		return tRBillLadingNo;
	}

	/**
	 * Set trust receipt's bill of lading reference number.
	 * 
	 * @param tRBillLadingNo of type String
	 */
	public void setTRBillLadingNo(String tRBillLadingNo) {
		this.tRBillLadingNo = tRBillLadingNo;
	}

	/**
	 * Get trust receipt amount.
	 * 
	 * @return Amount
	 */
	public Amount getTRAmount() {
		return tRAmount;
	}

	/**
	 * Set trust receipt amount.
	 * 
	 * @param tRAmount of type Amount
	 */
	public void setTRAmount(Amount tRAmount) {
		this.tRAmount = tRAmount;
	}

	/**
	 * Get trust receipt date.
	 * 
	 * @return Date
	 */
	public Date getTRDate() {
		return tRDate;
	}

	/**
	 * Set trust receipt date.
	 * 
	 * @param tRDate of type Date
	 */
	public void setTRDate(Date tRDate) {
		this.tRDate = tRDate;
	}

	/**
	 * Get trust receipt maturity date.
	 * 
	 * @return Date
	 */
	public Date getTRMaturityDate() {
		return tRMaturityDate;
	}

	/**
	 * Set trust receipt maturity date.
	 * 
	 * @param tRMaturityDate of type Date
	 */
	public void setTRMaturityDate(Date tRMaturityDate) {
		this.tRMaturityDate = tRMaturityDate;
	}

	/**
	 * Get trust receipt remarks.
	 * 
	 * @return String
	 */
	public String getTRRemarks() {
		return tRRemarks;
	}

	/**
	 * Set trust receipt remarks.
	 * 
	 * @param tRRemarks of type String
	 */
	public void setTRRemarks(String tRRemarks) {
		this.tRRemarks = tRRemarks;
	}

	/**
	 * Get other document number.
	 * 
	 * @return String
	 */
	public String getOtherDocNo() {
		return otherDocNo;
	}

	/**
	 * Set other document number.
	 * 
	 * @param otherDocNo of type String
	 */
	public void setOtherDocNo(String otherDocNo) {
		this.otherDocNo = otherDocNo;
	}

	/**
	 * Get other document description.
	 * 
	 * @return String
	 */
	public String getOtherDocDesc() {
		return otherDocDesc;
	}

	/**
	 * Set other document description.
	 * 
	 * @param otherDocDesc of type String
	 */
	public void setOtherDocDesc(String otherDocDesc) {
		this.otherDocDesc = otherDocDesc;
	}

	/**
	 * Get other document date.
	 * 
	 * @return Date
	 */
	public Date getOtherDocDate() {
		return otherDocDate;
	}

	/**
	 * Set other document date.
	 * 
	 * @param otherDocDate of type Date
	 */
	public void setOtherDocDate(Date otherDocDate) {
		this.otherDocDate = otherDocDate;
	}

	/**
	 * Get other document due date.
	 * 
	 * @return Date
	 */
	public Date getOtherDocDueDate() {
		return otherDocDueDate;
	}

	/**
	 * Set other document due date.
	 * 
	 * @param otherDocDueDate of type Date
	 */
	public void setOtherDocDueDate(Date otherDocDueDate) {
		this.otherDocDueDate = otherDocDueDate;
	}

	/**
	 * Get other document remarks.
	 * 
	 * @return String
	 */
	public String getOtherDocRemarks() {
		return otherDocRemarks;
	}

	/**
	 * Set other document remarks.
	 * 
	 * @param otherDocRemarks of type String
	 */
	public void setOtherDocRemarks(String otherDocRemarks) {
		this.otherDocRemarks = otherDocRemarks;
	}

	/**
	 * Get transaction date.
	 * 
	 * @return Date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Set transaction date.
	 * 
	 * @param transactionDate of type Date
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Get cms common reference id for actual and staging.
	 * 
	 * @return String
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id for actual and staging.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get latest title document by using transaction date and title doc id as a
	 * comparator.
	 * 
	 * @param allTitleDocs all commodity deal title documents
	 * @return latest commodity title document
	 */
	public static ICommodityTitleDocument getTitleDocsLatestByTitleDocID(ICommodityTitleDocument[] allTitleDocs) {
		if ((allTitleDocs == null) || (allTitleDocs.length == 0)) {
			return null;
		}

		CommodityTitleDocComparator c = new CommodityTitleDocComparator(
				CommodityTitleDocComparator.COMPARE_BY_TR_DATE_TD_ID);
		Arrays.sort(allTitleDocs, c);
		return allTitleDocs[allTitleDocs.length - 1];
	}

	/**
	 * Get latest title documents by transaction date. It will return a list of
	 * title docs as user can actually input multiple title doc with the same
	 * transaction date.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public static ICommodityTitleDocument[] getTitleDocsLatest(ICommodityTitleDocument[] allTitleDocs) {
		if ((allTitleDocs == null) || (allTitleDocs.length == 0)) {
			return allTitleDocs;
		}

		CommodityTitleDocComparator c = new CommodityTitleDocComparator();
		Arrays.sort(allTitleDocs, c);

		ArrayList list = new ArrayList();

		ICommodityTitleDocument temp = null;
		for (int i = allTitleDocs.length - 1; i >= 0; i--) {
			if (c.compare(allTitleDocs[i], temp) >= 0) {
				temp = allTitleDocs[i];
				list.add(temp);
			}
			else {
				break;
			}
		}

		return (OBCommodityTitleDocument[]) list.toArray(new OBCommodityTitleDocument[0]);
	}

	/**
	 * Get history of commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public static ICommodityTitleDocument[] getTitleDocsHistory(ICommodityTitleDocument[] allTitleDocs) {
		if ((allTitleDocs == null) || (allTitleDocs.length == 0)) {
			return allTitleDocs;
		}

		CommodityTitleDocComparator c = new CommodityTitleDocComparator();
		Arrays.sort(allTitleDocs, c);

		ArrayList list = new ArrayList();
		ICommodityTitleDocument temp = null;
		boolean isHistory = false;

		for (int i = allTitleDocs.length - 1; i >= 0; i--) {
			if (!isHistory && (c.compare(allTitleDocs[i], temp) >= 0)) {
				temp = allTitleDocs[i];
			}
			else {
				isHistory = true;
				list.add(allTitleDocs[i]);
			}
		}

		return (OBCommodityTitleDocument[]) list.toArray(new OBCommodityTitleDocument[0]);
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(titleDocID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof ICommodityTitleDocument)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}