package com.integrosys.cms.ui.cersaiMapping;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingJdbcImpl;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerRejectEditCersaiMappingCmd extends AbstractCommand implements ICommonEventConstant {

	private ICersaiMappingProxyManager cersaiMappingProxy;

	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMappingProxy;
	}

	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMappingProxy) {
		this.cersaiMappingProxy = cersaiMappingProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public CheckerRejectEditCersaiMappingCmd() {
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
				{"ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue", SERVICE_SCOPE},
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
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
				{"masterName", "java.lang.String", SERVICE_SCOPE},
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
				{"masterName", "java.lang.String", REQUEST_SCOPE}
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
		ICersaiMappingTrxValue trxValueIn = (OBCersaiMappingTrxValue) map.get("ICersaiMappingTrxValue");
		String stagingRefId=trxValueIn.getStagingReferenceID();
		CersaiMappingJdbcImpl cersaiMappingJdbc=(CersaiMappingJdbcImpl)BeanHouse.get("cersaiMappingJdbc");
		ICersaiMapping[] masterValueList =cersaiMappingJdbc.fetchValueList(stagingRefId);
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		OBCersaiMapping ob=(OBCersaiMapping) trxValueIn.getStagingCersaiMapping();
		String masterName=(String)ob.getMasterName();
		if(ob.getMasterValueList() == null) {
			ob.setMasterValueList(masterValueList);
		}
		if(masterName==null) {
			try {
				masterName=cersaiMappingJdbc.getMasterName(stagingRefId);
				ob.setMasterName(masterName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		if(remarks == null||remarks.trim().equals("")){
			exceptionMap.put("facilityRemarksError", new ActionMessage("error.reject.remark"));
			resultMap.put("trxId", trxValueIn.getTransactionID());
			resultMap.put("masterValueList", masterValueList);
			resultMap.put("masterName", masterName);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}else {
			try {
				ctx.setRemarks(remarks);
			    ICersaiMappingTrxValue trxValueOut = getCersaiMappingProxy().checkerRejectCersaiMapping(ctx, trxValueIn);
			    resultMap.put("masterValueList", masterValueList);
			    resultMap.put("request.ITrxValue", trxValueOut);
			}catch (CersaiMappingException ex) {
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
