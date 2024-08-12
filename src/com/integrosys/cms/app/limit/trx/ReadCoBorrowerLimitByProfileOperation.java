package com.integrosys.cms.app.limit.trx;

import java.util.ArrayList;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.SecurityAccessValidationUtils;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit;
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

public class ReadCoBorrowerLimitByProfileOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCoBorrowerLimitByProfileOperation() {
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
			ITrxContext ctx = trxVal.getTrxContext();
			ILimit[] actualLimits = null;
			String segment = null;
			boolean generalAccess = true;
			if ((ctx == null) || (ctx.getLimitProfile() == null) || (ctx.getCustomer() == null)) {
				generalAccess = false;
			}
			else {
				actualLimits = ctx.getLimitProfile().getLimits();
				segment = ctx.getCustomer().getCMSLegalEntity().getCustomerSegment();
				segment = segment == null ? "" : segment;
			}

			long teamTypeMshipID = this.getTeamTypeMembershipID(ctx.getTeam(), ctx.getUser());
			IDataProtectionProxy dpProxy = DataProtectionProxyFactory.getProxy();

			ArrayList coBorrowerLmtTrxValueList = new ArrayList();
			if (actualLimits != null) {
				for (int i = 0; i < actualLimits.length; i++) {
					ICoBorrowerLimit[] coBorrowerLimits = actualLimits[i].getCoBorrowerLimits();
					for (int x = 0; x < coBorrowerLimits.length; x++) {
						coBorrowerLimits[x].setProductDesc(actualLimits[i].getProductDesc());
						ICMSTrxValue tempValue = getTrxManager().getTrxByRefIDAndTrxType(
								String.valueOf(coBorrowerLimits[x].getLimitID()), trxVal.getTransactionType());
						ICoBorrowerLimitTrxValue value = new OBCoBorrowerLimitTrxValue(tempValue);
						OBCoBorrowerLimit stageCoBorrowerLimit = new OBCoBorrowerLimit();
						stageCoBorrowerLimit.setLimitID(Long.parseLong(tempValue.getStagingReferenceID()));
						stageCoBorrowerLimit.setProductDesc(actualLimits[i].getProductDesc());
						if (generalAccess) {
							boolean allowAccess = SecurityAccessValidationUtils.isTeamHasAccessToBookingLocation(ctx.getTeam(),
									coBorrowerLimits[x].getBookingLocation())
									&& SecurityAccessValidationUtils.isTeamHasAccessOfSegment(ctx.getTeam(), segment);

							if (allowAccess) {
								allowAccess = dpProxy
										.isDataAccessAllowed(ICMSConstant.INSTANCE_LIMIT,
												IDataProtectionProxy.DAP_EDIT, teamTypeMshipID,
												new String[] { coBorrowerLimits[x].getBookingLocation()
														.getCountryCode() }, new String[] { coBorrowerLimits[x]
														.getBookingLocation().getOrganisationCode() }, true);
							}
							coBorrowerLimits[x].setIsDAPError(!allowAccess);
							stageCoBorrowerLimit.setIsDAPError(!allowAccess);
						}
						else {
							coBorrowerLimits[i].setIsDAPError(generalAccess);
							stageCoBorrowerLimit.setIsDAPError(generalAccess);
						}
						value.setLimit(coBorrowerLimits[x]);
						value.setStagingLimit(stageCoBorrowerLimit);
						coBorrowerLmtTrxValueList.add(value);
					}
				}
			}
			ICoBorrowerLimit[] stageLimits = new ICoBorrowerLimit[coBorrowerLmtTrxValueList.size()];
			for (int i = 0; i < coBorrowerLmtTrxValueList.size(); i++) {
				ICoBorrowerLimitTrxValue trxValue = (ICoBorrowerLimitTrxValue) coBorrowerLmtTrxValueList.get(i);
				stageLimits[i] = trxValue.getStagingLimit();
			}
			// reset the staging co borrower limits
			SBLimitManager mgr = getSBLimitManagerStaging();
			stageLimits = mgr.resetCoBorrowerLimits(stageLimits);
			for (int i = 0; i < stageLimits.length; i++) {
				ICoBorrowerLimitTrxValue coTrxValue = (ICoBorrowerLimitTrxValue) coBorrowerLmtTrxValueList.get(i);
				coTrxValue.setStagingLimit(stageLimits[i]);
				DefaultLogger.debug(this, "staging activated limits: ---------------- "
						+ coTrxValue.getStagingLimit().getActivatedLimitAmount());
				DefaultLogger.debug(this, "staging approved limits: ----------------- "
						+ coTrxValue.getStagingLimit().getApprovedLimitAmount());
				DefaultLogger.debug(this, "reason: ---------------------------------- "
						+ coTrxValue.getStagingLimit().getZerorisedReasons());
			}
			ICoBorrowerLimitTrxValue[] values = (ICoBorrowerLimitTrxValue[]) coBorrowerLmtTrxValueList
					.toArray(new ICoBorrowerLimitTrxValue[0]);
			ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue = new OBCoBorrowerLimitTrxValue(trxVal);
			if (values.length > 0) {
				AccessorUtil.copyValue(values[0], coBorrowerLimitTrxValue,
						new String[] { "getCoBorrowerLimitTrxValues" });
			}
			coBorrowerLimitTrxValue.setCoBorrowerLimitTrxValues(values);
			return coBorrowerLimitTrxValue;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException(e);
		}
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
}
