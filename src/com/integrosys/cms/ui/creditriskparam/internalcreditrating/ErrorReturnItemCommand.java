package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ErrorReturnItemCommand extends AbstractCommand {
public class ErrorReturnItemCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return new String[][]{	
		{"InternalCreditRatingItemForm", "java.lang.Object", FORM_SCOPE},
      };
	}
	
	public String[][] getResultDescriptor() {
		return new String[][]{	
			{"InternalCreditRatingItemForm", "java.lang.Object", FORM_SCOPE},
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		    HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			    IInternalCreditRating form = (IInternalCreditRating)(map.get("InternalCreditRatingItemForm"));
	        //result.put("InternalCreditRatingItemForm", form);
        	
		}
		catch(Exception ex)
		{
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}	
	
}
