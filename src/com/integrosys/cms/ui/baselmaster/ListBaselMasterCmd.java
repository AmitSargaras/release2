package com.integrosys.cms.ui.baselmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.baselmaster.proxy.BaselProxyManagerImpl;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListBaselMasterCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IBaselProxyManager baselProxy;
	
	
	
	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}

	public ListBaselMasterCmd(){
		
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"baselList", "java.util.ArrayList", SERVICE_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					 {"searchbaselName", "java.lang.String", REQUEST_SCOPE},
			         {"session.searchbaselName", "java.lang.String", SERVICE_SCOPE},
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
	        });
	    }
	   
	   public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        String startIndex = (String) map.get("startIndex");
	        int stindex = 0;
	        String searchbaselName="";
	        
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	
	        	
	        	List baselCommonList = new ArrayList(getBaselProxy().getAllActualCommon().getResultList());
	        	HashMap commonMap=new HashMap();
	        	//LinkedHashMap commonMap1=new LinkedHashMap();
		        if(baselCommonList!=null){
		        	for(int i=0;i<baselCommonList.size();i++){
		        		ICommonCodeEntry common=(OBCommonCodeEntry)baselCommonList.get(i);
		        		commonMap.put(common.getEntryCode(),common.getEntryCode());
		        	
		        		//commonMap.put(common.getEntryCode(),common.getEntryCode());
		        	}
		        	/*if(commonMap1!=null){
		        		List commonList=(List)commonMap1.values();
		        		for(int i=0;i<commonList.size();i++){
		        			ICommonCodeEntry common=(OBCommonCodeEntry)commonList.get(i);
		        			commonMap.put(common.getEntryCode(),common.getEntryCode());
		        		}
		        	}*/
		        }
	        	//baselCommonList= (SearchResult)  getBaselProxy().getAllActualCommon();
	        	
		        List baselList = new ArrayList(getBaselProxy().getAllActualBasel().getResultList());
	        	
		        /*if(baselList!=null){
		        	
			        	for(int i=0;i<baselList.size();i++){
			        		OBBaselMaster obBasel=(OBBaselMaster)baselList.get(i);
			        		if(!commonMap.containsKey(obBasel.getSystem())){
			        			baselList.remove(i);
			        		}
			        	}
			        
		        }*/
	        	
	            
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

	                  resultMap.put("baselList", baselList);
	        }catch (ComponentException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			} catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			resultMap.put("session.searchbaselName", searchbaselName);
			resultMap.put("searchbaselName", searchbaselName);
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }

}
