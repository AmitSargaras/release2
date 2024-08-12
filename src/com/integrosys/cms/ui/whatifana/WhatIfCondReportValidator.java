/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.whatifana;

import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Validates the input for generating a what-if-cond report.
 * @author Siew Kheat
 */
public class WhatIfCondReportValidator {

	static ActionErrors validateInput(WhatIfCondReportForm form, Locale locale) {

		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();

		if (WhatIfCondReportAction.EVENT_GENERATE.equals(event)) {
			validateForGenerate(errors, form, locale);
		}

		return errors;
	}

	private static void validateForGenerate(ActionErrors errors, WhatIfCondReportForm form, Locale locale) {

		// At least one is selected .
		String reportType = form.getReportType();

		if ((reportType == null) || (reportType.length() == 0)) {
			errors.add("atleastOne", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
					"at least one report"));
		}

		if (form.getReportName() == null || form.getReportName().length() == 0) {
			errors.add("reportName", new ActionMessage(WhatIfCondReportConstants.ERROR_MANDATORY));
		}

		if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_STATE)) {
			if (form.getState() != null && form.getState().length() == 0) {
				errors
						.add("state", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
								"at least a State"));
			}

			if (form.getDistrict() != null && form.getDistrict().length() == 0) {
				errors.add("district", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a District"));
			}

			if (form.getMukim() != null && form.getMukim().length() == 0) {
				errors
						.add("mukim", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
								"at least a Mukim"));
			}

			if (form.getDirectionState() != null && form.getDirectionState().length() == 0) {
				errors.add("directionState", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"a direction of value"));
			}

			if (StringUtils.isBlank(form.getReportFormatProperty())) {
				errors.add("reportFormatProperty", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"one of the report format"));
			}

			if (form.getPercentageState() != null && form.getPercentageState().length() == 0) {
				errors.add("percentageState", new ActionMessage(WhatIfCondReportConstants.ERROR_MANDATORY));
			}
			else if (!Validator.validateNumber(form.getPercentageState(), true, 0, 100)) {
				errors.add("percentageState", new ActionMessage("error.number.format"));
			}
			else if (!Validator.checkDoubleDigits(form.getPercentageState(), 3, 0, false)) {
				errors.add("percentageState", new ActionMessage("error.number.decimalexceeded"));
			}
		}
		else if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_INDEX_TYPE)) {
			if (form.getIndexType() != null && form.getIndexType().length() == 0) {
				errors.add("indexType", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Index Type"));
			}

			if (form.getStockCode() != null && form.getStockCode().length() == 0) {
				errors.add("stockCode", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Counter"));
			}

			if (form.getDirectionIndexType() != null && form.getDirectionIndexType().length() == 0) {
				errors.add("directionIndexType", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"a direction of value"));
			}

			if (form.getPercentageIndexType() != null && form.getPercentageIndexType().length() == 0) {
				errors.add("percentageIndexType", new ActionMessage(WhatIfCondReportConstants.ERROR_MANDATORY));
			}
			else if (!Validator.validateNumber(form.getPercentageIndexType(), true, 0, 100)) {
				errors.add("percentageIndexType", new ActionMessage("error.number.format"));
			}
			else if (!Validator.checkDoubleDigits(form.getPercentageIndexType(), 3, 0, false)) {
				errors.add("percentageIndexType", new ActionMessage("error.number.decimalexceeded"));
			}

			if (StringUtils.isBlank(form.getReportFormatStockIndex())) {
				errors.add("reportFormatStockIndex", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"one of the report format"));
			}
		}
		else if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_MAKE)) {
			if (form.getMake() != null && form.getMake().length() == 0) {
				errors.add("make", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Vehicle Brand"));
			}

			if (form.getModel() != null && form.getModel().length() == 0) {
				errors.add("model", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Vehicle Model"));
			}

			if (form.getYearOfManufacture() != null && form.getYearOfManufacture().length() == 0) {
				errors.add("yearOfManufacture", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Year Of Manufacture"));
			}

			if (form.getDirectionMake() != null && form.getDirectionMake().length() == 0) {
				errors.add("directionMake", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"a direction of value"));
			}

			if (form.getPercentageMake() != null && form.getPercentageMake().length() == 0) {
				errors.add("percentageMake", new ActionMessage(WhatIfCondReportConstants.ERROR_MANDATORY));
			}
			else if (!Validator.validateNumber(form.getPercentageMake(), true, 0, 100)) {
				errors.add("percentageMake", new ActionMessage("error.number.format"));
			}
			else if (!Validator.checkDoubleDigits(form.getPercentageMake(), 3, 0, false)) {
				errors.add("percentageMake", new ActionMessage("error.number.decimalexceeded"));
			}

			if (StringUtils.isBlank(form.getReportFormatVehicle())) {
				errors.add("reportFormatVehicle", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"one of the report format"));
			}
		}
		else if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_GOLD_GRADE)) {
			if (form.getGoldGrade() != null && form.getGoldGrade().length() == 0) {
				errors.add("goldGrade", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"at least a Gold Grade"));
			}

			if (form.getDirectionGoldGrade() != null && form.getDirectionGoldGrade().length() == 0) {
				errors.add("directionGoldGrade", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"a direction of value"));
			}

			if (form.getPercentageGoldGrade() != null && form.getPercentageGoldGrade().length() == 0) {
				errors.add("percentageGoldGrade", new ActionMessage(WhatIfCondReportConstants.ERROR_MANDATORY));
			}
			else if (!Validator.validateNumber(form.getPercentageGoldGrade(), true, 0, 100)) {
				errors.add("percentageGoldGrade", new ActionMessage("error.number.format"));
			}
			else if (!Validator.checkDoubleDigits(form.getPercentageGoldGrade(), 3, 0, false)) {
				errors.add("percentageGoldGrade", new ActionMessage("error.number.decimalexceeded"));
			}

			if (StringUtils.isBlank(form.getReportFormatGold())) {
				errors.add("reportFormatGold", new ActionMessage(WhatIfCondReportConstants.ERROR_NO_SELECTION,
						"one of the report format"));
			}
		}
	}
}
