/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/SaveBondListCommand.java,v 1.17 2005/08/30 09:49:57 hshii Exp $
 */

package com.integrosys.cms.ui.digitalLibrary;

import java.util.Arrays;
import java.util.Comparator;
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
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class SaveDigitalLibraryCommand extends AbstractCommand {
	
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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ DigitalLibraryForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(DigitalLibraryForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
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

			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if ((inputEntriesArr[i].getClimsDocCategory()).equalsIgnoreCase(entriesArr[j].getClimsDocCategory())) {
						entriesArr[offset + i].setClimsDocCategory(inputEntriesArr[i].getClimsDocCategory());
						entriesArr[offset + i].setDigiLibDocCategory(inputEntriesArr[i].getDigiLibDocCategory());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingDigitalLibraryGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IDigitalLibraryTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getDigitalLibraryProxy().makerUpdateRejectedDigitalLibraryGroup(trxContext, value);
			}
			else {
				resultValue = getDigitalLibraryProxy().makerUpdateDigitalLibraryGroup(trxContext, value, value.getStagingDigitalLibraryGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingDigitalLibraryGroup().getFeedEntries();

			// Sort the array.
			/*if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IDigitalLibraryEntry entry1 = (IDigitalLibraryEntry) a;
						IDigitalLibraryEntry entry2 = (IDigitalLibraryEntry) b;
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
			resultValue.getStagingDigitalLibraryGroup().setFeedEntries(entriesArr);
			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = DigitalLibraryMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("digitalLibraryTrxValue", resultValue);
			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(DigitalLibraryForm.MAPPER, resultValue);

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
