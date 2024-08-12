package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.Locale;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.creditriskparam.internalcreditrating.InternalCreditRatingItemForm;


public class InternalCreditRatingFormValidator {
       
    public static ActionErrors validateInternalCreditRatingForm(InternalCreditRatingForm aForm, Locale locale) {
	
	      String event = aForm.getEvent();
        
        String errorCode = null;
        ActionErrors errors = new ActionErrors();
        DefaultLogger.debug(InternalCreditRatingFormValidator.class,"lolcale from common action >>>>>>>>>>"+locale);
        try {
            InternalCreditRatingForm creditRatingForm = (InternalCreditRatingForm) aForm;            

	         if("maker_delete_item".equals(event)){
		          if(creditRatingForm.getDeletedItemList() == null || creditRatingForm.getDeletedItemList().length == 0)
		          {
			                errors.add("deleteItems", new ActionMessage("error.chk.del.records"));
		          }
           }
           
           if("checker_approve_internalCreditrating".equals(event) || "checker_reject_internalCreditrating".equals(event) 
               || "maker_submit_internalCreditrating". equals(event))
           {
	            if (!(errorCode = RemarksValidatorUtil.checkRemarks(creditRatingForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) 
	            {
	                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
	            }
            }
	          

        }
        catch (Exception ex) {
			ex.printStackTrace();
        }
        return errors;
    }

    public static ActionErrors validateInternalCreditRatingItemForm(ActionForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();		
        DefaultLogger.debug(InternalCreditRatingFormValidator.class,"locale from common action >>>>>>>>>>"+locale);
        try {
             InternalCreditRatingItemForm itemForm = (InternalCreditRatingItemForm) aForm;

		         if (AbstractCommonMapper.isEmptyOrNull(itemForm.getCreditRatingGrade())) {
							   errors.add("creditRatingGrade", new ActionMessage("error.string.mandatory"));
			       }
			
		        if (AbstractCommonMapper.isEmptyOrNull(itemForm.getCreditRatingLmtAmt())) {
							   errors.add("creditRatingLmtAmt", new ActionMessage("error.string.mandatory"));
			       }	
					   else{  
						  
						      if(!(Validator.checkPattern(itemForm.getCreditRatingLmtAmt(),"[-]*[0-9]+(\\.[0-9]*)?$")))
								  {
//									  System.out.print("@@@Debug::::::::1" );
									  errors.add("creditRatingLmtAmt", new ActionMessage("error.number.format", "1", "15"));
						      }
						      else if(Double.parseDouble(itemForm.getCreditRatingLmtAmt()) < 1)
						      {
//							      System.out.print("@@@Debug::::::::2" );
							      errors.add("creditRatingLmtAmt", new ActionMessage("error.internal.amount","1","15"));
						      }
	                else if (!(errorCode = Validator.checkAmount(itemForm.getCreditRatingLmtAmt(), true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE))
	                {	        
//		                System.out.print("@@@Debug::::::::3");      
		                errors.add("creditRatingLmtAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
		              }
			       }
    
      }catch (Exception ex) {
			   ex.printStackTrace();
      }
        return errors;
    }
}
