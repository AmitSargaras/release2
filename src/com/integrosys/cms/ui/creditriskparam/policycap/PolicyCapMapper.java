/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.OBPolicyCap;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PolicyCapMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public PolicyCapMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "policyCapMap", "java.util.HashMap", FORM_SCOPE }, // Collection
																		// of
																		// com.
																		// integrosys
																		// .cms.
																		// app.
																		// creditriskparam
																		// .bus.
																		// policycap
																		// .
																		// OBPolicyCap
				{ "policyCapTrxValue", "com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue",
						SERVICE_SCOPE }, });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);

		DefaultLogger.debug(this, "map=" + map);
		DefaultLogger.debug(this, "cForm=" + cForm);

		PolicyCapForm aForm = (PolicyCapForm) cForm;
		if (PolicyCapAction.EVENT_VIEW.equals(event)) {

			DefaultLogger.debug(this, "policycapmapper.java ....");

			String stockExchange = aForm.getStockExchange();
			DefaultLogger.debug(this, "stockExchange=" + stockExchange);

			OBPolicyCap obPolicyCap = new OBPolicyCap();

			// obPolicyCap.setStockExchange( stockExchange );

			DefaultLogger.debug(this, "policycapmapper.java ....after obPolicyCap.setStockExchange");
			DefaultLogger.debug(this, "obPolicyCap=" + obPolicyCap);

			return obPolicyCap;

		}
		else if (PolicyCapAction.EVENT_MAKER_UPDATE.equals(event)) {

			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_MAKER_UPDATE ");

			IPolicyCapTrxValue oldTrxValue = (IPolicyCapTrxValue) map.get("policyCapTrxValue");

			// copy all old values from ORIGINAL value int newBusinessValue.
			IPolicyCap[] newPolicyCap = null;

			if (PolicyCapAction.EVENT_MAKER_UPDATE.equals(event)) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				try {
					newPolicyCap = (IPolicyCap[]) CommonUtil.deepClone(oldTrxValue.getPolicyCap());
					DefaultLogger.debug(this, "successfull copy all old values from ORIGINAL value");
				}
				catch (Exception e) {
					DefaultLogger.error(this, "error in deepClone:" + e);
				}
			}
			else if (event.equals("maker_edit_reject_edit")) {
				// copy all old values from STAGING value int newBusinessValue.
				DefaultLogger.debug(this, ">>>>>>>>>> In Getting Staging Policy Cap: "
						+ oldTrxValue.getStagingPolicyCap());
				DefaultLogger.debug(this, ">>>>>>>>>> In Getting Staging Policy Cap: "
						+ oldTrxValue.getStagingPolicyCap().getClass().getName());
				newPolicyCap = oldTrxValue.getStagingPolicyCap();
			}

			if (newPolicyCap != null) {
				DefaultLogger.debug(this, ">>>>>>>>>> In Coverting");

				for (int i = 0; i < aForm.getBoard().length; i++) {
					newPolicyCap[i].setMaxTradeCapNonFI(Float.parseFloat(aForm.getMaxTradeCapNonFI()[i]));
					newPolicyCap[i].setMaxCollateralCapNonFI(Float.parseFloat(aForm.getMaxCollateralCapNonFI()[i]));
					newPolicyCap[i].setQuotaCollateralCapNonFI(Float.parseFloat(aForm.getQuotaCollateralCapNonFI()[i]));
					newPolicyCap[i].setMaxCollateralCapFI(Float.parseFloat(aForm.getMaxCollateralCapFI()[i]));
					newPolicyCap[i].setQuotaCollateralCapFI(Float.parseFloat(aForm.getQuotaCollateralCapFI()[i]));
					newPolicyCap[i].setLiquidMOA(Float.parseFloat(aForm.getLiquidMOA()[i]));
					newPolicyCap[i].setIlliquidMOA(Float.parseFloat(aForm.getIlliquidMOA()[i]));
					newPolicyCap[i].getPriceCap().setAmount(Double.parseDouble(aForm.getMaxPriceCap()[i]));
				}
				DefaultLogger.debug(this, "newPolicyCap=" + newPolicyCap);
				HashMap policyCapMap = new HashMap();
				policyCapMap.put("policyCapList", newPolicyCap);
				DefaultLogger.debug(this, ">>>>>>>> returning policy cap hashmap");
				return policyCapMap;
			}
		}

		return null;

	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form");
			PolicyCapForm aForm = (PolicyCapForm) cForm;
			DefaultLogger.debug(this, "obj=" + obj);

			if (obj != null) {
				HashMap policyCapMap = (HashMap) obj;
				if (policyCapMap != null) {
					IPolicyCap[] policyCapList = (IPolicyCap[]) policyCapMap.get("policyCapList");
					int size = (policyCapList == null) ? 0 : policyCapList.length;

					if (size != 0) {
						// aForm.setStockExchange(policyCapList[0].
						// getStockExchange());

						String board[] = new String[size];
						String[] maxTradeCapNonFI = new String[size];
						String[] maxCollateralCapNonFI = new String[size];
						String[] quotaCollateralCapNonFI = new String[size];
						String[] maxCollateralCapFI = new String[size];
						String[] quotaCollateralCapFI = new String[size];
						String[] liquidMOA = new String[size];
						String[] illiquidMOA = new String[size];
						String[] maxPriceCap = new String[size];
						String[] currency = new String[size];

						IPolicyCap policyCap = null;
						for (int i = 0; i < size; i++) {
							policyCap = policyCapList[i];
							board[i] = policyCapList[i].getBoard();
							maxTradeCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getMaxTradeCapNonFI()) ? null
									: String.valueOf(policyCapList[i].getMaxTradeCapNonFI());
							maxCollateralCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxTradeCapNonFI()) ? null : String.valueOf(policyCapList[i]
									.getMaxCollateralCapNonFI());
							quotaCollateralCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxTradeCapNonFI()) ? null : String.valueOf(policyCapList[i]
									.getQuotaCollateralCapNonFI());
							maxCollateralCapFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxTradeCapNonFI()) ? null : String.valueOf(policyCapList[i]
									.getMaxCollateralCapFI());
							quotaCollateralCapFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxTradeCapNonFI()) ? null : String.valueOf(policyCapList[i]
									.getQuotaCollateralCapFI());
							liquidMOA[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getMaxTradeCapNonFI()) ? null
									: String.valueOf(policyCapList[i].getLiquidMOA());
							illiquidMOA[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getMaxTradeCapNonFI()) ? null
									: String.valueOf(policyCapList[i].getIlliquidMOA());
							maxPriceCap[i] = (ICMSConstant.DOUBLE_INVALID_VALUE == policyCap.getMaxTradeCapNonFI()) ? null
									: String.valueOf(policyCapList[i].getPriceCap().getAmount());
							currency[i] = policyCapList[i].getPriceCap().getCurrencyCode();
						}

						aForm.setBoard(board);
						aForm.setMaxTradeCapNonFI(maxTradeCapNonFI);
						aForm.setMaxCollateralCapNonFI(maxCollateralCapNonFI);
						aForm.setQuotaCollateralCapNonFI(quotaCollateralCapNonFI);
						aForm.setMaxCollateralCapFI(maxCollateralCapFI);
						aForm.setQuotaCollateralCapFI(quotaCollateralCapFI);
						aForm.setLiquidMOA(liquidMOA);
						aForm.setIlliquidMOA(illiquidMOA);
						aForm.setMaxPriceCap(maxPriceCap);
						aForm.setCurrency(currency);
					}
				}
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "error in PolicyCapMapper is" + e);
		}
		return null;
	}
}
