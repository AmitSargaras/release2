/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCreditRiskParamProxyManagerBean
 *
 * Created on 11:13:28 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamSearchCriteria;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamBusDelegate;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sharecounter.CreditRiskParamShareCounterTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.unittrust.CreditRiskParamUnitTrustTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 11:13:28 AM
 */
public class SBCreditRiskParamProxyManagerBean implements SessionBean {
	private SessionContext context;

	public SBCreditRiskParamProxyManagerBean() {

	}

	public void ejbActivate() throws EJBException, RemoteException {

	}

	public void ejbPassivate() throws EJBException, RemoteException {

	}

	public void ejbRemove() throws EJBException, RemoteException {

	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
		this.context = sessionContext;
	}

	public void ejbCreate() throws CreateException {

	}

	public void ejbPostCreate() {

	}

	// methods implemented for ICreditRiskParamProxy
	// generic search function
	public SearchResult getSearchResultForCriteria(SearchCriteria criteria, CreditRiskParamType type)
			throws CreditRiskParamGroupException {
		try {
			CreditRiskParamSearchCriteria searchCriteria = (CreditRiskParamSearchCriteria) criteria;
			CreditRiskParamBusDelegate delegate = new CreditRiskParamBusDelegate();

			String groupType = "";

			if ((CreditRiskParamType.SHARE_COUNTER == type) || CreditRiskParamType.SHARE_COUNTER.equals(type)) {
				groupType = "STOCK";
			}
			else if ((CreditRiskParamType.UNIT_TRUST == type) || CreditRiskParamType.UNIT_TRUST.equals(type)) {
				groupType = "UNIT_TRUST";
			}

			ArrayList list = (ArrayList) delegate.getCreditRiskParamGroup(groupType, searchCriteria.getGroupSubType(),
					searchCriteria.getGroupStockType());
			SearchResult result = new SearchResult(criteria.getStartIndex(), searchCriteria.getCurrentIndex(), criteria
					.getNItems(), list);

			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreditRiskParamGroupException(ex);
		}
	}

	// generic methods available for maker
	public ICMSTrxValue makerReadCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		return read(ctx, trxValue, trxOb, type);
	}

	public ICMSTrxValue makerReadRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		return readByTrxId(ctx, trxValue, trxOb, type);
	}

	public ICMSTrxValue makerUpdateCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE);
	}

	public ICMSTrxValue makerUpdateRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE_REJECTED);
	}

	public ICMSTrxValue makerCloseRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_CLOSE);
	}

	// generic methods available for checker
	public ICMSTrxValue checkerViewCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		return readByTrxId(ctx, trxValue, trxOb, type);
	}

	public ICMSTrxValue checkerApproveCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_CHECKER_APPROVE);
	}

	public ICMSTrxValue checkerRejectCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_CHECKER_REJECT);
	}

	// private methods to service the ICreditRiskParamProxy methods

	private ICMSTrxValue read(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		DefaultLogger.debug(this, "Now starting reading process");

		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_READ);
	}

	private ICMSTrxValue readByTrxId(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		DefaultLogger.debug(this, "Now starting reading process");

		return operate(ctx, trxValue, trxOb, type, ICMSConstant.ACTION_CREDIT_RISK_PARAM_READBY_TRXID);
	}

	private ICMSTrxValue operate(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type, String action) throws CreditRiskParamGroupException {
		DefaultLogger.debug(this, "Now starting process for action : " + action);

		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			ICMSTrxValue iTrx = formulateTrxValue(ctx, trxValue, trxOb, type);

			DefaultLogger.debug(this, "iTrx.getReferenceID ()  : " + iTrx.getReferenceID());

			param.setAction(action);

			return operate(iTrx, param, type);
		}
		catch (CreditRiskParamGroupException crpgex) {
			rollback();

			throw crpgex;
		}
		catch (Exception ex) {
			rollback();

			ex.printStackTrace();

			throw new CreditRiskParamGroupException(ex);
		}
	}

	private ICMSTrxValue operate(ICMSTrxValue trxValue, ITrxParameter trxParam, CreditRiskParamType type)
			throws CreditRiskParamGroupException, TransactionException {
		ITrxControllerFactory factory = getControllerFactory(type);
		ITrxController controller = factory.getController(trxValue, trxParam);
		ITrxResult trxResult = controller.operate(trxValue, trxParam);

		return (ICMSTrxValue) trxResult.getTrxValue();
	}

	private ICMSTrxValue formulateTrxValue(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) {
		OBCreditRiskParamGroupTrxValue groupTrxValue = null;

		if (trxValue != null) {
			groupTrxValue = new OBCreditRiskParamGroupTrxValue(trxValue);

			DefaultLogger.debug(this, "Transaction id of the passed trxValue : " + trxValue.getTransactionID());
		}
		else {
			groupTrxValue = new OBCreditRiskParamGroupTrxValue();
		}

		DefaultLogger.debug(this, "Transaction id of the new trxValue : " + groupTrxValue.getTransactionID());

		groupTrxValue.setStagingCreditRiskParamGroup(trxOb);
		groupTrxValue.setTrxContext(ctx);

		if ((CreditRiskParamType.SHARE_COUNTER == type) || CreditRiskParamType.SHARE_COUNTER.equals(type)) {
			groupTrxValue.setTransactionType(ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER);
		}
		else if ((CreditRiskParamType.UNIT_TRUST == type) || CreditRiskParamType.UNIT_TRUST.equals(type)) {
			groupTrxValue.setTransactionType(ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST);
		}

		return groupTrxValue;
	}

	private ITrxControllerFactory getControllerFactory(CreditRiskParamType type) throws CreditRiskParamGroupException {
		if (type == null) {
			throw new CreditRiskParamGroupException("CreditRiskParamType argument is null !");
		}

		DefaultLogger
				.debug(this, "Retrieving ITrxControllerFactory object for CreditRiskParamType : " + type.getType());

		if ((CreditRiskParamType.SHARE_COUNTER == type) || CreditRiskParamType.SHARE_COUNTER.equals(type)) {
			DefaultLogger.debug(this, "Share counter type detected");

			return new CreditRiskParamShareCounterTrxControllerFactory();
		}

		if ((CreditRiskParamType.UNIT_TRUST == type) || CreditRiskParamType.UNIT_TRUST.equals(type)) {
			DefaultLogger.debug(this, "Unit trust type detected");

			return new CreditRiskParamUnitTrustTrxControllerFactory();
		}

		if ((CreditRiskParamType.CAP_POLICY == type) || CreditRiskParamType.CAP_POLICY.equals(type)) {
			DefaultLogger.debug(this, "Cap policy type detected");

			throw new CreditRiskParamGroupException("Operation for Cap Policy not supported"); // cap
																								// policy
																								// not
																								// implemented
																								// by
																								// this
																								// Session
																								// bean
		}

		throw new CreditRiskParamGroupException("Unknown CreditRiskParamType : " + type.getType()); // impossible
																									// to
																									// get
																									// here
																									// ...
																									// ...
																									// .
																									// .
	}

	private void rollback() {
		if (this.context != null) {
			try {
				this.context.setRollbackOnly();
			}
			catch (Throwable t) {

			}
		}
	}

}
