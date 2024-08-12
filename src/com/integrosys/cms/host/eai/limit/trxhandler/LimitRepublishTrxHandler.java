package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;

/**
 * Workflow Handler for republish type of limit message.
 * 
 * @author marvin
 * @author Chong Jun Yong
 */
public class LimitRepublishTrxHandler extends LimitTrxHandler {

	private static final long serialVersionUID = -2811339829222723609L;

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		LimitHelper hp = new LimitHelper();
		HashMap limitMap = new HashMap();
		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessageBody.getLimitProfile();
		Vector limitsVec = aaMessageBody.getLimits();

		if (limitsVec == null) {
			limitsVec = new Vector();
		}

		Map parameters = new HashMap();
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			parameters.put("cmsLimitProfileId", limitProfile.getCMSLimitProfileId());
		}
		else if (limitProfile.getLimitProfileId() > 0) {
			parameters.put("cmsLimitProfileId", new Long(limitProfile.getLimitProfileId()));
		}

		if (!parameters.isEmpty()) {
			List nonDeletedLimitList = getCustomerDao().retrieveNonDeletedObjectsListByParameters(parameters,
					"updateStatusIndicator", IEaiConstant.ACTUAL_LIMIT_GENERAL);

			for (Iterator itr = nonDeletedLimitList.iterator(); itr.hasNext();) {
				ILimitTrxValue ltv = new OBLimitTrxValue();
				LimitGeneral lmts = (LimitGeneral) itr.next();

				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				try {
					ltv = limitProxy.getTrxLimit(lmts.getCmsId());
				}
				catch (LimitException ex) {
					throw new EAITransactionException("failed to retrieve limit transaction for limit, key ["
							+ lmts.getCmsId() + "]", ex);
				}

				for (Iterator iter = limitsVec.iterator(); iter.hasNext();) {
					Limits temp = (Limits) iter.next();

					// changed the oldLimitid to cmslimitid
					// for the variation cases
					if (temp.getLimitGeneral().getCMSLimitId() > 0) {
						if (temp.getLimitGeneral().getCMSLimitId() == lmts.getCmsId()) {
							lmts.setLOSLimitId(temp.getLimitGeneral().getLOSLimitId());
						}
					}
				}

				limitMap.put(hp.constructLimitKey(lmts.getCIFId(), lmts.getSubProfileId(), lmts.getCmsLimitProfileId(),
						lmts.getLOSLimitId()), ltv);
			}
		}

		limitMap = setEmptyTrxValue(msg, limitMap);

		flatMessage.put(getTrxKey(), limitMap);
		return flatMessage;
	}

	/**
	 * This method will set Empty TrxValue Object into the Limit HashMap that
	 * exist in the Message object. This is to facilitate Create Transaction for
	 * New Limits. fix Problem : Without a trxValue in the HashMap , no
	 * transaction process will occur for a Limit. may have to enchance in
	 * future for multiple limits
	 * 
	 */
	private HashMap setEmptyTrxValue(Message msg, HashMap limitMap) {
		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		Vector vecLimit = aaMessageBody.getLimits();
		LimitHelper hp = new LimitHelper();

		if (!hp.anyLimit(vecLimit)) {
			vecLimit = new Vector();
		}

		for (Iterator iter = vecLimit.iterator(); iter.hasNext();) {
			ILimitTrxValue ltv = new OBLimitTrxValue();
			Limits lmts = (Limits) iter.next();

			if ((lmts.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& (lmts.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {
				String key = hp.constructLimitKey(lmts.getLimitGeneral().getCIFId(), lmts.getLimitGeneral()
						.getSubProfileId(), lmts.getLimitGeneral().getCmsLimitProfileId(), lmts.getLimitGeneral()
						.getLOSLimitId());

				if (limitMap.get(key) == null) {
					limitMap.put(key, ltv);
				}
			}

		}

		return limitMap;
	}

}
