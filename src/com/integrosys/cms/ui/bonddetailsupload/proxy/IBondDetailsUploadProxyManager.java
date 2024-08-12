package com.integrosys.cms.ui.bonddetailsupload.proxy;


import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IBondDetailsUploadProxyManager {

	public IFileUploadTrxValue makerCreateFile(ITrxContext anITrxContext, IFileUpload anFile)throws FileUploadException,TrxParameterException,TransactionException;
	public IFileUploadTrxValue getFileUploadByTrxID(String aTrxID)throws FileUploadException, TransactionException,	CommandProcessingException;
	public IFileUploadTrxValue checkerApproveFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
			throws FileUploadException, TrxParameterException,TransactionException;
	public IFileUploadTrxValue checkerRejectFileUpload(ITrxContext anITrxContext,IFileUploadTrxValue anFileTrxVal) throws FileUploadException,
		TrxParameterException, TransactionException;
	public IFileUploadTrxValue makerCloseRejectFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
		throws FileUploadException, TrxParameterException,TransactionException ;

}

