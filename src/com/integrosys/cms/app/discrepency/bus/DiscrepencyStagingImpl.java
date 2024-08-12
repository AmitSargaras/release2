package com.integrosys.cms.app.discrepency.bus;


/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class DiscrepencyStagingImpl extends AbstractDiscrepencyBusManager{

	 public String getDiscrepencyName() {
	        return IDiscrepencyDAO.STAGING_DISCREPENCY_ENTITY_NAME;
	    }
}
