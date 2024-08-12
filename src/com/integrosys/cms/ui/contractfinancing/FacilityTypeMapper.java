/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing;

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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IAdvance;
import com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.OBContractFacilityType;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class FacilityTypeMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public FacilityTypeMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "facilityTypeIndex", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });

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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityTypeForm aForm = (FacilityTypeForm) cForm;

		IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

		IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

		try {
			if (FacilityTypeAction.EVENT_CREATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				// define FacilityType object
				OBContractFacilityType newFacilityType = new OBContractFacilityType();
				newFacilityType.setFacilityType(aForm.getFacilityType());
				newFacilityType.setFacilityTypeOthers(aForm.getFacilityOthers());
				newFacilityType.setFacilityDate(DateUtil.convertDate(locale, aForm.getFacilityDate()));
				newFacilityType.setMoa(Integer.parseInt(aForm.getMoa()));
				if (!aForm.getMaxCapCurrency().equals("") && !aForm.getMaxCapAmount().equals("")) {
					newFacilityType.setMaxCap(CurrencyManager.convertToAmount(locale, aForm.getMaxCapCurrency(), aForm
							.getMaxCapAmount()));
				}
				newFacilityType.setRemarks(aForm.getRemarks());

				ArrayList facilityTypeList = new ArrayList();
				IContractFacilityType[] oldFacilityType = contractFinancingObj.getFacilityTypeList();
				boolean addNew = true;
				if ((oldFacilityType != null) && (oldFacilityType.length != 0)) {
					for (int i = 0; i < oldFacilityType.length; i++) {

						OBContractFacilityType obFacilityType = (OBContractFacilityType) oldFacilityType[i];
						facilityTypeList.add(obFacilityType);

						// check duplicate data
						if (!oldFacilityType[i].getIsDeleted()) {
							if (oldFacilityType[i].getFacilityType().equals(newFacilityType.getFacilityType())) {
								addNew = false;
								// todo: need route to wip??
							}
						}
					}
				}
				if (addNew) {
					facilityTypeList.add(newFacilityType);
				}

				contractFinancingObj.setFacilityTypeList((IContractFacilityType[]) facilityTypeList
						.toArray(new OBContractFacilityType[0]));

				return contractFinancingObj; // contractFinancingObj
			}
			else if (FacilityTypeAction.EVENT_UPDATE.equals(event)) {

				String facilityTypeIndex = (String) map.get("facilityTypeIndex");

				IContractFacilityType[] newFacilityType = contractFinancingObj.getFacilityTypeList();
				int i = Integer.parseInt(facilityTypeIndex);
				newFacilityType[i].setFacilityType(aForm.getFacilityType());
				newFacilityType[i].setFacilityTypeOthers(aForm.getFacilityOthers());
				newFacilityType[i].setFacilityDate(DateUtil.convertDate(locale, aForm.getFacilityDate()));
				newFacilityType[i].setMoa(Integer.parseInt(aForm.getMoa()));
				if (!aForm.getMaxCapCurrency().equals("") && !aForm.getMaxCapAmount().equals("")) {
					newFacilityType[i].setMaxCap(CurrencyManager.convertToAmount(locale, aForm.getMaxCapCurrency(),
							aForm.getMaxCapAmount()));
				}
				newFacilityType[i].setRemarks(aForm.getRemarks());

				contractFinancingObj.setFacilityTypeList(newFacilityType);
				return contractFinancingObj; // contractFinancingObj
			}
			else if (FacilityTypeAction.EVENT_DELETE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				String[] deletedBox = aForm.getDeletedBox();
				ArrayList facilityTypeList = new ArrayList();
				IContractFacilityType[] oldFacilityType = contractFinancingObj.getFacilityTypeList();
				if ((oldFacilityType != null) && (oldFacilityType.length != 0)) {
					for (int i = 0; i < oldFacilityType.length; i++) {
						boolean userDelete = false;
						if (deletedBox != null) {
							for (int j = 0; j < deletedBox.length; j++) {
								if (deletedBox[j].equals(String.valueOf(i))) {
									userDelete = true;

									IAdvance[] advanceList = contractFinancingObj.getAdvanceList();
									if (advanceList != null) {
										for (int k = 0; k < advanceList.length; k++) {
											if (!advanceList[k].getIsDeleted()) {
												if (advanceList[k].getFacilityType() == null) { // when
																								// get
																								// from
																								// db
																								// ,
																								// no
																								// facility
																								// type
													if (advanceList[k].getFacilityTypeID() == oldFacilityType[i]
															.getFacilityTypeID()) {
														userDelete = false;
														oldFacilityType[i].setHasChild(true);
														break; // cannot delete
																// if have
																// advancelist
													}
												}
												else {
													if (advanceList[k].getFacilityType().equals(
															oldFacilityType[i].getFacilityType())) {
														userDelete = false;
														oldFacilityType[i].setHasChild(true);
														break; // cannot delete
																// if have
																// advancelist
													}
												}

											}
										}
									}

									break;
								}
							}
						}
						if (userDelete) {
							OBContractFacilityType obFacilityType = (OBContractFacilityType) oldFacilityType[i];
							if (obFacilityType.getFacilityTypeID() != ICMSConstant.LONG_INVALID_VALUE) {
								obFacilityType.setIsDeleted(true);
								facilityTypeList.add(obFacilityType);
							}
							else {
								// not to add
							}
						}
						else {
							OBContractFacilityType obFacilityType = (OBContractFacilityType) oldFacilityType[i];
							facilityTypeList.add(obFacilityType);
						}
					}
				}
				contractFinancingObj.setFacilityTypeList((IContractFacilityType[]) facilityTypeList
						.toArray(new OBContractFacilityType[0]));
				return contractFinancingObj; // contractFinancingObj
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return contractFinancingObj;
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
			DefaultLogger.debug(this, "inside mapOb to form");
			FacilityTypeForm aForm = (FacilityTypeForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				OBContractFacilityType obFacilityType = (OBContractFacilityType) obj;

				aForm.setFacilityType(obFacilityType.getFacilityType());
				aForm.setFacilityOthers(obFacilityType.getFacilityTypeOthers());
				aForm.setFacilityDate(DateUtil.formatDate(locale, obFacilityType.getFacilityDate()));
				aForm.setMoa(new DecimalFormat("#").format(obFacilityType.getMoa()));
				if (obFacilityType.getMaxCap() != null) {
					aForm.setMaxCapCurrency(obFacilityType.getMaxCap().getCurrencyCode());
					aForm.setMaxCapAmount(new DecimalFormat("#").format(obFacilityType.getMaxCap().getAmount()));
				}
				aForm.setRemarks(obFacilityType.getRemarks());
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in FacilityTypeMapper is" + e);
		}
		return null;
	}
}
