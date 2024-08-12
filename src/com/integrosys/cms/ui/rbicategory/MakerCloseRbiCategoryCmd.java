package com.integrosys.cms.ui.rbicategory;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 * @author govind.sahu: 
 * Command for maker to close the rejected rbi category trx value
 */

public class MakerCloseRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {
    
	private IRbiCategoryProxyManager rbiCategoryProxy;
	
	

	/**
     * Default Constructor
     */
    public MakerCloseRbiCategoryCmd() {
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
                {"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue", SERVICE_SCOPE},
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
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,RbiCategoryException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
            IRbiCategoryTrxValue trxValueIn = (OBRbiCategoryTrxValue) map.get("rbiCategoryTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            String event = (String) map.get("event");
            if(event.equals("maker_confirm_draft_close")){
            	IRbiCategoryTrxValue trxValueOut = getRbiCategoryProxy().makerCloseDraftRbiCategory(ctx, trxValueIn);
            	 resultMap.put("request.ITrxValue", trxValueOut);
            }else{
            IRbiCategoryTrxValue trxValueOut = getRbiCategoryProxy().makerCloseRejectedRbiCategory(ctx, trxValueIn);
            resultMap.put("request.ITrxValue", trxValueOut);
            }
           

        }catch (RbiCategoryException ex) {
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

	/**
	 * @return the rbiCategoryProxy
	 */
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	/**
	 * @param rbiCategoryProxy the rbiCategoryProxy to set
	 */
	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}
}



