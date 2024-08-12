/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author Administrator <p/> TODO To change the template for this generated
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakerUpdateSecDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "secDetailForm", "java.lang.Object", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			DefaultLogger.debug(this, ">>>> In MakerUpdateSecDetailCmd.java");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICollateralTrxValue secTrxObj = (ICollateralTrxValue) map.get("secTrxObj");
			ICollateral col = (ICollateral) (map.get("secDetailForm"));

			if ((col.getPledgors() == null) || (col.getPledgors().length == 0)) {
				exceptionMap.put("pledgorMandatory", new ActionMessage("error.no.entries"));
			}
			else {
				for (int i = 0; i < col.getPledgors().length; i++) {
					for (int j = 0; j < col.getPledgors().length; j++) {
						if ((i != j) && col.getPledgors()[i].getLegalID().equals(col.getPledgors()[j].getLegalID())) {
							exceptionMap.put("pledgorMandatory", new ActionMessage("error.entries.duplicate"));
						}
					}
				}
			}

			MISecurityUIHelper helper = new MISecurityUIHelper();
			ICollateral stagingCol = secTrxObj.getStagingCollateral();
			helper.setTrxLocation(ctx, stagingCol);
			helper.setPledgorLocation(stagingCol);
			secTrxObj.setStagingCollateral(helper.getCollateralBySubtype(stagingCol));
			SBMISecProxy proxy = helper.getSBMISecProxy();
			ICMSTrxResult res = proxy.makerUpdateCollateralTrx(ctx, secTrxObj, false);

			result.put("request.ITrxResult", res);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
