/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.app.leiDetailsUpload;

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
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationDao;
import com.integrosys.cms.app.leiDetailsUpload.proxy.ILeiDetailsUploadProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileLeiDetails;
import com.integrosys.cms.batch.leidetails.ILeiDetailsErrorLog;
import com.integrosys.cms.batch.leidetails.OBLeiDetailsErrorLog;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
//import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;

/**
 * @author $Author: Mukesh Mohapatra$ Command for CERSAI LeiDetails Upload
 */
public class LeiDetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String LEIDETAILS_UPLOAD = "LeiDetailsUpload";
	private ILeiDetailsUploadProxyManager leiDetailsuploadProxy;
	

	public ILeiDetailsUploadProxyManager getLeiDetailsUploadProxy() {
		return leiDetailsuploadProxy;
	}

	public void setLeiDetailsUploadProxy(
			ILeiDetailsUploadProxyManager leiDetailsuploadProxy) {
		this.leiDetailsuploadProxy = leiDetailsuploadProxy;
	} 
	
	/**
	 * Default Constructor
	 */

	public LeiDetailsFileUploadCmd() {
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
				{ "leiDetailsUploadObj", "com.integrosys.cms.app.leiDetailsUpload.OBLeiDetailsUpload", FORM_SCOPE },
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
		ILeiDetailsErrorLog objLeiDetailsErrorLog = new OBLeiDetailsErrorLog();
		ArrayList errorList = new ArrayList();
		ArrayList resultList = new ArrayList();
		ArrayList rowlist = new ArrayList();
		ArrayList rowlist1 = new ArrayList();
		
		long countPass = 0;
		long countFail = 0;
		
		int size=0;
		List finalList = new ArrayList<LeiDetailsUploadError>();
		ArrayList totalUploadedList = new ArrayList();
		ArrayList listOfErrorDate=new ArrayList();
		ArrayList exceldatalist = new ArrayList();
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionType("LEI_DETAILS_UPLOAD");
		try {

			OBLeiDetailsUpload leiDetailsUpload = (OBLeiDetailsUpload) map.get("leiDetailsUploadObj");
			if(leiDetailsUpload.getFileUpload().getFileName().isEmpty()){
				exceptionMap.put("leiDetailsfileuploadError", new ActionMessage("label.leiDetails.file.empty"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else if (!leiDetailsUpload.getFileUpload().getFileName().endsWith(".xls")
					&& !leiDetailsUpload.getFileUpload().getFileName().endsWith(".XLS")) {
				fileType = "NOT_EXCEL";
				strError = "errorEveList";
			} else {
				
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				
				ProcessDataFileLeiDetails dataFile = new ProcessDataFileLeiDetails();
			    resultList = dataFile.processFile(leiDetailsUpload.getFileUpload(), LEIDETAILS_UPLOAD);
			    boolean flagA=false;
			    if(resultList != null && !resultList.isEmpty()) {
			    	rowlist1 = (ArrayList) resultList.get(0);
			    	if(rowlist1.get(0) != null && rowlist1.get(1) != null && rowlist1.get(2) != null) {
			    		 if (leiDetailsUpload.getFileUpload().getFileName().endsWith(".XLS") || leiDetailsUpload.getFileUpload().getFileName().endsWith(".xls"))
				          {	
			    		 HSSFCell cell1 = (HSSFCell) rowlist1.get(0);
			    		 String head1=cell1.getStringCellValue();
			    		 
			    		 HSSFCell cell2 = (HSSFCell) rowlist1.get(1);
			    		 String head2=cell2.getStringCellValue();
			    		 
			    		 HSSFCell cell3 = (HSSFCell) rowlist1.get(2);
			    		 String head3=cell3.getStringCellValue();
			    		 			    		 
			    	if(head1.equalsIgnoreCase("PARTY ID") && head2.equalsIgnoreCase("LEI Code") && head3.equalsIgnoreCase("LEI Expiry Date")) {
			    		flagA=true;
			    	
			    	}else {
			    		flagA=false;
			    	}
			    	}
			    	else {
			    		flagA=false;
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
					for (int p = 0; p < 3; p++) {
							LeiDetailsUploadError leiDetailsError = new LeiDetailsUploadError();
							if(null != errList[p][0]){
							leiDetailsError.setColumnName(errList[p][0]);
							leiDetailsError.setErrorMessage(errList[p][1]);
							leiDetailsError.setFileRowNo(errList[p][3]);
							if(100 == finalList.size()){
								break;
							}
							finalList.add(leiDetailsError);
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
							"#####################In LeiDetailsUploadUploadCmd ############:: "
									+ size);
					
					//create stage entry for file id
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType("LEI_DETAILS_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(leiDetailsUpload.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList
								.size()));
						trxValueOut = getLeiDetailsUploadProxy().makerCreateFile(ctx,
								fileObj);
						long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
						String cellDate="";
							if (trxValueOut != null) {
								DateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
								for (int i = 1; i < resultList.size(); i++) {
									rowlist = (ArrayList) resultList.get(i);
									exceldatalist.clear();
									for (int j = 0; j < rowlist.size(); j++) {
										if (j == 2) {
											if (rowlist.get(j) != null) {
												cellDate = (String) rowlist.get(2).toString().replaceAll("-", "/");
												 if(cellDate.contains("-")) {
													cellDate = cellDate.replaceAll("-", "/");
												 }
											}
										}
										if (leiDetailsUpload.getFileUpload().getFileName().endsWith(".XLS")
												|| leiDetailsUpload.getFileUpload().getFileName().endsWith(".xls")) {
											HSSFCell cell = (HSSFCell) rowlist.get(j);
											if (cell == null)
												exceldatalist.add(null);
											else if(j == 2)
												exceldatalist.add(cellDate);
											else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
												exceldatalist.add(cell.getNumericCellValue());
											else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
												exceldatalist.add(cell.getStringCellValue());
											else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
												exceldatalist.add(null);
										}
									}
						               
							StringBuilder reasonString = new StringBuilder();
						      
						    ArrayList<OBLeiDetailsFile> leiDetailsFile = jdbc.getAllCMSLeiSecurityFile(exceldatalist.get(0));
						    OBLeiDetailsFile leiDetailsFileList = leiDetailsFile.get(0);
						    OBLeiDetailsFile obj=new OBLeiDetailsFile();
							if(null!=exceldatalist.get(0) && !exceldatalist.get(0).toString().trim().isEmpty()){
								obj.setPartyId(((String.valueOf(exceldatalist.get(0)))));
							}else{
							    obj.setPartyId(null);
							}
							
							if(null!=exceldatalist.get(1)&& !exceldatalist.get(1).toString().trim().isEmpty()){
								obj.setLeiCode((String.valueOf(exceldatalist.get(1))));
							}else{
							    obj.setLeiCode(null);
							}
							if(null!=exceldatalist.get(2)&& !exceldatalist.get(2).toString().trim().isEmpty()){
								 DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
								 formatter.setLenient(false);
								 try {
								     Date date= formatter.parse(exceldatalist.get(2).toString().trim());
								     obj.setLeiExpDate(new java.sql.Date((simpleDateFormat.parse(exceldatalist.get(2).toString().trim()).getTime())));
								 } catch (Exception e) {
									obj.setUploadStatus("N");
									reasonString.append(" Invalid Date Format,");
									obj.setStatus("FAIL");
								    obj.setLeiExpDate(null);
								    listOfErrorDate.add(obj);
								 }
							}else{
							         obj.setLeiExpDate(null);
							}


							if(exceldatalist.get(0)==null || exceldatalist.get(1)==null ||
									exceldatalist.get(2)==null ||
								    exceldatalist.get(0).toString().trim().isEmpty() ||
								    exceldatalist.get(1).toString().trim().isEmpty() ||
									exceldatalist.get(2).toString().trim().isEmpty()){
									String field ="";
									if(null == exceldatalist.get(0) || exceldatalist.get(0).toString().trim().isEmpty()) {
										field = " Party ID, ";
									}
									
									if(null == exceldatalist.get(1) || exceldatalist.get(1).toString().trim().isEmpty()) {
										field = field + "LEI Code, ";
									}
									
									if(null == exceldatalist.get(2) || exceldatalist.get(2).toString().trim().isEmpty()) {
										field = field + "LEI Exp. Date ";
									}
										
										obj.setUploadStatus("N");
										reasonString.append("Mandatory fields are blank,");
										obj.setStatus("FAIL");		
							}
							ILeiDateValidationDao leiDateValidationDao = (ILeiDateValidationDao) BeanHouse.get("leiDateValidationDao");
														
							 if(null != obj.getPartyId()) {
								 boolean isValidPartyID = leiDateValidationDao.isValidPartyID(obj.getPartyId());
							if(!isValidPartyID) {
								obj.setUploadStatus("N");
								reasonString.append(" Party ID Incorrect,");
								obj.setStatus("FAIL");
							}
							 }
							
							 if(null != obj.getLeiCode()) {
								boolean flag = ASSTValidator.isValidLeiCode(obj.getLeiCode());
								if (!flag) {
									obj.setUploadStatus("N");
									reasonString.append(" LEI Code Incorrect,");	
									obj.setStatus("FAIL");
								}
								if(obj.getLeiCode().trim().length()!=20) {
								 	obj.setUploadStatus("N");
									reasonString.append(" LEI Code length is not 20 digit ,");	
									obj.setStatus("FAIL");
							    }
							 }	
									Integer leiDateValidationPeriod = leiDateValidationDao.getLeiDateValidationPeriod(obj.getPartyId());							
									if(leiDateValidationPeriod == 0) {
										leiDateValidationPeriod = Integer.parseInt(generalParamDao.getGenralParamValues("LEI_Period"));
									}		
									int monthsCount = 0;
									try{
										  SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
										  Date date = sdf.parse(exceldatalist.get(2).toString().trim());
										  monthsCount = - calculateMonthsFromDate(date);	
									}catch(Exception e){
										System.out.println(e);
									}									
		
									if(null != obj.getLeiExpDate() && leiDateValidationPeriod < monthsCount) {
										obj.setUploadStatus("N");
										reasonString.append(" LEI Expiry date cannot be more than the "+leiDateValidationPeriod+" months as per updated in master or as per property file.");
										obj.setStatus("FAIL");		
									}
							
//							reasonString = reasonString.replace((reasonString.length())-1, reasonString.length(),".");
							obj.setReason(reasonString.toString());
							
							if(!"FAIL".equals(obj.getStatus())){
								obj.setUploadStatus("Y");
								obj.setReason("LEI details updated.");
								obj.setStatus("PASS");
								countPass++;
							}	
							obj.setFileId(fileId);
							totalUploadedList.add(obj);
					}	      
					
						DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBLeiDetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
							jdbc.createEntireLeiDetailsStageFile(batchList);
						}
						for (int j = 0; j < totalUploadedList.size(); j++) {
							OBLeiDetailsFile obLeiDetailsFile  = (OBLeiDetailsFile) totalUploadedList.get(j);
							if(obLeiDetailsFile.getStatus().equalsIgnoreCase("PASS") && obLeiDetailsFile.getUploadStatus().equalsIgnoreCase("Y"))
							{	
//							  jdbc.insertLeiDetailsStageSecurity(obLeiDetailsFile);
						    }   
						}
						countFail=totalUploadedList.size()-countPass;
					}
				}
			}
			}else {
				exceptionMap.put("leiDetailsfileformatError", new ActionMessage("label.leiDetails.fileformat.error"));
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

	private static int calculateMonthsFromDate(Date date){
        int numberOfMonths = 0;
        Calendar currentDate = Calendar.getInstance();
        int year = 0; 
        int months = 0;
         
        if(date != null)
        {
              Calendar dateOfBirth = Calendar.getInstance();
              dateOfBirth.setTime(date);
              year = currentDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
              months = (currentDate.get(Calendar.MONTH)+1) - ((dateOfBirth.get(Calendar.MONTH) +1) );
                    if(currentDate.get(Calendar.DAY_OF_MONTH) < (dateOfBirth.get(Calendar.DAY_OF_MONTH)) )     
                    {
                          months--;
                    }
              numberOfMonths = months + (year * 12);
        }

        return numberOfMonths;

        }
}
