package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of System Bank Branch
 */
public abstract class AbstractSystemBankBranchBusManager implements
		ISystemBankBranchBusManager {

	private ISystemBankBranchDao systemBankBranchDao;

	private ISystemBankBranchJdbc systemBankBranchJdbc;
	
	
	public ISystemBankBranchDao getSystemBankBranchDao() {
		return systemBankBranchDao;
	}

	public void setSystemBankBranchDao(ISystemBankBranchDao systemBankBranchDao) {
		this.systemBankBranchDao = systemBankBranchDao;
	}

	public ISystemBankBranchJdbc getSystemBankBranchJdbc() {
		return systemBankBranchJdbc;
	}

	public void setSystemBankBranchJdbc(
			ISystemBankBranchJdbc systemBankBranchJdbc) {
		this.systemBankBranchJdbc = systemBankBranchJdbc;
	}
	public abstract String getSystemBankBranchName();
	
	/**
	  * @return Particular System Bank Branch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ISystemBankBranch getSystemBankBranchById(long id)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getSystemBankBranchDao().getSystemBankBranch(
					getSystemBankBranchName(), new Long(id));
		} else {
			throw new SystemBankException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized System Bank Branch according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllSystemBankBranch(String searchBy, String searchText)throws SystemBankBranchException,TrxParameterException,TransactionException {

		return getSystemBankBranchJdbc().getAllSystemBankBranch(searchBy,
				searchText);
	}
	/**
	 * @return List of all authorized System Bank Branch
	 */

	public SearchResult getAllSystemBankBranch()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getSystemBankBranchDao().getAllSystemBankBranch();
	}
	/**
	 * @return List of all authorized System Bank Branch according to Search Criteria provided.
	 * 
	 */
	public SearchResult searchBranch(String login)throws SystemBankBranchException,TrxParameterException,TransactionException {

		return getSystemBankBranchJdbc().getAllSystemBankBranchSearch(login);
	}
	
	/**
	 @return SystemBankBranch Object after update
	 * 
	 */

	public ISystemBankBranch updateSystemBankBranch(ISystemBankBranch item)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		try {
			return getSystemBankBranchDao().updateSystemBankBranch(
					getSystemBankBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankBranchException("current SystemBankBranch ["
					+ item + "] was updated before by ["
					+ item.getSystemBankBranchCode() + "] at ["
					+ item.getSystemBankBranchName() + "]");
		}
	}
	/**
	 @return SystemBankBranch Object after delete
	 * 
	 */
	public ISystemBankBranch deleteSystemBankBranch(ISystemBankBranch item)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		try {
			return getSystemBankBranchDao().deleteSystemBankBranch(
					getSystemBankBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankBranchException("current SystemBankBranch ["
					+ item + "] was updated before by ["
					+ item.getSystemBankBranchCode() + "] at ["
					+ item.getSystemBankBranchName() + "]");
		}
	}
	/**
	 @return SystemBankBranch Object after create
	 * 
	 */

	public ISystemBankBranch createSystemBankBranch(
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException {
		if (!(systemBankBranch == null)) {
			return getSystemBankBranchDao().createSystemBankBranch(getSystemBankBranchName(), systemBankBranch);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object   is null. ");
		}
	}
	
	/**
	 @return Validate System branch Code
	 * 
	 */

	public boolean isUniqueCode(String coloumn ,
			String branchCode)
			throws SystemBankBranchException {
		if (!(branchCode == null)) {
			return getSystemBankBranchDao().isUniqueCode(coloumn,branchCode);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object   is null. ");
		}
	}

	/**
	 * @return List of all authorized System Bank Branch
	 */
	

	public List getAllHUBBranchId()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getSystemBankBranchJdbc().getAllHUBBranchId();
	}
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	

	public List getAllHUBBranchValue()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getSystemBankBranchJdbc().getAllHUBBranchValue();
	}
	/**
	 * @return List of all authorized System Bank Branch according to Search Criteria provided.
	 * 
	 */

	public List getFileMasterList(String searchBy)throws SystemBankBranchException,TrxParameterException,TransactionException {

		return getSystemBankBranchDao().getFileMasterList(searchBy);
	}
	
	public List getAllStageSystemBankBranch(String searchBy, String login)throws SystemBankBranchException,TrxParameterException,TransactionException {

		return getSystemBankBranchDao().getAllStageSystemBankBranch(searchBy, login);
	}

	/**
	 @return SystemBankBranch Object after create
	 * 
	 */

	public IFileMapperId insertSystemBankBranch(
			IFileMapperId fileId, ISystemBankBranchTrxValue trxValue)
			throws SystemBankBranchException {
		if (!(fileId == null)) {
			return getSystemBankBranchDao().insertSystemBankBranch(getSystemBankBranchName(), fileId, trxValue);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object is null. ");
		}
	}


	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws SystemBankBranchException {
		if (!(fileId == null)) {
			return getSystemBankBranchDao().createFileId(getSystemBankBranchName(), fileId);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object   is null. ");
		}
	}

	/**
	  * @return Particular System Bank Branch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IFileMapperId getInsertFileById(long id)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getSystemBankBranchDao().getInsertFileList(
					getSystemBankBranchName(), new Long(id));
		} else {
			throw new SystemBankException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	 @return SystemBankBranch insertSystemBankBranch
	 * 
	 */
	public int insertSystemBankBranch(IFileMapperMaster trans_Id, String userName, ArrayList result)
			throws SystemBankBranchException {
		try {
			return getSystemBankBranchDao().insertSystemBankBranch(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankBranchException("File is not in proper format");
		}
	}
	/**
	 @return SystemBankBranch insertSystemBankBranch
	 * 
	 */
	public ISystemBankBranch insertActualSystemBankBranch(String sysId)
			throws SystemBankBranchException {
		try {
			return getSystemBankBranchDao().insertActualSystemBankBranch(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankBranchException("File is not in proper format");
		}
	}
	
	/**
	 @return SystemBankBranch Object after create
	 * 
	 */

	public ISystemBankBranch insertSystemBankBranch(
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException {
		if (!(systemBankBranch == null)) {
			return getSystemBankBranchDao().insertSystemBankBranch("actualSystemBankBranch", systemBankBranch);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public boolean isPrevFileUploadPending()
	throws SystemBankBranchException {
		try {
			return getSystemBankBranchDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SystemBankBranchException("File is not in proper format");
		}
	}
	
	public SearchResult getAllSystemBankBranchForHUB()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getSystemBankBranchDao().getAllSystemBankBranchForHUB();
	}
	
	
	 public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) throws SystemBankException{
		return getSystemBankBranchDao().getSystemBranchList(branchCode, branchName,state,city);
	}
	 
	 public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode)throws SystemBankBranchException, TrxParameterException,TransactionException {
		return getSystemBankBranchDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
	 
	 public boolean isHubValid(String linkedHub) {
		return getSystemBankBranchDao().isHubValid(linkedHub);
	}
	 
	 public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getSystemBankBranchDao().deleteTransaction(obFileMapperMaster);
	}
}