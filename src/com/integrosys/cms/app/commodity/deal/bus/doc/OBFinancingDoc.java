/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/OBFinancingDoc.java,v 1.3 2005/09/15 06:00:48 czhou Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Financing Doc entity.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/15 06:00:48 $ Tag: $Name: $
 */
public class OBFinancingDoc implements IFinancingDoc {
	private long financingDocID;

	private String docTypeCode;

	private String otherDocType;

	private String goodsDesc;

	private Amount orderAmount;

	private Quantity orderQuantity;

	private String documentNo;

	private Date orderExpDate;

	private String bankName;

	private String bankCountryCode;

	private String lCRefNo;

	private Date lCExpDate;

	private Date locDueDate;

	private String counterParty;

	private String termsMatched;

	private String remarks;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default Constructor.
	 */
	public OBFinancingDoc() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IFinancingDoc
	 */
	public OBFinancingDoc(IFinancingDoc obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get financing document id.
	 * 
	 * @return long
	 */
	public long getFinancingDocID() {
		return financingDocID;
	}

	/**
	 * Set financing document id.
	 * 
	 * @param financingDocID of type long
	 */
	public void setFinancingDocID(long financingDocID) {
		this.financingDocID = financingDocID;
	}

	/**
	 * Get sales documentation type code.
	 * 
	 * @return String
	 */
	public String getDocTypeCode() {
		return docTypeCode;
	}

	/**
	 * Set sales documentation type code.
	 * 
	 * @param docTypeCode of type String
	 */
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}

	/**
	 * Get other sales document type name.
	 * 
	 * @return String
	 */
	public String getOtherDocType() {
		return otherDocType;
	}

	/**
	 * Set other sales document type name.
	 * 
	 * @param otherDocType of type String
	 */
	public void setOtherDocType(String otherDocType) {
		this.otherDocType = otherDocType;
	}

	/**
	 * Get description of goods.
	 * 
	 * @return String
	 */
	public String getGoodsDesc() {
		return goodsDesc;
	}

	/**
	 * Set description of goods.
	 * 
	 * @param goodsDesc of type String
	 */
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	/**
	 * Get sales order amount.
	 * 
	 * @return Amount
	 */
	public Amount getOrderAmount() {
		return orderAmount;
	}

	/**
	 * Set sales order amount.
	 * 
	 * @param orderAmount of type Amount
	 */
	public void setOrderAmount(Amount orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * Get sales order quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getOrderQuantity() {
		return orderQuantity;
	}

	/**
	 * Set sales order quantity.
	 * 
	 * @param orderQuantity of type Quantity
	 */
	public void setOrderQuantity(Quantity orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	/**
	 * Get sales document number.
	 * 
	 * @return String
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * Set sales document number.
	 * 
	 * @param documentNo of type String
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	/**
	 * Get sales order expiry date.
	 * 
	 * @return Date
	 */
	public Date getOrderExpDate() {
		return orderExpDate;
	}

	/**
	 * Set sales order expiry date.
	 * 
	 * @param orderExpDate of type Date
	 */
	public void setOrderExpDate(Date orderExpDate) {
		this.orderExpDate = orderExpDate;
	}

	/**
	 * Get export LC issuing bank name.
	 * 
	 * @return String
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Set export LC issuing bank name.
	 * 
	 * @param bankName of type String
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Get country of export LC issuing bank.
	 * 
	 * @return String
	 */
	public String getBankCountryCode() {
		return bankCountryCode;
	}

	/**
	 * Set country of export LC issuing bank.
	 * 
	 * @param bankCountryCode String
	 */
	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}

	/**
	 * Get Letter of Cover reference.
	 * 
	 * @return String
	 */
	public String getLCRefNo() {
		return lCRefNo;
	}

	/**
	 * Set Letter of Cover reference.
	 * 
	 * @param lCRefNo of type String
	 */
	public void setLCRefNo(String lCRefNo) {
		this.lCRefNo = lCRefNo;
	}

	/**
	 * Get Letter of Cover expiry date.
	 * 
	 * @return Date
	 */
	public Date getLCExpDate() {
		return lCExpDate;
	}

	/**
	 * Set Letter of Cover expiry date.
	 * 
	 * @param lCExpDate of type Date
	 */
	public void setLCExpDate(Date lCExpDate) {
		this.lCExpDate = lCExpDate;
	}

	/**
	 * Get Letter of Credit due date.
	 * 
	 * @return Date
	 */
	public Date getLOCDueDate() {
		return locDueDate;
	}

	/**
	 * Set Letter of Credit due date.
	 * 
	 * @param locDueDate of type Date
	 */
	public void setLOCDueDate(Date locDueDate) {
		this.locDueDate = locDueDate;
	}

	/**
	 * Get counter party name.
	 * 
	 * @return String
	 */
	public String getCounterParty() {
		return counterParty;
	}

	/**
	 * Set counter party name.
	 * 
	 * @param counterParty of type String
	 */
	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	/**
	 * Get terms matched for back-to-back LC.
	 * 
	 * @return String
	 */
	public String getTermsMatched() {
		return termsMatched;
	}

	/**
	 * Set terms matched for back-to-back LC.
	 * 
	 * @param termsMatched of type String
	 */
	public void setTermsMatched(String termsMatched) {
		this.termsMatched = termsMatched;
	}

	/**
	 * Get financing document remarks.
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set financing document remarks.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get common reference id for actual and staging data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id for actual and staging data.
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
	 * Get cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
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
		String hash = String.valueOf(financingDocID);
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
		else if (!(obj instanceof IFinancingDoc)) {
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