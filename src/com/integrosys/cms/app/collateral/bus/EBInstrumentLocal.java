package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

public interface EBInstrumentLocal extends EJBLocalObject {

	public IInstrument getValue();

	public void setValue(IInstrument aInstrument);
}