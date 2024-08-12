package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class BaselTrxControllerFactory implements ITrxControllerFactory{
	
	
	private ITrxController baselReadController;

    private ITrxController baselTrxController;

    private ITrxController baselInsertFileTrxController;

    private ITrxController readBaselInsertFileTrxController;
    
    

	public ITrxController getBaselReadController() {
		return baselReadController;
	}



	public void setBaselReadController(ITrxController baselReadController) {
		this.baselReadController = baselReadController;
	}



	public ITrxController getBaselTrxController() {
		return baselTrxController;
	}



	public void setBaselTrxController(ITrxController baselTrxController) {
		this.baselTrxController = baselTrxController;
	}



	public ITrxController getBaselInsertFileTrxController() {
		return baselInsertFileTrxController;
	}



	public void setBaselInsertFileTrxController(
			ITrxController baselInsertFileTrxController) {
		this.baselInsertFileTrxController = baselInsertFileTrxController;
	}



	public ITrxController getReadBaselInsertFileTrxController() {
		return readBaselInsertFileTrxController;
	}



	public void setReadBaselInsertFileTrxController(
			ITrxController readBaselInsertFileTrxController) {
		this.readBaselInsertFileTrxController = readBaselInsertFileTrxController;
	}


	 public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
	        if (isReadOperation(param.getAction())) 
	        
	        {
	            return getBaselReadController();
	        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
	        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
	        	return getBaselInsertFileTrxController();
	        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
	        	return getBaselInsertFileTrxController();
	    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
	        	return getReadBaselInsertFileTrxController();
	        }
	        return getBaselTrxController();
	    }

	    private boolean isReadOperation(String anAction) {
	        if ((anAction.equals(ICMSConstant.ACTION_READ_BASEL)) ||
	                (anAction.equals(ICMSConstant.ACTION_READ_BASEL_ID))) {
	            return true;
	        }
	        return false;
	    }

}
