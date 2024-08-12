package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCreateCityCommand extends AbstractCommand {

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "cityObj", "com.integrosys.cms.app.geography.city.bus.ICity",FORM_SCOPE },
				{ "ICityTrxValue","com.integrosys.cms.app.geography.city.trx.ICityTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "cityCode", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "cityObj", "com.integrosys.cms.app.geography.city.bus.ICity",REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of City is done.
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

		DefaultLogger.debug(this, "============ MakerCreateCityCommand ()");
		String event = (String) map.get("event");
		ICity city = (ICity) map.get("cityObj");
		ICityTrxValue trxValueIn = (OBCityTrxValue) map.get("ICityTrxValue");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICityTrxValue trxValueOut = new OBCityTrxValue();

		try {			
			boolean validateCode = false;
			if ( trxValueIn == null  )
				validateCode = true;
			
			else if( trxValueIn.getFromState().equals("PENDING_PERFECTION") )
				validateCode = true;
					
			String newCityName = city.getCityName();			
			String oldCityName = "";
			
			long newStateId = city.getStateId().getIdState();
			long oldStateId ;

			boolean isCityCodeUnique = false;
			boolean isCityNameUnique = false;
			
			if ( validateCode ) {			
				String cityCode = (String)map.get("cityCode");				
				
				if( cityCode!=null )
					isCityCodeUnique = getCityProxy().isCityCodeUnique(cityCode.trim());
	
				if( isCityCodeUnique != false )
					exceptionMap.put("cityCodeError", new ActionMessage("error.string.exist","City Code"));
				
				if( newCityName != null )
					isCityNameUnique = getCityProxy().isCityNameUnique(newCityName.trim(),newStateId);
	
				if( isCityNameUnique != false )
					exceptionMap.put("cityNameError", new ActionMessage("error.string.exist","City Name"));
				
				if ( isCityCodeUnique || isCityNameUnique ) {
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
					return returnMap;
				}
			}
			if (event.equals("maker_save_city")) {
				trxValueOut = getCityProxy().makerSaveCity(ctx, city);
				resultMap.put("cityObj", city);
			} else if (event.equals("maker_create_city")) {
				trxValueOut = getCityProxy().makerCreateCity(ctx, trxValueOut,city); // City need not to be sent
				resultMap.put("cityObj", city);
			} else if (trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
				trxValueOut = getCityProxy().makerCreateCity(ctx, trxValueIn,city);
			} else if (event.equals("maker_create_saved_city") || event.equals("maker_edit_save_created_city")){
				
				if( event.equals("maker_edit_save_created_state") ){ 	/* Maker Edit, Saved it */
					oldCityName = trxValueIn.getActualCity().getCityName();
					oldStateId = trxValueIn.getActualCity().getStateId().getIdState();
				}
				else{	/* Maker Edit, Saved it. Maker ToDo Submit */
					oldCityName = trxValueIn.getStagingCity().getCityName();
					oldStateId = trxValueIn.getActualCity().getStateId().getIdState();
				}
				
				if(  ( ! newCityName.equals(oldCityName) ) ||  oldStateId != newStateId )
					isCityNameUnique = getCityProxy().isCityNameUnique(newCityName.trim(),newStateId);
	
				if( isCityNameUnique != false ){
					exceptionMap.put("cityNameError", new ActionMessage("error.string.exist","City Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
					
				trxValueOut = getCityProxy().makerUpdateSaveUpdateCity(ctx,trxValueIn, city);
			}
			else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getCityProxy().makerEditRejectedCity(ctx,trxValueIn, city);
			}
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		resultMap.put("request.ITrxValue", trxValueOut);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}