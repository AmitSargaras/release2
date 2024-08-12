package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerImpl extends AbstractBusManager implements IBusManager {

	public String getName() {
		return IBankingArrangementFacExclusionDao.ACTUAL_NAME;
	}
	
	public IBankingArrangementFacExclusion updateToWorkingCopy(IBankingArrangementFacExclusion workingCopy, IBankingArrangementFacExclusion imageCopy)
	throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IBankingArrangementFacExclusion updated;
		try{
			workingCopy.setSystem(imageCopy.getSystem());
			workingCopy.setFacCategory(imageCopy.getFacCategory());
			workingCopy.setFacName(imageCopy.getFacName());
			workingCopy.setExcluded(imageCopy.getExcluded());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			updated = update(workingCopy);
			return update(updated);
		}catch (Exception e) {
			throw new BankingArrangementFacExclusionException("Error while Copying copy to main file");
		}
	}
	
	public IBankingArrangementFacExclusion deleteToWorkingCopy(IBankingArrangementFacExclusion workingCopy, IBankingArrangementFacExclusion imageCopy)
			throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException,ConcurrentUpdateException {
				IBankingArrangementFacExclusion updated;
				try{
					workingCopy.setSystem(imageCopy.getSystem());
					workingCopy.setFacCategory(imageCopy.getFacCategory());
					workingCopy.setFacName(imageCopy.getFacName());
					workingCopy.setExcluded(imageCopy.getExcluded());
					workingCopy.setStatus(imageCopy.getStatus());
					workingCopy.setDeprecated(imageCopy.getDeprecated());
					workingCopy.setCreateBy(imageCopy.getCreateBy());
					workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
					updated = update(workingCopy);
					return delete(updated);
				}catch (Exception e) {
					throw new BankingArrangementFacExclusionException("Error while Copying copy to main file");
				}
			}
			
	public SearchResult getAll() throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getJdbc().getAll();
	}
	
}