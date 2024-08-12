package com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus;

import java.math.BigDecimal;
import java.util.Date;

public interface ILimitsOfAuthorityMasterTrxLog {

	public long getId() ;
	public void setId(long id) ;
	public String getPartyId();
	public void setPartyId(String partyId) ;
	public String getPartyName() ;
	public void setPartyName(String partyName) ;
	public BigDecimal getTrxLimitReleaseAmt();
	public void setTrxLimitReleaseAmt(BigDecimal trxLimitReleaseAmt);
	public BigDecimal getTrxTotalSanctionedLimit();
	public void setTrxTotalSanctionedLimit(BigDecimal trxTotalSanctionedLimit);
	public BigDecimal getTrxPropertyValuation();
	public void setTrxPropertyValuation(BigDecimal trxPropertyValuation);
	public BigDecimal getTrxFdAmount();
	public void setTrxFdAmount(BigDecimal trxFdAmount) ;
	public BigDecimal getTrxDrawingPower();
	public void setTrxDrawingPower(BigDecimal trxDrawingPower);
	public BigDecimal getTrxSblcSecurityOmv();
	public void setTrxSblcSecurityOmv(BigDecimal trxSblcSecurityOmv);
	public String getTrxFacilityCamCovenant();
	public void setTrxFacilityCamCovenant(String trxFacilityCamCovenant);
	public String getIsExceptionalUser();
	public void setIsExceptionalUser(String isExceptionalUser);
	public String getUserId();
	public void setUserId(String userId);
	public long getReferenceId();
	public void setReferenceId(long referenceId) ;
	public long getStagingReferenceId();
	public void setStagingReferenceId(long stagingReferenceId);
	public String getTransactionType();
	public void setTransactionType(String transactionType);
	public long getStagingLoaMasterReferenceId();
	public void setStagingLoaMasterReferenceId(long stagingLoaMasterReferenceId);
	public Date getMakerDateTime();
	public void setMakerDateTime(Date makerDateTime);
	public Date getCheckerDateTime();
	public void setCheckerDateTime(Date checkerDateTime);
	public String getMakerId();
	public void setMakerId(String makerId);
	public String getCheckerId();
	public void setCheckerId(String checkerId);
	public long getTrxHistoryId();
	public void setTrxHistoryId(long trxHistoryId);
}
