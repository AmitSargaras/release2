/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.createfacupload;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileCreatefacilityline;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;


public class CreateFacilitylineFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String CREATE_FACILITYLINEDETAILS_UPLOAD = "CreatefacilitylineUpload";


	/**
	 * Default Constructor
	 */

	public CreateFacilitylineFileUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to the
	 * doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "createfacilitylineUploadObj",
						"com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineUpload", FORM_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "fileType", "java.lang.String", REQUEST_SCOPE },
				/*{ "trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue",
						SERVICE_SCOPE },*/
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "errorList", "java.util.List", SERVICE_SCOPE }, { "finalList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE }, { "fail", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the results
	 * back into the HashMap.
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
		//IReleaselinedetailsErrorLog objReleaselinedetailsErrorLog = new OBReleaselinedetailsErrorLog();
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();

		long countPass = 0;
		long countFail = 0;

		int size = 0;
		
		LimitDAO limitDao=new LimitDAO();
	//	List finalList = new ArrayList<ReleaselinedetailsError>();
		ArrayList<OBCreatefacilitylineFile> totalUploadedList = new ArrayList<OBCreatefacilitylineFile>();
		ArrayList exceldatalist = new ArrayList();
		HashMap<String,List<OBCreatefacilitylineFile>> dummRefFileMap=new HashMap<String, List<OBCreatefacilitylineFile>>();
		/*IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("RLD_UPLOAD");*/
		
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		
		try {

			OBCreatefacilitylineUpload createfacilitylineUpload = (OBCreatefacilitylineUpload) map
					.get("createfacilitylineUploadObj");
			DefaultLogger.debug(this,"#####################In CreateFacilitylineFileUploadCmd ############:: " + createfacilitylineUpload.toString());
			if (createfacilitylineUpload.getFileUpload().getFileName().isEmpty()) {
				exceptionMap.put("createfacilitylineuploadError",
						new ActionMessage("label.facilitylineupload.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			} else if (!createfacilitylineUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !createfacilitylineUpload.getFileUpload().getFileName().endsWith(".XLS")
					&& !createfacilitylineUpload.getFileUpload().getFileName().endsWith(".XLSX")
					&& !createfacilitylineUpload.getFileUpload().getFileName().endsWith(".xlsx")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {

				OBTrxContext context = (OBTrxContext) map.get("theOBTrxContext");
				String uploadedby= context.getUser().getLoginID();
//				context.getUser().get
//				context.setCustomer(null);
//				context.setLimitProfile(null);

				ProcessDataFileCreatefacilityline dataFile = new ProcessDataFileCreatefacilityline();
				resultList = dataFile.processFile(createfacilitylineUpload.getFileUpload(), CREATE_FACILITYLINEDETAILS_UPLOAD);
				IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				HashMap eachDataMap = (HashMap) dataFile.getErrorList();

				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup = generalParamDao
						.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
				Date applicationDate = new Date();
				for (int i = 0; i < generalParamEntries.length; i++) {
					if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
						applicationDate.setDate(new Date(generalParamEntries[i].getParamValue()).getDate());
						applicationDate.setMonth(new Date(generalParamEntries[i].getParamValue()).getMonth());
						applicationDate.setYear(new Date(generalParamEntries[i].getParamValue()).getYear());
						//applicationDate = new Date(generalParamEntries[i].getParamValue());
					}
				}
				
				//fetch fcc branch and store in map for reference.
				
				IFCCBranchDao fccBranchDao = (IFCCBranchDao) BeanHouse.get("fccBranchDao");
				List branchAllowedList = fccBranchDao.getFccBranchList();
				HashMap fccBranchIdMap=new HashMap<String, String>();
//				try {

					for (int i = 0; i < branchAllowedList.size(); i++) {
						IFCCBranch fccBranch = (IFCCBranch) branchAllowedList.get(i);
						if (fccBranch.getStatus().equals("ACTIVE")) {

							String key = fccBranch.getBranchCode();
							String value = Long.toString(fccBranch.getId());
							
							fccBranchIdMap.put(key, value);
						}
					}
//				} catch (Exception ex) {
//					
//					ex.printStackTrace();
//				}
/*
				List list = new ArrayList(eachDataMap.values());
				for (int i = 0; i < list.size(); i++) {
					String[][] errList = (String[][]) list.get(i);
					for (int p = 0; p < 8; p++) {
						ReleaselinedetailsError releaselinedetailsError = new ReleaselinedetailsError();
						if (null != errList[p][0]) {
							releaselinedetailsError.setColumnName(errList[p][0]);
							releaselinedetailsError.setErrorMessage(errList[p][1]);
							releaselinedetailsError.setFileRowNo(errList[p][3]);
							if (100 == finalList.size()) {
								break;
							}
							finalList.add(releaselinedetailsError);
						}
					}
					if (100 == finalList.size()) {
						break;
					}
				}*/

			/*	if (finalList.size() == 0) {*/
				
				DateFormat dfs=new SimpleDateFormat("dd/MMM/yyyy");
				
					if (resultList.size() > 0) {

						size = resultList.size();
						DefaultLogger.debug(this,
								"#####################In CreateFacilitylineFileUploadCmd ############:: " + size);
						// create stage entry for file id
					/*	IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("RLD_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(createfacilitylineUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList.size()));*/
					//	trxValueOut = getReleaselinedetailsuploadProxy().makerCreateFile(ctx, fileObj);// ENTRY IN
																										// CMS_FILE_UPLOAD
					//	long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
					//	if (trxValueOut != null) {
							DateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
							Date uploadDate=new Date();
							for (int i = 1; i < resultList.size(); i++) {
								rowlist = (ArrayList) resultList.get(i);
								DefaultLogger.debug(this,"#####################In CreateFacilitylineFileUploadCmd ############ rowlist.size():: " + rowlist.size());
								exceldatalist.clear();
								for (int j = 0; j < rowlist.size(); j++) {
									if (createfacilitylineUpload.getFileUpload().getFileName().endsWith(".XLS")
											|| createfacilitylineUpload.getFileUpload().getFileName()
													.endsWith(".xls")) {
										HSSFCell cell = (HSSFCell) rowlist.get(j);
										if (cell == null)
											exceldatalist.add(null);
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
											/*if(cell.getColumnIndex()==9 || cell.getColumnIndex()==10 || cell.getColumnIndex()==11 || cell.getColumnIndex()==16 || cell.getColumnIndex()==31) {
											exceldatalist.add(cell.getNumericCellValue());
										}
										else{*/
											int val = (int)cell.getNumericCellValue(); 
									        String strCellValue = String.valueOf(val);
									        exceldatalist.add(strCellValue);
										//}
										}
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
											exceldatalist.add(cell.getStringCellValue());
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											exceldatalist.add(null);
										
									} else if (createfacilitylineUpload.getFileUpload().getFileName().endsWith(".XLSX")
											|| createfacilitylineUpload.getFileUpload().getFileName()
													.endsWith(".xlsx")) {
										XSSFCell cell = (XSSFCell) rowlist.get(j);
										if (cell == null)
											exceldatalist.add(null);
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
												/*if(cell.getColumnIndex()==9 || cell.getColumnIndex()==10 || cell.getColumnIndex()==11 || cell.getColumnIndex()==16 || cell.getColumnIndex()==31) {
													exceldatalist.add(cell.getNumericCellValue());
												}
												else{*/
													int val = (int)cell.getNumericCellValue(); 
											        String strCellValue = String.valueOf(val);
											        exceldatalist.add(strCellValue);
												//}
										}
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
											exceldatalist.add(cell.getStringCellValue());
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
											exceldatalist.add(null);
									}
									DefaultLogger.debug(this,"#####################In CreateFacilitylineFileUploadCmd ############exceldatalist:: " + exceldatalist);
								}

								OBCreatefacilitylineFile obj = new OBCreatefacilitylineFile();
							//	obj.setTempCounter(i);
								DefaultLogger.debug(this,"#####################In CreateFacilitylineFileUploadCmd ############exceldatalist.size():: " + exceldatalist.size());
								obj.setFileName(createfacilitylineUpload.getFileUpload().getFileName());
								if (null != exceldatalist.get(0) && !exceldatalist.get(0).toString().trim().isEmpty()) {
									obj.setPartyId((String.valueOf(exceldatalist.get(0)).trim()).toUpperCase());
								} else {
									obj.setPartyId(""); 
								}
								
								if (null != exceldatalist.get(1) && !exceldatalist.get(1).toString().trim().isEmpty()) {
									obj.setFacilityCategory((String.valueOf(exceldatalist.get(1)).trim()));
								} else {
									obj.setFacilityCategory(""); 
								}
								if (null != exceldatalist.get(2) && !exceldatalist.get(2).toString().trim().isEmpty()) {
									obj.setFacilityName((String.valueOf(exceldatalist.get(2)).trim()));
								} else {
									obj.setFacilityName(""); 
								}
								if (null != exceldatalist.get(3) && !exceldatalist.get(3).toString().trim().isEmpty()) {
									obj.setDummyRefId((String.valueOf(exceldatalist.get(3)).trim()));
								} else {
									obj.setDummyRefId(""); 
								}
								if (null != exceldatalist.get(4) && !exceldatalist.get(4).toString().trim().isEmpty()) {
									obj.setSystem((String.valueOf(exceldatalist.get(4)).trim()));
								} else {
									obj.setSystem(""); 
								}

								if (null != exceldatalist.get(5) && !exceldatalist.get(5).toString().trim().isEmpty()) {
									obj.setGrade((String.valueOf(exceldatalist.get(5)).trim()));
								} else {
									obj.setGrade(""); 
								}

								if (null != exceldatalist.get(6) && !exceldatalist.get(6).toString().trim().isEmpty()) {
									obj.setCurrency((String.valueOf(exceldatalist.get(6)).trim()));
								} else {
									obj.setCurrency("");
								}

								if (null != exceldatalist.get(7) && !exceldatalist.get(7).toString().trim().isEmpty()) {
									obj.setIsReleased((String.valueOf(exceldatalist.get(7)).trim()));
								} else {
									obj.setIsReleased("");
								}

								if (null != exceldatalist.get(8) && !exceldatalist.get(8).toString().trim().isEmpty()) {
									obj.setIsAdhoc((String.valueOf(exceldatalist.get(8)).trim()));
								} else {
									obj.setIsAdhoc("");
								}

								if (null != exceldatalist.get(9) && !exceldatalist.get(9).toString().trim().isEmpty()) {
									String s9=UIUtil.removeComma(String.valueOf(exceldatalist.get(9)).trim());
									try {
									obj.setAdhoclimitAmt((new BigDecimal(s9).toPlainString()));
									}catch(Exception e) {
										obj.setAdhoclimitAmt((String.valueOf(exceldatalist.get(9)).trim()));
									}
									
								} else {
									obj.setAdhoclimitAmt("");
								}
								if (null != exceldatalist.get(10) && !exceldatalist.get(10).toString().trim().isEmpty()) {
									String s10=UIUtil.removeComma(String.valueOf(exceldatalist.get(10)).trim());
									try {
									obj.setSanctionedAmt((new BigDecimal(s10).toPlainString()));
									}catch(Exception e) {
										obj.setSanctionedAmt((String.valueOf(exceldatalist.get(10)).trim()));
									}
								} else {
									obj.setSanctionedAmt("");
								}
								if (null != exceldatalist.get(11) && !exceldatalist.get(11).toString().trim().isEmpty()) {
									
									String s11=UIUtil.removeComma(String.valueOf(exceldatalist.get(11)).trim());
									try {
									obj.setReleasableAmt((new BigDecimal(s11).toPlainString()));
									}catch(Exception e) {
										obj.setReleasableAmt((String.valueOf(exceldatalist.get(11)).trim()));
									}
									
								} else {
									obj.setReleasableAmt("");
								}
								if (null != exceldatalist.get(12) && !exceldatalist.get(12).toString().trim().isEmpty()) {
									obj.setSystemID((String.valueOf(exceldatalist.get(12)).trim()));
								} else {
									obj.setSystemID("");
								}
								if (null != exceldatalist.get(13) && !exceldatalist.get(13).toString().trim().isEmpty()) {
									obj.setLineNo((String.valueOf(exceldatalist.get(13)).trim()));
								} else {
									obj.setLineNo("");
								}
								if (null != exceldatalist.get(14) && !exceldatalist.get(14).toString().trim().isEmpty()) {
									obj.setSerialNo((String.valueOf(exceldatalist.get(14)).trim()));
								} else {
									obj.setSerialNo("");
								}
								if (null != exceldatalist.get(15) && !exceldatalist.get(15).toString().trim().isEmpty()) {
									obj.setLiabBranch((String.valueOf(exceldatalist.get(15)).trim()));
								} else {
									obj.setLiabBranch("");
								}
								if (null != exceldatalist.get(16) && !exceldatalist.get(16).toString().trim().isEmpty()) {
									String s16=UIUtil.removeComma(String.valueOf(exceldatalist.get(16)).trim());
									try {
									obj.setReleaseAmount((new BigDecimal(s16).toPlainString()));
									}catch(Exception e) {
										obj.setReleaseAmount((String.valueOf(exceldatalist.get(16)).trim()));
									}
									
									
								} else {
									obj.setReleaseAmount("");
								}
								if (null != exceldatalist.get(17) && !exceldatalist.get(17).toString().trim().isEmpty()) {
									/*Date dateUpl1=dfs.parse((String) exceldatalist.get(17));
									DefaultLogger.debug(this, "dateUpl1 limitStartdate:"+dateUpl1);
									obj.setLimitStartDate(dateUpl1);*/
									obj.setLimitStartDate((String) exceldatalist.get(17));
								} else {
									obj.setLimitStartDate(null);
								}
								if (null != exceldatalist.get(18) && !exceldatalist.get(18).toString().trim().isEmpty()) {
									obj.setAvailable((String.valueOf(exceldatalist.get(18)).trim()));
								} else {
									obj.setAvailable("");
								}
								if (null != exceldatalist.get(19) && !exceldatalist.get(19).toString().trim().isEmpty()) {
									obj.setRevolvingLine((String.valueOf(exceldatalist.get(19)).trim()));
								} else {
									obj.setRevolvingLine("");
								}
								if (null != exceldatalist.get(20) && !exceldatalist.get(20).toString().trim().isEmpty()) {
									obj.setSendToFile((String.valueOf(exceldatalist.get(20)).trim()));
								} else {
									obj.setSendToFile("");
								}
								if (null != exceldatalist.get(21) && !exceldatalist.get(21).toString().trim().isEmpty()) {
									/*Date dateUpl1=dfs.parse((String) exceldatalist.get(21));
									DefaultLogger.debug(this, "dateUpl1 limitExpirydate:"+dateUpl1);
									obj.setLimitExpiryDate(dateUpl1);*/
									obj.setLimitExpiryDate((String) exceldatalist.get(21));
								} else {
									obj.setLimitExpiryDate(null);
								}
								if (null != exceldatalist.get(22) && !exceldatalist.get(22).toString().trim().isEmpty()) {
									obj.setFreeze((String.valueOf(exceldatalist.get(22)).trim()));
								} else {
									obj.setFreeze("");
								}
								if (null != exceldatalist.get(23) && !exceldatalist.get(23).toString().trim().isEmpty()) {
									obj.setSegment1((String.valueOf(exceldatalist.get(23)).trim()));
								} else {
									obj.setSegment1("");
								}
								if (null != exceldatalist.get(24) && !exceldatalist.get(24).toString().trim().isEmpty()) {
									obj.setIsCapitalMarketExpo((String.valueOf(exceldatalist.get(24)).trim()));
								} else {
									obj.setIsCapitalMarketExpo("");
								}
								if (null != exceldatalist.get(25) && !exceldatalist.get(25).toString().trim().isEmpty()) {
									obj.setIsRealEstateExpo((String.valueOf(exceldatalist.get(25)).trim()));
								} else {
									obj.setIsRealEstateExpo("");
								}
								if (null != exceldatalist.get(26) && !exceldatalist.get(26).toString().trim().isEmpty()) {
									obj.setRuleId((String.valueOf(exceldatalist.get(26)).trim()));
								} else {
									obj.setRuleId("");
								}
								if (null != exceldatalist.get(27) && !exceldatalist.get(27).toString().trim().isEmpty()) {
									obj.setPslFlag((String.valueOf(exceldatalist.get(27)).trim()));
								} else {
									obj.setPslFlag("");
								}
								if (null != exceldatalist.get(28) && !exceldatalist.get(28).toString().trim().isEmpty()) {
									obj.setPslValue((String.valueOf(exceldatalist.get(28)).trim()));
								} else {
									obj.setPslValue("");
								}
								if (null != exceldatalist.get(29) && !exceldatalist.get(29).toString().trim().isEmpty()) {
									obj.setUncondCancelComm((String.valueOf(exceldatalist.get(29)).trim()));
								} else {
									obj.setUncondCancelComm("");
								}
								if (null != exceldatalist.get(30) && !exceldatalist.get(30).toString().trim().isEmpty()) {
									obj.setInterestRate((String.valueOf(exceldatalist.get(30)).trim()));
								} else {
									obj.setInterestRate("");
								}
								if (null != exceldatalist.get(31) && !exceldatalist.get(31).toString().trim().isEmpty()) {
									String s31=UIUtil.removeComma(String.valueOf(exceldatalist.get(31)).trim());
									try {
									obj.setRateValue((new BigDecimal(s31).toPlainString()));
									}catch(Exception e) {
										obj.setRateValue((String.valueOf(exceldatalist.get(31)).trim()));
									}
									
									
								} else {
									obj.setRateValue("");
								}
								if (null != exceldatalist.get(32) && !exceldatalist.get(32).toString().trim().isEmpty()) {
									obj.setRealEsExpoValue((String.valueOf(exceldatalist.get(32)).trim()));
								} else {
									obj.setRealEsExpoValue("");
								}
								if (null != exceldatalist.get(33) && !exceldatalist.get(33).toString().trim().isEmpty()) {
									obj.setCommercialRealEstate(String.valueOf(exceldatalist.get(33)).trim());
								} else {
									obj.setCommercialRealEstate("");
								}
								if (null != exceldatalist.get(34) && !exceldatalist.get(34).toString().trim().isEmpty()) {
									obj.setLimitTenor((String.valueOf(exceldatalist.get(34)).trim()));
								} else {
									obj.setLimitTenor("");
								}
								if (null != exceldatalist.get(35) && !exceldatalist.get(35).toString().trim().isEmpty()) {
									obj.setRemark((String.valueOf(exceldatalist.get(35)).trim()));
								} else {
									obj.setRemark("");
								}
								obj.setUploadedBy(uploadedby);
								obj.setUploadDate(uploadDate);
								obj.setTempCounter(i);
								obj.setStatus("");
								obj.setReason("");
								
								
								
								totalUploadedList.add(obj);
								FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
								//Store dummy Ref Id of failed records in set so that for pass records we can also mark it as fail
								
								
							
							
							}

							DefaultLogger.debug(this, "##################### totalUploadedList ############:: "
									+ totalUploadedList.size()); //pass status
						//	countFail = errorList.size();
							
							//Get MAster data

							Set<String> segmentSet = limitDao.getCommonCode(CategoryCodeConstant.SEGMENT_1);
							Set<String> prioritySecNoSet = limitDao.getCommonCode(CategoryCodeConstant.CommonCode_PRIORITY_SECTOR);
							Set<String> prioritySecYesSet = limitDao.getCommonCode(CategoryCodeConstant.CommonCode_PRIORITY_SECTOR_Y);
							Set<String> unconCancelCommSet = limitDao.getCommonCode(CategoryCodeConstant.UNCONDI_CANCL_COMMITMENT);
							Set<String> ruleIdSet = limitDao.getCommonCode(CategoryCodeConstant.NPA_RULE_ID);
							Set<String> commRealEstateSet = limitDao.getCommonCode(CategoryCodeConstant.CommonCode_COMMERCIAL_REAL_ESTATE);
							Set<String> currencySet = limitDao.getCurrencySet();
							Set validPartySet = limitDao.getValidPartySet();
						    Set<String> systemSet = limitDao.getCommonCode(com.integrosys.cms.ui.collateral.CategoryCodeConstant.SYSTEM);
						    Set<String> facilityCategorySet = limitDao.getCommonCode(com.integrosys.cms.ui.collateral.CategoryCodeConstant.FACILITY_CATEGORY);
						    
						    HashMap<String,String> facMaster = limitDao.getFacMaster();
							//validate facility and line details.
							
							validateFacilityAndLine(totalUploadedList,dummRefFileMap,limitDao,fccBranchIdMap,bundle, Locale.getDefault(),segmentSet,prioritySecNoSet,prioritySecYesSet,unconCancelCommSet,ruleIdSet,commRealEstateSet,currencySet,validPartySet,systemSet,facilityCategorySet,facMaster,simpleDateFormat);
							validateFacilityDummyRefId(dummRefFileMap,bundle);
							validateDuplicateAccount(dummRefFileMap, bundle);
							validateTotalRelAmt(totalUploadedList, dummRefFileMap);
							
							updateTotalUploadeList(totalUploadedList, dummRefFileMap);
							HashMap<String,List<OBCreatefacilitylineFile>> actualRefFileMap=new HashMap<String, List<OBCreatefacilitylineFile>>();
							createMapWithPassRecord(dummRefFileMap,actualRefFileMap);
							//create facility and line object
							createFacilityLineTrx(totalUploadedList,limitDao,context,actualRefFileMap,bundle,fccBranchIdMap,applicationDate,facMaster,simpleDateFormat);
							
							
							updateFinalTotalUploadList(totalUploadedList, dummRefFileMap);
							//insert file data into table
							int batchSize = 200;
							for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
								List<OBCreatefacilitylineFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
								jdbc.createFacilitylineFile(batchList);
							}
							
							/*DefaultLogger.debug(this, "##################### errorList ############:: "
									+ errorList.size()); //fail status
							
							for (int j = 0; j < errorList.size(); j += batchSize) {
								List<OBCreatefacilitylineFile> batchList = errorList.subList(j,j + batchSize > errorList.size() ? errorList.size(): j + batchSize);
								jdbc.createFacilitylineFile(batchList);
							}*/
							
						
							

//						}
					}
				//}

			}

		//	resultMap.put("trxValueOut", trxValueOut);
			resultMap.put("totalUploadedList", totalUploadedList);
			resultMap.put("errorList", errorList);
		//	resultMap.put("finalList", finalList);
			/*resultMap.put("total", String.valueOf(totalUploadedList.size() + errorList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));*/

			resultMap.put("fileType", fileType);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		} catch (FileUploadException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		}
		return returnMap;

	}
	
	
	private void updateFinalTotalUploadList(ArrayList<OBCreatefacilitylineFile> totalUploadedList,
			HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap) {

		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside updateFinalTotalUploadList");
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
        
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	  
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
        
        		   for(int i=0;i<totalUploadedList.size();i++) {
        			   OBCreatefacilitylineFile obCreatefacilitylineFile2 = totalUploadedList.get(i);
        			   if(obCreatefacilitylineFile.getTempCounter() == obCreatefacilitylineFile2.getTempCounter()) {
				       obCreatefacilitylineFile2.setStatus(obCreatefacilitylineFile.getStatus());
				       obCreatefacilitylineFile2.setReason(obCreatefacilitylineFile.getReason());
				       break;
			        }
		  	    }
             }
           
        }
		
		
	}

	private void createMapWithPassRecord(HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap,
			HashMap<String, List<OBCreatefacilitylineFile>> actualRefFileMap) {
		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside createMapWithPassRecord");
        while(dummRefFileMapItr.hasNext()) 
        { 
        	 int passCount=0;
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             String key = entry.getKey();
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
        
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	  
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
				if ("PASS".equals(obCreatefacilitylineFile.getStatus())) {

					if (!(actualRefFileMap.containsKey(key))) {
						passCount = 1;
					} else {
						passCount++;
					}
					if (!(passCount > 99)) {
						if (actualRefFileMap.containsKey(key)) {
							List<OBCreatefacilitylineFile> list = actualRefFileMap.get(key);
							list.add(obCreatefacilitylineFile);
							actualRefFileMap.put(key, list);
						} else {
							ArrayList<OBCreatefacilitylineFile> createFacList = new ArrayList<OBCreatefacilitylineFile>();
							createFacList.add(obCreatefacilitylineFile);
							actualRefFileMap.put(key, createFacList);
						}
					} else {
						obCreatefacilitylineFile.setStatus("FAIL");
						obCreatefacilitylineFile.setReason("We cannot Add more than 99 Lines");
					}

				}
           }
        }
	}

	private void updateTotalUploadeList(ArrayList<OBCreatefacilitylineFile> totalUploadedList, HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap) {
		
		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside updateTotalUploadeList");
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
        
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	  
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
        	   if("FAIL".equals(obCreatefacilitylineFile.getStatus())){
        		   for(int i=0;i<totalUploadedList.size();i++) {
        			   OBCreatefacilitylineFile obCreatefacilitylineFile2 = totalUploadedList.get(i);
        			   if(obCreatefacilitylineFile.getTempCounter() == obCreatefacilitylineFile2.getTempCounter()) {
				       obCreatefacilitylineFile2.setStatus("FAIL");
				       obCreatefacilitylineFile2.setReason(obCreatefacilitylineFile.getReason());
				       break;
			        }
		  	    }
             }
           }
        }
		
	}

	private void validateFacilityDummyRefId(HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap,ResourceBundle bundle) {
		
		String defaultCurrency = bundle.getString("createfaclineupload.default.currency");
		
		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside validateFacilityDummyRefId");
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
        
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	  
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
        	   for(int j=1;j<createfacilitylineFileList.size();j++) {
        		   boolean flagFac=false;
        		   boolean gradeFlag=false;
        		   boolean currencyFlag=false;
        		   boolean releasedFlag=false;
        		   
//        		   BigDecimal adhocAmt=new BigDecimal(0);
//        		   BigDecimal sanctionedAmt=new BigDecimal(0);
//        		   BigDecimal releasableAmt=new BigDecimal(0);
//        		   
//        		   BigDecimal adhocAmt2=new BigDecimal(0);
//        		   BigDecimal sanctionedAmt2=new BigDecimal(0);
//        		   BigDecimal releasableAmt2=new BigDecimal(0);
//        		   
//        		
        	   OBCreatefacilitylineFile obCreatefacilitylineFile2 = createfacilitylineFileList.get(j);
        	   if(!(obCreatefacilitylineFile.getGrade()).equalsIgnoreCase(obCreatefacilitylineFile2.getGrade())) {
        	   if((null==obCreatefacilitylineFile2.getGrade() || "".equals(obCreatefacilitylineFile2.getGrade()) || "5".equalsIgnoreCase(obCreatefacilitylineFile2.getGrade()))
        		 && (null==obCreatefacilitylineFile.getGrade() || "".equals(obCreatefacilitylineFile.getGrade()) || "5".equalsIgnoreCase(obCreatefacilitylineFile.getGrade()))){
        	   gradeFlag=true;
        	   }
        	   }
        	   if(!(obCreatefacilitylineFile.getCurrency()).equalsIgnoreCase(obCreatefacilitylineFile2.getCurrency())) {
            	   if((null==obCreatefacilitylineFile2.getCurrency() || "".equals(obCreatefacilitylineFile2.getCurrency()) || defaultCurrency.equalsIgnoreCase(obCreatefacilitylineFile2.getCurrency()))
            		 && (null==obCreatefacilitylineFile.getCurrency() || "".equals(obCreatefacilitylineFile.getCurrency()) || defaultCurrency.equalsIgnoreCase(obCreatefacilitylineFile.getCurrency()))){
            		   currencyFlag=true;
            	   }
            	   }
        	   if(!(obCreatefacilitylineFile.getIsReleased()).equalsIgnoreCase(obCreatefacilitylineFile2.getIsReleased())) {
            	   if((null==obCreatefacilitylineFile2.getIsReleased() || "".equals(obCreatefacilitylineFile2.getIsReleased()) || "Y".equalsIgnoreCase(obCreatefacilitylineFile2.getIsReleased()))
            		 && (null==obCreatefacilitylineFile.getIsReleased() || "".equals(obCreatefacilitylineFile.getIsReleased()) || "Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsReleased()))){
            		   releasedFlag=true;
            	   }
            	   }
//        	   if(!(null==obCreatefacilitylineFile.getAdhoclimitAmt() || "".equals(obCreatefacilitylineFile.getAdhoclimitAmt()))) {
//        		   adhocAmt=new BigDecimal(obCreatefacilitylineFile.getAdhoclimitAmt());
//        	   }
//        	   if(!(null==obCreatefacilitylineFile.getSanctionedAmt() || "".equals(obCreatefacilitylineFile.getSanctionedAmt()))) {
//        		   sanctionedAmt=new BigDecimal(obCreatefacilitylineFile.getSanctionedAmt());
//        	   }
//        	   if(!(null==obCreatefacilitylineFile.getReleasableAmt() || "".equals(obCreatefacilitylineFile.getReleasableAmt()))) {
//        		   releasableAmt=new BigDecimal(obCreatefacilitylineFile.getReleasableAmt());
//        	   }
//        	   
//        	   if(!(null==obCreatefacilitylineFile2.getAdhoclimitAmt() || "".equals(obCreatefacilitylineFile2.getAdhoclimitAmt()))) {
//        		   adhocAmt2=new BigDecimal(obCreatefacilitylineFile2.getAdhoclimitAmt());
//        	   }
//        	   if(!(null==obCreatefacilitylineFile2.getSanctionedAmt() || "".equals(obCreatefacilitylineFile2.getSanctionedAmt()))) {
//        		   sanctionedAmt2=new BigDecimal(obCreatefacilitylineFile2.getSanctionedAmt());
//        	   }
//        	   if(!(null==obCreatefacilitylineFile2.getReleasableAmt() || "".equals(obCreatefacilitylineFile2.getReleasableAmt()))) {
//        		   releasableAmt2=new BigDecimal(obCreatefacilitylineFile2.getReleasableAmt());
//        	   }
        	   
        	   if(!(obCreatefacilitylineFile.getPartyId()).equalsIgnoreCase(obCreatefacilitylineFile2.getPartyId())){
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getFacilityCategory()).equalsIgnoreCase(obCreatefacilitylineFile2.getFacilityCategory())) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getFacilityName()).equalsIgnoreCase(obCreatefacilitylineFile2.getFacilityName())) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getSystem()).equalsIgnoreCase(obCreatefacilitylineFile2.getSystem() )) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getGrade()).equalsIgnoreCase(obCreatefacilitylineFile2.getGrade()) || gradeFlag) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getCurrency()).equalsIgnoreCase(obCreatefacilitylineFile2.getCurrency()) || currencyFlag) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getIsReleased()).equalsIgnoreCase(obCreatefacilitylineFile2.getIsReleased()) || releasedFlag) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getIsAdhoc()).equalsIgnoreCase(obCreatefacilitylineFile2.getIsAdhoc())) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getAdhoclimitAmt()).equalsIgnoreCase(obCreatefacilitylineFile2.getAdhoclimitAmt())) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getSanctionedAmt()).equalsIgnoreCase(obCreatefacilitylineFile2.getSanctionedAmt())) {
        		   flagFac=true; 
        	   }else if(!(obCreatefacilitylineFile.getReleasableAmt()).equalsIgnoreCase(obCreatefacilitylineFile2.getReleasableAmt())) {
        		   flagFac=true; 
        	   }
        		if(flagFac) {
        			obCreatefacilitylineFile2.setStatus("FAIL");
        			obCreatefacilitylineFile2.setReason(" Facility data is not matching with dummy ref id '"+obCreatefacilitylineFile2.getDummyRefId()+"'.");
        		}
        		if(j==(createfacilitylineFileList.size()-1)) {
        			k=j+1;
        		}
        	   }
           }
        }
		
	}

	private void validateDuplicateAccount(HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap,ResourceBundle bundle) {
		
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem =bundle.getString("ubs.systemName");
		
		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside validateDuplicateAccount");
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
          HashSet<String> serialLineSystemId=new HashSet<String>();
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	   boolean validate=false;
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
        	//   if(null==obCreatefacilitylineFile.getIsReleased() || "Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsReleased()) || "".equals(obCreatefacilitylineFile.getIsReleased())){
        		   if("PASS".equals(obCreatefacilitylineFile.getStatus())) {
        		 if(fcubsSystem.equals(obCreatefacilitylineFile.getSystem()) || ubsSystem.equals(obCreatefacilitylineFile.getSystem())){
        			 if("N".equals(obCreatefacilitylineFile.getSendToFile())) {
        				 validate=true; 	 
        			 }
        		 }else {
        			 validate=true; 
        		 }
        		 if(validate) {
        	  String refId=obCreatefacilitylineFile.getSerialNo()+obCreatefacilitylineFile.getDbLineNo()+obCreatefacilitylineFile.getSystemID();
        	   if(serialLineSystemId.contains(refId )) {
        		   obCreatefacilitylineFile.setReason(obCreatefacilitylineFile.getReason()+" An account with this Account No. already exists.");
        		   obCreatefacilitylineFile.setStatus("FAIL");
        		
        	   }else {
        		   serialLineSystemId.add(refId);
        	   }
        		   }
        	   }
           }
        }
		
	}

	private void validateTotalRelAmt(ArrayList<OBCreatefacilitylineFile> totalUploadedList,HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap) {
		// TODO Auto-generated method stub
		//validate total rel amt
	Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        DefaultLogger.debug(this, "inside createDummyRefIdMap");
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
             BigDecimal totalRelAmt=new BigDecimal(0);
           for(int k=0;k<createfacilitylineFileList.size();k++) {
        	   OBCreatefacilitylineFile obCreatefacilitylineFile = createfacilitylineFileList.get(k);
        	   
        	   if("PASS".equals(obCreatefacilitylineFile.getStatus())) {
        	   BigDecimal totalRelAmtTemp=totalRelAmt;
        	   String releasableAmt = obCreatefacilitylineFile.getReleasableAmt();
        	   BigDecimal releasableAmtBd=new BigDecimal(releasableAmt);
        	
        	   String  reason ="";
        		totalRelAmtTemp=totalRelAmtTemp.add(new BigDecimal(obCreatefacilitylineFile.getReleaseAmount()));
        		if(totalRelAmtTemp.compareTo(releasableAmtBd)>0) {
        			  reason = reason+" Total Released Amount cannot be greater than Releasable Amount.";
        			  obCreatefacilitylineFile.setReason(reason);
        			  obCreatefacilitylineFile.setStatus("FAIL");
//        			  
//        			  for(int i=0;i<totalUploadedList.size();i++) {
//        				  OBCreatefacilitylineFile obCreatefacilitylineFile2 = totalUploadedList.get(i);
//        				  if(obCreatefacilitylineFile.getTempCounter() == obCreatefacilitylineFile2.getTempCounter()) {
//        					  obCreatefacilitylineFile2.setStatus("FAIL");
//        					  obCreatefacilitylineFile2.setReason(obCreatefacilitylineFile2.getReason()+reason);
//        					  break;
//        				  }
//        			  }
        			
        		}else {
        			totalRelAmt=totalRelAmtTemp;
        		}
           }
           }
		
	}
	}

	private void validateFacilityAndLine(ArrayList<OBCreatefacilitylineFile> totalUploadedList,HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap, LimitDAO limitDao, HashMap fccBranchIdMap, ResourceBundle bundle,Locale local, Set<String> segmentSet, Set<String> prioritySecNoSet, Set<String> prioritySecYesSet, Set<String> unconCancelCommSet, Set<String> ruleIdSet, Set<String> commRealEstateSet, Set<String> currencySet, Set validPartySet, Set<String> systemSet, Set<String> facilityCategorySet, HashMap<String, String> facMaster, DateFormat simpleDateFormat) {
		String errorCode=null;
		// TODO Auto-generated method stub
		DefaultLogger.debug(this, "inside validateFacilityAndLine");
		
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem =bundle.getString("ubs.systemName");
		
	//	String errorCode = null;
		for(int k=0;k<totalUploadedList.size();k++) {
			 OBCreatefacilitylineFile obCreatefacilitylineFile = totalUploadedList.get(k);
			 String reason = "";
			 boolean facName=false;
			 boolean system=false;
			 boolean facCategory=false;
			 boolean facilityIsActive=false;
			 boolean partyIsActive = false;
			 boolean systemFlag=false;
			 boolean currencyIsValid = false;
			 boolean isAdhoc = false;
			 boolean sanctAmtisProper = false;
			 boolean relesableAmtisProper=false;
			 List<String> sysIDs=new ArrayList<String>();
			 
		
			 if (null == obCreatefacilitylineFile.getPartyId() || "".equals(obCreatefacilitylineFile.getPartyId())) {
				 reason=reason+" Party id is mandatory.";
				  }
			 else if(!validPartySet.contains(obCreatefacilitylineFile.getPartyId())) {
				 reason=reason+" Party is in Inactive state or CAM for PARTY does not exist.";
			 }else {
				  partyIsActive = true;
			 }
			 
			 if (null == obCreatefacilitylineFile.getFacilityCategory() || "".equals(obCreatefacilitylineFile.getFacilityCategory())) {
				 reason=reason+" Facility Category is mandatory.";
				  }
			 else if(!facilityCategorySet.contains(obCreatefacilitylineFile.getFacilityCategory())) {
				 reason=reason+" Facility Category code is invalid.";
			 }else {
				  facCategory=true;
			  }
			 if (null == obCreatefacilitylineFile.getFacilityName() || "".equals(obCreatefacilitylineFile.getFacilityName())) {
				 reason=reason+" Facility Name is mandatory.";
				  }else {
					  facName=true;
				  }
			
			 if (null == obCreatefacilitylineFile.getDummyRefId() || "".equals(obCreatefacilitylineFile.getDummyRefId())) {
				 reason=reason+" Dummy Ref id is mandatory.";
				  }
			 
			 if (null == obCreatefacilitylineFile.getSystem() || "".equals(obCreatefacilitylineFile.getSystem())) {
				 reason=reason+" Facility System is mandatory.";
				  }
			 else if(!systemSet.contains(obCreatefacilitylineFile.getSystem())) {
				 reason=reason+" Facility System code is invalid.";
			 }else {
				  system=true;
			  }
			 if (system && facCategory && facName) {
			if(!facMaster.containsKey(obCreatefacilitylineFile.getFacilityCategory()+"|"+obCreatefacilitylineFile.getFacilityName()+"|"+obCreatefacilitylineFile.getSystem())) {
				 reason=reason+" Facility is not of funded/non funded type or given facility is not present in master.";
			}else {
				facilityIsActive=true;
				String facValue = facMaster.get(obCreatefacilitylineFile.getFacilityCategory()+"|"+obCreatefacilitylineFile.getFacilityName()+"|"+obCreatefacilitylineFile.getSystem());
				String[] str = facValue.split("\\|");
				String lineNo = str[2];
				obCreatefacilitylineFile.setDbLineNo(lineNo);
			}
			 }
			 

			 if (!(null == obCreatefacilitylineFile.getGrade() || "".equals(obCreatefacilitylineFile.getGrade()))) {
				 if (!("1".equals(obCreatefacilitylineFile.getGrade()) || "2".equals(obCreatefacilitylineFile.getGrade()) || "3".equals(obCreatefacilitylineFile.getGrade()) ||
						 "4".equals(obCreatefacilitylineFile.getGrade()) || "5".equals(obCreatefacilitylineFile.getGrade()) || "6".equals(obCreatefacilitylineFile.getGrade()) ||
						 "7".equals(obCreatefacilitylineFile.getGrade()) || "8".equals(obCreatefacilitylineFile.getGrade()) || "9".equals(obCreatefacilitylineFile.getGrade()) ||
						 "10".equals(obCreatefacilitylineFile.getGrade()) )) {
					 reason=reason+" Grade value should be between 1 to 10.";
					  }
				 }
			 
			 if(!("Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsAdhoc()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getIsAdhoc()))) {
				 reason=reason+"Adhoc flag should be 'Y' or 'N'.";  
			 }else if ("Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsAdhoc())) {
				    isAdhoc=true;
					if(null==obCreatefacilitylineFile.getAdhoclimitAmt() || "".equals(obCreatefacilitylineFile.getAdhoclimitAmt())) {
						reason=reason+" Adhoc amount is mandatory.";
					}
					else if (!(errorCode = Validator.checkAmount(obCreatefacilitylineFile.getAdhoclimitAmt(), true, 1,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2, IGlobalConstant.DEFAULT_CURRENCY, local)).equals(Validator.ERROR_NONE)) {
						if("decimalexceeded".equals(errorCode)) {
					reason=reason+" In Adhoc limit, only 2 digits are allowed after decimal point. ";		
						}else {
					 // reason=reason+" Valid range for adhoc limit is between 1 to '99999999999999999999999999999999999999.99'. ";
					  reason=reason+" The Adhoc limit should be numeric field within the allowed range.";
						}
					}
			 }else if("N".equalsIgnoreCase(obCreatefacilitylineFile.getIsAdhoc())) {
					if(!(null==obCreatefacilitylineFile.getAdhoclimitAmt() || "".equals(obCreatefacilitylineFile.getAdhoclimitAmt()))) {
						reason=reason+" Adhoc amount should be blank.";
					}
			 }
			 
			 if(null==obCreatefacilitylineFile.getSanctionedAmt() || "".equals(obCreatefacilitylineFile.getSanctionedAmt())) {
					reason=reason+" Sanctioned amount is mandatory.";
				}
				else if (!(errorCode = Validator.checkAmount(obCreatefacilitylineFile.getSanctionedAmt(), true, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2, IGlobalConstant.DEFAULT_CURRENCY, local)).equals(Validator.ERROR_NONE)) {
					if("decimalexceeded".equals(errorCode)) {
						reason=reason+" In Sanctioned amount, only 2 digits are allowed after decimal point. ";		
							}else {
						//reason=reason+" Valid range for Sanctioned amount is between 0 to '99999999999999999999999999999999999999.99'.";
							reason=reason+" The Sanctioned Amount should be numeric field within the allowed range.";
							}
				}else {
					
					 sanctAmtisProper = true;
					//do validatiion with party funded and non funded
					 if(partyIsActive && facilityIsActive && !isAdhoc) {
						 
						 BigDecimal exchangeRate=null;
						if(!AbstractCommonMapper.isEmptyOrNull(obCreatefacilitylineFile.getSanctionedAmt())){
							 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
							 
							 if(currencyIsValid)
							 exchangeRate = frxPxy.getExchangeRateWithINR(obCreatefacilitylineFile.getCurrency().trim());
							 else
								 exchangeRate = frxPxy.getExchangeRateWithINR(bundle.getString("createfaclineupload.default.currency").trim());	 
							 DefaultLogger.debug(this,"obCreatefacilitylineFile.getCurrency():"+obCreatefacilitylineFile.getCurrency()+" / " +exchangeRate);
					    
							 BigDecimal sanctioneAmtBd = new BigDecimal(obCreatefacilitylineFile.getSanctionedAmt());
					 		
						BigDecimal	tempRequiredSecCov = sanctioneAmtBd.multiply(exchangeRate);
						String facValue =facMaster.get(obCreatefacilitylineFile.getFacilityCategory()+"|"+obCreatefacilitylineFile.getFacilityName()+"|"+obCreatefacilitylineFile.getSystem());
						String[] str = facValue.split("\\|");
						
						if(  "FUNDED".equalsIgnoreCase(str[1])) {
							BigDecimal getfundedAmt = limitDao.getfundedNonfunAmt("funded", obCreatefacilitylineFile.getPartyId());
							BigDecimal lmtAmtSum=new BigDecimal(0);
							List<String> lmtAmtCurrency = limitDao.getSanctionedAmtOfLmt(obCreatefacilitylineFile.getPartyId(), "'FUNDED'");
							for(String amtCurrency:lmtAmtCurrency) {
							String[] lmtAmtCurrencyStr = amtCurrency.split("\\|");
							
							BigDecimal exchRateLmt = frxPxy.getExchangeRateWithINR(lmtAmtCurrencyStr[1].trim());
							BigDecimal lmtSanctAmt=new BigDecimal(lmtAmtCurrencyStr[0]);
							BigDecimal	lmtSanctAmtbd = lmtSanctAmt.multiply(exchRateLmt);
							lmtAmtSum=lmtAmtSum.add(lmtSanctAmtbd);
							}
							
							BigDecimal remainingAmtFunded=getfundedAmt.subtract(lmtAmtSum);
							if(-1==remainingAmtFunded.signum()) {
								remainingAmtFunded=new BigDecimal(0);
							}
							if(tempRequiredSecCov.compareTo(remainingAmtFunded) > 0) {
								reason=reason+" Sanctioned Amount cannot be greater than Total Funded Amount.";
							}
							
						}else if( ("NON_FUNDED".equalsIgnoreCase(str[1]) || "Non Funded".equalsIgnoreCase(str[1]))) {
							BigDecimal getnonfundedAmt = limitDao.getfundedNonfunAmt("nonfunded", obCreatefacilitylineFile.getPartyId());
							
							BigDecimal lmtAmtSum=new BigDecimal(0);
							List<String> lmtAmtCurrency = limitDao.getSanctionedAmtOfLmt(obCreatefacilitylineFile.getPartyId(), "'NON_FUNDED','Non Funded'");
							for(String amtCurrency:lmtAmtCurrency) {
							String[] lmtAmtCurrencyStr = amtCurrency.split("\\|");
							
							BigDecimal exchRateLmt = frxPxy.getExchangeRateWithINR(lmtAmtCurrencyStr[1].trim());
							BigDecimal lmtSanctAmt=new BigDecimal(lmtAmtCurrencyStr[0]);
							BigDecimal	lmtSanctAmtbd = lmtSanctAmt.multiply(exchRateLmt);
							lmtAmtSum=lmtAmtSum.add(lmtSanctAmtbd);
							}
							
							BigDecimal remainingAmtNonFunded=getnonfundedAmt.subtract(lmtAmtSum);
							if(-1==remainingAmtNonFunded.signum()) {
								remainingAmtNonFunded=new BigDecimal(0);
							}
							if(tempRequiredSecCov.compareTo(remainingAmtNonFunded) > 0) {
								reason=reason+" Sanctioned Amount cannot be greater than Total Non Funded Amount.";
							  }
					    }
					 }
				  }
				}
				if(null==obCreatefacilitylineFile.getReleasableAmt() || "".equals(obCreatefacilitylineFile.getReleasableAmt())) {
					reason=reason+" Releasable amount is mandatory.";
				}
				else if (!(errorCode = Validator.checkAmount(obCreatefacilitylineFile.getReleasableAmt(), true, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2, IGlobalConstant.DEFAULT_CURRENCY, local)).equals(Validator.ERROR_NONE)) {
					if("decimalexceeded".equals(errorCode)) {
						reason=reason+" In Releasable amount, only 2 digits are allowed after decimal point. ";		
							}else {
				    // reason=reason+" Valid range for Releasable amount is between 0 to '99999999999999999999999999999999999999.99'.";
						reason=reason+" The Releasable Amount should be numeric field within the allowed range.";
							}
				}else {
					relesableAmtisProper=true;
				    if(sanctAmtisProper) {
					BigDecimal relesableAmt = new BigDecimal(obCreatefacilitylineFile.getReleasableAmt());
					BigDecimal sanctionAmt=new BigDecimal(0);
					if(!"".equals(obCreatefacilitylineFile.getSanctionedAmt())) {
					 sanctionAmt = new BigDecimal(obCreatefacilitylineFile.getSanctionedAmt());
					}
					
					if( relesableAmt.compareTo(sanctionAmt) > 0){
					reason=reason+" Releasable Amount cannot be greater than Sanctioned Amount.";
				}
				    }
				}
				
			
			 
				 
			 if(partyIsActive && facilityIsActive) {
					
					 sysIDs = limitDao.getPartySystemId(obCreatefacilitylineFile.getSystem(), obCreatefacilitylineFile.getPartyId());
					if(sysIDs.size() <= 0) {
						reason=reason+" There is no System ID attached with selected facility."; 
					}else {
						systemFlag=true;
					}
					
					}
			 
			 if (!(null == obCreatefacilitylineFile.getCurrency() || "".equals(obCreatefacilitylineFile.getCurrency()))) {
				 if(!currencySet.contains(obCreatefacilitylineFile.getCurrency()))
				 reason=reason+" Currency Code is invalid.";
				 else {
					 currencyIsValid = true;
				 }
				  }
			// if(facilityIsActive) {
			 if(null==obCreatefacilitylineFile.getIsReleased() || "".equals(obCreatefacilitylineFile.getIsReleased()) || "Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsReleased())) {
				 if(facilityIsActive) { 
				 
			 if (null == obCreatefacilitylineFile.getLimitExpiryDate()) {
				 reason=reason+" Limit Expiry date is mandatory.";
				  }else if(!ASSTValidator.isValidDateDDMMMYYYY(obCreatefacilitylineFile.getLimitExpiryDate())){
					  reason=reason+" Limit Expiry date format is invalid."; 
				  }
			
			  if (null!= obCreatefacilitylineFile.getIsCapitalMarketExpo() && !obCreatefacilitylineFile.getIsCapitalMarketExpo().isEmpty()
				    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsCapitalMarketExpo()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getIsCapitalMarketExpo())) ){
			 reason=reason+" Capital market exposure should be 'Y','N' or empty.";
			  	}
			  if (null!= obCreatefacilitylineFile.getIsRealEstateExpo() && !obCreatefacilitylineFile.getIsRealEstateExpo().isEmpty()
					    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsRealEstateExpo()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getIsRealEstateExpo())) ){
				 reason=reason+" Real esate exposure should be 'Y','N' or empty.";
				  	}
			  if ("Y".equalsIgnoreCase(obCreatefacilitylineFile.getIsRealEstateExpo())){
				  if(null==obCreatefacilitylineFile.getRealEsExpoValue() || "".equals(obCreatefacilitylineFile.getRealEsExpoValue())) {
					  reason=reason+" Real Estate Exposure type is mandatory.";
				  }else if(!("Residential real Estate".equalsIgnoreCase(obCreatefacilitylineFile.getRealEsExpoValue()) || "Commercial Real estate".equalsIgnoreCase(obCreatefacilitylineFile.getRealEsExpoValue()) 
						  || "Indirect real estate".equalsIgnoreCase(obCreatefacilitylineFile.getRealEsExpoValue()))){
					  reason=reason+" Real Estate Exposure type should have one of the values from list i.e 'Residential real Estate' or 'Commercial Real estate' or 'Indirect real estate'";
					  
				  }else if("Commercial Real estate".equalsIgnoreCase(obCreatefacilitylineFile.getRealEsExpoValue())){
					  if(null==obCreatefacilitylineFile.getCommercialRealEstate() || "".equals(obCreatefacilitylineFile.getCommercialRealEstate())) {
						  reason=reason+" Commercial Real estate value is mandatory.";
					  }else if( !commRealEstateSet.contains(obCreatefacilitylineFile.getCommercialRealEstate())) {
								reason=reason+" Commercial Real estate code is invalid.";
					  }
					  
				  }
			  }
			  
			  if (null!= obCreatefacilitylineFile.getPslFlag() && !obCreatefacilitylineFile.getPslFlag().isEmpty()
					    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getPslFlag()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getPslFlag())) ){
				 reason=reason+" Priority sector flag should be 'Y','N' or empty.";
				  	}
			 if ("Y".equalsIgnoreCase(obCreatefacilitylineFile.getPslFlag())) {
				 if(null==obCreatefacilitylineFile.getPslValue() || "".equals(obCreatefacilitylineFile.getPslValue()))
					 reason=reason+" Priority sector value is mandatory.";
				 else if (!prioritySecYesSet.contains(obCreatefacilitylineFile.getPslValue())){
					 reason=reason+" Priority sector value is invalid.";
				 }
					 
			 }
		     if("N".equalsIgnoreCase(obCreatefacilitylineFile.getPslFlag()) || null==obCreatefacilitylineFile.getPslFlag() || "".equalsIgnoreCase(obCreatefacilitylineFile.getPslFlag())){
		    	 if(null==obCreatefacilitylineFile.getPslValue() || "".equals(obCreatefacilitylineFile.getPslValue()))
					 reason=reason+" Priority sector value is mandatory.";
				 else if (!prioritySecNoSet.contains(obCreatefacilitylineFile.getPslValue())){
					 reason=reason+" Priority sector value is invalid.";
				 }
					 
			 
				  	}
		     
			  //to be checked
			 if(!(null==obCreatefacilitylineFile.getInterestRate() || "".equals(obCreatefacilitylineFile.getInterestRate()) || "Fixed".equalsIgnoreCase(obCreatefacilitylineFile.getInterestRate()))){
			
				  reason=reason+" Interest rate should have value as 'Fixed'.";
			  }
			 if(!(null==obCreatefacilitylineFile.getRateValue() || "".equals(obCreatefacilitylineFile.getRateValue()))) {
					
					if (!(errorCode = Validator.checkNumber(obCreatefacilitylineFile.getRateValue(), false , 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2,3,local))
							.equals(Validator.ERROR_NONE)) {
						if("decimalexceeded".equals(errorCode)) {
							reason=reason+" In Interest rate, only 2 digits are allowed after decimal point. ";		
								}else {
					     reason=reason+" Valid range for Interest rate is 0 to 100 .";
								}
					}
					/*if(ASSTValidator.isValidAlphaNumStringWithSpace(obCreatefacilitylineFile.getRateValue())) {
						 reason=reason+" Special Character(s) Not Allowed in Interest rate.";
					}*/
			  }
			 if(system) {
			 if(fcubsSystem.equals(obCreatefacilitylineFile.getSystem()) || ubsSystem.equals(obCreatefacilitylineFile.getSystem())){
				
				
				 if (null!= obCreatefacilitylineFile.getSendToFile() && !obCreatefacilitylineFile.getSendToFile().isEmpty()
						    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getSendToFile()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getSendToFile())) ){
							 reason=reason+" Send to File flag should be 'Y','N' or empty.";
						 }else if ( "N".equalsIgnoreCase(obCreatefacilitylineFile.getSendToFile())) {
							 if(null==obCreatefacilitylineFile.getSerialNo() || "".equals(obCreatefacilitylineFile.getSerialNo())) {
								 reason=reason+" Serial no is mandatory.";
							 }else if(ASSTValidator.isValidAlphaNumStringWithSpace(obCreatefacilitylineFile.getSerialNo())) {
								 reason=reason+" Special Character(s) Not Allowed in Serial no.";
								}else if (obCreatefacilitylineFile.getSerialNo().length()>2 || !(StringUtils.isNumeric(obCreatefacilitylineFile.getSerialNo()))) {
									
									 reason=reason+" Max 2 digit Serial no is allowed without decimal.";	
								}
								
						 }else if (null==obCreatefacilitylineFile.getSendToFile() || "".equals(obCreatefacilitylineFile.getSendToFile())|| "Y".equalsIgnoreCase(obCreatefacilitylineFile.getSendToFile())) {
							 if(!(obCreatefacilitylineFile.getSerialNo()==null || "".equals(obCreatefacilitylineFile.getSerialNo()))) {
								 reason=reason+" Serial no field should be blank.";	 
							 }
						 }
				 
					 if (null==obCreatefacilitylineFile.getLiabBranch() || "".equals(obCreatefacilitylineFile.getLiabBranch())) {
						 reason=reason+" Liabbranch is mandatory.";
						
					 }else  if(null==fccBranchIdMap.get(obCreatefacilitylineFile.getLiabBranch())) {
						 
						 reason=reason+" Liabbranch is invalid.";
						 
					 }
					 if (null == obCreatefacilitylineFile.getLimitStartDate()) {
						 reason=reason+" Limit start date is mandatory.";
						  }else if(ASSTValidator.isValidDateDDMMMYYYY(obCreatefacilitylineFile.getLimitStartDate())){
							 
							  Date dateLimitStartDate=null;
							try {
								dateLimitStartDate = simpleDateFormat.parse(obCreatefacilitylineFile.getLimitStartDate());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	  
					if(dateLimitStartDate.after(DateUtil.getDate()))
					 {
						 reason=reason+" Limit start date can not be Future Date.";
					 }
						  }else {
							  reason=reason+" Limit start date format is invalid."; 
						  }
				
					 if (null!= obCreatefacilitylineFile.getAvailable() && !obCreatefacilitylineFile.getAvailable().isEmpty()
							    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getAvailable()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getAvailable())) ){
								 reason=reason+" Available flag should be 'Y','N' or empty.";
							 }
					 
				
				 if (null!= obCreatefacilitylineFile.getFreeze() && !obCreatefacilitylineFile.getFreeze().isEmpty()
						    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getFreeze()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getFreeze())) ){
					 reason=reason+" Freeze flag should be 'Y','N' or empty.";
					}
				
				 if (null!= obCreatefacilitylineFile.getRevolvingLine() && !obCreatefacilitylineFile.getRevolvingLine().isEmpty()
						    && !( "Y".equalsIgnoreCase(obCreatefacilitylineFile.getRevolvingLine()) || "N".equalsIgnoreCase(obCreatefacilitylineFile.getRevolvingLine())) ){
					 reason=reason+" Revolving line should be 'Y','N' or empty.";
					}
				 
				 if (obCreatefacilitylineFile.getSegment1() == null || obCreatefacilitylineFile.getSegment1().equals("")) {
					 reason=reason+" Segment1 is mandatory.";
					}
				 else if( !segmentSet.contains(obCreatefacilitylineFile.getSegment1())){
						reason=reason+" Segment1 code is invalid.";
							
				 }
				 if ((obCreatefacilitylineFile.getRuleId() == null) || (obCreatefacilitylineFile.getRuleId().equals(""))) {
					 reason=reason+" RuleId is mandatory.";
					}
				 else {
					 if( !ruleIdSet.contains(obCreatefacilitylineFile.getRuleId()))
							reason=reason+" Rule Id code is invalid.";
				 }
				 
				 if (!(null==obCreatefacilitylineFile.getUncondCancelComm() || "".equals(obCreatefacilitylineFile.getUncondCancelComm()))) {
					if( !unconCancelCommSet.contains(obCreatefacilitylineFile.getUncondCancelComm()))
						reason=reason+" Unconditional cancel commitment code is invalid.";
					}
				 
				 if (!(errorCode = Validator.checkString(obCreatefacilitylineFile.getRemark(), false, 1, 1000)).equals(Validator.ERROR_NONE)) {
					 reason=reason+" InternalRemarks Valid range is between 1 and 1000 characters.";
				}
				
				if(ASSTValidator.isValidInternalRemarks(obCreatefacilitylineFile.getRemark())) {
					 reason=reason+" Special Characters Not Allowed in internalRemarks.";
				}
				
				if (null != obCreatefacilitylineFile.getLimitTenor() && !"".equalsIgnoreCase(obCreatefacilitylineFile.getLimitTenor())) {
					if (!(StringUtils.isNumeric(obCreatefacilitylineFile.getLimitTenor()))) 
						reason=reason+" Only numeric characters are allowed in Limit Tenor.";
				}
				
			 }else {
				boolean nonUbs=false;
						 if(null==obCreatefacilitylineFile.getSerialNo() || "".equals(obCreatefacilitylineFile.getSerialNo())) {
								 reason=reason+" Serial no is manadatory.";
							 }else if(ASSTValidator.isValidAlphaNumStringWithSpace(obCreatefacilitylineFile.getSerialNo())) {
								 reason=reason+" Special Character(s) Not Allowed in Serial no.";
								}else if (obCreatefacilitylineFile.getSerialNo().length()>2|| !(StringUtils.isNumeric(obCreatefacilitylineFile.getSerialNo()))) {
									
									 reason=reason+" Max 2 digit Serial no is allowed without decimal.";	
								
								}
						 
						
				if (!(null== obCreatefacilitylineFile.getSendToFile() || obCreatefacilitylineFile.getSendToFile().isEmpty())){
					nonUbs=true;	 								
				}else if (!(null==obCreatefacilitylineFile.getLiabBranch() || "".equals(obCreatefacilitylineFile.getLiabBranch()))) {
					nonUbs=true;
						 						
				}else  if (null != obCreatefacilitylineFile.getLimitStartDate()) {
					nonUbs=true;
				}else  if (!(null== obCreatefacilitylineFile.getAvailable() || obCreatefacilitylineFile.getAvailable().isEmpty())){
					nonUbs=true;
					 }
				else if (!(null== obCreatefacilitylineFile.getFreeze() || obCreatefacilitylineFile.getFreeze().isEmpty())) {
						 						   
					nonUbs=true;
					}else if (!(null== obCreatefacilitylineFile.getRevolvingLine() || obCreatefacilitylineFile.getRevolvingLine().isEmpty())) {
					  nonUbs=true;
					}else if (!(obCreatefacilitylineFile.getSegment1() == null || obCreatefacilitylineFile.getSegment1().equals(""))) {
						nonUbs=true;
						}else if (!(obCreatefacilitylineFile.getRuleId() == null || obCreatefacilitylineFile.getRuleId().equals(""))) {
						 		nonUbs=true;
						}else  if (!(null==obCreatefacilitylineFile.getUncondCancelComm() || "".equals(obCreatefacilitylineFile.getUncondCancelComm()))) {
						 					
						 	nonUbs=true;
						}else  if (!(null==obCreatefacilitylineFile.getRemark() || "".equals(obCreatefacilitylineFile.getRemark()))) {
		 					nonUbs=true;
					}else  if (!(null==obCreatefacilitylineFile.getLimitTenor() || "".equals(obCreatefacilitylineFile.getLimitTenor()))) {
				
								nonUbs=true;
						}
						 if(nonUbs) {
							 reason=reason+" Please make sure all the fields related to ubs system should be blank as system is not the ubs system."; 
						 }
						 
						
					
			 	}
			 }
			 
			
			 if (!(errorCode = Validator.checkString(obCreatefacilitylineFile.getSystemID(), true, 1, 100)).equals(Validator.ERROR_NONE)) { 
				 reason=reason+" SytemId is mandatory."; 
			}else {
				if(systemFlag && !sysIDs.contains(obCreatefacilitylineFile.getSystemID()))
					reason=reason+" SytemId is invalid."; 
				}
			
			
				
			 if(null==obCreatefacilitylineFile.getReleaseAmount() || "".equals(obCreatefacilitylineFile.getReleaseAmount())) {
					reason=reason+" Released Amount is mandatory.";
				}
			 else if (!(errorCode = Validator.checkAmount(obCreatefacilitylineFile.getReleaseAmount(), true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2, IGlobalConstant.DEFAULT_CURRENCY, local)).equals(Validator.ERROR_NONE)) {
					if("decimalexceeded".equals(errorCode)) {
						reason=reason+" In Released amount, only 2 digits are allowed after decimal point. ";		
							}else {
						//reason=reason+" Valid range for Released Amount is between 0 to '99999999999999999999999999999999999999.99'.";
						reason=reason+" The Released Amount should be numeric field within the allowed range.";
							}
						}else  if(relesableAmtisProper){
							BigDecimal relesableAmt=new BigDecimal(0);
							if(!"".equals(obCreatefacilitylineFile.getReleasableAmt())) {
								relesableAmt = new BigDecimal(obCreatefacilitylineFile.getReleasableAmt());
							}
							BigDecimal releaseAmt = new BigDecimal(obCreatefacilitylineFile.getReleaseAmount());
							if( releaseAmt.compareTo(relesableAmt) > 0){
						
							reason=reason+" Released Amount cannot be greater than Releasable Amount.";
							}
						}
			
			 }else {
				 reason=reason+" Please enter proper facility details before adding line details.";
			 }
			 }else if("N".equalsIgnoreCase(obCreatefacilitylineFile.getIsReleased())){ 
				 Boolean lineField=false;
				 if (!(null == obCreatefacilitylineFile.getLimitExpiryDate() || "".equals(obCreatefacilitylineFile.getLimitExpiryDate()))) {
					 lineField=true;
					  }
				else if (!(null== obCreatefacilitylineFile.getPslFlag() || obCreatefacilitylineFile.getPslFlag().isEmpty())){
					  lineField=true;
					  	}
				else  if (!(null== obCreatefacilitylineFile.getIsCapitalMarketExpo() || obCreatefacilitylineFile.getIsCapitalMarketExpo().isEmpty())){
				 lineField=true;
				  	}
				  else if (!(null== obCreatefacilitylineFile.getIsRealEstateExpo() || obCreatefacilitylineFile.getIsRealEstateExpo().isEmpty())){
					  lineField=true;
					  	}
				else if(!(null==obCreatefacilitylineFile.getRealEsExpoValue() || "".equals(obCreatefacilitylineFile.getRealEsExpoValue()))) {
						  lineField=true;
					  }else if(!(null==obCreatefacilitylineFile.getCommercialRealEstate() || "".equals(obCreatefacilitylineFile.getCommercialRealEstate()))) {
							 lineField=true;
						  }
				 else  if(!(null==obCreatefacilitylineFile.getInterestRate() || "".equals(obCreatefacilitylineFile.getInterestRate()) )){
				 lineField=true;
				 }else if(!(null==obCreatefacilitylineFile.getRateValue() || "".equals(obCreatefacilitylineFile.getRateValue()))) {
							
					lineField=true;
				}
					
				 else if (!(null== obCreatefacilitylineFile.getSendToFile() || obCreatefacilitylineFile.getSendToFile().isEmpty())){
						lineField=true;	 								
					}else if (!(null==obCreatefacilitylineFile.getLiabBranch() || "".equals(obCreatefacilitylineFile.getLiabBranch()))) {
						lineField=true;
							 						
					}else  if (null != obCreatefacilitylineFile.getLimitStartDate()) {
						lineField=true;
					}else  if (!(null== obCreatefacilitylineFile.getAvailable() || obCreatefacilitylineFile.getAvailable().isEmpty())){
						lineField=true;
						 }
					else if (!(null== obCreatefacilitylineFile.getFreeze() || obCreatefacilitylineFile.getFreeze().isEmpty())) {
							 						   
						lineField=true;
						}else if (!(null== obCreatefacilitylineFile.getRevolvingLine() || obCreatefacilitylineFile.getRevolvingLine().isEmpty())) {
						  lineField=true;
						}else if (!(obCreatefacilitylineFile.getSegment1() == null || obCreatefacilitylineFile.getSegment1().equals(""))) {
							lineField=true;
						}else if (!(obCreatefacilitylineFile.getRuleId() == null || obCreatefacilitylineFile.getRuleId().equals(""))) {
								lineField=true;
						}else  if (!(null==obCreatefacilitylineFile.getUncondCancelComm() || "".equals(obCreatefacilitylineFile.getUncondCancelComm()))) {
							 					
							 	lineField=true;
							}else  if (!(null==obCreatefacilitylineFile.getRemark() || "".equals(obCreatefacilitylineFile.getRemark()))) {
			 					lineField=true;
						}else  if (!(null==obCreatefacilitylineFile.getLimitTenor() || "".equals(obCreatefacilitylineFile.getLimitTenor()))) {
					
									lineField=true;
							}
							 
				else if (!(null==obCreatefacilitylineFile.getSystemID() || "".equals(obCreatefacilitylineFile.getSystemID()))) { 
					lineField=true;
				}
				else if(!(null==obCreatefacilitylineFile.getReleaseAmount() || "".equals(obCreatefacilitylineFile.getReleaseAmount()))) {
						lineField=true;
					}
				if(lineField) {
								 reason=reason+" Please make sure all line level fields should be blank as release flag is 'N'."; 
							 }
				
				 
			 }else {
				 reason=reason+" Released flag should be 'Y','N' or empty."; 
			 }
			 
			
			 obCreatefacilitylineFile.setReason(reason);
			if ("".equals(reason)) {

				obCreatefacilitylineFile.setStatus("PASS");
				// add the data in Map
				if (dummRefFileMap.containsKey(obCreatefacilitylineFile.getDummyRefId())) {
					List<OBCreatefacilitylineFile> list = dummRefFileMap.get(obCreatefacilitylineFile.getDummyRefId());
					list.add(obCreatefacilitylineFile);
					dummRefFileMap.put(obCreatefacilitylineFile.getDummyRefId(), list);
				} else {
					ArrayList createFacList = new ArrayList();
					createFacList.add(obCreatefacilitylineFile);
					dummRefFileMap.put(obCreatefacilitylineFile.getDummyRefId(), createFacList);
				}
			} else {
				obCreatefacilitylineFile.setStatus("FAIL");
			}
			 
			
		}
		
	
	}

	private void createFacilityLineTrx(List<OBCreatefacilitylineFile> totalUploadedList,LimitDAO limitDao,OBTrxContext context, HashMap<String, List<OBCreatefacilitylineFile>> dummRefFileMap, ResourceBundle bundle, HashMap fccBranchIdMap, Date applicationDate, HashMap<String, String> facMaster, DateFormat simpleDateFormat) {
		// Create facility and line for proper objects 
		
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		DefaultLogger.debug(this, "inside createFacilityLineTrx");
	
		Iterator<Entry<String, List<OBCreatefacilitylineFile>>> dummRefFileMapItr = dummRefFileMap.entrySet().iterator(); 
        
        while(dummRefFileMapItr.hasNext()) 
        { 
             Entry<String, List<OBCreatefacilitylineFile>> entry = dummRefFileMapItr.next(); 
             String key = entry.getKey();
             List<OBCreatefacilitylineFile> createfacilitylineFileList = entry.getValue();
             DefaultLogger.debug(this, "Key = " + entry.getKey() +  
                                 ", Value = " + entry.getValue()); 
            
//		for (int j = 0; j < createfacilitylineFileList.size();j++) {.size
             OBCreatefacilitylineFile ob = null;
             if(createfacilitylineFileList.size()>0) {
			 ob=(OBCreatefacilitylineFile)createfacilitylineFileList.get(0);
             }
		String limitProfileID = limitDao.getLimitProfileID(ob.getPartyId());
		DefaultLogger.debug(this, "completed getLimitFromFile ");
		ILimitProfile profile;
		try {
			profile = limitProxy.getLimitProfile(Long.parseLong(limitProfileID));
		
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		//Fetching Party Details and set to the context 
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
		
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		
		OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
		
		OBLimit newLimit = getLimitFromFile(ob,createfacilitylineFileList,limitDao,bundle,fccBranchIdMap,applicationDate,facMaster,simpleDateFormat);
		newLimit.setLimitProfileID(Long.parseLong(limitProfileID));
		limitTrxValue.setStagingLimit(newLimit);

		/*OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;

		user = (OBCommonUser) getUserProxy().getUser("CPUADM_C");
		teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
*/
		
		context.setLimitProfile(profile);
		context.setCustomer(cust);
		helper.setTrxLocation(context, limitTrxValue.getStagingLimit());
		limitTrxValue.setCustomerID(cust.getCustomerID());
		limitTrxValue.setCustomerName(cust.getCustomerName());
		limitTrxValue.setLegalID(profile.getLEReference());
		limitTrxValue.setLegalName(cust.getCustomerName());
		limitTrxValue.setTeamID(context.getTeam().getTeamID());
		limitTrxValue.setLimitProfileID(Long.parseLong(limitProfileID));
		limitTrxValue.setLimitProfileReferenceNumber(profile.getBCAReference());
		
		DefaultLogger.debug(this, "inside createFacilityLineTrx calling createLimitTrx");
		
		try {
		ICMSTrxResult res = proxy.createLimitTrx(context, limitTrxValue, false);
		
		for(int l=0;l<createfacilitylineFileList.size();l++) {
			
			createfacilitylineFileList.get(l).setStatus("PASS");
			createfacilitylineFileList.get(l).setReason("Facility created by maker with facility id '"+((OBLimitTrxValue)res.getTrxValue()).getStagingReferenceID()+"' .");
	
		}
	
		DefaultLogger.debug(this, "inside createFacilityLineTrx res");
		}
		catch (LimitException e) {
			// TODO Auto-generated catch block
			for(int l=0;l<createfacilitylineFileList.size();l++) {
		
					createfacilitylineFileList.get(l).setStatus("FAIL");
					createfacilitylineFileList.get(l).setReason("Exception while creating limit: "+e.getMessage());
			
			}
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception:"+e.getMessage());
		}

			}
		
		 catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception:"+e.getMessage());
			} 
		catch (Exception ex) {
			ex.printStackTrace();
			DefaultLogger.debug(this, "Exception:"+ex.getMessage());
		
		}
       // }
        }
        DefaultLogger.debug(this, "completed createFacilityLineTrx");
	}

	public OBLimit  getLimitFromFile(OBCreatefacilitylineFile ob,List<OBCreatefacilitylineFile> obList ,LimitDAO limitDao, ResourceBundle bundle, HashMap fccBranchIdMap, Date applicationDate, HashMap<String, String> facMaster, DateFormat simpleDateFormat) {

		DefaultLogger.debug(this, "inside getLimitFromFile");
		
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem =bundle.getString("ubs.systemName");
		String defaultUncCancelComm = bundle.getString("createfaclineupload.default.unconditionalCancelCommCode");
		String defaultCurrency = bundle.getString("createfaclineupload.default.currency");
		
		//facility
		OBLimit lmtObj = new OBLimit();
		lmtObj.setFacilityCat(ob.getFacilityCategory());
		lmtObj.setFacilityName(ob.getFacilityName());
		/*String facDetFromMaster = limitDao.getFacDetFromMaster(ob);
		String test="ABC";
		DefaultLogger.debug(this, "test:"+test);
		String[] str = facDetFromMaster.split("\\|");*/
		
		String key=ob.getFacilityCategory()+"|"+ob.getFacilityName()+"|"+ob.getSystem();
		String facValue = facMaster.get(key);
		String[] str = facValue.split("\\|");
		DefaultLogger.debug(this, "inside getLimitFromFile str:"+str);
		if(null!=str && str.length==4) {
			lmtObj.setFacilityCode(str[0]);
			lmtObj.setFacilityType(str[1]);				
			lmtObj.setLineNo(str[2]);		
			lmtObj.setPurpose(str[3]);		
		}
	    lmtObj.setFacilitySystem(ob.getSystem());	
		lmtObj.setIsSecured("N");
		lmtObj.setSyndicateLoan("N");
		lmtObj.setLimitType("No");
		
		if(null==ob.getIsReleased() || "".equals(ob.getIsReleased()) || "YES".equalsIgnoreCase(ob.getIsReleased()) ||  "Y".equalsIgnoreCase(ob.getIsReleased())) {
			lmtObj.setIsReleased("Y");	
		}else {
			lmtObj.setIsReleased("N");	
		}
		
		if(null==ob.getGrade() || "".equals(ob.getGrade())) {
			lmtObj.setGrade("5");	
		}else {
			lmtObj.setGrade(ob.getGrade());	
		}
		
		if(null==ob.getCurrency() || "".equals(ob.getCurrency())) {
			lmtObj.setCurrencyCode(defaultCurrency);	
		}else {
			lmtObj.setCurrencyCode(ob.getCurrency());	
		}
		
			lmtObj.setRequiredSecurityCoverage(ob.getSanctionedAmt());
			lmtObj.setReleasableAmount(ob.getReleasableAmt());
			if("N".equals(lmtObj.getIsReleased())) {
			lmtObj.setTotalReleasedAmount("");
			}
	        else {
	        	
	        	//lmtObj.setTotalReleasedAmount(ob.getTotalReleasedAmt());//set this in vailidation
			}
			
			if("YES".equalsIgnoreCase(ob.getIsAdhoc()) ||  "Y".equalsIgnoreCase(ob.getIsAdhoc())) {
				lmtObj.setIsAdhoc("Y");	
				lmtObj.setIsAdhocToSum("N"); //N
				lmtObj.setAdhocLmtAmount(ob.getAdhoclimitAmt());
			
			}else {
				lmtObj.setIsAdhoc("N");	
				lmtObj.setIsAdhocToSum("N");//N
			}
		
		
		//lmtObj.setGuarantee("No");	
		
	    lmtObj.setLimitStatus("ACTIVE");
		lmtObj.setIsFromCamonlineReq('N');
		
		//line details
//		ILimitSysXRef[] limitSysXRefs = new ILimitSysXRef;
//		ILimitSysXRef  iLimitSysXRef=
		if("Y".equals(lmtObj.getIsReleased()) ) {
			
		int arrayLength = (obList == null ? 0 : obList.size());
		ILimitSysXRef[] limitSysXRefs = new ILimitSysXRef[arrayLength];
		String totalReleaseAmt="";
		for(int k=0;k<obList.size();k++) {
		OBCreatefacilitylineFile oBCreatefacilityline=obList.get(k);
		OBCustomerSysXRef account=new OBCustomerSysXRef();
		if("".equals(totalReleaseAmt)) {
			totalReleaseAmt=oBCreatefacilityline.getReleaseAmount();	
		}
		else {
			String newRelAmount=oBCreatefacilityline.getReleaseAmount();	
		BigDecimal newRelAmountbd=new BigDecimal(newRelAmount);
		BigDecimal totalReleaseAmtbd=new BigDecimal(totalReleaseAmt);
		BigDecimal total=totalReleaseAmtbd.add(newRelAmountbd);
			totalReleaseAmt=total.toString();
		}
		lmtObj.setTotalReleasedAmount(totalReleaseAmt);
		account.setUtilizedAmount("0");
//start
		if(null==oBCreatefacilityline.getInterestRate() || "".equals(oBCreatefacilityline.getInterestRate()) || "fixed".equalsIgnoreCase(oBCreatefacilityline.getInterestRate())) {
			 account.setInterestRateType("fixed");
			 
			 if(null==oBCreatefacilityline.getRateValue() || "".equals(oBCreatefacilityline.getRateValue()) || "0".equalsIgnoreCase(oBCreatefacilityline.getRateValue())) {
			 account.setIntRateFix("0");
			 }else {
				 account.setIntRateFix(oBCreatefacilityline.getRateValue());	 
			 }
		}
   
	
		account.setReleasedAmount(oBCreatefacilityline.getReleaseAmount());
    
	try {
		if(null!=oBCreatefacilityline.getLimitExpiryDate()) {
		account.setDateOfReset(simpleDateFormat.parse(oBCreatefacilityline.getLimitExpiryDate()));
		}else {
			account.setDateOfReset(null);
		}
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	if(null==oBCreatefacilityline.getIsCapitalMarketExpo() || "".equals(oBCreatefacilityline.getIsCapitalMarketExpo()) || "N".equalsIgnoreCase(oBCreatefacilityline.getIsCapitalMarketExpo())) {
		account.setIsCapitalMarketExposer("No");	
	}else {
		account.setIsCapitalMarketExposer("Yes");	
	}
	
	if(null==oBCreatefacilityline.getIsRealEstateExpo() || "".equals(oBCreatefacilityline.getIsRealEstateExpo()) || "N".equalsIgnoreCase(oBCreatefacilityline.getIsRealEstateExpo())) {
		account.setIsRealEstateExposer("No");	
	}else {
		account.setIsRealEstateExposer("Yes");	
		
		if("Commercial Real estate".equalsIgnoreCase(oBCreatefacilityline.getRealEsExpoValue()) || "Residential real Estate".equalsIgnoreCase(oBCreatefacilityline.getRealEsExpoValue()) || "Indirect real estate".equalsIgnoreCase(oBCreatefacilityline.getRealEsExpoValue()))
		account.setEstateType(oBCreatefacilityline.getRealEsExpoValue());
		
		if("Commercial Real estate".equalsIgnoreCase(oBCreatefacilityline.getRealEsExpoValue())){
			account.setCommRealEstateType(oBCreatefacilityline.getCommercialRealEstate());
		}else{
			account.setCommRealEstateType("");
		}
	}
	
	
	
	if(null==oBCreatefacilityline.getPslFlag() || "".equals(oBCreatefacilityline.getPslFlag()) || "N".equalsIgnoreCase(oBCreatefacilityline.getPslFlag())) {
		account.setIsPrioritySector("No");
	}else {
		account.setIsPrioritySector("Yes");
	}
	
	account.setPrioritySector(oBCreatefacilityline.getPslValue());
	account.setFacilitySystem(oBCreatefacilityline.getSystem());
	account.setFacilitySystemID(oBCreatefacilityline.getSystemID());
	account.setLineNo(str[2]);
	//account.setLineNo(oBCreatefacilityline.getLineNo());

	//DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	account.setCreatedOn(applicationDate);
//	account.setCreatedOn(oBCreatefacilityline.getUploadDate());
	
	account.setCreatedBy(oBCreatefacilityline.getUploadedBy());
	
	account.setUploadStatus("N");
	try {
		if(null==oBCreatefacilityline.getLimitStartDate()) {
			account.setLimitStartDate(null);	
		}else {
		account.setLimitStartDate(simpleDateFormat.parse(oBCreatefacilityline.getLimitStartDate()));
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	Calendar c =Calendar.getInstance();

		//end
		if(null!=oBCreatefacilityline.getSystem() && (oBCreatefacilityline.getSystem().equals(fcubsSystem) || oBCreatefacilityline.getSystem().equals(ubsSystem))) {
			if("N".equals(oBCreatefacilityline.getSendToFile())){
				account.setSerialNo(oBCreatefacilityline.getSerialNo());	
				account.setHiddenSerialNo(oBCreatefacilityline.getSerialNo());		
			}else {
			Random random = new Random(); 
			String serialNo=""+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MILLISECOND)+k+random.nextInt(999);
			account.setSerialNo(serialNo);
			account.setHiddenSerialNo(serialNo);
			}
			account.setStatus("PENDING_UPDATE");
			account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_NEW);
			
			account.setSegment(oBCreatefacilityline.getSegment1());
			account.setRuleId(oBCreatefacilityline.getRuleId());
			//defaultUncCancelComm
			if(null==oBCreatefacilityline.getUncondCancelComm() || "".equals(oBCreatefacilityline.getUncondCancelComm())) {
			account.setUncondiCancl(defaultUncCancelComm);
			}else {
				account.setUncondiCancl(oBCreatefacilityline.getUncondCancelComm());	
			}
			account.setLimitTenorDays(oBCreatefacilityline.getLimitTenor());
			account.setInternalRemarks(oBCreatefacilityline.getRemark());
			
			if(null==oBCreatefacilityline.getSendToFile() || "".equals(oBCreatefacilityline.getSendToFile()) || "Y".equalsIgnoreCase(oBCreatefacilityline.getSendToFile())) {
				account.setSendToFile("Y");	
			}else {
				account.setSendToFile("N");	
			}
			
			
			if(null==oBCreatefacilityline.getAvailable() || "".equals(oBCreatefacilityline.getAvailable()) || "Y".equalsIgnoreCase(oBCreatefacilityline.getAvailable())) {
				account.setAvailable("Yes");	
			}else {
				account.setAvailable("No");	
			}
			if(null==oBCreatefacilityline.getFreeze() || "".equals(oBCreatefacilityline.getFreeze()) || "N".equalsIgnoreCase(oBCreatefacilityline.getFreeze())) {
				account.setFreeze("No");	
			}else {
				account.setFreeze("Yes");	
			}
			if(null==oBCreatefacilityline.getRevolvingLine() || "".equals(oBCreatefacilityline.getRevolvingLine()) || "Y".equalsIgnoreCase(oBCreatefacilityline.getRevolvingLine())) {
				account.setRevolvingLine("Yes");	
			}else {
				account.setRevolvingLine("No");	
			}
			
			account.setCloseFlag("N");
			account.setCurrencyRestriction("N");
			account.setCurrency(lmtObj.getCurrencyCode());
			
			String liabId = (String)fccBranchIdMap.get(oBCreatefacilityline.getLiabBranch());
			account.setLiabBranch(liabId);
			
			
			
		}else {
			account.setSerialNo(oBCreatefacilityline.getSerialNo());	
			account.setHiddenSerialNo(oBCreatefacilityline.getSerialNo());
		}
		
		limitSysXRefs[k] = createLimitSysXRef(account);
		DefaultLogger.debug(this, "inside getLimitFromFile adding limitSysXRefs"+limitSysXRefs[k]);
		lmtObj.setLimitSysXRefs(limitSysXRefs);
		}
		}
		DefaultLogger.debug(this, "completed getLimitFromFile ");
		return lmtObj;
	}
	
	private ILimitSysXRef createLimitSysXRef(ICustomerSysXRef account) {
		ILimitSysXRef link = new OBLimitSysXRef();
		link.setCustomerSysXRef(account);
		link.setStatus(ICMSConstant.STATE_ACTIVE);
		return link;
	}
}