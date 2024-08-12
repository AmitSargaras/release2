package com.integrosys.cms.ui.newtatmaster;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.proxy.ITatmasterProxyManager;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditTatMasterCmd extends AbstractCommand implements ICommonEventConstant {

	private ITatmasterProxyManager tatMasterProxy;
		

	public ITatmasterProxyManager getTatMasterProxy() {
		return tatMasterProxy;
	}

	public void setTatMasterProxy(ITatmasterProxyManager tatMasterProxy) {
		this.tatMasterProxy = tatMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditTatMasterCmd() {
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
				{"ITatMasterTrxValue", "com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue", SERVICE_SCOPE},
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
			Date toDate=new Date();
			
			ITatMasterTrxValue trxValueIn = (OBTatMasterTrxValue) map.get("ITatMasterTrxValue");
			trxValueIn.getStagingtatMaster().setLastUpdatedBy(ctx.getUser().getLoginID());
			
			trxValueIn.getStagingtatMaster().setLastUpdatedOn(toDate);
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			ITatMasterTrxValue trxValueOut = getTatMasterProxy().checkerApproveTatMaster(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (TatMasterException ex) {
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
