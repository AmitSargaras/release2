/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalMapper.java,v 1.5 2005/09/08 08:56:22 hshii Exp $
 */
package com.integrosys.cms.ui.srp.global;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralSubTypeTrxValue;
import com.integrosys.cms.ui.common.ConvertFloatToString;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/08 08:56:22 $ Tag: $Name: $
 */
public class SRPGlobalMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SRPGlobalMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "CollateralSubTypeTrxValue", OBCollateralSubTypeTrxValue.class.getName(), SERVICE_SCOPE } });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		SRPGlobalForm aForm = (SRPGlobalForm) cForm;
		if (event.equals("maker_list_srpglobal") || event.equals("redirect_list_srpglobal")) {
			// build a SearchCriteira
			// retrieve
			return null;

		}
		else if (SRPGlobalAction.EVENT_VIEW.equals(event) || event.equals("maker_edit_srpglobal_read")) {

			String securityTypeCode = aForm.getSecurityTypeCode();

			OBCollateralSubType obCollateralSubType = new OBCollateralSubType();

			obCollateralSubType.setTypeCode(securityTypeCode);

			return obCollateralSubType;

		}
		else if (event.equals("maker_edit_srpglobal_read_rejected") || event.equals("maker_cncl_reject_edit")) {

			OBCollateralSubTypeTrxValue oldTrxValue = (OBCollateralSubTypeTrxValue) map
					.get("CollateralSubTypeTrxValue");

			String securityTypeCode = oldTrxValue.getCollateralSubTypes()[0].getTypeCode();
			OBCollateralSubType obCollateralSubType = new OBCollateralSubType();

			obCollateralSubType.setTypeCode(securityTypeCode);

			return obCollateralSubType;
		}
		else if (event.equals("maker_edit_srpglobal") || event.equals("maker_edit_reject_edit")) {
			OBCollateralSubTypeTrxValue oldTrxValue = (OBCollateralSubTypeTrxValue) map
					.get("CollateralSubTypeTrxValue");

			// copy all old values from ORIGINAL value int newBusinessValue.
			OBCollateralSubType[] newCollateralSubTypes = null;

			if (event.equals("maker_edit_srpglobal")) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				newCollateralSubTypes = (OBCollateralSubType[]) oldTrxValue.getCollateralSubTypes();
				DefaultLogger.debug(this,"successfull copy all old values from ORIGINAL value");
			}
			else if (event.equals("maker_edit_reject_edit")) {
				// copy all old values from STAGING value int newBusinessValue.
				newCollateralSubTypes = (OBCollateralSubType[]) oldTrxValue.getStagingCollateralSubTypes();
			}

			if (newCollateralSubTypes != null) {
				String[] securitySubTypeCodes = aForm.getSubTypeCodes();
				String[] maxValues = aForm.getMaxValues();

				String[] subTypeStandardisedApproach = aForm.getSubTypeStandardisedApproach();
				String[] subTypeFoundationIRB = aForm.getSubTypeFoundationIRB();
				String[] subTypeAdvancedIRB = aForm.getSubTypeAdvancedIRB();

				for (int i = 0; i < newCollateralSubTypes.length; i++) {

					for (int j = 0; j < securitySubTypeCodes.length; j++) {
						if (newCollateralSubTypes[i].getSubTypeCode().equals(securitySubTypeCodes[j])) {
							// set MaxValue
							newCollateralSubTypes[i].setMaxValue(Double.parseDouble(maxValues[j]));

							newCollateralSubTypes[i].setSubTypeStandardisedApproach(false);
							newCollateralSubTypes[i].setSubTypeFoundationIRB(false);
							newCollateralSubTypes[i].setSubTypeAdvancedIRB(false);

							if (subTypeStandardisedApproach != null) {
								for (int k = 0; k < subTypeStandardisedApproach.length; k++) {
									if (Integer.parseInt(subTypeStandardisedApproach[k]) == j) {
										newCollateralSubTypes[i].setSubTypeStandardisedApproach(true);
										break;
									}
								}
							}

							if (subTypeFoundationIRB != null) {
								for (int k = 0; k < subTypeFoundationIRB.length; k++) {
									if (Integer.parseInt(subTypeFoundationIRB[k]) == j) {
										newCollateralSubTypes[i].setSubTypeFoundationIRB(true);
										break;
									}
								}
							}

							if (subTypeAdvancedIRB != null) {
								for (int k = 0; k < subTypeAdvancedIRB.length; k++) {
									if (Integer.parseInt(subTypeAdvancedIRB[k]) == j) {
										newCollateralSubTypes[i].setSubTypeAdvancedIRB(true);
										break;
									}
								}
							}
						}
					}
				}

				return Arrays.asList(newCollateralSubTypes);
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
			DefaultLogger.debug(this, "inside mapOb to form ");
			SRPGlobalForm aForm = (SRPGlobalForm) cForm;
			if (obj != null) {
				ICollateralSubType[] sr = (ICollateralSubType[]) obj;
				if (sr.length != 0) {
					String maxValue[] = new String[sr.length];

					for (int i = 0; i < sr.length; i++) {
						maxValue[i] = ConvertFloatToString.getInstance().convertDouble(sr[i].getMaxValue());
					}
					aForm.setMaxValues(maxValue);
				}
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in SRPGlobalMapper is", e);
		}
		return null;
	}
}
