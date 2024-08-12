package com.integrosys.cms.batch.deferralExtension.schedular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBDeferralExtensionFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;

public class DeferralExtensionJob implements IFileUploadConstants {

	//private final static Logger logger = LoggerFactory.getLogger(DeferralExtensionJob.class);
	boolean flagDeferralExt = false;
	public static void main(String[] args) {

		new DeferralExtensionJob().execute();
	}

	public DeferralExtensionJob() {
	}

	public void execute() {
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String deferralExtensionServerName = bundle1.getString("deferral.extension.server.name");
		System.out.println("<<<<In execute() DeferralExtensionJob Strating....>>>>"+deferralExtensionServerName);
		FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
		if(null!= deferralExtensionServerName && deferralExtensionServerName.equalsIgnoreCase("app1")){
			System.out.println("DeferralExtensionJob => flagDeferralExt flag is Line no 48..=>"+flagDeferralExt);
			try {
				boolean flag = false;
				if(flagDeferralExt == false) {
				flagDeferralExt = true;
				System.out.println("DeferralExtensionJob => flagDeferralExt flag is Line no 56..=>"+flagDeferralExt);
					
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup = generalParamDao
					.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
			Date applicationDate = new Date();

			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
					applicationDate = new Date(generalParamEntries[i].getParamValue());
				}
			}
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String checkDate = df.format(applicationDate);
			
			System.out.println("<<<<In execute() of DeferralExtensionJob....Before download");
			fileUploadFtpService.downlodUploadedFiles("DEFERRAL_EXTENSION");
			System.out.println("<<<<In execute() of DeferralExtensionJob....after download");

			File path = new File(bundle.getString(FTP_FILEUPLOAD_DEFERRAL_EXTENSION_LOCAL_DIR));
			File currentFile = null;
			File list[] = path.listFiles();
			
			if(list.length>0) {
				currentFile = list[0];
				int max = 2;
				//to get latest file
				for (int k = 1; k <= list.length - 1; k++) {
					File sortFile = list[k];
					if ((sortFile.getName().contains(FTP_DEFERRAL_EXTENSION_FILE_NAME + checkDate)
						&& (sortFile.getName().endsWith(".csv") || sortFile.getName().endsWith(".CSV")))) {
						max = currentFile.getName().compareTo(sortFile.getName());
						if (max < 0)
							currentFile = sortFile;
					}
				}
			}
			
			if (null!=currentFile && (currentFile.getName().contains(FTP_DEFERRAL_EXTENSION_FILE_NAME + checkDate)
					&& (currentFile.getName().endsWith(".csv") || currentFile.getName().endsWith(".CSV")))) {
				// Read file
				System.out.println("<<<<In execute() of DeferralExtensionJob.....current file name:"+currentFile.getName()+">>>>");
				ArrayList resultList = new ArrayList();
				ProcessDataFile dataFile = new ProcessDataFile();
				System.out.println("<<<<In execute() of DeferralExtensionJob.....Going for dataFile.processFileUpload ..");
				resultList = dataFile.processFileUpload(currentFile, "DeferralExtensionUpload");
				System.out.println("<<<<In execute() of DeferralExtensionJob....resultList.size():"+ resultList.size());
				// Error list while reading file
				HashMap eachDataMap = (HashMap) dataFile.getErrorList();
				System.out.println("<<<<In execute() of DeferralExtensionJob....getErrorList.size():"+ eachDataMap.size());
				List csvReadErrorList = new ArrayList(eachDataMap.values());
				System.out.println("<<<<In execute() of DeferralExtensionJob....csvReadErrorList:"+ csvReadErrorList);
				List finalList = new ArrayList();
				for (int i = 0; i < csvReadErrorList.size(); i++) {
					String[][] errList = (String[][]) csvReadErrorList.get(i);
					String str = "";
					for (int p = 0; p < 12; p++) {
						for (int j = 0; j < 4; j++) {
							str = str + ((errList[p][j] == null) ? "" : errList[p][j] + ";");
						}
						str = str + "||";
					}
					finalList.add(str);
					System.out.println("<<<<In execute() of DeferralExtensionJob....csvReadErrorList:"+ str);
				}

				// insert data here
				if (resultList.size() > 0) {
					System.out.println("<<<<In execute() of DeferralExtensionJob line no 126::"+resultList.size());
					OBTrxContext ctx = new OBTrxContext();
					IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
					IFileUploadProxyManager fileUploadProxy = (IFileUploadProxyManager) BeanHouse
							.get("fileUploadProxy");
					IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
					trxValueOut.setTransactionSubType("DEFERRAL_EXTENSION_UPLOAD");
					ArrayList totalUploadedList = new ArrayList();
					ArrayList errorList = new ArrayList();
					ArrayList consolidateList=new ArrayList();
					ArrayList consolidateListMain=new ArrayList();
					int countPass = 0;
					int countFail = 0;
					HashMap mp = new HashMap();
					//Update nextDueDate here
					mp = (HashMap) jdbc.insertDeferralExtnsionFile(resultList,applicationDate,currentFile.getName());
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					errorList = (ArrayList) mp.get("errorList");
					System.out.println("<<<<In execute() of DeferralExtensionJob totalUploadedList.size():"+totalUploadedList.size());
					System.out.println("<<<<In execute() of DeferralExtensionJob errorList.size():"+errorList.size());
					if (totalUploadedList.size()>0 ||errorList.size()>0) {
						//Entry into STAGE_FILE_UPLOAD
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("DEFERRAL_EXTENSION_UPLOAD");
						fileObj.setUploadBy("System");
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(currentFile.getName());
						fileObj.setTotalRecords(String.valueOf(resultList.size()));
						
						for (int i = 0; i < totalUploadedList.size(); i++) {
							OBDeferralExtensionFileUpload obj = (OBDeferralExtensionFileUpload) totalUploadedList.get(i);
							if ("PASS".equals(obj.getStatus())) {
								countPass++;
							}
						}
						
						for (int j = 0; j < errorList.size(); j++) {
							OBDeferralExtensionFileUpload errorObj = (OBDeferralExtensionFileUpload) errorList.get(j);
							System.out.println("<<<<In execute() of DeferralExtensionJob 164 =========errorobj status is : " +errorObj.getStatus());
							if ("FAIL".equals(errorObj.getStatus())) {
								System.out.println("<<<<In execute() of DeferralExtensionJob 166 =========error is : " +errorObj.getFailReason());
								countFail++;
							}
						}
						
						fileObj.setApproveRecords(String.valueOf(countPass));
						System.out.println("<<<<In execute() of DeferralExtensionJob before makerCreateFile");
						trxValueOut = fileUploadProxy.makerCreateFile(ctx, fileObj);//STAGE_FILE_UPLOAD
						System.out.println("<<<<In execute() of DeferralExtensionJob after makerCreateFile");
						//Entry into STAGE_DEFERRAL_UPLOAD
						if(trxValueOut!=null){
							for(int i=0;i<totalUploadedList.size();i++){
								OBDeferralExtensionFileUpload obj=(OBDeferralExtensionFileUpload)totalUploadedList.get(i);
								obj.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
								consolidateList.add(obj);
							}
							for(int i=0;i<errorList.size();i++){
								OBDeferralExtensionFileUpload obj=(OBDeferralExtensionFileUpload)errorList.get(i);
								obj.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
								consolidateList.add(obj);
							}
							System.out.println("<<<<In execute() of DeferralExtensionJob consolidateList"+consolidateList.size());
							int batchSize = 200;
							for (int j = 0; j < consolidateList.size(); j += batchSize) {
								List<OBDeferralExtensionFileUpload> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
								jdbc.createEntireDeferralExtensionFile(batchList);//STAGE_DEFERRAL_UPLOAD
							}
							System.out.println("<<<<In execute() of DeferralExtensionJob  line no 193");
						}
						//Entry into CMS_FILE_UPLOAD
						IFileUpload stgFile= trxValueOut.getStagingfileUpload();
						stgFile.setApproveBy("System");
						stgFile.setApproveRecords(String.valueOf(countPass));
						errorList = (ArrayList) mp.get("errorList");
						trxValueOut.setStagingfileUpload(stgFile);
						trxValueOut = fileUploadProxy.checkerApproveFileUpload(ctx, trxValueOut);//CMS_FILE_UPLOAD
						System.out.println("<<<<In execute() of DeferralExtensionJob  line no 202");
						//Entry into CMS_DEFERRAL_UPLOAD
						if(trxValueOut!=null){
							long fileId=Long.parseLong(trxValueOut.getReferenceID());
							if(totalUploadedList.size()>0){
								for(int i=0;i<totalUploadedList.size();i++){
									OBDeferralExtensionFileUpload obj=(OBDeferralExtensionFileUpload)totalUploadedList.get(i);
									obj.setFileId(fileId);
									consolidateListMain.add(obj);
								}
							}
							for(int i=0;i<errorList.size();i++){
								OBDeferralExtensionFileUpload obj=(OBDeferralExtensionFileUpload)errorList.get(i);
								obj.setFileId(fileId);
								consolidateListMain.add(obj);
							}
							System.out.println("<<<<In execute() of DeferralExtensionJob consolidateListMain"+consolidateListMain.size());
							int batchSize = 200;
							for (int j = 0; j < consolidateListMain.size(); j += batchSize) {
								List<OBDeferralExtensionFileUpload> batchList = consolidateListMain.subList(j, j + batchSize > consolidateListMain.size() ? consolidateListMain.size() : j + batchSize);
								jdbc.approveEntireDeferralExtensionFile(batchList); //CMS_DEFERRAL_UPLOAD
							}
						}
						System.out.println("<<<<In execute() of DeferralExtensionJob line 225");
					}
				}
				// For Error list
				if(finalList.size() > 0) {
						System.out.println("<<<<In execute() of DeferralExtensionJob finalList.size()"+finalList.size());
						ArrayList consolidateList=new ArrayList();
						IFileUpload fileObj = new OBFileUpload();
						OBTrxContext ctx = new OBTrxContext();
						IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
						IFileUploadProxyManager fileUploadProxy = (IFileUploadProxyManager) BeanHouse.get("fileUploadProxy");
						IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
						
						fileObj.setFileType("DEFERRAL_EXTENSION_UPLOAD");
						fileObj.setUploadBy("System");
						fileObj.setApproveBy("System");
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(currentFile.getName());
						fileObj.setTotalRecords(String.valueOf(finalList.size()));
						fileObj.setApproveRecords(String.valueOf(0));
						
						trxValueOut = fileUploadProxy.makerCreateFile(ctx, fileObj);//STAGE_FILE_UPLOAD
						
						if(trxValueOut!=null){
							String failReason ="";
							for(int i=0;i<finalList.size();i++){
								failReason =(String) finalList.get(i);
								failReason=failReason.replace("|", "");
								OBDeferralExtensionFileUpload obj=new OBDeferralExtensionFileUpload();
								obj.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
								obj.setFailReason(failReason);
								obj.setFileName(currentFile.getName());
								obj.setStatus("FAIL");
								obj.setUploadStatus("Y");
								Timestamp st = null;
								st = new Timestamp(System.currentTimeMillis());
								Date date =new Date(st.getTime());
								java.sql.Date sAppDate = new java.sql.Date(date.getTime());
								obj.setDateOfRequest(sAppDate);
								obj.setDateOfProcess(sAppDate);
								consolidateList.add(obj);
							}
							
							int batchSize = 200;
							for (int j = 0; j < consolidateList.size(); j += batchSize) {
								List<OBDeferralExtensionFileUpload> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
								jdbc.createEntireDeferralExtensionFile(batchList);//STAGE_DEFERRAL_UPLOAD
							}
						
							trxValueOut = fileUploadProxy.checkerApproveFileUpload(ctx, trxValueOut);//CMS_FILE_UPLOAD
							
							long fileId=Long.parseLong(trxValueOut.getReferenceID());
							for(int i=0;i<consolidateList.size();i++){
								OBDeferralExtensionFileUpload obj=(OBDeferralExtensionFileUpload) consolidateList.get(i);
								obj.setFileId(fileId);
								try {
								if(null!=obj.getModule() && obj.getModule().equalsIgnoreCase("OtherDocument")){	
								String status =	jdbc.getCheckListItemStatusFromDocCode(Long.parseLong(obj.getChecklistID()),Long.parseLong(obj.getDocID()),obj.getLadCode());
								System.out.println("<<<<In execute() of DeferralExtensionJob line no 283 \n CheckList Id  : "+obj.getChecklistID()+
										" ItemRef : "+obj.getDocID()+" doc code : "+obj.getLadCode()+
										" checklist status :"+status);
								
								if(status.equalsIgnoreCase("DEFERRED")) {
								IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
								String updateInsuranceDetailStg = insuranceGCJdbc
										.updateInsuranceDetailStg(Long.parseLong(obj.getChecklistID()));
								if ("success".equals(updateInsuranceDetailStg)) {
									String updateInsuranceDetail = insuranceGCJdbc.updateInsuranceDetail(Long.parseLong(obj.getChecklistID()));
									DefaultLogger.info(this, "Actual Security Insurance update status: " + updateInsuranceDetail);
								}
								String statusAfterUpdate =	jdbc.getCheckListItemStatusFromDocCode(Long.parseLong(obj.getChecklistID()),Long.parseLong(obj.getDocID()),obj.getLadCode());
								System.out.println("<<<<In execute() of DeferralExtensionJob line no 296 after updateInsuranceDetail \n CheckList Id  : "+obj.getChecklistID()+
										" ItemRef : "+obj.getDocID()+" doc code : "+obj.getLadCode()+
										" checklist status :"+statusAfterUpdate);
								}
								
								}}catch(Exception e) {
									System.out.println("<<<<In execute() of DeferralExtensionJob line no 303 exception is :"+e);
								}
								consolidateList.set(i, obj);
							}
					
							for (int j = 0; j < consolidateList.size(); j += batchSize) {
								List<OBDeferralExtensionFileUpload> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
								jdbc.approveEntireDeferralExtensionFile(batchList); //CMS_DEFERRAL_UPLOAD
							}
						}
				}
				flag=true;
				System.out.println("<<<<In execute() of DeferralExtensionJob line no 313 flag got true ");
				//backup local files
				moveFile(currentFile,currentFile.getName());
				deleteOldFiles();
			}
			flagDeferralExt = false;
			System.out.println("DeferralExtensionJob => flagDeferralExt flag is Line no 319..=>"+flagDeferralExt);
			
			}
				System.out.println("DeferralExtensionJob => flag is Line no 322..=>"+flag);
				
				if(flag) {
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				System.out.println(" EodIndependentMethodsJob going for executeDeferralInsurancePolicy method ... ");
				collateralDAO.executeDeferralInsurancePolicy();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for executeDeferralGCPolicy method ... ");
				collateralDAO.executeDeferralGCPolicy();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for insertDeferralChecklistProperty method ... ");
				collateralDAO.insertDeferralChecklistProperty();//1 /// Scheduler Added ******************
			}
				System.out.println("DeferralExtensionJob => after running EOD job  Line no 334..=>");
						
			} catch (Exception e) {
				flagDeferralExt = false;
				System.out.println("Exception caught in DeferralExtensionJob line no 339  .. flagDeferralExt =>"+flagDeferralExt+" ... e=>"+e);
				e.printStackTrace();
			}
		}
	}
	
	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of DeferralExtensionJob deleteOldFiles()");
		try{
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString("ftp.master.deferral.extension.backup.local.dir");
			String days = bundle.getString("ftp.master.deferral.extension.noofDays");
			long noofDays = 1;
			if(null!= days && !"".equalsIgnoreCase(days)){

				noofDays = Long.parseLong(days);
			}
			File folder = new File(serverFilePath);

			if (folder.exists()) {

				File[] listFiles = folder.listFiles();

				long eligibleForDeletion = System.currentTimeMillis() -
						(noofDays * 24 * 60 * 60 * 1000);

				for (File listFile: listFiles) {

					if (listFile.lastModified() < eligibleForDeletion) {

						boolean delete = listFile.delete();
						if(delete==false) {
							System.out.println("file  deletion failed for file:"+listFile.getPath());	
						}
					}
				}
			}
		}
		catch(Exception e){

			e.printStackTrace();
		}

	}


	public void moveFile(File sourceFile,String fileName) {
		System.out.println("<<<<In execute() of DeferralExtensionJob moveFile()");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString("ftp.master.deferral.extension.backup.local.dir");
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists())
			{
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}


		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+sourceFile.getPath());	
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}
}
