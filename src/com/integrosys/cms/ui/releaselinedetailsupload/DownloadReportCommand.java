package com.integrosys.cms.ui.releaselinedetailsupload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.OBReleaselinedetailsFile;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.app.poi.report.writer.FileFormat;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.batch.reports.ReportException;

/**
 * This command creates a Image Tag
 * 
 * 
 * 
 */

public class DownloadReportCommand extends AbstractCommand  implements IFileUploadConstants {


	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "errorList", "java.util.List", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
		});

	}
	
	public String getBasePath() {
		return PropertyManager.getValue(IReportConstants.BASE_PATH);
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String basePath = getBasePath();
		OutputStream file ;
		byte[] fileData;
		String fileName = "";
		try {

			fileName = IFileUploadConstants.FILEUPLOAD_RELEASELINEDETAILS_TRANS_SUBTYPE+"_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			DefaultLogger.debug(this, "File name is "+fileName);


			List<OBReleaselinedetailsFile> totalUploadedList = (List<OBReleaselinedetailsFile>) map.get("totalUploadedList");
			List<OBReleaselinedetailsFile> errorList = (List<OBReleaselinedetailsFile>) map.get("errorList");
			List<Object[]> totalUploadedObjList = new ArrayList<Object[]>();
			Object[] obj = null;
			DefaultLogger.debug(this, "error list is "+errorList);
			DefaultLogger.debug(this, "totalUploadedList list is "+totalUploadedList);

			if (totalUploadedList != null && totalUploadedList.size() > 0) {
				for (OBReleaselinedetailsFile l1 : totalUploadedList) {
					obj = new Object[11];
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getSystemID());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getLineNo());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getSerialNo());
					DefaultLogger.debug(this, "l1.getLiabBranch() "+l1.getLiabBranch());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getReleaseAmount());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getExpDate());
					
					if(null==l1.getSystemID()){
						obj[0] ="";	
						}else{
						obj[0] = l1.getSystemID();
						}
					
					if(null==l1.getLineNo()){
					obj[1] ="";	
					}else{
					obj[1] = l1.getLineNo();
					}
					if(null==l1.getSerialNo()){
					obj[2] ="";	
					}else{
						obj[2] = l1.getSerialNo();
					}
					if(null==l1.getLiabBranch()){
						obj[3] ="";	
						}else{
							FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
							String id=fileUpload.getReleaselinedetailsLiabBranchReverseID(l1.getLiabBranch());
							//obj[3] = l1.getLiabBranch();
							obj[3] =id;
						}
					if(null==l1.getReleaseAmount()){
						obj[4] ="";	
						}else{
							if(validateAmount(l1.getReleaseAmount())) {
							obj[4] =  new BigDecimal(l1.getReleaseAmount()).toPlainString();
							}else {
								obj[4] = "";
							}
						}
					if(null==l1.getExpDate()){
						obj[5] ="";	
						}else{
							obj[5] = l1.getExpDate();
						}
					if(null==l1.getPslFlag()){
						obj[6] ="";	
						}else{
							obj[6] = l1.getPslFlag();
						}
					if(null==l1.getPslValue()){
						obj[7] ="";	
						}else{
							obj[7] = l1.getPslValue();
						}
					if(null==l1.getRuleID()){
						obj[8] ="";	
						}else{
							obj[8] = l1.getRuleID();
						}
					System.out.println("positive DownloadReport for releaseline upload=>l1.getExpDate()=>"+l1.getExpDate());	
					obj[9] = l1.getStatus();
					obj[10] = l1.getReason();
					totalUploadedObjList.add(obj);
				}
			}
			if (errorList != null && errorList.size() > 0) {
				for (OBReleaselinedetailsFile l1 : errorList) {
					obj = new Object[11];
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getSystemID());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getLineNo());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getSerialNo());
					DefaultLogger.debug(this, "l1.getLiabBranch() "+l1.getLiabBranch());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getReleaseAmount());
					DefaultLogger.debug(this, "l1.getLineNo() "+l1.getExpDate());
					obj[0] = l1.getSystemID();
					if(null==l1.getLineNo()){
						obj[1] ="";	
						}else{
						obj[1] = l1.getLineNo();
						}
						if(null==l1.getSerialNo()){
						obj[2] ="";	
						}else{
							obj[2] = l1.getSerialNo();
						}
						if(null==l1.getLiabBranch()){
							obj[3] ="";	
							}else{
								FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
								String id=fileUpload.getReleaselinedetailsLiabBranchReverseID(l1.getLiabBranch());
								//obj[3] = l1.getLiabBranch();
								obj[3]=id;
							}
						if(null==l1.getReleaseAmount()){
							obj[4] ="";	
							}else{
								if(validateAmount(l1.getReleaseAmount())) {
								obj[4] =  new BigDecimal(l1.getReleaseAmount()).toPlainString();
								}else {
									obj[4] = "";
								}
							}
						if(null==l1.getExpDate()){
							obj[5] ="";	
							}else{
								obj[5] = l1.getExpDate();
							}
						if(null==l1.getPslFlag()){
							obj[6] ="";	
							}else{
								obj[6] = l1.getPslFlag();
							}
						if(null==l1.getPslValue()){
							obj[7] ="";	
							}else{
								obj[7] = l1.getPslValue();
							}
						if(null==l1.getRuleID()){
							obj[8] ="";	
							}else{
								obj[8] = l1.getRuleID();
							}
						System.out.println("positive DownloadReport for releaseline upload=>l1.getExpDate()=>"+l1.getExpDate());	
						obj[9] = l1.getStatus();
						obj[10] = l1.getReason();
					totalUploadedObjList.add(obj);
				}
			}
			
				Map parameters = generateParamatersMap(IFileUploadConstants.FILEUPLOAD_RELEASELINEDETAILS_TRANS_SUBTYPE);
				List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
				String reportFileType="xls";
				new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			
			File file1 = new File(basePath+fileName);
			FileInputStream fis = new FileInputStream(file1);
			fileData = IOUtils.toByteArray(fis);
			output.write(fileData);
			result.put("fileName", fileName);
			result.put("output", output);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			DefaultLogger.debug(this, "Going out of doExecute()");


		}
		catch(Exception e){
			e.printStackTrace();
		}
		return returnMap;
	}
	int textFormat = FileFormat.COL_TYPE_STRING;

	int amountFormat = FileFormat.COL_TYPE_AMOUNT_FLOAT;
	
	private Map generateParamatersMap(String uploadFileType) {
		Map parameters = new HashMap();
		
		int noOfColumns = 13;
		
		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		
		
		columnLabels= new String[]{"System ID","Line Number","Serial Number","Liab Branch","Released Amount","Limit Expiry Date","PSL Flag","PSL Value","Rule ID","Status", "Reason"};
		columnWidths= new Integer[]{50,50,50,40,40,50,50,50,50,50,50};
		columnType = new int[]{textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat};

		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		parameters.put(KEY_REPORT_NAME, uploadFileType+".xls");
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));

		return parameters;
	}
	
	private List<String[]> getReportDataList(List<Object[]> reportQueryResult,
			Map parameters) {

		int noOfColumns = ((String[]) parameters.get(KEY_COL_LABEL)).length;

		List<String[]> dataList = new LinkedList<String[]>();

		if (reportQueryResult.size() > 0) {
			DefaultLogger.debug(this,"reportQueryResult.size()---------------->"+reportQueryResult.size());
			int no=1;
			for (Object[] objects : reportQueryResult) {
				String records[] = new String[noOfColumns];

				if (objects.length != noOfColumns) {
//					System.out.println("Query columns dosen't match with configured columns");
					throw new ReportException("Query columns dosen't match with configured columns");
				}

				for (int i = 0; i < noOfColumns; i++) {
					if (objects[i] != null && !"".equals(objects[i])) {
						records[i] = convertToString(objects[i]);
					} else {
						records[i] = "-";
					}
				}
				dataList.add(records);
				no++;
			}

		}

		return dataList;
	}
	
	public String convertToString(Object object) {
		if (object != null && !object.equals("")) {
	
			if (object instanceof Boolean) {
				if ((Boolean) object == true)
					return "Yes";
				else
					return "No";
			}
			return object.toString();
		} else
			return "";
	}
	
	public static boolean validateAmount(String str) 
    { 
		
       // String regex = "[0-9]+\\.[0-9]*"; 
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
