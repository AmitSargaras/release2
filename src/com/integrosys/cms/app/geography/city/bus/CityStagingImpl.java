package com.integrosys.cms.app.geography.city.bus;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class CityStagingImpl extends AbstractCityBusManager{

	 public String getCityName() {
	        return ICityDAO.STAGING_CITY_ENTITY_NAME;
	    }
}
