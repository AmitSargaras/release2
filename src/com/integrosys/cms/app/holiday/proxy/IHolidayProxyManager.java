package com.integrosys.cms.app.holiday.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IHolidayProxyManager {

	public List searchHoliday(String login) throws HolidayException,TrxParameterException,TransactionException;
	public SearchResult getAllActualHoliday() throws HolidayException,TrxParameterException,TransactionException;
	public SearchResult getAllActual(String searchBy,String searchText) throws HolidayException,TrxParameterException,TransactionException;
	public IHoliday deleteHoliday(IHoliday holiday) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerCloseRejectedHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerCloseDraftHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public IHoliday getHolidayById(long id) throws HolidayException,TrxParameterException,TransactionException ;
	public IHoliday updateHoliday(IHoliday holiday) throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IHolidayTrxValue makerDeleteHoliday(ITrxContext anITrxContext, IHolidayTrxValue anICCHolidayTrxValue, IHoliday anICCHoliday)throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerUpdateHoliday(ITrxContext anITrxContext, IHolidayTrxValue anICCHolidayTrxValue, IHoliday anICCHoliday)
	throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerUpdateSaveUpdateHoliday(ITrxContext anITrxContext, IHolidayTrxValue anICCHolidayTrxValue, IHoliday anICCHoliday)
	throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerUpdateSaveCreateHoliday(ITrxContext anITrxContext, IHolidayTrxValue anICCHolidayTrxValue, IHoliday anICCHoliday)
	throws HolidayException,TrxParameterException,TransactionException;
	
	public IHolidayTrxValue makerEditRejectedHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue, IHoliday anHoliday) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue getHolidayTrxValue(long aHolidayId) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue getHolidayByTrxID(String aTrxID) throws HolidayException,TransactionException,CommandProcessingException;
	public IHolidayTrxValue checkerApproveHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue checkerRejectHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerCreateHoliday(ITrxContext anITrxContext, IHoliday anICCHoliday)throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerSaveHoliday(ITrxContext anITrxContext, IHoliday anICCHoliday)throws HolidayException,TrxParameterException,TransactionException;
	
	public boolean isPrevFileUploadPending() throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerInsertMapperHoliday(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws HolidayException,TrxParameterException,TransactionException;
	public int insertHoliday(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue getInsertFileByTrxID(String aTrxID) throws HolidayException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue checkerApproveInsertHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws HolidayException,TrxParameterException,TransactionException;
	public IHoliday insertActualHoliday(String sysId) throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IHolidayTrxValue checkerCreateHoliday(ITrxContext anITrxContext,IHoliday anICCHoliday, String refStage)throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue checkerRejectInsertHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	public IHolidayTrxValue makerInsertCloseRejectedHoliday(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) throws HolidayException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
