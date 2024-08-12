/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.aurionpro.clims.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralLimitMapComparator;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.IInstrument;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
/**
 * This class represents a Collateral entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2006/09/15 08:30:10 $ Tag: $Name: $
 */
public class StockLineRestRequestDTO {
	
	private String lineDetailId;
	private String facilityName ;
	private String facilityId ;
	private String lineNumber;
	private String serialNumber ;
	private String fasNumber ;
	private String ltv ;
	private String remarks;
	private String facDetailMandatory = ICMSConstant.NO;
	private String marketableEquityId ;
	private String refID = "";
	
	private String LineUniqueId;
	private String actionFlag;
	private String ClimsLineId;
	
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFacDetailMandatory() {
		return facDetailMandatory;
	}
	public void setFacDetailMandatory(String facDetailMandatory) {
		this.facDetailMandatory = facDetailMandatory;
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
	public String getLineUniqueId() {
		return LineUniqueId;
	}
	public void setLineUniqueId(String lineUniqueId) {
		LineUniqueId = lineUniqueId;
	}
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public String getClimsLineId() {
		return ClimsLineId;
	}
	public void setClimsLineId(String climsLineId) {
		ClimsLineId = climsLineId;
	}

	
	
	

	
	
	
	
}