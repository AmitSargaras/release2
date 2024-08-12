package com.integrosys.cms.app.holiday.bus;

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
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of Holiday
 */
public abstract class AbstractHolidayBusManager implements
		IHolidayBusManager {

	private IHolidayDao holidayDao;

	private IHolidayJdbc holidayJdbc;
	
	
	public IHolidayDao getHolidayDao() {
		return holidayDao;
	}

	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	public IHolidayJdbc getHolidayJdbc() {
		return holidayJdbc;
	}

	public void setHolidayJdbc(
			IHolidayJdbc holidayJdbc) {
		this.holidayJdbc = holidayJdbc;
	}
	public abstract String getHolidayName();
	
	/**
	  * @return Particular Holiday according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IHoliday getHolidayById(long id)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getHolidayDao().getHoliday(
					getHolidayName(), new Long(id));
		} else {
			throw new HolidayException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized Holiday according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllHoliday(String searchBy, String searchText)throws HolidayException,TrxParameterException,TransactionException {

		return getHolidayJdbc().getAllHoliday(searchBy,searchText);
	}
	/**
	 * @return List of all authorized Holiday
	 */

	public SearchResult getAllHoliday()throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getHolidayJdbc().getAllHoliday();
	}
	/**
	 * @return List of all authorized Holiday according to Search Criteria provided.
	 * 
	 */
	public List searchHoliday(String login)throws HolidayException,TrxParameterException,TransactionException {

		return getHolidayJdbc().getAllHolidaySearch(login);
	}
	
	/**
	 @return Holiday Object after update
	 * 
	 */

	public IHoliday updateHoliday(IHoliday item)
			throws HolidayException, TrxParameterException,
			TransactionException {
		try {
			return getHolidayDao().updateHoliday(
					getHolidayName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new HolidayException("current Holiday");
		}
	}
	/**
	 @return Holiday Object after delete
	 * 
	 */
	public IHoliday deleteHoliday(IHoliday item)
			throws HolidayException, TrxParameterException,
			TransactionException {
		try {
			return getHolidayDao().deleteHoliday(
					getHolidayName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new HolidayException("current Holiday ");
		}
	}
	/**
	 @return Holiday Object after create
	 * 
	 */

	public IHoliday createHoliday(
			IHoliday holiday)
			throws HolidayException {
		if (!(holiday == null)) {
			return getHolidayDao().createHoliday(getHolidayName(), holiday);
		} else {
			throw new HolidayException(
					"ERROR- Holiday object   is null. ");
		}
	}


	public boolean isPrevFileUploadPending()
	throws HolidayException {
		try {
			return getHolidayDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new HolidayException("File is not in proper format");
		}
	}

	public int insertHoliday(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws HolidayException {
		try {
			return getHolidayDao().insertHoliday(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new HolidayException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertHoliday(
			IFileMapperId fileId, IHolidayTrxValue trxValue)
			throws HolidayException {
		if (!(fileId == null)) {
			return getHolidayDao().insertHoliday(getHolidayName(), fileId, trxValue);
		} else {
			throw new HolidayException(
					"ERROR- Holiday object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws HolidayException {
		if (!(fileId == null)) {
			return getHolidayDao().createFileId(getHolidayName(), fileId);
		} else {
			throw new HolidayException(
					"ERROR- Holiday object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getHolidayDao().getInsertFileList(
					getHolidayName(), new Long(id));
		} else {
			throw new HolidayException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageHoliday(String searchBy, String login)throws HolidayException,TrxParameterException,TransactionException {

		return getHolidayDao().getAllStageHoliday(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws HolidayException,TrxParameterException,TransactionException {

		return getHolidayDao().getFileMasterList(searchBy);
	}
	
	
	public IHoliday insertActualHoliday(String sysId)
	throws HolidayException {
		try {
			return getHolidayDao().insertActualHoliday(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new HolidayException("File is not in proper format");
		}
	}
	
	public IHoliday insertHoliday(
			IHoliday holiday)
			throws HolidayException {
		if (!(holiday == null)) {
			return getHolidayDao().insertHoliday("actualHoliday", holiday);
		} else {
			throw new HolidayException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getHolidayDao().deleteTransaction(obFileMapperMaster);		
	}
	
}