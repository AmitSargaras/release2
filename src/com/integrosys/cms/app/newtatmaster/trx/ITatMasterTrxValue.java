package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ITatMasterTrxValue extends ICMSTrxValue{
	
	public INewTatMaster getTatMaster();
	public void setTatMaster(INewTatMaster tatMaster);

	public INewTatMaster getStagingtatMaster();
	public void setStagingtatMaster(INewTatMaster stagingtatMaster);

	public IFileMapperId getFileMapperID();
	public void setFileMapperID(IFileMapperId fileMapperID);

	public IFileMapperId getStagingFileMapperID();
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID);
	 
	 

}
