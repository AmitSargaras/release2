/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.buildup;

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
import com.integrosys.cms.app.bridgingloan.bus.OBBuildUp;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class BuildUpMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public BuildUpMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "buildUpIndex", "java.lang.String", REQUEST_SCOPE },
				{ "current_page", "java.lang.String", REQUEST_SCOPE },
				{ "copyFromIndex", "java.lang.String", REQUEST_SCOPE },
				{ "noOfCopy", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB (BuildUpMapper)");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		DefaultLogger.debug(this, "event=" + event);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		BuildUpForm aForm = (BuildUpForm) cForm;
		if (BuildUpAction.EVENT_CREATE.equals(event)) {
			DefaultLogger.debug("\n******", "EVENT_CREATE (BuildUpMapper)");

			IBuildUp[] oldBuildUp = (IBuildUp[]) objBridgingLoan.getBuildUpList();
			ArrayList buildUpList = new ArrayList();
			try {
				OBBuildUp newBuildUp = new OBBuildUp();
				newBuildUp.setPropertyType(aForm.getPropertyType());
				newBuildUp.setUnitID(aForm.getUnitID());
				newBuildUp.setBlockNo(aForm.getBlockNo());
				newBuildUp.setTitleNo(aForm.getTitleNo());
				newBuildUp.setUnitNo(aForm.getUnitNo());
				if (!aForm.getIsUnitDischarged().equals("")) {
					newBuildUp.setIsUnitDischarged(TypeConverter.convertStringToBooleanEquivalent(aForm
							.getIsUnitDischarged()));
				}
				if (!aForm.getApproxArea().equals("") && !aForm.getApproxAreaUOM().equals("")) {
					newBuildUp.setApproxArea(TypeConverter.convertToArea(TypeConverter.convertToObjectType(Double
							.parseDouble(aForm.getApproxArea())), aForm.getApproxAreaUOM()));
				}
				if (!aForm.getApproxAreaSecondary().equals("") && !aForm.getApproxAreaUOMSecondary().equals("")) {
					newBuildUp.setApproxAreaSecondary(TypeConverter.convertToArea(TypeConverter
							.convertToObjectType(Double.parseDouble(aForm.getApproxAreaSecondary())), aForm
							.getApproxAreaUOMSecondary()));
				}
				if (!aForm.getRedemptionCurrency().equals("") && !aForm.getRedemptionAmount().equals("")) {
					newBuildUp.setRedemptionAmount(CurrencyManager.convertToAmount(locale, aForm
							.getRedemptionCurrency(), aForm.getRedemptionAmount()));
				}
				if (!aForm.getSalesCurrency().equals("") && !aForm.getSalesAmount().equals("")) {
					newBuildUp.setSalesAmount(CurrencyManager.convertToAmount(locale, aForm.getSalesCurrency(), aForm
							.getSalesAmount()));
				}
				if (!aForm.getSalesDate().equals("")) {
					newBuildUp.setSalesDate(DateUtil.convertDate(locale, aForm.getSalesDate()));
				}
				newBuildUp.setPurchaserName(aForm.getPurchaserName());
				newBuildUp.setEndFinancier(aForm.getEndFinancier());
				newBuildUp.setBuRemarks(aForm.getBuRemarks());

				if (!aForm.getTenancyDate().equals("")) {
					newBuildUp.setTenancyDate(DateUtil.convertDate(locale, aForm.getTenancyDate()));
				}
				newBuildUp.setTenantName(aForm.getTenantName());
				if (!aForm.getTenancyPeriod().equals("")) {
					newBuildUp.setTenancyPeriod(Integer.parseInt(aForm.getTenancyPeriod()));
					newBuildUp.setTenancyPeriodUnit(aForm.getTenancyPeriodUnit());
				}
				if (!aForm.getTenancyExpiryDate().equals("")) {
					newBuildUp.setTenancyExpiryDate(DateUtil.convertDate(locale, aForm.getTenancyExpiryDate()));
				}
				if (!aForm.getRentalCurrency().equals("") && !aForm.getRentalAmount().equals("")) {
					newBuildUp.setRentalAmount(CurrencyManager.convertToAmount(locale, aForm.getRentalCurrency(), aForm
							.getRentalAmount()));
				}
				newBuildUp.setPaymentFrequency(aForm.getPaymentFrequency());

				if ((oldBuildUp != null) && (oldBuildUp.length > 0)) {
					for (int i = 0; i < oldBuildUp.length; i++) {
						OBBuildUp objBuildUp = (OBBuildUp) oldBuildUp[i];
						buildUpList.add(objBuildUp);
					}
				}
				buildUpList.add(newBuildUp);
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception error: " + e);
			}
			objBridgingLoan.setBuildUpList((IBuildUp[]) buildUpList.toArray(new IBuildUp[0]));
			return objBridgingLoan;
		}
		else if (BuildUpAction.EVENT_UPDATE.equals(event) || BuildUpAction.EVENT_MAKER_NAVIGATE_TAB.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_UPDATE && EVENT_MAKER_NAVIGATE_TAB ");
			String currentPage = String.valueOf(map.get("current_page"));
			if (objBridgingLoan != null) {
				if (!BuildUpAction.EVENT_LIST_SUMMARY.equals(currentPage)) { // Skip
																				// update
																				// if
																				// the
																				// currrent
																				// page
																				// is
																				// list_summary
					IBuildUp[] newBuildUp = (IBuildUp[]) objBridgingLoan.getBuildUpList();
					try {
						if ((newBuildUp != null) && (newBuildUp.length > 0)) {
							int buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));
							DefaultLogger.debug(this, "buildUpIndex: " + buildUpIndex);

							newBuildUp[buildUpIndex].setPropertyType(aForm.getPropertyType());
							newBuildUp[buildUpIndex].setUnitID(aForm.getUnitID());
							newBuildUp[buildUpIndex].setBlockNo(aForm.getBlockNo());
							newBuildUp[buildUpIndex].setTitleNo(aForm.getTitleNo());
							newBuildUp[buildUpIndex].setUnitNo(aForm.getUnitNo());
							if (!aForm.getIsUnitDischarged().equals("")) {
								newBuildUp[buildUpIndex].setIsUnitDischarged(TypeConverter
										.convertStringToBooleanEquivalent(aForm.getIsUnitDischarged()));
							}
							if (!aForm.getApproxArea().equals("")) {
								newBuildUp[buildUpIndex].setApproxArea(TypeConverter.convertToArea(TypeConverter
										.convertToObjectType(Double.parseDouble(aForm.getApproxArea())), aForm
										.getApproxAreaUOM()));
							}
							if (!aForm.getApproxAreaSecondary().equals("")
									&& !aForm.getApproxAreaUOMSecondary().equals("")) {
								newBuildUp[buildUpIndex].setApproxAreaSecondary(TypeConverter.convertToArea(
										TypeConverter.convertToObjectType(Double.parseDouble(aForm
												.getApproxAreaSecondary())), aForm.getApproxAreaUOMSecondary()));
							}
							if (!aForm.getRedemptionCurrency().equals("") && !aForm.getRedemptionAmount().equals("")) {
								newBuildUp[buildUpIndex].setRedemptionAmount(CurrencyManager.convertToAmount(locale,
										aForm.getRedemptionCurrency(), aForm.getRedemptionAmount()));
							}
							if (!aForm.getSalesCurrency().equals("") && !aForm.getSalesAmount().equals("")) {
								newBuildUp[buildUpIndex].setSalesAmount(CurrencyManager.convertToAmount(locale, aForm
										.getSalesCurrency(), aForm.getSalesAmount()));
							}
							if (!aForm.getSalesDate().equals("")) {
								newBuildUp[buildUpIndex].setSalesDate(DateUtil
										.convertDate(locale, aForm.getSalesDate()));
							}
							newBuildUp[buildUpIndex].setPurchaserName(aForm.getPurchaserName());
							newBuildUp[buildUpIndex].setEndFinancier(aForm.getEndFinancier());
							newBuildUp[buildUpIndex].setBuRemarks(aForm.getBuRemarks());

							if (!aForm.getTenancyDate().equals("")) {
								newBuildUp[buildUpIndex].setTenancyDate(DateUtil.convertDate(locale, aForm
										.getTenancyDate()));
							}
							newBuildUp[buildUpIndex].setTenantName(aForm.getTenantName());
							if (!aForm.getTenancyPeriod().equals("")) {
								newBuildUp[buildUpIndex].setTenancyPeriod(Integer.parseInt(aForm.getTenancyPeriod()));
								newBuildUp[buildUpIndex].setTenancyPeriodUnit(aForm.getTenancyPeriodUnit());
							}
							if (!aForm.getTenancyExpiryDate().equals("")) {
								newBuildUp[buildUpIndex].setTenancyExpiryDate(DateUtil.convertDate(locale, aForm
										.getTenancyExpiryDate()));
							}
							if (!aForm.getRentalCurrency().equals("") && !aForm.getRentalAmount().equals("")) {
								newBuildUp[buildUpIndex].setRentalAmount(CurrencyManager.convertToAmount(locale, aForm
										.getRentalCurrency(), aForm.getRentalAmount()));
							}
							newBuildUp[buildUpIndex].setPaymentFrequency(aForm.getPaymentFrequency());

							objBridgingLoan.setBuildUpList(newBuildUp);
						}
						else if (newBuildUp == null) { // For firstly new
														// buildup
							ArrayList buildUpList = new ArrayList();
							OBBuildUp newObjBuildUp = new OBBuildUp();

							newObjBuildUp.setPropertyType(aForm.getPropertyType());
							newObjBuildUp.setUnitID(aForm.getUnitID());
							newObjBuildUp.setBlockNo(aForm.getBlockNo());
							newObjBuildUp.setTitleNo(aForm.getTitleNo());
							newObjBuildUp.setUnitNo(aForm.getUnitNo());
							if (!aForm.getIsUnitDischarged().equals("")) {
								newObjBuildUp.setIsUnitDischarged(TypeConverter.convertStringToBooleanEquivalent(aForm
										.getIsUnitDischarged()));
							}
							if (!aForm.getApproxArea().equals("") && !aForm.getApproxAreaUOM().equals("")) {
								newObjBuildUp.setApproxArea(TypeConverter.convertToArea(TypeConverter
										.convertToObjectType(Double.parseDouble(aForm.getApproxArea())), aForm
										.getApproxAreaUOM()));
							}
							if (!aForm.getApproxAreaSecondary().equals("")
									&& !aForm.getApproxAreaUOMSecondary().equals("")) {
								newObjBuildUp.setApproxAreaSecondary(TypeConverter.convertToArea(TypeConverter
										.convertToObjectType(Double.parseDouble(aForm.getApproxAreaSecondary())), aForm
										.getApproxAreaUOMSecondary()));
							}
							if (!aForm.getRedemptionCurrency().equals("") && !aForm.getRedemptionAmount().equals("")) {
								newObjBuildUp.setRedemptionAmount(CurrencyManager.convertToAmount(locale, aForm
										.getRedemptionCurrency(), aForm.getRedemptionAmount()));
							}
							if (!aForm.getSalesCurrency().equals("") && !aForm.getSalesAmount().equals("")) {
								newObjBuildUp.setSalesAmount(CurrencyManager.convertToAmount(locale, aForm
										.getSalesCurrency(), aForm.getSalesAmount()));
							}
							if (!aForm.getSalesDate().equals("")) {
								newObjBuildUp.setSalesDate(DateUtil.convertDate(locale, aForm.getSalesDate()));
							}
							newObjBuildUp.setPurchaserName(aForm.getPurchaserName());
							newObjBuildUp.setEndFinancier(aForm.getEndFinancier());
							newObjBuildUp.setBuRemarks(aForm.getBuRemarks());

							if (!aForm.getTenancyDate().equals("")) {
								newObjBuildUp.setTenancyDate(DateUtil.convertDate(locale, aForm.getTenancyDate()));
							}
							newObjBuildUp.setTenantName(aForm.getTenantName());
							if (!aForm.getTenancyPeriod().equals("")) {
								newObjBuildUp.setTenancyPeriod(Integer.parseInt(aForm.getTenancyPeriod()));
								newObjBuildUp.setTenancyPeriodUnit(aForm.getTenancyPeriodUnit());
							}
							if (!aForm.getTenancyExpiryDate().equals("")) {
								newObjBuildUp.setTenancyExpiryDate(DateUtil.convertDate(locale, aForm
										.getTenancyExpiryDate()));
							}
							if (!aForm.getRentalCurrency().equals("") && !aForm.getRentalAmount().equals("")) {
								newObjBuildUp.setRentalAmount(CurrencyManager.convertToAmount(locale, aForm
										.getRentalCurrency(), aForm.getRentalAmount()));
							}
							newObjBuildUp.setPaymentFrequency(aForm.getPaymentFrequency());

							buildUpList.add(newObjBuildUp);
							objBridgingLoan.setBuildUpList((IBuildUp[]) buildUpList.toArray(new IBuildUp[0]));
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				return objBridgingLoan;
			}
		}
		else if (BuildUpAction.EVENT_DELETE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_DELETE");
			ArrayList buildUpList = new ArrayList();
			IBuildUp[] oldBuildUp = objBridgingLoan.getBuildUpList();
			int buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));

			// TODO: Sales Proceeds not included in the deletion process
			try {
				if ((oldBuildUp != null) && (oldBuildUp.length > 0)) {
					for (int i = 0; i < oldBuildUp.length; i++) {
						// If child record is dependent to parent record, then
						// skip deletion
						if ((oldBuildUp[i].getSalesProceedsList() == null)
								|| (oldBuildUp[i].getSalesProceedsList().length > 0)) {
							OBBuildUp objBuildUp = (OBBuildUp) oldBuildUp[i];
							if (buildUpIndex == i) {
								if (objBuildUp.getBuildUpID() != ICMSConstant.LONG_INVALID_VALUE) {
									objBuildUp.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							buildUpList.add(objBuildUp);
						}
					}
					objBridgingLoan.setBuildUpList((IBuildUp[]) buildUpList.toArray(new IBuildUp[0]));
				}
			}
			catch (Exception e) {
				DefaultLogger.error(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (BuildUpAction.EVENT_CREATE_COPY.equals(event)) {
			ArrayList buildUpList = new ArrayList();
			IBuildUp[] oldBuildUp = objBridgingLoan.getBuildUpList();
			int copyFromIndex = Integer.parseInt((String) map.get("copyFromIndex"));
			int noOfCopy = Integer.parseInt((String) map.get("noOfCopy"));

			try {
				if ((oldBuildUp != null) && (oldBuildUp.length > 0)) {
					if ((oldBuildUp != null) && (oldBuildUp.length > 0)) {
						for (int j = 0; j < oldBuildUp.length; j++) {
							OBBuildUp objBuildUp = (OBBuildUp) oldBuildUp[j];
							buildUpList.add(objBuildUp); // Add all old build up
						}
					}
					String unitId = oldBuildUp[copyFromIndex].getUnitID(); // Original
																			// copy
																			// of
																			// unitId
					String blockNo = oldBuildUp[copyFromIndex].getBlockNo(); // Block
																				// No
					String unitNo = oldBuildUp[copyFromIndex].getUnitNo(); // Original
																			// copy
																			// of
																			// unitNo

					String initUnitIdGen = unitId.substring(0, (unitId.indexOf("-"))); // From
																						// index
																						// 0
																						// to
																						// before
																						// -
					String initUnitNoGen = unitNo.substring(unitNo.indexOf("-") + 1, unitNo.length()); // From
																										// after
																										// -
																										// to
																										// index
																										// end
					String prexUnitIdGen = unitId.substring((unitId.indexOf("-") + 1), unitId.length()); // From
																											// after
																											// -
																											// to
																											// index
																											// end
					String prexUnitNoGen = unitNo.substring((unitNo.indexOf("#") + 1), unitNo.indexOf("-")); // From
																												// after
																												// #
																												// to
																												// before
																												// -

					for (int i = 1; i <= noOfCopy; i++) {
						OBBuildUp newBuildUp = new OBBuildUp(); // For new copy
																// of buildup
						String unitIdGen = Integer.toString(Integer.parseInt(prexUnitIdGen) + i);
						String prexGenId = "";

						for (int j = 0; j < prexUnitIdGen.length(); j++) {
							if ((prexUnitIdGen.length() - (j + unitIdGen.length())) == 0) {
								prexGenId += unitIdGen;
								break;
							}
							else {
								prexGenId += "0";
							}
						}
						String unitNoGen = Integer.toString(Integer.parseInt(prexUnitNoGen) + i);
						String prexGenNo = "";
						for (int k = 0; k < prexUnitNoGen.length(); k++) {
							if ((prexUnitNoGen.length() - (k + unitNoGen.length())) == 0) {
								prexGenNo += unitNoGen;
								break;
							}
							else {
								prexGenNo += "0";
							}
						}
						newBuildUp.setUnitID(initUnitIdGen + "-" + prexGenId);
						newBuildUp.setBlockNo(blockNo);
						newBuildUp.setUnitNo("#" + prexGenNo + "-" + initUnitNoGen);

						buildUpList.add(newBuildUp); // Add all new buildup
					}
				}
				objBridgingLoan.setBuildUpList((IBuildUp[]) buildUpList.toArray(new IBuildUp[0]));
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(this, e.toString());
			}
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
			DefaultLogger.debug(this, "******************** inside mapOb to form (BuildUpMapper)");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			BuildUpForm aForm = (BuildUpForm) cForm;

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IBuildUp[] objBuildUpList = (IBuildUp[]) objBridgingLoan.getBuildUpList();
				int buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));
				DefaultLogger.debug(this, "buildUpIndex: " + buildUpIndex);
				DefaultLogger.debug(this, "getRedemptionAmount: " + objBuildUpList[buildUpIndex].getRedemptionAmount());

				if (objBuildUpList != null) {
					aForm.setBuildUpID(String.valueOf(objBuildUpList[buildUpIndex].getBuildUpID())); // BuildUp
																										// Id
																										// for
																										// report
					aForm.setPropertyType(objBuildUpList[buildUpIndex].getPropertyType());
					aForm.setUnitID(objBuildUpList[buildUpIndex].getUnitID());
					aForm.setBlockNo(objBuildUpList[buildUpIndex].getBlockNo());
					aForm.setTitleNo(objBuildUpList[buildUpIndex].getTitleNo());
					aForm.setUnitNo(objBuildUpList[buildUpIndex].getUnitNo());
					aForm.setIsUnitDischarged(TypeConverter
							.convertBooleanToStringEquivalent(objBuildUpList[buildUpIndex].getIsUnitDischarged()));
					if (objBuildUpList[buildUpIndex].getApproxArea() != null) {
						aForm.setApproxAreaUOM(objBuildUpList[buildUpIndex].getApproxArea().getUnitOfMeasurement());
						aForm.setApproxArea(String.valueOf(objBuildUpList[buildUpIndex].getApproxArea().getAreaSize()));
					}
					if (objBuildUpList[buildUpIndex].getApproxAreaSecondary() != null) {
						aForm.setApproxAreaUOMSecondary(objBuildUpList[buildUpIndex].getApproxAreaSecondary()
								.getUnitOfMeasurement());
						aForm.setApproxAreaSecondary(String.valueOf(objBuildUpList[buildUpIndex]
								.getApproxAreaSecondary().getAreaSize()));
					}
					if (objBuildUpList[buildUpIndex].getRedemptionAmount() != null) {
						aForm.setRedemptionCurrency(objBuildUpList[buildUpIndex].getRedemptionAmount()
								.getCurrencyCode());
						aForm.setRedemptionAmount(CurrencyManager.convertToString(locale, objBuildUpList[buildUpIndex]
								.getRedemptionAmount()));
					}
					if (objBuildUpList[buildUpIndex].getSalesAmount() != null) {
						aForm.setSalesCurrency(objBuildUpList[buildUpIndex].getSalesAmount().getCurrencyCode());
						aForm.setSalesAmount(CurrencyManager.convertToString(locale, objBuildUpList[buildUpIndex]
								.getSalesAmount()));
					}
					aForm.setSalesDate(DateUtil.formatDate(locale, objBuildUpList[buildUpIndex].getSalesDate()));
					aForm.setPurchaserName(objBuildUpList[buildUpIndex].getPurchaserName());
					aForm.setEndFinancier(objBuildUpList[buildUpIndex].getEndFinancier());
					aForm.setBuRemarks(objBuildUpList[buildUpIndex].getBuRemarks());

					if (objBuildUpList[buildUpIndex].getTenancyDate() != null) {
						aForm
								.setTenancyDate(DateUtil.formatDate(locale, objBuildUpList[buildUpIndex]
										.getTenancyDate()));
					}
					aForm.setTenantName(objBuildUpList[buildUpIndex].getTenantName());
					if (objBuildUpList[buildUpIndex].getTenancyPeriod() != ICMSConstant.INT_INVALID_VALUE) {
						aForm.setTenancyPeriod(Integer.toString(objBuildUpList[buildUpIndex].getTenancyPeriod()));
						aForm.setTenancyPeriodUnit(objBuildUpList[buildUpIndex].getTenancyPeriodUnit());
					}
					aForm.setTenancyExpiryDate(DateUtil.formatDate(locale, objBuildUpList[buildUpIndex]
							.getTenancyExpiryDate()));
					if (objBuildUpList[buildUpIndex].getRentalAmount() != null) {
						aForm.setRentalCurrency(objBuildUpList[buildUpIndex].getRentalAmount().getCurrencyCode());
						aForm.setRentalAmount(CurrencyManager.convertToString(locale, objBuildUpList[buildUpIndex]
								.getRentalAmount()));
					}
					aForm.setPaymentFrequency(objBuildUpList[buildUpIndex].getPaymentFrequency());
				}
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