package com.integrosys.cms.app.image.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBImageUploadDetailsTrxValue extends OBCMSTrxValue implements IImageUploadDetailsTrxValue{

    public  OBImageUploadDetailsTrxValue(){}

    IImageUploadDetails imageUploadDetails ;
    IImageUploadDetails stagingImageUploadDetails  ;

    public OBImageUploadDetailsTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IImageUploadDetails getImageUploadDetails() {
		return imageUploadDetails;
	}

	public void setImageUploadDetails(IImageUploadDetails imageUploadDetails) {
		this.imageUploadDetails = imageUploadDetails;
	}

	public IImageUploadDetails getStagingImageUploadDetails() {
		return stagingImageUploadDetails;
	}

	public void setStagingImageUploadDetails(IImageUploadDetails stagingImageUploadDetails) {
		this.stagingImageUploadDetails = stagingImageUploadDetails;
	}

	
   

}
