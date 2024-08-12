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
public class StockRestRequestDTO {
	
	private String stockExchange;
	private String isinCode;
	private String isserIdentType;
	private String indexName; //AssetAircraft
	private String noOfUnit;
	private String nominalValue;
	private String certNo;
	private String issuerName;
	
	private String stockSecUniqueId;
	private String actionFlag;
	private String ClimsItemId;
	
	private List<StockLineRestRequestDTO> LineDetailsList;

	
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	public String getIsinCode() {
		return isinCode;
	}
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}
	public String getIsserIdentType() {
		return isserIdentType;
	}
	public void setIsserIdentType(String isserIdentType) {
		this.isserIdentType = isserIdentType;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getNoOfUnit() {
		return noOfUnit;
	}
	public void setNoOfUnit(String noOfUnit) {
		this.noOfUnit = noOfUnit;
	}
	public String getNominalValue() {
		return nominalValue;
	}
	public void setNominalValue(String nominalValue) {
		this.nominalValue = nominalValue;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	
	
	public List<StockLineRestRequestDTO> getLineDetailsList() {
		return LineDetailsList;
	}
	public void setLineDetailsList(List<StockLineRestRequestDTO> lineDetailsList) {
		LineDetailsList = lineDetailsList;
	}
	public String getStockSecUniqueId() {
		return stockSecUniqueId;
	}
	public void setStockSecUniqueId(String stockSecUniqueId) {
		this.stockSecUniqueId = stockSecUniqueId;
	}
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public String getClimsItemId() {
		return ClimsItemId;
	}
	public void setClimsItemId(String climsItemId) {
		ClimsItemId = climsItemId;
	}
	
	
	
	
	
	

	
	
	
	
}