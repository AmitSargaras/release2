package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface IFacilityNewMasterTrxValue  extends ICMSTrxValue {

    public IFacilityNewMaster getFacilityNewMaster();

    public IFacilityNewMaster getStagingFacilityNewMaster();

    public void setFacilityNewMaster(IFacilityNewMaster value);

    public void setStagingFacilityNewMaster(IFacilityNewMaster value);
}
