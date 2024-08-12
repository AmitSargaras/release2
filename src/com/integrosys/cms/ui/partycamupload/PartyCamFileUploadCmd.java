/**
\ * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.partycamupload;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBPartyCamFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.CSVReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFilePartyCam;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;
import com.integrosys.cms.batch.partycam.OBPartyCamErrorLog;
import com.integrosys.cms.ui.partycamupload.proxy.IPartyCamUploadProxyManager;

/**
 * @author $Author: Abhijeett J$ Command for UBS Upload
 */
public class PartyCamFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String PARTYCAM_UPLOAD = "PartyCamUpload";
	private IPartyCamUploadProxyManager partyCamuploadProxy;
	

	public IPartyCamUploadProxyManager getPartyCamuploadProxy() {
		return partyCamuploadProxy;
	}

	public void setPartyCamuploadProxy(
			IPartyCamUploadProxyManager partyCamuploadProxy) {
		this.partyCamuploadProxy = partyCamuploadProxy;
	} 
	
/*	private IFileUploadProxyManager fileUploadProxy;
	
	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}
*/
	/**
	 * Default Constructor
	 */

	public PartyCamFileUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "partyCamUploadObj", "com.integrosys.cms.ui.partycamupload.OBPartyCamUpload", FORM_SCOPE },
				
				{ "dataFromPartyCamUploadMv", "java.util.Set", SERVICE_SCOPE },
				{ "riskGrade", "java.util.Set", SERVICE_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			/*	{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "partyCamUploadObj", "com.integrosys.cms.ui.partycamupload.OBPartyCamUpload", FORM_SCOPE },
				{ "errorList", "java.util.HashMap", REQUEST_SCOPE },
				{ "rowCount", "java.lang.Integer", REQUEST_SCOPE },
				{ "fileUploadPending", "java.lang.String", REQUEST_SCOPE },
				{ "fileCheckSum", "java.lang.String", REQUEST_SCOPE }, */
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
			/*	{ "objPartyCamErrorLog", "com.integrosys.cms.batch.partycam.IPartyCamErrorLog", REQUEST_SCOPE },
				{ "objPartyCamErrorLogService", "com.integrosys.cms.batch.partycam.IPartyCamErrorLog", SERVICE_SCOPE },
				{ "partyCamErrorLogList", "java.util.List", SERVICE_SCOPE } */
				
				{"trxValueOut","com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue",SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "errorList", "java.util.List", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		HashMap exceptionMap = new HashMap();	
		
		String strError = "";
		String fileType = "";
	/*	String uploadId = "";
		Set errMsg = null; */
		IPartyCamErrorLog objPartyCamErrorLog = new OBPartyCamErrorLog();
		
		Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		/* set local */
		CSVReader.setLocale(local);
	/*	Set stErrorDet = null;
		int count = 0; */
	//	IPartyCamErrDetLog obPartyCamErrDetLog[] = null;
		
		ArrayList<OBPartyCamFile> errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		Set<String> dataFromPartyCamUploadMv = new HashSet<String>();
		dataFromPartyCamUploadMv = (HashSet) map.get("dataFromPartyCamUploadMv");
		
		Set<String> riskGrade = new HashSet<String>();
		riskGrade = (HashSet) map.get("riskGrade");
		
		long countPass = 0;
		long countFail = 0;
		
		int size=0;
		List finalList = new ArrayList<PartyCamError>();
		ArrayList totalUploadedList = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("PARTYCAM_UPLOAD");
		//trxValueOut.setTransactionSubType("PARTYCAM_UPLOAD");
		try {

			OBPartyCamUpload partyCamUpload = (OBPartyCamUpload) map.get("partyCamUploadObj");
			
			if(partyCamUpload.getFileUpload().getFileName().isEmpty()){
				exceptionMap.put("partycamfileuploadError", new ActionMessage("label.partycam.file.empty"));
				
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else if (!partyCamUpload.getFileUpload().getFileName().endsWith(".csv")
					&& !partyCamUpload.getFileUpload().getFileName().endsWith(".CSV")) {
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {
				
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null); //Setting Cust object to null.
				ctx.setLimitProfile(null);
				
				ProcessDataFilePartyCam dataFile = new ProcessDataFilePartyCam();
			    resultList = dataFile.processFile(partyCamUpload.getFileUpload(), PARTYCAM_UPLOAD);
//				try {
//					uploadId = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_UPLOAD, true);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
	/*			obPartyCamErrDetLog = new IPartyCamErrDetLog[dataFile.getErrorList().size()];
				for (int j = 0; j < dataFile.getMaxCount(); j++) {

					String[][] errorData = new String[50][2];
					errorData = (String[][]) dataFile.getErrorList().get(new Integer(j));

					if (!(errorData == null)) {
						errMsg = new HashSet();
						for (int k = 0; k <= errorData.length - 1; k++) {
							if (errorData[k][0] != null) {
//								DefaultLogger.debug(this, errorData[k][0] + " Value: " + errorData[k][1]);
								errMsg.add(errorData[k][0]);
							}
						}
						obPartyCamErrDetLog[count] = new OBPartyCamErrDetLog();
						obPartyCamErrDetLog[count].setPtId(uploadId);
						obPartyCamErrDetLog[count].setRecordNo(j + 1 + "");
						obPartyCamErrDetLog[count].setErrorMsg("Validation Error in " + errMsg.toString());
						obPartyCamErrDetLog[count].setTime(new Date());
						count++;
					}

				}
				objPartyCamErrorLog = getPartyCamuploadProxy().insertPartyCamfile(resultList, partyCamUpload.getFileUpload().getFileName(),
						uploadId, obPartyCamErrDetLog);
				stErrorDet = objPartyCamErrorLog.getErrEntriesSet();
				/*
				 * while(itemIter.hasNext()){ obPartyCamErrDetLog =
				 * (IPartyCamErrDetLog)itemIter.next(); }
				 */  
				
				IFileUploadDao dao = (IFileUploadDao) BeanHouse.get("fileUploadDao");
				IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				
				IFileUploadJdbc jdbcFile = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				Set<String> ramType = new HashSet<String>();
				ramType = (HashSet) jdbcFile.getRatingType();
		
				HashMap eachDataMap = (HashMap) dataFile.getErrorList();
				
				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup = generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
				Date applicationDate = new Date();
				for (int i = 0; i < generalParamEntries.length; i++) {
					if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
				applicationDate = new Date(generalParamEntries[i].getParamValue());
					}
				}
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String appDate=df.format(applicationDate);
				
				Date d = DateUtil.getDate();
				applicationDate.setHours(d.getHours());
				applicationDate.setMinutes(d.getMinutes());
				applicationDate.setSeconds(d.getSeconds());
				
				Calendar c =Calendar.getInstance();
				c.setTime(d);
				Integer currentYear=new Integer(c.get(Calendar.YEAR));
				Integer previousYear=currentYear -new Integer(1);
				Integer previousYear2=currentYear -new Integer(2);
				
				List list = new ArrayList(eachDataMap.values());
				for (int i = 0; i < list.size(); i++) {
					String[][] errList = (String[][]) list.get(i);
//					String str = "";
					for (int p = 0; p < errList.length; p++) {
				//		for (int j = 0; j < 4; j++) {
							PartyCamError partyCamError = new PartyCamError();
							if(null != errList[p][0]){
							partyCamError.setColumnName(errList[p][0]);
							partyCamError.setErrorMessage(errList[p][1]);
							partyCamError.setFileRowNo(errList[p][3]);
							if(100 == finalList.size()){
								break;
							}
							finalList.add(partyCamError);
							}
//							str = str
//									+ ((errList[p][j] == null) ? ""
//											: errList[p][j] + ";");
//						}
//						str = str + "||";
					}
					if(100 == finalList.size()){
						break;
					}
//					finalList.add(str);
				}
				
				if(finalList.size()==0){
				if (resultList.size() > 0) {
					
					 size = resultList.size();
					DefaultLogger.debug(this,
							"#####################In PartyCamFileUploadCmd ############:: "
									+ size);
					
					//create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("PARTYCAM_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(partyCamUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
						trxValueOut = getPartyCamuploadProxy().makerCreateFile(ctx,
								fileObj);
					if (trxValueOut != null) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
					long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
					 // ILimitDAO limitDao = LimitDAOFactory.getDAO();
							for (int index = 0; index < size; index++) {
								HashMap eachDataMap1 = (HashMap) resultList.get(index);
								OBPartyCamFile obj = new OBPartyCamFile();
								obj.setPartyId(((String) eachDataMap1.get("PARTY_ID")).trim());
								if (null != eachDataMap1.get("CAM_DATE")
										&& !eachDataMap1.get("CAM_DATE").toString().trim().isEmpty()) {
									obj.setCamDate(new java.sql.Date((simpleDateFormat
											.parse(eachDataMap1.get("CAM_DATE").toString().trim()).getTime())));
								}
								if (null != eachDataMap1.get("CAM_LOGIN_DATE")
										&& !eachDataMap1.get("CAM_LOGIN_DATE").toString().trim().isEmpty()) {
									obj.setCamLoginDate(new java.sql.Date((simpleDateFormat
											.parse(eachDataMap1.get("CAM_LOGIN_DATE").toString().trim()).getTime())));
								}

								if (null != eachDataMap1.get("RAM_RATING")
										&& !eachDataMap1.get("RAM_RATING").toString().trim().isEmpty()) {
									obj.setRamRating(new Integer(
											Integer.parseInt(eachDataMap1.get("RAM_RATING").toString().trim())));
								}
								if (null != eachDataMap1.get("RAM_RATING_YEAR")
										&& !eachDataMap1.get("RAM_RATING_YEAR").toString().trim().isEmpty()) {
									obj.setRamRatingYear(new Integer(
											Integer.parseInt(eachDataMap1.get("RAM_RATING_YEAR").toString().trim())));
								}
								if (null != eachDataMap1.get("CUSTOMER_RAM_ID")
										&& !eachDataMap1.get("CUSTOMER_RAM_ID").toString().trim().isEmpty()) {
									obj.setCustomerRamId(new Long(
											Long.parseLong(eachDataMap1.get("CUSTOMER_RAM_ID").toString().trim())));
								}
								if (null != eachDataMap1.get("CAM_EXPIRY_DATE")
										&& !eachDataMap1.get("CAM_EXPIRY_DATE").toString().trim().isEmpty()) {
									obj.setCamExpiryDate(new java.sql.Date((simpleDateFormat
											.parse(eachDataMap1.get("CAM_EXPIRY_DATE").toString().trim()).getTime())));
								}
								if (null != eachDataMap1.get("CAM_EXTENSION_DATE")
										&& !eachDataMap1.get("CAM_EXTENSION_DATE").toString().isEmpty()) {
									obj.setCamExtensionDate(new java.sql.Date((simpleDateFormat
											.parse(eachDataMap1.get("CAM_EXTENSION_DATE").toString().trim())
											.getTime())));
								}
								if (null != eachDataMap1.get("RATING_TYPE")
										&& !eachDataMap1.get("RATING_TYPE").toString().trim().isEmpty()) {
									obj.setRamType(((String) eachDataMap1.get("RATING_TYPE")).trim());
								}
								if (null != eachDataMap1.get("RAM_FINALIZATION_DATE")
										&& !eachDataMap1.get("RAM_FINALIZATION_DATE").toString().isEmpty()) {
									obj.setRamFinalizationDate(new java.sql.Date((simpleDateFormat
											.parse(eachDataMap1.get("RAM_FINALIZATION_DATE").toString().trim())
											.getTime())));
								}
								if (null != eachDataMap1.get("FUNDED_AMOUNT")
										&& !eachDataMap1.get("FUNDED_AMOUNT").toString().trim().isEmpty()) {
									obj.setFundedAmount(((String) eachDataMap1.get("FUNDED_AMOUNT")).trim()); 
									/*obj.setFundedAmount(new Double(
											Double.parseDouble(eachDataMap1.get("FUNDED_AMOUNT").toString().trim())));*/
								}
								//MORATORIUM REGULATORY UPLOAD
								if (null != eachDataMap1.get("NON_FUNDED_AMOUNT")
										&& !eachDataMap1.get("NON_FUNDED_AMOUNT").toString().trim().isEmpty()) {
									/*obj.setNonfundedAmount(new Double(
											Double.parseDouble(eachDataMap1.get("NON_FUNDED_AMOUNT").toString().trim())));*/
									obj.setNonfundedAmount(((String) eachDataMap1.get("NON_FUNDED_AMOUNT")).trim()); 
									System.out.println("Value of non funded amount"+obj.getNonfundedAmount());
								}
								if (null != eachDataMap1.get("RM_EMPLOYEE_CODE")
										&& !eachDataMap1.get("RM_EMPLOYEE_CODE").toString().isEmpty()) {
									obj.setRmEmployeeCode(eachDataMap1.get("RM_EMPLOYEE_CODE").toString().trim());
								}

								if (dataFromPartyCamUploadMv.contains(obj.getPartyId())) {
									// obj.setUploadStatus("Y");
									// obj.setReason("Party and CAM details are updated.");
									// obj.setStatus("PASS");
									String isFail="No";
									boolean camDateNull = false;
									boolean camExpiryDateNull = false;

									if (null == obj.getCamDate() || "".equals(obj.getCamDate())) {
										camDateNull = true;
									}
									if (null == obj.getCamExpiryDate() || "".equals(obj.getCamExpiryDate())) {
										camExpiryDateNull = true;
									}

									if (null != obj.getRamRating()
											&& !riskGrade.contains(String.valueOf(obj.getRamRating()))) {
										obj.setUploadStatus("N");
										obj.setReason("Invalid RAM Rating.");
										obj.setStatus("FAIL");
										isFail="Yes";

									} else if (null != obj.getRamRatingYear()
											&& (!((0 == obj.getRamRatingYear().compareTo(currentYear))
													|| (0 == obj.getRamRatingYear().compareTo(previousYear))
													|| (0 == obj.getRamRatingYear().compareTo(previousYear2))))) {
										obj.setUploadStatus("N");
										obj.setReason("Invalid RAM Rating Year.");
										obj.setStatus("FAIL");
										isFail="Yes";
									} else if (null != obj.getCamDate() && !"".equals(obj.getCamDate())
											&& (d.compareTo(obj.getCamDate()) < 0)) {

										obj.setUploadStatus("N");
										obj.setReason("CAM Date cannot be later than Current Date.");
										obj.setStatus("FAIL");
										isFail="Yes";
									} else if (null != obj.getCamLoginDate() && !"".equals(obj.getCamLoginDate())
											&& (d.compareTo(obj.getCamLoginDate()) < 0)) {

										obj.setUploadStatus("N");
										obj.setReason("CAM Login Date cannot be later than Current Date.");
										obj.setStatus("FAIL");
										isFail="Yes";
									} else if (null != obj.getCamExpiryDate() && !"".equals(obj.getCamExpiryDate())
											&& !camDateNull
											&& (obj.getCamExpiryDate().compareTo(obj.getCamDate())) < 0) {

										obj.setUploadStatus("N");
										obj.setReason("CAM Expiry Date cannot be earlier than CAM Date.");
										obj.setStatus("FAIL");
										isFail="Yes";

									} else if (null != obj.getCamExtensionDate()
											&& !"".equals(obj.getCamExtensionDate())) {
										if (!camDateNull
												&& (obj.getCamExtensionDate().compareTo(obj.getCamDate())) < 0) {

											obj.setUploadStatus("N");
											obj.setReason("CAM Extension Date cannot be earlier than CAM Date.");
											obj.setStatus("FAIL");
											isFail="Yes";
										} else if (!camExpiryDateNull
												&& (obj.getCamExtensionDate().compareTo(obj.getCamExpiryDate())) < 0) {
											obj.setUploadStatus("N");
											obj.setReason("CAM Extension Date cannot be earlier than CAM Expiry Date.");
											obj.setStatus("FAIL");
											isFail="Yes";
										} 
									} 

									ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
									
									if (null != obj.getCamExpiryDate()
											&& !"".equals(obj.getCamExpiryDate()) && (null == obj.getCamExtensionDate()
													|| "".equals(obj.getCamExtensionDate())) && null != obj.getPartyId())  {
										
										Date camExtensionDate = customerDAO.getCamExpiryORExtensionDate(2,obj.getPartyId());
										
										if(null != camExtensionDate && !"".equals(camExtensionDate) && camExtensionDate.compareTo(obj.getCamExpiryDate()) < 0) {
											obj.setUploadStatus("N");
											obj.setReason("CAM Expiry Date cannot be later than 'CAM Details'=> CAM Extension Date.");
											obj.setStatus("FAIL");
											isFail="Yes";
										}
									}
									
									
									if (null != obj.getCamExtensionDate()
											&& !"".equals(obj.getCamExtensionDate()) && (null == obj.getCamExpiryDate()
													|| "".equals(obj.getCamExpiryDate())) && null != obj.getPartyId())  {
										
										Date camExpiryDate = customerDAO.getCamExpiryORExtensionDate(1,obj.getPartyId());
										
										if(null != camExpiryDate && !"".equals(camExpiryDate) && obj.getCamExtensionDate().compareTo(camExpiryDate) < 0) {
											obj.setUploadStatus("N");
											obj.setReason("CAM Extension Date cannot be earlier than 'CAM Details'=>CAM Expiry Date.");
											obj.setStatus("FAIL");
											isFail="Yes";
										}
									}
									
									if (null != obj.getRamType()
											&& !ramType.contains(String.valueOf(obj.getRamType()))) {
										obj.setUploadStatus("N");
										obj.setReason("Invalid RAM TYPE.");
										obj.setStatus("FAIL");
										isFail="Yes";

									}
									
									if (null != obj.getRamFinalizationDate() && !"".equals(obj.getRamFinalizationDate())
											&& (d.compareTo(obj.getRamFinalizationDate()) < 0)) {

										obj.setUploadStatus("N");
										obj.setReason("Ram Finalization Date cannot be later than Current Date.");
										obj.setStatus("FAIL");
										isFail="Yes";
									}
									
									//MORATORIUM REGULATORY UPLOAD
									/*BigDecimal zero = new BigDecimal("0");
									if(null != obj.getNonfundedAmount() && !obj.getNonfundedAmount().toString().trim().isEmpty()) {
										if (obj.getNonfundedAmount().compareTo(zero)==-1) {
											obj.setUploadStatus("N");
											obj.setReason("Total Non Funded Amount can not be negative.");
											obj.setStatus("FAIL");
											isFail="Yes";
										}
									}*/
									
									
								/*	if (null != obj.getFundedAmount() && !"".equals(obj.getFundedAmount())) {
										Double totalFundedAmt =jdbcFile.getTotalFundedAmount(obj.getPartyId());
										if(totalFundedAmt!=null && totalFundedAmt > obj.getFundedAmount()) {
											obj.setUploadStatus("N");
											obj.setReason("Total funded amount can not be decreased.");
											obj.setStatus("FAIL");
											isFail="Yes";
										}
									}*/
									
									if (null != obj.getRmEmployeeCode()
											&& !"".equals(obj.getRmEmployeeCode())) {
										boolean isValid = isValidRMEmpCode(obj.getRmEmployeeCode());
										if(!isValid) {
											obj.setUploadStatus("N");
											obj.setReason("Invalid RM Employee Code.");
											obj.setStatus("FAIL");
											isFail="Yes";
										}
									}
									
									if(isFail.equals("No")) {
										obj.setUploadStatus("Y");
										obj.setReason("Party and CAM Details updated.");
										obj.setStatus("PASS");
										countPass++;
									}

								}else {
									obj.setUploadStatus("N");
									obj.setReason(
											"Party doesn't not exist OR Party or CAM are pending for authorization.");
									obj.setStatus("FAIL");
								}
								// OBCommonFdFile commonObj = obj;
								obj.setFileId(fileId);
								totalUploadedList.add(obj);
								// String ramratingyear=String.valueOf(obj.getRamRatingYear());
								// String checkListId=limitDao.getChecklistIdByPartyId(obj.getPartyId());
								// limitDao.updateChecklistDetails(checkListId,String.valueOf(currentYear),ramratingyear);
							}
					
						DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBPartyCamFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
							System.out.println(batchList);
							jdbc.createEntirePartyCamStageFile(batchList);
						}
		
						DefaultLogger.debug(this,"########## File Data is dumped into Stage Table for PARTY CAM Upload##################:: ");
						
						jdbc.updateUploadStatusToNull("SCI_LE_CRI_STAGING");
						jdbc.updateUploadStatusToNull("STAGE_LIMIT_PROFILE");
						
						DefaultLogger.debug(this,"spUpdatePartyCamUpload Stage started:");
						jdbc.spUpdatePartyCamUpload(String.valueOf(fileId),"STAGE");
						DefaultLogger.debug(this,"spUpdatePartyCamUpload Stage finished:");
						
						countFail=size-countPass;
							
					}
				}
			}
			}
		
			resultMap.put("trxValueOut", trxValueOut);
			resultMap.put("totalUploadedList", totalUploadedList);
			resultMap.put("errorList", errorList);
			resultMap.put("finalList", finalList);
			resultMap.put("total", String.valueOf(resultList.size()+ finalList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail + finalList.size()));
			
			resultMap.put("fileType", fileType);
		/*	resultMap.put("errorEveList", strError);
			resultMap.put("objPartyCamErrorLog", objPartyCamErrorLog);
			// Added BY Anil for Pagination
			List partyCamErrorLogList = new ArrayList();
			if (objPartyCamErrorLog.getErrEntriesSet() != null && objPartyCamErrorLog.getErrEntriesSet().size() > 0)
				partyCamErrorLogList.addAll(objPartyCamErrorLog.getErrEntriesSet());

			DefaultLogger.debug(this, "-------PartyCamErrorLog size------" + partyCamErrorLogList.size());
			resultMap.put("objPartyCamErrorLogService", objPartyCamErrorLog);
			resultMap.put("partyCamErrorLogList", partyCamErrorLogList);  */
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

			return returnMap;

		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

	}
	private boolean isValidRMEmpCode(String rmEmpCode){
		boolean valid= false;	
		
		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS = 'ACTIVE'";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;
	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	
	            	String count = rs.getString("count");
	              if(count.equals("1")){
	            	   valid = true;
	               }
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }
		return valid;
	}
}
