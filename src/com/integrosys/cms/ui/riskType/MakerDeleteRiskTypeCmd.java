package com.integrosys.cms.ui.riskType;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteRiskTypeCmd extends AbstractCommand implements ICommonEventConstant {

	private IRiskTypeProxyManager riskTypeProxy;

	public IRiskTypeProxyManager getRiskTypeProxy() {
		return riskTypeProxy;
	}

	public void setRiskTypeProxy(IRiskTypeProxyManager riskTypeProxy) {
		this.riskTypeProxy = riskTypeProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerDeleteRiskTypeCmd() {
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
				{"IRiskTypeTrxValue", "com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "riskTypeObj", "com.integrosys.cms.app.riskType.bus.OBRiskType", FORM_SCOPE },
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
		try{
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IRiskTypeTrxValue trxValueIn = (OBRiskTypeTrxValue) map.get("IRiskTypeTrxValue");
		OBRiskType riskType = (OBRiskType) map.get("riskTypeObj");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		IRiskTypeTrxValue trxValueOut = new OBRiskTypeTrxValue();

		//boolean isProductCodeUnique = false;
		
		if( event.equals("maker_confirm_resubmit_delete") ){
//			String productCode=riskType.getProductCode();
//			String productCodeActual=trxValueIn.getStagingRiskType().getProductCode();
//			if(! productCode.equals(productCodeActual)) {
//				isProductCodeUnique = getRiskTypeProxy().isProductCodeUnique(productCode);				
//				if(isProductCodeUnique != false){
//					exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
//					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//					return returnMap;
//				}
//			}
			ctx.setRemarks(remarks);
			trxValueOut = getRiskTypeProxy().makerEditRejectedRiskType(ctx, trxValueIn,riskType);
			//trxValueOut = getRiskTypeProxy().makerDeleteRiskType(ctx, trxValueIn,riskType);
		}
		
		resultMap.put("request.ITrxValue", trxValueOut);
			
	} catch (RiskTypeException ex) {
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
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;
}
}
