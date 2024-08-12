package com.integrosys.cms.ui.valuationAgency;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */

public class ValuationAgencyForm extends TrxContextForm implements Serializable {

	private String valuationAgencyCode;
	private String valuationAgencyName;
	
	private String valuationAgencyCodeSearch;
	private String valuationAgencyNameSearch;
	
	
	private String address;
	private String cityTown;
	private String state;
	private String country;
	private String region;
	private String versionTime;
	private String lastUpdateDate;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String status;
	private String deprecated;
	private String masterId;
	private String id;
	private String remarks;
	private String go="";
	
	private FormFile fileUpload;

	public String[][] getMapper() {

		String[][] input = { { "valuationObj", VALUATION_AGENCY_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER } };
		
		return input;

	}

	public static final String VALUATION_AGENCY_MAPPER = "com.integrosys.cms.ui.valuationAgency.ValuationAgencyMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getValuationAgencyCode() {
		return valuationAgencyCode;
	}

	public void setValuationAgencyCode(String valuationAgencyCode) {
		this.valuationAgencyCode = valuationAgencyCode;
	}

	public String getValuationAgencyName() {
		return valuationAgencyName;
	}

	public void setValuationAgencyName(String valuationAgencyName) {
		this.valuationAgencyName = valuationAgencyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityTown() {
		return cityTown;
	}

	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	
	
	public String getValuationAgencyCodeSearch() {
		return valuationAgencyCodeSearch;
	}

	public void setValuationAgencyCodeSearch(String valuationAgencyCodeSearch) {
		this.valuationAgencyCodeSearch = valuationAgencyCodeSearch;
	}

	public String getValuationAgencyNameSearch() {
		return valuationAgencyNameSearch;
	}

	public void setValuationAgencyNameSearch(String valuationAgencyNameSearch) {
		this.valuationAgencyNameSearch = valuationAgencyNameSearch;
	}

	public String getGo() {
		return go;
	}

	public void setGo(String go) {
		this.go = go;
	}

}
