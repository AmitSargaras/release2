package com.integrosys.cms.app.holiday.bus;

import java.util.ArrayList;
import java.util.List;

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
 * @author  Abhijit R. 
 */
public interface IHolidayBusManager {
	

		List searchHoliday(String login) throws HolidayException,TrxParameterException,TransactionException;
		IHoliday getHolidayById(long id) throws HolidayException,TrxParameterException,TransactionException;
	
		SearchResult getAllHoliday()throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllHoliday(String searchBy,String searchText)throws HolidayException,TrxParameterException,TransactionException;
		IHoliday updateHoliday(IHoliday item) throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IHoliday deleteHoliday(IHoliday item) throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IHoliday updateToWorkingCopy(IHoliday workingCopy, IHoliday imageCopy) throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IHoliday createHoliday(IHoliday holiday)throws HolidayException;
		
		boolean isPrevFileUploadPending() throws HolidayException;
		int insertHoliday(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws HolidayException;
		IFileMapperId insertHoliday(IFileMapperId fileId, IHolidayTrxValue idxTrxValue)throws HolidayException;
		IFileMapperId createFileId(IFileMapperId obFileMapperID)throws HolidayException;
		IFileMapperId getInsertFileById(long id) throws HolidayException,TrxParameterException,TransactionException;
		List getAllStageHoliday(String searchBy, String login)throws HolidayException,TrxParameterException,TransactionException;
		List getFileMasterList(String searchBy)throws HolidayException,TrxParameterException,TransactionException;
		IHoliday insertActualHoliday(String sysId)throws HolidayException;
		IHoliday insertHoliday(IHoliday holiday)throws HolidayException;
		
		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
