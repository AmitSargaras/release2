package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.security.EAISecurityMessageException;
import com.integrosys.cms.host.eai.security.NoSuchInsurancePolicyException;
import com.integrosys.cms.host.eai.security.NoSuchPledgorException;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityJdbc;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;

public class SecurityInternalKeyCheckingHandler extends AbstractCommonActualTrxHandler {

	ISecurityJdbc securityJdbc;

	public ISecurityJdbc getSecurityJdbc() {
		return securityJdbc;
	}

	public void setSecurityJdbc(ISecurityJdbc securityJdbc) {
		this.securityJdbc = securityJdbc;
	}

	public Message persistActualTrx(Message msg) {
		SecurityMessageBody msgBody = ((SecurityMessageBody) msg.getMsgBody());
		ApprovedSecurity sec = msgBody.getSecurityDetail();
		if (msgBody.getSecurityDetail().getCMSSecurityId() > 0) {
			if (this.securityJdbc.isCollateralDeleted(new Long(sec.getCMSSecurityId()), null, null)) {
				throw new CollateralDeletedException(new Long(sec.getCMSSecurityId()));
			}
		}
		else {
			if (this.securityJdbc.isCollateralDeleted(null, sec.getLOSSecurityId(), sec.getSourceId())) {
				throw new CollateralDeletedException(sec.getLOSSecurityId(), sec.getSourceId());
			}
		}

		return checkValidCMSId(msg);
	}

	protected Message checkValidCMSId(Message msg) {
		SecurityMessageBody msgBody = ((SecurityMessageBody) msg.getMsgBody());

		if (msgBody.getSecurityDetail().getCMSSecurityId() > 0) {
			ApprovedSecurity apprSec = msgBody.getSecurityDetail();

			validateApprovedSecurityId(apprSec);

			validateInsurancePolicyId(msgBody.getInsurancePolicyDetail());

			validatePledgorId(msgBody.getPledgor());
		}

		return msg;
	}

	private void validateApprovedSecurityId(ApprovedSecurity securityDetail) {
		long oldCMSSecurityId = securityDetail.getCMSSecurityId();
		Long cmsId = getSecurityJdbc().getCmsSecurityIdByOldCmsSecurityId(oldCMSSecurityId);

		if ((cmsId != null) && (cmsId.longValue() > 0)) {
			securityDetail.setCMSSecurityId(cmsId.longValue());
		}
		else {
			throw new NoSuchSecurityException(oldCMSSecurityId);
		}
	}

	private void validatePledgorId(Vector pledgors) {
		if (pledgors == null)
			pledgors = new Vector();

		for (Iterator iterator = pledgors.iterator(); iterator.hasNext();) {
			Pledgor pledgor = (Pledgor) iterator.next();

			long oldCMSPledgorId = 0l;

			if (pledgor.getCMSPledgorId() > 0)
				oldCMSPledgorId = pledgor.getCMSPledgorId();
			else
				continue;

			Long CMSPledgorId = getSecurityJdbc().getPledgorIdByOldCMSPledgorId(oldCMSPledgorId);

			if ((CMSPledgorId != null) && (CMSPledgorId.longValue() > 0)) {
				pledgor.setCMSPledgorId(CMSPledgorId.longValue());
			}
			else {
				throw new NoSuchPledgorException(oldCMSPledgorId);
			}
		}
	}

	private void validateInsurancePolicyId(Vector insurancePolicies) {
		if (insurancePolicies == null) {
			insurancePolicies = new Vector();
		}

		for (Iterator iterator = insurancePolicies.iterator(); iterator.hasNext();) {
			SecurityInsurancePolicy insurance = (SecurityInsurancePolicy) iterator.next();
			long oldCMSInsurancePolicyId;
			long oldCMSCollateralId;

			if ((insurance.getInsurancePolicyId() > 0) && (insurance.getCMSSecurityId() > 0)) {
				oldCMSInsurancePolicyId = insurance.getInsurancePolicyId();
				oldCMSCollateralId = insurance.getCMSSecurityId();
			}
			else {
				continue;
			}

			Long insurancePolicyId = getSecurityJdbc().getInsuranceIdByOldInsuranceId(oldCMSCollateralId,
					oldCMSInsurancePolicyId);

			if ((insurancePolicyId != null) && (insurancePolicyId.longValue() > 0)) {
				insurance.setInsurancePolicyId(insurancePolicyId.longValue());
			}
			else {
				throw new NoSuchInsurancePolicyException(oldCMSCollateralId, oldCMSInsurancePolicyId);
			}
		}
	}

	class CollateralDeletedException extends EAISecurityMessageException {

		private static final long serialVersionUID = 6423533054637881748L;

		public CollateralDeletedException(Long cmsSecurityId) {
			super("Collateral has been deleted in the system; CMS Collateral Key [" + cmsSecurityId + "]");
		}

		public CollateralDeletedException(String losSecurityId, String sourceId) {
			super("Collateral has been deleted in the system; LOS Collateral Key [" + losSecurityId + "], Source ["
					+ sourceId + "]");
		}

		public String getErrorCode() {
			return "COL_DELETED";
		}
	}
}
