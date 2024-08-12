package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBImageTagTrxValue extends OBCMSTrxValue implements IImageTagTrxValue{

    public  OBImageTagTrxValue(){}

    IImageTagDetails imageTagDetails ;
    IImageTagDetails stagingImageTagDetails  ;

    public OBImageTagTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IImageTagDetails getImageTagDetails() {
		return imageTagDetails;
	}

	public void setImageTagDetails(IImageTagDetails imageTagDetails) {
		this.imageTagDetails = imageTagDetails;
	}

	public IImageTagDetails getStagingImageTagDetails() {
		return stagingImageTagDetails;
	}

	public void setStagingImageTagDetails(IImageTagDetails stagingImageTagDetails) {
		this.stagingImageTagDetails = stagingImageTagDetails;
	}

	
   

}
