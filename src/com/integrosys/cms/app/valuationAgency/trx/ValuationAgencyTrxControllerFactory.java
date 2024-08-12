package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */
public class ValuationAgencyTrxControllerFactory implements
		ITrxControllerFactory {

	private ITrxController valuationAgencyReadController;

	private ITrxController valuationAgencyTrxController;
	
	
	//**********************UPLOAD START********************************
	private ITrxController valuationAgencyInsertFileTrxController;

	private ITrxController readValuationAgencyInsertFileTrxController;
	
	public ITrxController getValuationAgencyInsertFileTrxController() {
		return valuationAgencyInsertFileTrxController;
	}

	public void setValuationAgencyInsertFileTrxController(
			ITrxController valuationAgencyInsertFileTrxController) {
		this.valuationAgencyInsertFileTrxController = valuationAgencyInsertFileTrxController;
	}

	public ITrxController getReadValuationAgencyInsertFileTrxController() {
		return readValuationAgencyInsertFileTrxController;
	}

	public void setReadValuationAgencyInsertFileTrxController(
			ITrxController readValuationAgencyInsertFileTrxController) {
		this.readValuationAgencyInsertFileTrxController = readValuationAgencyInsertFileTrxController;
	}
	//**********************UPLOAD END********************************
	
	
		

	public ITrxController getValuationAgencyReadController() {
		return valuationAgencyReadController;
	}

	public void setValuationAgencyReadController(
			ITrxController valuationAgencyReadController) {
		this.valuationAgencyReadController = valuationAgencyReadController;
	}

	public ITrxController getValuationAgencyTrxController() {
		return valuationAgencyTrxController;
	}

	public void setValuationAgencyTrxController(
			ITrxController valuationAgencyTrxController) {
		this.valuationAgencyTrxController = valuationAgencyTrxController;
	}

	public ValuationAgencyTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)
			throws TrxParameterException {
		if (isReadOperation(param.getAction()))

		{return getValuationAgencyReadController();
		}
		
		//**********************UPLOAD START********************************
		else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_VALUATION_AGENCY)){
        	return getValuationAgencyInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getValuationAgencyInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadValuationAgencyInsertFileTrxController();
        }
		return getValuationAgencyTrxController();
		
		//**********************UPLOAD END********************************
	}

	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_VALUATION_AGENCY))
				|| (anAction
						.equals(ICMSConstant.ACTION_READ_VALUATION_AGENCY_ID))) {
			return true;
		}
		return false;
	}

	

	
}
