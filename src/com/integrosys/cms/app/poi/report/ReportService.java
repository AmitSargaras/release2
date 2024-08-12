package com.integrosys.cms.app.poi.report;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.context.BeanHouse;

//import org.apache.commons.collections.map.ListOrderedMap;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.app.poi.report.writer.FileFormat;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.app.poi.report.xml.schema.Report;
import com.integrosys.cms.app.poi.report.xml.schema.ReportColumn;
import com.integrosys.cms.app.poi.report.xml.schema.ReportParamters;
import com.integrosys.cms.app.poi.report.xml.schema.Reports;
import com.integrosys.cms.batch.reports.ReportException;

public class ReportService implements IReportConstants,IReportService {
	
	private IReportDao reportDao;
	
	public IReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	
	int textFormat = FileFormat.COL_TYPE_STRING;

	int amountFormat = FileFormat.COL_TYPE_AMOUNT_FLOAT;
	
	public static String KEY_SECURITY_TYPE = "securityType";
	public static String KEY_BANKING_METHOD = "bankingMethod";
	public static String KEY_FROM_DATE = "fromDate";
	public static String KEY_TO_DATE = "toDate";
	public static String KEY_FROM_DATE_REPORT = "fromDate1";
	public static String KEY_TO_DATE_REPORT = "toDate1";

