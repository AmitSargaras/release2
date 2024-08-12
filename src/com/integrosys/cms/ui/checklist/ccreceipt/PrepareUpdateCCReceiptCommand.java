/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/03/27 03:17:35 $ Tag: $Name: $
 */
public class PrepareUpdateCCReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdateCCReceiptCommand() {
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
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }, });
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
				{ "frame", "java.lang.String", SERVICE_SCOPE }, { "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkListObj", "com.integrosys.cms.app.checklist.bus.ICheckList", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String tCheckListID = (String) map.get("checkListID");
			long checkListID = Long.parseLong(tCheckListID);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckList checkList = null;
			OBCCCheckListOwner owner = (OBCCCheckListOwner) map.get("ownerObj");
			DefaultLogger.debug(this, "Owner: " + owner);

            String limitBkgLoc = (String)map.get("limitBkgLoc");
            ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
            boolean ddapAllowed = CheckListUtil.allowByDDAP(limitBkgLoc, team);

            if(!ddapAllowed) {
                AccessDeniedException e = new AccessDeniedException(
                        "Your belonging team does not have access to maintain checklist belonging to country " + limitBkgLoc + " !");
                e.setErrorCode(ICMSErrorCodes.DDAP_DIFF_COUNTRY);
                throw e;
            }

            ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			boolean isCPCChecker = false;
			TOP_LOOP: for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {// parse
																						// team
																						// membership
																						// to
																						// validate
																						// user
																						// first
				for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) { // parse
																										// team
																										// memebers
																										// to
																										// get
																										// the
																										// team
																										// member
																										// first
																										// .
																										// .
					if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
							.getUserID()) {
						if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CPC_CHECKER) {
							isCPCChecker = true;
							break TOP_LOOP;
						}
					}
				}
			}

			String event = (String) map.get("event");
			int wip = proxy.allowCheckListTrx(owner);
			DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			if ((ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) && (event != null)
					&& (event.equals("prepare_update") || (event.equals("view") && isCPCChecker))) {
				resultMap.put("wip", "wip");
			}
			else if ((ICMSConstant.HAS_PENDING_DOC_LOC_TRX == wip) && (event != null) && event.equals("prepare_update")) {
				resultMap.put("wip_doc_loc", "wip_doc_loc");
			}
			else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					resultMap.put("no_template", "true");
				}
				else {
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>getCheckList( Checklistid) " + checkListID);
					ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();
					ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					checkList.setCheckListItemList(sortedItems);
					resultMap.put("checkListTrxVal", checkListTrxVal);
				}
				resultMap.put("checkList", checkList);
				resultMap.put("checkListObj", checkList);
			}
		}
        catch (AccessDeniedException e) {
            throw e;
        }
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
