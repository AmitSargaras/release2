package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBFacilityNewMasterTrxValue extends OBCMSTrxValue implements IFacilityNewMasterTrxValue{

    public  OBFacilityNewMasterTrxValue(){}

    IFacilityNewMaster facilityNewMaster ;
    IFacilityNewMaster stagingFacilityNewMaster ;

    public OBFacilityNewMasterTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IFacilityNewMaster getFacilityNewMaster() {
		return facilityNewMaster;
	}

	public void setFacilityNewMaster(IFacilityNewMaster facilityNewMaster) {
		this.facilityNewMaster = facilityNewMaster;
	}

	public IFacilityNewMaster getStagingFacilityNewMaster() {
		return stagingFacilityNewMaster;
	}

	public void setStagingFacilityNewMaster(IFacilityNewMaster stagingFacilityNewMaster) {
		this.stagingFacilityNewMaster = stagingFacilityNewMaster;
	}
    
   

}
