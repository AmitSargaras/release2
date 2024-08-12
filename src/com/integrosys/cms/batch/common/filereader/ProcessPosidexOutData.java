package com.integrosys.cms.batch.common.filereader;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eod.bus.IPosidexFileGenDao;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.batch.common.posidex.templateparser.PosidexTemplateOut;
import com.integrosys.cms.batch.common.posidex.templateparser.ReportColumn;
import com.integrosys.cms.batch.common.posidex.templateparser.ReportParamters;
import com.integrosys.cms.batch.eod.IPosidexFileGenConstants;



/**
 * This class process the dat file.
 * 
 * @author anil.pandey
 */
public class ProcessPosidexOutData implements IPosidexFileGenConstants{
	
	
	private IPosidexFileGenDao posidexFileGen;
    private IGeneralParamDao generalParam;
    
    public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}

	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
	}
	
	public IPosidexFileGenDao getPosidexFileGen() {
		return posidexFileGen;
	}

	public void setPosidexFileGen(IPosidexFileGenDao posidexFileGen) {
		this.posidexFileGen = posidexFileGen;
	}
	
	public void generatePosidexFile() throws Exception {

		PosidexTemplateParser parser = new PosidexTemplateParser();
		
		PosidexTemplateOut posidexTemplateObj = parser.getTemplateObj("POSIDEX");
		if(posidexTemplateObj==null)
			throw new Exception("Posidex Template object not found.");
		
		List<Object[]> reportQueryResult = getPosidexFileGen().getReportQueryResult(posidexTemplateObj,new OBFilter());
			
		Map<String,Object> parameters = generateParamatersMap(posidexTemplateObj);
		List<String[]> reportDataList = getPosidexFileGen().getReportDataList(reportQueryResult,parameters);
//		syncStatus.setTotalCount(reportDataList.size());
		if(reportDataList!=null && ! reportDataList.isEmpty()){
			if(validateTemplateObject(reportQueryResult, posidexTemplateObj)){
				String fileName = posidexTemplateObj.getReportParamters().getReportName();
				IGeneralParamEntry generalParamEntry = getGeneralParam().getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				Calendar currentDate = Calendar.getInstance();
				currentDate.setTime(new Date(generalParamEntry.getParamValue()));
				fileName=fileName+"_"+CommonUtil.getCurrentDateForPosidex(currentDate)+".txt";
//				syncStatus.setFileName(fileName);
				String reportFileType="delimiterText";
				new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			}
		}
//		getEodSyncOutMaster().updateEodSyncStatus(masterName,reportDataList);
		
//		return syncStatus;
	}
	
	private Map<String,Object> generateParamatersMap(PosidexTemplateOut posidexTemplateOut) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		ReportParamters reportParamters = posidexTemplateOut.getReportParamters();
		ReportColumn[] reportColumns = reportParamters.getReportColumns().getReportColumn();
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

		parameters.put(KEY_COL_LABEL, columnLabels);
		parameters.put(KEY_COL_WIDTH, columnWidths);
		parameters.put(KEY_COL_FORMAT, columnType);
		parameters.put(KEY_REPORT_NAME, reportParamters.getReportName());
		parameters.put(KEY_LOGO, PropertyManager.getValue(HDFC_LOGO));
		parameters.put(KEY_DELIMETER, posidexTemplateOut.getDelimiter());
		parameters.put(KEY_SEC_DELIMETER, posidexTemplateOut.getSecondaryDelimiter());
		parameters.put(KEY_PRINTHEADER_COLUMNNAME, "Y");
		parameters.put(KEY_IS_POSIDEX_FILE, "Y");
		parameters.put(KEY_ADDITIONAL_HEADER, reportParamters.getAdditionalHeader());
		parameters.put(KEY_ADDITIONAL_TRAILER, reportParamters.getAdditionalTrailer());
		

		return parameters;
	}
	
	private boolean validateTemplateObject(List<Object[]> reportQueryResult,
			PosidexTemplateOut posidexTemplateOut) {
		
		//-------------Validating -------------Query--------------------------------------
		if(posidexTemplateOut.getQuery()==null||"".equals(posidexTemplateOut.getQuery().trim()))
			throw new EODSyncStatusException("Query tag is mandatory.");
		
		//-------------Validating -------------Where Clause------------------------------------------
		//TODO: will be changed after where clause finalization.
		if(posidexTemplateOut.getWhereClause()==null)
			throw new EODSyncStatusException("WhereClause tag is mandatory.");
		
		//-------------Validating -------------Report Paramaters--------------------------------------
		if(posidexTemplateOut.getReportParamters()==null)
			throw new EODSyncStatusException("ReportParamters tag is mandatory.");
		//-------------Validating -------------Report Paramaters---Report columns---------------------
		if(posidexTemplateOut.getReportParamters().getReportColumns()==null)
			throw new EODSyncStatusException("ReportColumns tag is mandatory.");
		if(posidexTemplateOut.getReportParamters().getReportName()==null)
			throw new EODSyncStatusException("ReportName tag is mandatory.Please specify predefined file name");
		
		//-------------Validating -------------Report Paramaters---Report columns------Report column--
		ReportColumn[] reportColumn = posidexTemplateOut.getReportParamters().getReportColumns().getReportColumn();
		if(reportColumn!=null && reportColumn.length>0){
		//Check for Query columns Vs Configured template columns
		if (reportQueryResult.size() > 0) {
			Object[] objects=reportQueryResult.get(0);
			if(objects.length!=reportColumn.length){
//				System.out.println("Through Exception.....");
				throw new EODSyncStatusException("Query columns dosen't match with configured columns");
			}
		}
		// Check for mandatory parameters of report column
		for (int i = 0; i < reportColumn.length; i++) {
			ReportColumn column = reportColumn[i];
			if(column.getHeader()==null ||"".equals(column.getHeader().trim())){
//				System.out.println("Report header for each column is mandatory.");
				throw new EODSyncStatusException("Report header for each column is mandatory.");
			}
			if(column.getFormat()==null ||"".equals(column.getFormat().trim())){
//				System.out.println("Report format for each column is mandatory.");
				throw new EODSyncStatusException("Report format for each column is mandatory.");
			}
			if(column.getWidth()==null ||"".equals(column.getWidth().toString().trim())){
//				System.out.println("Width is mandatory for each report column.");
				throw new EODSyncStatusException("Width is mandatory for each report column.");
				
			}
		}

			return true;
		}else
			throw new EODSyncStatusException("Report columns are not configured.");
	}

	
}
