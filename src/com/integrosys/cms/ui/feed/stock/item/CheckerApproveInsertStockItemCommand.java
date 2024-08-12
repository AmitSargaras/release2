package com.integrosys.cms.ui.feed.stock.item;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.OBStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertStockItemCommand extends AbstractCommand implements ICommonEventConstant {


	private IStockFeedProxy stockFeedProxy;


	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertStockItemCommand() {
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
				{"stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",SERVICE_SCOPE },
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
			// Credit Approval Trx value
			IStockFeedGroupTrxValue trxValueIn = (OBStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			// Function  to approve updated StockFeedEntry Trx
			IStockFeedGroupTrxValue trxValueOut = getStockFeedProxy().checkerApproveInsertStockFeedEntry(ctx, trxValueIn);
			
			//--------------getList--------------------

	        
			List listId = getStockFeedProxy().getFileMasterList(trxValueOut.getTransactionID());
			ICreditApprovalTrxValue trxValue = null;
			OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
			IStockFeedEntry stockFeedEntry = null;
			String currencyCode = "";
			String exchangeRate ="";
			String [][]codeRate = null;
			List refStockFeedEntryList = null;
			for (int i = 0; i < listId.size(); i++) {
				obFileMapperMaster = (OBFileMapperMaster) listId.get(i);
				 String regStage = String.valueOf(obFileMapperMaster.getSysId());
				 refStockFeedEntryList = getStockFeedProxy().insertActualStockFeedEntry(regStage);			
    		}
			getStockFeedProxy().updateStockFeedEntryItem(refStockFeedEntryList);


			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (StockFeedGroupException ex) {
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

	/**
	 * @return the stockFeedProxy
	 */
	public IStockFeedProxy getStockFeedProxy() {
		return stockFeedProxy;
	}

	/**
	 * @param stockFeedProxy the stockFeedProxy to set
	 */
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}


}



