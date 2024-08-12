package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixReplicationUtils;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.proxy.IApprovalMatrixProxy;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class implements command
 */
public class SubmitApprovalMatrixCommand extends AbstractCommand {
	
	private IApprovalMatrixProxy approvalMatrixProxy;
	
	/**
	 * @return the approvalMatrixProxy
	 */
	public IApprovalMatrixProxy getApprovalMatrixProxy() {
		return approvalMatrixProxy;
	}

	/**
	 * @param approvalMatrixProxy the approvalMatrixProxy to set
	 */
	public void setApprovalMatrixProxy(IApprovalMatrixProxy approvalMatrixProxy) {
		this.approvalMatrixProxy = approvalMatrixProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ ApprovalMatrixForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
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
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue",
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

			List inputList = (List) map.get(ApprovalMatrixForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(1));
			// Element at index 2 is the target offset which is not required
			// here.
			IApprovalMatrixGroup inputGroup = (IApprovalMatrixGroup) inputList.get(1);
			IApprovalMatrixEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");
			IApprovalMatrixGroup group = value.getStagingApprovalMatrixGroup();

			IApprovalMatrixGroup replicatedGroup = ApprovalMatrixReplicationUtils.replicateApprovalMatrixGroupForCreateStagingCopy(group);

			IApprovalMatrixEntry[] entriesArr = replicatedGroup.getFeedEntries();

			// Update using the input unit prices.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getRiskGrade() == entriesArr[j].getRiskGrade()) {
							entriesArr[j].setRiskGrade(inputEntriesArr[i].getRiskGrade());
							entriesArr[j].setLevel1(inputEntriesArr[i].getLevel1());
							entriesArr[j].setLevel2(inputEntriesArr[i].getLevel2());
							entriesArr[j].setLevel3(inputEntriesArr[i].getLevel3());
							entriesArr[j].setLevel4(inputEntriesArr[i].getLevel4());
						}
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingApprovalMatrixGroup(replicatedGroup);

			IApprovalMatrixTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getApprovalMatrixProxy().makerSubmitRejectedApprovalMatrixGroup(trxContext, value);
			}
			else {
				resultValue = getApprovalMatrixProxy().makerSubmitApprovalMatrixGroup(trxContext, value, value.getStagingApprovalMatrixGroup());
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
