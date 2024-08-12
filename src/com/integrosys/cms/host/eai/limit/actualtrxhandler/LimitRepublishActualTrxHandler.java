package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class LimitRepublishActualTrxHandler extends LimitActualTrxHandler {

	public Message preprocess(Message msg) {
		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessageBody.getLimitProfile();

		String source = msg.getMsgHeader().getSource();

		long aaCmsId = ICMSConstant.LONG_INVALID_VALUE;

		Map parameters = new HashMap();
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			parameters.put("limitProfileId", limitProfile.getCMSLimitProfileId());
		}
		else {
			parameters.put("LOSAANumber", limitProfile.getLOSAANumber());
		}
		parameters.put("AASource", source);

		LimitProfile tmpmp = (LimitProfile) getLimitDao().retrieveNonDeletedObjectByParameters(parameters,
				"updateStatusIndicator", LimitProfile.class);

		if (tmpmp != null) {
			aaCmsId = tmpmp.getLimitProfileId();
			parameters.clear();
			parameters.put("cmsLimitProfileId", new Long(aaCmsId));
		}

		List limitIdList = new ArrayList();
		if (aaCmsId != ICMSConstant.LONG_INVALID_VALUE) {
			List limitList = getLimitDao().retrieveNonDeletedObjectsListByParameters(parameters,
					"updateStatusIndicator", LimitGeneral.class);

			for (Iterator itr = limitList.iterator(); itr.hasNext();) {
				LimitGeneral value = (LimitGeneral) itr.next();
				limitIdList.add(new Long(value.getCmsId()));
			}
		}

		aaMessageBody.setLimits(fixLimitsRepublishInd(aaMessageBody.getLimits(), limitIdList));

		return msg;
	}

	protected Vector fixLimitsRepublishInd(Vector col, List list) {
		LimitHelper hp = new LimitHelper();

		if (col == null)
			col = new Vector();
		/*
		 * if (!hp.anyLimit(col)) { col = new Vector(); }
		 */
		for (int i = col.size() - 1; i >= 0; i--) {
			Limits tmp = (Limits) col.elementAt(i);

			tmp.getLimitGeneral().setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			tmp.getLimitGeneral().setChangeIndicator(String.valueOf(CHANGEINDICATOR));
		}

		if (list.isEmpty()) {
			return col;
		}

		List nonDeletedLimitList = getLimitDao().retrieveNonDeletedObjectsListByLimitCmsIdList(list, "cmsId",
				"updateStatusIndicator", LimitGeneral.class);

		// set all indicator to insert , incase of deleteIndicator.

		int count = 0;
		for (Iterator itr = nonDeletedLimitList.iterator(); itr.hasNext();) {
			LimitGeneral value = (LimitGeneral) itr.next();

			++count;

			try {
				Limits updateLimit = null;

				// for variation cases
				updateLimit = hp.getLimitByCmsLimitId(col, value.getCmsId());

				if (updateLimit == null) {
					updateLimit = hp.getLimit(col, value.getCIFId(), value.getSubProfileId(), 0, value.getLOSLimitId());
				}

				updateLimit.getLimitGeneral().setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
				updateLimit.getLimitGeneral().setChangeIndicator(String.valueOf(CHANGEINDICATOR));

			}
			catch (NoSuchLimitException nsspe) {
				Limits tmpLimit = null;
				boolean isRenewedLimit = false;

				if (hp.anyLimit(col)) {
					Iterator iter = col.iterator();

					while (iter.hasNext()) {
						tmpLimit = (Limits) iter.next();

						// if
						// (value.getLOSLimitId().equals(tmpLimit.getLimitGeneral().getLOSLimitId()))
						// {
						if (value.getCmsId() == tmpLimit.getLimitGeneral().getCMSLimitId()) {
							isRenewedLimit = true;
							break;
						}
					}
				}

				if (!isRenewedLimit) {
					Limits deletingLimit = new Limits();
					LimitGeneral deletingLimitGeneral = null;
					try {

						deletingLimitGeneral = (LimitGeneral) AccessorUtil.deepClone(value);
						deletingLimit.setLimitGeneral(deletingLimitGeneral);

					}
					catch (Throwable t) {
						IllegalStateException isex = new IllegalStateException("error cloning limit, AA number ["
								+ value.getLOSAANumber() + "] limit id [" + value.getLOSLimitId() + "] details ["
								+ t.getMessage() + "]");
						isex.initCause(t);
						throw isex;
					}

					deletingLimitGeneral.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					deletingLimitGeneral.setChangeIndicator(String.valueOf(CHANGEINDICATOR));

					col.add(deletingLimit);
				}

			}
		}

		return col;
	}

}
