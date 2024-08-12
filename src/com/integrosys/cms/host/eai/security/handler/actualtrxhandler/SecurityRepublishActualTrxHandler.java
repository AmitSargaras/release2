/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityRepublishActualTrxHandler.java,v 1.5 2003/12/05 10:59:44 slong Exp $
 */
package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.marketable.PortfolioItem;

/**
 * This class will handle republishing of actual and staging business data for
 * approved security.
 * 
 * @author slong
 * @author Chong Jun Yong
 * @since 05.12.2003
 */
public class SecurityRepublishActualTrxHandler extends SecurityActualTrxHandler {
	private String SECURITY_KEY = IEaiConstant.SECURITY_REPUBLISH_KEY;

	public String getTrxKey() {
		return SECURITY_KEY;
	}

	public Message preprocess(Message msg) {
		msg = super.preprocess(msg);

		SecurityMessageBody siMsg = (SecurityMessageBody) msg.getMsgBody();

		ApprovedSecurity approvedSecurity = siMsg.getSecurityDetail();

		String source = msg.getMsgHeader().getSource();

		Map parameters = new HashMap();
		if (approvedSecurity.getCMSSecurityId() > 0) {
			parameters.put("CMSSecurityId", new Long(approvedSecurity.getCMSSecurityId()));
		}
		else {
			parameters.put("LOSSecurityId", approvedSecurity.getLOSSecurityId());
		}

		siMsg.setSecurityDetail(fixSecurityDetailInd(approvedSecurity, parameters));

		String securityType = approvedSecurity.getSecurityType().getStandardCodeValue();

		if ((ICMSConstant.SECURITY_TYPE_ASSET).equals(securityType)) {
			helper.fixAssetDetailInd(siMsg.getAssetDetail(), siMsg.getSecurityDetail());
			helper.fixChequeDetailInd(siMsg.getChequeDetail(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_CASH).equals(securityType)) {
			helper.fixCashDetailInd(siMsg.getCashDetail(), siMsg.getSecurityDetail());
			// helper.fixCashDepositInd(siMsg.getDepositDetail(), siMsg
			// .getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_INSURANCE).equals(securityType)) {

			helper.fixInsuranceDetailInd(siMsg.getInsuranceDetail(), siMsg.getSecurityDetail());
			helper.fixCreditDefaultSwapsDetailInd(siMsg.getCreditDefaultSwapsDetail(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_MARKETABLE).equals(securityType)) {
			helper.fixMarketableSecurityInd(siMsg.getMarketableSecDetail(), siMsg.getSecurityDetail());
			helper.fixPortFolioItemsInd(siMsg.getPortfolioItems(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_DOCUMENT).equals(securityType)) {

			helper.fixDocumentDetailInd(siMsg.getDocumentationDetail(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_OTHERS).equals(securityType)) {
			helper.fixOthersDetailInd(siMsg.getOtherDetail(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_GUARANTEE).equals(securityType)) {

			helper.fixGuaranteeDetailInd(siMsg.getGuaranteeDetail(), siMsg.getSecurityDetail());
		}
		else if ((ICMSConstant.SECURITY_TYPE_PROPERTY).equals(securityType)) {
			helper.fixPropertyDetailInd(siMsg.getPropertyDetail(), siMsg.getSecurityDetail());
		}
		else if (ICMSConstant.SECURITY_TYPE_CLEAN.equals(securityType)) {
		}

		siMsg.setValuationDetail(fixValuationInd(approvedSecurity, siMsg.getValuationDetail(), source));

		 siMsg.setInsurancePolicyDetail(fixSecurityInsuranceInd(approvedSecurity, siMsg.getInsurancePolicyDetail()));

		// siMsg.setPledgor(fixPledgorInd(approvedSecurity,
		// siMsg.getPledgor()));

		return msg;
	}

	private ApprovedSecurity fixSecurityDetailInd(ApprovedSecurity secDetail, Map parameters) {

		secDetail.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
		secDetail.setChangeIndicator(String.valueOf(CHANGEINDICATOR));

		ApprovedSecurity existingAppSec = (ApprovedSecurity) getSecurityDao().retrieveObjectByParameters(parameters,
				ApprovedSecurity.class);
		if (existingAppSec != null) {
			secDetail.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			secDetail.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}

		return secDetail;
	}

	private SecurityValuation cloneValuation(SecurityValuation source) {
		SecurityValuation clonedValuation = new SecurityValuation();
		// AccessorUtil.copyValue(source, clonedValuation);
		clonedValuation.setLOSSecurityId(source.getLOSSecurityId());
		clonedValuation.setLOSValuationId(source.getLOSValuationId());
		return clonedValuation;
	}

	private Vector fixValuationInd(ApprovedSecurity security, Vector valuations, String source) {
		if (valuations == null) {
			valuations = new Vector();
		}
		if (helper.isCreate(security)) {
			for (Iterator iterator = valuations.iterator(); iterator.hasNext();) {
				SecurityValuation valuation = (SecurityValuation) iterator.next();
				valuation.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
				valuation.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
			}
		}

		else if (helper.isUpdate(security)) {
			Vector deletingValuations = new Vector();
			Map parameters = new HashMap();

			// first set all to create
			for (Iterator iterator = valuations.iterator(); iterator.hasNext();) {
				SecurityValuation valuation = (SecurityValuation) iterator.next();
				valuation.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
				valuation.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
			}

			List existingValuations = new Vector();

			if (security.getCMSSecurityId() > 0) {
				parameters.put("CMSSecurityId", new Long(security.getCMSSecurityId()));
				parameters.put("sourceType", ICMSConstant.VALUATION_SOURCE_TYPE_S);
				parameters.put("sourceId", source);
				existingValuations = getSecurityDao().retrieveObjectsListByParameters(parameters,
						SecurityValuation.class);
			}
			else {
				// first get the CMSSecurityId
				parameters.put("LOSSecurityId", security.getLOSSecurityId());

				ApprovedSecurity existingAppSec = (ApprovedSecurity) getSecurityDao().retrieveObjectByParameters(
						parameters, ApprovedSecurity.class);

				parameters.clear();
				parameters.put("CMSSecurityId", new Long(existingAppSec.getCMSSecurityId()));
				parameters.put("sourceType", ICMSConstant.VALUATION_SOURCE_TYPE_S);
				parameters.put("sourceId", source);
				existingValuations = getSecurityDao().retrieveObjectsListByParameters(parameters,
						SecurityValuation.class);
			}

			if (existingValuations == null) {
				return valuations;
			}
			for (Iterator existingValsIter = existingValuations.iterator(); existingValsIter.hasNext();) {
				SecurityValuation existingValuation = (SecurityValuation) existingValsIter.next();
				boolean delete = true;
				for (Iterator newValIter = valuations.iterator(); newValIter.hasNext();) {
					SecurityValuation newValuation = (SecurityValuation) newValIter.next();

					if (newValuation.getLOSValuationId()!=null && newValuation.getLOSValuationId().equals(existingValuation.getLOSValuationId())) {
						newValuation.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
						newValuation.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
						delete = false;
					}
				}
				if (delete) {
					SecurityValuation deletingValuation = cloneValuation(existingValuation);
					deletingValuation.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					deletingValuation.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
					// getSecurityDao().evit(existingValuation);
					deletingValuations.addElement(deletingValuation);
				}
			}
			valuations.addAll(deletingValuations);
		}
		return valuations;
	}

	private SecurityInsurancePolicy cloneInsurancePolicy(SecurityInsurancePolicy sourcePolicy) {

		SecurityInsurancePolicy clonedPolicy = new SecurityInsurancePolicy();
		AccessorUtil.copyValue(sourcePolicy, clonedPolicy);
		return clonedPolicy;
	}

	private Vector fixSecurityInsuranceInd(ApprovedSecurity security, Vector insurancePolicies) {
		if (insurancePolicies == null) {
			insurancePolicies = new Vector();
		}

		for (Iterator iterator = insurancePolicies.iterator(); iterator.hasNext();) {
			SecurityInsurancePolicy newIns = (SecurityInsurancePolicy) iterator.next();
			newIns.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			newIns.setChangeIndicator(String.valueOf(CHANGEINDICATOR));

		}

		// to hold the existing insurance policies which are to be deleted.
		Vector deletingInsurancePolicies = new Vector();

		// getting the list of existing insurance policies
		Map parameters = new HashMap();
		if (security.getCMSSecurityId() > 0) {
			parameters.put("CMSSecurityId", new Long(security.getCMSSecurityId()));
		}
		else {
			parameters.put("securityId", security.getLOSSecurityId());
		}

		List existingInsurances = getSecurityDao().retrieveObjectsListByParameters(parameters,
				SecurityInsurancePolicy.class);

		for (Iterator existingIter = existingInsurances.iterator(); existingIter.hasNext();) {
			SecurityInsurancePolicy existingIns = (SecurityInsurancePolicy) existingIter.next();

			boolean delete = true;// flag for the delete indicator for existing
			// insurance policy

			for (Iterator newInter = insurancePolicies.iterator(); newInter.hasNext();) {
				SecurityInsurancePolicy newIns = (SecurityInsurancePolicy) newInter.next();
				if (( (existingIns.getLOSInsurancePolicyId()!=null && existingIns.getLOSInsurancePolicyId().equals(newIns.getLOSInsurancePolicyId())))
						|| (existingIns.getInsurancePolicyId() == newIns.getInsurancePolicyId())) {
					newIns.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					newIns.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
					delete = false;
				}
			}

			if (delete) {
				SecurityInsurancePolicy deletingPolicy = cloneInsurancePolicy(existingIns);
				deletingPolicy.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
				deletingPolicy.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				deletingInsurancePolicies.addElement(deletingPolicy);
			}
		}

		insurancePolicies.addAll(deletingInsurancePolicies);
		return insurancePolicies;
	}

	private Vector fixPledgorInd(ApprovedSecurity security, Vector pledgors) {
		if (pledgors == null) {
			pledgors = new Vector();
		}
		Vector indFixedPledgors = new Vector();
		if (helper.isCreate(security)) {
			Iterator iter = pledgors.iterator();
			while (iter.hasNext()) {
				Pledgor pledgor = (Pledgor) iter.next();

				pledgor.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
				pledgor.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				indFixedPledgors.add(pledgor);
			}
		}
		else if ((helper.isUpdate(security))) {
			Iterator iter = pledgors.iterator();
			while (iter.hasNext()) {
				Pledgor pledgor = (Pledgor) iter.next();

				pledgor.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
				pledgor.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				indFixedPledgors.add(pledgor);
			}
		}
		return indFixedPledgors;
	}

	public Vector fixPortFolioItemsInd(Vector portFolioItems, ApprovedSecurity security) {
		if (portFolioItems == null) {
			portFolioItems = new Vector();
		}
		Vector indFixedportFolioItems = new Vector();
		if (helper.isCreate(security)) {
			Iterator iter = portFolioItems.iterator();
			while (iter.hasNext()) {
				PortfolioItem portfolioItem = (PortfolioItem) iter.next();

				portfolioItem.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				portfolioItem.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
			}
		}
		else if ((helper.isUpdate(security))) {
			Iterator iter = portFolioItems.iterator();
			while (iter.hasNext()) {
				PortfolioItem portfolioItem = (PortfolioItem) iter.next();

				portfolioItem.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				portfolioItem.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
			}
			while (iter.hasNext()) {
				PortfolioItem portfolioItem = (PortfolioItem) iter.next();

				portfolioItem.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
				portfolioItem.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedportFolioItems.add(portfolioItem);
			}
		}
		return indFixedportFolioItems;
	}

}
