package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;

public interface ICollateralRocBusManager {

	SearchResult getAllCollateralRoc()throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	ICollateralRoc createCollateralRoc(ICollateralRoc collateralRoc)throws CollateralRocException;
	ICollateralRoc getCollateralRocById(long id) throws CollateralRocException,TrxParameterException,TransactionException;
	ICollateralRoc updateCollateralRoc(ICollateralRoc item) throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	ICollateralRoc updateToWorkingCopy(ICollateralRoc workingCopy, ICollateralRoc imageCopy) throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode);
	
	SearchResult getSearchedCollateralRoc(String type,String text)throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
