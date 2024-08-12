package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * insurance Coverage Trx controller factory
 */
public class InsuranceCoverageTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController insuranceCoverageReadController;

    private ITrxController insuranceCoverageTrxController;
    
    private ITrxController insuranceCoverageInsertFileTrxController;
    
    private ITrxController readInsuranceCoverageInsertFileTrxController;

	/**
	 * @return the insuranceCoverageInsertFileTrxController
	 */
	public ITrxController getInsuranceCoverageInsertFileTrxController() {
		return insuranceCoverageInsertFileTrxController;
	}

	/**
	 * @param insuranceCoverageInsertFileTrxController the insuranceCoverageInsertFileTrxController to set
	 */
	public void setInsuranceCoverageInsertFileTrxController(
			ITrxController insuranceCoverageInsertFileTrxController) {
		this.insuranceCoverageInsertFileTrxController = insuranceCoverageInsertFileTrxController;
	}

	/**
	 * @return the readInsuranceCoverageInsertFileTrxController
	 */
	public ITrxController getReadInsuranceCoverageInsertFileTrxController() {
		return readInsuranceCoverageInsertFileTrxController;
	}

	/**
	 * @param readInsuranceCoverageInsertFileTrxController the readInsuranceCoverageInsertFileTrxController to set
	 */
	public void setReadInsuranceCoverageInsertFileTrxController(
			ITrxController readInsuranceCoverageInsertFileTrxController) {
		this.readInsuranceCoverageInsertFileTrxController = readInsuranceCoverageInsertFileTrxController;
	}

	/**
	 * @return the insuranceCoverageReadController
	 */
	public ITrxController getInsuranceCoverageReadController() {
		return insuranceCoverageReadController;
	}

	/**
	 * @param insuranceCoverageReadController the insuranceCoverageReadController to set
	 */
	public void setInsuranceCoverageReadController(
			ITrxController insuranceCoverageReadController) {
		this.insuranceCoverageReadController = insuranceCoverageReadController;
	}

	/**
	 * @return the insuranceCoverageTrxController
	 */
	public ITrxController getInsuranceCoverageTrxController() {
		return insuranceCoverageTrxController;
	}

	/**
	 * @param insuranceCoverageTrxController the insuranceCoverageTrxController to set
	 */
	public void setInsuranceCoverageTrxController(
			ITrxController insuranceCoverageTrxController) {
		this.insuranceCoverageTrxController = insuranceCoverageTrxController;
	}

	public InsuranceCoverageTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        {
            return getInsuranceCoverageReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getInsuranceCoverageInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getInsuranceCoverageInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadInsuranceCoverageInsertFileTrxController();
        }
        return getInsuranceCoverageTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_ID))) {
            return true;
        }
        return false;
    }
}
