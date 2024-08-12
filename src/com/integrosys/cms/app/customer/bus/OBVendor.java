package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

public class OBVendor implements IVendor{
	private long _vendorId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private String vendorName;
	//private String customerId;
	private  long LEID;
	public OBVendor(IVendor value) {
		this();
		AccessorUtil.copyValue(value, this);
	}
	
    public OBVendor() {}
    
    public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}

	public long getVendorId() {
		return _vendorId;
	}

	public void setVendorId(long _vendorId) {
		this._vendorId = _vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
	
}
