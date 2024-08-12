package com.integrosys.cms.app.rbicategory.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author Govind.Sahu
 * Bus Manager Implication for staging Rbi Category
 */
public class RbiCategoryBusManagerStagingImpl extends AbstractRbiCategoryBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging system bank branch table  
     * 
     */
	
	public String getRbiCategoryName() {
        return IRbiCategoryDao.STAGE_RBI_CATEGORY_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IRbiCategory updateToWorkingCopy(IRbiCategory workingCopy, IRbiCategory imageCopy)
            throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public List searchRbiCategory(String login) throws RbiCategoryException,
			TrxParameterException, TransactionException {
		// TODO Auto-generated method stub
		return null;
	}



}