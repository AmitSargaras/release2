package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;

public class FacilityMainCommand extends AbstractCommand {
	private IFacilityProxy facilityProxy;

	public IFacilityProxy getFacilityProxy() {
		return facilityProxy;
	}

	public void setFacilityProxy(IFacilityProxy facilityProxy) {
		this.facilityProxy = facilityProxy;
	}

}
