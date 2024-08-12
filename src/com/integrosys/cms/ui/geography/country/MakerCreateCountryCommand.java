package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCreateCountryCommand extends AbstractCommand {

	private ICountryProxyManager countryProxy;

	public ICountryProxyManager getCountryProxy() {
		return countryProxy;
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "countryObj","com.integrosys.cms.app.geography.country.bus.ICountry",FORM_SCOPE },
				{ "ICountryTrxValue","com.integrosys.cms.app.geography.country.trx.ICountryTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "countryCode", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "countryObj","com.integrosys.cms.app.geography.country.bus.ICountry",REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of Country is done.
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

		DefaultLogger.debug(this, "============ MakerCreateCountryCommand ()");
		String event = (String) map.get("event");
		ICountry country = (ICountry) map.get("countryObj");
		ICountryTrxValue trxValueIn = (OBCountryTrxValue) map.get("ICountryTrxValue");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICountryTrxValue trxValueOut = new OBCountryTrxValue();

		try {
			boolean validateCode = false;
			if ( trxValueIn == null  )
				validateCode = true;
			
			else if( trxValueIn.getFromState().equals("PENDING_PERFECTION") )
				validateCode = true;
			
			String newCountryName = country.getCountryName();
			String oldCountryName = "";
			
			boolean isCountryCodeUnique = false;
			boolean isCountryNameUnique = false;
			
			if ( validateCode ) {	/* Maker Add and than Save / Submit */
				
				String countryCode = (String)map.get("countryCode");
												
				if( countryCode != null )
					isCountryCodeUnique = getCountryProxy().isCountryCodeUnique(countryCode.trim());
	
				if( isCountryCodeUnique )
					exceptionMap.put("countryCodeError", new ActionMessage("error.string.exist","Country Code"));
				
				if( newCountryName != null )
					isCountryNameUnique = getCountryProxy().isCountryNameUnique(newCountryName.trim());
	
				if( isCountryNameUnique )
					exceptionMap.put("countryNameError", new ActionMessage("error.string.exist","Country Name"));
				
				if( isCountryCodeUnique || isCountryNameUnique ){
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			if (event.equals("maker_create_country")) {
				trxValueOut = getCountryProxy().makerCreateCountry(ctx,trxValueOut, country);
				resultMap.put("countryObj", country);
			} else if (event.equals("maker_save_country")) {				
				trxValueOut = getCountryProxy().makerSaveCountry(ctx, country);
				resultMap.put("countryObj", country);
			} else if (trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
				trxValueOut = getCountryProxy().makerCreateCountry(ctx,trxValueIn, country);
			} else if (event.equals("maker_create_saved_country") || event.equals("maker_edit_save_created_country")){
				
				if( event.equals("maker_edit_save_created_country") ) 					/* Maker Edit, Saved it */
					oldCountryName = trxValueIn.getActualCountry().getCountryName();
				else																	/* Maker Edit, Saved it. Maker ToDo Submit */
					oldCountryName = trxValueIn.getStagingCountry().getCountryName();
				
					if( ! newCountryName.equals(oldCountryName) )
						isCountryNameUnique = getCountryProxy().isCountryNameUnique(newCountryName.trim());
		
					if( isCountryNameUnique ){
						exceptionMap.put("countryNameError", new ActionMessage("error.string.exist","Country Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				trxValueOut = getCountryProxy().makerUpdateSaveUpdateCountry(ctx, trxValueIn, country);
			}
			else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getCountryProxy().makerEditRejectedCountry(ctx,trxValueIn, country);
			}
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			e.printStackTrace();
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