package com.integrosys.cms.app.commoncode.proxy;

import java.rmi.RemoteException;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchCriteria;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.bus.OBCommonCodeType;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManager;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManagerHome;
import com.integrosys.cms.app.commoncode.trx.ICommonCodeTypeTrxValue;
import com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeManagerImpl implements ICommonCodeTypeProxy {

	public SearchResult getCategoryList(CommonCodeTypeSearchCriteria aCriteria) throws CommonCodeTypeException,
			SearchDAOException {
		try {
			return getCommonCodeTypeBusManager().getCategoryListByType(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in getCategoryList: " + ex.toString());
		}
	}

	public ICommonCodeTypeTrxValue getCategoryType(int categoryType) throws CommonCodeTypeException {
		OBCommonCodeTypeTrxValue obCommonCodeTypeTrxValue = new OBCommonCodeTypeTrxValue();

		try {
			List categoryList = getCommonCodeTypeBusManager().getCategoryListByType(categoryType);
			if ((categoryList != null) && !categoryList.isEmpty()) {
				OBCommonCodeType[] obCommonCodeType = new OBCommonCodeType[categoryList.size()];
				Object[] objects = (Object[]) categoryList.toArray();
				for (int i = 0; i < categoryList.size(); i++) {
					obCommonCodeType[i] = (OBCommonCodeType) objects[i];
				}
				obCommonCodeTypeTrxValue.setCommonCodeTypeList(obCommonCodeType);
			}
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in getCategoryType: " + ex.toString());
		}

		return obCommonCodeTypeTrxValue;
	}

	public ICommonCodeTypeTrxValue getCategoryTrxId(String transactionId) throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().getCategoryByTrxId(transactionId);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in getCategoryTrxId: " + ex.toString());
		}

	}

	public ICommonCodeTypeTrxValue getCategoryId(long categoryId) throws CommonCodeTypeException {

		OBCommonCodeTypeTrxValue obCommonCodeTypeTrxValue = new OBCommonCodeTypeTrxValue();

		try {
			OBCommonCodeType obCommonCodeType = (OBCommonCodeType) getCommonCodeTypeBusManager().getCategoryById(
					categoryId);
			if (obCommonCodeType != null) {
				obCommonCodeTypeTrxValue.setCommonCodeType(obCommonCodeType);
			}
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in getCategoryType: " + ex.toString());
		}

		return obCommonCodeTypeTrxValue;
	}

	public ICommonCodeTypeTrxValue makerCreateCategory(ITrxContext anITrxContext, ICommonCodeType obCommonCodeType)
			throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().makerCreateCategory(anITrxContext, obCommonCodeType);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in makerCreateCategory: " + ex.toString());
		}
	}

	public ICommonCodeTypeTrxValue makerUpdateCategory(ITrxContext ctx, ICommonCodeTypeTrxValue commonCodeTypeTrxVal,
			ICommonCodeType obCommonCodeType) throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().makerUpdateCategory(ctx, commonCodeTypeTrxVal, obCommonCodeType);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in makerUpdateCategory: " + ex.toString());
		}
	}

	public ICommonCodeTypeTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().checkerApproveCategory(anITrxContext, anICommonCodeTypeTrxValue);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in checkerApproveDocItem: " + ex.toString());
		}

	}

	public ICommonCodeTypeTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().checkerRejectCategory(anITrxContext, anICommonCodeTypeTrxValue);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in checkerApproveDocItem: " + ex.toString());
		}

	}

	public ICommonCodeTypeTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue, ICommonCodeType obCommonCodeType)
			throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().makerEditRejectedCategory(anITrxContext, anICommonCodeTypeTrxValue,
					obCommonCodeType);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in makerEditRejectedTrx: " + ex.toString());
		}

	}

	public ICommonCodeTypeTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue commonCodeTypeTrxVal) throws CommonCodeTypeException {
		try {
			return getCommonCodeTypeProxyManager().makerCancelUpdateCategory(anITrxContext, commonCodeTypeTrxVal);
		}
		catch (RemoteException ex) {
			throw new CommonCodeTypeException("RemoteException in makerEditRejectedTrx: " + ex.toString());
		}

	}

	/**
	 * To get the remote handler for the commoncode type proxy manager
	 * @return SBCommonCodeTypeProxyManager - the remote handler for the common
	 *         code type proxy manager
	 */
	private SBCommonCodeTypeProxyManager getCommonCodeTypeProxyManager() {
		SBCommonCodeTypeProxyManager proxymgr = (SBCommonCodeTypeProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMON_CODE_TYPE_PROXY_JNDI, SBCommonCodeTypeProxyManagerHome.class.getName());
		return proxymgr;
	}

	/**
	 * To get the handler for the commoncodetype manager
	 * @return SBCommonCodeTypeProxyManager - the remote handler for the common
	 *         code type proxy manager
	 */
	private SBCommonCodeTypeBusManager getCommonCodeTypeBusManager() {
		SBCommonCodeTypeBusManager proxymgr = (SBCommonCodeTypeBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMON_CODE_TYPE_BUS_JNDI, SBCommonCodeTypeBusManagerHome.class.getName());
		return proxymgr;
	}
}
