/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/AddCommodityUOMItemCommand.java,v 1.3 2004/08/19 02:11:02 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureComparator;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;
import com.integrosys.cms.ui.commodityglobal.commodityuom.MarketUOMList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 02:11:02 $ Tag: $Name: $
 */

public class AddCommodityUOMItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityUOMTrxValue", "com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue",
						SERVICE_SCOPE },
				{ "commodityUOMItemObj", "com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityUOMTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue", SERVICE_SCOPE }, });
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

		IUnitofMeasureTrxValue trxValue = (IUnitofMeasureTrxValue) map.get("commodityUOMTrxValue");
		IUnitofMeasure obj = (IUnitofMeasure) map.get("commodityUOMItemObj");

		boolean duplicated = false;
		boolean sameAsStandard = false;
		String uomName = obj.getName().trim().toUpperCase();
		IUnitofMeasure[] existingArray = trxValue.getStagingUnitofMeasure();
		Collection marketUOMValue = MarketUOMList.getInstance().getMarketUOMValue();
		Iterator itr = marketUOMValue.iterator();
		while (itr.hasNext() && !sameAsStandard) {
			String standard = (String) itr.next();
			if (uomName.equalsIgnoreCase(standard)) {
				sameAsStandard = true;
			}
		}
		if (sameAsStandard) {
			exceptionMap.put("unitOfMeasure", new ActionMessage("error.commodityuom.samestandard"));
		}
		else {
			IProfile profile = obj.getCommodityProfile();
			String subType = profile.getProductSubType().trim().toUpperCase();

			if (existingArray != null) {
				for (int i = 0; (i < existingArray.length) && !duplicated; i++) {
					if (uomName.equals(existingArray[i].getName().trim().toUpperCase())) {
						IProfile tmpProfile = existingArray[i].getCommodityProfile();
						if (subType.equals(tmpProfile.getProductSubType().trim().toUpperCase())) {
							duplicated = true;
						}
					}
				}
			}
		}
		if (duplicated) {
			exceptionMap.put("unitOfMeasure", new ActionMessage("error.commodityuom.duplicate"));
		}
		if (!duplicated && !sameAsStandard) {
			int arrayLength = 0;
			if (existingArray != null) {
				arrayLength = existingArray.length;
			}

			IUnitofMeasure[] newArray = new IUnitofMeasure[arrayLength + 1];
			if (existingArray != null) {
				System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
			}

			newArray[arrayLength] = obj;
			Arrays.sort(newArray, new UnitofMeasureComparator(UnitofMeasureComparator.BY_PROFILE));

			trxValue.setStagingUnitofMeasure(newArray);
			result.put("commodityUOMTrxValue", trxValue);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
