package com.integrosys.cms.ui.manualinput.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class ConfirmDeletePartyGroupCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	private IPartyGroupProxyManager partyGroupProxy;

	public String[][] getParameterDescriptor() {
		return (new String[][] {

				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
						{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },

				{ "partyId", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
	}

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
				/*
				 * { "customerOb",
				 * "com.integrosys.cms.app.customer.bus.OBCMSCustomer",
				 * REQUEST_SCOPE },
				 */

				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		List system = null;
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		
		String index = (String) map.get("index");
		String partyId = (String) map.get("partyId");
		String amount = (String) map.get("amount");
		List partyGrpList = (List) map.get("partyGrpList");
	
		OBSubline subline = null;
		try {

			
			if (partyGrpList == null) {
				partyGrpList = new ArrayList();
				
			}		
				
				//IPartyGroup party = (IPartyGroup) getPartyGroupProxy().getPartyGroupById(Long.parseLong(partyId));
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			    List cust = customerDAO.searchCustomerByCustomerId(partyId);
				
				ICMSCustomer customer = (ICMSCustomer)cust.get(0);
				
				subline = new OBSubline();
				subline.setPartyId(customer.getCustomerID());
				//subline.setAmount(new Double(amount));
				subline.setAmount(amount);
				partyGrpList.remove((Integer.parseInt(index))-1);
				
		} catch (PartyGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("partyGrpList", partyGrpList);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
