package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerStagingImpl extends AbstractBusManager {

	public String getName() {
        return ILimitsOfAuthorityMasterDao.STAGE_NAME;
    }
	
    public ILimitsOfAuthorityMaster updateToWorkingCopy(ILimitsOfAuthorityMaster workingCopy, ILimitsOfAuthorityMaster imageCopy)
            throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public ILimitsOfAuthorityMaster deleteToWorkingCopy(ILimitsOfAuthorityMaster workingCopy,
			ILimitsOfAuthorityMaster imageCopy) throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		throw new IllegalStateException("'deleteToWorkingCopy' should not be implemented.");
	}
}
