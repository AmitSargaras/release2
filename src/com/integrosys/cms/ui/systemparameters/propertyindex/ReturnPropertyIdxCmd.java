package com.integrosys.cms.ui.systemparameters.propertyindex;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class ReturnPropertyIdxCmd extends PropertyIdxCmd {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},				
        	{"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},
        	{"PropertyIndex","com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx",FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			IPropertyIdxTrxValue PropertyIdxTrxValue = (IPropertyIdxTrxValue)(map.get("IPropertyIdxTrxValue"));
			String fromEvent = (String)map.get("fromEvent");
            // for read event render form from original object
	        // otherwise render form from staging object
	        IPropertyIdx curPropertyIdx = null;
			
	        if ("read".equals(fromEvent))
	        {
	        	curPropertyIdx = PropertyIdxTrxValue.getPrIdx();
	        	DefaultLogger.debug("read Property Idx::::", curPropertyIdx);
	        }
	        else
	        {
	        	curPropertyIdx = PropertyIdxTrxValue.getStagingPrIdx();
	        	DefaultLogger.debug("retrun Property Idx::::", curPropertyIdx);
	        }
			result.put("PropertyIndex", curPropertyIdx);
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
