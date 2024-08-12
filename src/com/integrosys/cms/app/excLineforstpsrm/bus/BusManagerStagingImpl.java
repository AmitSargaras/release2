package com.integrosys.cms.app.excLineforstpsrm.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerStagingImpl extends AbstractBusManager {

	public String getName() {
        return IExcLineForSTPSRMDao.STAGE_NAME;
    }
	
    public IExcLineForSTPSRM updateToWorkingCopy(IExcLineForSTPSRM workingCopy, IExcLineForSTPSRM imageCopy)
            throws ExcLineForSTPSRMException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	@Override
	public IExcLineForSTPSRM deleteToWorkingCopy(IExcLineForSTPSRM workingCopy,
			IExcLineForSTPSRM imageCopy) throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		throw new IllegalStateException("'deleteToWorkingCopy' should not be implemented.");
	}
}
