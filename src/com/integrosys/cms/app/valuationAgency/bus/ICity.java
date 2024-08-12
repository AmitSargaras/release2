package com.integrosys.cms.app.valuationAgency.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.state.bus.IState;

public interface ICity extends IValueObject,Serializable{
	
	
	public long getId();
	public void setId(long id);
	public long getIdCity();
	public void setIdCity(long idCity);
	public ICountry getCountryId() ;
	public void setCountryId(ICountry countryId);
	public String getCityCode() ;
	public void setCityCode(String cityCode);
	public String getCityName() ;
	public void setCityName(String cityName) ;
	public String getStatus() ;
	public void setStatus(String status) ;
	public String getDeprecated();
	public void setDeprecated(String deprecated);
	
	
	public IState getStateId();
	public void setStateId(IState stateId);

}
