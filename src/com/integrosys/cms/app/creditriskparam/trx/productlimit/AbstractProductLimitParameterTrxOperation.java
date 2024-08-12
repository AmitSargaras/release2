package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

import java.rmi.RemoteException;
import java.util.Iterator;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public abstract class AbstractProductLimitParameterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	
	private static final long serialVersionUID = 1L;

    IProductLimitBusManager productLimitParameterBusManager;
    IProductLimitBusManager stagingProductLimitParameterBusManager;

    public IProductLimitBusManager getProductLimitParameterBusManager() {
        return productLimitParameterBusManager;
    }

    public void setProductLimitParameterBusManager(IProductLimitBusManager productLimitParameterBusManager) {
        this.productLimitParameterBusManager = productLimitParameterBusManager;
    }

    public IProductLimitBusManager getStagingProductLimitParameterBusManager() {
        return stagingProductLimitParameterBusManager;
    }

    public void setStagingProductLimitParameterBusManager(IProductLimitBusManager stagingProductLimitParameterBusManager) {
        this.stagingProductLimitParameterBusManager = stagingProductLimitParameterBusManager;
    }

    public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
        return value;
    }
	
	protected IProductLimitParameterTrxValue createStaging(IProductLimitParameterTrxValue productLimitParameterTrxValue) throws TrxOperationException {
		try {
			IProductProgramLimitParameter stagingProductProgramLimitParameter = productLimitParameterTrxValue.getStagingProductProgramLimitParameter();

			stagingProductProgramLimitParameter = (IProductProgramLimitParameter) getStagingProductLimitParameterBusManager().createLimit(stagingProductProgramLimitParameter);
			productLimitParameterTrxValue.setStagingProductProgramLimitParameter(stagingProductProgramLimitParameter);
			
			return productLimitParameterTrxValue;
		
        } catch (ProductLimitException e) {
            throw new TrxOperationException ("ProductLimitException caught!", e);
        }
	}

    protected IProductLimitParameterTrxValue createActual(IProductLimitParameterTrxValue productLimitParameterTrxValue) throws TrxOperationException {
        try {
        	IProductProgramLimitParameter stagingProductProgramLimitParameter = productLimitParameterTrxValue.getStagingProductProgramLimitParameter();

            IProductProgramLimitParameter actualProductProgramLimitParameter = (IProductProgramLimitParameter) getProductLimitParameterBusManager().createLimit(stagingProductProgramLimitParameter);
            productLimitParameterTrxValue.setActualProductProgramLimitParameter(actualProductProgramLimitParameter);
            
            return productLimitParameterTrxValue;
        } catch (ProductLimitException e) {
            throw new TrxOperationException ("ProductLimitException caught!", e);
        }
    }

    protected IProductLimitParameterTrxValue updateActual(IProductLimitParameterTrxValue productLimitParameterTrxValue) throws TrxOperationException {
        try {
        	
        	IProductProgramLimitParameter actualProductProgramLimitParameter = getProductLimitParameterBusManager().findByPrimaryKey(productLimitParameterTrxValue.getActualProductProgramLimitParameter());
        	IProductProgramLimitParameter stagingProductProgramLimitParameter = productLimitParameterTrxValue.getStagingProductProgramLimitParameter();
            
            actualProductProgramLimitParameter = (IProductProgramLimitParameter) getProductLimitParameterBusManager().updateToWorkingCopy(actualProductProgramLimitParameter, stagingProductProgramLimitParameter);

            productLimitParameterTrxValue.setActualProductProgramLimitParameter(actualProductProgramLimitParameter);

            return productLimitParameterTrxValue;

        } catch (ProductLimitException e) {
            throw new TrxOperationException ("ProductLimitException caught!", e);
        } catch (Exception ex) {
            throw new TrxOperationException("Exception in updateActual(): " + ex.toString());
        }
    }

    protected IProductLimitParameterTrxValue createTransaction(IProductLimitParameterTrxValue productLimitParameterTrxValue) throws TrxOperationException {
        productLimitParameterTrxValue = prepareTrxValue(productLimitParameterTrxValue);
        ICMSTrxValue tmpVal = super.createTransaction(productLimitParameterTrxValue);
        OBProductLimitParameterTrxValue newVal = new OBProductLimitParameterTrxValue(tmpVal);
        newVal.setActualProductProgramLimitParameter(productLimitParameterTrxValue.getActualProductProgramLimitParameter());
        newVal.setStagingProductProgramLimitParameter(productLimitParameterTrxValue.getStagingProductProgramLimitParameter());
        return newVal;
    }

    protected IProductLimitParameterTrxValue updateTransaction(IProductLimitParameterTrxValue productLimitParameterTrxValue) throws TrxOperationException {
        productLimitParameterTrxValue = prepareTrxValue(productLimitParameterTrxValue);
        ICMSTrxValue tmpVal = super.updateTransaction(productLimitParameterTrxValue);
        OBProductLimitParameterTrxValue newVal = new OBProductLimitParameterTrxValue(tmpVal);
        newVal.setActualProductProgramLimitParameter(productLimitParameterTrxValue.getActualProductProgramLimitParameter());
        newVal.setStagingProductProgramLimitParameter(productLimitParameterTrxValue.getStagingProductProgramLimitParameter());
        return newVal;
    }

    protected IProductLimitParameterTrxValue getTrxValue(ITrxValue iTrxValue) throws TrxOperationException {
        if (iTrxValue instanceof IProductLimitParameterTrxValue) {
            return (IProductLimitParameterTrxValue) iTrxValue;
        } else {
            return new OBProductLimitParameterTrxValue(iTrxValue);
        }
    }

    protected IProductLimitParameterTrxValue prepareTrxValue(IProductLimitParameterTrxValue productLimitParameterTrxValue) {

        if (productLimitParameterTrxValue != null) {
        	IProductProgramLimitParameter actualProductProgramLimitParameter  = productLimitParameterTrxValue.getActualProductProgramLimitParameter();
        	IProductProgramLimitParameter stagingProductProgramLimitParameter = productLimitParameterTrxValue.getStagingProductProgramLimitParameter();

            if (actualProductProgramLimitParameter != null && actualProductProgramLimitParameter.getId() != null) {
            	productLimitParameterTrxValue.setReferenceID(String.valueOf(actualProductProgramLimitParameter.getId()));
            } else
                productLimitParameterTrxValue.setReferenceID(null);
            

            if (stagingProductProgramLimitParameter != null && stagingProductProgramLimitParameter.getId() != null) {
            	productLimitParameterTrxValue.setStagingReferenceID(String.valueOf(stagingProductProgramLimitParameter.getId()));
            } else
                productLimitParameterTrxValue.setStagingReferenceID(null);
            
        }
        return productLimitParameterTrxValue;
    }

    protected ITrxResult prepareResult(IProductLimitParameterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
