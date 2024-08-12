package com.integrosys.cms.app.caseBranch.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class CaseBranchBusManagerImpl extends AbstractCaseBranchBusManager implements ICaseBranchBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging CaseBranch table  
     * 
     */
	
	
	public String getCaseBranchName() {
		return ICaseBranchDao.ACTUAL_CASEBRANCH_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return CaseBranch Object
	 * 
	 */
	

	public ICaseBranch getSystemBankById(long id) throws CaseBranchException,TrxParameterException,TransactionException {
		
		return getCaseBranchDao().load( getCaseBranchName(), id);
	}

	/**
	 * @return WorkingCopy-- updated CaseBranch Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ICaseBranch updateToWorkingCopy(ICaseBranch workingCopy, ICaseBranch imageCopy)
	throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ICaseBranch updated;
		try{
			workingCopy.setBranchCode(imageCopy.getBranchCode());
			workingCopy.setCoordinator1(imageCopy.getCoordinator1());
			workingCopy.setCoordinator2(imageCopy.getCoordinator2());
			workingCopy.setCoordinator1Email(imageCopy.getCoordinator1Email());
			workingCopy.setCoordinator2Email(imageCopy.getCoordinator2Email());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateCaseBranch(workingCopy);
			return updateCaseBranch(updated);
		}catch (Exception e) {
			throw new CaseBranchException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized CaseBranch
	 */
	

	public SearchResult getAllCaseBranch()throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCaseBranchJdbc().getAllCaseBranch();
	}

	/**
	 * @return List of all authorized CaseBranch
	 */
	public SearchResult getAllFilteredCaseBranch(String code,String name)throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCaseBranchJdbc().getAllFilteredCaseBranch(code,name);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCaseBranchDao().deleteTransaction(obFileMapperMaster);		
	}
}
