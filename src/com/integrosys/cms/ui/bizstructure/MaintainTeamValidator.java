package com.integrosys.cms.ui.bizstructure;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/17 15:39:16 $ Tag: $Name: $
 */
public class MaintainTeamValidator {

	public static ActionErrors validateInput(MaintainTeamForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		try {
			if (!(errorCode = Validator.checkString(form.getTeamName(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("nameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"30"));
			}
			if (!(errorCode = Validator.checkString(form.getTeamDesc(), false, 1, 45)).equals(Validator.ERROR_NONE)) {
				errors.add("descError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"45"));
			}
			/*if ((form.getCtryCode() == null) || (form.getCtryCode().length <= 0)) {
				errors.add("ctryCodeError", new ActionMessage("error.team.ctryCode"));
			}
			if (BizStructureHelper.requireOrgCodeFilter()) {
				if ((form.getOrgGroupCode() == null) || form.getOrgGroupCode().length <= 0) {
					errors.add("orgGroupCodeError", new ActionMessage("error.team.orgGroupCode"));
				}
			}
			
			if (BizStructureHelper.requireBizSegment()) {
				if ((form.getSgmtCode() == null) || (form.getSgmtCode().length <= 0)) {
					errors.add("sgmtCodeError", new ActionMessage("error.team.sgmtCode"));
				}
			}
			
			if (BizStructureHelper.requireCMSSegment()) {
				if ((form.getSgmtCodeCMS() == null) || (form.getSgmtCodeCMS().length <= 0)) {
					errors.add("cmsSgmtCodeError", new ActionMessage("error.team.cmsSgmtCode"));
				}
			}
			if ((form.getOrgCode() == null) || (form.getOrgCode().length <= 0)) {
				errors.add("orgCodeError", new ActionMessage("error.team.orgCode"));
			}*/
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
		}
		return errors;

	}

}