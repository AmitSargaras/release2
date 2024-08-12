package com.integrosys.cms.ui.digitalLibrary;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryReplicationUtils;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.proxy.IDigitalLibraryProxy;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class implements command
 */
public class SubmitDigitalLibraryCommand extends AbstractCommand {
	
	private IDigitalLibraryProxy digitalLibraryProxy;
	
	/**
	 * @return the digitalLibraryProxy
	 */
	public IDigitalLibraryProxy getDigitalLibraryProxy() {
		return digitalLibraryProxy;
	}

	/**
	 * @param digitalLibraryProxy the digitalLibraryProxy to set
	 */
	public void setDigitalLibraryProxy(IDigitalLibraryProxy digitalLibraryProxy) {
		this.digitalLibraryProxy = digitalLibraryProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ DigitalLibraryForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
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
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue",
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

			List inputList = (List) map.get(DigitalLibraryForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(1));
			// Element at index 2 is the target offset which is not required
			// here.
			IDigitalLibraryGroup inputGroup = (IDigitalLibraryGroup) inputList.get(1);
			IDigitalLibraryEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");
			IDigitalLibraryGroup group = value.getStagingDigitalLibraryGroup();

			IDigitalLibraryGroup replicatedGroup = DigitalLibraryReplicationUtils.replicateDigitalLibraryGroupForCreateStagingCopy(group);

			IDigitalLibraryEntry[] entriesArr = replicatedGroup.getFeedEntries();

			// Update using the input unit prices.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if ((inputEntriesArr[i].getClimsDocCategory()).equalsIgnoreCase(entriesArr[j].getClimsDocCategory())) {
							entriesArr[j].setClimsDocCategory(inputEntriesArr[i].getClimsDocCategory());
							entriesArr[j].setDigiLibDocCategory(inputEntriesArr[i].getDigiLibDocCategory());
							
							
							if(("Facility").equalsIgnoreCase(entriesArr[j].getClimsDocCategory()))
							{
								entriesArr[j].setClimsDocCategorySeq("1");
							}
							else if(("CAM").equalsIgnoreCase(entriesArr[j].getClimsDocCategory()))
							{
								entriesArr[j].setClimsDocCategorySeq("2");
							}
							else if(("Recurrent").equalsIgnoreCase(entriesArr[j].getClimsDocCategory()))
							{
								entriesArr[j].setClimsDocCategorySeq("5");
							}
							else if(("Other").equalsIgnoreCase(entriesArr[j].getClimsDocCategory()))
							{
								entriesArr[j].setClimsDocCategorySeq("4");
							}
							else if(("Security").equalsIgnoreCase(entriesArr[j].getClimsDocCategory()))
							{
								entriesArr[j].setClimsDocCategorySeq("3");
							}
						}
					}
				}
			}
			
			
			
			
			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingDigitalLibraryGroup(replicatedGroup);

			IDigitalLibraryTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getDigitalLibraryProxy().makerSubmitRejectedDigitalLibraryGroup(trxContext, value);
			}
			else {
				resultValue = getDigitalLibraryProxy().makerSubmitDigitalLibraryGroup(trxContext, value, value.getStagingDigitalLibraryGroup());
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
