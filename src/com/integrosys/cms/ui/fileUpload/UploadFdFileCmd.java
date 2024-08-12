package com.integrosys.cms.ui.fileUpload;

/**
 * @author uma.khot
 * to support FD Upload
 */
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBCommonFdFile;
import com.integrosys.cms.app.fileUpload.bus.OBFdFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class UploadFdFileCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IFileUploadProxyManager fileUploadProxy;
	public static final String FD_UPLOAD = "FdUpload";
	
	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}

	public UploadFdFileCmd() {
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID,
						"java.lang.String", GLOBAL_SCOPE },
			//	{ "dataFromFdCacheView", "java.util.Set", SERVICE_SCOPE },
				{ "dataFromUpdLineFacilityMV",
						"java.util.concurrent.ConcurrentMap", SERVICE_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchcomponentName", "java.lang.String",
						SERVICE_SCOPE },
				{
						"trxValueOut",
						"com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue",
						SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "errorList", "java.util.List", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{"stagingReferenceId", "java.lang.String", SERVICE_SCOPE}

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event = (String) map.get("event");
		String path = (String) map.get("path");

//		Set<String> dataFromFdCacheView = new HashSet<String>();
//		dataFromFdCacheView = (HashSet) map.get("dataFromFdCacheView");

//		DefaultLogger
//				.debug(
//						this,
//						"#####################In UploadFdFileCommand ##### size of dataFromFdCacheView map is : "
//								+ dataFromFdCacheView.size());
		// DefaultLogger.debug(this,
		// "#####################In UploadUbsFileCommand ##### size of dataFromUpdLineFacilityMV map is : "+dataFromUpdLineFacilityMV.size());

		DefaultLogger.debug(this,
				"#####################In UploadFdFileCommand: Path of FD File ############:: "
						+ path);
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);

