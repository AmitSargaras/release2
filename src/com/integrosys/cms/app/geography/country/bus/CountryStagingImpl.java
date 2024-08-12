package com.integrosys.cms.app.geography.country.bus;


public class CountryStagingImpl extends AbstractCountryBusManager{

	 public String getCountryName() {
	        return ICountryDAO.STAGING_COUNTRY_ENTITY_NAME;
	    }		
}
