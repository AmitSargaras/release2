/*
 * Created on 2007-2-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMasterJdbc;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MakerSaveLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE } });

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
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			ILimit lmt = (ILimit) (map.get("lmtDetailForm"));
			String isCreate = (String) (map.get("isCreate"));
			MILimitUIHelper helper = new MILimitUIHelper();
			helper.setTrxLocation(ctx, lmtTrxObj.getStagingLimit());
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			ICMSTrxResult res = null;
			Boolean chkResult = helper.checkSecurityNotReq(lmt);
			if ((chkResult != null) && chkResult.booleanValue()) {
				// security is not required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secNotReq"));
			}
			if ((chkResult != null) && !chkResult.booleanValue()) {
				// security is required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secReq"));
			}
			

			String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(lmtTrxObj, null, null);
			lmtTrxObj.setMinEmployeeGrade(minGrade);


			if ("y".equals(isCreate)) {
				if (proxy.checkDuplicateLmt(lmtTrxObj.getStagingLimit().getLimitRef())) {
					exceptionMap.put("miLimitError", new ActionMessage("error.miLimit.duplicateLimitId"));
				}
				if (exceptionMap.size() == 0) {
					res = proxy.createLimitTrx(ctx, lmtTrxObj, true);
					result.put("request.ITrxResult", res);
				}
			}
			else if (exceptionMap.size() == 0) {
				res = proxy.makerUpdateLmtTrx(ctx, lmtTrxObj, true);
				result.put("request.ITrxResult", res);
			}
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
