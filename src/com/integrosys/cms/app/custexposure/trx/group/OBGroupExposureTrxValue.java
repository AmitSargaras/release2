package com.integrosys.cms.app.custexposure.trx.group;

import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBGroupExposureTrxValue extends OBCMSTrxValue implements IGroupExposureTrxValue {


	private static final long serialVersionUID = 1L;
	private IGroupExposure groupExposure;

    public IGroupExposure getGroupExposure() {
        return groupExposure;
    }

    public void setGroupExposure(IGroupExposure groupExposure) {
        this.groupExposure = groupExposure;
    }
}
