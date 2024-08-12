package com.integrosys.cms.app.leiDateValidation.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class LeiDateValidationBusManagerImpl extends AbstractLeiDateValidationBusManager
		implements ILeiDateValidationBusManager {
	/**
	 * 
	 * This method give the entity name of staging LeiDateValidation table
	 * 
	 */

	public String getLeiDateValidationName() {
		return ILeiDateValidationDao.ACTUAL_LEI_DATE_VALIDATION;
	}

	/**
	 * @return WorkingCopy-- updated LeiDateValidation Object
	 * @param working
	 *            copy-- Entry of Actual Table
	 * @param image
	 *            Copy-- Entry Of Staging Table
	 * 
	 *            After Approval From Checker the Working Copy is updated as per the
	 *            image copy.
	 * 
	 */
	public ILeiDateValidation updateToWorkingCopy(ILeiDateValidation workingCopy, ILeiDateValidation imageCopy)
			throws LeiDateValidationException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		ILeiDateValidation updated;
		try {
			workingCopy.setPartyID(imageCopy.getPartyID());
			workingCopy.setPartyName(imageCopy.getPartyName());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setLeiDateValidationPeriod(imageCopy.getLeiDateValidationPeriod());
			updated = updateLeiDateValidation(workingCopy);
			return updateLeiDateValidation(updated);
		} catch (Exception e) {
			throw new LeiDateValidationException("Error while Copying copy to main file");
		}
	}

	/**
	 * @return List of all authorized LeiDateValidation
	 */

	public SearchResult getAllLeiDateValidation()
			throws LeiDateValidationException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getLeiDateValidationJdbc().getAllLeiDateValidation();
	}

	/**
	 * @return List of all authorized LeiDateValidation
	 */

	public SearchResult getAllFilteredLeiDateValidation(String code, String name)
			throws LeiDateValidationException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getLeiDateValidationJdbc().getAllFilteredLeiDateValidation(code, name);
	}
}
