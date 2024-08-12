package com.integrosys.cms.ui.creditriskparam.internallimit.list;

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

public class InternalLimitListFormValidator implements java.io.Serializable {

	 private static final long serialVersionUID = 1L;

	public static ActionErrors validate(InternalLmtParameterForm aForm,Locale locale) {
		
		String event = aForm.getEvent();
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
//   System.out.println("************** validate *************");
		try{
			
			int i;
		
			String regex1 = "[-]*[0-9]+(\\.[0-9]*)?$";
//			System.out.println( "Internal Limit Parameter B4::::" + event);
			if("checker_approve_internalLmt".equals(event) || "checker_reject_internalLmt".equals(event))
      {
//	         System.out.println("Internal Limit Parameter After::::" + event);
	            if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
              }
       }
       else
       {
				for(i=0;i<aForm.getTotalLoanAdvAmt().length;i++)
				{
					if(!(aForm.getTotalLoanAdvAmt()[i]).trim().equals(""))
					{     
	                     
							   if(!Validator.checkPattern(aForm.getTotalLoanAdvAmt()[i],"[-]*[0-9]+(\\.[0-9]*)?$"))
							  {
								  errors.add("totalLoanAdvAmt"+i, new ActionMessage("error.check.amt", "1", "15"));
					      }
					      else if(!Validator.checkPattern(aForm.getTotalLoanAdvAmt()[i],"[-]*[0-9]+(\\,[0-9]*)?$"))
					      {
						      errors.add("totalLoanAdvAmt"+i, new ActionMessage("error.check.amt", "1", "15"));
					      }
							  else if(Double.parseDouble(aForm.getTotalLoanAdvAmt()[i]) < 0)
								{
									errors.add("totalLoanAdvAmt"+i, new ActionMessage("error.enter.min.numeric", "1", "15"));
								}
								else if (!(errorCode = Validator.checkAmount(aForm.getTotalLoanAdvAmt()[i], true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE))
								{
						       errors.add("totalLoanAdvAmt"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
								}	
					}
					else
					{
						errors.add("totalLoanAdvAmt"+i, new ActionMessage("error.enter.min.numeric", "1", "15"));
		      }
		      
				}
	      
				for(i=0;i<aForm.getCapitalFundAmt().length;i++)
				{
		      
					if(!aForm.getCapitalFundAmt()[i].trim().equals(""))
					{
						
						//if (!Validator.checkDoubleDigits(aForm.getCapitalFundAmt()[i], 10, 2, false)) {
		         //            errors.add("capitalFundAmt"+i, new ActionMessage("error.check.amt", "1", "15"));
		                     
				 		//	}
				 		 if(!Validator.checkPattern(aForm.getCapitalFundAmt()[i],"[-]*[0-9]+(\\.[0-9]*)?$"))
							  {
								  errors.add("capitalFundAmt"+i, new ActionMessage("error.check.amt", "1", "15"));
					      }
						 else if(Double.parseDouble(aForm.getCapitalFundAmt()[i]) < 0)
							{
								errors.add("capitalFundAmt"+i, new ActionMessage("error.enter.min.numeric", "1", "15"));
							} 
						else if (!(errorCode = Validator.checkAmount(aForm.getCapitalFundAmt()[i], true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE))
							{
					       errors.add("capitalFundAmt"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
					                        
							}	
				  }
					else
					{
						errors.add("capitalFundAmt"+i, new ActionMessage("error.enter.min.numeric", "1", "15"));
		      }
				}
	      
	      
	 			 for(i=0;i<aForm.getGp5LmtPercentage().length;i++)
					{
							if(!aForm.getGp5LmtPercentage()[i].trim().equals(""))
							{
							
									if (!Validator.checkPattern(aForm.getGp5LmtPercentage()[i], "[-]*[0-9]+(\\.[0-9]*)?$")) 
									{
									      errors.add("gp5LmtPercentage" + i, new ActionMessage("error.check.numeric", "1", "15"));
									}
									else if(!(Double.parseDouble(aForm.getGp5LmtPercentage()[i]) >= 0 && Double.parseDouble(aForm.getGp5LmtPercentage()[i]) <= 100))
									{
									    errors.add("gp5LmtPercentage"+i, new ActionMessage("error.check.range.percentage", "1", "15"));
									}
									else if (!Validator.checkNumber(aForm.getGp5LmtPercentage()[i], true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale).equals(Validator.ERROR_NONE)) 
									{
									    //errors.add("gp5LmtPercentage"+i, new ActionMessage("error.check.decimal.places","","","2"));
									    //if ( errorCode.equals("decimalexceeded")) 
			 	 							//{
			 	 								errors.add("gp5LmtPercentage"+i, new ActionMessage("error.number.moredecimalexceeded","","","2"));
			 	 							//}
									}
							}
							else
							{
							    errors.add("gp5LmtPercentage"+i, new ActionMessage("error.enter.min.percentage", "1", "15"));
							}
	      }
	 			
	 			for(i=0;i<aForm.getInternalLmtPercentage().length;i++)
	 			{
		 			
				 			if(!aForm.getInternalLmtPercentage()[i].trim().equals(""))
						  {
							
									if (!Validator.checkPattern(aForm.getInternalLmtPercentage()[i], "[-]*[0-9]+(\\.[0-9]*)?$")) 
									{
									      errors.add("internalLmtPercentage" + i, new ActionMessage("error.check.numeric", "1", "15"));
									}
									else if(!(Double.parseDouble(aForm.getInternalLmtPercentage()[i]) >= 0 && Double.parseDouble(aForm.getInternalLmtPercentage()[i]) <= 100))
									{
									    errors.add("internalLmtPercentage"+i, new ActionMessage("error.check.range.percentage", "1", "15"));
									}
									else if (!Validator.checkNumber(aForm.getInternalLmtPercentage()[i], true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale).equals(Validator.ERROR_NONE)) 
									{
									    //errors.add("internalLmtPercentage"+i, new ActionMessage("error.check.decimal.places","","","2"));
									    //if ( errorCode.equals("decimalexceeded")) 
			 	 							//{
			 	 								errors.add("internalLmtPercentage"+i, new ActionMessage("error.number.moredecimalexceeded","","","2"));
			 	 							//}
									}
							}
							else
							{
							    errors.add("internalLmtPercentage"+i, new ActionMessage("error.enter.min.percentage", "1", "15"));
							}
						}
						
						if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
	                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
	          }
	 			 
 			}
 			
 			
            
 		//	if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
     //           errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
     // }
      
    /*  if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
		            errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		        }
       */     
       
		}
		catch(Exception ex) 
		{
			   ex.printStackTrace();
		}
		
		return errors;
	}

}