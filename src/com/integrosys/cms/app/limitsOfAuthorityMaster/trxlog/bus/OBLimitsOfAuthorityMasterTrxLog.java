package com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBLimitsOfAuthorityMasterTrxLog implements ILimitsOfAuthorityMasterTrxLog{

	private long id;
	private String partyId;
	private String partyName;
	
	private BigDecimal trxLimitReleaseAmt;
	private BigDecimal trxTotalSanctionedLimit;
	private BigDecimal trxPropertyValuation;
	private BigDecimal trxFdAmount;
	private BigDecimal trxDrawingPower;
	private BigDecimal trxSblcSecurityOmv;
	private String trxFacilityCamCovenant;
	
	private String isExceptionalUser;
	private String userId;
	
	private long trxHistoryId = ICMSConstant.LONG_INVALID_VALUE;
	private long referenceId;
	private long stagingReferenceId;
	private String transactionType ;
	private long stagingLoaMasterReferenceId = ICMSConstant.LONG_INVALID_VALUE;
	
	private Date makerDateTime;
	private Date checkerDateTime;
	private String makerId;
	private String checkerId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public BigDecimal getTrxLimitReleaseAmt() {
		return trxLimitReleaseAmt;
	}
	public void setTrxLimitReleaseAmt(BigDecimal trxLimitReleaseAmt) {
		this.trxLimitReleaseAmt = trxLimitReleaseAmt;
	}
	public BigDecimal getTrxTotalSanctionedLimit() {
		return trxTotalSanctionedLimit;
	}
	public void setTrxTotalSanctionedLimit(BigDecimal trxTotalSanctionedLimit) {
		this.trxTotalSanctionedLimit = trxTotalSanctionedLimit;
	}
	public BigDecimal getTrxPropertyValuation() {
		return trxPropertyValuation;
	}
	public void setTrxPropertyValuation(BigDecimal trxPropertyValuation) {
		this.trxPropertyValuation = trxPropertyValuation;
	}
	public BigDecimal getTrxFdAmount() {
		return trxFdAmount;
	}
	public void setTrxFdAmount(BigDecimal trxFdAmount) {
		this.trxFdAmount = trxFdAmount;
	}
	public BigDecimal getTrxDrawingPower() {
		return trxDrawingPower;
	}
	public void setTrxDrawingPower(BigDecimal trxDrawingPower) {
		this.trxDrawingPower = trxDrawingPower;
	}
	public BigDecimal getTrxSblcSecurityOmv() {
		return trxSblcSecurityOmv;
	}
	public void setTrxSblcSecurityOmv(BigDecimal trxSblcSecurityOmv) {
		this.trxSblcSecurityOmv = trxSblcSecurityOmv;
	}
	public String getTrxFacilityCamCovenant() {
		return trxFacilityCamCovenant;
	}
	public void setTrxFacilityCamCovenant(String trxFacilityCamCovenant) {
		this.trxFacilityCamCovenant = trxFacilityCamCovenant;
	}
	public String getIsExceptionalUser() {
		return isExceptionalUser;
	}
	public void setIsExceptionalUser(String isExceptionalUser) {
		this.isExceptionalUser = isExceptionalUser;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(long referenceId) {
		this.referenceId = referenceId;
	}
	public long getStagingReferenceId() {
		return stagingReferenceId;
	}
	public void setStagingReferenceId(long stagingReferenceId) {
		this.stagingReferenceId = stagingReferenceId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public long getStagingLoaMasterReferenceId() {
		return stagingLoaMasterReferenceId;
	}
	public void setStagingLoaMasterReferenceId(long stagingLoaMasterReferenceId) {
		this.stagingLoaMasterReferenceId = stagingLoaMasterReferenceId;
	}
	public Date getMakerDateTime() {
		return makerDateTime;
	}
	public void setMakerDateTime(Date makerDateTime) {
		this.makerDateTime = makerDateTime;
	}
	public Date getCheckerDateTime() {
		return checkerDateTime;
	}
	public void setCheckerDateTime(Date checkerDateTime) {
		this.checkerDateTime = checkerDateTime;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	public long getTrxHistoryId() {
		return trxHistoryId;
	}
	public void setTrxHistoryId(long trxHistoryId) {
		this.trxHistoryId = trxHistoryId;
	}
	
}
