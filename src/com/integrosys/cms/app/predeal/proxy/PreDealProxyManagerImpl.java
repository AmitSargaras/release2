/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealProxyImpl
 *
 * Created on 5:31:12 PM
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
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.IEarMarkGroup;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.trx.IPreDealTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:31:12 PM
 */
public class PreDealProxyManagerImpl implements IPreDealProxy {

   

    private VelocityEngine velocityEngine;

	private SBPreDealProxyManager preDealProxyManager;

    public Collection searchByCounterName(String name) throws PreDealException {
		try {
			return preDealProxyManager.searchByCounterName(name);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public Collection searchByRIC(String ric) throws PreDealException {
		try {
			return preDealProxyManager.searchByRIC(ric);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public Collection searchByStockCode(String stockCode) throws PreDealException {
		try {
			return preDealProxyManager.searchByStockCode(stockCode);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public Collection searchByIsinCode(String isinCode) throws PreDealException {
		try {
			return preDealProxyManager.searchByIsinCode(isinCode);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public PreDealSearchRecord searchByFeedId(String feedId) throws PreDealException {
		try {
			return preDealProxyManager.searchByFeedId(feedId);
		}
		catch (PreDealException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue checkerApprove(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException {
		try {
			return preDealProxyManager.checkerApprove(ctx, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue checkerReject(ITrxContext ctx, IPreDealTrxValue trxValue) throws PreDealException {
		try {
			return preDealProxyManager.checkerReject(ctx, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerCreateNewEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException {
		try {
			return preDealProxyManager.makerCreateNewEar(ctx, trxValue, data);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerUpdateEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data, String updateType)
			throws PreDealException {
		try {
			return preDealProxyManager.makerUpdateEar(ctx, trxValue, data, updateType);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerUpdateRejectedEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException {
		try {
			return preDealProxyManager.makerUpdateRejectedEar(ctx, trxValue, data);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue makerCloseEar(ITrxContext ctx, IPreDealTrxValue trxValue, IPreDeal data)
			throws PreDealException {
		try {
			return preDealProxyManager.makerCloseEar(ctx, trxValue, data);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByTrxID(String trxId) throws PreDealException {
		try {
			return preDealProxyManager.getEarByTrxID(trxId);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByEarMarkId(String earMarkId) throws PreDealException {
		try {
			return  preDealProxyManager.getEarByEarMarkId(earMarkId);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public ICMSTrxValue getEarByFeedId(String feedId) throws PreDealException {
		try {
			return  preDealProxyManager.getEarByFeedId(feedId);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public SearchResult viewEarGroup(SearchCriteria criteria) throws PreDealException {
		try {
			return  preDealProxyManager.viewEarGroup(criteria);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public SearchResult viewEarMark(SearchCriteria criteria) throws PreDealException {
		try {
			return  preDealProxyManager.viewEarMark(criteria);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystem, long feedId) throws PreDealException {

		try {
			return  preDealProxyManager.getEarMarkGroupBySourceAndFeedId(sourceSystem, feedId);

		}
		catch (Exception e) {
			e.printStackTrace();

			throw new PreDealException(e);
		}
	}

	private SBPreDealProxyManager getSbPreDealProxyManager() {
		SBPreDealProxyManager proxymgr = (SBPreDealProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PRE_DEAL_PROXY_JNDI, SBPreDealProxyManagerHome.class.getName());

		if (proxymgr == null) {
			DefaultLogger.debug(this, "Proxy Bean is null");
		}

		return proxymgr;
	}


    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public SBPreDealProxyManager getPreDealProxyManager() {
        return preDealProxyManager;
    }

    public void setPreDealProxyManager(SBPreDealProxyManager preDealProxyManager) {
        this.preDealProxyManager = preDealProxyManager;
    }
}