package com.integrosys.cms.ui.caseCreationUpdate;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 * @author abhijit.rudrakshawar: 
 * Command for maker to close the rejected CaseCreation trx value
 */

public class MakerCloseCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
    
	private ICaseCreationProxyManager caseCreationProxy;
	
	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(
			ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}

	/**
     * Default Constructor
     */
    public MakerCloseCaseCreationCmd() {
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,CaseCreationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
            ICaseCreationTrxValue trxValueIn = (OBCaseCreationTrxValue) map.get("ICaseCreationTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            String event = (String) map.get("event");
            if(event.equals("maker_confirm_draft_close")){
            	ICaseCreationTrxValue trxValueOut = getCaseCreationProxy().makerCloseDraftCaseCreation(ctx, trxValueIn);
            	 resultMap.put("request.ITrxValue", trxValueOut);
            }else{
            ICaseCreationTrxValue trxValueOut = getCaseCreationProxy().makerCloseRejectedCaseCreation(ctx, trxValueIn);
            resultMap.put("request.ITrxValue", trxValueOut);
            }
           

        }catch (CaseCreationException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
        catch (TransactionException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



