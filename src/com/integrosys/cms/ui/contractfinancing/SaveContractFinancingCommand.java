/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.proxy.ContractFinancingProxyManagerFactory;
import com.integrosys.cms.app.contractfinancing.proxy.IContractFinancingProxyManager;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.app.contractfinancing.trx.OBContractFinancingTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SaveContractFinancingCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SaveContractFinancingCommand() {
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
				{ "contractFinancingObj", "com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing",
						FORM_SCOPE }, // Collection of
										// com.integrosys.cms.app.contractfinancing
										// .bus.OBContractFinancing
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
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

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> In save Command!");
		IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> trxContext=" + trxContext);

		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			trxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
			trxContext.setTrxOrganisationOrigin(limit.getOriginatingLocation().getOrganisationCode());

			IContractFinancing obContractFinancing = (IContractFinancing) map.get("contractFinancingObj");
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> obContractFinancing=" + obContractFinancing);

			IContractFinancingProxyManager proxy = ContractFinancingProxyManagerFactory
					.getContractFinancingProxyManager();

			if (trxValue == null) {
				trxValue = new OBContractFinancingTrxValue();
				trxValue.setStagingContractFinancing(obContractFinancing);
				trxValue.setTrxContext(trxContext);
			}

			trxValue = proxy.makerSaveContractFinancing(trxContext, trxValue, obContractFinancing);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> after makerUpdateContractFinancing");
			resultMap.put("request.ITrxValue", trxValue);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}