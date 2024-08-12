/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ExemptFacilityGroupTrxControllerFactory.java,v 1.9 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 *
 * @author $Author: btchng $
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 10:15:18 $
 * Tag: $Name:  $
 */
public class ExemptFacilityGroupTrxControllerFactory implements ITrxControllerFactory {

    /**
     * Default Constructor
     */
    public ExemptFacilityGroupTrxControllerFactory() {
        super();
    }

    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_EXEMPT_INST) ||
            anAction.equals (ICMSConstant.ACTION_READ_EXEMPT_INST_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Returns an ITrxController given the ITrxValue and ITrxParameter objects
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return ITrxController
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if any error occurs
     */
    public ITrxController getController(ITrxValue value, ITrxParameter param)
            throws TrxParameterException {

        if (isReadOperation (param.getAction())) {
            return new ExemptFacilityGroupReadController();
        }
        return new ExemptFacilityGroupTrxController();
    }
}
