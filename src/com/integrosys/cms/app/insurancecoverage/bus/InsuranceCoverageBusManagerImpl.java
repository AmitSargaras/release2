package com.integrosys.cms.app.insurancecoverage.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * This InsuranceCoverageBusManagerImpl implements all the methods of  IInsuranceCoverageBusManager 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.2 $
 */

public class InsuranceCoverageBusManagerImpl extends AbstractInsuranceCoverageBusManager implements IInsuranceCoverageBusManager  {
	
	IInsuranceCoverageDAO insuranceCoverageDAO;

	/**
	 * @return the insuranceCoverageDAO
	 */
	public IInsuranceCoverageDAO getInsuranceCoverageDAO() {
		return insuranceCoverageDAO;
	}

	/**
	 * @param insuranceCoverageDAO the insuranceCoverageDAO to set
	 */
	public void setInsuranceCoverageDAO(IInsuranceCoverageDAO insuranceCoverageDAO) {
		this.insuranceCoverageDAO = insuranceCoverageDAO;
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverage() throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageDAO().getInsuranceCoverage();
	}

	/**
	 * @return the InsuranceCoverage Details
	 */
	public IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException {
		return (IInsuranceCoverage)getInsuranceCoverageDAO().getInsuranceCoverageById(id);
	}

	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageDAO().getInsuranceCoverageList(icCode,companyName);
	}
	
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageDAO().getInsuranceCoverageDtlsList(id);
	}
	
	/**
	 * @return the InsuranceCoverage Details
	 */
	public IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {
		return (IInsuranceCoverage)getInsuranceCoverageDAO().updateInsuranceCoverage(InsuranceCoverage);
	}
	
	/**
	 * @return the InsuranceCoverage Details
	 */
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {
		return (IInsuranceCoverage)getInsuranceCoverageDAO().deleteInsuranceCoverage(InsuranceCoverage);
	}
	
	/**
	 * @return the InsuranceCoverage Details
	 */
	public IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {
		return (IInsuranceCoverage)getInsuranceCoverageDAO().createInsuranceCoverage(InsuranceCoverage);
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

	public IInsuranceCoverage updateToWorkingCopy(IInsuranceCoverage workingCopy,IInsuranceCoverage imageCopy) throws InsuranceCoverageException,TrxParameterException, 
	TransactionException,ConcurrentUpdateException {
		IInsuranceCoverage updated;
		try {
			workingCopy.setInsuranceCoverageCode(imageCopy.getInsuranceCoverageCode());
			workingCopy.setCompanyName(imageCopy.getCompanyName());
			workingCopy.setAddress(imageCopy.getAddress());
			workingCopy.setContactNumber(imageCopy.getContactNumber());
			workingCopy.setStatus(imageCopy.getStatus());
			
			updated = updateInsuranceCoverage(workingCopy);
		} catch (InsuranceCoverageException e) {
			throw new InsuranceCoverageException(
					"Error while Copying copy to main file");
		}

		return updateInsuranceCoverage(updated);
	}
	
	public boolean isICCodeUnique(String rmCode){
		 return getInsuranceCoverageDAO().isICCodeUnique(rmCode);
	 }
	
	public String getInsuranceCoverageName(){
		return IInsuranceCoverageDAO.ACTUAL_ENTITY_NAME;
	}

	public boolean isCompanyNameUnique(String companyName) {
		return getInsuranceCoverageDAO().isCompanyNameUnique(companyName);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getInsuranceCoverageDAO().deleteTransaction(obFileMapperMaster);					
	}
}
