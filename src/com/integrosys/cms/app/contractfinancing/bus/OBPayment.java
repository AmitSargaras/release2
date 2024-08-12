package com.integrosys.cms.app.contractfinancing.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBPayment implements IPayment {

	private long paymentID = ICMSConstant.LONG_INVALID_VALUE;

	// Payment received
	private Date receiveDate;

	private Amount receiveAmount;

	private String receiveFrom;

	// Payment distributed
	private Date distributeDate;

	private Amount distributeAmount;

	private boolean isToFDR;

	private Amount fdrAmount;

	private boolean isToRepo;

	private Amount repoAmount;

	private boolean isToAPG;

	private Amount apgAmount;

	private boolean isToTL1;

	private Amount tl1Amount;

	private boolean isToTL2;

	private Amount tl2Amount;

	private boolean isToTL3;

	private Amount tl3Amount;

	private boolean isToBC;

	private Amount bcAmount;

	private boolean isToCollectionAccount;

	private Amount collectionAccountAmount;

	private boolean isToOthers;

	private Amount othersAmount;

	private String remarks;

	// Common attributes
	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeleted;

	/**
	 * Default Constructor
	 */
	public OBPayment() {
	}

	public long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Amount getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Amount receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getReceiveFrom() {
		return receiveFrom;
	}

	public void setReceiveFrom(String receiveFrom) {
		this.receiveFrom = receiveFrom;
	}

	public Date getDistributeDate() {
		return distributeDate;
	}

	public void setDistributeDate(Date distributeDate) {
		this.distributeDate = distributeDate;
	}

	public Amount getDistributeAmount() {
		return distributeAmount;
	}

	public void setDistributeAmount(Amount distributeAmount) {
		this.distributeAmount = distributeAmount;
	}

	public boolean getIsToFDR() {
		return isToFDR;
	}

	public void setIsToFDR(boolean toFDR) {
		this.isToFDR = toFDR;
	}

	public Amount getFDRAmount() {
		return fdrAmount;
	}

	public void setFDRAmount(Amount fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public boolean getIsToRepo() {
		return isToRepo;
	}

	public void setIsToRepo(boolean toRepo) {
		this.isToRepo = toRepo;
	}

	public Amount getRepoAmount() {
		return repoAmount;
	}

	public void setRepoAmount(Amount repoAmount) {
		this.repoAmount = repoAmount;
	}

	public boolean getIsToAPG() {
		return isToAPG;
	}

	public void setIsToAPG(boolean toAPG) {
		this.isToAPG = toAPG;
	}

	public Amount getAPGAmount() {
		return apgAmount;
	}

	public void setAPGAmount(Amount apgAmount) {
		this.apgAmount = apgAmount;
	}

	public boolean getIsToTL1() {
		return isToTL1;
	}

	public void setIsToTL1(boolean toTL1) {
		this.isToTL1 = toTL1;
	}

	public Amount getTL1Amount() {
		return tl1Amount;
	}

	public void setTL1Amount(Amount tl1Amount) {
		this.tl1Amount = tl1Amount;
	}

	public boolean getIsToTL2() {
		return isToTL2;
	}

	public void setIsToTL2(boolean toTL2) {
		this.isToTL2 = toTL2;
	}

	public Amount getTL2Amount() {
		return tl2Amount;
	}

	public void setTL2Amount(Amount tl2Amount) {
		this.tl2Amount = tl2Amount;
	}

	public boolean getIsToTL3() {
		return isToTL3;
	}

	public void setIsToTL3(boolean toTL3) {
		this.isToTL3 = toTL3;
	}

	public Amount getTL3Amount() {
		return tl3Amount;
	}

	public void setTL3Amount(Amount tl3Amount) {
		this.tl3Amount = tl3Amount;
	}

	public boolean getIsToBC() {
		return isToBC;
	}

	public void setIsToBC(boolean toBC) {
		this.isToBC = toBC;
	}

	public Amount getBCAmount() {
		return bcAmount;
	}

	public void setBCAmount(Amount bcAmount) {
		this.bcAmount = bcAmount;
	}

	public boolean getIsToCollectionAccount() {
		return isToCollectionAccount;
	}

	public void setIsToCollectionAccount(boolean toCollectionAccount) {
		isToCollectionAccount = toCollectionAccount;
	}

	public Amount getCollectionAccountAmount() {
		return collectionAccountAmount;
	}

	public void setCollectionAccountAmount(Amount collectionAccountAmount) {
		this.collectionAccountAmount = collectionAccountAmount;
	}

	public boolean getIsToOthers() {
		return isToOthers;
	}

	public void setIsToOthers(boolean toOthers) {
		this.isToOthers = toOthers;
	}

	public Amount getOthersAmount() {
		return othersAmount;
	}

	public void setOthersAmount(Amount othersAmount) {
		this.othersAmount = othersAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
