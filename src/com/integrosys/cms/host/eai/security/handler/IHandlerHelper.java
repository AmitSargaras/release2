package com.integrosys.cms.host.eai.security.handler;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;

/**
 * @author zhaijian
 * @author Chong Jun Yong
 * @since 16.05.2007
 */
public interface IHandlerHelper {
	void persistValuationDetail(SecurityMessageBody secMsgBody, ApprovedSecurity apprSec);

	void persistStageValuationDetail(SecurityMessageBody secMsgBody, StageApprovedSecurity stageSec);

	void persistInsurancePolicyDetail(EAIHeader msgHeader,SecurityMessageBody secMsgBody, ApprovedSecurity apprSec);

	void persistStageInsPolicyDetail(EAIHeader msgHeader,SecurityMessageBody secMsgBody, StageApprovedSecurity stageSec);

	void persistChequeDetail(EAIHeader msgHeader,SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageChequeDetail(EAIHeader msgHeader,SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistCreditDefaultSwapsDetail(EAIHeader msgHeader,SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageCreditDefaultSwapsDetail(EAIHeader msgHeader,SecurityMessageBody msg, StageApprovedSecurity stageSec);

	void persistPortFolioItems(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStagePortFolioItems(Message msg, StageApprovedSecurity stageSec);

	void persistCashDeposit(EAIHeader msgHeader,SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageCashDeposit(EAIHeader msgHeader,SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistAssetDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageAssetDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistCashDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageCashDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistInsuranceDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageInsuranceDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistDocumentDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageDocumentDetial(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistGuaranteeDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageGuaranteeDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistPropertyDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStagePropertyDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistOthersDetail(SecurityMessageBody s1, ApprovedSecurity apprSec);

	void persistStageOthersDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec);

	void persistSecurityInstrument(Vector securityInstrumentList, long cMSSecurityId);

	void persistStageSecurityInstrument(Vector securityInstrumentList, long cMSSecurityId);

	void persistMarketableSecurity(SecurityMessageBody s1,ApprovedSecurity accSec);

	void persistStageMarketableSecurity(SecurityMessageBody s1,StageApprovedSecurity stageSec);

	void setCommonFieldsForSecurity(SecurityMessageBody s1, ApprovedSecurity apprSec, String secuirityType);

	void setStandardCodeDescription(ApprovedSecurity apprSec);

	void retrieveCmvFsvfromValuation(ApprovedSecurity apprSec, Vector valuationDetailList);

	String[] excludeUnchangedCmvFsv(double cmv);

	void defaultCmvFsv(ApprovedSecurity sec);
	
	Object copyVariationProperties(Object source,Object target,List properties);

	void removeAllSecurityInstrument(Map parameters, Class securityInstrumentClass);
	
	ApprovedSecurity getApprovedSecurity(ApprovedSecurity appSec, String oldSecurityId, String sourceId)
	throws NoSuchSecurityException;
}
