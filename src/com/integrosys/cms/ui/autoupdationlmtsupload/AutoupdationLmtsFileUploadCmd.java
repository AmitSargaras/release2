/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.autoupdationlmtsupload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessage;

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
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBAutoupdationLmtsFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.autoupdationlmts.IAutoupdationLmtsErrorLog;
import com.integrosys.cms.batch.autoupdationlmts.OBAutoupdationLmtsErrorLog;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileAutoupdationLmts;
//import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.ui.autoupdationlmtsupload.proxy.IAutoupdationLmtsUploadProxyManager;

/**
 * @author $Author: Mukesh Mohapatra$ Command for CERSAI AutoupdationLmts Upload
 */
public class AutoupdationLmtsFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String AUTOUPDATIONLMTS_UPLOAD = "AutoupdationLmtsUpload";
	private IAutoupdationLmtsUploadProxyManager autoupdationlmtsuploadProxy;
	

	public IAutoupdationLmtsUploadProxyManager getAutoupdationlmtsuploadProxy() {
		return autoupdationlmtsuploadProxy;
	}

	public void setAutoupdationlmtsuploadProxy(
			IAutoupdationLmtsUploadProxyManager autoupdationlmtsuploadProxy) {
		this.autoupdationlmtsuploadProxy = autoupdationlmtsuploadProxy;
	} 
	
	/**
	 * Default Constructor
	 */

	public AutoupdationLmtsFileUploadCmd() {
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
				{ "autoupdationlmtsUploadObj", "com.integrosys.cms.ui.autoupdationlmtsupload.OBAutoupdationLmtsUpload", FORM_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
				{"trxValueOut","com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue",SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "listOfErrorDate", "java.util.List", SERVICE_SCOPE },
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
		IAutoupdationLmtsErrorLog objAutoupdationLmtsErrorLog = new OBAutoupdationLmtsErrorLog();
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();
		ArrayList rowlist1 = new ArrayList();
		
		long countPass = 0;
		long countFail = 0;
		
		int size=0;
		List finalList = new ArrayList<AutoupdationLmtsError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList listOfErrorDate=new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("AUTO_UPLOAD");
		try {

			OBAutoupdationLmtsUpload autoupdationlmtsUpload = (OBAutoupdationLmtsUpload) map.get("autoupdationlmtsUploadObj");
			/*if(autoupdationlmtsUpload.getFileUpload().getFileName().isEmpty()){
				exceptionMap.put("autoupdationlmtsfileuploadError", new ActionMessage("label.autoupdationlmts.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else if (!autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLS")
					&& !autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLSX")
					&& !autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xlsx")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {*/
				
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				
				ProcessDataFileAutoupdationLmts dataFile = new ProcessDataFileAutoupdationLmts();
//			    resultList = dataFile.processFile(autoupdationlmtsUpload.getFileUpload(), AUTOUPDATIONLMTS_UPLOAD);
			    boolean flagA=false;
			    /* if(resultList != null && !resultList.isEmpty()) {
			    	rowlist1 = (ArrayList) resultList.get(0);
			    	if(rowlist1.get(0) != null && rowlist1.get(1) != null && rowlist1.get(2) != null && rowlist1.get(3) != null && rowlist1.get(4) != null) {
			    		 if (autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLS") || autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xls"))
				          {	
			    		 HSSFCell cell1 = (HSSFCell) rowlist1.get(0);
			    		 String head1=cell1.getStringCellValue();
			    		 
			    		 HSSFCell cell2 = (HSSFCell) rowlist1.get(1);
			    		 String head2=cell2.getStringCellValue();
			    		 
			    		 HSSFCell cell3 = (HSSFCell) rowlist1.get(2);
			    		 String head3=cell3.getStringCellValue();
			    		 
			    		 HSSFCell cell4 = (HSSFCell) rowlist1.get(3);
			    		 String head4=cell4.getStringCellValue();
			    		 
			    		 HSSFCell cell5 = (HSSFCell) rowlist1.get(4);
			    		 String head5=cell5.getStringCellValue();
			    		 
			    	if(head1.equalsIgnoreCase("SECURITY ID") && head2.equalsIgnoreCase("CERSAI Transaction Reference Number") && head3.equalsIgnoreCase("CERSAI security Interest ID") 
			    			&& head4.equalsIgnoreCase("CERSAI Asset ID") && head5.equalsIgnoreCase("Date of CERSAI registration")) {
			    		flagA=true;
			    	
			    	}else {
			    		flagA=false;
			    	}
			    	}
			    	else if (autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLSX") || autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xlsx"))
			    	 {
			    		XSSFCell cell1 = (XSSFCell) rowlist1.get(0);
			    		 String head1=cell1.getStringCellValue();
			    		 
			    		 XSSFCell cell2 = (XSSFCell) rowlist1.get(1);
			    		 String head2=cell2.getStringCellValue();
			    		 
			    		 XSSFCell cell3 = (XSSFCell) rowlist1.get(2);
			    		 String head3=cell3.getStringCellValue();
			    		 
			    		 XSSFCell cell4 = (XSSFCell) rowlist1.get(3);
			    		 String head4=cell4.getStringCellValue();
			    		 
			    		 XSSFCell cell5 = (XSSFCell) rowlist1.get(4);
			    		 String head5=cell5.getStringCellValue();
			    		 
			    	if(head1.equalsIgnoreCase("SECURITY ID") && head2.equalsIgnoreCase("CERSAI Transaction Reference Number") && head3.equalsIgnoreCase("CERSAI security Interest ID") 
			    			&& head4.equalsIgnoreCase("CERSAI Asset ID") && head5.equalsIgnoreCase("Date of CERSAI registration")) {
			    		flagA=true;
			    	}
			    	else {
			    		flagA=false;
			    	}
			    }
			   }
			  }*/
			    
			    IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
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
//				DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
				
				ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
				String flag = resbundle.getString("auto.updation.fcubs.frequency.values");
				System.out.println("frequencyNamesConditions flag=>"+flag);
				
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String appDate = df.format(applicationDate);
				resultList = jdbc.getAutoUpdationResultList(appDate,flag);
				
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
					for (int p = 0; p < 8; p++) {
							AutoupdationLmtsError autoupdationlmtsError = new AutoupdationLmtsError();
							if(null != errList[p][0]){
							autoupdationlmtsError.setColumnName(errList[p][0]);
							autoupdationlmtsError.setErrorMessage(errList[p][1]);
							autoupdationlmtsError.setFileRowNo(errList[p][3]);
							if(100 == finalList.size()){
								break;
							}
							finalList.add(autoupdationlmtsError);
							}						
					}
					if(100 == finalList.size()){
						break;
					}					
				}
				
				if(finalList.size()==0){
				if (resultList.size() > 0) {
					
					 size = resultList.size();
					DefaultLogger.debug(this,
							"#####################In AutoupdationLmtsFileUploadCmd ############:: "
									+ size);
					
					//create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("AUTO_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName("");
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
						trxValueOut = getAutoupdationlmtsuploadProxy().makerCreateFile(ctx,
								fileObj);
						long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
						String cellDate="";
					if (trxValueOut != null) {
						DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
					for (int i = 0; i < resultList.size(); i++) {
						       rowlist = (ArrayList) resultList.get(i);
//						      exceldatalist.clear();
						      /*for (int j = 0; j < rowlist.size(); j++) {
						    	  if(j == 4) {
						    	  if(rowlist.get(j) != null) {
						    		  cellDate=(String) rowlist.get(4).toString();
						    	  }
						    	  }
						    	 if (autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLS") || autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xls"))
						          {		 
					                   HSSFCell cell = (HSSFCell) rowlist.get(j);
						               if(cell==null) 
						            	   exceldatalist.add(null);
						            	   else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
						                     exceldatalist.add(cell.getNumericCellValue());
						            	   else if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
						                     exceldatalist.add(cell.getStringCellValue());
						            	   else if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
						            	   exceldatalist.add(null);
						           }
						    	 else if (autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".XLSX") || autoupdationlmtsUpload.getFileUpload().getFileName().endsWith(".xlsx"))
						    	 {
						    		 XSSFCell cell = (XSSFCell) rowlist.get(j);
						    		 if(cell==null) 
						            	   exceldatalist.add(null);
						            	   else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC)
						                     exceldatalist.add(cell.getNumericCellValue());
						            	   else if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
						                     exceldatalist.add(cell.getStringCellValue());
						            	   else if(cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
						            	   exceldatalist.add(null);
						    	 }
						               
						  }*/
						      
//						    ArrayList<OBAutoupdationLmtsFile> autoupdationlmtsFile = jdbc.getAllCMSSecurityFile(exceldatalist.get(0));
//						    ArrayList<OBAutoupdationLmtsFile> autoupdationlmtsFile = new ArrayList();
//						    OBAutoupdationLmtsFile autoupdationlmtsFileList = autoupdationlmtsFile.get(0);
							OBAutoupdationLmtsFile obj=new OBAutoupdationLmtsFile();
							
							
							/*data = rs.getString("PARTY_ID");
							resultList1.add(data);
							data = rs.getString("PARTY_NAME");
							resultList1.add(data);
							data = rs.getString("FACILITY_ID");
							resultList1.add(data);
							data = rs.getString("FACILITY_NAME");
							resultList1.add(data);
							data = rs.getString("LIAB_BRANCH");
							resultList1.add(data);
							data = rs.getString("LINE_NO");
							resultList1.add(data);
							data = rs.getString("SERIAL_NO");
							resultList1.add(data);
							data = rs.getString("SECURITY_ID");
							resultList1.add(data);
							data = rs.getString("SECURITY_SUBTYPE");
							resultList1.add(data);
							data = rs.getString("STATUS");*/
							
							
//							obj.setSecurityID();
							if(null!=rowlist.get(0) && !"".equals(rowlist.get(0)))
							{
								obj.setPartyId(rowlist.get(0).toString());
							}
							else 
							{
								obj.setPartyId("");
							}
			
							if(null!=rowlist.get(1) && !"".equals(rowlist.get(1)))
							{
								obj.setPartyName(rowlist.get(1).toString());
							}
							else 
							{
								obj.setPartyName("");
							}
							
							if(null!=rowlist.get(2) && !"".equals(rowlist.get(2)))
							{
								obj.setFacilityId(rowlist.get(2).toString());
							}
							else 
							{
								obj.setFacilityId("");
							}
							
							if(null!=rowlist.get(3) && !"".equals(rowlist.get(3)))
							{
								obj.setFacilityName(rowlist.get(3).toString());
							}
							else 
							{
								obj.setFacilityName("");
							}
							
							if(null!=rowlist.get(4) && !"".equals(rowlist.get(4)))
							{
								obj.setLiabBranch(rowlist.get(4).toString());
							}
							else 
							{
								obj.setLiabBranch("");
							}
							
							if(null!=rowlist.get(5) && !"".equals(rowlist.get(5)))
							{
								obj.setLineNo(rowlist.get(5).toString());
							}
							else 
							{
								obj.setLineNo("");
							}
							
							if(null!=rowlist.get(6) && !"".equals(rowlist.get(6)))
							{
								obj.setSerialNo(rowlist.get(6).toString());
							}
							else 
							{
								obj.setSerialNo("");
							}
							
							
							if(null!=rowlist.get(7) && !"".equals(rowlist.get(7)))
							{
								obj.setSecurityID(rowlist.get(7).toString());
							}
							else 
							{
								obj.setSecurityID("");
							}
							
							if(null!=rowlist.get(8) && !"".equals(rowlist.get(8)))
							{
								obj.setSecuritySubtype(rowlist.get(8).toString());
							}
							else 
							{
								obj.setSecuritySubtype("");
							}
							
							if(null!=rowlist.get(9) && !"".equals(rowlist.get(9)))
							{
								obj.setLineStatus(rowlist.get(9).toString());
							}
							else 
							{
								obj.setLineStatus("");
							}
							
							if(null!=rowlist.get(10) && !"".equals(rowlist.get(10)))
							{
								obj.setXrefId(rowlist.get(10).toString());
							}
							else 
							{
								obj.setXrefId("");
							}
							
							if(null!=rowlist.get(11) && !"".equals(rowlist.get(11)))
							{
								obj.setFacilitySystemId(rowlist.get(11).toString());
							}
							else 
							{
								obj.setFacilitySystemId("");
							}
							
							if(null!=rowlist.get(12) && !"".equals(rowlist.get(12)))
							{
								obj.setFacilitySystemName(rowlist.get(12).toString());
							}
							else 
							{
								obj.setFacilitySystemName("");
							}
							
							if(null!=rowlist.get(13) && !"".equals(rowlist.get(13)))
							{
								obj.setDueDateMax(rowlist.get(13).toString());
							}
							else 
							{
								obj.setDueDateMax("");
							}
							
							if(null!=rowlist.get(14) && !"".equals(rowlist.get(14)))
							{
								obj.setDocCode(rowlist.get(14).toString());
							}
							else 
							{
								obj.setDocCode("");
							}
							
							/*if(obj.getLineStatus() == null || "".equals(obj.getLineStatus())) {
								obj.setReason("Line Status is Null.");
								obj.setStatus("Reject");
							}else if("PENDING".equals(obj.getLineStatus())) {
								obj.setReason("Line is in Pending Status.");
								obj.setStatus("Reject");
							}else if("SUCCESS".equals(obj.getLineStatus())) {
								obj.setReason("");
								obj.setStatus("Success");
								countPass++;
							}else{
								obj.setReason("Line Status is not Success.");
								obj.setStatus("Reject");
							}*/
							
							
							
							/*if(obj.getLineStatus() == null || "".equals(obj.getLineStatus())) {
								obj.setReason("");
								obj.setStatus("Success");
								obj.setUploadStatus("Y");
								countPass++;
							}else*/ 
							if("PENDING".equals(obj.getLineStatus())) {
								obj.setReason("Line is in Pending Status.");
								obj.setStatus("Reject");
								obj.setUploadStatus("N");
							}else if("SUCCESS".equals(obj.getLineStatus())) {
								obj.setReason("");
								obj.setStatus("Success");
								obj.setUploadStatus("Y");
								countPass++;
							}
							/*else{
								obj.setReason("");
								obj.setStatus("Success");
								obj.setUploadStatus("Y");
								countPass++;
							}*/
							
							
							
						/*	if(autoupdationlmtsFileList.getCerTrnRefNo() == null) {
								autoupdationlmtsFileList.setCerTrnRefNo("");
							}
							if(autoupdationlmtsFileList.getCerSecIntID() == null) {
								autoupdationlmtsFileList.setCerSecIntID("");
							}
							if(autoupdationlmtsFileList.getCerAssetID() == null) {
								autoupdationlmtsFileList.setCerAssetID("");
							}
							if(autoupdationlmtsFileList.getRegistrationDate() == null) {
								Date date5 = new Date();  
							    SimpleDateFormat formatter7 = new SimpleDateFormat("dd-MM-yyyy");  
							    String strDate= formatter7.format(date5);  
							    autoupdationlmtsFileList.setRegistrationDate(new java.sql.Date((formatter7.parse(strDate)).getTime()));
							}
							
							if(autoupdationlmtsFileList.getSecurityID() == null || autoupdationlmtsFileList.getSecurityID().toString().trim().isEmpty())
							{
								obj.setUploadStatus("N");
								if(flagb == false) {
									obj.setReason("CERSAI AutoupdationLmts Record Failed due to Security Id Mismatch and Date Format (" + cellDate+ ") is wrong.");
								}else {
								obj.setReason("CERSAI AutoupdationLmts Record Failed due to Security Id Mismatch.");
								}
								obj.setStatus("FAIL");	
							}
							else if(flagb==false) {
								obj.setUploadStatus("N");
								obj.setReason("CERSAI AutoupdationLmts Record Failed due to Date Format (" + cellDate+ ") is wrong.");
								obj.setStatus("FAIL");	
							}
							else if(exceldatalist.get(1)==null ||
									exceldatalist.get(2)==null ||
									exceldatalist.get(3)==null ||
									exceldatalist.get(4)==null ||
								    exceldatalist.get(1).toString().trim().isEmpty() ||
									exceldatalist.get(2).toString().trim().isEmpty() ||
									exceldatalist.get(3).toString().trim().isEmpty() ||
									exceldatalist.get(4).toString().trim().isEmpty()){
										obj.setUploadStatus("N");
										obj.setReason("CERSAI AutoupdationLmts Record Failed due to field is empty.");
										obj.setStatus("FAIL");		
								}
							else if(autoupdationlmtsFileList.getSecurityID().equalsIgnoreCase(obj.getSecurityID()) && autoupdationlmtsFileList.getCerTrnRefNo() != null 
										&& autoupdationlmtsFileList.getCerSecIntID() != null &&  autoupdationlmtsFileList.getCerAssetID() != null && 
										autoupdationlmtsFileList.getRegistrationDate() != null && !autoupdationlmtsFileList.getCerTrnRefNo().toString().trim().isEmpty()
										&& !autoupdationlmtsFileList.getCerSecIntID().toString().trim().isEmpty() && !autoupdationlmtsFileList.getCerAssetID().toString().trim().isEmpty()
										&& !autoupdationlmtsFileList.getRegistrationDate().toString().trim().isEmpty()
										&& obj.getCerTrnRefNo() != null && !obj.getCerTrnRefNo().toString().trim().isEmpty() 
										&& obj.getCerSecIntID() != null && !obj.getCerSecIntID().toString().trim().isEmpty() 
										&& obj.getCerAssetID() != null && !obj.getCerAssetID().toString().trim().isEmpty() 
										&& obj.getRegistrationDate() != null && !obj.getRegistrationDate().toString().trim().isEmpty() ){	
									obj.setUploadStatus("Y");
									obj.setReason("CERSAI AutoupdationLmts details updated.");
									obj.setStatus("PASS");
									countPass++;
								}
							else if(autoupdationlmtsFileList.getSecurityID().equalsIgnoreCase(obj.getSecurityID()) && (autoupdationlmtsFileList.getCerTrnRefNo() == null 
										|| autoupdationlmtsFileList.getCerSecIntID() == null ||  autoupdationlmtsFileList.getCerAssetID() == null ||
										autoupdationlmtsFileList.getRegistrationDate() == null || autoupdationlmtsFileList.getCerTrnRefNo().toString().trim().isEmpty()
										|| autoupdationlmtsFileList.getCerSecIntID().toString().trim().isEmpty() || autoupdationlmtsFileList.getCerAssetID().toString().trim().isEmpty()
										|| autoupdationlmtsFileList.getRegistrationDate().toString().trim().isEmpty())
									&& obj.getCerTrnRefNo() != null && !obj.getCerTrnRefNo().toString().trim().isEmpty() 
									&& obj.getCerSecIntID() != null && !obj.getCerSecIntID().toString().trim().isEmpty() 
									&& obj.getCerAssetID() != null && !obj.getCerAssetID().toString().trim().isEmpty() 
									&& obj.getRegistrationDate() != null && !obj.getRegistrationDate().toString().trim().isEmpty() ){ 
									obj.setUploadStatus("Y");
									obj.setReason("CERSAI AutoupdationLmts details updated.");
									obj.setStatus("PASS");
									countPass++;
								}
							else{
							obj.setUploadStatus("N");
							obj.setReason("CERSAI AutoupdationLmts Record Failed.");
							obj.setStatus("FAIL");	
							}	*/
							obj.setFileId(fileId);
							totalUploadedList.add(obj);
					}	     
					
						DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBAutoupdationLmtsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
							jdbc.createEntireAutoupdationLmtsStageFile(batchList);
						}
						for (int j = 0; j < totalUploadedList.size(); j++) {
							OBAutoupdationLmtsFile obAutoupdationLmtsData  = (OBAutoupdationLmtsFile) totalUploadedList.get(j);
							if(obAutoupdationLmtsData.getStatus().equalsIgnoreCase("Success") && obAutoupdationLmtsData.getUploadStatus().equalsIgnoreCase("Y"))
							{	
//							  jdbc.insertAutoupdationLmtsStageSecurity(obAutoupdationLmtsData);
								System.out.println("obAutoupdationLmtsData.getFacilitySystemId() =>"+obAutoupdationLmtsData.getFacilitySystemId()+" obAutoupdationLmtsData.getLineNo()=>"+obAutoupdationLmtsData.getLineNo()+" obAutoupdationLmtsData.getSerialNo()=>"+obAutoupdationLmtsData.getSerialNo()+" obAutoupdationLmtsData.getFacilitySystemName()=>"+obAutoupdationLmtsData.getFacilitySystemName());
								jdbc.updateAutoupdationLmtsStage(obAutoupdationLmtsData);
								jdbc.updateAutoupdationLmtsDpAmtStage(obAutoupdationLmtsData);
								
						    }   
						}
						countFail=totalUploadedList.size()-countPass;
					}
				}
			}
			else {
				resultMap.put("trxValueOut", trxValueOut);
				resultMap.put("totalUploadedList", totalUploadedList);
				resultMap.put("errorList", errorList);
		        resultMap.put("finalList", finalList);
				resultMap.put("total", String.valueOf(totalUploadedList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				resultMap.put("listOfErrorDate", listOfErrorDate);
				resultMap.put("fileType", fileType);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				//returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}
//		}		
		
			resultMap.put("trxValueOut", trxValueOut);
			resultMap.put("totalUploadedList", totalUploadedList);
			resultMap.put("errorList", errorList);
	        resultMap.put("finalList", finalList);
			resultMap.put("total", String.valueOf(totalUploadedList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));
			resultMap.put("listOfErrorDate", listOfErrorDate);
			resultMap.put("fileType", fileType);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		}
		catch(FileUploadException ex){
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
