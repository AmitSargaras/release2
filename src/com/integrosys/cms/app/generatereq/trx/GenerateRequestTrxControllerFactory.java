/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/GenerateRequestTrxControllerFactory.java,v 1.2 2003/09/12 17:43:20 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

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
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 17:43:20 $ Tag: $Name: $
 */
public class GenerateRequestTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public GenerateRequestTrxControllerFactory() {
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
		if (isWaiverRequestOperation(param.getAction())) {
			if (isReadOperation(param.getAction())) {
				return new WaiverRequestReadController();
			}
			return new WaiverRequestTrxController();
		}

		if (isReadOperation(param.getAction())) {
			return new DeferralRequestReadController();
		}
		return new DeferralRequestTrxController();
	}

	private boolean isWaiverRequestOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_READ_WAIVER_REQ_ID))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_RM_APPROVE_GENERATE_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_RM_REJECT_GENERATE_WAIVER_REQ))) {
			return true;
		}
		return false;
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_WAIVER_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_READ_WAIVER_REQ_ID))
				|| (anAction.equals(ICMSConstant.ACTION_READ_DEFERRAL_REQ))
				|| (anAction.equals(ICMSConstant.ACTION_READ_DEFERRAL_REQ_ID))) {
			return true;
		}
		return false;
	}

}