/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/NavigationGenChargeCommand.java,v 1.5 2006/11/11 15:21:00 jzhai Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.CollateralUiUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/11/11 15:21:00 $ Tag: $Name: $
 */
public class NavigationGenChargeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "next_page", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "tab", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "bcaLocationList", "java.util.ArrayList", SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE }, });
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

		String nextPage = (String) map.get("next_page");
		String from_page = (String) map.get("from_page");

		boolean isSSC = false;
		boolean isCPC = false;

		String forwardPage = nextPage + "_" + from_page;

		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);

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
					if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER
							||userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH) {
						isSSC = true;
						DefaultLogger.debug(this, "User is ssc maker...");
						break TOP_LOOP;
					}
					else if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CPC_MAKER) {
						isCPC = true;
						DefaultLogger.debug(this, "User is cpc maker...");
						break TOP_LOOP;
					}
				}
			}
		}

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String event = (String) map.get("event");
		if (AssetGenChargeAction.EVENT_UPDATE_RETURN.equals(event)) {
			nextPage = (String) map.get("tab");
		}

		ArrayList bcalocationList = (ArrayList) map.get("bcaLocationList");
		ArrayList list1 = new ArrayList();
		ArrayList list2 = new ArrayList();
		ArrayList list3 = new ArrayList();

		list1.add("IN");
		list1.add("MY");

		list2.add("IN");

		list3.add("MY");

		if (from_page.equals(AssetGenChargeAction.EVENT_PREPARE_UPDATE)
				|| from_page.equals(AssetGenChargeAction.EVENT_PROCESS_UPDATE)) {
			if (CollateralConstant.TAB_DEBTOR.equals(nextPage) || CollateralConstant.TAB_DRAWING_POWER.equals(nextPage)) {
				// if
				// ("IN".equals(itrxValue.getCollateral().getCollateralLocation
				// ()) ||
				//"MY".equals(itrxValue.getCollateral().getCollateralLocation())
				// )
				if (CollateralUiUtil.isCollateralCanUseTab(bcalocationList, list1)) {
					if (isCPC) {
						forwardPage = nextPage + "_" + AssetGenChargeAction.EVENT_READ;
					}
				}
				else {
					if (isSSC) {
						forwardPage = nextPage + "_" + AssetGenChargeAction.EVENT_READ;
					}
				}
			}
			else if (CollateralConstant.TAB_STOCK.equals(nextPage) || CollateralConstant.TAB_FAO.equals(nextPage)) {
				// if
				// ("IN".equals(itrxValue.getCollateral().getCollateralLocation
				// ())) {
				if (CollateralUiUtil.isCollateralCanUseTab(bcalocationList, list2)) {
					if (isCPC) {
						forwardPage = nextPage + "_" + AssetGenChargeAction.EVENT_READ;
					}
				}
				else {
					// if (isSSC &&
					// !"MY".equals(itrxValue.getCollateral().
					// getCollateralLocation())) {

					if (isSSC && !CollateralUiUtil.isCollateralCanUseTab(bcalocationList, list3)) {
						forwardPage = nextPage + "_" + AssetGenChargeAction.EVENT_READ;
					}
				}
			}
			else { // General Tab
				if (isSSC) {
					forwardPage = nextPage + "_" + AssetGenChargeAction.EVENT_READ;
				}
			}
		}

		if (from_page.equals(AssetGenChargeAction.EVENT_PROCESS)
				|| from_page.equals(AssetGenChargeAction.EVENT_PROCESS_UPDATE)) {
			String remarks = (String) map.get("remarks");
			if (((String) map.get("tab")).equals(CollateralConstant.TAB_GENERAL)) {
				DefaultLogger.debug(this, "<<<<<<<<<<<<< remarks: " + remarks + "\tremark is null: "
						+ String.valueOf(remarks == null));
				ITrxContext trxContext = itrxValue.getTrxContext();
				trxContext.setRemarks(remarks);
				itrxValue.setTrxContext(trxContext);
				result.put("serviceColObj", itrxValue);
			}
		}

		result.put("forwardPage", forwardPage);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
