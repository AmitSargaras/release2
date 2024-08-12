/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/AddBondListCommand.java,v 1.4 2004/03/29 10:45:20 sathish Exp $
 */
package com.integrosys.cms.ui.generalparam;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class AddGeneralParamListCommand extends AbstractCommand {
	
	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}	
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ GeneralParamListForm.MAPPER, "com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IGeneralParamGroup inputFeedGroup = (IGeneralParamGroup) map.get(GeneralParamListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IGeneralParamEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IGeneralParamGroupTrxValue value = (IGeneralParamGroupTrxValue) map.get("generalParamGroupTrxValue");
			IGeneralParamGroup group = value.getStagingGeneralParamGroup();
			IGeneralParamEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getParamName().equals(entriesArr[j].getParamName())) {
							entriesArr[j].setParamValue(inputEntriesArr[i].getParamValue());
						}
					}

				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingGeneralParamGroup(group);

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
