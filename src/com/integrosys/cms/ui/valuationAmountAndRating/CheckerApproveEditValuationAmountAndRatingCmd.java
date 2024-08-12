package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.HashMap;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.proxy.IValuationAmountAndRatingProxyManager;
import com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditValuationAmountAndRatingCmd extends AbstractCommand implements ICommonEventConstant {

	private IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy;

	public IValuationAmountAndRatingProxyManager getValuationAmountAndRatingProxy() {
		return valuationAmountAndRatingProxy;
	}

	public void setValuationAmountAndRatingProxy(IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy) {
		this.valuationAmountAndRatingProxy = valuationAmountAndRatingProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditValuationAmountAndRatingCmd() {
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
				{"IValuationAmountAndRatingTrxValue", "com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
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
			// ValuationAmountAndRating Trx value
			
			String event = (String) map.get("event");
			
			IValuationAmountAndRatingTrxValue trxValueIn = (OBValuationAmountAndRatingTrxValue) map.get("IValuationAmountAndRatingTrxValue");
//			String productCode = (String) map.get("productCode");
//			String productName = (String) map.get("productName");
			/*boolean isExcludedFacilityDescriptionUnique = false;
			if(trxValueIn.getFromState().equals("ND") || trxValueIn.getStatus().equals("PENDING_CREATE")){
				isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
				if(isExcludedFacilityDescriptionUnique != false){
					exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}else if(trxValueIn.getFromState().equals("ACTIVE") && trxValueIn.getStatus().equals("PENDING_UPDATE")){
				if(null!=excludedFacilityDescription){
				if(!(excludedFacilityDescription.equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
				isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
				if(isExcludedFacilityDescriptionUnique != false){
					exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
						}
					}
				}
			}else if(trxValueIn.getFromState().equals("DRAFT") && trxValueIn.getStatus().equals("PENDING_UPDATE")){
				if(null!=excludedFacilityDescription){
					if(!(excludedFacilityDescription.equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
					isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
					if(isExcludedFacilityDescriptionUnique != false){
						exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
							}
						}
					}
				}*/
			
			OBValuationAmountAndRating st = (OBValuationAmountAndRating) trxValueIn.getStagingValuationAmountAndRating();
	        String vals = st.getValuationAmount();
	        vals = UIUtil.removeComma(vals);
	        vals = vals.replace(".00", "");
//			obItem.setValuationAmount(form.getValuationAmount());
	        st.setValuationAmount(vals);
			
			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated ValuationAmountAndRating Trx
			IValuationAmountAndRatingTrxValue trxValueOut = getValuationAmountAndRatingProxy().checkerApproveValuationAmountAndRating(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (ValuationAmountAndRatingException ex) {
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
