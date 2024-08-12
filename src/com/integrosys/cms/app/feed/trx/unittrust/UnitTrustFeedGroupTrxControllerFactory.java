/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/UnitTrustFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:19 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.unittrust;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/12 10:15:19 $ Tag: $Name: $
 */
public class UnitTrustFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	private ITrxController unitTrustFeedGroupReadController;

	private ITrxController unitTrustFeedGroupTrxContoller;

	public ITrxController getUnitTrustFeedGroupReadController() {
		return unitTrustFeedGroupReadController;
	}

	public void setUnitTrustFeedGroupReadController(ITrxController unitTrustFeedGroupReadController) {
		this.unitTrustFeedGroupReadController = unitTrustFeedGroupReadController;
	}

	public ITrxController getUnitTrustFeedGroupTrxContoller() {
		return unitTrustFeedGroupTrxContoller;
	}

	public void setUnitTrustFeedGroupTrxContoller(ITrxController unitTrustFeedGroupTrxContoller) {
		this.unitTrustFeedGroupTrxContoller = unitTrustFeedGroupTrxContoller;
	}

	public UnitTrustFeedGroupTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (param.getAction().equals(ICMSConstant.ACTION_READ_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_UNIT_TRUST_FEED_GROUP)) {
			return getUnitTrustFeedGroupTrxContoller();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}
}
