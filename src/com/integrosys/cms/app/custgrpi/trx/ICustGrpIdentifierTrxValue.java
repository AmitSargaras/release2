package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ICustGrpIdentifierTrxValue extends ICMSTrxValue {


    public ICustGrpIdentifier getCustGrpIdentifier();
    public void setCustGrpIdentifier(ICustGrpIdentifier custGrpIdentifier);

    public ICustGrpIdentifier getStagingCustGrpIdentifier();
    public void setStagingCustGrpIdentifier(ICustGrpIdentifier stagingCustGrpIdentifier);


    public boolean isHasLimitBooking();
    public void setHasLimitBooking(boolean hasLimitBooking);

    public boolean isHasDeleteErr();
    public void setHasDeleteErr(boolean hasDeleteErr);
}
