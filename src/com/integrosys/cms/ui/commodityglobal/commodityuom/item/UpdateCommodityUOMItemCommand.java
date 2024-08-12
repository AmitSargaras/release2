/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/UpdateCommodityUOMItemCommand.java,v 1.3 2004/08/19 02:11:02 hshii Exp $
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

public class UpdateCommodityUOMItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityUOMItemObj", "com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityUOMTrxValue", "com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue",
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

		int index = Integer.parseInt((String) map.get("indexID"));
		IUnitofMeasureTrxValue trxValue = (IUnitofMeasureTrxValue) map.get("commodityUOMTrxValue");
		IUnitofMeasure obj = (IUnitofMeasure) map.get("commodityUOMItemObj");
		IUnitofMeasure[] uomList = trxValue.getStagingUnitofMeasure();
		boolean duplicated = false;
		boolean sameAsStandard = false;
		String uomName = obj.getName().trim().toUpperCase();
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
			IUnitofMeasure[] existingArray = trxValue.getStagingUnitofMeasure();
			IProfile profile = obj.getCommodityProfile();
			String uomSubType = profile.getProductSubType().trim().toUpperCase();
			if (existingArray != null) {
				for (int i = 0; (i < existingArray.length) && !duplicated; i++) {
					if ((index != i) && uomName.equals(existingArray[i].getName().trim().toUpperCase())) {
						IProfile tempProfile = existingArray[i].getCommodityProfile();
						if (uomSubType.equals(tempProfile.getProductSubType().trim().toUpperCase())) {
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
			uomList[index] = obj;
			Arrays.sort(uomList, new UnitofMeasureComparator(UnitofMeasureComparator.BY_PROFILE));

			trxValue.setStagingUnitofMeasure(uomList);
			result.put("commodityUOMTrxValue", trxValue);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
