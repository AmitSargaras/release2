/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/MaintainAssetLifeMapper.java,v 1 2007/01/30 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.collateral.bus.OBCollateralAssetLife;
import com.integrosys.cms.app.collateral.trx.assetlife.OBCollateralAssetLifeTrxValue;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Asset Life
 * Description: Map the value from database to the screen or from the screen
 * that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/30$ Tag: $Name$
 */

public class MaintainAssetLifeMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "CollateralAssetLifeTrxValue", OBCollateralAssetLifeTrxValue.class.getName(), SERVICE_SCOPE } });

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
		MaintainAssetLifeForm aForm = (MaintainAssetLifeForm) cForm;
		if (MaintainAssetLifeAction.EVENT_VIEW.equals(event) || event.equals("maker_view_assetlife")
				|| event.equals("maker_edit_assetlife")) {

			OBCollateralAssetLife obCollateralAssetLife = new OBCollateralAssetLife();
			return obCollateralAssetLife;

		}
		else if (event.equals("maker_close_assetlife_confirm")) {

			OBCollateralAssetLife obCollateralAssetLife = new OBCollateralAssetLife();
			return obCollateralAssetLife;

		}
		else if (event.equals("maker_edit_assetlife_confirm") || event.equals("maker_edit_assetlife_reject")) {
			// System.out.println(
			// "---------------------------------inside ifevent.equals(assetlife_maker_edit)--------------------------------"
			// );
			OBCollateralAssetLifeTrxValue oldTrxValue = (OBCollateralAssetLifeTrxValue) map
					.get("CollateralAssetLifeTrxValue");
			// System.out.println(
			// "---------------------------------after oldTrxValue: " +
			// oldTrxValue);

			// copy all old values from ORIGINAL value int newBusinessValue.
			OBCollateralAssetLife[] newCollateralAssetLifes = null;

			if (event.equals("maker_edit_assetlife_confirm")) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				newCollateralAssetLifes = (OBCollateralAssetLife[]) oldTrxValue.getCollateralAssetLifes();
			}
			else if (event.equals("maker_edit_assetlife_reject")) {
				// copy all old values from STAGING value int newBusinessValue.
				newCollateralAssetLifes = (OBCollateralAssetLife[]) oldTrxValue.getStagingCollateralAssetLifes();
			}

			if (newCollateralAssetLifes != null) {
				String[] securitySubTypeCodes = aForm.getSubTypeCodes();
				String[] yearValues = aForm.getYearValues();

				DefaultLogger.debug(this, " :::Debug : getSecuritySubTypeIds=");
				_print(aForm.getSubTypeCodes());
				DefaultLogger.debug(this, " :::Debug : getYearValues()=");
				_print(aForm.getYearValues());

				for (int i = 0; i < newCollateralAssetLifes.length; i++) {

					for (int j = 0; j < securitySubTypeCodes.length; j++) {
						if (newCollateralAssetLifes[i].getSubTypeCode().equals(securitySubTypeCodes[j])) {
							// set yearValue
							newCollateralAssetLifes[i].setLifeSpan(Integer.parseInt(yearValues[j]));

						}
					}
				}

				return Arrays.asList(newCollateralAssetLifes);
			}
		}
		return null;

	}

	private void _print(String[] x) {
		if (x == null) {
			DefaultLogger.debug(this, " IS NULL ");
		}

		for (int i = 0; i < x.length; i++) {
			DefaultLogger.debug(this, x[i]);
		}
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
			MaintainAssetLifeForm aForm = (MaintainAssetLifeForm) cForm;

			if (obj != null) {
				DefaultLogger.debug(this, "mapping OB TO FORM");
				ICollateralAssetLife[] sr = (ICollateralAssetLife[]) obj;

				if (sr.length != 0) {
					String yearValue[] = new String[sr.length];

					for (int i = 0; i < sr.length; i++) {
						yearValue[i] = String.valueOf(sr[i].getLifeSpan());
					}

					aForm.setYearValues(yearValue);

				}
			}

			DefaultLogger.debug(this, "Going out of mapOb to form ");

			return aForm;

		}
		catch (Exception e) {

			DefaultLogger.error(this, "error in SRPGlobalMapper is" + e);
		}

		return null;
	}
}
