/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/DocumentItemTrxController.java,v 1.4 2003/07/03 08:15:51 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/03 08:15:51 $ Tag: $Name: $
 */
public class DocumentItemTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public DocumentItemTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table. Not
	 * implemented.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_DOC_ITEM_LIST;
	}

	/**
	 * Returns an ITrxOperation object
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to factory the operations
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on error
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
	    if(toState!=null){
		if(toState.equals(ICMSConstant.STATE_DRAFT)){
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM)) {
        		return new MakerUpdateDraftCreateDocOperation();

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DOC_ITEM)) {
        		return new MakerUpdateDocItemOperation();
        	}
        }}
		if (action.equals(ICMSConstant.ACTION_MAKER_DRAFT_DOC_ITEM)) {
			return new MakerDraftDocItemOperation();
		}
		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM)) {
				return new MakerCreateDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DOC_ITEM)) {
				return new CheckerApproveCreateDocItemOperation();
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DOC_ITEM)) {
				return new CheckerRejectDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DOC_ITEM)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateDocItemOperation();
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return new MakerCloseRejectedDeleteDocItemOperation();
				}
				return new MakerCloseRejectedUpdateDocItemOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DOC_ITEM)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateDocItemOperation();
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return new MakerEditRejectedDeleteDocItemOperation();
				}
				return new MakerEditRejectedUpdateDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DOC_ITEM)) {
				return new MakerUpdateDocItemOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_DOC_ITEM)) {
				return new MakerDeleteDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DOC_ITEM)) {
				return new CheckerApproveUpdateDocItemOperation();
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DOC_ITEM)) {
				return new CheckerRejectDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DOC_ITEM)) {
				return new CheckerApproveDeleteDocItemOperation();
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DOC_ITEM)) {
				return new CheckerRejectDocItemOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("From State does not match presets! No operations found!");
	}
}