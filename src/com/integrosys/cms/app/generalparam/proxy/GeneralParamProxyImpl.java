/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/BondFeedProxyImpl.java,v 1.7 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.generalparam.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.GeneralParamEntryException;
import com.integrosys.cms.app.generalparam.bus.GeneralParamGroupException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamBusManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.generalparam.trx.OBGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class GeneralParamProxyImpl implements IGeneralParamProxy {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IBondFeedGroup
	 * @throws GeneralParamGroupException on errors retrieving the bond feed
	 */

	private IGeneralParamBusManager generalParamBusManager;

	private IGeneralParamBusManager stagingGeneralParamBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IGeneralParamBusManager getGeneralParamBusManager() {
		return generalParamBusManager;
	}

	public void setGeneralParamBusManager(
			IGeneralParamBusManager generalParamBusManager) {
		this.generalParamBusManager = generalParamBusManager;
	}

	public IGeneralParamBusManager getStagingGeneralParamBusManager() {
		return stagingGeneralParamBusManager;
	}

	public void setStagingGeneralParamBusManager(
			IGeneralParamBusManager stagingGeneralParamBusManager) {
		this.stagingGeneralParamBusManager = stagingGeneralParamBusManager;
	}

	protected IGeneralParamGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_GENERAL_PARAM_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GeneralParamGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new GeneralParamGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new GeneralParamGroupException(e);
		}
		catch (Exception ex) {
			throw new GeneralParamGroupException(ex.toString());
		}
	}

	protected IGeneralParamGroupTrxValue operate(IGeneralParamGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GeneralParamGroupException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IGeneralParamGroupTrxValue) result.getTrxValue();
	}

	protected IGeneralParamGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IGeneralParamGroup aFeedGroup) {

		IGeneralParamGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBGeneralParamGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBGeneralParamGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IGeneralParamGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingGeneralParamGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IGeneralParamGroupTrxValue getGeneralParamGroup(long groupID) throws GeneralParamGroupException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IGeneralParamGroup getActualGeneralParamGroup() throws GeneralParamGroupException {
		return getGeneralParamBusManager().getGeneralParamGroup(ICMSConstant.GENERAL_PARAM_GROUP_TYPE);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	public IGeneralParamGroupTrxValue checkerApproveGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aTrxValue) throws GeneralParamGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */

	public IGeneralParamGroupTrxValue checkerRejectGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);
	}

	public IGeneralParamGroupTrxValue getGeneralParamGroup() throws GeneralParamGroupException {
		IGeneralParamBusManager mgr = getGeneralParamBusManager();
		IGeneralParamGroup group = mgr.getGeneralParamGroup(ICMSConstant.GENERAL_PARAM_GROUP_TYPE);

		if (group == null) {
			GeneralParamGroupException e = new GeneralParamGroupException("Cannot find the bond index feed group.");
			e.setErrorCode(IGeneralParamProxy.NO_FEED_GROUP);
			throw e;
		}

		IGeneralParamGroupTrxValue bb = new OBGeneralParamGroupTrxValue();
		bb.setReferenceID(String.valueOf(group.getGeneralParamGroupID()));
		bb.setGeneralParamGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GENERAL_PARAM_GROUP);
		return operate(bb, param);
	}

	/**
	 * Get the transaction value containing GeneralParamGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IGeneralParamGroupTrxValue
	 */
	public IGeneralParamGroupTrxValue getGeneralParamGroupByTrxID(long trxID) throws GeneralParamGroupException {
		IGeneralParamGroupTrxValue trxValue = new OBGeneralParamGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GENERAL_PARAM_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the GeneralParamGroup
	 * object
	 * @param aFeedGroup - IGeneralParamGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 * @return IGeneralParamGroupTrxValue the saved trxValue
	 */
	public IGeneralParamGroupTrxValue makerUpdateGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aFeedGroupTrxValue, IGeneralParamGroup aFeedGroup) throws GeneralParamGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_GENERAL_PARAM_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the GeneralParamGroup
	 * object
	 * @param aFeedGroup - IGeneralParamGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public IGeneralParamGroupTrxValue makerSubmitGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aFeedGroupTrxValue, IGeneralParamGroup aFeedGroup) throws GeneralParamGroupException {

		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_GENERAL_PARAM_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws GeneralParamGroupException
	 */
	public IGeneralParamGroupTrxValue makerSubmitRejectedGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aTrxValue) throws GeneralParamGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateGeneralParamGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	public IGeneralParamGroupTrxValue makerUpdateRejectedGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aTrxValue) throws GeneralParamGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a GeneralParamGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	public IGeneralParamGroupTrxValue makerCloseRejectedGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aTrxValue) throws GeneralParamGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a GeneralParamGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	public IGeneralParamGroupTrxValue makerCloseDraftGeneralParamGroup(ITrxContext anITrxContext,
			IGeneralParamGroupTrxValue aTrxValue) throws GeneralParamGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GENERAL_PARAM_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws GeneralParamEntryException
	 */
	public IGeneralParamEntry getGeneralParamEntryByRic(String ric) throws GeneralParamEntryException {
		return getGeneralParamBusManager().getGeneralParamEntryByRic(ric);
	}

	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode) throws GeneralParamEntryException {
		return getGeneralParamBusManager().getGeneralParamEntryByParamCodeActual(paramCode);
	}
}
