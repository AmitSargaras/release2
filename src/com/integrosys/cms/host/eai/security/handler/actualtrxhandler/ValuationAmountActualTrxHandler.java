package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import org.springframework.beans.BeanUtils;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.CollateralParameter;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.handler.IHandlerHelper;

/**
 * Handler to do the valuation amount update, ie, base on the CMV to come out
 * the FSV.
 * 
 * @author Chong Jun Yong
 * 
 */
public class ValuationAmountActualTrxHandler extends AbstractCommonActualTrxHandler {

	private static final String VAL_MECHANISM_MARGIN = "margin";

	private static final String VAL_MECHANISM_HAIRCUT = "haircut";

	private static final String VALUATION_METHOD = PropertyManager
			.getValue("valuation.mechanism", VAL_MECHANISM_MARGIN);

	private ISecurityDao securityDao;

	private IHandlerHelper securityHandlerHelper;

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public IHandlerHelper getSecurityHandlerHelper() {
		return securityHandlerHelper;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void setSecurityHandlerHelper(IHandlerHelper securityHandlerHelper) {
		this.securityHandlerHelper = securityHandlerHelper;
	}

	public Message persistActualTrx(Message msg) {
		SecurityMessageBody secMsgBody = (SecurityMessageBody) msg.getMsgBody();
		ApprovedSecurity security = secMsgBody.getSecurityDetail();
		Vector valuations = secMsgBody.getValuationDetail();

		if (valuations != null && !valuations.isEmpty()) {
			updateFsvBasedOnCmv(valuations, security);

			updateSecurityForValuation(security, valuations);
		}

		return msg;
	}

	private void updateSecurityForValuation(ApprovedSecurity security, Vector valuations) {
		ApprovedSecurity storedSecurity = (ApprovedSecurity) getSecurityDao().retrieve(
				new Long(security.getCMSSecurityId()), security.getClass());

		// to retrieve CMV / FSV from LOS Valuation
		getSecurityHandlerHelper().retrieveCmvFsvfromValuation(storedSecurity, valuations);

		// to default CMV / FSV to 0 if no valid value
		getSecurityHandlerHelper().defaultCmvFsv(storedSecurity);

		getSecurityDao().update(storedSecurity, storedSecurity.getClass());
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		SecurityMessageBody secMsgBody = (SecurityMessageBody) msg.getMsgBody();
		ApprovedSecurity security = secMsgBody.getSecurityDetail();
		Vector valuations = secMsgBody.getValuationDetail();

		if (valuations != null && !valuations.isEmpty()) {
			updateFsvBasedOnCmv(valuations, security);

			updateSecurityForValuation(security, valuations);
		}

		return msg;
	}

	public String getTrxKey() {
		return IEaiConstant.SECURITY_KEY;
	}

	/**
	 * To update valuation fsv based on the cmv applied the collateral parameter
	 * of the collateral. If there is no collateral parameter found nothing will
	 * be happend.
	 * 
	 * @param valuations the valuations details list
	 * @param security the security entity to pull the cmv info
	 */
	protected void updateFsvBasedOnCmv(Vector valuations, ApprovedSecurity security) {
		String countryCode = security.getSecurityLocation().getLocationCountry();
		String subTypeCode = security.getSecuritySubType().getStandardCodeValue();

		CollateralParameter collateralParam = getSecurityDao().findCollateralParameterBySubTypeAndCountryCode(
				subTypeCode, countryCode);

		if (collateralParam == null) {
			logger.warn("There is no collateral parameter setup for country code [" + countryCode
					+ "], sub type code [" + subTypeCode + "], skipped for collateral, id ["
					+ security.getLOSSecurityId() + "]");
			return;
		}

		if (collateralParam.getThresholdPercent() == null) {
			logger.warn("There is no threashold percentage defined for "
					+ "the collateral parameter setup for country code [" + countryCode + "], sub type code ["
					+ subTypeCode + "], skipped for collateral, id [" + security.getLOSSecurityId() + "]");
			return;
		}

		double thresholdPercent = collateralParam.getThresholdPercent().doubleValue();

		for (Iterator itr = valuations.iterator(); itr.hasNext();) {
			SecurityValuation valuation = (SecurityValuation) itr.next();
			if (!String.valueOf(DELETEINDICATOR).equals(valuation.getUpdateStatusIndicator())) {
				Double cmv = valuation.getCMV();
				if (cmv != null & cmv.doubleValue() > 0 && valuation.getValuationId() > 0) {
					double calculatedFsv = 0;
					if (VAL_MECHANISM_MARGIN.equals(VALUATION_METHOD)) {
						calculatedFsv = cmv.doubleValue() * (thresholdPercent / 100);
					}
					else if (VAL_MECHANISM_HAIRCUT.equals(VALUATION_METHOD)) {
						calculatedFsv = cmv.doubleValue() * (1 - (thresholdPercent / 100));
					}

					valuation.setFSV(new Double(calculatedFsv));

					SecurityValuation storedValuation = (SecurityValuation) getSecurityDao().retrieve(
							new Long(valuation.getValuationId()), valuation.getClass());
					BeanUtils.copyProperties(valuation, storedValuation, new String[] { "valuationId" });

					getSecurityDao().update(storedValuation, valuation.getClass());
				}
			}
		}
	}
}
