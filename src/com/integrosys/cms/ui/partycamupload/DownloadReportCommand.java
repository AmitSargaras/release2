package com.integrosys.cms.ui.partycamupload;

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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.OBPartyCamFile;
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

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
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

			fileName = IFileUploadConstants.FILEUPLOAD_PARTYCAM_TRANS_SUBTYPE+"_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
			

			List<OBPartyCamFile> totalUploadedList = (List<OBPartyCamFile>) map.get("totalUploadedList");
			List<OBPartyCamFile> errorList = (List<OBPartyCamFile>) map.get("errorList");
			List<Object[]> totalUploadedObjList = new ArrayList<Object[]>();
			Object[] obj = null;
			if (totalUploadedList != null && totalUploadedList.size() > 0) {
				for (OBPartyCamFile l1 : totalUploadedList) {
					obj = new Object[15];
					
					obj[0] = l1.getPartyId();
					
					if (null == l1.getCamDate()) {
						obj[1] = "";
					} else {
						obj[1] = simpleDateFormat.format(l1.getCamDate());
					}
					if (null == l1.getCamLoginDate()) {
						obj[2] = "";
					} else {
						obj[2] = simpleDateFormat.format(l1.getCamLoginDate());
					}
					if (null == l1.getRamRating()) {
						obj[3] = "";
					} else {
						obj[3] = l1.getRamRating();
					}

					if (null == l1.getRamRatingYear()) {
						obj[4] = "";
					} else {
						obj[4] = l1.getRamRatingYear();
					}

					if (null == l1.getCustomerRamId()) {
						obj[5] = "";
					} else {
						obj[5] = l1.getCustomerRamId();
					}
					if (null == l1.getCamExpiryDate()) {
						obj[6] = "";
					} else {
						obj[6] = simpleDateFormat.format(l1.getCamExpiryDate());
					}
					if (null == l1.getCamExtensionDate()) {
						obj[7] = "";
					} else {
						obj[7] = simpleDateFormat.format(l1.getCamExtensionDate());
					}
					if (null == l1.getRamType()) {
						obj[8] = "";
					} else {
						obj[8] = l1.getRamType();
					}
					if (null == l1.getRamFinalizationDate()) {
						obj[9] = "";
					} else {
						obj[9] = simpleDateFormat.format(l1.getRamFinalizationDate());
					}
					if (null == l1.getFundedAmount()) {
						obj[10] = "";
					} else {
						obj[10] = new BigDecimal(l1.getFundedAmount()).toPlainString();
					}
					//MORATORIUM REGULATORY UPLOAD
					if (null == l1.getNonfundedAmount()) {
						obj[11] = "";
					} else {
						obj[11] = new BigDecimal(l1.getNonfundedAmount()).toPlainString();
					}

					if (null == l1.getRmEmployeeCode()) {
						obj[12] = "";
					} else {
						obj[12] = l1.getRmEmployeeCode();
					}

					obj[13] = l1.getStatus();
					obj[14] = l1.getReason();

					totalUploadedObjList.add(obj);
				}
			}
			if (errorList != null && errorList.size() > 0) {
				for (OBPartyCamFile l1 : errorList) {
					obj = new Object[15];
					obj[0] = l1.getPartyId();
					if (null == l1.getCamDate()) {
						obj[1] = "";
					} else {
						obj[1] = simpleDateFormat.format(l1.getCamDate());
					}
					if (null == l1.getCamLoginDate()) {
						obj[2] = "";
					} else {
						obj[2] = simpleDateFormat.format(l1.getCamLoginDate());
					}
					if (null == l1.getRamRating()) {
						obj[3] = "";
					} else {
						obj[3] = l1.getRamRating();
					}

					if (null == l1.getRamRatingYear()) {
						obj[4] = "";
					} else {
						obj[4] = l1.getRamRatingYear();
					}

					if (null == l1.getCustomerRamId()) {
						obj[5] = "";
					} else {
						obj[5] = l1.getCustomerRamId();
					}
					if (null == l1.getCamExpiryDate()) {
						obj[6] = "";
					} else {
						obj[6] = simpleDateFormat.format(l1.getCamExpiryDate());
					}
					if (null == l1.getCamExtensionDate()) {
						obj[7] = "";
					} else {
						obj[7] = simpleDateFormat.format(l1.getCamExtensionDate());
					}
					if (null == l1.getRamType()) {
						obj[8] = "";
					} else {
						obj[8] = l1.getRamType();
					}
					if (null == l1.getRamFinalizationDate()) {
						obj[9] = "";
					} else {
						obj[9] = simpleDateFormat.format(l1.getRamFinalizationDate());
					}

					if (null == l1.getFundedAmount()) {
						obj[10] = "";
					} else {
						obj[10] = new BigDecimal(l1.getFundedAmount()).toPlainString();
					}
					//MORATORIUM REGULATORY UPLOAD
					if (null == l1.getNonfundedAmount()) {
						obj[11] = "";
					} else {
						obj[11] = new BigDecimal(l1.getNonfundedAmount()).toPlainString();
					}
					if (null == l1.getRmEmployeeCode()) {
						obj[12] = "";
					} else {
						obj[12] = l1.getRmEmployeeCode();
					}
					
					obj[13] = l1.getStatus();
					obj[14] = l1.getReason();

					totalUploadedObjList.add(obj);
				}
			}
			
				Map parameters = generateParamatersMap(IFileUploadConstants.FILEUPLOAD_PARTYCAM_TRANS_SUBTYPE);
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
		

		int noOfColumns = 15;
		
		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		
		
		columnLabels= new String[]{"Party ID","CAM Date","CAM Login Date","RAM Rating","RAM Rating Year","Customer RAM ID","CAM Expiry Date","CAM Extension Date","RAM Type","RAM Finalization Date", "Total Funded Amount","Total Non Funded Amount","RM Employee Code" ,"Status", "Reason" };
		columnWidths= new Integer[]{50,50,50,10,10,50,50,50,50,50,50,50,50,120,50};
		columnType = new int[]{textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat,textFormat};


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
	
}
