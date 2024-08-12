package com.integrosys.cms.app.holiday.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class HolidayBusManagerImpl extends AbstractHolidayBusManager implements IHolidayBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Holiday table  
     * 
     */
	
	
	public String getHolidayName() {
		return IHolidayDao.ACTUAL_HOLIDAY_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return Holiday Object
	 * 
	 */
	

	public IHoliday getSystemBankById(long id) throws HolidayException,TrxParameterException,TransactionException {
		
		return getHolidayDao().load( getHolidayName(), id);
	}

	/**
	 * @return WorkingCopy-- updated Holiday Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IHoliday updateToWorkingCopy(IHoliday workingCopy, IHoliday imageCopy)
	throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IHoliday updated;
		try{
			workingCopy.setDescription(imageCopy.getDescription());
			workingCopy.setStartDate(imageCopy.getStartDate());
			workingCopy.setEndDate(imageCopy.getEndDate());
			workingCopy.setIsRecurrent(imageCopy.getIsRecurrent());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateHoliday(workingCopy);
			return updateHoliday(updated);
		}catch (Exception e) {
			throw new HolidayException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized Holiday
	 */
	

	public SearchResult getAllHoliday()throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getHolidayJdbc().getAllHolidayForYear();
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getHolidayDao().deleteTransaction(obFileMapperMaster);		
	}
}
