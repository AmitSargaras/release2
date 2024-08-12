/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean acts as the facade to the Entity Beans for Customer Relationship staging data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBCustRelationshipBusManagerStagingBean extends SBCustRelationshipBusManagerBean
{
    /**
     * Default Constructor
     */
    public SBCustRelationshipBusManagerStagingBean()
    {}
    
    /**
	     * Get staging home interface of EBCustRelationship.
	     *
	     * @return EBCustRelationshipHome
	     * @throws CustRelationshipException on errors encountered
	     */
	protected EBCustRelationshipHome getEBCustRelationshipHome()
        throws CustRelationshipException
    {
        EBCustRelationshipHome ejbHome = (EBCustRelationshipHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_CUST_RELNSHIP_STAGING_JNDI, EBCustRelationshipHome.class.getName());

        if (ejbHome == null)
            throw new CustRelationshipException("EBCustRelationshipHome for staging is null!");

        return ejbHome;
    }

	 /**
	     * Get home interface of EBCustShareholder.
	     *
	     * @return EBCustShareholderHome
	     * @throws CustRelationshipException on errors encountered
	     */
    protected EBCustShareholderHome getEBCustShareholderHome()
        throws CustRelationshipException
    {
        EBCustShareholderHome ejbHome = (EBCustShareholderHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_SHAREHOLDER_STAGING_JNDI, EBCustShareholderHome.class.getName());

        if (ejbHome == null)
            throw new CustRelationshipException("EBCustShareholderHome for staging is null!");

        return ejbHome;
    }
}