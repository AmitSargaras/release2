/**
 * 
 */
package com.integrosys.cms.asst.validator;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author janki.mandalia
 * 
 */
public class ASSTValidator {

	private static boolean validateXSS(String input) {
		if (input == null) {
			return true;
		} else {
			input = input.toLowerCase();

			if (input.indexOf("<script") != -1 || input.indexOf("alert(") != -1
					|| input.indexOf("<img") != -1
					//|| input.indexOf("style ") != -1
					|| input.indexOf("script:") != -1
					|| input.indexOf("@import") != -1
					|| input.indexOf("javascript:") != -1
					|| input.indexOf("</") != -1 || input.indexOf("src=") != -1
					|| input.indexOf("type=") != -1
					|| input.indexOf("expression(") != -1
					|| input.indexOf("href=") != -1
					|| input.indexOf("url(") != -1) {

				// TODO: TODO in case of java 1.5 or higher user below mentioned
				// block
				/*
				 * if (input.contains("<script") || input.contains("alert(") ||
				 * input.contains("<img") || input.contains("style ") ||
				 * input.contains("script:") || input.contains("@import") ||
				 * input.contains("javascript:") || input.contains("</") ||
				 * input.contains("src=") || input.contains("type=") ||
				 * input.contains("expression(") || input.contains("href=") ||
				 * input.contains("url(")) {
				 */

				return true;
			} else {
				return false;
			}
		}

	}

