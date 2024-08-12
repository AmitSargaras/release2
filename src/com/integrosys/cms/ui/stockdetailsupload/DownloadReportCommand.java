package com.integrosys.cms.ui.stockdetailsupload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.OBStockDetailsFile;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.ui.stockdetailsupload.proxy.IStockdetailsUploadProxyManager;

public class DownloadReportCommand extends AbstractCommand implements IFileUploadConstants, IStockDetailsUploadConstants{

private IStockdetailsUploadProxyManager stockdetailsuploadProxy;
	
	public IStockdetailsUploadProxyManager getStockdetailsuploadProxy() {
		return stockdetailsuploadProxy;
	}

	public void setStockdetailsuploadProxy(IStockdetailsUploadProxyManager stockdetailsuploadProxy) {
		this.stockdetailsuploadProxy = stockdetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_FAILED_LIST, List.class.getName(),SERVICE_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ "output", ByteArrayOutputStream.class.getName(), REQUEST_SCOPE },
			{ "fileName", String.class.getName(), REQUEST_SCOPE },
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException , AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String basePath = PropertyManager.getValue(IReportConstants.BASE_PATH);
		byte[] fileData;
		String fileName = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			fileName = String.format("%s_%s%s", ICMSConstant.STOCK_DETAILS_UPLOAD,sdf.format(new Date()),".xls");
			DefaultLogger.info(this, "Generating report of file: " +  fileName + " for download");
			
			List<OBStockDetailsFile> totalUploadedList = (List<OBStockDetailsFile>) map.get(SESSION_TOTAL_UPLOADED_LIST);
			List<OBStockDetailsFile> totalFailedList = (List<OBStockDetailsFile>) map.get(SESSION_TOTAL_FAILED_LIST);
			List<OBStockDetailsFile> totalList = new ArrayList<OBStockDetailsFile>();
			
			if(totalUploadedList != null) {
				totalList.addAll(totalUploadedList);
			}
			if(totalFailedList != null) {
				totalList.addAll(totalFailedList);
			}
			
			List<String[]> itemList = new ArrayList<String[]>();
			DefaultLogger.info(this, "Processing data required for download report");
			if(totalList != null) {
				for(OBStockDetailsFile uploadedItem : totalList) {
					List<String> item = Arrays.asList(ifEmptyOrNullReturnDash(uploadedItem.getPartyId()), ifEmptyOrNullReturnDash(uploadedItem.getSourceSecurityId()),
							ifEmptyOrNullReturnDash(uploadedItem.getSecuritySubType()),ifEmptyOrNullReturnDash(uploadedItem.getNameOfStockExchange()),
							ifEmptyOrNullReturnDash(uploadedItem.getScriptCode()), ifEmptyOrNullReturnDash(uploadedItem.getNoOfUnits()),
							ifEmptyOrNullReturnDash(uploadedItem.getIssuerIdType()), ifEmptyOrNullReturnDash(uploadedItem.getNominalValue()),
							ifEmptyOrNullReturnDash(uploadedItem.getCertificateNo()), ifEmptyOrNullReturnDash(uploadedItem.getIssuerName()), 
							ifEmptyOrNullReturnDash(uploadedItem.getStatus()), ifEmptyOrNullReturnDash(uploadedItem.getReason()));
					
					itemList.add(item.toArray(new String[0]));
				}
			}
			
			Map<String, Object> parameters = generateParamatersMap(ICMSConstant.STOCK_DETAILS_UPLOAD);
			 
			BaseReport report = new BaseReport();
			report.exportReportByWriter(parameters, fileName,itemList, "xls");
			
			DefaultLogger.info(this, "Converted processed data in excel format");
			
			File file = new File(basePath+fileName);
			FileInputStream fis = new FileInputStream(file);
			fileData = IOUtils.toByteArray(fis);
			output.write(fileData);
			
			resultMap.put("fileName", fileName);
			resultMap.put("output", output);
			returnMap.put(COMMAND_RESULT_MAP, resultMap);
		}catch(Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error while generating and downloading report",e);
			throw new CommandProcessingException("Error while generating and downloading report", e);
		}
		return returnMap;
	}
	
	private String ifEmptyOrNullReturnDash(Object obj) {
		if(obj == null) {
			return "-";
		}
		if(obj instanceof String) {
			String str = (String) obj;
			if(StringUtils.isBlank(str)) {
				return "-";
			} else { 
				return str;
			}
		}
		
		return obj.toString();
	}
	
	private Map<String, Object> generateParamatersMap(String uploadFileType) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		int noOfColumns = 12;
		
		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		
		
		columnLabels= new String[]
				{"Party ID", "Source Security ID", "Security Sub Type", "Name of Stock Exchange" , "Script Code", 
				"Number Of Units", "1) Promoters of the borrowing company 2) Group company 3) Others", "Nominal Value" , 
				"Certificate Number", "Issuer name","Status", "Reason"};
		columnWidths= new Integer[]{50, 50 ,50 ,50 ,50 ,50 ,50 ,50, 50, 50 , 50 ,50};
		columnType = new int[]
				{textFormat, textFormat, textFormat, textFormat,textFormat,
				amountFormat, amountFormat, amountFormat, 
				textFormat, textFormat, textFormat, textFormat};

		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		parameters.put(KEY_REPORT_NAME, uploadFileType+".xls");
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));

		return parameters;
	}
}
