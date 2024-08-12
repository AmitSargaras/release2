package com.integrosys.cms.app.excLineforstpsrm.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerImpl extends AbstractBusManager implements IBusManager {

	public String getName() {
		return IExcLineForSTPSRMDao.ACTUAL_NAME;
	}
	
	public IExcLineForSTPSRM updateToWorkingCopy(IExcLineForSTPSRM workingCopy, IExcLineForSTPSRM imageCopy)
	throws ExcLineForSTPSRMException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IExcLineForSTPSRM updated;
		try{
			workingCopy.setLineCode(imageCopy.getLineCode());
			workingCopy.setExcluded(imageCopy.getExcluded());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			updated = update(workingCopy);
			return update(updated);
		}catch (Exception e) {
			throw new ExcLineForSTPSRMException("Error while Copying copy to main file");
		}
	}
	
	public IExcLineForSTPSRM deleteToWorkingCopy(IExcLineForSTPSRM workingCopy, IExcLineForSTPSRM imageCopy)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException,ConcurrentUpdateException {
				IExcLineForSTPSRM updated;
				try{
					workingCopy.setLineCode(imageCopy.getLineCode());
					workingCopy.setExcluded(imageCopy.getExcluded());
					workingCopy.setStatus(imageCopy.getStatus());
					workingCopy.setDeprecated(imageCopy.getDeprecated());
					workingCopy.setCreateBy(imageCopy.getCreateBy());
					workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
					updated = update(workingCopy);
					return delete(updated);
				}catch (Exception e) {
					throw new ExcLineForSTPSRMException("Error while Copying copy to main file");
				}
			}
			
	public SearchResult getAll() throws ExcLineForSTPSRMException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getJdbc().getAll();
	}
	
}