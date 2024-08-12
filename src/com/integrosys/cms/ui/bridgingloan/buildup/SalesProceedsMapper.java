/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.buildup;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.integrosys.cms.app.bridgingloan.bus.IBuildUp;
import com.integrosys.cms.app.bridgingloan.bus.ISalesProceeds;
import com.integrosys.cms.app.bridgingloan.bus.OBSalesProceeds;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SalesProceedsMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SalesProceedsMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "buildUpIndex", "java.lang.String", REQUEST_SCOPE },
				{ "salesProceedsIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB (SalesProceedsMapper) ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		SalesProceedsForm aForm = (SalesProceedsForm) cForm;
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		int buildUpIndex = 0;
		if (((String) map.get("buildUpIndex") != null) && !String.valueOf(map.get("buildUpIndex")).equals("")) {
			buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));
		}
		DefaultLogger.debug(this, "buildUpIndex = " + buildUpIndex);
		IBuildUp[] objBuildUp = (IBuildUp[]) objBridgingLoan.getBuildUpList();
		DefaultLogger.debug(this, "objBuildUp = " + objBuildUp);

		if (SalesProceedsAction.EVENT_CREATE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping - EVENT_CREATE ");

			ISalesProceeds[] oldSalesProceeds = (ISalesProceeds[]) objBuildUp[buildUpIndex].getSalesProceedsList(); // Get
																													// whole
																													// list
			DefaultLogger.debug("******", "oldSalesProceeds " + oldSalesProceeds);
			ArrayList salesProceedsList = new ArrayList();
			salesProceedsList.clear();
			try {
				OBSalesProceeds newSalesProceeds = new OBSalesProceeds();
				if (!aForm.getProceedsDate().equals("")) {
					newSalesProceeds.setProceedsDate(DateUtil.convertDate(locale, aForm.getProceedsDate()));
				}
				newSalesProceeds.setPurpose(aForm.getPurpose());
				if (!aForm.getPurposePercent().equals("")) {
					newSalesProceeds.setPurposePercent(Float.parseFloat(aForm.getPurposePercent()));
				}
				newSalesProceeds.setBankName(aForm.getBankName());
				newSalesProceeds.setChequeNo(aForm.getChequeNo());
				if (!aForm.getReceiveCurrency().equals("") && !aForm.getReceiveAmount().equals("")) {
					newSalesProceeds.setReceiveAmount(CurrencyManager.convertToAmount(locale, aForm
							.getReceiveCurrency(), aForm.getReceiveAmount()));
				}
				newSalesProceeds.setStatus(aForm.getStatus());
				newSalesProceeds.setRemarks(aForm.getRemarks());

				if (!aForm.getDistributeDate().equals("")) {
					newSalesProceeds.setDistributeDate(DateUtil.convertDate(locale, aForm.getDistributeDate()));
				}
				if (!aForm.getDistributeCurrency().equals("") && !aForm.getDistributeAmount().equals("")) {
					newSalesProceeds.setDistributeAmount(CurrencyManager.convertToAmount(locale, aForm
							.getDistributeCurrency(), aForm.getDistributeAmount()));
				}
				newSalesProceeds.setIsToTL1(TypeConverter.convertStringToBooleanEquivalent(aForm.getIsToTL1()));

				if ((aForm.getIsToTL1() == null) || aForm.getIsToTL1().equals("")) {
					newSalesProceeds.setIsToTL1(false);
				}
				else {
					newSalesProceeds.setIsToTL1(true);
					if (!aForm.getTL1Currency().equals("") && !aForm.getTL1Amount().equals("")) {
						newSalesProceeds.setTL1Amount(CurrencyManager.convertToAmount(locale, aForm.getTL1Currency(),
								aForm.getTL1Amount()));
					}
				}
				if ((aForm.getIsToOD() == null) || aForm.getIsToOD().equals("")) {
					newSalesProceeds.setIsToOD(false);
				}
				else {
					newSalesProceeds.setIsToOD(true);
					if (!aForm.getOdCurrency().equals("") && !aForm.getOdAmount().equals("")) {
						newSalesProceeds.setOdAmount(CurrencyManager.convertToAmount(locale, aForm.getOdCurrency(),
								aForm.getOdAmount()));
					}
				}
				if ((aForm.getIsToFDR() == null) || aForm.getIsToFDR().equals("")) {
					newSalesProceeds.setIsToFDR(false);
				}
				else {
					newSalesProceeds.setIsToFDR(true);
					if (!aForm.getFdrCurrency().equals("") && !aForm.getFdrAmount().equals("")) {
						newSalesProceeds.setFdrAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(),
								aForm.getFdrAmount()));
					}
				}
				if ((aForm.getIsToHDA() == null) || aForm.getIsToHDA().equals("")) {
					newSalesProceeds.setIsToHDA(false);
				}
				else {
					if (!aForm.getHdaCurrency().equals("") && !aForm.getHdaAmount().equals("")) {
						newSalesProceeds.setHdaAmount(CurrencyManager.convertToAmount(locale, aForm.getHdaCurrency(),
								aForm.getHdaAmount()));
					}
				}
				if ((aForm.getIsToOthers() == null) || aForm.getIsToOthers().equals("")) {
					newSalesProceeds.setIsToOthers(false);
				}
				else {
					newSalesProceeds.setIsToOthers(true);
					newSalesProceeds.setOthersAccount(aForm.getOthersAccount());
					if (!aForm.getOthersCurrency().equals("") && !aForm.getOthersAmount().equals("")) {
						newSalesProceeds.setOthersAmount(CurrencyManager.convertToAmount(locale, aForm
								.getOthersCurrency(), aForm.getOthersAmount()));
					}
				}
				if ((oldSalesProceeds != null) && (oldSalesProceeds.length != 0)) {
					for (int i = 0; i < oldSalesProceeds.length; i++) {
						OBSalesProceeds objSalesProceeds = (OBSalesProceeds) oldSalesProceeds[i];
						salesProceedsList.add(objSalesProceeds);
					}
				}
				salesProceedsList.add(newSalesProceeds);
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, e.toString());
			}
			objBuildUp[buildUpIndex].setSalesProceedsList((ISalesProceeds[]) salesProceedsList
					.toArray(new ISalesProceeds[0]));
			objBridgingLoan.setBuildUpList(objBuildUp);
			return objBridgingLoan;
		}
		else if (SalesProceedsAction.EVENT_UPDATE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_UPDATE ");
			ISalesProceeds[] newSalesProceeds = (ISalesProceeds[]) objBuildUp[buildUpIndex].getSalesProceedsList();
			int salesProceedsIndex = Integer.parseInt((String) map.get("salesProceedsIndex"));
			DefaultLogger.debug(this, "salesProceedsIndex: " + salesProceedsIndex);

			if ((newSalesProceeds != null) && (newSalesProceeds.length != 0)) {
				try {
					if (!aForm.getProceedsDate().equals("")) {
						newSalesProceeds[salesProceedsIndex].setProceedsDate(DateUtil.convertDate(locale, aForm
								.getProceedsDate()));
					}
					newSalesProceeds[salesProceedsIndex].setPurpose(aForm.getPurpose());
					if (!aForm.getPurposePercent().equals("")) {
						newSalesProceeds[salesProceedsIndex].setPurposePercent(Float.parseFloat(aForm
								.getPurposePercent()));
					}
					newSalesProceeds[salesProceedsIndex].setBankName(aForm.getBankName());
					newSalesProceeds[salesProceedsIndex].setChequeNo(aForm.getChequeNo());
					if (!aForm.getReceiveCurrency().equals("") && !aForm.getReceiveAmount().equals("")) {
						newSalesProceeds[salesProceedsIndex].setReceiveAmount(CurrencyManager.convertToAmount(locale,
								aForm.getReceiveCurrency(), aForm.getReceiveAmount()));
					}
					newSalesProceeds[salesProceedsIndex].setStatus(aForm.getStatus());
					newSalesProceeds[salesProceedsIndex].setRemarks(aForm.getRemarks());

					if (!aForm.getDistributeDate().equals("")) {
						newSalesProceeds[salesProceedsIndex].setDistributeDate(DateUtil.convertDate(locale, aForm
								.getDistributeDate()));
					}
					if (!aForm.getDistributeAmount().equals("")) {
						newSalesProceeds[salesProceedsIndex].setDistributeAmount(CurrencyManager.convertToAmount(
								locale, aForm.getDistributeCurrency(), aForm.getDistributeAmount()));
					}

					DefaultLogger.debug(this, "getIsToTL1(): " + aForm.getIsToTL1());
					DefaultLogger.debug(this, "getIsToOD(): " + aForm.getIsToOD());
					DefaultLogger.debug(this, "getIsToFDR(): " + aForm.getIsToFDR());
					DefaultLogger.debug(this, "getIsToHDA(): " + aForm.getIsToHDA());
					DefaultLogger.debug(this, "getIsToOthers(): " + aForm.getIsToOthers());

					DefaultLogger.debug(this, "getTL1Amount(): " + aForm.getTL1Amount());
					DefaultLogger.debug(this, "getOdAmount(): " + aForm.getOdAmount());

					if ((aForm.getIsToTL1() == null) || aForm.getIsToTL1().equals("") || aForm.getIsToTL1().equals("N")) {
						if ((aForm.getTL1Amount() == null) || aForm.getTL1Amount().equals("")) {
							newSalesProceeds[salesProceedsIndex].setIsToTL1(false);
							newSalesProceeds[salesProceedsIndex].setTL1Amount(CurrencyManager.convertToAmount(locale,
									null, null)); // Set Amount to empty
						}
					}
					else {
						newSalesProceeds[salesProceedsIndex].setIsToTL1(true);
						newSalesProceeds[salesProceedsIndex].setTL1Amount(CurrencyManager.convertToAmount(locale, aForm
								.getTL1Currency(), aForm.getTL1Amount()));
					}
					if ((aForm.getIsToOD() == null) || aForm.getIsToOD().equals("") || aForm.getIsToOD().equals("N")) {
						if ((aForm.getOdAmount() == null) || aForm.getOdAmount().equals("")) {
							newSalesProceeds[salesProceedsIndex].setIsToOD(false);
							newSalesProceeds[salesProceedsIndex].setOdAmount(CurrencyManager.convertToAmount(locale,
									null, null)); // Set Amount to empty
						}
					}
					else {
						newSalesProceeds[salesProceedsIndex].setIsToOD(true);
						newSalesProceeds[salesProceedsIndex].setOdAmount(CurrencyManager.convertToAmount(locale, aForm
								.getOdCurrency(), aForm.getOdAmount()));
					}
					if ((aForm.getIsToFDR() == null) || aForm.getIsToFDR().equals("") || aForm.getIsToFDR().equals("N")) {
						if ((aForm.getIsToFDR() != null) || aForm.getIsToFDR().equals("")) {
							newSalesProceeds[salesProceedsIndex].setIsToFDR(false);
							newSalesProceeds[salesProceedsIndex].setFdrAmount(CurrencyManager.convertToAmount(locale,
									null, null)); // Set Amount to empty
						}
					}
					else {
						newSalesProceeds[salesProceedsIndex].setIsToFDR(true);
						newSalesProceeds[salesProceedsIndex].setFdrAmount(CurrencyManager.convertToAmount(locale, aForm
								.getFdrCurrency(), aForm.getFdrAmount()));
					}
					if ((aForm.getIsToHDA() == null) || aForm.getIsToHDA().equals("") || aForm.getIsToHDA().equals("N")) {
						if ((aForm.getIsToHDA() == null) || aForm.getIsToHDA().equals("")) {
							newSalesProceeds[salesProceedsIndex].setIsToHDA(false);
							newSalesProceeds[salesProceedsIndex].setHdaAmount(CurrencyManager.convertToAmount(locale,
									null, null)); // Set Amount to empty
						}
					}
					else {
						newSalesProceeds[salesProceedsIndex].setIsToHDA(true);
						newSalesProceeds[salesProceedsIndex].setHdaAmount(CurrencyManager.convertToAmount(locale, aForm
								.getHdaCurrency(), aForm.getHdaAmount()));
					}
					if ((aForm.getIsToOthers() == null) || aForm.getIsToOthers().equals("")
							|| aForm.getIsToOthers().equals("N")) {
						if ((aForm.getOthersAmount() == null) || (aForm.getOthersAccount() == null)
								|| aForm.getOthersAmount().equals("") || aForm.getOthersAccount().equals("")) {
							newSalesProceeds[salesProceedsIndex].setIsToOthers(false);
							newSalesProceeds[salesProceedsIndex].setOthersAccount(null);
							newSalesProceeds[salesProceedsIndex].setOthersAmount(CurrencyManager.convertToAmount(
									locale, null, null)); // Set Amount to empty
						}
					}
					else {
						newSalesProceeds[salesProceedsIndex].setIsToOthers(true);
						newSalesProceeds[salesProceedsIndex].setOthersAccount(aForm.getOthersAccount());
						newSalesProceeds[salesProceedsIndex].setOthersAmount(CurrencyManager.convertToAmount(locale,
								aForm.getOthersCurrency(), aForm.getOthersAmount()));
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
				objBuildUp[buildUpIndex].setSalesProceedsList(newSalesProceeds);
				objBridgingLoan.setBuildUpList(objBuildUp);
				return objBridgingLoan;
			}
		}
		else if (SalesProceedsAction.EVENT_DELETE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_DELETE");
			ArrayList salesProceedsList = new ArrayList();
			ISalesProceeds[] oldSalesProceeds = objBuildUp[buildUpIndex].getSalesProceedsList();
			int salesProceedsIndex = Integer.parseInt((String) map.get("salesProceedsIndex"));

			if ((oldSalesProceeds != null) && (oldSalesProceeds.length != 0)) {
				try {
					if ((oldSalesProceeds != null) && (oldSalesProceeds.length != 0)) {
						for (int i = 0; i < oldSalesProceeds.length; i++) {
							OBSalesProceeds objSalesProceeds = (OBSalesProceeds) oldSalesProceeds[i];
							if (salesProceedsIndex == i) {
								if (objSalesProceeds.getProceedsID() != ICMSConstant.LONG_INVALID_VALUE) {
									objSalesProceeds.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							salesProceedsList.add(objSalesProceeds);
						}
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
			}
			objBuildUp[buildUpIndex].setSalesProceedsList((ISalesProceeds[]) salesProceedsList
					.toArray(new ISalesProceeds[0]));
			objBridgingLoan.setBuildUpList(objBuildUp);
			return objBridgingLoan;
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
			SalesProceedsForm aForm = (SalesProceedsForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			int buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));
			DefaultLogger.debug(this, "buildUpIndex: " + buildUpIndex);

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IBuildUp[] objBuildUp = (IBuildUp[]) objBridgingLoan.getBuildUpList();
				ISalesProceeds[] objSalesProceedsList = (ISalesProceeds[]) objBuildUp[buildUpIndex]
						.getSalesProceedsList();
				int salesProceedsIndex = Integer.parseInt((String) map.get("salesProceedsIndex"));
				DefaultLogger.debug(this, "salesProceedsIndex: " + salesProceedsIndex);

				if (objSalesProceedsList != null) {
					aForm.setProceedsDate(DateUtil.formatDate(locale, objSalesProceedsList[salesProceedsIndex]
							.getProceedsDate()));
					aForm.setPurpose(objSalesProceedsList[salesProceedsIndex].getPurpose());
					if (objSalesProceedsList[salesProceedsIndex].getPurposePercent() != ICMSConstant.FLOAT_INVALID_VALUE) {
						aForm.setPurposePercent(new DecimalFormat("#").format(objSalesProceedsList[salesProceedsIndex]
								.getPurposePercent()));
					}
					aForm.setBankName(objSalesProceedsList[salesProceedsIndex].getBankName());
					aForm.setChequeNo(objSalesProceedsList[salesProceedsIndex].getChequeNo());
					if (objSalesProceedsList[salesProceedsIndex].getReceiveAmount() != null) {
						aForm.setReceiveCurrency(objSalesProceedsList[salesProceedsIndex].getReceiveAmount()
								.getCurrencyCode());
						aForm.setReceiveAmount(CurrencyManager.convertToString(locale,
								objSalesProceedsList[salesProceedsIndex].getReceiveAmount()));
					}
					aForm.setStatus(objSalesProceedsList[salesProceedsIndex].getStatus());
					aForm.setRemarks(objSalesProceedsList[salesProceedsIndex].getRemarks());

					aForm.setDistributeDate(DateUtil.formatDate(locale, objSalesProceedsList[salesProceedsIndex]
							.getDistributeDate()));
					if (objSalesProceedsList[salesProceedsIndex].getDistributeAmount() != null) {
						aForm.setDistributeCurrency(objSalesProceedsList[salesProceedsIndex].getDistributeAmount()
								.getCurrencyCode());
						aForm.setDistributeAmount(CurrencyManager.convertToString(locale,
								objSalesProceedsList[salesProceedsIndex].getDistributeAmount()));
					}

					aForm.setIsToTL1(TypeConverter
							.convertBooleanToStringEquivalent(objSalesProceedsList[salesProceedsIndex].getIsToTL1()));
					if (objSalesProceedsList[salesProceedsIndex].getTL1Amount() != null) {
						if (objSalesProceedsList[salesProceedsIndex].getTL1Amount().getCurrencyCode() != null) {
							aForm.setTL1Currency(objSalesProceedsList[salesProceedsIndex].getTL1Amount()
									.getCurrencyCode());
							aForm.setTL1Amount(CurrencyManager.convertToString(locale,
									objSalesProceedsList[salesProceedsIndex].getTL1Amount()));
						}
					}
					aForm.setIsToOD(TypeConverter
							.convertBooleanToStringEquivalent(objSalesProceedsList[salesProceedsIndex].getIsToOD()));
					if (objSalesProceedsList[salesProceedsIndex].getOdAmount() != null) {
						if (objSalesProceedsList[salesProceedsIndex].getOdAmount().getCurrencyCode() != null) {
							aForm.setOdCurrency(objSalesProceedsList[salesProceedsIndex].getOdAmount()
									.getCurrencyCode());
							aForm.setOdAmount(CurrencyManager.convertToString(locale,
									objSalesProceedsList[salesProceedsIndex].getOdAmount()));
						}
					}
					aForm.setIsToFDR(TypeConverter
							.convertBooleanToStringEquivalent(objSalesProceedsList[salesProceedsIndex].getIsToFDR()));
					if (objSalesProceedsList[salesProceedsIndex].getFdrAmount() != null) {
						if (objSalesProceedsList[salesProceedsIndex].getFdrAmount().getCurrencyCode() != null) {
							aForm.setFdrCurrency(objSalesProceedsList[salesProceedsIndex].getFdrAmount()
									.getCurrencyCode());
							aForm.setFdrAmount(CurrencyManager.convertToString(locale,
									objSalesProceedsList[salesProceedsIndex].getFdrAmount()));
						}
					}
					aForm.setIsToHDA(TypeConverter
							.convertBooleanToStringEquivalent(objSalesProceedsList[salesProceedsIndex].getIsToHDA()));
					if (objSalesProceedsList[salesProceedsIndex].getHdaAmount() != null) {
						if (objSalesProceedsList[salesProceedsIndex].getHdaAmount().getCurrencyCode() != null) {
							aForm.setHdaCurrency(objSalesProceedsList[salesProceedsIndex].getHdaAmount()
									.getCurrencyCode());
							aForm.setHdaAmount(CurrencyManager.convertToString(locale,
									objSalesProceedsList[salesProceedsIndex].getHdaAmount()));
						}
					}
					aForm
							.setIsToOthers(TypeConverter
									.convertBooleanToStringEquivalent(objSalesProceedsList[salesProceedsIndex]
											.getIsToOthers()));
					aForm.setOthersAccount(objSalesProceedsList[salesProceedsIndex].getOthersAccount());
					if (objSalesProceedsList[salesProceedsIndex].getOthersAmount() != null) {
						if (objSalesProceedsList[salesProceedsIndex].getOthersAmount().getCurrencyCode() != null) {
							aForm.setOthersCurrency(objSalesProceedsList[salesProceedsIndex].getOthersAmount()
									.getCurrencyCode());
							aForm.setOthersAmount(CurrencyManager.convertToString(locale,
									objSalesProceedsList[salesProceedsIndex].getOthersAmount()));
						}
					}
				}
			}
			return aForm;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, e.toString());
		}
		return null;
	}
}