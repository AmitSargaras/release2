package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.IInternalCreditRatingProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class CheckerApproveInternalCreditRatingCommand extends AbstractCommand {
public class CheckerApproveInternalCreditRatingCommand extends InternalCreditRatingCommand {
	 public String[][] getParameterDescriptor() {
        return (new String[][]{
           {"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
			     {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
			      {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }
    
    
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try
				{
		        	OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		        	IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
//		        	IInternalCreditRatingProxy proxy = InternalCreditRatingProxyFactory.getProxy();
                    IInternalCreditRatingProxy proxy = getInternalCreditRatingProxy();

							IInternalCreditRatingTrxValue trxVal = proxy.checkerApproveUpdateInternalCreditRating(ctx, internalCreditRatingTrxObj);
							result.put("request.ITrxValue", trxVal);
		
					
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
