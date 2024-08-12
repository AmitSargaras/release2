package com.integrosys.cms.ui.geography.state;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerEditStateCommand extends AbstractCommand {

	private IStateProxyManager stateProxy;

	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	public MakerEditStateCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "stateObj",
						"com.integrosys.cms.app.geography.state.bus.IState",
						FORM_SCOPE },
				{
						"IStateTrxValue",
						"com.integrosys.cms.app.geography.state.trx.IStateTrxValue",
						SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "countryId", "java.lang.String", REQUEST_SCOPE },
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
			IState state = (IState) map.get("stateObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IStateTrxValue trxValueIn = (OBStateTrxValue) map.get("IStateTrxValue");
			IStateTrxValue trxValueOut = new OBStateTrxValue();

			boolean isStateNameUnique = false;		
			
			String newStateName = state.getStateName();			
			long newCountryId = state.getRegionId().getCountryId().getIdCountry();				
			
			String oldStateName = "";
			long oldCountryId ;
			
			if (event.equals("maker_edit_state")) {
				
				oldStateName = trxValueIn.getActualState().getStateName();
				oldCountryId = trxValueIn.getActualState().getRegionId().getCountryId().getIdCountry();
				
				if( ( ! newStateName.equals(oldStateName) ) ||  oldCountryId != newCountryId  )
					isStateNameUnique = getStateProxy().isStateNameUnique(newStateName.trim(),newCountryId);

				if(isStateNameUnique != false){
					exceptionMap.put("stateNameError", new ActionMessage("error.string.exist","State Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				trxValueOut = getStateProxy().makerUpdateState(ctx, trxValueIn,state);
			} else {
				// event is maker_confirm_resubmit_edit
				if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") ){
					oldStateName = trxValueIn.getStagingState().getStateName();
					oldCountryId = trxValueIn.getStagingState().getRegionId().getCountryId().getIdCountry();
				}
				else{
					oldStateName = trxValueIn.getActualState().getStateName();
					oldCountryId = trxValueIn.getActualState().getRegionId().getCountryId().getIdCountry();
				}
				
				if(  ( ! newStateName.equals(oldStateName) ) ||  oldCountryId != newCountryId )
					isStateNameUnique = getStateProxy().isStateNameUnique(newStateName.trim(),newCountryId);

				if(isStateNameUnique != false){
					exceptionMap.put("stateNameError", new ActionMessage("error.string.exist","State Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getStateProxy().makerEditRejectedState(ctx,trxValueIn, state);
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
