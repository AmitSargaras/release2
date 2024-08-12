package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

public interface EBVendorDetailsLocal extends EJBLocalObject {
	public long getVendorId();

	/**
	 * Return an object representation of the vendor information.
	 * 
	 * @return IVendor
	 */
	public IVendor getValue();

	/**
	 * Persist a vendor information
	 * 
	 * @param value is of type IVendor
	 */
	public void setValue(IVendor value);
}
