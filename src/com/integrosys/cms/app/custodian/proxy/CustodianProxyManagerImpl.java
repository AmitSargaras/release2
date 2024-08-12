/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/CustodianProxyManagerImpl.java,v 1.41 2005/10/14 02:26:37 whuang Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.IMemo;
import com.integrosys.cms.app.custodian.bus.OBCustAuthorize;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

import javax.ejb.FinderException;

/**
 * This class is the proxy to the custodian transaction and business package.
 * The methods provided are more use case specific
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.41 $
 * @since $Date: 2005/10/14 02:26:37 $ Tag: $Name: $
 */
public class CustodianProxyManagerImpl implements ICustodianProxyManager {

	/**
	 * Save a custodian doc as draft
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the custodian doc that has been saved as draft
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc createDraftCustodianDoc(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		try {
			return getCustodianProxyManager().createDraftCustodianDoc(anITrxContext, anICustodianDoc);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Receive the custodian and submit the draft
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the draft custodian doc submit
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue receiveDraftCustodianDoc(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue) throws CustodianException {
		try {
			return getCustodianProxyManager().receiveDraftCustodianDoc(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Create a borrower custodian doc that is not in a checkList manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocMaker(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc, String borrowerTrxId)
			throws CustodianException {
		try {
			return getCustodianProxyManager().createDocMaker(anITrxContext, anICustodianDoc, borrowerTrxId);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue createDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().createDocChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Create a borrower custodian doc thru' checkList
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocByBorrower(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		try {
			return getCustodianProxyManager().createDocByBorrower(anITrxContext, anICustodianDoc);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Create a collateral custodian doc thru' checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return OBCustodianDoc - the biz object containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocByCollateral(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		try {
			return getCustodianProxyManager().createDocByCollateral(anITrxContext, anICustodianDoc);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianDoc
	 * @return ICustodianDoc- the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			return getCustodianProxyManager().lodgeDocMaker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianProxyManager().lodgeDocMaker(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianDoc
	 * @return ICustodianDoc- the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			return getCustodianProxyManager().lodgeDocMaker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker re Lodge a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianProxyManager().lodgeDocMaker(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().lodgeDocChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().lodgeDocChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianDoc
	 * @return ICustodianDoc- the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			return getCustodianProxyManager().permUplift(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker perm uplift a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianProxyManager().permUplift(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approvePermUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().approvePermUplift(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianDoc
	 * @return ICustodianDoc- the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			return getCustodianProxyManager().tempUplift(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker temp uplift a custodian doc
	 * @param anITrxContext -\of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be temp
	 *         uplifted
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianProxyManager().tempUplift(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approveTempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().approveTempUplift(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker approve updating a custodian doc. This will cater to the maintain
	 * custodian doc use case
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx object containing the info of the
	 *         custodian doc updated
	 * @throws CustodianException
	 */
	public ICustodianTrxValue updateDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		return null;
	}

    public SearchResult getPendingReversalList(long aLimitProfileID) throws CustodianException {

		return getPendingReversalList(null, aLimitProfileID);
	}


    public SearchResult getPendingReversalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingReversalList(aTrxContext, aLimitProfileID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}


    public SearchResult getPendingReversalListForNonBorrower(long aCustomerID) throws CustodianException {

        return getPendingLodgementListForNonBorrower(null, aCustomerID);
    }

    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
            throws CustodianException {
        try {
            return getCustodianProxyManager().getPendingLodgementListForNonBorrower(aTrxContext, aCustomerID);
        }
        catch (CustodianException cex) {
            throw cex;
        }
        catch (RemoteException rex) {
            throw new CustodianException(rex.toString());
        }
    }

    public SearchResult getPendingReversalListForNonBorrower(long aLimitProfileID, long aCustomerID)
            throws CustodianException {
        return getPendingLodgementListForNonBorrower(null, aLimitProfileID, aCustomerID);
    }

    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
            long aCustomerID) throws CustodianException {
        try {
            return getCustodianProxyManager().getPendingLodgementListForNonBorrower(aTrxContext, aLimitProfileID,
                    aCustomerID);
        }
        catch (CustodianException cex) {
            throw cex;
        }
        catch (RemoteException rex) {
            throw new CustodianException(rex.toString());
        }
    }





    /**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aLimitProfileID - the limit profile ID
	 * @return ICustodianDoc[] - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(long aLimitProfileID) throws CustodianException {

		return getPendingLodgementList(null, aLimitProfileID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aLimitProfileID - the limit profile ID
	 * @return ICustodianDoc[] - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingLodgementList(aTrxContext, aLimitProfileID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aCustomerID) throws CustodianException {

		return getPendingLodgementListForNonBorrower(null, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingLodgementListForNonBorrower(aTrxContext, aCustomerID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException {
		return getPendingLodgementListForNonBorrower(null, aLimitProfileID, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingLodgementListForNonBorrower(aTrxContext, aLimitProfileID,
					aCustomerID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * To get the custodian document using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianDoc - the interface containing the custodian document
	 *         info
	 * @throws CustodianException
	 */
	public ICustodianDoc getDocByID(long aCustodianDocID) throws CustodianException {
		try {
			return getCustodianProxyManager().getDocByID(aCustodianDocID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(long aLimitProfileID) throws CustodianException {

		return getPendingWithdrawalList(null, aLimitProfileID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingWithdrawalList(aTrxContext, aLimitProfileID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aCustomerID) throws CustodianException {

		return getPendingWithdrawalListForNonBorrower(null, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingWithdrawalListForNonBorrower(aTrxContext, aCustomerID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException {
		return getPendingWithdrawalListForNonBorrower(null, aLimitProfileID, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException {
		try {
			return getCustodianProxyManager().getPendingWithdrawalListForNonBorrower(aTrxContext, aLimitProfileID,
					aCustomerID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * To get the custodian document trx using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianTrxValue - the interface containing the custodian trx
	 *         document info
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getTrxCustodianDoc(long aCustodianDocID) throws CustodianException {
		try {
			return getCustodianProxyManager().getTrxCustodianDoc(aCustodianDocID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian doc transaction object based on the transaction ID The
	 * object contains the before/after image depending on the indicator
	 * @param aTransactionID - String
	 * @return ICustodianTrxValue - the transaction object for the custodian
	 *         document
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getDocByTrxID(String aTransactionID) throws CustodianException {
		try {
			return getCustodianProxyManager().getDocByTrxID(aTransactionID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	public HashMap getDocListWithOwnerInfo(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			return getCustodianProxyManager().getDocListWithOwnerInfo(aCustodianSearchCriteria);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Get the list of custodian document that satisfy the criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the search result containing the list of custodian
	 *         doc
	 * @throws CustodianException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			return getCustodianProxyManager().getDocList(aCustodianSearchCriteria);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws CustodianException {
		try {
			return getCustodianProxyManager().getCheckEnvelopeBarcodeExist(limitprofile, envBarcode);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws CustodianException {
		try {
			return getCustodianProxyManager().getCheckDocItemBarcodeExist(docItemBarcode, checkListItemRefID);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws CustodianException {
		try {
			return getCustodianProxyManager().getSecEnvItemLoc(docItemBarcode);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * To get the remote handler for the custodian proxy manager
	 * @return SBCustodianProxyManager - the remote handler for the custodian
	 *         proxy manager
	 */
	private SBCustodianProxyManager getCustodianProxyManager() {
		SBCustodianProxyManager proxymgr = (SBCustodianProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTODIAN_PROXY_JNDI, SBCustodianProxyManagerHome.class.getName());
		return proxymgr;
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzTempUpliftDocMaker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker authz temp uplift of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzTempUpliftDocMaker(anITrxContext, anICustodianTrxValue,
					anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzTempUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzTempUpliftChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzPermUpliftDocMaker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker authz perm uplift of custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be perm
	 *         uplift authz
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzPermUpliftDocMaker(anITrxContext, anICustodianTrxValue,
					anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzPermUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzPermUpliftChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzRelodgeDocMaker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager()
					.authzRelodgeDocMaker(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Checker approves the authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue authzRelodgeChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().authzRelodgeChecker(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue rejectCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().rejectCustodian(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue cnclCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().cnclCustodian(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().editCustodian(anITrxContext, anICustodianTrxValue);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * maker edit the rejected custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is edited
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			anICustodianTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
			return getCustodianProxyManager().editCustodian(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		}
		catch (CustodianException ex) {
			throw ex;
		}
		catch (RemoteException ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - OBCustAuthorize[]
	 * @throws CustodianException if errors
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException {
		try {
			getCustodianProxyManager().persistPrintAuthzDetails(custAuthz);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Persist custodian print authorization details...
	 * @param customer - ICMSCustomer
	 * @param checklistMap - HashMap with key as CheckListID and value as list
	 *        of CheckListItemRef for this checklist
	 * @return IMemo
	 * @throws CustodianException if errors
	 */
	public IMemo getPrintMemo(ICMSCustomer customer, HashMap checkListMap) throws CustodianException {
		try {
			return getCustodianProxyManager().getPrintMemo(customer, checkListMap);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc[] getCustodianDoc(long aCollateralID) throws CustodianException {
		try {
			return getCustodianProxyManager().getCustodianDoc(aCollateralID);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian doc by checklist ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist ID
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, FinderException {
		try {
			return getCustodianProxyManager().getCustodianDocByCheckList(aCheckListID);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	// new custodian methods
	/**
	 * Maker saves the custodian doc as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return the draft custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerSaveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		try {
			return getCustodianProxyManager().makerSaveCustodianDoc(ctx, trxVal);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker updates custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return newly updated custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerUpdateCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		try {
			return getCustodianProxyManager().makerUpdateCustodianDoc(ctx, trxVal);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Maker cancel rejected or draft custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return cancelled custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerCloseCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		try {
			return getCustodianProxyManager().makerCloseCustodianDoc(ctx, trxVal);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker approves custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerApproveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		try {
			return getCustodianProxyManager().checkerApproveCustodianDoc(ctx, trxVal);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Checker rejects custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerRejectCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		try {
			return getCustodianProxyManager().checkerRejectCustodianDoc(ctx, trxVal);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	public ICustodianTrxValue readCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException {
		try {
			return getCustodianProxyManager().readCustodianDoc(trxContext, value);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * To replace getDocListWithOwnerInfo Get the custodian trx value and
	 * related owner information.
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return HashMap - map containing the following key, value pair : <br>
	 *         1. "trxValue", ICustodianTrxValue <br>
	 *         2. ICMSConstant.CC_OWNER, CCCustodianInfo <br>
	 *         3. ICMSConstant.SEC_OWNER, CollateralCustodianInfo <br>
	 *         4. ICMSConstant.SUB_CATEGORY, String representing subcategory <br>
	 * @throws CustodianException
	 */
	public HashMap getDocWithOwnerInfo(CustodianSearchCriteria searchCriteria) throws CustodianException {
		try {
			return getCustodianProxyManager().getDocWithOwnerInfo(searchCriteria);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian trx value given a checklist ID.
	 * 
	 * @param checkListID - long
	 * @return ICustodianTrxValue
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getNewDocTrxValue(long checkListID) throws CustodianException {
		try {
			return getCustodianProxyManager().getNewDocTrxValue(checkListID);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws CustodianException
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws CustodianException {
		try {
			return getCustodianProxyManager().getTrxID(searchCriteria);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * update transaction
	 */
	public void updateTransaction(ITrxValue trxValue) throws CustodianException {
		try {
			getCustodianProxyManager().updateTransaction(trxValue);
		}
		catch (RemoteException e) {
			throw new CustodianException(e);
		}
	}
}
