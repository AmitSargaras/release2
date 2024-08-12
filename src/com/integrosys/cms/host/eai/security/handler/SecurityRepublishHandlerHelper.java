package com.integrosys.cms.host.eai.security.handler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.StageSecurityValuation;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.StageAssetSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.StageCashSecurity;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.document.StageDocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.StageGuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.StageInsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.StageMarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;
import com.integrosys.cms.host.eai.security.bus.others.StageOthersSecurity;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;
import com.integrosys.cms.host.eai.security.bus.property.StagePropertySecurity;

/**
 * @author zhaijian
 * @author Chong Jun Yong
 * @since 14.05.2007
 */
public class SecurityRepublishHandlerHelper extends AbstractCommonSecurityHandlerHelper {

	private String[] EXCLUDE_METHOD = new String[] { "getCMSSecurityId" };

	private SecurityRepublishHandlerHelper() {
	}

	/**
	 * store actual valuation detail
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 * @param cdb
	 * @throws Exception
	 */
	public void persistValuationDetail(SecurityMessageBody secMsgBody, ApprovedSecurity apprSec) {
		Vector valuationDetailList = secMsgBody.getValuationDetail();
		if (valuationDetailList == null) {
			valuationDetailList = new Vector();
		}

		for (int i = 0; i < valuationDetailList.size(); i++) {
			SecurityValuation obSecurityValuation = (SecurityValuation) valuationDetailList.elementAt(i);

			if (isChanged(String.valueOf(obSecurityValuation.getChangeIndicator()))) {

				obSecurityValuation.setCMSSecurityId(apprSec.getCMSSecurityId());

				/**
				 * TODO: SourceId no more exist
				 */
				// obSecurityValuation.setSourceId( apprSec.getSourceId());
				getSecurityDao().store(obSecurityValuation, obSecurityValuation.getClass());
			}
		}
	}

	/**
	 * store staging valuation detail
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 * @param cdb
	 * @throws Exception
	 */
	public void persistStageValuationDetail(SecurityMessageBody secMsgBody, StageApprovedSecurity stageSec) {
		Vector valuationDetailList = secMsgBody.getValuationDetail();
		if (valuationDetailList == null) {
			valuationDetailList = new Vector();
		}

		for (int i = 0; i < valuationDetailList.size(); i++) {
			SecurityValuation actSecurityValuation = (SecurityValuation) valuationDetailList.elementAt(i);

			if (isChanged(String.valueOf(actSecurityValuation.getChangeIndicator()))) {
				StageSecurityValuation stgSecurityValuation = new StageSecurityValuation();

				AccessorUtil.copyValue(actSecurityValuation, stgSecurityValuation);
				// stgSecurityValuation.setValuationId(Long.parseLong(seq1));
				stgSecurityValuation.setCMSSecurityId(stageSec.getCMSSecurityId());

				getSecurityDao().store(stgSecurityValuation, StageSecurityValuation.class);
			}
		}
	}

	public void persistMarketableSecurity(SecurityMessageBody s1, ApprovedSecurity apprSec) {

		persistOrUpdateSecurity(apprSec.getCMSSecurityId(), apprSec, MarketableSecurity.class);

		// TODO : never do update ?
	}

