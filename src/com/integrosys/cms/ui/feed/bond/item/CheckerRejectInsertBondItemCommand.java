package com.integrosys.cms.ui.feed.bond.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 *@author $Govind: Sahu $
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectInsertBondItemCommand extends AbstractCommand implements ICommonEventConstant {
	
	

	private IBondFeedProxy bondFeedProxy;


	/**
	 * Default Constructor
	 */
	public CheckerRejectInsertBondItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				 {"bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue",SERVICE_SCOPE },
				 {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
		
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE }
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		 HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        try {
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            IBondFeedGroupTrxValue trxValueIn = (OBBondFeedGroupTrxValue) map.get("bondFeedGroupTrxValue");
	            String event = (String) map.get("event");
	            String remarks = (String) map.get("remarks");
	            ctx.setRemarks(remarks);
	            
	            IBondFeedGroupTrxValue trxValueOut = getBondFeedProxy().checkerRejectInsertBondFeedEntry(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            
	        }catch (BondFeedGroupException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

	/**
	 * @return the bondFeedProxy
	 */
	public IBondFeedProxy getBondFeedProxy() {
		return bondFeedProxy;
	}

	/**
	 * @param bondFeedProxy the bondFeedProxy to set
	 */
	public void setBondFeedProxy(IBondFeedProxy bondFeedProxy) {
		this.bondFeedProxy = bondFeedProxy;
	}



}
