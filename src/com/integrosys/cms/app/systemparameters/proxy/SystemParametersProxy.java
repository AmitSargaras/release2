/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/systemparameters/proxy/SystemParametersProxy.java,v 1.1 2003/08/13 14:21:44 dayanand Exp $
 */

package com.integrosys.cms.app.systemparameters.proxy;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.TrxOperationHelper;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.commondata.app.bus.BusinessParameterGroupAlreadyExistsException;
import com.integrosys.component.commondata.app.bus.BusinessParameterGroupSearchCriteria;
import com.integrosys.component.commondata.app.bus.BusinessParameterGroupSearchResult;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;
import com.integrosys.component.commondata.app.bus.CommonDataSearchException;
import com.integrosys.component.commondata.app.bus.IBusinessParameterGroup;
import com.integrosys.component.commondata.app.bus.NoSuchBusinessParameterGroupExistsException;
import com.integrosys.component.commondata.app.proxy.CommonDataProxyFactory;
import com.integrosys.component.commondata.app.proxy.IBusinessParameterGroupProxy;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * SystemParametersProxy
 * @author $Author: dayanand $
 * @version $
 * @since Aug 12, 2003 1:19:51 PM$
 */
public class SystemParametersProxy implements IBusinessParameterGroupProxy {
	public SystemParametersProxy() {
	}

	// ******* Methods for maker/checker create and maintenance of user
	// **********
	public ICompTrxResult makerCreate(IBusinessParameterGroupTrxValue trxValue, IBusinessParameterGroup bpGroup)
			throws BusinessParameterGroupAlreadyExistsException, CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveCreate(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectCreate(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelCreate(IBusinessParameterGroupTrxValue trxValue) throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerUpdate(IBusinessParameterGroupTrxValue trx, IBusinessParameterGroup bpGroup)
			throws NoSuchBusinessParameterGroupExistsException, CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveUpdate(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectUpdate(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelUpdate(IBusinessParameterGroupTrxValue trxValue) throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerDelete(IBusinessParameterGroupTrxValue trx)
			throws NoSuchBusinessParameterGroupExistsException, CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveDelete(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectDelete(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelDelete(IBusinessParameterGroupTrxValue trxValue) throws CommonDataManagerException {
		throw new CommonDataManagerException(
				"Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	// non- maker/checker methods.
	public BusinessParameterGroupSearchResult searchBusinessParameterGroups(
			BusinessParameterGroupSearchCriteria criteria) throws CommonDataSearchException, CommonDataManagerException {
		return getProxy().searchBusinessParameterGroups(criteria);
	}

	public IBusinessParameterGroupTrxValue getBusinessParameterGroup(long trxID) throws CommonDataManagerException {
		return getProxy().getBusinessParameterGroup(trxID);
	}

	public IBusinessParameterGroupTrxValue getBusinessParameterGroupByGroupCode(String groupCode)
			throws CommonDataManagerException {
		return getProxy().getBusinessParameterGroupByGroupCode(groupCode);
	}

	public IBusinessParameterGroupTrxValue addBusinessParameterGroup(IBusinessParameterGroupTrxValue trxValue)
			throws BusinessParameterGroupAlreadyExistsException, CommonDataManagerException {
		return getProxy().addBusinessParameterGroup(trxValue);
	}

	public IBusinessParameterGroupTrxValue updateBusinessParameterGroup(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		return getProxy().updateBusinessParameterGroup(trxValue);
	}

	public IBusinessParameterGroupTrxValue deleteBusinessParameterGroup(IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		return getProxy().deleteBusinessParameterGroup(trxValue);
	}

	// maker/checker methods with TrxContext as parameter

	public ICompTrxResult makerCreate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue,
			IBusinessParameterGroup bpGroup) throws BusinessParameterGroupAlreadyExistsException,
			CommonDataManagerException {

		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerCreate(trxValue, bpGroup);
	}

	public ICompTrxResult checkerApproveCreate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerApproveCreate(trxValue);
	}

	public ICompTrxResult checkerRejectCreate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerRejectCreate(trxValue);
	}

	public ICompTrxResult makerCancelCreate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerCancelCreate(trxValue);
	}

	public ICompTrxResult makerUpdate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue,
			IBusinessParameterGroup bpGroup) throws NoSuchBusinessParameterGroupExistsException,
			CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerUpdate(trxValue, bpGroup);
	}

	public ICompTrxResult checkerApproveUpdate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerApproveUpdate(trxValue);
	}

	public ICompTrxResult checkerRejectUpdate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerRejectUpdate(trxValue);
	}

	public ICompTrxResult makerCancelUpdate(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerCancelUpdate(trxValue);
	}

	public ICompTrxResult makerDelete(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws NoSuchBusinessParameterGroupExistsException, CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerDelete(trxValue);
	}

	public ICompTrxResult checkerApproveDelete(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerApproveDelete(trxValue);
	}

	public ICompTrxResult checkerRejectDelete(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.checkerRejectDelete(trxValue);
	}

	public ICompTrxResult makerCancelDelete(ITrxContext trxContext, IBusinessParameterGroupTrxValue trxValue)
			throws CommonDataManagerException {
		trxValue = (IBusinessParameterGroupTrxValue) mapTrxValue(trxContext, trxValue);

		IBusinessParameterGroupProxy proxy = getProxy();

		return proxy.makerCancelDelete(trxValue);
	}

	private ITrxValue mapTrxValue(ITrxContext trxContext, ITrxValue value) throws CommonDataManagerException {
		try {
			value = TrxOperationHelper.mapTrxContext(trxContext, value);
			ICMSTrxValue trxValue = (ICMSTrxValue) value;
			trxValue.setTrxContext(trxContext);

			return trxValue;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			throw new CommonDataManagerException("Caught Exception! :" + e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommonDataManagerException("Caught Exception! :" + e);
		}
	}

	private IBusinessParameterGroupProxy getProxy() throws CommonDataManagerException {
		IBusinessParameterGroupProxy up = CommonDataProxyFactory.getBusinessParameterGroupProxy();
		if (null == up) {
			throw new CommonDataManagerException("SBCommonUserProxy is null!");
		}
		else {
			return up;
		}
	}
}
