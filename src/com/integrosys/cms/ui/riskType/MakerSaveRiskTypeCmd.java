package com.integrosys.cms.ui.riskType;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeDaoImpl;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerSaveRiskTypeCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerSaveRiskTypeCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "IRiskTypeTrxValue", "com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "riskTypeObj", "com.integrosys.cms.app.riskType.bus.OBRiskType", FORM_SCOPE }

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 *
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			String event = (String) map.get("event");
			OBRiskType riskType = (OBRiskType) map.get("riskTypeObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IRiskTypeTrxValue trxValueIn = (OBRiskTypeTrxValue) map.get("IRiskTypeTrxValue");
			IRiskTypeTrxValue trxValueOut = new OBRiskTypeTrxValue();

		//	boolean isProductCodeUnique = false;
//			String productCode=riskType.getProductCode(); 
//			isProductCodeUnique = getRiskTypeProxy().isProductCodeUnique(productCode);	
//			if (event.equals("maker_update_draft_valuation_amount_and_rating")) { 
//			String productCodeActual=trxValueIn.getStagingRiskType().getProductCode();
//				if(isProductCodeUnique != false){
//					exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
//					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//					return returnMap;
//				}
//			}
			if (event.equals("maker_update_draft_risk_type")) {
				
				trxValueOut = getRiskTypeProxy().makerUpdateSaveUpdateRiskType(ctx, trxValueIn,riskType);
				resultMap.put("request.ITrxValue", trxValueOut);
			} else {
				RiskTypeDaoImpl dao = new RiskTypeDaoImpl();
				String risktypecode=dao.getRiskTypeCode();
				riskType.setRiskTypeCode(risktypecode);
				trxValueOut = getRiskTypeProxy().makerSaveRiskType(ctx, riskType);
				resultMap.put("request.ITrxValue", trxValueOut);
			}
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
