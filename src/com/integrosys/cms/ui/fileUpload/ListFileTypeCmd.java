package com.integrosys.cms.ui.fileUpload;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListFileTypeCmd extends AbstractCommand implements ICommonEventConstant{
	
public ListFileTypeCmd(){
		
	}


public String[][] getParameterDescriptor() {
	return new String[][] {
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "totalCount", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
			{ "locale", "java.util.Locale", REQUEST_SCOPE },
			{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
			
			
		};
}
   public String[][] getResultDescriptor() {
        return (new String[][]{
                {"componentList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                { "event", "java.lang.String", REQUEST_SCOPE },
				 {"searchcomponentName", "java.lang.String", REQUEST_SCOPE},
		            {"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
        });
    }
   
   public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
       String event=(String)map.get("event");
		resultMap.put("event", event);		
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

}
