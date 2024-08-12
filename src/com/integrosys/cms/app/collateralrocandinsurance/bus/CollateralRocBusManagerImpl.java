package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;

public class CollateralRocBusManagerImpl  extends AbstractCollateralRocBusManager implements ICollateralRocBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Collateral Roc table  
     * 
     */
	
	
	public String getCollateralRocName() {
		return ICollateralRocDao.ACTUAL_COLLATERAL_ROC_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated Collateral Roc Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ICollateralRoc updateToWorkingCopy(ICollateralRoc workingCopy, ICollateralRoc imageCopy)
	throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ICollateralRoc updated;
		try{
			workingCopy.setCollateralCategory(imageCopy.getCollateralCategory());
			workingCopy.setCollateralRocCode(imageCopy.getCollateralRocCode());
			workingCopy.setCollateralRocDescription(imageCopy.getCollateralRocDescription());
			workingCopy.setIrbCategory(imageCopy.getIrbCategory());
			workingCopy.setInsuranceApplicable(imageCopy.getInsuranceApplicable());
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateCollateralRoc(workingCopy);
			return updateCollateralRoc(updated);
		}catch (Exception e) {
			throw new CollateralRocException("Error while Copying copy to main file");
		}
	}
	
	public SearchResult getAllCollateralRoc()throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getCollateralRocJdbc().getAllCollateralRoc();
	}
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode){
		return getCollateralRocDao().isCollateralRocCategoryUnique(collateralCategory,collateralRocCode);
	}
	
	public SearchResult getSearchedCollateralRoc(String type,String text)throws CollateralRocException,TrxParameterException,TransactionException {
		 return getCollateralRocDao().getSearchedCollateralRoc(type,text);
	}
}
