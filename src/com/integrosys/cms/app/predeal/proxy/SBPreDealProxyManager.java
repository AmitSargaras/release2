/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealProxyManager
 *
 * Created on 5:34:13 PM
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

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:34:13 PM
 */
public interface SBPreDealProxyManager extends EJBObject {
	public Collection searchByStockCode(String stockCode) throws RemoteException;

	public Collection searchByIsinCode(String isinCode) throws RemoteException;

	public Collection searchByRIC(String ric) throws RemoteException;

	public Collection searchByCounterName(String name) throws RemoteException;

	public PreDealSearchRecord searchByFeedId(String feedId) throws PreDealException, RemoteException;

	public SearchResult viewEarGroup(SearchCriteria criteria) throws RemoteException;

	public SearchResult viewEarMark(SearchCriteria criteria) throws RemoteException;

	public ICMSTrxValue getEarByTrxID(String trxId) throws PreDealException, RemoteException;

	public ICMSTrxValue getEarByEarMarkId(String earMarkId) throws PreDealException, RemoteException;

	public ICMSTrxValue getEarByFeedId(String feedId) throws PreDealException, RemoteException;

	public ICMSTrxValue makerCreateNewEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException;

	public ICMSTrxValue makerUpdateEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data, String updateType)
			throws PreDealException, RemoteException;

	public ICMSTrxValue makerUpdateRejectedEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException;

	public ICMSTrxValue makerCloseEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException, RemoteException;

	public ICMSTrxValue checkerApprove(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException,
			RemoteException;

	public ICMSTrxValue checkerReject(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException,
			RemoteException;

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystem, long feedId) throws PreDealException,
			RemoteException;
}
