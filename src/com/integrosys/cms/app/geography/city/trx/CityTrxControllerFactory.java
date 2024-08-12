package com.integrosys.cms.app.geography.city.trx;

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

public class CityTrxControllerFactory implements ITrxControllerFactory{

	 private ITrxController cityReadController;

	 private ITrxController cityTrxController;
	 
	 private ITrxController cityInsertFileTrxController;

	 private ITrxController readCityInsertFileTrxController;

	/**
	 * @return the cityInsertFileTrxController
	 */
	public ITrxController getCityInsertFileTrxController() {
		return cityInsertFileTrxController;
	}

	/**
	 * @param cityInsertFileTrxController the cityInsertFileTrxController to set
	 */
	public void setCityInsertFileTrxController(
			ITrxController cityInsertFileTrxController) {
		this.cityInsertFileTrxController = cityInsertFileTrxController;
	}

	/**
	 * @return the readCityInsertFileTrxController
	 */
	public ITrxController getReadCityInsertFileTrxController() {
		return readCityInsertFileTrxController;
	}

	/**
	 * @param readCityInsertFileTrxController the readCityInsertFileTrxController to set
	 */
	public void setReadCityInsertFileTrxController(
			ITrxController readCityInsertFileTrxController) {
		this.readCityInsertFileTrxController = readCityInsertFileTrxController;
	}

	public ITrxController getCityReadController() {
		return cityReadController;
	}

	public void setCityReadController(ITrxController cityReadController) {
		this.cityReadController = cityReadController;
	}

	public ITrxController getCityTrxController() {
		return cityTrxController;
	}

	public void setCityTrxController(ITrxController cityTrxController) {
		this.cityTrxController = cityTrxController;
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)throws TrxParameterException {
		if (isReadOperation(param.getAction()))	        
        {
            return getCityReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getCityInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getCityInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadCityInsertFileTrxController();
        }
        return getCityTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_CITY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_CITY_ID))) {
            return true;
        }
        return false;
    }

}
