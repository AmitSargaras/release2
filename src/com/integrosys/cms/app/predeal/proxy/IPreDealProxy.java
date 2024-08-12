/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * IPreDealProxy
 *
 * Created on 5:14:14 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.proxy;

import java.util.Collection;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.IEarMarkGroup;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.trx.IPreDealTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:14:14 PM
 */
public interface IPreDealProxy {
	public Collection searchByStockCode(String stockCode) throws PreDealException;

	public Collection searchByIsinCode(String isinCode) throws PreDealException;

	public Collection searchByRIC(String ric) throws PreDealException;

	public Collection searchByCounterName(String name) throws PreDealException;

	public PreDealSearchRecord searchByFeedId(String feedId) throws PreDealException;

	public SearchResult viewEarGroup(SearchCriteria criteria) throws PreDealException;

	public SearchResult viewEarMark(SearchCriteria criteria) throws PreDealException;

	public ICMSTrxValue getEarByTrxID(String trxId) throws PreDealException;

	public ICMSTrxValue getEarByEarMarkId(String earMarkId) throws PreDealException;

	public ICMSTrxValue getEarByFeedId(String feedId) throws PreDealException;

	public ICMSTrxValue makerCreateNewEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException;

	public ICMSTrxValue makerUpdateEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data, String updateType)
			throws PreDealException;

	public ICMSTrxValue makerUpdateRejectedEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException;

	public ICMSTrxValue makerCloseEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException;

	public ICMSTrxValue checkerApprove(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException;

	public ICMSTrxValue checkerReject(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException;

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystem, long feedId) throws PreDealException;
}
