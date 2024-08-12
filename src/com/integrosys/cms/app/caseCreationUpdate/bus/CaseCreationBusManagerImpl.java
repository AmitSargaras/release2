package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.util.Date;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class CaseCreationBusManagerImpl extends AbstractCaseCreationBusManager implements ICaseCreationBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging CaseCreation table  
     * 
     */
	
	
	public String getCaseCreationName() {
		return ICaseCreationDao.ACTUAL_CASECREATION_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return CaseCreation Object
	 * 
	 */
	

	public ICaseCreation getSystemBankById(long id) throws CaseCreationException,TrxParameterException,TransactionException {
		
		return getCaseCreationUpdateDao().load( getCaseCreationName(), id);
	}

	/**
	 * @return WorkingCopy-- updated CaseCreation Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ICaseCreation updateToWorkingCopy(ICaseCreation workingCopy, ICaseCreation imageCopy)
	throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ICaseCreation updated;
		try{
			 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
             IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
 			Date  applicationDate= new Date(generalParamEntries.getParamValue());
			workingCopy.setDescription(imageCopy.getDescription());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setBranchCode(imageCopy.getBranchCode());
			workingCopy.setCurrRemarks(imageCopy.getCurrRemarks());
			workingCopy.setPrevRemarks(imageCopy.getPrevRemarks());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setCreationDate(imageCopy.getCreationDate());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			workingCopy.setLastUpdateDate(applicationDate);
			workingCopy.setLimitProfileId(imageCopy.getLimitProfileId());
			
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateCaseCreation(workingCopy);
			return updateCaseCreation(updated);
		}catch (Exception e) {
			throw new CaseCreationException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized CaseCreation
	 */
	

	public SearchResult getAllCaseCreation(long id)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCaseCreationUpdateJdbc().getAllCaseCreation(id);
	}
	public SearchResult getAllCaseCreation()throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCaseCreationUpdateJdbc().getAllCaseCreation();
	}
	public SearchResult getAllCaseCreationBranchMenu(String branchCode)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCaseCreationUpdateJdbc().getAllCaseCreationBranchMenu(branchCode);
	}
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCaseCreationUpdateDao().deleteTransaction(obFileMapperMaster);		
	}
}
