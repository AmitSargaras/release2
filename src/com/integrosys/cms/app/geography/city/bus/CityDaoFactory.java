package com.integrosys.cms.app.geography.city.bus;

import com.integrosys.cms.ui.geography.city.CityDAOImpl;

public class CityDaoFactory {
	
	public static ICityDAO getCityDao(){
		return new CityDAOImpl();
	}


}
