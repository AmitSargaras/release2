package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IFileUploadTrxValue extends ICMSTrxValue{
	public IFileUpload getFileUpload();

	public void setFileUpload(IFileUpload fileUpload);

	public IFileUpload getStagingfileUpload();

	public void setStagingfileUpload(IFileUpload stagingfileUpload);

	public IFileMapperId getFileMapperID();

	public void setFileMapperID(IFileMapperId fileMapperID);

	public IFileMapperId getStagingFileMapperID();

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID);
}
