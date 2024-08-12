package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class FileUploadTrxControllerFactory implements ITrxControllerFactory{
	
	 private ITrxController fileUploadReadController;

	    private ITrxController fileUploadTrxController;

	    private ITrxController fileUploadInsertFileTrxController;

	    private ITrxController readfileUploadInsertFileTrxController;
	    
	    

	public ITrxController getFileUploadReadController() {
			return fileUploadReadController;
		}



		public void setFileUploadReadController(ITrxController fileUploadReadController) {
			this.fileUploadReadController = fileUploadReadController;
		}



		public ITrxController getFileUploadTrxController() {
			return fileUploadTrxController;
		}



		public void setFileUploadTrxController(ITrxController fileUploadTrxController) {
			this.fileUploadTrxController = fileUploadTrxController;
		}



		public ITrxController getFileUploadInsertFileTrxController() {
			return fileUploadInsertFileTrxController;
		}



		public void setFileUploadInsertFileTrxController(
				ITrxController fileUploadInsertFileTrxController) {
			this.fileUploadInsertFileTrxController = fileUploadInsertFileTrxController;
		}



		public ITrxController getReadfileUploadInsertFileTrxController() {
			return readfileUploadInsertFileTrxController;
		}



		public void setReadfileUploadInsertFileTrxController(
				ITrxController readfileUploadInsertFileTrxController) {
			this.readfileUploadInsertFileTrxController = readfileUploadInsertFileTrxController;
		}



	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)
			throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
	        
        {
            return getFileUploadReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getFileUploadInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getFileUploadInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadfileUploadInsertFileTrxController();
        }
        return getFileUploadTrxController();
    }
	
	private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_FILEUPLOAD)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_FILEUPLOAD_ID))) {
            return true;
        }
        return false;
    }

}
