package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.ui.common.NumberUtils;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class FixedDepositValidator {

	public static ActionErrors validateInput(FixedDepositForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		String regex = "[0-9]";
		/**/
		// added by Anup k.
		// Lien Amount Validaion
		/*Number depositAmountValue = NumberUtils.parseNumber(aForm.getDepAmt(), locale);
		if(depositAmountValue != null)
		{*/
	
		if (!(errorCode = Validator.checkNumber(aForm.getDepAmt(), true, 0.1, Double
				.parseDouble("9999999999999.99"))).equals(Validator.ERROR_NONE)) {
			if(errorCode.equals(Validator.ERROR_LESS_THAN)){
				errors.add("depAmt", new ActionMessage("error.string.more.rate", "Deposit Amount ", "0"));
				 }else{
			
			errors.add("depAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"0.1", "9999999999999.99"));
				 }
		}
		/*else if (new BigInteger(aForm.getDepAmt()).longValue()==0) {
			errors.add("depAmt", new ActionMessage("error.string.nonzero","Deposit Amount ",""));
		}*/
		else{
			if(aForm.getActive().equals("active")){
		double usedLienAmt = 0.0;
		 if(null!=aForm.getDepositRefNo()&& !"".equals(aForm.getDepositRefNo())){
			String fdReceiptNo =  CollateralDAOFactory.getDAO().getReceiptNoByDepositID(aForm.getCashDepositID());
		 if(fdReceiptNo.equals(aForm.getDepositRefNo())){
		 usedLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(aForm.getDepositRefNo());
		 }
		 if(!fdReceiptNo.equals(aForm.getDepositRefNo())){
		// usedLienAmt=usedLienAmt + CollateralDAOFactory.getDAO().getAllTotalLienAmount(fdReceiptNo);
		 usedLienAmt=usedLienAmt + CollateralDAOFactory.getDAO().getAllTotalLienAmount(aForm.getDepositRefNo());
		 }
		 }
		 double remainingLienAmt = 0.0;
		 
		 if(null!=aForm.getDepAmt()&& !"".equals(aForm.getDepAmt())){
		 remainingLienAmt=Double.parseDouble(aForm.getDepAmt().replaceAll(",",""))-usedLienAmt;
		 DecimalFormat dft = new DecimalFormat("#0.00");
		 String val = dft.format(remainingLienAmt);		 
		 remainingLienAmt = Double.parseDouble(val);
		 }
		 
		 double currentLienAmt = 0.0;
		// double currentLienAmtOld = 0.0;
		 if((null!=aForm.getCashDepositID()&& !"".equals(aForm.getCashDepositID()))&& (null!=aForm.getDepositRefNo()&& !"".equals(aForm.getDepositRefNo()))){
		
		  String fdReceiptNo =  CollateralDAOFactory.getDAO().getReceiptNoByDepositID(aForm.getCashDepositID());
		  
		  if(fdReceiptNo.equals(aForm.getDepositRefNo())){
				 currentLienAmt=CollateralDAOFactory.getDAO().getCurrentLienAmount(aForm.getCashDepositID(),fdReceiptNo);
			}
		/*	  if(!fdReceiptNo.equals(aForm.getDepositRefNo())){
					 currentLienAmt=CollateralDAOFactory.getDAO().getCurrentLienAmount(aForm.getCashDepositID(),fdReceiptNo);
					 DefaultLogger.debug("FixedDepositValidator 90", "currentLienAmt:"+currentLienAmt);
				 }
				 else
				 {
					  currentLienAmt=CollateralDAOFactory.getDAO().getCurrentLienAmount(aForm.getCashDepositID(),aForm.getDepositRefNo());
					  DefaultLogger.debug("FixedDepositValidator 95", "currentLienAmt:"+currentLienAmt);
				 } */
		  
		 }
		 String lienTotalstr = "";
		 if(aForm.getLienTotal()!=null ||aForm.getLienTotal().equals("")){
			if(Double.parseDouble(UIUtil.removeComma(aForm.getLienTotal()))>(currentLienAmt+remainingLienAmt))				
				errors.add("lienTotal", new ActionMessage("error.number.must.lessthan.lien.utilized",lienTotalstr));	
		//	exceptionMap.put("lienTotal", new ActionMessage("error.number.must.lessthan.lien.utilized",lienTotal));
		 }
			}
	}
		
		/**/
		/***************Start : commented by sachin patil (17 Aug,2011) this field is not mandetory for FD*******************/
		if (StringUtils.equals(aForm.getOwnBank(), "")) {
			//if (!(errorCode = Validator.checkString(aForm.getDepositorName(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of ownBank" + aForm.getOwnBank());
    			errors.add("ownBank", new ActionMessage("error.string.mandatory", "1", "20"));
    		//}

		}
		if(!aForm.getOwnBank().equals("Y")){
			if (!(errorCode = Validator.checkString(aForm.getDepositorName(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getDepositorName());
				errors.add("depositorName", new ActionMessage("error.string.mandatory", "1", "100"));
			}
			}
			
		
		if (!(errorCode = Validator.checkString(aForm.getSystemName(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getSystemName());
			errors.add("systemName", new ActionMessage("error.string.mandatory", "1", "10"));
		}
		if (!(errorCode = Validator.checkString(aForm.getSystemId(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getSystemId());
			errors.add("systemId", new ActionMessage("error.string.mandatory", "1", "10"));
		}
		/*
if (!(errorCode = Validator.checkString(aForm.getFinwareId(), false, 1, 10)).equals(Validator.ERROR_NONE)) {
			DefaultLogger.debug("this", "The value of Finware Id" + aForm.getFinwareId());
			errors.add("finwareId", new ActionMessage("error.string.mandatory", "1", "10"));
		}
*/
		
		/* Start : commented By sachin on 12 DEC 2011  - as i discuss with janki as for the new CR 
		 * we are going to change some logic like
		* 1.remove customer id from screen,2.remove enable/disable facility on selection of own radio button 
		*/
	/*	 else
		{
		if (StringUtils.equals(aForm.getOwnBank(), ICMSConstant.FALSE_VALUE)) {
			if (!(errorCode = Validator.checkString(aForm.getDepositorName(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getDepositorName());
    			errors.add("depositorName", new ActionMessage("error.string.mandatory", "1", "100"));
    		}
			
			//if (!(errorCode = Validator.checkNumber(aForm.getCustomerId(), true, 0, 9999999999L)).equals(Validator.ERROR_NONE)) {
    		//	DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getCustomerId());
    		//	errors.add("customerId", new ActionMessage("error.string.mandatory", "1", "10"));
    		//}
			
			if (!(errorCode = Validator.checkNumber(aForm.getCustomerId(), false, 0,
					9999999999L, 0, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("customerId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), null,
						null, "2"));
			}
			else if (!(errorCode = Validator.checkString(aForm.getCustomerId(), true, 1, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("customerId", new ActionMessage("error.string.mandatory", "1", "10"));
			}

		}
		else
		{
			aForm.setCustomerId("");
			if (!(errorCode = Validator.checkString(aForm.getSystemName(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getSystemName());
    			errors.add("systemName", new ActionMessage("error.string.mandatory", "1", "10"));
    		}
			if (!(errorCode = Validator.checkString(aForm.getSystemId(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getSystemId());
    			errors.add("systemId", new ActionMessage("error.string.mandatory", "1", "10"));
    		}
		}
		}
		*/
		/* END : commented By sachin on 12 DEC 2011  - as i discuss with janki as for the new CR 
		 * we are going to change some logic like
		* 1.remove customer id from screen,2.remove enable/disable facility on selection of own radio button 
		*/
		
		/*if (!(errorCode = Validator.checkNumber(aForm.getFinwareId(), true, 0, 9999999999L)).equals(Validator.ERROR_NONE)) {
			DefaultLogger.debug("this", "The value of Deposit Name" + aForm.getFinwareId());
			errors.add("finwareId", new ActionMessage("error.string.mandatory", "1", "10"));
		}
		*/
		
		if (aForm.getFinwareId()!=null && !"".equals(aForm.getFinwareId())){
		if(!isAlpha(aForm.getFinwareId())){
			errors.add("finwareId", new ActionMessage("error.number.format", "1", "10"));
		}
		else if (!isInteger(aForm.getFinwareId()) ){
			errors.add("finwareId", new ActionMessage("error.string.nodecimal","1","10"));
		}
		else if (new BigInteger(aForm.getFinwareId()).longValue()==0) {
			errors.add("finwareId", new ActionMessage("error.string.nonzero","Finware Id ",""));
		}		
		else if(new BigInteger(aForm.getFinwareId()).longValue()<0){
			errors.add("finwareId", new ActionMessage("error.string.negative","Finware Id ",""));
		}
		}
		 /*if (!(errorCode = Validator.checkString(aForm.getFinwareId(), true, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("finwareId", new ActionMessage("error.string.mandatory", "1", "10"));
		}*/
		if (!(errorCode = Validator.checkString(aForm.getActive(), true, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("active", new ActionMessage("error.string.mandatory", "1", "10"));
		}
		
		/*else if (StringUtils.equals(aForm.getOwnBank(), ICMSConstant.FALSE_VALUE)) {
            if (!(errorCode = Validator.checkDate(aForm.getIssueDate(), true, locale)).equals(Validator.ERROR_NONE)) {
                errors.add("issueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "10"));
            }
        }*/
		
		/***************End : commented by sachin patil (17 Aug,2011) this field is not mandetory for FD*******************/
		
			/*if (!(errorCode = Validator.checkNumber(aForm.getDepositReceiptNo(), false, 0, Double
					.parseDouble("9999999999999999999"))).equals(Validator.ERROR_NONE)) {
				errors.add("depositReceiptNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "9999999999999999999"));
			}
			else if (!StringUtils.isNumeric(aForm.getDepositReceiptNo())) {
				errors.add("depositReceiptNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE)); 
			}*/
			
			if (!(errorCode = Validator.checkString(aForm.getDepositReceiptNo(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of Deposit Receipt No:" + aForm.getDepositReceiptNo());
    			errors.add("depositReceiptNo", new ActionMessage("error.string.mandatory", "1", "20"));
    		}

			/*if (!(errorCode = Validator.checkNumber(aForm.getFdLinePercentage(), true, 0, Double
					.parseDouble("999.99"))).equals(Validator.ERROR_NONE)) {
				errors.add("fdLinePercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "999.99"));
			}*/
			 if (!(errorCode = Validator.checkNumber(aForm.getDepositeInterestRate(), true, Double
						.parseDouble("0.00"), Double.parseDouble("99.99"),3, locale)).equals(Validator.ERROR_NONE)) {
				 if(errorCode.equals(Validator.ERROR_LESS_THAN)){
				errors.add("depositeInterestRate", new ActionMessage("error.string.more.rate", "Deposit Interest Rate ", "0"));
				 }
				 else
				 {
					 errors.add("depositeInterestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
								"0", "99.99")); 
				 }
			}
		
      
            if (!(errorCode = Validator.checkDate(aForm.getIssueDate(), true, locale)).equals(Validator.ERROR_NONE)) {
                errors.add("issueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "10"));
            }
        
		
		/*if (!(errorCode = Validator.checkNumber(aForm.getDepositRefNo(), 
				StringUtils.equals(aForm.getOwnBank(), ICMSConstant.TRUE_VALUE), 0, Double
				.parseDouble("9999999999999999999"))).equals(Validator.ERROR_NONE)) {
			errors.add("depositRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"0", "9999999999999999999"));
		}
		else if (!StringUtils.isNumeric(aForm.getDepositRefNo())) {
			errors.add("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE));
		}
		*/	
					
            /*if (!(errorCode = Validator.checkString(aForm.getDepositRefNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
    			DefaultLogger.debug("this", "The value of Deposit Ref No:" + aForm.getDepositRefNo());
    			errors.add("depositRefNo", new ActionMessage("error.string.mandatory", "1", "20"));
    		}
            */
            if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(aForm.getDepositRefNo(), true, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				if(errorCode.equals("format"))
					errors.add("depositRefNo", new ActionMessage("error.string.specialcharacter"));
				else
				errors.add("depositRefNo", new ActionMessage("error.string.mandatory", "1", "20"));
			}
       
		if ("N".equalsIgnoreCase(aForm.getFdWebServiceFlag())) {
			if ("N".equals(aForm.getRadioSelect())) {
				int countFd = 0;
				String searchflag = "N";
				if (aForm.getDepositRefNo() != null
						|| !aForm.getDepositRefNo().equals("")) {
					countFd = CollateralDAOFactory.getDAO()
							.getNoOfDepositByDepositReceiptNo(
									aForm.getDepositRefNo());

				}
				if (countFd > 0) {
					if (null != aForm.getCashDepositID()
							|| !("").equals(aForm.getCashDepositID())) {
						searchflag = CollateralDAOFactory.getDAO()
								.getSerchFdFlagByDepositID(
										aForm.getCashDepositID(),
										aForm.getDepositRefNo());
					}
					if (searchflag.equals("")) {
						searchflag = "N";
					}

				} else {
					searchflag = "Y";
				}
				if ("N".equals(searchflag) || searchflag.isEmpty()) {
					errors.add("depositRefNo", new ActionMessage(
							"error.deposit.amount.search"));
				}
			}
		}
         
		/*if (!(errorCode = Validator.checkNumber(aForm.getDepositRefNo(), true,0,Double
				.parseDouble("9999999999999999999"))).equals(Validator.ERROR_NONE)) {
			errors.add("depositRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"0", "9999999999999999999"));
        }
		
		if (!StringUtils.isNumeric(aForm.getDepositRefNo())) {
			errors.add("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE));
		}*/

        if (StringUtils.isNotBlank(aForm.getIssueDate())
                && DateUtil.convertDate(locale, aForm.getIssueDate()).after(new Date())) {
            errors.add("issueDate", new ActionMessage("error.date.compareDate.cannotBelater",
                    "Fixed Deposit Date", "Today Date"));
        }

        if (!(errorCode = Validator.checkString(aForm.getThirdPartyBank(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
			DefaultLogger.debug("this", "The value of Third Party Bank IS:" + aForm.getThirdPartyBank());
			errors.add("thirdPartyBank", new ActionMessage("error.string.mandatory", "1", "20"));
		}

        /***************Start : commented by sachin patil (17 Aug,2011) this field is not mandetory for FD*******************/
		/*if (!(errorCode = Validator.checkString(aForm.getOwnBank(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("ownBank", new ActionMessage("error.string.mandatory", "1", "20"));
		}*/
        /***************End : commented by sachin patil (17 Aug,2011) this field is not mandetory for FD*******************/

		DefaultLogger.debug(FixedDepositValidator.class.getName(), "depMatDate is not null");
		
		 if (!(errorCode = Validator.checkDate(aForm.getDepMatDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				DefaultLogger.debug("this", "The value of dep Mat Date IS:" + aForm.getDepMatDate());
				errors.add("depMatDate", new ActionMessage("error.string.mandatory", "1", "10"));
			}
		
		/*if (!(errorCode = Validator.checkDate(aForm.getDepMatDate(), 
				StringUtils.equals(aForm.getOwnBank(), ICMSConstant.FALSE_VALUE), locale)).equals(Validator.ERROR_NONE)) {
			errors.add("depMatDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "10"));
		}*/
		 if(aForm.getActive().equals("active")){
		if (StringUtils.isNotBlank(aForm.getDepMatDate())
				&& DateUtil.convertDate(locale, aForm.getDepMatDate()).before(new Date())) {
			errors.add("depMatDate", new ActionMessage("error.date.compareDate.cannotBeEarlierOrSame",
					"Deposit Maturity Date", "Today Date"));
		}
		 }

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTenure())) {
			if (!(errorCode = Validator.checkNumber(aForm.getTenure(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tenure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), null,
						null, "2"));
			}
			else if (!(errorCode = Validator.checkString(aForm.getTenureUOM(), true, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("tenureUOM", new ActionMessage("error.string.mandatory", "1", "3"));
			}
		}

		

		//Number lienTot = NumberUtils.parseNumber(aForm.getLienTotal(), locale);
//		 if((aForm.getDepAmt() != null )&& (!aForm.getDepAmt().equals("") ) )
//		 {


		/*if (!(errorCode = Validator.checkNumber(aForm.getDepAmt(), true,0,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
            errors.add("depAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "9999999999999.99"));
        }*/

		Number depositAmountValue = NumberUtils.parseNumber(aForm.getDepAmt(), locale);
		 if ((depositAmountValue != null) && (depositAmountValue.intValue() == 0)) {
				errors.add("depAmt", new ActionMessage("error.number.must.morethan", "0"));
			}
		 /*
		 String depositStr = aForm.getDepAmt().toString();
		 DecimalFormat formatter = new DecimalFormat();
		 //int parsedFromString = formatter.parse(value);
		 try {
			 if(depositStr!=null && !depositStr.equals(""))
			 {
			depositStr = formatter.parse(depositStr).toString();
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 


		// double depositValue = Double.parseDouble(aForm.getDepAmt().toString());
		if((depositStr == null) ||  (depositStr.equals(""))){
			depositStr="0";
		}
			if((depositStr != null) &&  !(depositStr.equals(""))){
		double depositValue = Double.parseDouble(depositStr);
		if((aForm.getLienTotal().toString() != null) &&  !(aForm.getLienTotal().toString().equals(""))){
			double lienTot =  Double.parseDouble(aForm.getLienTotal().toString());
		
			 if(lienTot > depositValue)
			 {
				errors.add("lienTotal", new ActionMessage("error.number.must.lessthan.lien", "Deposit Amount"));
			 }
		}
		}*/
			
			if ((StringUtils.isNotBlank(aForm.getIssueDate()) &&(StringUtils.isNotBlank(aForm.getVerificationDate())))) {
				DateFormat formatterN ; 
				
				  formatterN = new SimpleDateFormat("dd/MMM/yyyy");
				Date IssueDate;
				Date  VerificationDate;
				try {
					IssueDate = (Date)formatterN.parse(aForm.getIssueDate());				
				  VerificationDate = (Date)formatterN.parse(aForm.getVerificationDate()); 
				  if(IssueDate.after(VerificationDate)){
						errors.add("verificationDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
								"Verification Date", "Fixed Deposit Date"));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  			
				
				
			}
			
			if ((StringUtils.isNotBlank(aForm.getDepMatDate()) &&(StringUtils.isNotBlank(aForm.getVerificationDate())))) {
				DateFormat formatterN ; 
				
				  formatterN = new SimpleDateFormat("dd/MMM/yyyy");
				Date matDate;
				Date  VerificationDate;
				try {
					matDate = (Date)formatterN.parse(aForm.getDepMatDate());				
				  VerificationDate = (Date)formatterN.parse(aForm.getVerificationDate()); 
				  if(VerificationDate.after(matDate)){
						errors.add("verificationDate", new ActionMessage("error.date.compareDate.cannotBelater",
								"Verification Date","Deposit Maturity Date" ));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  			
				
				
			}
		
		/*if (!(errorCode = Validator.checkNumber(aForm.getDepAmt(), 
				StringUtils.equals(aForm.getOwnBank(), ICMSConstant.FALSE_VALUE), 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
				3, locale)).equals(Validator.ERROR_NONE)) {
			if (Validator.ERROR_DECIMAL_EXCEEDED.equals(errorCode)) {
				errorCode = "moredecimalexceeded";
			}
			errors.add("depAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), null, null,
					"2"));
		}
		else if ((depositAmountValue != null) && (depositAmountValue.intValue() == 0)) {
			errors.add("depAmt", new ActionMessage("error.number.must.morethan", "0"));
		}*/

		if (!(errorCode = Validator.checkString(aForm.getDepCurr(), 
				StringUtils.equals(aForm.getOwnBank(), ICMSConstant.FALSE_VALUE), 1, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("depCurr", new ActionMessage("error.string.mandatory", "1", "3"));
		}
		return errors;
	}

	public static ActionErrors validateFDInfoSearch(FixedDepositForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		if (!(errorCode = Validator.checkString(aForm.getOwnBank(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("ownBank", new ActionMessage("error.string.mandatory", "1", "20"));
		}
		else if (ICMSConstant.FALSE_VALUE.equals(aForm.getOwnBank())) {
			errors.add("ownBank", new ActionMessage("error.fd.search.not.allow"));
		}
		else { // own bank
			if (!(errorCode = Validator.checkString(aForm.getDepositRefNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("depositRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"20"));
			}
			else if (!StringUtils.isNumeric(aForm.getDepositRefNo())) {
				errors.add("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE));
			}
			
			if (!StringUtils.isNumeric(aForm.getDepositReceiptNo())) {
				errors.add("depositReceiptNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE)); 
			}
		}
		return errors;
	}
	public static boolean isInteger( String input )   
	{   
	   try  
	   {   
	      //Integer.parseInt( input );   
		   BigInteger b=new BigInteger(input);
	      return true;   
	   }   
	   catch(Exception nfe) 
	   {   
	      return false;   
	   }   
	}  
	
	public static boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();
	    int count=0;
	    for (int i=0;i<chars.length;i++) {
	        if(Character.isLetter(chars[i])) {
	        	count=count+1;
	            
	        }
	    }
	    if(count!=0){
	    	return false;
	    }
	    else{
	    return true;
	    }
	}

}
