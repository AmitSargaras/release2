/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/GeneralInfoForm.java,v 1.10 2006/09/26 03:16:29 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/26 03:16:29 $ Tag: $Name: $
 */

public class GeneralInfoForm extends TrxContextForm implements Serializable {
	private String contractNo = "";

	private String securityID = "";

	private String limitID = "";

	private String subLimitID = "";

	private String limitCcy = "";

	private String availableOpLimit = "";

	private String tpDealRef = "";

	private String dealMaturityDate = "";

	private String extendedDealMaturityDate = "";

	private String originalFaceCcy = "";

	private String originalFaceAmt = "";

	private String percentageFinancing = "";

	private String dealAmount = "";

	private String balanceOrigFaceVal = "";

	private String cashRequirement = "";

	private String marginCash = "";

	private String cashReqAmt = "";

	private String actualCashReceive = "";

	private String totalCashReceive = "";

	private String shippingMarks = "";

	private String latestShipmentDate = "";

	private String containerNo = "";

	private String buyerName = "";

	private String preSold = "";

	private String enforcibilityAtt = "";

	private String enforcibilityAttDate = "";

	private String commDealRemarks = "";

	private String[] deleteCashDeposit;

	private String totalCashDeposit = "";

	private String isNewDeal = "";

	private String overallErrors = "";

	private String forwardUser = "";

	private String dealDate = "";

	private String colCashReqPct = "";

	public String getColCashReqPct() {
		return colCashReqPct;
	}

	public void setColCashReqPct(String colCashReqPct) {
		this.colCashReqPct = colCashReqPct;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getLimitID() {
		return limitID;
	}

	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	public String getSubLimitID() {
		return subLimitID;
	}

	public void setSubLimitID(String subLimitID) {
		this.subLimitID = subLimitID;
	}

	public String getTpDealRef() {
		return tpDealRef;
	}

	public void setTpDealRef(String tpDealRef) {
		this.tpDealRef = tpDealRef;
	}

	public String getDealMaturityDate() {
		return dealMaturityDate;
	}

	public void setDealMaturityDate(String dealMaturityDate) {
		this.dealMaturityDate = dealMaturityDate;
	}

	public String getExtendedDealMaturityDate() {
		return extendedDealMaturityDate;
	}

	public void setExtendedDealMaturityDate(String extendedDealMaturityDate) {
		this.extendedDealMaturityDate = extendedDealMaturityDate;
	}

	public String getOriginalFaceCcy() {
		return originalFaceCcy;
	}

	public void setOriginalFaceCcy(String originalFaceCcy) {
		this.originalFaceCcy = originalFaceCcy;
	}

	public String getOriginalFaceAmt() {
		return originalFaceAmt;
	}

	public void setOriginalFaceAmt(String originalFaceAmt) {
		this.originalFaceAmt = originalFaceAmt;
	}

	public String getPercentageFinancing() {
		return percentageFinancing;
	}

	public void setPercentageFinancing(String percentageFinancing) {
		this.percentageFinancing = percentageFinancing;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getBalanceOrigFaceVal() {
		return balanceOrigFaceVal;
	}

	public void setBalanceOrigFaceVal(String balanceOrigFaceVal) {
		this.balanceOrigFaceVal = balanceOrigFaceVal;
	}

	public String getCashRequirement() {
		return cashRequirement;
	}

	public void setCashRequirement(String cashRequirement) {
		this.cashRequirement = cashRequirement;
	}

	public String getMarginCash() {
		return marginCash;
	}

	public void setMarginCash(String marginCash) {
		this.marginCash = marginCash;
	}

	public String getShippingMarks() {
		return shippingMarks;
	}

	public void setShippingMarks(String shippingMarks) {
		this.shippingMarks = shippingMarks;
	}

	public String getLatestShipmentDate() {
		return latestShipmentDate;
	}

	public void setLatestShipmentDate(String latestShipmentDate) {
		this.latestShipmentDate = latestShipmentDate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPreSold() {
		return preSold;
	}

	public void setPreSold(String preSold) {
		this.preSold = preSold;
	}

	public String getEnforcibilityAtt() {
		return enforcibilityAtt;
	}

	public void setEnforcibilityAtt(String enforcibilityAtt) {
		this.enforcibilityAtt = enforcibilityAtt;
	}

	public String getEnforcibilityAttDate() {
		return enforcibilityAttDate;
	}

	public void setEnforcibilityAttDate(String enforcibilityAttDate) {
		this.enforcibilityAttDate = enforcibilityAttDate;
	}

	public String getCommDealRemarks() {
		return commDealRemarks;
	}

	public void setCommDealRemarks(String commDealRemarks) {
		this.commDealRemarks = commDealRemarks;
	}

	public String[] getDeleteCashDeposit() {
		return deleteCashDeposit;
	}

	public void setDeleteCashDeposit(String[] deleteCashDeposit) {
		this.deleteCashDeposit = deleteCashDeposit;
	}

	public String getAvailableOpLimit() {
		return availableOpLimit;
	}

	public void setAvailableOpLimit(String availableOpLimit) {
		this.availableOpLimit = availableOpLimit;
	}

	public String getCashReqAmt() {
		return cashReqAmt;
	}

	public void setCashReqAmt(String cashReqAmt) {
		this.cashReqAmt = cashReqAmt;
	}

	public String getLimitCcy() {
		return limitCcy;
	}

	public void setLimitCcy(String limitCcy) {
		this.limitCcy = limitCcy;
	}

	public String getActualCashReceive() {
		return actualCashReceive;
	}

	public void setActualCashReceive(String actualCashReceive) {
		this.actualCashReceive = actualCashReceive;
	}

	public String getTotalCashDeposit() {
		return totalCashDeposit;
	}

	public void setTotalCashDeposit(String totalCashDeposit) {
		this.totalCashDeposit = totalCashDeposit;
	}

	public String getTotalCashReceive() {
		return totalCashReceive;
	}

	public void setTotalCashReceive(String totalCashReceive) {
		this.totalCashReceive = totalCashReceive;
	}

	public String getIsNewDeal() {
		return isNewDeal;
	}

	public void setIsNewDeal(String isNewDeal) {
		this.isNewDeal = isNewDeal;
	}

	public String getOverallErrors() {
		return overallErrors;
	}

	public void setOverallErrors(String overallErrors) {
		this.overallErrors = overallErrors;
	}

	public String getForwardUser() {
		return forwardUser;
	}

	public void setForwardUser(String forwardUser) {
		this.forwardUser = forwardUser;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "generalInfoObj", "com.integrosys.cms.ui.commoditydeal.generalinfo.GeneralInfoMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}
}
