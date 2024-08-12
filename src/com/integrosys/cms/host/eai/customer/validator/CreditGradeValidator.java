package com.integrosys.cms.host.eai.customer.validator;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.CreditGrade;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;
import com.integrosys.cms.host.eai.customer.MainProfileDetails;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class CreditGradeValidator implements IEaiMessageValidator {

	private final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	public CreditGradeValidator() {
	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		MainProfileDetails mpd = EAICustomerHelper.getInstance().retrieveMainProfileDetail(scimessage);

		Vector creditGrades = mpd.getCreditGrade();

		if (creditGrades == null) {
			return;
		}

		// todo check if it correct to get the source id from here
		String source = scimessage.getMsgHeader().getSource();

		for (Iterator itr = creditGrades.iterator(); itr.hasNext();) {
			CreditGrade cg = (CreditGrade) itr.next();
			validator.validateStdCodeAllowNull(cg.getCreditGradeCode(), source, "19");
			validator.validateStdCodeAllowNull(cg.getCreditGradeType(), source, "18");
		}

	}
}
