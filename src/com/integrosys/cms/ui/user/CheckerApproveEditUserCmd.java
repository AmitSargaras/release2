/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserDAO;
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
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.UserException;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/28 04:35:25 $ Tag: $Name: $
 */
public class CheckerApproveEditUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditUserCmd() {
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "isUnlock", "java.lang.String", REQUEST_SCOPE }		
		});
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
		OBCommonUser user= (OBCommonUser) userTrxVal.getUser();
		long oldTeamTypeMembershipId = user.getTeamTypeMembership().getMembershipID();
		long oldMembershipTypeId =user.getTeamTypeMembership().getMembershipType().getMembershipTypeID();
		OBCommonUser stageUser= (OBCommonUser) userTrxVal.getStagingUser();
		String isUnlock=(String)map.get("isUnlock");
		
		try {
			if(user.getTeamTypeMembership().getMembershipID()==stageUser.getTeamTypeMembership().getMembershipID()){
				
				CMSUserProxy proxy = new CMSUserProxy();
				ICompTrxResult trxResult = proxy.checkerApproveUpdateUser(trxContext, userTrxVal);
				resultMap.put("request.ITrxResult", trxResult);
				
				updateUnlock(user.getUserID(),isUnlock);
				
			}else{
				try{
				CMSUserProxy proxy = new CMSUserProxy();
				ICompTrxResult trxResult = proxy.checkerApproveUpdateUser(trxContext, userTrxVal);
				
				updateUnlock(user.getUserID(),isUnlock);
				
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
				removeUsersDirectly( user.getUserID());
				
				StdUserDAO  stdUserDAO = new StdUserDAO();
				
				
				for(int k = 0;k<loopCount;k++){
					if(tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER || tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER 
							||tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER_WFH|| tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER_WFH){
						teamTypeMembershipId = teamTypeMembershipIdArr[k];
					}
					long teamMemberShipId =stdUserDAO.getTeamMembershipIdByTeamTypeMembershipId(teamTypeMembershipId);
					
					stdUserDAO.createTeamMember(user.getUserID(), teamMemberShipId);
				
				}
				
				
				
				
				
				/* Removing code for updating team memberships
				 * Now using direct updation using DBUTIL to enhance performance.
				 */
				
				
				
/*				for(int k = 0;k<loopCount;k++){
					if(tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER || tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER ){
						teamTypeMembershipId = teamTypeMembershipIdArr[k];
					}
				ICMSTeamProxy teamProxy = new CMSTeamProxy();
				//ITeamType[] teamTypearr = teamProxy.getNodeTeamTypes();
				
				 //removeUsers( trxContext,user.getUserID(),oldMembershipTypeId,oldTeamTypeMembershipId,teamProxy);
				
				long teamId = ICMSConstant.LONG_INVALID_VALUE;
				//boolean found = false;
				
				
				ITeam teamNew= teamProxy.getTeamByUserID(trxVal.getUser().getUserID());
				if(teamNew!=null){
				teamId=teamNew.getTeamID();
				//found=true;
				
				}else{
					throw new UserException("Role selected is invalid");
				}
				
				// For HDFC : To improve User Performance issue.
				for(int i =0;i<teamTypearr.length;i++){
						
					 * Get teams specific to the teamtype
					 * In case of HDFC CLIMS there will be only one team for a particular team type 
					 * so the first team will be considered for further processing.
					 * For future please not that incase there are multiple teams for a particular teamtpye then
					 * the team id needs to be got form the UI itself. Appropriate changes needs to be done.
					  
					ITeam[] teams = teamProxy.getTeamsByTeamType(teamTypearr[i].getTeamTypeID());
					
					ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
					for (int j = 0; j < teamTypeMembershipArr.length; j++) {
						if(teamTypeMembershipArr[j].getMembershipID()== teamTypeMembershipId){
							teamId = teams[0].getTeamID();
							found = true;
							break;
						}
					}
					if(found)
						break;
				}
				if(!found){
					throw new UserException("Role selected is invalid");
				}
				
				
				StdUserDAO  stdUserDAO = new StdUserDAO();
				
				teamId=stdUserDAO.getTeamByTeamTypeMembershipId(trxVal.getUser().getTeamTypeMembership().getMembershipID());
				
				
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
				
				
				
				//removeUsers(team,user.getUserID(),oldMembershipTypeId);
				
				ICompTrxResult trxResultteamMaker = teamProxy.makerUpdateTeam(trxContext, teamTrxVal, team);
				teamTrxVal = (ITeamTrxValue)trxResultteamMaker.getTrxValue();
				ICompTrxResult trxResultteamChecker = teamProxy.checkerApproveUpdateTeam(trxContext, teamTrxVal);
				
				
				
				// removeUsers( trxContext,teamTypearr,user.getUserID(),oldMembershipTypeId,oldTeamTypeMembershipId);
				}*/
				
				
				
				/*
				*
				*
				*/
				// Added by archana for adding team for user(HDFC requirement) - End
				resultMap.put("request.ITrxResult", trxResult);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
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
					e.getRootCause() instanceof AuthenticationException && 
					((AuthenticationException)e.getRootCause()).getErrorCode() != null) { 
					// exceptionMap.put("updateError", new
					// ActionMessage("error.string." + e.getErrorCode()));
					exceptionMap.put("updateError", new ActionMessage("error.string."+((AuthenticationException)e.getRootCause()).getErrorCode()));
			} else {
				throw (new CommandProcessingException(e.getMessage()));
			}			

		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
/*	private void removeUsers(OBTeam team, long userId, long membershipTypeId) {
//		List userList = Arrays.asList(users);
		ITeamMembership[] tmi = team.getTeamMemberships();
		OBTeamMembership[] tm = new OBTeamMembership[tmi.length];
		for (int i = 0; i < tm.length; i++) {
			tm[i] = new OBTeamMembership();
			AccessorUtil.copyValue(tmi[i], tm[i]);
		}
		for (int i = 0; i < tm.length; i++) {
			if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == membershipTypeId) {
				ArrayList remainingUsers = new ArrayList();
				ITeamMember[] imems = tm[i].getTeamMembers();
				if ((imems != null) && (imems.length > 0)) {
					for (int j = 0; j < imems.length; j++) {
						if (userId!=imems[j].getTeamMemberUser().getUserID()) {
							remainingUsers.add(imems[j]);
						}
					}
				}
				OBTeamMember[] remainingMembers = (OBTeamMember[]) remainingUsers
						.toArray(new OBTeamMember[remainingUsers.size()]);
				tm[i].setTeamMembers(remainingMembers);
			}
		}
		team.setTeamMemberships(tm);
	}*/
	
	private void removeUsers(OBTrxContext trxContext,  long userId, long membershipTypeId,long oldTeamTypeMembershipId ,ICMSTeamProxy teamProxy)throws Exception {
//		List userList = Arrays.asList(users);
		try{
		//ICMSTeamProxy teamProxy = new CMSTeamProxy();
		long teamId = ICMSConstant.LONG_INVALID_VALUE;
		//boolean found = false;
		
		
		
		ITeam teamNew= teamProxy.getTeamByUserID(userId);
		if(teamNew!=null){
		teamId=teamNew.getTeamID();
		//found=true;
		
		}else{
			throw new UserException("Role selected is invalid");
		}
		/*for(int i =0;i<teamTypearr.length;i++){
			
			 * Get teams specific to the teamtype
			 * In case of HDFC CLIMS there will be only one team for a particular team type 
			 * so the first team will be considered for further processing.
			 * For future please not that incase there are multiple teams for a particular teamtpye then
			 * the team id needs to be got form the UI itself. Appropriate changes needs to be done.
			 * 
			ITeam[] teams = teamProxy.getTeamsByTeamType(teamTypearr[i].getTeamTypeID());
			
			ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
			
			
			for (int j = 0; j < teamTypeMembershipArr.length; j++) {
				if(teamTypeMembershipArr[j].getMembershipID()== oldTeamTypeMembershipId){
					teamId = teams[0].getTeamID();
					found = true;
					break;
				}
			}
			if(found)
				break;
		}*/
		
		if(teamId == ICMSConstant.LONG_INVALID_VALUE)
			throw new UserException("Invalid team");
		ITeamTrxValue teamTrxVal = teamProxy.getTrxTeamByID(teamId);
		OBTeam team = (OBTeam)teamTrxVal.getTeam();
		//ITeamTypeMembership[] teamTypeMemberShipArr = team.getTeamType().getTeamTypeMemberships();
		//ITeamMembership[] teamMemberships = team.getTeamMemberships();
		
		
		
		ITeamMembership[] tmi = team.getTeamMemberships();
		OBTeamMembership[] tm = new OBTeamMembership[tmi.length];
		for (int i = 0; i < tm.length; i++) {
			tm[i] = new OBTeamMembership();
			AccessorUtil.copyValue(tmi[i], tm[i]);
		}
		for (int i = 0; i < tm.length; i++) {
			if(oldTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER || oldTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER 
					||oldTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER_WFH|| oldTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER_WFH){
				if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == 1 || tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == 2) {
					ArrayList remainingUsers = new ArrayList();
					ITeamMember[] imems = tm[i].getTeamMembers();
					if ((imems != null) && (imems.length > 0)) {
						for (int j = 0; j < imems.length; j++) {
							if (userId!=imems[j].getTeamMemberUser().getUserID()) {
								remainingUsers.add(imems[j]);
							}
						}
					}
					OBTeamMember[] remainingMembers = (OBTeamMember[]) remainingUsers
							.toArray(new OBTeamMember[remainingUsers.size()]);
					tm[i].setTeamMembers(remainingMembers);
				}
			}else{
			if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == membershipTypeId) {
				ArrayList remainingUsers = new ArrayList();
				ITeamMember[] imems = tm[i].getTeamMembers();
				if ((imems != null) && (imems.length > 0)) {
					for (int j = 0; j < imems.length; j++) {
						if (userId!=imems[j].getTeamMemberUser().getUserID()) {
							remainingUsers.add(imems[j]);
						}
					}
				}
				OBTeamMember[] remainingMembers = (OBTeamMember[]) remainingUsers
						.toArray(new OBTeamMember[remainingUsers.size()]);
				tm[i].setTeamMembers(remainingMembers);
			}
		}
		}
		team.setTeamMemberships(tm);

		ICompTrxResult trxResultteamMaker = teamProxy.makerUpdateTeam(trxContext, teamTrxVal, team);
		teamTrxVal = (ITeamTrxValue)trxResultteamMaker.getTrxValue();
		ICompTrxResult trxResultteamChecker = teamProxy.checkerApproveUpdateTeam(trxContext, teamTrxVal);
		//return team;
		}catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error in Checker Approval remove method");
		}
	}
	
	private void removeUsersDirectly(long userId)throws Exception {
//		List userList = Arrays.asList(users);
		try{
			StdUserDAO  stdUserDAO = new StdUserDAO();
			stdUserDAO.deleteTeamMember(userId);
		}catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error in Checker Approval remove method");
		}
	}

	private void updateUnlock(long userId,String isUnlock) {
		
		String query="";
		if("Y".equals(isUnlock))
			query="UPDATE CMS_USER SET IS_UNLOCK ='Y' WHERE USER_ID="+userId+"";
		else
			query="UPDATE CMS_USER SET IS_UNLOCK ='N' WHERE USER_ID="+userId+"";
		StringBuffer strBuffer = new StringBuffer(query.trim());
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuffer.toString());
			ResultSet rs = dbUtil.executeQuery();
			dbUtil.commit();
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}

}
