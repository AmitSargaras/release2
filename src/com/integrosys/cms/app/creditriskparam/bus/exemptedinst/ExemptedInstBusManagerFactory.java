/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;

/**
 * This factory creates SBExemptedInstBusManager.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ExemptedInstBusManagerFactory
{
    /**
     * Default Constructor
     */
    public ExemptedInstBusManagerFactory()
    {}
	
	 /**
	    * Get the SB for the actual storage of ExemptedInst
	    *
	    * @return SBExemptedInstManager
	    * @throws ExemptedInstException on errors
	    */
    public static SBExemptedInstBusManager getActualExemptedInstBusManager() throws ExemptedInstException {
        SBExemptedInstBusManager home = (SBExemptedInstBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_INST_MGR_JNDI, SBExemptedInstBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new ExemptedInstException("SBExemptedInstBusManager for Actual is null!");
	    }
    }
	
    /**
	    * Get the SB for the staging storage of ExemptedInst
	    *
	    * @return SBExemptedInstManager
	    * @throws ExemptedInstException on errors
	    */
    public static SBExemptedInstBusManager getStagingExemptedInstBusManager() throws ExemptedInstException {
        SBExemptedInstBusManager home = (SBExemptedInstBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_INST_MGR_STAGING_JNDI, SBExemptedInstBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new ExemptedInstException("SBExemptedInstBusManager for Staging is null!");
	    }
    }

}