package com.integrosys.cms.app.creditriskparam.proxy.productlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.OBProductLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.*;

import java.util.List;

public class ProductLimitParameterProxyImpl implements IProductLimitParameterProxy{
    IProductLimitBusManager stagingProductLimitBusManager;
    IProductLimitBusManager productLimitBusManager;
    ITrxControllerFactory trxControllerFactory;

    public ITrxControllerFactory getTrxControllerFactory() {
        return trxControllerFactory;
    }

    public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
        this.trxControllerFactory = trxControllerFactory;
    }

    public IProductLimitBusManager getStagingProductLimitBusManager() {
        return stagingProductLimitBusManager;
    }

    public void setStagingProductLimitBusManager(IProductLimitBusManager stagingProductLimitBusManager) {
        this.stagingProductLimitBusManager = stagingProductLimitBusManager;
    }

    public IProductLimitBusManager getProductLimitBusManager() {
        return productLimitBusManager;
    }

    public void setProductLimitBusManager(IProductLimitBusManager productLimitBusManager) {
        this.productLimitBusManager = productLimitBusManager;
    }

    public List listProductLimit() throws ProductLimitException {
        return getProductLimitBusManager().getAll();
    }
    
    public List listProductType() throws ProductLimitException {
        return getProductLimitBusManager().getAllChild();
    }
    
    public IProductProgramLimitParameter getProductLimitById (long id) throws ProductLimitException {
        return (IProductProgramLimitParameter) getProductLimitBusManager().getLimitById(id);
    }

    public IProductLimitParameterTrxValue getTrxValue(ITrxContext ctx) throws ProductLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER);
        IProductLimitParameterTrxValue trxValue = new OBProductLimitParameterTrxValue();

        return (IProductLimitParameterTrxValue) operate(formulateTrxValue(ctx, trxValue), param);
    }

    public IProductLimitParameterTrxValue getTrxValueById (long id) throws ProductLimitException {
    	IProductLimitParameterTrxValue trxValue = new OBProductLimitParameterTrxValue();
    	trxValue.setReferenceID(String.valueOf(id));
    	trxValue.setTransactionType(ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER);
    	OBCMSTrxParameter param = new OBCMSTrxParameter();
    	param.setAction(ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_ID);

    	return (IProductLimitParameterTrxValue) operate(formulateTrxValue(new OBTrxContext(), trxValue), param);
    }
    
    public IProductLimitParameterTrxValue getTrxValueByTrxId(ITrxContext ctx, String trxId) throws ProductLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_TRXID);
        IProductLimitParameterTrxValue trxValue = new OBProductLimitParameterTrxValue();
        trxValue.setTransactionID(trxId);

        return (IProductLimitParameterTrxValue) operate(formulateTrxValue(ctx, trxValue), param);
    }
	
	public IProductLimitParameterTrxValue makerUpdateList(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException {
        if (ctx == null)
            throw new ProductLimitException("ITrxContext is NULL");

        if (trxValue == null)
            throw new ProductLimitException("TrxValue is NULL");

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ( trxValue.getStatus().equals(ICMSConstant.STATE_ND) ||
			 trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE))  {
			param.setAction (ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_LIMIT_PARAMETER);
		}
		else {
			param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_PRODUCT_LIMIT_PARAMETER);
		}

        trxValue = formulateTrxValue(ctx, trxValue);
		return operate(trxValue, param);
	}

    public IProductLimitParameterTrxValue checkerApprove(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PRODUCT_LIMIT_PARAMETER);
        trxValue = formulateTrxValue(ctx, trxValue);
        return operate(trxValue, param);
    }

    public IProductLimitParameterTrxValue checkerReject(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PRODUCT_LIMIT_PARAMETER);
        trxValue = formulateTrxValue(ctx, trxValue);
        return operate(trxValue, param);
    }

    public IProductLimitParameterTrxValue makerClose(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();

        if (ICMSConstant.STATE_REJECTED_CREATE.equals(trxValue.getStatus()))
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CREATE_PRODUCT_LIMIT_PARAMETER);
        else if (ICMSConstant.STATE_REJECTED_UPDATE.equals(trxValue.getStatus()))
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_PRODUCT_LIMIT_PARAMETER);
        else
        	param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_PRODUCT_LIMIT_PARAMETER);

        trxValue = formulateTrxValue(ctx, trxValue);
        return operate(trxValue, param);
    }
    
    public IProductLimitParameterTrxValue makerDelete(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) throws ProductLimitException {
    	OBCMSTrxParameter param = new OBCMSTrxParameter();
    	param.setAction(ICMSConstant.ACTION_MAKER_DELETE_PRODUCT_LIMIT_PARAMETER);
    	trxValue = formulateTrxValue(ctx, trxValue);
    	return operate(trxValue, param);
    }

    private IProductLimitParameterTrxValue operate(IProductLimitParameterTrxValue value, OBCMSTrxParameter param) throws ProductLimitException {
        ICMSTrxResult result = operateForResult(value, param);
        return (IProductLimitParameterTrxValue) result.getTrxValue();
    }
    
    protected ICMSTrxResult operateForResult(ICMSTrxValue value, OBCMSTrxParameter param) throws ProductLimitException {
        try {
            ITrxController controller = getTrxControllerFactory().getController(value, param);
            if (controller == null)
                throw new ProductLimitException("ITrxController is NULL");

            ITrxResult result = controller.operate(value, param);
            return (ICMSTrxResult) result;
        } catch (TrxParameterException e) {
            throw new ProductLimitException ("TrxParameterException caught! " + e.toString());
        } catch (TransactionException e) {
            throw new ProductLimitException ("TransactionException caught! " + e.toString());
        }
    }
     protected IProductLimitParameterTrxValue formulateTrxValue(ITrxContext ctx, IProductLimitParameterTrxValue trxValue) {
        trxValue.setTrxContext(ctx);
        trxValue.setTransactionType(ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER);
        return trxValue;
    }
}
