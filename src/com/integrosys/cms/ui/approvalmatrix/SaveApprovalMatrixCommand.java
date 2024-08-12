/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/SaveBondListCommand.java,v 1.17 2005/08/30 09:49:57 hshii Exp $
 */

package com.integrosys.cms.ui.approvalmatrix;

import java.util.Arrays;
import java.util.Comparator;
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
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class SaveApprovalMatrixCommand extends AbstractCommand {
	
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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ ApprovalMatrixForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(ApprovalMatrixForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
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

			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (inputEntriesArr[i].getRiskGrade() == entriesArr[j].getRiskGrade()) {
						entriesArr[offset + i].setRiskGrade(inputEntriesArr[i].getRiskGrade());
						entriesArr[offset + i].setLevel1(inputEntriesArr[i].getLevel1());
						entriesArr[offset + i].setLevel2(inputEntriesArr[i].getLevel2());
						entriesArr[offset + i].setLevel3(inputEntriesArr[i].getLevel3());
						entriesArr[offset + i].setLevel4(inputEntriesArr[i].getLevel4());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingApprovalMatrixGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IApprovalMatrixTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getApprovalMatrixProxy().makerUpdateRejectedApprovalMatrixGroup(trxContext, value);
			}
			else {
				resultValue = getApprovalMatrixProxy().makerUpdateApprovalMatrixGroup(trxContext, value, value.getStagingApprovalMatrixGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingApprovalMatrixGroup().getFeedEntries();

			// Sort the array.
			/*if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IApprovalMatrixEntry entry1 = (IApprovalMatrixEntry) a;
						IApprovalMatrixEntry entry2 = (IApprovalMatrixEntry) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			}*/
			resultValue.getStagingApprovalMatrixGroup().setFeedEntries(entriesArr);
			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = ApprovalMatrixMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("approvalMatrixTrxValue", resultValue);
			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(ApprovalMatrixForm.MAPPER, resultValue);

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
