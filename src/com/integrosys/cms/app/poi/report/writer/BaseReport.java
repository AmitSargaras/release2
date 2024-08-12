package com.integrosys.cms.app.poi.report.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.batch.eod.IEodSyncConstants;
import com.integrosys.cms.batch.eod.IPosidexFileGenConstants;
import com.integrosys.cms.batch.erosion.schedular.IErosionFileConstants;
import com.integrosys.cms.batch.feeds.CSVFileWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class BaseReport extends CommonBaseReport{
	
	protected final String MSG_NOENTITYCONFIG = "No Entity Configuration Present";
	protected final String MSG_REPORTGENFAILED = "Report Generation Failed";
	public BaseReport() {
		super();
	}

	public void exportReportByWriter(Map<String, Object> parameters, String fileName, List<String[]> dataList, String fileType) {

		try {
			DefaultLogger.debug(this,"exportReportByWriter in >>>>>>>>>>>>>>>");
			Thread t = null;
			String basePath = getBasePath();
			BaseReportWriter writer=null;
			if (fileType.equals("xls") || fileType.equals("xlsx")){
				writer = new ExcelReportWriter( basePath, parameters, fileName, dataList);
			} else if("delimiterText".equals(fileType)){
				if("Y".equals(parameters.get(IEodSyncConstants.KEY_IS_EOD_REPORT))){
					basePath=getBasePathEOD();
				}else if("Y".equals(parameters.get(IPosidexFileGenConstants.KEY_IS_POSIDEX_FILE))){
					basePath=getBasePathPosidex();
				}
				System.out.println("BaseReport.java => write records for ONE file only =>Going for TextDataWriter");
				writer = new TextDataWriter(basePath, parameters, fileName, dataList, fileType);
			}
//				t = new ExcelReportWriter( basePath, parameters, fileName, dataList);
//				  
			/*else if (fileType.equals("pdf"))
				t = new PDFReportWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);
			else if ( fileType.equals("fixedText") ||  fileType.equals("delimiterText") || fileType.equals("txt"))
				t = new TextDataWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);
			else if ( fileType.equals("xml"))
				t= new XMLDataWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);*/
//			t.start();
			DefaultLogger.debug(this,"Before writer.generate() >>>>>>>>>>>>>>>");
			writer.generate();
			DefaultLogger.debug(this,"exportReportByWriter out >>>>>>>>>>>>>>>");
		} catch (Exception e) {
			System.out.println("BaseReport.java Exception Occures == "+e);
			e.printStackTrace();
		}
	}
	
	public void exportReportByWriter(Map<String, Object> parameters, String fileName,String fileName2,String fileName3, List<String[]> dataList, String fileType) {

		try {
			
			DefaultLogger.debug(this,"exportReportByWriter in >>>>>>>>>>>>>>>");
			Thread t = null;
			String basePath = getBasePath();
			BaseReportWriter writer=null;
			if (fileType.equals("xls")){
				writer = new ExcelReportWriter( basePath, parameters, fileName,fileName2,fileName3, dataList);
			} else if("delimiterText".equals(fileType)){
				if("Y".equals(parameters.get(IEodSyncConstants.KEY_IS_EOD_REPORT))){
					basePath=getBasePathEOD();
				}else if("Y".equals(parameters.get(IPosidexFileGenConstants.KEY_IS_POSIDEX_FILE))){
					basePath=getBasePathPosidex();
				}
				System.out.println("BaseReport.java => write records for TWO files and one zip =>Going for TextDataWriter");
				writer = new TextDataWriter(basePath, parameters, fileName,fileName2,fileName3, dataList, fileType);
			}
//				t = new ExcelReportWriter( basePath, parameters, fileName, dataList);
//				  
			/*else if (fileType.equals("pdf"))
				t = new PDFReportWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);
			else if ( fileType.equals("fixedText") ||  fileType.equals("delimiterText") || fileType.equals("txt"))
				t = new TextDataWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);
			else if ( fileType.equals("xml"))
				t= new XMLDataWriter((Ticket)parameters.get("ticket"), basePath, parameters, fileName, dataList, fileType);*/
//			t.start();
			writer.generate();
			DefaultLogger.debug(this,"exportReportByWriter out >>>>>>>>>>>>>>>");
		} catch (Exception e) {
			System.out.println("BaseReport.java => Exception Occures in ---- exportReportByWriter ---- === "+e);
			e.printStackTrace();
		}
	}
	
	
	
	public String getBasePathEOD() {
		return PropertyManager.getValue(IEodSyncConstants.FTP_MASTER_UPLOAD_LOCAL_DIR);
	}

	public String getBasePathPosidex() {
		return PropertyManager.getValue(IPosidexFileGenConstants.FTP_POSIDEX_UPLOAD_LOCAL_DIR);
	}
	
	public void writeCSVFile(String fileName, List<String[]> reportDataList) throws SearchDAOException {
		DefaultLogger.debug("File Name is : ", fileName);
		String basePath = getBasePath();
		OutputStream out;
		try {
			File f = null;
			f = new File(basePath+fileName);
			boolean createNewFile = f.createNewFile();
			 if(createNewFile==false) {
			System.out.println("Error while creating new file:"+f.getPath());	
		      }
			if (f.canWrite()) {
				out = new FileOutputStream(f);
				CSVFileWriter p = new CSVFileWriter(out);
				List<String[]> dataList = new LinkedList<String[]>();
				String records[] =null;
				if(fileName.contains("TD194")) {
					records = new String[8];
					records[0]="Deposit Number";
					records[1]="Date of Deposit";
					records[2]="Date of Maturity";
					records[3]="Interest Rate";
					records[4]="Status";
					records[5]="Reason";
					records[6]="File Name";
					records[7]="Upload Time";
				}else if(fileName.contains("UBS-LIMIT")) {
					records = new String[8];
					records[0]="Serial Number";
					records[1]="Line No.";
					records[2]="Customer Id";
					records[3]="Currency";
					records[4]="Status";
					records[5]="Reason";
					records[6]="File Name";
					records[7]="Upload Time";
				}
				dataList.add(records);
				p.println(dataList.get(0));
				for (int i = 0; i < reportDataList.size(); i++) {
					p.println(reportDataList.get(i));
				}
			}
			else {
				DefaultLogger.debug("writeCSVFile", " Could not open FileName " + fileName);
			}
		}
		catch (IOException e) {
			DefaultLogger.debug("writeCSVFile", "" + e.getMessage());
		}
	}
	
	public void writeCSVFile(String fileName, List<String[]> reportDataList, Map<String, Object> parameters,
			String reportType) throws SearchDAOException {
		DefaultLogger.debug("File Name is : ", fileName);
		try {
			File f = null;
			CSVWriter out = null;
			FileWriter fw = null;
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString(IErosionFileConstants.FTP_EROSION_UPLOAD_LOCAL_DIR);
			f = new File(serverFilePath + fileName);
			File dirFile = new File(serverFilePath);
			if (!dirFile.exists())
				dirFile.mkdirs();
			boolean createNewFile = f.createNewFile();
			if (createNewFile == false) {
				System.out.println("Error while creating new file:" + f.getPath());
			}
			if (f.canWrite()) {
				String[] columnsName = null;
				fw = new FileWriter(f.getAbsoluteFile(), true);
				out = new CSVWriter(fw, '~', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
						CSVWriter.DEFAULT_LINE_END);
				if (reportType.contains("EROSION")) {
					columnsName = (String[]) parameters.get("columnsMap");
				}
				out.writeNext(columnsName);
				for (int i = 0; i < reportDataList.size(); i++) {
					out.writeNext(reportDataList.get(i));
				}
				out.close();
				fw.close();
			} else {
				DefaultLogger.debug("writeCSVFile", " Could not open FileName " + fileName);
			}
		} catch (IOException e) {
			DefaultLogger.debug("writeCSVFile", "" + e.getMessage());
		}
	}
	
	
}
