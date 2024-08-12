package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.FacilityMessage;
import com.integrosys.cms.host.eai.limit.bus.FacilityMultiTierFinancing;
import com.integrosys.cms.host.eai.limit.bus.FacilityReduction;
import com.integrosys.cms.host.eai.limit.bus.Limits;

/**
 * @author Chong Jun Yong
 * 
 */
public class UpdateFacilityRepublishActualTrxHandler extends UpdateFacilityActualTrxHandler {

	public Message preprocess(Message msg) {
		AAMessageBody aamsg = (AAMessageBody) msg.getMsgBody();
		Vector limits = aamsg.getLimits();

		Iterator iter = limits.iterator();
		while (iter.hasNext()) {
			Limits limit = (Limits) iter.next();

			if (limit.getFacilityMaster() != null) {
				limit.getFacilityMaster().setUpdateStatusIndicator(limit.getLimitGeneral().getUpdateStatusIndicator());
				limit.getFacilityMaster().setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
			}

			if (limit.getIslamicFacilityMaster() != null) {
				limit.getIslamicFacilityMaster().setUpdateStatusIndicator(
						limit.getLimitGeneral().getUpdateStatusIndicator());
				limit.getIslamicFacilityMaster().setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
			}

			if (limit.getFacilityBBAVariPackage() != null) {
				limit.getFacilityBBAVariPackage().setUpdateStatusIndicator(
						limit.getLimitGeneral().getUpdateStatusIndicator());
				limit.getFacilityBBAVariPackage().setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
			}

			if (limit.getFacilityMultiTierFinancings() != null && !limit.getFacilityMultiTierFinancings().isEmpty()) {
				for (Iterator iterator = limit.getFacilityMultiTierFinancings().iterator(); iterator.hasNext();) {
					FacilityMultiTierFinancing financing = (FacilityMultiTierFinancing) iterator.next();
					financing.setUpdateStatusIndicator(limit.getLimitGeneral().getUpdateStatusIndicator());
					financing.setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
				}
			}

			if (limit.getFacilityMessages() != null && !limit.getFacilityMessages().isEmpty()) {
				for (Iterator iterator = limit.getFacilityMessages().iterator(); iterator.hasNext();) {
					FacilityMessage message = (FacilityMessage) iterator.next();
					message.setUpdateStatusIndicator(limit.getLimitGeneral().getUpdateStatusIndicator());
					message.setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
				}
			}

			if (limit.getFacilityReductions() != null && !limit.getFacilityReductions().isEmpty()) {
				for (Iterator iterator = limit.getFacilityReductions().iterator(); iterator.hasNext();) {
					FacilityReduction reduction = (FacilityReduction) iterator.next();
					reduction.setUpdateStatusIndicator(limit.getLimitGeneral().getUpdateStatusIndicator());
					reduction.setChangeIndicator(limit.getLimitGeneral().getChangeIndicator());
				}
			}
		}
		return msg;
	}

}
