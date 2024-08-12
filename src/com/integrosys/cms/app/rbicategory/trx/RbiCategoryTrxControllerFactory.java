package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
  * @author Govind.Sahu
 * Trx controller factory
 */
public class RbiCategoryTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController rbiCategoryReadController;

    private ITrxController rbiCategoryTrxController;

   

   

	/**
	 * @return the rbiCategoryReadController
	 */
	public ITrxController getRbiCategoryReadController() {
		return rbiCategoryReadController;
	}

	/**
	 * @param rbiCategoryReadController the rbiCategoryReadController to set
	 */
	public void setRbiCategoryReadController(
			ITrxController rbiCategoryReadController) {
		this.rbiCategoryReadController = rbiCategoryReadController;
	}

	/**
	 * @return the rbiCategoryTrxController
	 */
	public ITrxController getRbiCategoryTrxController() {
		return rbiCategoryTrxController;
	}

	/**
	 * @param rbiCategoryTrxController the rbiCategoryTrxController to set
	 */
	public void setRbiCategoryTrxController(ITrxController rbiCategoryTrxController) {
		this.rbiCategoryTrxController = rbiCategoryTrxController;
	}

	public RbiCategoryTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getRbiCategoryReadController();
        }
        return getRbiCategoryTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_RBI_CATEGORY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_RBI_CATEGORY_ID))) {
            return true;
        }
        return false;
    }
}
