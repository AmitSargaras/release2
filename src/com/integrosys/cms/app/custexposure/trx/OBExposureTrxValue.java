package com.integrosys.cms.app.custexposure.trx;

import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBExposureTrxValue extends OBCMSTrxValue implements IExposureTrxValue {


    private IGroupExposure groupExposure;
    private ICustExposure custExposure;
    private ICustGrpIdentifierTrxValue custGrpIdentifierTrxValue;



    /**
     * Default constructor.
     */
    public OBExposureTrxValue() {
    }


    public IGroupExposure getGroupExposure() {
        return groupExposure;
    }

    public void setGroupExposure(IGroupExposure groupExposure) {
        this.groupExposure = groupExposure;
    }


    public ICustExposure getCustExposure() {
        return custExposure;
    }

    public void setCustExposure(ICustExposure custExposure) {
        this.custExposure = custExposure;
    }

    public ICustGrpIdentifierTrxValue getCustGrpIdentifierTrxValue() {
        return custGrpIdentifierTrxValue;
    }

    public void setCustGrpIdentifierTrxValue(ICustGrpIdentifierTrxValue custGrpIdentifierTrxValue) {
        this.custGrpIdentifierTrxValue = custGrpIdentifierTrxValue;
    }


}
