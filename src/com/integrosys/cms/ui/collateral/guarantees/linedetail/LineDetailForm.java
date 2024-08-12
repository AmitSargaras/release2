package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class LineDetailForm extends CommonForm implements Serializable,ILineDetailConstants {

	private String lineDetailID = "";
	private String collateralId = "";
	private String facilityName = "";
	private String facilityID = "";
	private String lineNo = "";
	private String serialNo = "";
	private String lcnNo = "";
	private String lineLevelSecurityOMV = "";
	private String facDetailMandatory = ICMSConstant.NO;
	private String refId;

	public String getLineDetailID() {
		return lineDetailID;
	}

	public void setLineDetailID(String lineDetailID) {
		this.lineDetailID = lineDetailID;
	}

	public String getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
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

	public String getLcnNo() {
		return lcnNo;
	}

	public void setLcnNo(String lcnNo) {
		this.lcnNo = lcnNo;
	}

	public String getLineLevelSecurityOMV() {
		return lineLevelSecurityOMV;
	}

	public void setLineLevelSecurityOMV(String lineLevelSecurityOMV) {
		this.lineLevelSecurityOMV = lineLevelSecurityOMV;
	}
	
	public String getFacDetailMandatory() {
		return facDetailMandatory;
	}

	public void setFacDetailMandatory(String facDetailMandatory) {
		this.facDetailMandatory = facDetailMandatory;
	}
	
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String[][] getMapper() {
		String[][] input = { { LINE_DETAIL_FORM, LINE_DETAIL_MAPPER } };
		return input;
	}

}
