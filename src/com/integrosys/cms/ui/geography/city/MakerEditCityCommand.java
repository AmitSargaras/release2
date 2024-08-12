package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerEditCityCommand extends AbstractCommand {

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public MakerEditCityCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "cityObj", "com.integrosys.cms.app.geography.city.bus.ICity",FORM_SCOPE },
				{"ICityTrxValue","com.integrosys.cms.app.geography.city.trx.ICityTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } 
		});
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE }
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
			ICity city = (ICity) map.get("cityObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICityTrxValue trxValueIn = (OBCityTrxValue) map.get("ICityTrxValue");
			ICityTrxValue trxValueOut = new OBCityTrxValue();

			boolean isCityNameUnique = false;		
			
			String newCityName = city.getCityName();			
			String oldCityName = "";
			
			long newStateId = city.getStateId().getIdState();				
			long oldStateId ;
			
			if ( event.equals("maker_edit_city") ) {
				
				oldCityName = trxValueIn.getActualCity().getCityName();
				oldStateId = trxValueIn.getActualCity().getStateId().getIdState();
				
				if( ( ! newCityName.equals(oldCityName) ) ||  oldStateId != newStateId  )
					isCityNameUnique = getCityProxy().isCityNameUnique(newCityName.trim(),newStateId);

				if( isCityNameUnique != false ){
					exceptionMap.put("cityNameError", new ActionMessage("error.string.exist","City Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				trxValueOut = getCityProxy().makerUpdateCity(ctx, trxValueIn,city);
			} else {
				// event is maker_confirm_resubmit_edit
				
				if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") ){
					oldCityName = trxValueIn.getStagingCity().getCityName();
					oldStateId = trxValueIn.getStagingCity().getStateId().getIdState();
				}
				else{
					oldCityName = trxValueIn.getActualCity().getCityName();
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
				
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getCityProxy().makerEditRejectedCity(ctx,trxValueIn, city);
			}
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}