/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gteindiv/GteIndivValidationHelper.java,v 1.7 2004/06/04 05:19:57 hltan Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gteindiv;

import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/04 05:19:57 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GteIndivValidationHelper {
	public static ActionErrors validateInput(GteIndivForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkString(aForm.getValCurrency(), isMandatory, 1, 3)).equals(Validator.ERROR_NONE)) {
				// errors.add("valCurrency", new
				// ActionMessage("error.string.mandatory", "1", "3"));
			}
			try {
				if (!(errorCode = Validator.checkDate(aForm.getDateGuarantee(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 256 + ""));
				}
				else if ((aForm.getDateGuarantee() != null) && (aForm.getDateGuarantee().length() > 0)
						&& (aForm.getCollateralMaturityDate() != null)
						&& (aForm.getCollateralMaturityDate().length() > 0)) {
					Iterator itr = errors.get("collateralMaturityDate");
					if ((itr == null) || !itr.hasNext()) {
						Date currDate = DateUtil.convertDate(locale, aForm.getCollateralMaturityDate());
						Date date1 = DateUtil.convertDate(locale, aForm.getDateGuarantee());
						if (date1.after(currDate)) {
							errors.add("dateGuarantee", new ActionMessage("error.date.compareDate.more",
									"Date of Guarantee", "Security Maturity Date"));
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!(errorCode = Validator.checkInteger(aForm.getSecuredPortion(), false, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
				errors.add("securedPortion", new ActionMessage(errorMessage,"0", "100"));
			}else{
				if(aForm.getSecuredPortion()!=null){
					String securedPortion = aForm.getSecuredPortion();
					for(int i=0;i<securedPortion.length();i++){
						char aa = ',';
						if(aa==securedPortion.charAt(i)){
							errors.add("securedPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}
			
			if (!(errorCode = Validator.checkInteger(aForm.getUnsecuredPortion(), false, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
				errors.add("unsecuredPortion", new ActionMessage(errorMessage,"0", "100"));
			}else{
				if(aForm.getUnsecuredPortion()!=null){
					String unSecuredPortion = aForm.getUnsecuredPortion();
					for(int i=0;i<unSecuredPortion.length();i++){
						char aa = ',';
						if(aa==unSecuredPortion.charAt(i)){
							errors.add("unsecuredPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}
		}

		return errors;

	}
}
