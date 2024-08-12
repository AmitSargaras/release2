package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.support.LimitProfileHelper;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class LimitProfileRepublishTrxHandler extends LimitProfileTrxHandler {

	private static final long serialVersionUID = 7281086939442839452L;

	public static final String LEID = "leId";

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		LimitProfile limitProfile = ((AAMessageBody) msg.getMsgBody()).getLimitProfile();
		HashMap hm = new HashMap();
		LimitProfileHelper hp = new LimitProfileHelper();

		try {
			Map parameters = new HashMap();
			parameters.put("CIFId", limitProfile.getCIFId());
			if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
				parameters.put("limitProfileId", limitProfile.getCMSLimitProfileId());
			}
			else {
				parameters.put("LOSAANumber", limitProfile.getLOSAANumber());
			}

			List storedLimitProfileList = getLimitDao().retrieveObjectListByParameters(parameters, LimitProfile.class);

			for (Iterator itr = storedLimitProfileList.iterator(); itr.hasNext();) {
				LimitProfile lp = (LimitProfile) itr.next();

				lp.setUpdateStatusIndicator(limitProfile.getUpdateStatusIndicator());

				ILimitProfileTrxValue lptv = new OBLimitProfileTrxValue();

				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				lptv = limitProxy.getTrxLimitProfile(lp.getLimitProfileId());
				limitProfile.setLimitProfileId(lp.getLimitProfileId());

				String key = hp.constructLimitProfileKey(lp.getCIFId(), lp.getSubProfileId(), lp.getLimitProfileId());

				hm.put(key, lptv);

				// getLimitDao().update(lp, LimitProfile.class);
			}

			if (hm.size() == 0) {
				hm = setEmptyTrxValue(msg, hm);
			}

			flatMessage.put(getTrxKey(), hm);

			return flatMessage;
		}
		catch (LimitException le) {
			throw new EAITransactionException("Caught Exception while calling getting Transaction", le);
		}
	}

	/**
	 * This method will set Empty TrxValue Object into the Limit HashMap that
	 * exist in the Message object. This is to facilitate Create Transaction for
	 * New Limits. fix Problem : Without a trxValue in the HashMap , no
	 * transaction process will occur for a Limit.
	 */
	private HashMap setEmptyTrxValue(Message msg, HashMap lpMap) {

		LimitProfileHelper hp = new LimitProfileHelper();
		LimitProfile limitProfile = ((AAMessageBody) msg.getMsgBody()).getLimitProfile();

		ILimitProfileTrxValue lptv = new OBLimitProfileTrxValue();

		lpMap.put(hp.constructLimitProfileKey(limitProfile.getCIFId(), limitProfile.getSubProfileId(), limitProfile
				.getLimitProfileId()), lptv);

		return lpMap;

	}

}
