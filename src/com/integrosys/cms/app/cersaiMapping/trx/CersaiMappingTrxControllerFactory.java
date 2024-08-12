package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CersaiMappingTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController cersaiMappingReadController;

    private ITrxController cersaiMappingTrxController;

	
	
	public ITrxController getCersaiMappingReadController() {
		return cersaiMappingReadController;
	}

	public void setCersaiMappingReadController(ITrxController cersaiMappingReadController) {
		this.cersaiMappingReadController = cersaiMappingReadController;
	}

	public ITrxController getCersaiMappingTrxController() {
		return cersaiMappingTrxController;
	}

	public void setCersaiMappingTrxController(ITrxController cersaiMappingTrxController) {
		this.cersaiMappingTrxController = cersaiMappingTrxController;
	}

	public CersaiMappingTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getCersaiMappingReadController();
        }
        return getCersaiMappingTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_CERSAI_MAPPING)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_CERSAI_MAPPING_ID))) {
            return true;
        }
        return false;
    }
}
