package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class TatMasterTrxControllerFactory implements ITrxControllerFactory{

	
	 private ITrxController tatMasterReadController;

	    private ITrxController tatMasterTrxController;

	    private ITrxController tatMasterInsertFileTrxController;

	    private ITrxController readTatMasterInsertFileTrxController;
	    
	
	public ITrxController getTatMasterReadController() {
			return tatMasterReadController;
		}


		public void setTatMasterReadController(ITrxController tatMasterReadController) {
			this.tatMasterReadController = tatMasterReadController;
		}


		public ITrxController getTatMasterTrxController() {
			return tatMasterTrxController;
		}


		public void setTatMasterTrxController(ITrxController tatMasterTrxController) {
			this.tatMasterTrxController = tatMasterTrxController;
		}


		public ITrxController getTatMasterInsertFileTrxController() {
			return tatMasterInsertFileTrxController;
		}


		public void setTatMasterInsertFileTrxController(
				ITrxController tatMasterInsertFileTrxController) {
			this.tatMasterInsertFileTrxController = tatMasterInsertFileTrxController;
		}


		public ITrxController getReadTatMasterInsertFileTrxController() {
			return readTatMasterInsertFileTrxController;
		}


		public void setReadTatMasterInsertFileTrxController(
				ITrxController readTatMasterInsertFileTrxController) {
			this.readTatMasterInsertFileTrxController = readTatMasterInsertFileTrxController;
		}


	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)
			throws TrxParameterException {
		
		
        if (isReadOperation(param.getAction())) {
            return getTatMasterReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getTatMasterInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getTatMasterInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadTatMasterInsertFileTrxController();
        }
        
        
        return getTatMasterTrxController();
    
	}
	
	private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_TAT_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_TAT_MASTER_ID))) {
            return true;
        }
        return false;
    }

}
