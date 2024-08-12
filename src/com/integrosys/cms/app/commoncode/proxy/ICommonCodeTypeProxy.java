package com.integrosys.cms.app.commoncode.proxy;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchCriteria;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.trx.ICommonCodeTypeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface ICommonCodeTypeProxy {

	public SearchResult getCategoryList(CommonCodeTypeSearchCriteria aCriteria) throws CommonCodeTypeException,
			SearchDAOException;

	public ICommonCodeTypeTrxValue getCategoryType(int categoryType) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue getCategoryTrxId(String transactionId) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue getCategoryId(long categoryId) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue makerCreateCategory(ITrxContext ctx, ICommonCodeType obCommonCodeType)
			throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue makerUpdateCategory(ITrxContext ctx, ICommonCodeTypeTrxValue commonCodeTypeTrxVal,
			ICommonCodeType obCommonCodeType) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue commonCodeTypeTrxVal) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue commonCodeTypeTrxVal) throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue commonCodeTypeTrxVal, ICommonCodeType obCommonCodeType)
			throws CommonCodeTypeException;

	public ICommonCodeTypeTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue commonCodeTypeTrxVal) throws CommonCodeTypeException;

}
