/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/DigitalLibraryGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.digitalLibrary.trx;

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
public class DigitalLibraryTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController digitalLibraryGroupReadController;

	private ITrxController digitalLibraryGroupTrxContoller;
	
	public ITrxController getDigitalLibraryGroupReadController() {
		return digitalLibraryGroupReadController;
	}

	public void setDigitalLibraryGroupReadController(ITrxController digitalLibraryGroupReadController) {
		this.digitalLibraryGroupReadController = digitalLibraryGroupReadController;
	}

	public ITrxController getDigitalLibraryGroupTrxContoller() {
		return digitalLibraryGroupTrxContoller;
	}

	public void setDigitalLibraryGroupTrxContoller(ITrxController digitalLibraryGroupTrxContoller) {
		this.digitalLibraryGroupTrxContoller = digitalLibraryGroupTrxContoller;
	}
	
	public DigitalLibraryTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_DIGITAL_LIBRARY_GROUP)) {
			return getDigitalLibraryGroupTrxContoller();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	
}
