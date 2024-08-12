/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/proxy/ILimitProxy.java,v 1.49 2006/10/09 05:29:04 jzhai Exp $
 */
package com.integrosys.cms.app.limit.proxy;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.collateralthreshold.CollateralThresholdDAO;

/**
 * This interface defines the services that are available in for use in the
 * interaction with Limit and Limit Profile.
 * 
 * ********************************************************************* NOTE:
 * The ICollateral object contained in ICollateralAllocation does not contain
 * all collateral details. It only contain the common fields.
 * **********************************************************************
 * @author $Author: jzhai $
 * @version $Revision: 1.49 $
 * @since $Date: 2006/10/09 05:29:04 $ Tag: $Name: $
 */
public interface ILimitProxy extends java.io.Serializable {
	/**
	 * Create a limit profile record, without dual control.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimitProfile(ILimitProfile value) throws LimitException;

	/**
	 * Create a limit profile record, without dual control.
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimitProfile(ILimitProfileTrxValue value) throws LimitException;

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ILimit
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimit(ILimit value) throws LimitException;

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimit(ILimitTrxValue value) throws LimitException;

	/**
	 * Create a coborrower limit record, without dual control.
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException;

	/**
	 * Create a coborrower limit record, without dual control.
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createCoBorrowerLimit(ICoBorrowerLimitTrxValue value) throws LimitException;

	/**
	 * Get a Limit given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ILimit if it can be found, or null if the limit does not exist.
	 * @throws LimitException on errors
	 */
	public ILimit getLimit(long limitID) throws LimitException;

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ILimitTrxValue
	 * @throws LimitException on errors
	 */
	public ILimitTrxValue getTrxLimit(String trxID) throws LimitException;

	/**
	 * Get a Limit Transaction value given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ILimitTrxValue if it can be found, or null if the limit does not
	 *         exist.
	 * @throws LimitException on errors
	 */
	public ILimitTrxValue getTrxLimit(long limitID) throws LimitException;

	/**
	 * Get transaction belong to the limits of the given limit profile.
	 * 
	 * @param trxCtx transaction context
	 * @param lp of type ILimitProfile
	 * @return limit transaction value
	 * @throws LimitException on any errors encountered
	 */
	public ILimitTrxValue getTrxLimitByLimitProfile(ITrxContext trxCtx, ILimitProfile lp) throws LimitException;

	/**
	 * get transaction belongs to the co borrower limits of the given limit
	 * profile.
	 * @param trxCtx
	 * @param lp
	 * @return
	 * @throws LimitException
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimitByLimitProfile(ITrxContext trxCtx, ILimitProfile lp)
			throws LimitException;

	/**
	 * Get a CoBorrowerLimit given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimit if it can be found, or null if the limit does
	 *         not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimit getCoBorrowerLimit(long limitID) throws LimitException;

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(String trxID) throws LimitException;

	/**
	 * Get a limit transaction give a transaction reference ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimits(String trxID) throws LimitException;

	/**
	 * Get a CoBorrowerLimit Transaction value given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimitTrxValue if it can be found, or null if the limit
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(long limitID) throws LimitException;

	/**
	 * Get a LimitProfile Limits for a given limit profile ID - Security
	 * Coverage batch job
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile if it can be found, or null if the limit does not
	 *         exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfile getLimitProfileLimitsOnly(long limitProfileID) throws LimitException;

	/**
	 * Get a LimitProfile given a limit profile ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile if it can be found, or null if the limit does not
	 *         exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfile getLimitProfile(long limitProfileID) throws LimitException;

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ILimitProfileTrxValue
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(String trxID) throws LimitException;

	/**
	 * Get a LimitProfile Transaction value given a limit's ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfileTrxValue if it can be found, or null if the limit
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(long limitProfileID) throws LimitException;

	// limits
	/**
	 * Maker update limit details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @param limit is the ILimit object to be updated
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerUpdateLimit(ITrxContext context, ILimitTrxValue trxValue, ILimit limit)
			throws LimitException;

	/**
	 * Maker update all the limits in one go.
	 * 
	 * @param trxCtx transaction context
	 * @param trxValue limit transaction value
	 * @param map contains key: transaction id, value: ILimit object
	 * @return transaction result
	 * @throws LimitException on any errors encountered
	 */
	public ICMSTrxResult makerUpdateLimit(ITrxContext trxCtx, ILimitTrxValue trxValue, HashMap map)
			throws LimitException;

