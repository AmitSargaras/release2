package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerEditRegionCommand extends AbstractCommand {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	public MakerEditRegionCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "regionObj",
						"com.integrosys.cms.app.geography.region.bus.IRegion",
						FORM_SCOPE },
				{
						"IRegionTrxValue",
						"com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",
						SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE }, });
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
			IRegion region = (IRegion) map.get("regionObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IRegionTrxValue trxValueIn = (OBRegionTrxValue) map
					.get("IRegionTrxValue");
			IRegionTrxValue trxValueOut = new OBRegionTrxValue();

			boolean isRegionNameUnique = false;		
			
			String newRegionyName = region.getRegionName();			
			long newCountryId = region.getCountryId().getIdCountry();				
			
			String oldRegionName = "";
			long oldCountryId ;
			
			if ( event.equals("maker_edit_region")) {
				oldRegionName = trxValueIn.getActualRegion().getRegionName();
				oldCountryId = trxValueIn.getActualRegion().getCountryId().getIdCountry();
				
				if( ( ! newRegionyName.equals(oldRegionName) ) ||  oldCountryId != newCountryId  )
					isRegionNameUnique = getRegionProxy().isRegionNameUnique(newRegionyName.trim(),newCountryId);

				if(isRegionNameUnique != false){
					exceptionMap.put("regionNameError", new ActionMessage("error.string.exist","Region Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				trxValueOut = getRegionProxy().makerUpdateRegion(ctx,
						trxValueIn, region);
			} else {
				// event is maker_confirm_resubmit_edit
				
				if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") ){
					oldRegionName = trxValueIn.getStagingRegion().getRegionName();
					oldCountryId = trxValueIn.getStagingRegion().getCountryId().getIdCountry();
				}
				else{
					oldRegionName = trxValueIn.getActualRegion().getRegionName();
					oldCountryId = trxValueIn.getActualRegion().getCountryId().getIdCountry();
				}
				
				if(  ( ! newRegionyName.equals(oldRegionName) ) ||  oldCountryId != newCountryId )
					isRegionNameUnique = getRegionProxy().isRegionNameUnique(newRegionyName.trim(),newCountryId);

				if(isRegionNameUnique != false){
					exceptionMap.put("regionNameError", new ActionMessage("error.string.exist","Region Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getRegionProxy().makerEditRejectedRegion(ctx,
						trxValueIn, region);
			}

			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
