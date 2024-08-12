package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerApproveCityCommmand extends AbstractCommand implements
		ICommonEventConstant {

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public CheckerApproveCityCommmand() {
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
				{ "ICityTrxValue","com.integrosys.cms.app.geography.city.trx.ICityTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE } 
			});
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
			DefaultLogger.debug(this," doExecute : CheckerApproveCityCommmand ");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICityTrxValue trxValueIn = (OBCityTrxValue) map.get("ICityTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Start 
			if ( trxValueIn.getFromState().equals("DRAFT") || trxValueIn.getFromState().equals("ND") ) {				
				if ( ! trxValueIn.getStatus().equals("PENDING_UPDATE") ) {
					boolean isCityCodeUnique = false;						
					boolean isCityNameUnique = false;
					
					String cityCode = trxValueIn.getStagingCity().getCityCode();
					String cityName = trxValueIn.getStagingCity().getCityName();					
					long stateId = trxValueIn.getStagingCity().getStateId().getIdState();
					
					if( cityCode != null  )
						isCityCodeUnique = getCityProxy().isCityCodeUnique(cityCode.trim());
		
					if( isCityCodeUnique != false)
						exceptionMap.put("duplicateCityCodeError", new ActionMessage("error.string.exist","City Code"));
					
					if( cityName != null )
						isCityNameUnique = getCityProxy().isCityNameUnique(cityName.trim(),stateId);			
						
					if ( isCityNameUnique != false ) 
						exceptionMap.put("duplicateCityNameError",new ActionMessage("error.string.exist", "City Name"));
					
					if ( isCityCodeUnique || isCityNameUnique ) {
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
						return returnMap;
					}
				}
			}
			// End
			
			ICityTrxValue trxValueOut = getCityProxy().checkerApproveCity(ctx,trxValueIn);
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