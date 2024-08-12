package com.integrosys.cms.batch.fileUpload.schedular;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ftp.IFileUploadConstants;

public class FdFileUploadJob {

	private final static Logger logger = LoggerFactory.getLogger(FdFileUploadJob.class);

	public static void main(String[] args) {

		new FdFileUploadJob().execute();
	}

	public FdFileUploadJob() {

	}
	
	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config/spring/batch/ubsFileUpload/AppContext_Master.xml
	 * Schedular has been designed to carry out the following activities
	 * 1. Download the file from remote location to local 
	 * 2. Read the file.
	 * 3. Store the records in the database.
	 */
	
	public void execute() {	
		DefaultLogger.info(this, "::: Start FdFileUploadJob :::");
		System.out.println("::: Start FdFileUploadJob :::");
		
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String autoUploadServerName = bundle1.getString("auto.upload.server.name");
		
		if(null!= autoUploadServerName && autoUploadServerName.equalsIgnoreCase("app1")){
			//Download Files from Remote location
			Map resultMap  = FileUploadHelper.downloadFilesFromRemoteLocation(IFileUploadConstants.FILEUPLOAD_FD);

			List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
			if(fileInfo != null && fileInfo.size()>0) {
				//Maker Submit
				resultMap = FileUploadHelper.makerSubmitFileUpload(resultMap);
			
				//Checker Approve
				FileUploadHelper.checkerApproveFileUpload(resultMap);
			
				DefaultLogger.info(this, "::: End FdFileUploadJob Success :::");
				System.out.println("::: End FdFileUploadJob Success :::");
			}
			else {
				DefaultLogger.info(this, "::: End FdFileUploadJob :: fileInfo is null!! :::");
				System.out.println("::: End FdFileUploadJob :: fileInfo is null!! :::");
			}
		}
	}
}
