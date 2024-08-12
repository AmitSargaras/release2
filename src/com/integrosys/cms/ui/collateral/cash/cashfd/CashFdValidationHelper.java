package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/*
 * Created by IntelliJ IDEA.
 * User: Naveen
 * Date: Feb 20, 2007
 * Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class CashFdValidationHelper implements IFileUploadConstants{
	public static ActionErrors validateInput(CashFdForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		/*
		 * if (aForm.getEvent().equals("submit") ||
		 * aForm.getEvent().equals("update")) { if (!(errorCode =
		 * Validator.checkString(aForm.getValCurrency(), true, 1,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("valCurrency", new
		 * ActionMessage("error.string.mandatory", "1", "3"));
		 * DefaultLogger.debug
		 * ("com.integrosys.cms.ui.collateral.cash,cashfd.CashFdValidationHelper"
		 * ,
		 * "============= aForm.valBefMargin - aForm.valCurrency() ==========>"
		 * ); } }
		 */

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
	
		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}
		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {
			if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("minimalFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}

			if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 1, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage("error.string.mandatory", "1", "250"));
				// DefaultLogger.debug(LOGOBJ, "getIsInterestCapitalisation(): " +
				// aForm.getIsInterestCapitalisation());
			}

			if (!StringUtils.isNumericSpace(aForm.getCreditCardRefNumber())) {
			//	errors.add("creditCardRefNumber", new ActionMessage("error.number.format"));
			}

			/*if (!(errorCode = Validator.checkString(aForm.getIssuer(), isMandatory, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("issuer", new ActionMessage("error.string.mandatory", "1", "100"));
				// DefaultLogger.debug(LOGOBJ, "getIsInterestCapitalisation(): " +
				// aForm.getIsInterestCapitalisation());
			}*/
			}
	
		
		if("approve".equals(aForm.getEvent()) || "submit".equals(aForm.getEvent()) || "reject".equals(aForm.getEvent())){
			//For FD Upload validation
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(jdbc.getUplodCount(FILEUPLOAD_FD_TRANS_SUBTYPE)>0){
			//	if(errors.get("valBefMargin")==null){
				DefaultLogger.debug("CashFdValidationHelper" , "FD Upload is in Process(CashFdValidationHelper)");
				errors.add("valBefMargin", new ActionMessage("error.fd.uplod.process"));
			
				}
		}

		return errors;

	}
}
