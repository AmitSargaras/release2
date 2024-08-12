package com.integrosys.cms.app.otherbank.bus;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;


public class FileMapperIdBusManagerStagingImpl extends AbstractOtherBankBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getOtherBankName() {
        return IOtherBankDAO.STAGE_FILE_MAPPER_ID;
    }


    
    public IOtherBank updateToWorkingCopy(IOtherBank workingCopy, IOtherBank imageCopy)
            throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }



	
	public List getAllOtherBank() {
		// TODO Auto-generated method stub
		return null;
	}



	
	public IOtherBank deleteOtherBank(IOtherBank OtherBank)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		// TODO Auto-generated method stub
		return null;
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