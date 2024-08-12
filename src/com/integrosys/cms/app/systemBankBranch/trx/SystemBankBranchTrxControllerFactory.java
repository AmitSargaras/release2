package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class SystemBankBranchTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController systemBankBranchReadController;

    private ITrxController systemBankBranchTrxController;
    
    private ITrxController systemBankBranchInsertFileTrxController;

    private ITrxController readSystemBankBranchInsertFileTrxController;

    public ITrxController getSystemBankBranchReadController() {
		return systemBankBranchReadController;
	}

	public void setSystemBankBranchReadController(
			ITrxController systemBankBranchReadController) {
		this.systemBankBranchReadController = systemBankBranchReadController;
	}

	public ITrxController getSystemBankBranchTrxController() {
		return systemBankBranchTrxController;
	}

	public void setSystemBankBranchTrxController(
			ITrxController systemBankBranchTrxController) {
		this.systemBankBranchTrxController = systemBankBranchTrxController;
	}
	
	public ITrxController getSystemBankBranchInsertFileTrxController() {
		return systemBankBranchInsertFileTrxController;
	}

	public void setSystemBankBranchInsertFileTrxController(
			ITrxController systemBankBranchInsertFileTrxController) {
		this.systemBankBranchInsertFileTrxController = systemBankBranchInsertFileTrxController;
	}

	public ITrxController getReadSystemBankBranchInsertFileTrxController() {
		return readSystemBankBranchInsertFileTrxController;
	}

	public void setReadSystemBankBranchInsertFileTrxController(
			ITrxController readSystemBankBranchInsertFileTrxController) {
		this.readSystemBankBranchInsertFileTrxController = readSystemBankBranchInsertFileTrxController;
	}
	

	public SystemBankBranchTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getSystemBankBranchReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER)
        		|| param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getSystemBankBranchInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadSystemBankBranchInsertFileTrxController();
        }
        return getSystemBankBranchTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK_BRANCH)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK_BRANCH_ID))) {
            return true;
        }
        return false;
    }
}
