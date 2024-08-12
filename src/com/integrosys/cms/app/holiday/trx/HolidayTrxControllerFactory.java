package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class HolidayTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController holidayReadController;

    private ITrxController holidayTrxController;

    private ITrxController holidayInsertFileTrxController;

    private ITrxController readHolidayInsertFileTrxController;

    public ITrxController getHolidayReadController() {
		return holidayReadController;
	}

	public void setHolidayReadController(
			ITrxController holidayReadController) {
		this.holidayReadController = holidayReadController;
	}

	public ITrxController getHolidayTrxController() {
		return holidayTrxController;
	}

	public void setHolidayTrxController(
			ITrxController holidayTrxController) {
		this.holidayTrxController = holidayTrxController;
	}
	public ITrxController getHolidayInsertFileTrxController() {
		return holidayInsertFileTrxController;
	}

	public void setHolidayInsertFileTrxController(ITrxController holidayInsertFileTrxController) {
		this.holidayInsertFileTrxController = holidayInsertFileTrxController;
	}

	public ITrxController getReadHolidayInsertFileTrxController() {
		return readHolidayInsertFileTrxController;
	}

	public void setReadHolidayInsertFileTrxController(
			ITrxController readHolidayInsertFileTrxController) {
		this.readHolidayInsertFileTrxController = readHolidayInsertFileTrxController;
	}
	

	public HolidayTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getHolidayReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getHolidayInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getHolidayInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadHolidayInsertFileTrxController();
        }
        return getHolidayTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_HOLIDAY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_HOLIDAY_ID))) {
            return true;
        }
        return false;
    }
}
