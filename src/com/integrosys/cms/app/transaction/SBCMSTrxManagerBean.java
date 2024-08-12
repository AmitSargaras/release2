/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/SBCMSTrxManagerBean.java,v 1.26 2005/04/30 08:29:30 lyng Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.component.common.constant.ICompJNDIConstant;
import org.apache.commons.lang.Validate;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * <p/>
 * The SBCMSTrxManagerBean acts as the facade to the Entity Beans for
 * transaction data.
 * <p/>
 * <p/>
 * Consider to use
 * {@link #executeCMSTrxValueAction(CMSTrxValueAction, boolean, boolean)} when
 * need to interface with cms transaction value home. which this will handle
 * exception gracefully.
 *
 * @author Alfred Lee
 * @author Chong Jun Yong
 */
public class SBCMSTrxManagerBean extends CMSPersistenceTrxManager implements javax.ejb.SessionBean {
    /**
     * SessionContext object
     */
    protected SessionContext sessionContext = null;

    /**
     * Default Constructor
     */
    public SBCMSTrxManagerBean() {
        super();
    }

    /**
     * Get a transaction object given a transaction ID
     *
     * @param trxID is the String value of the transaction ID
     * @return ITrxValue
     * @throws TransactionException on any other errors
     */
    public ICMSTrxValue getTransaction(final String trxID) throws TransactionException {
        Validate.notNull(trxID, "transaction id supplied must not be null.");

        return (ICMSTrxValue) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                EBCMSTrxValue remote = home.findByPrimaryKey(trxID);
                ICMSTrxValue value = remote.getTransaction();

                DefaultLogger.debug(this, "History ID FRom Transaction is" + trxID);
                if ("true".equals(PropertyManager.getValue("integrosys.transaction.getAllHistory"))) {
                    EBTrxHistoryHome histHome = getTrxHistoryHome();
                    Collection c = histHome.findByTransactionId(trxID);

                    OBTrxHistoryValue[] hist = new OBTrxHistoryValue[c.size()];
                    Iterator historyIter = c.iterator();
                    for (int i = 0; historyIter.hasNext(); i++) {
                        EBTrxHistory trxHist = (EBTrxHistory) historyIter.next();
                        if (trxHist != null) {
                            hist[i] = trxHist.getValue();
                        }
                    }

                    if (hist != null) {
                        value.setTransactionHistory(hist);
                    }

                }
                return value;
            }
        }, false, false);

    }

    /**
     * Method to create a transaction record
     *
     * @param value is the ICMSTrxValue transaction object
     * @return ICMSTrxValue
     * @throws TransactionException on errors
     */
    public ICMSTrxValue createTransaction(final ICMSTrxValue value) throws TransactionException {
        if (null == value) {
            sessionContext.setRollbackOnly();
            throw new TransactionException("trx value must not be null", new NullPointerException(
                    "trx value supplied for creating transaction is null"));
        }

        return (ICMSTrxValue) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                Date d = DateUtil.getDate();
               
// By abhijit r : need to add code from general param.
                IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
    			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
    			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
    			Date date=new Date();
    			for(int i=0;i<generalParamEntries.length;i++){
    				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
    					 date=new Date(generalParamEntries[i].getParamValue());
    				}
    			}
    			
    			//date.setTime(d.getTime());
    			date.setHours(d.getHours());
    			date.setMinutes(d.getMinutes());
    			date.setSeconds(d.getSeconds());
    			DefaultLogger.debug(this,"date from general param:"+date);
    			 if (null == value.getCreateDate()) {
                     value.setCreateDate(date);
                 }
    			
                value.setTransactionDate(date);
                value.setSystemDate(d);
                EBCMSTrxValue remote = home.create(value);

                return remote.getTransaction();
            }
        }, false, true);

    }

    /**
     * <p/>
     * Update transaction record. Transaction has to be populated with correct
     * workflow param, such as from state, to state.
     * <p/>
     * <p/>
     * The transaction record passed into here will be populated with today date
     * into transaction date only.
     *
     * @param value is the transaction object
     * @return transaction value with updated data
     */
    public ICMSTrxValue updateTransaction(final ICMSTrxValue value) throws TransactionException {
        if (null == value) {
            sessionContext.setRollbackOnly();
            throw new TransactionException("trx value must not be null", new NullPointerException(
                    "trx value supplied for update transaction is null"));
        }

        String trxID = value.getTransactionID();
        Validate.notNull(trxID, "transaction id in trx value supplied must not be null.");

        return (ICMSTrxValue) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                Date d = DateUtil.getDate();
             // By abhijit r : need to add code from general param.
                IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
    			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
    			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
    			Date date=new Date();
    			for(int i=0;i<generalParamEntries.length;i++){
    				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
    					 date=new Date(generalParamEntries[i].getParamValue());
    				}
    			}
    			
    		
    			// date.setTime(d.getTime());
    			 date.setHours(d.getHours());
     			date.setMinutes(d.getMinutes());
     			date.setSeconds(d.getSeconds());
     			DefaultLogger.debug(this,"date from general param:"+date);
                value.setTransactionDate(date);
                value.setSystemDate(d);
                EBCMSTrxValue remote = home.findByPrimaryKey(value.getTransactionID());
                remote.setTransaction(value);

                ICMSTrxValue trxValue = remote.getTransaction();
                return trxValue;
            }
        }, false, true);
    }

    /**
     * Get the transaction by reference ID and transaction type
     *
     * @param referenceID - String
     * @return ICMSTrxValue - the obj containing the transaction info
     * @throws TrxParameterException
     * @throws TransactionException
     */
    public ICMSTrxValue getTrxByRefIDAndTrxType(String referenceID, String trxType) throws TransactionException {
        return findTrxByRefIDAndTrxType(referenceID, trxType);
    }

    /**
     * Get the transaction by stage reference ID and transaction type, else
     * return null
     *
     * @param stageReferenceID domain object primary key which is the staging
     *                         reference id in transaction
     * @param trxType          transaction type or instance name
     * @return ITrxValue the obj containing the transaction info match the
     *         criteria supplied, else null will be returned
     */
    public ICMSTrxValue getTrxByStageRefIDAndTrxType(final String stageReferenceID, final String trxType)
            throws TransactionException {
        Validate.notNull(stageReferenceID, "staging reference id to be searched must not be null.");

        return (ICMSTrxValue) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                EBCMSTrxValue remote = home.findByStageRefIDAndTrxType(new Long(stageReferenceID), trxType);
                ICMSTrxValue trxValue = remote.getTransaction();

                OBCMSTrxRouteInfo userInfo = CMSTransactionExtDAO.getUserInfo(trxValue);
                trxValue.setToUserInfo(userInfo.getLableOfUserInfo());
                return trxValue;
            }
        }, true, false);
    }

    /**
     * Find the transaction by reference ID and transaction type. Return
     * transaction info if the transaction is found, otherwise return null.
     *
     * @param referenceID domain object primary key which is the reference id in
     *                    trasaction.
     * @param trxType     transaction type or instance name
     * @return transaction value match the criteria supplied, else null will be
     *         returned
     */
    public ICMSTrxValue findTrxByRefIDAndTrxType(final String referenceID, final String trxType)
            throws TransactionException {
        Validate.notNull(referenceID, "reference id to be searched must not be null.");

        return (ICMSTrxValue) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                EBCMSTrxValue remote = home.findByRefIDAndTrxType(new Long(referenceID), trxType);
                ICMSTrxValue trxValue = remote.getTransaction();
                OBCMSTrxRouteInfo userInfo = CMSTransactionExtDAO.getUserInfo(trxValue);
                trxValue.setToUserInfo(userInfo.getLableOfUserInfo());
                return trxValue;
            }
        }, true, false);

    }

    /**
     * Get all transactions given a parent transaction ID
     *
     * @param parentTrxID is of type String
     * @param trxType     the String value of the transaction type
     * @return ICMSTrxValue[] or null if no such data is found
     * @throws TransactionException on error
     */
    public ICMSTrxValue[] getTrxByParentTrxID(final String parentTrxID, final String trxType)
            throws TransactionException {
        Validate.notNull(parentTrxID, "parent trx id to be searched must not be null.");

        return (ICMSTrxValue[]) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                Collection c = home.findByParentTrxID(new Long(parentTrxID), trxType);
                ArrayList list = new ArrayList();
                Iterator iter = c.iterator();
                while (iter.hasNext()) {
                    EBCMSTrxValue remote = (EBCMSTrxValue) iter.next();
                    list.add(remote.getTransaction());
                }
                return (ICMSTrxValue[]) list.toArray(new ICMSTrxValue[0]);
            }
        }, true, false);
    }

    public Collection searchNextRouteList(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAOXA().searchNextRouteList(criteria);
    }

    public int authLevel(CMSTrxSearchCriteria criteria) {
        return CMSTransactionExtDAO.authLevel(criteria);
    }

    public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO(criteria).searchTransactions(criteria);
        // +
    }

    public SearchResult searchWorkflowTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO().searchWorkflowTransactions(criteria);
    }

    public SearchResult searchPendingCases(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO().searchPendingCases(criteria);
    }

    public int getTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO(criteria).getTransactionCount(criteria);
    }

    public int getAllTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO().getAllTransactionCount(criteria);

    }

    public int getWorkflowTrxCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO().getWorkflowTrxCount(criteria);

    }

    public Collection getLPTodoList() throws SearchDAOException {
        return CMSTransactionDAOFactory.getDAO().getLPTodoList();
    }

    private EBCMSTrxValueHome getEBCMSTrxValueHome() throws TransactionException {
        EBCMSTrxValueHome home = (EBCMSTrxValueHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CMS_TRX_VALUE_HOME,
                ICMSJNDIConstant.EB_CMS_TRX_VALUE_HOME_PATH);
        if (null == home) {
            throw new TransactionException("EBCMSTrxValueHome is null!");
        } else {
            return home;
        }
    }

    private EBTrxHistoryHome getTrxHistoryHome() throws TrxOperationException {
        EBTrxHistoryHome home = (EBTrxHistoryHome) BeanController.getEJBHome(ICompJNDIConstant.JNDI_EBTrxHistory,
                ICompJNDIConstant.HOME_EBTrxHistory);
        if (null == home) {
            throw new TrxOperationException("EBTrxHistoryHome is null!");
        }
        return home;
    }

    /**
     * <p/>
     * Execute trx value action supplied by passing in the cms trx value home.
     * Handle all Exception caught using
     * {@link #handleException(Exception, boolean)}
     * <p/>
     * <p/>
     * It has to be known by caller that need to mark roll back for the current
     * <code>transaction</code> by supplying arg <code>markRollback</code>
     *
     * @param action       action to execute using eb cms trx value home.
     * @param returnNullIfCaughtFinderException
     *                     indicate whether return null if
     *                     finder exception is caught when executing home interface
     * @param markRollback set mark roll back for current
     *                     <code>transaction</code>
     * @return object that action class required
     * @throws TransactionException if there is any exception encountered,
     *                              {@link RemoteException} caught will be wrapped by using it's
     *                              {@link RemoteException#getCause()}, rather than treat remote
     *                              exception as cause
     */
    protected Object executeCMSTrxValueAction(CMSTrxValueAction action, boolean returnNullIfCaughtFinderException,
                                              boolean markRollback) throws TransactionException {
        try {
            EBCMSTrxValueHome home = getEBCMSTrxValueHome();
            return action.doInCMSTrxValueHome(home);
        }
        catch (Exception ex) {
            if (markRollback) {
                sessionContext.setRollbackOnly();
            }

            return handleException(ex, returnNullIfCaughtFinderException);
        }
    }

    interface CMSTrxValueAction {
        /**
         * Call back interface to implemented to execute on eb cms trx value
         * home interface.
         *
         * @param home home interface of eb cms trx vcalue
         * @return the result execute on home interface supplied
         * @throws Exception any exception encounter when executing on home
         *                   interface supplied, mainly ejb exception or remote exception
         */
        public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception;
    }

    /**
     * Handler EJB Exception and also Remote Exception, others unhandled
     * Exception will be wrapped into {@link TransactionException}.
     *
     * @param ex the exception caught by caller
     * @param returnNullIfCaughtFinderException
     *           indiciate whether to return null
     *           if finder exception caught
     * @return only will return null for such, others are throws
     *         TransactionException
     */
    protected Object handleException(Exception ex, boolean returnNullIfCaughtFinderException)
            throws TransactionException {
        if (ex instanceof CreateException) {
            throw new TransactionException("fail to create trx value bean", ex);
        }

        if (ex instanceof FinderException) {
            if (returnNullIfCaughtFinderException) {
                return null;
            }

            throw new TransactionException("fail to find trx value bean", ex);
        }

        if (ex instanceof ConcurrentUpdateException) {
            throw new TransactionException("fail to update transaction due to concurrent update.", ex);
        }

        if (ex instanceof RemoteException) {
            throw new TransactionException("fail to execute on cms trx value remote interface", ex.getCause());
        }

        throw new TransactionException("unknown exception [" + ex.getClass() + "]", ex);
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
        sessionContext = sc;
    }

    /**
     * @see com.integrosys.cms.app.transaction.SBCMSTrxManager#getWorkingTrxByTrxType
     */
    public ICMSTrxValue getWorkingTrxByTrxType(String trxType) throws TransactionException {
        ICMSTrxValue[] listing = getWorkingTrxListByTrxType(trxType);
        if (listing.length > 0) {
            return listing[0];
        }
        return null;
    }

    /**
     * @see com.integrosys.cms.app.transaction.SBCMSTrxManager#getWorkingTrxListByTrxType
     */
    public ICMSTrxValue[] getWorkingTrxListByTrxType(final String trxType) throws TransactionException {
        return (ICMSTrxValue[]) executeCMSTrxValueAction(new CMSTrxValueAction() {
            public Object doInCMSTrxValueHome(EBCMSTrxValueHome home) throws Exception {
                Collection c = home.findWorkingTrxByTrxType(trxType);
                ArrayList trxValList = new ArrayList();
                Iterator iter = c.iterator();
                for (int i = 0; iter.hasNext(); i++) {
                    EBCMSTrxValue remote = (EBCMSTrxValue) iter.next();
                    if (remote != null) {
                        trxValList.add(remote.getTransaction());
                    }
                }
                return trxValList.toArray(new OBCMSTrxValue[0]);
            }
        }, true, false);
    }
}