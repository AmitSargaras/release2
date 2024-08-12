package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public class AbstractFielUploadTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{
	
	private IFileUploadBusManager fileUploadBusManager;

    private IFileUploadBusManager stagingfileUploadBusManager;

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
    
	protected IFileUploadTrxValue prepareTrxValue(IFileUploadTrxValue fileUploadTrxValue)throws TrxOperationException {
        if (fileUploadTrxValue != null) {
        	IFileUpload actual = fileUploadTrxValue.getFileUpload();
        	IFileUpload staging = fileUploadTrxValue.getStagingfileUpload();
            if (actual != null) {
            	fileUploadTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	fileUploadTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	fileUploadTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	fileUploadTrxValue.setStagingReferenceID(null);
            }
            return fileUploadTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- component is null");
        }
    }
	
	 protected IFileUploadTrxValue getFileUploadTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
	    	if(anITrxValue!=null){
	    	try {
	            return (IFileUploadTrxValue) anITrxValue;
	        }
	        catch (FileUploadException e) {
	            throw new FileUploadException("The ITrxValue is not of type OBFileUploadTrxValue: ");
	        }
	        catch (ClassCastException ex) {
	            throw new FileUploadException("The ITrxValue is not of type OBFileUploadTrxValue: " + ex.toString());
	        }
	    	}else{
	        	throw new FileUploadException("Error : Error  while preparing result file upload in abstract trx operation");
	        }
	    }

	 protected IFileUploadTrxValue createStagingFileUpload(IFileUploadTrxValue fileUploadTrxValue) throws TrxOperationException {
	    	if(fileUploadTrxValue!=null){
	    	try {
	    		IFileUpload file = getStagingfileUploadBusManager().makerCreateFile(fileUploadTrxValue.getStagingfileUpload());
	    		fileUploadTrxValue.setStagingfileUpload(file);
	    		fileUploadTrxValue.setStagingReferenceID(String.valueOf(file.getId()));
	            return fileUploadTrxValue;
	        }
	        catch (FileUploadException e) {
	            throw new FileUploadException("Error : Error  while creating file upload in abstract trx operation");
	        }catch (Exception ex) {
	            throw new FileUploadException("Error : Error  while creating file upload in abstract trx operation");
	        }
	    	}else{
	        	throw new FileUploadException("Error : Error  while preparing result file upload in abstract trx operation");
	        }
	    }
	 
	 protected ITrxResult prepareResult(IFileUploadTrxValue value) {
	    	if(value!=null){
	        OBCMSTrxResult result = new OBCMSTrxResult();
	        result.setTrxValue(value);
	        return result;
	        }else{
	        	throw new FileUploadException("Error : Error  while preparing result file in abstract trx operation");
	        }
	    }
	 
	 protected IFileUploadTrxValue updateFileUploadTrx(IFileUploadTrxValue fileUploadTrxValue) throws TrxOperationException {
	    	if(fileUploadTrxValue!=null){
	    	try {
	    		fileUploadTrxValue = prepareTrxValue(fileUploadTrxValue);
	            ICMSTrxValue tempValue = super.updateTransaction(fileUploadTrxValue);
	            OBFileUploadTrxValue newValue = new OBFileUploadTrxValue(tempValue);
	            newValue.setFileUpload(fileUploadTrxValue.getFileUpload());
	            newValue.setStagingfileUpload(fileUploadTrxValue.getStagingfileUpload());
	            return newValue;
	        }
	        
	        catch (FileUploadException ex) {
	            throw new FileUploadException("General Exception: in update component  " );
	        }
	    	}else{
	        	throw new FileUploadException("Error : Error  while preparing result component in abstract trx operation");
	        }
	    }
	 
	public String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
    

}
