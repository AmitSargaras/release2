package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.collateralrocandinsurance.proxy.ICollateralRocProxyManager;
import com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue;
import com.integrosys.cms.app.collateralrocandinsurance.trx.OBCollateralRocTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditCollateralRocCmd extends AbstractCommand implements ICommonEventConstant {

	private ICollateralRocProxyManager collateralRocProxy;

	public ICollateralRocProxyManager getCollateralRocProxy() {
		return collateralRocProxy;
	}

	public void setCollateralRocProxy(ICollateralRocProxyManager collateralRocProxy) {
		this.collateralRocProxy = collateralRocProxy;
	}
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditCollateralRocCmd() {
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
				{"ICollateralRocTrxValue", "com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{ "collateralCategory", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocDescription", "java.lang.String", REQUEST_SCOPE }
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
			// CollateralRoc Trx value
			
			String event = (String) map.get("event");
			
			ICollateralRocTrxValue trxValueIn = (OBCollateralRocTrxValue) map.get("ICollateralRocTrxValue");
			String collateralCategory = (String) map.get("collateralCategory");
			String collateralRocDescription = (String) map.get("collateralRocDescription");
			
			boolean isCollateralRocCategoryUnique = false;
			
			if(trxValueIn.getFromState().equals("ND") || trxValueIn.getStatus().equals("PENDING_CREATE")){
				isCollateralRocCategoryUnique = getCollateralRocProxy().isCollateralRocCategoryUnique(collateralCategory, collateralRocDescription);				
				if(isCollateralRocCategoryUnique != false){
					exceptionMap.put("collateralRocCategoryError", new ActionMessage("error.string.exist","This Collateral Roc & Insurance Mapping "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			ICollateralRocTrxValue trxValueOut = getCollateralRocProxy().checkerApproveCollateralRoc(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (CollateralRocException ex) {
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
