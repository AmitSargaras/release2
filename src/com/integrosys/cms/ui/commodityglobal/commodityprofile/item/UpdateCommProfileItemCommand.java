/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/UpdateCommProfileItemCommand.java,v 1.4 2004/11/17 02:34:19 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileComparator;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/17 02:34:19 $ Tag: $Name: $
 */

public class UpdateCommProfileItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commProfileTrxValue", "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
						SERVICE_SCOPE },
				{ "commProfileItemObj", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commProfileTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue", SERVICE_SCOPE }, });
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

		int index = Integer.parseInt((String) map.get("indexID"));
		IProfileTrxValue trxValue = (IProfileTrxValue) map.get("commProfileTrxValue");
		IProfile obj = (IProfile) map.get("commProfileItemObj");

		boolean duplicated = false;
		String category = obj.getCategory().trim().toUpperCase();
		String productType = obj.getProductType().trim().toUpperCase();
		String subType = obj.getProductSubType().trim().toUpperCase();

		IProfile[] existingArray = trxValue.getStagingProfile();

		if (existingArray != null) {
			for (int i = 0; (i < existingArray.length) && !duplicated; i++) {
				IProfile tmp = existingArray[i];
				if ((index != i) && category.equals(tmp.getCategory().trim().toUpperCase())
						&& productType.equals(tmp.getProductType().trim().toUpperCase())
						&& subType.equals(tmp.getProductSubType().trim().toUpperCase())) {
					duplicated = true;
				}
			}
		}
		if (duplicated) {
			exceptionMap.put("productSubType", new ActionMessage("error.commodityprofile.duplicate"));
		}
		else {
			existingArray[index] = obj;
			ProfileComparator c = new ProfileComparator(ProfileComparator.BY_CAT_PDTTYPE_SUBTYPE);
			Arrays.sort(existingArray, c);
			trxValue.setStagingProfile(existingArray);
			result.put("commProfileTrxValue", trxValue);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
