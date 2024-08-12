package com.integrosys.cms.host.eai.customer.validator;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;
import com.integrosys.cms.host.eai.customer.MainProfileDetails;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;

/**
 * Validator to validate customer id info object.
 * @author Thurein
 * @author Chong Jun Yong
 */

public class CustomerIDInfoValidator implements IEaiMessageValidator {

	private final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	public CustomerIDInfoValidator() {
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		MainProfileDetails mpd = EAICustomerHelper.getInstance().retrieveMainProfileDetail(scimsg);
		String source = scimsg.getMsgHeader().getSource();

		Vector idinfos = mpd.getMainProfile().getIdInfo();

		if (idinfos == null) {
			return;
		}

		for (Iterator itr = idinfos.iterator(); itr.hasNext();) {
			CustomerIdInfo cg = (CustomerIdInfo) itr.next();
			validator.validateStdCodeAllowNull(cg.getIdType(), source, "ID_TYPE");
		}
	}

}
