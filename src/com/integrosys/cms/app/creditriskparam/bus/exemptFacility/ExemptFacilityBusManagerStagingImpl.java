/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.liquidation.bus.LiquidationBusManagerImpl;
import com.integrosys.cms.app.liquidation.bus.SBLiquidationBusManager;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.bus.SBLiquidationBusManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;

/**
NOT USED 
 */
public class ExemptFacilityBusManagerStagingImpl extends ExemptFacilityBusManagerImpl
{
    /**
     * Default constructor.
     */
    public ExemptFacilityBusManagerStagingImpl() {
//        super();
    }

    protected SBExemptFacilityBusManager getBusManager() throws ExemptFacilityException
    {
        SBExemptFacilityBusManager theEjb = (SBExemptFacilityBusManager) BeanController.getEJB (
            ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI_STAGING, SBExemptFacilityBusManagerHome.class.getName());

        if (theEjb == null)
            throw new ExemptFacilityException ("SBExemptFacilityBusManager for Staging is null!");

        return theEjb;
    }
}