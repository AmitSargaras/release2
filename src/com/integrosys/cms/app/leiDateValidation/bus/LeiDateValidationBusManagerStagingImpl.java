package com.integrosys.cms.app.leiDateValidation.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class LeiDateValidationBusManagerStagingImpl extends AbstractLeiDateValidationBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging LeiDateValidation table  
     * 
     */
	
	public String getLeiDateValidationName() {
        return ILeiDateValidationDao.STAGE_LEI_DATE_VALIDATION;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ILeiDateValidation updateToWorkingCopy(ILeiDateValidation workingCopy, ILeiDateValidation imageCopy)
            throws LeiDateValidationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualLeiDateValidation(String code,
			String name, String category, String type, String system,
			String line) throws LeiDateValidationException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}
}
