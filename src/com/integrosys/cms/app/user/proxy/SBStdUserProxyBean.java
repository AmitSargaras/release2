/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/proxy/SBStdUserProxyBean.java,v 1.1 2005/08/08 08:27:12 dli Exp $
 */
package com.integrosys.cms.app.user.proxy;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.common.transaction.OBCompTrxParameter;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.proxy.CommonUserProxy;
import com.integrosys.component.user.app.trx.CommonUserTrxControllerFactory;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * This session bean extends from CommonUserProxy, wrapped in an SLSB mechanism.
 * 
 * @author $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 08:27:12 $ Tag: $Name: $
 */

public class SBStdUserProxyBean extends CommonUserProxy implements SessionBean {
	public SBStdUserProxyBean() {
		DefaultLogger.debug(this, "!!!!!!!!!!SBStdUserProxyBean was created !!!!!!!!!! ");
		_context = null;
	}

	public SearchResult searchUsers(CommonUserSearchCriteria criteria) throws EntityNotFoundException, RemoteException,
			SearchDAOException {
		return (new StdUserDAO()).searchUser(criteria);
	}

	public ICompTrxResult makerCreateUser(ICommonUserTrxValue trxValue, ICommonUser user) throws UserException {
		try {
			trxValue.setStagingUser(user);
			trxValue.setTransactionType("USER");
			OBCompTrxParameter param = new OBCompTrxParameter();
			param.setAction("MAKER_CREATE_USER");
			ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
			ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
			return result;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerApproveCreateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_APPROVE_CREATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.debug(this, "!!!!!!!!!!ErrorCode in UserException: " + e.getErrorCode());
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			if (e instanceof OFAException) {
				OFAException oe = (OFAException) e;
				DefaultLogger.debug(this, "!!!!!!!!!!ErrorCode in OFAException: " + oe.getErrorCode());
			}
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerRejectCreateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_REJECT_CREATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult makerCancelCreateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("MAKER_CANCEL_CREATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult makerUpdateUser(ICommonUserTrxValue trxValue, ICommonUser user) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				trxValue.setStagingUser(user);
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("MAKER_UPDATE_USER");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerApproveUpdateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_APPROVE_UPDATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerRejectUpdateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_REJECT_UPDATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult makerCancelUpdateUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("MAKER_CANCEL_UPDATE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult makerDeleteUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("MAKER_DELETE_USER");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerApproveDeleteUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_APPROVE_DELETE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult checkerRejectDeleteUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("CHECKER_REJECT_DELETE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public ICompTrxResult makerCancelDeleteUser(ICommonUserTrxValue trxValue) throws UserException {
		try {
			if (null == trxValue) {
				throw new UserException("ICommonUserTrxValue is null!");
			}
			else {
				trxValue.setTransactionType("USER");
				OBCompTrxParameter param = new OBCompTrxParameter();
				param.setAction("MAKER_CANCEL_DELETE");
				ITrxController controller = (new CommonUserTrxControllerFactory()).getController(trxValue, param);
				ICompTrxResult result = (ICompTrxResult) controller.operate(trxValue, param);
				return result;
			}
		}
		catch (UserException e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "Caught Exception!", e);
			throw new UserException("Caught Exception!", e);
		}
	}

	public Date getLastLoginTime(long userID) throws RemoteException, UserException {
		return (new StdUserDAO()).getLastLoginTime(userID);
	}
	
	public String getUserNameByUserID(long userID) throws RemoteException, UserException
	{
	    return (new StdUserDAO()).getUserNameByUserID(userID);
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	private SessionContext _context;
}
