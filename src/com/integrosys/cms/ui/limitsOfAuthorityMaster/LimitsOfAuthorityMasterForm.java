package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.common.TrxContextMapper;

public class LimitsOfAuthorityMasterForm  extends TrxContextForm implements Serializable, ILimitsOfAuthorityMasterConstant {

	private String id;

	private String employeeGrade;
	
	private String rankingOfSequence;
	private String segment;
	private String limitReleaseAmt;
	private String totalSanctionedLimit;
	private String propertyValuation;
	private String fdAmount;
	private String drawingPower;
	private String sblcSecurityOmv;
	private String facilityCamCovenant;
	
	private String employeeGradeList;
	private String rankingSequenceList;
	private String segmentList;
	
	private String loaDataMap;
	
	private String status;
	
	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createdBy;
	private String lastUpdateBy;
	private String deprecated;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getRankingOfSequence() {
		return rankingOfSequence;
	}

	public void setRankingOfSequence(String rankingOfSequence) {
		this.rankingOfSequence = rankingOfSequence;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getLimitReleaseAmt() {
		return limitReleaseAmt;
	}

	public void setLimitReleaseAmt(String limitReleaseAmt) {
		this.limitReleaseAmt = limitReleaseAmt;
	}

	public String getTotalSanctionedLimit() {
		return totalSanctionedLimit;
	}

	public void setTotalSanctionedLimit(String totalSanctionedLimit) {
		this.totalSanctionedLimit = totalSanctionedLimit;
	}

	public String getPropertyValuation() {
		return propertyValuation;
	}

	public void setPropertyValuation(String propertyValuation) {
		this.propertyValuation = propertyValuation;
	}

	public String getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(String fdAmount) {
		this.fdAmount = fdAmount;
	}

	public String getDrawingPower() {
		return drawingPower;
	}

	public void setDrawingPower(String drawingPower) {
		this.drawingPower = drawingPower;
	}

	public String getSblcSecurityOmv() {
		return sblcSecurityOmv;
	}

	public void setSblcSecurityOmv(String sblcSecurityOmv) {
		this.sblcSecurityOmv = sblcSecurityOmv;
	}

	public String getFacilityCamCovenant() {
		return facilityCamCovenant;
	}

	public void setFacilityCamCovenant(String facilityCamCovenant) {
		this.facilityCamCovenant = facilityCamCovenant;
	}

	public String getEmployeeGradeList() {
		return employeeGradeList;
	}

	public void setEmployeeGradeList(String employeeGradeList) {
		this.employeeGradeList = employeeGradeList;
	}

	public String getRankingSequenceList() {
		return rankingSequenceList;
	}

	public void setRankingSequenceList(String rankingSequenceList) {
		this.rankingSequenceList = rankingSequenceList;
	}

	public String getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(String segmentList) {
		this.segmentList = segmentList;
	}

	public String getLoaDataMap() {
		return loaDataMap;
	}

	public void setLoaDataMap(String loaDataMap) {
		this.loaDataMap = loaDataMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDisableForSelection() {
		return disableForSelection;
	}

	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String[][] getMapper() {
		String[][] input = 
		{
			{ LIMITS_OF_AUTHORITY_OBJ, LOA_MASTER_MAPPER},
			{ TRX_CONTEXT, TRX_MAPPER }
		};
		return input;
	}
	
	public static final String LOA_MASTER_MAPPER = LimitsOfAuthorityMasterMapper.class.getName();

	public static final String TRX_MAPPER = TrxContextMapper.class.getName();
	
}