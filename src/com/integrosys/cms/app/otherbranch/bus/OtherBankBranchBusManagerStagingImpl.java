package com.integrosys.cms.app.otherbranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging System Bank 
 */
public class OtherBankBranchBusManagerStagingImpl extends AbstractOtherBankBranchBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getOtherBranchName() {
        return IOtherBranchDAO.STAGING_ENTITY_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public IOtherBranch updateToWorkingCopy(IOtherBranch workingCopy, IOtherBranch imageCopy)
            throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public List getAllOtherBranch() {

		return null;
	}
	
	/**
	 * @return the OtherBank details
	 */
	public IOtherBranch deleteOtherBranch(IOtherBranch otherBranch) throws OtherBranchException , TrxParameterException, TransactionException{
		throw new IllegalStateException("'otherBranch' should not be implemented.");	
	}
	
	/**
	 * @return the SearchResult  
	 */
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException{
		throw new IllegalStateException("Error in fetching other bank branch list");
	}
	
	
}