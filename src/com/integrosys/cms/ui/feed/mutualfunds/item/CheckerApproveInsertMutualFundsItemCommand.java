package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.mutualfunds.OBMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertMutualFundsItemCommand extends AbstractCommand implements ICommonEventConstant {


	private IMutualFundsFeedProxy mutualFundsFeedProxy;


	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertMutualFundsItemCommand() {
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
			IMutualFundsFeedGroupTrxValue trxValueIn = (OBMutualFundsFeedGroupTrxValue) map.get("mutualfundsFeedGroupTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			// Function  to approve updated MutualFundsFeedEntry Trx
			IMutualFundsFeedGroupTrxValue trxValueOut = getMutualFundsFeedProxy().checkerApproveInsertMutualFundsFeedEntry(ctx, trxValueIn);
			
			//--------------getList--------------------

	        
			List listId = getMutualFundsFeedProxy().getFileMasterList(trxValueOut.getTransactionID());
			ICreditApprovalTrxValue trxValue = null;
			OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
			IMutualFundsFeedEntry mutualfundsFeedEntry = null;
			String currencyCode = "";
			String exchangeRate ="";
			String [][]codeRate = null;
			List refMutualFundsFeedEntryList = null;
			for (int i = 0; i < listId.size(); i++) {
				obFileMapperMaster = (OBFileMapperMaster) listId.get(i);
				 String regStage = String.valueOf(obFileMapperMaster.getSysId());
				 refMutualFundsFeedEntryList = getMutualFundsFeedProxy().insertActualMutualFundsFeedEntry(regStage);			
    		}
			getMutualFundsFeedProxy().updateMutualFundsFeedEntryItem(refMutualFundsFeedEntryList);


			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (MutualFundsFeedGroupException ex) {
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



