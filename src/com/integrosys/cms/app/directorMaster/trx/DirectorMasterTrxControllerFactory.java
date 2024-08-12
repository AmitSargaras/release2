package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 * Trx controller factory
 */
public class DirectorMasterTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController directorMasterReadController;

    private ITrxController directorMasterTrxController;

   

    public ITrxController getDirectorMasterReadController() {
		return directorMasterReadController;
	}

	public void setDirectorMasterReadController(
			ITrxController directorMasterReadController) {
		this.directorMasterReadController = directorMasterReadController;
	}

	public ITrxController getDirectorMasterTrxController() {
		return directorMasterTrxController;
	}

	public void setDirectorMasterTrxController(
			ITrxController directorMasterTrxController) {
		this.directorMasterTrxController = directorMasterTrxController;
	}

	public DirectorMasterTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getDirectorMasterReadController();
        }
        return getDirectorMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_DIRECTOR_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_DIRECTOR_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
