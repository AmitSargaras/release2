package com.integrosys.cms.app.poi.report;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.poi.report.xml.schema.Reports;
import com.integrosys.cms.batch.reports.ReportException;

public interface IReportService {
	public String generateReport(String reportId, String fileName,OBFilter filter) throws ReportException;

	public int getTotalNoOfRowsCount(String reportId, OBFilter filter) throws ReportException;

	public void generateReport2(String reportId, String fileName,String fileName2,String fileName3,OBFilter filter) throws ReportException;

	public int getTotalNoOfRowsCountMortgage(String reportId, OBFilter filter)  throws ReportException;
	
	public Map generateParamatersMap(Reports reports) throws ReportException;
	
	public List<String[]> getReportDataList(List<Object[]> reportQueryResult, Map parameters,String reportFileType);

}
