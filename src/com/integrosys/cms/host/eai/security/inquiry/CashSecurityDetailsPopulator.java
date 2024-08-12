package com.integrosys.cms.host.eai.security.inquiry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashDeposit;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;

public class CashSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		ApprovedSecurity sec = securityMsgBody.getSecurityDetail();

		CashSecurity cash = (CashSecurity) securitySubtypeObject;
		cash.setLOSSecurityId(sec.getLOSSecurityId());
		securityMsgBody.setCashDetail(cash);

		Map parameters = new HashMap();
		parameters.put("collateralId", new Long(sec.getCMSSecurityId()));

		List depositList = getSecurityDao().retrieveObjectsListByParameters(parameters, CashDeposit.class);
		if (depositList != null && !depositList.isEmpty()) {
			for (Iterator itr = depositList.iterator(); itr.hasNext();) {
				CashDeposit deposit = (CashDeposit) itr.next();
				deposit.setSecurityId(sec.getLOSSecurityId());
			}

			Vector depositDetails = new Vector(depositList);
			securityMsgBody.setDepositDetail(depositDetails);
		}

	}

	protected Class getSecuritySubtypeClass() {
		return CashSecurity.class;
	}

}
