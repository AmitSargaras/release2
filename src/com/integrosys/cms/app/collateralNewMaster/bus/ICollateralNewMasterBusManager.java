package com.integrosys.cms.app.collateralNewMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
 * @author  Abhijit R. 
 */
public interface ICollateralNewMasterBusManager {
	

		List searchCollateralNewMaster(String login) throws CollateralNewMasterException,TrxParameterException,TransactionException;
		ICollateralNewMaster getCollateralNewMasterById(long id) throws CollateralNewMasterException,TrxParameterException,TransactionException;
	
		SearchResult getAllCollateralNewMaster()throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getFilteredCollateral(String collateralCode,String collateralDescription,String collateralMainType,String collateralSubType)throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllCollateralNewMaster(String searchBy,String searchText)throws CollateralNewMasterException,TrxParameterException,TransactionException;
		ICollateralNewMaster updateCollateralNewMaster(ICollateralNewMaster item) throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICollateralNewMaster deleteCollateralNewMaster(ICollateralNewMaster item) throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICollateralNewMaster updateToWorkingCopy(ICollateralNewMaster workingCopy, ICollateralNewMaster imageCopy) throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICollateralNewMaster createCollateralNewMaster(ICollateralNewMaster collateralNewMaster)throws CollateralNewMasterException;

		public boolean isCollateraNameUnique(String collateralName);
		public boolean isDuplicateRecord(String cpsId);
		
	
}
