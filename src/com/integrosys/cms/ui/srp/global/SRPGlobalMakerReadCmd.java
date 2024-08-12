/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalMakerReadCmd.java,v 1.4 2005/09/08 08:56:22 hshii Exp $
 */

package com.integrosys.cms.ui.srp.global;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralSubTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/08 08:56:22 $ Tag: $Name: $
 */
public class SRPGlobalMakerReadCmd extends AbstractCommand {

	private ICollateralParameterProxy collateralParameterProxy;

	public void setCollateralParameterProxy(ICollateralParameterProxy collateralParameterProxy) {
		this.collateralParameterProxy = collateralParameterProxy;
	}

	public ICollateralParameterProxy getCollateralParameterProxy() {
		return collateralParameterProxy;
	}

	/**
	 * Default Constructor
	 */
	public SRPGlobalMakerReadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialCollateralSubType", "com.integrosys.cms.app.collateral.bus.ICollateralSubType", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "CollateralSubTypeTrxValue", ICollateralSubTypeTrxValue.class.getName(), SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialCollateralSubType", "java.util.list", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICollateralSubType obCollateralSybType = (ICollateralSubType) map.get("InitialCollateralSubType");

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", securitySubTypeID="
				+ obCollateralSybType.getTypeCode());

		if (!event.equals("maker_add_edit_srpglobal_error")) {
			ICollateralSubTypeTrxValue collateralSubTypeTrxVal = null;
			try {
				collateralSubTypeTrxVal = getCollateralParameterProxy().getCollateralSubTypeTrxValue(trxContext,
						obCollateralSybType.getTypeCode());
			}
			catch (CollateralException ex) {
				throw new CommandProcessingException("failed to retrieve collateral sub type workflow by type code ["
						+ obCollateralSybType.getTypeCode() + "]", ex);
			}

			ICollateralSubType[] subtype = collateralSubTypeTrxVal.getCollateralSubTypes();
			Arrays.sort(subtype);
			collateralSubTypeTrxVal.setCollateralSubTypes(subtype);

			if (SRPGlobalAction.EVENT_VIEW.equals(event)) {
				resultMap.put("CollateralSubTypeTrxValue", collateralSubTypeTrxVal);
				resultMap.put("InitialCollateralSubType", collateralSubTypeTrxVal.getCollateralSubTypes());
			}
			else {
				// if current status is other than ACTIVE & REJECTED, then
				// show workInProgress.
				// i.e. allow edit only if status is either ACTIVE or
				// REJECTED
				if (!((collateralSubTypeTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (collateralSubTypeTrxVal
						.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialCollateralSubType", collateralSubTypeTrxVal.getStagingCollateralSubTypes());
				}
				else {
					resultMap.put("CollateralSubTypeTrxValue", collateralSubTypeTrxVal);
				}

				resultMap.put("InitialCollateralSubType", subtype);
			}
		}

		resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
		resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}