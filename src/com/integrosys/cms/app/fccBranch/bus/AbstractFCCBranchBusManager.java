package com.integrosys.cms.app.fccBranch.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of FCCBranch
 */
public abstract class AbstractFCCBranchBusManager implements
		IFCCBranchBusManager {

	private IFCCBranchDao fccBranchDao;

	private IFCCBranchJdbc fccBranchJdbc;
	
	
	

	/**
	 * @return the fccBranchDao
	 */
	public IFCCBranchDao getFccBranchDao() {
		return fccBranchDao;
	}

	/**
	 * @param fccBranchDao the fccBranchDao to set
	 */
	public void setFccBranchDao(IFCCBranchDao fccBranchDao) {
		this.fccBranchDao = fccBranchDao;
	}

	
	/**
	 * @return the fccBranchJdbc
	 */
	public IFCCBranchJdbc getFccBranchJdbc() {
		return fccBranchJdbc;
	}

	/**
	 * @param fccBranchJdbc the fccBranchJdbc to set
	 */
	public void setFccBranchJdbc(IFCCBranchJdbc fccBranchJdbc) {
		this.fccBranchJdbc = fccBranchJdbc;
	}

	public abstract String getBranchName();
	
	/**
	  * @return Particular FCCBranch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IFCCBranch getFCCBranchById(long id)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getFccBranchDao().getFCCBranch(
					getBranchName(), new Long(id));
		} else {
			throw new FCCBranchException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized FCCBranch according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllFCCBranch(String searchBy, String searchText)throws FCCBranchException,TrxParameterException,TransactionException {

		return getFccBranchJdbc().getAllFCCBranch(searchBy, searchText);
	}
	/**
	 * @return List of all authorized FCCBranch
	 */

	public SearchResult getAllFCCBranch()throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getFccBranchJdbc().getAllFCCBranch();
	}
	
	/**
	 @return FCCBranch Object after update
	 * 
	 */

	public IFCCBranch updateFCCBranch(IFCCBranch item)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		try {
			return getFccBranchDao().updateFCCBranch(
					getBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FCCBranchException("current FCCBranch");
		}
	}
	/**
	 @return FCCBranch Object after delete
	 * 
	 */
	public IFCCBranch deleteFCCBranch(IFCCBranch item)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		try {
			return getFccBranchDao().deleteFCCBranch(
					getBranchName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FCCBranchException("current FCCBranch ");
		}
	}
	/**
	 @return FCCBranch Object after create
	 * 
	 */

	public IFCCBranch createFCCBranch(
			IFCCBranch fccBranch)
			throws FCCBranchException {
		if (!(fccBranch == null)) {
			return getFccBranchDao().createFCCBranch(getBranchName(), fccBranch);
		} else {
			throw new FCCBranchException(
					"ERROR- FCCBranch object   is null. ");
		}
	}


	public boolean isPrevFileUploadPending()
	throws FCCBranchException {
		try {
			return getFccBranchDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FCCBranchException("File is not in proper format");
		}
	}

	public int insertFCCBranch(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws FCCBranchException {
		try {
			return getFccBranchDao().insertFCCBranch(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FCCBranchException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertFCCBranch(
			IFileMapperId fileId, IFCCBranchTrxValue trxValue)
			throws FCCBranchException {
		if (!(fileId == null)) {
			return getFccBranchDao().insertFCCBranch(getBranchName(), fileId, trxValue);
		} else {
			throw new FCCBranchException(
					"ERROR- FCCBranch object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws FCCBranchException {
		if (!(fileId == null)) {
			return getFccBranchDao().createFileId(getBranchName(), fileId);
		} else {
			throw new FCCBranchException(
					"ERROR- FCCBranch object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getFccBranchDao().getInsertFileList(
					getBranchName(), new Long(id));
		} else {
			throw new FCCBranchException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageFCCBranch(String searchBy, String login)throws FCCBranchException,TrxParameterException,TransactionException {

		return getFccBranchDao().getAllStageFCCBranch(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws FCCBranchException,TrxParameterException,TransactionException {

		return getFccBranchDao().getFileMasterList(searchBy);
	}
	
	
	public IFCCBranch insertActualFCCBranch(String sysId)
	throws FCCBranchException {
		try {
			return getFccBranchDao().insertActualFCCBranch(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FCCBranchException("File is not in proper format");
		}
	}
	
	public IFCCBranch insertFCCBranch(
			IFCCBranch fccBranch)
			throws FCCBranchException {
		if (!(fccBranch == null)) {
			return getFccBranchDao().insertFCCBranch("actualFCCBranch", fccBranch);
		} else {
			throw new FCCBranchException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getFccBranchDao().deleteTransaction(obFileMapperMaster);		
	}
	
	public SearchResult getAllFilteredFCCBranch(String code, String name)
			throws FCCBranchException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException {
		return getFccBranchDao().fccBranchUniqueCombination(branchCode,aliasBranchCode,id);
	}

	
}