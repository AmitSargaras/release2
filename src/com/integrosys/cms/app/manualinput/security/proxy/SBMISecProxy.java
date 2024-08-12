/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.MILeSearchCriteria;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMISecProxy extends EJBObject {
	public ICollateralTrxValue searchCollateralByColId(String colId) throws CollateralException, RemoteException;

	public ICollateralTrxValue searchCollateralByTrxId(String trxId) throws CollateralException, RemoteException;

	public ICMSTrxResult createCollateralTrx(ITrxContext ctx, ICollateralTrxValue value, boolean isSave)
			throws CollateralException, RemoteException;

	public ICMSTrxResult makerUpdateCollateralTrx(ITrxContext ctx, ICollateralTrxValue value, boolean isSave)
			throws CollateralException, RemoteException;

	public ICMSTrxResult makerCloseCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException, RemoteException;

	public ICMSTrxResult checkerRejectCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException, RemoteException;

	public ICMSTrxResult checkerApproveCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException, RemoteException;

	public List searchCustomerForPlgLink(MILeSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public ICollateralSubType[] getCollateralSubTypesByTypeCode(String typeCode) throws SearchDAOException,
			RemoteException;

	public ICMSTrxResult makerDirectCreateCollateralTrx(ITrxContext ctx, ICollateralTrxValue value)
			throws CollateralException, RemoteException;
	
	public List getCollateralCodeBySubTypes(String typeCode) throws SearchDAOException,
	RemoteException;

	/*public ICountry[] getListAllCountry() throws SearchDAOException,
	RemoteException;
	
	public ISystemBankBranch[] getListAllSystemBankBranch(String country) throws SearchDAOException,
	RemoteException;
	
	public IForexFeedEntry[] getCurrencyList() throws SearchDAOException,
	RemoteException;*/
}
