/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/IDocumentLocationProxyManager.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

//app
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchCriteria;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchResult;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSummary;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a document location
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */
public interface IDocumentLocationProxyManager {
	/**
	 * Check if there is any pending CC document location trx
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DocumentLocationException on errors
	 */
	public boolean hasPendingCCDocumentLocationTrx(long aLimitProfileID, String aCategory, long aCustomerID)
			throws DocumentLocationException;

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws DocumentLocationException;

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list and the whether collaboration is allowed or not
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws DocumentLocationException;

	/**
	 * Get the cc document location trx value using the limitprofile id,
	 * customer type, customer id and the country
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCDocumentLocationTrxValue - the trx value of the CC document
	 *         location
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationTrxValue(long aLimitProfileID, String aCategory,
			long aCustomerID) throws DocumentLocationException;

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws DocumentLocationException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException;

	/**
	 * Get the CC document location trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx value
	 * @throws DocumentLocationException
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws DocumentLocationException;

	/**
	 * Maker create the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocaation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the generates CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException;

	/**
	 * Maker update the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the generates CC Document Location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException;

	/**
	 * Checker approve the document location
	 * @param anITrxContext of ITrxContext type
	 * @param ICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the generated CC Document Location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException;

	/**
	 * Checker reject the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC Document Location trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException;

	/**
	 * Maker edit the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return ICCDocumentLocationTrxValue - the CC Document Location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException;

	/**
	 * Make close the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC Document Location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException;

	/**
	 * Get the list of CC Document Location that satisfy the search criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return CCDocumentLocationSearchResult[] - the list of cc document
	 *         location result that satisfy the criteria
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException;

}
