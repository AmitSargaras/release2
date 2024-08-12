package com.integrosys.cms.batch.ecbf;

import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;

public class ECommodityBasedFinancingBoImpl implements IECommodityBasedFinancingBo {

	private IECommodityBasedFinancingJdbc ecbfJdbc = (IECommodityBasedFinancingJdbc) BeanHouse.get("ecbfJdbc");
			
	@Override
	public List<OBEcbfDeferralReport> getEcbfDeferralReportData() {
		return ecbfJdbc.getEcbfDeferralReportData();
	}

}
