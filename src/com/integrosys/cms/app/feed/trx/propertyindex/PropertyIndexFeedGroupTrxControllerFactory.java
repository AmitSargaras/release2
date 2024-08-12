/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/PropertyIndexFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.propertyindex;

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
 * @since $Date: 2003/09/12 10:15:18 $ Tag: $Name: $
 */
public class PropertyIndexFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	public PropertyIndexFeedGroupTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_PROPERTY_INDEX_FEED_GROUP)) {
			return new PropertyIndexFeedGroupTrxController();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}
}
