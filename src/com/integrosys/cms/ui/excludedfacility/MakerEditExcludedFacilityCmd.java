package com.integrosys.cms.ui.excludedfacility;

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
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditExcludedFacilityCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerEditExcludedFacilityCmd() {
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
				{"excludedfacilityCategory", "java.lang.String", REQUEST_SCOPE},
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
			OBExcludedFacility excludedFacility = (OBExcludedFacility) map.get("excludedFacilityObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IExcludedFacilityTrxValue trxValueIn = (OBExcludedFacilityTrxValue) map.get("IExcludedFacilityTrxValue");

			String excludedFacilityCategory = (String) map.get("excludedfacilityCategory");
			boolean isExcludedFacilityCategoryUnique = false;

			

			IExcludedFacilityTrxValue trxValueOut = new OBExcludedFacilityTrxValue();
			
			//Added by Uma:While editing restrict the duplicate entries.
			if( event.equals("maker_edit_excluded_facility_category") ){
				if(null!=excludedFacility){
					if(!(excludedFacility.getExcludedFacilityDescription().equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
				isExcludedFacilityCategoryUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacility.getExcludedFacilityDescription());				
				if(isExcludedFacilityCategoryUnique != false){
					exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
					}
				}
			}
			}
			else if (event.equals("maker_save_update")) {
				if (null != excludedFacility) {
					if (trxValueIn.getExcludedFacility() != null) {
						if (!(excludedFacility.getExcludedFacilityDescription()
								.equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))) {
							isExcludedFacilityCategoryUnique = getExcludedFacilityProxy()
									.isExcludedFacilityDescriptionUnique(
											excludedFacility.getExcludedFacilityDescription());
							if (isExcludedFacilityCategoryUnique != false) {
								exceptionMap.put("excludedFacilityDescriptionError",
										new ActionMessage("error.string.exist", "This Facility "));
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								return returnMap;
							}
						}
					}
				}
			}
			
			
			
			if (trxValueIn.getFromState().equals("PENDING_PERFECTION")) {

				
					/*isExcludedFacilityCategoryUnique = getExcludedFacilityProxy().isExcludedFacilityCategoryUnique(excludedFacilityCategory);				
					if(isExcludedFacilityCategoryUnique != false){
						exceptionMap.put("excludedFacilityCategoryError", new ActionMessage("error.string.exist","This Facility Category"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}*/
				

				
				trxValueOut = getExcludedFacilityProxy().makerUpdateSaveCreateExcludedFacility(ctx, trxValueIn,
						excludedFacility);
			} else {
				if ((event.equals("maker_edit_excluded_facility_category")) || event.equals("maker_delete_excluded_facility_category")
						|| event.equals("maker_save_update")) {

					trxValueOut = getExcludedFacilityProxy().makerUpdateExcludedFacility(ctx, trxValueIn,
							excludedFacility);
				} else {
					// event is maker_confirm_resubmit_edit
					String remarks = (String) map.get("remarks");
					ctx.setRemarks(remarks);
					trxValueOut = getExcludedFacilityProxy().makerEditRejectedExcludedFacility(ctx, trxValueIn,
							excludedFacility);
				}
			}
			resultMap.put("request.ITrxValue", trxValueOut);

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
