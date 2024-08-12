package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.proxy.BridgingLoanProxyManagerFactory;
import com.integrosys.cms.app.bridgingloan.proxy.IBridgingLoanProxyManager;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.bridgingloan.trx.OBBridgingLoanTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 15, 2007 Time: 1:17:22 AM To
 * change this template use File | Settings | File Templates.
 */
public class SaveBridgingLoanCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SaveBridgingLoanCommand() {
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
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE }, // Collection
																												// of
																												// com
																												// .
																												// integrosys
																												// .
																												// cms
																												// .
																												// app
																												// .
																												// bridgingloan
																												// .
																												// bus
																												// .
																												// OBBridgingLoan
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Updates to the contract financing is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IBridgingLoan OBBridgingLoan = (IBridgingLoan) map.get("objBridgingLoan");

		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			trxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
			trxContext.setTrxOrganisationOrigin(limit.getOriginatingLocation().getOrganisationCode());

			IBridgingLoanProxyManager proxy = BridgingLoanProxyManagerFactory.getBridgingLoanProxyManager();

			if (trxValue == null) {
				trxValue = new OBBridgingLoanTrxValue();
				trxValue.setStagingBridgingLoan(OBBridgingLoan);
				trxValue.setTrxContext(trxContext);
			}
			trxValue = proxy.makerSaveBridgingLoan(trxContext, trxValue, OBBridgingLoan);
			resultMap.put("request.ITrxValue", trxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}