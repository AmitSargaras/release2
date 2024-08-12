package com.integrosys.cms.batch.sibs.collateral;

/**
 * @author gp loh
 * date 02 oct 08
 *
 */

import java.util.Date;

public interface ICollateralFD extends java.io.Serializable {

	public void setCollateralFeedEntryID(long feedID);
	public long getCollateralFeedEntryID();
	
	public void setRecordType(String recType);
	public String getRecordType();

	public void setSecurityID(String secID);
	public String getSecurityID();

	// 	length:19 pos:21-39  desc:New Deposit/Source Reference Number
	public void setReferenceNo(String refNo);
	public String getReferenceNo();

	// type:Alpha len:19 pos:40-58    desc:New Deposit Receipt No
	public void setReceiptNumber(String recpNo);
	public String getReceiptNumber();

	//type:Alpha length:4 pos:59-62    desc :New Deposit Amount Currency Code
	public void setNewAmountCurrency(String nAmtCurrency);
	public String getNewAmountCurrency();

	// type:Numeric  len:17  decimal(15,2)  pos:63-79    desc:New Deposit amount
	public void setNewAmount(double newAmt);
	public double getNewAmount();

	// type:Alpha len:1 , pos:80-80, desc: O - Others, B-Bank
	public void setNewThirdPartyBank(String newBank);
	public String getNewThirdPartyBank();

	// type:Numeric, len:20	, decimal(11,9)	, pos:81-100, desc: New FDR Rate
	public void setNewFDRRate(double fdrRate);
	public double getNewFDRRate();

	// Date, len:8, pos:101-108 , desc:New  Issue Date
	public void setNewIssueDate(Date issueDate);
	public Date getNewIssueDate();


	// Date, len:8 , pos:109-116, desc:New Deposit Maturity Date
	public void setNewMaturityDate(Date matDate);
	public Date getNewMaturityDate();

}