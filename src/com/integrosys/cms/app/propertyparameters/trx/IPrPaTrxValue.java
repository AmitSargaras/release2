package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:01:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPrPaTrxValue extends ICMSTrxValue {

	public IPropertyParameters getPrPa();

	public IPropertyParameters getStagingPrPa();

	public void setPrPa(IPropertyParameters value);

	public void setStagingPrPa(IPropertyParameters value);
}
