package com.integrosys.cms.ui.checklist;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;

public class MapChecklistItemCommand extends AbstractCommand implements ICommonEventConstant {
	
	public String[][] getParameterDescriptor() {
        return (new String[][] {
                { "checkListItem", ICheckListItem.class.getName() ,FORM_SCOPE}
        });
	}
	
	public String[][] getResultDescriptor() {
        return (new String[][] {
               { "checkListItem", ICheckListItem.class.getName() , SERVICE_SCOPE }
        });
    }
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
        resultMap.put("checkListItem", checkListItem);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
