/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/OBPostDatedCheque.java,v 1.12 2004/02/04 09:09:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankDao;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * This class represents post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2004/02/04 09:09:46 $ Tag: $Name: $
 */
public class OBPostDatedCheque implements IPostDatedCheque {
	

	

	
	
	public void setOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables) {
		this.isOwnProceedsOfReceivables = isOwnProceedsOfReceivables;
	}

	private long postDatedChequeID = ICMSConstant.LONG_MIN_VALUE;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private Amount chequeAmount;

	private String chequeCcyCode;

	private String issuerName;

	private String draweeBank;

	private String country;

	private Date issueDate;

	private Date expiryDate;

	private String chequeType;

	private String ownAccNo;

	private boolean isOwnProceedsOfReceivables;

	private String ownAccNoLocation;

	private String collateralCustodian;

	private String collateralCustodianType;

	private String isExchangeCtrlObtained;
	
	private Date exchangeCtrlDate;

	private double margin;

	private Amount beforeMarginValue;
	
	private Amount afterMarginValue;

	private Date valuationDate;

	private String valuationCcyCode;
	
	private String remarks;
	
	public IOtherBank otherbank;
	
	public IOtherBranch otherbranch;
	
	public ISystemBank systemBank;
	
	public ISystemBankBranch systemBankBranch;
	
	private Date startDate;
	
	private Date maturityDate;
	
	private String ramId;
	
    private String branchName;
    
    private String branchCode;
	
    private String chequeNumber;
	
    private String returnStatus;
	
    private Date returnDate;
	   
    private String bankName;
		
    private String bankCode;
		
    private String packetNumber;
		
    private String loanable;
		
    private long chequeNoFrom;
	
    private long chequeNoTo;
	
    private String bulkSingle;
		
  
	
	private String status = ICMSConstant.STATE_ACTIVE;
	
	
	
	
	

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}

	
	
	
	
	public long getChequeNoFrom() {
		return chequeNoFrom;
	}

	public void setChequeNoFrom(long chequeNoFrom) {
		this.chequeNoFrom = chequeNoFrom;
	}

	public long getChequeNoTo() {
		return chequeNoTo;
	}

	public void setChequeNoTo(long chequeNoTo) {
		this.chequeNoTo = chequeNoTo;
	}

	public String getBulkSingle() {
		return bulkSingle;
	}

	public void setBulkSingle(String bulkSingle) {
		this.bulkSingle = bulkSingle;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPacketNumber() {
		return packetNumber;
	}

	public void setPacketNumber(String packetNumber) {
		this.packetNumber = packetNumber;
	}

	public String getLoanable() {
		return loanable;
	}

	public void setLoanable(String loanable) {
		this.loanable = loanable;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	/**
	 * Default Constructor.
	 */
	public OBPostDatedCheque() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPostDatedCheque
	 */
	public OBPostDatedCheque(IPostDatedCheque obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get post dated cheque id.
	 * 
	 * @return long
	 */
	public long getPostDatedChequeID() {
		return postDatedChequeID;
	}

	/**
	 * Set post dated cheque id.
	 * 
	 * @param postDatedChequeID of type long
	 */
	public void setPostDatedChequeID(long postDatedChequeID) {
		this.postDatedChequeID = postDatedChequeID;
	}

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get amount of cheque.
	 * 
	 * @return Amount
	 */
	public Amount getChequeAmount() {
		return chequeAmount;
	}

	/**
	 * Set amount of cheque.
	 * 
	 * @param chequeAmount is of type Amount
	 */
	public void setChequeAmount(Amount chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	/**
	 * Get cheque amount currency code.
	 * 
	 * @return String
	 */
	public String getChequeCcyCode() {
		return chequeCcyCode;
	}

	/**
	 * Set cheque amount currency code.
	 * 
	 * @param chequeCcyCode of type String
	 */
	public void setChequeCcyCode(String chequeCcyCode) {
		this.chequeCcyCode = chequeCcyCode;
	}

	/**
	 * Get issuer.
	 * 
	 * @return String
	 */
	public String getIssuerName() {
		return issuerName;
	}

	/**
	 * Set issuer.
	 * 
	 * @param issuerName is of type String
	 */
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	/**
	 * Get drawee bank.
	 * 
	 * @return String
	 */
	public String getDraweeBank() {
		return draweeBank;
	}

	/**
	 * Set drawee bank.
	 * 
	 * @param draweeBank is of type String
	 */
	public void setDraweeBank(String draweeBank) {
		this.draweeBank = draweeBank;
	}

	/**
	 * Get country.
	 * 
	 * @return String
	 */
	public String getCountry() {
		if (country != null) {
			country = country.trim();
		}
		return country;
	}

	/**
	 * Set country.
	 * 
	 * @param country is of type String
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Get issue date.
	 * 
	 * @return Date
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * Set issue date.
	 * 
	 * @param issueDate is of type Date
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Get expiry date.
	 * 
	 * @return Date
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Set expiry date.
	 * 
	 * @param expiryDate of type Date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Get type of cheque.
	 * 
	 * @return String
	 */
	public String getChequeType() {
		return chequeType;
	}

	/**
	 * Set type of cheque.
	 * 
	 * @param chequeType is of type String
	 */
	public void setChequeType(String chequeType) {
		this.chequeType = chequeType;
	}

	/**
	 * Get own bank a/c no to which proceeds of receivables credited.
	 * 
	 * @return String
	 */
	public String getOwnAccNo() {
		return ownAccNo;
	}

	/**
	 * Set own bank a/c no to which proceeds of receivables credited.
	 * 
	 * @param ownAccNo is of type String
	 */
	public void setOwnAccNo(String ownAccNo) {
		this.ownAccNo = ownAccNo;
	}

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables() {
		return isOwnProceedsOfReceivables;
	}

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables) {
		this.isOwnProceedsOfReceivables = isOwnProceedsOfReceivables;
	}

	/**
	 * Get location of own bank account.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation() {
		return ownAccNoLocation;
	}

	/**
	 * Set location of own bank account.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setOwnAccNoLocation(String ownAccNoLocation) {
		this.ownAccNoLocation = ownAccNoLocation;
	}

	/**
	 * Get security custodian.
	 * 
	 * @return String
	 */
	public String getCollateralCustodian() {
		return collateralCustodian;
	}

	/**
	 * Set security custodian.
	 * 
	 * @param collateralCustodian of type String
	 */
	public void setCollateralCustodian(String collateralCustodian) {
		this.collateralCustodian = collateralCustodian;
	}

	/**
	 * Get collateral custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType() {
		return collateralCustodianType;
	}

	/**
	 * Set collateral custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType) {
		this.collateralCustodianType = collateralCustodianType;
	}

	/**
	 * Get if exchange control approval obtained.
	 * 
	 * @return String
	 */
	public String getIsExchangeCtrlObtained() {
		return isExchangeCtrlObtained;
	}

	/**
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained) {
		this.isExchangeCtrlObtained = isExchangeCtrlObtained;
	}

	/**
	 * Get margin.
	 * 
	 * @return double
	 */
	public double getMargin() {
		return margin;
	}

	/**
	 * Set margin.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		this.margin = margin;
	}

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getBeforeMarginValue() {
		return beforeMarginValue;
	}

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount beforeMarginValue) {
		this.beforeMarginValue = beforeMarginValue;
	}

	/**
	 * Get valuation date.
	 * 
	 * @return Date
	 */
	public Date getValuationDate() {
		return valuationDate;
	}

	/**
	 * Set valuation date.
	 * 
	 * @param valuationDate of type Date
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * Get valuation currency code.
	 * 
	 * @return String
	 */
	public String getValuationCcyCode() {
		return valuationCcyCode;
	}

	/**
	 * Set valuation currency code.
	 * 
	 * @param valuationCcyCode of type String
	 */
	public void setValuationCcyCode(String valuationCcyCode) {
		this.valuationCcyCode = valuationCcyCode;
	}

	/**
	 * Get the status of the cheque, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the cheque, deleted or active.
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
		String hash = String.valueOf(postDatedChequeID);
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
		else if (!(obj instanceof OBPostDatedCheque)) {
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

	public Date getExchangeCtrlDate() {
		return exchangeCtrlDate;
	}

	public void setExchangeCtrlDate(Date exchangeCtrlDate) {
		this.exchangeCtrlDate = exchangeCtrlDate;
	}

	public Amount getAfterMarginValue() {
		return afterMarginValue;
	}

	public void setAfterMarginValue(Amount afterMarginValue) {
		this.afterMarginValue = afterMarginValue;
	}
	public ISystemBank getSystemBank() {
		String sysbankId = getDraweeBank();
		String bankId = "";
		if (sysbankId == null||"".equals(sysbankId)) {
			return null;
		}else if(sysbankId.equals("S")){
			bankId=getBankCode();
			
		}else if(sysbankId.equals("O")){
		
			return null;
		}
		
		
			 OBSystemBank bank = null;;
			
				ISystemBankDao systemBank = (ISystemBankDao)BeanHouse.get("systemBankDao");
			return	systemBank.getSystemBankByCode(bankId);
	}
	
	public void setSystemBank(ISystemBank systembank) {
		if (null == systembank) {
			setBankCode(null);
		}
		else {
			setBankCode(String.valueOf(systembank.getId()));
		}
	}
	
	public ISystemBankBranch getSystemBankBranch() {
		String draweeBranch = getIssuerName();
		String bankIdval = "";
		if (draweeBranch == null||"".equals(draweeBranch)) {
			return null;
		}else if(draweeBranch.equals("SB")){
			bankIdval=getBranchCode();
			
		}else if(draweeBranch.equals("OB")){
			return null;
			
		}
		
		
		
		OBOtherBranch branch = null;
		
		ISystemBankBranchDao otherBank = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
		return	otherBank.getSystemBankBranch(bankIdval);
	}
	
	public void setSystemBankBranch(ISystemBankBranch otherbranch) {
		if (null == otherbranch) {
			setBranchCode(null);
		}
		else {
			setBranchCode(String.valueOf(otherbranch.getId()));
		}
	}
	
	
	
	

	public IOtherBank getOtherbank() {
		String bankId = getDraweeBank();
		String bankIdVAL="";
		if (bankId == null||"".equals(bankId)) {
			return null;
		}else if(bankId.equals("O")){
			bankIdVAL=getBankCode();
			
		}else if(bankId.equals("S")){
		
			return null;
		}
			 OBOtherBank bank = null;;
			
				IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
			return	otherBank.getOtherBankListForPDC(bankIdVAL);
	}

	public void setOtherbank(IOtherBank otherbank) {
		if (null == otherbank) {
			setBankCode(null);
		}
		else {
			setBankCode(String.valueOf(otherbank.getId()));
		}
	}

	public IOtherBranch getOtherbranch() {
		String branchId =getIssuerName();
		String bankIdval = "";
		if (branchId == null||"".equals(branchId)) {
			return null;
		}	else if(branchId.equals("OB")){
				bankIdval=getBranchCode();
				
			}else if(branchId.equals("SB")){
				return null;
				
			}
		
		
		OBOtherBranch branch = null;
		
		IOtherBranchDAO otherBank = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
		return	otherBank.getOtherBranchListForPDC(bankIdval);
	}

	public void setOtherbranch(IOtherBranch otherbranch) {
		if (null == otherbranch) {
			setBranchCode(null);
		}
		else {
			setBranchCode(String.valueOf(otherbranch.getId()));
		}
	}


}