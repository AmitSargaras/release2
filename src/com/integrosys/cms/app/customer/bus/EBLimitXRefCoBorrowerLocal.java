package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBLimitXRefCoBorrowerLocal extends EJBLocalObject{
	public long getId();
	public ILimitXRefCoBorrower getValue();
	public void setValue(ILimitXRefCoBorrower value);
}
