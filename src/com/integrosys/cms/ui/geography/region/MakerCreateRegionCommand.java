package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCreateRegionCommand extends AbstractCommand {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "regionObj","com.integrosys.cms.app.geography.region.bus.IRegion",FORM_SCOPE },
				{ "IRegionTrxValue","com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "regionCode", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "regionObj","com.integrosys.cms.app.geography.region.bus.IRegion",REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of Region is done.
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

		DefaultLogger.debug(this, "============ MakerCreateRegionCommand ()");
		String event = (String) map.get("event");
		IRegion region = (IRegion) map.get("regionObj");
		IRegionTrxValue trxValueIn = (OBRegionTrxValue) map.get("IRegionTrxValue");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IRegionTrxValue trxValueOut = new OBRegionTrxValue();

		try {
			boolean validateCode = false;
			if ( trxValueIn == null  )
				validateCode = true;
			
			else if( trxValueIn.getFromState().equals("PENDING_PERFECTION") )
				validateCode = true;
			
			String newRegionName = region.getRegionName();
			String oldRegionName = "";
			
			long newCountryId = region.getCountryId().getIdCountry();
			long oldCountryId ;

			boolean isRegionCodeUnique = false;
			boolean isRegionNameUnique = false;
			
			if ( validateCode ) {	/* Maker Add and than Save / Submit */
				
				String regionCode = (String)map.get("regionCode");
												
				if( regionCode != null )
					isRegionCodeUnique = getRegionProxy().isRegionCodeUnique(regionCode.trim());
	
				if( isRegionCodeUnique )
					exceptionMap.put("regionCodeError", new ActionMessage("error.string.exist","Region Code"));
					
				if( newRegionName != null )
					isRegionNameUnique = getRegionProxy().isRegionNameUnique(newRegionName.trim(),newCountryId);
	
				if( isRegionNameUnique )
					exceptionMap.put("regionNameError", new ActionMessage("error.string.exist","Region Name"));
					
				if( isRegionCodeUnique || isRegionNameUnique ){
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			if (event.equals("maker_create_region")) {
				trxValueOut = getRegionProxy().makerCreateRegion(ctx,trxValueOut, region);
				resultMap.put("regionObj", region);
			} else if (event.equals("maker_save_region")) {				
				trxValueOut = getRegionProxy().makerSaveRegion(ctx, region);
				resultMap.put("regionObj", region);
			} else if (trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
				trxValueOut = getRegionProxy().makerCreateRegion(ctx,trxValueIn, region);
			} else if (event.equals("maker_create_saved_region") || event.equals("maker_edit_save_created_region")){
				
				if( event.equals("maker_edit_save_created_region") ){ 				/* Maker Edit, Saved it */
					oldRegionName = trxValueIn.getActualRegion().getRegionName();
					oldCountryId = trxValueIn.getActualRegion().getCountryId().getIdCountry();
				}
				else{																/* Maker Edit, Saved it. Maker ToDo Submit */
					oldRegionName = trxValueIn.getStagingRegion().getRegionName();
					oldCountryId = trxValueIn.getActualRegion().getCountryId().getIdCountry();
				}
				
				if(  ( ! newRegionName.equals(oldRegionName) ) ||  oldCountryId != newCountryId )
					isRegionNameUnique = getRegionProxy().isRegionNameUnique(newRegionName.trim(),newCountryId);
	
				if(isRegionNameUnique != false){
					exceptionMap.put("regionNameError", new ActionMessage("error.string.exist","Region Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
					
				trxValueOut = getRegionProxy().makerUpdateSaveUpdateRegion(ctx, trxValueIn, region);
			}
			else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getRegionProxy().makerEditRejectedRegion(ctx,trxValueIn, region);
			}
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
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