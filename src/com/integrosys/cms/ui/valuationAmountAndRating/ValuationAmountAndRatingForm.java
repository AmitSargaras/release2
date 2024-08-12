package com.integrosys.cms.ui.valuationAmountAndRating;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class ValuationAmountAndRatingForm extends TrxContextForm implements Serializable{

	private String criteria;
	private String valuationAmount;
	private String ramRating;
	private String excludePartyId;
	
	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	
	private String status;
	private String deprecated;
	private String id;
	private String operationName;
	

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


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getLastUpdateBy() {
		return lastUpdateBy;
	}


	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDeprecated() {
		return deprecated;
	}


	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOperationName() {
		return operationName;
	}


	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public static final String VALUATION_AMOUNT_AND_RATING_MAPPER = "com.integrosys.cms.ui.valuationAmountAndRating.ValuationAmountAndRatingMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "valuationAmountAndRatingObj", VALUATION_AMOUNT_AND_RATING_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}


	public String getCriteria() {
		return criteria;
	}


	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}


	public String getValuationAmount() {
		return valuationAmount;
	}


	public void setValuationAmount(String valuationAmount) {
		this.valuationAmount = valuationAmount;
	}


	public String getRamRating() {
		return ramRating;
	}


	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}


	public String getExcludePartyId() {
		return excludePartyId;
	}


	public void setExcludePartyId(String excludePartyId) {
		this.excludePartyId = excludePartyId;
	}


	
}
