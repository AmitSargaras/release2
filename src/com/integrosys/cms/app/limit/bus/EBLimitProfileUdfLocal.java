package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.cms.app.customer.bus.CustomerException;

public interface EBLimitProfileUdfLocal extends EJBLocalObject {
	public long getId();
	public ILimitProfileUdf getValue();
	public void setValue(ILimitProfileUdf value);
}
