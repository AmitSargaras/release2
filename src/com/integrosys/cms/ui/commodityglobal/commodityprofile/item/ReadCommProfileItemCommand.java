/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/ReadCommProfileItemCommand.java,v 1.3 2006/10/12 03:17:54 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.list.CommProfileListAction;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/10/12 03:17:54 $ Tag: $Name: $
 */

public class ReadCommProfileItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "commProfileTrxValue", "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
						SERVICE_SCOPE }, });
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
				{ "commProfileItemObj", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", FORM_SCOPE },
				{ "stageProfile", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", REQUEST_SCOPE },
				{ "actualProfile", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", REQUEST_SCOPE },
				{ "isTransferable", "java.lang.Boolean", REQUEST_SCOPE },
				{ "supplierMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "buyerMap", "java.util.HashMap", SERVICE_SCOPE }, });
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

		long ref = Long.parseLong((String) map.get("indexID"));
		long stageProfileID = ICMSConstant.LONG_INVALID_VALUE;
		String from_event = (String) map.get("from_event");
		IProfileTrxValue trxValue = (IProfileTrxValue) map.get("commProfileTrxValue");
		IProfile[] list = trxValue.getStagingProfile();
		IProfile profile = new OBProfile();
		if ((from_event != null) && (from_event.equals(CommProfileListAction.EVENT_PROCESS))) {
			profile = getItem(list, ref);
			if (profile == null) {
				profile = getItem(trxValue.getProfile(), ref);
			}
			else {
				stageProfileID = profile.getProfileID();
			}
			IProfile stageObj = getItem(list, ref);
			IProfile actualObj = getItem(trxValue.getProfile(), ref);
			result.put("stageProfile", stageObj);
			result.put("actualProfile", actualObj);
		}
		else {
			profile = list[(int) ref];
			stageProfileID = profile.getProfileID();
		}

		DefaultLogger.debug(this, "profile is: " + profile.getProfileID());
		DefaultLogger.debug(this, "stage profile is: " + stageProfileID);

		boolean isTransferable = true;
		if (ICMSConstant.LONG_INVALID_VALUE != stageProfileID) {
			try {
				isTransferable = CommodityMaintenanceProxyFactory.getProxy().isRICTypeTransferable(stageProfileID);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		result.put("isTransferable", new Boolean(isTransferable));

		result.put("commProfileItemObj", profile);
		result.put("from_event", from_event);

		HashMap supplierMap = new HashMap();
		ISupplier[] supplierList = profile.getSuppliers();
		if (supplierList != null) {
			for (int i = 0; i < supplierList.length; i++) {
				supplierMap.put(supplierList[i].getName(), supplierList[i]);
			}
		}
		result.put("supplierMap", supplierMap);

		HashMap buyerMap = new HashMap();
		IBuyer[] buyerList = profile.getBuyers();
		if (buyerList != null) {
			for (int i = 0; i < buyerList.length; i++) {
				buyerMap.put(buyerList[i].getName(), buyerList[i]);
			}
		}
		result.put("buyerMap", buyerMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IProfile getItem(IProfile temp[], long commonRef) {
		IProfile item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCommonRef() == commonRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}
