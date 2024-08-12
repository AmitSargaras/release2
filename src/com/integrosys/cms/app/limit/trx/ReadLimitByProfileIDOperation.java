/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ReadLimitByProfileIDOperation.java,v 1.11 2006/09/14 05:54:15 hshii Exp $
 */
package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This operation is to read a transaction of limits belong to a limit profile.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.11 $
 * @since $Date: 2006/09/14 05:54:15 $ Tag: $Name: $
 */
public class ReadLimitByProfileIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadLimitByProfileIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 *         ICMSConstant.ACTION_READ_LIMIT_BY_LPID
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_LIMIT_BY_LPID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val ITrxValue object containing the parameters required for
	 *        retrieving limit by limit profile id
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue trxVal = super.getCMSTrxValue(val);
			boolean generalAccess = true;
			ITrxContext ctx = trxVal.getTrxContext();
			ILimit[] actualLmts = null;
			String segment = null;
			if ((ctx == null) || (ctx.getLimitProfile() == null) || (ctx.getCustomer() == null)) {
				generalAccess = false;
			}
			else {
				actualLmts = ctx.getLimitProfile().getLimits();
				segment = ctx.getCustomer().getCMSLegalEntity().getCustomerSegment();
				segment = segment == null ? "" : segment;
			}

			long teamTypeMshipID = this.getTeamTypeMembershipID(ctx.getTeam(), ctx.getUser());

			int count = actualLmts == null ? 0 : actualLmts.length;
			ILimitTrxValue[] lmtTrx = new OBLimitTrxValue[count];
			ILimit[] stageLmts = new OBLimit[count];

			IDataProtectionProxy dpProxy = DataProtectionProxyFactory.getProxy();
			for (int i = 0; i < count; i++) {
				ICMSTrxValue tempVal = getTrxManager().getTrxByRefIDAndTrxType(
						String.valueOf(actualLmts[i].getLimitID()), trxVal.getTransactionType());
				lmtTrx[i] = new OBLimitTrxValue(tempVal);
				if (tempVal.getStagingReferenceID() != null) {
					stageLmts[i] = new OBLimit();
					stageLmts[i].setLimitID(Long.parseLong(tempVal.getStagingReferenceID()));
					stageLmts[i].setCollateralAllocations(actualLmts[i].getCollateralAllocations());
				}
				if (generalAccess) {
					boolean allowAccess = dpProxy.isLocationAccessibleByUser(ctx.getTeam(), segment, actualLmts[i]
							.getBookingLocation());
					if (allowAccess) {
						allowAccess = dpProxy.isDataAccessAllowed(ICMSConstant.INSTANCE_LIMIT,
								IDataProtectionProxy.DAP_EDIT, teamTypeMshipID, new String[] { actualLmts[i]
										.getBookingLocation().getCountryCode() }, new String[] { actualLmts[i]
										.getBookingLocation().getOrganisationCode() }, true);
					}
					actualLmts[i].setIsDAPError(!allowAccess);
					stageLmts[i].setIsDAPError(!allowAccess);
				}
				else {
					actualLmts[i].setIsDAPError(generalAccess);
					stageLmts[i].setIsDAPError(generalAccess);
				}
				lmtTrx[i].setLimit(actualLmts[i]);
				lmtTrx[i].setStagingLimit(stageLmts[i]);
			}
			SBLimitManager mgr = getSBLimitManagerStaging();
			stageLmts = mgr.resetLimits(stageLmts, new String[] { "getIsDAPError", "getCollateralAllocations" });

			for (int x = 0; x < stageLmts.length; x++) {
				lmtTrx[x].setStagingLimit(stageLmts[x]);
			}

			OBLimitTrxValue newValue = new OBLimitTrxValue(trxVal);
			if (count > 0) {
				AccessorUtil.copyValue(lmtTrx[0], newValue, new String[] { "getLimitTrxValues" });
			}
			newValue.setLimitTrxValues(lmtTrx);
			return newValue;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Helper method to get team type membership out.
	 * 
	 * @param team of type ITeam
	 * @param user of type ICommonUser
	 * @return team type membership id
	 */
	private long getTeamTypeMembershipID(ITeam team, ICommonUser user) {
		ITeamMembership[] teamMship = team.getTeamMemberships();
		for (int i = teamMship.length - 1; i >= 0; i--) {
			ITeamMember[] members = teamMship[i].getTeamMembers();
			for (int j = members.length - 1; j >= 0; j--) {
				if (members[j].getTeamMemberUser().getUserID() == user.getUserID()) {
					return teamMship[i].getTeamTypeMembership().getMembershipID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the SBLimitManager remote interface
	 * 
	 * @return SBLimitManager
	 */
	private SBLimitManager getSBLimitManager() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
				SBLimitManagerHome.class.getName());

		if (home == null) {
			throw new TransactionException("SBLimitManager is null!");
		}
		return home;
	}

	/**
	 * Get the staging SBLimitManager remote interface
	 * 
	 * @return SBLimitManager
	 */
	private SBLimitManager getSBLimitManagerStaging() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI_STAGING,
				SBLimitManagerHome.class.getName());

		if (home == null) {
			throw new TransactionException("SBLimitManager is null!");
		}
		return home;
	}
}