	private static boolean validateSQLinject(String input) {

		if (input == null) {
			return true;
		} else {
			input = input.toLowerCase();
			if (input.indexOf("select ") != -1
					|| input.indexOf("delete ") != -1
					|| input.indexOf("drop index ") != -1
					|| input.indexOf("drop table ") != -1
					|| input.indexOf("drop database ") != -1
					|| input.indexOf("drop user ") != -1
					|| input.indexOf("drop view ") != -1
					|| input.indexOf("drop column ") != -1
					|| input.indexOf("drop schema ") != -1
					|| input.indexOf("drop certificate ") != -1
					|| input.indexOf("drop procedure ") != -1
					|| input.indexOf("drop function ") != -1
					|| input.indexOf("drop login ") != -1
					|| input.indexOf("drop trigger ") != -1
					|| input.indexOf("drop synonym ") != -1
					|| input.indexOf("drop sequence ") != -1
					|| input.indexOf("drop role ") != -1
					|| input.indexOf("insert ") != -1
					|| input.indexOf("truncate ") != -1
					|| input.indexOf("update ") != -1
					|| input.indexOf(" distinct ") != -1
					// || input.indexOf(" union ") != -1
					|| input.indexOf(" join ") != -1
					|| input.indexOf(" table ") != -1) {

				// TODO in case of java 1.5 or higher user below mentioned block
				/*
				 * if (input.contains("select ") || input.contains("delete ") ||
				 * input.contains("drop ") || input.contains("insert ") ||
				 * input.contains("truncate ") || input.contains("update ") ||
				 * input.contains("distinct ") || input.contains("union ") ||
				 * input.contains("join ") || input.contains("table ")) {
				 */
				return true;
			} else {

				return false;
			}
		}

	}

public static boolean isValidGenericASST(String string){
	if (string!=null && string.trim().length() != 0) {
		if (validateXSS(string) || validateSQLinject(string)) {
			return true;
		} else {
			return false;
		}
		} else {
			return false;
		}
}
	public static boolean isValidAlphaNumStringWithoutSpace(String string) {

		if (string!=null && string.trim().length() != 0) {

			// Pattern pattern = Pattern.compile("^[\\w\\S][\\.]+$");
			Pattern pattern = Pattern.compile("^[\\w]+$");

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				pattern = Pattern.compile("^[\\_]");
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					return true;
				} else {
					// validate XSS attack and SQL injection
					if (validateXSS(string) || validateSQLinject(string)) {
						return true;
					} else {
						return false;
					}
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static boolean isValidAlphaNumStringWithSpace(String string) {

		if (string!=null && string.trim().length() != 0) {

			Pattern pattern = Pattern.compile("^[\\w\\s\\.]+$");// ("([^a-zA-z0-9\\s])");//

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				pattern = Pattern.compile("[\\_]");
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					return true;
				} else {
					// validate XSS attack and SQL injection
					if (validateXSS(string) || validateSQLinject(string)) {
						return true;
					} else {
						return false;
					}
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static boolean isValidAlphaNumStringWithSpacewWithSlash(String string) {

		if (string!=null && string.trim().length() != 0) {

			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\/]+$");// ("([^a-zA-z0-9\\s])");//

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				pattern = Pattern.compile("[\\_]");
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					return true;
				} else {
					// validate XSS attack and SQL injection
					if (validateXSS(string) || validateSQLinject(string)) {
						return true;
					} else {
						return false;
					}
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	public static boolean isValidAlphaNumStringWithSpacewAndUnderScore(String string) {
		
		if (string!=null && string.trim().length() != 0) {
			
			Pattern pattern = Pattern.compile("^[\\w\\s\\_]+$");// ("([^a-zA-z0-9\\s_])");//
			
			Matcher matcher = pattern.matcher(string);
			
			if (matcher.find()) {
				
				pattern = Pattern.compile("^[\\_]");
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					return true;
				} else {
					// validate XSS attack and SQL injection
					if (validateXSS(string) || validateSQLinject(string)) {
						return true;
					} else {
						return false;
					}
				}
				
			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean isValidLeiCode(String leiCode) {

		String regex = "^[0-9]{4}[0]{2}[A-Z0-9]{12}[0-9]{2}$";

		Pattern p = Pattern.compile(regex);

		if (leiCode == null) {
			return false;
		}

		Matcher m = p.matcher(leiCode);

		return m.matches();
	}
	
	
	public static boolean isValidFacilityLineNumber(String string) {

		if (string!=null && string.trim().length() != 0) {

			// Pattern pattern = Pattern.compile("^[\\w\\S][\\.]+$");
			Pattern pattern = Pattern.compile("^[\\w\\-\\_\\(\\)]+$");

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static boolean isValidFacilityName(String string) {

		if (string!=null && string.trim().length() != 0) {

			//Pattern pattern = Pattern.compile("^[\\w\\s\\.\\-\\/\\(\\)\\&\\,\\%]+$");// ("([^a-zA-z0-9\\s])");//
			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\-\\/\\(\\)\\,\\%]+$");// ("([^a-zA-z0-9\\s])");//

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static boolean isValidCAMNumber(String string) {

		if (string!=null && string.trim().length() != 0) {

			// Pattern pattern = Pattern.compile("^[\\w\\S][\\.]+$");
			Pattern pattern = Pattern.compile("^[\\w\\/]+$");

			Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean validateIFSC(String ifsc)
	{
		String patternString= "^[A-Z]{4}0[A-Z0-9]{6}$";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(ifsc);
		return matcher.matches();
	}

/*	public static boolean isValidEmail(String string) {
		if (string!=null && string.trim().length() != 0) {
		Pattern p = Pattern.compile("[a-zA-Z]*[0-9]*.*[a-zA-Z]*[0-9]*@[a-zA-Z]*.[a-zA-Z]*.[a-zA-Z]*");
		Matcher m = p.matcher(string);
		// Matcher m=p.matcher(args[0]);
		boolean b = m.matches();
			if (m.matches()) {
				DefaultLogger.debug("ASSTValidator","Valid Email ID");
				return false;
			} else {
				DefaultLogger.debug("ASSTValidator","InValid Email ID");
				return true;
			}
		} else
			return false;
	}*/
	
	//CLMJ-14
	public static boolean isValidEmail(String string) {
		//String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
        "[a-zA-Z0-9_+&*-]+)*@" + 
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
        "A-Z]{2,7}$";
		if (string!=null && string.trim().length() != 0) {
		boolean b = string.matches(regex);
			if (b) {
				DefaultLogger.debug("ASSTValidator","Valid Email ID");
				return false;
			} else {
				DefaultLogger.debug("ASSTValidator","InValid Email ID");
				return true;
			}
		} else
			return false;
	}

	
	public static boolean isValidCurrencyName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\-]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	
	
	public static boolean isValidANDName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			//Pattern pattern = Pattern.compile("^[\\w\\s\\&]+$");
			Pattern pattern = Pattern.compile("^[\\w\\s]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidOtherBankName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\-\\'\\,\\(\\)\\.]+$");
			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\-\\'\\,\\(\\)\\.]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidOtherBankBranchName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\-\\.]+$");
			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\-]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	public static boolean  isValidCreditApproverName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\']+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidRelationshipManagerName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\'\\_\\-\\(\\)]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidDirectorName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\']+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidPercentRoundBrackets(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			/*Pattern pattern = Pattern.compile("^[\\w\\s\\%\\.\\,\\-\\&]+$");*/
			Pattern pattern = Pattern.compile("^[\\w\\s\\%\\(\\)]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean  isValidAndDotDashRoundBrackets(String string) {

		if (string!=null && string.trim().length() != 0) {
			
			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\-\\(\\)]+$");
			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\.\\-\\(\\)]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	
	
	public static boolean  isValidAndRoundBrackets(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\(\\)]+$");
			Pattern pattern = Pattern.compile("^[\\w\\s\\(\\)]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static boolean  isValidDash(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\-]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	
	public static boolean isValidDocumentName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\(\\)\\,\\/\\-\\.\\%]+$");
			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\(\\)\\,\\/\\-\\.\\%]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	
	public static boolean isValidMutualFundName(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\(\\)\\-]+$");
			//Pattern pattern = Pattern.compile("^[\\w\\s\\&\\(\\)\\-]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean isValidCustomerAPRCode(String string) {

		if (string!=null && string.trim().length() != 0) {
			

			Pattern pattern = Pattern.compile("^[\\w\\s\\-\\/]+$");
	
			Matcher matcher = pattern.matcher(string);
	
			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean isValidPanNo(String str1) {
		
		String str2 = str1.substring(0, 5);
		String str3 = str1.substring(5, 9);
		String str4 = str1.substring(9, 10);	
		
		try{
			Integer.parseInt(str3);
		}catch(Exception e){
			return true;
		}
		
		try{
			Integer.parseInt(str4);
			return true;
		}catch(Exception e){		}
		
		if (str2!=null && str2.trim().length() != 0) {
			Pattern pattern = Pattern.compile("[a-zA-Z]*");
			Matcher matcher = pattern.matcher(str2);
			boolean b = matcher.matches();
			if (b != true) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String str[]) {
		
		System.out.println(ASSTValidator
				.isValidDocumentName("janki /mandalia"));

	}

	
	public static boolean isValidDecimalNumberWithComma(String num){
		
		if(null!=num && !num.trim().isEmpty()){
			Pattern pattern = Pattern.compile("^-?[0-9]\\d*(\\.\\d+)?$");
			Matcher matcher = pattern.matcher(num);
			boolean b = matcher.matches();
			if (b) {
				return true;
			}
		}
		return false;
		
	}

	
	//Added for FD Upload
	public static boolean isAlphaNumeric(String s){
		if(null!=s && !s.isEmpty()){
	    String pattern= "^[a-zA-Z0-9]*$";
	        if(s.trim().matches(pattern)){
	            return true;
	        }
	        
		}
		
	    return false;   
	}
	
	
	public static boolean isValidInternalRemarks(String string) {

		if (string!=null && string.trim().length() != 0) {

			
			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\-\\#\\&\\+\\%\\=\"]+$");
			 Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}
	
	public static boolean isValidLineDescription(String string) {

		if (string!=null && string.trim().length() != 0) {

			
			Pattern pattern = Pattern.compile("^[\\w\\s\\.\\-\\#\\&\\+\\%\\=\"]+$");
			 Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				// validate XSS attack and SQL injection
				if (validateXSS(string) || validateSQLinject(string)) {
					return true;
				} else {
					return false;
				}

			} else {
				return true;
			}
		} else
			return false;
	}

	public static String checkBigInteger(String input, boolean check, BigInteger min, BigInteger max){
		PatternCompiler compiler = new Perl5Compiler();   
		PatternMatcher matcher = new Perl5Matcher();
		String ss = "noerror";
	        try
	        {
	            if(input != null)
	            	input = input.trim();
	            if((ss = checkMandatory(input, check)) == null){
	            
	            ss = "noerror";
	            String sInteger = "";
	            try
	            {
	            	PatternMatcherInput  patternMatcherInput = new PatternMatcherInput(input);
	                org.apache.oro.text.regex.Pattern pattern = compiler.compile("[^0-9,-]");
	                if(matcher.contains(input, pattern))
	                {
	                    DefaultLogger.debug("ASSTValidator", input + ": input contains the pattern otherthan 0-9,',','-'");
	                    ss = "format";
	                } else
	                {
	                    for(StringTokenizer st = new StringTokenizer(input, ",", false); st.hasMoreTokens();)
	                        sInteger = sInteger + st.nextToken();

	                    BigInteger iIntVal = new BigInteger(sInteger.trim());
	                    if( -1 == iIntVal.compareTo(min))
	                        ss = "lessthan";
	                    if(1 == iIntVal.compareTo(max))
	                        ss = "greaterthan";
	                }
	            }
	            catch(MalformedPatternException e)
	            {
	                DefaultLogger.error("ASSTValidator", "", e);
	                ss = "format";
	            }
	            catch(NumberFormatException e)
	            {
	                ss = "format";
	            }
	            catch(Exception e)
	            {
	                DefaultLogger.error("ASSTValidator", "", e);
	                ss = "format";
	            }
	            return ss;
	            }
	        }
	        catch(Exception e)
	        {
	            ss = "format";
	        }
	        
	        return ss;
		    } 
	
	   static String checkMandatory(String aString, boolean chk)
	    {
	        if(isEmptyString(aString))
	        {
	            if(chk)
	                return "mandatory";
	            else
	                return "noerror";
	        } else
	        {
	            return null;
	        }
	    }
	   static boolean isEmptyString(String aString)
	    {
	        if(aString != null)
	            aString = aString.trim();
	        return aString == null || aString.equals("");
	    }
	   
		public static boolean isAlphaNumericStringWithHyphen(String string) {

			if (StringUtils.isNotBlank(string) && string.trim().length() != 0) {
				Pattern pattern = Pattern.compile("^[-a-zA-Z0-9-]*$");
				Matcher matcher = pattern.matcher(string.trim());

				if (matcher.matches()) {
					return true;
				} 
			} 
			return false;
		}
	   
		//Added for RAM RATING Deferral Extension
		public static boolean isAlphaNumericWithDash(String s){
			if(null!=s && !s.isEmpty()){
		    String pattern= "^[a-zA-Z-0-9]*$";
		        if(s.trim().matches(pattern)){
		            return true;
		        }
			}
		    return false;   
		}

		public static boolean isAlphaNumericWithSpace(String s) {
			if(null!=s && !s.isEmpty()){
			    String pattern= "^[a-zA-Z 0-9]*$";
			        if(s.trim().matches(pattern)){
			            return true;
			        }
			}
			return false;
		}
	//End RAM RATING Deferral Extension
		
		public static boolean isValidDateDDMMMYYYY(String dateStr){
		
			try {
				 DefaultLogger.debug("ASSTValidator", "inside isValidDateDDMMMYYYY");
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				  sdf.setLenient(false);
			      sdf.parse(dateStr);
			      if(11==(dateStr.length())){
					     if('/'== dateStr.charAt(2) && '/'== dateStr.charAt(6)){
					    	 return true;
					     }else{
					    	 return false;
					     }
			      }else{
			    	  return false;
			      }
			 }
			 catch(Exception e){
				 DefaultLogger.debug("ASSTValidator", "Exception in isValidDateDDMMMYYYY:"+e.getMessage());
				 e.printStackTrace();
				 return false;
			 }
		
		}
		
		public static boolean isNumeric(String str) { 
			  try {  
			    Double.parseDouble(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
			}
		
		public static boolean isInteger(String num)
		{
			try {
			String input = num; 
			String pattern = "^\\d{1,15}$";

			Pattern regex = Pattern.compile(pattern);
			Matcher matcher = regex.matcher(input);
			if (matcher.matches()) {
			return true;
			} else {
			return false;
			}
			}
			catch(Exception e) {
			 e.printStackTrace();
			 return false;
			}
		
		}

}
