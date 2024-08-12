package com.integrosys.cms.ui.valuationAgency;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

/**
 * @author rajib.aich  For Valuation Agency
 * Command for checker to approve edit .
 */
public class CreateReadValuationAgencyCmd extends AbstractCommand implements ICommonEventConstant {


	private IValuationAgencyProxyManager valuationAgencyProxy;

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}
	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	/**
	 * Default Constructor
	 */
	public CreateReadValuationAgencyCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},

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
				{ "valuationObj", "com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency", FORM_SCOPE },
				{"IValuationAgencyTrxValue", "com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ValuationAgencyException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IValuationAgency valuationAgency;
			IValuationAgencyTrxValue trxValue=null;
			String valuationAgencyCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			trxValue = (OBValuationAgencyTrxValue) getValuationAgencyProxy().getValuationAgencyByTrxID(valuationAgencyCode);
			valuationAgency = (OBValuationAgency) trxValue.getStagingValuationAgency();
			resultMap.put("IValuationAgencyTrxValue", trxValue);
			resultMap.put("valuationObj", valuationAgency);
			resultMap.put("event", event);
		} catch (ValuationAgencyException e) {

			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


}
