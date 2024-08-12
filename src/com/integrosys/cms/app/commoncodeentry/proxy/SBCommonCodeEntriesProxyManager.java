/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCommonCodeEntriesProxyManager.java
 *
 * Created on February 6, 2007, 11:40 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntriesException;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * 
 * @author Eric
 */
public interface SBCommonCodeEntriesProxyManager extends EJBObject {
	public SearchResult getCategoryList(SearchCriteria aCriteria) throws CommonCodeEntriesException,
			SearchDAOException, RemoteException;

	public ICommonCodeEntriesTrxValue getCategoryType(int categoryType) throws CommonCodeEntriesException,
			RemoteException;

	public ICommonCodeEntriesTrxValue getCategoryTrxId(String transactionId) throws CommonCodeEntriesException,
			RemoteException;

	public ICommonCodeEntriesTrxValue getCategoryId(long categoryId) throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntriesTrxValue getEntryValues(long categoryId,String desc,String value) throws CommonCodeEntriesException, RemoteException;
	
	public ICommonCodeEntriesTrxValue makerUpdateCategory(ITrxContext ctx,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntriesTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntriesTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntriesTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException, RemoteException;

	public ICommonCodeEntriesTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException, RemoteException;

}
