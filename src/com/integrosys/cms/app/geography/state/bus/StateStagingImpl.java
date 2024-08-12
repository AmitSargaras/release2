package com.integrosys.cms.app.geography.state.bus;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class StateStagingImpl extends AbstractStateBusManager{

	 public String getStateName() {
	        return IStateDAO.STAGING_STATE_ENTITY_NAME;
	    }
}
