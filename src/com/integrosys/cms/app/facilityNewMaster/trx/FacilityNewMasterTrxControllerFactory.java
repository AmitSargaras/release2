package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class FacilityNewMasterTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController facilityNewMasterReadController;

    private ITrxController facilityNewMasterTrxController;

   

    public ITrxController getFacilityNewMasterReadController() {
		return facilityNewMasterReadController;
	}

	public void setFacilityNewMasterReadController(
			ITrxController facilityNewMasterReadController) {
		this.facilityNewMasterReadController = facilityNewMasterReadController;
	}

	public ITrxController getFacilityNewMasterTrxController() {
		return facilityNewMasterTrxController;
	}

	public void setFacilityNewMasterTrxController(
			ITrxController facilityNewMasterTrxController) {
		this.facilityNewMasterTrxController = facilityNewMasterTrxController;
	}

	public FacilityNewMasterTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getFacilityNewMasterReadController();
        }
        return getFacilityNewMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_FACILITY_NEW_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_FACILITY_NEW_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
