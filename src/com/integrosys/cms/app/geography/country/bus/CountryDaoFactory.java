package com.integrosys.cms.app.geography.country.bus;

import com.integrosys.cms.ui.geography.country.CountryDAOImpl;

public class CountryDaoFactory {

	public static ICountryDAO getCountryDao(){
		return new CountryDAOImpl();
	}
}
