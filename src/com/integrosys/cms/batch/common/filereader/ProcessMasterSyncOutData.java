package com.integrosys.cms.batch.common.filereader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eod.bus.IEODSyncOutMasterDao;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.eod.sync.bus.OBEODSyncStatus;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.ReportColumn;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.ReportParamters;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.SyncMasterTemplateOut;
import com.integrosys.cms.batch.eod.IEodSyncConstants;



/**
 * This class process the dat file.
 * 
 * @author anil.pandey
 */
public class ProcessMasterSyncOutData implements IEodSyncConstants{
	private IEODSyncOutMasterDao eodSyncOutMaster;
	
	/**
	 * @return the eodSyncOutMaster
	 */
	public IEODSyncOutMasterDao getEodSyncOutMaster() {
		return eodSyncOutMaster;
	}
	/**
	 * @param eodSyncOutMaster the eodSyncOutMaster to set
	 */
	public void setEodSyncOutMaster(IEODSyncOutMasterDao eodSyncOutMaster) {
		this.eodSyncOutMaster = eodSyncOutMaster;
	}
	
	
	public OBEODSyncStatus generateMasterFile(OBEODSyncStatus syncStatus) {
		String masterName= syncStatus.getProcessKey();
		

		EodSyncTemplateParser parser = new EodSyncTemplateParser();
		
		 
		SyncMasterTemplateOut masterTemplateObj = parser.getMasterTemplateObj(masterName);
		if(masterTemplateObj==null)
			throw new EODSyncStatusException("Master Template object not found.");
		
		List<Object[]> reportQueryResult = getEodSyncOutMaster().getReportQueryResult(masterTemplateObj,new OBFilter());
			
		Map<String,Object> parameters = generateParamatersMap(masterTemplateObj);
		List<String[]> reportDataList = getEodSyncOutMaster().getReportDataList(reportQueryResult,parameters);
		syncStatus.setTotalCount(reportDataList.size());
		if(reportDataList!=null && ! reportDataList.isEmpty()){
			if(validateTemplateObject(reportQueryResult, masterTemplateObj)){
				String fileName = masterTemplateObj.getReportParamters().getReportName();
				fileName=fileName+"_"+CommonUtil.getCurrentDateTimeStamp()+".dat";
				syncStatus.setFileName(fileName);
				String reportFileType="delimiterText";
				new BaseReport().exportReportByWriter(parameters, fileName,reportDataList, reportFileType);
			}
		}
		getEodSyncOutMaster().updateEodSyncStatus(masterName,reportDataList);
		
		return syncStatus;
	}
	
	private Map<String,Object> generateParamatersMap(SyncMasterTemplateOut syncMasterTemplateOut) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		ReportParamters reportParamters = syncMasterTemplateOut
				.getReportParamters();
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
		parameters.put(KEY_DELIMETER, syncMasterTemplateOut.getDelimiter());
		parameters.put(KEY_SEC_DELIMETER, syncMasterTemplateOut.getSecondaryDelimiter());
		parameters.put(KEY_PRINTHEADER_COLUMNNAME, "Y");
		parameters.put(KEY_IS_EOD_REPORT, "Y");
		

		return parameters;
	}
	
	private boolean validateTemplateObject(List<Object[]> reportQueryResult,
			SyncMasterTemplateOut masterTemplateObj) {
		
		//-------------Validating -------------Query--------------------------------------
		if(masterTemplateObj.getQuery()==null||"".equals(masterTemplateObj.getQuery().trim()))
			throw new EODSyncStatusException("Query tag is mandatory.");
		
		//-------------Validating -------------Where Clause------------------------------------------
		//TODO: will be changed after where clause finalization.
		if(masterTemplateObj.getWhereClause()==null)
			throw new EODSyncStatusException("WhereClause tag is mandatory.");
		
		//-------------Validating -------------Report Paramaters--------------------------------------
		if(masterTemplateObj.getReportParamters()==null)
			throw new EODSyncStatusException("ReportParamters tag is mandatory.");
		//-------------Validating -------------Report Paramaters---Report columns---------------------
		if(masterTemplateObj.getReportParamters().getReportColumns()==null)
			throw new EODSyncStatusException("ReportColumns tag is mandatory.");
		if(masterTemplateObj.getReportParamters().getReportName()==null)
			throw new EODSyncStatusException("ReportName tag is mandatory.Please specify predefined file name");
		
		//-------------Validating -------------Report Paramaters---Report columns------Report column--
		ReportColumn[] reportColumn = masterTemplateObj.getReportParamters().getReportColumns().getReportColumn();
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
