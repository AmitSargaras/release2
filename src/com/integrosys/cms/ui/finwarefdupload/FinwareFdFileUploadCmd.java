/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.finwarefdupload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.common.filereader.CSVReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrDetLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.finwarefdupload.proxy.IFinwareFdUploadProxyManager;



/**
@author $Author: Abhijeet J$
* Command for Upload UBS File
 */
public class FinwareFdFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String FINWAREFD_UPLOAD = "FinwareFdUpload";
	private IFinwareFdUploadProxyManager finwarefduploadProxy;



	public IFinwareFdUploadProxyManager getFinwarefduploadProxy() {
		return finwarefduploadProxy;
	}

	public void setFinwarefduploadProxy(
			IFinwareFdUploadProxyManager finwarefduploadProxy) {
		this.finwarefduploadProxy = finwarefduploadProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public FinwareFdFileUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	                { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
	    				GLOBAL_SCOPE },
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{  "finwarefduploadObj", "com.integrosys.cms.ui.finwarefdupload.OBFinwareFdUpload", FORM_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{  "finwarefduploadObj", "com.integrosys.cms.ui.finwarefdupload.OBFinwareFdUpload", FORM_SCOPE },
					{"fileType", "java.lang.String", REQUEST_SCOPE},
					{"objUbsErrorLog", "com.integrosys.cms.batch.ubs.IUbsErrorLog", REQUEST_SCOPE},
					{ "objUbsErrorLogService", "com.integrosys.cms.batch.ubs.IUbsErrorLog", SERVICE_SCOPE },
					{ "ubsErrorLogList", "java.util.List", SERVICE_SCOPE } 
					});				  
		}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			String strError = "";
			String fileType = "";
			String uploadId="";
			Set errMsg=null;
			IUbsErrorLog objErrDetail =new OBUbsErrorLog();

			Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			/*set local*/
			CSVReader.setLocale(local);
			 int count=0;
			 IUbsErrDetLog obUbsErrDetLog[]=null;
			try {

				OBFinwareFdUpload finwarefdupload = (OBFinwareFdUpload) map.get("finwarefduploadObj");
				if (!finwarefdupload.getFileUpload().getFileName().endsWith(".txt")&&
						!finwarefdupload.getFileUpload().getFileName().endsWith(".TXT")) {
					fileType = "NOT_TXT";
					strError = "errorEveList";
				}
				else
				{
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(finwarefdupload.getFileUpload(),FINWAREFD_UPLOAD);
				try {
					uploadId=(new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_UPLOAD, true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				obUbsErrDetLog=new IUbsErrDetLog[dataFile.getErrorList().size()];
				for(int j=0;j<dataFile.getMaxCount();j++)
				{


					String [][]errorData = new String[50][2];
					errorData = (String[][])dataFile.getErrorList().get(new Integer(j));
					
					if(!(errorData==null)){
						errMsg=new HashSet();
						for(int k=0;k<=errorData.length-1;k++) {
							if(errorData[k][0]!=null) {		
//								System.out.println(errorData[k][0]+" Value: " +errorData[k][1]);																	

								errMsg.add(errorData[k][0]);}
							}
						obUbsErrDetLog[count]=new OBUbsErrDetLog();
						obUbsErrDetLog[count].setPtId(uploadId);
						obUbsErrDetLog[count].setRecordNo(j+1+"");
						obUbsErrDetLog[count].setErrorMsg("Validation Error in "+errMsg.toString());
						obUbsErrDetLog[count].setTime(new Date());
						count++;
					}
					
					
					

				}
				
				
				objErrDetail = getFinwarefduploadProxy().compareFinwareFdfile(resultList,finwarefdupload.getFileUpload().getFileName(),uploadId,obUbsErrDetLog);
				}
				// Added BY Anil for Pagination
				List ubsErrorLogList = new ArrayList();
				if (objErrDetail.getErrEntriesSet() != null && objErrDetail.getErrEntriesSet().size() > 0)
					ubsErrorLogList.addAll(objErrDetail.getErrEntriesSet());

				DefaultLogger.debug(this, "-------UbsErrorLog size------" + ubsErrorLogList.size());
				resultMap.put("objUbsErrorLogService", objErrDetail);
				resultMap.put("ubsErrorLogList", ubsErrorLogList);
				
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
		    	resultMap.put("objUbsErrorLog", objErrDetail);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}
			catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			
			
	    }


}