//		int countPass = 0;
//		int countFail = 0;
		long countPass = 0;
		long countFail = 0;
		List finalList = new ArrayList();
		String preUpload = "false";
		ArrayList totalUploadedList = new ArrayList();
		ArrayList consolidateList = new ArrayList();
		ArrayList<OBFdFile> errorList = new ArrayList();
		HashMap mp = new HashMap();
		ArrayList resultList = new ArrayList();
		IUbsErrorLog objUbsErrorLog = new OBUbsErrorLog();
		String fileType = "CSV";
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionSubType("FD_UPLOAD");
		// below code create master transaction
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setCustomer(null); //Setting Cust object to null.
			ctx.setLimitProfile(null);
			File file = new File(path);
			FileInputStream readFile = new FileInputStream(path);
			String fileName = file.getName();
			// DefaultLogger.debug(this,
			// "#####################In UploadFDFileCommand ##### line no 106#######:: "+fileName);
			IFileUploadDao dao = (IFileUploadDao) BeanHouse
					.get("fileUploadDao");
			IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse
					.get("fileUploadJdbc");
			if (fileName.endsWith(".CSV") || fileName.endsWith(".csv")) {
				ProcessDataFile dataFile = new ProcessDataFile();
				resultList = dataFile.processFileUpload(file, FD_UPLOAD);
				HashMap eachDataMap = (HashMap) dataFile.getErrorList();

				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse
						.get("generalParamDao");
				IGeneralParamGroup generalParamGroup = generalParamDao
						.getGeneralParamGroupByGroupType(
								"actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[] generalParamEntries = generalParamGroup
						.getFeedEntries();
				Date applicationDate = new Date();
				for (int i = 0; i < generalParamEntries.length; i++) {
					if (generalParamEntries[i].getParamCode().equals(
							"APPLICATION_DATE")) {
						applicationDate = new Date(generalParamEntries[i]
								.getParamValue());
					}
				}
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String appDate=df.format(applicationDate);
				
				Date d = DateUtil.getDate();
				applicationDate.setHours(d.getHours());
				applicationDate.setMinutes(d.getMinutes());
				applicationDate.setSeconds(d.getSeconds());

				List list = new ArrayList(eachDataMap.values());
				for (int i = 0; i < list.size(); i++) {
					String[][] errList = (String[][]) list.get(i);
					String str = "";
					for (int p = 0; p < 6; p++) {
						for (int j = 0; j < 4; j++) {
							str = str
									+ ((errList[p][j] == null) ? ""
											: errList[p][j] + ";");
						}
						str = str + "||";
					}
					finalList.add(str);
				}
				if (resultList.size() > 0) {
					DefaultLogger.debug(this,
							"#####################In UploadFDFileCommand ############:: "
									+ resultList.size());
					
					//create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("FD_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(fileName);
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
//						for (int i = 0; i < totalUploadedList.size(); i++) {
//							OBFdFile fdRecord = (OBFdFile) totalUploadedList
//									.get(i);
//							if ("PASS".equals(fdRecord.getStatus())) {
//								countPass++;
//							} else if ("FAIL".equals(fdRecord.getStatus())) {
//								countFail++;
//							}
//						}
//						fileObj.setApproveRecords(String.valueOf(countPass));
						
						trxValueOut = getFileUploadProxy().makerCreateFile(ctx,
								fileObj);
					if (trxValueOut != null) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
						for (int index = 0; index < resultList.size(); index++) {
							HashMap eachDataMap1 = (HashMap) resultList.get(index);
							OBFdFile obj=new OBFdFile();
							obj.setDepositNumber(((String) eachDataMap1.get("DEPOSIT_NUMBER")).trim());
							if(null!=eachDataMap1.get("DATE_OF_DEPOSIT")&& !eachDataMap1.get("DATE_OF_DEPOSIT").toString().isEmpty())
							{
								//obj.setDateOfDeposit(eachDataMap1.get("DATE_OF_DEPOSIT").toString());
								
								//Uma Khot:Fd Start date issue, 2012 is shown as 2020
								obj.setDateOfDeposit(new java.sql.Date((simpleDateFormat.parse(eachDataMap1.get("DATE_OF_DEPOSIT").toString()).getTime())));
							}
							if(null!=eachDataMap1.get("DATE_OF_MATURITY")&& !eachDataMap1.get("DATE_OF_MATURITY").toString().isEmpty()) 
							{
								//obj.setDateOfMaturity(eachDataMap1.get("DATE_OF_MATURITY").toString());
								
								//Uma Khot:Fd Start date issue, 2012 is shown as 2020
								obj.setDateOfMaturity(new java.sql.Date((simpleDateFormat.parse(eachDataMap1.get("DATE_OF_MATURITY").toString()).getTime())));
							}
							
							if(null!=eachDataMap1.get("INTEREST_RATE")&& !eachDataMap1.get("INTEREST_RATE").toString().isEmpty())
							{
								obj.setInterestRate(new Double(Double.parseDouble(eachDataMap1.get("INTEREST_RATE").toString())));
							}
							obj.setUploadStatus("Y");
							obj.setReason("FD Details Present in File but not in CLIMS");
							obj.setStatus("FAIL");
						//	OBCommonFdFile commonObj = obj;
							obj.setFileId(fileId);
							totalUploadedList.add(obj);
						
						}
					
						DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBFdFile> batchList = totalUploadedList
									.subList(
											j,
											j + batchSize > totalUploadedList
													.size() ? totalUploadedList
													.size()
													: j + batchSize);
							jdbc.createEntireFdStageFile(batchList);
						}
						DefaultLogger.debug(this,"########## File Data is dumped into Stage Table for FD Upload##################:: ");
						
						//Start:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
						jdbc.updateCashDepositToNull("CMS_STAGE_CASH_DEPOSIT");
						jdbc.updateStageFdUploadStatus();
						
						jdbc.updateStageFdDetailsWithStatusActive(fileId, appDate);
						
						jdbc.updateStageFdDetails(fileId);
						jdbc.updateTempFdDetails(fileId);
						countPass =jdbc.getCount(fileId,"STAGE_FD_FILE_UPLOAD", "PASS");
						countFail=jdbc.getCount(fileId,"STAGE_FD_FILE_UPLOAD", "FAIL");
						totalUploadedList.clear();
						totalUploadedList.addAll(jdbc.getTotalUploadedList(fileId,"STAGE_FD_FILE_UPLOAD"));
						
						//End:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
						
						//call the procedure to compare file and mv data and update the stage(CMS_STAGE_CASH_DEPOSIT) and temp stage(STAGE_FD_FILE_UPLOAD)

//					DefaultLogger.debug(this,"##################### calling procedure SP_FD_UPLOAD ############:: ");	
//					getJdbcTemplate().execute("{call "+getSpFdUpload() + "("+ fileId+")}", new CallableStatementCallback() {
//						
//						@Override
//						public Object doInCallableStatement(CallableStatement cs)
//								throws SQLException, DataAccessException {
//							// TODO Auto-generated method stub
//							cs.executeUpdate();
//							return null;
//						}
//					});
//					DefaultLogger.debug(this,"##################### SP_FD_UPLOAD procedure completed. ############:: ");
					}
			/*		mp = (HashMap) jdbc.insertFdfile(resultList, this,
							fileName, dataFromFdCacheView);
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");

					// jdbc.updateXrefStageAmount(totalUploadedList,"FD-LIMITS");

					errorList = (ArrayList) mp.get("errorList");
					if (totalUploadedList.size() > 0) {
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("FD_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(fileName);
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
						for (int i = 0; i < totalUploadedList.size(); i++) {
							OBFdFile fdRecord = (OBFdFile) totalUploadedList
									.get(i);
							if ("PASS".equals(fdRecord.getStatus())) {
								countPass++;
							} else if ("FAIL".equals(fdRecord.getStatus())) {
								countFail++;
							}
						}
						fileObj.setApproveRecords(String.valueOf(countPass));
						// DefaultLogger.debug(this,
						// "##########4###########before create ##### line no 162#######:: ");
						trxValueOut = getFileUploadProxy().makerCreateFile(ctx,
								fileObj);
						// DefaultLogger.debug(this,
						// "##########5###########after create ###### line no 164######:: ");
						if (trxValueOut != null) {
							for (int i = 0; i < totalUploadedList.size(); i++) {
								OBFdFile fdRecord = (OBFdFile) totalUploadedList
										.get(i);
								fdRecord.setFileId(Long.parseLong(trxValueOut
										.getStagingReferenceID()));
								consolidateList.add(fdRecord);
							}
							for (int i = 0; i < errorList.size(); i++) {
								OBFdFile fdRecord = (OBFdFile) errorList.get(i);
								fdRecord.setFileId(Long.parseLong(trxValueOut
										.getStagingReferenceID()));
								consolidateList.add(fdRecord);
							}
							DefaultLogger.debug(this,
									"##################### consolidateList ############:: "
											+ consolidateList.size());
							int batchSize = 200;
							for (int j = 0; j < consolidateList.size(); j += batchSize) {
								List<OBFdFile> batchList = consolidateList
										.subList(
												j,
												j + batchSize > consolidateList
														.size() ? consolidateList
														.size()
														: j + batchSize);
								jdbc.createEntireFdStageFile(batchList);
							}
							DefaultLogger
									.debug(this,
											"########## Temp Stage Table updated for FD Upload##################:: ");

						}
					} */
				}

			} else {
				fileType = "NOT_CSV";
			}
		} catch (FileUploadException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		// end of master transaction
		resultMap.put("event", event);
		resultMap.put("fileType", fileType);
		resultMap.put("preUpload", preUpload);
		resultMap.put("trxValueOut", trxValueOut);
		if(null != trxValueOut) {
			resultMap.put("stagingReferenceId", trxValueOut.getStagingReferenceID());
		}
		resultMap.put("totalUploadedList", totalUploadedList);
		resultMap.put("errorList", errorList);
		resultMap.put("finalList", finalList);
		resultMap.put("total", String.valueOf(resultList.size()
				+ finalList.size()));
		resultMap.put("correct", String.valueOf(countPass));
		resultMap.put("fail", String.valueOf(countFail + finalList.size()));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//		resultMap.put("depositReceiptNoParamsList", mp
//				.get("depositReceiptNoParamsList"));
//		resultMap.put("cmsRefIdParamsList", mp.get("cmsRefIdParamsList"));
		return returnMap;
	}

}
