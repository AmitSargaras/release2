package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ProductLimitParameterTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController productLimitReadController;

	private ITrxController productLimitTrxContoller;

    public ITrxController getProductLimitReadController() {
        return productLimitReadController;
    }

    public void setProductLimitReadController(ITrxController productLimitReadController) {
        this.productLimitReadController = productLimitReadController;
    }

    public ITrxController getProductLimitTrxContoller() {
        return productLimitTrxContoller;
    }

    public void setProductLimitTrxContoller(ITrxController productLimitTrxContoller) {
        this.productLimitTrxContoller = productLimitTrxContoller;
    }

    public ProductLimitParameterTrxControllerFactory() {
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
        if (ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER.equals(iTrxParameter.getAction()) ||
        		ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_ID.equals(iTrxParameter.getAction()) ||
                ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_TRXID.equals(iTrxParameter.getAction()))
            return getProductLimitReadController();
        else
            return getProductLimitTrxContoller();
    }
}
