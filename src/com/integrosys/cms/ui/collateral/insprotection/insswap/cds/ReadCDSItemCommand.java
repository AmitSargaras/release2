/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/insswap/cds/ReadCDSItemCommand.java,v 1.1 2005/09/29 09:41:57 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:41:57 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class ReadCDSItemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "form.CDSItem", "com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "actualCDS", "com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem", REQUEST_SCOPE },
				{ "stageCDS", "com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem", REQUEST_SCOPE }, });
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

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		long index = Long.parseLong((String) map.get("indexID"));

		String from_event = (String) map.get("from_event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICreditDefaultSwaps insurance;
		if ((from_event != null) && from_event.equals("read")) {
			insurance = (ICreditDefaultSwaps) itrxValue.getCollateral();
		}
		else {
			insurance = (ICreditDefaultSwaps) itrxValue.getStagingCollateral();
			if ((from_event != null) && from_event.equals("process")) {
				ICDSItem actualCDS = null;
				if(itrxValue.getCollateral()!=null){
					actualCDS = getItem(((ICreditDefaultSwaps) itrxValue.getCollateral()).getCdsItems(), index);
				}
				ICDSItem stageCDS = getItem(insurance.getCdsItems(), index);
				result.put("actualCDS", actualCDS);
				result.put("stageCDS", stageCDS);
			}
		}

		ICDSItem item;
		if (from_event != null) {
			item = getItem(insurance.getCdsItems(), index);
			if ((item == null) && from_event.equals("process")) {
				item = getItem(((ICreditDefaultSwaps) itrxValue.getCollateral()).getCdsItems(), index);
			}
		}
		else {
			item = insurance.getCdsItems()[(int) index];
		}
		result.put("form.CDSItem", item);
		result.put("indexID", map.get("indexID"));
		result.put("from_event", from_event);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ICDSItem getItem(ICDSItem temp[], long itemRef) {
		ICDSItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}
