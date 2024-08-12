/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitValidator {

    public static ActionErrors validateCountryLimit(ActionForm aForm, Locale locale) {
	
        String errorCode = null;
        ActionErrors errors = new ActionErrors();
        Date currDate = DateUtil.getDate();
        try {
            CountryLimitForm lmtForm = (CountryLimitForm) aForm;    
			      String event = lmtForm.getEvent();
			      
						if (!(errorCode = RemarksValidatorUtil.checkRemarks(lmtForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
			                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));               

            }	
            
            if("delete_item".equals(event)){
			          if(lmtForm.getDeletedItemList() == null || lmtForm.getDeletedItemList().length == 0)
			          {
				                errors.add("chkDeleteItems", new ActionMessage("error.chk.del.records"));
			          }
	          }				
        }
        catch (Exception ex) {
			ex.printStackTrace();
        }
        return errors;
    }

    public static ActionErrors validateCountryLimitItem(ActionForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();		

        try {
            CountryLimitItemForm itemForm = (CountryLimitItemForm) aForm;			
			
			if (AbstractCommonMapper.isEmptyOrNull(itemForm.getCountry())) {
				errors.add("country", new ActionMessage("error.string.mandatory"));
            }	
			if (AbstractCommonMapper.isEmptyOrNull(itemForm.getCountryRating())) {
				errors.add("countryRating", new ActionMessage("error.string.mandatory"));
            }			
			
        }
        catch (Exception ex) {
			ex.printStackTrace();
        }
        return errors;
    }

	public static ActionErrors validateCountryRating(ActionForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();		

        try {
            CountryRatingForm itemForm = (CountryRatingForm) aForm;
			
			String[] ratingList = itemForm.getCountryRating();			
			String[] bankCapFundPercentList = itemForm.getBankCapFundPercent();			
			String[] presetCountryLimitPercentList = itemForm.getPresetCountryLimitPercent();		
				
			if( ratingList != null ) {
				for (int j = 0; j < ratingList.length; j++) {

					if (AbstractCommonMapper.isEmptyOrNull( bankCapFundPercentList[j] )) {
						//is not mandatory    
		            }			
					else{
						String value = bankCapFundPercentList[j];												
																		
		                if (!(errorCode = Validator.checkNumber(value, false, 0, 100, 3, locale)).equals(Validator.ERROR_NONE)) {
		                  if( errorCode.equals("greaterthan") || errorCode.equals("lessthan") ) {
							errors.add("bankCapFundPercent"+j, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "100"));

		                  } else if ( errorCode.equals("decimalexceeded") ) {
		                    errors.add("bankCapFundPercent"+j, new ActionMessage("error.number.moredecimalexceeded","","","2"));

		                  }else if ( !errorCode.equals("mandatory") ){
		                    errors.add("bankCapFundPercent"+j, new ActionMessage("error.number." + errorCode));
		                  }
		                }
		              }
					  
					if (AbstractCommonMapper.isEmptyOrNull( presetCountryLimitPercentList[j] )) {
					    //is not mandatory
					}			
					else{
						String value = presetCountryLimitPercentList[j];
						
						if (!(errorCode = Validator.checkNumber(value, false, 0, 100, 3, locale)).equals(Validator.ERROR_NONE)) {
						  if( errorCode.equals("greaterthan") || errorCode.equals("lessthan") ) {
							errors.add("presetCountryLimitPercent"+j, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "100"));

						  } else if ( errorCode.equals("decimalexceeded") ) {
							errors.add("presetCountryLimitPercent"+j, new ActionMessage("error.number.moredecimalexceeded","","","2"));

						  }else if ( !errorCode.equals("mandatory") ){
							errors.add("presetCountryLimitPercent"+j, new ActionMessage("error.number." + errorCode));
						  }
						}
					} 				
					
				}//end for	
			}	
        }
        catch (Exception ex) {
			ex.printStackTrace();
        }
        return errors;
    }
}
