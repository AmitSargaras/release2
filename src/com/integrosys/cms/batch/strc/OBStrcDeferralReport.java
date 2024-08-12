package com.integrosys.cms.batch.strc;

import java.util.Date;

import com.integrosys.cms.ui.common.UIUtil;

public class OBStrcDeferralReport {
	
	
	
	private String partyId;
	private String partyName;
	private String sourceSecurityID;
	private String collateralCodeName;
	private String facilityId;
	private String facilityName;
	private String facSanctionAmt;
	private String facilityReleasedAmt;
	private String securityDPAmt;
	private String drawingPower;
	private String stockDueMonth;
	private String stockDueYear;
	private String dueDate;
	private String dpShare;
	private String remarksByMaker;
	private String termLoanOutamt;
	private String marginAssetCover;
	private String receivableGiven;
	private String makerID;
	private String checkerID;
	private String makerDate;
	private String checkerDate;
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityReleasedAmt() {
		return facilityReleasedAmt;
	}
	public void setFacilityReleasedAmt(String facilityReleasedAmt) {
		this.facilityReleasedAmt = facilityReleasedAmt;
	}
	public String getSourceSecurityID() {
		return sourceSecurityID;
	}
	public void setSourceSecurityID(String sourceSecurityID) {
		this.sourceSecurityID = sourceSecurityID;
	}
	public String getSecurityDPAmt() {
		return securityDPAmt;
	}
	public void setSecurityDPAmt(String securityDPAmt) {
		this.securityDPAmt = securityDPAmt;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getStockDueMonth() {
		return stockDueMonth;
	}
	public void setStockDueMonth(String stockDueMonth) {
		this.stockDueMonth = stockDueMonth;
	}
	public String getStockDueYear() {
		return stockDueYear;
	}
	public void setStockDueYear(String stockDueYear) {
		this.stockDueYear = stockDueYear;
	}
	public String getRemarksByMaker() {
		return remarksByMaker;
	}
	public void setRemarksByMaker(String remarksByMaker) {
		this.remarksByMaker = remarksByMaker;
	}
	public String getTermLoanOutamt() {
		return termLoanOutamt;
	}
	public void setTermLoanOutamt(String termLoanOutamt) {
		this.termLoanOutamt = termLoanOutamt;
	}
	public String getMarginAssetCover() {
		return marginAssetCover;
	}
	public void setMarginAssetCover(String marginAssetCover) {
		this.marginAssetCover = marginAssetCover;
	}
	public String getReceivableGiven() {
		return receivableGiven;
	}
	public void setReceivableGiven(String receivableGiven) {
		this.receivableGiven = receivableGiven;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getCheckerID() {
		return checkerID;
	}
	public void setCheckerID(String checkerID) {
		this.checkerID = checkerID;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getCheckerDate() {
		return checkerDate;
	}
	public void setCheckerDate(String checkerDate) {
		this.checkerDate = checkerDate;
	}
	
	public String getCollateralCodeName() {
		return collateralCodeName;
	}
	public void setCollateralCodeName(String collateralCodeName) {
		this.collateralCodeName = collateralCodeName;
	}
	public String getFacSanctionAmt() {
		return facSanctionAmt;
	}
	public void setFacSanctionAmt(String facSanctionAmt) {
		this.facSanctionAmt = facSanctionAmt;
	}
	public String getDrawingPower() {
		return drawingPower;
	}
	public void setDrawingPower(String drawingPower) {
		this.drawingPower = drawingPower;
	}
	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}
	
	@Override
	public String toString() {
		return ((getPartyId()==null ? "" : getPartyId()) + "|" 
				+ (getPartyName()==null ? "" : getPartyName().toString()) + "|" 
				+ (getSourceSecurityID()==null ? "" : getSourceSecurityID()) + "|" 
				+ (getCollateralCodeName()==null ? "" : getCollateralCodeName()) + "|" 
				+ (getFacilityId()==null ? "" : getFacilityId()) + "|" 
				+ (getFacilityName()==null ? "" : getFacilityName()) + "|" 
				+ (getFacSanctionAmt()==null ? "" : getFacSanctionAmt())+ "|" 
				+ (getFacilityReleasedAmt()==null ? "" : getFacilityReleasedAmt())+ "|" 
				+ (getSecurityDPAmt()==null ? "" : getSecurityDPAmt())+ "|" 
				+ (getDrawingPower()==null ? "" : getDrawingPower())+ "|" 
				+ (getStockDueMonth()==null ? "" : getStockDueMonth())+ "|" 
				+ (getStockDueYear()==null ? "" : getStockDueYear())+ "|" 
				+ (getDueDate()==null ? "" : getDueDate())+ "|" 
				+ (getDpShare()==null ? "" : getDpShare())+ "|" 
				+ (getRemarksByMaker()==null ? "" : getRemarksByMaker())+ "|" 
				+ (getFacSanctionAmt()==null ? "" : getFacSanctionAmt())+ "|"
				+ (getTermLoanOutamt()==null ? "" : getTermLoanOutamt())+ "|"
				+ (getMarginAssetCover()==null ? "" : getMarginAssetCover())+ "|"
				+ (getReceivableGiven()==null ? "" : getReceivableGiven())+ "|"
				+ (getMakerID()==null ? "" : getMakerID())+ "|"
				+ (getCheckerID()==null ? "" : getCheckerID())+ "|"
				+ (getMakerDate()==null ? "" : getMakerDate())+ "|"
				+ (getCheckerDate()==null ? "" : getCheckerDate()));
	}
	
	/*@Override
	public String toString() {
		return "OBStrcDeferralReport [transactionID=" + transactionID + ", transactionHistoryID=" + transactionHistoryID
				+ ", partyId=" + partyId + ", partyName=" + partyName + ", facilityId=" + facilityId + ", facilityName="
				+ facilityName + ", facilityReleasedAmt=" + facilityReleasedAmt + ", sourceSecurityID="
				+ sourceSecurityID + ", securityDPAmt=" + securityDPAmt + ", dueDate=" + dueDate + ", stockDueMonth="
				+ stockDueMonth + ", stockDueYear=" + stockDueYear + ", remarksByMaker=" + remarksByMaker
				+ ", termLoanOutamt=" + termLoanOutamt + ", marginAssetCover=" + marginAssetCover + ", receivableGiven="
				+ receivableGiven + ", makerID=" + makerID + ", checkerID=" + checkerID + ", makerDate=" + makerDate
				+ ", checkerDate=" + checkerDate + "]";
	}*/
	
	
	
	
		}
