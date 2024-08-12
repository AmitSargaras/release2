package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;

/**
 * <p>
 * To update charge info usage indicator based on the first limit security
 * linkage of the same security.
 * 
 * <p>
 * Currently, only pledge amount usage indicator is populated, draw amount
 * follow the same of pledge amount usage.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitSecMapChargeInfoActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ISecurityDao securityDao;

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();
		Vector limitsApprovedSecurityMaps = aaMsgBody.getLimitsApprovedSecurityMap();

		/* key is the cms security id, value is the pledge amount usage */
		Map securityIdPledgeAmountUsageMap = new HashMap();

		if (limitsApprovedSecurityMaps != null && !limitsApprovedSecurityMaps.isEmpty()) {
			for (Iterator itr = limitsApprovedSecurityMaps.iterator(); itr.hasNext();) {
				LimitsApprovedSecurityMap limitsApprovedSecMap = (LimitsApprovedSecurityMap) itr.next();

				ApprovedSecurity security = (ApprovedSecurity) this.securityDao.retrieve(new Long(limitsApprovedSecMap
						.getCmsSecurityId()), ApprovedSecurity.class);

				if (security != null
						&& securityIdPledgeAmountUsageMap.get(new Long(security.getCMSSecurityId())) == null
						&& limitsApprovedSecMap.getChangeIndicator().equals(
								String.valueOf(IEaiConstant.CHANGEINDICATOR))
						&& limitsApprovedSecMap.getUpdateStatusIndicator().equals(
								String.valueOf(IEaiConstant.CREATEINDICATOR))) {
					// for pledge $ or %
					if (limitsApprovedSecMap.getAmtPledge() != null
							&& limitsApprovedSecMap.getAmtPledge().doubleValue() > 0) {
						security.setChargeInfoPledgeAmountUsageIndicator(String
								.valueOf(ICollateral.CHARGE_INFO_AMOUNT_USAGE));

						securityIdPledgeAmountUsageMap.put(new Long(security.getCMSSecurityId()),
								ICollateral.CHARGE_INFO_AMOUNT_USAGE);
					}
					else if (limitsApprovedSecMap.getPercentPledge() != null
							&& limitsApprovedSecMap.getPercentPledge().doubleValue() > 0) {
						security.setChargeInfoPledgeAmountUsageIndicator(String
								.valueOf(ICollateral.CHARGE_INFO_PERCENTAGE_USAGE));

						securityIdPledgeAmountUsageMap.put(new Long(security.getCMSSecurityId()),
								ICollateral.CHARGE_INFO_PERCENTAGE_USAGE);
					}

					// for draw $ or %
					if (limitsApprovedSecMap.getAmtDraw() != null
							&& limitsApprovedSecMap.getAmtDraw().doubleValue() > 0) {
						security.setChargeInfoDrawAmountUsageIndicator(String
								.valueOf(ICollateral.CHARGE_INFO_AMOUNT_USAGE));

						securityIdPledgeAmountUsageMap.put(new Long(security.getCMSSecurityId()),
								ICollateral.CHARGE_INFO_AMOUNT_USAGE);
					}
					else if (limitsApprovedSecMap.getPercentDraw() != null
							&& limitsApprovedSecMap.getPercentDraw().doubleValue() > 0) {
						security.setChargeInfoDrawAmountUsageIndicator(String
								.valueOf(ICollateral.CHARGE_INFO_PERCENTAGE_USAGE));

						securityIdPledgeAmountUsageMap.put(new Long(security.getCMSSecurityId()),
								ICollateral.CHARGE_INFO_PERCENTAGE_USAGE);
					}

					this.securityDao.update(security, ApprovedSecurity.class);

					Map parameters = new WeakHashMap();
					parameters.put("referenceID", new Long(security.getCMSSecurityId()));
					parameters.put("transactionType", ICMSConstant.INSTANCE_COLLATERAL);

					CMSTransaction securityTransaction = (CMSTransaction) this.securityDao.retrieveObjectByParameters(
							parameters, CMSTransaction.class);

					StageApprovedSecurity stageSecurity = (StageApprovedSecurity) this.securityDao.retrieve(new Long(
							securityTransaction.getStageReferenceID()), StageApprovedSecurity.class);
					stageSecurity.setChargeInfoDrawAmountUsageIndicator(security
							.getChargeInfoDrawAmountUsageIndicator());
					stageSecurity.setChargeInfoPledgeAmountUsageIndicator(security
							.getChargeInfoPledgeAmountUsageIndicator());

					this.securityDao.update(stageSecurity, StageApprovedSecurity.class);
				}
			}
		}
		securityIdPledgeAmountUsageMap.clear();

		return msg;
	}
}
