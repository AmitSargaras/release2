package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBLimitXRefUdfLocal2 extends EJBLocalObject{
	public long getId();
	public ILimitXRefUdf2 getValue();
	public void setValue(ILimitXRefUdf2 value);
}
