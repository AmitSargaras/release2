package com.integrosys.cms.app.fccBranch.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;


public class FCCBranchBusManagerImpl extends AbstractFCCBranchBusManager implements IFCCBranchBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging FCCBranch table  
     * 
     */
	
	
	public String getBranchName() {
		return IFCCBranchDao.ACTUAL_FCCBRANCH_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return FCCBranch Object
	 * 
	 */
	

	public IFCCBranch getSystemBankById(long id) throws FCCBranchException,TrxParameterException,TransactionException {
		
		return getFccBranchDao().load( getBranchName(), id);
	}

	/**
	 * @return WorkingCopy-- updated FCCBranch Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IFCCBranch updateToWorkingCopy(IFCCBranch workingCopy, IFCCBranch imageCopy)
	throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IFCCBranch updated;
		try{
			workingCopy.setBranchCode(imageCopy.getBranchCode());
			workingCopy.setAliasBranchCode(imageCopy.getAliasBranchCode());
			workingCopy.setBranchName(imageCopy.getBranchName());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateFCCBranch(workingCopy);
			return updateFCCBranch(updated);
		}catch (Exception e) {
			throw new FCCBranchException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized FCCBranch
	 */
	

	public SearchResult getAllFCCBranch()throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getFccBranchJdbc().getAllFCCBranch();
	}

	
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getFccBranchDao().deleteTransaction(obFileMapperMaster);		
	}
	
	
	/**
	 * @return List of all authorized FCCBranch
	 */
	public SearchResult getAllFilteredFCCBranch(String code,String name)throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getFccBranchJdbc().getAllFCCBranch(code,name);
	}
	
	public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException {
		return getFccBranchDao().fccBranchUniqueCombination(branchCode,aliasBranchCode,id);
	}
}
