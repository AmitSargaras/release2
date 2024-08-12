/*
* Copyright Integro Technologies Pte Ltd
* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ExemptFacilityGroupReadController.java,v 1.3 2003/08/06 08:10:08 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * This controller controls document item related read-operations.
 *
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/06 08:10:08 $
 * Tag: $Name:  $
 */
public class ExemptFacilityGroupReadController
        extends AbstractTrxController
        implements ITrxOperationFactory {

    /**
     * Default Constructor
     */
    public ExemptFacilityGroupReadController() {
    }


    /**
     * Return instance name
     * @return String - the instance name
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_EXEMPT_FACILITY_GROUP;
    }


    /**
     * This operate method invokes the operation for a read operation.
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxResult - the trx result
     */
    public ITrxResult operate(ITrxValue value, ITrxParameter param)
            throws TrxParameterException, TrxControllerException,
            TransactionException {
        if (null == value) {
            throw new TrxParameterException("ITrxValue is null!");
        }
        if (null == param) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        value = setInstanceName(value);
        DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
        ITrxOperation op = getOperation(value, param);
        DefaultLogger.debug(this, "From state " + value.getFromState());
        CMSReadTrxManager mgr = new CMSReadTrxManager();

        ITrxResult result = null;
        try {
            result = mgr.operateTransaction(op, value);
            return result;
        } catch (TransactionException te) {
            throw te;
        } catch (Exception re) {
            throw new TrxControllerException(
                    "Caught Unknown Exception: " + re.toString());
        }
    }


    /**
     * Get the ITrxOperation
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException on errors
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
            throws TrxParameterException {
        if (null == param) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        String action = param.getAction();
        if (action != null) {
            if (action.equals(ICMSConstant.ACTION_READ_EXEMPT_FACILITY)) {
                return new ReadExemptFacilityGroupOperation();
            }
            else if (action.equals (ICMSConstant.ACTION_READ_EXEMPT_FACILITY_GROUP_BY_TRXID)) {
                return new ReadExemptFacilityByTrxIDOperation();
            }
        }

        throw new TrxParameterException("Action is null!");
    }
}
