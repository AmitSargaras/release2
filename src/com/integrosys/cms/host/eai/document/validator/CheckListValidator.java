package com.integrosys.cms.host.eai.document.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.EAIDocumentInputHelper;
import com.integrosys.cms.host.eai.document.InvalidCheckListItemStatusException;
import com.integrosys.cms.host.eai.document.NoCheckListItemsFoundException;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;

/**
 * EAI Message Validator for checklist
 * 
 * @author shphoon
 * @author Chong Jun Yong
 * @author Iwan Satria
 * @since 1.1
 */
public class CheckListValidator implements IEaiMessageValidator {

	public CheckListValidator() {
	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		CheckList checkList = EAIDocumentInputHelper.getInstance().retrieveCheckList(scimessage);
		EaiValidationHelper vH = EaiValidationHelper.getInstance();

		if (checkList == null) {
			throw new MandatoryFieldMissingException("CheckList");
		}
		else {
			CCChecklist cc = checkList.getCcChecklist();
			SCChecklist sc = checkList.getScChecklist();

			vH.validateMandatoryFieldForString("LOSAANumber", checkList.getAANumber());

			vH.validateMandatoryFieldForString("ChecklistType", checkList.getChecklistType());

			if (checkList.getCheckListID() <= 0) {
				vH.validateMandatoryFieldForLong("TemplateID", checkList.getTemplateID());
				if (checkList.getTemplateID() <= 0) {
					throw new MandatoryFieldMissingException("TemplateID");
				}
			}

			if (!ArrayUtils.contains(DocumentConstantsCla.getChecklistTypes(), checkList.getChecklistType())) {
				throw new FieldValueNotAllowedException("Checklist Type", checkList.getChecklistType(),
						DocumentConstantsCla.getChecklistTypes());
			}

			if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checkList.getChecklistType())) {
				vH.validateMandatoryFieldForString("CIFNo", cc.getCIFNo());
				vH.validateMandatoryFieldForString("CustomerType", cc.getCustomerType());

				if (!ArrayUtils.contains(DocumentConstantsCla.getChecklistCategories(), cc.getCustomerType())) {
					throw new FieldValueNotAllowedException("Customer Type", cc.getCustomerType(),
							DocumentConstantsCla.getChecklistCategories());
				}
			}

			if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checkList.getChecklistType())) {
				vH.validateMandatoryFieldForString("LOSSecurityId", sc.getLOSSecurityId());
			}

			Vector col = checkList.getCheckListItem();
			if (col == null) {
				throw new NoCheckListItemsFoundException(scimessage.getMsgHeader());
			}

			for (Iterator itr = col.iterator(); itr.hasNext();) {
				CheckListItem dc = (CheckListItem) itr.next();

				vH.validateMandatoryFieldForString("DocCode", dc.getDocCode());

				vH.validateMandatoryFieldForLong("DocNo", dc.getDocNo());
				if (dc.getDocNo() <= 0) {
					throw new MandatoryFieldMissingException("DocNo");
				}

				vH.validateString(String.valueOf(dc.getMandatoryInd()), "MandatoryInd", true, 1, 1, new String[] { "Y",
						"N" });

				vH.validateMandatoryFieldForString("Description", dc.getDescription());

				vH.validateMandatoryFieldForString("Status", dc.getStatus());

				if (dc.getStatus().equalsIgnoreCase(ICMSConstant.STATE_ITEM_RECEIVED)) {
					vH.validateDate(dc.getReceivedDate(), "ReceivedDate", true);
				}
				else if (dc.getStatus().equalsIgnoreCase(ICMSConstant.STATE_ITEM_WAIVED)) {
					vH.validateDate(dc.getWaivedDate(), "WaivedDate", true);
				}
				else if (!dc.getStatus().equalsIgnoreCase(ICMSConstant.STATE_ITEM_AWAITING)) {
					throw new InvalidCheckListItemStatusException(dc.getDocNo(), dc.getStatus());
				}
			}
		}
	}
}
