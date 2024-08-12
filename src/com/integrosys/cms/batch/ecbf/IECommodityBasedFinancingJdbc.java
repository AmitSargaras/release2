package com.integrosys.cms.batch.ecbf;

import java.util.List;

import com.integrosys.cms.batch.ecbf.OBEcbfDeferralReport;

/*e-CBF -> Electronic Commodity Based Financing*/
public interface IECommodityBasedFinancingJdbc {

	public List<OBEcbfDeferralReport> getEcbfDeferralReportData();
	public List<OBEcbfDeferralReport> ecbfDeferralReportDataMapper(String sql) ;
}
