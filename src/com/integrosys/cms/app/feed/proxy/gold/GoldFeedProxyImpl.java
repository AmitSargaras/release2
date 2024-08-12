package com.integrosys.cms.app.feed.proxy.gold;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedBusManager;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.gold.OBGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class GoldFeedProxyImpl implements IGoldFeedProxy {

	private IGoldFeedBusManager goldFeedBusManager;

	private IGoldFeedBusManager stagingGoldFeedBusManager;

	private ITrxControllerFactory TrxControllerFactory;

	/**
	 * @return the goldFeedBusManager
	 */
	public IGoldFeedBusManager getGoldFeedBusManager() {
		return goldFeedBusManager;
	}

	/**
	 * @param goldFeedBusManager the goldFeedBusManager to set
	 */
	public void setGoldFeedBusManager(IGoldFeedBusManager goldFeedBusManager) {
		this.goldFeedBusManager = goldFeedBusManager;
	}

	/**
	 * @return the stagingGoldFeedBusManager
	 */
	public IGoldFeedBusManager getStagingGoldFeedBusManager() {
		return stagingGoldFeedBusManager;
	}

	/**
	 * @param stagingGoldFeedBusManager the stagingGoldFeedBusManager to set
	 */
	public void setStagingGoldFeedBusManager(IGoldFeedBusManager stagingGoldFeedBusManager) {
		this.stagingGoldFeedBusManager = stagingGoldFeedBusManager;
	}

	/**
	 * @return the trxControllerFactory
	 */
	public ITrxControllerFactory getTrxControllerFactory() {
		return TrxControllerFactory;
	}

	/**
	 * @param trxControllerFactory the trxControllerFactory to set
	 */
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		TrxControllerFactory = trxControllerFactory;
	}

	protected void rollback() throws GoldFeedGroupException {
	}

	public IGoldFeedGroupTrxValue checkerApproveGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aTrxValue) throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IGoldFeedGroupTrxValue checkerRejectGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IGoldFeedGroupTrxValue getGoldFeedGroup() throws GoldFeedGroupException {
		IGoldFeedGroupTrxValue vv = new OBGoldFeedGroupTrxValue();
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GOLD_FEED_GROUP);

		return operate(vv, param);
	}

	public IGoldFeedGroupTrxValue getGoldFeedGroup(long groupID) throws GoldFeedGroupException {
		// TODO Auto-generated method stub
		return null;
	}

	public IGoldFeedGroupTrxValue getGoldFeedGroupByTrxID(long trxID) throws GoldFeedGroupException {
		IGoldFeedGroupTrxValue trxValue = new OBGoldFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GOLD_FEED_GROUP);
		return operate(trxValue, param);
	}

	public IGoldFeedGroupTrxValue makerCloseDraftGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aTrxValue) throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IGoldFeedGroupTrxValue makerCloseRejectedGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aTrxValue) throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IGoldFeedGroupTrxValue makerSubmitGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aFeedGroupTrxValue, IGoldFeedGroup aFeedGroup) throws GoldFeedGroupException {
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_GOLD_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);
	}

	public IGoldFeedGroupTrxValue makerSubmitRejectedGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aTrxValue) throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IGoldFeedGroupTrxValue makerUpdateGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aFeedGroupTrxValue,
			IGoldFeedGroup aFeedGroup) throws GoldFeedGroupException {
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_GOLD_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	public IGoldFeedGroupTrxValue makerUpdateRejectedGoldFeedGroup(ITrxContext anITrxContext,
			IGoldFeedGroupTrxValue aTrxValue) throws GoldFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_GOLD_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param aTrxValue -
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	protected IGoldFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_GOLD_FEED_GROUP);

		return aTrxValue;
	}

	protected IGoldFeedGroupTrxValue operate(IGoldFeedGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GoldFeedGroupException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IGoldFeedGroupTrxValue) result.getTrxValue();
	}

	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GoldFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new GoldFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new GoldFeedGroupException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new GoldFeedGroupException(ex.toString());
		}
	}
	
	protected IGoldFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IGoldFeedGroup aFeedGroup) {

		IGoldFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBGoldFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBGoldFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IGoldFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingGoldFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}
}
