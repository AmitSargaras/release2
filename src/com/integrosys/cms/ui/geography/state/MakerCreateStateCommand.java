package com.integrosys.cms.ui.geography.state;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCreateStateCommand extends AbstractCommand {

	private IStateProxyManager stateProxy;

	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "stateObj","com.integrosys.cms.app.geography.state.bus.IState",FORM_SCOPE },
				{ "IStateTrxValue","com.integrosys.cms.app.geography.state.trx.IStateTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "stateCode", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "stateObj","com.integrosys.cms.app.geography.state.bus.IState",REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of State is done.
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

		DefaultLogger.debug(this, "============ MakerCreateStateCommand ()");
		String event = (String) map.get("event");
		IState state = (IState) map.get("stateObj");
		IStateTrxValue trxValueIn = (OBStateTrxValue) map.get("IStateTrxValue");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IStateTrxValue trxValueOut = new OBStateTrxValue();

		try {
			boolean validateCode = false;
			if ( trxValueIn == null  )
				validateCode = true;
			
			else if( trxValueIn.getFromState().equals("PENDING_PERFECTION") )
				validateCode = true;
					
			String newStateName = state.getStateName();
			String oldStateName = "";
			
			long newCountryId = state.getRegionId().getCountryId().getIdCountry();
			long oldCountryId ;

			boolean isStateCodeUnique = true;
			boolean isStateNameUnique = false;
			
			if ( validateCode ) {
				
				String stateCode = (String) map.get("stateCode");
				if (stateCode != null)
					isStateCodeUnique = getStateProxy().isStateCodeUnique(stateCode.trim());
				
				if ( isStateCodeUnique ) 
					exceptionMap.put("stateCodeError",new ActionMessage("error.string.exist","State Code"));				
				
				if( newStateName != null )
					isStateNameUnique = getStateProxy().isStateNameUnique(newStateName.trim(),newCountryId);
				
				if( isStateNameUnique )
					exceptionMap.put("stateNameError", new ActionMessage("error.string.exist","State Name"));
	
				if( isStateCodeUnique || isStateNameUnique ){
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			if (event.equals("maker_create_state")) {
				trxValueOut = getStateProxy().makerCreateState(ctx,trxValueOut, state);
				resultMap.put("stateObj", state);
			} else if (event.equals("maker_save_state")) {
				trxValueOut = getStateProxy().makerSaveState(ctx, state);
				resultMap.put("stateObj", state);
			} else if (trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
				trxValueOut = getStateProxy().makerCreateState(ctx, trxValueIn,state);
			} else if (event.equals("maker_create_saved_state") || event.equals("maker_edit_save_created_state")){
				
				if( event.equals("maker_edit_save_created_state") ){ 					/* Maker Edit, Saved it */
					oldStateName = trxValueIn.getActualState().getStateName();
					oldCountryId = trxValueIn.getActualState().getRegionId().getCountryId().getIdCountry();
				}
				else{																	/* Maker Edit, Saved it. Maker ToDo Submit */
					oldStateName = trxValueIn.getStagingState().getStateName();
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
				trxValueOut = getStateProxy().makerUpdateSaveUpdateState(ctx,trxValueIn, state);
			}
			else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getStateProxy().makerEditRejectedState(ctx,trxValueIn, state);
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