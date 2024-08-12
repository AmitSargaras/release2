package com.integrosys.cms.ui.goodsMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerRejectEditGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant {

	private IGoodsMasterProxyManager goodsMasterProxy;

	public IGoodsMasterProxyManager getGoodsMasterProxy() {
		return goodsMasterProxy;
	}

	public void setGoodsMasterProxy(IGoodsMasterProxyManager goodsMasterProxy) {
		this.goodsMasterProxy = goodsMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public CheckerRejectEditGoodsMasterCmd() {
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
				{"IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		});
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
		});
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
		HashMap exceptionMap = new HashMap();
		
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IGoodsMasterTrxValue trxValueIn = (OBGoodsMasterTrxValue) map.get("IgoodsMasterTrxValue");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
			
		if(remarks == null||remarks.trim().equals("")){
			exceptionMap.put("facilityRemarksError", new ActionMessage("error.reject.remark"));
			resultMap.put("trxId", trxValueIn.getTransactionID());
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}else {
			try {
				ctx.setRemarks(remarks);
			    IGoodsMasterTrxValue trxValueOut = getGoodsMasterProxy().checkerRejectGoodsMaster(ctx, trxValueIn);
			    resultMap.put("request.ITrxValue", trxValueOut);
			}catch (GoodsMasterException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
