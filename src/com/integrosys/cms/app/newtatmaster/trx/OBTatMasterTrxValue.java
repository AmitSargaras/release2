package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBTatMasterTrxValue extends OBCMSTrxValue implements ITatMasterTrxValue{
	
	
	public OBTatMasterTrxValue(){
		
	}
	
	 public OBTatMasterTrxValue(ICMSTrxValue anICMSTrxValue){
	        AccessorUtil.copyValue (anICMSTrxValue, this);
	    }
	 
	 
	 INewTatMaster tatMaster ;
	 INewTatMaster stagingtatMaster;
	    
	 IFileMapperId FileMapperID;
	 IFileMapperId stagingFileMapperID;
	 
	public INewTatMaster getTatMaster() {
		return tatMaster;
	}

	public void setTatMaster(INewTatMaster tatMaster) {
		this.tatMaster = tatMaster;
	}

	public INewTatMaster getStagingtatMaster() {
		return stagingtatMaster;
	}

	public void setStagingtatMaster(INewTatMaster stagingtatMaster) {
		this.stagingtatMaster = stagingtatMaster;
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
