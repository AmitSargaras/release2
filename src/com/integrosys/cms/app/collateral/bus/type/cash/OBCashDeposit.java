/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/OBCashDeposit.java,v 1.3 2003/10/23 06:20:47 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents cash deposit for cash collateral type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/23 06:20:47 $ Tag: $Name: $
 */
public class OBCashDeposit implements ICashDeposit {
	private long cashDepositID = ICMSConstant.LONG_MIN_VALUE;

	private String depositReceiptNo;

	// private String depositNo;
	private Date depositMaturityDate;

	private Amount depositAmount;

	private String depositCcyCode;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private String depositRefNo;

	private double fdrRate;

	private Date issueDate;

	private String thirdPartyBank;

	private String accountTypeValue;

	private String accountTypeNum;
	
	private int tenure = ICMSConstant.INT_INVALID_VALUE;
	
	private String tenureUOM;
	
	private boolean ownBank;
	
	private String groupAccountNumber;
	
	private String holdStatus;
	
	private boolean hasValidated;
	
	private Date verificationDate;
	
	//private double fdLienPercentage;
	
	private double depositeInterestRate;
	
	private String depositorName;

    //Andy Wong, 3 July 2009: collateral exists flag used for Stp inquiry, not persist to DB
    private String collateralExists;
/**/
    private String maker_id;
    
    private String checker_id;
    
    private String flag;
	
	private Date maker_date;
	
	private Date checker_date;
	
	private String searchFlag;
    /**/
    
    public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	/**
     * Methods for Lien added 
     * by @author sachin.patil 
     */
    
    private ILienMethod[] lien = null;
    
    private String systemName;      

	private String systemId;
    
    private String customerId;
    
    private String finwareId;
    
    private String active;
    
   // private ILienMethod lien;
    

	

	public String getCollateralExists() {
        return collateralExists;
    }

    public void setCollateralExists(String collateralExists) {
        this.collateralExists = collateralExists;
    }

    public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	public String getTenureUOM() {
		return tenureUOM;
	}

	public void setTenureUOM(String tenureUOM) {
		this.tenureUOM = tenureUOM;
	}

	public boolean getOwnBank() {
		return ownBank;
	}

	public void setOwnBank(boolean ownBank) {
		this.ownBank = ownBank;
	}

	public String getGroupAccountNumber() {
		return groupAccountNumber;
	}

	public void setGroupAccountNumber(String groupAccountNumber) {
		this.groupAccountNumber = groupAccountNumber;
	}

