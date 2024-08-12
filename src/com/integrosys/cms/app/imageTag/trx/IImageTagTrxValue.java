package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface IImageTagTrxValue  extends ICMSTrxValue {

    public IImageTagDetails getImageTagDetails();

    public IImageTagDetails getStagingImageTagDetails();

    public void setImageTagDetails(IImageTagDetails value);

    public void setStagingImageTagDetails(IImageTagDetails value);
}
