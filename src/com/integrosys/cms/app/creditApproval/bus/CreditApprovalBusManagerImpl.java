
package com.integrosys.cms.app.creditApproval.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author govind.sahu
 * @since 2011/04/07
 */
public class CreditApprovalBusManagerImpl extends AbstractCreditApprovalBusManager {

	public String getCreditApprovalActualEntityName() {
		return ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME;
	}

	public String getCreditApprovalStagingEntityName() {
		return ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME;
	}
	
	
	
	/**
	 * Gets the CreditApproval list based on Approval code

	 * @return true if found record else false
	 * @throws CreditApprovalException on errors.
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode){
		return (boolean)getCreditApprovalDao().getCheckCreditApprovalUniquecode(getCreditApprovalActualEntityName(),appCode);
	}
	
	/**
	 * Gets the CreditApproval list

	 * @return The CreditApproval  having CreditApproval details
	 * @throws CreditApprovalException on errors.
	 */
	public List getCreditApprovalList() throws CreditApprovalException {
		return getCreditApprovalDao().getCreditApprovalList();
	}
	

	/**
	 * Gets the CreditApproval   
	 * @return The CreditApproval having the CreditApproval
	 * @throws CreditApprovalException on errors.
	 */
	public ICreditApproval createCreditApproval(ICreditApproval creditApprovalEntry) throws CreditApprovalException {
		creditApprovalEntry = getCreditApprovalDao().createCreditApproval(getCreditApprovalActualEntityName(), creditApprovalEntry);
		return getCreditApprovalDao().updateCreditApproval(getCreditApprovalActualEntityName(), creditApprovalEntry);
	
	}
	
	/**
	 * Gets the CreditApproval   
	 * @return The CreditApproval having the CreditApproval 
	 * @throws CreditApprovalException on errors.
	 */
	public ICreditApproval updateStatusCreditApproval(ICreditApproval creditApprovalEntry) throws CreditApprovalException {
		creditApprovalEntry = getCreditApprovalDao().updateStatusCreditApproval(getCreditApprovalActualEntityName(), creditApprovalEntry);
		return (ICreditApproval)creditApprovalEntry;
	
	}


	/**
	 * @return WorkingCopy-- updated system Bank Branch Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public ICreditApproval updateToWorkingCopy(ICreditApproval workingCopy, ICreditApproval CreditAppCopy)
	throws CreditApprovalException {
		ICreditApproval updated;
		try{
			long workingId;
			workingId = workingCopy.getId();
			workingCopy = CreditApprovalReplicationUtils.replicateCreditApprovalForCreateStagingCopy(CreditAppCopy);
			workingCopy.setId(workingId);
			updated = updateCreditApproval(workingCopy);
			return updateCreditApproval(updated);
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreditApprovalException("Error while Updating working copy to main file "+e.getMessage());
		}


	}

	public String getCreditApprovalFileMapperName() {
        return ICreditApprovalDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	public long getCountryIdForCountry(String countryName) {
		return getCreditApprovalDao().getCountryIdForCountry(countryName);
	}

	public boolean isCreditApprovalNameUnique(String creditApprovalName) {
		return getCreditApprovalDao().isCreditApprovalNameUnique(creditApprovalName);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getCreditApprovalDao().deleteTransaction(obFileMapperMaster);
	}

	public boolean isRegionCodeVaild(String regionCode, long countryId) {
		return getCreditApprovalDao().isRegionCodeVaild(regionCode, countryId);
	}
	
	public List getRegionList(String countryId){
		return (List)getCreditApprovalDao().getRegionList(countryId);
	}
	
	
	public boolean isCreditEmployeeIdUnique(String employeeId) {
		return getCreditApprovalDao().isCreditEmployeeIdUnique(employeeId);
	}
}
