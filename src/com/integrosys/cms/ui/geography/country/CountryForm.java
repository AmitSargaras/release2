package com.integrosys.cms.ui.geography.country;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CountryForm extends TrxContextForm implements Serializable {

	static final long serialVersionUID = 0L;

	private String id;
	private String countryCode;
	private String countryName;

	private String status;
	private String deprecated;
	
	private String searchText;

	private FormFile fileUpload;
	private String cpsId;
	private String operationName;
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
      /*dilshad changes fro test*/
	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	/*end*/
	public String[][] getMapper() {

		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "countryObj", COUNTRY_MAPPER } };
		return input;
	}

	public static final String COUNTRY_MAPPER = "com.integrosys.cms.ui.geography.country.CountryMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
}
