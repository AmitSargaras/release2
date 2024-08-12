/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManager;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManagerHome;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.MILeSearchCriteria;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.manualinput.security.trx.MISecTransactionController;
import com.integrosys.cms.app.manualinput.security.trx.TransactionActionConst;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SBMISecProxyBean implements SessionBean {
	private SessionContext _context = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext sc) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		_context = sc;
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public ICollateralTrxValue searchCollateralByColId(String colId) throws CollateralException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(colId, ICMSConstant.INSTANCE_COLLATERAL);
			OBCollateralTrxValue trxValue = new OBCollateralTrxValue(cmsTrxValue);
			String refId = trxValue.getReferenceID();
			if ((refId != null) && !(Long.parseLong(refId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBCollateralBusManager manager = getSBCollateralManager();
				ICollateral col = manager.getCollateral(Long.parseLong(refId));
				trxValue.setCollateral(col);
			}
			String stgRefId = trxValue.getStagingReferenceID();
			if ((stgRefId != null) && !(Long.parseLong(stgRefId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBCollateralBusManager manager = getSBCollateralManagerStaging();
				ICollateral col = manager.getCollateral(Long.parseLong(stgRefId));
				trxValue.setStagingCollateral(col);
			}
			MISecProxyHelper helper = new MISecProxyHelper();
			ICollateralTrxValue newTrxValue = helper.prepareTrxSec(trxValue);
			return newTrxValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CollateralException("Exception in method searchCollateralByColId");
		}
	}

	public ICollateralTrxValue searchCollateralByTrxId(String trxId) throws CollateralException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(trxId);
			OBCollateralTrxValue trxValue = new OBCollateralTrxValue(cmsTrxValue);
			String refId = trxValue.getReferenceID();
			if ((refId != null) && !(Long.parseLong(refId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBCollateralBusManager manager = getSBCollateralManager();
				ICollateral col = manager.getCollateral(Long.parseLong(refId));
				trxValue.setCollateral(col);
			}
			String stgRefId = trxValue.getStagingReferenceID();
			if ((stgRefId != null) && !(Long.parseLong(stgRefId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBCollateralBusManager manager = getSBCollateralManagerStaging();
				ICollateral col = manager.getCollateral(Long.parseLong(stgRefId));
				trxValue.setStagingCollateral(col);
			}
			MISecProxyHelper helper = new MISecProxyHelper();
			ICollateralTrxValue newTrxValue = helper.prepareTrxSec(trxValue);
			return newTrxValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CollateralException("Exception in method searchCollateralByTrxId");
		}
	}

	public ICMSTrxResult createCollateralTrx(ITrxContext ctx, ICollateralTrxValue value, boolean isSave)
			throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			value.setStatus(ICMSConstant.STATE_ND);
			value.setFromState(ICMSConstant.STATE_ND);
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if (!isSave) {
				param.setAction(TransactionActionConst.ACTION_MANUAL_CREATE_SEC);
			}
			else {
				param.setAction(TransactionActionConst.ACTION_MANUAL_SAVE_SEC);
			}
			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in createCollateralTrx");
		}
	}

	public ICMSTrxResult makerDirectCreateCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			value.setStatus(ICMSConstant.STATE_ND);
			value.setFromState(ICMSConstant.STATE_ND);
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();

			param.setAction(TransactionActionConst.ACTION_MAKER_DIRECT_CREATE_SEC);

			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in createCollateralTrx");
		}
	}

	public ICMSTrxResult makerUpdateCollateralTrx(ITrxContext ctx, ICollateralTrxValue value, boolean isSave)
			throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if (!isSave) {
				param.setAction(TransactionActionConst.ACTION_MANUAL_UPDATE_SEC);
			}
			else {
				param.setAction(TransactionActionConst.ACTION_MANUAL_SAVE_SEC);
			}
			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in makerUpdateSecTrx");
		}
	}

	public ICMSTrxResult makerCloseCollateralTrx(ITrxContext ctx, ICollateralTrxValue value) throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_CLOSE_SEC);

			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in makerCloseSecTrx");
		}
	}

	public ICMSTrxResult checkerRejectCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_REJECT_SEC);

			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in checkerRejectSecTrx");
		}
	}

	public ICMSTrxResult checkerApproveCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException {
		try {
			MISecProxyHelper helper = new MISecProxyHelper();
			helper.constructTrxValue(ctx, value);
			// pass the manualinput stage, setback transaction subtype to null
			// value.setTransactionSubType(null);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_APPROVE_SEC);

			ITrxController controller = new MISecTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new CollateralException("Exception in checkerApproveSecTrx");
		}
	}

	public List searchCustomerForPlgLink(MILeSearchCriteria criteria) throws SearchDAOException {
		return new CustomerDAO().searchCustomerForPlgLink(criteria);
	}

	public ICollateralSubType[] getCollateralSubTypesByTypeCode(String typeCode) throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getCollateralSubTypesByTypeCode(typeCode);
	}

	private SBCMSTrxManager getTrxManager() throws TransactionException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());
		if (null == mgr) {
			throw new TransactionException("SBCMSTrxManager is null!");
		}
		else {
			return mgr;
		}
	}

	private SBCollateralBusManager getSBCollateralManager() throws TransactionException {
		SBCollateralBusManager home = (SBCollateralBusManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI, SBCollateralBusManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBCollateraBusManager is null!");
		}
	}

	private SBCollateralBusManager getSBCollateralManagerStaging() throws TransactionException {
		SBCollateralBusManager home = (SBCollateralBusManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_STAGING_JNDI, SBCollateralBusManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBCollateraBusManager is null!");
		}
	}
	
	public List getCollateralCodeBySubTypes(String subType) throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getCollateralCodeBySubTypes(subType);
	}
	
/*	public ICountry[] getListAllCountry() throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getListAllCountry();
	}
	
	public ISystemBankBranch[] getListAllSystemBankBranch(String country) throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getListAllSystemBankBranch(country); 
	}
	
	public IForexFeedEntry[] getCurrencyList() throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getCurrencyList(); 
	}*/

}
