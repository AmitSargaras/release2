package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class ImageTagTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController imageTagReadController;

    private ITrxController imageTagTrxController;

   


	public ITrxController getImageTagReadController() {
		return imageTagReadController;
	}

	public void setImageTagReadController(ITrxController imageTagReadController) {
		this.imageTagReadController = imageTagReadController;
	}

	public ITrxController getImageTagTrxController() {
		return imageTagTrxController;
	}

	public void setImageTagTrxController(ITrxController imageTagTrxController) {
		this.imageTagTrxController = imageTagTrxController;
	}

	public ImageTagTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getImageTagReadController();
        }
        return getImageTagTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_IMAGE_TAG)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_IMAGE_TAG_ID))) {
            return true;
        }
        return false;
    }
}
