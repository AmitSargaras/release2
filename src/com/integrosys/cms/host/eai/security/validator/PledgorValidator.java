package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.PledgorCreditGrade;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * EAI Message validator to validate instance of <tt>Pledgor</tt> and
 * <tt>PledgorCreditGrade</tt>
 * @author marvin
 * @author Chong Jun Yong
 */
public class PledgorValidator implements IEaiMessageValidator {

	protected static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] securityTypeIdsPledgorDetailsMandatory;

	public void setSecurityTypeIdsPledgorDetailsMandatory(String[] securityTypeIdsPledgorDetailsMandatory) {
		this.securityTypeIdsPledgorDetailsMandatory = securityTypeIdsPledgorDetailsMandatory;
	}

	public void validate(EAIMessage eaimessage) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) eaimessage.getMsgBody();
		String secType = msg.getSecurityDetail().getSecurityType().getStandardCodeValue();
		Vector pledgors = msg.getPledgor();
		String source = eaimessage.getMsgHeader().getSource();

		if ((pledgors == null) || (pledgors.isEmpty())) {
			if (ArrayUtils.contains(this.securityTypeIdsPledgorDetailsMandatory, secType)) {
				throw new MandatoryFieldMissingException("PledgorDetail");
			}
			else {
				return;
			}
		}

		for (Iterator itr = pledgors.iterator(); itr.hasNext();) {
			Pledgor pledgor = (Pledgor) itr.next();

			if (!(pledgor.getCMSPledgorId() <= 0) || StringUtils.isNotEmpty(pledgor.getPledgorCIFSource())) {
				validator.validateString(pledgor.getPledgorCIFSource(), "PledgorDetail - PledgorCIFSource", true, 1, 4);

				validator.validateStdCode(new StandardCode(CategoryCodeConstant.CATEGROY_SYS_CODE, pledgor
						.getPledgorCIFSource()), source, "37");
			}
			if (IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(pledgor.getUpdateStatusIndicator())
					&& IEaiConstant.CHANGE_INDICATOR_YES.equals(pledgor.getChangeIndicator())) {
				validator.validateNumber(new Long(pledgor.getCMSPledgorId()), "PledgorDetail - CMSPledgorId", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
			}

			validator.validateString(pledgor.getCIF(), "PledgorDetail - CIF", true, 1, 20);

			validator.validateString(pledgor.getPledgorLegalName(), "PledgorDetail - PledgorLegalName", true, 1, 100);

			validator.rejectIfNull(pledgor.getIdInfo(), "PledgorDetail - IdInfo");

			validator.validateString(pledgor.getIdInfo().getIdNumber(), "PledgorDetail - IdInfo - IdNumber", true, 1,
					100);

			validator.validateStdCode(pledgor.getIdInfo().getIdType(), source, "ID_TYPE");

			validator.validateString(pledgor.getIdInfo().getCountryIssued(), "PledgorDetail - IdInfo - CountryIssued",
					false, 0, 2);

			validator.rejectIfNull(pledgor.getRelationship(), "PledgorDetail - Relationship");
			validator.validateStdCode(pledgor.getRelationship(), source, "RELATIONSHIP");

			validator.validateString(pledgor.getUpdateStatusIndicator(), "PledgorDetail - UpdateStatusIndicator",
					false, 0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(pledgor.getChangeIndicator(), "PledgorDetail - ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			validateCreditGrade(pledgor.getCreditGrade(), source);
		}	
	}

	private void validateCreditGrade(Vector creditGrades, String source) throws EAIMessageValidationException {
		if ((creditGrades == null) || (creditGrades.isEmpty())) {
			return;
		}

		for (Iterator itr = creditGrades.iterator(); itr.hasNext();) {
			PledgorCreditGrade credit = (PledgorCreditGrade) itr.next();

			validator.validateStdCode(credit.getCreditGradeType(), source, "18");

			validator.validateStdCode(credit.getCreditGradeCode(), source, "19");

			validator.validateDate(credit.getJDOCreditGradeEffectiveDate(),
					"PledgorCreditGrade - CreditGradeEffectiveDate", true);

			validator.validateString(credit.getCreditGradeReasonForChange(),
					"PledgorCreditGrade - CreditGradeReasonForChange", false, 1, 100);

			validator.validateString(credit.getUpdateStatusIndicator(), "PledgorCreditGrade - UpdateStatusIndicator",
					false, 0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(credit.getChangeIndicator(), "PledgorCreditGrade - ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

		}
	}
}
