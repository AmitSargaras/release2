package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import java.util.List;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ReturnInternalCreditRatingCommand extends AbstractCommand {
public class ReturnInternalCreditRatingCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},				
        	{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},			
        	{"InternalCreditRatingForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)(map.get("internalCreditRatingTrxObj"));
			String fromEvent = (String)map.get("fromEvent");
            // for read event render form from original object
	        // otherwise render form from staging object

            IInternalCreditRatingParam creditRatingList = new OBInternalCreditRatingParam();
            
            List stagingList = internalCreditRatingTrxObj.getStagingICRList();
            
            if(stagingList != null && stagingList.size() > 0){
            	String[] param = {"IntCredRatCode"};
                SortUtil.sortObject(stagingList, param);  
            }
            
            creditRatingList.setInternalCreditRatingList(stagingList);

            result.put("InternalCreditRatingForm", creditRatingList);
			
			if (fromEvent != null || !fromEvent.trim().equals(""))
			{
				result.put("fromEvent", fromEvent);
			}				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}
}
