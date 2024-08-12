package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CountryTrxControllerFactory implements ITrxControllerFactory{

	 private ITrxController countryReadController;

	 private ITrxController countryTrxController;
	 
	 private ITrxController countryInsertFileTrxController;

	 private ITrxController readCountryInsertFileTrxController;
	 
	/**
	 * @return the countryInsertFileTrxController
	 */
	public ITrxController getCountryInsertFileTrxController() {
		return countryInsertFileTrxController;
	}

	/**
	 * @param countryInsertFileTrxController the countryInsertFileTrxController to set
	 */
	public void setCountryInsertFileTrxController(
			ITrxController countryInsertFileTrxController) {
		this.countryInsertFileTrxController = countryInsertFileTrxController;
	}

	/**
	 * @return the readCountryInsertFileTrxController
	 */
	public ITrxController getReadCountryInsertFileTrxController() {
		return readCountryInsertFileTrxController;
	}

	/**
	 * @param readCountryInsertFileTrxController the readCountryInsertFileTrxController to set
	 */
	public void setReadCountryInsertFileTrxController(
			ITrxController readCountryInsertFileTrxController) {
		this.readCountryInsertFileTrxController = readCountryInsertFileTrxController;
	}

	public ITrxController getCountryReadController() {
		return countryReadController;
	}

	public void setCountryReadController(ITrxController countryReadController) {
		this.countryReadController = countryReadController;
	}

	public ITrxController getCountryTrxController() {
		return countryTrxController;
	}

	public void setCountryTrxController(ITrxController countryTrxController) {
		this.countryTrxController = countryTrxController;
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)throws TrxParameterException {
		if (isReadOperation(param.getAction()))	        
        {
            return getCountryReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getCountryInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getCountryInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadCountryInsertFileTrxController();
        }
        return getCountryTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_COUNTRY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_COUNTRY_ID))) {
            return true;
        }
        return false;
    }

}
