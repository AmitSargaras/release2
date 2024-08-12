/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/PortItemValidator.java,v 1.30 2006/09/22 05:31:31 jitendra Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.30 $
 * @since $Date: 2006/09/22 05:31:31 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 11:27:46 AM
 * To change this template use Options | File Templates.
 */
public class PortItemValidator { 
	
	public static void decimal_check(String ll,ActionErrors errors, String subtype,Locale locale){
		long nn;
		String errorCode = null;
		if("".equals(ll)||null==ll)
			errors.add("noOfUnit",new ActionMessage("error.mandatory"));
		else{
			//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
			if(null!=subtype && "MarksecMainLocal".equals(subtype)){
			//		if(!(Validator.ERROR_NONE).equals(Validator.checkNumber(ll, false, 0.0, 99.99))){
				if(!(Validator.ERROR_NONE).equals(Validator.checkNumber(ll, false, 0, 9999999999.9999,5,locale))){
				//if(Double.parseDouble(ll)>9999999999.99d)
					errors.add("noOfUnit",new ActionMessage("error.amount.greaterthan","0","9999999999.9999"));
				}
			}
			//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
			else{
		try{
			if(Double.parseDouble(ll)>9999999999d)
				errors.add("noOfUnit", new ActionMessage("error.amount.greaterthan", "0",
						"9999999999" + ""));
			nn=Long.parseLong(ll);
		}catch(Exception e){
			if(errors.isEmpty())
			errors.add("noOfUnit",new ActionMessage("error.amount.decimalnotallowed"));
		}
			}
	}
	}

	private static String LOGOBJ = PortItemValidator.class.getName();

