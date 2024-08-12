package com.integrosys.cms.host.eai.security.validator;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;

/**
 * EAI Message validator to validate instance of <tt>OthersSecurity</tt>
 * @author Chong Jun Yong
 * 
 */
public class OtherSecurityValidator extends SecurityValidator {
	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		ApprovedSecurity security = msg.getSecurityDetail();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_OTHERS.equals(security.getSecurityType().getStandardCodeValue())) {
			OthersSecurity others = msg.getOtherDetail();

			validator.rejectIfNull(others, "OtherDetail");

			validator.validateString(others.getLOSSecurityId(), "OtherDetail - LOSSecurityId", true, 1, 20);

			validator.validateString(others.getAssetDescription(), "OtherDetail - Description", true, 1, 250);

			validator.validateString(others.getPhysicalInspection(), "OtherDetail - PhysicalInspection", false, 1, 1);

			if ((ICMSConstant.TRUE_VALUE).equals(others.getPhysicalInspection())) {
				validator.validateNumber(others.getPhysicalInspectionFrequencyUnit(),
						"OtherDetail  - PhysicalInspectionFrequencyUnit", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_10);

				validator.validateString(others.getPhysicalInspectionFrequencyUOM(),
						"OtherDetail  - PhysicalInspectionFrequencyUOM", true, 1, 1,
						EaiConstantCla.getAllowedValuesFrequencyUnit());
			}

			if ((ICMSConstant.TRUE_VALUE).equals(others.getEnvironmentallyRiskyStatus())) {
				validator.validateDate(others.getEnvironmentallyRiskyConfirmedDate(),
						"OtherDetail - EnvironmentallyRiskyConfirmedDate", true);
			}

			validator.validateDoubleDigit(others.getNumberOfUnits(), "OtherDetail - NumberOfUnits", true, 13, 2, false);

			validator.validateString(others.getUpdateStatusIndicator(), "OtherDetail - UpdateStatusIndicator", false,
					0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(others.getChangeIndicator(), "OtherDetail - ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, others.getUpdateStatusIndicator(), others
						.getChangeIndicator());
			}
		}
	}
}
