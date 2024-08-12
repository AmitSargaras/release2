/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesManagerImpl.java
 *
 * Created on February 6, 2007, 11:09 AM
 *
 * Purpose: the procy manager class with direct the method call to other classes
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
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntriesException;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * 
 * @author Eric
 */
public class CommonCodeEntriesProxyManagerImpl implements ICommonCodeEntriesProxy {

	/** Creates a new instance of CommonCodeEntriesManagerImpl */
	public CommonCodeEntriesProxyManagerImpl() {
	}

	public ICommonCodeEntriesTrxValue getCategoryTrxId(String transactionId) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.getCategoryTrxId(transactionId);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.makerCancelUpdate(anITrxContext, commonCodeTypeTrxVal);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.checkerRejectCategory(anITrxContext, commonCodeTypeTrxVal);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.checkerApproveCategory(anITrxContext, commonCodeTypeTrxVal);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue getCategoryType(int categoryType) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.getCategoryType(categoryType);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public SearchResult getCategoryList(SearchCriteria aCriteria) throws CommonCodeEntriesException, SearchDAOException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.getCategoryList(aCriteria);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue makerUpdateCategory(ITrxContext ctx,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.makerUpdateCategory(ctx, commonCodeTypeTrxVal, obCommonCodeType);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.makerEditRejectedTrx(anITrxContext, commonCodeTypeTrxVal, obCommonCodeType);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	public ICommonCodeEntriesTrxValue getCategoryId(long categoryId) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.getCategoryId(categoryId);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}
	
	public ICommonCodeEntriesTrxValue getEntryValues(long categoryId,String desc,String value) throws CommonCodeEntriesException {
		try {
			SBCommonCodeEntriesProxyManager mananger = getSBCommonCodeEntriesProxyManager();
			return mananger.getEntryValues(categoryId, desc, value);
		}
		catch (Exception ex) {
			throw new CommonCodeEntriesException("",ex);
		}
	}

	private SBCommonCodeEntriesProxyManager getSBCommonCodeEntriesProxyManager() {
		SBCommonCodeEntriesProxyManager proxymgr = (SBCommonCodeEntriesProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMON_CODE_ENTRIES_JNDI, SBCommonCodeEntriesProxyManagerHome.class.getName());
		return proxymgr;
	}

}
