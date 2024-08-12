package com.integrosys.cms.ui.checklist;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;

public class BackToChecklistItemCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {
	
	public String[][] getParameterDescriptor() {
        return (new String[][] {
                { "checkListItem", ICheckListItem.class.getName() , SERVICE_SCOPE }
        });
	}
	
	public String[][] getResultDescriptor() {
        return (new String[][] {
               { "checkListItem", ICheckListItem.class.getName() , FORM_SCOPE},
               { "status", String.class.getName() , REQUEST_SCOPE },
               { TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), SERVICE_SCOPE },
   			   { IMG_ID_UNTAG_STATUS_MAP, Map.class.getName(), SERVICE_SCOPE },
   			   { UPLOADED_DMS_IMG_IDS, List.class.getName() , SERVICE_SCOPE },
   			   { ALL_IMG_IDS, LinkedList.class.getName() , SERVICE_SCOPE },
   			   { ALL_IMAGES_UPLOAD_ADD_MAP, Map.class.getName() , SERVICE_SCOPE }
        });
    }
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
	        resultMap.put("checkListItem", checkListItem);
	        resultMap.put("status", checkListItem.getItemStatus());
	        
	        resultMap.put(TAG_UNTAG_IMAGE_DTL_LIST, null);
			resultMap.put(IMG_ID_UNTAG_STATUS_MAP, null);
			resultMap.put(UPLOADED_DMS_IMG_IDS, null);
			resultMap.put(ALL_IMG_IDS, null);
			resultMap.put(ALL_IMAGES_UPLOAD_ADD_MAP, null);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught : ", e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
