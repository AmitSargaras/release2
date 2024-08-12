package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class CaseBranchTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController caseBranchReadController;

    private ITrxController caseBranchTrxController;

    private ITrxController caseBranchInsertFileTrxController;

    private ITrxController readCaseBranchInsertFileTrxController;

    public ITrxController getCaseBranchReadController() {
		return caseBranchReadController;
	}

	public void setCaseBranchReadController(
			ITrxController caseBranchReadController) {
		this.caseBranchReadController = caseBranchReadController;
	}

	public ITrxController getCaseBranchTrxController() {
		return caseBranchTrxController;
	}

	public void setCaseBranchTrxController(
			ITrxController caseBranchTrxController) {
		this.caseBranchTrxController = caseBranchTrxController;
	}
	public ITrxController getCaseBranchInsertFileTrxController() {
		return caseBranchInsertFileTrxController;
	}

	public void setCaseBranchInsertFileTrxController(ITrxController caseBranchInsertFileTrxController) {
		this.caseBranchInsertFileTrxController = caseBranchInsertFileTrxController;
	}

	public ITrxController getReadCaseBranchInsertFileTrxController() {
		return readCaseBranchInsertFileTrxController;
	}

	public void setReadCaseBranchInsertFileTrxController(
			ITrxController readCaseBranchInsertFileTrxController) {
		this.readCaseBranchInsertFileTrxController = readCaseBranchInsertFileTrxController;
	}
	

	public CaseBranchTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getCaseBranchReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getCaseBranchInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getCaseBranchInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadCaseBranchInsertFileTrxController();
        }
        return getCaseBranchTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_CASEBRANCH)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_CASEBRANCH_ID))) {
            return true;
        }
        return false;
    }
}
