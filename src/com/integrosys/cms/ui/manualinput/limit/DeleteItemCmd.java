/*
 * Created on 2007-2-17
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
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteItemCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "deletedLmtSec", "java.lang.String", REQUEST_SCOPE },
				{ "securityIdDel", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE }});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimit limit = (ILimit) (map.get("lmtDetailForm"));
			result.put("lmtDetailForm", limit);
			String secId = (String)map.get("securityIdDel");
			
			if(secId!=null){
				ICheckListDAO checkListDAO = CheckListDAOFactory.getCheckListDAO();
				int count = checkListDAO.getCountSecurityDocsInCheckList(Long.parseLong(secId));
				if(count>0){
					exceptionMap.put("secNotReq", new ActionMessage("security.documents.mapped.checklist",secId));
				}
			}
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		result.put("fundedAmount", map.get("fundedAmount"));
		result.put("nonFundedAmount", map.get("nonFundedAmount"));
		result.put("memoExposer", map.get("memoExposer"));
		result.put("sanctionedLimit", map.get("sanctionedLimit"));
		result.put("inrValue", map.get("inrValue"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
