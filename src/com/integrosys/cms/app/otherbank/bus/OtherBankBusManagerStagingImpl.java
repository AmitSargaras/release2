package com.integrosys.cms.app.otherbank.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging System Bank 
 */
public class OtherBankBusManagerStagingImpl extends AbstractOtherBankBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getOtherBankName() {
        return IOtherBankDAO.STAGING_ENTITY_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public IOtherBank updateToWorkingCopy(IOtherBank workingCopy, IOtherBank imageCopy)
            throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public List getAllOtherBank() {

		throw new IllegalStateException("Error in fetching other bank list");
	}
	
	/**
	 * @return the OtherBank details
	 */
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException , TrxParameterException, TransactionException{
		throw new IllegalStateException("'OtherBank' should not be implemented.");
	}
	
	public SearchResult getInsurerList() throws OtherBankException {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchResult getInsurerNameFromCode(String insurerName)
			throws OtherBankException {
		// TODO Auto-generated method stub
		return null;
	}
}