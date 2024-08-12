package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.asset.VesselDetail;

/**
 * A search criteria represent the vessel detail
 * 
 * @author Chong Jun Yong
 * 
 */
public class VesselDetailSearchItem extends VesselDetail {

	private static final long serialVersionUID = -4862724909059011118L;

	private StandardCode vesselType;

	public StandardCode getVesselType() {
		return vesselType;
	}

	public void setVesselType(StandardCode vesselType) {
		this.vesselType = vesselType;
	}

}
