package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 17, 2008
 */
public interface IPropertyIdxTrxValue  extends ICMSTrxValue {

    public IPropertyIdx getPrIdx();

    public IPropertyIdx getStagingPrIdx();

    public void setPrIdx(IPropertyIdx value);

    public void setStagingPrIdx(IPropertyIdx value);
}
