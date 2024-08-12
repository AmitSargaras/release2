package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.*;

public class NpaTraqCodeMasterBusManagerImpl extends AbstractNpaTraqCodeMasterBusManager implements INpaTraqCodeMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging NpaTraqCodeMaster table  
     * 
     */
	
	
	public String getNpaTraqCodeMasterName() {
		return INpaTraqCodeMasterDao.ACTUAL_NPA_TRAQ_CODE_MASTER_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated NpaTraqCodeMaster Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public INpaTraqCodeMaster updateToWorkingCopy(INpaTraqCodeMaster workingCopy, INpaTraqCodeMaster imageCopy)
	throws NpaTraqCodeMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		INpaTraqCodeMaster updated;
		try{
			workingCopy.setNpaTraqCode(imageCopy.getNpaTraqCode());
			workingCopy.setSecurityType(imageCopy.getSecurityType());
			workingCopy.setSecuritySubType(imageCopy.getSecuritySubType());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setPropertyTypeCodeDesc(imageCopy.getPropertyTypeCodeDesc());
			updated = updateNpaTraqCodeMaster(workingCopy);
			return updateNpaTraqCodeMaster(updated);
		}catch (Exception e) {
			throw new NpaTraqCodeMasterException("Error while Copying copy to main file");
		}
	}
	
	/**
	 * @return List of all authorized NpaTraqCodeMaster
	 */
	

	public SearchResult getAllNpaTraqCodeMaster()throws NpaTraqCodeMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getNpaTraqCodeMasterJdbc().getAllNpaTraqCodeMaster();
	}
	
	public boolean isNpaTraqCodeUniqueJdbc(String securityType, String securitySubType, String propertyTypeDesc) {
		return getNpaTraqCodeMasterJdbc().isNpaTraqCodeUniqueJdbc(securityType,securitySubType,propertyTypeDesc);
	}
	/**
	 * @return List of all authorized ProductMaster
	 */
	

	/*public SearchResult getAllFilteredProductMaster(String code,String name)throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getProductMasterJdbc().getAllFilteredProductMaster(code,name);
	}*/
}
