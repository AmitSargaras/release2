/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.OBThresholdRating;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Threshold
 * Rating Description: Map the value from database to the screen or from the
 * screen that user key in to OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class ThresholdRatingMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "lmtProfileTrxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, });

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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			ThresholdRatingForm aForm = (ThresholdRatingForm) cForm;
			int l = 0;

			if ("prepare_add_threshold_new_confirm".equals(event)
					|| "prepare_update_threshold_new_confirm".equals(event)) {

				IThresholdRating thresholdRating = new OBThresholdRating();

				thresholdRating.setRatingType(aForm.getCreditRatingType());
				thresholdRating.setRating(aForm.getCreditRating());
				// String curr = aForm.getThresholdCurCode();
				// String amt =
				// UIUtil.mapStringToBigDecimal(aForm.getThresholdAmt
				// ()).toString();
				// thresholdRating.setThresholdAmount(CurrencyManager.
				// convertToAmount(locale, curr , amt ));
				Amount thresholdAmount = new Amount(UIUtil.mapStringToBigDecimal(aForm.getThresholdAmt()),
						new CurrencyCode(aForm.getThresholdCurCode()));
//				System.out.println("inside mapper thresholdAmount = " + thresholdAmount);
				thresholdRating.setThresholdAmount(thresholdAmount);
//				System.out.println("inside mapper thresholdRating.getThresholdAmount() = "+ thresholdRating.getThresholdAmount());

				return thresholdRating;

			}
			else if ("prepare_add_threshold_update_confirm".equals(event)
					|| "prepare_update_threshold_update_confirm".equals(event)) {

				IThresholdRating newThresholdRating = null;
				IThresholdRating[] curThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
				if (curThresholdRating != null) {
					String ind = (String) map.get("index");
					int index = Integer.parseInt(ind);
					newThresholdRating = new OBThresholdRating();
					newThresholdRating.setRatingType(aForm.getCreditRatingType());
					newThresholdRating.setRating(aForm.getCreditRating());
					// String curr = aForm.getThresholdCurCode();
					// String amt =
					// UIUtil.mapStringToBigDecimal(aForm.getThresholdAmt
					// ()).toString();
					// newThresholdRating.setThresholdAmount(CurrencyManager.
					// convertToAmount(locale, curr , amt ));
					Amount thresholdAmount = new Amount(UIUtil.mapStringToBigDecimal(aForm.getThresholdAmt()),
							new CurrencyCode(aForm.getThresholdCurCode()));
//					System.out.println("inside mapper thresholdAmount = " + thresholdAmount);
					newThresholdRating.setThresholdAmount(thresholdAmount);
//					System.out.println("inside mapper newThresholdRating.getThresholdAmount() = "+ newThresholdRating.getThresholdAmount());
				}

				return newThresholdRating;

			}
			else if ("prepare_add_threshold_delete".equals(event) || "prepare_update_threshold_delete".equals(event)) {

				IThresholdRating[] oldThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
				IThresholdRating[] newThresholdRating = null;

				if (oldThresholdRating != null) {
					if (aForm.getDeletedThreshold() != null) {
						String[] index = aForm.getDeletedThreshold();

						if (index.length <= oldThresholdRating.length) {
							int itemDeleted = 0;

							for (int k = 0; k < index.length; k++) {
								if (Integer.parseInt(index[k]) < oldThresholdRating.length) {
									itemDeleted++;
								}
							}

							if (itemDeleted != 0) {
								newThresholdRating = new IThresholdRating[oldThresholdRating.length - itemDeleted];
								int i = 0;
								int j = 0;

								while (i < oldThresholdRating.length) {
									if ((j < index.length) && (Integer.parseInt(index[j]) == i)) {
										j++;
									}
									else {
										newThresholdRating[i - j] = oldThresholdRating[i];
									}
									i++;
								}

							}
						}
					}

					return newThresholdRating;
				}
			}
			else if ("refresh_prepare_add_threshold_new".equals(event)
					|| "refresh_prepare_update_threshold_new".equals(event)
					|| "refresh_prepare_add_threshold_update".equals(event)
					|| "refresh_prepare_update_threshold_update".equals(event)) {
				IThresholdRating thresholdRating = new OBThresholdRating();

				thresholdRating.setRatingType(aForm.getCreditRatingType());
				thresholdRating.setRating(aForm.getCreditRating());
				if (!(aForm.getThresholdCurCode().equals("") || aForm.getThresholdCurCode().equals(null))
						|| !(aForm.getThresholdAmt().equals("") || aForm.getThresholdAmt().equals(null))) {
					// String curr = aForm.getThresholdCurCode();
					// String amt = aForm.getThresholdAmt();
					// thresholdRating.setThresholdAmount(CurrencyManager.
					// convertToAmount(locale, curr , amt ));
					Amount thresholdAmount = new Amount(UIUtil.mapStringToBigDecimal(aForm.getThresholdAmt()),
							new CurrencyCode(aForm.getThresholdCurCode()));
//					System.out.println("inside mapper thresholdAmount = " + thresholdAmount);
					thresholdRating.setThresholdAmount(thresholdAmount);
//					System.out.println("inside mapper thresholdRating.getThresholdAmount() = "+ thresholdRating.getThresholdAmount());
				}

				return thresholdRating;

			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
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
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String index = (String) map.get("index");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {

			ThresholdRatingForm aForm = (ThresholdRatingForm) cForm;
			if ("prepare_add_threshold".equals(event) || "prepare_update_threshold".equals(event)
					|| EVENT_VIEW.equals(event) || "prepare_add_threshold_new_confirm".equals(event)
					|| "prepare_update_threshold_new_confirm".equals(event)
					|| "prepare_add_threshold_update_confirm".equals(event)
					|| "prepare_update_threshold_update_confirm".equals(event)
					|| "prepare_add_threshold_delete".equals(event) || "prepare_update_threshold_delete".equals(event)) {
				if (obj != null) {
					IThresholdRating[] sr = (IThresholdRating[]) obj;

					if (sr.length != 0) {

						String ratingType[] = new String[sr.length];
						String rating[] = new String[sr.length];
						String thresholdAmount[] = new String[sr.length];

						for (int i = 0; i < sr.length; i++) {
							if (sr[i].getThresholdAmount() != null) {
								if (sr[i].getThresholdAmount().getAmount() >= 0) {
									thresholdAmount[i] = UIUtil.formatAmount(sr[i].getThresholdAmount(), 4, locale,
											true);
								}
								else {
									thresholdAmount[i] = null;
								}
								ratingType[i] = sr[i].getRatingType();
								rating[i] = sr[i].getRating();
							}
							else {
								ratingType[i] = null;
								rating[i] = null;
								thresholdAmount[i] = null;
							}
						}
						aForm.setRatingType(ratingType);
						aForm.setRating(rating);
						aForm.setThresholdAmount(thresholdAmount);
					}
					else {
						aForm.setRatingType(null);
						aForm.setRating(null);
						aForm.setThresholdAmount(null);
					}
				}
				else {
					aForm.setRatingType(null);
					aForm.setRating(null);
					aForm.setThresholdAmount(null);
				}
			}
			else {
				if (obj != null) {
					IThresholdRating sr = (IThresholdRating) obj;

					aForm.setCreditRatingType(sr.getRatingType());
					aForm.setCreditRating(sr.getRating());
					if (sr.getThresholdAmount() != null) {
						aForm.setThresholdCurCode(sr.getThresholdAmount().getCurrencyCode());
						String thresholdAmt = UIUtil.formatAmount(sr.getThresholdAmount(), 4, locale, false);
//						System.out.println("inside mapper thresholdAmt = " + thresholdAmt);
						aForm.setThresholdAmt(thresholdAmt);
					}

				}
				else {

					aForm.setCreditRatingType(null);
					aForm.setCreditRating(null);
					aForm.setThresholdCurCode(null);
					aForm.setThresholdAmt(null);

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
