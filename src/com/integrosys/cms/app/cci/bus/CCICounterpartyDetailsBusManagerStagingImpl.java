package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 10:30:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class CCICounterpartyDetailsBusManagerStagingImpl extends CCICounterpartyDetailsBusManagerImpl
{
    /**
     * Default constructor.
     */
    public CCICounterpartyDetailsBusManagerStagingImpl() {
        super();
    }

    /**
     * helper method to get an ejb object to liquidation business manager session bean.
     *
     * @return liquidation manager ejb object
     * @throws CCICounterpartyDetailsException on errors encountered
     */
    protected SBCCICounterpartyDetailsBusManager getBusManager() throws CCICounterpartyDetailsException
    {
        SBCCICounterpartyDetailsBusManager theEjb = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB (
            ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_STAGING_JNDI, SBCCICounterpartyDetailsBusManagerHome
                .class.getName());

        if (theEjb == null)
            throw new CCICounterpartyDetailsException ("SBLiquidationBusManager for Staging is null!");

        return theEjb;
    }
}