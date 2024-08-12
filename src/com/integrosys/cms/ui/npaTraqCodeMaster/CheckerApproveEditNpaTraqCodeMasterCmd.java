package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.npaTraqCodeMaster.proxy.INpaTraqCodeMasterProxyManager;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.OBNpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditNpaTraqCodeMasterCmd extends AbstractCommand implements ICommonEventConstant {

	private INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy;
	
	public INpaTraqCodeMasterProxyManager getNpaTraqCodeMasterProxy() {
		return npaTraqCodeMasterProxy;
	}

	public void setNpaTraqCodeMasterProxy(INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy) {
		this.npaTraqCodeMasterProxy = npaTraqCodeMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditNpaTraqCodeMasterCmd() {
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
				{"INpaTraqCodeMasterTrxValue", "com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"npaTraqCode", "java.lang.String", REQUEST_SCOPE},
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
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// NpaTraqCodeMaster Trx value
			
			String event = (String) map.get("event");
			
			INpaTraqCodeMasterTrxValue trxValueIn = (OBNpaTraqCodeMasterTrxValue) map.get("INpaTraqCodeMasterTrxValue");
			String productCode = (String) map.get("npaTraqCode");
			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			boolean isNpaTraqCodeUnique = false;
			String securityType=trxValueIn.getStagingNpaTraqCodeMaster().getSecurityType();
			String securitySubType=trxValueIn.getStagingNpaTraqCodeMaster().getSecuritySubType();
			String propertyTypeDesc=trxValueIn.getStagingNpaTraqCodeMaster().getPropertyTypeCodeDesc();
			
			isNpaTraqCodeUnique = getNpaTraqCodeMasterProxy().isNpaTraqCodeUniqueJdbc(securityType,securitySubType,propertyTypeDesc);				
			if(isNpaTraqCodeUnique != false && !trxValueIn.getStatus().equals("PENDING_DELETE")&& !trxValueIn.getStatus().equals("PENDING_UPDATE")){
				if("PT".equals(securityType)) {
					exceptionMap.put("propertyTypeCodeDescError", new ActionMessage("error.string.exist","This Property Type Code Description "));
				}else {
					exceptionMap.put("npaTraqCodeError", new ActionMessage("error.string.exist","This NPA TRAQ Code, Security Type, Security Sub-Type "));
				}
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			// Function  to approve updated NpaTraqCodeMaster Trx
			INpaTraqCodeMasterTrxValue trxValueOut = getNpaTraqCodeMasterProxy().checkerApproveNpaTraqCodeMaster(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (NpaTraqCodeMasterException ex) {
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
