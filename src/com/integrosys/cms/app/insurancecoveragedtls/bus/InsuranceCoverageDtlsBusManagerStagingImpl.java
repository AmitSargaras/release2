package com.integrosys.cms.app.insurancecoveragedtls.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * @author dattatray.thorat
 * Bus Manager Implication for staging Insurance Coverage Details
 */
public class InsuranceCoverageDtlsBusManagerStagingImpl extends AbstractInsuranceCoverageDtlsBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getInsuranceCoverageDtlsName() {
        return IInsuranceCoverageDtlsDAO.STAGING_ENTITY_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public IInsuranceCoverageDtls updateToWorkingCopy(IInsuranceCoverageDtls workingCopy, IInsuranceCoverageDtls imageCopy)
            throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the InsuranceCoverage details
	 */
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverageDtls) throws InsuranceCoverageDtlsException , TrxParameterException, TransactionException{
		throw new IllegalStateException("'insuranceCoverageDtls' cannot be null.");		
	}
}