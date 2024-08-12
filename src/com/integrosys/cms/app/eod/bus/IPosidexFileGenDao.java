package com.integrosys.cms.app.eod.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.batch.common.posidex.templateparser.PosidexTemplateOut;

public interface IPosidexFileGenDao {
	public List<Object[]> getReportQueryResult( PosidexTemplateOut posidexTemplateOut,OBFilter filter);
	public List<String[]> getReportDataList(List<Object[]> reportQueryResult,Map parameters);
}
