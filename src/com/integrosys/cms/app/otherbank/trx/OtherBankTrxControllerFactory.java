package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Trx controller factory
 */
public class OtherBankTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController otherBankReadController;

    private ITrxController otherBankTrxController;
    
    private ITrxController otherBankInsertFileTrxController;

	private ITrxController readOtherBankInsertFileTrxController;

	
	
	public ITrxController getOtherBankInsertFileTrxController() {
		return otherBankInsertFileTrxController;
	}

	public void setOtherBankInsertFileTrxController(
			ITrxController otherBankInsertFileTrxController) {
		this.otherBankInsertFileTrxController = otherBankInsertFileTrxController;
	}

	public ITrxController getReadOtherBankInsertFileTrxController() {
		return readOtherBankInsertFileTrxController;
	}

	public void setReadOtherBankInsertFileTrxController(
			ITrxController readOtherBankInsertFileTrxController) {
		this.readOtherBankInsertFileTrxController = readOtherBankInsertFileTrxController;
	}
	
	
    /**
	 * @return the otherBankReadController
	 */
	public ITrxController getOtherBankReadController() {
		return otherBankReadController;
	}

	/**
	 * @param otherBankReadController the otherBankReadController to set
	 */
	public void setOtherBankReadController(ITrxController otherBankReadController) {
		this.otherBankReadController = otherBankReadController;
	}

	/**
	 * @return the otherBankTrxController
	 */
	public ITrxController getOtherBankTrxController() {
		return otherBankTrxController;
	}

	/**
	 * @param otherBankTrxController the otherBankTrxController to set
	 */
	public void setOtherBankTrxController(ITrxController otherBankTrxController) {
		this.otherBankTrxController = otherBankTrxController;
	}

	public OtherBankTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getOtherBankReadController();
        }

		//**********************UPLOAD START********************************
		else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK)){
        	return getOtherBankInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getOtherBankInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadOtherBankInsertFileTrxController();
        }
		
		
		//**********************UPLOAD END********************************
        
        return getOtherBankTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_OTHER_BANK)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_OTHER_BANK_ID))) {
            return true;
        }
        return false;
    }

	
}
