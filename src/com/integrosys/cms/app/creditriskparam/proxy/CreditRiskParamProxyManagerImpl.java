/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamProxyManagerImpl
 *
 * Created on 9:44:57 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.proxy;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 9:44:57 AM
 */
public class CreditRiskParamProxyManagerImpl implements ICreditRiskParamProxy {

	// generic search function
	public SearchResult getSearchResultForCriteria(SearchCriteria criteria, CreditRiskParamType type)
			throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().getSearchResultForCriteria(criteria, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	// generic methods available for maker
	public ICMSTrxValue makerReadCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().makerReadCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue makerReadRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().makerReadRejectedCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue makerUpdateCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().makerUpdateCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue makerUpdateRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().makerUpdateRejectedCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue makerCloseRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().makerCloseRejectedCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	// generic methods
	public ICMSTrxValue checkerViewCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().checkerViewCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue checkerApproveCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().checkerApproveCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	public ICMSTrxValue checkerRejectCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException {
		try {
			return getSBCreditRiskParamProxyManager().checkerRejectCreditRiskParam(ctx, trxValue, trxOb, type);
		}
		catch (CreditRiskParamGroupException ex) {
			throw ex;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CreditRiskParamGroupException(e);
		}
	}

	private static SBCreditRiskParamProxyManager getSBCreditRiskParamProxyManager() {
		return (SBCreditRiskParamProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CREDIT_RISK_PARAM_PROXY_MANAGER_HOME, SBCreditRiskParamProxyManagerHome.class
						.getName());
	}

}
