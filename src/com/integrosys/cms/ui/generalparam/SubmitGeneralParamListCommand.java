package com.integrosys.cms.ui.generalparam;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.GeneralParamReplicationUtils;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class SubmitGeneralParamListCommand extends AbstractCommand {
	
	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ GeneralParamListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue",
				REQUEST_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(GeneralParamListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));

			IGeneralParamGroup inputGroup = (IGeneralParamGroup) inputList.get(1);
			IGeneralParamEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IGeneralParamGroupTrxValue value = (IGeneralParamGroupTrxValue) map.get("generalParamGroupTrxValue");
			IGeneralParamGroup group = value.getStagingGeneralParamGroup();
			IGeneralParamGroup replicatedGroup = GeneralParamReplicationUtils.replicateGeneralParamGroupForCreateStagingCopy(group);
			IGeneralParamEntry[] entriesArr = replicatedGroup.getFeedEntries();

			// Update using the input unit prices.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getParamName().equals(entriesArr[j].getParamName())) {
							entriesArr[j].setParamValue(inputEntriesArr[i].getParamValue());
							entriesArr[j].setLastUpdatedDate(inputEntriesArr[i].getLastUpdatedDate());
						}
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingGeneralParamGroup(replicatedGroup);

			IGeneralParamGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getGeneralParamProxy().makerSubmitRejectedGeneralParamGroup(trxContext, value);
			}
			else {
				resultValue = getGeneralParamProxy().makerSubmitGeneralParamGroup(trxContext, value, value.getStagingGeneralParamGroup());
			}
			resultMap.put("request.ITrxValue", resultValue);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
