/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/06/09 09:35:29 $ Tag: $Name: $
 */
public class MaintainCCCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	private ICheckListTemplateProxyManager checklistTemplateProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public void setCheckListTemplateProxyManager(ICheckListTemplateProxyManager checklistTemplateProxyManager) {
		this.checklistTemplateProxyManager = checklistTemplateProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public MaintainCCCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner", FORM_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ "law", "java.lang.String", REQUEST_SCOPE },
				{ "dispatchToMaintain", "java.lang.String", REQUEST_SCOPE },
				{ "sameCountryInd", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "wip_doc_loc", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "error_code", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Inside doExecute()");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		ICheckList checkList;
		OBCCCheckListOwner owner = (OBCCCheckListOwner) map.get("ownerObj");
		String sameCountryInd = (String) map.get("sameCountryInd");
		String legalConstitution = (String) map.get("legalConstitution");
		String limitBkgLoc = (String) map.get("limitBkgLoc");
		String orgCode = (String) map.get("orgCode");
		String law = (String) map.get("law");
		String tCheckListID = (String) map.get("checkListID");
		long checkListID = Long.parseLong(tCheckListID);

		try {
			int wip = this.checklistProxyManager.allowCheckListTrx(owner);
			if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
				resultMap.put("wip", "wip");
			}
			else if (ICMSConstant.HAS_PENDING_DOC_LOC_TRX == wip) {
				resultMap.put("wip_doc_loc", "wip_doc_loc");
			}
			else if ("false".equals(sameCountryInd)) {
				AccessDeniedException e = new AccessDeniedException(""
						+ "Your belonging team does not have access to maintain checklist belonging to country "
						+ map.get("limitBkgLoc") + " !");
				e.setErrorCode(ICMSErrorCodes.DDAP_DIFF_COUNTRY);
				throw e;
			}
			else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					resultMap.put("limitBkgLoc", limitBkgLoc);
					if ("null".equals(orgCode)) {
						orgCode = null;
					}

					checkList = this.checklistProxyManager.getDefaultCCCheckList(owner, legalConstitution,
							new OBBookingLocation(limitBkgLoc, orgCode), law);
					resultMap.put("checkListTrxVal", null);
				}
				else {
					ICheckListTrxValue checkListTrxVal = this.checklistProxyManager.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();

					if (checkList.getTemplateID() <= 0) {

						ITemplate template = this.checklistTemplateProxyManager.getCCTemplate(law, legalConstitution,
								limitBkgLoc);
						if (template != null) {
							checkList.setTemplateID(template.getTemplateID());
						}
					}

					resultMap.put("checkListTrxVal", checkListTrxVal);
				}

				if (checkList.getCheckListItemList() != null) {
					Arrays.sort(checkList.getCheckListItemList());
				}

				String checkListStatus = checkList.getCheckListStatus();
				// perform sorting only if checklist status is not NEW
				if ((checkListStatus == null)
						|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
					ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					checkList.setCheckListItemList(sortedItems);
				}

				String dispatchToMaintain = ("Y".equals(map.get("dispatchToMaintain")) || "Y".equals(checkList
						.getDisableCollaborationInd())) ? "Y" : "N";
				checkList.setDisableCollaborationInd(dispatchToMaintain);

				String event = (String) map.get("event");
				if ("delete".equals(event)) {
					((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
				}

				resultMap.put("checkList", checkList);
			}
		}
		catch (TemplateNotSetupException e) {
			if ((ICMSErrorCodes.NO_LEGAL_CONSTITUTION.equals(e.getErrorCode()))
					|| (ICMSErrorCodes.NO_INSTR_BKG_LOCATION.equals(e.getErrorCode()))) {
				resultMap.put("error_code", e.getErrorCode());
				resultMap.put("checklist_error", "true");
			}
			else {
				resultMap.put("no_template", "true");
			}
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to retrieve checklist template", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("failed to maintain cc checklist", ex);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
