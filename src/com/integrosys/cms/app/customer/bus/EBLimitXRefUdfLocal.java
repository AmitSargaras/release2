package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBLimitXRefUdfLocal extends EJBLocalObject{
	public long getId();
	public ILimitXRefUdf getValue();
	public void setValue(ILimitXRefUdf value);
}
