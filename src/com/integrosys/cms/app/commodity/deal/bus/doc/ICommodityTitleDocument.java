/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/ICommodityTitleDocument.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;

/**
 * This interface represents Commodity Title Document.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface ICommodityTitleDocument extends Serializable {
	/**
	 * Get commodity title document id.
	 * 
	 * @return long
	 */
	public long getTitleDocID();

	/**
	 * Set commodity title document id.
	 * 
	 * @param titleDocID of type long
	 */
	public void setTitleDocID(long titleDocID);

	/**
	 * Get process stage code.
	 * 
	 * @return String
	 */
	public String getProcessStageCode();

	/**
	 * Set process stage code.
	 * 
	 * @param processStageCode of type String
	 */
	public void setProcessStageCode(String processStageCode);

	/**
	 * Get eligibility of advance percentage.
	 * 
	 * @return double
	 */
	public double getAdvEligibilityPct();

	/**
	 * Set eligibility of advance percentage.
	 * 
	 * @param advEligibilityPct of type double
	 */
	public void setAdvEligibilityPct(double advEligibilityPct);

	/**
	 * Get indicator to check if the title doc is secured or not.
	 * 
	 * @return String
	 */
	public String getIsSecured();

	/**
	 * Set indicator to check if the title doc is secured or not.
	 * 
	 * @param isSecured of type String
	 */
	public void setIsSecured(String isSecured);

	/**
	 * Get title document type.
	 * 
	 * @return ITitleDocument
	 */
	public ITitleDocument getTitleDocType();

	/**
	 * Set title document type.
	 * 
	 * @param titleDocType of type ITitleDocument
	 */
	public void setTitleDocType(ITitleDocument titleDocType);

	/**
	 * Get warehouse details.
	 * 
	 * @return IWarehouseReceipt[]
	 */
	public IWarehouseReceipt[] getWarehouseReceipts();

	/**
	 * Set warehouse details.
	 * 
	 * @param warehouseReceipts of type IWarehouseReceipt[]
	 */
	public void setWarehouseReceipts(IWarehouseReceipt[] warehouseReceipts);

	/**
	 * Get bill of lading number.
	 * 
	 * @return String
	 */
	public String getBLNo();

	/**
	 * Set bill of lading number.
	 * 
	 * @param bLNo of type String
	 */
	public void setBLNo(String bLNo);

	/**
	 * Get shipping company name.
	 * 
	 * @return String
	 */
	public String getBLShippingCompany();

	/**
	 * Set shipping company name.
	 * 
	 * @param bLShippingCompany of type String
	 */
	public void setBLShippingCompany(String bLShippingCompany);

	/**
	 * Get bill of lading date.
	 * 
	 * @return Date
	 */
	public Date getBLDate();

	/**
	 * Set bill of lading date.
	 * 
	 * @param bLDate of type Date
	 */
	public void setBLDate(Date bLDate);

	/**
	 * Get bill of lading remarks.
	 * 
	 * @return String
	 */
	public String getBLRemarks();

	/**
	 * Set bill of lading remarks.
	 * 
	 * @param bLRemarks of type String
	 */
	public void setBLRemarks(String bLRemarks);

	/**
	 * Get trust receipt number.
	 * 
	 * @return String
	 */
	public String getTRNo();

	/**
	 * Set trust receipt number.
	 * 
	 * @param tRNo of type String
	 */
	public void setTRNo(String tRNo);

	/**
	 * Get trust receipt's bill of lading reference number.
	 * 
	 * @return String
	 */
	public String getTRBillLadingNo();

	/**
	 * Set trust receipt's bill of lading reference number.
	 * 
	 * @param tRBillLadingNo of type String
	 */
	public void setTRBillLadingNo(String tRBillLadingNo);

	/**
	 * Get trust receipt amount.
	 * 
	 * @return Amount
	 */
	public Amount getTRAmount();

	/**
	 * Set trust receipt amount.
	 * 
	 * @param tRAmount of type Amount
	 */
	public void setTRAmount(Amount tRAmount);

	/**
	 * Get trust receipt date.
	 * 
	 * @return Date
	 */
	public Date getTRDate();

	/**
	 * Set trust receipt date.
	 * 
	 * @param tRDate of type Date
	 */
	public void setTRDate(Date tRDate);

	/**
	 * Get trust receipt maturity date.
	 * 
	 * @return Date
	 */
	public Date getTRMaturityDate();

	/**
	 * Set trust receipt maturity date.
	 * 
	 * @param tRMaturityDate of type Date
	 */
	public void setTRMaturityDate(Date tRMaturityDate);

	/**
	 * Get trust receipt remarks.
	 * 
	 * @return String
	 */
	public String getTRRemarks();

	/**
	 * Set trust receipt remarks.
	 * 
	 * @param tRRemarks of type String
	 */
	public void setTRRemarks(String tRRemarks);

	/**
	 * Get other document number.
	 * 
	 * @return String
	 */
	public String getOtherDocNo();

	/**
	 * Set other document number.
	 * 
	 * @param otherDocNo of type String
	 */
	public void setOtherDocNo(String otherDocNo);

	/**
	 * Get other document description.
	 * 
	 * @return String
	 */
	public String getOtherDocDesc();

	/**
	 * Set other document description.
	 * 
	 * @param otherDocDesc of type String
	 */
	public void setOtherDocDesc(String otherDocDesc);

	/**
	 * Get other document date.
	 * 
	 * @return Date
	 */
	public Date getOtherDocDate();

	/**
	 * Set other document date.
	 * 
	 * @param otherDocDate of type Date
	 */
	public void setOtherDocDate(Date otherDocDate);

	/**
	 * Get other document due date.
	 * 
	 * @return Date
	 */
	public Date getOtherDocDueDate();

	/**
	 * Set other document due date.
	 * 
	 * @param otherDocDueDate of type Date
	 */
	public void setOtherDocDueDate(Date otherDocDueDate);

	/**
	 * Get other document remarks.
	 * 
	 * @return String
	 */
	public String getOtherDocRemarks();

	/**
	 * Set other document remarks.
	 * 
	 * @param otherDocRemarks of type String
	 */
	public void setOtherDocRemarks(String otherDocRemarks);

	/**
	 * Get transaction date.
	 * 
	 * @return Date
	 */
	public Date getTransactionDate();

	/**
	 * Set transaction date.
	 * 
	 * @param transactionDate of type Date
	 */
	public void setTransactionDate(Date transactionDate);

	/**
	 * Get cms common reference id for actual and staging.
	 * 
	 * @return String
	 */
	public long getRefID();

	/**
	 * Set common reference id for actual and staging.
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
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}