	/**
	 * maker update co borrower limit
	 * @param trxCtx
	 * @param trxValue
	 * @return
	 * @throws LimitException
	 */
	public ICMSTrxResult makerUpdateCoBorrowerLimit(ITrxContext trxCtx, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * Maker Cancel limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException;

	/**
	 * Checker Approve limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException;

	/**
	 * Checker Reject limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException;

	/**
	 * Host Update Limit. The caller of this method is responsible for setting
	 * the corresponding limit objects into the ILimitTrxValue, for both actual
	 * and staging limits.
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException;

	/**
	 * System Update Limit.
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemUpdateLimit(ILimitTrxValue trxValue) throws LimitException;

	/**
	 * System Delete Limit. The caller of this method is responsible for setting
	 * the corresponding limit objects into the ILimitTrxValue, for both actual
	 * and staging limits.
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteLimit(ILimitTrxValue trxValue) throws LimitException;

	/**
	 * System Delete LimitProfile. The caller of this method is responsible for
	 * setting the corresponding limit objects into the ILimitTrxValue, for both
	 * actual and staging limits.
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteLimitProfile(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * System Close Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException;

	/**
	 * System Close Limit
	 * 
	 * @param trxValue is of type ICMSTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimit(ICMSTrxValue trxValue) throws LimitException;

	// coborrower limit
	/**
	 * Maker update limit details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @param limit is the ICoBorrowerLimit object to be updated
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue,
			ICoBorrowerLimit limit) throws LimitException;

	/**
	 * Maker Cancel limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Approve limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Reject limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * Host Update CoBorrowerLimit. The caller of this method is responsible for
	 * setting the corresponding limit objects into the
	 * ICoBorrowerLimitTrxValue, for both actual and staging limits.
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * System Delete CoBorrowerLimit.
	 * 
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteCoBorrowerLimit(ICoBorrowerLimitTrxValue trxValue) throws LimitException;

	/**
	 * System Close CoBorrowerLimit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException;

	/**
	 * System Close CoBorrowerLimit
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseCoBorrowerLimit(ICMSTrxValue trxValue) throws LimitException;

	// limit prolfile
	/**
	 * Maker update limit details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @param limitProfile is the ILimitProfile object to be updated
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue,
			ILimitProfile limitProfile) throws LimitException;

	/*
	 * Maker Resubmit limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 * object
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * 
	 * @param limitProfile is the ILimitProfile object to be updated
	 * 
	 * @return ICMSTrxResult
	 * 
	 * @throws LimitException on errors
	 * 
	 * public ICMSTrxResult makerResubmitUpdateLimitProfile(ITrxContext context,
	 * ILimitProfileTrxValue trxValue, ILimitProfile limitProfile) throws
	 * LimitException;
	 */
	/**
	 * Maker Cancel limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Approve limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Reject limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Host Update Limit. The caller of this method is responsible for setting
	 * the corresponding limit profile objects into the ILimitProfileTrxValue,
	 * for both actual and staging limit profiles.
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * System Update Limit Profile.
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemUpdateLimitProfile(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * System Close Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * System Close Limit
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimitProfile(ICMSTrxValue trxValue) throws LimitException;

	/**
	 * Maker Create TAT
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @param limitProfile is the ILimitProfile object to be updated
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCreateTAT(ITrxContext context, ILimitProfileTrxValue trxValue, ILimitProfile limitProfile)
			throws LimitException;

	/**
	 * Maker Cancel create tat
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelCreateTAT(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Approve create tat
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveCreateTAT(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker Reject create tat
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectCreateTAT(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Get a HashMap containing the following information: 1. The HashMap keys
	 * are ICollateral objects 2. The HashMap values are ArrayList of ILimit
	 * objects that are linked to the ICollateral keys
	 * 
	 * @param limitProfile
	 * @return a HashMap with key ICollateral and value an ArrayList of ILimit
	 *         objects
	 */
	public HashMap getCollateralLimitMap(ILimitProfile limitProfile);

	// CMSSP-536 Starts
	/**
	 * Filter off deleted collaterals that have no checklist attached to it.
	 * 
	 * @param trxCtx of type ITrxContext
	 * @param limitProfile of type ILimitProfile
	 * @return limits with filtered collateral allocation
	 * @throws LimitException on error during filtering
	 */
	public ILimit[] getFilteredNilColCheckListLimits(ITrxContext trxCtx, ILimitProfile limitProfile)
			throws LimitException;

	/**
	 * Issue Draft BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult issueDraftBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Print BFL reminder.
	 * 
	 * @param context of type ITrxContext
	 * @param trxValue limit profile transaction value object
	 * @param tatEntry of type ITATEntry
	 * @return transaction result
	 * @throws LimitException on any errors encountered
	 */
	public ICMSTrxResult printReminderBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Send Draft BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult sendDraftBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Acknowledge Receipt of Draft BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult ackReceiptDraftBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Issue Clean-Type BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult issueCleanTypeBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Special Issue Clean-Type BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult specialIssueCleanTypeBFL(ITrxContext context, ILimitProfileTrxValue trxValue,
			ITATEntry tatEntry) throws LimitException;

	/**
	 * Issue Final BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult issueFinalBFL(ITrxContext context, ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * Customer Accept BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult customerAcceptBFL(ITrxContext context, ILimitProfileTrxValue trxValue, ITATEntry tatEntry)
			throws LimitException;

	/**
	 * Receipt of First Checklist item (TAT)
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult receiptFirstChecklistItem(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * First Generation of CCC
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult cccGenerated(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * First Generation of SCC
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult sccGenerated(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * BCA Processing Completion (TAT)
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult completeProcessBCA(ILimitProfileTrxValue trxValue) throws LimitException;

	/**
	 * Renew an existing BCA. The old BCA transaction and all related
	 * transactions will be system-closed. The new BCA transaction will be
	 * created and all related transactions for checklist will also be created.
	 * Any limits that exist in the old BCA, and also exist in the renewed BCA
	 * will have the getExistingInd method return true. Any limits or security
	 * that has been removed in the new BCA from the security-limit link data
	 * will have a "deleted" status indicator.
	 * 
	 * @deprecated use <code>renewBCA(long profileID)</code> instead
	 * @param oldProfileID is of type long and is the limit profile ID of the
	 *        BCA to be closed.
	 * @param newProfile is of type ILimitProfile and is the new limit Profile
	 *        to be created.
	 * @return ICMSTrxResult
	 * @throws LimitException on error
	 */
	// public ICMSTrxResult renewBCA(long oldProfileID, ILimitProfile
	// newProfile) throws LimitException;
	/**
	 * Renew an existing BCA.
	 * 
	 * @param profileID is the long value of the limit profile ID to be renewed
	 * @throws LimitException on error
	 */
	public void renewBCA(long profileID) throws LimitException;

	/**
	 * Identify if a limit profile has been issued SCC. This method returns an
	 * enumerated value in integer to represent if SCC status has not been
	 * issued, partially issued, or full issued. The constant values can be
	 * found in <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile object to be evaluated
	 * @return int
	 */
	public int getSCCStatus(ILimitProfile value);

	/**
	 * Identify if a limit profile has been issued CCC. This method returns an
	 * enumerated value in integer to represent if CCC status has not been
	 * issued, partially issued, or full issued. The constant values can be
	 * found in <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile object to be evaluated
	 * @return int
	 */
	public int getCCCStatus(ILimitProfile value);

	/**
	 * Identify the BFL status of a limit profile. The possible BFL status are:
	 * 
	 * <pre>
	 * 1. BFL status unknown. This occurs when Create TAT has not been performed yet on the limit profile.
	 * 2. BFL not required. This occurs when during Create TAT, BFL not required is indicated.
	 * 3. BFL not init. This occurs when during Create TAT, BFL is indicated as required, but the first BFL task has
	 * not been performed yet.
	 * 4. BFL in progress. This occurs when at least one BFL tasks have been performed, but the final task has not been
	 * performed yet
	 * 5. BFL completed. This occurs when all BFL tasks have been performed.
	 * 
	 * Note: The constant values identifying the various status can be found in
	 * <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return int
	 */
	public int getBFLStatus(ILimitProfile value);

	/**
	 * Get the description of the latest BFL that was performed
	 * 
	 * @return String
	 */
	public String getLatestBFLDesc(ILimitProfile value);

	/**
	 * Identify if all limits in a limit profile (including co-borrower limits)
	 * have been activated. The method returns true if ALL limits are not
	 * activated, partially activated or fully activated.
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return int
	 */
	public int getActivatedLimitsStatus(ILimitProfile value);

	/**
	 * Identify if all limits in a limit profile (including co-borrower limits)
	 * have been activated. The method returns true if ALL limits are activated.
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return boolean
	 */
	public boolean areAllLimitsActivated(ILimitProfile value);

	/**
	 * To get the name of the FAM for the given BCA
	 * 
	 * @param limitProfileID the ID of the limit profile
	 * @return hashmap with ICMSConstant.FAM_CODE, ICMSConstant.FAM_NAME as Key
	 *         with corresponding values
	 */
	public HashMap getFAMName(long limitProfileID) throws LimitException;

	/**
	 * To get the transaction subtype of the limit transaction
	 * 
	 * @param trxID the ID of the transaction
	 * @return the transaction subtype
	 */
	public String getTrxSubTypeByTrxID(long trxID) throws LimitException;

	/**
	 * To get the name of the FAM for the given customer
	 * 
	 * @param customerID the ID of the customer
	 * @return the name of the FAM
	 */
	public HashMap getFAMNameByCustomer(long customerID) throws LimitException;

	/**
	 * Computes the due date of a date given its day period and country code.
	 * 
	 * @param startDate start date
	 * @param numOfDays the number of days before the startDate is due
	 * @param countryCode country code
	 * @return due date
	 */
	public Date getDueDate(Date startDate, int numOfDays, String countryCode);

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String country) throws LimitException;

