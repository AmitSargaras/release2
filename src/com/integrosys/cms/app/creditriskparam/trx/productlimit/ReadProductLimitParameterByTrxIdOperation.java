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

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ReadProductLimitParameterByTrxIdOperation extends AbstractProductLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_TRXID;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
        try {
            ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(value.getTransactionID());
            DefaultLogger.debug (this, "ProductLimit >>>>> Transaction: " + cmsTrxValue.toString());
            
            OBProductLimitParameterTrxValue trxValue = new OBProductLimitParameterTrxValue(cmsTrxValue);
            DefaultLogger.debug (this, "ProductLimit >>>>> Transaction: " + trxValue.toString());
            String stagingRef = cmsTrxValue.getStagingReferenceID();
            String actualRef = cmsTrxValue.getReferenceID();

            DefaultLogger.debug (this, "ProductLimit >>>>> Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (stagingRef != null) {
                IProductProgramLimitParameter limitList =  (IProductProgramLimitParameter) getStagingProductLimitParameterBusManager().getLimitById(Long.parseLong(stagingRef));
                trxValue.setStagingProductProgramLimitParameter(limitList);
            }
                 
            if (actualRef != null) {
                IProductProgramLimitParameter limitList = (IProductProgramLimitParameter) getProductLimitParameterBusManager().getLimitById(Long.parseLong(actualRef));
                trxValue.setActualProductProgramLimitParameter(limitList);
                }

            DefaultLogger.debug (this, "ProductLimit >>>>> read by trx value: " + trxValue );
            
            return trxValue;
            
        } catch (RemoteException e) {
            throw new TransactionException("RemoteException caught!", e);


        } catch (ProductLimitException e) {
            throw new TransactionException("ProductLimitException caught!", e);


        }
    }
}
