/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/ReadCalPositionCommand.java,v 1.5 2004/08/23 03:23:12 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/23 03:23:12 $ Tag: $Name: $
 */
public class ReadCalPositionCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
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
		return (new String[][] { { "ARM_Code", "java.lang.String", SERVICE_SCOPE }, });
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		String armCode = "";
		if (limitProfile != null) {
			try {
				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				HashMap famMap = famMap = limitProxy.getFAMName(limitProfile.getLimitProfileID());
				armCode = (String) famMap.get(ICMSConstant.FAM_CODE);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		result.put("ARM_Code", armCode);

		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		String[] teamCountry = team.getCountryCodes();
		String customerCountry = limitProfile.getOriginatingLocation().getCountryCode();
		boolean isCustomerBelongTeam = false;
		if (customerCountry != null) {
			for (int i = 0; (i < teamCountry.length) && !isCustomerBelongTeam; i++) {
				if (customerCountry.equals(teamCountry[i])) {
					isCustomerBelongTeam = true;
				}
			}
		}
		else {
			isCustomerBelongTeam = true;
		}
		if (!isCustomerBelongTeam) {
			throw new AccessDeniedException("BCA Booking Location is not belongs to team location.");
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
