package com.integrosys.cms.app.caseBranch.bus;

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
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of CaseBranch
 */
public abstract class AbstractCaseBranchBusManager implements
		ICaseBranchBusManager {

	private ICaseBranchDao caseBranchDao;

	private ICaseBranchJdbc caseBranchJdbc;
	
	
	public ICaseBranchDao getCaseBranchDao() {
		return caseBranchDao;
	}

	public void setCaseBranchDao(ICaseBranchDao caseBranchDao) {
		this.caseBranchDao = caseBranchDao;
	}

	public ICaseBranchJdbc getCaseBranchJdbc() {
		return caseBranchJdbc;
	}

	public void setCaseBranchJdbc(
			ICaseBranchJdbc caseBranchJdbc) {
		this.caseBranchJdbc = caseBranchJdbc;
	}
	public abstract String getCaseBranchName();
	
	/**
	  * @return Particular CaseBranch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ICaseBranch getCaseBranchById(long id)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCaseBranchDao().getCaseBranch(
					getCaseBranchName(), new Long(id));
		} else {
			throw new CaseBranchException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized CaseBranch according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllCaseBranch(String searchBy, String searchText)throws CaseBranchException,TrxParameterException,TransactionException {

		return getCaseBranchJdbc().getAllCaseBranch(searchBy,searchText);
	}
	/**
	 * @return List of all authorized CaseBranch
	 */

	public SearchResult getAllCaseBranch()throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCaseBranchJdbc().getAllCaseBranch();
	}
	/**
	 * @return List of all authorized CaseBranch according to Search Criteria provided.
	 * 
	 */
	public List searchCaseBranch(String login)throws CaseBranchException,TrxParameterException,TransactionException {

		return getCaseBranchJdbc().getAllCaseBranchSearch(login);
	}
	
	/**
	 @return CaseBranch Object after update
	 * 
	 */

	public ICaseBranch updateCaseBranch(ICaseBranch item)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		try {
			return getCaseBranchDao().updateCaseBranch(
					getCaseBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseBranchException("current CaseBranch");
		}
	}
	/**
	 @return CaseBranch Object after delete
	 * 
	 */
	public ICaseBranch deleteCaseBranch(ICaseBranch item)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		try {
			return getCaseBranchDao().deleteCaseBranch(
					getCaseBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseBranchException("current CaseBranch ");
		}
	}
	/**
	 @return CaseBranch Object after create
	 * 
	 */

	public ICaseBranch createCaseBranch(
			ICaseBranch caseBranch)
			throws CaseBranchException {
		if (!(caseBranch == null)) {
			return getCaseBranchDao().createCaseBranch(getCaseBranchName(), caseBranch);
		} else {
			throw new CaseBranchException(
					"ERROR- CaseBranch object   is null. ");
		}
	}


	public boolean isPrevFileUploadPending()
	throws CaseBranchException {
		try {
			return getCaseBranchDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseBranchException("File is not in proper format");
		}
	}

	public int insertCaseBranch(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws CaseBranchException {
		try {
			return getCaseBranchDao().insertCaseBranch(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseBranchException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertCaseBranch(
			IFileMapperId fileId, ICaseBranchTrxValue trxValue)
			throws CaseBranchException {
		if (!(fileId == null)) {
			return getCaseBranchDao().insertCaseBranch(getCaseBranchName(), fileId, trxValue);
		} else {
			throw new CaseBranchException(
					"ERROR- CaseBranch object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws CaseBranchException {
		if (!(fileId == null)) {
			return getCaseBranchDao().createFileId(getCaseBranchName(), fileId);
		} else {
			throw new CaseBranchException(
					"ERROR- CaseBranch object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCaseBranchDao().getInsertFileList(
					getCaseBranchName(), new Long(id));
		} else {
			throw new CaseBranchException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageCaseBranch(String searchBy, String login)throws CaseBranchException,TrxParameterException,TransactionException {

		return getCaseBranchDao().getAllStageCaseBranch(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws CaseBranchException,TrxParameterException,TransactionException {

		return getCaseBranchDao().getFileMasterList(searchBy);
	}
	
	
	public ICaseBranch insertActualCaseBranch(String sysId)
	throws CaseBranchException {
		try {
			return getCaseBranchDao().insertActualCaseBranch(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CaseBranchException("File is not in proper format");
		}
	}
	
	public ICaseBranch insertCaseBranch(
			ICaseBranch caseBranch)
			throws CaseBranchException {
		if (!(caseBranch == null)) {
			return getCaseBranchDao().insertCaseBranch("actualCaseBranch", caseBranch);
		} else {
			throw new CaseBranchException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCaseBranchDao().deleteTransaction(obFileMapperMaster);		
	}
	
	public SearchResult getAllFilteredCaseBranch(String code, String name)
			throws CaseBranchException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}

	
}