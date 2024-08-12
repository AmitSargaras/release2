package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.guarantees.linedetail.ILineDetailConstants;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class MarketableEquityLineDetailValidator {
	
	public static ActionErrors validateInput(MarketableEquityLineDetailForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		
		boolean isFacDetailMandatory = ICMSConstant.YES.equals(aForm.getFacDetailMandatory());
		
		double totalLtv = 0;
		if(StringUtils.isNotBlank(aForm.getTotalLtv())) {
			totalLtv =Double.parseDouble(aForm.getTotalLtv());
		}
		
		if(isFacDetailMandatory && StringUtils.isBlank(aForm.getFacilityName())) {
			errors.add("facilityName", new ActionMessage("error.string.mandatory"));
		}
		if(isFacDetailMandatory && StringUtils.isBlank(aForm.getFacilityId())) {
			errors.add("facilityId", new ActionMessage("error.string.mandatory"));
		}
		if(StringUtils.isBlank(aForm.getLineNumber())) {
			errors.add("lineNumber", new ActionMessage("error.string.mandatory"));
		}
		
		if(StringUtils.isBlank(aForm.getSerialNumber())) {
			errors.add("serialNumber", new ActionMessage("error.string.mandatory"));
		}
		else if (!(errorCode = Validator.checkNumber(aForm.getSerialNumber(), isFacDetailMandatory, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
			errors.add("serialNumber", new ActionMessage("error.string.serial.no"));
		}
		
		if (!(errorCode = Validator.checkString(aForm.getFasNumber(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("fasNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
		}
		
		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 1, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
		}
		
		if (!(errorCode = Validator.checkNumber(aForm.getLtv(), false, 0, 100,4,locale)).equals(Validator.ERROR_NONE)) {
			if("decimalexceeded".equals(errorCode)) {
				errors.add("ltv", new ActionMessage("error.number.custom.decimalexceeded", "3"));
			}
			else {
				errors.add("ltv", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
			}
		}
		else if(StringUtils.isNotBlank(aForm.getLtv())) {
			totalLtv += Double.parseDouble(aForm.getLtv());
			if(totalLtv>100) {
				errors.add("ltv", new ActionMessage("error.total.ltv.exceeded"));
			}
		}
		
		if (!(errorCode = Validator.checkNumber(aForm.getLineValue(), false, 0,
				ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_24_4, 5, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lineValue",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_24_4_STR));
		}
		else {
			errorCode = UIUtil.compareExponentialValue(aForm.getLineValue(), ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_24_4_STR);
			if(!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("lineValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_24_4_STR));
			}
		}
		
		DefaultLogger.error(MarketableEquityLineDetailValidator.class.getName(),"  errors "+ errors.size());

		return errors;
	}
	
	
}
