package com.integrosys.cms.ui.newTat;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.newTat.bus.INewTatDAO;
import com.integrosys.cms.app.newTat.bus.INewTatJdbc;
import com.integrosys.cms.app.newTat.bus.NewTatDAOFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 $Author: Abhijit R $
 Command for list New TAT
 */
public class ListNewTatCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	

	public ListNewTatCmd() {
		
	}
	
	
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE },
				
				{"cusName", "java.lang.String", REQUEST_SCOPE },
			    {"cusId", "java.lang.String", REQUEST_SCOPE },
				{"caseIni", "java.lang.String", REQUEST_SCOPE },
				{"lastUpdate", "java.lang.String", REQUEST_SCOPE },
				{"rgn", "java.lang.String", REQUEST_SCOPE },
				{"sts", "java.lang.String", REQUEST_SCOPE },
				{"sgmnt", "java.lang.String", REQUEST_SCOPE },
				{"mdl", "java.lang.String", REQUEST_SCOPE },
				{"cusNameSearch", "java.lang.String", SERVICE_SCOPE },
			    {"cusIdSearch", "java.lang.String", SERVICE_SCOPE },
				{"caseIniSearch", "java.lang.String", SERVICE_SCOPE },
				{"lastUpdateSearch", "java.lang.String", SERVICE_SCOPE },
				{"rgnSearch", "java.lang.String", SERVICE_SCOPE },
				{"stsSearch", "java.lang.String", SERVICE_SCOPE },
				{"sgmntSearch", "java.lang.String", SERVICE_SCOPE },
				{"mdlSearch", "java.lang.String", SERVICE_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"newTatList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
	                {"newTatList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
	                //{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	                
	                {"cusName", "java.lang.String", REQUEST_SCOPE },
				    {"cusId", "java.lang.String", REQUEST_SCOPE },
					{"caseIni", "java.lang.String", REQUEST_SCOPE },
					{"lastUpdate", "java.lang.String", REQUEST_SCOPE },
					{"rgn", "java.lang.String", REQUEST_SCOPE },
					{"sts", "java.lang.String", REQUEST_SCOPE },
					{"sgmnt", "java.lang.String", REQUEST_SCOPE },
					{"mdl", "java.lang.String", REQUEST_SCOPE },
					{"cusNameSearch", "java.lang.String", SERVICE_SCOPE },
				    {"cusIdSearch", "java.lang.String", SERVICE_SCOPE },
					{"caseIniSearch", "java.lang.String", SERVICE_SCOPE },
					{"lastUpdateSearch", "java.lang.String", SERVICE_SCOPE },
					{"rgnSearch", "java.lang.String", SERVICE_SCOPE },
					{"stsSearch", "java.lang.String", SERVICE_SCOPE },
					{"sgmntSearch", "java.lang.String", SERVICE_SCOPE },
					{"mdlSearch", "java.lang.String", SERVICE_SCOPE },
					{"event", "java.lang.String", REQUEST_SCOPE },
	        });
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        String startIndexInner = (String) map.get("startIndexInner");
	        String event = (String) map.get("event");
	        int stindex = 0;
	        String cusName = (String) map.get("cusName");
	        String cusId = (String) map.get("cusId");
	        String caseIni = (String) map.get("caseIni");
	        String lastUpdate = (String) map.get("lastUpdate");
	        String rgn = (String) map.get("rgn");
	        String sts = (String) map.get("sts");
	        String sgmnt = (String) map.get("sgmnt");
	        String mdl = (String) map.get("mdl");
	        
	        String cusNameSearch = (String) map.get("cusNameSearch");
	        String cusIdSearch = (String) map.get("cusIdSearch");
	        String caseIniSearch = (String) map.get("caseIniSearch");
	        String lastUpdateSearch = (String) map.get("lastUpdateSearch");
	        String rgnSearch = (String) map.get("rgnSearch");
	        String stsSearch = (String) map.get("stsSearch");
	        String sgmntSearch = (String) map.get("sgmntSearch");
	        String mdlSearch = (String) map.get("mdlSearch");
	        if(event!=null && event.equals("list")){
	        	cusName="";
	        	cusId="";
	        	caseIni="";
	        	lastUpdate="";
	        	rgn="";
	        	sts="";
	        	sgmnt="";
	        	mdl="";
	        	cusNameSearch="";
	        	cusIdSearch="";
	        	caseIniSearch="";
	        	lastUpdateSearch="";
	        	rgnSearch="";
	        	stsSearch="";
	        	sgmntSearch="";
	        	mdlSearch="";
	        }
	        else if(event!=null && event.equals("maker_list_tat")){
	        	cusNameSearch=cusName;
	        	cusIdSearch=cusId;
	        	caseIniSearch=caseIni;
	        	lastUpdateSearch=lastUpdate;
	        	rgnSearch=rgn;
	        	stsSearch=sts;
	        	sgmntSearch=sgmnt;
	        	mdlSearch=mdl;
	        }
	        else{
	        	cusName=cusNameSearch;
	        	cusId=cusIdSearch;
	        	caseIni=caseIniSearch;
	        	lastUpdate=lastUpdateSearch;
	        	rgn=rgnSearch;
	        	sts=stsSearch;
	        	sgmnt=sgmntSearch;
	        	mdl=mdlSearch;
	        }
	        if(null==cusName||"null".equalsIgnoreCase(cusName))
	        	cusName="";
	 		if(null==cusId||"null".equalsIgnoreCase(cusId))
	 			cusId="";
	 		if(null==caseIni||"null".equalsIgnoreCase(caseIni))
	 			caseIni="";
	 		if(null==lastUpdate||"null".equalsIgnoreCase(lastUpdate))
	 			lastUpdate="";
	 		if(null==rgn||"null".equalsIgnoreCase(rgn))
	 			rgn="";
	 		if(null==sts||"null".equalsIgnoreCase(sts))
	 			sts="";
	 		if(null==sgmnt||"null".equalsIgnoreCase(sgmnt))
	 			sgmnt="";
	 		if(null==mdl||"null".equalsIgnoreCase(mdl))
	 			mdl="";
	 		
	 		
	 		
	 		cusName=cusName.trim();
	 		cusId=cusId.trim();
	 		caseIni=caseIni.trim();
	 		lastUpdate=lastUpdate.trim();
	 		rgn=rgn.trim();
	 		sts=sts.trim();
	 		sgmnt=sgmnt.trim();
	 		mdl=mdl.trim();
	 		
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
	        	ArrayList newTatListArray= new ArrayList(); 
	        	if(cusName.equals("")&&cusId.equals("")&&caseIni.equals("")&&lastUpdate.equals("")&&rgn.equals("")&&sts.equals("")&&sgmnt.equals("")&&mdl.equals(""))
	        	{ newTatListArray= (ArrayList)  NewTatDAOFactory.getNewTatDAO().getListNewTat();
	        	}else{
	        		newTatListArray= (ArrayList)  newTat.getFilteredActualTat(cusName, cusId, caseIni, lastUpdate, rgn, sts, sgmnt, mdl);
	        	}
	            SearchResult newTatList = new SearchResult(0, 10, newTatListArray.size(), newTatListArray);

	                  resultMap.put("newTatList", newTatList);
	        } catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        
	        resultMap.put("cusName",cusName);
            resultMap.put("cusId",cusId);
            resultMap.put("caseIni",caseIni);
            resultMap.put("lastUpdate",lastUpdate);
            resultMap.put("rgn", rgn);
            resultMap.put("sts", sts);
            resultMap.put("sgmnt",sgmnt);
            resultMap.put("segmentSearch",sgmnt);
            resultMap.put("mdl", mdl);
            resultMap.put("startIndexInner", startIndexInner);
            resultMap.put("cusNameSearch",cusNameSearch);
            resultMap.put("cusIdSearch",cusIdSearch);
            resultMap.put("caseIniSearch",caseIniSearch);
            resultMap.put("lastUpdateSearch",lastUpdateSearch);
            resultMap.put("rgnSearch", rgnSearch);
            resultMap.put("stsSearch", stsSearch);
            resultMap.put("sgmntSearch",sgmntSearch);
            resultMap.put("mdlSearch", mdlSearch);
	        
	        
	        
	        
	        return returnMap;
	    }
}



