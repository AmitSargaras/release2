package com.integrosys.cms.host.eai.security.inquiry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.insurance.CreditDefaultSwapsDetail;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;

public class InsuranceSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		InsuranceSecurity insurance = (InsuranceSecurity) securitySubtypeObject;
		insurance.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setInsuranceDetail(insurance);

		if (ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS.equals(securityMsgBody.getSecurityDetail().getSecuritySubType()
				.getStandardCodeValue())) {
			Map parameters = new HashMap();
			parameters.put("collateralId", new Long(securityMsgBody.getSecurityDetail().getCMSSecurityId()));

			List creditDefaultSwapsList = getSecurityDao().retrieveObjectsListByParameters(parameters,
					CreditDefaultSwapsDetail.class);
			if (creditDefaultSwapsList != null && !creditDefaultSwapsList.isEmpty()) {
				for (Iterator itr = creditDefaultSwapsList.iterator(); itr.hasNext();) {
					CreditDefaultSwapsDetail cds = (CreditDefaultSwapsDetail) itr.next();
					cds.setSecurityId(insurance.getLOSSecurityId());
				}

				Vector creditDefaultSwapsDetails = new Vector(creditDefaultSwapsList);
				securityMsgBody.setCreditDefaultSwapsDetail(creditDefaultSwapsDetails);
			}
		}

		populateValuationDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return InsuranceSecurity.class;
	}

}
