package com.integrosys.cms.host.eai.security.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationHandler;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecurityInstrument;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.StageSecurityInstrument;
import com.integrosys.cms.host.eai.security.bus.StageSecurityInsurancePolicy;
import com.integrosys.cms.host.eai.security.bus.asset.ChequeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.StageChequeDetail;
import com.integrosys.cms.host.eai.security.bus.cash.CashDeposit;
import com.integrosys.cms.host.eai.security.bus.cash.StageCashDeposit;
import com.integrosys.cms.host.eai.security.bus.clean.CleanSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.CreditDefaultSwapsDetail;
import com.integrosys.cms.host.eai.security.bus.insurance.StageCreditDefaultSwapsDetail;
import com.integrosys.cms.host.eai.support.ReflectionUtils;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * This class contains common methods for Security Handler on persistence
 * 
 * @author shphoon
 * @author Chong Jun Yong
 * @since 1.1
 */

/*
 * Remove the CMS Header from message according to the template get the source
 * from message header
 */
public abstract class AbstractCommonSecurityHandlerHelper implements IHandlerHelper {

	protected Log logger = LogFactory.getLog(getClass());

	private ISecurityDao securityDao;

	private IValuationHandler valuationHandler;

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void setValuationHandler(IValuationHandler valuationHandler) {
		this.valuationHandler = valuationHandler;
	}

	public IValuationHandler getValuationHandler() {
		return valuationHandler;
	}

	public AbstractCommonSecurityHandlerHelper() {
	}

	private String[] CMV_FSV_EXCLUDE_METHOD = new String[] { "getCmv", "getFsv", "getCmvCurrency", "getFsvCurrency" };

	public abstract void persistValuationDetail(SecurityMessageBody secMsgBody, ApprovedSecurity apprSec);

	public abstract void persistStageValuationDetail(SecurityMessageBody secMsgBody, StageApprovedSecurity stageSec);

