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
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class EditedPartyGroupCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	private IPartyGroupProxyManager partyGroupProxy;

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {

				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
						{ "event", "java.lang.String", REQUEST_SCOPE },
						{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "partyId", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
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

				{ "partyGrpList", "java.util.List", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
						{ "event", "java.lang.String", REQUEST_SCOPE },
						{ "index", "java.lang.String", REQUEST_SCOPE },
						{ "amount", "java.lang.String", REQUEST_SCOPE },
						{ "index", "java.lang.String", REQUEST_SCOPE },
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
		String index = (String) map.get("index");
		String amount = (String) map.get("amount");
		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		List system = null;
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");

		String partyId = (String) map.get("partyId");
		List partyGrpList = new ArrayList();
		try {

			//IPartyGroup party = (IPartyGroup) getPartyGroupProxy().getPartyGroupById(Long.parseLong(partyId));
			/*ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
		    List cust = customerDAO.searchCustomerByCIFNumber(partyId);
			
			ICMSCustomer customer = (ICMSCustomer)cust.get(0);*/
			ISubline subline = new OBSubline();
			subline.setPartyId(Long.parseLong(partyId));
			subline.setAmount(amount);
			partyGrpList.add(subline);

		} catch (PartyGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("partyGrpList", partyGrpList);

		/*
		 * String source = (String) map.get("legalSource"); ICMSCustomer
		 * obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer"); OBTrxContext
		 * ctx = (OBTrxContext) map.get("theOBTrxContext"); ICMSCustomerTrxValue
		 * trxValue = new OBCMSCustomerTrxValue(); List system =
		 * obCustomer.getCMSLegalEntity().getOtherSystem();
		 * 
		 * 
		 * trxValue.setTrxContext(ctx); trxValue.setStagingCustomer(obCustomer);
		 * trxValue.setLegalName(obCustomer.getLegalEntity().getLegalName());
		 * trxValue.setCustomerName(obCustomer.getCustomerName());
		 * trxValue.setLimitProfileReferenceNumber(obCustomer.getCifId());
		 * DefaultLogger.debug(this, " -------- Before Create : "+obCustomer);
		 * try { ICMSCustomerTrxValue trx =
		 * CustomerProxyFactory.getProxy().createCustomer(trxValue);
		 * resultMap.put("customerOb", trx); resultMap.put("request.ITrxValue",
		 * trx); } catch (CustomerException e) { CommandProcessingException cpe
		 * = new CommandProcessingException(e.getMessage()); cpe.initCause(e);
		 * throw cpe; } catch (Exception e) { CommandProcessingException cpe =
		 * new CommandProcessingException("Internal Error While Processing ");
		 * cpe.initCause(e); throw cpe; }
		 */
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
