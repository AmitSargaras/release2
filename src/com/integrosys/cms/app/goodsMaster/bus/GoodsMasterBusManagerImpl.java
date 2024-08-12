package com.integrosys.cms.app.goodsMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class GoodsMasterBusManagerImpl extends AbstractGoodsMasterBusManager implements IGoodsMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging GoodsMaster table  
     * 
     */
	
	
	public String getGoodsMasterName() {
		return IGoodsMasterDao.ACTUAL_GOODS_MASTER_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated GoodsMaster Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IGoodsMaster updateToWorkingCopy(IGoodsMaster workingCopy, IGoodsMaster imageCopy)
	throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IGoodsMaster updated;
		try{
			workingCopy.setGoodsParentCode(imageCopy.getGoodsParentCode());
			workingCopy.setGoodsCode(imageCopy.getGoodsCode());
			workingCopy.setGoodsName(imageCopy.getGoodsName());
			workingCopy.setRestrictionType(imageCopy.getRestrictionType());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			updated = updateGoodsMaster(workingCopy);
			return updateGoodsMaster(updated);
		}catch (Exception e) {
			throw new GoodsMasterException("Error while Copying copy to main file");
		}
	}
	
	/**
	 * @return List of all authorized GoodsMaster
	 */
	

	public SearchResult getAllGoodsMaster()throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getGoodsMasterJdbc().getAllGoodsMaster();
	}
	
	/**
	 * @return List of all authorized GoodsMaster
	 */
	

	public SearchResult getAllFilteredGoodsMaster(String code,String name)throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getGoodsMasterJdbc().getAllFilteredGoodsMaster(code,name);
	}
}
