package com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus;

public interface IFccColFdValProcessUpload {

	public void logData(String fileType, String path, String fileName, int totalRecords,String fileStatus);
	
}
