package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

public interface EBFacilityCoBorrowerDetailsLocal extends EJBLocalObject {

	public long getCoBorrowerId();
	
	public IFacilityCoBorrowerDetails getValue();

	public void setValue(IFacilityCoBorrowerDetails value);
}
