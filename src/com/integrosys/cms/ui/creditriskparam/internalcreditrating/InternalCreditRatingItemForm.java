package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import com.integrosys.cms.ui.common.TrxContextForm;
import java.io.Serializable;
import java.util.List;

/**
* Describe this class.
* Purpose: To set get and set method for the value needed by Auto Valuation Parameters
* Description: Have set and get method to store the screen value and get the value from other command class
*
* @author $Author$<br>
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class InternalCreditRatingItemForm extends TrxContextForm implements Serializable {
	
	   private String fromEvent;	
		 private String indexID;
		
		 private String creditRatingGrade;
		 private String creditRatingLmtAmtCode;
		 private String creditRatingLmtAmt;
	 

		public String getFromEvent() 
		{
			return fromEvent;
		}
		
		public void setFromEvent(String fromEvent) 
		{
			this.fromEvent = fromEvent;
	  }
	  
	  public String getIndexID(){
	  	return indexID;
	  }
	  
	  public void setIndexID(String indexID)
	  {
		  this.indexID = indexID;
	  }
	  
	  public String getCreditRatingGrade()
	  {
		  return creditRatingGrade;
	  }
	  
	  public void setCreditRatingGrade(String creditRatingGrade)
	  {
		  this.creditRatingGrade = creditRatingGrade;
	  }
	  
	  public String getCreditRatingLmtAmtCode()
	  {
		  return creditRatingLmtAmtCode;
	  }
	  
	  public void setCreditRatingLmtAmtCode(String creditRatingLmtAmtCode)
	  {
		  this.creditRatingLmtAmtCode = creditRatingLmtAmtCode;
	  }
	  
	  public String getCreditRatingLmtAmt()
	  {
		  return creditRatingLmtAmt;
	  }
	  
	  public void setCreditRatingLmtAmt(String creditRatingLmtAmt)
	  {
		  this.creditRatingLmtAmt = creditRatingLmtAmt;
	  }		
   
	  public String[][] getMapper() {
	
	        String[][] input = {
	               {"InternalCreditRatingItemForm", "com.integrosys.cms.ui.creditriskparam.internalcreditrating.InternalCreditRatingItemMapper"},
	                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
	            };
	        return input;
	
	   }
	
}



