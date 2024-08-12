package com.integrosys.cms.app.leiDetailsUpload.proxy;


import java.util.ArrayList;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: Mukesh Mohapatra $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface ILeiDetailsUploadProxyManager {


	//public IPartyCamErrorLog insertPartyCamfile(ArrayList resultList,String fileName,String uploadId,IPartyCamErrDetLog[] obPartyCamErrDetLog) throws HolidayException,TrxParameterException,TransactionException;
	
	public IFileUploadTrxValue makerCreateFile(ITrxContext anITrxContext, IFileUpload anFile)throws FileUploadException,TrxParameterException,TransactionException;
	public IFileUploadTrxValue getFielUploadByTrxID(String aTrxID)throws FileUploadException, TransactionException,	CommandProcessingException;
	public IFileUploadTrxValue checkerApproveFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
	throws FileUploadException, TrxParameterException,TransactionException;
	 public IFileUploadTrxValue checkerRejectFileUpload(ITrxContext anITrxContext,IFileUploadTrxValue anFileTrxVal) throws FileUploadException,
		TrxParameterException, TransactionException;
	 public IFileUploadTrxValue makerCloseRejectUbsFile(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
		throws FileUploadException, TrxParameterException,TransactionException ;


}

