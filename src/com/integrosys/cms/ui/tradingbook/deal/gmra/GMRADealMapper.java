/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary;
import com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for GMRA Deal
 * Description: Map the value from database to the screen or from the screen
 * that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "GMRADealSummaryTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary", SERVICE_SCOPE },
				{ "GMRADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue", SERVICE_SCOPE },
				{ "GMRADeal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "InitialGMRADeal", "java.util.list", FORM_SCOPE } });

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
		GMRADealForm aForm = (GMRADealForm) cForm;
		Double haircut;
		Double dealRate;

		try {
			if ("view_gmra_deal_detail".equals(event) || GMRADealAction.EVENT_VIEW.equals(event)) {

				OBGMRADealSummary obGMRADealSummary = new OBGMRADealSummary();
				return obGMRADealSummary;

			}
			else if (event.equals("refresh_maker_add_deal") || "maker_add_deal".equals(event)) {

				OBGMRADealDetail obGMRADealDetail = new OBGMRADealDetail();

				obGMRADealDetail.setLimitProfileID(Long.parseLong(aForm.getLimitProfileID()));
				obGMRADealDetail.setAgreementID(Long.parseLong(aForm.getAgreementID()));

				if (event.equals("refresh_maker_add_deal")) {
					OBGMRADeal newGMRADeal = new OBGMRADeal();
					try {
						if (!(aForm.getHaircut().equals(null) || aForm.getHaircut().equals(""))) {
							haircut = Double.valueOf(aForm.getHaircut());
							newGMRADeal.setHaircut((Double) haircut);
						}
						else {
							newGMRADeal.setHaircut(null);
						}
						if (!(aForm.getDealRate().equals(null) || aForm.getDealRate().equals(""))) {
							dealRate = Double.valueOf(aForm.getDealRate());
							newGMRADeal.setDealRate((Double) dealRate);
						}
						else {
							newGMRADeal.setDealRate(null);
						}

						newGMRADeal.setProductType(aForm.getProductType());
						newGMRADeal.setSecDesc(aForm.getSecDesc());
						newGMRADeal.setISINCode(aForm.getIsinCode());
						newGMRADeal.setCUSIPCode(aForm.getCusipCode());

						Amount valAmt = new Amount();
						if (!(aForm.getDealPrice().equals(null) || aForm.getDealPrice().equals(""))) {
							valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getDealPrice()), new CurrencyCode(
									aForm.getDealCurCode()));
							newGMRADeal.setDealAmount(valAmt);
						}
						else {
							newGMRADeal.setDealAmount(null);
						}

						valAmt = new Amount();
						if (!(aForm.getNotional().equals(null) || aForm.getNotional().equals(""))) {
							valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNotional()), new CurrencyCode(
									aForm.getNotionalCurCode()));
							newGMRADeal.setNotionalAmount(valAmt);
						}
						else {
							newGMRADeal.setNotionalAmount(null);
						}

						if (!(aForm.getRepoStartAmt().equals(null) || aForm.getRepoStartAmt().equals(""))) {
							newGMRADeal.setRepoStartAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoStartAmt()));
						}
						else {
							newGMRADeal.setRepoStartAmt(null);
						}

						if (!(aForm.getRepoEndAmt().equals(null) || aForm.getRepoEndAmt().equals(""))) {
							newGMRADeal.setRepoEndAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoEndAmt()));
						}
						else {
							newGMRADeal.setRepoEndAmt(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTradeDate())) {
							Date stageDate = DateUtil.convertDate(locale, aForm.getTradeDate());
							newGMRADeal.setTradeDate(stageDate);
						}
						else {
							newGMRADeal.setTradeDate(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getMaturityDate())) {
							Date stageDate = DateUtil.convertDate(locale, aForm.getMaturityDate());
							newGMRADeal.setMaturityDate(stageDate);
						}
						else {
							newGMRADeal.setMaturityDate(null);
						}

						newGMRADeal.setDealCountry(aForm.getDealCountry());
						newGMRADeal.setDealBranch(aForm.getDealBranch());

					}
					catch (Exception e) {
						DefaultLogger.debug(this, "got exception in mapFormToOB" + e);
						throw (new MapperException(e.getMessage()));
					}

					obGMRADealDetail.setGMRADealDetail((IGMRADeal) newGMRADeal);
				}

				return obGMRADealDetail;

			}
			else if (event.equals("process_gmra_dealdetails") || event.equals("edit_gmra_dealdetails")
					|| event.equals("refresh_edit_gmra_dealdetails") || event.equals("delete_gmra_dealdetails_confirm")
					|| event.equals("delete_gmra_dealdetails")) {

				OBGMRADeal obGMRADeal = new OBGMRADeal();

				obGMRADeal.setCMSDealID(Long.parseLong(aForm.getCmsDealID()));

				if (event.equals("refresh_edit_gmra_dealdetails")) {
					try {
						if (!(aForm.getHaircut().equals(null) || aForm.getHaircut().equals(""))) {
							haircut = Double.valueOf(aForm.getHaircut());
							obGMRADeal.setHaircut((Double) haircut);
						}
						else {
							obGMRADeal.setHaircut(null);
						}
						if (!(aForm.getDealRate().equals(null) || aForm.getDealRate().equals(""))) {
							dealRate = Double.valueOf(aForm.getDealRate());
							obGMRADeal.setDealRate((Double) dealRate);
						}
						else {
							obGMRADeal.setDealRate(null);
						}

						obGMRADeal.setProductType(aForm.getProductType());
						obGMRADeal.setSecDesc(aForm.getSecDesc());
						obGMRADeal.setISINCode(aForm.getIsinCode());
						obGMRADeal.setCUSIPCode(aForm.getCusipCode());

						Amount valAmt = new Amount();
						if (!(aForm.getDealPrice().equals(null) || aForm.getDealPrice().equals(""))) {
							valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getDealPrice()), new CurrencyCode(
									aForm.getDealCurCode()));
							obGMRADeal.setDealAmount(valAmt);
						}
						else {
							obGMRADeal.setDealAmount(null);
						}

						valAmt = new Amount();
						if (!(aForm.getNotional().equals(null) || aForm.getNotional().equals(""))) {
							valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNotional()), new CurrencyCode(
									aForm.getNotionalCurCode()));
							obGMRADeal.setNotionalAmount(valAmt);
						}
						else {
							obGMRADeal.setNotionalAmount(null);
						}

						if (!(aForm.getRepoStartAmt().equals(null) || aForm.getRepoStartAmt().equals(""))) {
							obGMRADeal.setRepoStartAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoStartAmt()));
						}
						else {
							obGMRADeal.setRepoStartAmt(null);
						}

						if (!(aForm.getRepoEndAmt().equals(null) || aForm.getRepoEndAmt().equals(""))) {
							obGMRADeal.setRepoEndAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoEndAmt()));
						}
						else {
							obGMRADeal.setRepoEndAmt(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTradeDate())) {
							Date stageDate = DateUtil.convertDate(locale, aForm.getTradeDate());
							obGMRADeal.setTradeDate(stageDate);
						}
						else {
							obGMRADeal.setTradeDate(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getMaturityDate())) {
							Date stageDate = DateUtil.convertDate(locale, aForm.getMaturityDate());
							obGMRADeal.setMaturityDate(stageDate);
						}
						else {
							obGMRADeal.setMaturityDate(null);
						}

						obGMRADeal.setDealCountry(aForm.getDealCountry());
						obGMRADeal.setDealBranch(aForm.getDealBranch());

					}
					catch (Exception e) {
						DefaultLogger.debug(this, "got exception in mapFormToOB" + e);
						throw (new MapperException(e.getMessage()));
					}

				}

				return obGMRADeal;

			}
			else if (event.equals("maker_close_dealdetails_confirm")) {

				OBGMRADealDetail obGMRADealDetail = new OBGMRADealDetail();
				return obGMRADealDetail;

			}
			else if (event.equals("maker_update_dealdetails_confirm") || event.equals("maker_edit_reject_confirm")
					|| "refresh_maker_edit_dealdetails_reject".equals(event)) {

				OBGMRADealTrxValue oldTrxValue = (OBGMRADealTrxValue) map.get("GMRADealTrxValue");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBGMRADeal newGMRADeal = null;

				if (event.equals("maker_update_dealdetails_confirm")) {
					// copy all old values from ORIGINAL value int
					// newBusinessValue.
					if (oldTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {

						newGMRADeal = (OBGMRADeal) oldTrxValue.getStagingGMRADeal();
					}
					else {
						newGMRADeal = (OBGMRADeal) oldTrxValue.getGMRADeal();

					}

				}
				else if ("maker_edit_reject_confirm".equals(event)
						|| "refresh_maker_edit_dealdetails_reject".equals(event)) {
					// copy all old values from STAGING value int
					// newBusinessValue.
					newGMRADeal = (OBGMRADeal) oldTrxValue.getStagingGMRADeal();
				}

				if (newGMRADeal != null) {
					newGMRADeal.setProductType(aForm.getProductType());
					newGMRADeal.setSecDesc(aForm.getSecDesc());
					newGMRADeal.setISINCode(aForm.getIsinCode());
					newGMRADeal.setCUSIPCode(aForm.getCusipCode());

					Amount valAmt = new Amount();
					if (!(aForm.getDealPrice().equals(null) || aForm.getDealPrice().equals(""))) {
						valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getDealPrice()), new CurrencyCode(aForm
								.getDealCurCode()));
						newGMRADeal.setDealAmount(valAmt);
					}
					else {
						newGMRADeal.setDealAmount(null);
					}

					valAmt = new Amount();
					if (!(aForm.getNotional().equals(null) || aForm.getNotional().equals(""))) {
						valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNotional()), new CurrencyCode(aForm
								.getNotionalCurCode()));
						newGMRADeal.setNotionalAmount(valAmt);
					}
					else {
						newGMRADeal.setNotionalAmount(null);
					}

					if (!(aForm.getRepoStartAmt().equals(null) || aForm.getRepoStartAmt().equals(""))) {
						newGMRADeal.setRepoStartAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoStartAmt()));
					}
					else {
						newGMRADeal.setRepoStartAmt(null);
					}

					if (!(aForm.getRepoEndAmt().equals(null) || aForm.getRepoEndAmt().equals(""))) {
						newGMRADeal.setRepoEndAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoEndAmt()));
					}
					else {
						newGMRADeal.setRepoEndAmt(null);
					}

					if (!(aForm.getHaircut().equals(null) || aForm.getHaircut().equals(""))) {
						haircut = Double.valueOf(aForm.getHaircut());
						newGMRADeal.setHaircut((Double) haircut);
					}
					else {
						newGMRADeal.setHaircut(null);
					}

					if (!(aForm.getDealRate().equals(null) || aForm.getDealRate().equals(""))) {
						dealRate = Double.valueOf(aForm.getDealRate());
						newGMRADeal.setDealRate((Double) dealRate);
					}
					else {
						newGMRADeal.setDealRate(null);
					}

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTradeDate())) {
						Date stageDate = compareDate(locale, newGMRADeal.getTradeDate(), aForm.getTradeDate());
						newGMRADeal.setTradeDate(stageDate);
					}
					else {
						newGMRADeal.setTradeDate(null);
					}

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getMaturityDate())) {
						Date stageDate = compareDate(locale, newGMRADeal.getMaturityDate(), aForm.getMaturityDate());
						newGMRADeal.setMaturityDate(stageDate);
					}
					else {
						newGMRADeal.setMaturityDate(null);
					}

					newGMRADeal.setDealCountry(aForm.getDealCountry());
					System.out.println("newGMRADeal.getDealCountry() = " + newGMRADeal.getDealCountry());
					newGMRADeal.setDealBranch(aForm.getDealBranch());

					return newGMRADeal;
				}
			}
			else if (event.equals("maker_add_deal_confirm")) {

				OBGMRADealTrxValue oldTrxValue = (OBGMRADealTrxValue) map.get("GMRADealTrxValue");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBGMRADeal newGMRADeal = new OBGMRADeal();

				// todo
				newGMRADeal.setAgreementID(Long.parseLong(aForm.getAgreementID()));
				newGMRADeal.setProductType(aForm.getProductType());
				newGMRADeal.setSecDesc(aForm.getSecDesc());
				newGMRADeal.setISINCode(aForm.getIsinCode());
				newGMRADeal.setCUSIPCode(aForm.getCusipCode());

				Amount valAmt = new Amount();
				if (!(aForm.getDealPrice().equals(null) || aForm.getDealPrice().equals(""))) {
					valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getDealPrice()), new CurrencyCode(aForm
							.getDealCurCode()));
					newGMRADeal.setDealAmount(valAmt);
				}
				else {
					newGMRADeal.setDealAmount(null);
				}

				valAmt = new Amount();
				if (!(aForm.getNotional().equals(null) || aForm.getNotional().equals(""))) {
					valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNotional()), new CurrencyCode(aForm
							.getNotionalCurCode()));
					newGMRADeal.setNotionalAmount(valAmt);
				}
				else {
					newGMRADeal.setNotionalAmount(null);
				}

				if (!(aForm.getRepoStartAmt().equals(null) || aForm.getRepoStartAmt().equals(""))) {
					newGMRADeal.setRepoStartAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoStartAmt()));
				}
				else {
					newGMRADeal.setRepoStartAmt(null);
				}

				if (!(aForm.getRepoEndAmt().equals(null) || aForm.getRepoEndAmt().equals(""))) {
					newGMRADeal.setRepoEndAmt(UIUtil.mapStringToBigDecimal(aForm.getRepoEndAmt()));
				}
				else {
					newGMRADeal.setRepoEndAmt(null);
				}

				if (!(aForm.getHaircut().equals(null) || aForm.getHaircut().equals(""))) {
					haircut = Double.valueOf(aForm.getHaircut());
					newGMRADeal.setHaircut((Double) haircut);
				}
				else {
					newGMRADeal.setHaircut(null);
				}

				if (!(aForm.getDealRate().equals(null) || aForm.getDealRate().equals(""))) {
					dealRate = Double.valueOf(aForm.getDealRate());
					newGMRADeal.setDealRate((Double) dealRate);
				}
				else {
					newGMRADeal.setDealRate(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTradeDate())) {
					Date stageDate = compareDate(locale, newGMRADeal.getTradeDate(), aForm.getTradeDate());
					newGMRADeal.setTradeDate(stageDate);
				}
				else {
					newGMRADeal.setTradeDate(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getMaturityDate())) {
					Date stageDate = compareDate(locale, newGMRADeal.getMaturityDate(), aForm.getMaturityDate());
					newGMRADeal.setMaturityDate(stageDate);
				}
				else {
					newGMRADeal.setMaturityDate(null);
				}

				newGMRADeal.setDealCountry(aForm.getDealCountry());
				newGMRADeal.setDealBranch(aForm.getDealBranch());

				return newGMRADeal;

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in SRPGlobalMapper is" + e);
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
			GMRADealForm aForm = (GMRADealForm) cForm;
			if (obj != null) {
				IGMRADeal sr1 = (IGMRADeal) obj;
				String curCodeDeal = null;
				String curCodeNotional = null;

				aForm.setDealID(String.valueOf(sr1.getDealID()));
				aForm.setCmsDealID(String.valueOf(sr1.getCMSDealID()));
				aForm.setProductType(sr1.getProductType());
				aForm.setSecDesc(sr1.getSecDesc());
				aForm.setIsinCode(sr1.getISINCode());
				aForm.setCusipCode(sr1.getCUSIPCode());

				if (sr1.getDealAmount() != null) {
					curCodeDeal = sr1.getDealAmount().getCurrencyCode();
					aForm.setDealCurCode(curCodeDeal);
					aForm.setDealPrice(UIUtil.formatAmount(sr1.getDealAmount(), 4, locale, false));
				}
				else {
					aForm.setDealCurCode(null);
					aForm.setDealPrice(null);
				}

				if (sr1.getNotionalAmount() != null) {
					curCodeNotional = sr1.getNotionalAmount().getCurrencyCode();
					aForm.setNotionalCurCode(curCodeNotional);
					aForm.setNotional(UIUtil.formatAmount(sr1.getNotionalAmount(), 4, locale, false));
				}
				else {
					aForm.setNotionalCurCode(null);
					aForm.setNotional(null);
				}

				if (sr1.getHaircut() != null) {
					aForm.setHaircut(String.valueOf(sr1.getHaircut()));
				}
				else {
					aForm.setHaircut(null);
				}

				if (sr1.getDealRate() != null) {
					aForm.setDealRate(String.valueOf(sr1.getDealRate()));
				}
				else {
					aForm.setHaircut(null);
				}

				if (sr1.getTradeDate() != null) {
					aForm.setTradeDate(DateUtil.formatDate(locale, sr1.getTradeDate()));
				}
				else {
					aForm.setTradeDate(null);
				}

				if (sr1.getMaturityDate() != null) {
					aForm.setMaturityDate(DateUtil.formatDate(locale, sr1.getMaturityDate()));
				}
				else {
					aForm.setMaturityDate(null);
				}

				aForm.setDealCountry(sr1.getDealCountry());
				aForm.setDealBranch(sr1.getDealBranch());

				if (sr1.getRepoStartAmt() != null) {
					aForm.setRepoStartAmt(UIUtil.formatAmount(null, sr1.getRepoStartAmt(), 4, locale));
				}
				else {
					aForm.setRepoStartAmt(null);
				}

				if (sr1.getRepoEndAmt() != null) {
					aForm.setRepoEndAmt(UIUtil.formatAmount(null, sr1.getRepoEndAmt(), 4, locale));
				}
				else {
					aForm.setRepoEndAmt(null);
				}

				DefaultLogger.debug(this, "Before putting vector result");
			}
			else {
				aForm.setDealID(null);
				aForm.setProductType(null);
				aForm.setSecDesc(null);
				aForm.setIsinCode(null);
				aForm.setCusipCode(null);
				aForm.setDealCurCode(null);
				aForm.setDealPrice(null);
				aForm.setNotionalCurCode(null);
				aForm.setNotional(null);
				aForm.setHaircut(null);
				aForm.setDealRate(null);
				aForm.setTradeDate(null);
				aForm.setMaturityDate(null);
				aForm.setDealCountry(null);
				aForm.setDealBranch(null);
				aForm.setRepoStartAmt(null);
				aForm.setRepoEndAmt(null);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in GMRADealMapper is" + e);
		}
		return null;
	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}
		return returnDate;
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(GMRADealMapper.class.getName(), "offset " + offset + " + length " + length
					+ " >= maxSize " + maxSize);
			if (maxSize == 0) {
				// Do not reduce offset further.
				adjustedOffset = 0;
			}
			else if (offset == maxSize) {
				// Reduce.
				adjustedOffset = offset - length;
			}
			else {
				// Rely on "/" = Integer division.
				// Go to the start of the last strip.
				adjustedOffset = maxSize / length * length;
			}
			DefaultLogger.debug(GMRADealMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
	}

}
