package com.integrosys.cms.app.riskType.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class RiskTypeBusManagerStagingImpl extends AbstractRiskTypeBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging RiskType table  
     * 
     */
	
	public String getRiskTypeName() {
        return IRiskTypeDao.STAGE_RISK_TYPE_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IRiskType updateToWorkingCopy(IRiskType workingCopy, IRiskType imageCopy)
            throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualRiskType(String code,
			String name, String category, String type, String system,
			String line) throws RiskTypeException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}

	@Override
	public IRiskType deleteToWorkingCopy(IRiskType workingCopy,
			IRiskType imageCopy) throws RiskTypeException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		throw new IllegalStateException("'deleteToWorkingCopy' should not be implemented.");
	}
}
