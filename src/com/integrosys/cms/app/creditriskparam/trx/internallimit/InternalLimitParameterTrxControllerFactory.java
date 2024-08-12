/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ForexFeedGroupTrxControllerFactory.java,v 1.9 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 10:15:18 $ Tag: $Name: $
 */
public class InternalLimitParameterTrxControllerFactory implements ITrxControllerFactory {
    
    private InternalLimitParameterReadController internalLimitParameterReadController;
    private InternalLimitParameterTrxController internalLimitParameterTrxController;

    public InternalLimitParameterTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
		if (ICMSConstant.ACTION_READ_INTERNAL_LIMIT.equals(param.getAction()) || ICMSConstant.ACTION_READ_INTERNAL_LIMIT_BY_TRX_ID.equals(param.getAction())) {
			return getInternalLimitParameterReadController();
		} else {
			return getInternalLimitParameterTrxController();
		}
	}

    public void setInternalLimitParameterReadController(InternalLimitParameterReadController internalLimitParameterReadController) {
        this.internalLimitParameterReadController = internalLimitParameterReadController;
    }

    public InternalLimitParameterReadController getInternalLimitParameterReadController() {
        return internalLimitParameterReadController;
    }

    public void setInternalLimitParameterTrxController(InternalLimitParameterTrxController internalLimitParameterTrxController) {
        this.internalLimitParameterTrxController = internalLimitParameterTrxController;
    }

    public InternalLimitParameterTrxController getInternalLimitParameterTrxController() {
        return internalLimitParameterTrxController;
    }
}
