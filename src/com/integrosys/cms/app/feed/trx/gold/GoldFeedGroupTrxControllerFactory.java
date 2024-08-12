package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class GoldFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	private ITrxController goldFeedGroupReadController;

	private ITrxController goldFeedGroupTrxController;

	/**
	 * @return the goldFeedGroupReadController
	 */
	public ITrxController getGoldFeedGroupReadController() {
		return goldFeedGroupReadController;
	}

	/**
	 * @param goldFeedGroupReadController the goldFeedGroupReadController to set
	 */
	public void setGoldFeedGroupReadController(ITrxController goldFeedGroupReadController) {
		this.goldFeedGroupReadController = goldFeedGroupReadController;
	}

	/**
	 * @return the goldFeedGroupTrxController
	 */
	public ITrxController getGoldFeedGroupTrxController() {
		return goldFeedGroupTrxController;
	}

	/**
	 * @param goldFeedGroupTrxController the goldFeedGroupTrxController to set
	 */
	public void setGoldFeedGroupTrxController(ITrxController goldFeedGroupTrxController) {
		this.goldFeedGroupTrxController = goldFeedGroupTrxController;
	}

	public GoldFeedGroupTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (param.getAction().equals(ICMSConstant.ACTION_READ_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_GOLD_FEED_GROUP)) {
			return getGoldFeedGroupTrxController();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

}
