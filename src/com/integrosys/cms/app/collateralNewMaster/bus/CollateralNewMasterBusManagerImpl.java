package com.integrosys.cms.app.collateralNewMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class CollateralNewMasterBusManagerImpl extends AbstractCollateralNewMasterBusManager implements ICollateralNewMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging CollateralNewMaster table  
     * 
     */
	
	
	
	
	public String getCollateralNewMasterName() {
		return ICollateralNewMasterDao.ACTUAL_COLLATERAL_NEW_MASTER_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return CollateralNewMaster Object
	 * 
	 */
	

	public ICollateralNewMaster getSystemBankById(long id) throws CollateralNewMasterException,TrxParameterException,TransactionException {
		
		return getCollateralNewMasterDao().load( getCollateralNewMasterName(), id);
	}

	/**
	 * @return WorkingCopy-- updated CollateralNewMaster Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ICollateralNewMaster updateToWorkingCopy(ICollateralNewMaster workingCopy, ICollateralNewMaster imageCopy)
	throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ICollateralNewMaster updated;
		try{
			workingCopy.setNewCollateralCode(imageCopy.getNewCollateralCode());
			workingCopy.setNewCollateralDescription(imageCopy.getNewCollateralDescription());
			workingCopy.setNewCollateralMainType(imageCopy.getNewCollateralMainType());
			workingCopy.setNewCollateralSubType(imageCopy.getNewCollateralSubType());
			workingCopy.setInsurance(imageCopy.getInsurance());
			workingCopy.setRevaluationFrequencyCount(imageCopy.getRevaluationFrequencyCount());
			workingCopy.setRevaluationFrequencyDays(imageCopy.getRevaluationFrequencyDays());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setIsApplicableForCersaiInd(imageCopy.getIsApplicableForCersaiInd());
			workingCopy.setNewCollateralCategory(imageCopy.getNewCollateralCategory());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateCollateralNewMaster(workingCopy);
			return updateCollateralNewMaster(updated);
		}catch (Exception e) {
			throw new CollateralNewMasterException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized CollateralNewMaster
	 */
	

	public SearchResult getAllCollateralNewMaster()throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCollateralNewMasterJdbc().getAllCollateralNewMaster();
	}
	
	public SearchResult getFilteredCollateral(String collateralCode,String collateralDescription,String collateralMainType,String collateralSubType)throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCollateralNewMasterJdbc().getFilteredCollateral(collateralCode,collateralDescription,collateralMainType,collateralSubType);
	}

	public boolean isCollateraNameUnique(String collateralName) {
		return getCollateralNewMasterDao().isCollateraNameUnique(collateralName);
	}
	public boolean isCollateralCodeDuplicate(String cpsId) {
		return getCollateralNewMasterDao().isDuplicateRecord(cpsId);
	}
	
	

}
