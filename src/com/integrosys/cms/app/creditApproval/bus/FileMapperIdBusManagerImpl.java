package com.integrosys.cms.app.creditApproval.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerImpl extends AbstractCreditApprovalBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging creditApproval table  
     * 
     */
	
	public String getCreditApprovalFileMapperName() {
        return ICreditApprovalDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  creditApproval can never be working copy
	 */
    
    public ICreditApproval updateToWorkingCopy(ICreditApproval workingCopy, ICreditApproval imageCopy)
            throws CreditApprovalException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public String getCreditApprovalActualEntityName() {
		return ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME;
	}

	public String getCreditApprovalStagingEntityName() {
		return ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME;
	}

}