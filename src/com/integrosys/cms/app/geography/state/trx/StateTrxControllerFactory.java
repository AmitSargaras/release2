package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class StateTrxControllerFactory implements ITrxControllerFactory{

	 private ITrxController stateReadController;

	 private ITrxController stateTrxController;
	 
	 private ITrxController stateInsertFileTrxController;

	 private ITrxController readStateInsertFileTrxController;
	 
	/**
	 * @return the stateInsertFileTrxController
	 */
	public ITrxController getStateInsertFileTrxController() {
		return stateInsertFileTrxController;
	}

	/**
	 * @param stateInsertFileTrxController the stateInsertFileTrxController to set
	 */
	public void setStateInsertFileTrxController(
			ITrxController stateInsertFileTrxController) {
		this.stateInsertFileTrxController = stateInsertFileTrxController;
	}

	/**
	 * @return the readStateInsertFileTrxController
	 */
	public ITrxController getReadStateInsertFileTrxController() {
		return readStateInsertFileTrxController;
	}

	/**
	 * @param readStateInsertFileTrxController the readStateInsertFileTrxController to set
	 */
	public void setReadStateInsertFileTrxController(
			ITrxController readStateInsertFileTrxController) {
		this.readStateInsertFileTrxController = readStateInsertFileTrxController;
	}

	public ITrxController getStateReadController() {
		return stateReadController;
	}

	public void setStateReadController(ITrxController stateReadController) {
		this.stateReadController = stateReadController;
	}

	public ITrxController getStateTrxController() {
		return stateTrxController;
	}

	public void setStateTrxController(ITrxController stateTrxController) {
		this.stateTrxController = stateTrxController;
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)throws TrxParameterException {
		if (isReadOperation(param.getAction()))	        
        {
            return getStateReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getStateInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getStateInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadStateInsertFileTrxController();
        }
        return getStateTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_STATE)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_STATE_ID))) {
            return true;
        }
        return false;
    }

}
