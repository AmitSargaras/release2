/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ApproveBondListCommand.java,v 1.9 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.proxy.IApprovalMatrixProxy;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class ApproveApprovalMatrixCommand extends AbstractCommand {
	
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
				// Consume the current feed entries to be saved as a whole.
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IApprovalMatrixGroup group = value.getApprovalMatrixGroup();
			IApprovalMatrixGroup stageGroup = value.getStagingApprovalMatrixGroup();
			IApprovalMatrixEntry[] actualEntries = null;
			IApprovalMatrixEntry[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			
			if(group.getFeedEntries()==null){
				DefaultLogger.debug(this, "///////////////////////////group is null //////////");
			}else{
				DefaultLogger.debug(this, "///////////////////////////group is not null //////////");
			}

			
			stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingApprovalMatrixGroup(stageGroup);


			value = getApprovalMatrixProxy().checkerApproveApprovalMatrixGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private IApprovalMatrixEntry[] updateLastUpdateDate(IApprovalMatrixEntry[] actualEntries, IApprovalMatrixEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getApprovalMatrixEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IApprovalMatrixEntry actual = (IApprovalMatrixEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getApprovalMatrixEntryRef()));
				if ((actual == null) || (actual.getLevel1() != stageEntries[i].getLevel1())
						|| (actual.getLevel2() != stageEntries[i].getLevel2())
						|| (actual.getLevel3() != stageEntries[i].getLevel3())
						|| (actual.getLevel4() != stageEntries[i].getLevel4())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}

}
