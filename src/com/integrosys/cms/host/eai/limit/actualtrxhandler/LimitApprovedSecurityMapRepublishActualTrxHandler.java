package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;

public class LimitApprovedSecurityMapRepublishActualTrxHandler extends LimitApprovedSecurityMapActualTrxHandler {
	public Message preprocess(Message msg) {
		AAMessageBody msgBody = (AAMessageBody) msg.getMsgBody();
		fixLimitsApprSecMapRepublishInd(msgBody.getLimitsApprovedSecurityMap(), msgBody.getLimitProfile());
		return msg;
	}

	protected Vector fixLimitsApprSecMapRepublishInd(Vector limitSecurityMaps, LimitProfile limitProfile) {
		Vector deletingMap = new Vector();
		if (limitSecurityMaps == null) {
			limitSecurityMaps = new Vector();
		}

		for (int i = limitSecurityMaps.size() - 1; i >= 0; i--) {
			LimitsApprovedSecurityMap tmp = (LimitsApprovedSecurityMap) limitSecurityMaps.elementAt(i);

			if (tmp.getSecurityId() != null) {
				tmp.setSecurityId(tmp.getSecurityId().toUpperCase());
			}

			tmp.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			tmp.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
		}

		Map parameters = new HashMap();
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			parameters.put("limitProfileId", limitProfile.getCMSLimitProfileId());
		}
		else if (limitProfile.getLimitProfileId() != ICMSConstant.LONG_INVALID_VALUE) {
			parameters.put("limitProfileId", new Long(limitProfile.getLimitProfileId()));
		}

		if (!parameters.isEmpty()) {
			LimitHelper hp = new LimitHelper();

			List nonDeletedLimitSecMapList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters,
					"updateStatusIndicator", LimitsApprovedSecurityMap.class);

			for (Iterator itr = nonDeletedLimitSecMapList.iterator(); itr.hasNext();) {
				LimitsApprovedSecurityMap value = (LimitsApprovedSecurityMap) itr.next();

				LimitsApprovedSecurityMap v = null;

				v = hp.getLimitMapByCMSId(limitSecurityMaps, value.getCmsId());

				if (v == null) {
					v = hp.getLimitMap(limitSecurityMaps, value.getCIFId(), value.getSubProfileId(),
							value.getLimitId(), value.getSecurityId());
				}

				if (v == null) {
					value.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					value.setChangeIndicator(String.valueOf(CHANGEINDICATOR));

					LimitsApprovedSecurityMap newMap = new LimitsApprovedSecurityMap();
					AccessorUtil.copyValue(value, newMap);
					deletingMap.addElement(newMap);

					logger.debug("LimitsApprovedSecurityMap is null, deleting this entry with cms id : ["
							+ value.getCmsId() + "]");
				}
				else {
					v.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					v.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
					v.setCmsId(value.getCmsId());
					v.setCmsLimitId(value.getCmsLimitId());
					v.setCmsSecurityId(value.getCmsSecurityId());

					logger.debug("LimitsApprovedSecurityMap is not null, updating this entry with cms id : ["
							+ value.getCmsId() + "]");
				}
			}
			limitSecurityMaps.addAll(deletingMap);
		}

		return limitSecurityMaps;
	}
}
