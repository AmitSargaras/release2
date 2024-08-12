package com.integrosys.cms.app.insurancecoverage.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 * Bus Manager Implication for staging Insurance Coverage 
 */
public class InsuranceCoverageBusManagerStagingImpl extends AbstractInsuranceCoverageBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Insurance Coverage  table  
     * 
     */
	
    public String getInsuranceCoverageName() {
        return IInsuranceCoverageDAO.STAGING_ENTITY_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public IInsuranceCoverage updateToWorkingCopy(IInsuranceCoverage workingCopy, IInsuranceCoverage imageCopy)
            throws InsuranceCoverageException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the InsuranceCoverage details
	 */
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage insuranceCoverage) throws InsuranceCoverageException , TrxParameterException, TransactionException{
		throw new IllegalStateException("'insuranceCoverage' cannot be null.");	
	}
}