package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Trx controller factory
 */
public class OtherBranchTrxControllerFactory implements ITrxControllerFactory {

   

	private ITrxController otherBranchReadController;

    private ITrxController otherBranchTrxController;
    
    
    private ITrxController otherBankBranchInsertFileTrxController;

	private ITrxController readOtherBankBranchInsertFileTrxController;


	
	 public ITrxController getOtherBankBranchInsertFileTrxController() {
			return otherBankBranchInsertFileTrxController;
		}

		public void setOtherBankBranchInsertFileTrxController(
				ITrxController otherBankBranchInsertFileTrxController) {
			this.otherBankBranchInsertFileTrxController = otherBankBranchInsertFileTrxController;
		}

		public ITrxController getReadOtherBankBranchInsertFileTrxController() {
			return readOtherBankBranchInsertFileTrxController;
		}

		public void setReadOtherBankBranchInsertFileTrxController(
				ITrxController readOtherBankBranchInsertFileTrxController) {
			this.readOtherBankBranchInsertFileTrxController = readOtherBankBranchInsertFileTrxController;
		}
	
	
	
    /**
	 * @return the otherBranchReadController
	 */
	public ITrxController getOtherBranchReadController() {
		return otherBranchReadController;
	}

	/**
	 * @param otherBranchReadController the otherBranchReadController to set
	 */
	public void setOtherBranchReadController(
			ITrxController otherBranchReadController) {
		this.otherBranchReadController = otherBranchReadController;
	}

	/**
	 * @return the otherBranchTrxController
	 */
	public ITrxController getOtherBranchTrxController() {
		return otherBranchTrxController;
	}

	/**
	 * @param otherBranchTrxController the otherBranchTrxController to set
	 */
	public void setOtherBranchTrxController(ITrxController otherBranchTrxController) {
		this.otherBranchTrxController = otherBranchTrxController;
	}

	public OtherBranchTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getOtherBranchReadController();
        }
        
    	//**********************UPLOAD START********************************
		else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK_BRANCH)){
        	return getOtherBankBranchInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getOtherBankBranchInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadOtherBankBranchInsertFileTrxController();
        }
		
		
		//**********************UPLOAD END********************************
   
        return getOtherBranchTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_OTHER_BANK_BRANCH)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_OTHER_BANK_BRANCH_ID))) {
            return true;
        }
        return false;
    }
}