	public String generateReport(String reportId, String fileName,OBFilter filter) throws ReportException{
		System.out.println("Inside generateReport with single file");
		String reportFileType = "";
		//reportformat if CersaiCompatible then delimiterText else xls
		if(filter==null && reportId.equals("RPT0102")) {
			reportFileType = "xlsx";
		}
		else if(filter==null && reportId.equals("RPT0094")) {
			reportFileType = "xls";
		}
		else {
		 if(filter.getReportId().equals("RPT0067")) {
			if(filter.getReportFormat().equals("CersaiCompatible")) {
			reportFileType = "delimiterText";
			}else {
				reportFileType = "xls";
			}
		}else if(filter.getReportId().equals("RPT0066")) {
			reportFileType = "delimiterText";
		}
		else {
			reportFileType = "xls";
		}
		}
		
		DefaultLogger.debug(this,"=============In generateReport()=============");
		ReportParser parser = new ReportParser();
		Reports reports = parser.getReportObject(reportId,filter);

		if(reports.getReport()==null)
			throw new ReportException("Report object not found.");
		
		if(filter==null)
			filter= new OBFilter();
		
//		ReportDaoImpl dao = new ReportDaoImpl();
		List<Object[]> reportQueryResult = Collections.emptyList();
		DefaultLogger.debug(this,"=============Before  getReportQueryResult()=============");
//		List<Object[]> reportQueryResult = getReportDao().getReportQueryResult(reports,filter);
		if(filter!= null && (reportId.equals("RPT0094") || reportId.equals("RPT0102")))
		{
			/*ReportDaoImpl rn = new ReportDaoImpl();*/
			
			IReportDao rn = (IReportDao) BeanHouse.get("poiReportDao");
		System.out.println("Value of Report and Filter:: "+reports+","+filter);
		reportQueryResult = rn.getReportQueryResult(reports,filter);	
			
		}else {
		if(filter.getReportId().equals("RPT0078") || filter.getReportId().equals("RPT0079") || filter.getReportId().equals("RPT0080")) {
			reportQueryResult = getReportDao().getReportQueryResultFCC(reports,filter);
		}
		else if(filter.getReportId().equals("RPT0085")) {
			reportQueryResult = getReportDao().getReportQueryResultEventOrCriteria(reports,filter);
		}
		else {
		reportQueryResult = getReportDao().getReportQueryResult(reports,filter);
		}
		/*if(filter.getReportId().equals("RPT0073")) {
			reportQueryResult = getReportDao().getMortgageValuationsReportQueryResult(reports, filter);
		}*/
		/*if(filter.getReportId().equals("RPT0073")) {
			reportQueryResult = getReportDao().getMortgageValuationsReportQueryResult(reports, filter);
		}*/
		}
		
		DefaultLogger.debug(this,"=============After  getReportQueryResult()=============");
		
		
		
		
		if (validateReportObject(reportQueryResult, reports)){
			DefaultLogger.debug(this,"Report template validation done.Proceeding for report genration");
			
//			Map parameters = generateParamatersMap(reports);
			DefaultLogger.debug(this,"=============Before  getReportDataList()============="+reportQueryResult.size());
//			List<String[]> reportDataList = getReportDataList(reportQueryResult,parameters);

			Map parameters =null;

			if(filter!= null && (reportId.equals("RPT0094") || reportId.equals("RPT0102")))
			{
				parameters = generateParamatersMap(reports);
			}
			else {
			if(filter.getReportId().equals("RPT0067")) {
					
				if(filter.getReportFormat().equals("UserReference")) {
					parameters = generateParamatersMap(reports,filter,reportQueryResult);
				}else {
					parameters = generateParamatersMap(reports,filter);
				}
				
			}else if(filter.getReportId().equals("RPT0066")) {
				parameters = generateParamatersMap(reports,filter);
			}
			else {
				parameters = generateParamatersMap(reports);
			}
			}
			//Map parameters = generateParamatersMap(reports,filter);
			List<String[]> reportDataList = getReportDataList(reportQueryResult,parameters, reportFileType);

			DefaultLogger.debug(this,"=============After  getReportDataList()============="+reportDataList.size());
			if("RPT0062_FD".equals(reportId) || "RPT0062_UBS".equals(reportId) ) {
				int count=reportDataList.size();
				fileName= fileName+count+".csv";
				new BaseReport().writeCSVFile(fileName, reportDataList);
			}else {
				int count=reportDataList.size();
				fileName= fileName+count+".xls";
				new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			}
			
			DefaultLogger.debug(this,"------------After BaseReport().exportReportByWriter----------------in generateReport of ReportService.java File");
		}

		return fileName;
	}

	
	public int getTotalNoOfRowsCount(String reportId, OBFilter filter) throws ReportException{
		
		String reportFileType = "xls";
		ReportParser parser = new ReportParser();
		Reports reports = parser.getReportObject(reportId,filter);

		if(reports.getReport()==null)
			throw new ReportException("Report object not found.");
		
		if(filter.getReportId().equals("RPT0078") || filter.getReportId().equals("RPT0079") || filter.getReportId().equals("RPT0080")) {
			return getReportDao().getReportQueryResultFCC(reports,filter).size();
		}
		else if(filter.getReportId().equals("RPT0085")) {
			return getReportDao().getReportQueryResultEventOrCriteria(reports,filter).size();
		}
		else {
			return getReportDao().getReportQueryResult(reports,filter).size();
		}
//		return getReportDao().getReportQueryResult(reports,filter).size();
	}

	
	public int getTotalNoOfRowsCountMortgage(String reportId, OBFilter filter) throws ReportException{
		
		String reportFileType = "xls";
		ReportParser parser = new ReportParser();
		Reports reports = parser.getReportObject(reportId,filter);

		if(reports.getReport()==null)
			throw new ReportException("Report object not found in getTotalNoOfRowsCountMortgage.");
		
		return getReportDao().getMortgageValuationsReportQueryResult(reports,filter).size();
	}
	
	
	
