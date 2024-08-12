/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/ProfileTrxControllerFactory.java,v 1.3 2004/07/21 02:36:13 cchen Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: cchen $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/21 02:36:13 $ Tag: $Name: $
 */
public class ProfileTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public ProfileTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
	 *         any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (isReadOperation(param.getAction())) {
			return new ProfileReadController();
		}
		// else if( isNonReadOperation(param.getAction())) {
		// return new ProfileTrxController();
		// } else

		// throw new TrxParameterException("Action '" + param.getAction() +
		// "' is invalid ( not supported )  !!!");
		else {
			return new ProfileTrxController();
		}
	}

	private boolean isReadOperation(String operation) {
		return (operation.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID)
				|| operation.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID) || operation
				.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_PROFILE_GROUP));
	}

	private boolean isNonReadOperation(String operation) {

		return (operation.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN)
				|| operation.equals(ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN) || operation
				.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN));

	}

}