/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.exemptedinst;

/**
 * This factory creates IExemptedInstProxy object.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ExemptedInstProxyFactory
{
    /**
     * Default Constructor
     */
    public ExemptedInstProxyFactory()
    {}

    /**
     * Creates an IExemptedInstProxy.
     *
     * @return IExemptedInstProxy
     */
    public static IExemptedInstProxy getProxy() {
        return new ExemptedInstProxyImpl();
    }
}