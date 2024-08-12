/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import java.util.ArrayList;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADeal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookDAOFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.trx.CashMarginTrxControllerFactory;
import com.integrosys.cms.app.tradingbook.trx.GMRADealTrxControllerFactory;
import com.integrosys.cms.app.tradingbook.trx.GMRADealValTrxControllerFactory;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.ISDACSADealValTrxControllerFactory;
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.TradingBookHelper;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This session bean provides the implementation of the
 * AbstractTradingBookProxy, wrapped in an EJB mechanism.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBTradingBookProxyBean implements ITradingBookProxy, SessionBean {
	/** SessionContext object */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBTradingBookProxyBean() {
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws TradingBookException on errors rolling back
	 */
	protected void rollback() throws TradingBookException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new TradingBookException(e.toString());
		}
	}

	// ********** EJB Methods ****************
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
	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		if (trxValue instanceof IISDACSADealValTrxValue) {
			IISDACSADealValTrxValue colTrx = (IISDACSADealValTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_ISDA_DEAL_VAL);
			colTrx.setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
			colTrx.setTrxContext(ctx);

		}
		else if (trxValue instanceof IGMRADealValTrxValue) {
			IGMRADealValTrxValue colTrx = (IGMRADealValTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_GMRA_DEAL_VAL);
			colTrx.setTrxContext(ctx);
		}
		else if (trxValue instanceof ICashMarginTrxValue) {
			ICashMarginTrxValue colTrx = (ICashMarginTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_CASH_MARGIN);
			colTrx.setTrxContext(ctx);
		}
		else if (trxValue instanceof IGMRADealTrxValue) {
			IGMRADealTrxValue colTrx = (IGMRADealTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_GMRA_DEAL);
			colTrx.setTrxContext(ctx);
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
	 * @throws TradingBookException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws TradingBookException {
		if (trxVal == null) {
			throw new TradingBookException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof IGMRADealTrxValue) {
				controller = (new GMRADealTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof IISDACSADealValTrxValue) {
				controller = (new ISDACSADealValTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof ICashMarginTrxValue) {
				controller = (new CashMarginTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof IGMRADealValTrxValue) {
				controller = (new GMRADealValTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new TradingBookException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (TradingBookException e) {
			e.printStackTrace();
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new TradingBookException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new TradingBookException("Exception caught! " + e.toString(), e);
		}
	}

	// ******************** Proxy methods for ISDA Deal ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealSummaryByAgreement
	 */
	public IISDACSADealSummary getISDACSADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException {
		try {
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			IISDACSADeal[] csaArray = mgr.getISDACSADealByAgreementID(agreementID);

			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			ICPAgreementDetail cpAgree = dao.getCounterPartyAgreementSummary(limitProfileID, agreementID);

			ArrayList valList = new ArrayList();
			for (int i = 0; i < csaArray.length; i++) {

				IISDACSADeal csa = (IISDACSADeal) csaArray[i];

				OBISDACSADealVal val = new OBISDACSADealVal();
				val.setISDACSADealDetail(csa);
				valList.add(val);

			}
			OBISDACSADealSummary summ = new OBISDACSADealSummary(cpAgree);
			summ.setISDACSADealSummary(TradingBookHelper.toCSAValArray(valList));

			return summ;
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			throw new TradingBookException("SearchDAOException caught at getISDACSADealSummaryByAgreement "
					+ e.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TradingBookException("Exception caught at getISDACSADealSummaryByAgreement: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADeal
	 */
	public IISDACSADealDetail getISDACSADeal(long cmsDealID) throws TradingBookException {
		try {

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			IISDACSADeal csa = mgr.getISDACSADeal(cmsDealID);

			if (csa != null) {
				ICPAgreementDetail cpAgree = getCounterPartyAgreementDetail(csa.getAgreementID());

				IISDACSADealDetail detail = new OBISDACSADealDetail(cpAgree);

				detail.setISDACSADealDetail(csa);
				return detail;

			}
		}
		catch (TradingBookException e) {
			throw new TradingBookException("TradingBookException caught at getISDACSADeal " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getISDACSADeal: " + e.toString());
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValue
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_ISDA_DEAL_VAL);
		OBISDACSADealValTrxValue trxValue = new OBISDACSADealValTrxValue();
		trxValue.setReferenceID(String.valueOf(agreementID));
		trxValue.setAgreementID(agreementID);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValueByTrxID
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_ISDA_DEAL_VAL_BY_TRXID);
		OBISDACSADealValTrxValue trxValue = new OBISDACSADealValTrxValue();
		trxValue.setTransactionID(trxID);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_ISDA_DEAL_VAL);
		trxVal.setStagingISDACSADealValuation(value);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerSaveISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_ISDA_DEAL_VAL);
		trxVal.setStagingISDACSADealValuation(value);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerCancelUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_ISDA_DEAL_VAL);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerApproveUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_ISDA_DEAL_VAL);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerRejectUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_ISDA_DEAL_VAL);
		return (IISDACSADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	// ******************** Proxy methods for GMRA Deal ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealSummaryByAgreement
	 */
	public IGMRADealSummary getGMRADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException {
		try {
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			IGMRADeal[] gmraArray = mgr.getGMRADealByAgreementID(agreementID);

			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			ICPAgreementDetail cpAgree = dao.getCounterPartyAgreementSummary(limitProfileID, agreementID);

			ArrayList valList = new ArrayList();
			for (int i = 0; i < gmraArray.length; i++) {

				IGMRADeal gmra = (IGMRADeal) gmraArray[i];

				OBGMRADealVal val = new OBGMRADealVal();
				val.setGMRADealDetail(gmra);
				valList.add(val);

			}
			OBGMRADealSummary summ = new OBGMRADealSummary(cpAgree);
			summ.setGMRADealSummary(TradingBookHelper.toGMRAValArray(valList));

			return summ;
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			throw new TradingBookException("SearchDAOException caught at getGMRADealSummaryByAgreement " + e.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TradingBookException("Exception caught at getGMRADealSummaryByAgreement: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADeal
	 */
	public IGMRADealDetail getGMRADeal(long cmsDealID) throws TradingBookException {
		try {
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			IGMRADeal gmra = mgr.getGMRADeal(cmsDealID);

			if (gmra != null) {
				ICPAgreementDetail cpAgree = getCounterPartyAgreementDetail(gmra.getAgreementID());

				IGMRADealDetail detail = new OBGMRADealDetail(cpAgree);

				detail.setGMRADealDetail(gmra);
				return detail;

			}
		}
		catch (TradingBookException e) {
			throw new TradingBookException("TradingBookException caught at getGMRADeal " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getGMRADeal: " + e.toString());
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCounterPartyAgreementDetail
	 */
	public ICPAgreementDetail getCounterPartyAgreementDetail(long agreementID) throws TradingBookException {
		try {
			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			return dao.getCounterPartyAgreementSummary(agreementID);
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			throw new TradingBookException("SearchDAOException caught at getCounterPartyAgreementDetail "
					+ e.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TradingBookException("Exception caught at getCounterPartyAgreementDetail: " + e.toString());
		}
	}

	// ******************** Proxy methods for Add/Edit/Remove GMRA Deal
	// ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValue
	 */
	public IGMRADealTrxValue getGMRADealTrxValue(ITrxContext ctx, long cmsDealID) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GMRA_DEAL);
		OBGMRADealTrxValue trxValue = new OBGMRADealTrxValue();
		trxValue.setReferenceID(String.valueOf(cmsDealID));
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByAgreementID
	 */
	public IGMRADealTrxValue[] getGMRADealTrxValueByAgreementID(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
		IGMRADeal[] gmraArray = mgr.getGMRADealByAgreementID(agreementID);

		ArrayList list = new ArrayList();
		for (int i = 0; i < gmraArray.length; i++) {

			IGMRADeal gmra = (IGMRADeal) gmraArray[i];

			IGMRADealTrxValue trxVal = getGMRADealTrxValue(ctx, gmra.getCMSDealID());
			list.add(trxVal);
		}
		return (IGMRADealTrxValue[]) list.toArray(new OBGMRADealTrxValue[0]);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByTrxID
	 */
	public IGMRADealTrxValue getGMRADealTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GMRA_DEAL_BY_TRXID);
		OBGMRADealTrxValue trxValue = new OBGMRADealTrxValue();
		trxValue.setTransactionID(trxID);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, long agreementID,
			IGMRADeal value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("GMRA Deal is null");
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_GMRA_DEAL);
		if (trxVal == null) {
			trxVal = new OBGMRADealTrxValue();
			trxVal.setAgreementID(agreementID);
		}
		trxVal.setStagingGMRADeal(value);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, IGMRADeal value)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL);
		trxVal.setStagingGMRADeal(value);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_GMRA_DEAL);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_GMRA_DEAL);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerDeleteGMRADeal
	 */
	public IGMRADealTrxValue makerDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_GMRA_DEAL);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, DeleteGMRADealException {
		if (checkCanDeleteGMRADeal(ctx, trxVal)) {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL);
			return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
		}
		else {
			DeleteGMRADealException exp = new DeleteGMRADealException("Cannot delete GMRA Deal");
			throw exp;
		}
	}

	private boolean checkCanDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue value) throws TradingBookException {
		IGMRADeal deal = value.getGMRADeal();
		long agreementID = deal.getAgreementID();
		IGMRADealValTrxValue gmraValTrxValue = getGMRADealValuationTrxValue(ctx, agreementID);

		if (!(gmraValTrxValue.getStatus().equals(ICMSConstant.STATE_ND) || gmraValTrxValue.getStatus().equals(
				ICMSConstant.STATE_ACTIVE))) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerRejectUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GMRA_DEAL);
		return (IGMRADealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	// ******************** Proxy methods for GMRA Deal Valuation
	// ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValueByTrxID
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GMRA_DEAL_VAL);
		OBGMRADealValTrxValue trxValue = new OBGMRADealValTrxValue();
		trxValue.setReferenceID(String.valueOf(agreementID));
		trxValue.setAgreementID(agreementID);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValueByTrxID
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GMRA_DEAL_VAL_BY_TRXID);
		OBGMRADealValTrxValue trxValue = new OBGMRADealValTrxValue();
		trxValue.setTransactionID(trxID);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL_VAL);
		trxVal.setStagingGMRADealValuation(value);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveGMRADealValuation
	 */
	public IGMRADealValTrxValue makerSaveGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_GMRA_DEAL_VAL);
		trxVal.setStagingGMRADealValuation(value);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerCancelUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_GMRA_DEAL_VAL);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerApproveUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL_VAL);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerRejectUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GMRA_DEAL_VAL);
		return (IGMRADealValTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	// ******************** Proxy methods for Cash Margin ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValue
	 */
	public ICashMarginTrxValue getCashMarginTrxValue(ITrxContext ctx, long agreementID) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CASH_MARGIN);
		OBCashMarginTrxValue trxValue = new OBCashMarginTrxValue();
		trxValue.setReferenceID(String.valueOf(agreementID));
		trxValue.setAgreementID(agreementID);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValueByTrxID
	 */
	public ICashMarginTrxValue getCashMarginTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CASH_MARGIN_BY_TRXID);
		OBCashMarginTrxValue trxValue = new OBCashMarginTrxValue();
		trxValue.setTransactionID(trxID);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateCashMargin
	 */
	public ICashMarginTrxValue makerUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CASH_MARGIN);
		trxVal.setStagingCashMargin(value);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveCashMargin
	 */
	public ICashMarginTrxValue makerSaveCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CASH_MARGIN);
		trxVal.setStagingCashMargin(value);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateCashMargin
	 */
	public ICashMarginTrxValue makerCancelUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_CASH_MARGIN);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerApproveUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CASH_MARGIN);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerRejectUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CASH_MARGIN);
		return (ICashMarginTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

}