/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/unittrust/UnitTrustFeedProxyImpl.java,v 1.7 2005/07/29 03:29:21 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.unittrust;

import java.rmi.RemoteException;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedBusManager;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedBusManager;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedEntryException;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.OBUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.UnitTrustFeedGroupTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class UnitTrustFeedProxyImpl implements IUnitTrustFeedProxy {
	/**
	 * Get actual unit price stock feed price group.
	 * 
	 * @param subType unit trust country
	 * @return unit trust feed group
	 * @throws UnitTrustFeedGroupException on errors getting the unit trust feed
	 * group
	 */
	public IUnitTrustFeedBusManager unitTrustFeedBusManager;

	public IUnitTrustFeedBusManager stagingunitTrustFeedBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public IUnitTrustFeedBusManager getUnitTrustFeedBusManager() {
		return unitTrustFeedBusManager;
	}

	public void setUnitTrustFeedBusManager(IUnitTrustFeedBusManager unitTrustFeedBusManager) {
		this.unitTrustFeedBusManager = unitTrustFeedBusManager;
	}

	public IUnitTrustFeedBusManager getStagingunitTrustFeedBusManager() {
		return stagingunitTrustFeedBusManager;
	}

	public void setStagingunitTrustFeedBusManager(IUnitTrustFeedBusManager stagingunitTrustFeedBusManager) {
		this.stagingunitTrustFeedBusManager = stagingunitTrustFeedBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	protected IUnitTrustFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws UnitTrustFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new UnitTrustFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new UnitTrustFeedGroupException(e);
		}
		catch (Exception ex) {
			throw new UnitTrustFeedGroupException(ex.toString());
		}
	}

	protected IUnitTrustFeedGroupTrxValue operate(IUnitTrustFeedGroupTrxValue aTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws UnitTrustFeedGroupException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IUnitTrustFeedGroupTrxValue) result.getTrxValue();
	}

	protected IUnitTrustFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IUnitTrustFeedGroup aFeedGroup) {

		IUnitTrustFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBUnitTrustFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBUnitTrustFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IUnitTrustFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingUnitTrustFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroup(long groupID) throws UnitTrustFeedGroupException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IUnitTrustFeedGroup getActualUnitTrustFeedGroup(String subType) throws UnitTrustFeedGroupException {
		return getUnitTrustFeedBusManager().getUnitTrustFeedGroup(ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE, subType);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	public IUnitTrustFeedGroupTrxValue checkerApproveUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */

	public IUnitTrustFeedGroupTrxValue checkerRejectUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroup(String subType) throws UnitTrustFeedGroupException {

		IUnitTrustFeedBusManager mgr = getUnitTrustFeedBusManager();
		IUnitTrustFeedGroup group = mgr.getUnitTrustFeedGroup(ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE, subType);

		if (group == null) {
			UnitTrustFeedGroupException e = new UnitTrustFeedGroupException("Cannot find the unit trust feed group.");
			e.setErrorCode(IUnitTrustFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IUnitTrustFeedGroupTrxValue uu = new OBUnitTrustFeedGroupTrxValue();
		uu.setReferenceID(String.valueOf(group.getUnitTrustFeedGroupID()));
		uu.setUnitTrustFeedGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_UNIT_TRUST_FEED_GROUP);
		return operate(uu, param);
	}

	/**
	 * Get the transaction value containing UnitTrustFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IUnitTrustFeedGroupTrxValue
	 */
	public IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroupByTrxID(long trxID) throws UnitTrustFeedGroupException {

		IUnitTrustFeedGroupTrxValue trxValue = new OBUnitTrustFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_UNIT_TRUST_FEED_GROUP);
		return operate(trxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the UnitTrustFeedGroup
	 * object
	 * @param aFeedGroup - IUnitTrustFeedGroup, this could have been passed in
	 * the trx value, but the intention is that the caller should not have
	 * modified the trxValue, as the caller does not need to know about staging
	 * settings et al.
	 * @return IUnitTrustFeedGroupTrxValue the saved trxValue
	 */
	public IUnitTrustFeedGroupTrxValue makerUpdateUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aFeedGroupTrxValue, IUnitTrustFeedGroup aFeedGroup)
			throws UnitTrustFeedGroupException {

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_UNIT_TRUST_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);

	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the UnitTrustFeedGroup
	 * object
	 * @param aFeedGroup - IUnitTrustFeedGroup, this could have been passed in
	 * the trx value, but the intention is that the caller should not have
	 * modified the trxValue, as the caller does not need to know about staging
	 * settings et al.
	 */
	public IUnitTrustFeedGroupTrxValue makerSubmitUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aFeedGroupTrxValue, IUnitTrustFeedGroup aFeedGroup)
			throws UnitTrustFeedGroupException {

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_UNIT_TRUST_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);

	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws UnitTrustFeedGroupException
	 */
	public IUnitTrustFeedGroupTrxValue makerSubmitRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateUnitTrustFeedGroup except that
	 * it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	public IUnitTrustFeedGroupTrxValue makerUpdateRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * Cancels an initiated transaction on a UnitTrustFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	public IUnitTrustFeedGroupTrxValue makerCloseRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * Cancels an initiated transaction on a UnitTrustFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	public IUnitTrustFeedGroupTrxValue makerCloseDraftUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException {

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_UNIT_TRUST_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws UnitTrustFeedEntryException
	 */
	public IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String ric) throws UnitTrustFeedEntryException {

		return getUnitTrustFeedBusManager().getUnitTrustFeedEntryByRic(ric);
	}
}
