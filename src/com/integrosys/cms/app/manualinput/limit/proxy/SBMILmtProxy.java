/*
 * Created on 2007-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.LmtColSearchCriteria;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMILmtProxy extends EJBObject {
	public ILimitTrxValue searchLimitByLmtId(String lmtId) throws LimitException, RemoteException;

	public ILimitTrxValue searchLimitByTrxId(String trxId) throws LimitException, RemoteException;

	public ICMSTrxResult createLimitTrx(ITrxContext ctx, ILimitTrxValue value, boolean isSave) throws LimitException,
			RemoteException;

	public ICMSTrxResult makerUpdateLmtTrx(ITrxContext ctx, ILimitTrxValue value, boolean isSave)
			throws LimitException, RemoteException;

	public ICMSTrxResult makerCloseLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException, RemoteException;

	public ICMSTrxResult makerDeleteLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException,
			RemoteException;

	public ICMSTrxResult checkerRejectLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException,
			RemoteException;

	public ICMSTrxResult checkerApproveLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException,
			RemoteException;

	public SearchResult searchCollateralByIdSubtype(LmtColSearchCriteria criteria) throws SearchDAOException,
			RemoteException;

	public List getSecSubtypeList() throws SearchDAOException, RemoteException;

	public boolean checkDuplicateLmt(String lmtId) throws LimitException, RemoteException;

	public Amount getNetoutStandingForAccount(String accountId, String limitCurrency) throws LimitException,
			RemoteException;

	public List getLimitSummaryListByAA(String aaId) throws LimitException, RemoteException;
	
	public List getLimitSummaryListByCustID(String aaId) throws LimitException, RemoteException;   //Shiv 280911
	
	public List getLimitSummaryListByCustID(String aaId, String facilityId) throws LimitException, RemoteException;   //Shiv 101011
	
	public List getLimitTranchListByFacilityFor(String facilityId, String facilityFor) throws LimitException, RemoteException;   //Shiv 280911
	
	public String[] getLimitTranchListByCustID(String facilityId, String serialNo) throws LimitException, RemoteException;   //Shiv 280911
	
	public List getLimitListByFacilityFor(String aaId, String facilityId) throws LimitException, RemoteException;   //Shiv 280911
	
	public String getRemainingPropertyValue() throws LimitException, RemoteException;  //Shiv 251111

	public List getOuterLimitList(String aaId) throws LimitException, RemoteException;

	public String getAgreementByAA(String aaId) throws SearchDAOException, RemoteException;

	public String getFacilityGroupByAA(String aaId) throws SearchDAOException, RemoteException;
	
	public List getFacNameList(String facCat) throws LimitException, RemoteException;
	
	public List getFacDetailList(String facDetail, String custID) throws LimitException, RemoteException;
	
	public List getFacDetailListRest(String facDetail, String custID) throws LimitException, RemoteException;
	
	public List getRelationShipMngrList() throws LimitException, RemoteException;
	
	
	
	public List getSubFacNameList(String profileID) throws LimitException, RemoteException;
	
	public List getSubPartyNameList(String profileID) throws LimitException, RemoteException;
	
	public List getSubSecurityList(long profileID) throws LimitException, RemoteException;
	
	public List getSystemID(String system, String custID) throws LimitException, RemoteException;
	
	public List getVendorDtls(String custID) throws LimitException, RemoteException;
	
	public String getBorrowerScmFlag (String custID ) throws LimitException, RemoteException;
	
	public double getReleaseAmountForParty (String custID ) throws LimitException, RemoteException;

	
	public List getLiabilityIDList(String profileID) throws LimitException, RemoteException;  //Shiv 151212
	
	public ICMSTrxResult createSubmitFacility(ITrxContext ctx, ILimitTrxValue value , boolean isDelete) throws LimitException,
	RemoteException;
	
	public String getFacilityName(String lmtId) throws SearchDAOException,RemoteException;
	
//	public void updateSanctionedLimitToZero(String camId) throws RemoteException,LimitException;
	//added by santosh UB CR
	public List getRejectedLimitSummaryListByAA() throws LimitException, RemoteException;
	public List getRejectedLimitSummaryListByAA(String searchCriteria) throws LimitException, RemoteException;
	//end santosh

	public List getFacilityNameByAAId(String custid) throws LimitException, RemoteException;
}
