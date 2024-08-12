package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.asset.AirCraftDetail;

/**
 * A search criteria represent the aircraft detail
 * 
 * @author Chong Jun Yong
 * 
 */
public class AirCraftDetailSearchItem extends AirCraftDetail {

	private static final long serialVersionUID = -8813012534303221093L;

	private StandardCode aircraftType;

	private StandardCode model;

	private Long manufactureYear;

	public StandardCode getAircraftType() {
		return aircraftType;
	}

	public StandardCode getModel() {
		return model;
	}

	public Long getManufactureYear() {
		return manufactureYear;
	}

	public void setAircraftType(StandardCode aircraftType) {
		this.aircraftType = aircraftType;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public void setManufactureYear(Long manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

}
