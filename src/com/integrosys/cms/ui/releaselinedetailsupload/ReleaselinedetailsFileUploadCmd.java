/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.releaselinedetailsupload;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBReleaselinedetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileReleaselinedetails;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.releaselinedetailsupload.proxy.IReleaselinedetailsUploadProxyManager; 

/**
 * @author $Author: Abhijeet J$ Command for Upload UBS File
 */
public class ReleaselinedetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String RELEASELINEDETAILS_UPLOAD = "ReleaselinedetailsUpload";
	private IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy;

	public IReleaselinedetailsUploadProxyManager getReleaselinedetailsuploadProxy() {
		return releaselinedetailsuploadProxy;
	}

	public void setReleaselinedetailsuploadProxy(IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy) {
		this.releaselinedetailsuploadProxy = releaselinedetailsuploadProxy;
	}

	/**
	 * Default Constructor
	 */

	public ReleaselinedetailsFileUploadCmd() {
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
				{ "releaselinedetailsUploadObj",
						"com.integrosys.cms.ui.releaselinedetailsupload.OBReleaselinedetailsUpload", FORM_SCOPE },
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
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();

		long countPass = 0;
		long countFail = 0;

		int size = 0;
		List finalList = new ArrayList<ReleaselinedetailsError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("RLD_UPLOAD");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {

			OBReleaselinedetailsUpload releaselinedetailsUpload = (OBReleaselinedetailsUpload) map
					.get("releaselinedetailsUploadObj");
			if (releaselinedetailsUpload.getFileUpload().getFileName().isEmpty()) {
				exceptionMap.put("releaselinedetailsfileuploadError",
						new ActionMessage("label.releaselinedetails.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			} else if (!releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".XLS")
					&& !releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".XLSX")
					&& !releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".xlsx")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {

				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null);
				ctx.setLimitProfile(null);

				ProcessDataFileReleaselinedetails dataFile = new ProcessDataFileReleaselinedetails();
				DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd before Process File ############ ");
				resultList = dataFile.processFile(releaselinedetailsUpload.getFileUpload(), RELEASELINEDETAILS_UPLOAD);
				DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd After Process File ############ ");
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
					for (int p = 0; p < 9; p++) {
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
				}
				
				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				ILimitTrxValue lmtTrxObj = null;
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				ILimit curLmt = null;

				if (finalList.size() == 0) {
					if (resultList.size() > 0) {

						size = resultList.size();
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd ############:: " + size);
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd1 ############:: " + resultList);

						// create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("RLD_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(releaselinedetailsUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList.size()));
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd TotalRecords object ############:: " + String.valueOf(resultList.size()-1));
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd ctx object ############:: " + ctx);
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd fileOBj object ############:: " + fileObj);
						trxValueOut = getReleaselinedetailsuploadProxy().makerCreateFile(ctx, fileObj);// ENTRY IN
																										// CMS_FILE_UPLOAD
						long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
						DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd fileId ############:: " + fileId);
						if (trxValueOut != null) {
							for (int i = 1; i < resultList.size(); i++) {
								rowlist = (ArrayList) resultList.get(i);
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd2 rowlist############:: " + rowlist);
								exceldatalist.clear();
								for (int j = 0; j < rowlist.size(); j++) {
									if (releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".XLS")
											|| releaselinedetailsUpload.getFileUpload().getFileName()
													.endsWith(".xls")) {
										DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd2 ############:: " + rowlist.get(j));
										exceldatalist.add(rowlist.get(j));
									DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
							} else if (releaselinedetailsUpload.getFileUpload().getFileName().endsWith(".XLSX")
											|| releaselinedetailsUpload.getFileUpload().getFileName()
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
										else if (cell.getCellType() == cell.CELL_TYPE_BLANK)
											exceldatalist.add(null);
									}

								}

								OBReleaselinedetailsFile obj = new OBReleaselinedetailsFile();
								
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd getting values from upload file and setting in obj############:: " );
								
							if (null != exceldatalist.get(0) && !exceldatalist.get(0).toString().trim().isEmpty()) {
									obj.setSystemID((String.valueOf(exceldatalist.get(0)).trim()));
								} else {
									obj.setSystemID(""); 
								}

								if (null != exceldatalist.get(1) && !exceldatalist.get(1).toString().trim().isEmpty()) {
									obj.setLineNo((String.valueOf(exceldatalist.get(1)).trim()));
								} else {
									obj.setLineNo("");
								}

								if (null != exceldatalist.get(2) && !exceldatalist.get(2).toString().trim().isEmpty()) {
									obj.setSerialNo((String.valueOf(exceldatalist.get(2)).trim()));
								} else {
									obj.setSerialNo("");
								}

								if (null != exceldatalist.get(3) && !exceldatalist.get(3).toString().trim().isEmpty()) {
									obj.setLiabBranch((String.valueOf(exceldatalist.get(3)).trim()));
								} else {
									obj.setLiabBranch("");
								}

								if (null != exceldatalist.get(4) && !exceldatalist.get(4).toString().trim().isEmpty()) {
									obj.setReleaseAmount((String.valueOf(exceldatalist.get(4)).trim()));
								} else {
									obj.setReleaseAmount("");
								}
								
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-1 ############:: "+obj.getReleaseAmount()+"test");
								if (null != exceldatalist.get(5) && !exceldatalist.get(5).toString().trim().isEmpty()) {
									obj.setExpDate((String.valueOf(exceldatalist.get(5)).trim()));
									DefaultLogger.debug(this,"releaselinedetailsfileuploadcmd.java inside if obj.setExpDate((String.valueOf(exceldatalist.get(5)).trim()=>"+obj.getExpDate());
								} else {
									obj.setExpDate("");
									DefaultLogger.debug(this,"releaselinedetailsfileuploadcmd.java inside else obj.setExpDate((String.valueOf(exceldatalist.get(5)).trim()=>"+obj.getExpDate());
								}
								
								DefaultLogger.debug(this,"releaselinedetailsfileuploadcmd.java for releaseline upload=>l1.getExpDate()=>"+obj.getExpDate());
								
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-1 ############:: "+obj.getExpDate());
								
								
								//PSL Flag, PSL Value and Rule ID
								if(null != exceldatalist.get(6) && !exceldatalist.get(6).toString().trim().isEmpty())
								{
									obj.setPslFlag((String.valueOf(exceldatalist.get(6)).trim()));
									if(obj.getPslFlag().equalsIgnoreCase("Y")) {
					    				 obj.setPslFlag("Yes");
					    			 }else if(obj.getPslFlag().equalsIgnoreCase("N"))
					    			 {
					    				 obj.setPslFlag("No");
					    			 }
								} else {
									obj.setPslFlag("");
								}
								
								if(null != exceldatalist.get(7) && !exceldatalist.get(7).toString().trim().isEmpty())
								{
									obj.setPslValue((String.valueOf(exceldatalist.get(7)).trim())); 	
								} else {
									obj.setPslValue("");
								}
								
								if(null != exceldatalist.get(8) && !exceldatalist.get(8).toString().trim().isEmpty())
								{
									obj.setRuleID((String.valueOf(exceldatalist.get(8)).trim())); 
									DefaultLogger.debug(this,"#####################In RULEID while set -> "+obj.getRuleID());
								} else {
									obj.setRuleID("");
								}

								String failReason="";
								String passReason="";
								
								
								FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
								
								String liabBranchCode=fileUpload.getReleaselinedetailsLiabBranchID(obj.getLiabBranch());
								obj.setLiabBranch(liabBranchCode);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd liabBranchCode>>>>" + liabBranchCode);
								
								int	facilityIdCount=0;
								facilityIdCount=fileUpload.getReleaselinedetailsFacilityIdCount(obj.getSystemID());
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd facilityIdCount>>>>" + facilityIdCount);
								
								int serialIdCount=0;
								serialIdCount=fileUpload.getReleaselinedetailsSerialNoCount(obj.getSerialNo());
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd serialIdCount>>>>" + serialIdCount);
								
								int lineNoCount=0;
								lineNoCount=fileUpload.getReleaselinedetailsLineNoCount(obj.getLineNo());
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd lineNoCount>>>>" + lineNoCount);
								
								int liabBranchCount=0;
								liabBranchCount=fileUpload.getReleaselinedetailsLiabBranchCount(obj.getLiabBranch());
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd liabBranchCount>>>>" + liabBranchCount);
								
								String rldStatus="";
								rldStatus=fileUpload.getReleaselinedetailsStatus(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd rldStatus>>>>" + rldStatus);
								
								String limitDetailsStatus="";
								limitDetailsStatus=fileUpload.getReleaselinedetailsLimitDetailsStatus(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd limitDetailsStatus>>>>" + limitDetailsStatus);
								
								String limitTrxStatus="";
								limitTrxStatus=fileUpload.getFacilityTransactionStatus(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd limitTrxStatus>>>>" + limitTrxStatus);
								
								String partyStatus="";
								partyStatus=fileUpload.getReleaselinedetailsPartyStatus(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd partyStatus>>>>" + partyStatus);
								
								String partyName="";
								partyName=fileUpload.getReleaselinedetailsPartyName(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd partyName>>>>" + partyName);
								
								BigDecimal releasable=new BigDecimal("0");
								releasable=fileUpload.getReleaselinedetailsSanctionAmount(obj);
								DefaultLogger.debug(this,">>>>In ReleaselinedetailsFileUploadCmd releasable>>>>" + releasable);
								
								Set<String>	releasedAmount=null;
								releasedAmount=fileUpload.getReleaselinedetailsReleasedAmount(obj);
								BigDecimal releaseAmount=new BigDecimal(0);
								DefaultLogger.debug(this,"#####################Value of releaseAmount before entering the If condition"+releaseAmount);
								DefaultLogger.debug(this,"#####################Value of releaseAmount size before entering the If condition"+releasedAmount.size());
								if(releasedAmount.size()!=0) {
								  if(releasedAmount.size()==1 && releasedAmount.contains(null)) {
									  DefaultLogger.debug(this,"#####################Size is 1 and contains null");
								}else {
									releaseAmount=new BigDecimal(releasedAmount.iterator().next());
									DefaultLogger.debug(this,"#####################I am inside as this releasedAmount value is ############::"+releaseAmount);
								}
								}
								BigDecimal amount= new BigDecimal("0");
								String amt="";
								String amtContainsChar = null;
								if(!"".equals(obj.getReleaseAmount()) && obj.getReleaseAmount()!=null && !obj.getReleaseAmount().isEmpty()) {
									DefaultLogger.debug(this,"#####################I am inside as this val value is ############::"+obj.getReleaseAmount());
									try {
										amount=new BigDecimal(obj.getReleaseAmount());
									}catch(Exception e) {
										DefaultLogger.debug(this,"#####################I am not numeric and inside catch ############::"+obj.getReleaseAmount());
										amtContainsChar = obj.getReleaseAmount();
									}
								}else {
									amt=obj.getReleaseAmount();
								}
								
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-1 going for validation ############:: "+amount);
								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-1 going for validation ############:: "+amt);

								 
								String expiryDate ="" ;
								expiryDate = fileUpload.getReleaselinedetailsexpDate(obj);
								
								//For PSL Value Count in Common Code Category
								int pslCount=0;
								// PSL Value check with UI
								int pslValueCheck=0;
								if(null != obj.getPslFlag() && null != obj.getPslValue())
								{
								pslCount = fileUpload.getPslValueCount(obj);
								if( !"".equals(obj.getPslValue()) && !"".equals(obj.getPslFlag())) {
								pslValueCheck = fileUpload.getPslValueCheckWithLine(obj);
								}
								}
								
								
								
								//For Rule ID count
								int ruleCount=0;
								// Rule ID check with UI
								String ruleIdValueCheck= null;
								if(null != obj.getRuleID() && !"".equals(obj.getRuleID()))
								{
								ruleCount = fileUpload.getRuleCount(obj);
								
								ruleIdValueCheck = fileUpload.getRuleValueCheckWithLine(obj);
								DefaultLogger.debug(this,"#####################In Rule Count for ReleaseLineDetail"+ruleCount);

								DefaultLogger.debug(this,"#####################In RuleID value check for ReleaseLineDetail"+ruleIdValueCheck);
								}

								DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 going for validation ############:: ");
									if(obj.getSystemID()!=null && obj.getLineNo()!=null && obj.getSerialNo()!=null && 
											obj.getLiabBranch()!=null) {	
										DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-1 ############:: ");

									if(!obj.getSystemID().toString().trim().isEmpty() && !obj.getLineNo().toString().trim().isEmpty()
												&& !obj.getSerialNo().toString().trim().isEmpty() && !obj.getLiabBranch().toString().trim().isEmpty()) {
										DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-2 ############:: ");

										if(facilityIdCount > 0 && serialIdCount > 0 && lineNoCount > 0 && liabBranchCount > 0) {
											DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-3 ############:: ");

											if(!("PENDING".equalsIgnoreCase(rldStatus)) && !("".equalsIgnoreCase(rldStatus))) {
												DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-4 ############:: ");

												if(!("PENDING".equalsIgnoreCase(limitDetailsStatus)) && !("".equalsIgnoreCase(limitDetailsStatus))) {
													DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-5 ############:: ");

													if(!("INACTIVE".equalsIgnoreCase(partyStatus)) && !("".equalsIgnoreCase(partyStatus))) {

														
														if(!("PENDING_UPDATE".equalsIgnoreCase(limitTrxStatus))) {
														
															

														DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-6 ############:: ");

														if((!"".equals(obj.getReleaseAmount()) && obj.getReleaseAmount()!=null) || ( !"".equals(obj.getExpDate()) && obj.getExpDate()!=null)
																||(!obj.getRuleID().isEmpty() && null != obj.getRuleID()) 
																|| ((!obj.getPslFlag().isEmpty() && null != obj.getPslFlag()) 
																		&& (!obj.getPslValue().isEmpty() && null != obj.getPslValue()))
																){
															if(!"".equals(obj.getReleaseAmount()) && obj.getReleaseAmount()!=null) {
																DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-7 ############:: ");
																// This if condition will be helpful if there is any requirement to validate amount for spl char
															if(validateAmount(obj.getReleaseAmount())) {
																DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2.1 inside validation-7 ############:: ");
															if(amount.compareTo(releaseAmount)!=0) {
																DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-8 ############:: ");


																if(!"".equals(obj.getReleaseAmount()) && obj.getReleaseAmount()!=null && !obj.getReleaseAmount().isEmpty()) {
																	DefaultLogger.debug(this,"#####################I am going inside as I am not empty ############:: "+obj.getReleaseAmount());

																if (amount.signum()!=-1) {
																	DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-9 ############:: ");

																if(amount.compareTo(releasable) < 1){
																	DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-10 ############:: ");

																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==1==>> obj.getLiabBranch()"+obj.getLiabBranch());
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==2==>> obj.getFacilityID()"+obj.getFacilityID());
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==3==>> obj.getReleaseAmount()"+obj.getReleaseAmount());
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==4==>> obj.getLineNo()"+obj.getLineNo());
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==4==>> amount"+amount);
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==4==>> releasable"+releasable);
																DefaultLogger.debug(this, "in Releaselinedetailsfileuploadcmd.java ==4==>> releaseAmount"+releaseAmount);
																
																obj.setUploadStatus("Y");
																obj.setStatus("PASS");
																passReason="Must require Authorization to reflect the correct status.";
																obj.setReason(passReason);
																obj.setFileId(fileId);
																String sourceRefNo=fileUpload.generateSourceRefNo();
																obj.setSourceRefNo(sourceRefNo);
																
															}else {
																
																obj.setFileId(fileId);
																failReason="Release Amount is more than Releasable Amount of Party '"+partyName+"'.";
																obj.setReason(failReason);
																obj.setUploadStatus("N");
																obj.setStatus("FAIL");
																errorList.add(obj);
																DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 496<<<<"+failReason);
															}
																}	else {
																	obj.setFileId(fileId);
																	failReason="Release Amount cannot be negative.";
																	obj.setReason(failReason);
																	obj.setUploadStatus("N");
																	obj.setStatus("FAIL");
																	errorList.add(obj);
																	DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 505<<<<"+failReason);
																}
															}else {
																obj.setStatus("PASS");
															}
															}else {
																if(obj.getExpDate().equals(expiryDate) && amount.compareTo(releaseAmount)==0) {
																	obj.setFileId(fileId);
																	failReason="Rejected as Release Amount and Limit Expiry date is having same value.";
																	obj.setReason(failReason);
																	obj.setUploadStatus("N");
																	obj.setStatus("FAIL");
																	errorList.add(obj);
																	DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 518<<<<"+failReason);
																}else {
																obj.setFileId(fileId);
																failReason="Release Amount is same of Party '"+partyName+"'.";
																obj.setReason(failReason);
																obj.setUploadStatus("N");
																obj.setStatus("FAIL");
																errorList.add(obj);
																DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 526<<<<"+failReason);
																}
															}
                                                           }else {
                                                            	obj.setFileId(fileId);
																failReason="Release Amount contains non numeric values "+obj.getReleaseAmount();
																obj.setReason(failReason);
																obj.setUploadStatus("N");
																obj.setStatus("FAIL");
																errorList.add(obj);
																DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 536<<<<"+failReason);
                                                            }
															}else {
																obj.setStatus("PASS");
															}
															
															DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 554 get STATUS before PSL condition>>>"+obj.getStatus());
															
															//Checking PSL Flag Value and Rule ID
															if((!"".equals(obj.getPslFlag()) || !"".equals(obj.getPslValue()) || !"".equals(obj.getRuleID())) && ("PASS".equals(obj.getStatus() ))) {
														     if(!"".equals(obj.getPslFlag())  && !"".equals(obj.getPslValue()) && "PASS".equals(obj.getStatus())) {
														    	 if((obj.getPslFlag().equalsIgnoreCase("Yes") || obj.getPslFlag().equalsIgnoreCase("No")) ) {
														    		 
														    		 //Check PSL Value
														    		 if(  null != obj.getPslValue() && pslValueCheck==0 && pslCount==1 ) {															    			 
														    			    obj.setUploadStatus("Y");
																			obj.setStatus("PASS");
																			passReason="Must require Authorization to reflect the correct status.";
																			obj.setReason(passReason);
																			obj.setFileId(fileId);
																			String sourceRefNo=fileUpload.generateSourceRefNo();
																			obj.setSourceRefNo(sourceRefNo); 
																			DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 570<<<<"+ obj.getPslValue());
														    		 }
														    		 else if(obj.getPslValue()==null)
														    		 {
														    			 obj.setFileId(fileId);
																			failReason=failReason+"If PSL Flag is selected then PSL value is mandatory.";
																			obj.setReason(failReason);
																			obj.setUploadStatus("N");
																			obj.setStatus("FAIL");
																			errorList.add(obj);
																			DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 597<<<<"+failReason);
														    		 }
														    		 else if(pslValueCheck==1)
														    		 {
														    			 obj.setFileId(fileId);
																			failReason=failReason+"PSL Value are same as existing.";
																			obj.setReason(failReason);
																			obj.setUploadStatus("N");
																			obj.setStatus("FAIL");
																			errorList.add(obj);
																			DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 607<<<<"+failReason);
														    		 }
														    		 else if(pslCount==0) {
															    		 obj.setFileId(fileId);
																		failReason=failReason+"PSL Value doesn't exist.";
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj);
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 616<<<<"+failReason);
															    	 }
														    		 
														    	 }
														    	 else if(pslCount==-1)
														    	 {
														    		 //PSL Flag will be Y or N
														    		    obj.setFileId(fileId);
																		failReason=failReason+"PSL Flag will be Y or N.";
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj);
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 629<<<<"+failReason);
														    	 }
														    	 
														        }
														     else if((!"".equals(obj.getPslFlag())  && "".equals(obj.getPslValue()))
														    		|| ("".equals(obj.getPslFlag())  && !"".equals(obj.getPslValue())) ){
														    	 obj.setFileId(fileId);
																	failReason=failReason+"PSL Flag and PSL value are conditional Mandatory.";
																	obj.setReason(failReason);
																	obj.setUploadStatus("N");
																	obj.setStatus("FAIL");
																	errorList.add(obj);
																	DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 641<<<<"+failReason);
														     }
														     //Rule ID
														     
														     DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 628 get STATUS before RuleID condition>>>>"+obj.getStatus());
														     if( !"".equals(obj.getRuleID()) && ("PASS".equals(obj.getStatus()) || "".equals(obj.getStatus()) || null==obj.getStatus())) {
														    	 if(ruleCount>0 && (ruleIdValueCheck==null || !ruleIdValueCheck.equals(obj.getRuleID()) )) {
														    			DefaultLogger.debug(this,"#####################Inside Rule auth RuleId value"+obj.getRuleID());
														    		 obj.setUploadStatus("Y");
																		obj.setStatus("PASS");
																		passReason="Must require Authorization to reflect the correct status.";
																		obj.setReason(passReason);
																		obj.setFileId(fileId);
																		String sourceRefNo=fileUpload.generateSourceRefNo();
																		obj.setSourceRefNo(sourceRefNo);									    		 
														    	 }else if((ruleIdValueCheck.equalsIgnoreCase(obj.getRuleID())))
													    		 {
													    			 obj.setFileId(fileId);
																		failReason=failReason+"Rule ID is same as existing.";
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj);
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 662<<<<"+failReason);
													    		 }
													    		 else if(ruleCount==0){
													    			    obj.setFileId(fileId);
																		failReason=failReason+"Rule ID doesn't exist.";
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj);
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 671<<<<"+failReason);
													    		 }	
													    		 else if(ruleCount==-1)
													    		 {
													    			 obj.setFileId(fileId);
																		failReason=failReason+"Rejected as Rule ID is blank.";
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj); 
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 681<<<<"+failReason);
													    		 }
														    	 
														     }
															}
															
															DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 670 get STATUS before ExpDate condition>>>"+obj.getStatus());
															
															
															if(obj.getExpDate()!= null && !"".equals(obj.getExpDate()) && "PASS".equals(obj.getStatus())) {
																if(validateJavaDate(obj.getExpDate())) {
																	if (!obj.getExpDate().equals(expiryDate)){
																	DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-11 ############:: ");	
																	obj.setDateOfReset(DateUtil.convertDate(locale, obj.getExpDate()));
																	obj.setUploadStatus("Y");
																	obj.setStatus("PASS");
																	passReason="Must require Authorization to reflect the correct status.";
																	obj.setReason(passReason);
																	obj.setFileId(fileId);
																	obj.setSourceRefNo(fileUpload.generateSourceRefNo());
																	DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 552<<<<"+obj.getExpDate());
																	}else {
																		failReason=failReason+"Failed as Limit expiry date is same";
																		obj.setFileId(fileId);
																		obj.setReason(failReason);
																		obj.setUploadStatus("N");
																		obj.setStatus("FAIL");
																		errorList.add(obj);
																		DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 560<<<<"+failReason);
																	}
																}else {
																	failReason=failReason+"Failed due to invalid date format";
																	obj.setFileId(fileId);
																	obj.setReason(failReason);
																	obj.setUploadStatus("N");
																	obj.setStatus("FAIL");
																	errorList.add(obj);
																	DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 569<<<<"+failReason);
																}
															}
														}else{
																DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd-2 inside validation-12 ############:: ");	
																obj.setFileId(fileId);
																failReason="Failed as fields Limit Expiry date, Released amount, PSL Flag/PSL Value, Rule ID are blank/Null.";
																obj.setReason(failReason);
																obj.setUploadStatus("N");
																obj.setStatus("FAIL");
																errorList.add(obj);
																DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 704<<<<"+failReason);
															}
															
															if("PASS".equals(obj.getStatus())){
																totalUploadedList.add(obj);
																countPass++;
															}
													}else {
															obj.setFileId(fileId);
															failReason=" Transaction is in pending for authorisation of respective Facility for Party '"+partyName+"'.";
															obj.setReason(failReason);
															obj.setUploadStatus("N");
															obj.setStatus("FAIL");
															errorList.add(obj);
															DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 718<<<<"+failReason);
													}
													}else {
														obj.setFileId(fileId);
														if(partyStatus=="") {
															failReason="Party not found.";
														}else {
															failReason="'"+partyName+"' Party is not ACTIVE.";
														}
														obj.setReason(failReason);
														obj.setUploadStatus("N");
														obj.setStatus("FAIL");
														errorList.add(obj);
														DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 731<<<<"+failReason);
													}
													
												}else {
													obj.setFileId(fileId);
													if(limitDetailsStatus=="") {
														failReason="Party not found in Limit Detalis.";
													}else {
														failReason="Limit Detalis is PENDING of Party '"+partyName+"'.";
													}
													obj.setReason(failReason);
													obj.setUploadStatus("N");
													obj.setStatus("FAIL");
													errorList.add(obj);
													DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 745<<<<"+failReason);
												}
												
											}
											else {
												obj.setFileId(fileId);
												if(rldStatus=="") {
													failReason="Record Not Found Against(System ID,Serial Number,Line Number,Liab Branch) this combination.";
												}
												else {
													failReason="Release Line Detalis is PENDING of Party '"+partyName+"'.";
												}
												obj.setReason(failReason);
												obj.setUploadStatus("N");
												obj.setStatus("FAIL");
												errorList.add(obj);
												DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 761<<<<"+failReason);
											}
											
										}
										else {
											if(facilityIdCount <= 0) {
												failReason="Failed due to System Id is not exist.";
											}
											if(serialIdCount <= 0) {
												failReason=failReason+"Failed due to Serial Number is not exist.";
											}
											if(lineNoCount <= 0) {
												failReason=failReason+"Failed due to Line Number is not exist.";
											}
											if(liabBranchCount <= 0) {
												failReason=failReason+"Failed due to Liab Branch is not exist.";
											}
											obj.setFileId(fileId);
											obj.setReason(failReason);
											obj.setUploadStatus("N");
											obj.setStatus("FAIL");
											errorList.add(obj);
											DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 783<<<<"+failReason);
										}
										
									}else {
										if(obj.getSystemID().toString().trim().isEmpty()) {
											failReason="Failed due to System Id is empty.";
										}
										if(obj.getLineNo().toString().trim().isEmpty()) {
											failReason=failReason+"Failed due to Line Number is empty.";
										}
										if(obj.getSerialNo().toString().trim().isEmpty()) {
											failReason=failReason+"Failed due to Serial Number is empty.";
										}
										if(obj.getLiabBranch().toString().trim().isEmpty()) {
											failReason=failReason+"Failed due to Liab Branch is empty.";
										}
										obj.setFileId(fileId);
										obj.setReason(failReason);
										obj.setUploadStatus("N");
										obj.setStatus("FAIL");
										errorList.add(obj);
										DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 804<<<<"+failReason);
									}
								
								}else {
									if(obj.getSystemID()==null) {
										failReason="Failed due to System Id is null.";
									}
									if(obj.getLineNo()==null) {
										failReason=failReason+"Failed due to Line Number is null.";
									}
									if(obj.getSerialNo()==null) {
										failReason=failReason+"Failed due to Serial Number is null.";
									}
									if(obj.getLiabBranch()==null) {
										failReason=failReason+"Failed due to Liab Branch is null.";
									}
									obj.setFileId(fileId);
									obj.setReason(failReason);
									obj.setUploadStatus("N");
									obj.setStatus("FAIL");
									errorList.add(obj);
									DefaultLogger.debug(this, ">>>>In Releaselinedetailsfileuploadcmd line 825<<<<"+failReason);
								}
								
								
								
								
							}

							DefaultLogger.debug(this, "##################### totalUploadedList ############:: "
									+ totalUploadedList.size());
							int batchSize = 200;
							for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
								List<OBReleaselinedetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
								DefaultLogger.debug(this, "##################### totalUploadedList batchList ############:: "
										+ batchList);
								jdbc.createEntireReleaselinedetailsStageFile(batchList);
							}
							
							DefaultLogger.debug(this, "##################### errorList ############:: "
									+ errorList.size());
							
							for (int j = 0; j < errorList.size(); j += batchSize) {
								List<OBReleaselinedetailsFile> batchList = errorList.subList(j,j + batchSize > errorList.size() ? errorList.size(): j + batchSize);
								jdbc.createEntireReleaselinedetailsStageFile(batchList);
							}
							
							for (int j = 0; j < totalUploadedList.size(); j++) {
								OBReleaselinedetailsFile obReleaselinedetailsFile = (OBReleaselinedetailsFile) totalUploadedList.get(j);
								if (obReleaselinedetailsFile.getStatus().equalsIgnoreCase("PASS") && obReleaselinedetailsFile.getUploadStatus().equalsIgnoreCase("Y")) {
									
									/*jdbc.updateReleaseAmountStage(obReleaselinedetailsFile);
									jdbc.updateTotalReleasedAmtStage(obReleaselinedetailsFile);
									if(obReleaselinedetailsFile.getExpDate()!= null && !"".equals(obReleaselinedetailsFile.getExpDate())) {
										jdbc.updateLimitExpDate(obReleaselinedetailsFile);
									}*/
								}
							}
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

	public static boolean validateJavaDate(String strDate)
	   {
		/* Check if date is 'null' */
		if (strDate.trim().equals(""))
		{
		    return true;
		}
		/* Date is not 'null' */
		else
		{
		    /*
		     * Set preferred date format,
		     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
		    
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MMM/yyyy");
		    sdfrmt.setLenient(false);
		    /* Create Date object
		     * parse the string into date 
	             */
		    try
		    {
		        Date javaDate = sdfrmt.parse(strDate); 
		        System.out.println(strDate+" is valid date format");
		        
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        System.out.println(strDate+" is Invalid Date format");
		        return false;
		    }
		    
		    String dateParts[] = strDate.split("/"); 
		    String day = dateParts[0]; 
	        String month = dateParts[1]; 
	        String year = dateParts[2]; 
	        System.out.println("Day: " + day); 
	        System.out.println("Month: " + month); 
	        System.out.println("Year: " + year); 
		    /* Return true if date format is valid */
		    if(!year.matches("[0-9]+")||!day.matches("[0-9]+")) {
		    	 return false;
		      }else if(year.length()!=4) {
		    	  return false;
		      }else 
		    	  return true;
		}
}
	
	
	public static boolean validateAmount(String str) 
    { 
		
		String regex = "[0-9]+[\\.]?[0-9]*";
        Pattern p = Pattern.compile(regex); 
        if (str == null) { 
            return false; 
        }else if(str.contains("E")) {
        	return true;
        }else{
        	Matcher m = p.matcher(str); 
        	return m.matches(); 
        }
        
    } 
}