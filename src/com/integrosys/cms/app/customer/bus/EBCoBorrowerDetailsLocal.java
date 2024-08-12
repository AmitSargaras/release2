package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBCoBorrowerDetailsLocal extends EJBLocalObject {

	public long getCoBorrowerId();
	
	public ICoBorrowerDetails getValue();

	public void setValue(ICoBorrowerDetails value);
}
