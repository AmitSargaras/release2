package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.Limits;

/**
 * <p>
 * EAI Handler to process Limit info, before Limit being handled by the actual
 * process.
 * <p>
 * Currently is copying <code>ACFNo</code> from <code>FacilityMaster</code> to
 * <code>LimitGeneral</code>.
 * @author Chong Jun Yong
 * 
 */
public class LimitPrePopulationActualTrxHandler extends AbstractCommonActualTrxHandler {

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();
		Vector limits = aaMsgBody.getLimits();
		if (limits != null && !limits.isEmpty()) {
			for (Iterator itr = limits.iterator(); itr.hasNext();) {
				Limits limit = (Limits) itr.next();
				if (limit.getLimitGeneral() != null && limit.getFacilityMaster() != null) {
					limit.getLimitGeneral().setAcfNo(limit.getFacilityMaster().getACFNo());
				}
			}
		}

		return msg;
	}

}
