package com.integrosys.cms.app.insurancecoveragedtls.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * This InsuranceCoverageBusManagerImpl implements all the methods of  IInsuranceCoverageBusManager 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.2 $
 */

public class InsuranceCoverageDtlsBusManagerImpl implements IInsuranceCoverageDtlsBusManager  {
	
	IInsuranceCoverageDtlsDAO insuranceCoverageDtlsDAO;

	/**
	 * @return the insuranceCoverageDtlsDAO
	 */
	public IInsuranceCoverageDtlsDAO getInsuranceCoverageDtlsDAO() {
		return insuranceCoverageDtlsDAO;
	}

	/**
	 * @param insuranceCoverageDtlsDAO the insuranceCoverageDtlsDAO to set
	 */
	public void setInsuranceCoverageDtlsDAO(
			IInsuranceCoverageDtlsDAO insuranceCoverageDtlsDAO) {
		this.insuranceCoverageDtlsDAO = insuranceCoverageDtlsDAO;
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtls() throws InsuranceCoverageDtlsException{
		return (SearchResult)getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtls();
	}

	/**
	 * @return the InsuranceCoverageDtls Details
	 */
	public IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException {
		return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtlsById(id);
	}

	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException{
		return (SearchResult)getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtlsList();
	}

	
	/**
	 * @return the InsuranceCoverageDtls Details
	 */
	public IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {
		return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsDAO().updateInsuranceCoverageDtls(InsuranceCoverageDtls);
	}
	
	/**
	 * @return the InsuranceCoverageDtls Details
	 */
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {
		return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsDAO().deleteInsuranceCoverageDtls(InsuranceCoverageDtls);
	}
	
	/**
	 * @return the InsuranceCoverageDtls Details
	 */
	public IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {
		return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsDAO().createInsuranceCoverageDtls(InsuranceCoverageDtls);
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

	public IInsuranceCoverageDtls updateToWorkingCopy(IInsuranceCoverageDtls workingCopy,IInsuranceCoverageDtls imageCopy) throws InsuranceCoverageDtlsException,TrxParameterException, 
	TransactionException,ConcurrentUpdateException {
		IInsuranceCoverageDtls updated;
		try {
			workingCopy.setInsuranceCoverageCode(imageCopy.getInsuranceCoverageCode());
			workingCopy.setInsuranceType(imageCopy.getInsuranceType());
			workingCopy.setInsuranceCategoryName(imageCopy.getInsuranceCategoryName());
			
			updated = updateInsuranceCoverageDtls(workingCopy);
		} catch (InsuranceCoverageDtlsException e) {
			throw new InsuranceCoverageDtlsException(
					"Error while Copying copy to main file");
		}

		return updateInsuranceCoverageDtls(updated);
	}
	
	public boolean isICCodeUnique(String rmCode){
		 return getInsuranceCoverageDtlsDAO().isICCodeUnique(rmCode);
	 }
}
