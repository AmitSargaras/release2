/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/ccreceipt/ForwardCCReceiptCommand.java,v 1.2.18.1 2006/12/14 12:22:26 jychong Exp $
 */

package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: jychong $
 * @version $Revision: 1.2.18.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class ForwardCCReceiptCommand extends AbstractCommand implements ICommonEventConstant {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.ICommand#doExecute(java.util.HashMap)
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String forwardUser = (String) map.get("forwardUser");
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			// DefaultLogger.debug(this, "checkListTrxVal before forward-----> "
			// + checkListTrxVal);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ctx.setTrxCountryOrigin(CheckListUtil.getCCTrxCountry(checkListTrxVal.getStagingCheckList()));
			DefaultLogger.debug(this, "User forward to-----> " + forwardUser);
			OBCMSTrxRouteInfo forwardRouteInfo = new OBCMSTrxRouteInfo(forwardUser);
			long prevToAuthGroupTypeId = checkListTrxVal.getToAuthGroupTypeId();
			checkListTrxVal.setToAuthGroupTypeId((Long.valueOf(forwardRouteInfo.getMemberShipTypeID())).longValue());
			checkListTrxVal.setToAuthGId((Long.valueOf(forwardRouteInfo.getTeamID())).longValue());
			checkListTrxVal.setToUserId((Long.valueOf(forwardRouteInfo.getUserID())).longValue());
			if ((checkListTrxVal.getToAuthGroupTypeId() == ICMSConstant.TEAM_TYPE_FAM_USER)
					&& (prevToAuthGroupTypeId > checkListTrxVal.getToAuthGroupTypeId()))
			// Backward to FAM?
			{
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_BACKWARD);
			}
			else {
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_FORWARD);
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
			if (isCPCChecker) {
				DefaultLogger.debug(this, "<<<<<<<<<< makerstatus edit");
				String makerStatus = (String) map.get("makerStatus");
				ICheckList chkList = checkListTrxVal.getStagingCheckList();
				chkList.setRemarks(makerStatus);
				checkListTrxVal.setStagingCheckList(chkList);
			}

			checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);
			/*
			 * if(ICMSConstant.STATE_PENDING_MGR_VERIFY.equals(checkListTrxVal.getStatus
			 * ())){ checkListTrxVal =
			 * proxy.managerApproveCheckListReceipt(ctx,checkListTrxVal); } else
			 * { checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx,
			 * checkListTrxVal); }
			 */
			resultMap.put("request.ITrxValue", checkListTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal after forward ----->"
			// + checkListTrxVal);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "forwardUser", "java.lang.String", REQUEST_SCOPE },
				{ "makerStatus", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}
}
