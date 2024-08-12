/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/DigitalLibraryProxyImpl.java,v 1.7 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryEntryException;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryBusManager;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.digitalLibrary.trx.OBDigitalLibraryTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class DigitalLibraryProxyImpl implements IDigitalLibraryProxy {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IDigitalLibraryGroup
	 * @throws DigitalLibraryException on errors retrieving the bond feed
	 */

	private IDigitalLibraryBusManager digitalLibraryBusManager;

	private IDigitalLibraryBusManager stagingDigitalLibraryBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IDigitalLibraryBusManager getDigitalLibraryBusManager() {
		return digitalLibraryBusManager;
	}

	public void setDigitalLibraryBusManager(IDigitalLibraryBusManager digitalLibraryBusManager) {
		this.digitalLibraryBusManager = digitalLibraryBusManager;
	}

	public IDigitalLibraryBusManager getStagingDigitalLibraryBusManager() {
		return stagingDigitalLibraryBusManager;
	}

	public void setStagingDigitalLibraryBusManager(IDigitalLibraryBusManager stagingDigitalLibraryBusManager) {
		this.stagingDigitalLibraryBusManager = stagingDigitalLibraryBusManager;
	}

	protected IDigitalLibraryTrxValue formulateTrxValue(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_DIGITAL_LIBRARY_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws DigitalLibraryException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new DigitalLibraryException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new DigitalLibraryException(e);
		}
		catch (Exception ex) {
			throw new DigitalLibraryException(ex.toString());
		}
	}

	protected IDigitalLibraryTrxValue operate(IDigitalLibraryTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws DigitalLibraryException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IDigitalLibraryTrxValue) result.getTrxValue();
	}

	protected IDigitalLibraryTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IDigitalLibraryGroup aFeedGroup) {

		IDigitalLibraryTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBDigitalLibraryTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBDigitalLibraryTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IDigitalLibraryTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingDigitalLibraryGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IDigitalLibraryTrxValue getDigitalLibraryGroup(long groupID) throws DigitalLibraryException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IDigitalLibraryGroup getActualDigitalLibraryGroup() throws DigitalLibraryException {
		return getDigitalLibraryBusManager().getDigitalLibraryGroup(ICMSConstant.DIGITAL_LIBRARY_GROUP_TYPE);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	public IDigitalLibraryTrxValue checkerApproveDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aTrxValue) throws DigitalLibraryException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */

	public IDigitalLibraryTrxValue checkerRejectDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);
	}

	public IDigitalLibraryTrxValue getDigitalLibraryGroup() throws DigitalLibraryException {
		IDigitalLibraryBusManager mgr = getDigitalLibraryBusManager();
		IDigitalLibraryGroup group = mgr.getDigitalLibraryGroup(ICMSConstant.DIGITAL_LIBRARY_GROUP_TYPE);

		if (group == null) {
			DigitalLibraryException e = new DigitalLibraryException("Cannot find the bond index feed group.");
			e.setErrorCode(IDigitalLibraryProxy.NO_FEED_GROUP);
			throw e;
		}

		IDigitalLibraryTrxValue bb = new OBDigitalLibraryTrxValue();
		bb.setReferenceID(String.valueOf(group.getDigitalLibraryGroupID()));
		bb.setDigitalLibraryGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DIGITAL_LIBRARY_GROUP);
		return operate(bb, param);
	}

	/**
	 * Get the transaction value containing DigitalLibraryGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IDigitalLibraryTrxValue
	 */
	public IDigitalLibraryTrxValue getDigitalLibraryGroupByTrxID(long trxID) throws DigitalLibraryException {
		IDigitalLibraryTrxValue trxValue = new OBDigitalLibraryTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DIGITAL_LIBRARY_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the DigitalLibraryGroup
	 * object
	 * @param aFeedGroup - IDigitalLibraryGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 * @return IDigitalLibraryTrxValue the saved trxValue
	 */
	public IDigitalLibraryTrxValue makerUpdateDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aFeedGroupTrxValue, IDigitalLibraryGroup aFeedGroup) throws DigitalLibraryException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DIGITAL_LIBRARY_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**  
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the DigitalLibraryGroup
	 * object
	 * @param aFeedGroup - IDigitalLibraryGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public IDigitalLibraryTrxValue makerSubmitDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aFeedGroupTrxValue, IDigitalLibraryGroup aFeedGroup) throws DigitalLibraryException {

		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_DIGITAL_LIBRARY_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws DigitalLibraryException
	 */
	public IDigitalLibraryTrxValue makerSubmitRejectedDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aTrxValue) throws DigitalLibraryException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateDigitalLibraryGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	public IDigitalLibraryTrxValue makerUpdateRejectedDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aTrxValue) throws DigitalLibraryException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a DigitalLibraryGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	public IDigitalLibraryTrxValue makerCloseRejectedDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aTrxValue) throws DigitalLibraryException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a DigitalLibraryGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	public IDigitalLibraryTrxValue makerCloseDraftDigitalLibraryGroup(ITrxContext anITrxContext,
			IDigitalLibraryTrxValue aTrxValue) throws DigitalLibraryException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_DIGITAL_LIBRARY_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws DigitalLibraryEntryException
	 */
	public IDigitalLibraryEntry getDigitalLibraryEntryByRic(String ric) throws DigitalLibraryEntryException {
		return getDigitalLibraryBusManager().getDigitalLibraryEntryByRic(ric);
	}
}
