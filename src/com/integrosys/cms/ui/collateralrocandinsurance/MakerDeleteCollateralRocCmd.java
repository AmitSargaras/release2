package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.HashMap;


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

public class MakerDeleteCollateralRocCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerDeleteCollateralRocCmd() {
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
				{ "collateralRocObj", "com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE }

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
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
			OBCollateralRoc collateralRoc = (OBCollateralRoc) map.get("collateralRocObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICollateralRocTrxValue trxValueIn = (OBCollateralRocTrxValue) map.get("ICollateralRocTrxValue");

			ICollateralRocTrxValue trxValueOut = new OBCollateralRocTrxValue();
			
			/*boolean isExcludedFacilityCategoryUnique = false;
			
			if( event.equals("maker_confirm_resubmit_delete") ){
				
				if("PENDING_UPDATE".equals(trxValueIn.getFromState()) && "REJECTED".equals(trxValueIn.getStatus()) ){
					if(null!=collateralRoc ){
					if(!(collateralRoc.getExcludedFacilityDescription().equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
						isExcludedFacilityCategoryUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(collateralRoc.getExcludedFacilityDescription());				
					if(isExcludedFacilityCategoryUnique != false){
						exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
							}
						}
					}
				}
				else if("PENDING_CREATE".equals(trxValueIn.getFromState()) && "REJECTED".equals(trxValueIn.getStatus())){
					if(null!=collateralRoc){
					isExcludedFacilityCategoryUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(collateralRoc.getExcludedFacilityDescription());				
					if(isExcludedFacilityCategoryUnique != false){
						exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
						}
					}
				}
				
			}*/
			
			if (event.equals("maker_delete_collateral_roc")) {
				trxValueOut = getCollateralRocProxy().makerDeleteCollateralRoc(ctx, trxValueIn, collateralRoc);
			} else {
				// event is  maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				
				ctx.setRemarks(remarks);
				trxValueOut = getCollateralRocProxy().makerEditRejectedCollateralRoc(ctx, trxValueIn, collateralRoc);
			} 

			resultMap.put("request.ITrxValue", trxValueOut);
			
		}catch (CollateralRocException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
    }
}
