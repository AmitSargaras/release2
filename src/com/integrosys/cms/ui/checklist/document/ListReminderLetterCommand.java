package com.integrosys.cms.ui.checklist.document;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocDAO;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListReminderLetterCommand extends AbstractCommand implements ICommonEventConstant {

	public ListReminderLetterCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "preReminderMap", "java.util.HashMap[]", SERVICE_SCOPE },
				{ "postReminderMap", "java.util.HashMap[]", SERVICE_SCOPE },
				{ "canRetrieveLetterOfInstruction", "java.lang.Boolean", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		ICheckListProxyManager proxy = (ICheckListProxyManager) BeanHouse.get("checklistProxy");
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);

		boolean isSSCMaker = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_MAKER).equals(teamTypeMembershipID)
				||String.valueOf(ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH).equals(teamTypeMembershipID);

		if (limitProfile == null) {
			throw new AccessDeniedException("There is no Limit Profile / AA object in the session, "
					+ "you cannot proceed further.");
		}

		long cmsLimitProfileId = limitProfile.getLimitProfileID();

		ITatDocDAO tatDocDao = (ITatDocDAO) BeanHouse.get("tatDocDao");
		ITatDoc tatDoc = tatDocDao.getTatDocByLimitProfileID(ITatDocDAO.ACTUAL_TAT_DOC, cmsLimitProfileId);

		if (isSSCMaker) {
			resultMap.put("canRetrieveLetterOfInstruction", Boolean.TRUE);
		}
		else {
			resultMap.put("canRetrieveLetterOfInstruction", Boolean.valueOf(tatDoc != null
					&& tatDoc.getSolicitorInstructionDate() != null));
		}

		try {
			HashMap[] preReminderMap = proxy.getDetailsForPreDisbursementReminderLetter(cmsLimitProfileId);
			HashMap[] postReminderMap = proxy.getDetailsForPostDisbursementReminderLetter(cmsLimitProfileId);

			resultMap.put("preReminderMap", preReminderMap);
			resultMap.put("postReminderMap", postReminderMap);
		}
		catch (CheckListException e) {
			throw new CommandProcessingException("failed to get details of reminder letter for Limit Profile ["
					+ limitProfile + "]", e);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
