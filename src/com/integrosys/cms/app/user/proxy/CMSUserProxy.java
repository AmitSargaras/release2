//Source file: C:\\los_rose\\src\\com\\integrosys\\los\\app\\user\\UserProxy.java

package com.integrosys.cms.app.user.proxy;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.TrxOperationHelper;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.constant.JNDIConstant;
import com.integrosys.component.user.app.proxy.CommonUserProxy;
import com.integrosys.component.user.app.proxy.SBCommonUserProxy;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

public class CMSUserProxy extends CommonUserProxy {

	/**
     */
	public CMSUserProxy() {
		super();
	}

	public ICompTrxResult makerCreateUser(ICommonUserTrxValue trxValue, ICommonUser user) throws UserException,
			RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveCreateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectCreateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelCreateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerUpdateUser(ICommonUserTrxValue trx, ICommonUser user) throws UserException,
			RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveUpdateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectUpdateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelUpdateUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerDeleteUser(ICommonUserTrxValue trx) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveDeleteUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectDeleteUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelDeleteUser(ICommonUserTrxValue trxValue) throws UserException, RemoteException {
		throw new UserException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	// ******* Methods for maker/checker create and maintenance of user
	// **********
	public ICompTrxResult makerCreateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue, ICommonUser user)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, user);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerCreateUser(trxValue, user);
	}

	public ICompTrxResult checkerApproveCreateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerApproveCreateUser(trxValue);
	}

	public ICompTrxResult checkerRejectCreateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerRejectCreateUser(trxValue);
	}

	public ICompTrxResult makerCancelCreateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerCancelCreateUser(trxValue);
	}

	public ICompTrxResult makerUpdateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue, ICommonUser user)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, user);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerUpdateUser(trxValue, user);
	}

	public ICompTrxResult checkerApproveUpdateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerApproveUpdateUser(trxValue);
	}

	public ICompTrxResult checkerRejectUpdateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerRejectUpdateUser(trxValue);
	}

	public ICompTrxResult makerCancelUpdateUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerCancelUpdateUser(trxValue);
	}

	public ICompTrxResult makerDeleteUser(ITrxContext trxContext, ICommonUserTrxValue trxValue) throws UserException,
			RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerDeleteUser(trxValue);
	}

	public ICompTrxResult checkerApproveDeleteUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerApproveDeleteUser(trxValue);
	}

	public ICompTrxResult checkerRejectDeleteUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.checkerRejectDeleteUser(trxValue);
	}

	public ICompTrxResult makerCancelDeleteUser(ITrxContext trxContext, ICommonUserTrxValue trxValue)
			throws UserException, RemoteException {
		trxValue = (ICommonUserTrxValue) mapTrxValue(trxContext, trxValue, null);

		SBCommonUserProxy proxy = getProxy();
		return proxy.makerCancelDeleteUser(trxValue);
	}

	/**
	 * Prepares map trx context into ICMSTrxValue
	 */
	private ITrxValue mapTrxValue(ITrxContext trxContext, ITrxValue value, ICommonUser user) throws UserException {
		try {
			value = TrxOperationHelper.mapTrxContext(trxContext, value);
			ICMSTrxValue trxValue = (ICMSTrxValue) value;
			trxValue.setTrxContext(trxContext);
			OBUserTrxValue ut = (OBUserTrxValue) value;
			if (user != null) {
				ut.setCustomerName(user.getLoginID());
			}
			else if (ut.getStagingUser() != null) {
				ut.setCustomerName(ut.getStagingUser().getLoginID());
			}
			else if (ut.getUser() != null) {
				ut.setCustomerName(ut.getUser().getLoginID());
			}

			return trxValue;
		}
		catch (TransactionException e) {
			throw new UserException("Failed to map transaction context into transaction value", e);
		}
	}

	private SBCommonUserProxy getProxy() throws UserException {
		SBCommonUserProxy up = (SBCommonUserProxy) BeanController.getEJB(JNDIConstant.SB_COMMONUSER_PROXY_HOME,
				JNDIConstant.SB_COMMONUSER_PROXY_HOME_PATH);
		if (null == up) {
			throw new UserException("SBCommonUserProxy is null!");
		}
		else {
			return up;
		}
	}
}
