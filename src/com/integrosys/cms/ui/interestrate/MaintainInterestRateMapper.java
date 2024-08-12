/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/MaintainInterestRateMapper.java,v 1 2007/02/09 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.OBInterestRate;
import com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue;
import com.integrosys.cms.ui.common.ConvertFloatToString;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Interest
 * Rate Description: Map the value from database to the screen or from the
 * screen that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class MaintainInterestRateMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "InterestRateTrxValue", "com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue",
						SERVICE_SCOPE }, { "InitialInterestRate", "java.util.list", FORM_SCOPE } });

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
		MaintainInterestRateForm aForm = (MaintainInterestRateForm) cForm;
		if (MaintainInterestRateAction.EVENT_LIST.equals(event) || "checker_view".equals(event)) {

			String intRateType = aForm.getTypeInterestRates();
			Date mthYear = aForm.getMonthYearDt();

			OBInterestRate obInterestRate = new OBInterestRate();

			obInterestRate.setIntRateType(intRateType);
			obInterestRate.setIntRateDate(mthYear);

			return obInterestRate;

		}
		else if (event.equals("maker_close_interestrate_confirm")) {

			OBInterestRate obInterestRate = new OBInterestRate();
			return obInterestRate;

		}
		else if (event.equals("maker_edit_interestrate_confirm") || event.equals("maker_edit_interestrate_reject")
				|| "maker_edit_reject_confirm".equals(event)) {

			// System.out.println(
			// "---------------------------------inside ifevent.equals(assetlife_maker_edit)--------------------------------"
			// );
			OBInterestRateTrxValue oldTrxValue = (OBInterestRateTrxValue) map.get("InterestRateTrxValue");
			// System.out.println(
			// "---------------------------------after oldTrxValue: " +
			// oldTrxValue);

			// copy all old values from ORIGINAL value int newBusinessValue.
			OBInterestRate[] newInterestRates = null;

			if (event.equals("maker_edit_interestrate_confirm")) {
				// copy all old values from ORIGINAL value int newBusinessValue.
				newInterestRates = (OBInterestRate[]) oldTrxValue.getInterestRates();
			}
			else if (event.equals("maker_edit_interestrate_reject") || "maker_edit_reject_confirm".equals(event)) {
				// copy all old values from STAGING value int newBusinessValue.
				newInterestRates = (OBInterestRate[]) oldTrxValue.getStagingInterestRates();
			}

			if (newInterestRates != null) {
				String[] intPerc = aForm.getIntRatePercent();
				String[] intDate = aForm.getIntRateDate();

				for (int i = 0; i < newInterestRates.length; i++) {

					for (int j = 0; j < intDate.length; j++) {

						if (UIUtil.formatDate(newInterestRates[i].getIntRateDate()).equals(intDate[j])) {
							// set MaxValue
							if ((intPerc[j] != null) && (intPerc[j].trim().length() != 0)) {
								newInterestRates[i].setIntRatePercent(new Double(intPerc[j]));
							}
							else {
								newInterestRates[i].setIntRatePercent(null);
							}
						}
					}

				}

				return Arrays.asList(newInterestRates);
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			MaintainInterestRateForm aForm = (MaintainInterestRateForm) cForm;
			if (obj != null) {
				IInterestRate[] sr = (IInterestRate[]) obj;

				if (sr.length != 0) {
					String intRateDate[] = new String[sr.length];
					String maxValue[] = new String[sr.length];
					for (int i = 0; i < sr.length; i++) {
						Double per = sr[i].getIntRatePercent();
						if (per != null) {
							maxValue[i] = ConvertFloatToString.getInstance().convertDouble(per.doubleValue());
						}

						intRateDate[i] = DateUtil.formatDate(locale, sr[i].getIntRateDate());
					}
					aForm.setIntRateDate(intRateDate);
					aForm.setIntRatePercent(maxValue);
				}

				DefaultLogger.debug(this, "Before putting vector result");
			}
			else {
				aForm.setIntRateDate(null);
				aForm.setIntRatePercent(null);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in MaintainInterestRateMapper is" + e);
		}
		return null;
	}

}
