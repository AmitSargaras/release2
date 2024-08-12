/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/SBCompTrxManagerBean.java,v 1.1 2003/07/23 12:39:08 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.component.user.app.bus.UserException;

/**
 * The SBCMSTrxManagerBean acts as the facade to the Entity Beans for
 * transaction data. It provides some methods to retrieve fund buy transactions.
 * The implemented <code.getTrxProcessList()</code> method contains the list of
 * ITrxProcess that is used to drive the transaction engine.
 * 
 * @author Alfred Lee
 * @version $Revision: 1.1 $
 */
public class SBCompTrxManagerBean implements javax.ejb.SessionBean {

	private static final long serialVersionUID = -9217273468013035766L;

	/**
	 * SessionContext object
	 */
	protected SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBCompTrxManagerBean() {
		super();
	}

	/**
	 * Get a transaction object given a transaction ID
	 * 
	 * @param trxID is the String value of the transaction ID
	 * @return ITrxValue
	 * @throws TransactionException on any other errors
	 */
	public ITrxValue getTransaction(String trxID) throws TransactionException {
		if (null == trxID) {
			throw new TransactionException("TrxID is null!");
		}
		EBCMSTrxValueHome home = getEBCMSTrxValueHome();
		try {
			EBCMSTrxValue remote = home.findByPrimaryKey(trxID);
			return remote.getTransaction();
		}
		catch (FinderException ex) {
			throw new TransactionException("Cannot find transaction for transaction id [" + trxID + "] ", ex);
		}
		catch (RemoteException ex) {
			throw new TransactionException("Failed to get transaction for transaction id [" + trxID + "]", ex
					.getCause());
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is the ITrxValue transaction object
	 * @return ITrxValue
	 * @throws TransactionException on errors
	 */
	public ITrxValue createTransaction(ITrxValue value) throws TransactionException {
		if (null == value) {
			_context.setRollbackOnly();
			throw new TransactionException("ITrxValue is null!");
		}
		EBCMSTrxValueHome home = getEBCMSTrxValueHome();
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) value;
			Date d = DateUtil.getDate();
			if (null == trxValue.getCreateDate()) {
				try {
					trxValue.setCreateDate(getApplicationDate());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// By abhijit r : need to add code from general param.
			try {
				//trxValue.setTransactionDate(getApplicationDate());
				trxValue.setTransactionDate(d);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EBCMSTrxValue remote = home.create(trxValue);
			return remote.getTransaction();
		}
		catch (CreateException ex) {
			_context.setRollbackOnly();
			throw new TransactionException("Cannot create transaction for value [" + value + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new TransactionException("Failed to create transaction for value [" + value + "] ", ex.getCause());
		}
	}

	/**
	 * Method to update a transaction record
	 * 
	 * @param value is the ITrxValue transaction object
	 * @return ITrxValue
	 * @throws TransactionException
	 */
	public ITrxValue updateTransaction(ITrxValue value) throws TransactionException {
		if (null == value) {
			_context.setRollbackOnly();
			throw new TransactionException("ITrxValue is null!");
		}
		String trxID = value.getTransactionID();
		if (null == trxID) {
			throw new TransactionException("TrxID is null!");
		}
		EBCMSTrxValueHome home = getEBCMSTrxValueHome();
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) value;

			Date d = DateUtil.getDate();
			// By abhijit r : need to add code from general param.
			try {
//				trxValue.setTransactionDate(getApplicationDate());
				trxValue.setTransactionDate(d);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EBCMSTrxValue remote = home.findByPrimaryKey(trxID);
			remote.setTransaction(trxValue);
			return remote.getTransaction();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new TransactionException("Cannot find the transaction to be updated, for trx ID ["
					+ value.getTransactionID() + "]", ex);
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw new TransactionException("Another thread has updated the same trx, for trx ID ["
					+ value.getTransactionID() + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new TransactionException("Faield to update transaction, for trx ID [" + value.getTransactionID()
					+ "]", ex.getCause());
		}
	}

	/**
	 * Get the transaction by reference ID and transaction type
	 * @param referenceID - String
	 * @return ITrxValue - the obj containing the transaction info
	 * @throws TrxParameterException
	 * @throws TransactionException
	 */
	public ITrxValue getTrxByRefIDAndTrxType(String referenceID, String trxType) throws TransactionException {
		if (referenceID == null) {
			throw new TransactionException("Reference ID is null!!!");
		}

		try {
			EBCMSTrxValue remote = getEBCMSTrxValueHome().findByRefIDAndTrxType(new Long(referenceID), trxType);
			return remote.getTransaction();
		}
		catch (FinderException ex) {
			throw new TransactionException("Cannot find transaction, for reference id [" + referenceID
					+ "], transaction type [" + trxType + "]: ", ex);
		}
		catch (RemoteException ex) {
			throw new TransactionException("Faield to find transaction, for reference id [" + referenceID
					+ "], transaction type [" + trxType + "]: ", ex.getCause());
		}
	}

	/**
	 * Helper method
	 */
	private EBCMSTrxValueHome getEBCMSTrxValueHome() throws TransactionException {
		EBCMSTrxValueHome home = (EBCMSTrxValueHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CMS_TRX_VALUE_HOME,
				ICMSJNDIConstant.EB_CMS_TRX_VALUE_HOME_PATH);
		if (null == home) {
			throw new TransactionException("EBCMSTrxValueHome is null!");
		}
		else {
			return home;
		}
	}

	// EJB methods

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
	
	
	
	public Date getApplicationDate() throws SQLException{
		DefaultLogger.debug(this, "Entering to get application::::::::::::::");
		 DBUtil dbUtil = null;
		 ResultSet rs=null;
		String userSQL = " select trunc(TO_TIMESTAMP(gp.param_value,'DD/MM/YYYY')) from  cms_general_param gp where gp.param_code='APPLICATION_DATE' ";
		// String userSQL = "select to_date(TO_date(gp.param_value,'DD/MM/YYYY') ) || ' ' || TO_CHAR (SYSDATE, 'HH24:MI:SS')  AS DATE1 from  cms_general_param gp where gp.param_code='APPLICATION_DATE' ";

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			rs= dbUtil.executeQuery();
			
			//dbUtil.commit();
			return (rs.next() ? rs.getDate(1) : new Date());
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				throw new UserException("Exception at dormantUserAccounts() method");
			} catch (UserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				try {
					throw new UserException("Exception at dormantUserAccounts() method");
				} catch (UserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		 return  new Date();
		
	}
}