	/**
	 * Get a list of BFL TAT Parameters belong to the given country.
	 * 
	 * @param country code
	 * @return a list of BFL TAT Parameters
	 */
	public List getBFLTATParameter(String country) throws LimitException;

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location code
	 * @throws LimitException on error getting the booking location
	 */
	public String[] getUniqueBookingLocation(String country) throws LimitException;

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String[] country) throws LimitException;

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public String[] getUniqueBookingLocation(String[] country) throws LimitException;

	/**
	 * Get country given the booking location.
	 * 
	 * @param bookingLocation country code
	 * @return booking location
	 * @throws LimitException on error getting the country code
	 */
	public IBookingLocation getCountryCodeByBookingLocation(String bookingLocation) throws LimitException;

	/**
	 * Get a booking location given the SCI booking location id.
	 * 
	 * @param sciBkgLocknID booking location id from SCI
	 * @return booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation getBookingLocation(long sciBkgLocknID) throws LimitException;

	/**
	 * Identify if there is a reminder for issue clean BFL, given a limit ID
	 * 
	 * @param limitProfileID is of type long
	 * @return boolean true if reminder exist
	 * @throws LimitException on errors
	 */
	public boolean isCleanBFLReminderRequired(long limitProfileID) throws LimitException;

	/**
	 * Given Limit Profile Id, get the Local and Foreign BFL Dates.
	 * @param isRenewed true for renewed, false other
	 * @param segment Customer Segment Code
	 * @param country BCA Origination Country
	 * @param bcaRenewDate date Current BCA Renewed Date.
	 * @return A array of Date, Element 0 represents the local date & Element 1
	 *         represents overseas.
	 * @throws LimitException
	 */
	public Date[] getBFLDueDates(boolean isRenewed, String segment, String country, Date bcaRenewDate)
			throws LimitException;

	/**
	 * To get the name of the TatEntry for the given BCA
	 * 
	 * @param limitProfileID the ID of the limit profile
	 * @return List of OBTATEntry objects
	 */
	public ITATEntry[] getTatEntry(long limitProfileID) throws LimitException;

	/**
	 * get Limit Amount
	 * @param limitIDList
	 * @return
	 * @throws LimitException
	 */
	public HashMap getApprovedLimitAmount(List limitIDList) throws LimitException;

	public HashMap getApprovedLimitAmount4CoBorrower(List limitIDList) throws LimitException;

	/**
	 * Compute the Limit Threshold for Collateral Threshold Allocation batch job
	 * (changed to proxy call)
	 * @param limit
	 * @param dao CollateralThresholdDAO
	 * @param proxy LimitProxy
	 * @throws Exception
	 */
	public void computeLimitThreshold(ILimit limit, CollateralThresholdDAO dao, ILimitProxy proxy) throws Exception;

	/**
	 * Perform Charge Allocation for Collateral Threshold Allocation batch job
	 * (changed to proxy call)
	 * @param col Collateral
	 * @param dao CollateralThresholdDAO
	 * @throws Exception
	 */
	public void performChargeAllocation(ICollateral col, CollateralThresholdDAO dao) throws Exception;

	/**
	 * Compute the Limit Profile Threshold for Collateral Threshold Allocation
	 * batch job (changed to proxy call)
	 * @param profile
	 * @param dao CollateralThresholdDAO
	 * @throws Exception
	 */
	public void computeLimitProfileThreshold(ILimitProfile profile, CollateralThresholdDAO dao) throws Exception;

	/**
	 * Get a HashMap containing the following information: 1. The HashMap keys
	 * are LimitProfileId as Long. 2. The HashMap values are LE_ID+BCA_REF_NUM
	 * as String.
	 * 
	 * @param CMS_COLLATERAL_ID
	 * @return a HashMap with key LimitProfileId and value LE_ID+BCA_REF_NUM.
	 */
	public HashMap getLimitProfileIds(long collateralId) throws LimitException;

	/**
	 * Get a List containing keys of Limit Profile Id
	 * 
	 * 
	 * @param CMS_COLLATERAL_ID
	 * @return a List
	 */
	public List getLimitProfileIdsByApprLmts(long collateralId) throws LimitException;

	/**
	 * Get LEId&BCARef given limitProfileId.
	 * 
	 * @param - limitProfileId
	 * @return LEId&BCARef concatenated as a String.
	 */
	public String getLEIdAndBCARef(long limitProfileId) throws LimitException;

	/**
	 * Maker updates a Limit Profile/AA.
	 * 
	 * @param ctx transaction context
	 * @param trxVal Limit Profile transaction value
	 * @param value a Limit Profile object to use for updating.
	 * @return updated Limit Profile transaction value
	 * @throws LimitException on errors encountered
	 */
	public ICMSTrxResult makerCreateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue,
			ILimitProfile value) throws LimitException;

	/**
	 * Maker close Limit Profile/AA created by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal Limit Profile transaction value
	 * @return the updated Limit Profile transaction value
	 * @throws LimitException on errors encountered
	 */
	public ICMSTrxResult makerCancelCreateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Checker approve Limit Profile/AA deleted by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal Limit Profile transaction value
	 * @return the updated Limit Profile transaction value
	 * @throws LimitException on errors encountered
	 */
	public ICMSTrxResult checkerApproveDeleteLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Maker delete a Limit Profile/AA.
	 * 
	 * @param ctx transaction context
	 * @param trxVal Limit Profile transaction value
	 * @return updated Limit Profile transaction value
	 * @throws LimitException on errors encountered
	 */
	public ICMSTrxResult makerDeleteLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException;

	/**
	 * Maker reassign limit profile segment
	 * 
	 * @param limitProfileIDList limit profie ID list
	 * @param segment Limit Profile new segment value
	 * @param context transaction context
	 * @throws LimitException on errors encountered
	 */
	public void makerReassignLimitProfileSegment(String[] limitProfileIDList, String segment, ITrxContext context)
			throws LimitException;
    
   	public String[] getDistinctOuterLimitID(long limitProfileId) throws LimitException;
   	
   	
   	public boolean getTrxCam(String customerID) throws LimitException;  //Shiv
   	
   	public String getTotalAmountByFacType(String camId,String facType,String climsFacilityId) throws LimitException, RemoteException;

	public ICMSTrxResult submitCreateLimitProfile(OBTrxContext trxContext, ILimitProfileTrxValue limitProfileTrxVal,
			OBLimitProfile newLimitProfile) throws LimitException;
	
}
