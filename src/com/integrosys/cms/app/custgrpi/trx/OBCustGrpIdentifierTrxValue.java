package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;


public class OBCustGrpIdentifierTrxValue extends OBCMSTrxValue implements ICustGrpIdentifierTrxValue {


    private ICustGrpIdentifier custGrpIdentifier;

    private ICustGrpIdentifier stagingCustGrpIdentifier;

    private boolean hasLimitBooking = false;
    private boolean hasDeleteErr = false;

    /**
     * Default constructor.
     */
    public OBCustGrpIdentifierTrxValue() {
    }


    /**
     * Construct the object based on its parent info
     * @param anICMSTrxValue - ICMSTrxValue
     */
    public OBCustGrpIdentifierTrxValue(ICMSTrxValue anICMSTrxValue) {
        AccessorUtil.copyValue(anICMSTrxValue, this);
    }

    public ICustGrpIdentifier getCustGrpIdentifier() {
        return custGrpIdentifier;
    }

    public void setCustGrpIdentifier(ICustGrpIdentifier custGrpIdentifier) {
        this.custGrpIdentifier = custGrpIdentifier;
    }



    public ICustGrpIdentifier getStagingCustGrpIdentifier() {
        return stagingCustGrpIdentifier;
    }

    public void setStagingCustGrpIdentifier(ICustGrpIdentifier stagingCustGrpIdentifier) {
        this.stagingCustGrpIdentifier = stagingCustGrpIdentifier;
    }



    public boolean isHasLimitBooking() {
        return hasLimitBooking;
    }

    public void setHasLimitBooking(boolean hasLimitBooking) {
        this.hasLimitBooking = hasLimitBooking;
    }

    public boolean isHasDeleteErr() {
        return hasDeleteErr;
    }

    public void setHasDeleteErr(boolean hasDeleteErr) {
        this.hasDeleteErr = hasDeleteErr;
    }
    
}
