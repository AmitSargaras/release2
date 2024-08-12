package com.integrosys.cms.ui.leiDateValidation;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;

public class LeiDateValidationForm extends TrxContextForm implements Serializable{
	private String id;
	private String versionTime;

	private String status;
	private String creationDate;
	private String createBy;
	private String lastUpdateDate;
	private String lastUpdateBy;
	private String partyID;
	private String partyName;
	private String leiDateValidationPeriod;

	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getVersionTime() {
		return versionTime;
	}


	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	public String getLastUpdateBy() {
		return lastUpdateBy;
	}


	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}


	public String getPartyID() {
		return partyID;
	}


	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}


	public String getPartyName() {
		return partyName;
	}


	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}


	public String getLeiDateValidationPeriod() {
		return leiDateValidationPeriod;
	}


	public void setLeiDateValidationPeriod(String leiDateValidationPeriod) {
		this.leiDateValidationPeriod = leiDateValidationPeriod;
	}


	public static final String LEI_DATE_VALIDATION_MAPPER = "com.integrosys.cms.ui.leiDateValidation.LeiDateValidationMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "leiDateValidationObj", LEI_DATE_VALIDATION_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}

}
