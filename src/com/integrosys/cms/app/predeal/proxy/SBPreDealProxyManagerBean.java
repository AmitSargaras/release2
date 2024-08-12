/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealProxyManagerBean
 *
 * Created on 5:33:53 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

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
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.IEarMarkGroup;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.predeal.bus.PreDealDao;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManager;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManagerHome;
import com.integrosys.cms.app.predeal.trx.IPreDealTrxValue;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;
import com.integrosys.cms.app.predeal.trx.PreDealTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:33:53 PM
 */
public class SBPreDealProxyManagerBean implements SessionBean {
	private SessionContext context;
    

    public SBPreDealProxyManagerBean() {

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

	public ICMSTrxValue checkerApprove(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException,
			RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_EAR_MARK);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue checkerReject(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException,
			RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_EAR_MARK);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerCreateNewEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		trxValue.setStagingPreDeal(data);
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_EAR_MARK);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerCloseEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		trxValue.setStagingPreDeal(data);
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_EAR_MARK);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerUpdateEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data, String updateType)
			throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		trxValue.setStagingPreDeal(data);
		param.setAction(updateType);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerUpdateRejectedEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		trxValue.setTrxContext(ctx);
		trxValue.setStagingPreDeal(data);
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_EAR_MARK);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByTrxID(String trxId) throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		IPreDealTrxValue trxValue = new OBPreDealTrxValue();

		trxValue.setTransactionID(trxId);
		param.setAction(ICMSConstant.ACTION_READ_PRE_DEAL_BY_TRX_ID);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByEarMarkId(String earMarkId) throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		IPreDealTrxValue trxValue = new OBPreDealTrxValue();

		trxValue.setTransactionID(earMarkId);

		param.setAction(ICMSConstant.ACTION_READ_PRE_DEAL);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByFeedId(String feedId) throws PreDealException, RemoteException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		IPreDealTrxValue trxValue = new OBPreDealTrxValue();

		trxValue.setTransactionID(feedId);
		param.setAction(ICMSConstant.ACTION_READ_PRE_DEAL_BY_FEED_ID);

		try {
			return operate(trxValue, param);
		}
		catch (TransactionException e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public Collection searchByCounterName(String name) {
		return new PreDealDao().searchByCounterName(name);
	}

	public Collection searchByRIC(String ric) {
		return new PreDealDao().searchByRIC(ric);
	}

	public Collection searchByStockCode(String stockCode) {
		return new PreDealDao().searchByStockCode(stockCode);
	}

	public Collection searchByIsinCode(String isinCode) {
		return new PreDealDao().searchByIsinCode(isinCode);
	}

	public PreDealSearchRecord searchByFeedId(String feedId) throws PreDealException {
		return new PreDealDao().searchByFeedId(feedId);
	}

	public SearchResult viewEarGroup(SearchCriteria criteria) {
		return new PreDealDao().viewEarGroup(criteria);
	}

	public SearchResult viewEarMark(SearchCriteria criteria) {
		return new PreDealDao().viewEarMark(criteria);
	}

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystem, long feedId) throws PreDealException,
			RemoteException {

		try {
			return getPreDealBusManager().getEarMarkGroupBySourceAndFeedId(sourceSystem, feedId);

		}
		catch (Exception e) {
			throw new PreDealException(e);
		}
	}

	private ICMSTrxValue operate(ICMSTrxValue trxValue, ITrxParameter trxParam) throws TransactionException {
		ITrxControllerFactory factory = new PreDealTrxControllerFactory();
		ITrxController controller = factory.getController(trxValue, trxParam);
		ITrxResult trxResult = controller.operate(trxValue, trxParam);

		return (ICMSTrxValue) trxResult.getTrxValue();
	}

	private void rollback() {
		if (this.context != null) {
			try {
				this.context.setRollbackOnly();
			}
			catch (Throwable t) {
				// do nothing
			}
		}
	}

	protected SBPreDealBusManager getPreDealBusManager() throws Exception {
		SBPreDealBusManager home = (SBPreDealBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_PRE_DEAL_BUS_JNDI,
				SBPreDealBusManagerHome.class.getName());

		if (home != null) {
			return home;
		}
		else {
			throw new Exception("SBPreDealBusManager is null!");
		}
	}

}