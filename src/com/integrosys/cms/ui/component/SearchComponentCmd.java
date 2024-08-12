package com.integrosys.cms.ui.component;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.base.businfra.search.SearchResult;

public class SearchComponentCmd extends AbstractCommand implements ICommonEventConstant {

	private IComponentProxyManager componentProxy;
	
	
	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}

	public void setComponentProxy(IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"searchcomponentName", "java.lang.String", REQUEST_SCOPE},
	            {"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
	            {"componentList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
	          
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{"componentList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
	            {"event", "java.lang.String", SERVICE_SCOPE}
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        boolean execute = false;
        try {
        	String searchcomponentName="";
        	String startIndex="";
        	String event=(String)map.get("event");
        	if("search_component".equals(event)){
        		searchcomponentName=(String)map.get("searchcomponentName");
        		
        		startIndex=(String)map.get("startIndex");
        	}
        	if("search_component_next".equals(event)){
        		if(searchcomponentName==null||"".equals(searchcomponentName)){
        			searchcomponentName=(String)map.get("session.searchcomponentName");
        		}
        		startIndex=(String)map.get("startIndex");
        	}
        	if("return_search_component".equals(event)){
        		searchcomponentName=(String)map.get("session.searchcomponentName");
        		startIndex=(String)map.get("session.startIndex");
        	}
        	/*if(searchcomponentName!=null && !searchcomponentName.trim().equals("")){
        		boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchcomponentName);
        		if( searchTextFlag){
        			execute=true;        			
					//exceptionMap.put("componentName", new ActionMessage("error.string.invalidCharacter"));					
					resultMap.put("componentList", map.get("componentList"));
					resultMap.put("session.searchcomponentName", searchcomponentName);
		        	resultMap.put("event", event);
		        	resultMap.put("session.startIndex",startIndex);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			       
				}
        		
        		
        	}     */  	
        	
        	 if(searchcomponentName==null || searchcomponentName.trim().equals("")){
        		 SearchResult componentList= (SearchResult)  getComponentProxy().getAllActualComponent();
        		resultMap.put("componentList", componentList);
				resultMap.put("session.searchcomponentName", searchcomponentName);
	        	resultMap.put("event", event);
	        	resultMap.put("session.startIndex",startIndex);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        		
        	}
        	 else if(searchcomponentName!=null && !searchcomponentName.trim().equals("")){
	        	SearchResult componentList=getComponentProxy().getSearchComponentList(searchcomponentName.trim());
	        	resultMap.put("componentList", componentList);
	        	resultMap.put("session.searchcomponentName", searchcomponentName);
	        	resultMap.put("event", event);
	        	resultMap.put("session.startIndex",startIndex);
	        	  returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	              returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        	
        	}
        }
        catch(Exception ex){
        	 DefaultLogger.error(this, "got exception in doExecute" ,ex);
        	
        }
      
        return returnMap;
	}
	
}