	public static ActionErrors validateInput(PortItemForm aForm, Locale locale) {

		ActionErrors errors = new ActionErrors();

		decimal_check(aForm.getNoOfUnit(),errors,aForm.getSubtype(),locale);
		String errorCode = null;

		if(!aForm.getSubtype().equals("MarksecOtherListedLocal")){
		if (aForm.getIsSSC().equals("false")
				&& !(aForm.getSubtype().equals("MarksecBondForeign") || aForm.getSubtype().equals("MarksecBondLocal") || aForm
						.getSubtype().equals("MarksecBill") || aForm.getSubtype().equals("MarksecGovtForeignSame"))) {
			/*if (!(errorCode = Validator.checkString(aForm.getEquityType(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("equityType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						20 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... equityType...");
			}*/
		}
		}

		if (aForm.getSubtype().equals("MarksecBondLocal")) {
			if (!(errorCode = Validator.checkString(aForm.getCertNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("certNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						20 + ""));
				DefaultLogger
						.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... certNo...");
			}
		}
		else if (!(errorCode = Validator.checkString(aForm.getCertNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("certNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 20 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... certNo...");
		}

		if (aForm.getSubtype().equals("MarksecCustSec") && aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkString(aForm.getNomineeName(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("nomineeName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... nomineeName...");
			}
		}
		else if (!(errorCode = Validator.checkString(aForm.getNomineeName(), false, 1, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nomineeName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					50 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					"... nomineeName...");
		}

		if (!(errorCode = Validator.checkString(aForm.getRegisteredName(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("registeredName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					50 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					"... registeredName...");
		}

		if (aForm.getSubtype().equals("MarksecCustSec") && aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkDate(aForm.getDateConfirmNomine(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateConfirmNomine", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... dateConfirmNomine...");
			}
			else {
				Date currDate = DateUtil.getDate();
				Date date1 = DateUtil.convertDate(locale, aForm.getDateConfirmNomine());
				if (date1.after(currDate)) {
					errors.add("dateConfirmNomine", new ActionMessage("error.date.compareDate.more",
							"Date of Confirmation from Bank's Agent", "current date"));
				}
			}
		}
		else if (!(errorCode = Validator.checkDate(aForm.getDateConfirmNomine(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("dateConfirmNomine", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
					256 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					"... dateConfirmNomine...");
		}
		/*if (!(errorCode = Validator.checkNumber(aForm.getNoOfUnit(), true, 0, Double.parseDouble("9999999999"), 0,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("noOfUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"9999999999" + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... noOfUnit...");
		}*/

		if ((aForm.getSubtype().equals("MarksecNonListedLocal") || aForm.getSubtype().equals("MarksecBill") 
				//|| aForm.getSubtype().equalsIgnoreCase("MarksecMainLocal")
				)
				&& !(errorCode = Validator.checkNumber(aForm.getUnitPrice(), false, 0.0, 9999.999999, 7, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("unitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"9999.999999"));
		}
		
		//Add by Govind S:Validation of UnitPrice for Other Listed Local form 
		if(aForm.getUnitPrice()!=null && !aForm.getUnitPrice().trim().equals(""))
		{
			if (aForm.getSubtype().equals("MarksecOtherListedLocal")&& !(errorCode = Validator.checkNumber(aForm.getUnitPrice(), false, 0.0, 99999.99)).equals(Validator.ERROR_NONE)) {
				errors.add("unitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"99999.99"));
				DefaultLogger.debug(LOGOBJ, "scriptValueError= "
						+ aForm.getUnitPrice());
			}
		}
		
		// DefaultLogger.debug("portItemValidator",
		// "---------------------------- unit price: "+aForm.getUnitPrice());
		if (aForm.getSubtype().equals("MarksecNonListedLocal") && (aForm.getUnitPrice() != null)
				&& (aForm.getUnitPrice().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getUnitPriceCcyCode(), true, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("unitPriceCcyCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 3 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... unitpriceccycode...");
			}
		}
		if (!aForm.getSubtype().equals("MarksecMainLocal")&&(!aForm.getSubtype().equals("MarksecBondLocal"))){
		/*if (!(errorCode = Validator.checkNumber(aForm.getNominalValue(), true, 0, Double.parseDouble("9999999999999"),
				0, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nominalValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"9999999999999" + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					" Error... nominalValue...");
		}*/
		}
		/*if (aForm.getSubtype().equals("MarksecMainLocal")){
		if (!(errorCode = Validator.checkNumber(aForm.getNominalValue(), true, 0.0, Double.parseDouble("99999999999.99"),
				3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nominalValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"99999999999.99" + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					" Error... nominalValue...");
		}
		}*/

		if (!(errorCode = UIValidator.checkNumber(aForm.getExercisePrice(), false, 0, Double
				.parseDouble("9999999999999"), 5, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("exercisePrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"9999999999999" + "", "4"));
		}

		/*if (!aForm.getSubtype().equals("MarksecBill")
				&& !(errorCode = Validator.checkString(aForm.getIssuerName(), true, 0, 50))
						.equals(Validator.ERROR_NONE)) {
			errors.add("issuerName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger
					.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... issuerName...");
		}*/

		if (aForm.getSubtype().equals("MarksecBill")
				&& !(errorCode = Validator.checkString(aForm.getIsSecurityReferred(), true, 0, 5))
						.equals(Validator.ERROR_NONE)) {
			errors.add("isSecurityReferred", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 5 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					"... isSecurityReferred...");
		}

		if (aForm.getIsSSC().equals("false")
				&& (aForm.getSubtype().equals("MarksecBondForeign") || aForm.getSubtype().equals("MarksecBondLocal"))) {
			if (!(errorCode = Validator.checkString(aForm.getIsserIdentType(), false, 0, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("isserIdentType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 10 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... isserIdentType...");
			}
		}
		else if (aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkString(aForm.getIsserIdentType(), false, 0, 12))
					.equals(Validator.ERROR_NONE)) {
				errors.add("isserIdentType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 10 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... isserIdentType...");
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getIndexName(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("indexName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... indexName...");
		}

		if (aForm.getIsSSC().equals("false")
				&& (aForm.getSubtype().equals("MarksecBill") || aForm.getSubtype().equals("MarksecGovtForeignDiff") || aForm
						.getSubtype().equals("MarksecGovtForeignSame"))) {
			if (aForm.getIsGuaranteeByGovt().equals("true")) {
				if (!(errorCode = Validator.checkString(aForm.getGovtName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
					errors.add("govtName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
					DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
							"... govtName...");
				}
			}
			else {
				if (aForm.getGovtName().length() > 0) {
					errors.add("govtName", new ActionMessage("error.string.empty", "1", "50"));
				}
			}
		}

		if (aForm.getIsSSC().equals("false")
				&& (aForm.getSubtype().equals("MarksecMainForeign") || aForm.getSubtype().equals("MarksecMainLocal"))) {
			if (aForm.getEquityType().equals("EQUITY02")) {
				if (aForm.getBaselComplaintUT().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
					errors.add("baselComplaintUT", new ActionMessage("error.collateral.na.disallowed"));
				}
			}
			/*else {
				if (!aForm.getBaselComplaintUT().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
					errors.add("baselComplaintUT", new ActionMessage("error.collateral.na.allowed"));
				}
			}*/
		}

		if (aForm.getSubtype().equals("MarksecMainLocal") || aForm.getSubtype().equals("MarksecBill")
				//|| aForm.getSubtype().equals("MarksecOtherListedLocal")
				|| aForm.getSubtype().equals("MarksecGovtForeignSame")
				|| aForm.getSubtype().equals("MarksecOtherListedForeign")
				|| aForm.getSubtype().equals("MarksecBondLocal") || aForm.getSubtype().equals("MarksecBondForeign")) {
			if (!(errorCode = Validator.checkString(aForm.getStockCode(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("stockCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"30"));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... stockCode...");
			}
		}

		if (aForm.getSubtype().equals("MarksecBondLocal")) {
			if (!(errorCode = Validator.checkString(aForm.getUnitPrice(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("unitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						30 + ""));
				DefaultLogger
						.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... unitPrice...");
			}
		}
		
		if (aForm.getSubtype().equals("MarksecBondLocal")) {
			if (!(errorCode = UIValidator.checkNumber(aForm.getBondRating(), false, 0.0, Double
					.parseDouble("99.99"), 5, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("bondRating", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"99" + "", "4"));
				DefaultLogger
						.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... bondRating...");
			}
		}
		
		
		
		
		if (aForm.getIsSSC().equals("false") && (aForm.getSubtype().equals("MarksecBondLocal"))) {
			if (!(errorCode = Validator.checkDate(aForm.getBondMatDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("bondMatDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
						256 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... bondMatDate...");
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getBaselComplaintUT(), false, 1, 256))
				.equals(Validator.ERROR_NONE)) {
			errors.add("baselComplaintUT", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					256 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
					"... baselComplainUI...");
		}

		if (!(errorCode = Validator.checkString(aForm.getLeadMgr(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("leadMgr", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							50 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... leadMgr...");
		}

		if (!(errorCode = Validator.checkString(aForm.getSettleOrg(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("settleOrg", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					50 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... settleOrg...");
		}

		try {
			if (aForm.getIsSSC().equals("false")) {
				if (!(errorCode = Validator.checkDate(aForm.getBondIssueDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("bondIssueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"1", 256 + ""));
					DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
							"... bondIssueDate...");
				}
				else if ((aForm.getBondIssueDate() != null) && (aForm.getBondIssueDate().length() > 0)) {
					Date currDate = DateUtil.getDate();
					Date date1 = DateUtil.convertDate(locale, aForm.getBondIssueDate());
					if (date1.after(currDate)) {
						errors.add("bondIssueDate", new ActionMessage("error.date.compareDate.more", "Bond Issue Date",
								"current date"));
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "error encountered"
					+ e.toString());
		}

		if ((aForm.getBondMatDate() != null) && aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkDate(aForm.getBondMatDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("bondMatDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
						256 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... bondMatDate...");
				/*
				 * } else if (aForm.getBondMatDate().length() > 0 &&
				 * !DateUtil.convertDate(aForm.getBondMatDate()).after(new
				 * Date())) { errors.add("bondMatDate", new
				 * ActionMessage("error.collateral.security.maturityDateEarlier" ,
				 * "1", "256"));DefaultLogger.debug(
				 * "com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator" ,
				 * "... bondMatDate...");
				 */
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getRic(), false, 1, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("ric", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", 30 + ""));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... ric...");
		}

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getSecCustodianExt(),
		 * false, 1, 50)).equals(Validator.ERROR_NONE)) {
		 * errors.add("secCustodianExt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "1", 50 + ""));DefaultLogger.debug(
		 * "com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
		 * "... secCustodian..."); }
		 */
		if ((aForm.getSecMaturityDate() != null) && aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkDate(aForm.getSecMaturityDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... secMaturityDate...");
				/*
				 * } else if (aForm.getSecMaturityDate().length() > 0 &&
				 * !DateUtil.convertDate(aForm.getSecMaturityDate()).after(new
				 * Date())) { errors.add("secMaturityDate", new
				 * ActionMessage("error.collateral.security.maturityDateEarlier" ,
				 * "1", "256"));DefaultLogger.debug(
				 * "com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator" ,
				 * "... secMaturityDate...");
				 */
			}
		}

		// MBB-824
		if (ICMSConstant.EQUITY_TYPE_UNIT_TRUST.equals(aForm.getEquityType()) || aForm.getSubtype().equals("MarksecOtherListedLocal")) {
			if (!(errorCode = Validator.checkString(aForm.getIsinCode(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("isinCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
						"... isinCode...");
			}
		}




		/*if (!(errorCode = Validator.checkString(aForm.getSecCustodianType(), true, 1, 50)).equals(Validator.ERROR_NONE)) {

			errors.add("secCustodianType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					50 + ""));
		}
		else {
			if (aForm.getSecCustodianType().equals(ICMSConstant.INTERNAL_COL_CUSTODIAN)
					&& AbstractCommonMapper.isEmptyOrNull(aForm.getSecCustodianInt()))
				errors.add("secCustodianInt", new ActionMessage("error.mandatory"));
			else if (aForm.getSecCustodianType().equals(ICMSConstant.EXTERNAL_COL_CUSTODIAN)) {
				if (!(errorCode = Validator.checkString(aForm.getSecCustodianExt(), true, 1, 50))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secCustodianExt", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "1", 50 + ""));
				}
			}
		}*/
	
		/**************HDFC**************/
		/*if (!aForm.getSubtype().equals("MarksecBill") && !aForm.getSubtype().equals("MarksecBondLocal")
				&& !aForm.getSubtype().equals("MarksecNonListedLocal"))
			/*if (!(errorCode = Validator.checkString(aForm.getSecBlackListed(), true, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secBlackListed", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", 50 + ""));
			}*/

		if (aForm.getSubtype().equals("MarksecBondForeign") || aForm.getSubtype().equals("MarksecBondLocal")) {
			if (!(errorCode = Validator.checkString(aForm.getBondType(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("bondType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}
		}
		//HDFC: Name of stock exchange
		if (aForm.getSubtype().equals("MarksecOtherListedLocal")) {
			if (!(errorCode = Validator.checkString(aForm.getStockExchange(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("stockExchange", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}
		}
		/*
		 * if (!(errorCode = Validator.checkDate(aForm.getLeDate(), false,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("leDate", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
		 * "1", 256 + ""));
		 * DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
		 * "... LeDate..."); }
		 */

		if (!aForm.getSubtype().equals("MarksecBondForeign")) {
			if ("Y".equals(aForm.getXchangeCtrlObtained())) {
				if (!(errorCode = Validator.checkDate(aForm.getXchangeCtrlDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("xchangeCtrlDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"1", "256"));
					DefaultLogger.debug(LOGOBJ, " aForm.getXchangeCtrlDate()= " + aForm.getXchangeCtrlDate());
				}
			}
		}
		DefaultLogger.debug(LOGOBJ, "No of Errors..." + errors.size());
		return errors;
	}

}
