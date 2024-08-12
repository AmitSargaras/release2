package com.integrosys.cms.app.valuationAgency.bus;

import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.state.bus.IState;

public class OBCity implements ICity{
	
private long id;
private long idCity;
	private ICountry countryId;
	private String cityCode;
	private String cityName;     
    private String status;
    private String deprecated;
    private IState stateId;
	
    
    
	public IState getStateId() {
		return stateId;
	}
	public void setStateId(IState stateId) {
		this.stateId = stateId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdCity() {
		return idCity;
	}
	public void setIdCity(long idCity) {
		this.idCity = idCity;
	}
	public ICountry getCountryId() {
		return countryId;
	}
	public void setCountryId(ICountry countryId) {
		this.countryId = countryId;
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
	public long getVersionTime() {
		return 0;
	}
	public void setVersionTime(long arg0) {		
	}

}
