package com.integrosys.cms.ui.geography.city;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class CityForm extends TrxContextForm implements Serializable {

	static final long serialVersionUID = 0L;

	private String id;
	private String cityCode;
	private String cityName;

	private String countryId;
	private String regionId;
	private String stateId;

	private String countryOBId;
	private String regionOBId;
	private String stateOBId;

	private String status;
	private String deprecated;
	
	private String searchText;

	private List countryList;
	private List regionList;
	private List stateList;
	
	private FormFile fileUpload;
	
	private String ecbfCityId;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getRegionOBId() {
		return regionOBId;
	}

	public void setRegionOBId(String regionOBId) {
		this.regionOBId = regionOBId;
	}

	public String getStateOBId() {
		return stateOBId;
	}

	public void setStateOBId(String stateOBId) {
		this.stateOBId = stateOBId;
	}

	public String getCountryOBId() {
		return countryOBId;
	}

	public void setCountryOBId(String countryOBId) {
		this.countryOBId = countryOBId;
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

	public List getRegionList() {
		return regionList;
	}

	public void setRegionList(List regionList) {
		this.regionList = regionList;
	}

	public List getStateList() {
		return stateList;
	}

	public void setStateList(List stateList) {
		this.stateList = stateList;
	}
	
	public String getEcbfCityId() {
		return ecbfCityId;
	}

	public void setEcbfCityId(String ecbfCityId) {
		this.ecbfCityId = ecbfCityId;
	}

	public String[][] getMapper() {

		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "cityObj", CITY_MAPPER } };
		return input;
	}

	public static final String CITY_MAPPER = "com.integrosys.cms.ui.geography.city.CityMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
}
