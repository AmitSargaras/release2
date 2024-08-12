/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.exception.EntityInsertException;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.bizstructure.app.bus.OBTeamMember;
import com.integrosys.component.bizstructure.app.bus.OBTeamMembership;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.UserException;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/04 09:53:54 $ Tag: $Name: $
 */
public class CheckerApproveAddUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerApproveAddUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBUserTrxValue userTrxVal = (OBUserTrxValue) map.get("CommonUserTrxValue");
		CMSUserProxy proxy = new CMSUserProxy();
		ICommonUser commonUser=null;
		boolean existingUser=true;
		DefaultLogger.debug(this, "Inside doExecute()");
		if(userTrxVal.getStagingUser().getLoginID()!=null && !(userTrxVal.getStagingUser().getLoginID().trim().equals(""))){
			try {
				CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
				StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
				criteriaOB.setEmployeeId(userTrxVal.getStagingUser().getEmployeeID().trim());
				criteriaOB.setLoginId(userTrxVal.getStagingUser().getLoginID().trim());
				criteria.setCriteria(criteriaOB);
				criteria.setNItems(10);
				SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
				
				if(searchResult !=null){
					if(searchResult.getNItems()==0){
						existingUser=false;
					}
				}else{
					existingUser=false;
				}
				// commonUser= proxy.getUser(userTrxVal.getStagingUser().getLoginID());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		if(existingUser){
			exceptionMap.put("loginIDDuplicateError", new ActionMessage("error.string.duplicateLoginId"));
			ICompTrxResult trxResult = null;
			resultMap.put("request.ITrxResult", trxResult);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
			
		}else{
		try {
			
			ICompTrxResult trxResult = proxy.checkerApproveCreateUser(trxContext, userTrxVal);
			
			// Added by archana for adding team for user(HDFC requirement) - Start
			OBUserTrxValue trxVal = (OBUserTrxValue) trxResult.getTrxValue();
			long teamTypeMembershipId = trxVal.getUser().getTeamTypeMembership().getMembershipID();
			int loopCount = 1;
			long[] teamTypeMembershipIdArr = new long[2];
			long tempTeamTypeMembershipId = 0;
			if(teamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER){
				tempTeamTypeMembershipId = teamTypeMembershipId;
				loopCount = 2;
				teamTypeMembershipIdArr[0]= ICMSConstant.CPU_MAKER;
				teamTypeMembershipIdArr[1]= ICMSConstant.CPU_CHECKER;
			}else if(teamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER){
				loopCount = 2;
				tempTeamTypeMembershipId = teamTypeMembershipId;
				teamTypeMembershipIdArr[0]= ICMSConstant.CPU_ADMIN_MAKER;
				teamTypeMembershipIdArr[1]= ICMSConstant.CPU_ADMIN_CHECKER;
			}else if(teamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER_WFH){
				tempTeamTypeMembershipId = teamTypeMembershipId;
				loopCount = 2;
				teamTypeMembershipIdArr[0]= ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH;
				teamTypeMembershipIdArr[1]= ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH;
			}else if(teamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER_WFH){
				loopCount = 2;
				tempTeamTypeMembershipId = teamTypeMembershipId;
				teamTypeMembershipIdArr[0]= ICMSConstant.TEAM_TYPE_SC_MAKER_WFH;
				teamTypeMembershipIdArr[1]= ICMSConstant.TEAM_TYPE_SC_CHECKER_WFH;
			}
			
			for(int k = 0;k<loopCount;k++){
				if(tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER || tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER 
						||tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER_WFH|| tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER_WFH){
					teamTypeMembershipId = teamTypeMembershipIdArr[k];
				}
				
				// to enchance the performance of user creation : By Abhijit R 21-05-2012
				/*ICMSTeamProxy teamProxy = new CMSTeamProxy();
				ITeamType[] teamTypearr = teamProxy.getNodeTeamTypes();
				long teamId = ICMSConstant.LONG_INVALID_VALUE;
				boolean found = false;
				for(int i =0;i<teamTypearr.length;i++){
					
				 * Get teams specific to the teamtype
				 * In case of HDFC CLIMS there will be only one team for a particular team type 
				 * so the first team will be considered for further processing.
				 * For future please not that incase there are multiple teams for a particular teamtpye then
				 * the team id needs to be got form the UI itself. Appropriate changes needs to be done.
				 * 
				ITeam[] teams = teamProxy.getTeamsByTeamType(teamTypearr[i].getTeamTypeID());
				
				ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
				for (int j = 0; j < teamTypeMembershipArr.length; j++) {
					if(teamTypeMembershipArr[j].getMembershipID()== teamTypeMembershipId){
						if(teams!=null){
						   if(teams.length!=0){
							teamId = teams[0].getTeamID();
						found = true;
						break;
						}
						}
					}
				}
				if(found)
					break;
			}
			if(!found){
				throw new UserException("Role selected is invalid");
			}
			if(teamId == ICMSConstant.LONG_INVALID_VALUE)
				throw new UserException("Invalid team");
			ITeamTrxValue teamTrxVal = teamProxy.getTrxTeamByID(teamId);
			OBTeam team = (OBTeam)teamTrxVal.getTeam();
			ITeamTypeMembership[] teamTypeMemberShipArr = team.getTeamType().getTeamTypeMemberships();
			ITeamMembership[] teamMemberships = team.getTeamMemberships();
			ITeamMembership[] newTm = new ITeamMembership[teamTypeMemberShipArr.length];
			
			//validate if teamMemberships.lenght and teamTypeMemberShipArr.length is equal

			if(teamMemberships.length == teamTypeMemberShipArr.length){
				for (int i = 0; i < newTm.length; i++) {
					newTm[i] = new OBTeamMembership();
					AccessorUtil.copyValue(teamMemberships[i], newTm[i]);
				}
			}else {
				if (teamMemberships.length == 0) {
					for (int i = 0; i < teamTypeMemberShipArr.length; i++) {
						newTm[i] = new OBTeamMembership();
						((OBTeamMembership) newTm[i]).setTeamTypeMembership(teamTypeMemberShipArr[i]);
					}
				} else {			
					for (int i = 0; i < teamTypeMemberShipArr.length; i++) {
						newTm[i] = new OBTeamMembership();
						boolean teamMembershipAdded = false;
						for (int j = 0; j < teamMemberships.length; j++) {
							if (teamMemberships[j].getTeamTypeMembership().getMembershipID() == teamTypeMemberShipArr[i].getMembershipID()) {
								AccessorUtil.copyValue(teamMemberships[j], newTm[i]);
								teamMembershipAdded = true;
								break;
							}
						}
						if (!teamMembershipAdded)
							((OBTeamMembership) newTm[i]).setTeamTypeMembership(teamTypeMemberShipArr[i]);
	
					}
				}
			}
			OBTeamMember newTeamMember = new OBTeamMember();
			newTeamMember.setTeamMemberUser(trxVal.getUser());
			
			for (int i = 0; i < newTm.length; i++) {
				if (newTm[i].getTeamTypeMembership().getMembershipID() == teamTypeMembershipId) {
					ITeamMember[] oldTM = newTm[i].getTeamMembers();
					if (oldTM != null) {
						ITeamMember[] newTM = new ITeamMember[oldTM.length +1];
						for (int j = 0; j < oldTM.length; j++) {
							newTM[j] = oldTM[j];
						}
						
						newTM[oldTM.length] = newTeamMember;
						
						((OBTeamMembership) newTm[i]).setTeamMembers(newTM);
					}
					else {
						ITeamMember[] otm = new ITeamMember[1];
						otm[0] = newTeamMember;
						((OBTeamMembership) newTm[i]).setTeamMembers(otm);
					}
					break;
				}
			}
			team.setTeamMemberships(newTm);
			ICompTrxResult trxResultteamMaker = teamProxy.makerUpdateTeam(trxContext, teamTrxVal, team);
			teamTrxVal = (ITeamTrxValue)trxResultteamMaker.getTrxValue();
			ICompTrxResult trxResultteamChecker = teamProxy.checkerApproveUpdateTeam(trxContext, teamTrxVal);;*/
				

				StdUserDAO  stdUserDAO = new StdUserDAO();
				long teamMembershipId= stdUserDAO.getTeamMembershipIdByTeamTypeMembershipId(teamTypeMembershipId);
				stdUserDAO.createTeamMember(trxVal.getUser().getUserID(), teamMembershipId);
				
				
				
			}
			// Added by archana for adding team for user(HDFC requirement) - End
			
			resultMap.put("request.ITrxResult", trxResult);
		}
		catch (RemoteException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (UserException e) {
			DefaultLogger.debug(this, "got exception in doExecute - error code ---" + e.getErrorCode());
			// e.printStackTrace();
			if (e.getRootCause() != null &&
					e.getRootCause() instanceof EntityInsertException && 
					((EntityInsertException)e.getRootCause()).getErrorCode() != null) { 
					// exceptionMap.put("updateError", new
					// ActionMessage("error.string." + e.getErrorCode()));
					exceptionMap.put("updateError", new ActionMessage(((EntityInsertException)e.getRootCause()).getErrorCode()));
			} else {
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}
	}
}
