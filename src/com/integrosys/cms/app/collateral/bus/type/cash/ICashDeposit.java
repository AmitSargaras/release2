/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/ICashDeposit.java,v 1.3 2003/10/23 06:20:47 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ISystem;

/**
 * This interface represents cash deposit for cash collateral type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/23 06:20:47 $ Tag: $Name: $
 */

public interface ICashDeposit extends Serializable {
	/**
	 * Get cash deposit id.
	 * 
	 * @return long
	 */
	public long getCashDepositID();

	/**
	 * Set cash deposit id.
	 * 
	 * @param cashDepositID of type long
	 */
	public void setCashDepositID(long cashDepositID);

	/**
	 * Get deposit receipt no.
	 * 
	 * @return String
	 */
	public String getDepositReceiptNo();

	/**
	 * Set deposit receipt no.
	 * 
	 * @param depositReceiptNo is of type String
	 */
	public void setDepositReceiptNo(String depositReceiptNo);

	/**/
	 public String getFlag();
	    
	 public void setFlag(String flag);
	    
    public String getMaker_id();
    
    public void setMaker_id(String maker_id);
    
    public String getChecker_id();
    
    public void setChecker_id(String checker_id);
	
	public Date getMaker_date();

	public void setMaker_date(Date maker_date);
	
	public Date getChecker_date();

	public void setChecker_date(Date checker_date);
	
	 public String getSearchFlag();
	    
	    public void setSearchFlag(String searchFlag);
	/**/
	/**
	 * Get deposit maturity date.
	 * 
	 * @return Date
	 */
	public Date getDepositMaturityDate();

	/**
	 * Set deposit maturity date.
	 * 
	 * @param depositMaturityDate is of type Date
	 */
	public void setDepositMaturityDate(Date depositMaturityDate);

	/**
	 * Get deposit amount.
	 * 
	 * @return Amount
	 */
	public Amount getDepositAmount();

	/**
	 * Set deposit amount.
	 * 
	 * @param depositAmount of type Amount
	 */
	public void setDepositAmount(Amount depositAmount);

	/**
	 * Get deposit amount currency code.
	 * 
	 * @return String
	 */
	public String getDepositCcyCode();

	/**
	 * Set deposit amount currency code.
	 * 
	 * @param depositCcyCode
	 */
	public void setDepositCcyCode(String depositCcyCode);

	/**
	 * Get reference id of staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set reference id of staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get the status of the cash deposit.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of the cash deposit.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/*
	 * public String getDepositNo() ; public void setDepositNo(String depositNo)
	 * ;
	 */

	public String getDepositRefNo();

	public void setDepositRefNo(String depositRefNo);

	public double getFdrRate();

	public void setFdrRate(double fdrRate);

	public Date getIssueDate();

	public void setIssueDate(Date issueDate);

	public String getThirdPartyBank();

	public void setThirdPartyBank(String thirdPartyBank);

	public String getAccountTypeNum();

	public void setAccountTypeNum(String accountTypeNum);

	public String getAccountTypeValue();

	public void setAccountTypeValue(String accountTypeValue);

	public int getTenure();
	
	public void setTenure(int tenure);
	
	public String getTenureUOM();
	
	public void setTenureUOM(String tenureUOM);
	
	public boolean getOwnBank();
	
	public void setOwnBank(boolean ownBank);
	
	public String getGroupAccountNumber();
	
	public void setGroupAccountNumber(String groupAccountNumber);
	
	public String getHoldStatus();

	public void setHoldStatus(String holdStatus);
	
	public boolean getHasValidated();

	public void setHasValidated(boolean hasValidated);

    //Andy Wong, 3 July 2009: collateral exists flag used for Stp inquiry, not persist to DB    
    public String getCollateralExists();

    public void setCollateralExists(String collateralExists);
    
    public Date getVerificationDate();
    
    public void setVerificationDate(Date verificationDate);
    
    /*public double getFdLienPercentage();

	public void setFdLienPercentage(double fdLienPercentage);*/
		
	public double getDepositeInterestRate();

	public void setDepositeInterestRate(double depositeInterestRate);
	
	public String getDepositorName();

    public void setDepositorName(String depositorName);   
   
    
    public ILienMethod[] getLien();  

	public void setLien(ILienMethod[] lien); 
	
	
	public String getSystemId();

    public void setSystemId(String systemId); 
    
    public String getSystemName();

    public void setSystemName(String systemName);
    
    public String getCustomerId();

    public void setCustomerId(String customerId);
    
    public String getFinwareId();

    public void setFinwareId(String finwareId);
    
    public String getActive();

    public void setActive(String active);
   
}