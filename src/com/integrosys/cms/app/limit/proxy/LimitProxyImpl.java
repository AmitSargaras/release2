/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/proxy/LimitProxyImpl.java,v 1.45 2006/10/09 05:29:38 jzhai Exp $
 */
package com.integrosys.cms.app.limit.proxy;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
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

/**
 * This class defines the services that are available in for use in the
 * interaction with Limit and Limit Profile.
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.45 $
 * @since $Date: 2006/10/09 05:29:38 $ Tag: $Name: $
 */
public class LimitProxyImpl extends AbstractLimitProxy {
	/**
	 * Identify if a limit profile has been issued CCC. This method returns an
	 * enumerated value in integer to represent if CCC status has not been
	 * issued, partially issued, or full issued. The constant values can be
	 * found in <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile object to be evaluated
	 * @return int
	 */
	public int getCCCStatus(ILimitProfile value) {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getCCCStatus(value);
		}
		catch (Exception e) {
			IllegalStateException ise = new IllegalStateException("Failed to retrieve CCC status ");
			ise.initCause(e);
			throw ise;
		}
	}

	/**
	 * Create a limit PROFILE record, without dual control.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimitProfile(ILimitProfile value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createLimitProfile(value);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a limit profile record, without dual control.
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimitProfile(ILimitProfileTrxValue value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createLimitProfile(value);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ILimit
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimit(ILimit value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createLimit(value);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimit(ILimitTrxValue value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createLimit(value);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createCoBorrowerLimit(value);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a limit record, without dual control.
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createCoBorrowerLimit(ICoBorrowerLimitTrxValue value) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.createCoBorrowerLimit(value);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a Limit given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ILimit if it can be found, or null if the limit does not exist.
	 * @throws LimitException on errors
	 */
	public ILimit getLimit(long limitID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimit limit = proxy.getLimit(limitID);

			limit = prepareLimit(limit);
			return limit;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ILimitTrxValue
	 * @throws LimitException on errors
	 */
	public ILimitTrxValue getTrxLimit(String trxID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimitTrxValue trx = proxy.getTrxLimit(trxID);

			trx = prepareTrxLimit(trx);
			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a Limit Transaction value given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ILimitTrxValue if it can be found, or null if the limit does not
	 *         exist.
	 * @throws LimitException on errors
	 */
	public ILimitTrxValue getTrxLimit(long limitID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimitTrxValue trx = proxy.getTrxLimit(limitID);

			trx = prepareTrxLimit(trx);
			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get transaction belong to the limits of the given limit profile.
	 * 
	 * @param trxCtx transaction context
	 * @param lp of type ILimitProfile
	 * @return limit transaction value
	 * @throws LimitException on any errors encountered
	 */
	public ILimitTrxValue getTrxLimitByLimitProfile(ITrxContext trxCtx, ILimitProfile lp) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getTrxLimitByLimitProfile(trxCtx, lp);
		}
		catch (RemoteException e) {
			throw new LimitException("RemoteException caught!", e);
		}
	}

	/**
	 * get transaction by limit profile
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimitByLimitProfile(ITrxContext trxCtx, ILimitProfile lp)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getTrxCoBorrowerLimitByLimitProfile(trxCtx, lp);
		}
		catch (RemoteException e) {
			throw new LimitException(e);
		}
	}

	/**
	 * Get a CoBorrowerLimit given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimit if it can be found, or null if the limit does
	 *         not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimit getCoBorrowerLimit(long limitID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICoBorrowerLimit limit = proxy.getCoBorrowerLimit(limitID);

			limit = prepareLimit(limit, new HashMap());

			return limit;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(String trxID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICoBorrowerLimitTrxValue trx = proxy.getTrxCoBorrowerLimit(trxID);

			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimits(String trxID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICoBorrowerLimitTrxValue trx = proxy.getTrxCoBorrowerLimits(trxID);

			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a CoBorrowerLimit Transaction value given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimitTrxValue if it can be found, or null if the limit
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(long limitID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICoBorrowerLimitTrxValue trx = proxy.getTrxCoBorrowerLimit(limitID);

			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ILimitProfile getLimitProfileLimitsOnly(long limitProfileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimitProfile limitProf = proxy.getLimitProfile(limitProfileID);

			return limitProf;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a LimitProfile given a limit's ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile if it can be found, or null if the limit does not
	 *         exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfile getLimitProfile(long limitProfileID) throws LimitException {
		try {
			DefaultLogger.debug(this, "Inside LimitProxyImpl.java getLimitProfile() line 421");
			SBLimitProxy proxy = getProxy();
			ILimitProfile profile = proxy.getLimitProfile(limitProfileID);
			profile = prepareLimitProfile(profile);
			DefaultLogger.debug(this, "Out LimitProxyImpl.java getLimitProfile() line 425");
			return profile;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ILimitProfileTrxValue
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(String trxID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimitProfileTrxValue trx = proxy.getTrxLimitProfile(trxID);

			trx = prepareTrxLimitProfile(trx);
			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a LimitProfile Transaction value given a limit's ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfileTrxValue if it can be found, or null if the limit
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(long limitProfileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ILimitProfileTrxValue trx = proxy.getTrxLimitProfile(limitProfileID);

			trx = prepareTrxLimitProfile(trx);
			return trx;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerUpdateLimit(context, trxValue, limit);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * maker update co borrower limit
	 */
	public ICMSTrxResult makerUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerUpdateCoBorrowerLimit(context, trxValue);
			return result;
		}
		catch (RemoteException e) {
			throw new LimitException(e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerUpdateLimit(trxCtx, trxValue, map);
			// result = prepareTrxResultLimit(result);
			return result;
		}
		catch (RemoteException e) {
			throw new LimitException("RemoteException Caught!", e);
		}
	}

	/**
	 * Maker Cancel limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCancelUpdateLimit(context, trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Checker Approve limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerApproveUpdateLimit(context, trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Checker Reject limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerRejectUpdateLimit(context, trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Host Update Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.hostUpdateLimit(context, trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Update Limit.
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemUpdateLimit(ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemUpdateLimit(trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Delete Limit. The caller of this method is responsible for setting
	 * the corresponding limit objects into the ILimitTrxValue, for both actual
	 * and staging limits.
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteLimit(ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemDeleteLimit(trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Delete LimitProfile. The caller of this method is responsible for
	 * setting the corresponding limit objects into the ILimitTrxValue, for both
	 * actual and staging limits.
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteLimitProfile(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemDeleteLimitProfile(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimit(ITrxContext context, ILimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseLimit(context, trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close Limit
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimit(ICMSTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseLimit(trxValue);

			result = prepareTrxResultLimit(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			ICoBorrowerLimit limit) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerUpdateCoBorrowerLimit(context, trxValue, limit);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCancelUpdateCoBorrowerLimit(context, trxValue);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerApproveUpdateCoBorrowerLimit(context, trxValue);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerRejectUpdateCoBorrowerLimit(context, trxValue);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Host Update CoBorrowerLimit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateCoBorrowerLimit(ITrxContext context, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.hostUpdateCoBorrowerLimit(context, trxValue);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Delete CoBorrowerLimit.
	 * 
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteCoBorrowerLimit(ICoBorrowerLimitTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemDeleteCoBorrowerLimit(trxValue);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseCoBorrowerLimit(context, trxValue);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close CoBorrowerLimit
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseCoBorrowerLimit(ICMSTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseCoBorrowerLimit(trxValue);

			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Maker update limitProfile details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @param limitProfile is the ILimitProfile object to be updated
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue,
			ILimitProfile limitProfile) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerUpdateLimitProfile(context, trxValue, limitProfile);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Maker Cancel limitProfile update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult makerCancelUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCancelUpdateLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Checker Approve limitProfile update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerApproveUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerApproveUpdateLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Checker Reject limitProfile update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult checkerRejectUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerRejectUpdateLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Host Update Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult hostUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.hostUpdateLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Update Limit Profile.
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemUpdateLimitProfile(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemUpdateLimitProfile(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close Limit Profile
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close Limit Profile
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimitProfile(ICMSTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.systemCloseLimitProfile(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCreateTAT(context, trxValue, limitProfile);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCancelCreateTAT(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerApproveCreateTAT(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerRejectCreateTAT(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.issueDraftBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.printReminderBFL(context, trxValue, tatEntry);
			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.sendDraftBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.ackReceiptDraftBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.issueCleanTypeBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			ITATEntry tatEntry) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.specialIssueCleanTypeBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Issue Final BFL (TAT)
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult issueFinalBFL(ITrxContext context, ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.issueFinalBFL(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

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
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.customerAcceptBFL(context, trxValue, tatEntry);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Receipt of First Checklist item (TAT)
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult receiptFirstChecklistItem(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.receiptFirstChecklistItem(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * First Generation of CCC
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult cccGenerated(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.cccGenerated(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * First Generation of SCC
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult sccGenerated(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.sccGenerated(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * BCA Processing Completion (TAT)
	 * 
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult completeProcessBCA(ILimitProfileTrxValue trxValue) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.completeProcessBCA(trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Renew an existing BCA. The old BCA transaction and all related
	 * transactions will be system-closed. The new BCA transaction will be
	 * created and all related transactions for checklist will also be created.
	 * Any limits that exist in the old BCA, and also exist in the renewed BCA
	 * will have the getExistingInd method return true. Any limits or security
	 * that has been removed in the new BCA from the security-limit link data
	 * will have a "deleted" status indicator.
	 * 
	 @deprecated use <code>renewBCA(long profileID)</code> instead
	 * @param oldProfileID is of type long and is the limit profile ID of the
	 *        BCA to be closed.
	 * @param newProfile is of type ILimitProfile and is the new limit Profile
	 *        to be created.
	 * @return ICMSTrxResult
	 * @throws LimitException on error
	 */
	/*
	 * public ICMSTrxResult renewBCA(long oldProfileID, ILimitProfile
	 * newProfile) throws LimitException { try { SBLimitProxy proxy =
	 * getProxy(); ICMSTrxResult result = proxy.renewBCA(oldProfileID,
	 * newProfile);
	 * 
	 * result = prepareTrxResultLimitProfile(result); return result; }
	 * catch(LimitException e) { e.printStackTrace(); throw e; } catch(Exception
	 * e) { e.printStackTrace(); throw new LimitException("Caught Exception!",
	 * e); } }
	 */
	/**
	 * Renew an existing BCA.
	 * 
	 * @param profileID is the long value of the limit profile ID to be renewed
	 * @throws LimitException on error
	 */
	public void renewBCA(long profileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			proxy.renewBCA(profileID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * To get the name of the FAM for the given BCA
	 * 
	 * @param limitProfileID the ID of the limit profile
	 * @return the name of the FAM
	 */
	public HashMap getFAMName(long limitProfileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getFAMName(limitProfileID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * To get the name of the FAM for the given customer
	 * 
	 * @param customerID the ID of the customer
	 * @return the name of the FAM
	 */
	public HashMap getFAMNameByCustomer(long customerID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getFAMNameByCustomer(customerID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * To get the transaction subtype of the limit transaction
	 * 
	 * @param trxID the ID of the transaction
	 * @return the transaction subtype
	 */
	public String getTrxSubTypeByTrxID(long trxID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getTrxSubTypeByTrxID(trxID);
		}
		catch (RemoteException ex) {
			throw new LimitException("RemoteException in getTrxSubTypeByTrxID: " + ex.toString());
		}
	}

	/**
	 * Computes the due date of a date given its day period and country code.
	 * 
	 * @param startDate start date
	 * @param numOfDays the number of days before the startDate is due
	 * @param countryCode country code
	 * @return due date
	 */
	public Date getDueDate(Date startDate, int numOfDays, String countryCode) {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getDueDate(startDate, numOfDays, countryCode);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Exception in calculating Due date, returning null.", e);
			return null;
		}
	}

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String country) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getBookingLocationByCountry(country);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location
	 * @throws LimitException on error getting the booking location
	 */
	public String[] getUniqueBookingLocation(String country) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getUniqueBookingLocation(country);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String[] country) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getBookingLocationByCountry(country);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get a list of BFL TAT Parameters belong to the given country.
	 * 
	 * @param country code
	 * @return a list of BFL TAT Parameters
	 */
	public List getBFLTATParameter(String country) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getBFLTATParameter(country);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public String[] getUniqueBookingLocation(String[] country) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getUniqueBookingLocation(country);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get country given the booking location.
	 * 
	 * @param bookingLocation country code
	 * @return booking location
	 * @throws LimitException on error getting the country code
	 */
	public IBookingLocation getCountryCodeByBookingLocation(String bookingLocation) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getCountryCodeByBookingLocation(bookingLocation);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Get a booking location given the SCI booking location id.
	 * 
	 * @param sciBkgLocknID booking location id from SCI
	 * @return booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation getBookingLocation(long sciBkgLocknID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getBookingLocation(sciBkgLocknID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception caught", e);
		}
	}

	/**
	 * Identify if there is a reminder for issue clean BFL, given a limit ID
	 * 
	 * @param limitProfileID is of type long
	 * @return boolean true if reminder exist
	 * @throws LimitException on errors
	 */
	public boolean isCleanBFLReminderRequired(long limitProfileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.isCleanBFLReminderRequired(limitProfileID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception in isCleanBFLReminderRequired", e);
		}
	}

	public Date[] getBFLDueDates(boolean isRenewed, String segment, String country, Date bcaRenewDate)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getBFLDueDates(isRenewed, segment, country, bcaRenewDate);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception in getBFLDueDates", e);
		}
	}

	public ITATEntry[] getTatEntry(long limitProfileID) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getTatEntry(limitProfileID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Exception in getTatEntry", e);
		}
	}

	public HashMap getApprovedLimitAmount(List limitIDList) throws LimitException {
		try {
			return getProxy().getApprovedLimitAmount(limitIDList);
		}
		catch (LimitException e) {
			throw new LimitException(e);
		}
		catch (RemoteException e) {
			throw new LimitException(e);
		}
	}

	public HashMap getApprovedLimitAmount4CoBorrower(List limitIDList) throws LimitException {
		try {
			return getProxy().getApprovedLimitAmount4CoBorrower(limitIDList);
		}
		catch (LimitException e) {
			throw new LimitException(e);
		}
		catch (RemoteException e) {
			throw new LimitException(e);
		}
	}

	// **************** Private Method ************
	/**
	 * Get the SB reference proxy bean
	 * 
	 * @return SBLimitProxyBean
	 * @throws LimitException on error
	 */
	private SBLimitProxy getProxy() throws LimitException {
		SBLimitProxy home = (SBLimitProxy) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_PROXY_JNDI,
				SBLimitProxyHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("SBLimitProxy is null!");
		}
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimit prepareLimit(ILimit limit, HashMap hmap) throws LimitException {
		try {
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();
			if (null == allocList) {
				return limit;
			}

			ICollateralProxy proxy = CollateralProxyFactory.getProxy();

			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation alloc = allocList[i];
				if (null != alloc) {
					ICollateral col = alloc.getCollateral();
					if (null != col) {
						long collateralID = col.getCollateralID();
						// get from hashmap first
						if (null != hmap) {
							ICollateral temp = (ICollateral) hmap.get(new Long(collateralID));
							if (null == temp) {
								temp = proxy.getCollateral(collateralID, false);
								hmap.put(new Long(collateralID), temp);
							}
							if (null != temp) {
								alloc.setCollateral(temp);
								allocList[i] = alloc;
							}
							else {
								throw new LimitException(
										"Collateral from ICollateralProxy.getCollateral() for colalteral ID: "
												+ collateralID + " returns null!");
							}
						}
						else {
							DefaultLogger.debug(this, "prepareLimit, hmap is  null ");
						}
					}
				}
				else {
					DefaultLogger.debug(this, "prepareLimit, ICollateralAllocation is  null ");
				}
			}
			limit.setCollateralAllocations(allocList);
			return limit;
		}
		catch (CollateralException e) {
			throw new LimitException("Caught CollateralException!", e);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ICoBorrowerLimit prepareLimit(ICoBorrowerLimit limit, HashMap hmap) throws LimitException {
		try {
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();
			if (null == allocList) {
				return limit;
			}

			ICollateralProxy proxy = CollateralProxyFactory.getProxy();

			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation alloc = allocList[i];

				if (null != alloc) {

					ICollateral col = alloc.getCollateral();
					if (null != col) {
						long collateralID = col.getCollateralID();
						// get from hashmap first
						if (null != hmap) {
							ICollateral temp = (ICollateral) hmap.get(new Long(collateralID));
							if (null == temp) {
								temp = proxy.getCollateral(collateralID, false);
								hmap.put(new Long(collateralID), temp);
							}
							if (null != temp) {
								alloc.setCollateral(temp);
								allocList[i] = alloc;
							}
							else {
								throw new LimitException(
										"Collateral from ICollateralProxy.getCollateral() for colalteral ID: "
												+ collateralID + " returns null!");
							}
						}
						else {
							DefaultLogger.debug(this, "prepareLimit, hmap is  null ");
						}
					}
				}
				else {
					DefaultLogger.debug(this, "prepareLimit, ICollateralAllocation is  null ");
				}

			}
			limit.setCollateralAllocations(allocList);
			return limit;
		}
		catch (CollateralException e) {
			throw new LimitException("Caught CollateralException!", e);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimit prepareLimit(ILimit limit) throws LimitException {
		return prepareLimit(limit, new HashMap());
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimitTrxValue prepareTrxLimit(ILimitTrxValue trxValue) throws LimitException {
		HashMap hmap = new HashMap();

		ILimit actual = trxValue.getLimit();
		ILimit stage = trxValue.getStagingLimit();

		if (null != actual) {
			actual = prepareLimit(actual, hmap);
		}
		if (null != stage) {
			stage = prepareLimit(stage, hmap);
		}
		trxValue.setLimit(actual);
		trxValue.setStagingLimit(stage);

		return trxValue;
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimitProfile prepareLimitProfile(ILimitProfile profile, HashMap hmap) throws LimitException {
		ILimit[] limitList = profile.getLimits();
		if (null != limitList) {
			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				limit = prepareLimit(limit, hmap);
				ICoBorrowerLimit[] cbLimitList = limit.getCoBorrowerLimits();
				for (int j = 0; j < cbLimitList.length; j++) {
					ICoBorrowerLimit cbLimit = cbLimitList[j];
					cbLimit = prepareLimit(cbLimit, hmap);
				}
				limitList[i] = limit;
			}
		}
		profile.setLimits(limitList);
		return profile;
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimitProfile prepareLimitProfile(ILimitProfile profile) throws LimitException {
		return prepareLimitProfile(profile, new HashMap());
	}

	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	private ILimitProfileTrxValue prepareTrxLimitProfile(ILimitProfileTrxValue trxValue) throws LimitException {
		HashMap hmap = new HashMap();

		ILimitProfile actual = trxValue.getLimitProfile();
		ILimitProfile stage = trxValue.getStagingLimitProfile();

		if (null != actual) {
			actual = prepareLimitProfile(actual, hmap);
		}
		if (null != stage) {
			stage = prepareLimitProfile(stage, hmap);
		}
		// LimitDAO dao = new LimitDAO();
		/*
		 * actual.setApproverEmployeeID1(dao.getCoinCode(actual.getLimitProfileID
		 * ()));
		 * stage.setApproverEmployeeID1(dao.getCoinCode(stage.getLimitProfileID
		 * ()));
		 */
		trxValue.setLimitProfile(actual);
		trxValue.setStagingLimitProfile(stage);

		return trxValue;
	}

	private ICMSTrxResult prepareTrxResultLimitProfile(ICMSTrxResult result) throws LimitException {
		try {
			ILimitProfileTrxValue trx = (ILimitProfileTrxValue) result.getTrxValue();
			trx = prepareTrxLimitProfile(trx);
			result.setTrxValue(trx);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	private ICMSTrxResult prepareTrxResultLimit(ICMSTrxResult result) throws LimitException {
		try {
			ILimitTrxValue trx = (ILimitTrxValue) result.getTrxValue();
			trx = prepareTrxLimit(trx);
			result.setTrxValue(trx);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a HashMap containing the following information: 1. The HashMap keys
	 * are LimitProfileId as Long. 2. The HashMap values are LE_ID+BCA_REF_NUM
	 * as String.
	 * 
	 * @param CMS_COLLATERAL_ID
	 * @return a HashMap with key LimitProfileId and value LE_ID+BCA_REF_NUM.
	 * @throws LimitException on errors
	 */
	public HashMap getLimitProfileIds(long collateralId) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getLimitProfileIds(collateralId);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a List containing keys of Limit Profile Id
	 * 
	 * @param CMS_COLLATERAL_ID
	 * @return a HashMap with key LimitProfileId and value LE_ID+BCA_REF_NUM.
	 * @throws LimitException on errors
	 */
	public List getLimitProfileIdsByApprLmts(long collateralId) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getLimitProfileIdsByApprLmts(collateralId);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get LEId&BCARef given limitProfileId.
	 * 
	 * @param - limitProfileId
	 * @return LEId&BCARef concatenated as a String.
	 */
	public String getLEIdAndBCARef(long limitProfileId) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			return proxy.getLEIdAndBCARef(limitProfileId);
		}
		catch (LimitException e) {
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ICMSTrxResult makerCreateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue,
			ILimitProfile limitProfile) throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCreateLimitProfile(context, trxValue, limitProfile);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ICMSTrxResult makerCancelCreateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerCancelCreateLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ICMSTrxResult checkerApproveDeleteLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.checkerApproveDeleteLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ICMSTrxResult makerDeleteLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			ICMSTrxResult result = proxy.makerDeleteLimitProfile(context, trxValue);

			result = prepareTrxResultLimitProfile(result);
			return result;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Host Update Limit
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ILimitProfileTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public void makerReassignLimitProfileSegment(String[] limitProfileIDList, String segment, ITrxContext context)
			throws LimitException {
		try {
			SBLimitProxy proxy = getProxy();
			proxy.makerReassignLimitProfileSegment(limitProfileIDList, segment, context);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

    /**
     * Get a list of Distinct Outer Limit ID belong to the given Limit Profile ID.
     *
     * @param limitProfileId is of type long
     * @return a list of Distinct Outer Limit ID
     * @throws LimitException on error getting the booking location
     */
    public String[] getDistinctOuterLimitID (long limitProfileId)
        throws LimitException
    {
        try {
            SBLimitProxy proxy = getProxy();
            return proxy.getDistinctOuterLimitID (limitProfileId);
        }
        catch (LimitException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new LimitException ("Exception caught", e);
        }
    }
    
    public boolean getTrxCam(String customerID) throws LimitException {
    	try {
            SBLimitProxy proxy = getProxy();
            return proxy.getTrxCam(customerID);
        }
        catch (LimitException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new LimitException ("Exception caught", e);
        }
    }
    
    public String getTotalAmountByFacType(String camId,String facType,String climsFacilityID) throws LimitException {
    	try {
            SBLimitProxy proxy = getProxy();
            return proxy.getTotalAmountByFacType(camId,facType,climsFacilityID);
        }
        catch (LimitException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new LimitException ("Exception caught", e);
        }
    }

	
	public ICMSTrxResult submitCreateLimitProfile(OBTrxContext trxContext, ILimitProfileTrxValue limitProfileTrxVal,
			OBLimitProfile newLimitProfile) throws LimitException {
		
		try {
			 SBLimitProxy proxy = getProxy();
	         return proxy.submitCreateLimitProfile(trxContext, limitProfileTrxVal, newLimitProfile);
		}catch (LimitException e) {
			
			throw e;
		}
		catch (Exception e) {
			
			throw new LimitException("Exception caught! " + e.toString(), e);
		}
		
	}
	
}
