/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/ForwardCommodityDealCommand.java,v 1.8 2004/08/23 07:58:00 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/23 07:58:00 $ Tag: $Name: $
 */
public class ForwardCommodityDealCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "forwardUser", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue", REQUEST_SCOPE } });
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

		String forwardUser = (String) map.get("forwardUser");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		if (trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE_VERIFY)
				|| ICMSConstant.STATE_PENDING_CLOSE_VERIFY.equals(trxValue.getStatus())
				|| ICMSConstant.STATE_PENDING_UPDATE_VERIFY.equals(trxValue.getStatus())) {
			if ((forwardUser == null) || (forwardUser.length() == 0)) {
				exceptionMap.put("forwardUser", new ActionMessage("error.string.mandatory"));
			}
			if ((forwardUser != null) && (forwardUser.length() > 0)) {
				if (forwardUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_FAM_USER))) {
					trxValue.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_FAM_USER);
				}
				else {
					OBCMSTrxRouteInfo[] forwardList = trxValue.getNextRouteList();
					if (forwardList != null) {
						boolean found = false;
						for (int i = 0; !found && (i < forwardList.length); i++) {
							if (forwardList[i].getUserID().equals(forwardUser)) {
								found = true;
								DefaultLogger.debug(this, "forward list info: userid: " + forwardList[i].getUserID()
										+ "\tteam id: " + forwardList[i].getTeamID() + "\tteam type id: "
										+ forwardList[i].getMemberShipTypeID());
								if ((forwardList[i].getUserID() != null) && (forwardList[i].getUserID().length() > 0)) {
									trxValue.setToUserId(Long.parseLong(forwardList[i].getUserID()));
								}
								else {
									trxValue.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
								}
								if ((forwardList[i].getTeamID() != null) && (forwardList[i].getTeamID().length() > 0)) {
									trxValue.setToAuthGId(Long.parseLong(forwardList[i].getTeamID()));
								}
								else {
									trxValue.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
								}
								if ((forwardList[i].getMemberShipTypeID() != null)
										&& (forwardList[i].getMemberShipTypeID().length() > 0)) {
									trxValue.setToAuthGroupTypeId(Long.parseLong(forwardList[i].getMemberShipTypeID()));
								}
								else {
									trxValue.setToAuthGroupTypeId(ICMSConstant.LONG_INVALID_VALUE);
								}
							}
						}
					}
				}
			}
		}
		if (exceptionMap.isEmpty()) {
			ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
			try {
				if (trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)
						|| ICMSConstant.STATE_PENDING_CLOSE.equals(trxValue.getStatus())
						|| ICMSConstant.STATE_PENDING_UPDATE.equals(trxValue.getStatus())) {
					trxValue.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_FAM_USER);
					trxValue = proxy.checkerForwardCommodityDeal(ctx, trxValue);
				}
				else {
					trxValue = proxy.officerForwardCommodityDeal(ctx, trxValue);
				}
				result.put("request.ITrxValue", trxValue);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
