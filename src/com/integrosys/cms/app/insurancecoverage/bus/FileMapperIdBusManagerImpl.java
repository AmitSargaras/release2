package com.integrosys.cms.app.insurancecoverage.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerImpl extends AbstractInsuranceCoverageBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getInsuranceCoverageName() {
        return IInsuranceCoverageDAO.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IInsuranceCoverage updateToWorkingCopy(IInsuranceCoverage workingCopy, IInsuranceCoverage imageCopy)
            throws InsuranceCoverageException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
    
    public IInsuranceCoverage deleteInsuranceCoverage(
			IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException,
			TrxParameterException, TransactionException {
		throw new InsuranceCoverageException("IInsuranceCoverage object is null");
	}
}