package com.integrosys.cms.ui.mf.schemadetailsupload.proxy;


import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadBusManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class SchemaDetailsUploadProxyManagerImpl implements ISchemadetailsUploadProxyManager {
	
	private IFileUploadBusManager fileUploadBusManager;
	private IFileUploadBusManager stagingfileUploadBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private IFileUploadBusManager stagingfileUploadFileMapperIdBusManager;
	private IFileUploadBusManager fileUploadFileMapperIdBusManager;
	

	public IFileUploadBusManager getFileUploadBusManager() {
		return fileUploadBusManager;
	}

	public void setFileUploadBusManager(IFileUploadBusManager fileUploadBusManager) {
		this.fileUploadBusManager = fileUploadBusManager;
	}

	public IFileUploadBusManager getStagingfileUploadBusManager() {
		return stagingfileUploadBusManager;
	}

	public void setStagingfileUploadBusManager(
			IFileUploadBusManager stagingfileUploadBusManager) {
		this.stagingfileUploadBusManager = stagingfileUploadBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IFileUploadBusManager getStagingfileUploadFileMapperIdBusManager() {
		return stagingfileUploadFileMapperIdBusManager;
	}

	public void setStagingfileUploadFileMapperIdBusManager(
			IFileUploadBusManager stagingfileUploadFileMapperIdBusManager) {
		this.stagingfileUploadFileMapperIdBusManager = stagingfileUploadFileMapperIdBusManager;
	}

	public IFileUploadBusManager getFileUploadFileMapperIdBusManager() {
		return fileUploadFileMapperIdBusManager;
	}

	public void setFileUploadFileMapperIdBusManager(
			IFileUploadBusManager fileUploadFileMapperIdBusManager) {
		this.fileUploadFileMapperIdBusManager = fileUploadFileMapperIdBusManager;
	}

	public IFileUploadTrxValue makerCreateFile(ITrxContext anITrxContext,IFileUpload anFile)
	throws FileUploadException,TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new FileUploadException("The ITrxContext is null!!!");
        }
        if (anFile == null) {
            throw new FileUploadException("The ICCComponent to be updated is null !!!");
        }

        IFileUploadTrxValue trxValue = formulateTrxValue(anITrxContext, null, anFile);
        trxValue.setFromState("PENDING_CREATE");
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_FILEUPLOAD);
        return operate(trxValue, param);
	}
	
	private IFileUploadTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileUpload anFile) {
		IFileUploadTrxValue fileUploadTrxValue = null;
        if (anICMSTrxValue != null) {
        	fileUploadTrxValue = new OBFileUploadTrxValue(anICMSTrxValue);
        } else {
        	fileUploadTrxValue = new OBFileUploadTrxValue();
        }
        fileUploadTrxValue = formulateTrxValue(anITrxContext, (IFileUploadTrxValue) fileUploadTrxValue);
        fileUploadTrxValue.setStagingfileUpload(anFile);
        return fileUploadTrxValue;
    }
	
	private IFileUploadTrxValue formulateTrxValue(ITrxContext anITrxContext, IFileUploadTrxValue anFile) {
		anFile.setTrxContext(anITrxContext);
		anFile.setTransactionType(ICMSConstant.MF_SCHEMA_DETAILS_UPLOAD);
        return anFile;
    }
	
	private IFileUploadTrxValue operate(IFileUploadTrxValue anFileTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws FileUploadException,TrxParameterException,TransactionException {
        ICMSTrxResult result = operateForResult(anFileTrxValue, anOBCMSTrxParameter);
        return (IFileUploadTrxValue) result.getTrxValue();
    }
	
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws FileUploadException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (ComponentException ex) {
			 throw new ComponentException("ERROR--Cannot Get the File Upload Controller.");
		 }
		 catch (Exception ex) {
			 throw new ComponentException("ERROR--Cannot Get the File Upload Controller.");
		 }
	}
	 
	public IFileUploadTrxValue getFileUploadByTrxID(String aTrxID)throws FileUploadException, TransactionException,
		CommandProcessingException {
		 IFileUploadTrxValue trxValue = new OBFileUploadTrxValue();
		 trxValue.setTransactionID(String.valueOf(aTrxID));
		 trxValue.setTransactionType(ICMSConstant.MF_SCHEMA_DETAILS_UPLOAD);
		 OBCMSTrxParameter param = new OBCMSTrxParameter();
		 param.setAction(ICMSConstant.ACTION_READ_FILEUPLOAD_ID);
		 return operate(trxValue, param);
	}

	public IFileUploadTrxValue checkerApproveFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
		throws FileUploadException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
	     throw new FileUploadException("The ITrxContext is null!!!");
		}
		if (anFileTrxVal == null) {
	     throw new FileUploadException
	             ("The IComponentTrxValue to be updated is null!!!");
		}
		anFileTrxVal.getStagingfileUpload().setApproveBy(anITrxContext.getUser().getLoginID());	 
		anFileTrxVal = formulateTrxValue(anITrxContext, anFileTrxVal);
		
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_FILEUPLOAD);
		return operate(anFileTrxVal, param);
	}
	 
	public IFileUploadTrxValue checkerRejectFileUpload(ITrxContext anITrxContext,IFileUploadTrxValue anFileTrxVal) throws FileUploadException,
		TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new FileUploadException("The ITrxContext is null!!!");
		}
		if (anFileTrxVal == null) {
			throw new FileUploadException("The IFileUploadTrxValue to be updated is null!!!");
		}
		 anFileTrxVal = formulateTrxValue(anITrxContext, anFileTrxVal);
		 OBCMSTrxParameter param = new OBCMSTrxParameter();
		 param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_FILEUPLOAD);
		 return operate(anFileTrxVal, param);
	}
	 
	public IFileUploadTrxValue makerCloseRejectFileUpload(ITrxContext anITrxContext, IFileUploadTrxValue anFileTrxVal)
				throws FileUploadException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new FileUploadException("The ITrxContext is null!!!");
        }
        if (anFileTrxVal == null) {
            throw new FileUploadException("The IFileUploadTrxValue to be updated is null!!!");
        }
        anFileTrxVal = formulateTrxValue(anITrxContext, anFileTrxVal);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FILEUPLOAD);
        return operate(anFileTrxVal, param);
	}

}