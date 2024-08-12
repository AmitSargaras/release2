package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.proxy.ICollateralRocProxyManager;
import com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue;
import com.integrosys.cms.app.collateralrocandinsurance.trx.OBCollateralRocTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCreateCollateralRocCmd extends AbstractCommand implements ICommonEventConstant {

	private ICollateralRocProxyManager collateralRocProxy;

	public ICollateralRocProxyManager getCollateralRocProxy() {
		return collateralRocProxy;
	}

	public void setCollateralRocProxy(ICollateralRocProxyManager collateralRocProxy) {
		this.collateralRocProxy = collateralRocProxy;
	}

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCollateralRocCmd() {
		super();
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
				{ "ICollateralRocTrxValue",
						"com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralCategory", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocCode", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocActualCode", "java.lang.String", FORM_SCOPE },
				//{ "collateralRocDescription", "java.lang.String", REQUEST_SCOPE },
				//{ "irbCategory", "java.lang.String", REQUEST_SCOPE },
				//{ "insuranceApplicable", "java.lang.String", REQUEST_SCOPE }, 
				{ "collateralRocObj","com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE }

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
		boolean validCode = false;
		String event = (String) map.get("event");
		
		String collateralCategory = (String) map.get("collateralCategory");
		String collateralRocCode = (String) map.get("collateralRocCode");
		boolean isCollateralRocCategoryUnique = false;

		OBCollateralRoc collateralRoc = (OBCollateralRoc) map.get("collateralRocObj");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		
		if (event.equals("maker_create_collateral_roc")) {
			isCollateralRocCategoryUnique = getCollateralRocProxy()
					.isCollateralRocCategoryUnique(collateralCategory,collateralRocCode);
			if (isCollateralRocCategoryUnique != false) {
				exceptionMap.put("collateralRocCategoryError",
						new ActionMessage("error.string.exist", "This Collateral Roc & Insurance Mapping "));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}
		try {
			ICollateralRocTrxValue trxValueOut = new OBCollateralRocTrxValue();
			trxValueOut = getCollateralRocProxy().makerCreateCollateralRoc(ctx, collateralRoc);

			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (CollateralRocException ex) {
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
		// }

	}
}
