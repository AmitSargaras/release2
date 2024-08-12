package com.integrosys.cms.ui.manualinput.partygroup;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.partygroup.trx.OBPartyGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Bharat waghela $ Command for edit Party Group
 */
public class MakerEditPartyGroupCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IPartyGroupProxyManager partyGroupProxy;

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerEditPartyGroupCmd() {
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
			OBPartyGroup partyGroup = (OBPartyGroup) map.get("partyGroupObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IPartyGroupTrxValue trxValueIn = (OBPartyGroupTrxValue) map
					.get("IPartyGroupTrxValue");

			IPartyGroupTrxValue trxValueOut = new OBPartyGroupTrxValue();
			String partyName = (String) map.get("partyName");
			if (event.equals("maker_save_create")) {
				trxValueOut = getPartyGroupProxy()
						.makerUpdateSaveUpdatePartyGroup(ctx, trxValueIn,
								partyGroup);
			} else if (trxValueIn.getFromState().equals(
					ICMSConstant.STATE_PENDING_PERFECTION)) {
				boolean isPartyCodeUnique = true;
				String partyCode = (String) map.get("partyCode");

				if (partyName != null)
					isPartyCodeUnique = getPartyGroupProxy().isPartyCodeUnique(
							partyName.trim());

				if (isPartyCodeUnique != false) {
					exceptionMap.put("dupPartyNameError", new ActionMessage(
							"error.string.partyname.exist"));
					IPartyGroupTrxValue partyGroupTrxValue = null;
					resultMap.put("request.ITrxValue", partyGroupTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
							resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
							exceptionMap);
					return returnMap;
				}
				trxValueOut = getPartyGroupProxy().makerCreatePartyGroup(ctx,
						trxValueIn, partyGroup);

			} else if ((event.equals("maker_edit_party_group") || event.equals("maker_save_update"))) {
				boolean isPartyCodeUnique = false;

				String oldCustomerName = "";
				String newCustomerName = (String) partyGroup.getPartyName();

				oldCustomerName = trxValueIn.getPartyGroup().getPartyName();

				if (!newCustomerName.equals(oldCustomerName)) {
					isPartyCodeUnique = getPartyGroupProxy().isPartyCodeUnique(
							partyName.trim());
				}
				if (isPartyCodeUnique != false) {
					exceptionMap.put("dupPartyNameError", new ActionMessage(
							"error.string.partyname.exist"));
					IPartyGroupTrxValue partyGroupTrxValue = null;
					resultMap.put("request.ITrxValue", partyGroupTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
							resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
							exceptionMap);
					return returnMap;
				}
				trxValueOut = getPartyGroupProxy().makerUpdatePartyGroup(ctx,
						trxValueIn, partyGroup);
			} else if (event.equals("maker_delete_party_group")) {
				trxValueOut = getPartyGroupProxy().makerUpdatePartyGroup(ctx,
						trxValueIn, partyGroup);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				boolean isPartyCodeUnique = false;
				String oldCustomerName = "";
				String newCustomerName = (String) partyGroup.getPartyName();
                
				
                  if(trxValueIn.getFromState().equals("PENDING_CREATE"))
                  {
                	  isPartyCodeUnique = getPartyGroupProxy().isPartyCodeUnique(
  							partyName.trim());  
                  }
                  else{
                	  oldCustomerName = trxValueIn.getPartyGroup().getPartyName();
				if (!newCustomerName.equals(oldCustomerName)) {
					isPartyCodeUnique = getPartyGroupProxy().isPartyCodeUnique(
							partyName.trim());
				}
                  }
				if (isPartyCodeUnique != false) {
					exceptionMap.put("dupPartyNameError", new ActionMessage(
							"error.string.partyname.exist"));
					IPartyGroupTrxValue partyGroupTrxValue = null;
					resultMap.put("request.ITrxValue", partyGroupTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
							resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
							exceptionMap);
					return returnMap;
				}

				trxValueOut = getPartyGroupProxy().makerEditRejectedPartyGroup(
						ctx, trxValueIn, partyGroup);
			}

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
