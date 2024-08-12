package com.integrosys.cms.app.rbicategory.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author Govind.Sahu
 * Abstract Bus Manager of Rbi Category
 */
public abstract class AbstractRbiCategoryBusManager implements IRbiCategoryBusManager {

	private IRbiCategoryDao rbiCategoryDao;
	
	
	/**
	 * @return the rbiCategoryDao
	 */
	public IRbiCategoryDao getRbiCategoryDao() {
		return rbiCategoryDao;
	}

	/**
	 * @param rbiCategoryDao the rbiCategoryDao to set
	 */
	public void setRbiCategoryDao(IRbiCategoryDao rbiCategoryDao) {
		this.rbiCategoryDao = rbiCategoryDao;
	}


	public abstract String getRbiCategoryName();
	
	/**
	  * @return Particular Rbi Category according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IRbiCategory getRbiCategoryById(long id)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getRbiCategoryDao().getRbiCategory(
					getRbiCategoryName(), new Long(id));
		} else {
			throw new RbiCategoryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	 * @return List of all authorized  Rbi Category list
	 */

	public List getAllRbiCategoryList()throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {

			return getRbiCategoryDao().getAllRbiCategoryList(getRbiCategoryName());
		}
	public List getRbiIndCodeByNameList(String indName)throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getRbiCategoryDao().getRbiIndCodeByNameList(indName,getRbiCategoryName());
	}
	
	/**
	 @return RbiCategory Object after update
	 * 
	 */

	public IRbiCategory updateRbiCategory(IRbiCategory item)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		try {
			return getRbiCategoryDao().updateRbiCategory(getRbiCategoryName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RbiCategoryException("current rbi Category ["
					+ item + "] was updated before by ["
					+ item.getIndustryNameId() + "] at ["
					+ item.getId() + "]");
		}
	}
	/**
	 @return RbiCategory Object after delete
	 * 
	 */
	public IRbiCategory deleteRbiCategory(IRbiCategory item)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		try {
			return getRbiCategoryDao().deleteRbiCategory(
					getRbiCategoryName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RbiCategoryException("current rbi category ["
					+ item + "] was updated before by ["
					+ item.getIndustryNameId() + "] at ["
					+ item.getId() + "]");
		}
	}
	/**
	 @return RbiCategory Object after create
	 * 
	 */

	public IRbiCategory createRbiCategory(IRbiCategory rbiCategory)throws RbiCategoryException {
		if (!(rbiCategory == null)) {
			return getRbiCategoryDao().createRbiCategory(getRbiCategoryName(), rbiCategory);
		} else {
			throw new RbiCategoryException(
					"ERROR- Rbi Category object   is null. ");
		}
	}

	public boolean getActualRbiCategory(IRbiCategory rbiCategory)
	throws RbiCategoryException {
		return getRbiCategoryDao().getActualRbiCategory(getRbiCategoryName(),rbiCategory);

	}
	
	/*
	 *  This method return true if Industry Name already approve else return false.
	 */
	public boolean isIndustryNameApprove(String industryNameId) {
		return getRbiCategoryDao().isIndustryNameApprove(getRbiCategoryName(),industryNameId);
	}
	
	/*
	 *  This method return true if Rbi Code Category already assign to Industry else return false.
	 */
	public List isRbiCodeCategoryApprove(OBRbiCategory stgObRbiCategory,boolean isEdit,OBRbiCategory actObRbiCategory) {
		return getRbiCategoryDao().isRbiCodeCategoryApprove(getRbiCategoryName(), stgObRbiCategory, isEdit, actObRbiCategory);
	}
	

}