/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/ApprovalMatrixProxyImpl.java,v 1.7 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixEntryException;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixBusManager;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.approvalmatrix.trx.OBApprovalMatrixTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class ApprovalMatrixProxyImpl implements IApprovalMatrixProxy {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IApprovalMatrixGroup
	 * @throws ApprovalMatrixException on errors retrieving the bond feed
	 */

	private IApprovalMatrixBusManager approvalMatrixBusManager;

	private IApprovalMatrixBusManager stagingApprovalMatrixBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IApprovalMatrixBusManager getApprovalMatrixBusManager() {
		return approvalMatrixBusManager;
	}

	public void setApprovalMatrixBusManager(IApprovalMatrixBusManager approvalMatrixBusManager) {
		this.approvalMatrixBusManager = approvalMatrixBusManager;
	}

	public IApprovalMatrixBusManager getStagingApprovalMatrixBusManager() {
		return stagingApprovalMatrixBusManager;
	}

	public void setStagingApprovalMatrixBusManager(IApprovalMatrixBusManager stagingApprovalMatrixBusManager) {
		this.stagingApprovalMatrixBusManager = stagingApprovalMatrixBusManager;
	}

	protected IApprovalMatrixTrxValue formulateTrxValue(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_APROVAL_MATRIX_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ApprovalMatrixException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new ApprovalMatrixException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new ApprovalMatrixException(e);
		}
		catch (Exception ex) {
			throw new ApprovalMatrixException(ex.toString());
		}
	}

	protected IApprovalMatrixTrxValue operate(IApprovalMatrixTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ApprovalMatrixException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IApprovalMatrixTrxValue) result.getTrxValue();
	}

	protected IApprovalMatrixTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IApprovalMatrixGroup aFeedGroup) {

		IApprovalMatrixTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBApprovalMatrixTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBApprovalMatrixTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IApprovalMatrixTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingApprovalMatrixGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IApprovalMatrixTrxValue getApprovalMatrixGroup(long groupID) throws ApprovalMatrixException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IApprovalMatrixGroup getActualApprovalMatrixGroup() throws ApprovalMatrixException {
		return getApprovalMatrixBusManager().getApprovalMatrixGroup(ICMSConstant.APROVAL_MATRIX_GROUP_TYPE);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	public IApprovalMatrixTrxValue checkerApproveApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aTrxValue) throws ApprovalMatrixException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */

	public IApprovalMatrixTrxValue checkerRejectApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);
	}

	public IApprovalMatrixTrxValue getApprovalMatrixGroup() throws ApprovalMatrixException {
		IApprovalMatrixBusManager mgr = getApprovalMatrixBusManager();
		IApprovalMatrixGroup group = mgr.getApprovalMatrixGroup(ICMSConstant.APROVAL_MATRIX_GROUP_TYPE);

		if (group == null) {
			ApprovalMatrixException e = new ApprovalMatrixException("Cannot find the bond index feed group.");
			e.setErrorCode(IApprovalMatrixProxy.NO_FEED_GROUP);
			throw e;
		}

		IApprovalMatrixTrxValue bb = new OBApprovalMatrixTrxValue();
		bb.setReferenceID(String.valueOf(group.getApprovalMatrixGroupID()));
		bb.setApprovalMatrixGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_APROVAL_MATRIX_GROUP);
		return operate(bb, param);
	}

	/**
	 * Get the transaction value containing ApprovalMatrixGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IApprovalMatrixTrxValue
	 */
	public IApprovalMatrixTrxValue getApprovalMatrixGroupByTrxID(long trxID) throws ApprovalMatrixException {
		IApprovalMatrixTrxValue trxValue = new OBApprovalMatrixTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_APROVAL_MATRIX_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the ApprovalMatrixGroup
	 * object
	 * @param aFeedGroup - IApprovalMatrixGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 * @return IApprovalMatrixTrxValue the saved trxValue
	 */
	public IApprovalMatrixTrxValue makerUpdateApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aFeedGroupTrxValue, IApprovalMatrixGroup aFeedGroup) throws ApprovalMatrixException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_APROVAL_MATRIX_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the ApprovalMatrixGroup
	 * object
	 * @param aFeedGroup - IApprovalMatrixGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public IApprovalMatrixTrxValue makerSubmitApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aFeedGroupTrxValue, IApprovalMatrixGroup aFeedGroup) throws ApprovalMatrixException {

		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_APROVAL_MATRIX_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws ApprovalMatrixException
	 */
	public IApprovalMatrixTrxValue makerSubmitRejectedApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aTrxValue) throws ApprovalMatrixException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateApprovalMatrixGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	public IApprovalMatrixTrxValue makerUpdateRejectedApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aTrxValue) throws ApprovalMatrixException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a ApprovalMatrixGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	public IApprovalMatrixTrxValue makerCloseRejectedApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aTrxValue) throws ApprovalMatrixException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a ApprovalMatrixGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	public IApprovalMatrixTrxValue makerCloseDraftApprovalMatrixGroup(ITrxContext anITrxContext,
			IApprovalMatrixTrxValue aTrxValue) throws ApprovalMatrixException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_APROVAL_MATRIX_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws ApprovalMatrixEntryException
	 */
	public IApprovalMatrixEntry getApprovalMatrixEntryByRic(String ric) throws ApprovalMatrixEntryException {
		return getApprovalMatrixBusManager().getApprovalMatrixEntryByRic(ric);
	}
}
