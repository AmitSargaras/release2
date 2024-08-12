package com.integrosys.cms.app.creditApproval.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author Govind.Sahu
 * @since 2011/04/06
 */
public abstract class AbstractCreditApprovalBusManager implements ICreditApprovalBusManager {
	public ICreditApprovalDao creditApprovalDao;


	
	/**
	 * @return the creditApprovalDao
	 */
	public ICreditApprovalDao getCreditApprovalDao() {
		return creditApprovalDao;
	}
	/**
	 * @param creditApprovalDao the creditApprovalDao to set
	 */
	public void setCreditApprovalDao(ICreditApprovalDao creditApprovalDao) {
		this.creditApprovalDao = creditApprovalDao;
	}
	/**
	 * Get CreditApproval List
	 * @return The CreditApproval.
	 */
	public List getCreditApprovalList() {
		
		return getCreditApprovalDao().getCreditApprovalList();
		
	}
	
	/**
	 * 
	 * @param The CreditApproval to update with.
	 * @return The updated CreditApproval.
	 * @throws CreditApprovalException when there are errors in updating CreditApproval
	 * add stg entity name.
	 * 
	 **/
	public ICreditApproval getCreditApprovalEntry(long id) throws CreditApprovalException {
		return getCreditApprovalDao().getCreditApprovalEntryByPrimaryKey(getCreditApprovalStagingEntityName(), new Long(id));
	}
	
	/**
	 * Updates the CreditApproval with the entries. This is a replacement
	 * action.
	 * @param The CreditApproval to update with.
	 * @return The updated CreditApproval.
	 * @throws CreditApprovalException when there are errors in updating CreditApproval
	 */
	public ICreditApproval getCreditApprovalEntry(String key) throws CreditApprovalException {
		return getCreditApprovalDao().getCreditApprovalEntryByEntryType(getCreditApprovalActualEntityName(), key);
	}



	
	/**
	 * Creates the CreditApproval with all the entries.
	 * @param group The CreditApproval to be created.
	 * @return The created CreditApproval.
	 * @throws CreditApprovalException
	 *        
	 */
	public ICreditApproval createCreditApproval(ICreditApproval creditApproval) throws CreditApprovalException {
		return getCreditApprovalDao().createCreditApproval(getCreditApprovalStagingEntityName(), creditApproval);
	}



	/**
	 * Updates the CreditApproval with the entries. This is a replacement
	 * action.
	 * @param  CreditApproval.
	 * @return The updated CreditApproval ob.
	 * @throws CreditApprovalException when there are errors in updating CreditApproval
	 */
	public ICreditApproval updateCreditApproval(ICreditApproval creditApproval) throws CreditApprovalException {
		return getCreditApprovalDao().updateCreditApproval(getCreditApprovalActualEntityName(), creditApproval);
	}
	
	/**
	 * Updates the CreditApproval with the entries. This is a replacement
	 * action.
	 * @param group The CreditApproval to update with.
	 * @return The updated CreditApproval.
	 * @throws CreditApprovalException when there are errors in updating the
	 *         group.
	 */
	public ICreditApproval updateStatusCreditApproval(ICreditApproval obj) throws CreditApprovalException {
		return getCreditApprovalDao().updateStatusCreditApproval(getCreditApprovalStagingEntityName(), obj);
	}
	
	/**
	 * @return List of all authorized Credit Approval according to Search Criteria provided.
	 * 
	 */

	public List getAllCreditApproval(String searchTxtApprovalCode, String searchTxtApprovalName)throws CreditApprovalException{

		return getCreditApprovalDao().getAllCreditApproval(getCreditApprovalActualEntityName(),searchTxtApprovalCode, searchTxtApprovalName);
	}
	
	/**
	*File Upload Methods
	**/
	
	public boolean isPrevFileUploadPending()
	throws CreditApprovalException {
		try {
			return getCreditApprovalDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CreditApprovalException("File is not in proper format");
		}
	}

	public int insertCreditApproval(IFileMapperMaster trans_Id, String userName, ArrayList result, long countryId)
	throws CreditApprovalException {
		try {
			return getCreditApprovalDao().insertCreditApproval(trans_Id, userName, result, countryId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CreditApprovalException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertCreditApproval(
			IFileMapperId fileId, ICreditApprovalTrxValue trxValue)
			throws CreditApprovalException {
		if (!(fileId == null)) {
			return getCreditApprovalDao().insertCreditApproval(getCreditApprovalFileMapperName(), fileId, trxValue);
		} else {
			throw new CreditApprovalException(
					"ERROR- CreditApproval object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws CreditApprovalException {
		if (!(fileId == null)) {
			return getCreditApprovalDao().createFileId(getCreditApprovalFileMapperName(), fileId);
		} else {
			throw new CreditApprovalException(
					"ERROR- CreditApproval object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws CreditApprovalException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCreditApprovalDao().getInsertFileList(
					getCreditApprovalFileMapperName(), new Long(id));
		} else {
			throw new CreditApprovalException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageCreditApproval(String searchBy, String login)throws CreditApprovalException,TrxParameterException,TransactionException {

		return getCreditApprovalDao().getAllStageCreditApproval(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws CreditApprovalException,TrxParameterException,TransactionException {

		return getCreditApprovalDao().getFileMasterList(searchBy);
	}
	
	
	public ICreditApproval insertActualCreditApproval(String sysId)
	throws CreditApprovalException {
		try {
			return getCreditApprovalDao().insertActualCreditApproval(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CreditApprovalException("File is not in proper format");
		}
	}
	
	public ICreditApproval insertCreditApproval(
			ICreditApproval creditApproval)
			throws CreditApprovalException {
		if (!(creditApproval == null)) {
			return getCreditApprovalDao().insertCreditApproval("actualCreditApproval", creditApproval);
		} else {
			throw new CreditApprovalException(
					"ERROR- Credit Approval object is null. ");
		}
	}
	
	/**
	 * Gets the CreditApproval list based on Approval code

	 * @return true if found record else false
	 * @throws CreditApprovalException on errors.
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode){
		return (boolean)getCreditApprovalDao().getCheckCreditApprovalUniquecode(getCreditApprovalActualEntityName(),appCode);
	}


	public abstract String getCreditApprovalStagingEntityName();

	public abstract String getCreditApprovalActualEntityName();
	
	public abstract String getCreditApprovalFileMapperName();

	public long getCountryIdForCountry(String countryName) {
		return getCreditApprovalDao().getCountryIdForCountry(countryName);
	}

	public boolean isCreditApprovalNameUnique(String creditApprovalName) {
		return getCreditApprovalDao().isCreditApprovalNameUnique(creditApprovalName);
	}
				
	public boolean isRegionCodeVaild(String regionCode, long countryId) {
		return getCreditApprovalDao().isRegionCodeVaild(regionCode, countryId);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getCreditApprovalDao().deleteTransaction(obFileMapperMaster);
	}
	
	public List getRegionList(String countryId) throws CreditApprovalException{
		return (List)getCreditApprovalDao().getRegionList(countryId);
	}
	
	
	public boolean isCreditEmployeeIdUnique(String employeeId) {
		return getCreditApprovalDao().isCreditEmployeeIdUnique(employeeId);
	}
}
