package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ReadProductLimitParameterOperation extends AbstractProductLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

        try {
			ICMSTrxValue trxValue = super.getCMSTrxValue (value);
			
			List actualList = null;
			
			String actualRef = null;
			String stagingRef = null;

            actualList = getProductLimitParameterBusManager().getAll();

            if (actualList != null && actualList.size() > 0) {
                actualRef = String.valueOf(((IProductProgramLimitParameter) actualList.get(0)).getId());
                DefaultLogger.debug (this, "ProductLimit actualRef = " + actualRef);

                try {
                    trxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRef, ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER);
                    stagingRef = trxValue.getStagingReferenceID();
                } catch (Exception e){
                    // do nothing for very first entry
                }
                
            } else {
                trxValue = getTrxManager().getWorkingTrxByTrxType(ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER);

                if (trxValue == null)
                    return null;

                if (trxValue != null) {
                    stagingRef = trxValue.getStagingReferenceID();
                    DefaultLogger.debug (this, "ProductLimit stagingRef = " + stagingRef);
                }
            }

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
