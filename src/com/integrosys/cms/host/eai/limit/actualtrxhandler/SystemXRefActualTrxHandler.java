package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitsSysXRefMap;
import com.integrosys.cms.host.eai.limit.bus.SystemXReference;

/**
 * <p>
 * Handler for process account information.
 * <p>
 * This will prepare account to limit/facility map information.
 * <p>
 * For staging account info, it's tied to staging limit cms id.
 * @author Chong Jun Yong
 * 
 */
public class SystemXRefActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ILimitDao limitDao;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessage = (AAMessageBody) msg.getMsgBody();

		Vector accounts = aaMessage.getLimitsSystemXReferenceMap();
		if (accounts == null || accounts.isEmpty()) {
			return msg;
		}

		for (Iterator itr = accounts.iterator(); itr.hasNext();) {
			SystemXReference xref = (SystemXReference) itr.next();
			Set limitsSysXRefMapSet = prepareLimitsSysXRefMapSet(xref);
			xref.setLimitsSysXRefMapSet(limitsSysXRefMapSet);

			Long cmsAccountId = (Long) this.limitDao.store(xref, SystemXReference.class);
			SystemXReference storedXref = (SystemXReference) this.limitDao.retrieve(cmsAccountId,
					SystemXReference.class);
			populateAccoutMapRefId(storedXref);
			this.limitDao.update(storedXref, SystemXReference.class);
		}

		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		// TODO revisit taking logic of LOS Limit Id
		return super.persistStagingTrx(msg, trxValue);
	}

	private void populateAccoutMapRefId(SystemXReference xref) {
		for (Iterator itr = xref.getLimitsSysXRefMapSet().iterator(); itr.hasNext();) {
			LimitsSysXRefMap map = (LimitsSysXRefMap) itr.next();
			map.setReferenceId(map.getCmsId());
		}
	}

	private Set prepareLimitsSysXRefMapSet(SystemXReference xref) {
		Long[] cmsLimitIds = xref.getCmsLimitIds();
		if (cmsLimitIds == null) {
			return null;
		}

		Set limitsSysXRefMapSet = new HashSet();
		for (int i = 0; i < cmsLimitIds.length; i++) {
			LimitsSysXRefMap map = new LimitsSysXRefMap();
			map.setCifId(xref.getCIFId());
			map.setSubProfileId(xref.getSubProfileId());

			LimitGeneral actualLimitGeneral = (LimitGeneral) this.limitDao.retrieve(cmsLimitIds[i],
					IEaiConstant.ACTUAL_LIMIT_GENERAL);
			if (actualLimitGeneral == null) {
				throw new NoSuchLimitException(ICMSConstant.LONG_INVALID_VALUE, cmsLimitIds[i].longValue());
			}

			map.setCmsLimitId(cmsLimitIds[i]);

			String mapStatus = ICMSConstant.HOST_STATUS_DELETE.equals(xref.getUpdateStatusIndicator()) ? ICMSConstant.STATE_DELETED
					: ICMSConstant.STATE_ACTIVE;

			map.setStatus(mapStatus);
			limitsSysXRefMapSet.add(map);
		}

		return limitsSysXRefMapSet;
	}
}
