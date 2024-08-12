package com.integrosys.cms.app.cci.trx;

import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ICCICounterpartyDetailsTrxValue    extends ICMSTrxValue {

     public ICCICounterpartyDetails getCCICounterpartyDetails();
     public void setCCICounterpartyDetails(ICCICounterpartyDetails aICCICounterpartyDetails);


    public ICCICounterpartyDetails getStagingCCICounterpartyDetails();
     public void setStagingCCICounterpartyDetails(ICCICounterpartyDetails aICCICounterpartyDetails);

   // public ICCICounterpartyDetails getActualCCICounterpartyDetails();
  //  public void setActualCCICounterpartyDetails(ICCICounterpartyDetails aICCICounterpartyDetails);

}
