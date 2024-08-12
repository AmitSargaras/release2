/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/SCCertificateTrxControllerFactory.java,v 1.3 2003/11/06 02:08:03 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

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
 * @version $Revision: 1.3 $
 * @since $Date: 2003/11/06 02:08:03 $ Tag: $Name: $
 */
public class SCCertificateTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public SCCertificateTrxControllerFactory() {
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
		if (isPartialSCC(param.getAction())) {
			if (isReadOperation(param.getAction())) {
				return new PartialSCCertificateReadController();
			}
			return new PartialSCCertificateTrxController();
		}
		if (isReadOperation(param.getAction())) {
			return new SCCertificateReadController();
		}
		return new SCCertificateTrxController();
	}

	/**
	 * To check if the action requires a pscc operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isPartialSCC(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_PSCC)) || (anAction.equals(ICMSConstant.ACTION_READ_PSCC_ID))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_GENERATE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_RESET_PSCC))) {
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
		if ((anAction.equals(ICMSConstant.ACTION_READ_SCC)) || (anAction.equals(ICMSConstant.ACTION_READ_SCC_ID))
				|| (anAction.equals(ICMSConstant.ACTION_READ_PSCC))
				|| (anAction.equals(ICMSConstant.ACTION_READ_PSCC_ID))) {
			return true;
		}
		return false;
	}
}