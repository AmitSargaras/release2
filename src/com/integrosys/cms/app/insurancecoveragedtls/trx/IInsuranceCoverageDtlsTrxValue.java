package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * Insurance Coverage Details Trx Value
 * 
 * @author dattatray.thorat
 */
public interface IInsuranceCoverageDtlsTrxValue extends ICMSTrxValue{
	
	/**
	 * @return the insuranceCoverageDtls
	 */
	public IInsuranceCoverageDtls getInsuranceCoverageDtls() ;

	/**
	 * @param insuranceCoverageDtls the insuranceCoverageDtls to set
	 */
	public void setInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverageDtls);

	/**
	 * @return the stagingInsuranceCoverageDtls
	 */
	public IInsuranceCoverageDtls getStagingInsuranceCoverageDtls() ;

	/**
	 * @param stagingInsuranceCoverageDtls the stagingInsuranceCoverageDtls to set
	 */
	public void setStagingInsuranceCoverageDtls(IInsuranceCoverageDtls stagingInsuranceCoverageDtls) ;
}
