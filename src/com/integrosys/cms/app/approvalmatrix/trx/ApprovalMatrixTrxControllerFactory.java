/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/ApprovalMatrixGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.approvalmatrix.trx;

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
public class ApprovalMatrixTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController approvalMatrixGroupReadController;

	private ITrxController approvalMatrixGroupTrxContoller;
	
	public ITrxController getApprovalMatrixGroupReadController() {
		return approvalMatrixGroupReadController;
	}

	public void setApprovalMatrixGroupReadController(ITrxController approvalMatrixGroupReadController) {
		this.approvalMatrixGroupReadController = approvalMatrixGroupReadController;
	}

	public ITrxController getApprovalMatrixGroupTrxContoller() {
		return approvalMatrixGroupTrxContoller;
	}

	public void setApprovalMatrixGroupTrxContoller(ITrxController approvalMatrixGroupTrxContoller) {
		this.approvalMatrixGroupTrxContoller = approvalMatrixGroupTrxContoller;
	}
	
	public ApprovalMatrixTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_APROVAL_MATRIX_GROUP)) {
			return getApprovalMatrixGroupTrxContoller();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	
}
