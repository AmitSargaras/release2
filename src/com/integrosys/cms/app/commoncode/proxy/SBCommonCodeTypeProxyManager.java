package com.integrosys.cms.app.commoncode.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.trx.ICommonCodeTypeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface SBCommonCodeTypeProxyManager extends EJBObject {
	public ICommonCodeTypeTrxValue getCategoryByTrxId(String transactionId) throws CommonCodeTypeException,
			RemoteException;

	public ICommonCodeTypeTrxValue makerCreateCategory(ITrxContext anITrxContext, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException, RemoteException;

	public ICommonCodeTypeTrxValue makerUpdateCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue aCommonCodeTypeTrxValue, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException, RemoteException;

	public ICommonCodeTypeTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException, RemoteException;

	public ICommonCodeTypeTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException, RemoteException;

	public ICommonCodeTypeTrxValue makerEditRejectedCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException, RemoteException;

	public ICommonCodeTypeTrxValue makerCancelUpdateCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException, RemoteException;

}
