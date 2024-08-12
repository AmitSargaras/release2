package com.integrosys.cms.ui.bulkudfupdateupload.proxy;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IBulkUDFUploadProxyManager {
	
	public IFileUploadTrxValue makerCreateFile(ITrxContext anITrxContext, IFileUpload anFile)throws FileUploadException,TrxParameterException,TransactionException;
	
	public IFileUploadTrxValue getFielUploadByTrxID(String aTrxID)throws FileUploadException, TransactionException,	CommandProcessingException;
	
	public IFileUploadTrxValue checkerApproveFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
	throws FileUploadException, TrxParameterException,TransactionException;
	
	public IFileUploadTrxValue checkerRejectFileUpload(ITrxContext anITrxContext,IFileUploadTrxValue anFileTrxVal) throws FileUploadException,
		TrxParameterException, TransactionException;
	
	public IFileUploadTrxValue makerCloseRejectUbsFile(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
		throws FileUploadException, TrxParameterException,TransactionException ;


}