	/**
	 * Default Constructor.
	 */
	public OBCashDeposit() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICashDeposit
	 */
	public OBCashDeposit(ICashDeposit obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get cash deposit id.
	 * 
	 * @return long
	 */
	public long getCashDepositID() {
		return cashDepositID;
	}

	/**
	 * Set cash deposit id.
	 * 
	 * @param cashDepositID of type long
	 */
	public void setCashDepositID(long cashDepositID) {
		this.cashDepositID = cashDepositID;
	}

	/*
	 * public String getDepositNo() { return depositNo; }
	 * 
	 * public void setDepositNo(String depositNo) { this.depositNo = depositNo;
	 * }
	 */

	/**
	 * Get deposit receipt no.
	 * 
	 * @return String
	 */
	public String getDepositReceiptNo() {
		return depositReceiptNo;
	}

	/**
	 * Set deposit receipt no.
	 * 
	 * @param depositReceiptNo is of type String
	 */
	public void setDepositReceiptNo(String depositReceiptNo) {
		this.depositReceiptNo = depositReceiptNo;
	}

	/**
	 * Get deposit maturity date.
	 * 
	 * @return Date
	 */
	public Date getDepositMaturityDate() {
		return depositMaturityDate;
	}

	/**
	 * Set deposit maturity date.
	 * 
	 * @param depositMaturityDate is of type Date
	 */
	public void setDepositMaturityDate(Date depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	/**
	 * Get deposit amount.
	 * 
	 * @return Amount
	 */
	public Amount getDepositAmount() {
		return depositAmount;
	}

	/**
	 * Set deposit amount.
	 * 
	 * @param depositAmount of type Amount
	 */
	public void setDepositAmount(Amount depositAmount) {
		this.depositAmount = depositAmount;
	}

	/**
	 * Get deposit amount currency code.
	 * 
	 * @return String
	 */
	public String getDepositCcyCode() {
		return depositCcyCode;
	}

	/**
	 * Set deposit amount currency code.
	 * 
	 * @param depositCcyCode
	 */
	public void setDepositCcyCode(String depositCcyCode) {
		this.depositCcyCode = depositCcyCode;
	}

	/**
	 * Get reference id of staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set reference id of staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get the status of the cash deposit.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the cash deposit.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepositRefNo() {
		return depositRefNo;
	}

	public void setDepositRefNo(String depositRefNo) {
		this.depositRefNo = depositRefNo;
	}

	public double getFdrRate() {
		return fdrRate;
	}

	public void setFdrRate(double fdrRate) {
		this.fdrRate = fdrRate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getThirdPartyBank() {
		return thirdPartyBank;
	}

	public void setThirdPartyBank(String thirdPartyBank) {
		this.thirdPartyBank = thirdPartyBank;
	}

	public String getAccountTypeNum() {
		return accountTypeNum;
	}

	public void setAccountTypeNum(String accountTypeNum) {
		this.accountTypeNum = accountTypeNum;
	}

	public String getAccountTypeValue() {
		return accountTypeValue;
	}

	public void setAccountTypeValue(String accountTypeValue) {
		this.accountTypeValue = accountTypeValue;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public boolean getHasValidated() {
		return hasValidated;
	}

	public void setHasValidated(boolean hasValidated) {
		this.hasValidated = hasValidated;
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
		String hash = String.valueOf(cashDepositID);
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
		else if (!(obj instanceof OBCashDeposit)) {
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

	
	
	/**
	 * Get verification Date.
	 * 
	 * @return Date
	 */
	public Date getVerificationDate() {
		return verificationDate;
	}

	/**
	 *   verification Date.
	 * 
	 * @param verificationDate is of type Date
	 */
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}

	/**
	 * Get verification Date.
	 * 
	 * @return double
	 *//*
	public double getFdLienPercentage() {
		return fdLienPercentage;
	}

	*//**
	 *   fdLienPercentage double.
	 * 
	 * @param fdLienPercentage is of type Date
	 *//*
	public void setFdLienPercentage(double fdLienPercentage) {
		this.fdLienPercentage = fdLienPercentage;
	}
	*/
	/**
	 * Get verification Date.
	 * 
	 * @return double
	 */
	public double getDepositeInterestRate() {
		return depositeInterestRate;
	}

	/**
	 *   depositeInterestRate double.
	 * 
	 * @param depositeInterestRate is of type Date
	 */
	public void setDepositeInterestRate(double depositeInterestRate) {
		this.depositeInterestRate = depositeInterestRate;
	}

	public String getDepositorName() {
		return depositorName;
	}

	public void setDepositorName(String depositorName) {
		this.depositorName = depositorName;
	}		
	
	 public String getSystemName() {
			return systemName;
		}

		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}

		public String getSystemId() {
			return systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getFinwareId() {
			return finwareId;
		}

		public void setFinwareId(String finwareId) {
			this.finwareId = finwareId;
		}
		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}
	

	public ILienMethod[] getLien() {
		return lien;
	}

	public void setLien(ILienMethod[] lien) {
		this.lien = lien;
	}

	public String getMaker_id() {
		return maker_id;
	}

	public void setMaker_id(String maker_id) {
		this.maker_id = maker_id;
	}

	public String getChecker_id() {
		return checker_id;
	}

	public void setChecker_id(String checker_id) {
		this.checker_id = checker_id;
	}

	public Date getMaker_date() {
		return maker_date;
	}

	public void setMaker_date(Date maker_date) {
		this.maker_date = maker_date;
	}

	public Date getChecker_date() {
		return checker_date;
	}

	public void setChecker_date(Date checker_date) {
		this.checker_date = checker_date;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	/*public ILienMethod getLien() {
		return lien;
	}

	public void setLien(ILienMethod lien) {
		this.lien = lien;
	}*/
	 
}