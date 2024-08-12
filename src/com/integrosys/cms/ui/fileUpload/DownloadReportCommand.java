package com.integrosys.cms.ui.fileUpload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBahrainFile;
import com.integrosys.cms.app.fileUpload.bus.OBFdFile;
import com.integrosys.cms.app.fileUpload.bus.OBBaselUploadFile;
import com.integrosys.cms.app.fileUpload.bus.OBFinwareFile;
import com.integrosys.cms.app.fileUpload.bus.OBHongKongFile;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.app.poi.report.writer.FileFormat;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.app.poi.report.xml.schema.ReportColumn;
import com.integrosys.cms.batch.reports.ReportException;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * This command creates a Image Tag
 * 
 * 
 * 
 */

public class DownloadReportCommand extends AbstractCommand  implements IFileUploadConstants {


	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "errorList", "java.util.List", SERVICE_SCOPE },
				{ "system", "java.lang.String", REQUEST_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "stagingReferenceId", "java.lang.String", SERVICE_SCOPE},
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

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String basePath = getBasePath();
		String system = (String) map.get("system");
		String stagingReferenceId = (String) map.get("stagingReferenceId");
		IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
		OutputStream file ;
		byte[] fileData;
		String fileName = "";
		try {

			if(system!=null && system.equals("UBS_UPLOAD")){
				fileName = IFileUploadConstants.FTP_UBS_FILE_NAME
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}else if(system!=null && system.equals("BAHRAIN_UPLOAD")){
				fileName = IFileUploadConstants.FTP_BAHRAIN_FILE_NAME
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}else if(system!=null && system.equals("FINWARE_UPLOAD")){
				fileName = IFileUploadConstants.FTP_FINWARE_FILE_NAME
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}else if(system!=null && system.equals("HONGKONG_UPLOAD")){
				fileName = IFileUploadConstants.FTP_HONGKONG_FILE_NAME
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}
			// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
			else if(system!=null && system.equals("BASEL_UPLOAD")){
				fileName = IFileUploadConstants.BASEL_UPLOAD_FILE_NAME
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}
			//Added for FD Upload
			else if(system!=null && "FD_UPLOAD".equals(system)){
				fileName = bundle.getString(IFileUploadConstants.FTP_FD_FILE_NAME)+"_"
						+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			}
				
			Map systemReportMap = fileUploadJdbc.getSystemUploadFileNameAndUploadTime(stagingReferenceId);
			String reportFileName = (String) systemReportMap.get("FILENAME");
			Date reportFileUploadDate = (Date) systemReportMap.get("UPLOADTIME");

			if (system != null && system.equals("BASEL_UPLOAD")) {
				List<OBBaselUploadFile> reportList = new ArrayList<OBBaselUploadFile>();
				IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				reportList = jdbc.reportList();
				List<OBBaselUploadFile> totalUploadedList = reportList;
				List<Object[]> totalUploadedObjList = new ArrayList<Object[]>();
				Object[] obj = null;
				if (totalUploadedList != null && totalUploadedList.size() > 0) {
					for (OBBaselUploadFile l1 : totalUploadedList) {
						obj = new Object[5];
						obj[0] = l1.getSerialNo();
						obj[1] = l1.getLine();
						obj[2] = l1.getCustomer();
						obj[3] = l1.getStatus();
						obj[4] = l1.getReason();
						totalUploadedObjList.add(obj);
					}
				}
				
				Map parameters = generateParamatersMap("BASEL_UPDATE_REPORT");
				List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
				String reportFileType="xls";
				new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			}
			// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
			
			if(system!=null && system.equals("UBS_UPLOAD")){
				List<OBUbsFile> totalUploadedList = (List<OBUbsFile>) map.get("totalUploadedList");
				List<OBUbsFile> errorList = (List<OBUbsFile>) map.get("errorList");
				List<Object[]> totalUploadedObjList= new ArrayList<Object[]>();
				Object[] obj=null;
				if(totalUploadedList!= null && totalUploadedList.size()>0){
				for (OBUbsFile l1 : totalUploadedList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}
				}
				if(errorList!= null && errorList.size()>0){
				for (OBUbsFile l1 : errorList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}
				}
					Map parameters = generateParamatersMap("UBS");
					List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
					String reportFileType="xls";
					new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
					
				
			}else if (system!=null && system.equals("HONGKONG_UPLOAD")){
				List<OBHongKongFile> totalUploadedList = (List<OBHongKongFile>) map.get("totalUploadedList");
				List<OBHongKongFile> errorList = (List<OBHongKongFile>) map.get("errorList");
				List<Object[]> totalUploadedObjList= new ArrayList<Object[]>();
				Object[] obj=null;
				if(totalUploadedList!= null && totalUploadedList.size()>0){
				for (OBHongKongFile l1 : totalUploadedList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}
				}
				if(errorList!= null && errorList.size()>0){
				for (OBHongKongFile l1 : errorList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}
				}
					Map parameters = generateParamatersMap("HONGKONG");
					List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
					String reportFileType="xls";
					new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
					
				
			}else if (system!=null && system.equals("BAHRAIN_UPLOAD")){
				List<OBBahrainFile> totalUploadedList = (List<OBBahrainFile>) map.get("totalUploadedList");
				List<OBBahrainFile> errorList = (List<OBBahrainFile>) map.get("errorList");
				List<Object[]> totalUploadedObjList= new ArrayList<Object[]>();
				Object[] obj=null;
				if(totalUploadedList!= null && totalUploadedList.size()>0){
				for (OBBahrainFile l1 : totalUploadedList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					 totalUploadedObjList.add(obj);
				}
				}
				if(errorList!= null && errorList.size()>0){
				for (OBBahrainFile l1 : errorList) {
					 obj=new Object[8];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[3]=l1.getCurrency();
					//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
					 obj[4]=l1.getStatus();
					 obj[5]=l1.getReason();
					 obj[6] = reportFileName;
					 obj[7] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}}
					Map parameters = generateParamatersMap("BAHRAIN");
					List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
					String reportFileType="xls";
					new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
					
			}else if (system!=null && system.equals("FINWARE_UPLOAD")){
				List<OBFinwareFile> totalUploadedList = (List<OBFinwareFile>) map.get("totalUploadedList");
				List<OBFinwareFile> errorList = (List<OBFinwareFile>) map.get("errorList");
				List<Object[]> totalUploadedObjList= new ArrayList<Object[]>();
				Object[] obj=null;
				if(totalUploadedList!= null && totalUploadedList.size()>0){
				for (OBFinwareFile l1 : totalUploadedList) {
					 obj=new Object[7];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					 obj[3]=l1.getStatus();
					 obj[4]=l1.getReason();
					 obj[5] = reportFileName;
					 obj[6] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}
				}
				if(errorList!= null && errorList.size()>0){
				for (OBFinwareFile l1 : errorList) {
					 obj=new Object[7];
					 obj[0]=l1.getSerialNo();
					 obj[1]=l1.getLine();
					 obj[2]=l1.getCustomer();
					 obj[3]=l1.getStatus();
					 obj[4]=l1.getReason();
					 obj[5] = reportFileName;
					 obj[6] = UIUtil.formatTime(reportFileUploadDate);
					totalUploadedObjList.add(obj);
				}}
					Map parameters = generateParamatersMap("FINWARE");
					List<String[]> reportDataList = getReportDataList(totalUploadedObjList,parameters);
					String reportFileType="xls";
					new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			}


            if (system != null && "FD_UPLOAD".equals(system)) {
				List<OBFdFile> totalUploadedList = (List<OBFdFile>) map
						.get("totalUploadedList");
				List<OBFdFile> errorList = (List<OBFdFile>) map
						.get("errorList");
				List<Object[]> totalUploadedObjList = new ArrayList<Object[]>();
				Object[] obj = null;
				if (totalUploadedList != null && totalUploadedList.size() > 0) {
					for (OBFdFile l1 : totalUploadedList) {
						obj = new Object[8];
						obj[0] = l1.getDepositNumber();
						
						//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obj[1] = simpleDateFormat.format(l1.getDateOfDeposit());
						obj[2] = simpleDateFormat.format(l1.getDateOfMaturity());
						obj[3] = l1.getInterestRate();
						obj[4] = l1.getStatus();
						obj[5] = l1.getReason();
						obj[6] = reportFileName;
						obj[7] = UIUtil.formatTime(reportFileUploadDate);
						totalUploadedObjList.add(obj);
					}
				}
				if (errorList != null && errorList.size() > 0) {
					for (OBFdFile l1 : errorList) {
						obj = new Object[8];
						obj[0] = l1.getDepositNumber();
						
						//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obj[1] = simpleDateFormat.format(l1.getDateOfDeposit());
						obj[2] = simpleDateFormat.format(l1.getDateOfMaturity());
						obj[3] = l1.getInterestRate();
						obj[4] = l1.getStatus();
						obj[5] = l1.getReason();
						obj[6] = reportFileName;
						obj[7] = UIUtil.formatTime(reportFileUploadDate);
						totalUploadedObjList.add(obj);
					}
				}
				Map parameters = generateParamatersMapForFd("FD",bundle);
				List<String[]> reportDataList = getReportDataList(
						totalUploadedObjList, parameters);
				String reportFileType = "xls";
				new BaseReport().exportReportByWriter(parameters, fileName,
						reportDataList, reportFileType);

			}
			

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
		
		int noOfColumns = 5;
		
		//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
		if("UBS".equals(uploadFileType) || "HONGKONG".equals(uploadFileType) || "BAHRAIN".equals(uploadFileType)){
			 noOfColumns = 8;
		}
		else if(!"BASEL_UPDATE_REPORT".equals(uploadFileType)) {
			noOfColumns = 7;
		}
		//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		
		//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
		if("UBS".equals(uploadFileType) || "HONGKONG".equals(uploadFileType) || "BAHRAIN".equals(uploadFileType)){
			columnLabels= new String[]{"Serial Number","Line No.","Customer Id","Currency","Status","Reason","File Name", "Upload Time"};
			columnWidths= new Integer[]{30,50,50,30,50,150,50,50};
			columnType = new int[]{textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat};
		}
		//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
		else if(!"BASEL_UPDATE_REPORT".equals(uploadFileType)){
			columnLabels= new String[]{"Serial Number","Line No.","Customer Id","Status","Reason","File Name", "Upload Time"};
			columnWidths= new Integer[]{30,50,50,50,150,50,50};
			columnType = new int[]{textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat};
			}
		else{
		columnLabels= new String[]{"Serial Number","Line No.","Customer Id","Status","Reason"};
		columnWidths= new Integer[]{30,50,50,50,150};
		columnType = new int[]{textFormat,textFormat,textFormat,textFormat,textFormat};
		}
		/*for (int i = 0; i < noOfColumns; i++) {
			ReportColumn column = reportColumns[i];
			columnLabels[i] = column.getHeader();
			columnWidths[i] = column.getWidth();

			if ("textFormat".equals(column.getFormat()))
				columnType[i] = textFormat;
			if ("amountFormat".equals(column.getFormat()))
				columnType[i] = amountFormat;

		}*/

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
			// if(object instanceof Date)
			// return DateFormatUtils.format(new Date(object.toString()),
			// "dd-MM-yyyy");
			// else
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
	
	//Added for FD Upload
	private Map generateParamatersMapForFd(String uploadFileType, ResourceBundle bundle) {
		Map parameters = new HashMap();

		int noOfColumns = 8;
		ReportColumn[] reportColumns = new ReportColumn[noOfColumns];
		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		columnLabels = new String[] { "Deposit Number", "Date of Deposit",
				"Date of Maturity", "Interest Rate", "Status", "Reason", "File Name", "Upload Time" };
		columnWidths = new Integer[] { 70, 50, 50, 10, 50, 120 , 50, 50 };
		columnType = new int[] { textFormat, textFormat, textFormat,
				FileFormat.COL_TYPE_FLOAT, textFormat, textFormat, textFormat, textFormat, textFormat };
		/*
		 * for (int i = 0; i < noOfColumns; i++) { ReportColumn column = new
		 * ReportColumn(); column.setHeader(columnLabels[i]);
		 * column.setWidth(columnWidths[i]);
		 * 
		 * if(columnType[i]==textFormat){ column.setFormat("textFormat"); }else{
		 * column.setFormat("FileFormat.COL_TYPE_FLOAT"); }
		 * reportColumns[i]=column; }
		 * 
		 * for (int i = 0; i < noOfColumns; i++) { ReportColumn column =
		 * reportColumns[i]; columnLabels[i] = column.getHeader();
		 * columnWidths[i] = column.getWidth();
		 * 
		 * if ("textFormat".equals(column.getFormat())) columnType[i] =
		 * textFormat; if
		 * ("FileFormat.COL_TYPE_FLOAT".equals(column.getFormat()))
		 * columnType[i] = amountFormat;
		 * 
		 * }
		 */

		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		//parameters.put(KEY_REPORT_NAME, uploadFileType + ".xls");
		parameters.put(KEY_REPORT_NAME, bundle.getString(IFileUploadConstants.FTP_FD_FILE_NAME) + ".xls");
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));

		return parameters;
	}
}
