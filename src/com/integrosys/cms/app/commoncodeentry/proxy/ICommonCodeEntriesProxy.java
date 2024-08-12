/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ICommonCodeEntriesProxy.java
 *
 * Created on February 6, 2007, 11:07 AM
 *
 * Purpose: the interfaces that defines the methods available for the MaintainCommonCodeParameters module
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.proxy;

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
public interface ICommonCodeEntriesProxy {
	public SearchResult getCategoryList(SearchCriteria aCriteria) throws CommonCodeEntriesException, SearchDAOException;

	public ICommonCodeEntriesTrxValue getCategoryType(int categoryType) throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue getCategoryTrxId(String transactionId) throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue getCategoryId(long categoryId) throws CommonCodeEntriesException;
	
	public ICommonCodeEntriesTrxValue getEntryValues(long categoryId,String desc,String value) throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue makerUpdateCategory(ITrxContext ctx,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException;

	public ICommonCodeEntriesTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException;
}
