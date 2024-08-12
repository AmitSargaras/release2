package com.integrosys.cms.app.custexposure.trx.group;

import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IGroupExposureTrxValue    extends ICMSTrxValue {

    public IGroupExposure getGroupExposure();
    public void setGroupExposure(IGroupExposure groupExposure);


}
