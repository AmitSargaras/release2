package com.integrosys.cms.ui.feed.bond.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.feed.bond.BondCommand;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This class implements command
 */
public class PrepareBondItemCommand extends BondCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ BondItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", FORM_SCOPE },
				{ "ratingTypeList", "java.util.List", REQUEST_SCOPE }, };
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			// default country currency to the users country
			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			/*String currencyCode = CurrencyList.getInstance().getCurrencyCodeByCountry(user.getCountry());
			resultMap.put(BondItemForm.MAPPER, currencyCode);*/

			resultMap.put("ratingTypeList", getRatingTypeList());

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private List getRatingTypeList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("RATING_TYPE");
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
