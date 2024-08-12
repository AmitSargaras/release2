package com.integrosys.cms.ui.bulkudfupdateupload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBulkUDFFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;

import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl.FdFileRowMapper;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileBulkUDF;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.integrosys.cms.ui.bulkudfupdateupload.proxy.IBulkUDFUploadProxyManager;


public class BulkUDFFileUploadCmd  extends AbstractCommand implements ICommonEventConstant{
	
	public static final String BULKUDF_UPLOAD = "BulkUDFUpload";
	
	private IBulkUDFUploadProxyManager bulkudfuploadProxy;
	

	
	
	/*IBulkUDFUploadProxyManager bulkudfuploadProxy = (IBulkUDFUploadProxyManager) BeanHouse.get("bulkudfuploadProxy");*/

	public BulkUDFFileUploadCmd(){
		
	}
	
	public IBulkUDFUploadProxyManager getBulkudfuploadProxy() {
		return bulkudfuploadProxy;
	}

	public void setBulkudfuploadProxy(IBulkUDFUploadProxyManager bulkudfuploadProxy) {
		this.bulkudfuploadProxy = bulkudfuploadProxy;
	}

	//input
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "bulkudfUploadObj",
						"com.integrosys.cms.ui.bulkudfupdateupload.OBBulkUDFUpload", FORM_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE }, });
	}
	//output
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
		List finalList = new ArrayList<BulkUDFError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("BULK_UDF_UPLOAD");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			OBBulkUDFUpload bulkudfupload = (OBBulkUDFUpload) map.get("bulkudfUploadObj");
			
			if(bulkudfupload.getFileUpload().getFileName().isEmpty()) {
				exceptionMap.put("bulkudfuploadError", new ActionMessage("label.bulkudf.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else if(!bulkudfupload.getFileUpload().getFileName().endsWith(".csv")
					&& !bulkudfupload.getFileUpload().getFileName().endsWith(".CSV")) {
			fileType = "NOT_CSV";
			strError = "errorEveList";
		} else {
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setCustomer(null); //Setting Cust object to null.
			ctx.setLimitProfile(null);
			
			ProcessDataFileBulkUDF dataFile = new ProcessDataFileBulkUDF();
		    resultList = dataFile.processFile(bulkudfupload.getFileUpload(), BULKUDF_UPLOAD);
		    
		    IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
			HashMap eachDataMap = (HashMap) dataFile.getErrorList();

			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup = generalParamDao
					.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
			
			
			//Emptying the temporary table
			jdbc.rollback();
			Date applicationDate = new Date();
			
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
					applicationDate = new Date(generalParamEntries[i].getParamValue());
				}
			}

			List list = new ArrayList(eachDataMap.values());
			for (int i = 0; i < list.size(); i++) {
				String[][] errList = (String[][]) list.get(i);
				for (int p = 0; p < errList.length; p++) {
					BulkUDFError bulkudfError = new BulkUDFError();
					if (null != errList[p][0]) {
						bulkudfError.setColumnName(errList[p][0]);
						bulkudfError.setErrorMessage(errList[p][1]);
						bulkudfError.setFileRowNo(errList[p][3]);
						if (100 == finalList.size()) {
							break;
						}
						finalList.add(bulkudfError);
					}
				}
				if (100 == finalList.size()) {
					break;
				}
			}
			
			if(finalList.size()==0){
				if (resultList.size() > 0) {
					
					 size = resultList.size();
						DefaultLogger.debug(this,"In Bulk UDF File Upload :: "+ size);
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("BULK_UDF_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setFileName(bulkudfupload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
					

						
						trxValueOut = bulkudfuploadProxy.makerCreateFile(ctx,
								fileObj);
						if (trxValueOut != null) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
							long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
							for (int index = 0; index < size; index++) {
								HashMap eachDataMap1 = (HashMap) resultList.get(index);
								OBTempBulkUDFFileUpload obj = new OBTempBulkUDFFileUpload();
								if(null!=eachDataMap1.get("TYPE_OF_UDF")&& !"".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString())
										&& ("party".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString()) || "cam".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString())
												|| "limit".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString()) || "limit_2".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString())))
								{
								//PARTY DETAILS	
								if(null!=eachDataMap1.get("PARTY_ID")&& !"".equalsIgnoreCase
										(eachDataMap1.get("PARTY_ID").toString()) && "party".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString())){
									
									int count = jdbc.getPartyIDCount(eachDataMap1.get("PARTY_ID").toString());
									if(count>0)
									{
										//auth check
										if(jdbc.checkauth(eachDataMap1.get("TYPE_OF_UDF").toString(),eachDataMap1.get("PARTY_ID").toString())) 
									   {
									obj.setTypeOfUDF("PARTY");
									obj.setPartyID(eachDataMap1.get("PARTY_ID").toString());
									String partyId= jdbc.getPartyIdFromMain(eachDataMap1.get("PARTY_ID").toString());
									
									obj.setCMS_LE_MAIN_PROFILE_ID(partyId);						
									
									if(null!=eachDataMap1.get("UDF_FIELD_SEQUENCE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString())
											&& null!=eachDataMap1.get("UDF_FIELD_NAME") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_NAME").toString()) &&
											null!=eachDataMap1.get("UDF_FIELD_VALUE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_VALUE").toString())) {
										
										int count2=jdbc.isValidUDFSequence("PARTY", eachDataMap1.get("UDF_FIELD_SEQUENCE").toString(), eachDataMap1.get("UDF_FIELD_NAME").toString());
										if(count2==1)
										{
											obj.setUdfFieldSequence(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString());
											obj.setUdfFieldName(eachDataMap1.get("UDF_FIELD_NAME").toString());
											obj.setUdfFieldValue(eachDataMap1.get("UDF_FIELD_VALUE").toString());
											obj.setValid("true");
											obj.setUploadStatus("Y");
											obj.setStatus("PASS");
											
										}
										else if(count2==3)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Sequence is Incorrect or not mapped with Party");
											obj.setReason("UDF Field Sequence is Incorrect or not mapped with Party");
											obj.setStatus("FAIL");
											
										}
										else if(count2==2)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setReason("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setStatus("FAIL");
											
											
										}

									}
									else
									{
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks("UDF Field, Sequence and Value Cannot be Null.");
										obj.setReason("UDF Field, Sequence and Value Cannot be Null.");
										obj.setStatus("FAIL");
										
									}
									}
										//auth else
										else
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("Party is in Unauthorized State.");
											obj.setReason("Party is in Unauthorized State.");
											obj.setStatus("FAIL");
											
										}
									}
									else
									{
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks("Invalid Party Details");
										obj.setReason("Invalid Party Details.");
										obj.setStatus("FAIL");
										
									}
								}
								
								//CAM DETAILS
								else if(null!=eachDataMap1.get("CAM_NO") && !"".equalsIgnoreCase(eachDataMap1.get("CAM_NO").toString()) 
										&& "cam".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString()))
								{
									int count = jdbc.getCamNoCount(eachDataMap1.get("CAM_NO").toString());
									if(count>0)
									{  
										if(jdbc.checkauth(eachDataMap1.get("TYPE_OF_UDF").toString(),eachDataMap1.get("CAM_NO").toString())) 
										   {
										obj.setTypeOfUDF("CAM");
										obj.setCamNo(eachDataMap1.get("CAM_NO").toString());
										String cam= jdbc.getCamIdFromMain(eachDataMap1.get("CAM_NO").toString());
										
										obj.setCMS_LSP_LMT_PROFILE_ID(cam);						
										
										
										
									if(null!=eachDataMap1.get("UDF_FIELD_SEQUENCE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString())
											&& null!=eachDataMap1.get("UDF_FIELD_NAME") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_NAME").toString()) &&
											null!=eachDataMap1.get("UDF_FIELD_VALUE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_VALUE").toString())) {
										
										int count2=jdbc.isValidUDFSequence("CAM", eachDataMap1.get("UDF_FIELD_SEQUENCE").toString(), eachDataMap1.get("UDF_FIELD_NAME").toString());
										if(count2==1)
										{
											obj.setUdfFieldSequence(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString());
											obj.setUdfFieldName(eachDataMap1.get("UDF_FIELD_NAME").toString());
											obj.setUdfFieldValue(eachDataMap1.get("UDF_FIELD_VALUE").toString());
											obj.setValid("true");
											obj.setUploadStatus("Y");
											obj.setStatus("PASS");
											
										}
										else if(count2==3)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Sequence is Incorrect or not mapped with CAM");
											obj.setReason("UDF Field Sequence is Incorrect or not mapped with CAM");
											obj.setStatus("FAIL");
											
										}
										else if(count2==2)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setReason("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setStatus("FAIL");
											
											
										}
									}
									else
									{
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks("UDF Sequence, Field and Value cannot be null");
										obj.setReason(
												"UDF Sequence, Field and Value cannot be null");
										obj.setStatus("FAIL");
										
									}
									}
										else {
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("CAM is pending for authorization.");
											obj.setReason("CAM is pending for authorization.");
											obj.setStatus("FAIL");
										
										}
								}
									else
									{
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks("Invalid CAM No.");
										obj.setReason("Invalid CAM No.");
										obj.setStatus("FAIL");
										
										
									}																	
								}
								
								//LIMIT DETAILS
								
								else if(null!=eachDataMap1.get("SYSTEM_ID") && !"".equalsIgnoreCase(eachDataMap1.get("SYSTEM_ID").toString())
										&& null!=eachDataMap1.get("LINE_NUMBER") && !"".equalsIgnoreCase(eachDataMap1.get("LINE_NUMBER").toString())
										&& null!=eachDataMap1.get("SERIAL_NUMBER") && !"".equalsIgnoreCase(eachDataMap1.get("SERIAL_NUMBER").toString())
										&& null!=eachDataMap1.get("LIAB_BRANCH") && !"".equalsIgnoreCase(eachDataMap1.get("LIAB_BRANCH").toString())
										&& "limit".equalsIgnoreCase(eachDataMap1.get("TYPE_OF_UDF").toString()))
								{    
									int count = 0;
									String liabBranchCode=jdbc.getReleaselinedetailsLiabBranchID(eachDataMap1.get("LIAB_BRANCH").toString());
									System.out.println("SYSTEM_ID :"+eachDataMap1.get("SYSTEM_ID").toString());
									String systemId= eachDataMap1.get("SYSTEM_ID").toString();
									System.out.println("LINE_NUMBER :"+eachDataMap1.get("LINE_NUMBER").toString());
									String lineNo= eachDataMap1.get("LINE_NUMBER").toString();
									System.out.println("SERIAL_NUMBER :"+eachDataMap1.get("SERIAL_NUMBER").toString());
									String serialNo= eachDataMap1.get("SERIAL_NUMBER").toString();
									System.out.println("LIAB_BRANCH :"+eachDataMap1.get("LIAB_BRANCH").toString());
									
								    count = jdbc.getLimitCount(systemId, lineNo,serialNo,liabBranchCode);
									if(count>0) {										
										String limitauth = jdbc.checkauthForLimit(systemId, lineNo,serialNo,liabBranchCode);
										String lineStatus = jdbc.checkauthForLine(systemId, lineNo,serialNo,liabBranchCode);
										String limitId = jdbc.getLimitIdForLine(systemId, lineNo,serialNo,liabBranchCode);
										//if for limit auth
										if(!("PENDING".equalsIgnoreCase(limitauth))) {
										//for line auth
											if(!("PENDING".equalsIgnoreCase(lineStatus)) && !("".equalsIgnoreCase(lineStatus))) {
										obj.setTypeOfUDF("LIMIT");
										obj.setSystemId(systemId);
										obj.setLineNumber(lineNo);
										obj.setSerialNumber(serialNo);
										obj.setLiabBranch(liabBranchCode);
										obj.setSCI_LSP_SYS_XREF_ID(limitId);
		
									if(null!=eachDataMap1.get("UDF_FIELD_SEQUENCE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString())
											&& null!=eachDataMap1.get("UDF_FIELD_NAME") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_NAME").toString()) &&
											null!=eachDataMap1.get("UDF_FIELD_VALUE") && !"".equalsIgnoreCase(eachDataMap1.get("UDF_FIELD_VALUE").toString())) {
										
										int count2=jdbc.isValidUDFSequence(obj.getTypeOfUDF(), eachDataMap1.get("UDF_FIELD_SEQUENCE").toString(), eachDataMap1.get("UDF_FIELD_NAME").toString());
										if(count2==1)
										{
											obj.setUdfFieldSequence(eachDataMap1.get("UDF_FIELD_SEQUENCE").toString());
											obj.setUdfFieldName(eachDataMap1.get("UDF_FIELD_NAME").toString());
											obj.setUdfFieldValue(eachDataMap1.get("UDF_FIELD_VALUE").toString());
											obj.setValid("true");
											obj.setUploadStatus("Y");
											obj.setStatus("PASS");
											
										}
										else if(count2==3)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Sequence is Incorrect or not mapped with Limit");
											obj.setReason("UDF Field Sequence is Incorrect or not mapped with Limit");
											obj.setStatus("FAIL");
											
										}
										else if(count2==2)
										{
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setReason("UDF Field Name is Incorrect or not mapped with sequence");
											obj.setStatus("FAIL");
											
											
										}
									}
									else
									{
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks(
												"UDF Sequence, Field and Value cannot be null");
										obj.setReason(
												"UDF Sequence, Field and Value cannot be null");
										obj.setStatus("FAIL");
									
									}
										}
										else {
											if(lineStatus=="")
											{
												obj.setReason("Record Not Found Against(System ID,Serial Number,Line Number,Liab Branch) this combination.");
												obj.setRemarks("Record Not Found Against(System ID,Serial Number,Line Number,Liab Branch) this combination.");
											}
											else
											{
												obj.setReason("Release Line Details of Line is pending");
												obj.setRemarks("Release Line Details of Line is pending");
											}
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setStatus("FAIL");
										
											
										}
									}
										else {
											obj.setValid("false");
											obj.setUploadStatus("N");
											obj.setRemarks(" Transaction is in pending for authorisation of respective Facility for Party.");
											obj.setReason(" Transaction is in pending for authorisation of respective Facility for Party");
											obj.setStatus("FAIL");
										
										}
								}
									else {
										obj.setValid("false");
										obj.setUploadStatus("N");
										obj.setRemarks("Invalid LIMIT details");
										obj.setReason(
												"Invalid LIMIT details");
										obj.setStatus("FAIL");
									
										
									}		
								}
								
								//IF Details are Null
								else if(null==eachDataMap1.get("SYSTEM_ID") || "".equalsIgnoreCase(eachDataMap1.get("SYSTEM_ID").toString()) 
										|| null==eachDataMap1.get("PARTY_ID")|| "".equalsIgnoreCase(eachDataMap1.get("PARTY_ID").toString()) || 
										null==eachDataMap1.get("PARTY_ID")|| "".equalsIgnoreCase(eachDataMap1.get("PARTY_ID").toString()) )
								{
									obj.setValid("false");
									obj.setRemarks("Module ID cannot be null");
									obj.setUploadStatus("N");
									obj.setReason(
											"Module ID cannot be null");
									obj.setStatus("FAIL");	
									
								}
								}
								
								else
								{
									obj.setValid("false");
									obj.setRemarks("Type of UDF is Null or Invalid");
									obj.setUploadStatus("N");
									obj.setReason("Type of UDF is Null or Invalid");
									obj.setStatus("FAIL");
								
								}
								obj.setFileId(fileId);
								totalUploadedList.add(obj);
								
							}
							DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
							int batchSize = 200;
							for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
								List<OBTempBulkUDFFileUpload> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
								System.out.println(batchList);
								jdbc.createEntireBulkUDFTempFile(batchList);
							List<OBTempBulkUDFFileUpload> nw=	jdbc.updateStagingCompBulkUDFTemp();
							
								for (int k = 0; k < totalUploadedList.size(); k++) {
									if (batchList.get(k).getStatus().equalsIgnoreCase("PASS")
											&& batchList.get(k).getUploadStatus().equalsIgnoreCase("Y")) {
										if (nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("PARTY")
												&& nw.get(k).getValid().equalsIgnoreCase("true")
												&& nw.get(k).getRemarks() == null) {
											jdbc.updatePartyTempBulkUdf(nw);
											/* jdbc.updateTempToStageBulkUdfParty(nw, nw.get(j).getUdfFieldSequence().toString()); */
										} else if (nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("CAM")
												&& nw.get(k).getValid().equalsIgnoreCase("true")
												&& nw.get(k).getRemarks() == null) {
											jdbc.updateCamTempBulkUdf(nw);
										} else if (nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("LIMIT")
												&& nw.get(k).getValid().equalsIgnoreCase("true")
												&& nw.get(k).getRemarks() == null) {
											jdbc.updateLimitTempBulkUdf(nw);
										}

									}}
						}
							countPass = jdbc.getCountPassFromTemp();
							countFail = size-countPass;
							System.out.println("########## File Data is dumped into Temp table for Bulk UDF Upload##################");
							DefaultLogger.debug(this,"########## File Data is dumped into Temp table for Bulk UDF Upload##################:: ");
							
							
											
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
			resultMap.put("fail", String.valueOf(countFail));
			
			resultMap.put("fileType", fileType);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			
			return returnMap;
		
		}
		catch(Exception e)
		{
			
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));	
		}
	
		
	}

}
