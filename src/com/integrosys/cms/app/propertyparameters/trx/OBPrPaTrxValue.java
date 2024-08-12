package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:01:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBPrPaTrxValue extends OBCMSTrxValue implements IPrPaTrxValue {

	public OBPrPaTrxValue() {
	}

	IPropertyParameters prPa;

	IPropertyParameters stagingPrPa;

	public OBPrPaTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IPropertyParameters getPrPa() {
		return prPa; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public IPropertyParameters getStagingPrPa() {
		return stagingPrPa; // To change body of implemented methods use File |
							// Settings | File Templates.
	}

	public void setPrPa(IPropertyParameters value) {
		this.prPa = value;
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void setStagingPrPa(IPropertyParameters value) {
		this.stagingPrPa = value;
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

}
