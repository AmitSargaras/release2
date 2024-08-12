package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.forex.OBForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertExchangeRateItemCommand extends AbstractCommand implements ICommonEventConstant {


	private IForexFeedProxy forexFeedProxy;


	/**
	 * @return the forexFeedProxy
	 */
	public IForexFeedProxy getForexFeedProxy() {
		return forexFeedProxy;
	}

	/**
	 * @param forexFeedProxy the forexFeedProxy to set
	 */
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertExchangeRateItemCommand() {
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
				{"forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",SERVICE_SCOPE },
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		
			IForexFeedGroupTrxValue trxValueIn = (OBForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			// Function  to approve updated ForexFeedEntry Trx
			IForexFeedGroupTrxValue trxValueOut = getForexFeedProxy().checkerApproveInsertForexFeedEntry(ctx, trxValueIn);
			
			//--------------getList--------------------

	        
			List listId = getForexFeedProxy().getFileMasterList(trxValueOut.getTransactionID());
			IForexFeedGroupTrxValue trxValue = null;
			OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
			IForexFeedEntry forexFeedEntry = null;
			String currencyCode = "";
			String exchangeRate ="";
			String [][]codeRate = null;
			List refForexFeedEntryList = null;
			for (int i = 0; i < listId.size(); i++) {
				obFileMapperMaster = (OBFileMapperMaster) listId.get(i);
				 String regStage = String.valueOf(obFileMapperMaster.getSysId());
				 refForexFeedEntryList = getForexFeedProxy().insertActualForexFeedEntry(regStage);			
    		}
			getForexFeedProxy().updateForexFeedEntryExchangeRate(refForexFeedEntryList);


			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (ForexFeedGroupException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}



