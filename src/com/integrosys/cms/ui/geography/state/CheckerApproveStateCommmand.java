package com.integrosys.cms.ui.geography.state;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerApproveStateCommmand extends AbstractCommand implements
		ICommonEventConstant {

	private IStateProxyManager stateProxy;

	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	public CheckerApproveStateCommmand() {
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
						"IStateTrxValue",
						"com.integrosys.cms.app.geography.state.trx.IStateTrxValue",
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
			IStateTrxValue trxValueIn = (OBStateTrxValue) map
					.get("IStateTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			// Start 
			
			if ( trxValueIn.getFromState().equals("DRAFT") || trxValueIn.getFromState().equals("ND") ) {				
				if (!trxValueIn.getStatus().equals("PENDING_UPDATE")) {
					boolean isStateCodeUnique = true;
					boolean isStateNameUnique = false;
					
					String stateCode = trxValueIn.getStagingState().getStateCode();
					String stateName = trxValueIn.getStagingState().getStateName();
					long countryId = trxValueIn.getStagingState().getRegionId().getCountryId().getIdCountry();
					
					if (stateCode != null)
						isStateCodeUnique = getStateProxy().isStateCodeUnique(stateCode.trim());
					if (isStateCodeUnique != false) {
						exceptionMap.put("duplicateStateCodeError",new ActionMessage("error.string.exist","State Code"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
						return returnMap;
					}
					
					if(stateName!=null)
				/* Change this name for region */		isStateNameUnique = getStateProxy().isStateNameUnique(stateName.trim(),countryId);			
				
					if (isStateNameUnique != false) {
						exceptionMap.put("duplicateStateNameError",new ActionMessage("error.string.exist", "State Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
						return returnMap;
						}
					}
				}
			
			// End
			
			IStateTrxValue trxValueOut = getStateProxy().checkerApproveState(
					ctx, trxValueIn);
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
