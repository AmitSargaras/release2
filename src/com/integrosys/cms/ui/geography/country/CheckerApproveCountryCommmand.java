package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveCountryCommmand extends AbstractCommand implements
		ICommonEventConstant {

	private ICountryProxyManager countryProxy;

	public ICountryProxyManager getCountryProxy() {
		return countryProxy;
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

	public CheckerApproveCountryCommmand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"ICountryTrxValue",
						"com.integrosys.cms.app.geography.country.trx.ICountryTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICountryTrxValue trxValueIn = (OBCountryTrxValue) map
					.get("ICountryTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Start 
			if ( trxValueIn.getFromState().equals("DRAFT") || trxValueIn.getFromState().equals("ND") ) {				
				if (!trxValueIn.getStatus().equals("PENDING_UPDATE")) {
				
				boolean isCountryCodeUnique = false;
				boolean isCountryNameUnique = false;
				
				String countryCode = trxValueIn.getStagingCountry().getCountryCode();
				String countryName = trxValueIn.getStagingCountry().getCountryName();
				
				if(countryCode!=null)
					isCountryCodeUnique = getCountryProxy().isCountryCodeUnique(countryCode.trim());
	
				if(isCountryCodeUnique != false){
					exceptionMap.put("duplicateCountryCodeError", new ActionMessage("error.string.exist","Country Code"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
					}
				
				if(countryName!=null)
					isCountryNameUnique = getCountryProxy().isCountryNameUnique(countryName.trim());
	
				if(isCountryNameUnique != false){
					exceptionMap.put("duplicateCountryNameError", new ActionMessage("error.string.exist","Country Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
					}
				}
			}
			// End
			ICountryTrxValue trxValueOut = getCountryProxy().checkerApproveCountry(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (NoSuchGeographyException ex) {
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