	public void persistStageMarketableSecurity(SecurityMessageBody s1, StageApprovedSecurity stageSec) {

		persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), stageSec, StageMarketableSecurity.class);

		// TODO : never do update ?
	}

	public void persistPortFolioItems(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		// current not in use
	}

	public void persistStagePortFolioItems(Message msg, StageApprovedSecurity stageSec) {
		// current not in use
	}

	public void persistAssetDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		AssetSecurity obAsset = s1.getAssetDetail();

		if (obAsset != null) {

			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), apprSec, AssetSecurity.class);

		}
	}

	public void persistStageAssetDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		AssetSecurity act = s1.getAssetDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageAssetSecurity.class);
		}
	}

	public void persistCashDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		CashSecurity obCash = s1.getCashDetail();

		if (obCash != null) {
			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obCash, CashSecurity.class);
		}

	}

	public void persistStageCashDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		CashSecurity act = s1.getCashDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageCashSecurity.class);
		}
	}

	public void persistInsuranceDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		InsuranceSecurity obInsurance = s1.getInsuranceDetail();

		if (obInsurance != null) {
			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obInsurance, InsuranceSecurity.class);
		}

	}

	public void persistStageInsuranceDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		InsuranceSecurity act = s1.getInsuranceDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageInsuranceSecurity.class);
		}
	}

	public void persistDocumentDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		DocumentSecurity obDocument = s1.getDocumentationDetail();

		if (obDocument != null) {
			obDocument.setMinimumAmtCcy(apprSec.getCurrency());
			obDocument.setMaximumAmtCcy(apprSec.getCurrency());
			obDocument.setDocumentAmtCcy(apprSec.getCurrency());

			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obDocument, DocumentSecurity.class);
		}
	}

	public void persistStageDocumentDetial(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		DocumentSecurity act = s1.getDocumentationDetail();

		if (act != null) {
			act.setMinimumAmtCcy(stageSec.getCurrency());
			act.setMaximumAmtCcy(stageSec.getCurrency());
			act.setDocumentAmtCcy(stageSec.getCurrency());

			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageDocumentSecurity.class);
		}
	}

	public void persistGuaranteeDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		GuaranteeSecurity obGuarantee = s1.getGuaranteeDetail();

		if (obGuarantee != null) {
			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obGuarantee, GuaranteeSecurity.class);
		}

	}

	public void persistStageGuaranteeDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		GuaranteeSecurity act = s1.getGuaranteeDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageGuaranteeSecurity.class);
		}
	}

	public void persistPropertyDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		PropertySecurity obProperty = s1.getPropertyDetail();

		if (obProperty != null) {
			if (obProperty.getTitleNumber() != null) {
				obProperty.setTitleNumber(obProperty.getTitleNumber().toUpperCase());
			}

			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obProperty, PropertySecurity.class);
		}
	}

	public void persistStagePropertyDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		PropertySecurity act = s1.getPropertyDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StagePropertySecurity.class);
		}
	}

	public void persistOthersDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		OthersSecurity obOthers = s1.getOtherDetail();

		if (obOthers != null) {
			persistOrUpdateSecurity(apprSec.getCMSSecurityId(), obOthers, OthersSecurity.class);
		}
	}

	public void persistStageOthersDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		OthersSecurity act = s1.getOtherDetail();

		if (act != null) {
			persistOrUpdateStageSecurity(stageSec.getCMSSecurityId(), act, StageOthersSecurity.class);
		}
	}

	protected void persistOrUpdateSecurity(long cmsSecurityId, ApprovedSecurity security, Class securityTypeClass) {
		ApprovedSecurity storedSecurity = (ApprovedSecurity) getSecurityDao().retrieve(new Long(cmsSecurityId),
				securityTypeClass);

		if (storedSecurity == null) {
			security.setCMSSecurityId(cmsSecurityId);
			getSecurityDao().store(security, securityTypeClass);
		}
		else {
			AccessorUtil.copyValue(security, storedSecurity, EXCLUDE_METHOD);
			getSecurityDao().update(storedSecurity, securityTypeClass);
		}
	}

	protected void persistOrUpdateStageSecurity(long cmsSecurityId, ApprovedSecurity security, Class securityTypeClass) {
		ApprovedSecurity storedSecurity = (ApprovedSecurity) getSecurityDao().retrieve(new Long(cmsSecurityId),
				securityTypeClass);

		if (storedSecurity == null) {
			security.setCMSSecurityId(cmsSecurityId);

			Object stageSecurity;
			try {
				stageSecurity = securityTypeClass.newInstance();
			}
			catch (InstantiationException e) {
				IllegalStateException isex = new IllegalStateException(
						"not able to instantiate a new instance of class [" + securityTypeClass
								+ "], is it an abstract class ?");
				isex.initCause(e);

				throw isex;
			}
			catch (IllegalAccessException e) {
				IllegalStateException isex = new IllegalStateException("not able access default constructor of class ["
						+ securityTypeClass + "], is it private ?");
				isex.initCause(e);

				throw isex;
			}

			AccessorUtil.copyValue(security, stageSecurity);
			getSecurityDao().store(stageSecurity, securityTypeClass);
		}
		else {
			AccessorUtil.copyValue(security, storedSecurity, EXCLUDE_METHOD);
			getSecurityDao().update(storedSecurity, securityTypeClass);
		}
	}

	public ApprovedSecurity getApprovedSecurity(ApprovedSecurity appSec, String oldSecurityId, String sourceId)
			throws NoSuchSecurityException {
		if (appSec.getLOSSecurityId().equalsIgnoreCase(oldSecurityId)
				&& (appSec.getSourceId().equalsIgnoreCase(sourceId))) {
			appSec.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			appSec.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			return appSec;
		}
		else {

		}
		throw new NoSuchSecurityException(oldSecurityId, sourceId);

	}

}
