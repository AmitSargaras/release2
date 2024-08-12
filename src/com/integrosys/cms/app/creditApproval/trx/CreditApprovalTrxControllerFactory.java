
package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $govind.sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/04/07 10:15:18 $ Tag: $Name: $
 */
public class CreditApprovalTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController creditApprovalReadController;

	private ITrxController creditApprovalTrxContoller;
	
    private ITrxController creditApprovalInsertFileTrxController;

    private ITrxController readCreditApprovalInsertFileTrxController;
	


	/**
	 * @return the creditApprovalReadController
	 */
	public ITrxController getCreditApprovalReadController() {
		return creditApprovalReadController;
	}

	/**
	 * @param creditApprovalReadController the creditApprovalReadController to set
	 */
	public void setCreditApprovalReadController(
			ITrxController creditApprovalReadController) {
		this.creditApprovalReadController = creditApprovalReadController;
	}


	
	/**
	 * @return the creditApprovalTrxContoller
	 */
	public ITrxController getCreditApprovalTrxContoller() {
		return creditApprovalTrxContoller;
	}

	/**
	 * @param creditApprovalTrxContoller the creditApprovalTrxContoller to set
	 */
	public void setCreditApprovalTrxContoller(
			ITrxController creditApprovalTrxContoller) {
		this.creditApprovalTrxContoller = creditApprovalTrxContoller;
	}

	public CreditApprovalTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_CREDIT_APPROVAL)) {
			return getCreditApprovalReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL_FEED_GROUP)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_DELETE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CREATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SAVE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL)) {
			return getCreditApprovalTrxContoller();
		}//For File Upload
		else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getCreditApprovalInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getCreditApprovalInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadCreditApprovalInsertFileTrxController();
        }
		
		
		

		throw new CreditApprovalException("Action " + param.getAction() + " is not recognised!!!");
	}

	/**
	 * @return the creditApprovalInsertFileTrxController
	 */
	public ITrxController getCreditApprovalInsertFileTrxController() {
		return creditApprovalInsertFileTrxController;
	}

	/**
	 * @param creditApprovalInsertFileTrxController the creditApprovalInsertFileTrxController to set
	 */
	public void setCreditApprovalInsertFileTrxController(
			ITrxController creditApprovalInsertFileTrxController) {
		this.creditApprovalInsertFileTrxController = creditApprovalInsertFileTrxController;
	}

	/**
	 * @return the readCreditApprovalInsertFileTrxController
	 */
	public ITrxController getReadCreditApprovalInsertFileTrxController() {
		return readCreditApprovalInsertFileTrxController;
	}

	/**
	 * @param readCreditApprovalInsertFileTrxController the readCreditApprovalInsertFileTrxController to set
	 */
	public void setReadCreditApprovalInsertFileTrxController(
			ITrxController readCreditApprovalInsertFileTrxController) {
		this.readCreditApprovalInsertFileTrxController = readCreditApprovalInsertFileTrxController;
	}

	
}
