package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ReadProductLimitParameterByIdOperation extends AbstractProductLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_ID;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

        try {
			ICMSTrxValue trxValue = super.getCMSTrxValue (value);

            trxValue = getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER);
            
            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            IProductLimitParameterTrxValue returnValue = new OBProductLimitParameterTrxValue(trxValue);

            if (actualRef != null) {
                IProductProgramLimitParameter limit = (IProductProgramLimitParameter) getProductLimitParameterBusManager().getLimitById(Long.parseLong(actualRef));
                returnValue.setActualProductProgramLimitParameter(limit);
            }

            if (stagingRef != null) {
                IProductProgramLimitParameter limit = (IProductProgramLimitParameter) getStagingProductLimitParameterBusManager().getLimitById(Long.parseLong(stagingRef));
                returnValue.setStagingProductProgramLimitParameter(limit);
            }
            return returnValue;

        } catch (ProductLimitException e) {
            throw new TransactionException(e);
        } catch (RemoteException e) {
            throw new TransactionException(e);
        }
    }
}
