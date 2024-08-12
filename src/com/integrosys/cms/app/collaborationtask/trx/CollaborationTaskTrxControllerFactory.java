/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/CollaborationTaskTrxControllerFactory.java,v 1.4 2006/04/13 06:26:50 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create collaboration task related
 * controllers.
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/04/13 06:26:50 $ Tag: $Name: $
 */
public class CollaborationTaskTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CollaborationTaskTrxControllerFactory() {
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
		if (isCollateralTaskRelated(param.getAction())) {
			if (isReadOperation(param.getAction())) {
				return new CollateralTaskReadController();
			}
			return new CollateralTaskTrxController();
		}

		if (isReadOperation(param.getAction())) {
			return new CCTaskReadController();
		}
		return new CCTaskTrxController();
	}

	/**
	 * To check if the action is a collateral task related
	 * @param anAction - String
	 * @return boolean - true if it is document related and false otherwise
	 */
	private boolean isCollateralTaskRelated(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_TASK_ID))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_REJECT_COLLATERAL_TASK))) {
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
		if ((anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_TASK_ID))
				|| (anAction.equals(ICMSConstant.ACTION_READ_CC_TASK))
				|| (anAction.equals(ICMSConstant.ACTION_READ_CC_TASK_ID)))

		{
			return true;
		}
		return false;
	}
}