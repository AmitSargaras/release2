package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;

/**
 * <p>
 * To populate extra info into Limits which is not getting varied in the LOS
 * system.
 * <p>
 * Currently, LOS AA Number is populated into Limits that dont are not varied.
 * <p>
 * Only, variation and normal publish should use this handler.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitNonVariedInfoPopulatorHandler extends AbstractCommonActualTrxHandler {

	private ILimitDao limitDao;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessage = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessage.getLimitProfile();

		boolean variation = (limitProfile.getCMSLimitProfileId() != null)
				&& (limitProfile.getCMSLimitProfileId().longValue() > 0);

		if (variation) {
			Set msgCmsLimitIdList = new HashSet();
			Set msgLosLimitIdList = new HashSet();

			if (aaMessage.getLimits() != null) {
				for (Iterator itr = aaMessage.getLimits().iterator(); itr.hasNext();) {
					Limits limit = (Limits) itr.next();
					if (limit.getLimitGeneral().getCMSLimitId() > 0) {
						msgCmsLimitIdList.add(new Long(limit.getLimitGeneral().getCMSLimitId()));
					}
					else {
						msgLosLimitIdList.add(limit.getLimitGeneral().getLOSLimitId());
					}
				}
			}

			// setting LOS AA number to the limits not getting varied
			List storedLimitGenerals = getLimitDao().retrieveLimitsOnlyByCmsLimitProfileId(
					limitProfile.getCMSLimitProfileId());
			for (Iterator itr = storedLimitGenerals.iterator(); itr.hasNext();) {
				LimitGeneral limitGeneral = (LimitGeneral) itr.next();

				if (!msgCmsLimitIdList.contains(new Long(limitGeneral.getCmsId()))
						&& !msgLosLimitIdList.contains(limitGeneral.getLimitId())) {
					limitGeneral.setLOSAANumber(limitProfile.getLOSAANumber());
					getLimitDao().update(limitGeneral, LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

					Map parameters = new HashMap();
					parameters.put("referenceID", new Long(limitGeneral.getCmsId()));
					parameters.put("transactionType", ICMSConstant.INSTANCE_LIMIT);
					CMSTransaction trx = (CMSTransaction) getLimitDao().retrieveObjectByParameters(parameters,
							CMSTransaction.class);

					if (trx != null && trx.getStageReferenceID() > 0) {
						long stagingReferenceId = trx.getStageReferenceID();
						LimitGeneral stagingLimit = (LimitGeneral) getLimitDao().retrieve(new Long(stagingReferenceId),
								IEaiConstant.STAGE_LIMIT_GENERAL);
						stagingLimit.setLOSAANumber(limitProfile.getLOSAANumber());
						getLimitDao().update(stagingLimit, LimitGeneral.class, IEaiConstant.STAGE_LIMIT_GENERAL);
					}
				}
			}
		}

		return msg;
	}
}
