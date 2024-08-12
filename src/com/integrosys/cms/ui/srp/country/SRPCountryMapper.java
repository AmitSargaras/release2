/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryMapper.java,v 1.10 2005/09/08 08:58:35 hshii Exp $
 */
package com.integrosys.cms.ui.srp.country;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.OBCollateralParameter;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralParameterTrxValue;
import com.integrosys.cms.ui.common.ConvertFloatToString;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/09/08 08:58:35 $ Tag: $Name: $
 */
public class SRPCountryMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SRPCountryMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "CollateralParameterTrxValue", OBCollateralParameterTrxValue.class.getName(), SERVICE_SCOPE } });

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
		SRPCountryForm aForm = (SRPCountryForm) cForm;
		if (event.equals("maker_list_srpcountry") || event.equals("redirect_list_srpcountry")) {
			// build a SearchCriteira
			// retrieve
			return null;

		}
		else if (event.equals("maker_edit_srpcountry_read") || SRPCountryAction.EVENT_VIEW.equals(event)) {

			String countryCode = null;
			String securitySubTypeID = null;

			countryCode = aForm.getCountryIsoCodes()[0];
			securitySubTypeID = aForm.getSecuritySubTypeIds()[0];

			OBCollateralParameter obCollateralParameter = new OBCollateralParameter();
			obCollateralParameter.setCountryIsoCode(countryCode);
			obCollateralParameter.setSecuritySubTypeId(securitySubTypeID);

			return obCollateralParameter;
		}
		else if (event.equals("maker_edit_srpcountry") || event.equals("maker_edit_reject_edit")) {
			OBCollateralParameterTrxValue oldTrxValue = (OBCollateralParameterTrxValue) map
					.get("CollateralParameterTrxValue");

			OBCollateralParameter[] newCollateralParameters = null;
			if (event.equals("maker_edit_srpcountry")) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				newCollateralParameters = (OBCollateralParameter[]) oldTrxValue.getCollateralParameters();
			}
			else if (event.equals("maker_edit_reject_edit")) {
				// copy all old values from STAGING value int newBusinessValue.
				newCollateralParameters = (OBCollateralParameter[]) oldTrxValue.getStagingCollateralParameters();
			}

			if (newCollateralParameters != null) {
				String[] securitySubTypes = aForm.getSecuritySubTypeIds();
				String[] thresholdPrecents = aForm.getThresholdPercents();
				String[] valuationFrequencies = aForm.getValuationFrequencies();
				String[] valuationFrequencyUnits = aForm.getValuationFrequencyUnits();

				for (int i = 0; i < newCollateralParameters.length; i++) {

					for (int j = 0; j < securitySubTypes.length; j++) {
						if (newCollateralParameters[i].getSecuritySubTypeId().equals(securitySubTypes[j])) {

							newCollateralParameters[i].setThresholdPercent(Double.parseDouble(thresholdPrecents[j]));
							newCollateralParameters[i].setValuationFrequency(Integer.parseInt(valuationFrequencies[j]));
							newCollateralParameters[i].setValuationFrequencyUnit(valuationFrequencyUnits[j]);

						}
					}
				}

				return Arrays.asList(newCollateralParameters);
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
			String event = (String) map.get(ICommonEventConstant.EVENT);
			SRPCountryForm aForm = (SRPCountryForm) cForm;
			if (obj != null) {
				ICollateralParameter[] sr = (ICollateralParameter[]) obj;
				if (sr.length != 0) {
					String freqUnit[] = new String[sr.length];
					String threshold[] = new String[sr.length];
					String frequency[] = new String[sr.length];
					String indicator[] = new String[sr.length];
					for (int i = 0; i < sr.length; i++) {
						if (sr[i].getValuationFrequencyUnit() != null) {
							freqUnit[i] = sr[i].getValuationFrequencyUnit();
						}
						else {
							freqUnit[i] = "";
						}

						String tempStr = ConvertFloatToString.getInstance().convertDouble(sr[i].getThresholdPercent());
						threshold[i] = tempStr;
						indicator[i] = tempStr;
						frequency[i] = sr[i].getValuationFrequency() + "";
					}
					aForm.setThresholdPercents(threshold);
					aForm.setIndicator(indicator);
					aForm.setValuationFrequencies(frequency);
					aForm.setValuationFrequencyUnits(freqUnit);
				}
				aForm.setEvent(event);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in SRPCountryMapper is", e);
		}
		return null;

	}
}
