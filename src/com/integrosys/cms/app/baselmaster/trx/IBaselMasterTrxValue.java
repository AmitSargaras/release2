package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IBaselMasterTrxValue extends ICMSTrxValue{
	
	public IBaselMaster getBaselMaster();

	public void setBaselMaster(IBaselMaster baselMaster);

	public IBaselMaster getStagingBaselMaster();

	public void setStagingBaselMaster(IBaselMaster stagingBaselMaster);

	public IFileMapperId getFileMapperID();

	public void setFileMapperID(IFileMapperId fileMapperID);

	public IFileMapperId getStagingFileMapperID();

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID);
	 
	 

}
