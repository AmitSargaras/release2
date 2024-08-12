/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/IFinancingDoc.java,v 1.3 2005/09/15 06:00:48 czhou Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.Quantity;

/**
 * This interface represents Financing document.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/15 06:00:48 $ Tag: $Name: $
 */
public interface IFinancingDoc extends Serializable {
	/**
	 * Get financing document id.
	 * 
	 * @return long
	 */
	public long getFinancingDocID();

	/**
	 * Set financing document id.
	 * 
	 * @param financingDocID of type long
	 */
	public void setFinancingDocID(long financingDocID);

	/**
	 * Get sales documentation type code.
	 * 
	 * @return String
	 */
	public String getDocTypeCode();

	/**
	 * Set sales documentation type code.
	 * 
	 * @param docTypeCode of type String
	 */
	public void setDocTypeCode(String docTypeCode);

	/**
	 * Get other sales document type name.
	 * 
	 * @return String
	 */
	public String getOtherDocType();

	/**
	 * Set other sales document type name.
	 * 
	 * @param otherDocType of type String
	 */
	public void setOtherDocType(String otherDocType);

	/**
	 * Get description of goods.
	 * 
	 * @return String
	 */
	public String getGoodsDesc();

	/**
	 * Set description of goods.
	 * 
	 * @param goodsDesc of type String
	 */
	public void setGoodsDesc(String goodsDesc);

	/**
	 * Get sales order amount.
	 * 
	 * @return Amount
	 */
	public Amount getOrderAmount();

	/**
	 * Set sales order amount.
	 * 
	 * @param orderAmount of type Amount
	 */
	public void setOrderAmount(Amount orderAmount);

	/**
	 * Get sales order quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getOrderQuantity();

	/**
	 * Set sales order quantity.
	 * 
	 * @param orderQuantity of type Quantity
	 */
	public void setOrderQuantity(Quantity orderQuantity);

	/**
	 * Get sales document number.
	 * 
	 * @return String
	 */
	public String getDocumentNo();

	/**
	 * Set sales document number.
	 * 
	 * @param documentNo of type String
	 */
	public void setDocumentNo(String documentNo);

	/**
	 * Get sales order expiry date.
	 * 
	 * @return Date
	 */
	public Date getOrderExpDate();

	/**
	 * Set sales order expiry date.
	 * 
	 * @param orderExpDate of type Date
	 */
	public void setOrderExpDate(Date orderExpDate);

	/**
	 * Get export LC issuing bank name.
	 * 
	 * @return String
	 */
	public String getBankName();

	/**
	 * Set export LC issuing bank name.
	 * 
	 * @param bankName of type String
	 */
	public void setBankName(String bankName);

	/**
	 * Get country of export LC issuing bank.
	 * 
	 * @return String
	 */
	public String getBankCountryCode();

	/**
	 * Set country of export LC issuing bank.
	 * 
	 * @param bankCountryCode String
	 */
	public void setBankCountryCode(String bankCountryCode);

	/**
	 * Get Letter of Cover reference.
	 * 
	 * @return String
	 */
	public String getLCRefNo();

	/**
	 * Set Letter of Cover reference.
	 * 
	 * @param lCRefNo of type String
	 */
	public void setLCRefNo(String lCRefNo);

	/**
	 * Get Letter of Cover expiry date.
	 * 
	 * @return Date
	 */
	public Date getLCExpDate();

	/**
	 * Set Letter of Cover expiry date.
	 * 
	 * @param lCExpDate of type Date
	 */
	public void setLCExpDate(Date lCExpDate);

	/**
	 * Get Letter of Credit due date.
	 * 
	 * @return Date
	 */
	public Date getLOCDueDate();

	/**
	 * Set Letter of Credit due date.
	 * 
	 * @param locDueDate of type Date
	 */
	public void setLOCDueDate(Date locDueDate);

	/**
	 * Get counter party name.
	 * 
	 * @return String
	 */
	public String getCounterParty();

	/**
	 * Set counter party name.
	 * 
	 * @param counterParty of type String
	 */
	public void setCounterParty(String counterParty);

	/**
	 * Get terms matched for back-to-back LC.
	 * 
	 * @return String
	 */
	public String getTermsMatched();

	/**
	 * Set terms matched for back-to-back LC.
	 * 
	 * @param termsMatched of type String
	 */
	public void setTermsMatched(String termsMatched);

	/**
	 * Get financing document remarks.
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Set financing document remarks.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks);

	/**
	 * Get common reference id for actual and staging data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set common reference id for actual and staging data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Get cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}