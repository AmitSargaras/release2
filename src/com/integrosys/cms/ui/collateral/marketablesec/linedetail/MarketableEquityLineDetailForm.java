package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.io.Serializable;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.marketablesec.PortItemForm;

public class MarketableEquityLineDetailForm extends CommonForm implements Serializable,IMarketableEquityLineDetailConstants{
	
	private String lineDetailId = "";
	private String facilityName = "";
	private String facilityId = "";
	private String lineNumber = "";
	private String serialNumber = "";
	private String fasNumber = "";
	private String ltv = "";
	private String remarks = "";
	private String facDetailMandatory = ICMSConstant.NO;
	private String marketableEquityId = "";
	private String refID = "";

	private String totalLtv = "";
	private String lineValue = "";

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


	public String getLtv() {
		return ltv;
	}


	public void setLtv(String ltv) {
		this.ltv = ltv;
	}


	public String[][] getMapper() {
		DefaultLogger.debug(this, "Getting Mapper");
		String[][] input =
            {
            	//{"form.PortItemObject", PortItemForm.class.getName()},
            	{MARKETABLE_EQUITY_LINE_DETAIL_FORM,MarketableEquityLineDetailMapper.class.getName()}
            };
        return input;
	}

	
	public String getLineDetailId() {
		return lineDetailId;
	}


	public void setLineDetailId(String lineDetailId) {
		this.lineDetailId = lineDetailId;
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


	public String getFacDetailMandatory() {
		return facDetailMandatory;
	}


	public void setFacDetailMandatory(String facDetailMandatory) {
		this.facDetailMandatory = facDetailMandatory;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getTotalLtv() {
		return totalLtv;
	}


	public void setTotalLtv(String totalLtv) {
		this.totalLtv = totalLtv;
	}


	public String getMarketableEquityId() {
		return marketableEquityId;
	}


	public void setMarketableEquityId(String marketableEquityId) {
		this.marketableEquityId = marketableEquityId;
	}


	public String getRefID() {
		return refID;
	}


	public void setRefID(String refID) {
		this.refID = refID;
	}


	public String getLineValue() {
		return lineValue;
	}


	public void setLineValue(String lineValue) {
		this.lineValue = lineValue;
	}

}
