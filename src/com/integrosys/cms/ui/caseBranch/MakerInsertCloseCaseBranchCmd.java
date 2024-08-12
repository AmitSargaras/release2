package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 * @author abhijit.rudrakshawar: 
 * Command for maker to close the rejected System Bank trx value
 */

public class MakerInsertCloseCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {
    
	private ICaseBranchProxyManager caseBranchProxy;

	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}

	/**
     * Default Constructor
     */
    public MakerInsertCloseCaseBranchCmd() {
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
        		{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue", SERVICE_SCOPE},
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
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
        	ICaseBranchTrxValue trxValueIn = (OBCaseBranchTrxValue) map.get("ICaseBranchTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            String event = (String) map.get("event");
            
            ICaseBranchTrxValue trxValueOut = getCaseBranchProxy().makerInsertCloseRejectedCaseBranch(ctx, trxValueIn);
            resultMap.put("request.ITrxValue", trxValueOut);
           

        }catch (CaseBranchException ex) {
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



