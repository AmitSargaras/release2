package com.integrosys.cms.ui.collateral;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.ui.collateral.bus.IInsuranceHistoryReport;

public interface IInsuranceBO {

	public List<IInsuranceHistoryReport> getFullInsuranceHistory(InsuranceHistorySearchCriteria criteria);
	public String createInsuranceHistoryReport(InsuranceHistorySearchCriteria criteria, Map<String, String> basicDataMap, Map insuranceCompanyNameMap);
	
}
