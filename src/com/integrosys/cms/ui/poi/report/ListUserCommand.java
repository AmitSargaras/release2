/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.poi.report;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.user.MaintainUserUitl;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class ListUserCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListUserCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "searchLoginID", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }
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
				{ "UserList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter", SERVICE_SCOPE },
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",SERVICE_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBFilter filter = (OBFilter) map.get("reportFormObj");
		if(filter.getReportId()!=null)
			result.put("reportFormObj", filter);
		
		String startIdx = (String) map.get("startIndex");
		if(startIdx==null){
			startIdx="0";
		}
		
		CommonUserSearchCriteria globalCriteria = (CommonUserSearchCriteria)map.get("User_SearchCriteria");
		
		String searchText=(String) map.get("searchLoginID");
		if(searchText==null){
			searchText=globalCriteria.getCriteria().getLoginId();
		}else{
			String errorCode="";
			if(ASSTValidator.isValidAlphaNumStringWithoutSpace(searchText)){
				exceptionMap.put("userNameError", new ActionMessage("error.string.invalidCharacter"));
			}else if (!(errorCode=Validator.checkString(searchText, true, 1, 12)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("userNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),1, 12 + ""));
			}
		}
		
		
//		CommonUserSearchCriteria searchCriteria= new CommonUserSearchCriteria();
		ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		CommonUserSearchCriteria sc = MaintainUserUitl.createEmptySearchCriteria(user.getLoginID(), null);
		try{
			OBCommonUserSearchCriteria obsc = sc.getCriteria();
				obsc.setLoginId(searchText);
				sc.setStartIndex(Integer.parseInt(startIdx));
				sc.setNItems(10);
				sc.setCriteria(obsc);
				
				SearchResult sr = CMSStdUserProxyFactory.getUserProxy().searchUsers(sc);
				result.put("UserList", sr);
				result.put("User_SearchCriteria", sc);
				
			}
			catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("failed to search User using search criteria '" + sc + "'");
				cpe.initCause(e);
				throw cpe;
			}


		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
