package com.integrosys.cms.batch.fileUpload.schedular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.component.user.app.bus.ICommonUser;

public class UbsFileUploadJob implements ICommonEventConstant,IFileUploadConstants{
	
	private IFileUploadProxyManager fileUploadProxy;
	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}
	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(UbsFileUploadJob.class);

	public static void main(String[] args) {

		new UbsFileUploadJob().execute();
	}
	
	public UbsFileUploadJob() {

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
		DefaultLogger.info(this, "::: Start UbsFileUploadJob :::");
		System.out.println("::: Start UbsFileUploadJob :::");
		
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String autoUploadServerName = bundle1.getString("auto.upload.server.name");
		
		if(null!= autoUploadServerName && autoUploadServerName.equalsIgnoreCase("app1")){
			//Download Files from Remote location
			Map resultMap  = FileUploadHelper.downloadFilesFromRemoteLocation(IFileUploadConstants.FILEUPLOAD_UBS);

			List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
			if(fileInfo != null && fileInfo.size()>0) {
			
				//Maker Submit
				resultMap = FileUploadHelper.makerSubmitFileUpload(resultMap);
			
				//Checker Approve
				FileUploadHelper.checkerApproveFileUpload(resultMap);
			
				DefaultLogger.info(this, "::: End UbsFileUploadJob Success :::");
				System.out.println("::: End UbsFileUploadJob Success:::");
			}
			else {
				DefaultLogger.info(this, "::: End UbsFileUploadJob :: fileInfo is null!! :::");
				System.out.println("::: End UbsFileUploadJob :: fileInfo is null!!:::");
			}
		}
	}
}
