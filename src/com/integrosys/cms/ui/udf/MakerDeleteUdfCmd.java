package com.integrosys.cms.ui.udf;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UDFConstants;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteUdfCmd extends AbstractCommand implements ICommonEventConstant {

	private IUdfProxyManager udfProxyManager;

	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerDeleteUdfCmd() {
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
				{"IUdfTrxValue", "com.integrosys.cms.app.udf.trx.IUdfTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "UdfObj", "com.integrosys.cms.app.udf.bus.OBUdf", FORM_SCOPE },
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
		IUdfTrxValue trxValueIn = (OBUdfTrxValue) map.get("IUdfTrxValue");
		OBUdf udf = (OBUdf) map.get("UdfObj");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		IUdfTrxValue trxValueOut = new OBUdfTrxValue();
		try {
			
			if (event.equals("maker_activate_udf")) {
				trxValueOut = getUdfProxyManager().makerActivateUdf(ctx,trxValueIn, udf);
				resultMap.put("request.ITrxValue", trxValueOut);
			} else if (event.equals("maker_delete_udf")) {
				trxValueOut = getUdfProxyManager().makerDeleteUdf(ctx,trxValueIn, udf);
				resultMap.put("request.ITrxValue", trxValueOut);
			} else {
		if( event.equals("maker_confirm_resubmit_delete") ){
			if(null==trxValueIn.getReferenceID()) {

			List seqList = getUdfProxyManager().getUdfSequencesByModuleId(Long.toString((udf.getModuleId())));
			
			int maxUdfLimit= UDFConstants.UDF_MAXLIMIT;
			if(udf.getModuleId()==3 ) {
				 maxUdfLimit= UDFConstants.UDF_MAXLIMIT_LIMITMODULE;
			}
			System.out.println("maxUdfLimit:: "+maxUdfLimit + " seqList.size():: "+seqList.size());
		  
			if (seqList != null && seqList.size() == maxUdfLimit) {
				exceptionMap.put("udfError", new ActionMessage("udf.maxLimitExceeded"));
			}
			else if (seqList != null && seqList.contains(new Integer(udf.getSequence()))) {
				exceptionMap.put("udfError", new ActionMessage("udf.duplicateUdfNo"));
			}
			
			else {
		
			
			ctx.setRemarks(remarks);
			trxValueOut = getUdfProxyManager().makerEditRejectedUdf(ctx, trxValueIn,udf);
			resultMap.put("request.ITrxValue", trxValueOut);
			}}
			else {

				ctx.setRemarks(remarks);
				trxValueOut = getUdfProxyManager().makerEditRejectedUdf(ctx, trxValueIn,udf);
				resultMap.put("request.ITrxValue", trxValueOut);
		}
		
		}}} catch (UdfException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
			} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
			} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
				}
	
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
