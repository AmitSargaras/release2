/**
 * 
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
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.OBPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue;

/**
 * Purpose: Policy Cap Group Mapper
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 31/AUG/2007 $ Tag: $Name: $
 */
public class PolicyCapGroupMapper extends AbstractCommonMapper {

	/**
	 * Default Construtor
	 */
	public PolicyCapGroupMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "policyCapGroupMap", "java.util.HashMap", FORM_SCOPE }, // Collection
																			// of
																			// com
																			// .
																			// integrosys
																			// .
																			// cms
																			// .
																			// app
																			// .
																			// creditriskparam
																			// .
																			// bus
																			// .
																			// policycap
																			// .
																			// OBPolicyCap
				{ "policyCapGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue", SERVICE_SCOPE }, });

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

		PolicyCapForm aForm = (PolicyCapForm) cForm;
		if (PolicyCapAction.EVENT_VIEW.equals(event)) {

			DefaultLogger.debug(this, "policycapmapper.java ....");

			String stockExchange = aForm.getStockExchange();
			DefaultLogger.debug(this, "stockExchange=" + stockExchange);

			String bankEntity = aForm.getStockExchange();
			DefaultLogger.debug(this, "bankEntity=" + bankEntity);

			OBPolicyCapGroup obPolicyCapGroup = new OBPolicyCapGroup();

			obPolicyCapGroup.setStockExchange(stockExchange);
			obPolicyCapGroup.setBankEntity(bankEntity);

			DefaultLogger.debug(this, "policycapmapper.java ....after obPolicyCapGroup.setStockExchange");

			return obPolicyCapGroup;

		}
		else if (PolicyCapAction.EVENT_MAKER_UPDATE.equals(event)) {

			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_MAKER_UPDATE ");

			IPolicyCapGroupTrxValue oldTrxValue = (IPolicyCapGroupTrxValue) map.get("policyCapGroupTrxValue");

			// copy all old values from ORIGINAL value int newBusinessValue.
			IPolicyCapGroup newPolicyCapGroup = null;

			if (PolicyCapAction.EVENT_MAKER_UPDATE.equals(event)) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				try {
					newPolicyCapGroup = (IPolicyCapGroup) CommonUtil.deepClone(oldTrxValue.getPolicyCapGroup());
					DefaultLogger.debug(this, "successfull copy all old values from ORIGINAL value");
				}
				catch (Exception e) {
					DefaultLogger.error(this, "error in deepClone:" + e);
				}
			}
			else if (event.equals("maker_edit_reject_edit")) { // TODO it won't
																// happen
				// copy all old values from STAGING value int newBusinessValue.
				DefaultLogger.debug(this, ">>>>>>>>>> In Getting Staging Policy Cap: "
						+ oldTrxValue.getStagingPolicyCapGroup());
				DefaultLogger.debug(this, ">>>>>>>>>> In Getting Staging Policy Cap: "
						+ oldTrxValue.getStagingPolicyCapGroup().getClass().getName());
				newPolicyCapGroup = oldTrxValue.getStagingPolicyCapGroup();
			}

			if (newPolicyCapGroup != null) {
				if ((newPolicyCapGroup.getPolicyCapArray() != null)
						&& (newPolicyCapGroup.getPolicyCapArray().length > 0)) {

					IPolicyCap[] newPolicyCap = newPolicyCapGroup.getPolicyCapArray();
					DefaultLogger.debug(this, ">>>>>>>>>> In Coverting");

					for (int i = 0; i < aForm.getBoard().length; i++) {

						if (aForm.getMaxTradeCapNonFI() != null) {
							newPolicyCap[i].setMaxTradeCapNonFI(Float.parseFloat(aForm.getMaxTradeCapNonFI()[i]));
						}
						newPolicyCap[i].setMaxCollateralCapNonFI(Float.parseFloat(aForm.getMaxCollateralCapNonFI()[i]));
						newPolicyCap[i].setQuotaCollateralCapNonFI(Float
								.parseFloat(aForm.getQuotaCollateralCapNonFI()[i]));
						if ((aForm.getMaxCollateralCapFI().length > i) && (aForm.getMaxCollateralCapFI()[i] != null)) {
							newPolicyCap[i].setMaxCollateralCapFI(Float.parseFloat(aForm.getMaxCollateralCapFI()[i]));
						}
						if ((aForm.getQuotaCollateralCapFI().length > i)
								&& (aForm.getQuotaCollateralCapFI()[i] != null)) {
							newPolicyCap[i].setQuotaCollateralCapFI(Float
									.parseFloat(aForm.getQuotaCollateralCapFI()[i]));
						}
						/*
						if (aForm.getLiquidMOA() != null) {
							newPolicyCap[i].setLiquidMOA(Float.parseFloat(aForm.getLiquidMOA()[i]));
						}
						if (aForm.getIlliquidMOA() != null) {
							newPolicyCap[i].setIlliquidMOA(Float.parseFloat(aForm.getIlliquidMOA()[i]));
						}
						*/
						if (aForm.getMaxPriceCap() != null) {
							newPolicyCap[i].getPriceCap().setAmount(Double.parseDouble(aForm.getMaxPriceCap()[i]));
						}
					}
					DefaultLogger.debug(this, "newPolicyCap=" + newPolicyCap);
				}

				String isBankGroup = aForm.getIsBankGroup();

				HashMap policyCapGroupMap = new HashMap();
				policyCapGroupMap.put("policyCapGroup", newPolicyCapGroup);
				policyCapGroupMap.put("isBankGroup", isBankGroup);
				DefaultLogger.debug(this, ">>>>>>>> returning policy cap hashmap");
				return policyCapGroupMap;
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

			if (obj != null) {
				HashMap policyCapGroupMap = (HashMap) obj;
				if (policyCapGroupMap != null) {
					IPolicyCapGroup policyCapGroup = (IPolicyCapGroup) policyCapGroupMap.get("policyCapGroup");
					int size = (policyCapGroup == null) ? 0 : policyCapGroup.getPolicyCapArray().length;

					DefaultLogger.debug(this, "Size of PolicyCap Array=" + size);
					if (size != 0) {

						String isBankGroup = (String) policyCapGroupMap.get("isBankGroup");
						DefaultLogger.debug(this, ">>>>>> isBankGroup : " + isBankGroup);
						if (isBankGroup != null) {
							aForm.setIsBankGroup("true");
						}
						else {
							aForm.setIsBankGroup("false");
						}

						aForm.setStockExchange(policyCapGroup.getStockExchange());
						aForm.setBankEntity(policyCapGroup.getBankEntity());

						String board[] = new String[size];
						String[] maxTradeCapNonFI = new String[size];
						String[] maxCollateralCapNonFI = new String[size];
						String[] quotaCollateralCapNonFI = new String[size];
						String[] maxCollateralCapFI = new String[size];
						String[] quotaCollateralCapFI = new String[size];
						//String[] liquidMOA = new String[size];
						//String[] illiquidMOA = new String[size];
						String[] maxPriceCap = new String[size];
						String[] currency = new String[size];

						IPolicyCap policyCap = null;
						for (int i = 0; i < size; i++) {
							policyCap = policyCapGroup.getPolicyCapArray()[i];
							board[i] = policyCap.getBoard();
							maxTradeCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getMaxTradeCapNonFI()) ? null
									: String.valueOf(policyCap.getMaxTradeCapNonFI());
							maxCollateralCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxCollateralCapNonFI()) ? null : String.valueOf(policyCap
									.getMaxCollateralCapNonFI());
							quotaCollateralCapNonFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getQuotaCollateralCapNonFI()) ? null : String.valueOf(policyCap
									.getQuotaCollateralCapNonFI());
							maxCollateralCapFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getMaxCollateralCapFI()) ? null : String
									.valueOf(policyCap.getMaxCollateralCapFI());
							quotaCollateralCapFI[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap
									.getQuotaCollateralCapFI()) ? null : String.valueOf(policyCap
									.getQuotaCollateralCapFI());
							/*
							liquidMOA[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getLiquidMOA()) ? null
									: String.valueOf(policyCap.getLiquidMOA());
							illiquidMOA[i] = (ICMSConstant.FLOAT_INVALID_VALUE == policyCap.getIlliquidMOA()) ? null
									: String.valueOf(policyCap.getIlliquidMOA());
							*/
							maxPriceCap[i] = (ICMSConstant.DOUBLE_INVALID_VALUE == policyCap.getPriceCap().getAmount()) ? null
									: String.valueOf(policyCap.getPriceCap().getAmount());
							currency[i] = policyCapGroup.getPolicyCapArray()[i].getPriceCap().getCurrencyCode();
						}

						aForm.setBoard(board);
						aForm.setMaxTradeCapNonFI(maxTradeCapNonFI);
						aForm.setMaxCollateralCapNonFI(maxCollateralCapNonFI);
						aForm.setQuotaCollateralCapNonFI(quotaCollateralCapNonFI);
						aForm.setMaxCollateralCapFI(maxCollateralCapFI);
						aForm.setQuotaCollateralCapFI(quotaCollateralCapFI);
						//aForm.setLiquidMOA(liquidMOA);
						//aForm.setIlliquidMOA(illiquidMOA);
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
