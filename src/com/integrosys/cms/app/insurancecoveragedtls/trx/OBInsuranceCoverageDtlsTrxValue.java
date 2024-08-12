package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * Insurance Coverage Details Trx Value
 * 
 * @author dattatray.thorat
 */
public class OBInsuranceCoverageDtlsTrxValue extends OBCMSTrxValue implements IInsuranceCoverageDtlsTrxValue{

    public  OBInsuranceCoverageDtlsTrxValue(){}

    IInsuranceCoverageDtls insuranceCoverageDtls ;
    IInsuranceCoverageDtls stagingInsuranceCoverageDtls ;

    public OBInsuranceCoverageDtlsTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the insuranceCoverageDtls
	 */
	public IInsuranceCoverageDtls getInsuranceCoverageDtls() {
		return insuranceCoverageDtls;
	}

	/**
	 * @param insuranceCoverageDtls the insuranceCoverageDtls to set
	 */
	public void setInsuranceCoverageDtls(
			IInsuranceCoverageDtls insuranceCoverageDtls) {
		this.insuranceCoverageDtls = insuranceCoverageDtls;
	}

	/**
	 * @return the stagingInsuranceCoverageDtls
	 */
	public IInsuranceCoverageDtls getStagingInsuranceCoverageDtls() {
		return stagingInsuranceCoverageDtls;
	}

	/**
	 * @param stagingInsuranceCoverageDtls the stagingInsuranceCoverageDtls to set
	 */
	public void setStagingInsuranceCoverageDtls(
			IInsuranceCoverageDtls stagingInsuranceCoverageDtls) {
		this.stagingInsuranceCoverageDtls = stagingInsuranceCoverageDtls;
	}

}
