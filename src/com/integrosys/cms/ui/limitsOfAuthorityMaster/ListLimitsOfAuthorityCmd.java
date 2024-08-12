package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.proxy.IProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ListLimitsOfAuthorityCmd extends AbstractCommand implements ICommonEventConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public ListLimitsOfAuthorityCmd() {}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", String.class.getName(), REQUEST_SCOPE },
				{ "totalCount", String.class.getName(), REQUEST_SCOPE },
			};
	}
	
	public String[][] getResultDescriptor() {
        return new String[][]{
                { "resultList", List.class.getName(), REQUEST_SCOPE },
				{ "event", String.class.getName(), REQUEST_SCOPE },
				{ "startIndex", String.class.getName(), REQUEST_SCOPE },
        };
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        String startIndex = (String) map.get("startIndex");
        int stindex = 0;
        
        try {
        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
        	SearchResult resultList = new SearchResult();
        	
        	resultList =  getProxyManager().getAllActual();
        	
            if (StringUtils.isBlank(startIndex)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					startIndex = String.valueOf(stindex);
					resultMap.put("startIndex", startIndex);
				}
				else {
					stindex = Integer.parseInt(globalStartIndex);
					startIndex = globalStartIndex;
					resultMap.put("startIndex", startIndex);
				}
			}
			else {
				stindex = Integer.parseInt(startIndex);
				resultMap.put("startIndex", startIndex);
			}

            resultMap.put("resultList", resultList);
            
        }catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
	    
}