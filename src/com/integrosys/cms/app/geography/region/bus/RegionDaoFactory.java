package com.integrosys.cms.app.geography.region.bus;

import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.ui.geography.region.RegionDAOImpl;

public class RegionDaoFactory {
	

		public static IRegionDAO getRegionDao(){
			return new RegionDAOImpl();
		}
	


}
