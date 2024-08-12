package com.integrosys.cms.batch.strc;

import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;

public class ECommodityBasedFinancingBoImpl implements IECommodityBasedFinancingBo {

	private IECommodityBasedFinancingJdbc strcJdbc = (IECommodityBasedFinancingJdbc) BeanHouse.get("strcJdbc");
			
	@Override
	public List<OBStrcDeferralReport> getEcbfDeferralReportData() {
		return strcJdbc.getEcbfDeferralReportData();
	}

}
