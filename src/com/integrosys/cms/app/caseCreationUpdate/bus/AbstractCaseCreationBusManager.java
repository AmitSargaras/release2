package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of CaseCreation
 */
public abstract class AbstractCaseCreationBusManager implements
		ICaseCreationBusManager {

	private ICaseCreationDao caseCreationUpdateDao;
	private ICaseCreationJdbc caseCreationUpdateJdbc;
	
	
	
	public ICaseCreationDao getCaseCreationUpdateDao() {
		return caseCreationUpdateDao;
	}

	public void setCaseCreationUpdateDao(ICaseCreationDao caseCreationUpdateDao) {
		this.caseCreationUpdateDao = caseCreationUpdateDao;
	}

	public ICaseCreationJdbc getCaseCreationUpdateJdbc() {
		return caseCreationUpdateJdbc;
	}

	public void setCaseCreationUpdateJdbc(ICaseCreationJdbc caseCreationUpdateJdbc) {
		this.caseCreationUpdateJdbc = caseCreationUpdateJdbc;
	}

	public abstract String getCaseCreationName();
	
	/**
	  * @return Particular CaseCreation according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ICaseCreation getCaseCreationById(long id)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCaseCreationUpdateDao().getCaseCreation(
					getCaseCreationName(), new Long(id));
		} else {
			throw new CaseCreationException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized CaseCreation according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllCaseCreation(String searchBy, String searchText)throws CaseCreationException,TrxParameterException,TransactionException {

		return getCaseCreationUpdateJdbc().getAllCaseCreation(searchBy,searchText);
	}
	/**
	 * @return List of all authorized CaseCreation
	 */

	public SearchResult getAllCaseCreation(long id)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCaseCreationUpdateJdbc().getAllCaseCreation(id);
	}
	
	public SearchResult getAllCaseCreation()throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCaseCreationUpdateJdbc().getAllCaseCreation();
	}
	
	
	public SearchResult getAllCaseCreationBranchMenu(String branchCode)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCaseCreationUpdateJdbc().getAllCaseCreationBranchMenu(branchCode);
	}
	/**
	 * @return List of all authorized CaseCreation according to Search Criteria provided.
	 * 
	 */
	public List searchCaseCreation(String login)throws CaseCreationException,TrxParameterException,TransactionException {

		return getCaseCreationUpdateJdbc().getAllCaseCreationSearch(login);
	}
	
	/**
	 @return CaseCreation Object after update
	 * 
	 */

	public ICaseCreation updateCaseCreation(ICaseCreation item)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		try {
			return getCaseCreationUpdateDao().updateCaseCreation(
					getCaseCreationName(), item);
			
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseCreationException("current CaseCreation");
		}
	}
	/**
	 @return CaseCreation Object after delete
	 * 
	 */
	public ICaseCreation deleteCaseCreation(ICaseCreation item)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		try {
			return getCaseCreationUpdateDao().deleteCaseCreation(
					getCaseCreationName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseCreationException("current CaseCreation ");
		}
	}
	/**
	 @return CaseCreation Object after create
	 * 
	 */

	public ICaseCreation createCaseCreation(
			ICaseCreation caseCreationUpdate)
			throws CaseCreationException {
		if (!(caseCreationUpdate == null)) {
			return getCaseCreationUpdateDao().createCaseCreation(getCaseCreationName(), caseCreationUpdate);
		} else {
			throw new CaseCreationException(
					"ERROR- CaseCreation object   is null. ");
		}
	}


	public boolean isPrevFileUploadPending()
	throws CaseCreationException {
		try {
			return getCaseCreationUpdateDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseCreationException("File is not in proper format");
		}
	}

	public int insertCaseCreation(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws CaseCreationException {
		try {
			return getCaseCreationUpdateDao().insertCaseCreation(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseCreationException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertCaseCreation(
			IFileMapperId fileId, ICaseCreationTrxValue trxValue)
			throws CaseCreationException {
		if (!(fileId == null)) {
			return getCaseCreationUpdateDao().insertCaseCreation(getCaseCreationName(), fileId, trxValue);
		} else {
			throw new CaseCreationException(
					"ERROR- CaseCreation object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws CaseCreationException {
		if (!(fileId == null)) {
			return getCaseCreationUpdateDao().createFileId(getCaseCreationName(), fileId);
		} else {
			throw new CaseCreationException(
					"ERROR- CaseCreation object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCaseCreationUpdateDao().getInsertFileList(
					getCaseCreationName(), new Long(id));
		} else {
			throw new CaseCreationException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageCaseCreation(String searchBy, String login)throws CaseCreationException,TrxParameterException,TransactionException {

		return getCaseCreationUpdateDao().getAllStageCaseCreation(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws CaseCreationException,TrxParameterException,TransactionException {

		return getCaseCreationUpdateDao().getFileMasterList(searchBy);
	}
	
	
	public ICaseCreation insertActualCaseCreation(String sysId)
	throws CaseCreationException {
		try {
			return getCaseCreationUpdateDao().insertActualCaseCreation(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseCreationException("File is not in proper format");
		}
	}
	
	public ICaseCreation insertCaseCreation(
			ICaseCreation caseCreationUpdate)
			throws CaseCreationException {
		if (!(caseCreationUpdate == null)) {
			return getCaseCreationUpdateDao().insertCaseCreation("actualCaseCreation", caseCreationUpdate);
		} else {
			throw new CaseCreationException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCaseCreationUpdateDao().deleteTransaction(obFileMapperMaster);		
	}
	public List getCaseCreationByBranchCode(String branchCode) {
		
		return getCaseCreationUpdateDao().getCaseCreationByBranchCode(branchCode); 
	}
}