/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.acknowledgmentupload;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.apache.poi.ss.usermodel.HSSFDateUtil;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBAcknowledgmentFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.acknowledgment.IAcknowledgmentErrorLog;
import com.integrosys.cms.batch.acknowledgment.OBAcknowledgmentErrorLog;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileAcknowledgment;
import com.integrosys.cms.batch.acknowledgment.IAcknowledgmentErrorLog;
import com.integrosys.cms.batch.acknowledgment.OBAcknowledgmentErrorLog;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
//import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.ui.acknowledgmentupload.proxy.IAcknowledgmentUploadProxyManager;

/**
 * @author $Author: Mukesh Mohapatra$ Command for CERSAI Acknowledgment Upload
 */
public class AcknowledgmentFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String ACKNOWLEDGMENT_UPLOAD = "AcknowledgmentUpload";
	private IAcknowledgmentUploadProxyManager acknowledgmentuploadProxy;
	

	public IAcknowledgmentUploadProxyManager getAcknowledgmentuploadProxy() {
		return acknowledgmentuploadProxy;
	}

	public void setAcknowledgmentuploadProxy(
			IAcknowledgmentUploadProxyManager acknowledgmentuploadProxy) {
		this.acknowledgmentuploadProxy = acknowledgmentuploadProxy;
	} 
	
	/**
	 * Default Constructor
	 */

	public AcknowledgmentFileUploadCmd() {
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
				{ "acknowledgmentUploadObj", "com.integrosys.cms.ui.acknowledgmentupload.OBAcknowledgmentUpload", FORM_SCOPE },
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
		IAcknowledgmentErrorLog objAcknowledgmentErrorLog = new OBAcknowledgmentErrorLog();
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();
		ArrayList rowlist1 = new ArrayList();
		
		long countPass = 0;
		long countFail = 0;
		
		int size=0;
		List finalList = new ArrayList<AcknowledgmentError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList listOfErrorDate=new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("ACK_UPLOAD");
		try {

			OBAcknowledgmentUpload acknowledgmentUpload = (OBAcknowledgmentUpload) map.get("acknowledgmentUploadObj");
			if(acknowledgmentUpload.getFileUpload().getFileName().isEmpty()){
				exceptionMap.put("acknowledgmentfileuploadError", new ActionMessage("label.acknowledgment.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else if (!acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLS")
					&& !acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLSX")
					&& !acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xlsx")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {
				
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				
				ProcessDataFileAcknowledgment dataFile = new ProcessDataFileAcknowledgment();
			    resultList = dataFile.processFile(acknowledgmentUpload.getFileUpload(), ACKNOWLEDGMENT_UPLOAD);
			    boolean flagA=false;
			    if(resultList != null && !resultList.isEmpty()) {
			    	rowlist1 = (ArrayList) resultList.get(0);
			    	if(rowlist1.get(0) != null && rowlist1.get(1) != null && rowlist1.get(2) != null && rowlist1.get(3) != null && rowlist1.get(4) != null) {
			    		 if (acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLS") || acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xls"))
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
			    	else if (acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLSX") || acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xlsx"))
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
			  }
			    if(flagA == true) {
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
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
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
							AcknowledgmentError acknowledgmentError = new AcknowledgmentError();
							if(null != errList[p][0]){
							acknowledgmentError.setColumnName(errList[p][0]);
							acknowledgmentError.setErrorMessage(errList[p][1]);
							acknowledgmentError.setFileRowNo(errList[p][3]);
							if(100 == finalList.size()){
								break;
							}
							finalList.add(acknowledgmentError);
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
							"#####################In AcknowledgmentFileUploadCmd ############:: "
									+ size);
					
					//create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("ACK_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(acknowledgmentUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
						trxValueOut = getAcknowledgmentuploadProxy().makerCreateFile(ctx,
								fileObj);
						long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
						String cellDate="";
					if (trxValueOut != null) {
						DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
					for (int i = 1; i < resultList.size(); i++) {
						       rowlist = (ArrayList) resultList.get(i);
						      exceldatalist.clear();
						      for (int j = 0; j < rowlist.size(); j++) {
						    	  if(j == 4) {
						    	  if(rowlist.get(j) != null) {
						    		  cellDate=(String) rowlist.get(4).toString();
						    	  }
						    	  }
						    	 if (acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLS") || acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xls"))
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
						    	 else if (acknowledgmentUpload.getFileUpload().getFileName().endsWith(".XLSX") || acknowledgmentUpload.getFileUpload().getFileName().endsWith(".xlsx"))
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
						               
						  }
						      
						    ArrayList<OBAcknowledgmentFile> acknowledgmentFile = jdbc.getAllCMSSecurityFile(exceldatalist.get(0));
						    OBAcknowledgmentFile acknowledgmentFileList = acknowledgmentFile.get(0);
							OBAcknowledgmentFile obj=new OBAcknowledgmentFile();
							obj.setSecurityID(((String.valueOf(exceldatalist.get(0)))));
							if(null!=exceldatalist.get(1) && !exceldatalist.get(1).toString().trim().isEmpty())
							{
								obj.setCerTrnRefNo((String.valueOf(exceldatalist.get(1))));
							}
							else 
							{
							         obj.setCerTrnRefNo((String.valueOf(exceldatalist.get(1))));
							}
			
							if(null!=exceldatalist.get(2) && !exceldatalist.get(2).toString().trim().isEmpty()) 
							{
								obj.setCerSecIntID((String.valueOf(exceldatalist.get(2))));
							}
							else 
							{
							         obj.setCerSecIntID((String.valueOf(exceldatalist.get(2))));
							}
							
							if(null!=exceldatalist.get(3)&& !exceldatalist.get(3).toString().trim().isEmpty())
							{
								obj.setCerAssetID((String.valueOf(exceldatalist.get(3))));
							}
							else 
							{
							         obj.setCerAssetID((String.valueOf(exceldatalist.get(3))));
							}
							boolean flagb=true;
							if(null!=exceldatalist.get(4)&& !exceldatalist.get(4).toString().trim().isEmpty())
							{
								
								 DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								 formatter.setLenient(false);
								 try {
								     Date date= formatter.parse(exceldatalist.get(4).toString().trim());
								     obj.setRegistrationDate(new java.sql.Date((simpleDateFormat.parse(exceldatalist.get(4).toString().trim()).getTime())));
								     System.out.println(date);
								 } catch (Exception e) {
								    System.out.println("Wrong Date Format");
								    flagb=false;
//								    DateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
//									obj.setRegistrationDate(new java.sql.Date((formatter1.parse(exceldatalist.get(4).toString().trim()).getTime())));
								    obj.setRegistrationDate(null);
								    obj.setSecurity_id(exceldatalist.get(4).toString().trim());
								    listOfErrorDate.add(obj);
								 }
								 
//								String s=exceldatalist.get(4).toString().trim();
//								String[] s1=s.split("-");
//								int n1=Integer.parseInt(s1[1]);
//								if(n1 > 12) {
//									flagb=false;
//									//String str=s1[0]+"-"+s1[1]+"-"+s1[2];
//									DateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
//									obj.setRegistrationDate(new java.sql.Date((formatter1.parse(exceldatalist.get(4).toString().trim()).getTime())));
//									obj.setSecurity_id(exceldatalist.get(4).toString().trim());
//									listOfErrorDate.add(obj);
//									//listOfErrorDate.add(exceldatalist.get(4).toString().trim());
//								}
//								else 
//								{
//									obj.setRegistrationDate(new java.sql.Date((simpleDateFormat.parse(exceldatalist.get(4).toString().trim()).getTime())));
//								}
							}
							else 
							{
							         obj.setRegistrationDate(null);
							}
							
							if(acknowledgmentFileList.getCerTrnRefNo() == null) {
								acknowledgmentFileList.setCerTrnRefNo("");
							}
							if(acknowledgmentFileList.getCerSecIntID() == null) {
								acknowledgmentFileList.setCerSecIntID("");
							}
							if(acknowledgmentFileList.getCerAssetID() == null) {
								acknowledgmentFileList.setCerAssetID("");
							}
							if(acknowledgmentFileList.getRegistrationDate() == null) {
								Date date5 = new Date();  
							    SimpleDateFormat formatter7 = new SimpleDateFormat("dd-MM-yyyy");  
							    String strDate= formatter7.format(date5);  
							    acknowledgmentFileList.setRegistrationDate(new java.sql.Date((formatter7.parse(strDate)).getTime()));
							}
							
							if(acknowledgmentFileList.getSecurityID() == null || acknowledgmentFileList.getSecurityID().toString().trim().isEmpty())
							{
								obj.setUploadStatus("N");
								if(flagb == false) {
									obj.setReason("CERSAI Acknowledgment Record Failed due to Security Id Mismatch and Date Format (" + cellDate+ ") is wrong.");
								}else {
								obj.setReason("CERSAI Acknowledgment Record Failed due to Security Id Mismatch.");
								}
								obj.setStatus("FAIL");	
							}
							else if(flagb==false) {
								obj.setUploadStatus("N");
								obj.setReason("CERSAI Acknowledgment Record Failed due to Date Format (" + cellDate+ ") is wrong.");
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
										obj.setReason("CERSAI Acknowledgment Record Failed due to field is empty.");
										obj.setStatus("FAIL");		
								}
							else if(acknowledgmentFileList.getSecurityID().equalsIgnoreCase(obj.getSecurityID()) && acknowledgmentFileList.getCerTrnRefNo() != null 
										&& acknowledgmentFileList.getCerSecIntID() != null &&  acknowledgmentFileList.getCerAssetID() != null && 
										acknowledgmentFileList.getRegistrationDate() != null && !acknowledgmentFileList.getCerTrnRefNo().toString().trim().isEmpty()
										&& !acknowledgmentFileList.getCerSecIntID().toString().trim().isEmpty() && !acknowledgmentFileList.getCerAssetID().toString().trim().isEmpty()
										&& !acknowledgmentFileList.getRegistrationDate().toString().trim().isEmpty()
										&& obj.getCerTrnRefNo() != null && !obj.getCerTrnRefNo().toString().trim().isEmpty() 
										&& obj.getCerSecIntID() != null && !obj.getCerSecIntID().toString().trim().isEmpty() 
										&& obj.getCerAssetID() != null && !obj.getCerAssetID().toString().trim().isEmpty() 
										&& obj.getRegistrationDate() != null && !obj.getRegistrationDate().toString().trim().isEmpty() ){	
									obj.setUploadStatus("Y");
									obj.setReason("CERSAI Acknowledgment details updated.");
									obj.setStatus("PASS");
									countPass++;
								}
							else if(acknowledgmentFileList.getSecurityID().equalsIgnoreCase(obj.getSecurityID()) && (acknowledgmentFileList.getCerTrnRefNo() == null 
										|| acknowledgmentFileList.getCerSecIntID() == null ||  acknowledgmentFileList.getCerAssetID() == null ||
										acknowledgmentFileList.getRegistrationDate() == null || acknowledgmentFileList.getCerTrnRefNo().toString().trim().isEmpty()
										|| acknowledgmentFileList.getCerSecIntID().toString().trim().isEmpty() || acknowledgmentFileList.getCerAssetID().toString().trim().isEmpty()
										|| acknowledgmentFileList.getRegistrationDate().toString().trim().isEmpty())
									&& obj.getCerTrnRefNo() != null && !obj.getCerTrnRefNo().toString().trim().isEmpty() 
									&& obj.getCerSecIntID() != null && !obj.getCerSecIntID().toString().trim().isEmpty() 
									&& obj.getCerAssetID() != null && !obj.getCerAssetID().toString().trim().isEmpty() 
									&& obj.getRegistrationDate() != null && !obj.getRegistrationDate().toString().trim().isEmpty() ){ 
									obj.setUploadStatus("Y");
									obj.setReason("CERSAI Acknowledgment details updated.");
									obj.setStatus("PASS");
									countPass++;
								}
							else{
							obj.setUploadStatus("N");
							obj.setReason("CERSAI Acknowledgment Record Failed.");
							obj.setStatus("FAIL");	
							}	
							obj.setFileId(fileId);
							totalUploadedList.add(obj);
					}	      
					
						DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBAcknowledgmentFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
							jdbc.createEntireAcknowledgmentStageFile(batchList);
						}
						for (int j = 0; j < totalUploadedList.size(); j++) {
							OBAcknowledgmentFile obAcknowledgmentFile  = (OBAcknowledgmentFile) totalUploadedList.get(j);
							if(obAcknowledgmentFile.getStatus().equalsIgnoreCase("PASS") && obAcknowledgmentFile.getUploadStatus().equalsIgnoreCase("Y"))
							{	
							  jdbc.insertAcknowledgmentStageSecurity(obAcknowledgmentFile);
						    }   
						}
						countFail=totalUploadedList.size()-countPass;
					}
				}
			}
			}else {
				exceptionMap.put("acknowledgmentfileformatError", new ActionMessage("label.acknowledgment.fileformat.error"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}
		}		
		
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
