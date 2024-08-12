package com.integrosys.cms.app.customer.bus;

public interface IVendor extends java.io.Serializable {
	
	public long getVendorId();

	public void setVendorId(long vendorId);

	public String getVendorName();

	public void setVendorName(String vendorName) ;
	
    public long getLEID();
	
	public void setLEID(long value);


}
