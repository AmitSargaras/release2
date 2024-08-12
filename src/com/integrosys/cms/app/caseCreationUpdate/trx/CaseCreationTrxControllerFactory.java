package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class CaseCreationTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController caseCreationUpdateReadController;

    private ITrxController caseCreationUpdateTrxController;

    private ITrxController caseCreationUpdateInsertFileTrxController;

    private ITrxController readCaseCreationInsertFileTrxController;

    public ITrxController getCaseCreationReadController() {
		return caseCreationUpdateReadController;
	}

	public void setCaseCreationReadController(
			ITrxController caseCreationUpdateReadController) {
		this.caseCreationUpdateReadController = caseCreationUpdateReadController;
	}

	public ITrxController getCaseCreationTrxController() {
		return caseCreationUpdateTrxController;
	}

	public void setCaseCreationTrxController(
			ITrxController caseCreationUpdateTrxController) {
		this.caseCreationUpdateTrxController = caseCreationUpdateTrxController;
	}
	public ITrxController getCaseCreationInsertFileTrxController() {
		return caseCreationUpdateInsertFileTrxController;
	}

	public void setCaseCreationInsertFileTrxController(ITrxController caseCreationUpdateInsertFileTrxController) {
		this.caseCreationUpdateInsertFileTrxController = caseCreationUpdateInsertFileTrxController;
	}

	public ITrxController getReadCaseCreationInsertFileTrxController() {
		return readCaseCreationInsertFileTrxController;
	}

	public void setReadCaseCreationInsertFileTrxController(
			ITrxController readCaseCreationInsertFileTrxController) {
		this.readCaseCreationInsertFileTrxController = readCaseCreationInsertFileTrxController;
	}
	

	public CaseCreationTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getCaseCreationReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getCaseCreationInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getCaseCreationInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadCaseCreationInsertFileTrxController();
        }
        return getCaseCreationTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_CASECREATION)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_CASECREATION_ID))) {
            return true;
        }
        return false;
    }
}
