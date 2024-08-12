package com.integrosys.cms.batch.fileUpload.schedular;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ftp.IFileUploadConstants;

public class FinwareFileUploadJob {

	private final static Logger logger = LoggerFactory.getLogger(FinwareFileUploadJob.class);

	public static void main(String[] args) {

		new FinwareFileUploadJob().execute();
	}

	public FinwareFileUploadJob() {

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
		DefaultLogger.info(this, "::: Start FinwareFileUploadJob :::");
		System.out.println("::: Start FinwareFileUploadJob :::");
		
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String autoUploadServerName = bundle1.getString("auto.upload.server.name");
		
		if(null!= autoUploadServerName && autoUploadServerName.equalsIgnoreCase("app1")){
			//Download Files from Remote location
			Map resultMap  = FileUploadHelper.downloadFilesFromRemoteLocation(IFileUploadConstants.FILEUPLOAD_FINWARE);
		
			List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
			if(fileInfo != null && fileInfo.size()>0) {
				//Maker Submit
				resultMap = FileUploadHelper.makerSubmitFileUpload(resultMap);
			
				//Checker Approve
				FileUploadHelper.checkerApproveFileUpload(resultMap);
			
				DefaultLogger.info(this, "::: End FinwareFileUploadJob Success :::");
				System.out.println("::: End FinwareFileUploadJob Success :::");
			}
			else {
				DefaultLogger.info(this, "::: End FinwareFileUploadJob :: fileInfo is null!! :::");
				System.out.println("::: End FinwareFileUploadJob :: fileInfo is null!! :::");
			}
		}
	}
}
