package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.asset.VehicleDetail;

/**
 * A search criteria represent the vehicle detail
 * 
 * @author Chong Jun Yong
 * 
 */
public class VehicleDetailSearchItem extends VehicleDetail {

	private static final long serialVersionUID = -2757747205685192403L;

	private StandardCode vehicleType;

	public StandardCode getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(StandardCode vehicleType) {
		this.vehicleType = vehicleType;
	}
}
