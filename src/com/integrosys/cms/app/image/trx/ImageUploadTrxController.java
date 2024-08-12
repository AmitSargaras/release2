package com.integrosys.cms.app.image.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * @author Govind.Sahu
 *  Image Upload Trx controller to manage trx operations
 */
public class ImageUploadTrxController extends CMSTrxController {

    private Map nameTrxOperationMap;

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

    /**
     * Default Constructor
     */
    public ImageUploadTrxController() {
        super();
    }

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     * Not implemented.
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_IMAGE_UPLOAD;
    }

    /**
     * Returns an ITrxOperation object
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException
     *          on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }

    /**
     * Helper method to factory the operations
     *
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
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateImageUploadOperation");
            }
             if (action.equals(ICMSConstant.ACTION_READ_IMAGE_UPLOAD))
            {
            	 return (ITrxOperation) getNameTrxOperationMap().get("ReadImageUploadOperation");
            	
            } if (action.equals(ICMSConstant.ACTION_READ_IMAGE_UPLOAD_ID))
            {
            	 return (ITrxOperation) getNameTrxOperationMap().get("ReadImageUploadIDOperation");
            }

            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }         
        /******************Checker Approve Create And Reject****************/
        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
        if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateImageUploadOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectImageUploadOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        /*********************************/
//        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
//            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_IMAGE_UPLOAD)) {
//                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateImageUploadOperation");
//            }
//
//            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_UPLOAD)) {
//                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectImageUploadOperation");
//            }
//            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
//        } 
        else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateImageUploadOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteImageUploadOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } 
//        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
//            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_IMAGE_UPLOAD)) {
//                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateImageUploadOperation");
//            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_UPLOAD)) {
//                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectImageUploadOperation");
//            }
//            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
//        } 
        
        else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteImageUploadOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_UPLOAD)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectImageUploadOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_IMAGE_UPLOAD)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateImageUploadOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateImageUploadOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteImageUploadOperation");
                }
            } //else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_IMAGE_UPLOAD)) {
            else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_IMAGE_UPLOAD)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateImageUploadOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateImageUploadOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteImageUploadOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
