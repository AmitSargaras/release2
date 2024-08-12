package com.integrosys.cms.app.image.trx;

import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface IImageUploadDetailsTrxValue  extends ICMSTrxValue {

    public IImageUploadDetails getImageUploadDetails();

    public IImageUploadDetails getStagingImageUploadDetails();

    public void setImageUploadDetails(IImageUploadDetails value);

    public void setStagingImageUploadDetails(IImageUploadDetails value);
}
