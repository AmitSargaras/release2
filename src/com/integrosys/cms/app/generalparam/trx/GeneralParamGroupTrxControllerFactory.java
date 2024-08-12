/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/BondFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.generalparam.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class GeneralParamGroupTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController generalParamGroupReadController;

	private ITrxController generalParamGroupTrxContoller;
	
	public ITrxController getGeneralParamGroupReadController() {
		return generalParamGroupReadController;
	}

	public void setGeneralParamGroupReadController(ITrxController generalParamGroupReadController) {
		this.generalParamGroupReadController = generalParamGroupReadController;
	}

	public ITrxController getGeneralParamGroupTrxContoller() {
		return generalParamGroupTrxContoller;
	}

	public void setGeneralParamGroupTrxContoller(ITrxController generalParamGroupTrxContoller) {
		this.generalParamGroupTrxContoller = generalParamGroupTrxContoller;
	}
	
	public GeneralParamGroupTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_GENERAL_PARAM_GROUP)) {
			return getGeneralParamGroupTrxContoller();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	
}
