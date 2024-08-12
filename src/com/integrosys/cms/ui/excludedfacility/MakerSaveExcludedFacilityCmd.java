package com.integrosys.cms.ui.excludedfacility;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerSaveExcludedFacilityCmd extends AbstractCommand implements ICommonEventConstant {

	private IExcludedFacilityProxyManager excludedFacilityProxy;

	public IExcludedFacilityProxyManager getExcludedFacilityProxy() {
		return excludedFacilityProxy;
	}

	public void setExcludedFacilityProxy(IExcludedFacilityProxyManager excludedFacilityProxy) {
		this.excludedFacilityProxy = excludedFacilityProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerSaveExcludedFacilityCmd() {
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
				{ "IExcludedFacilityTrxValue", "com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "excludedFacilityObj", "com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility", FORM_SCOPE }

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
			OBExcludedFacility excludedFacility = (OBExcludedFacility) map.get("excludedFacilityObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IExcludedFacilityTrxValue trxValueIn = (OBExcludedFacilityTrxValue) map.get("IExcludedFacilityTrxValue");
			IExcludedFacilityTrxValue trxValueOut = new OBExcludedFacilityTrxValue();

			/*boolean isFacilityNameUnique = false;
			boolean isLineNoUnique = false;*/

			if (event.equals("maker_draft_excluded_facility_category")) { // create -
																	// save
				/*isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName);
				if (isFacilityNameUnique != false) {
					exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist", "Facility Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}*/

			}
			if (event.equals("maker_update_draft_excluded_facility_category")) { // edit-
																		// save

				/*String oldFacilityName = trxValueIn.getFacilityNewMaster().getNewFacilityName();
				String oldLinNo = trxValueIn.getStagingFacilityNewMaster().getLineNumber();

				if (!newFacilityName.equals(oldFacilityName))
					isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName.trim());

				if (isFacilityNameUnique != false) {
					exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist", "Facility Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}*/

				trxValueOut = getExcludedFacilityProxy().makerUpdateSaveUpdateExcludedFacility(ctx, trxValueIn,
						excludedFacility);

				resultMap.put("request.ITrxValue", trxValueOut);
			} else {

				trxValueOut = getExcludedFacilityProxy().makerSaveExcludedFacility(ctx, excludedFacility);

				resultMap.put("request.ITrxValue", trxValueOut);
			}
		} catch (ExcludedFacilityException ex) {
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
