package com.integrosys.cms.app.image.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author govind.sahu
 * Trx controller factory
 */
public class ImageUploadTrxControllerFactory implements ITrxControllerFactory {


    private ITrxController imageUploadTrxController;
    
    private ITrxController imageUploadReadController;

    /**
	 * @return the imageUploadReadController
	 */
	public ITrxController getImageUploadReadController() {
		return imageUploadReadController;
	}

	/**
	 * @param imageUploadReadController the imageUploadReadController to set
	 */
	public void setImageUploadReadController(
			ITrxController imageUploadReadController) {
		this.imageUploadReadController = imageUploadReadController;
	}

	public ImageUploadTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getImageUploadReadController();
        }
        return getImageUploadTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if (anAction.equals(ICMSConstant.ACTION_READ_IMAGE_UPLOAD)||anAction.equals(ICMSConstant.ACTION_READ_IMAGE_UPLOAD_ID)) {
            return true;
        }
        
        return false;
    }

	/**
	 * @return the imageUploadTrxController
	 */
	public ITrxController getImageUploadTrxController() {
		return imageUploadTrxController;
	}

	/**
	 * @param imageUploadTrxController the imageUploadTrxController to set
	 */
	public void setImageUploadTrxController(ITrxController imageUploadTrxController) {
		this.imageUploadTrxController = imageUploadTrxController;
	}
}
