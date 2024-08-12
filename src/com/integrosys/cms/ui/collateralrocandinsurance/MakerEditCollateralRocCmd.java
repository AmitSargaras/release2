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
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditCollateralRocCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerEditCollateralRocCmd() {
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
				{ "ICollateralRocTrxValue", "com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralCategory", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocCode", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocDescription", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocObj", "com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE }

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
			OBCollateralRoc collateralRoc = (OBCollateralRoc) map.get("collateralRocObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICollateralRocTrxValue trxValueIn = (OBCollateralRocTrxValue) map.get("ICollateralRocTrxValue");
			String collateralCategory = (String) map.get("collateralCategory");
			String collateralRocDescription = (String) map.get("collateralRocDescription");
			boolean isCollateralRocCategoryUnique = false;
			
			ICollateralRocTrxValue trxValueOut = new OBCollateralRocTrxValue();
			
			 if(trxValueIn.getFromState().equals("ND") || trxValueIn.getStatus().equals("PENDING_CREATE")){
				if(null!=collateralRoc){
					isCollateralRocCategoryUnique = getCollateralRocProxy()
							.isCollateralRocCategoryUnique(collateralCategory,collateralRocDescription);
					if (isCollateralRocCategoryUnique != false) {
						exceptionMap.put("collateralRocCategoryError",
								new ActionMessage("error.string.exist", "This Collateral Roc & Insurance Mapping"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
			}
			
			if (trxValueIn.getFromState().equals("PENDING_PERFECTION")) {

				
				isCollateralRocCategoryUnique = getCollateralRocProxy()
						.isCollateralRocCategoryUnique(collateralCategory,collateralRocDescription);
				if (isCollateralRocCategoryUnique != false) {
					exceptionMap.put("collateralRocCategoryError",
							new ActionMessage("error.string.exist", "This Collateral Roc & Insurance Mapping"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				

				
				trxValueOut = getCollateralRocProxy().makerUpdateSaveCreateCollateralRoc(ctx, trxValueIn,
						collateralRoc);
			} else {
				if ((event.equals("maker_edit_collateral_roc")) || event.equals("maker_delete_collateral_roc")
						|| event.equals("maker_save_update")) {

					trxValueOut = getCollateralRocProxy().makerUpdateCollateralRoc(ctx, trxValueIn,
							collateralRoc);
				} else {
					// event is maker_confirm_resubmit_edit
					String remarks = (String) map.get("remarks");
					ctx.setRemarks(remarks);
					trxValueOut = getCollateralRocProxy().makerEditRejectedCollateralRoc(ctx, trxValueIn,
							collateralRoc);
				}
			}
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
	}
}
