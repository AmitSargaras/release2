package com.integrosys.cms.ui.feed.bond.item;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertBondItemCommand extends AbstractCommand implements ICommonEventConstant {


	private IBondFeedProxy bondFeedProxy;


	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertBondItemCommand() {
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
				{"bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue",SERVICE_SCOPE },
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
			IBondFeedGroupTrxValue trxValueIn = (OBBondFeedGroupTrxValue) map.get("bondFeedGroupTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			// Function  to approve updated BondFeedEntry Trx
			IBondFeedGroupTrxValue trxValueOut = getBondFeedProxy().checkerApproveInsertBondFeedEntry(ctx, trxValueIn);
			
			//--------------getList--------------------

	        
			List listId = getBondFeedProxy().getFileMasterList(trxValueOut.getTransactionID());
			ICreditApprovalTrxValue trxValue = null;
			OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
			IBondFeedEntry bondFeedEntry = null;
			String currencyCode = "";
			String exchangeRate ="";
			String [][]codeRate = null;
			List refBondFeedEntryList = null;
			for (int i = 0; i < listId.size(); i++) {
				obFileMapperMaster = (OBFileMapperMaster) listId.get(i);
				 String regStage = String.valueOf(obFileMapperMaster.getSysId());
				 refBondFeedEntryList = getBondFeedProxy().insertActualBondFeedEntry(regStage);			
    		}
			getBondFeedProxy().updateBondFeedEntryItem(refBondFeedEntryList);


			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (BondFeedGroupException ex) {
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



