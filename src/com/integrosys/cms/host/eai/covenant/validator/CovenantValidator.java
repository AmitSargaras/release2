package com.integrosys.cms.host.eai.covenant.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.covenant.CovenantMessageBody;
import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;

/**
 * EAI Message Validator to validate instance of <tt>Covenant</tt> and
 * <tt>CovenantItem</tt>
 * @author Thurein
 * @author Chong Jun Yong
 * 
 */
public class CovenantValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] sourceIdsFrequencyInfoApplicable;

	public void setSourceIdsFrequencyInfoApplicable(String[] sourceIdsFrequencyInfoApplicable) {
		this.sourceIdsFrequencyInfoApplicable = sourceIdsFrequencyInfoApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		CovenantMessageBody covenantMsgBdy = (CovenantMessageBody) scimsg.getMsgBody();
		RecurrentDoc recurrentDoc = (RecurrentDoc) covenantMsgBdy.getRecurrentDoc();

		if (recurrentDoc == null) {
			throw new MandatoryFieldMissingException("Covenant");
		}

		validator.validateString(recurrentDoc.getLOSAANumber(), "Covenant - LOSAANumber", true, 1, 35);

		if (recurrentDoc.getConvenantItems() == null || recurrentDoc.getConvenantItems().isEmpty()) {
			throw new MandatoryFieldMissingException("CovenantItem");
		}

		checkCovenantItems(recurrentDoc.getConvenantItems(), scimsg.getMsgHeader().getSource());
	}

	private void checkCovenantItems(Vector covenantItems, String source) {
		for (Iterator iter = covenantItems.iterator(); iter.hasNext();) {
			CovenantItem covenantItem = (CovenantItem) iter.next();

			boolean cmsCovenantItemIdMandatory = IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(covenantItem
					.getUpdateStatusIndicator())
					&& IEaiConstant.CHANGE_INDICATOR_YES.equals(covenantItem.getChangeIndicator());
			validator.validateNumber(covenantItem.getCMSCovenantItemID(), "CovenantItem - CMSCovenantItemID",
					cmsCovenantItemIdMandatory, 1, IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);

			validator.validateNumber(covenantItem.getLosConditionPrecedentId(), "CovenantItem - LOSCPID", false, 0,
					IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);

			validator.validateString(covenantItem.getCovenantCondition(), "CovenantItem - CovenantCondition", true, 1,
					400);

			validator.validateDate(covenantItem.getDocEndDate(), "CovenantItem - DocEndDate", true);

			if (ArrayUtils.contains(this.sourceIdsFrequencyInfoApplicable, source)) {
				validator.validateString(covenantItem.getOneOff(), "CovenantItem - OneOffInd", true, 1, 1,
						EaiConstantCla.getAllowedValuesYesNo());

				boolean frequencyMandatory = ICMSConstant.FALSE_VALUE.equals(covenantItem.getOneOff());
				validator.validateNumber(covenantItem.getFrequencyUnit(), "CovenantItem - FrequencyUnit",
						frequencyMandatory, 1, 999);

				validator.validateString(covenantItem.getFrequencyUom(), "CovenantItem - FrequencyUOM",
						frequencyMandatory, 1, 1, EaiConstantCla.getAllowedValuesFrequencyUnit());
			}

			validator.validateString(covenantItem.getRemarks(), "CovenantItem - Remarks", false, 0, 400);

			validator.validateString(covenantItem.getUpdateStatusIndicator(), "CovenantItem - UpdateStatusIndicator",
					false, 0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(covenantItem.getChangeIndicator(), "CovenantItem - ChangeStatusIndicator", false,
					0, 1, EaiConstantCla.getAllowedValuesYesNo());
		}
	}
}
