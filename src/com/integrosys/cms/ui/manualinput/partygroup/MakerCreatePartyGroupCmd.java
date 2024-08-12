/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.manualinput.partygroup;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.partygroup.trx.OBPartyGroupTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.relationshipmgr.SubmitCreateRelationShipManagerCommand;

/**
 * @author $Author: Bharat Waghela$ Command for Create Party group
 */
public class MakerCreatePartyGroupCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IPartyGroupProxyManager partyGroupProxy;

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}
private  static boolean isPartyCodeUnique = true;
	
	/**
	 * @return the isPartyCodeUnique
	 */
	public static boolean isPartyCodeUnique() {
		return isPartyCodeUnique;
	}

	/**
	 * @param isPartyCodeUnique the isPartyCodeUnique to set
	 */
	public static void setPartyCodeUnique(boolean isPartyCodeUnique) {
		MakerCreatePartyGroupCmd.isPartyCodeUnique = isPartyCodeUnique;
	}

	/**
	 * Default Constructor
	 */

	public MakerCreatePartyGroupCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"IPartyGroupTrxValue",
						"com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue",
						SERVICE_SCOPE },
						{ "partyCode", "java.lang.String", REQUEST_SCOPE },
						{ "partyName", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "partyGroupObj",
						"com.integrosys.cms.app.partygroup.bus.OBPartyGroup",
						FORM_SCOPE }

		});
	}

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
			boolean isPartyCodeUnique = true;
			String partyCode = (String)map.get("partyCode");
			String partyName = (String)map.get("partyName");
			if(partyName!=null)
				isPartyCodeUnique = getPartyGroupProxy().isPartyCodeUnique(partyName.trim());

			if(isPartyCodeUnique != false){
				exceptionMap.put("dupPartyNameError", new ActionMessage("error.string.partyname.exist"));
				IPartyGroupTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
        	
			
			OBPartyGroup partyGroup = (OBPartyGroup) map.get("partyGroupObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// IPartyGroupTrxValue trxValueIn = (OBPartyGroupTrxValue)
			// map.get("IPartyGroupTrxValue");

			IPartyGroupTrxValue trxValueOut = new OBPartyGroupTrxValue();
			trxValueOut = getPartyGroupProxy().makerCreatePartyGroup(ctx,
					partyGroup);

			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (PartyGroupException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