	public abstract void persistPortFolioItems(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStagePortFolioItems(Message msg, StageApprovedSecurity stageSec);

	public abstract void persistAssetDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageAssetDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistCashDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageCashDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistInsuranceDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageInsuranceDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistDocumentDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageDocumentDetial(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistGuaranteeDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageGuaranteeDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistPropertyDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStagePropertyDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistOthersDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageOthersDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	public abstract void persistMarketableSecurity(SecurityMessageBody s1, ApprovedSecurity apprSec);

	public abstract void persistStageMarketableSecurity(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	/**
	 * store actual insurance policy detail
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 */
	public void persistInsurancePolicyDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			ApprovedSecurity apprSec) {
		persistInsurancePolicyDetail(msgHeader, secMsgBody, apprSec, SecurityInsurancePolicy.class);
	}

	/**
	 * store staging insurance policy detail
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 */
	public void persistStageInsPolicyDetail(EAIHeader msgHeaders, SecurityMessageBody secMsgBody,
			StageApprovedSecurity stageSec) {
		persistInsurancePolicyDetail(msgHeaders, secMsgBody, stageSec, StageSecurityInsurancePolicy.class);
	}

	protected void persistInsurancePolicyDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			ApprovedSecurity apprSec, Class insurancePolicyDetailClass) {
		Vector insurancePolicyDetailList = secMsgBody.getInsurancePolicyDetail();
		if (insurancePolicyDetailList == null) {
			insurancePolicyDetailList = new Vector();
		}
		String sourceId = msgHeader.getSource();
		// removeAllInsurancePolicy(apprSec, sourceId,
		// insurancePolicyDetailClass);

		for (int i = 0; i < insurancePolicyDetailList.size(); i++) {
			SecurityInsurancePolicy obInsurancePolicy = (SecurityInsurancePolicy) insurancePolicyDetailList
					.elementAt(i);

			if (obInsurancePolicy.getChangeIndicator().equals("Y")) {
				SecurityInsurancePolicy storingSecurityInsurancePolicy = (insurancePolicyDetailClass == StageSecurityInsurancePolicy.class) ? new StageSecurityInsurancePolicy()
						: new SecurityInsurancePolicy();

				AccessorUtil.copyValue(obInsurancePolicy, storingSecurityInsurancePolicy,
						new String[] { "getInsurancePolicyId" });

				storingSecurityInsurancePolicy.setCMSSecurityId(apprSec.getCMSSecurityId());
				storingSecurityInsurancePolicy.setSecurityId(apprSec.getLOSSecurityId());
				storingSecurityInsurancePolicy.setSourceId(sourceId);
				storingSecurityInsurancePolicy.setStatus(ICMSConstant.STATE_ACTIVE);

				if (obInsurancePolicy.getUpdateStatusIndicator().equals("I")) {
					Long insrKey = (Long) getSecurityDao().store(storingSecurityInsurancePolicy,
							insurancePolicyDetailClass);
					storingSecurityInsurancePolicy.setInsuranceId(String.valueOf(insrKey));
					storingSecurityInsurancePolicy.setInsurancePolicyId(insrKey.longValue());

					getSecurityDao().update(storingSecurityInsurancePolicy, insurancePolicyDetailClass);
				}
				else {

					Map parameters = new HashMap();
					if (obInsurancePolicy.getInsurancePolicyId() > 0) {
						parameters.put("insurancePolicyId", new Long(obInsurancePolicy.getInsurancePolicyId()));
					}
					else {
						parameters
								.put("LOSInsurancePolicyId", storingSecurityInsurancePolicy.getLOSInsurancePolicyId());
					}

					SecurityInsurancePolicy existingPolicy = (SecurityInsurancePolicy) getSecurityDao()
							.retrieveObjectByParameters(parameters, insurancePolicyDetailClass);

					if (existingPolicy != null) {
						if (obInsurancePolicy.getUpdateStatusIndicator().equals("U")) {
							AccessorUtil.copyValue(storingSecurityInsurancePolicy, existingPolicy, new String[] {
									"getInsurancePolicyId", "getInsuranceId" });

							getSecurityDao().update(existingPolicy, insurancePolicyDetailClass);
						}
						else if (obInsurancePolicy.getUpdateStatusIndicator().equals("D")) {
							existingPolicy.setStatus(ICMSConstant.STATE_DELETED);
							getSecurityDao().update(existingPolicy, insurancePolicyDetailClass);
						}
					}
				}
			}
		}
	}

	private void removeAllInsurancePolicy(ApprovedSecurity apprSec, String sourceId, Class insurancePolicyClass) {
		Map parameters = new HashMap();
		if (apprSec.getCMSSecurityId() > 0) {
			parameters.put("CMSSecurityId", new Long(apprSec.getCMSSecurityId()));
		}
		else {
			parameters.put("securityId", apprSec.getLOSSecurityId());
			parameters.put("sourceId", sourceId);
		}
		List insrPolicyList = getSecurityDao().retrieveObjectsListByParameters(parameters, insurancePolicyClass);

		for (Iterator itr = insrPolicyList.iterator(); itr.hasNext();) {
			SecurityInsurancePolicy insrPolicy = (SecurityInsurancePolicy) itr.next();
			/** TODO : check whether we need to delete staging record **/
			if (insrPolicy.getClass() == SecurityInsurancePolicy.class) {
				getSecurityDao().remove(insrPolicy, SecurityInsurancePolicy.class);
			}
			else if (insrPolicy.getClass() == StageSecurityInsurancePolicy.class) {
				getSecurityDao().remove(insrPolicy, StageSecurityInsurancePolicy.class);
			}
		}
	}

	/**
	 * store actual cheque detail
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 * @param cdb
	 * @throws Exception
	 */
	public void persistChequeDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody, ApprovedSecurity apprSec) {
		persistChequeDetail(msgHeader, secMsgBody, apprSec, ChequeDetail.class);
	}

