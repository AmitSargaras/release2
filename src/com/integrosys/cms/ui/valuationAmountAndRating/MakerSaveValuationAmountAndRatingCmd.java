package com.integrosys.cms.ui.valuationAmountAndRating;

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
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.proxy.IValuationAmountAndRatingProxyManager;
import com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerSaveValuationAmountAndRatingCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerSaveValuationAmountAndRatingCmd() {
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
				{ "IValuationAmountAndRatingTrxValue", "com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "valuationAmountAndRatingObj", "com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE }

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
			OBValuationAmountAndRating valuationAmountAndRating = (OBValuationAmountAndRating) map.get("valuationAmountAndRatingObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IValuationAmountAndRatingTrxValue trxValueIn = (OBValuationAmountAndRatingTrxValue) map.get("IValuationAmountAndRatingTrxValue");
			IValuationAmountAndRatingTrxValue trxValueOut = new OBValuationAmountAndRatingTrxValue();

			boolean isProductCodeUnique = false;
//			String productCode=valuationAmountAndRating.getProductCode(); 
//			isProductCodeUnique = getValuationAmountAndRatingProxy().isProductCodeUnique(productCode);	
//			if (event.equals("maker_update_draft_valuation_amount_and_rating")) { 
//			String productCodeActual=trxValueIn.getStagingValuationAmountAndRating().getProductCode();
//				if(isProductCodeUnique != false){
//					exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
//					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//					return returnMap;
//				}
//			}
			if (event.equals("maker_update_draft_valuation_amount_and_rating")) { 
				trxValueOut = getValuationAmountAndRatingProxy().makerUpdateSaveUpdateValuationAmountAndRating(ctx, trxValueIn,valuationAmountAndRating);
				resultMap.put("request.ITrxValue", trxValueOut);
			} else {
				trxValueOut = getValuationAmountAndRatingProxy().makerSaveValuationAmountAndRating(ctx, valuationAmountAndRating);
				resultMap.put("request.ITrxValue", trxValueOut);
			}
		} catch (ValuationAmountAndRatingException ex) {
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
