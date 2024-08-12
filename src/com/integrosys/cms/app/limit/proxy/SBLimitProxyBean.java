/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/proxy/SBLimitProxyBean.java,v 1.66 2006/11/23 08:12:05 jychong Exp $
 */
package com.integrosys.cms.app.limit.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.limit.bus.CoBorrowerLimitUtils;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBTATEntry;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.limit.bus.TATValidator;
import com.integrosys.cms.app.limit.trx.CoBorrowerLimitTrxControllerFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.LimitProfileTrxControllerFactory;
import com.integrosys.cms.app.limit.trx.LimitTrxControllerFactory;
import com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.OBCMSTrxSearchResult;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * This session bean provides the implementation of the AbstractCAProxy, wrapped
 * in an EJB mechanism.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.66 $
 * @since $Date: 2006/11/23 08:12:05 $ Tag: $Name: $
 */
public class SBLimitProxyBean extends AbstractLimitProxy implements javax.ejb.SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBLimitProxyBean() {
	}

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
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			boolean allIssued = proxy.isAllCCCGenerated(value);
			if (true == allIssued) {
				return ICMSConstant.CCC_FULL_ISSUED;
			}
			else {
				return ICMSConstant.CCC_NOT_ISSUED;
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Error in getCCCStatus!", e);
			return ICMSConstant.CCC_NOT_ISSUED;
		}
	}

	/**
	 * Create a limit profile record, without dual control.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createLimitProfile(ILimitProfile value) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_LIMIT_PROFILE);

			OBLimitProfileTrxValue trxValue = new OBLimitProfileTrxValue();
			trxValue.setStagingLimitProfile(value);

			/*
			 * ITrxContext context = new OBTrxContext();
			 * trxValue.setTrxContext(context);
			 */

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_LIMIT_PROFILE);

			/*
			 * ITrxContext context = new OBTrxContext();
			 * value.setTrxContext(context);
			 */

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);

			/*
			 * //Used to generate default DDN from MQ long profileID =
			 * value.getLimitProfileID(); if(ICMSConstant.LONG_INVALID_VALUE ==
			 * profileID) { throw new LimitException("ProfileID is invalid!"); }
			 * ILimitProfile profile = getLimitProfile(profileID); if(null ==
			 * profile) { throw new
			 * LimitException("ILimitProfile is null for ProfileID: " +
			 * profileID); } //2. create default DDN if required only
			 * //handleExistingSCCertificate(profile, profile);
			 * createDefaultLLI(profile);
			 */
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_LIMIT);

			OBLimitTrxValue trxValue = new OBLimitTrxValue();
			trxValue.setStagingLimit(value);
			trxValue.setLimit(value); // since this is created by system

			/*
			 * ITrxContext context = new OBTrxContext();
			 * trxValue.setTrxContext(context);
			 */

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_LIMIT);

			/*
			 * ITrxContext context = new OBTrxContext();
			 * value.setTrxContext(context);
			 */

			ITrxController controller = (new LimitTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a CoBorrowerlimit record, without dual control.
	 * 
	 * @param value is of type ILimit
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult createCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_CO_LIMIT);

			OBCoBorrowerLimitTrxValue trxValue = new OBCoBorrowerLimitTrxValue();
			trxValue.setStagingLimit(value);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			SBLimitManager mgr = getLimitManager();
			return mgr.getLimit(limitID);
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_LIMIT);

			ILimitTrxValue trxValue = new OBLimitTrxValue(); // trx type init in
			// creation of
			// ob
			trxValue.setTransactionID(trxID);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ILimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_LIMIT_ID);

			ILimitTrxValue trxValue = new OBLimitTrxValue(); // trx type init in
			// creation of
			// ob
			trxValue.setReferenceID(String.valueOf(limitID));

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ILimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
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
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_LIMIT_BY_LPID);
			ILimitTrxValue trxValue = new OBLimitTrxValue();
			trxValue.setLimitProfileID(lp.getLimitProfileID());
			trxCtx.setLimitProfile(lp);
			trxValue.setTrxContext(trxCtx);
			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ILimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
		}
	}

	/**
	 * get co borrower limit by limit profile
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimitByLimitProfile(ITrxContext trxCtx, ILimitProfile lp)
			throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CO_BORROWER_LIMIT_BY_LPID);
			trxCtx.setLimitProfile(lp);
			ICoBorrowerLimitTrxValue trxValue = new OBCoBorrowerLimitTrxValue();
			trxValue.setLimitProfileID(lp.getLimitProfileID());
			trxValue.setTrxContext(trxCtx);
			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICoBorrowerLimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException(e);
		}
	}

	/**
	 * Get a CoBorrower Limit given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimit if it can be found, or null if the limit does
	 *         not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimit getCoBorrowerLimit(long limitID) throws LimitException {
		try {
			SBLimitManager mgr = getLimitManager();
			return mgr.getCoBorrowerLimit(limitID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a CoBorrower limit transaction give a transaction reference ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimits(String trxID) throws LimitException {
		try {

			LimitDAO dao = new LimitDAO();
			String[] trxIDs = dao.getCoBorrowerLimitTrxIDByTrxRefID(trxID);
			int size = 0;
			if ((trxIDs == null) || ((size = trxIDs.length) == 0)) {
				throw new LimitException("No transactions attached to the trx reference id!!!");
			}

			ICoBorrowerLimitTrxValue[] trxVals = new OBCoBorrowerLimitTrxValue[size];
			for (int i = 0; i < size; i++) {
				trxVals[i] = getTrxCoBorrowerLimit(trxIDs[i]);
			}

			ICoBorrowerLimitTrxValue trxValue = new OBCoBorrowerLimitTrxValue();
			trxValue.setCoBorrowerLimitTrxValues(trxVals);

			return trxValue;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a CoBorrower limit transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICoBorrowerLimitTrxValue
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(String trxID) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CO_LIMIT);

			ICoBorrowerLimitTrxValue trxValue = new OBCoBorrowerLimitTrxValue();
			trxValue.setTransactionID(trxID);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICoBorrowerLimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a CoBorrower Limit Transaction value given a limit's ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimitTrxValue if it can be found, or null if the limit
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimitTrxValue getTrxCoBorrowerLimit(long limitID) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CO_LIMIT_ID);

			ICoBorrowerLimitTrxValue trxValue = new OBCoBorrowerLimitTrxValue();
			trxValue.setReferenceID(String.valueOf(limitID));

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICoBorrowerLimitTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a LimitProfile given a limitProfile's ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile if it can be found, or null if the limitProfile
	 *         does not exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfile getLimitProfile(long limitProfileID) throws LimitException {
		try {
			SBLimitManager mgr = getLimitManager();
			return mgr.getLimitProfile(limitProfileID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limitProfile transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ILimitProfileTrxValue
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(String trxID) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_LIMIT_PROFILE);

			ILimitProfileTrxValue trxValue = new OBLimitProfileTrxValue();
			trxValue.setTransactionID(trxID);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ILimitProfileTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a LimitProfile Transaction value given a limitProfile's ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfileTrxValue if it can be found, or null if the
	 *         limitProfile does not exist.
	 * @throws LimitException on errors
	 */
	public ILimitProfileTrxValue getTrxLimitProfile(long limitProfileID) throws LimitException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_LIMIT_PROFILE_ID);

			ILimitProfileTrxValue trxValue = new OBLimitProfileTrxValue();
			trxValue.setReferenceID(String.valueOf(limitProfileID));

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ILimitProfileTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new LimitException("Caught TransactionException!", e);
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT);

			trxValue.setTransactionSubType(null);
			trxValue.setStagingLimit(limit);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Helper method to reset limits into actual or staging limit.
	 * 
	 * @param trxVal limit transaction value
	 * @param map contains key:transaction id, value:ILimit object
	 * @param isActual true if to reset actual limits, false to reset staging
	 *        limits
	 */
	private void resetLimits(ILimitTrxValue trxVal, HashMap map, boolean isActual) {
		ILimitTrxValue[] trxValues = trxVal.getLimitTrxValues();
		int count = trxValues.length;
		if (isActual) {
			for (int i = 0; i < count; i++) {
				trxValues[i].setTrxReferenceID(trxVal.getTransactionID());
				trxValues[i].setTrxContext(trxVal.getTrxContext());
				trxValues[i].setLimit((ILimit) map.get(trxValues[i].getTransactionID()));
			}
		}
		else {
			for (int i = 0; i < count; i++) {
				trxValues[i].setTrxReferenceID(trxVal.getTransactionID());
				trxValues[i].setTrxContext(trxVal.getTrxContext());
				trxValues[i].setStagingLimit((ILimit) map.get(trxValues[i].getTransactionID()));
			}
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
			if (trxValue == null) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			if ((trxValue.getLimitTrxValues() == null) || (trxValue.getLimitTrxValues().length == 0)) {
				throw new LimitException("ILimitTrxValue list is empty or null");
			}
			trxValue.setTrxContext(trxCtx);
			resetLimits(trxValue, map, false); // reset limits and trxRefId to
			// the trxval
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ICMSTrxResult makerUpdateCoBorrowerLimit(ITrxContext trxCtx, ICoBorrowerLimitTrxValue trxValue)
			throws LimitException {
		try {
			if (trxValue == null) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null! ");
			}
			if ((trxValue.getCoBorrowerLimitTrxValues() == null)
					|| (trxValue.getCoBorrowerLimitTrxValues().length == 0)) {
				throw new LimitException("ICoBorrowerLimitTrxValue list is null or empty!");
			}
			trxValue.setTrxContext(trxCtx);
			ICoBorrowerLimitTrxValue[] trxValues = trxValue.getCoBorrowerLimitTrxValues();
			for (int i = 0; i < trxValues.length; i++) {
				trxValues[i].setTrxReferenceID(trxValue.getTransactionID());
				trxValues[i].setTrxContext(trxValue.getTrxContext());
			}

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CO_LIMIT);
			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException(e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException(e);
		}
	}

	/*
	 * Maker Resubmit limit update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 * object
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * 
	 * @param limit is the ILimit object to be updated
	 * 
	 * @return ICMSTrxResult
	 * 
	 * @throws LimitException on errors
	 * 
	 * public ICMSTrxResult makerResubmitUpdateLimit(ITrxContext context,
	 * ILimitTrxValue trxValue, ILimit limit) throws LimitException { try {
	 * OBCMSTrxParameter param = new OBCMSTrxParameter();
	 * param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_LIMIT);
	 * 
	 * trxValue.setStagingLimit(limit);
	 * 
	 * ITrxController controller = (new
	 * LimitTrxControllerFactory()).getController(trxValue, param); ITrxResult
	 * result = controller.operate(trxValue, param); return
	 * (ICMSTrxResult)result; } catch(TransactionException e) {
	 * _context.setRollbackOnly(); e.printStackTrace(); throw new
	 * LimitException("Caught TransactionException!", e); } catch(Exception e) {
	 * _context.setRollbackOnly(); e.printStackTrace(); throw new
	 * LimitException("Caught Exception!", e); } }
	 */
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_UPDATE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Delete Limit
	 * 
	 * @param trxValue is ILimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteLimit(ILimitTrxValue trxValue) throws LimitException {
		try {
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			if (trxValue.getStatus().equals(ICMSConstant.STATE_DELETED)) { // do
				// nothing
				OBCMSTrxResult result = new OBCMSTrxResult();
				result.setTrxValue(trxValue);
				return result;
			}

			/*
			 * trxValue.setTrxContext(new OBTrxContext());
			 */

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			if (trxValue.getStatus().equals(ICMSConstant.STATE_DELETED)) { // do
				// nothing
				OBCMSTrxResult result = new OBCMSTrxResult();
				result.setTrxValue(trxValue);
				return result;
			}

			// Close recurrent checklist trx if any
			IRecurrentProxyManager chkMgr = (IRecurrentProxyManager) BeanHouse.get("recurrentProxy");
			chkMgr.systemCloseRecurrentCheckList(trxValue.getLimitProfile().getLimitProfileID(), trxValue
					.getLimitProfile().getCustomerID());

			// Close DDN trx if any
			IDDNProxyManager ddnMgr = DDNProxyManagerFactory.getDDNProxyManager();
			ddnMgr.systemCloseDDN(trxValue.getLimitProfile());

			/*
			 * trxValue.setTrxContext(new OBTrxContext());
			 */

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICMSTrxValue is null!");
			}

			ILimitTrxValue value = getTrxLimit(trxValue.getTransactionID());

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT);

			ITrxController controller = (new LimitTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CO_LIMIT);

			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setStagingLimit(limit);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CO_LIMIT);
			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_UPDATE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Delete CoBorrowerLimit
	 * 
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteCoBorrowerLimit(ICoBorrowerLimitTrxValue trxValue) throws LimitException {
		return systemDeleteCoBorrowerLimit(trxValue, null);
	}

	/**
	 * System Delete CoBorrowerLimit
	 * 
	 * @param trxValue is ICoBorrowerLimitTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemDeleteCoBorrowerLimit(ICoBorrowerLimitTrxValue trxValue, ILimitProfile profile)
			throws LimitException {
		try {
			if (null == trxValue) {
				throw new LimitException("ICoBorrowerLimitTrxValue is null!");
			}
			if (trxValue.getStatus().equals(ICMSConstant.STATE_DELETED)) { // do
				// nothing
				OBCMSTrxResult result = new OBCMSTrxResult();
				result.setTrxValue(trxValue);
				return result;
			}

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_DELETE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);

			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ICMSTrxValue is null!");
			}

			ICoBorrowerLimitTrxValue value = getTrxCoBorrowerLimit(trxValue.getTransactionID());

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CO_LIMIT);

			ITrxController controller = (new CoBorrowerLimitTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			// trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT_PROFILE);

			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			trxValue.setStagingLimitProfile(limitProfile);

			
			if (isDuplicateAANumber(trxValue)) {
				LimitException exp = new LimitException("Cannot create LimitProfile");
				exp.setErrorCode(LimitException.ERR_DUPLICATE_AA_NUM);
				throw exp;
			}
			else {
			
				ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
				ITrxResult result = controller.operate(constructTrxValue(context, trxValue), param);
				return (ICMSTrxResult) result;
			}
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/*
	 * Maker Resubmit limitProfile update
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
	 * LimitException { try { OBCMSTrxParameter param = new OBCMSTrxParameter();
	 * param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_LIMIT_PROFILE);
	 * 
	 * trxValue.setStagingLimitProfile(limitProfile);
	 * 
	 * ITrxController controller = (new
	 * LimitProfileTrxControllerFactory()).getController(trxValue, param);
	 * ITrxResult result = controller.operate(trxValue, param); return
	 * (ICMSTrxResult)result; } catch(TransactionException e) {
	 * _context.setRollbackOnly(); e.printStackTrace(); throw new
	 * LimitException("Caught TransactionException!", e); } catch(Exception e) {
	 * _context.setRollbackOnly(); e.printStackTrace(); throw new
	 * LimitException("Caught Exception!", e); } }
	 */
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			// trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(constructTrxValue(context, trxValue), param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			// trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(constructTrxValue(context, trxValue), param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			LimitException le= new LimitException("");
			try {
				 le= (LimitException) e.getCause();
				
			} catch (Exception e2) {
				throw new LimitException("Caught TransactionException!", e);
			}
			throw le;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			// trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(constructTrxValue(context, trxValue), param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Host Update LimitProfile
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			/*
			 * trxValue.setTrxContext(new OBTrxContext());
			 */

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
	private ICMSTrxResult makerDirectUpdateLimitProfile(ITrxContext context, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			/*
			 * trxValue.setTrxContext(new OBTrxContext());
			 */

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_DIRECT_UPDATE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(constructTrxValue(context, trxValue), param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close LimitProfile
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
			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * System Close LimitProfile
	 * 
	 * @param trxValue is ICMSTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws LimitException on errors
	 */
	public ICMSTrxResult systemCloseLimitProfile(ICMSTrxValue trxValue) throws LimitException {
		try {
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			ILimitProfileTrxValue value = getTrxLimitProfile(trxValue.getTransactionID());

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			/*
			 * no tat entry for create due maker/checker in create ITATEntry tat
			 * = createTATObject(ICMSConstant.TAT_CODE_CREATE_TAT); trxValue =
			 * prepareTATTrxValue(tat, trxValue);
			 */

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_TAT);

			if (null == trxValue) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}
			trxValue.setStagingLimitProfile(limitProfile);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_TAT);
			String bcaStatus = trxValue.getStagingLimitProfile().getBCAStatus();

			if ((bcaStatus == null) || ((bcaStatus != null) && !bcaStatus.equals(ICMSConstant.STATE_DELETED))) {
				trxValue.getStagingLimitProfile().setBCAStatus(ICMSConstant.STATE_ACTIVE);
			}

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (trxValue == null) {
				throw new LimitException("ILimitProfileTrxValue is null!");
			}

			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_ISSUE_FINAL_BFL);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL, tatEntry);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ILimitProfile profile = trxValue.getLimitProfile();
			boolean limitsActivated = areAllLimitsActivated(profile);

			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			boolean sccGen = sccProxy.isSCCFullyGenerated(profile);

			if (limitsActivated && sccGen) {
				profile.setBCACompleteInd(true);
				DefaultLogger.debug(this, "BCA is completed");
			}

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_REC_FIRST_CHECKLIST);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_GEN_CCC);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_GEN_SCC);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
			if (null == trxValue) {
				throw new LimitException("ILimitTrxValue is null!");
			}

			ITATEntry tat = createTATObject(ICMSConstant.TAT_CODE_COMPLETE_BCA);
			trxValue = prepareTATTrxValue(tat, trxValue);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_UPDATE_TAT);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			_context.setRollbackOnly();
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
	 * @deprecated use <code>renewBCA(long profileID)</code> instead
	 * @param oldProfileID is of type long and is the limit profile ID of the
	 *        BCA to be closed.
	 * @param newProfile is of type ILimitProfile and is the new limit Profile
	 *        to be created.
	 * @return ICMSTrxResult
	 * @throws LimitException on error
	 */
	/*
	 * public ICMSTrxResult renewBCA(long oldProfileID, ILimitProfile
	 * newProfile) throws LimitException { try {
	 * if(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE
	 * == oldProfileID) { throw new
	 * LimitException("oldProfileID is undefined!"); } if(null == newProfile) {
	 * throw new LimitException("newProfile is null!"); }
	 * 
	 * //1. Retrieve ILimitProfileTrxValue given oldProfileID
	 * ILimitProfileTrxValue oldTrx = getTrxLimitProfile(oldProfileID);
	 * 
	 * //2. Get all transactions related to this LimitProfile transaction
	 * SBCMSTrxManager trxMgr = getTrxManager(); ICMSTrxValue[] relatedTrxList =
	 * trxMgr.getTrxByParentTrxID(oldTrx.getTransactionID());
	 * 
	 * if(null != relatedTrxList) { //prepare to system close each one
	 * systemCloseRelatedTrx(relatedTrxList);
	 * 
	 * }
	 * 
	 * //3. Create limit profile transaction newProfile.setRenewalInd(true);
	 * ILimit[] limitList = newProfile.getLimits(); ICMSTrxResult result =
	 * createLimitProfile(newProfile); newProfile =
	 * ((ILimitProfileTrxValue)result.getTrxValue()).getLimitProfile();
	 * newProfile.setLimits(limitList); //4. for each limit in limit profile,
	 * renew it if existing, create new if not existing ILimitProfileTrxValue
	 * newTrx = (ILimitProfileTrxValue)result.getTrxValue();
	 * renewLimits(oldTrx.getLimitProfile(), newProfile);
	 * 
	 * //5. make copy of checklists (non-collateral) if exist, and at same time
	 * update custodian trx with new IDs if(null != relatedTrxList) {
	 * createNonCollateralChecklistTrx(newProfile.getLimitProfileID(),
	 * relatedTrxList); createCollateralChecklistTrx(newProfile,
	 * relatedTrxList); }
	 * 
	 * //6. make copy collateral checklist by inspecting the collateral
	 * allocation, and identifying // the collaterals that are deleted, and the
	 * ones that are copied.
	 * 
	 * //7. To convert SCC to DDN if any
	 * handleExistingSCCertificate(oldTrx.getLimitProfile(), newProfile);
	 * 
	 * //lastly: before returning, fetch limit profile again to refresh all
	 * newly created limit data newTrx =
	 * getTrxLimitProfile(newTrx.getTransactionID());
	 * result.setTrxValue(newTrx); return result;
	 * 
	 * } catch(LimitException e) { _context.setRollbackOnly();
	 * e.printStackTrace(); throw e; } catch(Exception e) {
	 * _context.setRollbackOnly(); e.printStackTrace(); throw new
	 * LimitException("Caught Exception!", e); } }
	 */
	/**
	 * Renew an existing BCA.
	 * 
	 * @param profileID is the long value of the limit profile ID to be renewed
	 * @throws LimitException on error
	 */
	public void renewBCA(long profileID) throws LimitException {
		try {
			// 1. fetch BCA
			if (ICMSConstant.LONG_INVALID_VALUE == profileID) {
				throw new LimitException("ProfileID is invalid!");
			}
			ILimitProfile profile = getLimitProfile(profileID);
			if (null == profile) {
				throw new LimitException("ILimitProfile is null for ProfileID: " + profileID);
			}

			// 2. create default DDN if required only
			// handleExistingSCCertificate(profile, profile);
			createDefaultDDN(profile);
			// 3. find all limits that are marked for delete, and where the cms
			// status is not already deleted
			profile = deleteMarkedLimits(profile); // method will refresh the
			// profile
			// 4. find all coborrower limits that marked for delete, and where
			// the cms status is not already deleted
			profile = deleteMarkedCoBorrowerLimits(profile); // method will
			// refresh the
			// profile
			// 5. next identify collaterals which
			ICollateral[] colList = getMarkedCollaterals(profile);
			// 6. delete checklist associated to this collateral
			deleteCollateralChecklists(colList, profile);
			// 7. system close all PSCC, SCC, CCC transactions if any (exclude
			// DDN since it'll be handled by handleExistingSCCertificate)
			systemCloseTrx(profile);
			// 8. renew checklist
			renewCheckLists(profile);
			// 9. void all other flags and TAT
			resetProfile(profile);
			// renewal is complete
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
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
		LimitDAO dao = new LimitDAO();
		return dao.getCleanBFLReminderRequiredInd(limitProfileID);
	}

	/**
	 * get Limit Amount
	 */
	public HashMap getApprovedLimitAmount(List limitIDList) throws LimitException {
		LimitDAO dao = new LimitDAO();
		return dao.getApprovedLimitAmount(limitIDList);
	}

	public HashMap getApprovedLimitAmount4CoBorrower(List limitIDList) throws LimitException {
		LimitDAO dao = new LimitDAO();
		return dao.getApprovedLimitAmount4CoBorrower(limitIDList);
	}

	// ***************** Private Methods
	private void createDefaultDDN(ILimitProfile anILimitProfile) throws LimitException {
		try {
			IDDNProxyManager ddnProxy = DDNProxyManagerFactory.getDDNProxyManager();
			// close DDN if exist
			ddnProxy.systemCloseDDN(anILimitProfile);
			// close LLI if exist
			// ddnProxy.systemCloseLLI(anILimitProfile);
			ddnProxy.systemCreateDefaultDDN(anILimitProfile);
		}
		catch (Exception ex) {
			throw new LimitException("Error in createDefaultDDN !!!", ex);
		}
	}

	/*
	 * private void createDefaultLLI(ILimitProfile anILimitProfile) throws
	 * LimitException { try { IDDNProxyManager ddnProxy =
	 * DDNProxyManagerFactory.getDDNProxyManager(); //close DDN if exist
	 * ddnProxy.systemCloseLLI(anILimitProfile);
	 * ddnProxy.systemCreateDefaultLLI(anILimitProfile); } catch(Exception ex) {
	 * throw new LimitException("Error in createDefaultLLI !!!", ex); } }
	 */
	/*
	 * private void handleExistingSCCertificate(ILimitProfile oldLimitProfile,
	 * ILimitProfile newLimitProfile) throws LimitException { try {
	 * IDDNProxyManager ddnProxy = DDNProxyManagerFactory.getDDNProxyManager();
	 * 
	 * //close DDN if exist ddnProxy.systemCloseDDN(oldLimitProfile);
	 * 
	 * ILimitProfile fullProfile =
	 * getLimitProfile(oldLimitProfile.getLimitProfileID());
	 * ISCCertificateProxyManager proxy =
	 * SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
	 * DefaultLogger.debug(this, "LimitProfile: " + fullProfile); ISCCertificate
	 * scc = proxy.getSCCertificateByLimitProfile(fullProfile); if (scc != null)
	 * { ITrxContext trxContext = new OBTrxContext(); IDDNTrxValue trxValue =
	 * ddnProxy.convertSCCToDDN(trxContext, newLimitProfile, scc); } else {
	 * IPartialSCCertificate pscc =
	 * proxy.getPartialSCCertificateByLimitProfile(fullProfile); if (pscc !=
	 * null) { ITrxContext trxContext = new OBTrxContext(); IDDNTrxValue
	 * trxValue = ddnProxy.convertPartialSCCToDDN(trxContext, newLimitProfile,
	 * pscc); } else { //TODO: to create DDN for clean type // how to check for
	 * clean type before the renewal ???? } } } catch(Exception ex) { throw new
	 * LimitException("Error in handleExistingSCCertificate !!!", ex); }
	 * 
	 * } / Get the transaction manager
	 */
	private SBCMSTrxManager getTrxManager() throws LimitException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());
		if (null == mgr) {
			throw new LimitException("SBCMSTrxManager is null!");
		}
		else {
			return mgr;
		}
	}

	/**
	 * Get the SB for the actual storage of Limit
	 * 
	 * @return SBLimitManager
	 * @throws Exception on errors
	 */
	private SBLimitManager getLimitManager() throws Exception {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
				SBLimitManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new Exception("SBLimitManager for Actual is null!");
		}
	}

	/**
	 * Helper method to create new checklist (non-collateral) trx, clone
	 * checklist data, and udpate custodian trx and biz of new IDs
	 */
	private void createNonCollateralChecklistTrx(long limitProfileID, ICMSTrxValue[] relatedTrx) throws LimitException {
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();

			for (int i = 0; i < relatedTrx.length; i++) {
				ICMSTrxValue trx = relatedTrx[i];
				if (ICMSConstant.INSTANCE_CHECKLIST.equals(trx.getTransactionType())) {
					// make copy of the trx and the biz data
					ICheckListTrxValue oldTrx = proxy.getCheckListByTrxID(trx.getTransactionID());
					ICheckList ck = oldTrx.getCheckList();

					if (ck.getCheckListOwner() instanceof ICCCheckListOwner) {
						ICCCheckListOwner oldOwner = (ICCCheckListOwner) ck.getCheckListOwner();

						ICheckListOwner newOwner = new OBCCCheckListOwner(limitProfileID, oldOwner.getSubOwnerID(),
								oldOwner.getSubOwnerType());
						ck.setCheckListOwner(newOwner);

						ITrxContext context = new OBTrxContext();
						ICheckListTrxValue newTrx = proxy.copyCheckList(context, ck);

						// update custodian transactions
						// updateCustodianTrx(oldTrx, newTrx);
					}
				}
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Unknown Exception!", e);
		}
	}

	/**
	 * Helper method to create new checklist (collateral) trx, clone checklist
	 * data, and udpate custodian trx and biz of new IDs
	 */
	private void createCollateralChecklistTrx(ILimitProfile newLimitProfile, ICMSTrxValue[] relatedTrx)
			throws LimitException {
		try {
			ILimit[] limitList = newLimitProfile.getLimits();
			DefaultLogger.debug(this, "Number of Limits:" + limitList.length);
			if ((limitList != null) && (limitList.length > 0)) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				for (int i = 0; i < relatedTrx.length; i++) {
					ICMSTrxValue trx = relatedTrx[i];
					if (ICMSConstant.INSTANCE_CHECKLIST.equals(trx.getTransactionType())) {
						// make copy of the trx and the biz data
						ICheckListTrxValue oldTrx = proxy.getCheckListByTrxID(trx.getTransactionID());
						ICheckList ck = oldTrx.getCheckList();
						for (int jj = 0; jj < limitList.length; jj++) {
							if (limitList[jj].getExistingInd()) {
								ICollateralAllocation[] colAllocationList = limitList[jj].getCollateralAllocations();
								if ((colAllocationList != null) && (colAllocationList.length > 0)) {
									DefaultLogger.debug(this, "Num of ColAllocation: " + colAllocationList.length);
									for (int kk = 0; kk < colAllocationList.length; kk++) {
										DefaultLogger.debug(this, "CollateralID: "
												+ colAllocationList[kk].getCollateral().getCollateralID());
										if (ck.getCheckListOwner() instanceof ICollateralCheckListOwner) {
											ICollateralCheckListOwner oldOwner = (ICollateralCheckListOwner) ck
													.getCheckListOwner();
											if (colAllocationList[kk].getCollateral().getCollateralID() == oldOwner
													.getCollateralID()) {
												ICheckListOwner newOwner = new OBCollateralCheckListOwner(
														newLimitProfile.getLimitProfileID(), oldOwner.getCollateralID());
												ck.setCheckListOwner(newOwner);

												ITrxContext context = new OBTrxContext();
												ICheckListTrxValue newTrx = proxy.copyCheckList(context, ck);

												// updateCustodianTrx(oldTrx,
												// newTrx);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Unknown Exception!", e);
		}
	}

	/**
	 * Helper method to renew limits
	 */
	private void renewLimits(ILimitProfile oldProfile, ILimitProfile newProfile) throws LimitException {
		try {
			ILimit[] newLimits = newProfile.getLimits();
			if (null != newLimits) {
				ILimit[] oldLimits = oldProfile.getLimits();
				if (null != oldLimits) {
					for (int i = 0; i < newLimits.length; i++) {
						ILimit newLimit = newLimits[i];
						for (int j = 0; j < oldLimits.length; j++) {
							ILimit oldLimit = oldLimits[j];

							if (newLimit.getLimitRef().equals(oldLimit.getLimitRef())) { // matches
								newLimit.setExistingInd(true);
								newLimits[i] = newLimit;
							}
						}
					}
				}

				// now create limit transaction
				for (int k = 0; k < newLimits.length; k++) {
					newLimits[k].setLimitProfileID(newProfile.getLimitProfileID());
					ICMSTrxResult result = createLimit(newLimits[k]);
					newLimits[k] = ((ILimitTrxValue) result.getTrxValue()).getLimit();

					// To set back the collateral Allocation which might not be
					// needed later on
					// when the collateral allocation is properly persisted
					for (int jj = 0; jj < oldLimits.length; jj++) {
						if (newLimits[k].getLimitRef().equals(oldLimits[jj].getLimitRef())) {
							newLimits[k].setCollateralAllocations(oldLimits[jj].getCollateralAllocations());
						}
					}
				}
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Helper method to system close all related trx
	 */
	private void systemCloseRelatedTrx(ICMSTrxValue[] trxList) throws LimitException {
		try {
			for (int i = 0; i < trxList.length; i++) {
				ICMSTrxValue value = trxList[i];

				// check type of transaction and make system close call
				// accordingly
				if (ICMSConstant.INSTANCE_LIMIT.equals(value.getTransactionType())) {
					systemCloseLimit(value);
				}
				else if (ICMSConstant.INSTANCE_LIMIT_PROFILE.equals(value.getTransactionType())) {
					systemCloseLimitProfile(value);
				}
				else if (ICMSConstant.INSTANCE_COBORROWER_LIMIT.equals(value.getTransactionType())) {
					systemCloseCoBorrowerLimit(value);
				}
				else {
					// for the rest of the checklist transactions, system close
					// too
				}
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Helper method to nullify BFL extended issuance date for issue draft bfl,
	 * send draft bfl, and receive draft bfl ack.
	 * 
	 * @param actual actual limit profile
	 * @param staging staging limit profile
	 * @param tat new tat entry
	 */
	private void voidExtendedBFLIssuanceDate(ILimitProfile actual, ILimitProfile staging, ITATEntry tat) {
		String code = tat.getTATServiceCode();
		if (code.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL) || code.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)
				|| code.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)) {
			if (actual != null) {
				actual.setExtendedBFLIssuanceDate(null);
			}
			if (staging != null) {
				staging.setExtendedBFLIssuanceDate(null);
			}
		}
	}

	/**
	 * Helper Method to prepare the LimitProfile object for TAT
	 */
	private ILimitProfileTrxValue prepareTATTrxValue(ITATEntry tat, ILimitProfileTrxValue trxValue)
			throws LimitException {
		try {
			ILimitProfile actual = trxValue.getLimitProfile();
			ILimitProfile staging = trxValue.getStagingLimitProfile();

			if ((null != staging) && (null != actual)) { // need to do on
				// staging first
				// otherwise actual
				// will be modified
				// before staging
				// can be updated

				ITATEntry[] actualEntries = actual.getTATEntries();
				ITATEntry[] stageEntries = (ITATEntry[]) CommonUtil.deepClone(actualEntries);

				ITATEntry stageTAT = (ITATEntry) CommonUtil.deepClone(tat);

				staging.setTATEntries(stageEntries);
				staging = setTATEntry(stageTAT, staging);
			}

			if (null != actual) {
				actual = setTATEntry(tat, actual);
			}
			voidExtendedBFLIssuanceDate(actual, staging, tat);
			trxValue.setLimitProfile(actual);
			trxValue.setStagingLimitProfile(staging);
			return trxValue;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception while deep cloning!", e);
		}
	}

	/**
	 * Helper method to set TAT into limit profile
	 */
	private ILimitProfile setTATEntry(ITATEntry tat, ILimitProfile profile) throws LimitException {
		ITATEntry[] entries = profile.getTATEntries();

		// before returning, validate that the addition is allowed, and set the
		// reference date if it's not already set.
		ITATEntry[] newEntries = TATValidator.processTAT(tat, entries);
		profile.setTATEntries(newEntries);

		// no exception so return
		return profile;
	}

	/**
	 * Helper method to create TAT Objects
	 */
	private ITATEntry createTATObject(String code) {
		OBTATEntry tat = new OBTATEntry();
		tat.setTATServiceCode(code);
		tat.setTATStamp(DateUtil.getDate());
		return tat;
	}

	private ITATEntry createTATObject(String code, ITATEntry tatEntry) {
		if (tatEntry == null) {
			tatEntry = new OBTATEntry();
		}
		tatEntry.setTATServiceCode(code);
		tatEntry.setTATStamp(DateUtil.getDate());
		return tatEntry;
	}

	// ************* Helper methods for Renewal
	/**
	 * Method to delete limits
	 */
	private ILimitProfile deleteMarkedLimits(ILimitProfile profile) throws LimitException {
		boolean change = false;

		ILimit[] limitList = profile.getLimits();
		if ((null == limitList) || (limitList.length == 0)) {
			return profile; // nothing to do
		}

		for (int i = 0; i < limitList.length; i++) {
			ILimit limit = limitList[i];
			long limitID = limit.getLimitID();
			DefaultLogger.debug(this, "Attempting to Delete Marked Limit: " + limitID);

			String hostStatus = limit.getHostStatus();
			DefaultLogger.debug(this, "Host Status: " + hostStatus);
			if ((hostStatus != null) && hostStatus.equals(ICMSConstant.HOST_STATUS_DELETE)) {
				// fetch the limit TRX
				ILimitTrxValue trx = getTrxLimit(limit.getLimitID());
				String trxStatus = trx.getStatus();
				DefaultLogger.debug(this, "Trx Status: " + trxStatus);

				if (trxStatus.equals(ICMSConstant.STATE_DELETED)) {
					// ignore
					continue;
				}
				else {
					change = true;
					// delete
					systemDeleteLimit(trx);
					ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
					// cascade delete coborrower too
					deleteCoBorrowerLimits(coLimitList, profile);
				}
			}
		}
		if (true == change) {
			// fetch profile again to refresh the data
			profile = getLimitProfile(profile.getLimitProfileID());
		}
		return profile;
	}

	/**
	 * Method to delete coborrowers
	 */
	private ILimitProfile deleteMarkedCoBorrowerLimits(ILimitProfile profile) throws LimitException {
		boolean change = false;
		if (null == profile) {
			return profile;
		}
		ILimit[] limitList = profile.getLimits();
		if (null == limitList) {
			return profile;
		}
		for (int i = 0; i < limitList.length; i++) {
			ILimit limit = limitList[i];
			ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
			change = deleteCoBorrowerLimits(coLimitList, profile);
		}

		if (true == change) {
			// fetch profile again to refresh the data
			profile = getLimitProfile(profile.getLimitProfileID());
		}
		return profile;
	}

	/**
	 * Method to delete coborrowers
	 */
	private boolean deleteCoBorrowerLimits(ICoBorrowerLimit[] coLimitList, ILimitProfile profile) throws LimitException {
		boolean change = false;

		if ((null == coLimitList) || (coLimitList.length == 0)) {
			return change;
		}
		for (int i = 0; i < coLimitList.length; i++) {
			ICoBorrowerLimit coLimit = coLimitList[i];
			long coLimitID = coLimit.getLimitID();
			DefaultLogger.debug(this, "Attempting to Delete CoBorrowerLimit: " + coLimitID);
			String hostStatus = coLimit.getHostStatus();
			if ((hostStatus != null) && hostStatus.equals(ICMSConstant.HOST_STATUS_DELETE)) {
				// fetech coborrower limit trx
				ICoBorrowerLimitTrxValue trx = getTrxCoBorrowerLimit(coLimitID);
				String trxStatus = trx.getStatus();
				DefaultLogger.debug(this, "Trx Status: " + trxStatus);

				if (trxStatus.equals(ICMSConstant.STATE_DELETED)) {
					// ignore
					continue;
				}
				else {
					change = true;
					// delete
					systemDeleteCoBorrowerLimit(trx, profile);
				}
			}
		}
		return change;
	}

	/**
	 * Method to get marked collaterals.
	 */
	private ICollateral[] getMarkedCollaterals(ILimitProfile profile) throws LimitException {
		if (null == profile) {
			return null;
		}
		ILimit[] limitList = profile.getLimits();
		if ((null == limitList) || (limitList.length == 0)) {
			return null;
		}
		try {
			ArrayList aList = new ArrayList();
			ArrayList cbLmtColList = new ArrayList();
			// ICollateralProxy proxy = CollateralProxyFactory.getProxy();

			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				DefaultLogger.debug(this, "Attempting to get Marked Collaterals for limitID: " + limit.getLimitID());
				String status = limit.getHostStatus();
				DefaultLogger.debug(this, "Host Status: " + status);

				if (status.equals(ICMSConstant.HOST_STATUS_DELETE)) {
					ICollateralAllocation[] allocList = limit.getCollateralAllocations();
					if (null == allocList) {
						continue;
					}
					else {
						for (int j = 0; j < allocList.length; j++) {
							ICollateralAllocation alloc = allocList[j];
							ICollateral col = alloc.getCollateral();
							aList.add(col);
						}
					}
				}
			}

			ICoBorrowerLimit[] allCoboLimitList = CoBorrowerLimitUtils.getAllCoBorowerLimitsByLimitProfile(profile);
			for (int i = 0; i < allCoboLimitList.length; i++) {
				ICoBorrowerLimit aCoboLimit = allCoboLimitList[i];
				if (ICMSConstant.HOST_STATUS_DELETE.equals(aCoboLimit.getHostStatus())) {
					ICollateralAllocation[] allocList = aCoboLimit.getCollateralAllocations();
					if (null == allocList) {
						continue;
					}
					else {
						for (int j = 0; j < allocList.length; j++) {
							ICollateralAllocation alloc = allocList[j];
							ICollateral col = alloc.getCollateral();
							cbLmtColList.add(col);
						}
					}
				}
			}

			// next identify if for the selected collaterals, all limits linked
			// to it has been deleted.
			ArrayList colList = new ArrayList(aList.size() + cbLmtColList.size());
			Iterator it = aList.iterator();
			while (it.hasNext()) {
				ICollateral col = (ICollateral) it.next();
				long collateralID = col.getCollateralID();
				boolean notDeleted = false;
				boolean found = false;
				for (int i = 0; i < limitList.length; i++) {
					ILimit limit = limitList[i];
					ICollateralAllocation[] allocList = limit.getCollateralAllocations();
					if (null == allocList) {
						continue;
					}
					else {
						found = true;
						for (int j = 0; j < allocList.length; j++) {
							ICollateralAllocation alloc = allocList[j];
							if (alloc.getCollateral().getCollateralID() == collateralID) {
								DefaultLogger.debug(this, "Attempting to mark collateral: " + collateralID);
								String status = limit.getHostStatus();
								DefaultLogger.debug(this, "Host Status: " + status);
								if (!(status.equals(ICMSConstant.HOST_STATUS_DELETE))) { // at
									// least
									// 1
									// limit
									// not
									// deleted
									notDeleted = true;
									break;
								}
							}
						}
					}
				}
				if (false == found) {
					throw new LimitException("Unable to find CollateralID: " + collateralID
							+ " in Marked Collateral List!");
				}
				else {
					if (false == notDeleted) { // ie. to be deleted
						colList.add(col);
					}
				}
			}

			Iterator cbLmtColItr = cbLmtColList.iterator();
			while (cbLmtColItr.hasNext()) {
				ICollateral col = (ICollateral) cbLmtColItr.next();
				long collateralID = col.getCollateralID();
				boolean notDeleted = false;
				boolean found = false;
				for (int i = 0; i < allCoboLimitList.length; i++) {
					ICoBorrowerLimit aCoboLimit = allCoboLimitList[i];
					ICollateralAllocation[] allocList = aCoboLimit.getCollateralAllocations();
					if (null == allocList) {
						continue;
					}
					else {
						found = true;
						for (int j = 0; j < allocList.length; j++) {
							ICollateralAllocation alloc = allocList[j];
							if (alloc.getCollateral().getCollateralID() == collateralID) {
								DefaultLogger.debug(this, "Attempting to mark collateral of cobo limit: "
										+ collateralID);
								String status = aCoboLimit.getHostStatus();
								DefaultLogger.debug(this, "cobo limit Host Status: " + status);
								if (!(ICMSConstant.HOST_STATUS_DELETE.equals(status))) { // at
									// least
									// 1
									// limit
									// not
									// deleted
									notDeleted = true;
									break;
								}
							}
						}
					}
				}
				if (false == found) {
					throw new LimitException("Unable to find CollateralID: " + collateralID
							+ " in Marked Collateral List!");
				}
				else {
					if (false == notDeleted) { // ie. to be deleted
						colList.add(col);
					}
				}
			}

			return (ICollateral[]) colList.toArray(new ICollateral[0]);
		}
		catch (Exception e) {
			throw new LimitException("Excpetion in getMarkedCollaterals!", e);
		}
	}

	/**
	 * Method to delete collateral checklist if any
	 */
	private void deleteCollateralChecklists(ICollateral[] colList, ILimitProfile profile) throws LimitException {

		if ((null == colList) || (colList.length == 0)) {
			return;
		}
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			for (int i = 0; i < colList.length; i++) {
				// delete related checklist. if checklist is already deleted,
				// ignore. This will cascade delete pledgor too
				proxy.systemDeleteCollateralCheckListTrx(profile.getLimitProfileID(), colList[i].getCollateralID());
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception in deleteCollateralChecklists!", e);
		}
	}

	/**
	 * Method to system close related trx
	 */
	private void systemCloseTrx(ILimitProfile profile) throws LimitException {
		try {
			// close CCC if exist
			ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			cccProxy.systemCloseCCC(profile);

			// close SCC and PSCC if exist
			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			sccProxy.systemCloseSCC(profile);
			sccProxy.systemClosePartialSCC(profile);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception in systemCloseTrx!", e);
		}
	}

	/**
	 * Method to reset profile values
	 */
	private ICMSTrxResult resetProfile(ILimitProfile profile) throws LimitException {
		try {
			ILimitProfileTrxValue profileTrx = getTrxLimitProfile(profile.getLimitProfileID());
			profile = profileTrx.getLimitProfile();

			profile.setBFLRequiredInd(true);
			profile.setBCACompleteInd(false);
			profile.setCCCCompleteInd(false);
			profile.setSCCCompleteInd(false);
			profile.setTATEntries(null);
			profile.setBCACreateDate(DateUtil.getDate());
			profile.setTATCreateDate(null);
			profile.setRenewalInd(true);
			profile.setExtendedBFLIssuanceDate(null);

			profileTrx.setLimitProfile(profile);
			profileTrx.setStagingLimitProfile(profile); // to be created as new

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE);

			ITrxController controller = (new LimitProfileTrxControllerFactory()).getController(profileTrx, param);
			ITrxResult result = controller.operate(profileTrx, param);
			return (ICMSTrxResult) result;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception in resetProfile!", e);
		}
	}

	/**
	 * renew checklists
	 */
	private void renewCheckLists(ILimitProfile profile) throws LimitException {
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			proxy.systemRenewCCCheckListTrx(profile);
			proxy.systemRenewCollateralCheckListTrx(profile);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception in renewCheckLists!", e);
		}
	}

	public ILimitProfile getLimitProfileLimitsOnly(long limitProfileID) throws LimitException {
		try {
			LimitDAO dao = new LimitDAO();
			return dao.getLimitProfileLimitOnly(limitProfileID);
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
			LimitDAO dao = new LimitDAO();
			return dao.getLimitProfileIds(collateralId);
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
			LimitDAO dao = new LimitDAO();
			return dao.getLimitProfileIdsByApprLmts(collateralId);
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
			LimitDAO dao = new LimitDAO();
			return dao.getLEIdAndBCARef(limitProfileId);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.proxy.ILimitProxy#makerCreateLimitProfile
	 */
	public ICMSTrxResult makerCreateLimitProfile(ITrxContext ctx, ILimitProfileTrxValue trxVal, ILimitProfile value)
			throws LimitException {
		if (value == null) {
			throw new LimitException("ILimitProfile is null");
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_LIMIT_PROFILE);
		if (trxVal == null) {
			trxVal = new OBLimitProfileTrxValue();
		}
		trxVal.setStagingLimitProfile(value);
		if (isDuplicateAANumber(trxVal)) {
			LimitException exp = new LimitException("Cannot create LimitProfile");
			exp.setErrorCode(LimitException.ERR_DUPLICATE_AA_NUM);
			throw exp;
		}
		else {
			return (ICMSTrxResult) operate(constructTrxValue(ctx, trxVal), param);
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.proxy.ILimitProxy#makerCancelCreateLimitProfile
	 */
	public ICMSTrxResult makerCancelCreateLimitProfile(ITrxContext ctx, ILimitProfileTrxValue trxVal)
			throws LimitException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_LIMIT_PROFILE);

		return (ICMSTrxResult) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.limit.proxy.ILimitProxy#makerDeleteLimitProfile
	 */
	public ICMSTrxResult makerDeleteLimitProfile(ITrxContext ctx, ILimitProfileTrxValue trxVal) throws LimitException {
		if (checkCanDeleteLimitProfile(ctx, trxVal)) {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_DELETE_LIMIT_PROFILE);

			return (ICMSTrxResult) operate(constructTrxValue(ctx, trxVal), param);
		}
		else {
			LimitException exp = new LimitException("Cannot delete LimitProfile");
			exp.setErrorCode(LimitException.ERR_CANNOT_DELETE_LMT_PROFILE);
			throw exp;
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.proxy.ILimitProxy#checkerApproveDeleteLimitProfile
	 */
	public ICMSTrxResult checkerApproveDeleteLimitProfile(ITrxContext ctx, ILimitProfileTrxValue trxVal)
			throws LimitException {
		if (checkCanDeleteLimitProfile(ctx, trxVal)) {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE);
			return (ICMSTrxResult) operate(constructTrxValue(ctx, trxVal), param);
		}
		else {
			LimitException exp = new LimitException("Cannot delete LimitProfile");
			exp.setErrorCode(LimitException.ERR_CANNOT_DELETE_LMT_PROFILE);
			throw exp;
		}
	}

	/**
	 * Maker reassign limit profile segment
	 * 
	 * @param limitProfileIDList limit profie ID list
	 * @param segment Limit Profile new segment value
	 * @param context transaction context
	 * @throws LimitException on errors encountered
	 */
	public void makerReassignLimitProfileSegment(String[] limitProfileIDList, String segment, ITrxContext context)
			throws LimitException {
		if ((limitProfileIDList == null) || (limitProfileIDList.length == 0) || (segment == null)
				|| (segment.trim().length() == 0)) {
			return;
		}

		for (int i = 0; i < limitProfileIDList.length; i++) {
			ILimitProfileTrxValue trxValue = getTrxLimitProfile(Long.parseLong(limitProfileIDList[i]));
			trxValue.getLimitProfile().setSegment(segment);
			trxValue.getStagingLimitProfile().setSegment(segment);
			makerDirectUpdateLimitProfile(context, trxValue);
		}
	}

	private boolean isDuplicateAANumber(ILimitProfileTrxValue trxValue) throws LimitException {
		ILimitDAO dao = LimitDAOFactory.getDAO();
		long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
		if (trxValue.getLimitProfile() != null) {
			limitProfileID = trxValue.getLimitProfile().getLimitProfileID();
		}
		return dao.checkDuplicateAANumber(trxValue.getStagingLimitProfile().getBCAReference(), limitProfileID);

	}

	private boolean checkIsWorkInProgress(SearchResult sr) {
		ArrayList list = (ArrayList) sr.getResultList();
		if ((list != null) && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {

				OBCMSTrxSearchResult srItem = (OBCMSTrxSearchResult) list.get(i);

				if (!(srItem.getStatus().equals(ICMSConstant.STATE_ND) || srItem.getStatus().equals(
						ICMSConstant.STATE_ACTIVE))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkCanDeleteLimitProfile(ITrxContext ctx, ILimitProfileTrxValue value) throws LimitException {
		DefaultLogger.debug(this, "checkCanDeleteLimitProfile, ILimitProfileTrxValue: " + value);

		ILimitProfile limitProfile = value.getLimitProfile();
		String aaType = limitProfile.getAAType();

		if (aaType.equals(ICMSConstant.AA_TYPE_TRADE)) {
			try {

				CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
				criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
				criteria.setTeamID(value.getTeamID());
				DefaultLogger.debug(this, "value.getTeamID() : " + value.getTeamID());
				DefaultLogger.debug(this, "value.getTeamTypeID() : " + value.getTeamTypeID());

				criteria.setOnlyMembershipRecords(false);
				criteria.setTransactionTypes(new String[] { ICMSConstant.INSTANCE_ISDA_DEAL_VAL,
						ICMSConstant.INSTANCE_GMRA_DEAL_VAL, ICMSConstant.INSTANCE_GMRA_DEAL,
						ICMSConstant.INSTANCE_CASH_MARGIN });
				criteria.setLimitProfileID(new Long(limitProfile.getLimitProfileID()));

				SearchResult sr = getTrxManager().searchTransactions(criteria);
				if ((sr != null) && !sr.getResultList().isEmpty()) {
					return !checkIsWorkInProgress(sr);
				}
			}
			catch (SearchDAOException e) {
				DefaultLogger.error(this, "Caught Exception!", e);
				throw new LimitException("SearchDAOException caught! " + e.toString(), e);
			}
			catch (RemoteException e) {
				DefaultLogger.error(this, "Caught Exception!", e);
				throw new LimitException("RemoteException caught! " + e.toString(), e);
			}
		}
		else {
			if ((limitProfile.getLimits() != null) && (limitProfile.getLimits().length > 0)) {
				return false;

			}
		}
		return true;
	}

	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		if (trxValue instanceof ILimitProfileTrxValue) {
			ILimitProfileTrxValue lmtPrfTrx = (ILimitProfileTrxValue) trxValue;
			lmtPrfTrx.setTransactionType(ICMSConstant.INSTANCE_LIMIT_PROFILE);
			if (ctx != null) {
				if (lmtPrfTrx.getStagingLimitProfile() != null) {
					IBookingLocation loc = lmtPrfTrx.getStagingLimitProfile().getOriginatingLocation();
					if(loc!= null){
					ctx.setTrxCountryOrigin(loc.getCountryCode());
					ctx.setTrxOrganisationOrigin(loc.getOrganisationCode());
					}

				}
			}

			lmtPrfTrx.setTrxContext(ctx);

		}
		else {
			((ICMSTrxValue) trxValue).setTrxContext(ctx);
		}
		return trxValue;
	}

	/**
	 * Helper method to operate transactions.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws LimitException on errors encountered
	 */
	private ITrxResult operate(ITrxValue trxVal, ITrxParameter param) throws LimitException {
		if (trxVal == null) {
			throw new LimitException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof ILimitProfileTrxValue) {
				controller = (new LimitProfileTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new LimitException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			// ITrxValue obj = result.getTrxValue();
			return result;
		}
		catch (LimitException e) {
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			rollback();
			throw new LimitException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new LimitException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws LimitException on errors rolling back
	 */
	protected void rollback() throws LimitException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new LimitException(e.toString());
		}
	}

	// ************* EJB Methods *****************

	/* EJB Methods */
	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {

	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {

	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}
	
	
	public boolean getTrxCam(String customerId) throws LimitException {
		try {
			LimitDAO dao = new LimitDAO();
			return dao.getTrxCam(customerId);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}
	
	public String getTotalAmountByFacType(String camId,String facType,String climsFacilityID) throws LimitException {
		try {
			LimitDAO dao = new LimitDAO();
			return dao.getTotalAmountByFacType(camId,facType,climsFacilityID);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}
	
	
	
	public ICMSTrxResult submitCreateLimitProfile(OBTrxContext trxContext, ILimitProfileTrxValue limitProfileTrxVal,
			OBLimitProfile newLimitProfile) throws LimitException {
		OBCMSTrxValue transaction = new OBCMSTrxValue();
		ICMSTrxResult trxResult=null;
		try {
			
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			trxContext = mcUtil.setContextForMaker();
			
			if(limitProfileTrxVal == null){
			trxResult = makerCreateLimitProfile(trxContext, limitProfileTrxVal, newLimitProfile);
			if (trxResult != null) {
				transaction = (OBCMSTrxValue) trxResult.getTrxValue();
			}
			limitProfileTrxVal = getTrxLimitProfile(transaction.getTransactionID());
			}
			else{
				trxResult = makerUpdateLimitProfile(trxContext, limitProfileTrxVal, newLimitProfile);
				if (trxResult != null) {
					transaction = (OBCMSTrxValue) trxResult.getTrxValue();
				}
				limitProfileTrxVal = getTrxLimitProfile(transaction.getTransactionID());
					
			}
			trxContext = mcUtil.setContextForChecker();
			trxResult = checkerApproveUpdateLimitProfile(trxContext, limitProfileTrxVal);
		}catch (LimitException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new LimitException("Exception caught! " + e.toString(), e);
		}
		return trxResult;
	}
	
	
}