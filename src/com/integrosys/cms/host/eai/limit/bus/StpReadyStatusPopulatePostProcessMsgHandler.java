package com.integrosys.cms.host.eai.limit.bus;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.app.limit.bus.IFacilityJdbc;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.AbstractPostProcessMessageHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;

/**
 * Implementation of {@link AbstractPostProcessMessageHandler} to update the stp
 * ready status indicator of all the facilities of a limit profile / AA
 * 
 * @author Chong Jun Yong
 * 
 */
public class StpReadyStatusPopulatePostProcessMsgHandler extends AbstractPostProcessMessageHandler {

	private IFacilityProxy facilityProxy;

	private IFacilityJdbc facilityJdbc;

	public IFacilityProxy getFacilityProxy() {
		return facilityProxy;
	}

	public IFacilityJdbc getFacilityJdbc() {
		return facilityJdbc;
	}

	public void setFacilityProxy(IFacilityProxy facilityProxy) {
		this.facilityProxy = facilityProxy;
	}

	public void setFacilityJdbc(IFacilityJdbc facilityJdbc) {
		this.facilityJdbc = facilityJdbc;
	}

	protected void doPostProcessMessage(EAIMessage eaiMessage) {
		AAMessageBody aaMessageBody = (AAMessageBody) eaiMessage.getMsgBody();
		Vector limits = aaMessageBody.getLimits();

		String aaType = aaMessageBody.getLimitProfile().getAAType();
        String aaLawType = aaMessageBody.getLimitProfile().getAALawType();
        long cmsSubProfileId = aaMessageBody.getLimitProfile().getCmsSubProfileId();

        if(limits == null )
        	limits = new Vector();
        
        for (Iterator itr = limits.iterator(); itr.hasNext();) {
			Limits aLimit = (Limits) itr.next();
			FacilityMaster facilityMaster = aLimit.getFacilityMaster();
			if (facilityMaster == null) {
				continue;
			}

			IFacilityTrxValue facilityTrxValue = getFacilityProxy().retrieveFacilityMasterTransactionById(
					facilityMaster.getId());

			String limitProductType = getFacilityProxy().getProductGroupByProductCode(
					facilityTrxValue.getStagingFacilityMaster().getLimit().getProductDesc());

			String limitDealerProductFlag = getFacilityProxy().getDealerProductFlagByProductCode(
					facilityTrxValue.getStagingFacilityMaster().getLimit().getProductDesc());

            String limitConceptCode = getFacilityProxy().getConceptCodeByProductCode(
					facilityTrxValue.getStagingFacilityMaster().getLimit().getProductDesc());

			String facilityAccountType = getFacilityProxy().getAccountTypeByFacilityCode(
					facilityTrxValue.getStagingFacilityMaster().getLimit().getFacilityCode());

            boolean isStpReady = FacilityUtil.CheckIsStpAllowed(facilityTrxValue.getStagingFacilityMaster(), aaType,
					limitProductType, limitDealerProductFlag, cmsSubProfileId, limitConceptCode, facilityAccountType,
                    aaLawType);

			getFacilityJdbc().updateOrInsertStpReadyStatus(facilityTrxValue.getTransactionID(), isStpReady);
		}
	}
}
