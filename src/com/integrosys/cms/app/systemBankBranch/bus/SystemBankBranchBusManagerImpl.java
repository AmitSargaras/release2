package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class SystemBankBranchBusManagerImpl extends AbstractSystemBankBranchBusManager implements ISystemBankBranchBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank branch table  
     * 
     */
	
	
	public String getSystemBankBranchName() {
		return ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return SystemBankBranch Object
	 * 
	 */
	

	public ISystemBankBranch getSystemBankById(long id) throws SystemBankBranchException,TrxParameterException,TransactionException {
		
		return getSystemBankBranchDao().load( getSystemBankBranchName(), id);
	}

	/**
	 * @return WorkingCopy-- updated system Bank Branch Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ISystemBankBranch updateToWorkingCopy(ISystemBankBranch workingCopy, ISystemBankBranch imageCopy)
	throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ISystemBankBranch updated;
		try{
//			workingCopy.setAddress(imageCopy.getAddress());
//			workingCopy.setCityTown(imageCopy.getCityTown());
//			workingCopy.setContactMail(imageCopy.getContactMail());
//			workingCopy.setContactNumber(imageCopy.getContactNumber());
//			workingCopy.setStatus(imageCopy.getStatus());
//			workingCopy.setDeprecated(imageCopy.getDeprecated());
//			workingCopy.setFaxNumber(imageCopy.getFaxNumber());
//			workingCopy.setCustodian1(imageCopy.getCustodian1());
//			workingCopy.setCustodian2(imageCopy.getCustodian2());
//			workingCopy.setIsHub(imageCopy.getIsHub());
//			workingCopy.setIsVault(imageCopy.getIsVault());
//			workingCopy.setLinkedHub(imageCopy.getLinkedHub());
			ISystemBankBranch bankBranch= new OBSystemBankBranch();
			bankBranch= workingCopy;
			
			//AccessorUtil.copyValue(imageCopy, bankBranch, new String[] { "id" });
			//DefaultLogger.debug(this, "Using accesor util>>>>>"+bankBranch.getId());
			AccessorUtil.copyValue(imageCopy, bankBranch, new String[] { "id","versionTime" });
			DefaultLogger.debug(this, "Using accesor util>>>>>"+bankBranch.getId());
			//updated = updateSystemBankBranch(workingCopy);
			return updateSystemBankBranch(bankBranch);
		}catch (Exception e) {
			throw new SystemBankBranchException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	

	public SearchResult getAllSystemBankBranch()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getSystemBankBranchDao().getAllSystemBankBranch();
	}
	
	public SearchResult getAllSystemBankBranchForHUB()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getSystemBankBranchDao().getAllSystemBankBranchForHUB();
	}
	
	  public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) throws SystemBankException{
			return getSystemBankBranchDao().getSystemBranchList(branchCode, branchName,state,city);
		}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getSystemBankBranchDao().deleteTransaction(obFileMapperMaster);
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode)throws SystemBankBranchException, TrxParameterException,TransactionException {
		return getSystemBankBranchDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
	
	public boolean isHubValid(String linkedHub) {
		return getSystemBankBranchDao().isHubValid(linkedHub);
	}
}
