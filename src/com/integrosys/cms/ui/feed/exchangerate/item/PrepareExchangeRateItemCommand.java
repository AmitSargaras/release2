package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAO;
import com.integrosys.cms.app.feed.bus.forex.IForexDao;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;
import org.apache.struts.util.LabelValueBean;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * This class implements command
 */
public class PrepareExchangeRateItemCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { /*{ "currencyLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "currencyValues", "java.util.Collection", REQUEST_SCOPE },
				{ "restrictionTypeList", "java.util.List", SERVICE_SCOPE }*/};
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

			/*IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");

			IForexFeedEntry[] entriesArr = value.getStagingForexFeedGroup().getFeedEntries();

			CurrencyList currencyList = CurrencyList.getInstance();
			List currencyLabels = (ArrayList) new ArrayList(currencyList.getCurrencyLabels()).clone();
			List currencyValues = (ArrayList) new ArrayList(currencyList.getCountryValues()).clone();

			String currencyCode = null;
			String s = null;
			for (int i = 0; i < entriesArr.length; i++) {
				currencyCode = entriesArr[i].getBuyCurrency();
				for (Iterator iter = currencyValues.iterator(), iter2 = currencyLabels.iterator(); iter.hasNext();) {
					s = (String) iter.next();
					iter2.next();
					if (s.equals(currencyCode)) {
						iter.remove();
						iter2.remove();
						break;
					}
				}
			}

			currencyLabels.add(0, "Please Select");
			currencyValues.add(0, "");

			resultMap.put("currencyLabels", currencyLabels);
			resultMap.put("currencyValues", currencyValues);*/

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}
		
		//IForexDao forexDAO = (IForexDao)BeanHouse.get("forexDAO");
		//ICollateralDAO restriction = new CollateralDAO();
		//resultMap.put("restrictionTypeList", getRestrictionTypeList());

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
	
	/*private List getRestrictionTypeList() {
		 List lbValList = new ArrayList();
			try {
				DefaultLogger.debug(this, "inside getGoodsParentCodeList() method");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				//IForexDao forexDAO = (IForexDao)BeanHouse.get("forexDAO");
				List idList = (List) collateralDAO.getRestrictionTypeList();
				for (int i = 0; i < idList.size(); i++) {			
						String id =(String) idList.get(i);
						String val = (String) idList.get(i);
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			//return CommonUtil.sortDropdown(lbValList);
			return lbValList;
		}*/
}
