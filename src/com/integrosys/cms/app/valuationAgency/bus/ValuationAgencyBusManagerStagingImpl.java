package com.integrosys.cms.app.valuationAgency.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ValuationAgencyBusManagerStagingImpl extends
		AbstractValuationAgencyBusManager {

	/**
	 * 
	 * This method give the entity name of staging ValuationAgency table
	 * 
	 */

	public String getValuationAgencyName() {
		return IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME;
	}

	

	

	public List getAllValuationAgency() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This method returns exception as staging ValuationAgency can never be
	 * working copy
	 */
	public IValuationAgency updateToWorkingCopy(IValuationAgency workingCopy,
			IValuationAgency imageCopy) throws ValuationAgencyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		throw new IllegalStateException(
				"'updateToWorkingCopy' should not be implemented.");
	}





	public List getFilteredValuationAgency() {
		// TODO Auto-generated method stub
		return null;
	}





	public List getFilteredValuationAgency(String code, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}