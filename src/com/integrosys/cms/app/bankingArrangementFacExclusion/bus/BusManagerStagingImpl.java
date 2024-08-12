package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerStagingImpl extends AbstractBusManager {

	public String getName() {
        return IBankingArrangementFacExclusionDao.STAGE_NAME;
    }
	
    public IBankingArrangementFacExclusion updateToWorkingCopy(IBankingArrangementFacExclusion workingCopy, IBankingArrangementFacExclusion imageCopy)
            throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	@Override
	public IBankingArrangementFacExclusion deleteToWorkingCopy(IBankingArrangementFacExclusion workingCopy,
			IBankingArrangementFacExclusion imageCopy) throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		throw new IllegalStateException("'deleteToWorkingCopy' should not be implemented.");
	}
}
