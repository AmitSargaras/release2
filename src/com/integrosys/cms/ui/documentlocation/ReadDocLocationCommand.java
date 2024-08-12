/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/ReadDocLocationCommand.java,v 1.11 2005/10/24 08:12:17 hshii Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSummary;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation;
import com.integrosys.cms.app.documentlocation.proxy.DocumentLocationProxyManagerFactory;
import com.integrosys.cms.app.documentlocation.proxy.IDocumentLocationProxyManager;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;
import com.integrosys.cms.app.documentlocation.trx.OBCCDocumentLocationTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/10/24 08:12:17 $ Tag: $Name: $
 */

public class ReadDocLocationCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "docLocationList", "java.util.Collection", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
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
		return (new String[][] {
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", FORM_SCOPE },
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", SERVICE_SCOPE },
				{ "docLocTrxValue", "com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue",
						SERVICE_SCOPE }, { "session.docOriginCountry", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String strIndex = (String) map.get("index");
		int index = 0;
		if ((strIndex != null) && !strIndex.equals("")) {
			index = Integer.parseInt((String) map.get("index"));
		}

		String event = (String) map.get("event");
		IDocumentLocationProxyManager proxyManager = DocumentLocationProxyManagerFactory.getProxyManager();
		CCDocumentLocationSummary summary = null;
		List list = (List) map.get("docLocationList");
		if ((list != null)
				&& (event.equals(DocumentLocationAction.EVENT_PREPARE_UPDATE_DOC_LOCATION) || event
						.equals(DocumentLocationAction.EVENT_VIEW))) {
			summary = (CCDocumentLocationSummary) list.get(index);
		}
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		try {
			if (DocumentLocationAction.EVENT_PREPARE_UPDATE_DOC_LOCATION.equals(event)) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				ICCCheckListOwner owner = null;
				if (limitProfile != null) {
					owner = new OBCCCheckListOwner(limitProfile.getLimitProfileID(), summary.getSummary()
							.getSubProfileID(), summary.getDocumentLocationCategory());
				}
				else {
					owner = new OBCCCheckListOwner(ICMSConstant.LONG_MIN_VALUE, summary.getSummary().getSubProfileID(),
							summary.getDocumentLocationCategory());
				}
				int wip = proxy.allowCheckListTrx(owner);
				if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
					result.put("wip_checklist", "wip_checklist");
				}
				else if (ICMSConstant.HAS_PENDING_DOC_LOC_TRX == wip) {
					result.put("wip", "wip");
				}
			}
			if ((result.get("wip") == null) && (result.get("wip_checklist") == null)) {
				ICCDocumentLocation docLoc = null;
				ICCDocumentLocationTrxValue docLocTrxValue = new OBCCDocumentLocationTrxValue();
				if (event.equals(DocumentLocationAction.EVENT_EDIT_REJECTED_DOC_LOCATION)
						|| event.equals(DocumentLocationAction.EVENT_PREPARE_CLOSE_DOC_LOCATION)
						|| event.equals(DocumentLocationAction.EVENT_PROCESS)
						|| event.equals(DocumentLocationAction.EVENT_TRACK_DOC_LOCATION)) {
					String trxID = (String) map.get("trxID");
					docLocTrxValue = proxyManager.getCCDocumentLocationByTrxID(trxID);
				}
				else {
					DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<< calling proxy to get document location..... ");
					if (limitProfile != null) {
						docLocTrxValue = proxyManager.getCCDocumentLocationTrxValue(limitProfile.getLimitProfileID(),
								summary.getDocumentLocationCategory(), summary.getSummary().getSubProfileID());
					}
					else {
						docLocTrxValue = proxyManager.getCCDocumentLocationTrxValue(ICMSConstant.LONG_MIN_VALUE,
								summary.getDocumentLocationCategory(), summary.getSummary().getSubProfileID());
					}
				}
				if (event.equals(DocumentLocationAction.EVENT_TRACK_DOC_LOCATION)) {
					ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
					ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
					String strUser = null;
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
									strUser = "checker";
									break TOP_LOOP;
								}
								else if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CPC_MAKER) {
									strUser = "maker";
								}
							}
						}
					}
					result.put("strUser", strUser);
				}

				if (docLocTrxValue != null) {
					result.put("docLocTrxValue", docLocTrxValue);
					if (DocumentLocationAction.EVENT_PREPARE_UPDATE_DOC_LOCATION.equals(event)
							|| DocumentLocationAction.EVENT_VIEW.equals(event)) {
						docLoc = docLocTrxValue.getCCDocumentLocation();
					}
					else {
						docLoc = docLocTrxValue.getStagingCCDocumentLocation();
					}
				}
				else {
					summary = (CCDocumentLocationSummary) list.get(index);
					DefaultLogger.debug(this, "--------------- CCDocumentLocationSummary: " + summary);
					docLoc = new OBCCDocumentLocation();
					docLoc.setCustomerID(summary.getSummary().getSubProfileID());
					docLoc.setCustomerType(summary.getSummary().getCustomerSegmentCode());

					docLoc.setDocLocationCategory(summary.getDocumentLocationCategory());
					// docLoc.setDocLocationID();
					docLoc.setLegalName(summary.getLegalName());
					docLoc.setLegalRef(summary.getLegalID());
					if (limitProfile != null) {
						docLoc.setLimitProfileID(limitProfile.getLimitProfileID());
					}
					docLoc.setOriginatingLocation(new OBBookingLocation(summary.getDocumentLocationCountry(), summary
							.getDocumentOrgCode()));
					// docLoc.setOriginatingLocation();
					result.put("docLocTrxValue", null);
				}
				result.put("session.docOriginCountry", docLoc.getOriginatingLocation().getCountryCode());
				result.put("docLocationObj", docLoc);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
