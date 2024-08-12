/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/RefreshCalPositionCommand.java,v 1.4 2004/08/23 03:23:12 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/23 03:23:12 $ Tag: $Name: $
 */
public class RefreshCalPositionCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "productSubType", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "commodityDealObj", "java.util.HashMap", FORM_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE }, });
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

		HashMap calPositionMap = new HashMap();
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		String limitStr = (String) map.get("limitID");
		if ((limitStr != null) && (limitStr.length() > 0)) {
			long limitID = Long.parseLong((String) map.get("limitID"));
			ILimit[] limitList = limitProfile.getLimits();
			if (limitList != null) {
				boolean found = false;
				for (int i = 0; !found && (i < limitList.length); i++) {
					if (limitList[i].getLimitID() == limitID) {
						found = true;
						calPositionMap.put("limit", limitList[i]);
					}
				}
			}
		}
		long securityID = Long.parseLong((String) map.get("securityID"));
		try {
			ICommodityCollateral dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy()
					.getCollateral(securityID, true);
			calPositionMap.put("dealCollateral", dealCollateral);
			result.put("dealCollateral", dealCollateral);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		String productSubType = (String) map.get("productSubType");
		if ((productSubType != null) && (productSubType.length() > 0)) {
			profileID = Long.parseLong(productSubType);
			try {
				ICommodityMaintenanceProxy mainProxy = CommodityMaintenanceProxyFactory.getProxy();
				IProfile profile = mainProxy.getProfileByProfileID(profileID);
				calPositionMap.put("profileService", profile);
				result.put("profileService", profile);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		result.put("commodityDealObj", calPositionMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
