package com.integrosys.cms.app.custexposure.trx;

import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IExposureTrxValue    extends ICMSTrxValue {

    public IGroupExposure getGroupExposure();
    public void setGroupExposure(IGroupExposure groupExposure);

    public ICustExposure getCustExposure() ;
    public void setCustExposure(ICustExposure custExposure);

    public ICustGrpIdentifierTrxValue getCustGrpIdentifierTrxValue();
    public void setCustGrpIdentifierTrxValue(ICustGrpIdentifierTrxValue custGrpIdentifierTrxValue) ;


}
