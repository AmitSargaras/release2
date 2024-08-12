package com.integrosys.cms.ui.todo;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class ReassignPendingCasesCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "reassignTo", "java.lang.String", REQUEST_SCOPE },
				{ "todoMapper", "[Ljava.lang.String;", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "reassignTo", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			String reassignTo = (String) map.get("reassignTo");
			String[] selectedItems = (String[]) map.get("todoMapper");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			limitProxy.makerReassignLimitProfileSegment(selectedItems, reassignTo, trxContext);

			result.put("reassignTo", reassignTo);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(COMMAND_RESULT_MAP, result);
		temp.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
