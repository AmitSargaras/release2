/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckerRejectLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxID", "java.lang.String", SERVICE_SCOPE },
				{ "limitId", "java.lang.String", SERVICE_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			 String remarks = (String) map.get("remarks");
			 String trxID = (String) (map.get("trxID"));
				String lmtID = (String) (map.get("limitId"));
			 if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("limitRemarksError", new ActionMessage("error.reject.remark"));
					ILimitTrxValue trxValueInValidate = null;
					
					result.put("trxId", trxID);
					result.put("limitId", lmtID);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					return temp;
				}else{
					 try {
	            ctx.setRemarks(remarks);
			MILimitUIHelper helper = new MILimitUIHelper();
			helper.setTrxLocation(ctx, lmtTrxObj.getStagingLimit());
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			ICMSTrxResult res = proxy.checkerRejectLmtTrx(ctx, lmtTrxObj);
			result.put("request.ITrxResult", res);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
