package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class ExcludedFacilityTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController excludedFacilityReadController;

    private ITrxController excludedFacilityTrxController;

	public ITrxController getExcludedFacilityReadController() {
		return excludedFacilityReadController;
	}

	public void setExcludedFacilityReadController(ITrxController excludedFacilityReadController) {
		this.excludedFacilityReadController = excludedFacilityReadController;
	}

	public ITrxController getExcludedFacilityTrxController() {
		return excludedFacilityTrxController;
	}

	public void setExcludedFacilityTrxController(ITrxController excludedFacilityTrxController) {
		this.excludedFacilityTrxController = excludedFacilityTrxController;
	}
	
	public ExcludedFacilityTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getExcludedFacilityReadController();
        }
        return getExcludedFacilityTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_EXCLUDED_FACILITY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_EXCLUDED_FACILITY_ID))) {
            return true;
        }
        return false;
    }
}
