/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
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
public class MakerCreateLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE }, });

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
			MILimitUIHelper helper = new MILimitUIHelper();
			helper.setTrxLocation(ctx, lmtTrxObj.getStagingLimit());
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			if (proxy.checkDuplicateLmt(lmtTrxObj.getStagingLimit().getLimitRef())) {
				exceptionMap.put("miLimitError", new ActionMessage("error.miLimit.duplicateLimitId"));
			}

			Boolean chkResult = helper.checkSecurityNotReq(lmt);
			if ((chkResult != null) && chkResult.booleanValue()) {
				// security is not required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secNotReq"));
			}
			if ((chkResult != null) && !chkResult.booleanValue()) {
				// security is required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secReq"));
			}
			if("Y".equals(lmt.getIsReleased())){
				if(!(lmt.getLimitSysXRefs()!=null && lmt.getLimitSysXRefs().length>0)){
					exceptionMap.put("lineDetailsError", new ActionMessage("error.line.details.mandatory.if.released"));
				}
			}
			
			if("Y".equals(lmt.getIsSecured())){
				if(!(lmt.getCollateralAllocations()!=null && lmt.getCollateralAllocations().length>0)){
					exceptionMap.put("securityMappingDetailsError", new ActionMessage("error.securityMapping.details.mandatory.if.secured"));
				}
			}
			if (exceptionMap.size() == 0) {
				
				String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(lmtTrxObj, null, null);
				lmtTrxObj.setMinEmployeeGrade(minGrade);
				
				helper.syncBankingArrangementAtLineFromFacility(lmtTrxObj.getStagingLimit());
				ICMSTrxResult res = proxy.createLimitTrx(ctx, lmtTrxObj, false);
				result.put("request.ITrxResult", res);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
