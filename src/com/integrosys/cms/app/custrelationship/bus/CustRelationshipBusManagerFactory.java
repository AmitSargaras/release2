/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;

/**
 * This factory creates SBCustRelationshipBusManager.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class CustRelationshipBusManagerFactory
{
    /**
     * Default Constructor
     */
    public CustRelationshipBusManagerFactory()
    {}
	
	 /**
	    * Get the SB for the actual storage of CustRelationship
	    *
	    * @return SBCustRelationshipManager
	    * @throws CustRelationshipException on errors
	    */
    public static SBCustRelationshipBusManager getActualCustRelationshipBusManager() throws CustRelationshipException {
        SBCustRelationshipBusManager home = (SBCustRelationshipBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_RELNSHIP_MGR_JNDI, SBCustRelationshipBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new CustRelationshipException("SBCustRelationshipBusManager for Actual is null!");
	    }
    }
	
    /**
	    * Get the SB for the staging storage of CustRelationship
	    *
	    * @return SBCustRelationshipManager
	    * @throws CustRelationshipException on errors
	    */
    public static SBCustRelationshipBusManager getStagingCustRelationshipBusManager() throws CustRelationshipException {
        SBCustRelationshipBusManager home = (SBCustRelationshipBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_RELNSHIP_MGR_STAGING_JNDI, SBCustRelationshipBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new CustRelationshipException("SBCustRelationshipBusManager for Staging is null!");
	    }
    }

}