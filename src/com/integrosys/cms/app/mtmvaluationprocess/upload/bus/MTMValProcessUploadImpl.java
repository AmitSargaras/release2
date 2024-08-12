package com.integrosys.cms.app.mtmvaluationprocess.upload.bus;

import java.util.Date;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class MTMValProcessUploadImpl extends JdbcDaoSupport implements IMTMValProcessUpload {

	public void logData(String fileType, String path, String fileName
			, int totalRecords) {
		StringBuffer sql = new StringBuffer("insert into CMS_MTMVAL_PROCESS_UPLOAD values ")
				.append("(CMS_MTMVAL_PROCESS_UPLOAD_SEQ.nextval, ?, ?, ?, ?, ?) ");
		
		getJdbcTemplate().update(sql.toString(), new Object[] { fileType, path, fileName, totalRecords, new Date() });
	}

}