	public void generateReport2(String reportId, String fileName,String fileName2,String fileName3,OBFilter filter) throws ReportException{
		String reportFileType = "";
		//reportformat if CersaiCompatible then delimiterText else xls
		if(filter.getReportId().equals("RPT0067")) {
			if(filter.getReportFormat().equals("CersaiCompatible")) {
			reportFileType = "delimiterText";
			}else {
				reportFileType = "xls";
			}
		}else if(filter.getReportId().equals("RPT0066")) {
			reportFileType = "delimiterText";
		}
		else {
			reportFileType = "xls";
		}

		ReportParser parser = new ReportParser();
		Reports reports = parser.getReportObject(reportId,filter);

		if(reports.getReport()==null)
			throw new ReportException("Report object not found.");
		
		if(filter==null)
			filter= new OBFilter();
		
//		ReportDaoImpl dao = new ReportDaoImpl();
		
		
		List<Object[]> reportQueryResult = getReportDao().getReportQueryResult(reports,filter);
		if (validateReportObject(reportQueryResult, reports)){
			DefaultLogger.debug(this,"Report template validation done.Proceeding for report genration");
			Map parameters =null;
			if(filter.getReportId().equals("RPT0067")) {
				
				if(filter.getReportFormat().equals("UserReference")) {
					parameters = generateParamatersMap(reports,filter,reportQueryResult);
				}else {
					parameters = generateParamatersMap(reports,filter);
				}
				
			}else if(filter.getReportId().equals("RPT0066")) {
				parameters = generateParamatersMap(reports,filter);
			}
			else {
				parameters = generateParamatersMap(reports);
			}
			//Map parameters = generateParamatersMap(reports,filter);
			List<String[]> reportDataList = getReportDataList(reportQueryResult,parameters, reportFileType);
			
			if(filter.getReportId().equals("RPT0066")) {
				new BaseReport().exportReportByWriter(parameters, fileName,fileName2,fileName3,reportDataList, reportFileType);
			}
			if(filter.getReportId().equals("RPT0067")) {
				if(reportDataList !=null && !reportDataList.isEmpty()) {
					
					if(reportDataList.size() > 1000) {
						filter.setIndustry("true");
						new BaseReport().exportReportByWriter(parameters, fileName,fileName2,fileName3,reportDataList, reportFileType);
					}else {
						new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
					}
					
				}else {
//					new BaseReport().exportReportByWriter(parameters, fileName,fileName2,fileName3,reportDataList, reportFileType);
					new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
				}
			}
			
			DefaultLogger.debug(this,"------------After BaseReport().exportReportByWriter----------------in generateReport2 of ReportService.java File");
		}
	}
	


	//TODO: This method will be moved in Validator/Exception Handler class
	private boolean validateReportObject(List<Object[]> reportQueryResult,
			Reports reports) {
		Report report=reports.getReport();
		//-------------Validating -------------Query--------------------------------------
		if(report.getQuery()==null||"".equals(report.getQuery().trim()))
			throw new ReportException("Query tag is mandatory.");
		
		//-------------Validating -------------Where Clause------------------------------------------
		//TODO: will be changed after whereclause finalization.
		if(report.getWhereClause()==null)
			throw new ReportException("WhereClause tag is mandatory.");
		
		//-------------Validating -------------Report Paramaters--------------------------------------
		if(report.getReportParamters()==null)
			throw new ReportException("ReportParamters tag is mandatory.");
		//-------------Validating -------------Report Paramaters---Report columns---------------------
		if(report.getReportParamters().getReportColumns()==null)
			throw new ReportException("ReportColumns tag is mandatory.");
		
		//-------------Validating -------------Report Paramaters---Report columns------Report column--
		ReportColumn[] reportColumn = report.getReportParamters().getReportColumns().getReportColumn();
		if(reportColumn!=null && reportColumn.length>0){
		//Check for Query columns Vs Configured template columns
		if (reportQueryResult.size() > 0) {
//			System.out.println("--------1--------"+reportQueryResult.get(0));
//			System.out.println("--------2--------"+reportQueryResult.get(0).getClass());
			Object[] objects=reportQueryResult.get(0);
			if(objects.length!=reportColumn.length){
//				System.out.println("Through Exception.....");
				throw new ReportException("Query columns dosen't match with configured columns");
			}
		}
		// Check for mandatory parameters of report column
		for (int i = 0; i < reportColumn.length; i++) {
			ReportColumn column = reportColumn[i];
			if(column.getHeader()==null ||"".equals(column.getHeader().trim())){
//				System.out.println("Report header for each column is mandatory.");
				throw new ReportException("Report header for each column is mandatory.");
			}
			if(column.getFormat()==null ||"".equals(column.getFormat().trim())){
//				System.out.println("Report format for each column is mandatory.");
				throw new ReportException("Report format for each column is mandatory.");
			}
			if(column.getWidth()==null ||"".equals(column.getWidth().toString().trim())){
//				System.out.println("Width is mandatory for each report column.");
				throw new ReportException("Width is mandatory for each report column.");
				
			}
		}

			return true;
		}else
			throw new ReportException("Report columns are not configured.");
	}

