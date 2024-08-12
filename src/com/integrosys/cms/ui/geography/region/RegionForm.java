package com.integrosys.cms.ui.geography.region;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RegionForm extends TrxContextForm implements Serializable {

	static final long serialVersionUID = 0L;

	private String id;
	private String countryId;
	private String regionCode;
	private String regionName;
	private String countryOBId;

	private String status;
	private String deprecated;

	private String searchText;
	
	private List countryList;
	
	private FormFile fileUpload;
	
	/*Dilshad Changes For testing*/
	private String cpsId;
	private String operationName;
	
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
	/*End Dilshad testing*/
	
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	
	public String getCountryOBId() {
		return countryOBId;
	}

	public void setCountryOBId(String countryOBId) {
		this.countryOBId = countryOBId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List getCountryList() {
		return countryList;
	}

	public void setCountryList(List countryList) {
		this.countryList = countryList;
	}

	public String[][] getMapper() {

		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "regionObj", REGION_MAPPER } };
		return input;
	}

	public static final String REGION_MAPPER = "com.integrosys.cms.ui.geography.region.RegionMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
}
