/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author User
 *
 */
public class OBCollateralFD implements ICollateralFD {

	private String recordType;
	private String securityID;
	private String referenceNo;
	private String receiptNumber;
	private String newAmountCurrency;
	private double newAmount;
	private String newThirdPartyBank;
	private double newFDRRate;
	private Date   newIssueDate;
	private Date   newMaturityDate;
	private String endLineInd;
	private long feedID;

	/**
	 * Default Constructor
	 */
	public OBCollateralFD() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type ICollateralFD
	 */
	public OBCollateralFD(ICollateralFD value) {
		this();
		AccessorUtil.copyValue(value, this);
	}



	public void setCollateralFeedEntryID(long feedID) {
		this.feedID = feedID;
	}
	public long getCollateralFeedEntryID() {
		return feedID;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewAmount()
	 */
	public double getNewAmount() {
		// TODO Auto-generated method stub
		return (newAmount/100);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewAmountCurrency()
	 */
	public String getNewAmountCurrency() {
		// TODO Auto-generated method stub
		return newAmountCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewFDRRate()
	 */
	public double getNewFDRRate() {
		// TODO Auto-generated method stub
		return (newFDRRate/1000000000);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewIssueDate()
	 */
	public Date getNewIssueDate() {
		// TODO Auto-generated method stub
		return newIssueDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewMaturityDate()
	 */
	public Date getNewMaturityDate() {
		// TODO Auto-generated method stub
		return newMaturityDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getNewThirdPartyBank()
	 */
	public String getNewThirdPartyBank() {
		// TODO Auto-generated method stub
		return newThirdPartyBank;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getReceiptNumber()
	 */
	public String getReceiptNumber() {
		// TODO Auto-generated method stub
		return receiptNumber;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getRecordType()
	 */
	public String getRecordType() {
		// TODO Auto-generated method stub
		return recordType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getReferenceNo()
	 */
	public String getReferenceNo() {
		// TODO Auto-generated method stub
		return referenceNo;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#getSecurityID()
	 */
	public String getSecurityID() {
		// TODO Auto-generated method stub
		return securityID;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewAmount(java.lang.String)
	 */
	public void setNewAmount(double newAmt) {
		// TODO Auto-generated method stub
		this.newAmount = newAmt;
	}


	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewAmountCurrency(java.lang.String)
	 */
	public void setNewAmountCurrency(String nAmtCurrency) {
		// TODO Auto-generated method stub
		this.newAmountCurrency = nAmtCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewFDRRate(java.lang.String)
	 */
	public void setNewFDRRate(double fdrRate) {
		// TODO Auto-generated method stub
		this.newFDRRate = fdrRate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewIssueDate(java.util.Date)
	 */
	public void setNewIssueDate(Date issueDate) {
		// TODO Auto-generated method stub
		this.newIssueDate = issueDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewMaturityDate(java.util.Date)
	 */
	public void setNewMaturityDate(Date matDate) {
		// TODO Auto-generated method stub
		this.newMaturityDate = matDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setNewThirdPartyBank(java.lang.String)
	 */
	public void setNewThirdPartyBank(String newBank) {
		// TODO Auto-generated method stub
		this.newThirdPartyBank = newBank;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setReceiptNumber(java.lang.String)
	 */
	public void setReceiptNumber(String recpNo) {
		// TODO Auto-generated method stub
		this.receiptNumber = recpNo;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setRecordType(java.lang.String)
	 */
	public void setRecordType(String recType) {
		// TODO Auto-generated method stub
		this.recordType = recType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setReferenceNo(java.lang.String)
	 */
	public void setReferenceNo(String refNo) {
		// TODO Auto-generated method stub
		this.referenceNo = refNo;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralFD#setSecurityID(java.lang.String)
	 */
	public void setSecurityID(String secID) {
		// TODO Auto-generated method stub
		this.securityID = secID;
	}

	public void setEndLineIndicator(String endT) {
		this.endLineInd = endT;
	}
	public String getEndLineIndicator() {
		return endLineInd;
	}
}
