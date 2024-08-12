package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Insurance Coverage Details Trx controller factory
 */
public class InsuranceCoverageDtlsTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController insuranceCoverageDtlsReadController;

    private ITrxController insuranceCoverageDtlsTrxController;

	/**
	 * @return the insuranceCoverageDtlsReadController
	 */
	public ITrxController getInsuranceCoverageDtlsReadController() {
		return insuranceCoverageDtlsReadController;
	}

	/**
	 * @param insuranceCoverageDtlsReadController the insuranceCoverageDtlsReadController to set
	 */
	public void setInsuranceCoverageDtlsReadController(
			ITrxController insuranceCoverageDtlsReadController) {
		this.insuranceCoverageDtlsReadController = insuranceCoverageDtlsReadController;
	}

	/**
	 * @return the insuranceCoverageDtlsTrxController
	 */
	public ITrxController getInsuranceCoverageDtlsTrxController() {
		return insuranceCoverageDtlsTrxController;
	}

	/**
	 * @param insuranceCoverageDtlsTrxController the insuranceCoverageDtlsTrxController to set
	 */
	public void setInsuranceCoverageDtlsTrxController(
			ITrxController insuranceCoverageDtlsTrxController) {
		this.insuranceCoverageDtlsTrxController = insuranceCoverageDtlsTrxController;
	}

	public InsuranceCoverageDtlsTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getInsuranceCoverageDtlsReadController();
        }
        return getInsuranceCoverageDtlsTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_DTLS)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_DTLS_ID))) {
            return true;
        }
        return false;
    }
}
