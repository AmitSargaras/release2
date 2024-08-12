package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 30-03-2011
	 *
	 */

public class ManualInputResubmitDeleteCustomerCommand extends AbstractCommand{
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "partyGrpList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "otherBankList", "java.util.List", SERVICE_SCOPE },
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},	            
                {"remarks", "java.lang.String", REQUEST_SCOPE}

		});
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
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "customerId", "java.lang.String", REQUEST_SCOPE }
			});				
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, " doExecute : ManualInputEditCustomerCommand");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
			
		OBCMSCustomer customer = (OBCMSCustomer) map.get("OBCMSCustomer");
		String event = (String) map.get("event");
		List partyGrpList = (List) map.get("partyGrpList");
		List systemList = (List) map.get("systemList");
		List  otherBankList= (List) map.get("otherBankList");
		List vendorList = (List) map.get("vendorList");
		IBankingMethod banking[]= new IBankingMethod[50];
		ISubline subline[]= new ISubline[50];
		ISystem system[]= new ISystem[50];
		IVendor vendor[] = new IVendor[50];
		List list = new ArrayList();
		if (systemList != null) {
			for (int i = 0; i < systemList.size(); i++) {
				system[i] = (ISystem) systemList.get(i);

			}
		}

		List bank = new ArrayList();
		if (otherBankList != null) {
			for (int i = 0; i < otherBankList.size(); i++) {
				
				banking[i] = (IBankingMethod) otherBankList.get(i);

			}
		}

		List subParty = new ArrayList();
		if (partyGrpList != null) {
			for (int i = 0; i < partyGrpList.size(); i++) {
				subline[i] = (ISubline) partyGrpList.get(i);

			}
		}
		
		List name = new ArrayList();
		if (vendorList != null) {
			for (int i = 0; i < vendorList.size(); i++) {
				vendor[i] = (IVendor) vendorList.get(i);

			}
		}
		
		customer.getCMSLegalEntity().setBankList(banking);
		customer.getCMSLegalEntity().setSublineParty(subline);
		customer.getCMSLegalEntity().setOtherSystem(system);
		customer.getCMSLegalEntity().setVendor(vendor);
		
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");
			
		trxValueIn.setTrxContext(ctx);
		ICMSTrxResult trxValueOut = new OBCMSTrxResult();
		try {
			trxValueOut = (ICMSTrxResult)CustomerProxyFactory.getProxy().makerResubmitDeleteCustomer(ctx, trxValueIn, customer);
//			trxValueOut = (ICMSTrxResult)CustomerProxyFactory.getProxy().hostDeleteCustomer(trxValueIn);
		} catch (CustomerException e) {
			CommandProcessingException cpe = new CommandProcessingException(e.getMessage());
			cpe.initCause(e);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		ICMSCustomerTrxValue trx = (ICMSCustomerTrxValue) trxValueOut.getTrxValue();
		resultMap.put("ICMSCustomerTrxValue", trxValueIn);
		resultMap.put("request.ITrxValue", trx);	

		DefaultLogger.debug(this, " -------- Successfully Deleted Customer------------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);		
		return returnMap;
	}

}
