/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.proxy;

/**
 * This factory creates ICustRelationshipProxy object.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class CustRelationshipProxyFactory
{
    /**
     * Default Constructor
     */
    public CustRelationshipProxyFactory()
    {}

    /**
     * Creates an ICustRelationshipProxy.
     *
     * @return ICustRelationshipProxy
     */
    public static ICustRelationshipProxy getProxy() {
        return new CustRelationshipProxyImpl();
    }
}