/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadSecDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "securityId", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "secDetailForm", "java.lang.Object", FORM_SCOPE }, { "wip", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			MISecurityUIHelper helper = new MISecurityUIHelper();
			SBMISecProxy proxy = helper.getSBMISecProxy();
			ICollateralTrxValue secTrxObj = null;
			DefaultLogger.debug(this, "****************** INSIDE ReadLmtDetailCmd EVENT IS: " + event);
			if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event)
					|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PROCESS.equals(event)) {
				String trxID = (String) (map.get("trxID"));
				DefaultLogger.debug(this, "********** Trasanction ID IS: " + trxID);
				secTrxObj = proxy.searchCollateralByTrxId(trxID);
			}
			else {
				String secID = (String) (map.get("securityId"));
				secTrxObj = proxy.searchCollateralByColId(secID);
			}
			result.put("secTrxObj", secTrxObj);
			if (CommonUtil.checkWip(event, secTrxObj)) {
				result.put("wip", "wip");
			}

			// for read event render form from original object
			// otherwise reder form from staging object
			ICollateral curSec = null;
			if (EventConstant.EVENT_READ.equals(event)) {
				curSec = secTrxObj.getCollateral();
			}
			else {
				curSec = secTrxObj.getStagingCollateral();
			}

			// for maker edit limit detail, we need to copy original object to
			// staging
			if (EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
				OBCollateral stgCol = new OBCollateral(secTrxObj.getCollateral());
				secTrxObj.setStagingCollateral(stgCol);
			}
			result.put("secDetailForm", curSec);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
