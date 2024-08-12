package com.integrosys.cms.ui.todo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxSearchResult;
import com.integrosys.cms.app.transaction.OBLPTodoObject;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.ui.collateral.CheckEditable;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PrepareCustomerTodoCommand extends AbstractCommand {

	private SBCMSTrxManager workflowManager;

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public SBCMSTrxManager getWorkflowManager() {
		return workflowManager;
	}

	/**
	 * Default Constructor
	 */
	public PrepareCustomerTodoCommand() {

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
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ "taskFlag", "java.lang.String", REQUEST_SCOPE },
				{ "customerTodoTaskFlag", "java.lang.String", GLOBAL_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "resultList", "java.util.Collection", REQUEST_SCOPE },
				{ "customerTodoTaskFlag", "java.lang.String", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here preparation for company borrower is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		String transactionID = (String) map.get("transactionID");
		String taskFlag = (String) map.get("taskFlag");
		if (taskFlag == null) {
			taskFlag = (String) map.get("customerTodoTaskFlag");
		}
		result.put("customerTodoTaskFlag", taskFlag);
		boolean isMainFlag = false, isCCColTask = false, isSecColTask = false, isLimitTask = false;
		if (taskFlag != null) {
			if (taskFlag.indexOf("M") >= 0) {
				isMainFlag = true;
			}
			if (taskFlag.indexOf("S") >= 0) {
				isSecColTask = true;
			}
			if (taskFlag.indexOf("C") >= 0) {
				isCCColTask = true;
			}
			if (taskFlag.indexOf("L") >= 0) {
				isLimitTask = true;
			}
		}

		try {
			// todo-type can be set based on the role type
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			long membershipTypeID = findMembershipID(team, user.getUserID());

			ICMSTrxValue trx = getWorkflowManager().getTransaction(transactionID);
			Collection todocol = getWorkflowManager().getLPTodoList();

			// process the customer transaction first
			if (trx.getTransactionType().equals(ICMSConstant.INSTANCE_CUSTOMER)) {
				for (Iterator iterator = todocol.iterator(); iterator.hasNext();) {
					OBLPTodoObject oblpTodoObject = (OBLPTodoObject) iterator.next();
					oblpTodoObject.setProcessURL(oblpTodoObject.getProcessURL() + "&"
							+ IGlobalConstant.REQUEST_CUSTOMER_ID + "=" + trx.getCustomerID());
					//oblpTodoObject.setProcessURL(oblpTodoObject.getProcessURL(
					// ) + "&" + IGlobalConstant.REQUEST_LIMITPROFILE_ID + "=" +
					// trx.getLimitProfileID());

					if (oblpTodoObject.getProcessMembershipID() == membershipTypeID) {
						oblpTodoObject.setEnabled(true);
					}
					else {
						oblpTodoObject.setEnabled(false);
					}

					if (oblpTodoObject.getActionID().equals("CCC")) {
						oblpTodoObject.setStatus("New");
					}
					else {
						oblpTodoObject.setStatus("N/A");
						oblpTodoObject.setEnabled(false);
					}

				}
				result.put("resultList", todocol);

				result.put(IGlobalConstant.USER_TEAM, team);
				result.put(IGlobalConstant.USER, user);

				returnMap.put(COMMAND_RESULT_MAP, result);
				return returnMap;
			}

			ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(trx.getLimitProfileID());

			boolean isTATDone = false;
			int bflStatus = LimitProxyFactory.getProxy().getBFLStatus(limitProfile);
			if (bflStatus != ICMSConstant.BFL_STATUS_UNKNOWN) {
				isTATDone = true;
			}

			// collateral details action
			boolean isPerfected = true, isSSCCollateralExist = false, isSSCPerfected = true, noCollaterals = true, isYourCountryCol = false;
			String cccStatus = "", limitStatus = "";
			String sccStatus = "";
			// boolean isCCCPending = false;
			boolean isLimitPending = false;
			if (isTATDone) {
				for (int i = 0; i < limitProfile.getLimits().length; i++) {
					ILimit iLimit = limitProfile.getLimits()[i];
					DefaultLogger.debug(this, "Checking collaterals for limit " + iLimit.getLimitRef());
					boolean noCoBorrowerCollaterals = true;
					ICoBorrowerLimit[] cbLimits = iLimit.getCoBorrowerLimits();
					if ((iLimit.getNonDeletedCollateralAllocations() == null)
							|| (iLimit.getNonDeletedCollateralAllocations().length < 1)) {
						if (cbLimits != null) {
							for (int j = 0; j < cbLimits.length; j++) {
								if ((cbLimits[j].getNonDeletedCollateralAllocations() != null)
										&& (cbLimits[j].getNonDeletedCollateralAllocations().length > 0)) {
									noCoBorrowerCollaterals = false;
									break;
								}
							}
						}
						if (noCoBorrowerCollaterals) {
							noCollaterals = noCollaterals && true;
							DefaultLogger
									.debug(this, "No collaterals for this limit or coborrower limit of this limit");
							continue;
						}
						else {
							noCollaterals = false;
						}
					}
					else {
						noCollaterals = false;
					}
					if (iLimit.getCollateralAllocations() != null) {
						for (int j = 0; j < iLimit.getCollateralAllocations().length; j++) {
							ICollateralAllocation iCollateralAllocation = iLimit.getCollateralAllocations()[j];

							String colStatus = iCollateralAllocation.getCollateral().getStatus();
							if ("DELETED".equals(colStatus) || "PENDING_DELETE".equals(colStatus)) {
								continue;
							}
							if (isInCountry(iCollateralAllocation.getCollateral().getCollateralLocation(), team)) {
								isYourCountryCol = true;
							}
							if (!iCollateralAllocation.getCollateral().getIsPerfected()) {
								DefaultLogger.debug(this, "This collateral is not perfected "
										+ iCollateralAllocation.getCollateral().getCollateralID());
								isPerfected = false;
							}
							else {
								DefaultLogger.debug(this, "This collateral is perfected "
										+ iCollateralAllocation.getCollateral().getCollateralID());
							}
							if (isSSCEditable(iCollateralAllocation.getCollateral())) {
								DefaultLogger.debug(this, "This collateral for SSC "
										+ iCollateralAllocation.getCollateral().getCollateralID());
								if (!iCollateralAllocation.getCollateral().getIsPerfected()) {
									// DefaultLogger.debug(this,
									// "The SSC collateral not perfected");
									isSSCPerfected = false;
								}
								isSSCCollateralExist = true;
							}
							else {
								DefaultLogger.debug(this, "Ths collateral is not for SSC "
										+ iCollateralAllocation.getCollateral().getCollateralID());
							}
						} // for j loop
					}

					// CR35 Coborrower Limit and security linkage
					if (!noCoBorrowerCollaterals && (cbLimits != null)) {
						for (int j = 0; j < cbLimits.length; j++) {
							ICollateralAllocation[] colAllocations = cbLimits[j].getCollateralAllocations();
							if (colAllocations != null) {
								for (int k = 0; k < colAllocations.length; k++) {
									ICollateralAllocation iCollateralAllocation = colAllocations[k];
									String colStatus = iCollateralAllocation.getCollateral().getStatus();
									if ("DELETED".equals(colStatus) || "PENDING_DELETE".equals(colStatus)) {
										continue;
									}
									if (isInCountry(iCollateralAllocation.getCollateral().getCollateralLocation(), team)) {
										isYourCountryCol = true;
									}
									if (!iCollateralAllocation.getCollateral().getIsPerfected()) {
										DefaultLogger.debug(this, "This collateral is not perfected "
												+ iCollateralAllocation.getCollateral().getCollateralID());
										isPerfected = false;
									}
									else {
										DefaultLogger.debug(this, "This collateral is perfected "
												+ iCollateralAllocation.getCollateral().getCollateralID());
									}
									if (isSSCEditable(iCollateralAllocation.getCollateral())) {
										DefaultLogger.debug(this, "This collateral for SSC "
												+ iCollateralAllocation.getCollateral().getCollateralID());
										if (!iCollateralAllocation.getCollateral().getIsPerfected()) {
											// DefaultLogger.debug(this,
											// "The SSC collateral not perfected"
											// );
											isSSCPerfected = false;
										}
										isSSCCollateralExist = true;
									}
									else {
										DefaultLogger.debug(this, "Ths collateral is not for SSC "
												+ iCollateralAllocation.getCollateral().getCollateralID());
									}
								}
							}
						}
					}
				} // for i loop

				// get all the relevant transactions
				CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
				criteria.setOnlyMembershipRecords(false);
				criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
				// R1.5 CR146 remove CCC
				String[] trxtypes = { ICMSConstant.INSTANCE_SCC, ICMSConstant.INSTANCE_LIMIT,
						ICMSConstant.INSTANCE_CHECKLIST };
				criteria.setTransactionTypes(trxtypes);
				criteria.setLimitProfileID(new Long(trx.getLimitProfileID()));
				criteria.setStartIndex(0);
				criteria.setNItems(Integer.MAX_VALUE);

				Collection relTrxs = getWorkflowManager().searchTransactions(criteria).getResultList();

				boolean isChecklistExist = false;
				for (Iterator iterator = relTrxs.iterator(); iterator.hasNext();) {
					OBCMSTrxSearchResult obcmsTrxSearchResult = (OBCMSTrxSearchResult) iterator.next();
					DefaultLogger.debug(this, "Got Trx " + obcmsTrxSearchResult.getTransactionType() + " with ref "
							+ obcmsTrxSearchResult.getReferenceID());
					if (obcmsTrxSearchResult.getTransactionType().equals(ICMSConstant.INSTANCE_CHECKLIST)) {

						isChecklistExist = true;
					}
				}

				OBCMSTrxSearchResult scctre = null;
				for (Iterator iterator = relTrxs.iterator(); iterator.hasNext();) {
					OBCMSTrxSearchResult obcmsTrxSearchResult = (OBCMSTrxSearchResult) iterator.next();
					if (obcmsTrxSearchResult.getTransactionType().equals(ICMSConstant.INSTANCE_SCC)
							&& !obcmsTrxSearchResult.getStatus().equals(ICMSConstant.STATE_CLOSED)) {
						scctre = obcmsTrxSearchResult;
						// break;
					}
				}
				if (scctre == null) {
					if (isChecklistExist) {
						sccStatus = "Pending";
					}
					else {
						sccStatus = "New";
					}
				}
				else {
					if (scctre.getReferenceID() == null) {
						sccStatus = "Pending";
					}
					else {
						sccStatus = "Completed";
					}
				}

				int atstatus = LimitProxyFactory.getProxy().getActivatedLimitsStatus(limitProfile);
				if (atstatus == ICMSConstant.ACTIVATED_LIMIT_NONE) {
					limitStatus = "New";
				}
				else if (atstatus == ICMSConstant.ACTIVATED_LIMIT_PARTIAL) {
					limitStatus = "Pending";
				}
				else {
					limitStatus = "Completed";
				}
			}

			for (Iterator iterator = todocol.iterator(); iterator.hasNext();) {
				OBLPTodoObject oblpTodoObject = (OBLPTodoObject) iterator.next();
				oblpTodoObject.setProcessURL(oblpTodoObject.getProcessURL() + "&" + IGlobalConstant.REQUEST_CUSTOMER_ID
						+ "=" + trx.getCustomerID());
				oblpTodoObject.setProcessURL(oblpTodoObject.getProcessURL() + "&"
						+ IGlobalConstant.REQUEST_LIMITPROFILE_ID + "=" + trx.getLimitProfileID());

				if (oblpTodoObject.getProcessMembershipID() == membershipTypeID) {
					oblpTodoObject.setEnabled(true);
				}
				else {
					oblpTodoObject.setEnabled(false);
				}

				if (oblpTodoObject.getActionID().equals("TAT")) {
					oblpTodoObject.setStatus(trx.getStatus());
					if (trx.getStatus().equals(ICMSConstant.STATE_NEW)) {
						oblpTodoObject.setStatus("New");
					}
					else if (isTATDone) {
						oblpTodoObject.setStatus("Completed");
						oblpTodoObject.setEnabled(false);
					}
					else {
						oblpTodoObject.setStatus("Pending");
					}
					if (!isMainFlag) {
						oblpTodoObject.setEnabled(false);
					}
					continue;
				}
				if (!isTATDone) {
					oblpTodoObject.setEnabled(false);
					oblpTodoObject.setStatus("New");
					continue;
				}
				if (oblpTodoObject.getActionID().equals("BFL")) {
					if (bflStatus == ICMSConstant.BFL_NOT_REQUIRED) {
						oblpTodoObject.setStatus("N/A");
						oblpTodoObject.setEnabled(false);
					}
					else if ((bflStatus == ICMSConstant.BFL_REQUIRED_NOT_INIT)
							|| (bflStatus == ICMSConstant.BFL_STATUS_UNKNOWN)) {
						oblpTodoObject.setStatus("New");
					}
					else if (bflStatus == ICMSConstant.BFL_IN_PROGRESS) {
						oblpTodoObject.setStatus("Pending");
					}
					else {
						oblpTodoObject.setStatus("Completed");
						oblpTodoObject.setEnabled(false);
					}
					if (!isTATDone
							&& ((bflStatus == ICMSConstant.BFL_REQUIRED_NOT_INIT) || (bflStatus == ICMSConstant.BFL_IN_PROGRESS))) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isMainFlag) {
						oblpTodoObject.setEnabled(false);
					}
					continue;
				}

				if (oblpTodoObject.getActionID().equals("SSCCOL")) {
					if (noCollaterals) {
						oblpTodoObject.setEnabled(false);
					}
					else if (!isSSCCollateralExist) {
						oblpTodoObject.setEnabled(false);
					}
					else if (isSSCPerfected) {
						oblpTodoObject.setEnabled(false);
					}
					if (noCollaterals || !isSSCCollateralExist) {
						oblpTodoObject.setStatus("N/A");
					}
					else if (isSSCPerfected) {
						oblpTodoObject.setStatus("Completed");
					}
					else {
						oblpTodoObject.setStatus("Pending");
					}
					if (oblpTodoObject.getStatus().equals("Completed")) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isMainFlag && !isSecColTask) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isYourCountryCol) {
						oblpTodoObject.setEnabled(false);
					}
					continue;
				}
				if (oblpTodoObject.getActionID().equals("CPCCOL")) {
					if (noCollaterals) {
						oblpTodoObject.setStatus("N/A");
						oblpTodoObject.setEnabled(false);
					}
					else if (isPerfected) {
						oblpTodoObject.setStatus("Completed");
					}
					else {
						oblpTodoObject.setStatus("Pending");
					}
					if (oblpTodoObject.getStatus().equals("Completed")) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isMainFlag && !isSecColTask) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isYourCountryCol) {
						oblpTodoObject.setEnabled(false);
					}
					continue;
				}

				if (oblpTodoObject.getActionID().equals("SCC")) {
					oblpTodoObject.setStatus(sccStatus);
					// Commented for CMS-.. which needs to make SCC independent
					// of BFL
					if (oblpTodoObject.getStatus().equals("Completed") || oblpTodoObject.getStatus().equals("N/A")) {
						// || (bflStatus != ICMSConstant.BFL_COMPLETED &&
						// bflStatus != ICMSConstant.BFL_NOT_REQUIRED)) {
						oblpTodoObject.setEnabled(false);
					}
					if (!isMainFlag && !isSecColTask) {
						oblpTodoObject.setEnabled(false);
					}
					continue;
				}
				if (oblpTodoObject.getActionID().equals("LIMIT")) {
					oblpTodoObject.setStatus(limitStatus);
					String roleType = (String) map.get(IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID);
					ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
					if (isLimitEditable(team, customer, limitProfile, roleType)) {
						oblpTodoObject.setEnabled(true);
					}
					if (oblpTodoObject.getStatus().equals("Completed")) {
						oblpTodoObject.setEnabled(false);
					}
				}
			}
			result.put("resultList", todocol);

			result.put(IGlobalConstant.USER_TEAM, team);
			result.put(IGlobalConstant.USER, user);

			returnMap.put(COMMAND_RESULT_MAP, result);
			return returnMap;
		}
		catch (Throwable e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	private boolean isLimitEditable(ITeam team, ICMSCustomer customer, ILimitProfile lmtProfile, String roleType) {
		boolean isLimitEditAllowed = false;
		boolean isEditAllowed = false;
		IDataProtectionProxy dpProxy = DataProtectionProxyFactory.getProxy();
		ILimit[] limits = lmtProfile.getLimits();
		long teamTypeMshipID = roleType == null ? ICMSConstant.LONG_INVALID_VALUE : Long.parseLong(roleType);
		if ((teamTypeMshipID == ICMSConstant.TEAM_TYPE_CPC_CHECKER)
				|| (teamTypeMshipID == ICMSConstant.TEAM_TYPE_SSC_CHECKER)
				|| (teamTypeMshipID == ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH)) {
			isEditAllowed = false;
		}
		if (isEditAllowed) {
			for (int i = limits.length - 1; i >= 0; i--) {
				isLimitEditAllowed = dpProxy.isLocationAccessibleByUser(team, customer.getCMSLegalEntity()
						.getCustomerSegment(), limits[i].getBookingLocation());
				if (isLimitEditAllowed) {
					isLimitEditAllowed = dpProxy.isDataAccessAllowed(ICMSConstant.INSTANCE_LIMIT,
							IDataProtectionProxy.DAP_EDIT, teamTypeMshipID, new String[] { limits[i]
									.getBookingLocation().getCountryCode() }, new String[] { limits[i]
									.getBookingLocation().getOrganisationCode() }, true);
					if (isLimitEditAllowed) {
						break;
					}
				}
			}
		}
		return isLimitEditAllowed;
	}

	protected CMSTrxSearchCriteria prepareSearchCriteria(CMSTrxSearchCriteria criteria) {
		criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
		return criteria;
	}

	private boolean isSSCEditable(ICollateral col) {
		return CheckEditable.isSSCEditable(col); // todo
	}

	private long findMembershipID(ITeam team, long userID) {
		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			ITeamMembership iTeamMembership = team.getTeamMemberships()[i];
			for (int j = 0; j < iTeamMembership.getTeamMembers().length; j++) {
				ITeamMember iTeamMember = iTeamMembership.getTeamMembers()[j];
				if (iTeamMember.getTeamMemberUser().getUserID() == userID) {
					return iTeamMembership.getTeamTypeMembership().getMembershipID();
				}
			}
		}
		return 0;
	}

	private boolean isInCountry(String cty, ITeam team) {
		if (team.getCountryCodes() != null) {
			for (int i = 0; i < team.getCountryCodes().length; i++) {
				String s = team.getCountryCodes()[i];
				if (s.equals(cty)) {
					return true;
				}
			}
		}
		return false;
	}

}
