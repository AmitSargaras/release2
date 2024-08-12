package com.integrosys.cms.ui.newTat;

/**
 *@author abhijit.rudrakshawar
 *$ Command for Listing Customer
 */

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.newTat.bus.INewTat;
import com.integrosys.cms.app.newTat.bus.INewTatDAO;
import com.integrosys.cms.app.newTat.bus.INewTatJdbc;
import com.integrosys.cms.app.newTat.bus.NewTatDAOFactory;
import com.integrosys.cms.app.newTat.bus.OBNewTat;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class ShowAuditTrailDetailsCommand extends AbstractCommand {

	public ShowAuditTrailDetailsCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				//{ "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{ "caseid", "java.lang.String", REQUEST_SCOPE }, 
				
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{"regionMap", "java.util.Map",SERVICE_SCOPE},
				{ "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "auditList", "java.util.List", REQUEST_SCOPE },
				{ "usersMap", "java.util.Map", REQUEST_SCOPE },
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{"rmMap", "java.util.Map",SERVICE_SCOPE},

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		//INewTat tat = (INewTat) map.get("newTatObj");
		String event = (String) map.get("event");
		String caseid = (String) map.get("caseid");
		String startIndexInner = (String) map.get("startIndexInner");
		String auditStartIndex = (String) map.get("auditStartIndex");
		
		INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
    	ArrayList newTatListArray= new ArrayList(); 
    	List users= new ArrayList(); 
    		newTatListArray= (ArrayList)  newTat.getAuditTrailDetail(caseid);
		
    		users= (new StdUserDAO()).getRmBranchCpuUser();
    		 HashMap lbValmap = new HashMap();
    		for(int i=0;i<users.size();i++){
    			List a = (List)users.get(i);
    			String id = (String)a.get(0);
						String val =(String)a.get(1);
						lbValmap.put(id, val);
			}
    		
		//INewTat newTat = (INewTat) NewTatDAOFactory.getNewTatDAO().createTAT("actualNewTAT", tat);
		result.put("regionMap", getRegionMap());
		if(event!=null && !"".equals(event))
		{
			result.put("event", event);
		}
		//result.put("rmMap", getRelationshipManagerMap());
		result.put("auditList", newTatListArray);
		result.put("usersMap", lbValmap);
		result.put("startIndexInner", startIndexInner);
		result.put("auditStartIndex", auditStartIndex);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	private HashMap getRegionMap() {
		HashMap lbValmap = new HashMap();
		try {

			IRegionDAO regionDAO = (IRegionDAO) BeanHouse.get("regionDAO");
			Collection idList = (regionDAO.listRegion("", "")).getResultList();			
			Iterator itr =idList.iterator();
			while(itr.hasNext()){
				IRegion region = (IRegion)itr.next();
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				lbValmap.put(id, val);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lbValmap;
	}


	private HashMap getRelationshipManagerMap() {
		HashMap lbValmap = new HashMap();
		try {

			IRelationshipMgrDAO relationshipMgrDAO = (IRelationshipMgrDAO) BeanHouse.get("relationshipMgrDAO");
			Collection idList = relationshipMgrDAO.getRelationshipMgrList("").getResultList();			
			Iterator itr =idList.iterator();
			while(itr.hasNext()){
				IRelationshipMgr relationshipMgr = (IRelationshipMgr)itr.next();
				String id = Long.toString(relationshipMgr.getId());
				String val = relationshipMgr.getRelationshipMgrName();
				lbValmap.put(id, val);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lbValmap;
	}
}
