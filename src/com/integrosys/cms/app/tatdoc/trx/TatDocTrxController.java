package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 6:08:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatDocTrxController extends CMSTrxController {


    private Map nameTrxOperationMap;

    // ****************************
    // Getter and Setter Methods
    // ****************************
    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }


    // ****************************
    // Original Methods
    // ****************************
    /**
     * Default Constructor
     */
    public TatDocTrxController() {
    }

    
    /**
     * Return the instance name associated to this ITrxController. The instance
     * name refers to the instance of the state transition table
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_TAT_DOC;
    }

    /**
     * Get transaction operation given the transaction value and parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
     *         transaction parameter is invalid
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        return op;
    }


    /**
     * Helper method to get the operation given the transaction value and
     * transaction parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction operation
     * @throws TrxParameterException if the transaction parameter is invalid
     */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        String action = param.getAction();
        if (null == action) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }

        String toState = value.getToState();

        if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_TAT_DOC)) {
            if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateOperation");
            }
            else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
            }
        }

        if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_TAT_DOC)) {
            return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
        }

        if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_TAT_DOC)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseOperation");
        }

        if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_TAT_DOC)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateOperation");
        }

        if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_TAT_DOC)) {
            if (toState == null) { // new - ND
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateNDOperation");
            }
            else if (toState.equals(ICMSConstant.STATE_DRAFT) || toState.equals(ICMSConstant.STATE_REJECTED)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateOperation");
            }
        }

        if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_TAT_DOC)) {
            if (toState == null) { // new - ND
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveNDOperation");
            }
            if (toState.equals(ICMSConstant.STATE_ACTIVE) || toState.equals(ICMSConstant.STATE_DRAFT)
                    || toState.equals(ICMSConstant.STATE_REJECTED)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveOperation");
            }
        }


        if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_TAT_DOC)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
        }

        DefaultLogger.debug(this, "No operations found !");

        throw new TrxParameterException("No operations found!");
    }



}
