package com.integrosys.cms.app.holiday.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
/**
 * @author  Abhijit R. 
 */
public interface IHolidayDao {

	static final String ACTUAL_HOLIDAY_NAME = "actualHoliday";
	static final String STAGE_HOLIDAY_NAME = "stageHoliday";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	IHoliday getHoliday(String entityName, Serializable key)throws HolidayException;
	IHoliday updateHoliday(String entityName, IHoliday item)throws HolidayException;
	IHoliday deleteHoliday(String entityName, IHoliday item);
	IHoliday load(String entityName,long id)throws HolidayException;
	
	IHoliday createHoliday(String entityName, IHoliday holiday)
	throws HolidayException;
	IFileMapperId insertHoliday(String entityName, IFileMapperId fileId, IHolidayTrxValue trxValue)
	throws HolidayException;
	int insertHoliday(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws HolidayException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws HolidayException;
	IHoliday insertActualHoliday(String sysId)
	throws HolidayException;
	IHoliday insertHoliday(String entityName, IHoliday holiday)
	throws HolidayException;
	boolean isPrevFileUploadPending() throws HolidayException;
	List getFileMasterList(String searchBy)throws HolidayException;
	List getAllStageHoliday (String searchBy, String login)throws RelationshipMgrException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
