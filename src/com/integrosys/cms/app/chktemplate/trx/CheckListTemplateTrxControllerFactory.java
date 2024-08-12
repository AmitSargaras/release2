package com.integrosys.cms.app.chktemplate.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.*;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 17, 2008
 * Time: 12:40:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckListTemplateTrxControllerFactory implements ITrxControllerFactory {

    /**
     * Default Constructor
     */
    public CheckListTemplateTrxControllerFactory() {
        super();
    }

    /**
     * Returns an ITrxController given the ITrxValue and ITrxParameter objects
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return ITrxController
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if any error occurs
     */
    public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        if (isDocumentRelated(param.getAction())) {
            if (isReadOperation(param.getAction())) {
                return new DocumentItemReadController();
            }
            return new DocumentItemTrxController();
        }
        if (isTemplateRelated(param.getAction())) {
            if (isReadOperation(param.getAction())) {
                return new TemplateReadController();
            }
            return new TemplateTrxController();
        }

        throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
    }


    /**
	 * To check if the action is a document related one
     * @param anAction - String
     * @return boolean - true if it is document related and false otherwise
     */
    protected boolean isDocumentRelated(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_DRAFT_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_DELETE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_DOC_ITEM))
                || (anAction.equals(ICMSConstant.ACTION_READ_DOC_ITEM))) {
            return true;
        }
        return false;
    }

    /**
	 * To check if the action is a template related one
     * @param anAction - String
     * @return boolean - true if it is template related and false otherwise
     */
    protected boolean isTemplateRelated(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_MAKER_CREATE_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_TEMPLATE))
                || (anAction.equals(ICMSConstant.ACTION_READ_TEMPLATE))) {
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
		if ((anAction.equals(ICMSConstant.ACTION_READ_DOC_ITEM))
				|| (anAction.equals(ICMSConstant.ACTION_READ_TEMPLATE))) {
			return true;
		}
		return false;
	}
}
