package com.integrosys.cms.app.productMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ProductMasterBusManagerImpl extends AbstractProductMasterBusManager implements IProductMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging ProductMaster table  
     * 
     */
	
	
	public String getProductMasterName() {
		return IProductMasterDao.ACTUAL_PRODUCT_MASTER_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated ProductMaster Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IProductMaster updateToWorkingCopy(IProductMaster workingCopy, IProductMaster imageCopy)
	throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IProductMaster updated;
		try{
			workingCopy.setProductCode(imageCopy.getProductCode());
			workingCopy.setProductName(imageCopy.getProductName());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			updated = updateProductMaster(workingCopy);
			return updateProductMaster(updated);
		}catch (Exception e) {
			throw new ProductMasterException("Error while Copying copy to main file");
		}
	}
	
	/**
	 * @return List of all authorized ProductMaster
	 */
	

	public SearchResult getAllProductMaster()throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getProductMasterJdbc().getAllProductMaster();
	}
	
	/**
	 * @return List of all authorized ProductMaster
	 */
	

	public SearchResult getAllFilteredProductMaster(String code,String name)throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getProductMasterJdbc().getAllFilteredProductMaster(code,name);
	}
}
