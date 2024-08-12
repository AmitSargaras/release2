package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class FileMapperIdBusManagerStagingImpl extends	AbstractTatMasterBusManager{
	
	public String getTatMasterName() {
		return ITatMasterDao.STAGE_FILE_MAPPER_ID;
	}
	
	public INewTatMaster updateToWorkingCopy(INewTatMaster workingCopy, INewTatMaster imageCopy)
    throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
}

	
	

}
