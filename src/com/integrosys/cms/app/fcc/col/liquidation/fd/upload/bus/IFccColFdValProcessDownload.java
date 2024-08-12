package com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus;

import java.sql.SQLException;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;

public interface IFccColFdValProcessDownload {

	public void readingFileSuccess(String filePath,String fileNames) throws DBConnectionException, SQLException, Exception;

	public void dataTransferTempToActualFcc(String procedureName) throws Exception;
	
}
