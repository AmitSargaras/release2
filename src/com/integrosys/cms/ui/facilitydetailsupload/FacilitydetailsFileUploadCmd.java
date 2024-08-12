/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.facilitydetailsupload;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
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

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.apache.poi.ss.usermodel.HSSFDateUtil;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFacilitydetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileFacilitydetails;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
//import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.ui.facilitydetailsupload.proxy.IFacilitydetailsUploadProxyManager;
import com.integrosys.cms.ui.manualinput.limit.LimitListSummaryItem;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

import java.util.Iterator;

/**
 * @author $Author: Abhijeet J$ Command for Upload UBS File
 */
public class FacilitydetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String FACILTIY_DETAILS_UPLOAD = "FacilitydetailsUpload";
	private IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy;

	public IFacilitydetailsUploadProxyManager getFacilitydetailsuploadProxy() {
		return facilitydetailsuploadProxy;
	}

	public void setFacilitydetailsuploadProxy(IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy) {
		this.facilitydetailsuploadProxy = facilitydetailsuploadProxy;
	}

	/**
	 * Default Constructor
	 */

	public FacilitydetailsFileUploadCmd() {
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
				{ "facilitydetailsUploadObj",
						"com.integrosys.cms.ui.facilitydetailsupload.OBFacilitydetailsUpload", FORM_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "fileType", "java.lang.String", REQUEST_SCOPE },
				{ "trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue",
						SERVICE_SCOPE },
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
		//IFacilitydetailsErrorLog objFacilitydetailsErrorLog = new OBFacilitydetailsErrorLog();
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();

		long countPass = 0;
		long countFail = 0;
		ActionErrors errors = new ActionErrors();
		int size = 0;
		List finalList = new ArrayList<FacilitydetailsError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("FCT_UPLOAD");
		try {

			OBFacilitydetailsUpload facilitydetailsUpload = (OBFacilitydetailsUpload) map
					.get("facilitydetailsUploadObj");
			if (facilitydetailsUpload.getFileUpload().getFileName().isEmpty()) {
				exceptionMap.put("facilitydetailsfileuploadError",
						new ActionMessage("label.facilitydetails.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			} else if (!facilitydetailsUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !facilitydetailsUpload.getFileUpload().getFileName().endsWith(".XLS")
					&& !facilitydetailsUpload.getFileUpload().getFileName().endsWith(".XLSX")
					&& !facilitydetailsUpload.getFileUpload().getFileName().endsWith(".xlsx")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {

				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null);
				ctx.setLimitProfile(null);

				ProcessDataFileFacilitydetails dataFile = new ProcessDataFileFacilitydetails();
				resultList = dataFile.processFile(facilitydetailsUpload.getFileUpload(), FACILTIY_DETAILS_UPLOAD);
				IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				HashMap eachDataMap = (HashMap) dataFile.getErrorList();

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

				List list = new ArrayList(eachDataMap.values());
				for (int i = 0; i < list.size(); i++) {
					String[][] errList = (String[][]) list.get(i);
					for (int p = 0; p < 8; p++) {
						FacilitydetailsError facilitydetailsError = new FacilitydetailsError();
						if (null != errList[p][0]) {
							facilitydetailsError.setColumnName(errList[p][0]);
							facilitydetailsError.setErrorMessage(errList[p][1]);
							facilitydetailsError.setFileRowNo(errList[p][3]);
							if (100 == finalList.size()) {
								break;
							}
							finalList.add(facilitydetailsError);
						}
					}
					if (100 == finalList.size()) {
						break;
					}
				}
				FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				ILimitTrxValue lmtTrxObj = null;
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				//LimitDetailsJdbc lmtdao = new LimitDetailsJdbc();
				Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
				ILimit curLmt = null;
				
				if (finalList.size() == 0) {
					if (resultList.size() > 0) {

						size = resultList.size();
						DefaultLogger.debug(this,
								"#####################In FacilitydetailsFileUploadCmd ############:: " + size);
						// create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("FCT_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(facilitydetailsUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList.size()));
						trxValueOut = getFacilitydetailsuploadProxy().makerCreateFile(ctx, fileObj);// ENTRY IN
																										// CMS_FILE_UPLOAD
						long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
						if (trxValueOut != null) {
							DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
							for (int i = 1; i < resultList.size(); i++) {
								rowlist = (ArrayList) resultList.get(i);
								exceldatalist.clear();
								for (int j = 0; j < rowlist.size(); j++) {
									if (facilitydetailsUpload.getFileUpload().getFileName().endsWith(".XLS")
											|| facilitydetailsUpload.getFileUpload().getFileName()
													.endsWith(".xls")) {
										HSSFCell cell = (HSSFCell) rowlist.get(j);
										if (cell == null)
											exceldatalist.add(null);
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
											exceldatalist.add(cell.getNumericCellValue());
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
											exceldatalist.add(cell.getStringCellValue());
										else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											exceldatalist.add(null);
									} else if (facilitydetailsUpload.getFileUpload().getFileName().endsWith(".XLSX")
											|| facilitydetailsUpload.getFileUpload().getFileName()
													.endsWith(".xlsx")) {
										XSSFCell cell = (XSSFCell) rowlist.get(j);
										if (cell == null)
											exceldatalist.add(null);
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
											int val = (int)cell.getNumericCellValue(); 
											String strCellValue = String.valueOf(val);
											exceldatalist.add(strCellValue);
										}
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
											exceldatalist.add(cell.getStringCellValue());
										else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
											exceldatalist.add(null);
									}

								}

								OBFacilitydetailsFile obj = new OBFacilitydetailsFile();
								
								if (null != exceldatalist.get(0) && !exceldatalist.get(0).toString().trim().isEmpty()) {
									obj.setFacilityID((String.valueOf(exceldatalist.get(0)).trim()));
								} else {
									obj.setFacilityID(""); 
								}

								if (null != exceldatalist.get(1) && !exceldatalist.get(1).toString().trim().isEmpty()) {
									obj.setSanctionAmt((String.valueOf(exceldatalist.get(1)).trim()));
								} else {
									obj.setSanctionAmt("");
								}

								if (null != exceldatalist.get(2) && !exceldatalist.get(2).toString().trim().isEmpty()) {
									obj.setSanctionAmtInr((String.valueOf(exceldatalist.get(2)).trim()));
								} else {
									obj.setSanctionAmtInr("");
								}

								if (null != exceldatalist.get(3) && !exceldatalist.get(3).toString().trim().isEmpty()) {
									obj.setReleasableAmt((String.valueOf(exceldatalist.get(3)).trim()));
								} else {
									obj.setReleasableAmt("");
								}

								
								
								LimitDAO limitDao=new LimitDAO();
								String failReason="";
								String passReason="";
								//LmtDetailForm curLmt = new LmtDetailForm();
								
								if(obj.getFacilityID() != null) {
								boolean facId = fileUpload.getFacilittIdValidOrNot(obj.getFacilityID());
								if(facId) {
								long lmtProfId = fileUpload.getLimitProfileId(obj.getFacilityID());
								String lmtProfApprId = String.valueOf(fileUpload.getLimitProfileApprId(obj.getFacilityID()));//20200408000004568
								
								ILimitProfile profile = limitProxy.getLimitProfile(lmtProfId);  
								
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==119==>> profile.getCustomerID()"+ profile.getCustomerID());
								
								ICMSCustomer cust = custProxy.getCustomer(profile.getCustomerID());
								
								
								
								lmtTrxObj = proxy.searchLimitByLmtId(lmtProfApprId);
								
								
								List lmtList = proxy.getLimitSummaryListByAA(String.valueOf(lmtProfId));
								List lmtListFormated = helper.formatLimitListView(lmtList, null);
								
								
								curLmt = lmtTrxObj.getLimit();
								String lmtIdValue = curLmt.getLimitRef(); //20200408000004567
								
								BigDecimal funded = new BigDecimal("0");
								BigDecimal nonFunded = new BigDecimal("0");
								BigDecimal memoExposure = new BigDecimal("0");
								BigDecimal convertedAmount = new BigDecimal("0");
								BigDecimal exchangeRate = new BigDecimal("1");
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==229==>> lmtListFormated.size()"+lmtListFormated.size());
								for(int j = 0; j < lmtListFormated.size(); j++){
									LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(j);
									DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==231==>> lstSummaryItem.getFacilityTypeCode()"+lstSummaryItem.getFacilityTypeCode());
					 				if(!(lstSummaryItem.getLimitId()).subSequence(0, 17).equals(lmtIdValue)){
					 					
					 					
					 					
										if(!AbstractCommonMapper.isEmptyOrNull(lstSummaryItem.getCurrencyCode())){
											 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
											 exchangeRate = frxPxy.getExchangeRateWithINR(lstSummaryItem.getCurrencyCode().trim());
										}
										
										DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==239==>> exchangeRate/lstSummaryItem.getCurrencyCode().trim()"+exchangeRate+"/"+lstSummaryItem.getCurrencyCode().trim());
										DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==240==>> lstSummaryItem.getActualSecCoverage()=="+lstSummaryItem.getActualSecCoverage());
										if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) &&
										 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
										  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
										){
											convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
											funded = funded.add(convertedAmount);
											DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==244==>> convertedAmount/funded"+convertedAmount+"/"+funded);
										}
										
										
										
										if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("NON_FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) && 
												 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
														  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
									){
											convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
											nonFunded = nonFunded.add(convertedAmount);
											DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==252==>> convertedAmount/nonFunded"+convertedAmount+"/"+nonFunded);
										}
										
										
										if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("MEMO_EXPOSURE") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) && 
												 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
														  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
										){
											convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
											memoExposure = memoExposure.add(convertedAmount);
											DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==259==>> convertedAmount/memoExposure"+convertedAmount+"/"+memoExposure);
										}
										
									}
								}
								BigDecimal totFunded = new BigDecimal("0");
								BigDecimal totNonFunded = new BigDecimal("0");
								BigDecimal totMemoExposure = new BigDecimal("0");
								
								if(cust.getTotalFundedLimit()!= null)
								{
									 totFunded = new BigDecimal(cust.getTotalFundedLimit()).subtract(funded);
								}
								if(cust.getTotalNonFundedLimit()!= null)
								{
								 totNonFunded = new BigDecimal(cust.getTotalNonFundedLimit()).subtract(nonFunded);
								}
								if(cust.getMemoExposure()!= null)
								{
								 totMemoExposure = new BigDecimal(cust.getMemoExposure()).subtract(memoExposure);
								}
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==277==>>totFunded/totNonFunded/totMemoExposure/cust.getTotalSanctionedLimit()=>"+totFunded+"/"+totNonFunded+"/"+totMemoExposure+"/"+cust.getTotalSanctionedLimit());
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==278==>>cust.getTotalFundedLimit()=>"+cust.getTotalFundedLimit());
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==279==>>cust.getTotalNonFundedLimit()=>"+cust.getTotalNonFundedLimit());
								DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==280==>>cust.getMemoExposure()=>"+cust.getMemoExposure());
								
							
								
								double sanctionAmount=0;
								String sancAmt="";
								obj.setStatus("PASS");
								boolean numericFlag = true;
								String regex = "\\d+(\\.\\d+)?";
								
								if(obj.getSanctionAmt()!="") {
									if(obj.getSanctionAmt().matches(regex)){
										sanctionAmount=Double.parseDouble(obj.getSanctionAmt());
									}else {
										failReason=failReason+"Failed due to Sanction Amount is not valid amount.";				
										obj.setStatus("FAIL");
										numericFlag = false;
									}
								}else {
									sancAmt=obj.getSanctionAmt();
								}
								
								double sanctionAmountInr=0;
								String sancAmtInr="";
								
								if(obj.getSanctionAmtInr()!="") {
									if(obj.getSanctionAmtInr().matches(regex)){
										sanctionAmountInr=Double.parseDouble(obj.getSanctionAmtInr());
							        }else {
										failReason=failReason+"Failed due to Sanction Amount INR is not valid amount.";				
										obj.setStatus("FAIL");
										numericFlag = false;
									}
								}else {
									sancAmtInr=obj.getSanctionAmtInr();
								}
								
								double relesableamount=0;
								String releasableamt="";
								if(obj.getReleasableAmt()!="") {

									if(obj.getReleasableAmt().matches(regex)){
										relesableamount=Double.parseDouble(obj.getReleasableAmt());
									}else {
										failReason=failReason+"Failed due to Releasable Amount is not valid amount.";				
										obj.setStatus("FAIL");
										numericFlag = false;
									}
								}else {
									releasableamt=obj.getReleasableAmt();
								}
								
								if(obj.getFacilityID()!=null && !"".equals(obj.getFacilityID()) && numericFlag == true) {
									String trxFacStatus = limitDao.getFacilityTransactionStatus(obj.getFacilityID());
									if(trxFacStatus.startsWith("PENDING_")) {
										obj.setFileId(fileId);
										failReason=failReason+"Failed due to Facility is Pending at checker For Authorization.";
										
										obj.setStatus("FAIL");
									}
								}
								
								
								if(obj.getSanctionAmt()!=null && !"".equals(obj.getSanctionAmt()) && numericFlag == true) {
									
										
										if(sanctionAmount >= 0 ) {
											String relsAmt = "";
											
												if(obj.getReleasableAmt() != null && !"".equals(obj.getReleasableAmt()) ) {
													relsAmt = obj.getReleasableAmt();
												}else {
													relsAmt = curLmt.getReleasableAmount();
												}
												

												BigDecimal requiredSecCov =new BigDecimal("0");
												 BigDecimal tempRequiredSecCov =new BigDecimal("0");
												 BigDecimal releasableAmount = new BigDecimal("0");
												 BigDecimal totalReleasedAmount =new BigDecimal("0");
												 boolean flag = false;
												 DefaultLogger.debug(curLmt," FacilityType ==1===> "+curLmt.getFacilityType() );
												 DefaultLogger.debug(curLmt,"Required Security Coverage [Note :- check is not null/empty then flag = true] ==2===> "+obj.getSanctionAmt());
												 String errorCode;
												if (!(errorCode =Validator.checkAmount(obj.getSanctionAmt(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
															.equals(Validator.ERROR_NONE))   {
														/*errors.add("requiredSecCov", new ActionMessage(
																ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
														*/
														obj.setFileId(fileId);
														failReason=failReason+"Failed due to Sanction Amount is not valid amount.";
														
														obj.setStatus("FAIL");
												}
												

												 if (!AbstractCommonMapper.isEmptyOrNull(obj.getSanctionAmt())) {
														if (!(errorCode =Validator.checkAmount(obj.getSanctionAmt(), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
																.equals(Validator.ERROR_NONE))   {
															/*errors.add("requiredSecCov", new ActionMessage(
																	ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
															*/
															failReason=failReason+"Failed due to Sanction Amount is not valid amount.";
															obj.setStatus("FAIL");
														}
														flag = true;
												}
												 
												 
												 if(!AbstractCommonMapper.isEmptyOrNull(curLmt.getCurrencyCode()) && !AbstractCommonMapper.isEmptyOrNull(obj.getSanctionAmt())){
														 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
														 exchangeRate = frxPxy.getExchangeRateWithINR(curLmt.getCurrencyCode().trim());
														 DefaultLogger.debug(curLmt,"exchangeRate [CurrencyCode() of Required Security Coverage ]==3===> "+ curLmt.getCurrencyCode()+" / " +exchangeRate);
												 }
												
												boolean totalReleasedAmountFlag = false; //Flag is maintained so that at a time only one validation can be done one Released Amount
												
												if(flag){
													 DefaultLogger.debug(curLmt,"[flag = true] and [Required Security Coverage()/CurrencyCode]==4===> "+ obj.getSanctionAmt()+" / "+curLmt.getCurrencyCode()+" " +exchangeRate);
												 	if(!obj.getSanctionAmt().trim().equals("") && !AbstractCommonMapper.isEmptyOrNull(curLmt.getCurrencyCode())){
												 		DefaultLogger.debug(curLmt,"[Required Security Coverage() / AdhocLmtAmount] ==5===> "+ obj.getSanctionAmt()+" / "+curLmt.getAdhocLmtAmount());
//												 			requiredSecCov = new BigDecimal(obj.getSanctionAmt().replaceAll(",", "")).add(new BigDecimal(curLmt.getAdhocLmtAmount().replaceAll(",", "")));
												 			requiredSecCov = new BigDecimal(obj.getSanctionAmt().replaceAll(",", ""));
												 		
														tempRequiredSecCov = requiredSecCov.multiply(exchangeRate);
														DefaultLogger.debug(curLmt,"tempRequiredSecCov = [requiredSecCov.multiply(exchangeRate)] ==6===> "+ tempRequiredSecCov);
													}
												 
												 	
												}
												
												
												if(flag && curLmt.getLimitType().equals("No") ){
													
													DefaultLogger.debug(curLmt,"Old Flag is true(Flag checked again) ==16===> Flag = true");
											        /*BigDecimal funded = totFunded;//new BigDecimal(curLmt.getFundedAmount().replaceAll(",", ""));
												    BigDecimal nonFunded = new BigDecimal(curLmt.getNonFundedAmount().replaceAll(",", ""));
													BigDecimal memoExposer = new BigDecimal(curLmt.getMemoExposer().replaceAll(",", ""));*/
													DefaultLogger.debug(curLmt,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
													DefaultLogger.debug(curLmt," Funded / NON_FUNDED / MEMO_EXPOSURE ==17===> "+totFunded +" / " +totNonFunded +" / "+totMemoExposure );	
													DefaultLogger.debug(curLmt, "(AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals(N))===="+(AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals("N")));
													
													DefaultLogger.debug(curLmt, "curLmt.getIsAdhoc() + / +curLmt.getIsAdhoc()===="+curLmt.getIsAdhoc() +" / "+curLmt.getIsAdhoc());
													 BigDecimal tempRequiredSecCovTemp =new BigDecimal("0");
													if (tempRequiredSecCov.compareTo(tempRequiredSecCovTemp) > 0)
													{
														if((curLmt.getFacilityType().equalsIgnoreCase("Funded") || curLmt.getFacilityType().equalsIgnoreCase("FUNDED")) 
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals("N")) 
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhocToSum()) || curLmt.getIsAdhocToSum().equals("N"))
														) {
														if( tempRequiredSecCov.compareTo(totFunded) > 0){
															DefaultLogger.debug(curLmt,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
															//errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Funded Amount"));
															failReason=failReason+"Sanctioned Amount cannot be greater than Total Funded Amount.";
															
															obj.setStatus("FAIL");
														}
														}
														if((curLmt.getFacilityType().equalsIgnoreCase("Funded") || curLmt.getFacilityType().equalsIgnoreCase("FUNDED"))
																&& ( (curLmt.getIsAdhoc()!= null) &&  (curLmt.getIsAdhoc().equals("Y") ||  curLmt.getIsAdhoc().equals("true") || curLmt.getIsAdhoc().equals("on")) )
																&& ((curLmt.getIsAdhocToSum()!= null) && (curLmt.getIsAdhocToSum().equals("Y") || curLmt.getIsAdhocToSum().equals("on")) )
														) {
														if( tempRequiredSecCov.compareTo(totFunded) > 0){
															DefaultLogger.debug(curLmt,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
															//errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Funded Amount"));
															failReason=failReason+"Sanctioned Amount cannot be greater than Total Funded Amount.";

															obj.setStatus("FAIL");
														}
														}
														if((curLmt.getFacilityType().equalsIgnoreCase("Non Funded") || curLmt.getFacilityType().equalsIgnoreCase("NON_FUNDED") )
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals("N")) 
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhocToSum()) || curLmt.getIsAdhocToSum().equals("N"))
														) {
														if( tempRequiredSecCov.compareTo(totNonFunded) > 0 ) {
															DefaultLogger.debug(curLmt,"If NON_FUNDED then [tempRequiredSecCov > nonFunded ] ==19===> "+tempRequiredSecCov +" > " +nonFunded);
													//		errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Non Funded Amount"));
															failReason=failReason+"Sanctioned Amount cannot be greater than Total Non Funded Amount.";
															obj.setStatus("FAIL");
													
														}
														}
														if((curLmt.getFacilityType().equalsIgnoreCase("Non Funded") || curLmt.getFacilityType().equalsIgnoreCase("NON_FUNDED") )
																&& ((curLmt.getIsAdhoc()!= null) &&  (curLmt.getIsAdhoc().equals("Y") ||  curLmt.getIsAdhoc().equals("true")  || curLmt.getIsAdhoc().equals("on")))
																&& ((curLmt.getIsAdhocToSum()!= null) &&   (curLmt.getIsAdhocToSum().equals("Y")  || curLmt.getIsAdhocToSum().equals("on")))
														) {
														if( tempRequiredSecCov.compareTo(totNonFunded) > 0) {
															DefaultLogger.debug(curLmt,"If NON_FUNDED then [tempRequiredSecCov > nonFunded ] ==19===> "+tempRequiredSecCov +" > " +nonFunded);
															//errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Non Funded Amount"));
															failReason=failReason+"Sanctioned Amount cannot be greater than Total Non Funded Amount.";
															obj.setStatus("FAIL");
													
														}
														}
														/*if( tempRequiredSecCov.compareTo(totMemoExposure) > 0
																&& (curLmt.getFacilityType().equalsIgnoreCase("Memo Exposure") || curLmt.getFacilityType().equalsIgnoreCase("MEMO_EXPOSURE"))
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals("N"))
																&& (AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhocToSum()) || curLmt.getIsAdhocToSum().equals("N"))
														){
															DefaultLogger.debug(curLmt,"If MEMO_EXPOSURE then [tempRequiredSecCov > memoExposer ] ==20===> "+tempRequiredSecCov +" > " +totMemoExposure);
															errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Memo Exposer Amount"));
														}

														if( tempRequiredSecCov.compareTo(totMemoExposure) > 0 
																&& (curLmt.getFacilityType().equalsIgnoreCase("Memo Exposure") || curLmt.getFacilityType().equalsIgnoreCase("MEMO_EXPOSURE"))
																&& ( (curLmt.getIsAdhoc()!= null) && (curLmt.getIsAdhoc().equals("Y") ||  curLmt.getIsAdhoc().equals("true")  || curLmt.getIsAdhoc().equals("on")))
																&& ((curLmt.getIsAdhocToSum()!= null) && (curLmt.getIsAdhocToSum().equals("Y") || curLmt.getIsAdhocToSum().equals("on")) )
														){
															DefaultLogger.debug(curLmt,"If MEMO_EXPOSURE then [tempRequiredSecCov > memoExposer ] ==20===> "+tempRequiredSecCov +" > " +totMemoExposure);
															errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Memo Exposer Amount"));
														}
				*/									}
												}
												//start:added else loop for below validation(Individually sanction amount of each sub limit should not exceed sanction amount of main limit.)
												else if(flag && curLmt.getLimitType().equals("Yes") && curLmt.getGuarantee().equals("No") && !curLmt.getMainFacilityId().isEmpty()){
													
													DefaultLogger.debug(curLmt,"Old Flag is true(Flag checked again) ==16===> Flag = true");
											      //  BigDecimal funded = new BigDecimal(curLmt.getFundedAmount().replaceAll(",", ""));
												   // BigDecimal nonFunded = new BigDecimal(curLmt.getNonFundedAmount().replaceAll(",", ""));
													//BigDecimal memoExposer = new BigDecimal(curLmt.getMemoExposer().replaceAll(",", ""));
													//DefaultLogger.debug(curLmt,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
													//DefaultLogger.debug(curLmt," Funded / NON_FUNDED / MEMO_EXPOSURE ==17===> "+funded +" / " +nonFunded +" / "+memoExposer );	
													//DefaultLogger.debug(curLmt, "(AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals(N))===="+(AbstractCommonMapper.isEmptyOrNull(curLmt.getIsAdhoc()) || curLmt.getIsAdhoc().equals("N")));
													
													DefaultLogger.debug(curLmt, "curLmt.getIsAdhoc() + / +curLmt.getIsAdhoc()===="+curLmt.getIsAdhoc() +" / "+curLmt.getIsAdhoc());
													 BigDecimal tempRequiredSecCovTemp =new BigDecimal("0");
													 
//													 LimitDAO limitDao=new LimitDAO();
													 BigDecimal mainLimitSanctionAmount=limitDao.getSanctionedAmount(curLmt.getMainFacilityId());
													if (tempRequiredSecCov.compareTo(tempRequiredSecCovTemp) > 0)
													{
														BigDecimal mainLimitSanctionAmountInInr= mainLimitSanctionAmount.multiply(exchangeRate);
														if( tempRequiredSecCov.compareTo(mainLimitSanctionAmountInInr) > 0){
															DefaultLogger.debug(curLmt,"If SubLimit then [tempRequiredSecCov > mainLimitSanctionAmountInInr ] ==18===> "+tempRequiredSecCov +" > " +mainLimitSanctionAmountInInr);
															//errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sub Limit Sanctioned Amount", "Main Limit Sanctioned Amount"));
															failReason=failReason+"Sub Limit Sanctioned Amount cannot be greater than Main Limit Sanctioned Amount.";
															obj.setStatus("FAIL");
														}

														
													}
												}  // end
	
										}
										else {
											if(sanctionAmount < 0) {
												failReason="Failed due to Sanction Amount is not valid.";
											}

											obj.setStatus("FAIL");
										}
									}
								
							
								if(obj.getReleasableAmt()!=null && !"".equals(obj.getReleasableAmt()) && numericFlag == true) {
									
										String SancAmt = "";
										if(relesableamount >= 0 ) {
											
											if(obj.getSanctionAmt() != null && !"".equals(obj.getSanctionAmt())) {
												SancAmt = obj.getSanctionAmt();
											}else {
												SancAmt = curLmt.getRequiredSecurityCoverage();
											}
											

												BigDecimal requiredSecCov =new BigDecimal("0");
												 BigDecimal tempRequiredSecCov =new BigDecimal("0");
												 BigDecimal releasableAmount = new BigDecimal("0");
												 BigDecimal totalReleasedAmount =new BigDecimal("0");
												 boolean flag = false;
												 DefaultLogger.debug(curLmt," FacilityType ==1===> "+curLmt.getFacilityType() );
												 DefaultLogger.debug(curLmt,"Required Security Coverage [Note :- check is not null/empty then flag = true] ==2===> "+SancAmt);
												 String errorCode;
												if (!(errorCode =Validator.checkAmount(SancAmt, true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
															.equals(Validator.ERROR_NONE))   {
														/*errors.add("requiredSecCov", new ActionMessage(
																ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
														*/
														obj.setFileId(fileId);
														failReason=failReason+"Failed due to Sanction Amount is not valid amount.";
														obj.setStatus("FAIL");
												}
												

												 if (!AbstractCommonMapper.isEmptyOrNull(SancAmt)) {
														if (!(errorCode =Validator.checkAmount(SancAmt, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
																.equals(Validator.ERROR_NONE))   {
															/*errors.add("requiredSecCov", new ActionMessage(
																	ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
															*/
															obj.setFileId(fileId);
															failReason=failReason+"Failed due to Sanction Amount is not number.";
															obj.setStatus("FAIL");
														}
														flag = true;
												}
												 
												 
												 if(!AbstractCommonMapper.isEmptyOrNull(curLmt.getCurrencyCode()) && !AbstractCommonMapper.isEmptyOrNull(SancAmt)){
														 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
														 exchangeRate = frxPxy.getExchangeRateWithINR(curLmt.getCurrencyCode().trim());
														 DefaultLogger.debug(curLmt,"exchangeRate [CurrencyCode() of Required Security Coverage ]==3===> "+ curLmt.getCurrencyCode()+" / " +exchangeRate);
												 }
												
												boolean totalReleasedAmountFlag = false; //Flag is maintained so that at a time only one validation can be done one Released Amount
												
												if(flag){
													 DefaultLogger.debug(curLmt,"[flag = true] and [Required Security Coverage()/CurrencyCode]==4===> "+ SancAmt+" / "+curLmt.getCurrencyCode()+" " +exchangeRate);
													 if(!SancAmt.trim().equals("") && !AbstractCommonMapper.isEmptyOrNull(curLmt.getCurrencyCode())){
													 		DefaultLogger.debug(curLmt,"[Required Security Coverage() / AdhocLmtAmount] ==5===> "+ SancAmt+" / "+curLmt.getAdhocLmtAmount());
//													 			requiredSecCov = new BigDecimal(obj.getSanctionAmt().replaceAll(",", "")).add(new BigDecimal(curLmt.getAdhocLmtAmount().replaceAll(",", "")));
													 			requiredSecCov = new BigDecimal(SancAmt.replaceAll(",", ""));
													 		
															tempRequiredSecCov = requiredSecCov.multiply(exchangeRate);
															DefaultLogger.debug(curLmt,"tempRequiredSecCov = [requiredSecCov.multiply(exchangeRate)] ==6===> "+ tempRequiredSecCov);
														}
												 
												 	if(obj.getReleasableAmt() !=null && !obj.getReleasableAmt().trim().equals("")){
												 		releasableAmount = new BigDecimal(obj.getReleasableAmt().replaceAll(",", ""));
												 		DefaultLogger.debug(curLmt,"releasableAmount[obj.getReleasableAmt()] ==7===> "+ releasableAmount);
													}
													
													if(curLmt.getTotalReleasedAmount()!=null && !curLmt.getTotalReleasedAmount().trim().equals("")){
														totalReleasedAmount = new BigDecimal(curLmt.getTotalReleasedAmount().replaceAll(",", ""));
														DefaultLogger.debug(curLmt,"totalReleasedAmount[curLmt.getTotalReleasedAmount()] ==8===> "+ totalReleasedAmount);
													}
														
													if( totalReleasedAmount.compareTo(releasableAmount) > 0){
														DefaultLogger.debug(curLmt,"[Released Amount > releasableAmount] ==9===> "+totalReleasedAmount +" > " +releasableAmount);
															errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Releasable Amount"));
															obj.setFileId(fileId);
															failReason=failReason+"Releasable Amount can not be less than Released Amount.";
															obj.setStatus("FAIL");
														
														
														totalReleasedAmountFlag=true;
													}
												}else{
													DefaultLogger.debug(curLmt,"flag = false ==10===> "+flag);
													if(obj.getReleasableAmt()!=null && !obj.getReleasableAmt().trim().equals("")){
												 		releasableAmount = new BigDecimal(obj.getReleasableAmt().replaceAll(",", ""));
												 		DefaultLogger.debug(curLmt,"[releasableAmount] ==11===> "+releasableAmount);
													}
													
													if(curLmt.getTotalReleasedAmount()!=null && !curLmt.getTotalReleasedAmount().trim().equals("")){
														totalReleasedAmount = new BigDecimal(curLmt.getTotalReleasedAmount().replaceAll(",", ""));
														DefaultLogger.debug(curLmt,"[totalReleasedAmount] ==12===> "+totalReleasedAmount);
													}
													if( totalReleasedAmount.compareTo(releasableAmount) > 0){
														DefaultLogger.debug(curLmt,"[Released Amount > releasableAmount] ==13===> "+totalReleasedAmount +" > " +releasableAmount);
															//errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Releasable Amount"));
															failReason=failReason+"Releasable Amount can not be less than Released Amount.";
															obj.setStatus("FAIL");
														
														totalReleasedAmountFlag=true;
													}
												}
												 
												DefaultLogger.debug(curLmt,"Flag Checking END ==14===> ");
												if (!(errorCode =Validator.checkAmount(obj.getReleasableAmt(), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
														.equals(Validator.ERROR_NONE)){
													/*errors.add("releasableAmount", new ActionMessage(
															ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
													*/	
														
														releasableAmount = new BigDecimal(obj.getReleasableAmt());
														requiredSecCov = new BigDecimal(SancAmt);
														failReason=failReason+ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode)+".";
														obj.setStatus("FAIL");
													}else if( releasableAmount.compareTo(requiredSecCov) > 0){
														DefaultLogger.debug(curLmt,"[Released Amount > Sanctioned Amount ] ==15===> "+releasableAmount +" > " +requiredSecCov);
															//errors.add("releasableAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Sanctioned Amount"));
															obj.setFileId(fileId);
															failReason=failReason+"Releasable Amount can not be greater than Sanctioned Amount.";
															obj.setStatus("FAIL");
													}
												
														
										}
										else {
											if(relesableamount <= 0) {
												failReason=failReason+"Failed due to Releasable Amount is not valid.";
											}

											obj.setStatus("FAIL");
										}
									}
								}else {
									failReason=failReason+"Failed due to invalid facility ID.";
									obj.setStatus("FAIL");
								}
								
								}else {
									failReason=failReason+"facility ID can not be null.";
									obj.setStatus("FAIL");
								}
								if(obj.getStatus().equals("PASS")) {
									obj.setUploadStatus("Y");
									obj.setStatus("PASS");
									//obj.setReason(passReason);
									obj.setFileId(fileId);
									//obj.setSourceRefNo(sourceRefNo);
									totalUploadedList.add(obj);
									countPass++;
								}else if(obj.getStatus().equals("FAIL")) {
									obj.setFileId(fileId);
									obj.setReason(failReason);
									obj.setUploadStatus("N");
									errorList.add(obj);
								}
							}
							
							DefaultLogger.debug(this, "##################### totalUploadedList ############:: "
									+ totalUploadedList.size());
							int batchSize = 200;
							for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
								List<OBFacilitydetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
								jdbc.createEntireFacilitydetailsStageFile(batchList);
							}
							
							DefaultLogger.debug(this, "##################### errorList ############:: "
									+ errorList.size());
							
							for (int j = 0; j < errorList.size(); j += batchSize) {
								List<OBFacilitydetailsFile> batchList = errorList.subList(j,j + batchSize > errorList.size() ? errorList.size(): j + batchSize);
								jdbc.createEntireFacilitydetailsStageFile(batchList);
							}
							
							/*for (int j = 0; j < totalUploadedList.size(); j++) {
								OBFacilitydetailsFile obFacilitydetailsFile = (OBFacilitydetailsFile) totalUploadedList.get(j);
								if (obFacilitydetailsFile.getStatus().equalsIgnoreCase("PASS") && obFacilitydetailsFile.getUploadStatus().equalsIgnoreCase("Y")) {
									
									if(obFacilitydetailsFile.getSanctionAmt() != null && !"".equals(obFacilitydetailsFile.getSanctionAmt())) {
									jdbc.updateSanctionAmt(obFacilitydetailsFile);
									}
									if(obFacilitydetailsFile.getReleasableAmt() != null && !"".equals(obFacilitydetailsFile.getReleasableAmt())) {
									jdbc.updateReleasableAmt(obFacilitydetailsFile);
									}
								}
							}*/
							countFail = errorList.size();

						}
					}
				}

			}

			resultMap.put("trxValueOut", trxValueOut);
			resultMap.put("totalUploadedList", totalUploadedList);
			resultMap.put("errorList", errorList);
			resultMap.put("finalList", finalList);
			resultMap.put("total", String.valueOf(totalUploadedList.size() + errorList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));

			resultMap.put("fileType", fileType);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		} catch (FileUploadException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		} catch (TrxParameterException e) {
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		}
		return returnMap;

	}

}