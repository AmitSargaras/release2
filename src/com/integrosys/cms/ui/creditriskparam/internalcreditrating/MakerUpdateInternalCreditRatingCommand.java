package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import org.apache.struts.action.ActionMessage;
import java.util.List;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.IInternalCreditRatingProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the ctryLimitParam for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class MakerUpdateInternalCreditRatingCommand extends AbstractCommand {
public class MakerUpdateInternalCreditRatingCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
      {"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"InternalCreditRatingForm", "com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam", FORM_SCOPE},      
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
			    IInternalCreditRatingParam creditRatingForm = (IInternalCreditRatingParam)(map.get("InternalCreditRatingForm"));
        	
//			    IInternalCreditRatingProxy proxy = InternalCreditRatingProxyFactory.getProxy();
                IInternalCreditRatingProxy proxy = getInternalCreditRatingProxy();
			    
			    if(( internalCreditRatingTrxObj.getStagingICRList() != null && internalCreditRatingTrxObj.getStagingICRList().size() >0 )
		           || (internalCreditRatingTrxObj.getActualICRList() != null && internalCreditRatingTrxObj.getActualICRList().size() >0))
					{		
						internalCreditRatingTrxObj.setStagingICRList(creditRatingForm.getInternalCreditRatingList());
				    IInternalCreditRatingTrxValue res = proxy.makerUpdateInternalCreditRating(ctx, internalCreditRatingTrxObj);
				    result.put("request.ITrxValue", res);	
						
					}
					else
					{
						exceptionMap.put("internalCreditRatingList", new ActionMessage("error.internal.credit.rating.list"));
					}
				
				
					
					
			
			
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
