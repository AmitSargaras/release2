package com.integrosys.cms.ui.securityenvelope;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Erene Wong
 * @since 30 Jan 2010
 */
public class ReturnSecEnvelopeCmd extends SecEnvelopeCmd {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},
        	{"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},
        	{"SecurityEnvelope","com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope",FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			ISecEnvelopeTrxValue SecEnvelopeTrxValue = (ISecEnvelopeTrxValue)(map.get("ISecEnvelopeTrxValue"));
			String fromEvent = (String)map.get("fromEvent");
            String secEnvelopeItemId = (String)map.get("secEnvelopeItemId");
            // for read event render form from original object
	        // otherwise render form from staging object
	        ISecEnvelope curSecEnvelope = null;
			
	        if ("read".equals(fromEvent))
	        {
	        	curSecEnvelope = SecEnvelopeTrxValue.getSecEnvelope();
	        	DefaultLogger.debug("read Sec Envelope::::", curSecEnvelope);
	        }
	        else
	        {
	        	curSecEnvelope = SecEnvelopeTrxValue.getStagingSecEnvelope();
	        	DefaultLogger.debug("return Sec Envelope::::", curSecEnvelope);
	        }
			result.put("SecurityEnvelope", curSecEnvelope);
			if (fromEvent != null || !fromEvent.trim().equals(""))
			{
				result.put("fromEvent", fromEvent);
                result.put("secEnvelopeItemId", secEnvelopeItemId);
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
