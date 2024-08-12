package com.integrosys.cms.app.otherbranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

public class FileMapperBranchIdBusManagerImpl extends AbstractOtherBankBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	
	
	
	public String getOtherBranchName() {
		
		
		return IOtherBranchDAO.ACTUAL_STAGE_FILE_MAPPER_ID;
		
	}

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IOtherBranch updateToWorkingCopy(IOtherBranch workingCopy, IOtherBranch imageCopy)
            throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
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


	


	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch)
			throws OtherBranchException, TrxParameterException,
			TransactionException {
		// TODO Auto-generated method stub
		return null;
	}


	public List getAllOtherBranch() {
		// TODO Auto-generated method stub
		return null;
	}




}
