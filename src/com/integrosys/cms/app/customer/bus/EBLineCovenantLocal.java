package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBLineCovenantLocal extends EJBLocalObject{
	public long getCovenantId();
	public ILineCovenant getValue();
	public void setValue(ILineCovenant value);
}