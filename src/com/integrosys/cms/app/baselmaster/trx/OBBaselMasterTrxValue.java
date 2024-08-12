package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBBaselMasterTrxValue extends OBCMSTrxValue implements IBaselMasterTrxValue{
	public OBBaselMasterTrxValue(){
		
	}
	
	 public OBBaselMasterTrxValue(ICMSTrxValue anICMSTrxValue)
	    {
	        AccessorUtil.copyValue (anICMSTrxValue, this);
	    }
	 IBaselMaster baselMaster;
	 IBaselMaster stagingBaselMaster;
	 IFileMapperId FileMapperID;
	 IFileMapperId stagingFileMapperID;
	 
	public IBaselMaster getBaselMaster() {
		return baselMaster;
	}

	public void setBaselMaster(IBaselMaster baselMaster) {
		this.baselMaster = baselMaster;
	}

	public IBaselMaster getStagingBaselMaster() {
		return stagingBaselMaster;
	}

	public void setStagingBaselMaster(IBaselMaster stagingBaselMaster) {
		this.stagingBaselMaster = stagingBaselMaster;
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
