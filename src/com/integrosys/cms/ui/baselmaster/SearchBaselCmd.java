package com.integrosys.cms.ui.baselmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;

public class SearchBaselCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IBaselProxyManager baselProxy;

	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}
	
	public SearchBaselCmd(){
		
	}
	
public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"searchbaselName", "java.lang.String", REQUEST_SCOPE},
	            {"session.searchbaselName", "java.lang.String", SERVICE_SCOPE},
	            //{"baselList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
	          
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{"baselList", "java.util.ArrayList", SERVICE_SCOPE},
				{"session.searchbaselName", "java.lang.String", SERVICE_SCOPE},
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
        	String searchbaselName="";
        	String startIndex="";
        	String event=(String)map.get("event");
        	if("search_basel".equals(event)){
        		searchbaselName=(String)map.get("searchbaselName");
        		
        		startIndex=(String)map.get("startIndex");
        	}
        	if("search_basel_next".equals(event)){
        		if(searchbaselName==null||"".equals(searchbaselName)){
        			searchbaselName=(String)map.get("session.searchbaselName");
        		}
        		startIndex=(String)map.get("startIndex");
        	}
        	if("return_search_basel".equals(event)){
        		searchbaselName=(String)map.get("session.searchbaselName");
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
        	
        	 if(searchbaselName==null || searchbaselName.trim().equals("")){
        		 
        		 List baselCommonList = new ArrayList(getBaselProxy().getAllActualCommon().getResultList());
 	        	HashMap commonMap=new HashMap();
 	        	//LinkedHashMap commonMap1=new LinkedHashMap();
 		        if(baselCommonList!=null){
 		        	for(int i=0;i<baselCommonList.size();i++){
 		        		ICommonCodeEntry common=(OBCommonCodeEntry)baselCommonList.get(i);
 		        		commonMap.put(common.getEntryCode(),common.getEntryCode());
 		        	
 		        		//commonMap.put(common.getEntryCode(),common.getEntryCode());
 		        	}
 		        	 List baselList = new ArrayList(getBaselProxy().getAllActualBasel().getResultList());
 		        	/* if(baselList!=null){
 			        	
 			        	for(int i=0;i<baselList.size();i++){
 			        		OBBaselMaster obBasel=(OBBaselMaster)baselList.get(i);
 			        		if(!commonMap.containsKey(obBasel.getSystem())){
 			        			baselList.remove(i);
 			        		}
 			        	}
 			        
 		        }*/
        		resultMap.put("baselList", baselList);
				resultMap.put("session.searchbaselName", searchbaselName);
	        	resultMap.put("event", event);
	        	resultMap.put("session.startIndex",startIndex);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        		
 		        }
        	 }
        	 else if(searchbaselName!=null && !searchbaselName.trim().equals("")){
	        	SearchResult baselList=getBaselProxy().getSearchBasel(searchbaselName.trim());
	        	resultMap.put("baselList", baselList.getResultList());
	        	resultMap.put("session.searchbaselName", searchbaselName);
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
