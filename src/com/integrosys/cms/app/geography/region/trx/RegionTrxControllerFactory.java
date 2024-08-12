package com.integrosys.cms.app.geography.region.trx;

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

public class RegionTrxControllerFactory implements ITrxControllerFactory{

	 private ITrxController regionReadController;

	 private ITrxController regionTrxController;
	 
	 private ITrxController regionInsertFileTrxController;

	 private ITrxController readRegionInsertFileTrxController;
	 
	 
	/**
	 * @return the regionInsertFileTrxController
	 */
	public ITrxController getRegionInsertFileTrxController() {
		return regionInsertFileTrxController;
	}

	/**
	 * @param regionInsertFileTrxController the regionInsertFileTrxController to set
	 */
	public void setRegionInsertFileTrxController(
			ITrxController regionInsertFileTrxController) {
		this.regionInsertFileTrxController = regionInsertFileTrxController;
	}

	/**
	 * @return the readRegionInsertFileTrxController
	 */
	public ITrxController getReadRegionInsertFileTrxController() {
		return readRegionInsertFileTrxController;
	}

	/**
	 * @param readRegionInsertFileTrxController the readRegionInsertFileTrxController to set
	 */
	public void setReadRegionInsertFileTrxController(
			ITrxController readRegionInsertFileTrxController) {
		this.readRegionInsertFileTrxController = readRegionInsertFileTrxController;
	}

	public ITrxController getRegionReadController() {
		return regionReadController;
	}

	public void setRegionReadController(ITrxController regionReadController) {
		this.regionReadController = regionReadController;
	}

	public ITrxController getRegionTrxController() {
		return regionTrxController;
	}

	public void setRegionTrxController(ITrxController regionTrxController) {
		this.regionTrxController = regionTrxController;
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)throws TrxParameterException {
		if (isReadOperation(param.getAction()))	        
        {
            return getRegionReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getRegionInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getRegionInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadRegionInsertFileTrxController();
        }
        return getRegionTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_REGION)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_REGION_ID))) {
            return true;
        }
        return false;
    }

}
