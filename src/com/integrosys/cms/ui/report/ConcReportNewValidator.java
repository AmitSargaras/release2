/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewValidator.java,v 1.3 2003/09/02 04:02:56 btchng Exp $
 */

package com.integrosys.cms.ui.report;

import java.util.Iterator;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Validates the input for generating a concentration report.
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 04:02:56 $ Tag: $Name: $
 */
public class ConcReportNewValidator {

	static ActionErrors validateInput(ConcReportNewForm form, Locale locale) {

		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();

		if (ConcReportNewAction.EVENT_GENERATE.equals(event)) {
			validateForGenerate(errors, form, locale);
		}

		DefaultLogger.debug(ConcReportNewValidator.class.getName(), String.valueOf(errors.size()));

		for (Iterator iter = errors.properties(); iter.hasNext();) {
			DefaultLogger.debug(ConcReportNewValidator.class.getName(), iter.next());
		}

		return errors;
	}

	private static void validateForGenerate(ActionErrors errors, ConcReportNewForm form, Locale locale) {

		// At least one is selected .
		String[] listing = form.getChecks();

		if ((listing == null) || (listing.length < 1)) {
			errors.add("atleastOne",
					new ActionMessage(ConcReportNewConstants.ERROR_NO_SELECTION, "at least one report"));
		}

		/*
		 * String reportName = form.getReportName(); String reportType =
		 * form.getReportType();
		 * 
		 * String errorCode = "";
		 * 
		 * if (!Validator.ERROR_NONE.equals((errorCode =
		 * Validator.checkString(reportName, true, 1, 50)))) {
		 * errors.add("reportName", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * new Integer(1), new Integer(50))); }
		 * 
		 * if (ConcReportNewConstants.RPT_TYPE_STOCK_SHARES.equals(reportType))
		 * { String stockExCode = form.getStockExCode(); String
		 * stockSharesSecSubtype = form.getStockSharesNumSecSubtype(); String
		 * stockSharesSecType = form.getStockSharesDenSecType();
		 * 
		 * // Just check that there are valid selections. // Will not validate
		 * the values.
		 * 
		 * if (stockExCode == null || stockExCode.equals("")) {
		 * errors.add("stockExCode", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "an exchange")); }
		 * 
		 * if (stockSharesSecSubtype == null ||
		 * stockSharesSecSubtype.equals("")) {
		 * errors.add("stockSharesNumSecSubtype", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities subtype"));
		 * }
		 * 
		 * if (stockSharesSecType == null || stockSharesSecType.equals("")) {
		 * errors.add("stockSharesDenSecType", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities type")); } }
		 * else if
		 * (ConcReportNewConstants.RPT_TYPE_SEC_STOCK_EX.equals(reportType)) {
		 * String secStockExSecSubtype = form.getSecStockExNumSecSubtype();
		 * String secStockExSecType = form.getSecStockExDenSecType(); String
		 * secSrockExSecSubtype2 = form.getSecStockExDenSecSubtype();
		 * 
		 * // Just check that there are valid selections. // Will not validate
		 * the values.
		 * 
		 * if (secStockExSecSubtype == null || secStockExSecSubtype.equals(""))
		 * { errors.add("secStockExNumSecSubtype", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities subtype"));
		 * }
		 * 
		 * if (secStockExSecType == null || secStockExSecType.equals("")) {
		 * errors.add("secStockExDenSecType", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities type")); }
		 * 
		 * if (secSrockExSecSubtype2 == null ||
		 * secSrockExSecSubtype2.equals("")) {
		 * errors.add("secStockExDenSecSubtype", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities subtype"));
		 * }
		 * 
		 * } else if
		 * (ConcReportNewConstants.RPT_TYPE_SEC_SEC_TYPE.equals(reportType)) {
		 * String region = form.getSecSecTypeRegion(); String listing =
		 * form.getSecSecTypeListing();
		 * 
		 * // Just check that there are valid selections. // Will not validate
		 * the values.
		 * 
		 * if (region == null || region.equals("")) {
		 * errors.add("secSecTypeRegion", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a region")); }
		 * 
		 * if (listing == null || listing.equals("")) {
		 * errors.add("secSecTypeListing", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities subtype"));
		 * }
		 * 
		 * } else if
		 * (ConcReportNewConstants.RPT_TYPE_PROPERTY.equals(reportType)) {
		 * String listing = form.getPropertyListing(); String countryCode =
		 * form.getPropertyCountryCode();
		 * 
		 * // Just check that there are valid selections. // Will not validate
		 * the values.
		 * 
		 * if (listing == null || listing.equals("")) {
		 * errors.add("propertyListing", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a listing type")); }
		 * 
		 * if (countryCode == null || countryCode.equals("")) {
		 * errors.add("propertyCountryCode", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a country")); } } else if
		 * (ConcReportNewConstants.RPT_TYPE_CURRENCY_SEC_TYPE.equals(
		 * reportType)) { String listing = form.getCurrencySecTypeListing();
		 * 
		 * // Just check that there are valid selections. // Will not validate
		 * the values.
		 * 
		 * if (listing == null || listing.equals("")) {
		 * errors.add("currencySecTypeListing", new ActionMessage(
		 * ConcReportNewConstants.ERROR_NO_SELECTION, "a securities subtype"));
		 * }
		 * 
		 * } else { // No radio button selected. errors.add("reportType", new
		 * ActionMessage( ConcReportNewConstants.ERROR_NO_SELECTION,
		 * "a report type")); }
		 */
	}
}
