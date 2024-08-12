package com.integrosys.cms.app.collateral.bus.type.marketable.linedetail;

import java.math.BigDecimal;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBMarketableEquityLineDetail implements IMarketableEquityLineDetail {
	
	private long lineDetailId = ICMSConstant.LONG_INVALID_VALUE;
	private long refID = ICMSConstant.LONG_MIN_VALUE;
	//private long collateralId = ICMSConstant.LONG_MIN_VALUE;
	private long marketableEquityId = ICMSConstant.LONG_MIN_VALUE;
	
	private String facilityName;
	private String facilityId;
	private String lineNumber;
	private String serialNumber;
	private String fasNumber;
	private Double ltv;
	private String remarks;
	private String lineEquityUniqueID;
	
	private BigDecimal lineValue;
	public long getLineDetailId() {
		return lineDetailId;
	}
	public void setLineDetailId(long lineDetailId) {
		this.lineDetailId = lineDetailId;
	}
	public long getRefID() {
		return refID;
	}
	public void setRefID(long refID) {
		this.refID = refID;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getFasNumber() {
		return fasNumber;
	}
	public void setFasNumber(String fasNumber) {
		this.fasNumber = fasNumber;
	}
	public Double getLtv() {
		return ltv;
	}
	public void setLtv(Double ltv) {
		this.ltv = ltv;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	/*public long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}*/
	public long getMarketableEquityId() {
		return marketableEquityId;
	}
	public void setMarketableEquityId(long marketableEquityId) {
		this.marketableEquityId = marketableEquityId;
	}
	public BigDecimal getLineValue() {
		return lineValue;
	}
	public void setLineValue(BigDecimal lineValue) {
		this.lineValue = lineValue;
	}
	public String getLineEquityUniqueID() {
		return lineEquityUniqueID;
	}
	public void setLineEquityUniqueID(String lineEquityUniqueID) {
		this.lineEquityUniqueID = lineEquityUniqueID;
	}
	
	
	
}
