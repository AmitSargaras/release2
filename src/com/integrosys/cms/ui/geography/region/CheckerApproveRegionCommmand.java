package com.integrosys.cms.ui.geography.region;

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
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerApproveRegionCommmand extends AbstractCommand implements
		ICommonEventConstant {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	public CheckerApproveRegionCommmand() {
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
						"IRegionTrxValue",
						"com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",
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
			// Region Trx value
			IRegionTrxValue trxValueIn = (OBRegionTrxValue) map
					.get("IRegionTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);

			// Start 
			if ( trxValueIn.getFromState().equals("DRAFT") || trxValueIn.getFromState().equals("ND") ) {				
				if (!trxValueIn.getStatus().equals("PENDING_UPDATE")) {
					boolean isRegionCodeUnique = false;
					boolean isRegionNameUnique = false;
				
				String regionCode = trxValueIn.getStagingRegion().getRegionCode();
				String regionName = trxValueIn.getStagingRegion().getRegionName();
				long countryId = trxValueIn.getStagingRegion().getCountryId().getIdCountry();
				
				if(regionCode!=null)
					isRegionCodeUnique = getRegionProxy().isRegionCodeUnique(regionCode.trim());			
			
				if (isRegionCodeUnique != false) {
					exceptionMap.put("duplicateRegionyCodeError",new ActionMessage("error.string.exist", "Region Code"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
					return returnMap;
					}
				
				if(regionName!=null)
					isRegionNameUnique = getRegionProxy().isRegionNameUnique(regionName.trim(),countryId);			
			
				if (isRegionNameUnique != false) {
					exceptionMap.put("duplicateRegionyNameError",new ActionMessage("error.string.exist", "Region Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
					return returnMap;
					}
				}
			}
			IRegionTrxValue trxValueOut = getRegionProxy()
					.checkerApproveRegion(ctx, trxValueIn);
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
