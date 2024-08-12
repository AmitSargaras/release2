package com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail;

import java.math.BigDecimal;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBLineDetail implements ILineDetail {

	private long lineDetailID = ICMSConstant.LONG_MIN_VALUE;
	
	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;
	
	private String facilityName;
	
	private String facilityID;
	
	private String lineNo;
	
	private String serialNo;
	
	private Long  lcnNo;
	
	private BigDecimal lineLevelSecurityOMV;
	
	private long refId = ICMSConstant.LONG_MIN_VALUE;

	public long getLineDetailID() {
		return lineDetailID;
	}

	public void setLineDetailID(long lineDetailID) {
		this.lineDetailID = lineDetailID;
	}

	public long getCollateralID() {
		return collateralID;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getLineLevelSecurityOMV() {
		return lineLevelSecurityOMV;
	}

	public void setLineLevelSecurityOMV(BigDecimal lineLevelSecurityOMV) {
		this.lineLevelSecurityOMV = lineLevelSecurityOMV;
	}

	public Long getLcnNo() {
		return lcnNo;
	}

	public void setLcnNo(Long lcnNo) {
		this.lcnNo = lcnNo;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

}
