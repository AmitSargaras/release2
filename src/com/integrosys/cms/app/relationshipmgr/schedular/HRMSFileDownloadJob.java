package com.integrosys.cms.app.relationshipmgr.schedular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.batch.fileUpload.schedular.FileUploadHelper;

public class HRMSFileDownloadJob {

	ResourceBundle resbundle = ResourceBundle.getBundle("ofa");

	public static void main(String[] args) {
		new HRMSFileDownloadJob().execute();
	}

	public HRMSFileDownloadJob() {
	}

	private void execute() {
		try {
			System.out.println("Starting HRMSFileDownloadJob......123456");
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String autoUploadServerName = bundle1.getString("hrms.server.name");
		
			if(null!= autoUploadServerName && autoUploadServerName.equalsIgnoreCase("app1")){
				//Download Files from Remote location
				Map resultMap  = FileUploadHelper.downloadFilesFromRemoteLocation(IFileUploadConstants.FILEDOWNLOAD_HRMS);

				List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
				if(fileInfo != null && fileInfo.size()>0) {
				
					FileUploadHelper.checkerApproveFileUpload(resultMap);
				
					DefaultLogger.info(this, "::: End HRMSFileDownloadJob Success :::");
					System.out.println("::: End HRMSFileDownloadJob Success:::");
				}
				else {
					DefaultLogger.info(this, "::: End HRMSFileDownloadJob :: fileInfo is null!! :::");
					System.out.println("::: End HRMSFileDownloadJob :: fileInfo is null!!:::");
				}
			}
		}catch(Exception e) {
			DefaultLogger.info(this, "::: End HRMSFileDownloadJob Exception :::"+e);
		}
	}

}