	private Map generateParamatersMap(Reports reports, OBFilter filter,List reportQueryResult) {
		Map parameters = new HashMap();
		ReportParamters reportParamters = reports.getReport().getReportParamters();
		ReportColumn[] reportColumns = reportParamters.getReportColumns().getReportColumn();
		int noOfColumns = reportColumns.length;

		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];
		String[] headerString= new String[4];
		headerString[0]="FH";
		if("IMMOVABLE".equals(filter.getTypeOfSecurity())) {
			headerString[1]="ADSI";
		}else if("MOVABLE".equals(filter.getTypeOfSecurity())) {
			headerString[1]="MVIN";
		}else {
			headerString[1]="SATN";
		}
		headerString[2]=convertToString(reportQueryResult.size());
		headerString[3]=DateFormatUtils.format(new Date(), "dd-MM-yyyy");

		for (int i = 0; i < noOfColumns; i++) {
			ReportColumn column = reportColumns[i];
			columnLabels[i] = headerString[i];
			columnWidths[i] = column.getWidth();

			if ("textFormat".equals(column.getFormat()))
				columnType[i] = textFormat;
			if ("amountFormat".equals(column.getFormat()))
				columnType[i] = amountFormat;
		}
		System.out.println("ReportService.java => generateParamatersMap with 3 parameter => Adding Parameters to map");
		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		parameters.put(KEY_REPORT_NAME, reportParamters.getReportName());
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));
		parameters.put(KEY_SECURITY_TYPE,filter.getTypeOfSecurity());
		parameters.put(KEY_BANKING_METHOD,filter.getBankingMethod());
		parameters.put(KEY_FROM_DATE,filter.getFromDate());
		parameters.put(KEY_TO_DATE,filter.getToDate());
		parameters.put(KEY_FROM_DATE_REPORT,filter.getFromDate());
		parameters.put(KEY_TO_DATE_REPORT,filter.getToDate());
		System.out.println("ReportService.java => generateParamatersMap with 3 parameter => Added Parameters to map");
		
		return parameters;
	}
	
	private Map generateParamatersMap(Reports reports,OBFilter filter) {
		Map parameters = new HashMap();
		ReportParamters reportParamters = reports.getReport()
				.getReportParamters();
		ReportColumn[] reportColumns = reportParamters.getReportColumns()
				.getReportColumn();
		int noOfColumns = reportColumns.length;

		String[] columnLabels = new String[noOfColumns];
		Integer columnWidths[] = new Integer[noOfColumns];
		int columnType[] = new int[noOfColumns];

		for (int i = 0; i < noOfColumns; i++) {
			ReportColumn column = reportColumns[i];
			columnLabels[i] = column.getHeader();
			columnWidths[i] = column.getWidth();

			if ("textFormat".equals(column.getFormat()))
				columnType[i] = textFormat;
			if ("amountFormat".equals(column.getFormat()))
				columnType[i] = amountFormat;

		}
		System.out.println("ReportService.java => generateParamatersMap with 2 parameter => Adding Parameters to map");
		
	parameters.put(KEY_COL_LABEL, columnLabels);
	parameters.put(KEY_COL_WIDTH, columnWidths);
	parameters.put(KEY_COL_FORMAT, columnType);
	parameters.put(KEY_REPORT_NAME, reportParamters.getReportName());
	parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));
	parameters.put(KEY_SECURITY_TYPE,filter.getTypeOfSecurity());
	parameters.put(KEY_BANKING_METHOD,filter.getBankingMethod());
	parameters.put(KEY_FROM_DATE,filter.getFromDate());
	parameters.put(KEY_TO_DATE,filter.getToDate());
	parameters.put(KEY_FROM_DATE_REPORT,filter.getFromDate());
	parameters.put(KEY_TO_DATE_REPORT,filter.getToDate());
	
	System.out.println("ReportService.java => generateParamatersMap with 2 parameter => Added Parameters to map");

	return parameters;
}
		
		public Map generateParamatersMap(Reports reports) {
			Map parameters = new HashMap();
			ReportParamters reportParamters = reports.getReport()
					.getReportParamters();
			ReportColumn[] reportColumns = reportParamters.getReportColumns()
					.getReportColumn();
			int noOfColumns = reportColumns.length;

			String[] columnLabels = new String[noOfColumns];
			Integer columnWidths[] = new Integer[noOfColumns];
			int columnType[] = new int[noOfColumns];

			for (int i = 0; i < noOfColumns; i++) {
				ReportColumn column = reportColumns[i];
				columnLabels[i] = column.getHeader();
				columnWidths[i] = column.getWidth();

				if ("textFormat".equals(column.getFormat()))
					columnType[i] = textFormat;
				if ("amountFormat".equals(column.getFormat()))
					columnType[i] = amountFormat;

			}

			System.out.println("ReportService.java => generateParamatersMap with 1 parameter => Adding Parameters to map");
			
		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		parameters.put(KEY_REPORT_NAME, reportParamters.getReportName());
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));
		
		System.out.println("ReportService.java => generateParamatersMap with 1 parameter => Added Parameters to map");

		return parameters;
	}

	/**
	 * @param parameters
	 * @param reportQueryResult
	 * @param parameters
	 * @return
	 */
	public List<String[]> getReportDataList(List<Object[]> reportQueryResult,
			Map parameters,String reportFileType) {

		int noOfColumns = ((String[]) parameters.get(KEY_COL_LABEL)).length;
		String reportName=(String)parameters.get("reportname");
		int noOfColumns1=0;

		List<String[]> dataList = new LinkedList<String[]>();

		if (reportQueryResult.size() > 0) {
			DefaultLogger.debug(this,"reportQueryResult.size()---------------->"+reportQueryResult.size());
			int no=1;
			int objNo=0;
			String records[] = new String[noOfColumns];
			for (Object[] objects : reportQueryResult) {
				//String records[] = new String[noOfColumns];
				if (objects.length != noOfColumns) {
//					System.out.println("Query columns dosen't match with configured columns");
					throw new ReportException("Query columns dosen't match with configured columns");
				}
				if(reportName.equals("CERSAI Charge Release report") && reportFileType.equals("xls")) {
					objNo=0;
					noOfColumns1=noOfColumns+3;
					records = new String[noOfColumns1];
					for (int i = 0; i < noOfColumns1; i++) {
						if (objects[objNo] != null && !"".equals(objects[objNo])) {
							if(i == 0) {
								records[i] = "RH";
							}else if(i==1){
								records[i] = convertToString(no);
							}else if(i==5){
								records[i] = "01";
							}else {
								records[i] = convertToString(objects[objNo]);
								objNo++;
							}
						} else {
							if(i == 0) {
								records[i] = "RH";
							}else if(i==1){
								records[i] = convertToString(no);
							}else if(i==5){
								records[i] = "01";
							}else {
								records[i] = "-";
								objNo++;
							}
							//records[i] = "-";
						}
					}
					dataList.add(records);
					no++;
				}else {
					records = new String[noOfColumns];
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
//				dataList.add(records);
//				no++;
			}
		}

		return dataList;
	}

	// TODO: Need to move in util with proper implementation
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

	
}
