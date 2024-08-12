/*
O * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/IPostDatedCheque.java,v 1.12 2004/02/04 09:09:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2004/02/04 09:09:46 $ Tag: $Name: $
 */
public interface IPostDatedCheque extends Serializable {
	/**
	 * Get post dated cheque id.
	 * 
	 * @return long
	 */
	public long getPostDatedChequeID();

	/**
	 * Set post dated cheque id.
	 * 
	 * @param postDatedChequeID of type long
	 */
	public void setPostDatedChequeID(long postDatedChequeID);

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get amount of cheque.
	 * 
	 * @return Amount
	 */
	public Amount getChequeAmount();

	/**
	 * Set amount of cheque.
	 * 
	 * @param chequeAmount is of type Amount
	 */
	public void setChequeAmount(Amount chequeAmount);

	/**
	 * Get cheque amount currency code.
	 * 
	 * @return String
	 */
	public String getChequeCcyCode();

	/**
	 * Set cheque amount currency code.
	 * 
	 * @param chequeCcyCode of type String
	 */
	public void setChequeCcyCode(String chequeCcyCode);

	/**
	 * Get issuer.
	 * 
	 * @return String
	 */
	public String getIssuerName();

	/**
	 * Set issuer.
	 * 
	 * @param issuerName is of type String
	 */
	public void setIssuerName(String issuerName);

	/**
	 * Get drawee bank.
	 * 
	 * @return String
	 */
	public String getDraweeBank();

	/**
	 * Set drawee bank.
	 * 
	 * @param draweeBank is of type String
	 */
	public void setDraweeBank(String draweeBank);

	/**
	 * Get country.
	 * 
	 * @return String
	 */
	public String getCountry();

	/**
	 * Set country.
	 * 
	 * @param country is of type String
	 */
	public void setCountry(String country);

	/**
	 * Get issue date.
	 * 
	 * @return Date
	 */
	public Date getIssueDate();

	/**
	 * Set issue date.
	 * 
	 * @param issueDate is of type Date
	 */
	public void setIssueDate(Date issueDate);

	/**
	 * Get expiry date.
	 * 
	 * @return Date
	 */
	public Date getExpiryDate();

	/**
	 * Set expiry date.
	 * 
	 * @param expiryDate of type Date
	 */
	public void setExpiryDate(Date expiryDate);

	/**
	 * Get type of cheque.
	 * 
	 * @return String
	 */
	public String getChequeType();

	/**
	 * Set type of cheque.
	 * 
	 * @param chequeType is of type String
	 */
	public void setChequeType(String chequeType);

	/**
	 * Get own bank a/c no to which proceeds of receivables credited.
	 * 
	 * @return String
	 */
	public String getOwnAccNo();

	/**
	 * Set own bank a/c no to which proceeds of receivables credited.
	 * 
	 * @param ownAccNo is of type String
	 */
	public void setOwnAccNo(String ownAccNo);

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables();

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables);

	/**
	 * Get location of own bank account.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation();

	/**
	 * Set location of own bank account.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setOwnAccNoLocation(String ownAccNoLocation);

	/**
	 * Get security custodian.
	 * 
	 * @return String
	 */
	public String getCollateralCustodian();

	/**
	 * Set security custodian.
	 * 
	 * @param collateralCustodian of type String
	 */
	public void setCollateralCustodian(String collateralCustodian);

	/**
	 * Get collateral custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType();

	/**
	 * Set collateral custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType);

	/**
	 * Get if exchange control approval obtained.
	 * 
	 * @return String
	 */
	public String getIsExchangeCtrlObtained();

	/**
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	public Date getExchangeCtrlDate();
	
	public void setExchangeCtrlDate(Date exchangeCtrlDate);
	
	/**
	 * Get margin.
	 * 
	 * @return double
	 */
	public double getMargin();

	/**
	 * Set margin.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin);

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getBeforeMarginValue();

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount afterMarginValue);

	/**
	 * Get value after margin.
	 * 
	 * @return Amount
	 */
	public Amount getAfterMarginValue();

	/**
	 * Set value after margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setAfterMarginValue(Amount afterMarginValue);

	/**
	 * Get valuation date.
	 * 
	 * @return Date
	 */
	public Date getValuationDate();

	/**
	 * Set valuation date.
	 * 
	 * @param valuationDate of type Date
	 */
	public void setValuationDate(Date valuationDate);

	/**
	 * Get valuation currency code.
	 * 
	 * @return String
	 */
	public String getValuationCcyCode();

	/**
	 * Set valuation currency code.
	 * 
	 * @param valuationCcyCode of type String
	 */
	public void setValuationCcyCode(String valuationCcyCode);

	/**
	 * Get the status of the cheque, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of the cheque, deleted or active.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
	
	/*public String getChequeNoFrom();
	public void setChequeNoFrom(String chequeNoFrom);

	public String getChequeNoTo();
	public void setChequeNoTo(String chequeNoTo);
	public long getTotalNumberOfcheque();

	public void setTotalNumberOfcheque(long totalNumberOfcheque);
*/
	public String getChequeNumber();

	public void setChequeNumber(String chequeNumber);
	public String getReturnStatus();
	public void setReturnStatus(String returnStatus);
	public Date getReturnDate();

	public void setReturnDate(Date returnDate);
	public String getBankName();
	public void setBankName(String bankName);
	public String getBankCode() ;
	public void setBankCode(String bankCode);
	public String getPacketNumber();
	public void setPacketNumber(String packetNumber);
	public String getLoanable() ;
	public void setLoanable(String loanable);
	
	public String getRemarks() ;
	public void setRemarks(String remarks);
	
	public long getChequeNoFrom() ;

	public void setChequeNoFrom(long chequeNoFrom);

	public long getChequeNoTo() ;

	public void setChequeNoTo(long chequeNoTo);

	public String getBulkSingle();

	public void setBulkSingle(String bulkSingle) ;
	
	
	public Date getStartDate();

	public void setStartDate(Date startDate);
	public Date getMaturityDate();
	public void setMaturityDate(Date maturityDate);
	public String getRamId();

	public void setRamId(String ramId);
	
	public String getBranchName();

	public void setBranchName(String branchName);

	public String getBranchCode();

	public void setBranchCode(String branchCode);

	
}
