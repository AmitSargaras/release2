package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBFileUploadTrxValue extends OBCMSTrxValue implements IFileUploadTrxValue{
	
public OBFileUploadTrxValue(){}
	
	 public OBFileUploadTrxValue(ICMSTrxValue anICMSTrxValue)
	    {
	        AccessorUtil.copyValue (anICMSTrxValue, this);
	    }
	 
	 IFileUpload fileUpload ;
	 IFileUpload stagingfileUpload;	    
	 IFileMapperId FileMapperID;
	 IFileMapperId stagingFileMapperID;
	public IFileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public IFileUpload getStagingfileUpload() {
		return stagingfileUpload;
	}

	public void setStagingfileUpload(IFileUpload stagingfileUpload) {
		this.stagingfileUpload = stagingfileUpload;
	}

	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}
	 
	 

}
