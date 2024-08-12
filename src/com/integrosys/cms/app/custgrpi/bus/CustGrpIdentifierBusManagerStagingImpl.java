package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 10:30:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class CustGrpIdentifierBusManagerStagingImpl extends CustGrpIdentifierBusManagerImpl
{
    /**
     * Default constructor.
     */
    public CustGrpIdentifierBusManagerStagingImpl() {
        super();
    }

    /**
     * helper method to get an ejb object to liquidation business manager session bean.
     *
     * @return liquidation manager ejb object
     * @throws CustGrpIdentifierException on errors encountered
     */
    protected SBCustGrpIdentifierBusManager getBusManager() throws CustGrpIdentifierException
    {
        SBCustGrpIdentifierBusManager theEjb = (SBCustGrpIdentifierBusManager) BeanController.getEJB (
            ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_STAGING_JNDI, SBCustGrpIdentifierBusManagerHome
                .class.getName());

        if (theEjb == null)
            throw new CustGrpIdentifierException ("SBCustGrpIdentifierBusManager for Staging is null!");

        return theEjb;
    }
}