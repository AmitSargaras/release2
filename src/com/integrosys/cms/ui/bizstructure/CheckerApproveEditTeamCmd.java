/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.common.transaction.ICompTrxResult;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: dli $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class CheckerApproveEditTeamCmd extends AbstractTeamCommand implements ICommonEventConstant {

	/**
	 * Dual role support for CPC and CPC Custodian teams (CR33/R1.3.1)
	 */
	private int CPC_TEAM_TYPE_ID;

	private int CPC_CUSTODIAN_TEAM_TYPE_ID;

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditTeamCmd() {

		// Retrieve the configurable team type ids from property file
		// ofa_properties
		CPC_TEAM_TYPE_ID = PropertyManager.getInt("com.cms.common.team.multipleTeams.group.1.CPC");
		CPC_CUSTODIAN_TEAM_TYPE_ID = PropertyManager.getInt("com.cms.common.team.multipleTeams.group.1.CPC_CUSTODIAN");
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
				{ "TeamTrxValue", "com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue", SERVICE_SCOPE },
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
		OBCMSTeamTrxValue teamTrxVal = (OBCMSTeamTrxValue) map.get("TeamTrxValue");
		DefaultLogger.debug(this, "TeamTrxValue before Edit by Checker.........  = ");
		boolean validateUser = true;
		ITeam stgTeam = teamTrxVal.getStagingTeam();
		ITeam bussTeam = teamTrxVal.getTeam();

		// userTeam was changed to ITeam[] from ITeam to support dual role
		// for CPC & CPC Custodian
		ITeam[] userTeam = null;
		for (int i = 0; i < stgTeam.getTeamMemberships().length; i++) {
			for (int j = 0; j < stgTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
				try {
					DefaultLogger.debug(this, "Checker is retrieving the Team info if any ...");
					userTeam = getCmsTeamProxy().getTeamsByUserID(
							stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID());
					DefaultLogger.debug(this, "Checker is retrieving the Team info if any ...Successful");
				}
				catch (EntityNotFoundException e) {
					DefaultLogger.warn(this,
							"failed to retrieve team using user id ["
									+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
											.getUserID() + "]", e);
				}
				catch (BizStructureException e) {
					CommandProcessingException cpe = new CommandProcessingException(
							"failed to retrieve team using user id ["
									+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
											.getUserID() + "]");
					cpe.initCause(e);
					throw cpe;
				}

				DefaultLogger.debug(this, "Checker is retrieving the Team info if any ...Done");
				if ((userTeam != null) && (userTeam.length > 0)) {
					//DefaultLogger.debug(this, "===team[] is found===");
					for (int iLoop = 0; iLoop < userTeam.length; iLoop++) {
						//DefaultLogger.debug(this, "===Checking each team .... ===" + iLoop);

						// dual role is only allowed for CPC & CPC CUSTODIAN
						// teams
						if ((userTeam[iLoop].getTeamType().getTeamTypeID() == CPC_TEAM_TYPE_ID)
								|| (userTeam[iLoop].getTeamType().getTeamTypeID() == CPC_CUSTODIAN_TEAM_TYPE_ID)) {
							if (((stgTeam.getTeamType().getTeamTypeID() != CPC_TEAM_TYPE_ID) && (stgTeam.getTeamType()
									.getTeamTypeID() != CPC_CUSTODIAN_TEAM_TYPE_ID))) {
								DefaultLogger
										.debug(this,
												"=== user is not allowed to belong to more than 1 teams except CPC & CPC Custodian .... ===");
								exceptionMap.put("userError"
										+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
												.getLoginID(), new ActionMessage("error.team.user", userTeam[iLoop]
										.getAbbreviation()));
								DefaultLogger.debug(this, "user : team "
										+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
												.getLoginID() + "//" + userTeam[iLoop].getAbbreviation());
								if (validateUser) {
									validateUser = false;
								}
							}
						}
						// Users are not allowed to belong to more than 1
						// teams except CPC and CPC Customdian
						else if (userTeam[iLoop].getTeamID() != bussTeam.getTeamID()) {
							DefaultLogger
									.debug(this,
											"=== user is not allowed to belong to more than 1 teams except CPC & CPC Custodian .... ===");
							exceptionMap.put("userError"
									+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
											.getLoginID(), new ActionMessage("error.team.user", userTeam[iLoop]
									.getAbbreviation()));
							DefaultLogger.debug(this, "user : team "
									+ stgTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser()
											.getLoginID() + "//" + userTeam[iLoop].getAbbreviation());
							if (validateUser) {
								validateUser = false;
							}
						}

					}

				}

			}
		}
		if (validateUser) {
			DefaultLogger.debug(this, "Checker is approving the membership application ...");
			try {
				ICompTrxResult trxResult = getCmsTeamProxy().checkerApproveUpdateTeam(trxContext, teamTrxVal);
				resultMap.put("request.ITrxResult", trxResult);
			}
			catch (TeamException e) {
				CommandProcessingException cpe = new CommandProcessingException("failed to approve updated team", e);
				cpe.initCause(e);
				throw cpe;
			}
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
