package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;

/**
 * EAI Message validator to validate instance of <tt>SecurityValuation</tt>
 * @author Chong Jun Yong
 * 
 */
public class ValuationValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] securityTypeIdsValuationDetailApplicable;

	private String[] sourceIdsValuationDetailApplicable;

	public void setSecurityTypeIdsValuationDetailApplicable(String[] securityTypeIdsValuationDetailApplicable) {
		this.securityTypeIdsValuationDetailApplicable = securityTypeIdsValuationDetailApplicable;
	}

	public void setSourceIdsValuationDetailApplicable(String[] sourceIdsValuationDetailApplicable) {
		this.sourceIdsValuationDetailApplicable = sourceIdsValuationDetailApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		String secType = msg.getSecurityDetail().getSecurityType().getStandardCodeValue();

		if (!ArrayUtils.contains(this.securityTypeIdsValuationDetailApplicable, secType)) {
			return;
		}

		Vector valuationDetails = msg.getValuationDetail();
		if (valuationDetails == null || valuationDetails.isEmpty()) {
			return;
		}

		for (Iterator itr = valuationDetails.iterator(); itr.hasNext();) {
			SecurityValuation valuation = (SecurityValuation) itr.next();

			validator.validateString(valuation.getLOSSecurityId(), "ValuationDetail - LOSSecurityId", true, 1, 20);

			if (IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(valuation.getUpdateStatusIndicator())
					&& IEaiConstant.CHANGE_INDICATOR_YES.equals(valuation.getChangeIndicator())) {
				validator.validateNumber(new Long(valuation.getCMSSecurityId()), "ValuationDetail - CMSSecurityId",
						true, 1, IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
			}

			validator.validateString(valuation.getSourceType(), "ValuationDetail - SourceType", true, 1, 1,
					new String[] { "S", "M", "A" });

			validator.validateString(valuation.getSourceId(), "ValuationDetail - SourceId", true, 0, 40,
					this.sourceIdsValuationDetailApplicable);

			validator.validateStdCodeAllowNull(valuation.getValuer(), source, "VALUER");

			validator.validateString(valuation.getValuationCurrency(), "ValuationDetail - ValuationCurrency", true, 3,
					3);

			validator.validateDoubleDigit(valuation.getCMV(), "ValuationDetail - CMV", true, 13, 2, false);

			validator.validateDoubleDigit(valuation.getFSV(), "ValuationDetail - FSV", false, 13, 2, false);

			validator.validateDoubleDigit(valuation.getReservePrice(), "ValuationDetail - ReservePrice", false, 13, 2,
					false);

			validator.validateStdCodeAllowNull(valuation.getValuationType(), source, "VALUATION_TYPE");

			validator.validateString(valuation.getUpdateStatusIndicator(), "ValuationDetail - UpdateStatusIndicator",
					false, 0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(valuation.getChangeIndicator(), "ValuationDetail - ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());
		}
	}
}
