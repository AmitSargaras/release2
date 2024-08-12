package com.integrosys.cms.app.poi.report;

import java.util.List;

import com.integrosys.cms.app.poi.report.xml.schema.Reports;

public interface IReportDao {
	public List<Object[]> getReportQueryResult( Reports reports,OBFilter filter);

	public List<Object[]> getMortgageValuationsReportQueryResult(Reports reports, OBFilter filter);

	public List<Object[]> getReportQueryResultFCC(Reports reports, OBFilter filter);
	
	public List<Object[]> getReportQueryResultEventOrCriteria(Reports reports, OBFilter filter);
}
