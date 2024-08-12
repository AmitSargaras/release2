package com.integrosys.cms.batch.strc;

import java.util.List;

import com.integrosys.cms.batch.ecbf.OBEcbfDeferralReport;

/*e-CBF -> Electronic Commodity Based Financing*/
public interface IECommodityBasedFinancingJdbc {

	public List<OBStrcDeferralReport> getEcbfDeferralReportData();
	public List<OBStrcDeferralReport> ecbfDeferralReportDataMapper(String sql) ;
}
