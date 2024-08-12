package com.integrosys.cms.batch.strc;

import java.util.List;

import com.integrosys.cms.batch.ecbf.OBEcbfDeferralReport;

public interface IECommodityBasedFinancingBo {

	public List<OBStrcDeferralReport> getEcbfDeferralReportData();
}
