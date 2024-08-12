/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/ExemptFacilityBusManagerFactory.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;


/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 04:08:19 $
 * Tag: $Name:  $
 */
public class ExemptFacilityBusManagerFactory {

    public static SBExemptFacilityBusManager getActualFeedBusManager() throws ExemptFacilityException {
        SBExemptFacilityBusManager home = (SBExemptFacilityBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI, SBExemptFacilityBusManagerHome.class.getName());

	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new ExemptFacilityException("SBExemptFacilityBusManager for Actual is null!");
	    }
    }

    public static SBExemptFacilityBusManager getStagingFeedBusManager() throws ExemptFacilityException {
        SBExemptFacilityBusManager home = (SBExemptFacilityBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI_STAGING, SBExemptFacilityBusManagerHome.class.getName());

	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new ExemptFacilityException("SBExemptFacilityBusManager for Staging is null!");
	    }
    }
}
