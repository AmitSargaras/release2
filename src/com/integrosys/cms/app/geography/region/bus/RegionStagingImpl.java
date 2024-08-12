package com.integrosys.cms.app.geography.region.bus;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class RegionStagingImpl extends AbstractRegionBusManager{

	 public String getRegionName() {
	        return IRegionDAO.STAGING_REGION_ENTITY_NAME;
	    }
		
}
