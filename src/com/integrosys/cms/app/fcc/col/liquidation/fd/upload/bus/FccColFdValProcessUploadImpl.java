package com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus;

import java.util.Date;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class FccColFdValProcessUploadImpl extends JdbcDaoSupport implements IFccColFdValProcessUpload {

	public void logData(String fileType, String path, String fileName
			, int totalRecords,String fileStatus) {
		StringBuffer sql = new StringBuffer("insert into CMS_FCC_COL_PROCESS_UPLOAD values ")
				.append("(CMS_FCC_COL_PROCESS_UPLOAD_SEQ.nextval, ?, ?, ?, ?, ?, ?) ");
		
		getJdbcTemplate().update(sql.toString(), new Object[] { fileType, path, fileName, totalRecords, new Date(),fileStatus });
	}


}
