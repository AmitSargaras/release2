package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class SegmentWiseEmailTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController segmentWiseEmailReadController;

    private ITrxController segmentWiseEmailTrxController;
	
	public ITrxController getSegmentWiseEmailReadController() {
		return segmentWiseEmailReadController;
	}

	public void setSegmentWiseEmailReadController(ITrxController segmentWiseEmailReadController) {
		this.segmentWiseEmailReadController = segmentWiseEmailReadController;
	}

	public ITrxController getSegmentWiseEmailTrxController() {
		return segmentWiseEmailTrxController;
	}

	public void setSegmentWiseEmailTrxController(ITrxController segmentWiseEmailTrxController) {
		this.segmentWiseEmailTrxController = segmentWiseEmailTrxController;
	}

	public SegmentWiseEmailTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getSegmentWiseEmailReadController();
        }
        return getSegmentWiseEmailTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_SEGMENT_WISE_EMAIL)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_SEGMENT_WISE_EMAIL_ID))) {
            return true;
        }
        return false;
    }
}
