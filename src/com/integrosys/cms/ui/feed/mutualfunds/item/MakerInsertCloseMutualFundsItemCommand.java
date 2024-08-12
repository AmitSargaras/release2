package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.mutualfunds.OBMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 *@author $Govind: Sahu $
 * Command for maker to close the rejected System Bank trx value
 */

public class MakerInsertCloseMutualFundsItemCommand extends AbstractCommand implements ICommonEventConstant {
    
	private IMutualFundsFeedProxy mutualFundsFeedProxy;

	/**
     * Default Constructor
     */
    public MakerInsertCloseMutualFundsItemCommand() {
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
        		{"mutualfundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue",SERVICE_SCOPE },
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
        	IMutualFundsFeedGroupTrxValue trxValueIn = (OBMutualFundsFeedGroupTrxValue) map.get("mutualfundsFeedGroupTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            String event = (String) map.get("event");
           
            IMutualFundsFeedGroupTrxValue trxValueOut = getMutualFundsFeedProxy().makerInsertCloseRejectedMutualFundsFeedEntry(ctx, trxValueIn);
            resultMap.put("request.ITrxValue", trxValueOut);
          

        }catch (MutualFundsFeedGroupException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	   }
//        catch (TransactionException e) {
//            DefaultLogger.debug(this, "got exception in doExecute" + e);
//            e.printStackTrace();
//            throw (new CommandProcessingException(e.getMessage()));
//        }
        catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

	/**
	 * @return the mutualFundsFeedProxy
	 */
	public IMutualFundsFeedProxy getMutualFundsFeedProxy() {
		return mutualFundsFeedProxy;
	}

	/**
	 * @param mutualFundsFeedProxy the mutualFundsFeedProxy to set
	 */
	public void setMutualFundsFeedProxy(IMutualFundsFeedProxy mutualFundsFeedProxy) {
		this.mutualFundsFeedProxy = mutualFundsFeedProxy;
	}



}