	/**
	 * store stage cheque detail
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 * @param cdb
	 */
	public void persistStageChequeDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			StageApprovedSecurity stageSec) {
		persistChequeDetail(msgHeader, secMsgBody, stageSec, StageChequeDetail.class);
	}

	protected void persistChequeDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody, ApprovedSecurity apprSec,
			Class chequeDetailClass) {
		Vector chequeDetailList = secMsgBody.getChequeDetail();
		if (chequeDetailList == null) {
			chequeDetailList = new Vector();
		}
		String sourceId = msgHeader.getSource();
		removeAllChequeDetail(apprSec, sourceId, chequeDetailClass);

		for (int i = 0; i < chequeDetailList.size(); i++) {
			ChequeDetail obChequeDetail = (ChequeDetail) chequeDetailList.elementAt(i);

			ChequeDetail storingChequeDetail = (chequeDetailClass == StageChequeDetail.class) ? new StageChequeDetail()
					: new ChequeDetail();
			AccessorUtil.copyValue(obChequeDetail, storingChequeDetail);

			storingChequeDetail.setCollateralId(apprSec.getCMSSecurityId());
			storingChequeDetail.setSecurityId(apprSec.getLOSSecurityId());
			storingChequeDetail.setSourceId(sourceId);

			getSecurityDao().store(storingChequeDetail, chequeDetailClass);
		}
	}

	private void removeAllChequeDetail(ApprovedSecurity apprSec, String sourceId, Class chequeDetailClass) {
		Map parameters = new HashMap();

		if (apprSec.getCMSSecurityId() > 0) {
			parameters.put("collateralId", new Long(apprSec.getCMSSecurityId()));
		}
		else {
			parameters.put("securityId", apprSec.getLOSSecurityId());
		}
		parameters.put("sourceId", sourceId);

		List chequeDetailList = getSecurityDao().retrieveObjectsListByParameters(parameters, chequeDetailClass);
		for (Iterator itr = chequeDetailList.iterator(); itr.hasNext();) {
			ChequeDetail storedChequeDetail = (ChequeDetail) itr.next();

			if (storedChequeDetail.getClass() == StageChequeDetail.class) {
				getSecurityDao().remove(storedChequeDetail, StageChequeDetail.class);
			}
			else if (storedChequeDetail.getClass() == ChequeDetail.class) {
				getSecurityDao().remove(storedChequeDetail, ChequeDetail.class);
				/**
				 * TODO: Check the logic do we need to delete the staging
				 * records
				 **/
			}

		}
	}

	/**
	 * store actual Credit Default Swaps Detail
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 * @param cdb
	 */
	public void persistCreditDefaultSwapsDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			ApprovedSecurity apprSec) {
		persistCreditDefaultSwapsDetail(msgHeader, secMsgBody, apprSec, CreditDefaultSwapsDetail.class);
	}

	/**
	 * store stage Credit Default Swaps Detail
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 * @param cdb
	 */
	public void persistStageCreditDefaultSwapsDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			StageApprovedSecurity stageSec) {
		persistCreditDefaultSwapsDetail(msgHeader, secMsgBody, stageSec, StageCreditDefaultSwapsDetail.class);
	}

	private void persistCreditDefaultSwapsDetail(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			ApprovedSecurity apprSec, Class creditDefaultSwapDetailClass) {
		Vector creditDefaultSwapsDetailList = secMsgBody.getCreditDefaultSwapsDetail();
		if (creditDefaultSwapsDetailList == null) {
			creditDefaultSwapsDetailList = new Vector();
		}
		String sourceId = msgHeader.getSource();
		removeAllCreditDefaultSwapDetail(apprSec, sourceId, creditDefaultSwapDetailClass);

		for (int i = 0; i < creditDefaultSwapsDetailList.size(); i++) {
			CreditDefaultSwapsDetail actSwapsDetail = (CreditDefaultSwapsDetail) creditDefaultSwapsDetailList
					.elementAt(i);

			CreditDefaultSwapsDetail storingCreditDefaultSwap = (creditDefaultSwapDetailClass == StageCreditDefaultSwapsDetail.class) ? new StageCreditDefaultSwapsDetail()
					: new CreditDefaultSwapsDetail();
			AccessorUtil.copyValue(actSwapsDetail, storingCreditDefaultSwap);

			storingCreditDefaultSwap.setCollateralId(apprSec.getCMSSecurityId());
			storingCreditDefaultSwap.setSecurityId(apprSec.getLOSSecurityId());
			storingCreditDefaultSwap.setSourceId(sourceId);
			getSecurityDao().store(storingCreditDefaultSwap, creditDefaultSwapDetailClass);
		}
	}

	private void removeAllCreditDefaultSwapDetail(ApprovedSecurity aa, String sourceId,
			Class creditDefaultSwapsDetailClass) {
		Map parameters = new HashMap();
		if (aa.getCMSSecurityId() > 0) {
			parameters.put("collateralId", new Long(aa.getCMSSecurityId()));
		}
		else {
			parameters.put("securityId", aa.getLOSSecurityId());
		}
		parameters.put("sourceId", sourceId);

		List creditDefaultSwapsDetailList = getSecurityDao().retrieveObjectsListByParameters(parameters,
				creditDefaultSwapsDetailClass);
		for (Iterator itr = creditDefaultSwapsDetailList.iterator(); itr.hasNext();) {
			CreditDefaultSwapsDetail storedCreditDefaultSwapsDetail = (CreditDefaultSwapsDetail) itr.next();
			if (storedCreditDefaultSwapsDetail.getClass() == CreditDefaultSwapsDetail.class) {
				getSecurityDao().remove(storedCreditDefaultSwapsDetail, CreditDefaultSwapsDetail.class);
			}
			else if (storedCreditDefaultSwapsDetail.getClass() == StageCreditDefaultSwapsDetail.class) {
				getSecurityDao().remove(storedCreditDefaultSwapsDetail, StageCreditDefaultSwapsDetail.class);
			}
		}
	}

	/**
	 * store actual Cash Deposit
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 * @param cdb
	 */
	public void persistCashDeposit(EAIHeader msgHeader, SecurityMessageBody secMsgBody, ApprovedSecurity apprSec) {
		persistCashDeposit(msgHeader, secMsgBody, apprSec, CashDeposit.class);
	}

	/**
	 * store stage Cash Deposit
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 * @param cdb
	 */
	public void persistStageCashDeposit(EAIHeader msgHeader, SecurityMessageBody secMsgBody,
			StageApprovedSecurity stageSec) {
		persistCashDeposit(msgHeader, secMsgBody, stageSec, StageCashDeposit.class);

	}

	protected void persistCashDeposit(EAIHeader msgHeader, SecurityMessageBody secMsgBody, ApprovedSecurity apprSec,
			Class cashDepositClass) {
		Vector cashDepositList = secMsgBody.getDepositDetail();
		if (cashDepositList == null) {
			cashDepositList = new Vector();
		}

		String sourceId = msgHeader.getSource();
		removeAllCashDeposit(apprSec, sourceId, cashDepositClass);

		for (int i = 0; i < cashDepositList.size(); i++) {
			CashDeposit actCashDeposit = (CashDeposit) cashDepositList.elementAt(i);

			CashDeposit storingCashDeposit = (cashDepositClass == StageCashDeposit.class) ? new StageCashDeposit()
					: new CashDeposit();
			AccessorUtil.copyValue(actCashDeposit, storingCashDeposit);

			storingCashDeposit.setCollateralId(apprSec.getCMSSecurityId());
			storingCashDeposit.setSecurityId(apprSec.getLOSSecurityId());
			storingCashDeposit.setSourceId(sourceId);
			storingCashDeposit.setStatus(IEaiConstant.ACTIVE_STATUS);
			getSecurityDao().store(storingCashDeposit, cashDepositClass);
		}
	}

	protected void removeAllCashDeposit(ApprovedSecurity apprSec, String sourceId, Class cashDepositClass) {
		Map parameters = new HashMap();
		// parameters.put("securityId", apprSec.getOldSecurityId());
		if (apprSec.getCMSSecurityId() > 0) {
			parameters.put("collateralId", new Long(apprSec.getCMSSecurityId()));
		}
		else {
			parameters.put("securityId", apprSec.getLOSSecurityId());
		}

		parameters.put("sourceId", sourceId);

		List cashDepositList = getSecurityDao().retrieveObjectsListByParameters(parameters, cashDepositClass);

		for (Iterator itr = cashDepositList.iterator(); itr.hasNext();) {
			CashDeposit cashDeposit = (CashDeposit) itr.next();
			/** TODO: check whether we need to delete stage record **/
			if (cashDeposit.getClass() == CashDeposit.class) {
				getSecurityDao().remove(cashDeposit, CashDeposit.class);
			}
			else if (cashDeposit.getClass() == StageCashDeposit.class) {
				getSecurityDao().remove(cashDeposit, StageCashDeposit.class);
			}
		}
	}

	public void persistSecurityInstrument(Vector securityInstrumentList, long cMSSecurityId) {
		for (int i = 0; i < securityInstrumentList.size(); i++) {
			StandardCode obStandardCode = (StandardCode) securityInstrumentList.elementAt(i);

			// String seq = (new SequenceManager()).getSeqNum(ICMSConstant.
			// SEQUENCE_SECURITY_INSTRUMENT, true);
			SecurityInstrument obSecurityInstrument = new SecurityInstrument();
			// obSecurityInstrument.setCMSSecurityInstrumentId(Long.parseLong(seq
			// ));
			obSecurityInstrument.setCMSSecurityId(cMSSecurityId);
			obSecurityInstrument.setInstrumentCode(obStandardCode.getStandardCodeValue());

			getSecurityDao().store(obSecurityInstrument, SecurityInstrument.class);
		}
	}

	public void persistStageSecurityInstrument(Vector securityInstrumentList, long cMSSecurityId) {
		for (int i = 0; i < securityInstrumentList.size(); i++) {
			StandardCode obStandardCode = (StandardCode) securityInstrumentList.elementAt(i);

			// String seq = (new SequenceManager()).getSeqNum(ICMSConstant.
			// SEQUENCE_SECURITY_INSTRUMENT_STAGE, true);
			StageSecurityInstrument obStageSecurityInstrument = new StageSecurityInstrument();
			// obStageSecurityInstrument.setCMSSecurityInstrumentId(Long.parseLong
			// (seq));
			obStageSecurityInstrument.setCMSSecurityId(cMSSecurityId);
			obStageSecurityInstrument.setInstrumentCode(obStandardCode.getStandardCodeValue());

			getSecurityDao().store(obStageSecurityInstrument, StageSecurityInstrument.class);
		}
	}

	public void removeAllSecurityInstrument(Map parameters, Class securityInstrumentClass) {
		List securityInstrumentList = getSecurityDao().retrieveObjectListByParameters(parameters,
				securityInstrumentClass);
		for (Iterator itr = securityInstrumentList.iterator(); itr.hasNext();) {
			SecurityInstrument storedSecurityInstrument = (SecurityInstrument) itr.next();
			/** TODO: check whether we need to delete stage record **/
			if (storedSecurityInstrument.getClass() == SecurityInstrument.class) {
				getSecurityDao().remove(storedSecurityInstrument, StageCashDeposit.class);
			}
			else if (storedSecurityInstrument.getClass() == StageSecurityInstrument.class) {
				getSecurityDao().remove(storedSecurityInstrument, StageCashDeposit.class);
			}
			getSecurityDao().remove(storedSecurityInstrument, securityInstrumentClass);
		}
	}

	public void setCommonFieldsForSecurity(SecurityMessageBody s1, ApprovedSecurity apprSec, String secuirityType) {
		StandardCode securityType = apprSec.getSecurityType();
		StandardCode securitySubType = apprSec.getSecuritySubType();

		String securityTypeDesc = CommonDataSingleton.getCodeCategoryLabelByValue(securityType.getStandardCodeNumber(),
				securityType.getStandardCodeValue());

		String securitySubTypeDesc = CommonDataSingleton.getCodeCategoryLabelByValue(securitySubType
				.getStandardCodeNumber(), securitySubType.getStandardCodeValue());

		securityType.setStandardCodeDescription(securityTypeDesc);
		securitySubType.setStandardCodeDescription(securitySubTypeDesc);

		// set original currency & currency for security detaile
		if ((apprSec.getOriginalCurrency() == null) || ("").equals(apprSec.getOriginalCurrency().trim())) {
			apprSec.setOriginalCurrency("MYR"); // default to "MYR"
		}

		if ((apprSec.getCurrency() == null) || ("").equals(apprSec.getCurrency().trim())) {
			apprSec.setCurrency(apprSec.getOriginalCurrency());
		}

		if ((ICMSConstant.SECURITY_TYPE_CLEAN).equals(secuirityType)) {
			logger.debug("Setting common fields for security type ======== CLEAN, security id ["
					+ apprSec.getLOSSecurityId() + "]");
			if (s1.getCleanDetail() != null) {
				CleanSecurity obClean = s1.getCleanDetail();
				apprSec.setComments(obClean.getComments());
			}
		}
		if ((ICMSConstant.SECURITY_TYPE_GUARANTEE).equals(secuirityType)) {
			GuaranteeSecurity obCuarantee = s1.getGuaranteeDetail();
			obCuarantee.setCurrency(apprSec.getCurrency());
		}

	}

	public void setStandardCodeDescription(ApprovedSecurity apprSec) {
		// set description for security type
		String secTypeDesc = CommonDataSingleton.getCodeCategoryLabelByValue("31", apprSec.getSecurityType()
				.getStandardCodeValue());
		apprSec.getSecurityType().setStandardCodeDescription(secTypeDesc);

		// set description for security sub type
		String secSubTypeDesc = CommonDataSingleton.getCodeCategoryLabelByValue("54", apprSec.getSecuritySubType()
				.getStandardCodeValue());
		apprSec.getSecuritySubType().setStandardCodeDescription(secSubTypeDesc);

		// set sci security sub type
		apprSec.setSciSecuritySubType(apprSec.getSecuritySubType().getStandardCodeValue());
	}

	/**
	 * return true if the ChangeIndicator is 'Y', else return false
	 * @param changeIndicator
	 * @return
	 */
	public boolean isChanged(String changeIndicator) {
		if (String.valueOf(IEaiConstant.CHANGEINDICATOR).equals(changeIndicator)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void retrieveCmvFsvfromValuation(ApprovedSecurity apprSec, Vector valuationDetailList) {
		// Update latest LOS valuation's cmv / fsv into security table
		if ((valuationDetailList != null) && (valuationDetailList.size() > 0)) {
			SecurityValuation latestLOS = null;
			SecurityValuation obSecurityValuation = new SecurityValuation();

			// retrieve latest LOS Valuation (by valuation date)
			for (int i = 0; i < valuationDetailList.size(); i++) {
				obSecurityValuation = new SecurityValuation();
				obSecurityValuation = (SecurityValuation) valuationDetailList.elementAt(i);

				if (isChanged(String.valueOf(obSecurityValuation.getChangeIndicator()))
						&& !CommonUtil.isEmpty(obSecurityValuation.getLOSSecurityId())
						&& (obSecurityValuation.getJDOValuationDate() != null)
						&& !CommonUtil.isEmpty(obSecurityValuation.getValuationCurrency())
						&& (obSecurityValuation.getCMV() != null) && (obSecurityValuation.getCMV().doubleValue() > 0)) {
					if (latestLOS == null) {
						latestLOS = (SecurityValuation) obSecurityValuation;
					}
					else if (obSecurityValuation.getJDOValuationDate().after(latestLOS.getJDOValuationDate())) {
						latestLOS = (SecurityValuation) obSecurityValuation;
					}
				}
			}

			if (latestLOS == null) {
				logger.warn("-- No valid LOS Valuation found! -- Security Id [" + apprSec.getLOSSecurityId() + "]");
			}
			else {
				OBCollateral col = new OBCollateral();

				copyIntoValue(apprSec, col);
				Amount cmvAmount = new Amount(0d, latestLOS.getValuationCurrency());
				if (latestLOS.getCMV() != null) {
					cmvAmount = new Amount(latestLOS.getCMV().doubleValue(), latestLOS.getValuationCurrency());
				}
				Amount fsvAmount = new Amount(0d, latestLOS.getValuationCurrency());
				if (latestLOS.getFSV() != null) {
					fsvAmount = new Amount(latestLOS.getFSV().doubleValue(), latestLOS.getValuationCurrency());
				}

				IValuationModel latestValuation = getValuationHandler().calculateSecCmvFsv(col, cmvAmount, fsvAmount,
						ICMSConstant.VALUATION_SOURCE_TYPE_S, latestLOS.getJDOValuationDate(),
						latestLOS.getValuer().getStandardCodeValue());

				if (latestValuation != null) {
					if (latestValuation.getValOMV() != null) {
						apprSec.setCmv(new Double(latestValuation.getValOMV().getAmount()));
						apprSec.setCmvCurrency(latestValuation.getValOMV().getCurrencyCode());
					}
					if (latestValuation.getValFSV() != null) {
						apprSec.setFsv(new Double(latestValuation.getValFSV().getAmount()));
						apprSec.setFsvCurrency(latestValuation.getValFSV().getCurrencyCode());
					}

					apprSec.setValuationType(latestValuation.getValuationType());
					apprSec.setValuer(latestValuation.getValuer());
					apprSec.setLastRemarginDate(latestValuation.getValuationDate());

					Date nextValuationDate = ValuationUtil.getNextValuationDate(
							(latestValuation.getValuationDate() == null) ? new Date() : latestValuation
									.getValuationDate(), latestValuation.getValuationFrequency());
					apprSec.setNextRemarginDate(nextValuationDate);
				}
			}
		}
		else {
			logger.info("-- Not require info from LOS Valuation -- Security Id [" + apprSec.getLOSSecurityId() + "]");
		}
	}

	protected void copyIntoValue(ApprovedSecurity sec, OBCollateral col) {
		col.setCollateralLocation(sec.getSecurityLocation().getLocationCountry());
		col.setSCISubTypeValue(sec.getSecuritySubType().getStandardCodeValue());
		col.setCollateralID(sec.getCMSSecurityId());
	}

	public String[] excludeUnchangedCmvFsv(double cmv) {
		String[] filteredList = new String[] {};
		if (ICMSConstant.DOUBLE_INVALID_VALUE == cmv) {
			filteredList = CMV_FSV_EXCLUDE_METHOD;
		}
		return filteredList;
	}

	public void defaultCmvFsv(ApprovedSecurity sec) {
		if (sec.getCmv() == null) {
			sec.setCmv(new Double(0));
			sec.setCmvCurrency(null);
		}

		if (sec.getFsv() == null) {
			sec.setFsv(new Double(0));
			sec.setFsvCurrency(null);
		}
	}

	public Object copyVariationProperties(Object source, Object target, List properties) {
		ReflectionUtils.copyValuesByProperties(source, target, properties);
		return target;
	}
}
