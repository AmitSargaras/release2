/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemparameters.autoval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Auto
 * Valuation Parameters Description: Map the value from database to the screen
 * or from the screen that user key in to database
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class AutoValuationParameterMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public AutoValuationParameterMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE }, });
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
		AutoValuationParameterForm aForm = (AutoValuationParameterForm) cForm;
		OBPropertyParameters obParam = new OBPropertyParameters();

		DefaultLogger.debug(this, "Going for Insert");

		// obParam.setParameterId(aForm.getParameterId());
		obParam.setPropertyType(aForm.getSecurityType());
		obParam.setSecuritySubTypeList(new ArrayList(Arrays.asList(aForm.getSelectedSecuritySubType())));
		DefaultLogger.debug("MAPPER HERE", "obParam.getCollateralSubTypeList() : " + obParam.getSecuritySubTypeList());

		obParam.setCountryCode(aForm.getCountry());
		obParam.setStateList(new ArrayList(Arrays.asList(aForm.getSelectedState())));
		obParam.setDistrictList(new ArrayList(Arrays.asList(aForm.getSelectedDistrict())));
		obParam.setMukimList(new ArrayList(Arrays.asList(aForm.getSelectedMukim())));
		obParam.setPostcode(aForm.getPostCode());

		if ((aForm.getFromLandArea() != null) && !("").equals(aForm.getFromLandArea())) {
			try {
				obParam.setLandAreaValueFrom(Long.parseLong(aForm.getFromLandArea()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not long-parseable.");
			}
		}
		obParam.setLandAreaUnitFrom(aForm.getFromLandAreaMeasure());

		if ((aForm.getToLandArea() != null) && !("").equals(aForm.getToLandArea())) {
			try {
				obParam.setLandAreaValueTo(Long.parseLong(aForm.getToLandArea()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not long-parseable.");
			}
		}
		obParam.setLandAreaUnitTo(aForm.getToLandAreaMeasure());

		if ((aForm.getFromBuiltUpArea() != null) && !("").equals(aForm.getFromBuiltUpArea())) {
			try {
				obParam.setBuildupAreaValueFrom(Long.parseLong(aForm.getFromBuiltUpArea()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not long-parseable.");
			}
		}
		obParam.setBuildupAreaUnitFrom(aForm.getFromBuiltUpAreaMeasure());

		if ((aForm.getToBuiltUpArea() != null) && !("").equals(aForm.getToBuiltUpArea())) {
			try {
				obParam.setBuildupAreaValueTo(Long.parseLong(aForm.getToBuiltUpArea()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not long-parseable.");
			}
		}
		obParam.setBuildupAreaUnitTo(aForm.getToBuiltUpAreaMeasure());

		if ((aForm.getMinCurrentOMV() != null) && !("").equals(aForm.getMinCurrentOMV())) {
			try {
				obParam.setMinimumCurrentOmv(Double.parseDouble(aForm.getMinCurrentOMV()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not double-parseable.");
			}
		}

		obParam.setOmvType(aForm.getOmvIndicator());

		if ((aForm.getOmvValue() != null) && !("").equals(aForm.getOmvValue())) {
			try {
				obParam.setVariationOMV(Long.parseLong(aForm.getOmvValue()));
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "value is not long-parseable.");
			}
		}

		obParam.setValuationDescription(aForm.getValuationDescription());

		DefaultLogger.debug(this, "Document object in Mapper" + obParam);
		DefaultLogger.debug("Mapper", "Document object in Mapper" + obParam);

		return obParam;
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		DefaultLogger.debug("Mappper", "inside mapOb to form ");

		AutoValuationParameterForm aForm = (AutoValuationParameterForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			OBPropertyParameters tempOb = (OBPropertyParameters) obj;

			// aForm.setParameterId(tempOb.getParameterId());
			if ((tempOb.getPropertyType() != null) && !("").equals(tempOb.getPropertyType())) {
				aForm.setSecurityType(tempOb.getPropertyType());
			}

			aForm.setSelectedSecuritySubType((String[]) tempOb.getSecuritySubTypeList().toArray(
					new String[tempOb.getSecuritySubTypeList().size()]));
			aForm.setCountry(tempOb.getCountryCode());
			aForm.setSelectedState((String[]) tempOb.getStateList().toArray(new String[tempOb.getStateList().size()]));
			aForm.setSelectedDistrict((String[]) tempOb.getDistrictList().toArray(
					new String[tempOb.getDistrictList().size()]));
			aForm.setSelectedMukim((String[]) tempOb.getMukimList().toArray(new String[tempOb.getMukimList().size()]));

			if ((tempOb.getPostcode() != null) && !("").equals(tempOb.getPostcode())) {
				aForm.setPostCode(tempOb.getPostcode());
			}

			if (tempOb.getLandAreaValueFrom() != ICMSConstant.LONG_INVALID_VALUE) {
				aForm.setFromLandArea(String.valueOf(tempOb.getLandAreaValueFrom()));
			}

			if ((tempOb.getLandAreaUnitFrom() != null) && !("").equals(tempOb.getLandAreaUnitFrom())) {
				aForm.setFromLandAreaMeasure(tempOb.getLandAreaUnitFrom());
			}

			if (tempOb.getLandAreaValueTo() != ICMSConstant.LONG_INVALID_VALUE) {
				aForm.setToLandArea(String.valueOf(tempOb.getLandAreaValueTo()));
			}

			if ((tempOb.getLandAreaUnitTo() != null) && !("").equals(tempOb.getLandAreaUnitTo())) {
				aForm.setToLandAreaMeasure(tempOb.getLandAreaUnitTo());
			}

			if (tempOb.getBuildupAreaValueFrom() != ICMSConstant.LONG_INVALID_VALUE) {
				aForm.setFromBuiltUpArea(String.valueOf(tempOb.getBuildupAreaValueFrom()));
			}

			if ((tempOb.getBuildupAreaUnitFrom() != null) && !("").equals(tempOb.getBuildupAreaUnitFrom())) {
				aForm.setFromBuiltUpAreaMeasure(tempOb.getBuildupAreaUnitFrom());
			}

			if (tempOb.getBuildupAreaValueTo() != ICMSConstant.LONG_INVALID_VALUE) {
				aForm.setToBuiltUpArea(String.valueOf(tempOb.getBuildupAreaValueTo()));
			}

			if ((tempOb.getBuildupAreaUnitTo() != null) && !("").equals(tempOb.getBuildupAreaUnitTo())) {
				aForm.setToBuiltUpAreaMeasure(tempOb.getBuildupAreaUnitTo());
			}

			if (tempOb.getMinimumCurrentOmv() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setMinCurrentOMV(String.valueOf(tempOb.getMinimumCurrentOmv()));
			}

			if ((tempOb.getOmvType() != null) && !("").equals(tempOb.getOmvType())) {
				aForm.setOmvIndicator(tempOb.getOmvType());
			}

			if (tempOb.getVariationOMV() != ICMSConstant.LONG_INVALID_VALUE) {
				aForm.setOmvValue(String.valueOf(tempOb.getVariationOMV()));
			}

			if ((tempOb.getValuationDescription() != null) && !("").equals(tempOb.getValuationDescription())) {
				aForm.setValuationDescription(tempOb.getValuationDescription());
			}

		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
