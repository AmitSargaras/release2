package com.integrosys.cms.ui.creditApproval;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {


	private ICreditApprovalProxy creditApprovalProxy;




	/**
	 * @return the creditApprovalProxy
	 */
	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertCreditApprovalCommand() {
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
				{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE },
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
			ICreditApprovalTrxValue trxValueIn = (OBCreditApprovalTrxValue) map.get("creditApprovalTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated holiday Trx
			ICreditApprovalTrxValue trxValueOut = getCreditApprovalProxy().checkerApproveInsertCreditApproval(ctx, trxValueIn);
			
			//--------------getList--------------------
			String tempTrxValue = trxValueOut.getCurrentTrxHistoryID();
	        
	        
			List listId = getCreditApprovalProxy().getFileMasterList(trxValueOut.getTransactionID());
			ICreditApprovalTrxValue trxValue = null;
			OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
			for (int i = 0; i < listId.size(); i++) {
    			//HashMap mapList = (HashMap) listId.get(i);
				obFileMapperMaster = (OBFileMapperMaster) listId.get(i);
    			// String regStage = mapList.get("SYS_ID").toString();
				 String regStage = String.valueOf(obFileMapperMaster.getSysId());
    			 ICreditApproval refCreditApproval = getCreditApprovalProxy().insertActualCreditApproval(regStage);
    			 
    			 trxValue = getCreditApprovalProxy().checkerCreateCreditApproval(ctx, refCreditApproval, regStage);
    			     				
    		}			
			trxValue.setCurrentTrxHistoryID(tempTrxValue);

			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (CreditApprovalException ex) {
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



