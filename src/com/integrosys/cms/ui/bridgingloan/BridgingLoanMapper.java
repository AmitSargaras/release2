/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

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
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class BridgingLoanMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public BridgingLoanMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String trxID = (String) map.get("trxID");
		String from_page = (String) map.get("from_page");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		BridgingLoanForm aForm = (BridgingLoanForm) cForm;
		try {
			if ((from_page != null) && from_page.equals("projectinfo")) {
				if (BridgingLoanAction.EVENT_SAVE.equals(event) || BridgingLoanAction.EVENT_SUBMIT.equals(event)
						|| BridgingLoanAction.EVENT_MAKER_PREPARE_CREATE.equals(event)
						|| BridgingLoanAction.EVENT_MAKER_NAVIGATE_TAB.equals(event)) {

					IBridgingLoanTrxValue oldTrxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");

					if ((trxID == null) && BridgingLoanAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
						DefaultLogger.debug(this, "ONLY SET TIME ONE TIME WHEN CLICK EDIT BUTTON");
						oldTrxValue.setStagingBridgingLoan(oldTrxValue.getBridgingLoan());
					}
					IBridgingLoan newBridgingLoan = oldTrxValue.getStagingBridgingLoan();

					if (map.get("limitID") != null) {
						newBridgingLoan.setLimitProfileID(Long.parseLong((String) map.get("limitProfileID")));
						newBridgingLoan.setLimitID(Long.parseLong((String) map.get("limitID")));
						newBridgingLoan.setSourceLimit((String) map.get("sourceLimit"));
						newBridgingLoan.setProductDescription((String) map.get("productDescription"));
					}

					newBridgingLoan.setProjectNumber(aForm.getProjectNumber());
					newBridgingLoan.setContractDate(DateUtil.convertDate(locale, aForm.getContractDate()));
					if (!aForm.getContractCurrency().equals("") && !aForm.getContractAmount().equals("")) {
						newBridgingLoan.setContractAmount(CurrencyManager.convertToAmount(locale, aForm
								.getContractCurrency(), aForm.getContractAmount()));
					}
					if (!aForm.getFinancePercent().equals("")) {
						newBridgingLoan.setFinancePercent(Float.parseFloat(aForm.getFinancePercent()));
					}
					newBridgingLoan.setCollectionAccount(aForm.getCollectionAccount());
					newBridgingLoan.setHdaAccount(aForm.getHdaAccount());
					newBridgingLoan.setProjectAccount(aForm.getProjectAccount());
					newBridgingLoan.setCurrentAccount(aForm.getCurrentAccount());
					if (!aForm.getNoOfTypes().equals("")) {
						newBridgingLoan.setNoOfTypes(Integer.parseInt(aForm.getNoOfTypes()));
					}
					if (!aForm.getExpectedStartDate().equals("")) {
						newBridgingLoan
								.setExpectedStartDate(DateUtil.convertDate(locale, aForm.getExpectedStartDate()));
					}
					if (!aForm.getExpectedCompletionDate().equals("")) {
						newBridgingLoan.setExpectedCompletionDate(DateUtil.convertDate(locale, aForm
								.getExpectedCompletionDate()));
					}
					if (!aForm.getActualStartDate().equals("")) {
						newBridgingLoan.setActualStartDate(DateUtil.convertDate(locale, aForm.getActualStartDate()));
					}
					if (!aForm.getActualCompletionDate().equals("")) {
						newBridgingLoan.setActualCompletionDate(DateUtil.convertDate(locale, aForm
								.getActualCompletionDate()));
					}
					if (!aForm.getAvailabilityExpiryDate().equals("")) {
						newBridgingLoan.setAvailabilityExpiryDate(DateUtil.convertDate(locale, aForm
								.getAvailabilityExpiryDate()));
					}
					if (!aForm.getFullSettlementDate().equals("")) {
						newBridgingLoan.setFullSettlementDate(DateUtil.convertDate(locale, aForm
								.getFullSettlementDate()));
					}
					newBridgingLoan.setBlRemarks(aForm.getBlRemarks());

					return newBridgingLoan;
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
			DefaultLogger.debug(this, "******************** inside mapOb to form (BridgingLoanMapper)");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			BridgingLoanForm aForm = (BridgingLoanForm) cForm;

			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

			if (objBridgingLoan != null) {
				aForm.setLimitProfileID(Long.toString(objBridgingLoan.getLimitProfileID()));
				aForm.setProjectNumber(objBridgingLoan.getProjectNumber());
				if (objBridgingLoan.getContractDate() != null) {
					aForm.setContractDate(DateUtil.formatDate(locale, objBridgingLoan.getContractDate()));
				}
				if (objBridgingLoan.getContractAmount() != null) {
					aForm.setContractCurrency(objBridgingLoan.getContractAmount().getCurrencyCode());
					aForm.setContractAmount(CurrencyManager
							.convertToString(locale, objBridgingLoan.getContractAmount()));
				}
				if (objBridgingLoan.getFinancePercent() != ICMSConstant.FLOAT_INVALID_VALUE) {
					aForm.setFinancePercent(new DecimalFormat("#").format(objBridgingLoan.getFinancePercent()));
				}
				if (objBridgingLoan.getContractAmount() != null) {
					aForm.setContractCurrency(objBridgingLoan.getContractAmount().getCurrencyCode());
					aForm.setContractAmount(new DecimalFormat("#").format(objBridgingLoan.getContractAmount()
							.getAmount()));
					if (objBridgingLoan.getFinancedAmount() != null) {
						aForm.setFinancedAmount(new DecimalFormat("#").format(objBridgingLoan.getFinancedAmount()
								.getAmount()));
					}
				}
				aForm.setCollectionAccount(objBridgingLoan.getCollectionAccount());
				aForm.setHdaAccount(objBridgingLoan.getHdaAccount());
				aForm.setProjectAccount(objBridgingLoan.getProjectAccount());
				aForm.setCurrentAccount(objBridgingLoan.getCurrentAccount());
				if (objBridgingLoan.getNoOfTypes() != ICMSConstant.INT_INVALID_VALUE) {
					aForm.setNoOfTypes(String.valueOf(objBridgingLoan.getNoOfTypes()));
				}
				if (objBridgingLoan.getExpectedStartDate() != null) {
					aForm.setExpectedStartDate(DateUtil.formatDate(locale, objBridgingLoan.getExpectedStartDate()));
				}
				if (objBridgingLoan.getExpectedCompletionDate() != null) {
					aForm.setExpectedCompletionDate(DateUtil.formatDate(locale, objBridgingLoan
							.getExpectedCompletionDate()));
				}
				if (objBridgingLoan.getActualStartDate() != null) {
					aForm.setActualStartDate(DateUtil.formatDate(locale, objBridgingLoan.getActualStartDate()));
				}
				if (objBridgingLoan.getActualCompletionDate() != null) {
					aForm.setActualCompletionDate(DateUtil
							.formatDate(locale, objBridgingLoan.getActualCompletionDate()));
				}
				if (objBridgingLoan.getAvailabilityExpiryDate() != null) {
					aForm.setAvailabilityExpiryDate(DateUtil.formatDate(locale, objBridgingLoan
							.getAvailabilityExpiryDate()));
				}
				if (objBridgingLoan.getFullSettlementDate() != null) {
					aForm.setFullSettlementDate(DateUtil.formatDate(locale, objBridgingLoan.getFullSettlementDate()));
				}
				aForm.setBlRemarks(objBridgingLoan.getBlRemarks());
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.toString());
			e.printStackTrace();
		}
		return null;
	}
}