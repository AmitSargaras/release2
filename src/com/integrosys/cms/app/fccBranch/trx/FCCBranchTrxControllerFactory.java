package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class FCCBranchTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController fccBranchReadController;

    private ITrxController fccBranchTrxController;

    private ITrxController fccBranchInsertFileTrxController;

    private ITrxController readFCCBranchInsertFileTrxController;

   
	

	/**
	 * @return the fccBranchReadController
	 */
	public ITrxController getFccBranchReadController() {
		return fccBranchReadController;
	}

	/**
	 * @param fccBranchReadController the fccBranchReadController to set
	 */
	public void setFccBranchReadController(ITrxController fccBranchReadController) {
		this.fccBranchReadController = fccBranchReadController;
	}

	/**
	 * @return the fccBranchTrxController
	 */
	public ITrxController getFccBranchTrxController() {
		return fccBranchTrxController;
	}

	/**
	 * @param fccBranchTrxController the fccBranchTrxController to set
	 */
	public void setFccBranchTrxController(ITrxController fccBranchTrxController) {
		this.fccBranchTrxController = fccBranchTrxController;
	}

	/**
	 * @return the fccBranchInsertFileTrxController
	 */
	public ITrxController getFccBranchInsertFileTrxController() {
		return fccBranchInsertFileTrxController;
	}

	/**
	 * @param fccBranchInsertFileTrxController the fccBranchInsertFileTrxController to set
	 */
	public void setFccBranchInsertFileTrxController(
			ITrxController fccBranchInsertFileTrxController) {
		this.fccBranchInsertFileTrxController = fccBranchInsertFileTrxController;
	}

	/**
	 * @return the readFCCBranchInsertFileTrxController
	 */
	public ITrxController getReadFCCBranchInsertFileTrxController() {
		return readFCCBranchInsertFileTrxController;
	}

	/**
	 * @param readFCCBranchInsertFileTrxController the readFCCBranchInsertFileTrxController to set
	 */
	public void setReadFCCBranchInsertFileTrxController(
			ITrxController readFCCBranchInsertFileTrxController) {
		this.readFCCBranchInsertFileTrxController = readFCCBranchInsertFileTrxController;
	}

	public FCCBranchTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getFccBranchReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getFccBranchInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getFccBranchInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadFCCBranchInsertFileTrxController();
        }
        return getFccBranchTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_FCCBRANCH)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_FCCBRANCH_ID))) {
            return true;
        }
        return false;
    }
}
