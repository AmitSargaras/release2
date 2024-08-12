package com.integrosys.cms.host.eai.customer.validator;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.CustomerRelationshipMessageBody;
import com.integrosys.cms.host.eai.customer.bus.IEntityRelationship;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerRelationshipValidator implements IEaiMessageValidator {

	public CustomerRelationshipValidator() {

	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {

		CustomerRelationshipMessageBody msgBody = ((CustomerRelationshipMessageBody) scimessage.getMsgBody());

		String source = scimessage.getMsgHeader().getSource();

		EaiValidationHelper vH = EaiValidationHelper.getInstance();

		vH.validateString(msgBody.getEntityRelationshipHeader().getMainCifNo(), "MainCIFNo", true, 1, 20);

		checkCIFSource(vH, msgBody.getEntityRelationshipHeader().getMainCifSource(), source);

		vH.validateString(msgBody.getEntityRelationshipHeader().getMainCifName(), "MainCIFName", false, 1, 100);

		java.util.Vector v = msgBody.getEntityRelationshipDetail().getLinkage();

		for (int i = 0; i < v.size(); i++) {

			IEntityRelationship sdr = (IEntityRelationship) v.get(i);

			vH.validateString(sdr.getCifNo(), "CIFNo", true, 1, 20);
			// vH.validateString(sdr.getCifSource(), "CIFSource", true, 1, 10);

			checkCIFSource(vH, sdr.getCifSource(), source);

			vH.validateString(sdr.getCifName(), "CIFName", false, 1, 100);

			// Validate Relationship
			vH.validateStdCode(sdr.getRelationship(), source, "RELATIONSHIP");

			// Validate Percentage Own
			// 8 - Shareholder
			if ("8".equals(sdr.getRelationship().getStandardCodeValue())) {
				// validate Decimal
				vH.validateDoubleDigit("" + sdr.getPercentOwn(), "PercentageOwn", true, 3, 4, false);
			}

		}
	}

	public void checkCIFSource(EaiValidationHelper vH, String s, String source) throws EAIMessageValidationException {
		StandardCode sc = new StandardCode();

		sc.setStandardCodeNumber("LE_ID_TYPE");
		sc.setStandardCodeValue(s);

		vH.validateStdCode(sc, source, "LE_ID_TYPE");
	}

}
