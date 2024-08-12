package com.integrosys.cms.app.udf.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class UdfBusManagerStagingImpl extends AbstractUdfBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Udf table  
     * 
     */
	
	public String getUdfName() {
        return IUdfDao.STAGE_UDF_NAME; 
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IUdf updateToWorkingCopy(IUdf workingCopy, IUdf imageCopy)
            throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualUdf(String code,String name, String category, String type, String system,
			String line) throws UdfException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}

	

	
}
