/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class ContractDetailMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public ContractDetailMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", SERVICE_SCOPE }, { "sourceLimit", "java.lang.String", SERVICE_SCOPE },
				{ "productDescription", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>  Inside Map Form to OB ");

		String event = (String) map.get(ICommonEventConstant.EVENT);
		String trxID = (String) map.get("trxID");
		String from_page = (String) map.get("from_page");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ContractDetailForm aForm = (ContractDetailForm) cForm;

		try {
			if ((from_page != null) && from_page.equals("contractdetail")) {

				if (ContractFinancingAction.EVENT_SAVE.equals(event)
						|| ContractFinancingAction.EVENT_SUBMIT.equals(event)
						|| ContractFinancingAction.EVENT_MAKER_PREPARE_CREATE.equals(event)
						|| ContractFinancingAction.EVENT_MAKER_NAVIGATE_TAB.equals(event)) {

					DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

					IContractFinancingTrxValue oldTrxValue = (IContractFinancingTrxValue) map
							.get("contractFinancingTrxValue");

					if ((trxID == null) && ContractFinancingAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
						DefaultLogger.debug(this, "ONLY SET TIME ONE TIME WHEN CLICK EDIT BUTTON");
						oldTrxValue.setStagingContractFinancing(oldTrxValue.getContractFinancing());
					}
					IContractFinancing newContractFinancing = oldTrxValue.getStagingContractFinancing();

					if (map.get("limitID") != null) {
						newContractFinancing.setLimitProfileID(Long.parseLong((String) map.get("limitProfileID")));
						newContractFinancing.setLimitID(Long.parseLong((String) map.get("limitID")));
						newContractFinancing.setSourceLimit((String) map.get("sourceLimit"));
						newContractFinancing.setProductDescription((String) map.get("productDescription"));
					}

					newContractFinancing.setContractNumber(aForm.getContractNumber());
					newContractFinancing.setContractDate(DateUtil.convertDate(locale, aForm.getContractDate()));
					newContractFinancing.setAwarderType(aForm.getAwarderType());
					newContractFinancing.setAwarderTypeOthers(aForm.getAwarderTypeOthers());
					newContractFinancing.setAwarderName(aForm.getAwarderName());
					newContractFinancing.setContractType(aForm.getContractType());
					newContractFinancing.setContractTypeOthers(aForm.getContractTypeOthers());
					newContractFinancing.setStartDate(DateUtil.convertDate(locale, aForm.getStartDate()));
					newContractFinancing.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpiryDate()));
					newContractFinancing.setExtendedDate(DateUtil.convertDate(locale, aForm.getExtendedDate()));
					if (!aForm.getContractCurrency().equals("") && !aForm.getContractAmount().equals("")) {
						newContractFinancing.setContractAmount(CurrencyManager.convertToAmount(locale, aForm
								.getContractCurrency(), aForm.getContractAmount()));
					}
					if (!aForm.getActualFinanceCurrency().equals("") && !aForm.getActualFinanceAmount().equals("")) {
						newContractFinancing.setActualFinanceAmount(CurrencyManager.convertToAmount(locale, aForm
								.getActualFinanceCurrency(), aForm.getActualFinanceAmount()));
					}
					if (!aForm.getFinancePercent().equals("")) {
						newContractFinancing.setFinancePercent(Integer.parseInt(aForm.getFinancePercent()));
					}
					if (!aForm.getProjectedProfitCurrency().equals("") && !aForm.getProjectedProfitAmount().equals("")) {
						newContractFinancing.setProjectedProfit(CurrencyManager.convertToAmount(locale, aForm
								.getProjectedProfitCurrency(), aForm.getProjectedProfitAmount()));
					}
					newContractFinancing.setCollectionAccount(aForm.getCollectionAccount());
					newContractFinancing.setFacilityExpiryDate(DateUtil.convertDate(locale, aForm
							.getFacilityExpiryDate()));
					newContractFinancing.setProjectAccount(aForm.getProjectAccount());
					newContractFinancing.setSinkingFundInd(aForm.getSinkingFundInd());
					newContractFinancing.setSinkingFundParty(aForm.getSinkingFundParty());
					if (!aForm.getBuildUpFdr().equals("")) {
						newContractFinancing.setBuildUpFDR(Integer.parseInt(aForm.getBuildUpFdr()));
					}
					newContractFinancing.setContractDescription(aForm.getContractDescription());
					newContractFinancing.setRemark(aForm.getRemark());

					return newContractFinancing;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
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
			ContractDetailForm aForm = (ContractDetailForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");
			if (trxValue != null) {
				OBContractFinancing contractFinancingObj = (OBContractFinancing) trxValue.getStagingContractFinancing();

				aForm.setContractNumber(contractFinancingObj.getContractNumber());

				aForm.setContractDate(DateUtil.formatDate(locale, contractFinancingObj.getContractDate()));
				aForm.setAwarderType(contractFinancingObj.getAwarderType());
				aForm.setAwarderTypeOthers(contractFinancingObj.getAwarderTypeOthers());
				aForm.setAwarderName(contractFinancingObj.getAwarderName());
				aForm.setContractType(contractFinancingObj.getContractType());
				aForm.setContractTypeOthers(contractFinancingObj.getContractTypeOthers());
				aForm.setStartDate(DateUtil.formatDate(locale, contractFinancingObj.getStartDate()));
				aForm.setExpiryDate(DateUtil.formatDate(locale, contractFinancingObj.getExpiryDate()));
				aForm.setExtendedDate(DateUtil.formatDate(locale, contractFinancingObj.getExtendedDate()));
				if (contractFinancingObj.getContractAmount() != null) {
					aForm.setContractCurrency(contractFinancingObj.getContractAmount().getCurrencyCode());
					aForm.setContractAmount(new DecimalFormat("#").format(contractFinancingObj.getContractAmount()
							.getAmount()));
					aForm.setFinancedAmount(new DecimalFormat("#").format(contractFinancingObj.getFinancedAmount()
							.getAmount()));
				}
				if (contractFinancingObj.getActualFinanceAmount() != null) {
					aForm.setActualFinanceCurrency(contractFinancingObj.getActualFinanceAmount().getCurrencyCode());
					aForm.setActualFinanceAmount(new DecimalFormat("#").format(contractFinancingObj
							.getActualFinanceAmount().getAmount()));
				}
				if (contractFinancingObj.getFinancePercent() != ICMSConstant.FLOAT_INVALID_VALUE) {
					aForm.setFinancePercent(new DecimalFormat("#").format(contractFinancingObj.getFinancePercent()));
				}
				if (contractFinancingObj.getProjectedProfit() != null) {
					aForm.setProjectedProfitCurrency(contractFinancingObj.getProjectedProfit().getCurrencyCode());
					aForm.setProjectedProfitAmount(new DecimalFormat("#").format(contractFinancingObj
							.getProjectedProfit().getAmount()));
				}
				aForm.setCollectionAccount(contractFinancingObj.getCollectionAccount());
				aForm.setFacilityExpiryDate(DateUtil.formatDate(locale, contractFinancingObj.getFacilityExpiryDate()));
				aForm.setProjectAccount(contractFinancingObj.getProjectAccount());
				aForm.setSinkingFundInd(contractFinancingObj.getSinkingFundInd());
				aForm.setSinkingFundParty(contractFinancingObj.getSinkingFundParty());
				if (contractFinancingObj.getBuildUpFDR() != ICMSConstant.FLOAT_INVALID_VALUE) {
					aForm.setBuildUpFdr(new DecimalFormat("#").format(contractFinancingObj.getBuildUpFDR()));
				}
				aForm.setContractDescription(contractFinancingObj.getContractDescription());
				aForm.setRemark(contractFinancingObj.getRemark());
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in ContractFinancingMapper is" + e);
		}
		return null;
	}
}
