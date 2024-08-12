package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;



public interface EBLimitCovenantLocal  extends EJBLocalObject {
	public long getCovenantId();

	/**
	 * Return an object representation of the vendor information.
	 * 
	 * @return ILimitCovenant
	 */
	public ILimitCovenant getValue()  throws LimitException ;

	/**
	 * Persist a vendor information
	 * 
	 * @param value is of type IVendor
	 */
	public void setValue(ILimitCovenant value)  throws LimitException ;
}

