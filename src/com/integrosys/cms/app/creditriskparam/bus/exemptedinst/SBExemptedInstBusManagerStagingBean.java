/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean acts as the facade to the Entity Beans for Exempted Institution staging data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBExemptedInstBusManagerStagingBean extends SBExemptedInstBusManagerBean
{
    /**
     * Default Constructor
     */
    public SBExemptedInstBusManagerStagingBean()
    {}
    
    /**
	     * Get staging home interface of EBExemptedInst.
	     *
	     * @return EBExemptedInstHome
	     * @throws ExemptedInstException on errors encountered
	     */
	protected EBExemptedInstHome getEBExemptedInstHome()
        throws ExemptedInstException
    {
        EBExemptedInstHome ejbHome = (EBExemptedInstHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_EXEMPT_INST_STAGING_JNDI, EBExemptedInstHome.class.getName());

        if (ejbHome == null)
            throw new ExemptedInstException("EBExemptedInstHome for staging is null!");

        return ejbHome;
    }

	 
}