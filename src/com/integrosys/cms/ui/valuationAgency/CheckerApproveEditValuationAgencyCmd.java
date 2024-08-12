package com.integrosys.cms.ui.valuationAgency;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

/**
 * @author rajib.aich  For Valuation Agency
 * Command for checker to approve edit .
 */

public class CheckerApproveEditValuationAgencyCmd extends AbstractCommand implements ICommonEventConstant {

	private IValuationAgencyProxyManager valuationAgencyProxy;

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditValuationAgencyCmd() {
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
				{"IValuationAgencyTrxValue", "com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IValuationAgencyTrxValue trxValueIn = (OBValuationAgencyTrxValue) map.get("IValuationAgencyTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			String oldValuationName = "";			
			boolean isValuationNameUnique = false;
			
			if( trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE") ){
				oldValuationName = trxValueIn.getStagingValuationAgency().getValuationAgencyName();
				isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(oldValuationName.trim());
				
				if( isValuationNameUnique ){
					exceptionMap.put("valuationAgencyNameError", new ActionMessage("error.string.exist","ValuationAgency Name"));
					IValuationAgencyTrxValue valuationAgencyTrxValue = null;
					resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;		
				}
			}
			
			IValuationAgencyTrxValue trxValueOut = getValuationAgencyProxy().checkerApproveValuationAgency(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (ValuationAgencyException ex) {
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