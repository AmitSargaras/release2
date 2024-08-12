/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCreditRiskParamProxyManager
 *
 * Created on 10:59:02 AM
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

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 10:59:02 AM
 */
public interface SBCreditRiskParamProxyManager extends EJBObject {
	// generic search function
	public SearchResult getSearchResultForCriteria(SearchCriteria criteria, CreditRiskParamType type)
			throws CreditRiskParamGroupException, RemoteException;

	// generic methods available for maker
	public ICMSTrxValue makerReadCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException, RemoteException;

	public ICMSTrxValue makerReadRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException,
			RemoteException;

	public ICMSTrxValue makerUpdateCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException, RemoteException;

	public ICMSTrxValue makerUpdateRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException,
			RemoteException;

	public ICMSTrxValue makerCloseRejectedCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException,
			RemoteException;

	// generic methods
	public ICMSTrxValue checkerViewCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue, ICreditRiskParamGroup trxOb,
			CreditRiskParamType type) throws CreditRiskParamGroupException, RemoteException;

	public ICMSTrxValue checkerRejectCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException,
			RemoteException;

	public ICMSTrxValue checkerApproveCreditRiskParam(ITrxContext ctx, ICMSTrxValue trxValue,
			ICreditRiskParamGroup trxOb, CreditRiskParamType type) throws CreditRiskParamGroupException,
			RemoteException;

}
