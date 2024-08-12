package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;


public class OBCCICounterpartyDetailsTrxValue extends OBCMSTrxValue implements ICCICounterpartyDetailsTrxValue {


  //  private ICCICounterpartyDetails actualCCICounterpartyDetails;
    private ICCICounterpartyDetails CCICounterpartyDetails;

    private ICCICounterpartyDetails stagingCCICounterpartyDetails;


    /**
     * Default constructor.
     */
    public OBCCICounterpartyDetailsTrxValue() {
    }


    /**
     * Construct the object based on its parent info
     * @param anICMSTrxValue - ICMSTrxValue
     */
    public OBCCICounterpartyDetailsTrxValue(ICMSTrxValue anICMSTrxValue) {
        AccessorUtil.copyValue(anICMSTrxValue, this);
    }

//    public ICCICounterpartyDetails getActualCCICounterpartyDetails() {
//        return actualCCICounterpartyDetails;
//    }
//
//    public void setActualCCICounterpartyDetails(ICCICounterpartyDetails actualCCICounterpartyDetails) {
//        this.actualCCICounterpartyDetails = actualCCICounterpartyDetails;
//    }

    public ICCICounterpartyDetails getStagingCCICounterpartyDetails() {
        return stagingCCICounterpartyDetails;
    }

    public void setStagingCCICounterpartyDetails(ICCICounterpartyDetails stagingCCICounterpartyDetails) {
        this.stagingCCICounterpartyDetails = stagingCCICounterpartyDetails;
    }

    public ICCICounterpartyDetails getCCICounterpartyDetails() {
        return CCICounterpartyDetails;
    }

    public void setCCICounterpartyDetails(ICCICounterpartyDetails cCICounterpartyDetails) {
        this.CCICounterpartyDetails = cCICounterpartyDetails;
    }

}
